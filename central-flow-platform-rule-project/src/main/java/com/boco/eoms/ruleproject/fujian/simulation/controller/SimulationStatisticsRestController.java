package com.boco.eoms.ruleproject.fujian.simulation.controller;

import com.boco.eoms.ruleproject.fujian.simulation.model.SheetCondition;
import com.boco.eoms.ruleproject.fujian.simulation.model.Simulation;
import com.boco.eoms.ruleproject.fujian.simulation.model.SimulationCondition;
import com.boco.eoms.ruleproject.fujian.simulation.service.ISimulationService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ssh
 * 类说明：网关仿真统计controller
 */
@RestController
@RequestMapping("/api/v1/simulation/statistics")
@Api(value="网关仿真统计controller",tags={"网关仿真统计restful接口"})
public class SimulationStatisticsRestController {

    private Logger logger = LoggerFactory.getLogger(SimulationStatisticsRestController.class);
    @Autowired
    private ISimulationService simulationService;

    /**
     *条件查询规则集合数据按照时间（降序）查询
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/getSimulations",method=RequestMethod.POST)
    @ApiOperation(value="查询仿真记录",notes="查询仿真记录",response = Simulation.class)
    public Object getSimulations(@RequestBody SimulationCondition simulationCondition){
        try {
            return simulationService.getSimulations(simulationCondition);
        } catch (Exception e) {
            logger.error("查询异常",e);
        }
        return null;
    }

    /**
     *条件查询仿真的工单
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/getSheets",method=RequestMethod.POST)
    @ApiOperation(value="查询仿真的工单",notes="查询仿真的工单",response = Simulation.class)
    public Object getSheets(@RequestBody SheetCondition sheetCondition){
        try {
            return simulationService.getSimulationDatas(sheetCondition);
        } catch (Exception e) {
            logger.error("查询异常",e);
        }
        return null;
    }

    /**
     *条件查询工单详情
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/getSheetDetail",method=RequestMethod.GET)
    @ApiOperation(value="查询工单详情",notes="查询工单详情")
    @ApiImplicitParam(name="simulationDataId",value="仿真数据id",required=true,paramType="query",dataType = "String")
    public Object getSheetDetail(String simulationDataId){
        try {
            return simulationService.getSheetDetail(simulationDataId);
        } catch (Exception e) {
            logger.error("查询异常",e);
        }
        return null;
    }
    
    /**
     *AI折线图数据查询
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/getAISimilarData",method=RequestMethod.GET)
    @ApiOperation(value="AI折线图数据查询",notes="AI折线图数据查询")
    @ApiImplicitParam(name="simulationId",value="仿真id",required=true,paramType="query",dataType = "String")
    public Object getAISimilarData(String simulationId){
        try {
            return simulationService.simulationSimilarData(simulationId);
        } catch (Exception e) {
            logger.error("查询异常",e);
        }
        return null;
    }
    
    /**
     * 折线图页面仿真数据统计
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/getSimulationStatictisData",method=RequestMethod.GET)
    @ApiOperation(value="折线图页面仿真数据统计",notes="折线图页面仿真数据统计")
    @ApiImplicitParam(name="simulationId",value="仿真id",required=true,paramType="query",dataType = "String")
    public Object getSimulationStatictisData(String simulationId){
        try {
            return simulationService.simulationStatictisData(simulationId);
        } catch (Exception e) {
            logger.error("查询异常",e);
        }
        return null;
    }
}
