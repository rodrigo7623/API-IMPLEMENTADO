package com.api.desa.swagger;

import com.api.desa.entidad.NoticiaConFotoJson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.api.desa.controlador"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getApiInfo())
                .useDefaultResponseMessages(false)
                .directModelSubstitute(ResponseEntity.class, NoticiaConFotoJson.class);

    }

    private ApiInfo getApiInfo() {
        return new ApiInfo(
                "Apificar página web de ABC",
                "Obtiene la lista de noticias relacionadas con un texto dado",
                "1.0",
                "",
                new Contact("rodrigo7623", "http://localhost:8080/swagger-ui.html#/", "rodrigo7623@gmail.com"),
                "BUSCADOR ABC",
                "https://www.abc.com.py/buscar",
                Collections.emptyList());
    }
}
