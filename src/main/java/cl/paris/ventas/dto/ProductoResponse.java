package cl.paris.ventas.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ProductoResponse(
        Long id,
        Long idprovedor,
        String nombre,
        Integer precio,
        Integer stock,
        String descripcion,
        String categoria
) {
}
