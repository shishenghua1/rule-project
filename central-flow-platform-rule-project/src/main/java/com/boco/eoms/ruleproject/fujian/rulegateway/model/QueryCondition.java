package com.boco.eoms.ruleproject.fujian.rulegateway.model;

import com.boco.eoms.ruleproject.base.util.PageUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author ssh
 * @description:
 * @date 2020/1/1314:36
 */
@ApiModel(value="查询条件对象")
public class QueryCondition extends PageUtil {

    @ApiModelProperty(value="规则集合是否未关联网关,true表示未关联，空表示全量数据",example="true")
    private String isRelevance;

    @ApiModelProperty(value="规则集合名称",example="")
    private String ruleName;

    @ApiModelProperty(value="网关id",example="")
    private String gatewayId;

    @ApiModelProperty(value="模块id",example="")
    private String moduleId;

    public String getIsRelevance() {
        return isRelevance;
    }

    public void setIsRelevance(String isRelevance) {
        this.isRelevance = isRelevance;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getGatewayId() {
        return gatewayId;
    }

    public void setGatewayId(String gatewayId) {
        this.gatewayId = gatewayId;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }
}
