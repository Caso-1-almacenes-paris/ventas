package cl.paris.ventas.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

import cl.paris.ventas.dto.ClienteResponse;
import cl.paris.ventas.exception.ResourceNotFoundException;
import reactor.core.publisher.Mono;

/** Consume el microservicio "clientes" para validar que el cliente existe. */
@Component
public class ClienteClient {

    private final WebClient webClient;

    public ClienteClient(@Qualifier("clientesWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    /** Trae el cliente por id; lanza 404 si no existe. */
    public ClienteResponse obtenerCliente(UUID clienteId) {
        return webClient.get()
                .uri("/api/v1/clientes/{id}", clienteId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, resp ->
                        Mono.error(new ResourceNotFoundException(
                                "Cliente " + clienteId + " no encontrado en el servicio de clientes")))
                .bodyToMono(ClienteResponse.class)
                .block();
    }
}
