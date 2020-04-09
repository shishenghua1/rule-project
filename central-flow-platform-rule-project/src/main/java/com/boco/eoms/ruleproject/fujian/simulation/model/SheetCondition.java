package com.boco.eoms.ruleproject.fujian.simulation.model;

import com.boco.eoms.ruleproject.base.util.PageUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author ssh
 * @description:
 * @date 2020/3/23 14:36
 */
@ApiModel(value="仿真工单条件对象")
public class SheetCondition extends PageUtil {
    @ApiModelProperty(value="仿真id",example="")
    private String simulationId;
    @ApiModelProperty(value="AI质检是否通过,空则为全量,包括true和false",example="true")
    private String executeResult;

    public String getSimulationId() {
        return simulationId;
    }

    public void setSimulationId(String simulationId) {
        this.simulationId = simulationId;
    }

    public String getExecuteResult() {
        return executeResult;
    }

    public void setExecuteResult(String executeResult) {
        this.executeResult = executeResult;
    }
}
