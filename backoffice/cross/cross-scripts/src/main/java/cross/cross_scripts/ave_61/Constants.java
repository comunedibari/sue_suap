package cross.cross_scripts.ave_61;

import java.util.ArrayList;
import java.util.List;

public class Constants {
	

	public static String QUERY_PRATICHE_ID_COMUNE = "SELECT p.id_pratica  FROM pratica p where p.id_comune = :idComune "
			+ "and identificativo_pratica not like 'RSSNGL78M17L049T%' "
			+ "order by p.id_pratica asc";
	
	public static List<String> paperworksColumns = new ArrayList<>();
	
	static {
		paperworksColumns.add("id_pratica");
		paperworksColumns.add("identificativo_pratica");
		paperworksColumns.add("oggetto_pratica");
		paperworksColumns.add("protocollo");
		paperworksColumns.add("cod_registro");
		paperworksColumns.add("anno_riferimento");
		paperworksColumns.add("stato_pratica");
		paperworksColumns.add("data_apertura");
		paperworksColumns.add("data_chiusura");
		paperworksColumns.add("processo");
//		paperworksColumns.add("id_utente");
//		paperworksColumns.add("id_comune");
		paperworksColumns.add("data_ricezione");
		paperworksColumns.add("responsabile_procedimento");
		paperworksColumns.add("data_protocollazione");
		paperworksColumns.add("giorni_sospensione");
		paperworksColumns.add("tipo_pratica");
		paperworksColumns.add("numero_pratica");
		paperworksColumns.add("anno");
		paperworksColumns.add("descrizione_intervento");
		paperworksColumns.add("data_ultimo_evento_esterno");
	}
	public static List<String> cadastralDataColumns = new ArrayList<>();
	
	static {
		cadastralDataColumns.add("id_immobile");
		cadastralDataColumns.add("id_pratica");
		cadastralDataColumns.add("tipo_sistema_catastale");
		cadastralDataColumns.add("sezione");
		cadastralDataColumns.add("id_tipo_unita");
		cadastralDataColumns.add("foglio");
		cadastralDataColumns.add("mappale");
		cadastralDataColumns.add("tipo_particella");
		cadastralDataColumns.add("estensione_particella");
		cadastralDataColumns.add("subalterno");
		cadastralDataColumns.add("comune_censuario");
		cadastralDataColumns.add("cod_immobile");
		cadastralDataColumns.add("categoria");
	}
	
	public static List<String> cadastralStreetColumns = new ArrayList<>();
	static {
//		cadastralStreetColumns.add("id_indirizzo_intervento");
		cadastralStreetColumns.add("id_pratica");
		cadastralStreetColumns.add("localita");
		cadastralStreetColumns.add("id_dug");
		cadastralStreetColumns.add("indirizzo");
		cadastralStreetColumns.add("civico");
		cadastralStreetColumns.add("cap");
		cadastralStreetColumns.add("altre_informazioni_indirizzo");
		cadastralStreetColumns.add("cod_civico");
		cadastralStreetColumns.add("cod_via");
		cadastralStreetColumns.add("interno_numero");
		cadastralStreetColumns.add("interno_lettera");
		cadastralStreetColumns.add("interno_scala");
		cadastralStreetColumns.add("lettera");
		cadastralStreetColumns.add("colore");
		cadastralStreetColumns.add("latitudine");
		cadastralStreetColumns.add("longitudine");
		cadastralStreetColumns.add("dato_esteso_1");
		cadastralStreetColumns.add("dato_esteso_2");
		cadastralStreetColumns.add("piano");
	}
	
	public static List<String> personalDataColumns = new ArrayList<>();
	static {
		personalDataColumns.add("id_pratica");
//		personalDataColumns.add("id_anagrafica");
		personalDataColumns.add("tipo_anagrafica");
		personalDataColumns.add("cognome");
		personalDataColumns.add("nome");
		personalDataColumns.add("codice_fiscale");
		personalDataColumns.add("denominazione");
		personalDataColumns.add("partita_iva");
		personalDataColumns.add("flg_individuale");
		personalDataColumns.add("data_nascita");
		personalDataColumns.add("cittadinanza");
		personalDataColumns.add("nazionalita");
		personalDataColumns.add("comune_nascita");
		personalDataColumns.add("localita_nascita");
		personalDataColumns.add("sesso");
		personalDataColumns.add("tipo_collegio");
		personalDataColumns.add("numero_iscrizione");
		personalDataColumns.add("data_iscrizione");
		personalDataColumns.add("provincia_iscrizione");
		personalDataColumns.add("provincia_cciaa");
		personalDataColumns.add("flg_attesa_iscrizione_ri");
		personalDataColumns.add("flg_obbligo_iscrizione_ri");
		personalDataColumns.add("data_iscrizione_ri");
		personalDataColumns.add("n_iscrizione_ri");
		personalDataColumns.add("flg_attesa_iscrizione_rea");
		personalDataColumns.add("data_iscrizione_rea");
		personalDataColumns.add("n_iscrizione_rea");
		personalDataColumns.add("id_forma_giuridica");
		personalDataColumns.add("variante_anagrafica");
		personalDataColumns.add("tipo_ruolo");
		personalDataColumns.add("data_inizio_validita");
		personalDataColumns.add("tipo_qualifica");
		personalDataColumns.add("descrizione_titolarita");
		personalDataColumns.add("assenso_uso_pec");
		personalDataColumns.add("pec");
		personalDataColumns.add("recapito_notifica");
		personalDataColumns.add("flg_ditta_individuale");
		personalDataColumns.add("etichetta");
	}

	public static List<String> praticheEventiColumns = new ArrayList<>();
	static {
		praticheEventiColumns.add("id_pratica");
		praticheEventiColumns.add("id_evento");
		praticheEventiColumns.add("descrizione_evento");
		praticheEventiColumns.add("data_evento");
		praticheEventiColumns.add("protocollo");
		praticheEventiColumns.add("data_protocollo");
		praticheEventiColumns.add("verso");
	}

	public static List<String> praticheEventiAllegatiColumns = new ArrayList<>();
	static {
		praticheEventiAllegatiColumns.add("id_pratica");
		praticheEventiAllegatiColumns.add("id_evento");
		praticheEventiAllegatiColumns.add("data_creazione");
		praticheEventiAllegatiColumns.add("descrizione");
		praticheEventiAllegatiColumns.add("nome_file");
		praticheEventiAllegatiColumns.add("tipo_file");
		praticheEventiAllegatiColumns.add("path_file");
	}
}
