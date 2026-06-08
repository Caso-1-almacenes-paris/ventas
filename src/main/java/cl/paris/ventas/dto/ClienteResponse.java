package cl.paris.ventas.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ClienteResponse(
        UUID id,
        String rut,
        String nombre,
        String correo,
        String numero
) {
}
