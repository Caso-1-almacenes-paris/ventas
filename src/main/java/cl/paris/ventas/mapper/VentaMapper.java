package cl.paris.ventas.mapper;

import java.util.List;

import cl.paris.ventas.dto.DetalleResponse;
import cl.paris.ventas.dto.VentaResponse;
import cl.paris.ventas.model.DetalleVenta;
import cl.paris.ventas.model.Venta;

public final class VentaMapper {

    private VentaMapper() {
    }

    public static VentaResponse toResponse(Venta venta) {
        List<DetalleResponse> detalles = venta.getDetalles().stream()
                .map(VentaMapper::toDetalleResponse)
                .toList();

        return new VentaResponse(
                venta.getId(),
                venta.getClienteId(),
                venta.getEstado(),
                venta.getMontoTotal(),
                venta.getComisionTotal(),
                venta.getFecha(),
                detalles
        );
    }

    private static DetalleResponse toDetalleResponse(DetalleVenta detalle) {
        return new DetalleResponse(
                detalle.getProductoId(),
                detalle.getProveedorId(),
                detalle.getNombreProducto(),
                detalle.getCantidad(),
                detalle.getPrecioUnitario(),
                detalle.getSubtotal()
        );
    }
}
