package mate.jdbc.exeption;

public class DataProccessingExeption extends RuntimeException{
    public DataProcessingException(String message, Throwable exception) {
        super(message, exception);
    }
}
