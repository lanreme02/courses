package com.cognizant.config;


import com.google.common.base.Predicate;
import com.google.common.base.Predicates;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public  class CloudFoundryDataBaseConfig {
/*
    @Bean
    public Cloud cloud() {
        return new CloudFactory().getCloud();
    }

    @Bean
    public DataSource dataSource() {
        DataSource dataSource = cloud().getServiceConnector("bodha-mysql", DataSource.class, null);
        return dataSource;
    }
*/
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public Docket produceApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.cognizant.courses.controller"))
                .paths(paths())
                .build();
    }
    // Describe your apis
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Hotel Management Rest APIs")
                .description("This page lists all the rest apis for Hotel Management App.")
                .version("1.0-SNAPSHOT")
                .build();
    }
    // Only select apis that matches the given Predicates.
    private Predicate<String> paths() {
// Match all paths except /error
        return Predicates.and(
                PathSelectors.regex("/api.*"),
                Predicates.not(PathSelectors.regex("/error.*")));
    }
}

