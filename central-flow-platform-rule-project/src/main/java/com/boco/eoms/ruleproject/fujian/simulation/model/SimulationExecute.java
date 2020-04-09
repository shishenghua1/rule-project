package com.boco.eoms.ruleproject.fujian.simulation.model;

import java.util.Date;

/**
 * 仿真执行记录表
 * @author ssh
 */
public class SimulationExecute {
    //主键id
    private String id;
    //仿真数据id
    private String simulationDataId;
    //网关id
    private String gatewayId;
    //网关名称
    private String gatewayName;
    //仿真结果
    private String simulationResult;
    //仿真异常信息
    private String simulationError;
    //仿真返回参数
    private String simulationOutputParam;
    //仿真执行时长
    private int simulationExecuteDuration;
    //仿真执行时间
    private Date simulationExecuteTime;
    //AI相似执行结果
    private String aiSimilarScore;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSimulationDataId() {
        return simulationDataId;
    }

    public void setSimulationDataId(String simulationDataId) {
        this.simulationDataId = simulationDataId;
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

    public String getSimulationResult() {
        return simulationResult;
    }

    public void setSimulationResult(String simulationResult) {
        this.simulationResult = simulationResult;
    }

    public String getSimulationOutputParam() {
        return simulationOutputParam;
    }

    public void setSimulationOutputParam(String simulationOutputParam) {
        this.simulationOutputParam = simulationOutputParam;
    }

    public Date getSimulationExecuteTime() {
        return simulationExecuteTime;
    }

    public void setSimulationExecuteTime(Date simulationExecuteTime) {
        this.simulationExecuteTime = simulationExecuteTime;
    }

    public int getSimulationExecuteDuration() {
        return simulationExecuteDuration;
    }

    public void setSimulationExecuteDuration(int simulationExecuteDuration) {
        this.simulationExecuteDuration = simulationExecuteDuration;
    }

    public String getSimulationError() {
        return simulationError;
    }

    public void setSimulationError(String simulationError) {
        this.simulationError = simulationError;
    }

	public String getAiSimilarScore() {
		return aiSimilarScore;
	}

	public void setAiSimilarScore(String aiSimilarScore) {
		this.aiSimilarScore = aiSimilarScore;
	}
}
