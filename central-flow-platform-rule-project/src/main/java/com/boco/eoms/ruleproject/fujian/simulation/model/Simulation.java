package com.boco.eoms.ruleproject.fujian.simulation.model;

import com.boco.eoms.ruleproject.base.util.StaticMethod;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 仿真表
 * @author ssh
 */
public class Simulation {
    //主键id
    private String id;
    //操作人
    private String operator;
    //操作时间
    @JsonFormat(pattern = StaticMethod.DATE_FORMAT)
    private Date operationTime;
    //操作按钮,字典值包括upload（excel上传方式）和autoObtain（自动获取）
    private String operationButton;
    //地市id
    private String city;
    //网络工单一级分类
    private String networkSheetOne;
    //网络工单二级分类
    private String networkSheetTwo;
    //派单开始时间
    private Date sheetStartTime;
    //派单结束时间
    private Date sheetEndTime;
    //网关id集
    private String gatewayIds;
    //工单总量
    private String sheetTotal;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Date getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
    }

    public String getOperationButton() {
        return operationButton;
    }

    public void setOperationButton(String operationButton) {
        this.operationButton = operationButton;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNetworkSheetOne() {
        return networkSheetOne;
    }

    public void setNetworkSheetOne(String networkSheetOne) {
        this.networkSheetOne = networkSheetOne;
    }

    public String getNetworkSheetTwo() {
        return networkSheetTwo;
    }

    public void setNetworkSheetTwo(String networkSheetTwo) {
        this.networkSheetTwo = networkSheetTwo;
    }

    public Date getSheetStartTime() {
        return sheetStartTime;
    }

    public void setSheetStartTime(Date sheetStartTime) {
        this.sheetStartTime = sheetStartTime;
    }

    public Date getSheetEndTime() {
        return sheetEndTime;
    }

    public void setSheetEndTime(Date sheetEndTime) {
        this.sheetEndTime = sheetEndTime;
    }

    public String getGatewayIds() {
        return gatewayIds;
    }

    public void setGatewayIds(String gatewayIds) {
        this.gatewayIds = gatewayIds;
    }

    public String getSheetTotal() {
        return sheetTotal;
    }

    public void setSheetTotal(String sheetTotal) {
        this.sheetTotal = sheetTotal;
    }
}
