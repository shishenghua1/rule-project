package com.boco.eoms.ruleproject.fujian.simulation.model;

import com.boco.eoms.ruleproject.base.util.PageUtil;
import com.boco.eoms.ruleproject.base.util.StaticMethod;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author ssh
 * @description:
 * @date 2020/1/1314:36
 */
@ApiModel(value="仿真查询条件对象")
public class SimulationCondition extends PageUtil {

    @JsonFormat(pattern = StaticMethod.DATE_FORMAT)
    @ApiModelProperty(value="仿真查询开始时间",dataType="Date",example="2019-06-05 10:25:30")
    private Date simulationStartTime;

    @JsonFormat(pattern = StaticMethod.DATE_FORMAT)
    @ApiModelProperty(value="仿真查询结束时间",dataType="Date",example="2019-06-05 10:25:30")
    private Date simulationEndTime;

    public Date getSimulationStartTime() {
        return simulationStartTime;
    }

    public void setSimulationStartTime(Date simulationStartTime) {
        this.simulationStartTime = simulationStartTime;
    }

    public Date getSimulationEndTime() {
        return simulationEndTime;
    }

    public void setSimulationEndTime(Date simulationEndTime) {
        this.simulationEndTime = simulationEndTime;
    }
}
