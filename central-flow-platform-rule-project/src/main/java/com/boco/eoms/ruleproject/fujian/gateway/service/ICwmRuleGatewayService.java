package com.boco.eoms.ruleproject.fujian.gateway.service;

import com.boco.eoms.ruleproject.fujian.gateway.model.CwmRuleGateway;

import java.util.List;

/**
* @description:规则网关service接口
* @author ssh
* @date 2020-01-13 09:52:44
*/
public interface ICwmRuleGatewayService {

    /**
    *查询数据
     * @param moduleId
    * @return
     */
    List<CwmRuleGateway> getGateway(String moduleId) throws Exception;

    /**
     * 单条数据修改
     * @param cwmRuleGateway
     * @throws Exception
     */
    void updateByPrimaryKey(CwmRuleGateway cwmRuleGateway) throws  Exception;
}