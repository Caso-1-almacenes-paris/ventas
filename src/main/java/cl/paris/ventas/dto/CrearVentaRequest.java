package cl.paris.ventas.dto;

import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/** Datos que envia el cliente para crear una venta. */
public record CrearVentaRequest(

        @NotNull(message = "El clienteId es obligatorio")
        UUID clienteId,

        @NotEmpty(message = "La venta debe tener al menos un item")
        @Valid
        List<ItemRequest> items
) {
}
