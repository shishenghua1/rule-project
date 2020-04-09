package com.boco.eoms.ruleproject.fujian.simulation.mapper;

import com.boco.eoms.ruleproject.fujian.simulation.model.Simulation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 仿真mapper
 */
@Repository
public interface SimulationMapper {
    /**
     * 仿真对象保存
     * @param simulation
     */
    void insert(Simulation simulation);

    /**
     * 根据仿真id更新工单量
     * @param simulationId
     * @param sheetTotal
     */
    void updateSheetTotal(@Param("simulationId") String simulationId, @Param("sheetTotal") String sheetTotal);

    /**
     * 仿真id
     * @param simulationId
     * @return
     */
    Simulation selectById(@Param("simulationId") String simulationId);

    /**
     * 获取仿真数据
     * @param startTime
     * @param endTime
     * @return
     */
    List<Simulation> getSimulations(@Param("startTime") Date startTime, @Param("endTime") Date endTime);
}
