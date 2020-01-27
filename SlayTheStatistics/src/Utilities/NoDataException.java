package Utilities;

public class NoDataException extends RuntimeException {

    public NoDataException(String s) {
        super(s);
    }

    public NoDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoDataException(Throwable cause) {
        super(cause);
    }
}
