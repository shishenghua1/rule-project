package com.boco.eoms.ruleproject.base.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**

* 创建时间：2019年6月4日 上午9:01:44

* 项目名称：central-flow-platform-rule

* @author ssh
* 类说明： Swagger配置类

*/
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.boco.eoms.rule")) //Controller所在包(必须新建包)
                .paths(PathSelectors.any())
                .build();
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("规则服务系统")  //标题
                .description("Restful API")  //描述
                .termsOfServiceUrl("")  //超链接
                .version("1.0")
                .build();
    }
}

