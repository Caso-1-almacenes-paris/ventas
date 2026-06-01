package cl.paris.ventas.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/** Una linea solicitada en la compra: que producto y cuanto. */
public record ItemRequest(

        @NotNull(message = "El productoId es obligatorio")
        Long productoId,

        @NotNull(message = "La cantidad es obligatoria")
        @Min(value = 1, message = "La cantidad debe ser al menos 1")
        Integer cantidad
) {
}
