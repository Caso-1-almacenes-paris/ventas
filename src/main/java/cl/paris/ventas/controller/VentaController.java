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

@RestController
@RequestMapping("/api/v1/ventas")
public class VentaController {

    private final VentaService ventaService;

    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VentaResponse crear(@Valid @RequestBody CrearVentaRequest request) {
        return VentaMapper.toResponse(ventaService.crearVenta(request));
    }

    @GetMapping("/{id}")
    public VentaResponse obtener(@PathVariable Long id) {
        return VentaMapper.toResponse(ventaService.obtener(id));
    }

    @PatchMapping("/{id}/pagar")
    public VentaResponse pagar(@PathVariable Long id) {
        return VentaMapper.toResponse(ventaService.pagar(id));
    }

    @GetMapping("/cliente/{clienteId}")
    public List<VentaResponse> obtenerPorCliente(@PathVariable UUID clienteId) {
        return ventaService.obtenerPorCliente(clienteId).stream()
                .map(VentaMapper::toResponse)
                .toList();
    }
}
