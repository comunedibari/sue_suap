package cross.BonificaAnagrafica.sql;

public class Query {
	
	public static String getUTCDataTime = "SELECT CURRENT_TIMESTAMP as datatimezone";
	
	public static String delete_ruoli_cross = "TRUNCATE TABLE  @.`ruoli_cross_delete`";
	public static String delete_anagrafica_cross = "TRUNCATE TABLE @.`anagrafica_cross_delete`";
	public static String delete_anagrafica_cross_insert = "TRUNCATE TABLE @.`anagrafica_cross_insert`";

	public static String check_for_delete_ruoli_pratica_anagrafica = 
			" SELECT COUNT(1) AS conta_delete FROM @.`ruoli_cross_delete` pa "+ 
			" WHERE	pa.`pratica_id` = ? "+
			" AND 	pa.`anagrafica_id_cross` = ?";
			
	public static String select_nr_pratiche_civilia =
	" SELECT COUNT(1) nr_pratiche " +
	" FROM civilia_db.pratica p"+
	" WHERE p.thread_id = ?";
	
	public static String select_pratiche_civilia_old = 
		" SELECT DISTINCT " + 
		" civ_pra.identificativo_pratica AS identificativo_pratica, thread_id" + 
		" FROM `opencross`.`pratica` cros_pra " + 
		" INNER JOIN @.`pratica` civ_pra ON (cros_pra.identificativo_pratica = civ_pra.codice)" + 
		" where civ_pra.thread_id = ?";

	public static String tmp_select_pratiche_civilia = 
			" SELECT DISTINCT " + 
			" civ_pra.id AS pratica_id, " + 
			" civ_pra.codice AS identificativo_pratica, " + 
			" civ_pra.thread_id AS thread_id" + 
			" FROM @.`pratica` civ_pra " + 
			" WHERE civ_pra.codice = 'CIL-1355-2017'"
			+ " AND civ_pra.thread_id = ?";

	public static String select_pratiche_civilia = 
			" SELECT DISTINCT " + 
			" civ_pra.id AS pratica_id, " + 
			" civ_pra.codice AS identificativo_pratica, " + 
			" civ_pra.thread_id AS thread_id" + 
			" FROM @.`pratica` civ_pra " + 
			" WHERE civ_pra.thread_id = ?";

	public static String select_pratica_cross = 
			"SELECT `id_pratica` FROM @.`pratica` WHERE `identificativo_pratica` = '?'";
	
	public static String check_ruolociv_non_applicabile =
			"SELECT COUNT(1) AS ruolo_na FROM @.`ruoli_etichetta_civilia_cross` r WHERE " + 
			"	`ruolo_etichetta_civilia_cross` = '?' AND `ruolo_cross` = 'Non Applicabile'";
	
	public static String check_esistenza_anagrafica_cross_F = 
			"SELECT COUNT(1) AS anag_cross_esistente FROM @.`anagrafica` WHERE `codice_fiscale` = '?'";

	public static String check_esistenza_anagrafica_cross_G = 
			"SELECT COUNT(1) AS anag_cross_esistente FROM @.`anagrafica` WHERE `partita_iva` = '?'";

	public static String get_id_anagrafica_cross_F = 
			"SELECT id_anagrafica FROM @.`anagrafica` WHERE `codice_fiscale` = '?' limit 1";
	
	public static String get_id_anagrafica_cross_G = 
			"SELECT id_anagrafica FROM @.`anagrafica` WHERE `partita_iva` = '?' limit 1";

	public static String select_anagrafiche_cross = 
		"SELECT DISTINCT " + 
		"	cross_anag.`tipo_anagrafica` AS tipo_anagrafica_cross, " + 
		"	cross_anag.`denominazione` AS denominazione, " + 
		"	cross_anag.`cognome` AS cognome_cross, " + 
		"	cross_anag.`nome` AS nome_cross, " + 
		"	cross_anag.`codice_fiscale` AS codice_fiscale_cross, " + 
		"	cross_ruolo.`descrizione` AS ruolo_cross, " + 
		"	cross_anag.`partita_iva` AS partita_iva_cross, " + 
		"	cross_anag.`flg_individuale` AS flg_individuale_cross" + 
		"	FROM @.`pratica` cros_pra " + 
		"	LEFT OUTER JOIN @.`pratica_anagrafica` cross_pra_anag ON (cross_pra_anag.id_pratica = cros_pra.id_pratica)" + 
		"	LEFT OUTER JOIN @.`anagrafica` cross_anag ON (cross_anag.id_anagrafica = cross_pra_anag.id_anagrafica)" + 
		"   LEFT OUTER JOIN @.`lk_tipo_ruolo` cross_ruolo ON (cross_ruolo.id_tipo_ruolo = cross_pra_anag.id_tipo_ruolo)"+
		" WHERE cros_pra.identificativo_pratica = ? ORDER BY cross_anag.`codice_fiscale`,cross_anag.`partita_iva`";
	
