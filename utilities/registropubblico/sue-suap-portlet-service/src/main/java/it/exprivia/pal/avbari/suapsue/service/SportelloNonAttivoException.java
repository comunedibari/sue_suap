package it.exprivia.pal.avbari.suapsue.service;

public class SportelloNonAttivoException extends Exception {
	
	private static final long serialVersionUID = 1026998742569755838L;
	
	protected Long idSportello;
	
	
	public SportelloNonAttivoException() {
		super();
	}
	
	public SportelloNonAttivoException(Throwable t) {
		super(t);
	}
	
	public SportelloNonAttivoException(Long idSportello) {
		super();
		this.idSportello = idSportello;
	}
	
	@Override
	public String getLocalizedMessage() {
		return String.format("SportelloNonAttivoException: sportello %d non attivo", idSportello);
	}

	@Override
	public String getMessage() {
		return getLocalizedMessage();
	}
}