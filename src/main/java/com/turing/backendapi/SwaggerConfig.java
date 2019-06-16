package com.turing.backendapi;

import springfox.documentation.service.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2).useDefaultResponseMessages(false)
                                                  .select()
                                                  .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                                                  .paths(PathSelectors.any())
                                                  .build()
                                                  .apiInfo(metaData());
  }

  private ApiInfo metaData() {
    ApiInfo apiInfo = new ApiInfo("Turing Backend API - Challenge",
                                  "Spring Boot REST API for Turing Challenge",
                                  "1.0",
                                  "Terms of service",
                                  new Contact("Liviu Marinescu", "", "lmarinescu@gmail.com"),
                                  "Apache License Version 2.0",
                                  "https://www.apache.org/licenses/LICENSE-2.0",
                                  Collections.emptyList());

    return apiInfo;
  }
}