	public static String select_anagrafiche_cross_eventi = 
			" SELECT DISTINCT " + 
			"	cross_pra.id_pratica AS id_pratica," + 
			"	cross_pra_anag.id_anagrafica AS id_anagrafica," + 
			" 	cross_pra_anag.id_tipo_ruolo AS id_tipo_ruolo," +
			"   cross_anag.tipo_anagrafica AS tipo_anagrafica_cross," +
			"	cross_anag.`denominazione` AS denominazione, " + 
			" (SELECT COUNT(1) FROM @.`pratiche_eventi` cross_ev " + 
			" LEFT OUTER JOIN @.`pratiche_eventi_anagrafiche` cross_ea ON (cross_ev.id_pratica_evento = cross_ea.id_pratica_evento)" + 
			" WHERE cross_ev.id_pratica = cross_pra.id_pratica " + 
			" AND  cross_anag.id_anagrafica = cross_ea.id_anagrafica " + 
			" AND LOWER(cross_ev.descrizione_evento) LIKE '%collegamento nuova anagrafica%') AS nuova_anagrafica," + 
			"	cross_anag.`cognome` AS cognome_cross," + 
			"	cross_anag.`nome` AS nome_cross," + 
			"	cross_anag.`codice_fiscale` AS codice_fiscale_cross," + 
			"	cross_ruolo.`descrizione` AS ruolo_cross, " + 
			"	cross_anag.`partita_iva` AS partita_iva_cross," + 
			"	cross_anag.`flg_individuale` AS flg_individuale_cross" + 
			"	FROM @.`pratica` cross_pra " + 
			"	LEFT OUTER JOIN @.`pratica_anagrafica` cross_pra_anag ON (cross_pra_anag.id_pratica = cross_pra.id_pratica)" + 
			"	LEFT OUTER JOIN @.`lk_tipo_ruolo` cross_ruolo ON (cross_ruolo.id_tipo_ruolo = cross_pra_anag.id_tipo_ruolo)" + 
			"	LEFT OUTER JOIN @.`anagrafica` cross_anag ON (cross_anag.id_anagrafica = cross_pra_anag.id_anagrafica)" + 
			" WHERE cross_pra.identificativo_pratica = ?";
	
	public static String select_anagrafiche_civilia = 
			" SELECT DISTINCT " + 
			"	civ_anag.id_anagrafica," +
			"	civ_anag.tipoAnagrafica AS tipoAnagrafica_civ," + 
			"	civ_anag.cognome AS cognome_civ," + 
			"	civ_anag.nome AS nome_civ," + 
			"	civ_anag.codiceFiscale AS codiceFiscale_civ," + 
			"	civ_anag.partitaIva AS partitaIva_civ," + 
			"	civ_anag.denominazione AS ruolo_civ, " +
			" 	civ_anag.sesso as sesso" +
			" FROM @.anagrafica_civilia civ_anag " + 
			" WHERE civ_anag.pratica_id = ? ORDER BY civ_anag.codiceFiscale, civ_anag.partitaIva"; 
	
	public static String insert_anagrafica_cross = "INSERT INTO @.`anagrafica_cross_insert`" + 
			"	(`pratica_id`, `anagrafica_id_civ`, `anagrafica_id_cross`) " + 
			"	VALUES (?, ?,?)";
	
	public static String select_ruolo_etichetta_cross =
	" SELECT `id`,`ruolo_etichetta_civilia_cross`,`ruolo_cross` " +
	" FROM @.`ruoli_etichetta_civilia_cross` r1 " +
	" WHERE ruolo_etichetta_civilia_cross = '?' " + 
	" AND ruolo_cross = (SELECT descrizione FROM @.lk_tipo_ruolo WHERE id_tipo_ruolo = ? LIMIT 1)";
	
	public static String select_ruolo_etichetta =
			" SELECT `id`,`ruolo_etichetta_civilia_cross`,`ruolo_cross` " +
			" FROM @.`ruoli_etichetta_civilia_cross` r1 " +
			" WHERE ruolo_etichetta_civilia_cross = '?' AND `ruolo_cross` != 'Non Applicabile'"; 
	
