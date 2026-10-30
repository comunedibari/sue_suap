package CiviliaDao.utilities;

import java.net.*;
import java.sql.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.io.*;
import java.util.*;


public class UtilityRestClientClass {

	//static final Logger logger = Logger.getLogger(httpPostRestClientClass.class.getName());

	//private static LogClass logger;
	HashMap<String, String> map;
	
	public LogClass logger;
	
	/**
	 * @return the logger
	 */
	public LogClass getLogger() {
		return logger;
	}

	/**
	 * @param logger the logger to set
	 */
	public void setLogger(LogClass logger) {
		this.logger = logger;
	}

	public final static String LOG4J_CONFIG_PATH = "log4j.properties";

	private Connection getConn() {
		return conn;
	}

	private void setConn(Connection conn) {
		this.conn = conn;
	}

	private Connection conn;

	private String sUserID;

	private String sPassword;

	private String url;

	private String Resource;
	
	private String tokenKey;

	private String tokenValue;
	
	private String encrKey;
	
	private String access_token;

	private String refresh_token;

	private String token_type;

	private int expires_in;

	private String getAccess_token() {
		return access_token;
	}

	private void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	private String getRefresh_token() {
		return refresh_token;
	}

	private void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}

	private String getToken_type() {
		return token_type;
	}

	private void setToken_type(String token_type) {
		this.token_type = token_type;
	}

	private int getExpires_in() {
		return expires_in;
	}

	private void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}

	private String getsUserID() {
		return sUserID;
	}

	private void setsUserID(String sUserID) {
		this.sUserID = sUserID;
	}

	private String getsPassword() {
		return sPassword;
	}

	private void setsPassword(String sPassword) {
		this.sPassword = sPassword;
	}

	private String getEncrKey() {
		return encrKey;
	}

	private void setEncrKey(String encrKey) {
		this.encrKey = encrKey;
	}

	private String getResource() {
		return Resource;
	}

	private void setResource(String resource) {
		Resource = resource;
	}
		
	private String getUrl() {
		return url;
	}

	private void setUrl(String url) {
		this.url = url;
	}

	private String getTokenKey() {
		return tokenKey;
	}

	private void setTokenKey(String tokenKey) {
		this.tokenKey = tokenKey;
	}

	private String getTokenValue() {
		return tokenValue;
	}

	private void setTokenValue(String tokenValue) {
		this.tokenValue = tokenValue;
	}
	
	private static HttpURLConnection getURLConnection(String urlToConnect) throws IOException {
		URL url = new URL(urlToConnect);
		HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
		return httpUrlConnection;
	}

	private static void setHeaders(HttpURLConnection httpUrlConnection, Map<String, String> headers) {
		for (String headerKey : headers.keySet()) {
			httpUrlConnection.setRequestProperty(headerKey, headers.get(headerKey));
		}
	}
	private void ReadingArrayFromJSON (String json) {
		JSONParser jsonParser = new JSONParser();
		try {
			JSONObject jsonObject = (JSONObject) jsonParser.parse(json);

			this.setToken_type(jsonObject.get("token_type").toString());
			this.setAccess_token(jsonObject.get("access_token").toString());
			this.setTokenValue(this.getToken_type() + " " + this.getAccess_token());
			Integer iExpires_in = new Integer(jsonObject.get("expires_in").toString());
			this.setExpires_in(iExpires_in.intValue());

		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private int aggiorna_token() {
		int retCode = 0;
		Connection conn = this.getConn();
		String updateTokenValue="UPDATE psc_parametri set prm_val = ? where prm_cod = 'registroimprese_tokenValue'";
		PreparedStatement stmt = null;
		try {
			String aggiornaToken = this.getToken_type() + " " + this.getAccess_token();
			this.setTokenValue(aggiornaToken);
			stmt = conn.prepareStatement(updateTokenValue);
			stmt.setString(1, aggiornaToken);
			logger.info("aggiorna_token : "+ aggiornaToken);

			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.info("SQLException in aggiorna_token: "+ e.getMessage());
			retCode = e.getErrorCode();
		}
		return retCode;
	}
	private int aggiorna_encrKey() {
		int retCode = 0;
		Connection conn = this.getConn();
		String sqlUpdate="UPDATE psc_parametri set prm_val = ? where prm_cod = 'registroimprese_encrKey'";
		PreparedStatement stmt = null;
		try {
			String aggiornaVal = this.getEncrKey();
			this.setEncrKey(aggiornaVal);
			stmt = conn.prepareStatement(sqlUpdate);
			stmt.setString(1, aggiornaVal);

			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			retCode = e.getErrorCode();
		}
		return retCode;
	}

	private void getTokenAccess() throws IOException {
		String encoding = null;

		URL obj = null;
		HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
		postConnection.setRequestMethod("POST");
		postConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		postConnection.setRequestProperty("Authorization", "Basic " + encoding);
		// Send post request
		postConnection.setDoOutput(true);

		logger.info("POST_PARAMS:");
		
		postConnection.setDoOutput(true);
		Map<String,Object> params = new LinkedHashMap<String, Object>();
		params.put("grant_type", "client_credentials");
		StringBuilder postData = new StringBuilder();
        for (Map.Entry<String,Object> param : params.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");

		postConnection.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
		postConnection.getOutputStream().write(postDataBytes);

        Reader in = new BufferedReader(new InputStreamReader(postConnection.getInputStream(), "UTF-8"));

		int responseCode = postConnection.getResponseCode();

		String response = null;
		if (responseCode == HttpURLConnection.HTTP_OK) { //success

			int length_array_buffer_output = 0;
			char[] tmp_respons_array = new char[10000];
			for (int c; (c = in.read()) >= 0;length_array_buffer_output++) {
				tmp_respons_array[length_array_buffer_output] = (char)c;
			}
			char[] respons_array = new char[length_array_buffer_output];
			for (int x=0; x<length_array_buffer_output;x++) {
				respons_array[x] = tmp_respons_array[x];
			}
			response = String.valueOf(respons_array);

			this.ReadingArrayFromJSON(response);
			int rc = this.aggiorna_token();
			if(rc != 0) {
				throw new IOException();
			}

			logger.info("POST Response Message : " + response);
			
			postConnection.disconnect();
		} else {
			logger.info("Il servizio di richiesta token di accesso non funziona! Riprovare piu' tardi");
			System.exit(-5);
		}
	}

	private String call_httpPostRestClient(String pInput) throws Exception {

		int retCode = 0;
		String output = "", result= ""; 

		String url = "urlToConnect";
		HttpURLConnection conn = null;

		for(int j = 0; j<2; j++) {
			try {
				url = this.getUrl() + "/" + this.getResource() + "/false/" + pInput + "/" + this.getEncrKey();
				String tokenKey = this.getTokenKey();
				String tokenValue = this.getTokenValue();

				logger.info("call_httpPostRestClient = " + url.toString());

				conn = getURLConnection(url);
				Map<String, String> headers = new HashMap<String, String>();

				String userCredentials = "username:password";
				String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userCredentials.getBytes()));
				headers.put("content-type", "application/json");
				headers.put(this.getTokenKey(), this.getTokenValue());
				headers.put("Accept", "application/json");

				conn.setRequestMethod("GET");
				setHeaders(conn, headers);

				try {
					conn.getContent();
				}
				catch (IOException e) {
					if(j == 0) { 	
						conn.disconnect();
						this.getTokenAccess();
						continue;
					}
				}
				if (conn.getResponseCode() == 200) {
					BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
					
					while ((output = br.readLine()) != null) {
						result = result + output;
					}
					
					conn.disconnect();
					logger.info("RESPONSE WS:" + result);									
					break;
				}
				else{
					conn.disconnect();
					String error = "Failed : HTTP error code : " + conn.getResponseCode() + " " + conn.getResponseMessage();   
					throw new Exception(error);
				}
			} catch (MalformedURLException e) {
				String error = "URL Malformed";
				throw new Exception(error);
	
			} catch (IOException e) {
				if(j != 0) { 
					String error = "Servizio Registro Imprese non disponibile";
					throw new Exception(error);
				}
			}
		}
		return result;
	}

	private static void printUsage(String invalidOpt) {
		if (null != invalidOpt) {
			System.out.println("Invalid Option: " + invalidOpt);
		}
		System.out.println("Usage:");

		System.out.println("  UtilityClass -s [UtilityClass] -f [codiceFiscale]");

	}
	public void letturaParametri(String args[]) throws java.lang.Exception {
		JSONObject outputJson = null;
		byte[] decoder = null;

		
		logger.trace("ENTRY UtilityClass");
		logger.trace("PARAMETRI:");
		{

			for (int i = 0; i < args.length; i++)
			{
				try {
					if ('-' == args[i].charAt(0)) {
						switch (args[i].charAt(1)) {
						case '?':
							printUsage(null);
							System.exit(0);
							break;
                        case 's':
                        case 'S':
 /*                       	rnaWSSoapClient.setServizio(args[++i]);
                            logger.trace("[Servizio] :" + rnaWSSoapClient.getServizio());
*/                            break;
                        case 'r':
                        case 'R':
/*                        	rnaWSSoapClient.setIdRichiesta(Long.valueOf(args[++i]));
                            logger.info("[IdRichiesta] :" + rnaWSSoapClient.getIdRichiesta());
*/                            break;

						case 'f':
						case 'F':
/*							rnaWSSoapClient.setCodiceFiscale(args[++i]);
							logger.debug("[codiceFiscale] :" + rnaWSSoapClient.getCodiceFiscale());
*/							break;
						default:
							printUsage(args[i]);
							System.exit(0);
							break;
						}
					}
				} catch (Exception e) {
					System.out.println("Invalid option format.");
					printUsage(null);
					System.exit(0);
				}
			}
		}

		/*dbConnect = new DBConnect();

		dbConnect.getDBParameters(msgDao.getConnection());*/
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*Utility test = new Utility();
		test.readLog4jProperties();*/
	}
}
