package mate.exceprion;

public class DataProccesingExceprion extends RuntimeException {
    public DataProccesingExceprion(String messege, Throwable ex) {
        super(messege, ex);
    }
}
