package cl.paris.ventas.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.paris.ventas.client.ClienteClient;
import cl.paris.ventas.client.ProductoClient;
import cl.paris.ventas.dto.CrearVentaRequest;
import cl.paris.ventas.dto.ItemRequest;
import cl.paris.ventas.dto.ProductoResponse;
import cl.paris.ventas.exception.BusinessException;
import cl.paris.ventas.exception.ResourceNotFoundException;
import cl.paris.ventas.model.DetalleVenta;
import cl.paris.ventas.model.EstadoVenta;
import cl.paris.ventas.model.Venta;
import cl.paris.ventas.repository.VentaRepository;

@Service
public class VentaService {

    private final VentaRepository ventaRepository;
    private final ClienteClient clienteClient;
    private final ProductoClient productoClient;
    private final BigDecimal tasaComision;

    public VentaService(VentaRepository ventaRepository,
                        ClienteClient clienteClient,
                        ProductoClient productoClient,
                        @Value("${ventas.tasa-comision}") BigDecimal tasaComision) {
        this.ventaRepository = ventaRepository;
        this.clienteClient = clienteClient;
        this.productoClient = productoClient;
        this.tasaComision = tasaComision;
    }

    /**
     * Crea una venta: valida el cliente y cada producto via WebClient,
     * arma los detalles con el precio real, valida stock y calcula totales.
     */
    @Transactional
    public Venta crearVenta(CrearVentaRequest request) {
        // Validacion cruzada 1: el cliente debe existir
        clienteClient.obtenerCliente(request.clienteId());

        Venta venta = new Venta();
        venta.setClienteId(request.clienteId());
        venta.setFecha(LocalDateTime.now());
        venta.setEstado(EstadoVenta.CREADA);

        BigDecimal total = BigDecimal.ZERO;

        for (ItemRequest item : request.items()) {
            // Validacion cruzada 2: el producto debe existir
            ProductoResponse producto = productoClient.obtenerProducto(item.productoId());

            if (producto.stock() == null || producto.stock() < item.cantidad()) {
                throw new BusinessException(
                        "Stock insuficiente para el producto '" + producto.nombre() + "'");
            }

            BigDecimal precioUnitario = BigDecimal.valueOf(producto.precio());
            BigDecimal subtotal = precioUnitario.multiply(BigDecimal.valueOf(item.cantidad()));

            DetalleVenta detalle = new DetalleVenta();
            detalle.setProductoId(producto.id());
            detalle.setProveedorId(producto.idprovedor());
            detalle.setNombreProducto(producto.nombre());
            detalle.setCantidad(item.cantidad());
            detalle.setPrecioUnitario(precioUnitario);
            detalle.setSubtotal(subtotal);
            venta.agregarDetalle(detalle);

            total = total.add(subtotal);
        }

        venta.setMontoTotal(total);
        venta.setComisionTotal(total.multiply(tasaComision));

        return ventaRepository.save(venta);
    }

    @Transactional(readOnly = true)
    public Venta obtener(Long id) {
        return ventaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venta " + id + " no encontrada"));
    }

    /** Confirma el pago de una venta en estado CREADA. */
    @Transactional
    public Venta pagar(Long id) {
        Venta venta = obtener(id);
        if (venta.getEstado() != EstadoVenta.CREADA) {
            throw new BusinessException(
                    "Solo se puede pagar una venta en estado CREADA (estado actual: " + venta.getEstado() + ")");
        }
        venta.setEstado(EstadoVenta.PAGADA);
        return ventaRepository.save(venta);
    }

    @Transactional(readOnly = true)
    public List<Venta> obtenerPorCliente(UUID clienteId) {
        return ventaRepository.findByClienteId(clienteId);
    }
}