	public static String select_ruolo_cross_civilia =
			" SELECT id_tipo_ruolo" +
			" FROM @.`lk_tipo_ruolo` r1 " +
			" WHERE descrizione = '?'"; 
	
	public static String select_confronta_ruoli_cross =
		"SELECT " +
		" cross_ruolo_attuale.id_tipo_ruolo AS id_tipo_ruolo_attuale," +
		" cross_ruolo_attuale.descrizione AS descrizione_attuale," +
		" cross_ruolo_civilia.id_tipo_ruolo AS id_tipo_ruolo_civilia," +
		" cross_ruolo_civilia.descrizione AS descrizione_civilia" +
		" FROM @.`lk_tipo_ruolo` cross_ruolo_attuale " +
		" JOIN ( " +
		"	SELECT id_tipo_ruolo, descrizione " +
		"	FROM @.`lk_tipo_ruolo` lk " + 
		"   INNER JOIN ruoli_etichetta_civilia_cross c ON (c.ruolo_etichetta_civilia_cross = lk.descrizione) " + 
		" WHERE lk.id_tipo_ruolo  = ? "+	
		") cross_ruolo_civilia " +
		" WHERE cross_ruolo_attuale.id_tipo_ruolo = ?";
	
	public static String select_ruoli_cross_civilia =
		" SELECT id_tipo_ruolo, descrizione " + 
		" FROM opencross.`lk_tipo_ruolo` lk " + 
		" WHERE lk.descrizione = '?'";
	
	public static String select_ruolo_etichetta_non_applicabile = 
			" SELECT count(1) AS ruolo_non_applicabile " +
			" FROM @.`pratica_anagrafica` cross_pra_anag" + 
			" INNER JOIN @.`ruoli_etichetta_civilia_cross` ruoli_cross_na ON "+
			" (ruoli_cross_na.id = cross_pra_anag.id_etichetta AND ruoli_cross_na.ruolo_cross = 'Non applicabile')" + 
			" WHERE cross_pra_anag.id_pratica = ? AND cross_pra_anag.id_anagrafica = ?  limit 1"; 
	
	public static String select_ruolo_cross = "SELECT `id_tipo_ruolo` FROM @.`lk_tipo_ruolo` WHERE descrizione = '?'";
			

	public static String update_cross_pratica_anagrafica = 
			" UPDATE @.`pratica_anagrafica`" + 
			" SET `id_etichetta` = ?," + 
			"     `id_tipo_ruolo` = ?" + 
			" WHERE `id_pratica` = ? AND `id_anagrafica` = ? AND `id_tipo_ruolo` = ?";
	
	public static String delete_cross_pratica_anagrafica = 
			" DELETE from @.`pratica_anagrafica`" + 
			" WHERE `id_pratica` = ?";
//	" WHERE `id_pratica` = ? AND `id_anagrafica` = ?";
	
	public static String update_cross_pratica_anagrafica_etichetta = 
			" UPDATE @.`pratica_anagrafica`" + 
			" SET `id_etichetta` = ?" + 			
			" WHERE `id_pratica` = ? AND `id_anagrafica` = ? AND `id_tipo_ruolo` = ?";
	
	public static String check_for_update_cross_pratica_anagrafica = 
	" SELECT COUNT(1) AS ruolo_gia_esistente FROM @.`pratica_anagrafica` pa "+ 
	" WHERE	pa.`id_pratica` = ? "+
	" AND 	pa.`id_anagrafica` = ? "+ 
	" AND 	pa.`id_tipo_ruolo` = ?";
	
	public static String check_for_insert_cross_pratica_anagrafica = 
			" SELECT COUNT(1) AS conta_anag_piu_ruoli FROM @.`pratica_anagrafica` pa "+ 
			" WHERE	pa.`id_pratica` = ? "+
			" AND 	pa.`id_anagrafica` = ?";
	
	public static String insert_pratica_anagrafica_update = "INSERT INTO @.`pratica_anagrafica_update` "
			+ " (`id_pratica`, `id_anagrafica`, `id_etichetta`, `id_ruolo`) VALUES (?, ?, ?, ?)"; 
			
	public static String check_pratica_anagrafica = "SELECT count(1) AS esistenza_upd FROM @.`pratica_anagrafica_update` "
			+ " WHERE `id_pratica` = ? AND `id_anagrafica` = ? AND `id_etichetta` = ? AND `id_ruolo` = ?";

	
	public static String insertMsgLog ="INSERT INTO @.log_bonifica (stacktrace) VALUES (?)";

}
