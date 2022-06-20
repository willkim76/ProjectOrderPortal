package exceptions;

public class InvalidOrderException extends OrderException {
    private static final long serialVersionUID = -5247649455950280027L;

    public InvalidOrderException() {
        super();
    }

    public InvalidOrderException(String message) {
        super(message);
    }

    public InvalidOrderException(String message, Throwable context) {
        super(message, context);
    }
}
