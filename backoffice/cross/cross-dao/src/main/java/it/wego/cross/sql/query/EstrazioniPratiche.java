package it.wego.cross.sql.query;

public class EstrazioniPratiche {
	
	public static final String estazioneCilaSQL = "SELECT prt.id_pratica, prt.identificativo_pratica, sp.descrizione, prt.protocollo,  " + 
			"concat(u.cognome, ' ', u.nome) as in_carico_a, "+ 
			"(SELECT CONCAT(ut.cognome, ' ', ut.nome) "
			+ "FROM "
			+ "            opencross.pratiche_eventi pe, opencross.utente ut WHERE "
			+ "            id_pratica_evento = (SELECT MIN(id_pratica_evento)"
			+ "                FROM opencross.pratiche_eventi prt_ev,"
			+ "                    opencross.processi_eventi prc_ev WHERE"
			+ "                    prt_ev.id_pratica = prt.id_pratica"
			+ "                        AND prt.id_processo = prc_ev.id_processo"
			+ "                        AND prt_ev.id_evento = prc_ev.id_evento"
			+ "                        AND prc_ev.cod_evento = 'ASPRAT')"
			+ "                AND id_pratica = prt.id_pratica"
			+ "                and pe.id_utente = ut.id_utente) AS istruttore, " + 
			"prt.data_ricezione, prt.data_protocollazione  " + 
			"      ,(select MIN(data_evento)  " + 
			"          from opencross.pratiche_eventi prt_ev, opencross.processi_eventi prc_ev " + 
			"         where prt_ev.id_pratica = prt.id_pratica " + 
			"           and prt.id_processo = prc_ev.id_processo " + 
			"           and prt_ev.id_evento = prc_ev.id_evento " + 
			"           and prc_ev.cod_evento = 'COALRIAMM') as data_ric_integrazione " + 
			"     ,(select MIN(data_evento) " + 
			"          from opencross.pratiche_eventi prt_ev, opencross.processi_eventi prc_ev " + 
			"         where prt_ev.id_pratica = prt.id_pratica " + 
			"           and prt.id_processo = prc_ev.id_processo " + 
			"           and prt_ev.id_evento = prc_ev.id_evento " + 
			"           and prc_ev.cod_evento = 'SOSLAVAMM ') as data_sospensione " + 
			"      ,(select MIN(data_evento)  " + 
			"          from opencross.pratiche_eventi prt_ev, opencross.processi_eventi prc_ev " + 
			"         where prt_ev.id_pratica = prt.id_pratica " + 
			"           and prt.id_processo = prc_ev.id_processo " + 
			"           and prt_ev.id_evento = prc_ev.id_evento " + 
			"           and prc_ev.cod_evento = 'COMPARCONAMM') as data_parere_contrario, " + 
			"		prt.data_chiusura		    " + 
			"FROM opencross.pratica prt, opencross.utente u, opencross.lk_stato_pratica sp, opencross.procedimenti_enti proc_e " + 
			"where UPPER(oggetto_pratica) like '%CILA%' " + 
			"and prt.id_utente=u.id_utente " + 
			"and prt.id_proc_ente = proc_e.id_proc_ente " +
			"and prt.id_stato_pratica = sp.id_stato_pratica " +
			"and proc_e.id_ente = ? " +
			"and prt.data_ricezione between ? and ? " + 
			"order by prt.data_ricezione; ";
	
