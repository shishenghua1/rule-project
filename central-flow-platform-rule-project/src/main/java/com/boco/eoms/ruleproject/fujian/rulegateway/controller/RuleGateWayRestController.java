package com.boco.eoms.ruleproject.fujian.rulegateway.controller;

import com.alibaba.fastjson.JSONObject;
import com.boco.eoms.ruleproject.fujian.rulegateway.model.QueryCondition;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.boco.eoms.ruleproject.fujian.rulegateway.model.CwmRuleGroupGatewayRel;
import com.boco.eoms.ruleproject.fujian.rulegateway.service.ICwmRuleGroupGatewayRelService;
import com.boco.eoms.ruleproject.base.util.ReturnJsonUtil;

import java.util.List;
import java.util.Map;

/**
* @description:规则网关关联controller接口
* @author ssh
* @date 2020-01-13 10:05:11
*/
@RestController
@RequestMapping("/api/v1/rulegateway")
@Api(value="规则网关关联controller",tags={"规则网关关联restful接口"})
public class RuleGateWayRestController {

    @Autowired
    private ICwmRuleGroupGatewayRelService cwmRuleGroupGatewayRelService;

    @Autowired
    private Environment env;

    private Logger logger = LoggerFactory.getLogger(RuleGateWayRestController.class);

    /**
     *获取页面展示类型，dragPage拖动类型页面/commonPage普通页面
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/getPageType",method=RequestMethod.GET)
    @ApiOperation(value="获取页面展示类型",notes="dragPage拖动类型页面/commonPage普通页面",response = String.class)
    public String getPageType(){
        try {
            //获取页面展示类型
            return env.getProperty("pageShow.type");
        } catch (Exception e) {
            logger.error("获取异常",e);
        }
        return "commonPage";
    }

    /**
     *条件查询规则集合数据按照时间（降序）查询
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/getRules",method=RequestMethod.POST)
    @ApiOperation(value="查询规则",notes="查询全量规则",response = CwmRuleGroupGatewayRel.class)
    public Object queryRule(@RequestBody QueryCondition queryCondition){
        try {
            String moduleId = env.getProperty("moduleId.fault");
            queryCondition.setModuleId(moduleId);
            return cwmRuleGroupGatewayRelService.queryRule(queryCondition);
        } catch (Exception e) {
            logger.error("查询异常",e);
        }
        return null;
    }

    /**
    *根据id删除数据接口
    * @param id
    * @return
     */
    @RequestMapping(value="/{gatewayId}/{id}",method=RequestMethod.DELETE)
    @ApiOperation(value="规则网关关联删除",notes="根据id删除对象")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="path", name = "gatewayId", value = "规则网关id", required = true, dataType = "String"),
        @ApiImplicitParam(paramType="path", name = "id", value = "规则网关关联id", required = true, dataType = "String")
    })
    public JSONObject delete(@PathVariable("gatewayId") String gatewayId,@PathVariable("id") String id){
        try {
            cwmRuleGroupGatewayRelService.deleteByCondition(gatewayId,id);
            return ReturnJsonUtil.returnSuccessInfo("删除成功");
        } catch (Exception e) {
            logger.error("删除异常",e);
            return ReturnJsonUtil.returnFailInfo("删除失败");
        }
    }

    /**
     *批量进行规则网关关联
     * @return
     * @throws Exception
     */
	@ApiOperation(value="批量进行规则网关关联",notes="批量进行规则网关关联")
	@ApiResponses({ @ApiResponse(code = 500, message = "插入异常") })
	@RequestMapping(method = RequestMethod.POST)
	public JSONObject save(@RequestBody List<CwmRuleGroupGatewayRel> cwmRuleGroupGatewayRelList){
		try {
            return cwmRuleGroupGatewayRelService.batchInsert(cwmRuleGroupGatewayRelList);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
            return ReturnJsonUtil.returnFailInfo("关联失败");
		}
	}


    /**
    *单个修改规则网关关联
    * @param cwmRuleGroupGatewayRel
    * @return
     */
    @ApiOperation(value="规则网关关联修改",notes="规则网关关联单条数据修改,只改变状态，传id和状态即可")
    @RequestMapping(method = RequestMethod.PUT)
    public JSONObject update(@RequestBody CwmRuleGroupGatewayRel cwmRuleGroupGatewayRel){
        try {
            cwmRuleGroupGatewayRelService.updateByPrimaryKey(cwmRuleGroupGatewayRel);
            return ReturnJsonUtil.returnSuccessInfo("修改成功");
        } catch (Exception e) {
            logger.error("修改异常",e);
            return ReturnJsonUtil.returnFailInfo("修改失败");
        }
    }

    /**
     *规则网关顺序修改，不包括全部列表
     * @param groupGatewayRelList
     * @return
     */
    @ApiOperation(value="规则网关修改顺序，不包括全部列表",notes="把id,ruleOrder传入即可")
    @RequestMapping(value="/changeRuleOrder",method = RequestMethod.PUT)
    public JSONObject changeRuleOrder(@RequestBody List<CwmRuleGroupGatewayRel> groupGatewayRelList){
        try {
            return cwmRuleGroupGatewayRelService.changeOrder(groupGatewayRelList);
        } catch (Exception e) {
            logger.error("修改异常",e);
            return ReturnJsonUtil.returnFailInfo("修改失败");
        }
    }
}