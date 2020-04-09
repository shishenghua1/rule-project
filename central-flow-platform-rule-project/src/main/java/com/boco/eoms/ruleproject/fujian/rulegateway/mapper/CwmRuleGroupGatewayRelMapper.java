package com.boco.eoms.ruleproject.fujian.rulegateway.mapper;

import com.boco.eoms.ruleproject.fujian.rulegateway.model.CwmRuleGroupGatewayRel;
import com.boco.eoms.ruleproject.fujian.rulegateway.model.CwmRuleGroupRel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @description:规则网关关联mapper接口
* @author ssh
* @date 2020-01-13 10:05:11
*/
@Repository
public interface CwmRuleGroupGatewayRelMapper {

    /**
    *根据id删除数据
    * @param id
    * @return
     */
    int deleteByPrimaryKey(String id);

    /**
    *单条数据的修改
    * @param cwmRuleGroupGatewayRel
    * @return
     */
    int updateByPrimaryKey(CwmRuleGroupGatewayRel cwmRuleGroupGatewayRel);

    /**
     * 规则集合表条件查询规则全量的数据
     * @param ruleName
     * @param isRelevance
     * @return
     */
    List<CwmRuleGroupGatewayRel> queryRule(@Param("ruleName") String ruleName, @Param("isRelevance")  String isRelevance,
                                           @Param("moduleId") String moduleId);

    /**
     * 根据规则集合id查询所属网关信息
     * @param groupId
     * @param moduleId
     * @return
     */
    List<CwmRuleGroupGatewayRel> getGatewayByGroupId(@Param("groupId") String groupId,@Param("moduleId") String moduleId);

    /**
     * 规则网关关联表查询规则数据
     * @param ruleName
     * @param gatewayId
     * @return
     */
    List<CwmRuleGroupGatewayRel> queryRuleGateWay(@Param("ruleName") String ruleName, @Param("gatewayId")  String gatewayId);

    /**
     * 条件查询关联的网关数量
     * @param cwmRuleGroupGatewayRelList
     * @param moduleId
     * @return
     */
    int getRelatedNum(@Param("cwmRuleGroupGatewayRelList")List<CwmRuleGroupGatewayRel> cwmRuleGroupGatewayRelList,@Param("moduleId") String moduleId);

    /**
     * 规则集合关联网关批量插入
     * @param cwmRuleGroupGatewayRelList
     */
    void batchInsert(List<CwmRuleGroupGatewayRel> cwmRuleGroupGatewayRelList);

    /**
     * 根据网关id获取最大的排序值
     * @param gatewayId
     * @return
     */
    String getMaxRuleOrder(@Param("gatewayId") String gatewayId);

    /**
     * 根据网关id查询关联的条件数据量
     * @param gatewayId
     * @return
     */
    String getConditionId(@Param("gatewayId") String gatewayId);

    /**
     * 批量更新顺序
     * @param cwmRuleGroupGatewayRelList
     */
    void batchUpdate(List<CwmRuleGroupGatewayRel> cwmRuleGroupGatewayRelList);

    /**
     * 更新规则顺序
     * @param cwmRuleGroupGatewayRelList
     */
    void batchUpdateRule(List<CwmRuleGroupGatewayRel> cwmRuleGroupGatewayRelList);

    /**
     * 规则集合关联条件的插入
     * @param cwmRuleGroupRel
     */
    void insertRule(CwmRuleGroupRel cwmRuleGroupRel);

    /**
     * 根据id查询规则id
     * @param id
     * @return
     */
    String getRuleIdById(@Param("id") String id);
}