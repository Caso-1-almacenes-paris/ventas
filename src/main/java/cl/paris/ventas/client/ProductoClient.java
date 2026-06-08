package cl.paris.ventas.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import cl.paris.ventas.dto.ProductoResponse;
import cl.paris.ventas.exception.ResourceNotFoundException;
import reactor.core.publisher.Mono;

@Component
public class ProductoClient {

    private final WebClient webClient;

    public ProductoClient(@Qualifier("productosWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public ProductoResponse obtenerProducto(Long productoId) {
        return webClient.get()
                .uri("/api/v1/productos/{id}", productoId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, resp ->
                        Mono.error(new ResourceNotFoundException(
                                "Producto " + productoId + " no encontrado en el servicio de productos")))
                .bodyToMono(ProductoResponse.class)
                .block();
    }
}
