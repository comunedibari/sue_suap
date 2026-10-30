package cross.cross_scripts.ave_151;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Ave151Constants {
	
	public static final int BARI_START_YEAR = 2019;
	
	public static final String SEPARATOR = "/";
	
	public static final String COPY_COMMAND = "cp -p";
	public static final String MOVE_COMMAND = "mv";
	public static final String CREATE_COMMAND = "mkdir -p ";

	public static final String CREATE_DOC_FOLDER = "documenti.fs.folder";
	public static final String CREATE_MAIL_FOLDER = "mail.folder";
	public static final String CREATE_TEMP_FOLDER = "attachment.tmp.folder";

	//select * from lk_comuni where descrizione = 'bari';
	public static final List<Integer> ID_COMUNI_BARI = Arrays.asList(893,895); 
	public static final Integer ID_COMUNE_BARI = 893; 
	
	public static final String MAX = ":MAX:";
	public static final String MIN = ":MIN:";
	public static final String NEW_PATH_FILE = ":NEW_PATH_FILE:";
	public static final String OLD_PATH_FILE = ":OLD_PATH_FILE:";
	public static final String PATH_FILE = ":PATH_FILE:";
	public static final String PAPERWORK_ID = ":PAPERWORK_ID:";
	public static final String ATTACHMENT_ID = ":ATTACHMENT_ID:";
	public static final String YEAR_CONDITION = ":YEAR_CONDITION:";
	public static final String TOWN_CONDITION = ":TOWN_CONDITION:";


	public static String QUERY_ATTACHMENT= "select a.id as id, a.path_file as pathFile "
			+ "FROM allegati a "
			+ "where a.id = "+ATTACHMENT_ID;

	public static String QUERY_ATTACHMENTS= "select a.id as id, a.path_file as pathFile "
			+ "FROM allegati a "
			+ "where a.path_file is not null AND a.id BETWEEN "+MIN+" AND "+MAX+" "
			+ "ORDER BY a.id desc";
	
	public static String QUERY_PAPERWORK_EVENT_FROM_ATTACHMENT= "select pe.id_pratica as id_pratica, pe.id_pratica_evento as id_pratica_evento, pe.data_evento as data_evento from  "
			+ "pratiche_eventi pe "
			+ "inner join pratiche_eventi_allegati pea on pe.id_pratica_evento = pea.id_pratica_evento "
			+ "inner join allegati a on pea.id_allegato = a.id "
			+ "where a.id ="+ATTACHMENT_ID + " "
			+ "order by pe.id_pratica_evento asc";
	
	public static String QUERY_PAPERWORK= "select p.id_pratica as id_pratica, lkc.descrizione as descrizione from  "
			+ "pratica p inner join lk_comuni lkc on p.id_comune = lkc.id_comune "
			+ "where p.id_pratica ="+PAPERWORK_ID;

	public static String QUERY_UPDATE_PATH= "update allegati "
			+ "set path_file='" + NEW_PATH_FILE + "' "
			+ "where id ="+ATTACHMENT_ID + " and path_file = '"+OLD_PATH_FILE+"'";
	
	public static List<String> paperworksColumns = new ArrayList<>();
	
	static {
		paperworksColumns.add("id_pratica");
	}
}
