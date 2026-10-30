package it.wego.cross.cross_tomcat_services.utils;

public class PaperworkNumberException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PaperworkNumberException(String e){
		super(e);
	}

	public PaperworkNumberException(Exception e){
		super(e);
	}

	public PaperworkNumberException(String msg, Exception e){
		super(msg, e);
	}
}
