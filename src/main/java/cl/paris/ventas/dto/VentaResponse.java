package cl.paris.ventas.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import cl.paris.ventas.model.EstadoVenta;

/**
 * Respuesta de una venta. Este es el contrato que consumen los microservicios
 * registro, estado, tickets y feedback via WebClient.
 */
public record VentaResponse(
        Long id,
        UUID clienteId,
        EstadoVenta estado,
        BigDecimal montoTotal,
        BigDecimal comisionTotal,
        LocalDateTime fecha,
        List<DetalleResponse> detalles
) {
}
