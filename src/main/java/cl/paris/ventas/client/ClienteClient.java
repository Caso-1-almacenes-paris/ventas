package cl.paris.ventas.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.UUID;

import cl.paris.ventas.dto.ClienteResponse;
import cl.paris.ventas.exception.ResourceNotFoundException;

/** Consume el microservicio "clientes" para validar que el cliente existe. */
@Component
public class ClienteClient {

    private final RestClient restClient;

    public ClienteClient(@Qualifier("clientesRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    /** Trae el cliente por id; lanza 404 si no existe. */
    public ClienteResponse obtenerCliente(UUID clienteId) {
        return restClient.get()
                .uri("/api/v1/clientes/{id}", clienteId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    throw new ResourceNotFoundException(
                            "Cliente " + clienteId + " no encontrado en el servicio de clientes");
                })
                .body(ClienteResponse.class);
    }
}