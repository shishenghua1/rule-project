package com.boco.eoms.ruleproject.fujian.simulation.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.boco.eoms.ruleproject.base.paging.ReturnData;
import com.boco.eoms.ruleproject.base.util.StaticMethod;
import com.boco.eoms.ruleproject.fujian.rulegateway.model.CwmRuleGroupGatewayRel;
import com.boco.eoms.ruleproject.fujian.rulegateway.model.QueryCondition;
import com.boco.eoms.ruleproject.fujian.simulation.model.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ssh
 * 类说明：网关仿真service
 */
public interface ISimulationService {

    /**
     * 网关仿真数据文件导入
     * @param file
     * @param gatewayIds
     * @return
     */
    JSONObject upload(MultipartFile file, String gatewayIds, HttpServletRequest req) throws Exception;

    /**
     * 仿真数据导出
     * @param gatewayIds
     */
    void download(String gatewayIds, HttpServletResponse response)throws Exception;

    /**
     * 根据父节点地域id获取地市
     * @param parentAreaId
     * @return
     */
    JSONObject getCitys(String parentAreaId);

    /**
     * 仿真数据保存接口
     * @param param
     * @return
     */
    JSONObject saveSimulationData(JSONObject param) throws Exception;

    /**
     * 仿真运行数据保存接口
     * @param param
     * @return
     */
    JSONObject updateSimulationRunData(JSONObject param) throws Exception;

    /**
     * 根据父节点字典id获取字典内容
     * @param parentDictId
     * @return
     */
    JSONObject getDicts(String parentDictId);

    /**
     * 仿真数据获取
     * @param simulationParam
     * @return
     */
    JSONObject getSimulationData(SimulationParam simulationParam,HttpServletRequest req) throws Exception;

    /**
     * 根据网关标识仿真规则
     * @param gatewayId
     * @param gatewayName
     * @param simulationId
     * @param isLastGateWay
     * @return
     */
    JSONObject excuteRule(String gatewayId,String gatewayName,String simulationId,String isLastGateWay) throws Exception;

    /**
     * 仿真数据进度获取
     * @param simulationId
     * @return
     */
    JSONObject getSimulationDataProgess(String simulationId);

    /**
     * 仿真执行进度获取
     * @param gatewayId 网关id
     * @param simulationId 仿真id
     * @return
     */
    JSONObject simulationExecuteProgess(String gatewayId,String simulationId);
    
    /**
     * 获取AI相似度数据
     * @param simulationId
     * @return
     */
    JSONObject simulationSimilarData(String simulationId);
    
    /**
     * 仿真数据统计
     * @param simulationId
     * @return
     */
    JSONObject simulationStatictisData(String simulationId);


    /**
     * 条件查询规则仿真数据
     * @param simulationCondition
     * @return
     * @throws Exception
     */
    ReturnData<Simulation> getSimulations(SimulationCondition simulationCondition) throws Exception;

    /**
     * 条件查询规则仿真工单数据
     * @param sheetCondition
     * @return
     * @throws Exception
     */
    ReturnData<SimulationData> getSimulationDatas(SheetCondition sheetCondition) throws Exception;

    /**
     * 根据仿真数据id查询工单详情
     * @param simulationDataId
     * @return
     */
    JSONObject getSheetDetail(String simulationDataId) throws Exception;
}
