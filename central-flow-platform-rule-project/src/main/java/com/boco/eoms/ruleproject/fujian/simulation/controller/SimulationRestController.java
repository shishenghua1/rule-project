package com.boco.eoms.ruleproject.fujian.simulation.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.boco.eoms.ruleproject.base.util.ReturnJsonUtil;
import com.boco.eoms.ruleproject.base.util.StaticMethod;
import com.boco.eoms.ruleproject.fujian.simulation.model.SimulationParam;
import com.boco.eoms.ruleproject.fujian.simulation.service.ISimulationService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author ssh
 * 类说明：网关仿真controller
 */
@RestController
@RequestMapping("/api/v1/simulation")
@Api(value="网关仿真controller",tags={"网关仿真restful接口"})
public class SimulationRestController {

    @Autowired
    private ISimulationService simulationService;

    /**
     *
     * @return
     * @throws Exception
     */
    @ApiOperation(value="仿真数据导入",notes="仿真数据导入")
    @ApiResponses({ @ApiResponse(code = 500, message = "导入失败") })
    @ApiImplicitParam(name="gatewayIds",value="网关id集",dataType="String", paramType = "query")
    @RequestMapping(value = "upload",method=RequestMethod.POST,produces = {"text/html;charset=UTF-8"})
    public String upload(MultipartFile file, @RequestParam(value="gatewayIds",required=true)String gatewayIds,HttpServletRequest req){
        try {
            return simulationService.upload(file,gatewayIds,req).toJSONString();
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnJsonUtil.returnFailInfo("导入失败").toJSONString();
        }
    }

