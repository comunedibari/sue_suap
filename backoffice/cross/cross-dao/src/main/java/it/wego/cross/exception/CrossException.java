package it.wego.cross.exception;

public class CrossException extends RuntimeException {

    private String message;

    public CrossException(String message) {
        this.message = message;
    }

    public CrossException(String message, Throwable cause) {
        super(message, cause);
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    @Override
    public String getMessage() {
        return message;
    }
}
