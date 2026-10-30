package it.wego.cross.cross_dms.config;

public class ConfigException extends Exception {
    private static final long serialVersionUID = 3898485402278478206L;

    public ConfigException() {}
    public ConfigException(String message) {
        super(message);
    }
    public ConfigException(Throwable cause) {
        super(cause);
    }
    public ConfigException(String message, Throwable cause) {
        super(message, cause);
    }
}

