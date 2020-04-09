package com.boco.eoms.ruleproject.fujian.simulation.mapper;

import com.boco.eoms.ruleproject.fujian.simulation.model.Simulation;
import com.boco.eoms.ruleproject.fujian.simulation.model.SimulationData;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ssh
 * 类说明：仿真数据
 */
@Repository
public interface SimulationDataMapper {
    /**
     * 仿真数据对象集合保存
     * @param simulationDataList
     */
    void batchInsert(List<SimulationData> simulationDataList);

    /**
     * 根据仿真id查询工单量
     * @param simulationId
     * @return
     */
    int sheetNum(String simulationId);

    /**
     * 根据仿真id查询数据
     * @param simulationId
     * @return
     */
    List<SimulationData> getListBySimulationId(@Param("simulationId") String simulationId);

    /**
     * 批量更新仿真结构
     * @param simulationDataList
     */
    void batchUpdate(List<SimulationData> simulationDataList);

    /**
     * 仿真数据工单数据
     * @param simulationId
     * @param executeResult
     * @return
     */
    List<SimulationData> getSimulationDatas(@Param("simulationId") String simulationId,@Param("executeResult")String executeResult);

    /**
     * 根据仿真数据id查询对象
     * @return
     */
    SimulationData getSimulationDataById(@Param("simulationDataId")String simulationDataId);
}
