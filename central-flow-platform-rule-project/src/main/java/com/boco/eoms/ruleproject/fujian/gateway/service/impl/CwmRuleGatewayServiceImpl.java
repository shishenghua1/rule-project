package com.boco.eoms.ruleproject.fujian.gateway.service.impl;

import com.boco.eoms.ruleproject.base.util.HttpClientServlet;
import com.boco.eoms.ruleproject.fujian.gateway.controller.CwmRuleGatewayController;
import com.boco.eoms.ruleproject.fujian.gateway.model.CwmRuleGateway;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.boco.eoms.ruleproject.fujian.gateway.mapper.CwmRuleGatewayMapper;
import com.boco.eoms.ruleproject.fujian.gateway.service.ICwmRuleGatewayService;
import com.boco.eoms.ruleproject.base.util.ReturnJsonUtil;
import com.boco.eoms.ruleproject.base.util.StaticMethod;
import com.boco.eoms.ruleproject.base.util.UUIDHexGenerator;

import java.util.ArrayList;
import java.util.List;

/**
* @description:规则网关service实现层
* @author ssh
* @date 2020-01-13 09:52:44
*/
@Service("cwmRuleGatewayService")
public class CwmRuleGatewayServiceImpl implements ICwmRuleGatewayService {

    @Autowired
    private CwmRuleGatewayMapper cwmRuleGatewayMapper;
    @Autowired
    private Environment env;
    private Logger logger = LoggerFactory.getLogger(CwmRuleGatewayServiceImpl.class);

    public List<CwmRuleGateway> getGateway(String moduleId) throws Exception {
        //查询网关并统计对应关联集合数量
        List<CwmRuleGateway> cwmRuleGatewayList = cwmRuleGatewayMapper.getGateway(moduleId);
        //给元素赋值数量
        if(cwmRuleGatewayList!=null&&cwmRuleGatewayList.size()>0){
            for (int i = 0; i < cwmRuleGatewayList.size(); i++) {
                int gatewayRuleNum = cwmRuleGatewayMapper.getGateWayRuleNum(cwmRuleGatewayList.get(i).getId());
                cwmRuleGatewayList.get(i).setGroupNum(gatewayRuleNum);
            }
        }

        //创建全部规则的对象
        CwmRuleGateway cwmRuleGateway = new CwmRuleGateway();
        //查询全部的规则数量
        int ruleNum = cwmRuleGatewayMapper.getRuleNum(moduleId);
        cwmRuleGateway.setGatewayName("全量规则库");
        cwmRuleGateway.setGroupNum(ruleNum);
        cwmRuleGatewayList.add(0,cwmRuleGateway);
        return cwmRuleGatewayList;
    }

    @Transactional
    public void updateByPrimaryKey(CwmRuleGateway cwmRuleGateway) throws Exception {
        cwmRuleGatewayMapper.updateByPrimaryKey(cwmRuleGateway);
        //重新加载该规则
        String ip = env.getProperty("ruleReloadInfo.ip");
        String port = env.getProperty("ruleReloadInfo.port");
        String version = env.getProperty("ruleReloadInfo.version");
        String ruleId = cwmRuleGateway.getId();
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