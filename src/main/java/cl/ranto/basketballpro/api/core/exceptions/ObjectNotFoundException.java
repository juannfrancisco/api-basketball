package cl.ranto.basketballpro.api.core.exceptions;

public class ObjectNotFoundException extends Exception{

    private static final String MESSAGE = "Objeto no encontrado";

    public ObjectNotFoundException() {
        super(MESSAGE);
    }

    public ObjectNotFoundException(String message) {
        super(message);
    }

    public ObjectNotFoundException(String message, Throwable e) {
        super(message, e);
    }
}
