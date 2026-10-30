package it.wego.cross.mail;

public class MailQuery {
	public static String query = "SELECT 	 "
			+ "	c.`id_ente`,  "
			+ "	ec.`id_comune`,  "
			+ "	e.descrizione AS descrizione_ente, "
			+ "	lkc.descrizione AS descrizione_comune, "
			+ "	c.`value`,  "
			+ "	c.`note`	  "
			+ "FROM ((((`opencross`.`configuration` c ) "
			+ "JOIN `opencross`.`enti` e) "
			+ "JOIN opencross.enti_comuni ec) "
			+ "JOIN opencross.lk_comuni lkc) "
			+ "WHERE ((e.id_ente = c.id_ente) "
			+ "AND (ec.id_ente = e.id_ente) "
			+ "AND (lkc.id_comune = ec.id_comune)) "
			+ "AND c.name IN ('mail.host',  "
			+ "'mail.imap.host', "
			+ "'mail.username', "
			+ "'mail.password', "
			+ "'mail.imap.port', "
			+ "'abilita.lettura.email', "
			+ "'mail.config.cartellaCross', "
			+ "'mail.config.cartellaLette')  "
			+ "-- AND ec.id_comune = 893 "
			+ "AND c.value IS NOT NULL "
			+ "AND c.value <> '' "
			+ "AND e.descrizione LIKE 'SUE%' "
			+ "ORDER BY ec.id_comune ";
	
	/**
	 * @return the query
	 */
	public static String getQuery() {
		return query;
	}

}
