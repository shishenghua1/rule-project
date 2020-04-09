package com.boco.eoms.ruleproject.fujian.rulegateway.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.boco.eoms.ruleproject.base.paging.ReturnData;
import com.boco.eoms.ruleproject.base.util.HttpClientServlet;
import com.boco.eoms.ruleproject.base.util.ReturnJsonUtil;
import com.boco.eoms.ruleproject.base.util.StaticMethod;
import com.boco.eoms.ruleproject.base.util.UUIDHexGenerator;
import com.boco.eoms.ruleproject.fujian.gateway.mapper.CwmRuleGatewayMapper;
import com.boco.eoms.ruleproject.fujian.gateway.service.impl.CwmRuleGatewayServiceImpl;
import com.boco.eoms.ruleproject.fujian.rulegateway.mapper.CwmRuleGroupGatewayRelMapper;
import com.boco.eoms.ruleproject.fujian.rulegateway.model.CwmRuleGroupGatewayRel;
import com.boco.eoms.ruleproject.fujian.rulegateway.model.CwmRuleGroupRel;
import com.boco.eoms.ruleproject.fujian.rulegateway.model.QueryCondition;
import com.boco.eoms.ruleproject.fujian.rulegateway.service.ICwmRuleGroupGatewayRelService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
* @description:规则网关关联service实现层
* @author ssh
* @date 2020-01-13 10:05:11
*/
@Service("cwmRuleGroupGatewayRelService")
public class CwmRuleGroupGatewayRelServiceImpl implements ICwmRuleGroupGatewayRelService {

    @Autowired
    private CwmRuleGroupGatewayRelMapper cwmRuleGroupGatewayRelMapper;
    @Autowired
    private CwmRuleGatewayMapper cwmRuleGatewayMapper;
    @Autowired
    private Environment env;
    private Logger logger = LoggerFactory.getLogger(CwmRuleGroupGatewayRelServiceImpl.class);

    @Transactional
    public void deleteByCondition(String gatewayId,String id) throws Exception {
        cwmRuleGroupGatewayRelMapper.deleteByPrimaryKey(id);
        //查询对应网关规则集
        List<CwmRuleGroupGatewayRel> cwmRuleGroupGatewayRelList =  cwmRuleGroupGatewayRelMapper.queryRuleGateWay("",gatewayId);
        //数据不多，全量重新排序
        if(cwmRuleGroupGatewayRelList!=null&&!cwmRuleGroupGatewayRelList.isEmpty()){
            for (int i = 0; i < cwmRuleGroupGatewayRelList.size(); i++) {
                cwmRuleGroupGatewayRelList.get(i).setRuleOrder(String.valueOf(i+1));
            }
            //批量更新顺序
            cwmRuleGroupGatewayRelMapper.batchUpdate(cwmRuleGroupGatewayRelList);
            //规则重新加载
            reloadRule(gatewayId);
        }
    }

