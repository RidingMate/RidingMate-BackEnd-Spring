package com.ridingmate.api.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;

import com.fasterxml.classmate.TypeResolver;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRule;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Profile({ "local", "dev" })
@Configuration
@EnableSwagger2
@RequiredArgsConstructor
public class SwaggerConfig {

    private String groupName;

    private final TypeResolver typeResolver;

    @Bean
    public Docket v1Api() {
        groupName = "v1";
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .groupName(groupName)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ridingmate.api.controller"))
                .paths(PathSelectors.ant("/v1/**"))
                .build()
                .apiInfo(apiInfo())
                .alternateTypeRules(alternateTypeRuleForPageable());
    }

    @Bean
    public Docket v1GlobalAuthApi() {
        groupName = "v1_global_auth";
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .groupName(groupName)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ridingmate.api.controller"))
                .paths(PathSelectors.ant("/v1/**"))
                .build()
                .apiInfo(apiInfo())
                .securityContexts(securityContexts())
                .securitySchemes(securitySchemes())
                .alternateTypeRules(alternateTypeRuleForPageable());
    }

//    @Bean
//    public Docket v1DataApi() {
//        groupName = "v1_data";
//        return new Docket(DocumentationType.SWAGGER_2)
//                .useDefaultResponseMessages(false)
//                .groupName(groupName)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.ridingmate.api.controller"))
//                .paths(PathSelectors.ant("/v1/data/**"))
//                .build()
//                .apiInfo(apiInfo());
//    }

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
                .description("**응답 객체**\n"
                             + "```\n"
                             + "{\n"
                             + "    \"status\": {\n"
                             + "        \"code\"      : Int - 응답 코드,\n"
                             + "        \"message\"   : String - 응답 메시지,\n"
                             + "    },\n"
                             + "    \"response\"  : Object - 데이터\n"
                             + "}\n"
                             + "```")
                .version("1.0")
                .build();
    }

    public List<SecurityContext> securityContexts() {
        List<SecurityContext> contexts = new ArrayList<>();
        // jwt authorization
        contexts.add(SecurityContext.builder()
                        .securityReferences(Collections.singletonList(
                            new SecurityReference("JWT : Bearer <token>",
                                new AuthorizationScope[] {
                                    new AuthorizationScope(
                                        "global",
                                        "accessEveryThing")}))).build());
        return contexts;
    }

    public List<SecurityScheme> securitySchemes() {
        List<SecurityScheme> schemes = new ArrayList<>();
        schemes.add(new ApiKey("JWT : Bearer <token>", HttpHeaders.AUTHORIZATION, "Header"));
        return schemes;
    }

    @Bean
    public AlternateTypeRule alternateTypeRuleForPageable() {
        return AlternateTypeRules.newRule(
                typeResolver.resolve(Pageable.class),
                typeResolver.resolve(Page.class));
    }

    @Data
    static class Page {
        @ApiModelProperty(value = "페이지 번호(0..N) 0부터 시작", example = "0")
        private Integer page;

        @ApiModelProperty(value = "페이지 크기", allowableValues = "range[0, 100]", example = "10")
        private Integer size;

        @ApiModelProperty(value = "정렬(사용법: 컬럼명,ASC|DESC) createAt,DESC")
        private List<String> sort;
    }

}