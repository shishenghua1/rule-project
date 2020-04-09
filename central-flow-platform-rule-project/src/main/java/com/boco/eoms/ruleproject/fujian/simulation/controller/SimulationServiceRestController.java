package com.boco.eoms.ruleproject.fujian.simulation.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.boco.eoms.ruleproject.base.util.HttpClientServlet;
import com.boco.eoms.ruleproject.base.util.ReturnJsonUtil;
import com.boco.eoms.ruleproject.base.util.StaticMethod;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

/**
 * @author ssh
 * 类说明：网关仿真服务测试controller
 */
@RestController
@RequestMapping("/api/v1/simulation/service")
@Api(value="网关仿真测试controller",tags={"网关仿真测试restful接口"})
public class SimulationServiceRestController {

    /**
     * 地市数据
     * @param param
     * @return
     */
    @RequestMapping(value="/getCitys",method= RequestMethod.POST)
    @ResponseBody
    public String getCitys(@RequestBody JSONObject param){
        JSONObject result = new JSONObject();
        try {
            String dictName = StaticMethod.nullObject2String(param.get("parentAreaId"));//父级地域id
            //根据父级地区名称查询地市
            JSONArray cityArr = new JSONArray();
            JSONObject city1 = new JSONObject();
            city1.put("areaId","1");
            city1.put("areaName","福州市");
            JSONObject city2 = new JSONObject();
            city2.put("areaId","2");
            city2.put("areaName","宁德市");
            JSONObject city3 = new JSONObject();
            city3.put("areaId","3");
            city3.put("areaName","泉州市");
            cityArr.add(city1);
            cityArr.add(city2);
            cityArr.add(city3);
            result =  ReturnJsonUtil.returnSuccessInfo(cityArr,"地市获取成功");
        } catch (Exception e) {
            e.printStackTrace();
            result =  ReturnJsonUtil.returnFailInfo("地市获取异常");
        }finally {
            return result.toJSONString();
        }
    }

    /**
     * 字典数据
     * @param param
     * @return
     */
    @RequestMapping(value="/getDicts",method= RequestMethod.POST)
    @ResponseBody
    public String getDicts(@RequestBody JSONObject param){
        JSONObject result = new JSONObject();
        try {
            String parentDictId = StaticMethod.nullObject2String(param.get("parentDictId"));//父级字典id
            //根据父级地区名称查询地市
            JSONArray dictArr = new JSONArray();
            JSONObject dict1 = new JSONObject();
            dict1.put("dictId","1");
            dict1.put("dictName","无线网");
            JSONObject dict2 = new JSONObject();
            dict2.put("dictId","2");
            dict2.put("dictName","无线网2");
            dictArr.add(dict1);
            dictArr.add(dict2);
            result =  ReturnJsonUtil.returnSuccessInfo(dictArr,"字典获取成功");
        } catch (Exception e) {
            e.printStackTrace();
            result =  ReturnJsonUtil.returnFailInfo("字典获取异常");
        }finally {
            return result.toJSONString();
        }
    }

    /**
     * 仿真数据
     * @param param
     * @return
     */
    @RequestMapping(value="/getSimulationData",method= RequestMethod.POST)
    @ResponseBody
    public String getSimulationData(@RequestBody JSONObject param){
        JSONObject result = new JSONObject();
        try {
            JSONObject fieldObj = new JSONObject();
            fieldObj.put("sheetTotal","200");
            result =  ReturnJsonUtil.returnSuccessData(fieldObj,"仿真数据获取成功");
        } catch (Exception e) {
            e.printStackTrace();
            result =  ReturnJsonUtil.returnFailInfo("仿真数据获取异常");
        }finally {
            return result.toJSONString();
        }
    }

    /**
     * 仿真数据的推送
     * @return
     */
    @ResponseBody
    @GetMapping("saveSimulationData")
    @ApiResponses({ @ApiResponse(code = 500, message = "保存异常") })
    @ApiOperation(value="仿真数据的推送",notes="仿真数据的推送")
    @ApiImplicitParam(name="simulationId",value="仿真id,仿真数据获取接口返回的simulationId",required=true,paramType="query",dataType = "String")
    public Object saveSimulationData(String simulationId){
        try {
            JSONObject dataObj = new JSONObject();
            JSONArray arr =new JSONArray();
            for (int i = 0; i < 200; i++) {
                JSONObject obj = new JSONObject();
                obj.put("sheetId","sheetId"+i);
                obj.put("title","工单"+i);
                obj.put("mainId","mainId"+i);
                obj.put("checking_pass_rate","0.1");
                arr.add(obj);
            }
            dataObj.put("simulationId",simulationId);
            dataObj.put("fieldInfo",arr);
            String entityStr = HttpClientServlet.httpPostRaw("http://localhost:8006/api/v1/simulation/saveSimulationData",dataObj.toString(),null,null);
            return ReturnJsonUtil.returnSuccessInfo("仿真数据保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnJsonUtil.returnFailInfo("仿真数据保存失败");
        }
    }

    /**
     * 条件仿真规则
     * @return
     */
    @ResponseBody
    @PostMapping("excuteRule")
    @ApiResponses({ @ApiResponse(code = 500, message = "条件仿真规则") })
    @ApiOperation(value="条件仿真规则",notes="条件仿真规则")
    public Object excuteRule(){
        try {
            JSONObject simulationObj = new JSONObject(true);
            simulationObj.put("checking_pass_rate","1");
            String entityStr  = HttpClientServlet.httpPostRaw("http://10.91.0.122:8095/v1/rule/2c1168b9799142ffba2453be69b03b54/excuteRule",simulationObj.toString(),null,null);
            return ReturnJsonUtil.returnSuccessInfo("测试成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnJsonUtil.returnFailInfo("地市获取异常");
        }
    }
}
