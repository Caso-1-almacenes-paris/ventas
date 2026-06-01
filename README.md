# ventas

Microservicio de **órdenes de compra** del marketplace Paris (Caso 1, DSY1103).

## Responsabilidad
Registrar la compra de un cliente (cabecera + líneas), validar cliente y productos
contra sus microservicios, calcular monto y comisión, y confirmar el pago.

## Puerto
`8083`

## Endpoints

| Método | Ruta | Descripción |
|---|---|---|
| POST | `/api/v1/ventas` | Crea una venta (valida cliente y productos vía WebClient) |
| GET | `/api/v1/ventas/{id}` | Obtiene una venta (contrato que consumen registro/estado/tickets/feedback) |
| PATCH | `/api/v1/ventas/{id}/pagar` | Confirma el pago (CREADA → PAGADA) |
| GET | `/api/v1/ventas/cliente/{clienteId}` | Ventas de un cliente |

### Ejemplo POST `/api/v1/ventas`
```json
{
  "clienteId": "11111111-1111-1111-1111-111111111111",
  "items": [
    { "productoId": 55, "cantidad": 1 },
    { "productoId": 60, "cantidad": 2 }
  ]
}
```

## Comunicación (WebClient)
- `GET productos/api/v1/productos/{id}` → precio, stock y proveedor.
- `GET clientes/api/v1/clientes/{id}` → validación del cliente.

> ⚠️ **Dependencia de integración:** los servicios `clientes` y `productos` deben exponer
> `GET /api/v1/{recurso}/{id}` (hoy solo tienen el listado y el POST). Coordinar con el equipo.

## Base de datos
BD **independiente** en Neon (no compartir con otros servicios). Configurar vía variables
de entorno o editando `application.yml`:

```
VENTAS_DB_URL, VENTAS_DB_USER, VENTAS_DB_PASS
CLIENTES_URL (default http://localhost:8081)
PRODUCTOS_URL (default http://localhost:8082)
```

## Ejecutar
```bash
./mvnw spring-boot:run
```

## Modelo de datos
```
venta (1) ──< (N) detalle_venta
```
- `EstadoVenta`: CREADA, PAGADA, ANULADA
- Comisión del marketplace: 10% del monto total (`ventas.tasa-comision`)
