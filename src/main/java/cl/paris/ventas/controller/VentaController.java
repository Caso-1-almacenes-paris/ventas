package cl.paris.ventas.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cl.paris.ventas.dto.CrearVentaRequest;
import cl.paris.ventas.dto.VentaResponse;
import cl.paris.ventas.mapper.VentaMapper;
import cl.paris.ventas.service.VentaService;
import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Content;

@RestController
@RequestMapping("/api/v1/ventas")
@Tag(name = "Ventas", description = "Operaciones sobre las ventas")
public class VentaController {

    private final VentaService ventaService;

    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear una nueva venta", description = "Registra una venta con sus items asociados")
    @ApiResponse(responseCode = "201", description = "Venta creada de forma exitosa")
    public VentaResponse crear(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos para la creación de la venta",
                    content = @Content(
                            examples = @ExampleObject(
                                    name = "Ejemplo Venta",
                                    value = "{\n  \"clienteId\": \"123e4567-e89b-12d3-a456-426614174000\",\n  \"items\": [\n    {\n      \"productoId\": 1,\n      \"cantidad\": 2\n    }\n  ]\n}"
                            )
                    )
            )
            @Valid @RequestBody CrearVentaRequest request) {
        return VentaMapper.toResponse(ventaService.crearVenta(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener venta por ID", description = "Retorna el detalle completo de una venta")
    @ApiResponse(responseCode = "200", description = "Venta encontrada exitosamente")
    public VentaResponse obtener(@PathVariable Long id) {
        return VentaMapper.toResponse(ventaService.obtener(id));
    }

    @PatchMapping("/{id}/pagar")
    @Operation(summary = "Pagar venta", description = "Marca una venta específica como pagada")
    @ApiResponse(responseCode = "200", description = "Venta pagada exitosamente")
    public VentaResponse pagar(@PathVariable Long id) {
        return VentaMapper.toResponse(ventaService.pagar(id));
    }

    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Obtener ventas por cliente", description = "Retorna todas las ventas asociadas al ID de un cliente")
    @ApiResponse(responseCode = "200", description = "Listado de ventas obtenido exitosamente")
    public List<VentaResponse> obtenerPorCliente(@PathVariable UUID clienteId) {
        return ventaService.obtenerPorCliente(clienteId).stream()
                .map(VentaMapper::toResponse)
                .toList();
    }
}