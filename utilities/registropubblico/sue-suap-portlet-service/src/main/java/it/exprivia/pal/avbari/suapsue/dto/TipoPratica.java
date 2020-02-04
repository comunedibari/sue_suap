package it.exprivia.pal.avbari.suapsue.dto;

public enum TipoPratica {
	SUAP, SUE;
	
	public static TipoPratica decode(String tipo) {
		if ("SUAP".equalsIgnoreCase(tipo))
			return SUAP;
		else if ("SUE".equalsIgnoreCase(tipo))
			return SUE;
		else
			return null;
	}
}