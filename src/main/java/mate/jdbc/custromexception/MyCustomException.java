package mate.jdbc.custromexception;

public class MyCustomException extends RuntimeException {
    public MyCustomException(String message, Throwable cause) {
        super(message, cause);
    }
}
