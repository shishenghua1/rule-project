package com.boco.eoms.ruleproject.fujian.simulation.model;

import java.util.Date;

/**
 * 仿真数据参数表
 * @author ssh
 */
public class SimulationDataParam {
    //主键id
    private String id;
    //仿真数据id
    private String simulationDataId;
    //输入参数英文名
    private String inputParamEnname;
    //输入参数中文名
    private String inputParamCnname;
    //输入参数类型
    private String inputParamType;
    //输入参数值
    private String inputParamValue;
    //创建时间
    private Date createTime;

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

    public String getInputParamEnname() {
        return inputParamEnname;
    }

    public void setInputParamEnname(String inputParamEnname) {
        this.inputParamEnname = inputParamEnname;
    }

    public String getInputParamCnname() {
        return inputParamCnname;
    }

    public void setInputParamCnname(String inputParamCnname) {
        this.inputParamCnname = inputParamCnname;
    }

    public String getInputParamType() {
        return inputParamType;
    }

    public void setInputParamType(String inputParamType) {
        this.inputParamType = inputParamType;
    }

    public String getInputParamValue() {
        return inputParamValue;
    }

    public void setInputParamValue(String inputParamValue) {
        this.inputParamValue = inputParamValue;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
