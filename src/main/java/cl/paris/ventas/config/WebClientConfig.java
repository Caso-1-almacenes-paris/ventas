package cl.paris.ventas.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient clientesWebClient(@Value("${services.clientes.url}") String url) {
        return WebClient.builder().baseUrl(url).build();
    }

    @Bean
    public WebClient productosWebClient(@Value("${services.productos.url}") String url) {
        return WebClient.builder().baseUrl(url).build();
    }
}
