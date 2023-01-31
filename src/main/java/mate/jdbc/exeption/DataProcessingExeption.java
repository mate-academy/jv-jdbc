package mate.jdbc.exeption;

public class DataProcessingExeption extends RuntimeException {
    public DataProcessingExeption(String message, Throwable cause) {
        super(message, cause);
    }
}
