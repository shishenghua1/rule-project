package com.boco.eoms.ruleproject.fujian.simulation.mapper;

import com.boco.eoms.ruleproject.fujian.simulation.model.SimulationData;
import com.boco.eoms.ruleproject.fujian.simulation.model.SimulationDataParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ssh
 * 类说明：仿真数据参数对象
 */
@Repository
public interface SimulationDataParamMapper {
    /**
     * 仿真数据参数对象集合保存
     * @param simulationDataParamList
     */
    void batchInsert(List<SimulationDataParam> simulationDataParamList);

    /**
     * 仿真数据id
     * @param simulationDataId
     * @return
     */
    List<SimulationDataParam> getListBySimulationDataId(@Param("simulationDataId") String simulationDataId);
}
