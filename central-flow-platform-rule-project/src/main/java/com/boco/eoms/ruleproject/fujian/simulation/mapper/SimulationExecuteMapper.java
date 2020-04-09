package com.boco.eoms.ruleproject.fujian.simulation.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.boco.eoms.ruleproject.fujian.simulation.model.SimulationExecute;

/**
 * 仿真执行mapper
 */
@Repository
public interface SimulationExecuteMapper {
    /**
     * 仿真执行记录批量保存
     * @param simulationExecuteList
     */
    void batchInsert(List<SimulationExecute> simulationExecuteList);

    /**
     * 仿真执行记录查询
     * @param simulationDataId
     */
    List<SimulationExecute> getListBySimDataId(@Param("simulationDataId") String simulationDataId);

    int simulationSheetNum(@Param("gatewayId")String gatewayId,@Param("simulationId")String simulationId);
    /**
     * 仿真Ai相似度结果
     * @param simulationId
     * @return
     */
    List<Map> getSimulationSimilarData(@Param("simulationId")String simulationId);
    
    /**
     * 仿真总任务数
     * @param simulationId
     * @return
     */
    int totalSimulationTask(@Param("simulationId")String simulationId);
    
    /**
     * 已完成仿真数
     * @param simulationId
     * @return
     */
    int totalComplateSimulationTask(@Param("simulationId")String simulationId);
    
    /**
     * 进入人工复检数
     * @param simulationId
     * @param gatewayId
     * @return
     */
    int totalSimulationReCheckTask(@Param("simulationId")String simulationId,@Param("gatewayId")String gatewayId);
    
    /**
     * 仿真执行总时长
     * @param simulationId
     * @return
     */
    long totalSimulationExcuteTime(@Param("simulationId")String simulationId);
    
    /**
     * 非人工复检总任务数
     * @param simulationId
     * @param gatewayId
     * @return
     */
    int totalSimulationNotReCheckTask(@Param("simulationId")String simulationId,@Param("gatewayId")String gatewayId);
    
    /**
     * 非人工复检通过任务数
     * @param simulationId
     * @param gatewayId
     * @return
     */
    int totalSimulationNotReCheckTaskPass(@Param("simulationId")String simulationId,@Param("gatewayId")String gatewayId);

    /**
     * 数据的批量修改
     * @param simulationExecuteList
     */
    void batchUpdate(List<SimulationExecute> simulationExecuteList);
}
