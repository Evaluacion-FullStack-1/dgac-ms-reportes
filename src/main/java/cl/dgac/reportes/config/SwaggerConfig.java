package cl.dgac.reportes.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI dgacReportesOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API DGAC - Microservicio de Reportes")
                        .description("Documentación oficial de los endpoints para la generación, consulta y gestión de informes técnicos, auditorías y estadísticas del ecosistema de drones DGAC.")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("Equipo de Desarrollo DGAC")
                                .email("soporte@dgac.cl")
                                .url("https://www.dgac.cl")));
    }
}