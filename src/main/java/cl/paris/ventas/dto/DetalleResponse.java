package cl.paris.ventas.dto;

import java.math.BigDecimal;

public record DetalleResponse(
        Long productoId,
        Long proveedorId,
        String nombreProducto,
        Integer cantidad,
        BigDecimal precioUnitario,
        BigDecimal subtotal
) {
}
