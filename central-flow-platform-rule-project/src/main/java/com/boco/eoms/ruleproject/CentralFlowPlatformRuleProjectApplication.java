package com.boco.eoms.ruleproject;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement  // 启注解事务管理，等同于xml配置方式的 <tx:annotation-driven />
@MapperScan({"com.boco.eoms.ruleproject.rule.mapper","com.boco.eoms.ruleproject.*.*.mapper"})
public class CentralFlowPlatformRuleProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(CentralFlowPlatformRuleProjectApplication.class, args);
    }

}
