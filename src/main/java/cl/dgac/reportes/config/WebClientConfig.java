package cl.dgac.reportes.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${incidencias.base-url:http://dgac-ms-incidencias}")
    private String incidenciasBaseUrl;

    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public WebClient webClientIncidencias(WebClient.Builder builder) {
        return builder.baseUrl(incidenciasBaseUrl).build();
    }
}