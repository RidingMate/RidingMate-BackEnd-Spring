package com.ridingmate.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Profile({"local", "dev"})
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private String groupName;

    @Bean
    public Docket v1UserApi() {
        groupName = "v1_user";
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .groupName(groupName)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ridingmate.api.controller"))
                .paths(PathSelectors.ant("/v1/**"))
                .build()
                .apiInfo(apiInfo());
    }

    @Bean
    public Docket v1DataApi() {
        groupName = "v1_data";
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .groupName(groupName)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ridingmate.api.controller"))
                .paths(PathSelectors.ant("/v1/data/**"))
                .build()
                .apiInfo(apiInfo());
    }

    @Bean
    public Docket v1BikeApi() {
        groupName = "v1_bike";
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .groupName(groupName)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ridingmate.api.controller"))
                .paths(PathSelectors.ant("/v1/bike/**"))
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("RidingMate Web REST API TEST")
                .description("")
                .version("1.0")
                .build();
    }

}