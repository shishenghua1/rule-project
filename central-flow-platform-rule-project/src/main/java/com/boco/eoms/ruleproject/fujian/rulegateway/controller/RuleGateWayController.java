package com.boco.eoms.ruleproject.fujian.rulegateway.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author ssh
 * @description:规则网关页面跳转controller
 * @date 2020/1/1016:37
 */
@ApiIgnore
@Controller
public class RuleGateWayController {
    /**
     * 统计页面
     * @return
     */
    @RequestMapping("/statistics")
    public String statisticsPage(){
        return "statistics";
    }
    /**
     * 详情页面
     * @return
     */
    @RequestMapping("/details")
    public String detailsPage(){
        return "details";
    }

    /**
     * 仿真记录页面
     * @return
     */
    @RequestMapping("/records")
    public String recordsPage(){
        return "records";
    }



    /**
     * 规则网关页面
     * @return
     */
    @RequestMapping("/commonPage")
    public String commonRuleGatewayPage(){
        return "common-rule-gateway";
    }

    /**
     * 规则网关页面
     * @return
     */
    @RequestMapping("/dragPage")
    public String ruleGatewayPage(){
        return "drag-rule-gateway";
    }
    
    /**
     * 规则网关首页
     * @return
     */
    @RequestMapping("/index")
    public String indexPage(){
        return "index";
    }
}
