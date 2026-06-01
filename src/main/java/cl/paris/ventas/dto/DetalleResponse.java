package cl.paris.ventas.dto;

import java.math.BigDecimal;

/** Linea de la venta tal como se expone hacia afuera. */
public record DetalleResponse(
        Long productoId,
        Long proveedorId,
        String nombreProducto,
        Integer cantidad,
        BigDecimal precioUnitario,
        BigDecimal subtotal
) {
}