	public static final String estazioneSciaSQL = "SELECT prt.id_pratica, prt.identificativo_pratica, sp.descrizione, prt.protocollo, concat(u.cognome, ' ', u.nome) as in_carico_a, "+
	        "		(SELECT CONCAT(ut.cognome, ' ', ut.nome) " + 
	        "        FROM opencross.pratiche_eventi pe, opencross.utente ut " + 
	        "        WHERE id_pratica_evento = (SELECT MIN(id_pratica_evento) " + 
	        "                FROM opencross.pratiche_eventi prt_ev, opencross.processi_eventi prc_ev " + 
	        "                WHERE prt_ev.id_pratica = prt.id_pratica " + 
	        "                  AND prt.id_processo = prc_ev.id_processo " + 
	        "                  AND prt_ev.id_evento = prc_ev.id_evento " + 
	        "                  AND prc_ev.cod_evento = 'ASPRAT')" + 
	        "                AND id_pratica = prt.id_pratica " + 
	        "                and pe.id_utente = ut.id_utente) AS istruttore, " +
			"		prt.data_ricezione, prt.data_protocollazione " + 
			"      ,(select MIN(data_evento) " + 
			"          from opencross.pratiche_eventi prt_ev, opencross.processi_eventi prc_ev " + 
			"         where prt_ev.id_pratica = prt.id_pratica " + 
			"           and prt.id_processo = prc_ev.id_processo " + 
			"           and prt_ev.id_evento = prc_ev.id_evento " + 
			"           and prc_ev.cod_evento = 'COALRIAMM') as data_ric_integrazione " + 
			"      ,(select MIN(data_evento) " + 
			"          from opencross.pratiche_eventi prt_ev, opencross.processi_eventi prc_ev " + 
			"         where prt_ev.id_pratica = prt.id_pratica " + 
			"           and prt.id_processo = prc_ev.id_processo " + 
			"           and prt_ev.id_evento = prc_ev.id_evento " + 
			"           and prc_ev.cod_evento = 'SOSLAVAMM') as data_divieto " + 
			"      ,(select MiN(data_evento) " + 
			"          from opencross.pratiche_eventi prt_ev, opencross.processi_eventi prc_ev " + 
			"         where prt_ev.id_pratica = prt.id_pratica " + 
			"           and prt.id_processo = prc_ev.id_processo " + 
			"           and prt_ev.id_evento = prc_ev.id_evento " + 
			"           and prc_ev.cod_evento = 'RICEINT' " + 
			"          ) as data_Integrazione " + 
			"      ,(select MIN(data_evento) " + 
			"          from opencross.pratiche_eventi prt_ev, opencross.processi_eventi prc_ev " + 
			"         where prt_ev.id_pratica = prt.id_pratica " + 
			"           and prt.id_processo = prc_ev.id_processo " + 
			"           and prt_ev.id_evento = prc_ev.id_evento " + 
			"           and prc_ev.cod_evento = 'ESPOVE') as data_parere_favorevole, " + 
			"           (select MIN(data_evento) " + 
			"          from opencross.pratiche_eventi prt_ev, opencross.processi_eventi prc_ev " + 
			"         where prt_ev.id_pratica = prt.id_pratica " + 
			"           and prt.id_processo = prc_ev.id_processo " + 
			"           and prt_ev.id_evento = prc_ev.id_evento " + 
			"           and prc_ev.cod_evento = 'COMPARCONAMM') as data_parere_contrario, " + 
			"           prt.data_chiusura " + 
			"FROM opencross.pratica prt, opencross.utente u, opencross.lk_stato_pratica sp, opencross.procedimenti_enti proc_e  " + 
			"where UPPER(prt.oggetto_pratica) like '%SCIA%'  " + 
			"and prt.id_utente=u.id_utente " + 
			"and prt.id_proc_ente = proc_e.id_proc_ente " +
			"and prt.id_stato_pratica = sp.id_stato_pratica " +
			"and proc_e.id_ente = ? " +
			"and prt.data_ricezione between ? and ? " + "group by id_pratica " +
			"order by prt.data_ricezione";

