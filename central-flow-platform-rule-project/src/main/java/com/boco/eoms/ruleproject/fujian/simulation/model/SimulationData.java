package com.boco.eoms.ruleproject.fujian.simulation.model;

import java.util.Date;

/**
 * 仿真数据表
 * @author ssh
 */
public class SimulationData {
    //主键id
    private String id;
    //仿真id
    private String simulationId;
    //工单流水号
    private String sheetId;
    //工单主题
    private String sheetTitle;
    //工单主键
    private String mainId;
    //整体执行结果
    private String executeResult;
    //整体执行时长
    private Integer executeDuration;
    //创建时间
    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSimulationId() {
        return simulationId;
    }

    public void setSimulationId(String simulationId) {
        this.simulationId = simulationId;
    }

    public String getSheetId() {
        return sheetId;
    }

    public void setSheetId(String sheetId) {
        this.sheetId = sheetId;
    }

    public String getSheetTitle() {
        return sheetTitle;
    }

    public void setSheetTitle(String sheetTitle) {
        this.sheetTitle = sheetTitle;
    }

    public String getMainId() {
        return mainId;
    }

    public void setMainId(String mainId) {
        this.mainId = mainId;
    }

    public String getExecuteResult() {
        return executeResult;
    }

    public void setExecuteResult(String executeResult) {
        this.executeResult = executeResult;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getExecuteDuration() {
        return executeDuration;
    }

    public void setExecuteDuration(Integer executeDuration) {
        this.executeDuration = executeDuration;
    }
}
