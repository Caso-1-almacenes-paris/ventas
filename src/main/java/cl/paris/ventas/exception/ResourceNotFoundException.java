package cl.paris.ventas.exception;

/** Se lanza cuando un recurso (venta, producto, cliente) no existe. -> HTTP 404 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String mensaje) {
        super(mensaje);
    }
}
