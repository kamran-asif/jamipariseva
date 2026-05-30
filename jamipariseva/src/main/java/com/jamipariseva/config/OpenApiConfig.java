package com.jamipariseva.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Value("${server.servlet.context-path:/}")
    private String contextPath;

    @Bean
    public OpenAPI jamiparisevaOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Jamipariseva API v2")
                        .description("REST APIs for citizen UI — location, services, RoR verify, service requests")
                        .version("2.0"))
                .addServersItem(new Server()
                        .url(contextPath)
                        .description("Local server"));
    }
}
