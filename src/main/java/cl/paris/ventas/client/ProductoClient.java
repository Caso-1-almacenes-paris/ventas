package cl.paris.ventas.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import cl.paris.ventas.dto.ProductoResponse;
import cl.paris.ventas.exception.ResourceNotFoundException;

@Component
public class ProductoClient {

    private final RestClient restClient;

    // Nota: Cambié el nombre del qualifier a "productosRestClient" por coherencia.
    public ProductoClient(@Qualifier("productosRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    public ProductoResponse obtenerProducto(Long productoId) {
        return restClient.get()
                .uri("/api/v1/productos/{id}", productoId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    throw new ResourceNotFoundException(
                            "Producto " + productoId + " no encontrado en el servicio de productos");
                })
                .body(ProductoResponse.class);
    }
}