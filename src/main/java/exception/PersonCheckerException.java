package exception;

public class PersonCheckerException extends  Exception{

    public PersonCheckerException() {
    }

    public PersonCheckerException(String message) {
        super(message);
    }

    public PersonCheckerException(String message, Throwable cause) {
        super(message, cause);
    }

    public PersonCheckerException(Throwable cause) {
        super(cause);
    }

    public PersonCheckerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
