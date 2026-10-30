package cross.BonificaAnagrafica.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import it.wego.cross.civilia.config.ConfigException;
import it.wego.cross.civilia.utilities.LogClass;

public class DBConnect {
    //private static Logger logger2 = Logger.getLogger(DBConnect.class.getName());
	private static LogClass logger;

	private Connection conn = null;
   
	public Dao dao;
	
	
    public DBConnect() throws ConfigException, SQLException {
    }

    public Connection getConn() {
        return this.conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }
    
    public void getDBParameters(Connection conn) throws Exception{

        Statement stmt;
        String msg = "";

        try {        	
            stmt = conn.createStatement();

            String selezione_parametri = "SELECT * FROM public.configurazione ORDER BY parametro ASC ";

            ResultSet rows = stmt.executeQuery(selezione_parametri);

            int count=0;
            while (rows.next()) {
                count+=1;

                String sKey = rows.getString("parametro");
                String sValue = rows.getString("valore");

                msg = "Row #:" + count + " key:" + sKey + " Value:" + sValue;
                LogClass.trace(msg);

/*                if(sKey.compareTo("{sad_url}") == 0) {
                    this.setWs_url(sValue);
                }
                
                if(sKey.compareTo("{sad_token}") == 0) {
                    this.setWs_token(sValue);
                }
                                
                if(sKey.compareTo("{sad_testo_notifica}") == 0) {
                	ParserJson parse = new ParserJson();
                	parse.parseTesto(sValue);

                	this.setsPecOggetto(parse.get_oggetto());
                	this.setsPecCorpo(parse.get_corpo());                    
                }           
                
*/            }
            rows.close();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
			//Main.setTakeOffThreadGio();
        }
    }
}
