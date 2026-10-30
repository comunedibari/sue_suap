package dms.service;

public class GetAllegatiPratica {
	private static final String RESPONSE = 
			"{				\"ALLEGATI\": {"
			+ "					\"allegato\": ["
			+ "						{"
			+ "							\"id\": 501,"
			+ "							\"categoria\": null,"
			+ "							\"depositato\": true,"
			+ "							\"descrizione\": \"DMS 01- n.ro 01 copie AL, DIA, SCIA, PDC.\","
			+ "							\"data_arrivo\": \"20/07/2007\","
			+ "							\"nome_file\": \"20140521195135_PE_STAMPAWORD_16756.PDF\""
			+ "						},"
			+ "						{"
			+ "							\"id\": 502,"
			+ "							\"categoria\": null,"
			+ "							\"depositato\": true,"
			+ "							\"descrizione\": \"DMS 02- n.ro 01 copie Relazione tecnico-illustrativa dettagliata dell'intervento in aggiunta alla descrizione sintetica della relazione di Asseveramento.\","
			+ "							\"data_arrivo\": \"20/07/2007\","
			+ "							\"nome_file\": \"20140521195135_PE_STAMPAWORD_16757.PDF\""
			+ "						},"
			+ "						{"
			+ "							\"id\": 503,"
			+ "							\"categoria\": null,"
			+ "							\"depositato\": true,"
			+ "							\"descrizione\": \"03- n.ro 01 copie ELABORATI DI PROGETTO -tutti gli elaborati devono essere riducibili in formato UNI A4.\","
			+ "							\"data_arrivo\": \"20/07/2007\","
			+ "							\"nome_file\": \"20140521195135_PE_STAMPAWORD_16758.PDF\""
			+ "						},"
			+ "						{"
			+ "							\"id\": 504,"
			+ "							\"categoria\": null,"
			+ "							\"depositato\": true,"
			+ "							\"descrizione\": \"04- Documentazione fotografica con particolare riferimento alla zona di intervento con coni visuali.\","
			+ "							\"data_arrivo\": \"20/07/2007\","
			+ "							\"nome_file\": \"20140521195135_PE_STAMPAWORD_16759.PDF\""
			+ "						},"
			+ "						{"
			+ "							\"id\": 505,"
			+ "							\"categoria\": null,"
			+ "							\"depositato\": true,"
			+ "							\"descrizione\": \"05- Bilancio di produzione dei rifiuti e eventuali allegati grafici ai sensi del Regolamento Regionale n.6/2006.\","
			+ "							\"data_arrivo\": \"20/07/2007\","
			+ "							\"nome_file\": \"20140521195135_PE_STAMPAWORD_16760.PDF\""
			+ "						},"
			+ "						{"
			+ "							\"id\": 506,"
			+ "							\"categoria\": null,"
			+ "							\"depositato\": true,"
			+ "							\"descrizione\": \"06- Dichiarazione, relazione ed elaborati grafici ai sensi della L. 13/89 e s.m.i..\","
			+ "							\"data_arrivo\": \"20/07/2007\","
			+ "							\"nome_file\": \"20140521195135_PE_STAMPAWORD_16761.PDF\""
			+ "						},"
			+ "						{"
			+ "							\"id\": 507,"
			+ "							\"categoria\": null,"
			+ "							\"depositato\": true,"
			+ "							\"descrizione\": \"07- Diritti di Segreteria (secondo tabella in vigore).\","
			+ "							\"data_arrivo\": \"20/07/2007\","
			+ "							\"nome_file\": \"20140521195135_PE_STAMPAWORD_16762.PDF\""
			+ "						},"
			+ "						{"
			+ "							\"id\": 508,"
			+ "							\"categoria\": null,"
			+ "							\"depositato\": true,"
			+ "							\"descrizione\": \"08- D.U.R.C. (documento unico di regolarita' contributiva).\","
			+ "							\"data_arrivo\": \"20/07/2007\","
			+ "							\"nome_file\": \"20140521195135_PE_STAMPAWORD_16763.PDF\""
			+ "						},"
			+ "						{"
			+ "							\"id\": 509,"
			+ "							\"categoria\": null,"
			+ "							\"depositato\": true,"
			+ "							\"descrizione\": \"09- Copia dell'avvenuto deposito progetto degli impianti presso la Ripartizione Edilizia Pubblica - Ufficio Energia e Sicurezza degli Impianti (via Giulio Petroni, 103 - Bari).\","
			+ "							\"data_arrivo\": \"20/07/2007\","
			+ "							\"nome_file\": \"20140521195135_PE_STAMPAWORD_16764.PDF\""
			+ "						},"
			+ "						{"
			+ "							\"id\": 510,"
			+ "							\"categoria\": null,"
			+ "							\"depositato\": true,"
			+ "							\"descrizione\": \"10- Domanda di AP (vincolo D.Lgs. n. 42/2004 e s.m.i.), corredata da n. 4 copie degli allegati di cui primi quattro punti della voce precedente e relazione paesaggistica.\","
			+ "							\"data_arrivo\": \"20/07/2007\","
			+ "							\"nome_file\": \"20140521195135_PE_STAMPAWORD_16765.PDF\""
			+ "						},"
			+ "						{"
			+ "							\"id\": 511,"
			+ "							\"categoria\": null,"
			+ "							\"depositato\": true,"
			+ "							\"descrizione\": \"11- Parere della Soprintendenza per immobile soggetto a vincolo storico artistico (vincolo D.lgs. n. 42/2004 e s.m.i.) con elaborati grafici approvati.\","
			+ "							\"data_arrivo\": \"20/07/2007\","
			+ "							\"nome_file\": \"20140521195135_PE_STAMPAWORD_16766.PDF\""
			+ "						},"
			+ "						{"
			+ "							\"id\": 512,"
			+ "							\"categoria\": null,"
			+ "							\"depositato\": true,"
			+ "							\"descrizione\": \"DIA - 20 - Sospensione\","
			+ "							\"data_arrivo\": \"10/09/2007\","
			+ "							\"nome_file\": \"20140521195135_PE_STAMPAWORD_16767.PDF\""
			+ "						},"
			+ "						{"
			+ "							\"id\": 513,"
			+ "							\"categoria\": null,"
			+ "							\"depositato\": true,"
			+ "							\"descrizione\": \"DIA - 22 - Riattivazione post sospensione\","
			+ "							\"data_arrivo\": \"11/06/2008\","
			+ "							\"nome_file\": \"20140521195135_PE_STAMPAWORD_16768.PDF\""
			+ "						}"
			+ "					]"
			+ "				}"
			+ "			}"
			+ "		}"
			+ "	]"
			+ "}"; 
						
	private String request;
	private String response;
	

	public GetAllegatiPratica(String request) {
		super();
		this.request = request;
	}

	public String getRequest() {
		return request;
	}
	public void setRequest(String request) {
		this.request = request;
	}
	public String getResponse() {
		return RESPONSE;
	}
	public void setResponse(String response) {
		this.response = response;
	}

}
