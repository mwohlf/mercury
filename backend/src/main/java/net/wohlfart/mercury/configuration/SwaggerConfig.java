package net.wohlfart.mercury.configuration;

import com.google.common.collect.Sets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


// see: http://www.baeldung.com/swagger-2-documentation-for-spring-rest-api
// see: http://localhost:8080/swagger-resources
// see: http://localhost:8080/swagger-ui.html#/
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    public static final String BEARER = "Bearer";
    public static final String AUTHORIZATION = "dude";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)

                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/")
                .securitySchemes(securitySchemes())
                .produces(Sets.newHashSet(APPLICATION_JSON_VALUE))
                .consumes(Sets.newHashSet(APPLICATION_JSON_VALUE))
                ;
    }

    private List<ApiKey> securitySchemes() {
        //noinspection RedundantArrayCreation
        return Arrays.asList( new ApiKey[] {
            new ApiKey("mykey", "api_key", "header")

        });
    }

    @Bean
    SecurityConfiguration security() {
        return new SecurityConfiguration(
                null,
                null,
                null, // realm Needed for authenticate button to work
                null, // appName Needed for authenticate button to work
                BEARER,// apiKeyValue
                ApiKeyVehicle.HEADER,
                AUTHORIZATION, //apiKeyName
                null);
    }

}