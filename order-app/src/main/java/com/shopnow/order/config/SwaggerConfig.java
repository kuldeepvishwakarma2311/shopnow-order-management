package com.shopnow.order.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI shopNowOpenApi() {
        return new OpenAPI()
            .info(new Info()
                .title("ShopNow Order Management API")
                .version("1.0.0")
                .description("Enterprise order lifecycle, inventory, warehouse routing, shipment, and return APIs.")
                .contact(new Contact().name("ShopNow IT Support").email("support@shopnow.local")))
            .addSecurityItem(new SecurityRequirement().addList("basicAuth"))
            .schemaRequirement("basicAuth",
                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic"));
    }
}
