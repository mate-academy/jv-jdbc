package mate.jdbc.exception;

public class RecordDoesNotExistException extends RuntimeException {
    public RecordDoesNotExistException(Long id) {
        super("Record with id: " + id + " doesn't exist");
    }
}
