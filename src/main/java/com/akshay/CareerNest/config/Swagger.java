package com.akshay.CareerNest.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class Swagger {

    @Bean
public OpenAPI myCustomConfig()
{
   return new OpenAPI().info(
           new Info().title("CareerNest App API's")
                   .description("By Akshay Patil")
                   )
           .servers(List.of(new Server().url("http://localhost:8080").description("local"),
                   new Server().url("http://localhost:8082").description("live"))
           )
           .addSecurityItem(new SecurityRequirement().addList("basicAuth"))
           .components(new io.swagger.v3.oas.models.Components()
                   .addSecuritySchemes("basicAuth",
                           new SecurityScheme()
                                   .type(SecurityScheme.Type.HTTP)
                                   .scheme("basic")
                   )
           );



}

}