	public static final String estrazionePdcSQL = "SELECT prt.id_pratica, prt.identificativo_pratica, sp.descrizione, prt.protocollo, concat(u.cognome, ' ', u.nome) as in_carico_a ,"+
			"		 (SELECT CONCAT(ut.cognome, ' ', ut.nome) " + 
			"        FROM " + 
			"            opencross.pratiche_eventi pe," + 
			"            opencross.utente ut" + 
			"        WHERE " + 
			"            id_pratica_evento = (SELECT " + 
			"                    MIN(id_pratica_evento)" + 
			"                FROM " + 
			"                    opencross.pratiche_eventi prt_ev," + 
			"                    opencross.processi_eventi prc_ev" + 
			"                WHERE " + 
			"                    prt_ev.id_pratica = prt.id_pratica" + 
			"                        AND prt.id_processo = prc_ev.id_processo" + 
			"                        AND prt_ev.id_evento = prc_ev.id_evento" + 
			"                        AND prc_ev.cod_evento = 'ASPRAT')" + 
			"                AND id_pratica = prt.id_pratica" + 
			"                AND pe.id_utente = ut.id_utente) AS istruttore, " +
			"		prt.data_ricezione, prt.data_protocollazione " + 
			"      ,(select MIN(data_evento) " + 
			"          from opencross.pratiche_eventi prt_ev, opencross.processi_eventi prc_ev " + 
			"         where prt_ev.id_pratica = prt.id_pratica " + 
			"           and prt.id_processo = prc_ev.id_processo " + 
			"           and prt_ev.id_evento = prc_ev.id_evento " + 
			"           and prc_ev.cod_evento = 'COMDINAMM') as data_avvio_pre_diniego " + 
			"     ,(select MIN(data_evento) " + 
			"          from opencross.pratiche_eventi prt_ev, opencross.processi_eventi prc_ev " + 
			"         where prt_ev.id_pratica = prt.id_pratica " + 
			"           and prt.id_processo = prc_ev.id_processo " + 
			"           and prt_ev.id_evento = prc_ev.id_evento " + 
			"           and prc_ev.cod_evento = 'RICCDAMM ') as data_rice_contrDeduz_pre_diniego " + 
			"      ,(select MIN(data_evento)  " + 
			"          from opencross.pratiche_eventi prt_ev, opencross.processi_eventi prc_ev " + 
			"         where prt_ev.id_pratica = prt.id_pratica " + 
			"           and prt.id_processo = prc_ev.id_processo " + 
			"           and prt_ev.id_evento = prc_ev.id_evento " + 
			"           and prc_ev.cod_evento = 'RICHINTA20AMM') as data_rich_integr_art20  " + 
			"      ,(select MIN(data_evento) " + 
			"          from opencross.pratiche_eventi prt_ev, opencross.processi_eventi prc_ev " + 
			"         where prt_ev.id_pratica = prt.id_pratica " + 
			"           and prt.id_processo = prc_ev.id_processo " + 
			"           and prt_ev.id_evento = prc_ev.id_evento " + 
			"           and prc_ev.cod_evento = 'RICEINTA20') as data_rice_integr_art20  " + 
			"      ,(select MIN(data_evento)  " + 
			"          from opencross.pratiche_eventi prt_ev, opencross.processi_eventi prc_ev " + 
			"         where prt_ev.id_pratica = prt.id_pratica " + 
			"           and prt.id_processo = prc_ev.id_processo " + 
			"           and prt_ev.id_evento = prc_ev.id_evento " + 
			"           and prc_ev.cod_evento = 'RICHARPAMM') as data_rich_adempimenti  " + 
			"      ,(select MIN(data_evento) " + 
			"          from opencross.pratiche_eventi prt_ev, opencross.processi_eventi prc_ev " + 
			"         where prt_ev.id_pratica = prt.id_pratica " + 
			"           and prt.id_processo = prc_ev.id_processo " + 
			"           and prt_ev.id_evento = prc_ev.id_evento " + 
			"           and prc_ev.cod_evento = 'RICEARP ') as data_rice_adempimenti  " + 
			"      ,(select MIN(data_evento) " + 
			"          from opencross.pratiche_eventi prt_ev, opencross.processi_eventi prc_ev " + 
			"         where prt_ev.id_pratica = prt.id_pratica " + 
			"           and prt.id_processo = prc_ev.id_processo " + 
			"           and prt_ev.id_evento = prc_ev.id_evento " + 
			"           and prc_ev.cod_evento = 'RILPDCAMM') as data_rilascio  " + 
			"      ,(select MIN(data_evento) " + 
			"          from opencross.pratiche_eventi prt_ev, opencross.processi_eventi prc_ev " + 
			"         where prt_ev.id_pratica = prt.id_pratica " + 
			"           and prt.id_processo = prc_ev.id_processo " + 
			"           and prt_ev.id_evento = prc_ev.id_evento " + 
			"           and prc_ev.cod_evento = 'DINDEFAMM') as data_diniego_def, " + 
			"		prt.data_chiusura		    " + 
			"FROM opencross.pratica prt, opencross.utente u, opencross.lk_stato_pratica sp, opencross.procedimenti_enti proc_e  " + 
			"where UPPER(oggetto_pratica) like '%PERMESSO%' " + 
			"and prt.id_processo = ? " + 
			"and prt.id_utente=u.id_utente " + 
			"and prt.id_proc_ente = proc_e.id_proc_ente " +
			"and prt.id_stato_pratica = sp.id_stato_pratica " +
			"and proc_e.id_ente = ? " +
			"and prt.data_ricezione between ? and ? " + 
			"group by id_pratica " +
			"order by prt.data_ricezione";	
	