    @Transactional
    public JSONObject batchInsert(List<CwmRuleGroupGatewayRel> cwmRuleGroupGatewayRelList) throws Exception {
        if(cwmRuleGroupGatewayRelList!=null&&cwmRuleGroupGatewayRelList.size()>0){
            //校验规则是否已经关联网关,要求该集合只能在网关下单独使用
            String moduleId = env.getProperty("moduleId.fault");
            int relatedNum = cwmRuleGroupGatewayRelMapper.getRelatedNum(cwmRuleGroupGatewayRelList,moduleId);
            if(relatedNum>0){
                return ReturnJsonUtil.returnFailInfo("关联失败,存在关联的规则");
            }else{
                //获取网关id
                String gatewayId = cwmRuleGroupGatewayRelList.get(0).getGatewayId();
                //网关关联规则需要先保证条件的存在
                String condtionId = cwmRuleGroupGatewayRelMapper.getConditionId(gatewayId);
                if(condtionId==null||"".equals(condtionId)){
                    condtionId = UUIDHexGenerator.getInstance().getID()+"_20_0";
                    CwmRuleGroupRel cwmRuleGroupRel = new CwmRuleGroupRel();
                    cwmRuleGroupRel.setId(UUIDHexGenerator.getInstance().getID());
                    cwmRuleGroupRel.setNodeType("condition");
                    cwmRuleGroupRel.setParentNodeId(gatewayId);
                    cwmRuleGroupRel.setRuleIdRel(condtionId);
                    cwmRuleGroupRel.setRuleInfoRel("and");
                    cwmRuleGroupRel.setOrderBy(0);
                    cwmRuleGroupGatewayRelMapper.insertRule(cwmRuleGroupRel);
                }
                //获取对应网关下最大的序号,并设置排序
                int maxRuleOrder = StaticMethod.nullObject2int(cwmRuleGroupGatewayRelMapper.getMaxRuleOrder(gatewayId));
                for (int i = 0; i < cwmRuleGroupGatewayRelList.size(); i++) {
                    int ruleOrder = maxRuleOrder+i+1;
                    cwmRuleGroupGatewayRelList.get(i).setRuleOrder(String.valueOf(ruleOrder));
                    cwmRuleGroupGatewayRelList.get(i).setGatewayId(condtionId);
                }
                cwmRuleGroupGatewayRelMapper.batchInsert(cwmRuleGroupGatewayRelList);
                //规则重新加载
                reloadRule(gatewayId);
                return ReturnJsonUtil.returnSuccessInfo("关联成功");
            }
        }else{
            return ReturnJsonUtil.returnFailInfo("关联失败，数据为空");
        }
    }


    @Transactional
    public void updateByPrimaryKey(CwmRuleGroupGatewayRel cwmRuleGroupGatewayRel) throws Exception {
        cwmRuleGroupGatewayRelMapper.updateByPrimaryKey(cwmRuleGroupGatewayRel);
        //规则重新加载
        String ruleId = cwmRuleGroupGatewayRelMapper.getRuleIdById(cwmRuleGroupGatewayRel.getId());
        reloadRule(ruleId);
    }

    /**
     * 根据条件查询规则
     * @param queryCondition
     * @return
     * @throws Exception
     */
    public ReturnData<CwmRuleGroupGatewayRel> queryRule(QueryCondition queryCondition) throws Exception {
        //当前页
        Integer curPage = queryCondition.getCurPage();
        //页的大小
        Integer pageSize = queryCondition.getPageSize();
        long total = 0;
        //规则模糊搜索名称
        String ruleName = StaticMethod.nullObject2String(queryCondition.getRuleName());
        //规则是否关联
        String isRelevance = StaticMethod.nullObject2String(queryCondition.getIsRelevance());
        //网关标识
        String gatewayId = StaticMethod.nullObject2String(queryCondition.getGatewayId());
        //模块id
        String moduleId = StaticMethod.nullObject2String(queryCondition.getModuleId());

        List<CwmRuleGroupGatewayRel> cwmRuleGroupGatewayRelList = new ArrayList<>();
        Page page = PageHelper.startPage(curPage,pageSize);
        //判断过滤条件是否包含网关，如果包含则从网关关联表查询，否则从规则结合表全量过滤查询
        if("".equals(gatewayId)){
            cwmRuleGroupGatewayRelList = cwmRuleGroupGatewayRelMapper.queryRule(ruleName,isRelevance,moduleId);
            //如果isRelevance不为true，则查询的是全量规则数据，包括关联网关的数据
            if(cwmRuleGroupGatewayRelList!=null&&!cwmRuleGroupGatewayRelList.isEmpty()){
                for (int i = 0; i < cwmRuleGroupGatewayRelList.size(); i++) {
                    String groupId = cwmRuleGroupGatewayRelList.get(i).getGroupId();
                    //网关的规则集合要求不能复用,则关联的网关集合不会重复
                    List<CwmRuleGroupGatewayRel> cwmRuleGroupGatewayRel= cwmRuleGroupGatewayRelMapper.getGatewayByGroupId(groupId,moduleId);
                    if(cwmRuleGroupGatewayRel!=null&&cwmRuleGroupGatewayRel.size()>0){
                        cwmRuleGroupGatewayRelList.get(i).setGatewayId(StaticMethod.nullObject2String(cwmRuleGroupGatewayRel.get(0).getGatewayId()));
                        cwmRuleGroupGatewayRelList.get(i).setGatewayName(StaticMethod.nullObject2String(cwmRuleGroupGatewayRel.get(0).getGatewayName()));
                    }
                }
            }
        }else{
            cwmRuleGroupGatewayRelList = cwmRuleGroupGatewayRelMapper.queryRuleGateWay(ruleName,gatewayId);
            //对集合按照顺序排序
            Collections.sort(cwmRuleGroupGatewayRelList, new Comparator<CwmRuleGroupGatewayRel>() {
                public int compare(CwmRuleGroupGatewayRel o1, CwmRuleGroupGatewayRel o2) {
                    int ruleOrder1 = StaticMethod.nullObject2int(o1.getRuleOrder());
                    int ruleOrder2 = StaticMethod.nullObject2int(o2.getRuleOrder());
                    if(ruleOrder1 > ruleOrder2){
                        return 1;
                    }
                    if(ruleOrder1 == ruleOrder2){
                        return 0;
                    }
                    return -1;
                }
            });
        }
        //封装数据
        total = page.getTotal();
        ReturnData<CwmRuleGroupGatewayRel> ruleGateWayData = new ReturnData<CwmRuleGroupGatewayRel>();
        ruleGateWayData.setTotal(total);
        ruleGateWayData.setRows(cwmRuleGroupGatewayRelList);
        return ruleGateWayData;
    }

