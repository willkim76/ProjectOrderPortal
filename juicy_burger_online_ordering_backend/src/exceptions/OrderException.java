package exceptions;

public class OrderException extends RuntimeException {
    private static final long serialVersionUID = 5395358439807723306L;

    public OrderException() {
        super();
    }

    public OrderException(String message) {
        super(message);
    }

    public OrderException(String message, Throwable context) {
        super(message, context);
    }
}