    /**
     *仿真数据导出
     * @param response
     * @return
     * @throws Exception
     */
    @ApiOperation(value="仿真数据导出",notes="仿真数据导出")
    @ApiResponses({ @ApiResponse(code = 500, message = "导出失败") })
    @ApiImplicitParam(name="gatewayIds",value="网关id之间用逗号隔开1,2",dataType="String", paramType = "path")
    @GetMapping("download")
    public void download(String gatewayIds, HttpServletResponse response){
        try {
            simulationService.download(gatewayIds,response);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    /**
     * 根据父节点名称查询地市
     * @param parentAreaId
     * @return
     */
    @ResponseBody
    @GetMapping("getCitys")
    @ApiResponses({ @ApiResponse(code = 500, message = "查询异常") })
    @ApiOperation(value="地市查询",notes="根据父节点查询地市")
    @ApiImplicitParam(name="parentAreaId",value="父节点地域id",required=false,paramType="query",dataType = "String")
    public Object getCitys(@ApiParam(name="parentAreaId",value="父节点地域id",required=false) String parentAreaId){
        try {
            return simulationService.getCitys(StaticMethod.nullObject2String(parentAreaId));
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnJsonUtil.returnFailInfo("地市获取异常");
        }
    }

    /**
     * 根据父节点查询字典值，包括子集
     * @param parentDictId
     * @return
     */
    @ResponseBody
    @GetMapping("getDicts")
    @ApiResponses({ @ApiResponse(code = 500, message = "查询异常") })
    @ApiOperation(value="字典查询",notes="根据父节点查询字典")
    @ApiImplicitParam(name="parentDictId",value="父节点字典id",required=false,paramType="query",dataType = "String")
    public Object getDicts(@ApiParam(name="parentDictId",value="父节点名称",required=false) String parentDictId){
        try {
            return simulationService.getDicts(StaticMethod.nullObject2String(parentDictId));
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnJsonUtil.returnFailInfo("字典查询异常");
        }
    }

    /**
     * 仿真数据获取接口
     * @param simulationParam 请求参数
     * @return
     */
    @RequestMapping(value="/getSimulationData", method = RequestMethod.POST)
    @ApiResponses({ @ApiResponse(code = 500, message = "查询异常") })
    @ApiOperation(value="仿真数据查询",notes="仿真数据查询")
    public Object getSimulationData(@RequestBody SimulationParam simulationParam,HttpServletRequest req){
        try {
            return simulationService.getSimulationData(simulationParam,req);
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnJsonUtil.returnFailInfo("仿真数据获取异常");
        }
    }

    /**
     * 获取仿真数据进度
     * @param simulationId 请求参数
     * @return
     */
    @RequestMapping(value="/getSimulationDataProgess", method = RequestMethod.GET)
    @ApiResponses({ @ApiResponse(code = 500, message = "查询异常") })
    @ApiOperation(value="获取仿真数据进度接口",notes="根据仿真id获取仿真数据进度接口")
    @ApiImplicitParam(name="simulationId",value="仿真id",required=true,paramType="query",dataType = "String")
    public Object getSimulationDataProgess(String simulationId){
        try {
            return simulationService.getSimulationDataProgess(simulationId);
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnJsonUtil.returnFailInfo("仿真进度获取异常");
        }
    }

    /**
     * 获取网关仿真执行进度
     * @param simulationId 请求参数
     * @return
     */
    @RequestMapping(value="/simulationExecuteProgess", method = RequestMethod.GET)
    @ApiResponses({ @ApiResponse(code = 500, message = "查询异常") })
    @ApiOperation(value="获取网关仿真执行进度接口",notes="获取网关仿真执行进度接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name="gatewayId",value="网关id",required=true,paramType="query",dataType = "String"),
            @ApiImplicitParam(name="simulationId",value="仿真id,仿真数据获取接口返回的simulationId",required=true,paramType="query",dataType = "String"),
    })
    public Object simulationExecuteProgess(String gatewayId,String simulationId){
        try {
            return simulationService.simulationExecuteProgess(gatewayId,simulationId);
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnJsonUtil.returnFailInfo("仿真进度获取异常");
        }
    }

    /**
     * 条件仿真规则
     * @param gatewayId
     * @return
     */
    @ResponseBody
    @PostMapping("excuteRule")
    @ApiResponses({ @ApiResponse(code = 500, message = "条件仿真规则") })
    @ApiOperation(value="条件仿真规则",notes="条件仿真规则")
    @ApiImplicitParams({
            @ApiImplicitParam(name="gatewayId",value="网关id",required=true,paramType="query",dataType = "String"),
            @ApiImplicitParam(name="gatewayName",value="网关名称",required=true,paramType="query",dataType = "String"),
            @ApiImplicitParam(name="simulationId",value="仿真id,仿真数据获取接口返回的simulationId",required=true,paramType="query",dataType = "String"),
            @ApiImplicitParam(name="isLastGateWay",value="是否为该次仿真得最后一个网关,字典值包括ture和false",required=true,paramType="query",dataType = "String")
    })
    public Object excuteRule(String gatewayId,String gatewayName,String simulationId,String isLastGateWay){
        try {
            return simulationService.excuteRule(gatewayId,gatewayName,simulationId,isLastGateWay);
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnJsonUtil.returnFailInfo("仿真执行异常");
        }
    }

    /**
     * 仿真数据保存接口
     * @param
     * @return
     */
    @RequestMapping(value="/saveSimulationData", method = RequestMethod.POST)
    @ApiResponses({ @ApiResponse(code = 500, message = "查询异常") })
    @ApiOperation(value="仿真数据保存接口",notes="仿真数据保存接口")
    public Object saveSimulationData(@RequestBody JSONObject param){
        try {
            return simulationService.saveSimulationData(param);
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnJsonUtil.returnFailInfo("仿真数据保存失败");
        }
    }

    /**
     * 仿真执行数据保存接口
     * @param
     * @return
     */
    @RequestMapping(value="/updateSimulationRunData", method = RequestMethod.POST)
    @ApiResponses({ @ApiResponse(code = 500, message = "查询异常") })
    @ApiOperation(value="仿真运行数据修改接口",notes="仿真运行数据修改接口")
    public Object updateSimulationRunData(@RequestBody JSONObject param){
        try {
            return simulationService.updateSimulationRunData(param);
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnJsonUtil.returnFailInfo("仿真运行数据修改失败");
        }
    }

}
