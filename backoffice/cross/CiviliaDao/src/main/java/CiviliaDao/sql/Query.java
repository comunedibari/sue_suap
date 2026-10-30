package CiviliaDao.sql;

import CiviliaDao.utilities.LogClass;

public class Query {

	private static LogClass logger;
	
	public static String getUTCDataTime = "SELECT CURRENT_TIMESTAMP as datatimezone";
	
	public static String getNotificheDaElaborare_limit_1 = 
			"SELECT t1.id, t1.codice_fiscale, t1.oggetto, t1.corpo, t1.stato, t1.domande_domanda_id, t1.data_ins, t1.data_invio, t1.servizio_mittente_io_id, t2.token " + 
			" FROM public.notifiche_io t1" + 
			" inner join public.servizi_mittente_io t2 on (t1.servizio_mittente_io_id = t2.id) where t1.stato = 'C' limit 1"; 
			
	public static String insertMsgLog ="INSERT INTO public.log_servizio_gio (STACKTRACE) VALUES (?)";

}
