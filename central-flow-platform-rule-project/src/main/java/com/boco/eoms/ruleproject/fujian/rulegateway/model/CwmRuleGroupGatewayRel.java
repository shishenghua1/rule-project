package com.boco.eoms.ruleproject.fujian.rulegateway.model;

import com.boco.eoms.ruleproject.base.util.StaticMethod;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
* @description:规则网关关联实体类
* @author ssh
* @date 2020-01-13 10:05:11
*/
@ApiModel(value="规则网关关联实体,标注系统生成的无需填写")
public class CwmRuleGroupGatewayRel {

    @ApiModelProperty(value="主键id，系统生成",example="")
    private String id;

    @ApiModelProperty(value="规则集合id",example="bd36cee0e3b642c6971dfe0dfc0d7f71")
    private String groupId;

    @ApiModelProperty(value="规则集合名称",example="故障超时超限检测")
    private String groupName;

    @ApiModelProperty(value="网关id,是否为空用来区分是否关联网关",example="1")
    private String gatewayId;

    @ApiModelProperty(value="网关名称",example="限制回单规则网关")
    private String gatewayName;

    @JsonFormat(pattern = StaticMethod.DATE_FORMAT)
    @ApiModelProperty(value="创建时间,系统生成",dataType="Date",example="2019-06-05 10:25:30")
    private Date createTime;

    @ApiModelProperty(value="状态，字典值包括enable(表示启用)和 forbidden(表示禁用)",example="enable")
    private String status;

    @ApiModelProperty(value="规则顺序,系统生成",example="")
    private String ruleOrder;

    @ApiModelProperty(value="规则集合描述",example="规则1")
    private String groupDescription;

    @ApiModelProperty(value="规则集合备注",example="质检合格 ")
    private String groupRemark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGatewayId() {
        return gatewayId;
    }

    public void setGatewayId(String gatewayId) {
        this.gatewayId = gatewayId;
    }

    public String getGatewayName() {
        return gatewayName;
    }

    public void setGatewayName(String gatewayName) {
        this.gatewayName = gatewayName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRuleOrder() {
        return ruleOrder;
    }

    public void setRuleOrder(String ruleOrder) {
        this.ruleOrder = ruleOrder;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }

    public String getGroupRemark() {
        return groupRemark;
    }

    public void setGroupRemark(String groupRemark) {
        this.groupRemark = groupRemark;
    }

}