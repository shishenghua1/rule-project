package com.boco.eoms.ruleproject.fujian.gateway.mapper;

import com.boco.eoms.ruleproject.fujian.gateway.model.CwmRuleGateway;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @description:规则网关mapper接口
* @author ssh
* @date 2020-01-13 09:52:44
*/
@Repository
public interface CwmRuleGatewayMapper {

    /**
    *根据moduleId查询数据
    * @return
     */
    List<CwmRuleGateway>  getGateway(@Param("moduleId") String moduleId);

    /**
     * 查询全量规则数量
     * @param moduleId
     * @return
     */
    int getRuleNum(@Param("moduleId") String moduleId);

    /**
     * 根据规则集合id查询网关关联的规则数量
     * @param groupId
     * @return
     */
    int getGateWayRuleNum(@Param("groupId") String groupId);

    /**
     * 单条数据修改
     * @param cwmRuleGateway
     * @throws Exception
     */
    void updateByPrimaryKey(CwmRuleGateway cwmRuleGateway);

    /**
     * 根据规则集合id查询规则集合描述
     * @param ruleId
     * @return
     */
    String getGatewayById(@Param("ruleId") String ruleId);

}