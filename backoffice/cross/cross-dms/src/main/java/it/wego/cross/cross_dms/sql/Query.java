package it.wego.cross.cross_dms.sql;

import it.wego.cross.cross_dms.utilities.LogClass;

public class Query {

	private static LogClass logger;
	
	public static String getUTCDataTime = "SELECT CURRENT_TIMESTAMP as datatimezone";
				
	public static String insertMsgLog ="INSERT INTO public.log_servizio (STACKTRACE) VALUES (?)";
	
	public static String addpratica_dati_pratica = "SELECT DISTINCT" + 
			"  `t1`.`id_pratica`                AS `id_pratica`," + 
			"  `t1`.`identificativo_pratica`    AS `identificativo_pratica`," + 
			"  `t1`.`numero_pratica`    		AS `numero_pratica`,"+
			"  `t1`.`anno_riferimento`    					AS `anno`," +
			"  `t1`.`id_tipo_pratica` 	   		AS `id_tipo_pratica`,"+
			"  `t1`.`oggetto_pratica` 	        AS `oggetto_pratica`," +
			"  `t1`.`anno_riferimento`          AS `anno_riferimento`," + 
			"  `t1`.`cod_registro`              AS `cod_registro`," + 
			"  `t1`.`data_apertura`             AS `data_apertura`," + 
			"  `t1`.`data_chiusura`             AS `data_chiusura`," + 
			"  `t1`.`data_protocollazione`      AS `data_protocollazione`," + 
			"  `t1`.`data_ricezione`            AS `data_ricezione`," + 
			"  `t1`.`giorni_sospensione`        AS `giorni_sospensione`," + 
			"  `t1`.`identificativo_esterno`    AS `identificativo_esterno`," + 
			"  `t1`.`identificativo_pratica`    AS `identificativo_pratica`," + 
			"  `t1`.`oggetto_pratica`           AS `oggetto_pratica`," + 
			"  `t1`.`protocollo`                AS `protocollo`," + 
			"  `t1`.`responsabile_procedimento` AS `responsabile_procedimento`," + 
			"  `t1`.`ufficio_riferimento`       AS `ufficio_riferimento`" + 
			" FROM (((`pratica` `t1`       " + 
			" JOIN `utente_ruolo_procedimento` `t4`)" + 
			" JOIN `utente_ruolo_ente` `t3`)" + 
			" JOIN `procedimenti_enti` `t2`)   " +
			" WHERE ((`t1`.`id_utente` IS NOT NULL)" + 
			" AND (`t2`.`id_proc_ente` = `t1`.`id_proc_ente`)" + 
			" AND (`t4`.`id_proc_ente` = `t2`.`id_proc_ente`)" + 
			" AND (`t3`.`id_utente_ruolo_ente` = `t4`.`id_utente_ruolo_ente`)" + 
			" AND (`t1`.`data_chiusura` IS NOT NULL )" + 
			" AND (t1.identificativo_pratica = '?'))";
	
	public static String addpratica_referenti_pratica = "SELECT DISTINCT " + 
			"cros_pra.`id_pratica` AS id_pratica,"+ 
			"cross_anag.`id_anagrafica` AS id_anagrafica,"+ 
			"cross_anag.`tipo_anagrafica` AS tipo_anagrafica,"+ 
			"cross_anag.cognome AS cognome,"+ 	
			"cross_anag.denominazione AS denominazione,"+ 
			"cross_anag.`nome` AS nome,"+ 
			"cross_anag.`codice_fiscale` AS codice_fiscale,"+ 
			"cross_ruolo.`descrizione` AS ruolo,"+ 
			"cross_anag.`partita_iva` AS partita_iva,"+ 
			"cross_anag.`flg_individuale` AS flg_individuale"+
			" FROM `opencross`.`pratica` cros_pra "+
			" LEFT OUTER JOIN `opencross`.`pratica_anagrafica` cross_pra_anag ON (cross_pra_anag.id_pratica = cros_pra.id_pratica)"+
			" LEFT OUTER JOIN `opencross`.`anagrafica` cross_anag ON (cross_anag.id_anagrafica = cross_pra_anag.id_anagrafica)"+
			" LEFT OUTER JOIN `opencross`.`lk_tipo_ruolo` cross_ruolo ON (cross_ruolo.id_tipo_ruolo = cross_pra_anag.id_tipo_ruolo)"+
			" WHERE cros_pra.identificativo_pratica = '?'";
	
	public static String addpratica_indirizzi_intervento = "SELECT DISTINCT "+
			"			`localita`,"+ 	
			"			`indirizzo`,"+ 
			"			`civico`,"+ 
			"			`cod_civico`,"+ 
			"			`cod_via`,"+ 
			"			`interno_numero`,"+ 
			"			`interno_lettera`,"+ 
			"			`interno_scala`,"+ 
			"			`lettera`,"+ 
			"			`latitudine`,"+ 
			"			`longitudine`,"+ 
			"			`piano`"+	 
			"		FROM `opencross`.`indirizzi_intervento` it"+
			"		WHERE it.id_pratica = ?";



}
