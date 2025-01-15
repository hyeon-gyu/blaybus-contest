package contest.blaybus.v1.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI swaggerCustomUI() {
        return new OpenAPI()
                .info(getOpenAPIInfo())
                .components(getOpenAPIComponents())
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
    }

    public Components getOpenAPIComponents() {
        return new Components()
                .addSecuritySchemes("bearerAuth", new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .in(SecurityScheme.In.HEADER)
                        .name("Authorization")
                        .description("Please enter 'Bearer' followed by a space and then your token"));
    }

    private Info getOpenAPIInfo() {
        return new Info()
                .title("Blaybus 앱 개발 경진대회") // API의 제목
                .description("Backend Swagger") // API에 대한 설명
                .version("1v"); // API의 버전
    }
}