    @Transactional
    public JSONObject changeOrder(List<CwmRuleGroupGatewayRel> groupGatewayRelList) throws Exception {
        JSONObject obj = new JSONObject();
        if(groupGatewayRelList!=null&&!groupGatewayRelList.isEmpty()){
            String gatewayId = groupGatewayRelList.get(0).getGatewayId();
            if(groupGatewayRelList.size()==2){
                String ruleOrder1 = groupGatewayRelList.get(0).getRuleOrder();
                groupGatewayRelList.get(0).setRuleOrder(groupGatewayRelList.get(1).getRuleOrder());
                groupGatewayRelList.get(1).setRuleOrder(ruleOrder1);
                cwmRuleGroupGatewayRelMapper.batchUpdate(groupGatewayRelList);
                obj =  ReturnJsonUtil.returnSuccessInfo("顺序调换成功");
            }else if(groupGatewayRelList.size()>2){
                cwmRuleGroupGatewayRelMapper.batchUpdate(groupGatewayRelList);
                obj =  ReturnJsonUtil.returnSuccessInfo("顺序调换成功");
            }else{
                obj =  ReturnJsonUtil.returnFailInfo("数据异常");
            }
            //规则重新加载
            reloadRule(gatewayId);
            return obj;
        }else{
            return ReturnJsonUtil.returnFailInfo("数据异常");
        }
    }

    /**
     * 规则的重新加载
     * @param ruleId
     * @throws Exception
     */
    public void reloadRule(String ruleId) throws Exception{
        //重新加载该规则
        String ip = env.getProperty("ruleReloadInfo.ip");
        String port = env.getProperty("ruleReloadInfo.port");
        String version = env.getProperty("ruleReloadInfo.version");
        String url = "http://"+ip+":"+port+"/"+version+"/rule/"+ruleId+"/reloadRule";
        JSONObject returnObj = HttpClientServlet.httpPost(url);
        String status = StaticMethod.nullObject2String(returnObj.get("flag"));
        if("success".equals(status)){
            logger.info("规则"+StaticMethod.nullObject2String(returnObj.get("msg")));
        }else{
            throw new Exception();
        }
    }

}