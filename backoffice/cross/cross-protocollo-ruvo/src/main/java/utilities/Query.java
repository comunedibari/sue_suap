package utilities;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Query {

    public static final String PROTOCOLLO_USERNAME = "avbari.protocollo.ws.username";
    
    public static final String PROTOCOLLO_PASSWORD = "avbari.protocollo.ws.password";
    
	private String sUserNameComune;

	private String sPasswordComune;
	
	public static String sqlUsernameAndPassword = "SELECT `name`, `value` FROM `opencross`.`plugin_configuration` pc"
			+ " WHERE (pc.name = 'avbari.protocollo.ws.username'"
			+ " OR pc.name = 'avbari.protocollo.ws.password')"
			+ " AND id_ente = ?";
	
	public String getsUserNameComune() {
		return sUserNameComune;
	}

	public void setsUserNameComune(String sUserNameComune) {
		this.sUserNameComune = sUserNameComune;
	}

	public String getsPasswordComune() {
		return sPasswordComune;
	}

	public void setsPasswordComune(String sPasswordComune) {
		this.sPasswordComune = sPasswordComune;
	}

	public int setUsernameAndPassword(int idEnte) {
		int result = 1;
		MySqlConnect mysqlConn = null;

		try {
			mysqlConn = new MySqlConnect();

			mysqlConn.connect();

			java.sql.Connection conn = mysqlConn.getConnection();
	
			PreparedStatement ps = conn.prepareStatement(sqlUsernameAndPassword);
			int index=1;
			ps.setInt (index++, idEnte);
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				if(rs.getString("name").toString().equals(PROTOCOLLO_USERNAME)) {
					this.setsUserNameComune(rs.getString("value").toString());
				}
				if(rs.getString("name").toString().equals(PROTOCOLLO_PASSWORD)) {
					this.setsPasswordComune(rs.getString("value").toString());
				}
			}
			rs.close();
			ps.close();
			result = 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			mysqlConn.disconnect();
	    }
		return result;
	}
	
	public static void main(String[] args) {
		Query q = new Query();
		
		// cf_232/2022/1234 Ricezione pratica
		String numProtocollo = "cf_232/2022/1234";
		String idUdProtocollo = ""; 
		int anno = 0, numRegistro = 0;
		String[] splitProtocollo = numProtocollo.split("/");
		idUdProtocollo = splitProtocollo[0]; // 004
		anno = Integer.parseInt(splitProtocollo[1]); // 034556
		numRegistro = Integer.parseInt(splitProtocollo[2]); // 034556
		
	    Pattern p = Pattern.compile("(\\d+)");
	    Matcher m = p.matcher(idUdProtocollo);
	    while(m.find())
	    {
	        System.out.println(m.group(1));
	    }
		
		int result = q.setUsernameAndPassword(2001);
		if(result == 0) {
			System.out.println("USERNAME SUE BARI:" + q.getsUserNameComune());
			System.out.println("PASSWORD SUE BARI:" + q.getsPasswordComune());
		}		
	}

}
