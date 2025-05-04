package mate.exception;

public class DataProccesingException extends RuntimeException {
    public DataProccesingException(String messege, Throwable ex) {
        super(messege, ex);
    }
}
