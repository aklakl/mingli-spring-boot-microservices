package com.mingli.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import java.util.Collections;
import java.util.List;
import static com.google.common.collect.Lists.newArrayList;

@Configuration
@EnableSwagger2
@ConfigurationProperties
public class Swagger2Config {

    @Value("${security.oauth2.client.clientId}")
    private String clientId;
    @Value("${security.oauth2.client.clientSecret}")
    private String clientSecret;

    
    public static final String securitySchemaOAuth2 = "oauth";
    public static final String authorizationScopeGlobal = "global";
    public static final String authorizationScopeGlobalDesc = "accessEverything";
    
	@Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.mingli.controller"))
                .paths(PathSelectors.ant("/mingliTestController*/*"))
                .build()
                .securitySchemes(Collections.singletonList(securitySchema()))
                .securityContexts(Collections.singletonList(securityContext()))
                .pathMapping("/")
                .apiInfo(apiInfo());
    }
	
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("RobustWealth REST API documentation")
                .description("RobustWealth Spring Boot REST API")
                .version("1.0")
                .license("RobustWealth Licence Version 1.0")
                .licenseUrl("https://docs.advrw.com/TCPP.pdf")
                .contact(new Contact("RobustWealth", "https://www.robustwealth.com/", "service@robustwealth.com"))
                .build();
    }
    
    private OAuth securitySchema() {

        List<AuthorizationScope> authorizationScopeList = newArrayList();
        authorizationScopeList.add(new AuthorizationScope("global", "access all"));

        List<GrantType> grantTypes = newArrayList();
        final TokenRequestEndpoint tokenRequestEndpoint = new TokenRequestEndpoint("http://localhost:8765/auth/uaa/oauth/token", clientId, clientSecret);
        final TokenEndpoint tokenEndpoint = new TokenEndpoint("http://localhost:8765/auth/uaa/oauth/token", "access_token");
        AuthorizationCodeGrant authorizationCodeGrant = new AuthorizationCodeGrant(tokenRequestEndpoint, tokenEndpoint);

        grantTypes.add(authorizationCodeGrant);

        OAuth oAuth = new OAuth("oauth", authorizationScopeList, grantTypes);

        return oAuth;
    }
    
    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth())
                .forPaths(PathSelectors.ant("/mingliTestController*/*")).build();
    }

    private List<SecurityReference> defaultAuth() {

        final AuthorizationScope authorizationScope =
                new AuthorizationScope(authorizationScopeGlobal, authorizationScopeGlobalDesc);
        final AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Collections
                .singletonList(new SecurityReference(securitySchemaOAuth2, authorizationScopes));
    }

}
