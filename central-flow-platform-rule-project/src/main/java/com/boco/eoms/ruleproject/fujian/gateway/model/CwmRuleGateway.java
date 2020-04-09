package com.boco.eoms.ruleproject.fujian.gateway.model;

import com.boco.eoms.ruleproject.base.util.StaticMethod;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
* @description:规则网关实体类
* @author ssh
* @date 2020-01-13 09:52:44
*/
@ApiModel(value="规则网关实体,标注系统生成的无需填写")
public class CwmRuleGateway {

    @ApiModelProperty(value="主键id,系统生成",example="")
    private String id;

    @ApiModelProperty(value="网关名称",example="")
    private String gatewayName;

    @JsonFormat(pattern = StaticMethod.DATE_FORMAT)
    @ApiModelProperty(value="创建时间，系统生成",dataType="Date",example="2019-06-05 10:25:30")
    private Date createTime;

    @ApiModelProperty(value="字典值包括enable(表示启用)和 forbidden(表示禁用)",example="")
    private String status;

    @ApiModelProperty(value="网关下的数量",example="")
    private Integer groupNum;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getGatewayName(){ return gatewayName; }

    public void setGatewayName(String gatewayName){ this.gatewayName = gatewayName;}

    public Date getCreateTime(){ return createTime; }

    public void setCreateTime(Date createTime){ this.createTime = createTime;}

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(Integer groupNum) {
        this.groupNum = groupNum;
    }
}