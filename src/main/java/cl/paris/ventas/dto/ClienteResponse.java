package cl.paris.ventas.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Espejo de la respuesta del microservicio "clientes" (GET /api/v1/clientes/{id}).
 * Coincide con la entidad Cliente del equipo: id (UUID), rut, nombre, correo, numero.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record ClienteResponse(
        UUID id,
        String rut,
        String nombre,
        String correo,
        String numero
) {
}
