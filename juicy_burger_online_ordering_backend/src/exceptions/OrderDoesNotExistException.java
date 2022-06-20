package exceptions;

public class OrderDoesNotExistException extends OrderException {
    private static final long serialVersionUID = -6571694166174960908L;

    public OrderDoesNotExistException() {
        super();
    }

    public OrderDoesNotExistException(String message) {
        super(message);
    }

    public OrderDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