	public static final String estrazioneAgibSQL = "SELECT prt.id_pratica, prt.identificativo_pratica, sp.descrizione, prt.protocollo, concat(u.cognome, ' ', u.nome) as in_carico_a, " + 
			"		(SELECT CONCAT(ut.cognome, ' ', ut.nome) " + 
			"        FROM opencross.pratiche_eventi pe, " + 
			"            opencross.utente ut " + 
			"        WHERE id_pratica_evento = (SELECT MIN(id_pratica_evento) " + 
			"                FROM opencross.pratiche_eventi prt_ev, " + 
			"                    opencross.processi_eventi prc_ev " + 
			"                WHERE prt_ev.id_pratica = prt.id_pratica" + 
			"                        AND prt.id_processo = prc_ev.id_processo" + 
			"                        AND prt_ev.id_evento = prc_ev.id_evento" + 
			"                        AND prc_ev.cod_evento = 'ASPRAT')" + 
			"                AND id_pratica = prt.id_pratica" + 
			"                AND pe.id_utente = ut.id_utente) AS istruttore, " + 
			" 		prt.data_ricezione, prt.data_protocollazione  " + 
			"      ,(select MIN(data_evento)  " + 
			"          from opencross.pratiche_eventi prt_ev, opencross.processi_eventi prc_ev " + 
			"         where prt_ev.id_pratica = prt.id_pratica " + 
			"           and prt.id_processo = prc_ev.id_processo " + 
			"           and prt_ev.id_evento = prc_ev.id_evento " + 
			"           and prc_ev.cod_evento = 'COALRIAMM') as data_ric_integrazione " + 
			"      ,(select MIN(data_evento) " + 
			"          from opencross.pratiche_eventi prt_ev, opencross.processi_eventi prc_ev " + 
			"         where prt_ev.id_pratica = prt.id_pratica " + 
			"           and prt.id_processo = prc_ev.id_processo " + 
			"           and prt_ev.id_evento = prc_ev.id_evento " + 
			"           and prc_ev.cod_evento = 'RICEINT') as data_Integrazione " + 
			"      ,(select MAX(data_evento) " + 
			"          from opencross.pratiche_eventi prt_ev, opencross.processi_eventi prc_ev " + 
			"         where prt_ev.id_pratica = prt.id_pratica " + 
			"           and prt.id_processo = prc_ev.id_processo " + 
			"           and prt_ev.id_evento = prc_ev.id_evento " + 
			"           and prc_ev.cod_evento = 'ESPOVE') as data_parere_positivo, " + 
			"           (select MAX(data_evento) " + 
			"          from opencross.pratiche_eventi prt_ev, opencross.processi_eventi prc_ev " + 
			"         where prt_ev.id_pratica = prt.id_pratica " + 
			"           and prt.id_processo = prc_ev.id_processo " + 
			"           and prt_ev.id_evento = prc_ev.id_evento " + 
			"           and prc_ev.cod_evento = 'COMPARCONPOS') as data_parere_contrario, " + 
			"		   prt.data_chiusura " + 
			"FROM opencross.pratica prt, opencross.utente u, opencross.lk_stato_pratica sp, opencross.procedimenti_enti proc_e  " + 
			"where UPPER(oggetto_pratica) like '%AGIB%'  " + 
			"and prt.id_utente=u.id_utente " + 
			"and prt.id_proc_ente = proc_e.id_proc_ente " +
			"and prt.id_stato_pratica = sp.id_stato_pratica " +
			"and proc_e.id_ente = ? " +
			"and prt.data_ricezione between ? and ? " + 
			"group by id_pratica " +
			"order by prt.data_ricezione";
	
}
