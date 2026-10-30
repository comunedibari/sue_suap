package it.wego.cross.session;

public class SubSessionException extends RuntimeException{

	public SubSessionException(Exception e) {
		super(e);
	}
	
	public SubSessionException(String msg, Exception e) {
		super(msg, e);
	}

	public SubSessionException(String string) {
		super(string);
	}
}
