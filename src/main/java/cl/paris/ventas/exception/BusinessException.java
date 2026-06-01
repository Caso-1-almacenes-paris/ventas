package cl.paris.ventas.exception;

/** Se lanza al violar una regla de negocio (stock insuficiente, estado invalido). -> HTTP 422 */
public class BusinessException extends RuntimeException {

    public BusinessException(String mensaje) {
        super(mensaje);
    }
}
