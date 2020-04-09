package com.boco.eoms.ruleproject.fujian.gateway.controller;

import com.alibaba.fastjson.JSONObject;
import com.boco.eoms.ruleproject.fujian.rulegateway.model.CwmRuleGroupGatewayRel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.boco.eoms.ruleproject.fujian.gateway.model.CwmRuleGateway;
import com.boco.eoms.ruleproject.fujian.gateway.service.ICwmRuleGatewayService;
import com.boco.eoms.ruleproject.base.util.ReturnJsonUtil;
/**
* @description:规则网关controller接口
* @author ssh
* @date 2020-01-13 09:52:44
*/
@RestController
@RequestMapping("/api/v1/gateway")
@Api(value="规则网关controller",tags={"规则网关restful接口"})
public class CwmRuleGatewayController {

    @Autowired
    private ICwmRuleGatewayService cwmRuleGatewayService; 

    private Logger logger = LoggerFactory.getLogger(CwmRuleGatewayController.class);

    @Autowired
    private Environment env;
    /**
    *查询规则网关
    * @return
     */
    @ResponseBody
    @GetMapping("getGateway")
    @ApiOperation(value="查询规则网关",notes="查询规则网关",response = CwmRuleGateway.class)
    public Object query(){
        try {
            //故障工单模块id
            String moduleId = env.getProperty("moduleId.fault");
            return cwmRuleGatewayService.getGateway(moduleId);
        } catch (Exception e) {
            logger.error("查询异常",e);
        }
        return null;
    }

    /**
     *单个修改规则网关
     * @param cwmRuleGateway
     * @return
     */
    @ApiOperation(value="规则网关修改",notes="规则网关单条数据修改,只改变状态，传id和状态即可")
    @RequestMapping(method = RequestMethod.PUT)
    public JSONObject update(@RequestBody CwmRuleGateway cwmRuleGateway){
        try {
            cwmRuleGatewayService.updateByPrimaryKey(cwmRuleGateway);
            return ReturnJsonUtil.returnSuccessInfo("修改成功");
        } catch (Exception e) {
            logger.error("修改异常",e);
            return ReturnJsonUtil.returnFailInfo("修改失败");
        }
    }

}