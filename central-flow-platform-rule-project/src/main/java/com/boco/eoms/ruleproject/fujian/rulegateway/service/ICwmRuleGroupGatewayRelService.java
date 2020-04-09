package com.boco.eoms.ruleproject.fujian.rulegateway.service;

import com.alibaba.fastjson.JSONObject;
import com.boco.eoms.ruleproject.base.paging.ReturnData;
import com.boco.eoms.ruleproject.fujian.rulegateway.model.CwmRuleGroupGatewayRel;
import com.boco.eoms.ruleproject.fujian.rulegateway.model.QueryCondition;

import java.util.List;
import java.util.Map;

/**
* @description:规则网关关联service接口
* @author ssh
* @date 2020-01-13 10:05:11
*/
public interface ICwmRuleGroupGatewayRelService {

    /**
     * 条件删除关联关系
     * @param gatewayId
     * @param id
     * @throws Exception
     */
    void deleteByCondition(String gatewayId,String id) throws Exception;

    /**
     * 批量进行规则网关关联
     * @param cwmRuleGroupGatewayRelList
     * @throws Exception
     */
    JSONObject batchInsert(List<CwmRuleGroupGatewayRel> cwmRuleGroupGatewayRelList) throws Exception;

    /**
    *单条数据的修改
    * @param cwmRuleGroupGatewayRel
    * @return
     */
    void updateByPrimaryKey(CwmRuleGroupGatewayRel cwmRuleGroupGatewayRel) throws Exception;

    /**
     * 条件查询规则集合表数据
     * @param queryCondition
     * @return
     * @throws Exception
     */
    ReturnData<CwmRuleGroupGatewayRel> queryRule(QueryCondition queryCondition) throws Exception;

    /**
     * 改变规则顺序
     * @param groupGatewayRelList
     * @throws Exception
     */
    JSONObject changeOrder(List<CwmRuleGroupGatewayRel> groupGatewayRelList) throws Exception;
}