package cl.paris.ventas.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Espejo de la respuesta del microservicio "productos" (GET /api/v1/productos/{id}).
 * Coincide con la entidad Producto del equipo: id, idprovedor, nombre, precio, stock...
 */
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
