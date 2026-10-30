// Source code is decompiled from a .class file using FernFlower decompiler.
package it.wego.cross.cross_dms.utilities;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.sql.*;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.common.net.HttpHeaders;

import java.io.*;
import java.util.*;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


public class UtilityRestClientClass {
	private static final int BUFFER_SIZE = 4096;

	public static final String PUTPRATICA = "{    \"idPratica\": null,    \"identificativoPratica\": \"DIA-123456000-LOR\",    \"oggettoPratica\": \"\",    \"protocollo\": \"No idea\",    \"vecchioProtocollo\": \"Sto aggiornando\",    \"codRegistro\": \"Codice 102\",    \"annoRiferimento\": 1990,    \"idPraticaPadre\": 1,    \"statoPratica\": {\"idStatoPratica\": 1},    \"dataApertura\": \"2002-10-19\",    \"dataChiusura\": \"2019-10-19\",    \"recapito\": {\"idRecapito\": 6728},    \"processo\": {\"idProcesso\": 22},    \"utente\": {\"idUtente\": 1},    \"comune\": {\"idComune\": 893},    \"dataRicezione\": \"2002-10-18\",    \"idStaging\": 1,    \"idModello\": 1,    \"responsabileProcedimento\": \"Marco\",    \"dataProtocollazione\": \"2002-10-25\",    \"dataVecchiaProtocollazione\": \"2022-10-23\",    \"giorniSospensione\": 10,    \"statoEmail\": 1,    \"procedimentoEnte\": {\"idProcedimentoEnte\": 1509},    \"ufficioRiferimento\": \"Ufficio bari\",    \"identificativoEsterno\":\"boh 2.0\",    \"integrazione\": \"boh\",    \"dataProtSuap\": \"2002-11-12\",    \"protSuap\": \"boh\",    \"idTipoProcedimentoSuap\": 1,    \"idTipoInterventoSuap\": 1}";
	private final String USER_AGENT = "Mozilla/5.0";
	private HashMap<String, String> map;
	private LogClass logger;
	public static final String LOG4J_CONFIG_PATH = "log4j.properties";
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

	public UtilityRestClientClass() {
	}

	private LogClass getLogger() {
		return this.logger;
	}

	private void setLogger(LogClass logger) {
		this.logger = logger;
	}

	private Connection getConn() {
		return this.conn;
	}

	private void setConn(Connection conn) {
		this.conn = conn;
	}

	private String getAccess_token() {
		return this.access_token;
	}

	private void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	private String getRefresh_token() {
		return this.refresh_token;
	}

	private void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}

	private String getToken_type() {
		return this.token_type;
	}

	private void setToken_type(String token_type) {
		this.token_type = token_type;
	}

	private int getExpires_in() {
		return this.expires_in;
	}

	private void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}

	private String getsUserID() {
		return this.sUserID;
	}

	private void setsUserID(String sUserID) {
		this.sUserID = sUserID;
	}

	private String getsPassword() {
		return this.sPassword;
	}

	private void setsPassword(String sPassword) {
		this.sPassword = sPassword;
	}

	private String getEncrKey() {
		return this.encrKey;
	}

	private void setEncrKey(String encrKey) {
		this.encrKey = encrKey;
	}

	private String getResource() {
		return this.Resource;
	}

	private void setResource(String resource) {
		this.Resource = resource;
	}

	private String getUrl() {
		return this.url;
	}

	private void setUrl(String url) {
		this.url = url;
	}

	private String getTokenKey() {
		return this.tokenKey;
	}

	private void setTokenKey(String tokenKey) {
		this.tokenKey = tokenKey;
	}

	private String getTokenValue() {
		return this.tokenValue;
	}

	private void setTokenValue(String tokenValue) {
		this.tokenValue = tokenValue;
	}
	/* */
	private static HttpsURLConnection getURLConnectionHttps(String urlToConnect) throws IOException {

		SSLContext sc;
		HttpsURLConnection httpUrlConnection = null;
		try {
			TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
				public java.security.cert.X509Certificate[] getAcceptedIssuers() { return null; }
				public void checkClientTrusted1(X509Certificate[] certs, String authType) { }
				public void checkServerTrusted(X509Certificate[] certs, String authType) { }
				@Override
				public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
					// TODO Auto-generated method stub

				}
				public void checkServerTrusted1(X509Certificate[] arg0, String arg1) throws CertificateException {
					// TODO Auto-generated method stub

				}
			}};
			sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

			// Create all-trusting host name verifier
			HostnameVerifier allHostsValid = new HostnameVerifier() {
				public boolean verify(String hostname, SSLSession session) { return true; }
			};
			// Install the all-trusting host verifier
			HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

			URL url = new URL(urlToConnect);
			httpUrlConnection = (HttpsURLConnection) url.openConnection();			
		} catch (NoSuchAlgorithmException | KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return httpUrlConnection;
	}
	/* */
	private static HttpURLConnection getURLConnection(String urlToConnect) throws IOException {
		URL url = new URL(urlToConnect);
		HttpURLConnection httpUrlConnection = (HttpURLConnection)url.openConnection();
		return httpUrlConnection;
	}

	private static void setHeaders(HttpURLConnection httpUrlConnection, Map<String, String> headers) {
		Iterator var3 = headers.keySet().iterator();

		while(var3.hasNext()) {
			String headerKey = (String)var3.next();
			httpUrlConnection.setRequestProperty(headerKey, (String)headers.get(headerKey));
		}

	}

	private void ReadingArrayFromJSON(String json) {
		JSONParser jsonParser = new JSONParser();

		try {
			JSONObject jsonObject = (JSONObject)jsonParser.parse(json);
			this.setAccess_token(jsonObject.get("access_token").toString());
		} catch (ParseException var4) {
			var4.printStackTrace();
		}

	}

	public String HttpPostRestClientBearerAuth(String urlParam, String parameters, String token) throws Exception {
		String result = "";
		int responseCode = 0;
		new URL(urlParam);
		HttpURLConnection conn = getURLConnectionHttps(urlParam);
		conn.setRequestProperty("Authorization", "Bearer " + token);
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		StringBuilder postData = new StringBuilder();
		postData.append(parameters);
		byte[] postDataBytes = postData.toString().getBytes("UTF-8");
		conn.getOutputStream().write(postDataBytes);
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		responseCode = conn.getResponseCode();
		StringBuffer response = new StringBuffer();

		String output;
		while((output = in.readLine()) != null) {
			response.append(output);
		}

		result = response.toString();
		in.close();
		System.out.println("Response:-" + response.toString());
		return result;
	}

	public String getTokenAccessHttps(String url, String jsonCredential) {
		String msgLog = "";
		String response = null;
		String encoding = "";

		try {
			HttpsURLConnection postConnection = getURLConnectionHttps(url);
			postConnection.setRequestMethod("POST");
			postConnection.setRequestProperty("Content-Type", "application/json");
			postConnection.setDoOutput(true);
			StringBuilder postData = new StringBuilder();
			postData.append(jsonCredential);
			byte[] postDataBytes = postData.toString().getBytes("UTF-8");
			postConnection.getOutputStream().write(postDataBytes);
			Reader in = new BufferedReader(new InputStreamReader(postConnection.getInputStream(), "UTF-8"));
			int responseCode = postConnection.getResponseCode();
			msgLog = "";
			if (responseCode == 200) {
				int length_array_buffer_output = 0;

				char[] tmp_respons_array;
				int c;
				for(tmp_respons_array = new char[10000]; (c = in.read()) >= 0; ++length_array_buffer_output) {
					tmp_respons_array[length_array_buffer_output] = (char)c;
				}

				char[] respons_array = new char[length_array_buffer_output];

				for(int x = 0; x < length_array_buffer_output; ++x) {
					respons_array[x] = tmp_respons_array[x];
				}

				response = String.valueOf(respons_array);
				this.setAccess_token("");
				this.ReadingArrayFromJSON(response);
				msgLog = "";
				LogClass.info("POST Response Message : " + response);
				postConnection.disconnect();
				LogClass.info(msgLog);
			} else {
				msgLog = "";
				LogClass.info("Il servizio di richiesta token di accesso non funziona! Riprovare piu' tardi");
				LogClass.info(msgLog);
				System.exit(-5);
			}
		} catch (IOException var15) {
			var15.printStackTrace();
			LogClass.error(var15.getMessage());
		}

		return this.getAccess_token();
	}

	public String httpGetRestClient(String urlparam, String pInput) throws Exception {
		int retCode = 0;
		String output = "";
		String result = "";
		String url = "urlToConnect";
		HttpURLConnection conn = null;

		String error;
		try {
			url = urlparam + "?" + pInput;
			String tokenKey = this.getTokenKey();
			error = this.getTokenValue();
			LogClass.info("call_httpPostRestClient = " + url.toString());
			conn = getURLConnection(url);
			Map<String, String> headers = new HashMap();
			conn.setRequestMethod("GET");
			setHeaders(conn, headers);

			try {
				conn.getContent();
				if (conn.getResponseCode() != 200) {
					conn.disconnect();
					error = "Failed : HTTP error code : " + conn.getResponseCode() + " " + conn.getResponseMessage();
					throw new Exception(error);
				} else {
					for(BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream())); (output = br.readLine()) != null; result = result + output) {
					}

					conn.disconnect();
					LogClass.info("RESPONSE WS:" + result);
					return result;
				}
			} catch (IOException var12) {
				throw new Exception(var12);
			}
		} catch (MalformedURLException var13) {
			error = "URL Malformed";
			throw new Exception(error);
		} catch (IOException var14) {
			error = "Servizio DMS Non disponibile";
			throw new Exception(error);
		}
	}

	public String httpPostRestClient(String urlparam, String pInput, String token) throws Exception {
		int retCode = 0;
		String output = "";
		String result = "";
		String url = "urlToConnect";
		HttpURLConnection conn = null;

		String error;
		try {
			url = urlparam + "?" + pInput;
			String tokenKey = this.getTokenKey();
			error = this.getTokenValue();
			LogClass.info("call_httpPostRestClient = " + url.toString());
			conn = getURLConnection(url);
			Map<String, String> headers = new HashMap();
			String userCredentials = "username:password";
			String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userCredentials.getBytes()));
			headers.put("content-type", "application/json");
			headers.put(this.getTokenKey(), this.getTokenValue());
			headers.put("Accept", "application/json");
			conn.setRequestMethod("POST");
			setHeaders(conn, headers);

			try {
				conn.getContent();
				if (conn.getResponseCode() != 200) {
					conn.disconnect();
					error = "Failed : HTTP error code : " + conn.getResponseCode() + " " + conn.getResponseMessage();
					throw new Exception(error);
				} else {
					for(BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream())); (output = br.readLine()) != null; result = result + output) {
					}

					conn.disconnect();
					LogClass.info("RESPONSE WS:" + result);
					return result;
				}
			} catch (IOException var15) {
				throw new Exception(var15);
			}
		} catch (MalformedURLException var16) {
			error = "URL Malformed";
			throw new Exception(error);
		} catch (IOException var17) {
			error = "Servizio DMS Non disponibile";
			throw new Exception(error);
		}
	}

	public String HttpRESTFulGETDms_(String paramUrl, String parameters, String payloadDms) throws Exception {
		String result = "";
		URIBuilder builder = new URIBuilder(paramUrl);
		int offset = 0;
		builder.setParameter("offset", "0");
		builder.setParameter("filter", payloadDms);
		builder.setParameter("offset", "0");
		URI uri = builder.build();
		HttpGet request = new HttpGet(uri);
		HttpClient httpclient = HttpClients.createDefault();
		HttpResponse response = httpclient.execute(request);
		HttpEntity entity = response.getEntity();
		result = entity.toString();
		return result;
	}

	public String HttpRESTFulGETDms(String paramUrl, String parameters, String payloadDms) throws Exception {
		String result = "";
		Runtime rt = Runtime.getRuntime();

		try {
			(new StringBuilder(String.valueOf(paramUrl))).append("?").append(parameters).append("&filter=").append(encodeValue(payloadDms)).toString();
			String[] stringGet = new String[]{"curl", "-X", "GET", paramUrl, "-H", "content-type: application/json", "-d", "offset=0&filter=" + encodeValue(payloadDms)};
			System.out.println("\n\nREQUEST===" + stringGet.toString() + "===\n\n\n");
			ProcessBuilder ps = new ProcessBuilder(stringGet);
			ps.redirectErrorStream(true);
			Process pr = ps.start();
			pr.waitFor();
			BufferedReader reader2 = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			String line2 = "";
			StringBuilder output = new StringBuilder();

			while((line2 = reader2.readLine()) != null) {
				output.append(line2 + "\n");
			}

			System.out.println("\n\nRESPONSE===" + output.toString() + "===\n\n\n");
			result = output.toString();
		} catch (Exception var15) {
			System.out.println("===============ERROR===============\n" + var15.getMessage() + "\n\n\n");
		}

		return result;
	}

	public static String __HttpRESTFulPOSTDms(String paramUrl, String payloadDms) throws Exception {
		String result = "";
		Runtime rt = Runtime.getRuntime();

		try {
//			(new StringBuilder(String.valueOf(paramUrl))).append("?").append(parameters).append("&filter=").append(encodeValue(payloadDms)).toString();
			String[] stringPos = new String[]{"curl", "-X", "POST", paramUrl, "-H", "content-type: application/json", "-d", payloadDms};
			System.out.println("\n\nREQUEST===" + stringPos.toString() + "===\n\n\n");
			ProcessBuilder ps = new ProcessBuilder(stringPos);
			ps.redirectErrorStream(true);
			Process pr = ps.start();
			pr.waitFor();
			BufferedReader reader2 = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			String line2 = "";
			StringBuilder output = new StringBuilder();

			while((line2 = reader2.readLine()) != null) {
				output.append(line2 + "\n");
			}

			System.out.println("\n\nRESPONSE===" + output.toString() + "===\n\n\n");
			result = output.toString();
		} catch (Exception var15) {
			System.out.println("===============ERROR===============\n" + var15.getMessage() + "\n\n\n");
		}

		return result;
	}

	public static String sendPostRequest__(String requestUrl, String identificativo_pratica, String jsonInputString) {
		StringBuffer result = new StringBuffer();
		String sResult = "";
		String responseLine = null;

		try {

			try (PrintWriter out = new PrintWriter("/home/lorenzo/json_request/" + identificativo_pratica + ".json")) {
				out.println(requestUrl + "/" + jsonInputString);
			}

			URL url = new URL(requestUrl);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Accept", "application/json");
			connection.setRequestProperty("Content-Type", "application/json");
			/*			
			OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
			writer.write(payload);
			writer.close();
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			 */
			try(OutputStream os = connection.getOutputStream()) {
				byte[] input = jsonInputString.getBytes("utf-8");
				os.write(input, 0, input.length);
				os.close();			
			}
			try(BufferedReader br = new BufferedReader(
					new InputStreamReader(connection.getInputStream(), "utf-8"))) {
				StringBuilder response = new StringBuilder();
				while ((responseLine = br.readLine()) != null) {
					response.append(responseLine.trim());
				}
				System.out.println(response.toString());
				br.close();
			}

			connection.disconnect();
			sResult = responseLine;
			try (PrintWriter out2 = new PrintWriter("/home/lorenzo/json_response/" + identificativo_pratica + ".json")) {
				out2.println(sResult);
			}

		} catch (Exception var8) {
			throw new RuntimeException(var8.getMessage());
		}
		return sResult;

	}

	public static String sendPostRequest(String requestUrl, String identificativo_pratica, String jsonInputString) throws Exception {
		String result="";
		String step = "";

		try (PrintWriter out = new PrintWriter("/home/lorenzo/json_request/" + identificativo_pratica + ".json")) {
				out.println(requestUrl + "/" + jsonInputString);
		}

		URL url = new URL(requestUrl);
		HttpURLConnection httpConnection  = (HttpURLConnection) url.openConnection();
		httpConnection.setDoOutput(true);
		httpConnection.setRequestMethod("POST");
		httpConnection.setRequestProperty("Content-Type", "application/json");
		httpConnection.setRequestProperty("Accept", "application/json");

		// Not required
		// urlConnection.setRequestProperty("Content-Length", String.valueOf(input.getBytes().length));
		
		step = "URL and parameters for the connection";
		
		// Writes the JSON parsed as string to the connection
		DataOutputStream wr = new DataOutputStream(httpConnection.getOutputStream());
		wr.write(jsonInputString.toString().getBytes());
		Integer responseCode = httpConnection.getResponseCode();

		step = "Writes the JSON parsed as string to the connection, response:" + responseCode.toString();
		
		BufferedReader bufferedReader;

		if (responseCode > 199 && responseCode < 300) {
			bufferedReader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
			step = "Response code > 199";
		} else {
			bufferedReader = new BufferedReader(new InputStreamReader(httpConnection.getErrorStream()));
			step = "Response code < 199";
		}
		step = "Creates a reader buffer";

		StringBuilder content = new StringBuilder();
		String line;
		while ((line = bufferedReader.readLine()) != null) {
			content.append(line).append("\n");
		}
		bufferedReader.close();
		
		step = "To receive the response";

		result = content.toString();
		// Prints the response
		System.out.println(result.toString());

		try (PrintWriter out2 = new PrintWriter("/home/lorenzo/json_response/" + identificativo_pratica + ".json")) {
			out2.println(result);
		}
		
		System.out.println("result:" + result.toString());
		System.out.println("Ultimo step eseguito:" + step);

		return result;
	}
	public static String encodeValue(String value) throws UnsupportedEncodingException {
		return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
	}

	public static String GETDmsDettaglioPratica(String identificativo_pratica) throws Exception {
		String result= "", parameters="";
		String pra_tipo_pratica = "", pra_numero_pratica = "", pra_anno_pratica = ""; 
		myUtility utl = new myUtility();

		String tmp_pratica[] = identificativo_pratica.split("-", 3);
		for(int i=0;i<3;i++) {
			if(i==0) {pra_tipo_pratica = tmp_pratica[0];}
			if(i==1) {pra_numero_pratica = tmp_pratica[1];}
			if(i==2) {pra_anno_pratica = tmp_pratica[2];}
		}
		int anno = Integer.valueOf(pra_anno_pratica).intValue();  
		int numero_pratica = Integer.valueOf(pra_numero_pratica).intValue();  

		JSONObject jsonFilterPraticaDms = new JSONObject(); 			    
		JSONArray arrayPayloadFilter = new JSONArray();

		JSONObject itemPayload_type_id = new JSONObject();
		JSONObject payload_typeid = new JSONObject();
		payload_typeid.put("eq", (int)1480);
		itemPayload_type_id.put("typeId", payload_typeid);

		JSONObject itemPayload_type_anno_pratica = new JSONObject();			    
		JSONObject payload_anno = new JSONObject();
		payload_anno.put("eq", (int)anno);			    
		itemPayload_type_anno_pratica.put("typeMetadata.ANNO", payload_anno);

		JSONObject itemPayload_type_tipo_pratica = new JSONObject();
		JSONObject payload_tipo_pratica = new JSONObject();
		payload_tipo_pratica.put("eq", (String)pra_tipo_pratica);
		itemPayload_type_tipo_pratica.put("typeMetadata.TIPO PRATICA", payload_tipo_pratica);

		JSONObject itemPayload_type_numero_numero = new JSONObject();
		JSONObject payload_numero_pratica = new JSONObject();
		payload_numero_pratica.put("eq", (String)pra_numero_pratica);
		itemPayload_type_numero_numero.put("typeMetadata.NUMERO", payload_numero_pratica);

		arrayPayloadFilter.add(itemPayload_type_id);
		arrayPayloadFilter.add(itemPayload_type_anno_pratica);
		arrayPayloadFilter.add(itemPayload_type_tipo_pratica);
		arrayPayloadFilter.add(itemPayload_type_numero_numero);			    			    

		jsonFilterPraticaDms.put("and", arrayPayloadFilter);

		String sUrlDms = utl.getValueProperties("dms.getPraticaById").trim();

		String jsonArrayPraticheDMS = HttpClientRestGetMethodDMS(sUrlDms, "application/json", identificativo_pratica, jsonFilterPraticaDms.toString());

		System.out.println("response:" + jsonArrayPraticheDMS);

		return jsonArrayPraticheDMS;
	}

	public static String GETDmsAllegatiPratica(String identificativo_pratica) throws Exception {
		String result= "", parameters="";
		String pra_tipo_pratica = "", pra_numero_pratica = "", pra_anno_pratica = ""; 
		myUtility utl = new myUtility();

		String tmp_pratica[] = identificativo_pratica.split("-", 3);
		for(int i=0;i<3;i++) {
			if(i==0) {pra_tipo_pratica = tmp_pratica[0];}
			if(i==1) {pra_numero_pratica = tmp_pratica[1];}
			if(i==2) {pra_anno_pratica = tmp_pratica[2];}
		}
		int anno = Integer.valueOf(pra_anno_pratica).intValue();  
		int numero_pratica = Integer.valueOf(pra_numero_pratica).intValue();  

		/* Solo per test		
		FileInputStream fstream = new FileInputStream("json/DIA-4001-2002.json");
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
		StringBuilder sbr = new StringBuilder();
		String jsonResult;
		while ((jsonResult = br.readLine()) != null)   {sbr.append(jsonResult);}
		fstream.close();
		jsonResult = sbr.toString();
		String dettaglioPratica = jsonResult;
		 */		
		String dettaglioPratica = GETDmsDettaglioPratica(identificativo_pratica);

		LogClass.trace("GETDmsAllegatiPratica GETDmsDettaglioPratica REQUEST:" + identificativo_pratica + " RESPONSE:" + dettaglioPratica);

		it.wego.cross.beans.DettaglioPratica dtp = new it.wego.cross.beans.DettaglioPratica();
		dtp.parser_json(dettaglioPratica);
		String idFolder = dtp.getID().toString();

		JSONObject jsonFilterPraticaDms = new JSONObject(); 			    
		JSONArray arrayPayloadFilter = new JSONArray();

		JSONObject itemPayload_type_id = new JSONObject();
		JSONObject payload_typeid = new JSONObject();
		payload_typeid.put("eq", (int)1480);
		itemPayload_type_id.put("typeId", payload_typeid);

		JSONObject itemPayload_idFolders = new JSONObject();			    
		JSONObject payload_idFolders = new JSONObject();
		payload_idFolders.put("eq", idFolder);			    
		itemPayload_idFolders.put("idFolders", payload_idFolders);

		//arrayPayloadFilter.add(itemPayload_type_id);
		arrayPayloadFilter.add(itemPayload_idFolders);

		jsonFilterPraticaDms.put("and", arrayPayloadFilter);

		String sUrlDms = utl.getValueProperties("dms.getAllegatiPratica").trim();

		/*		
		fstream = new FileInputStream("json/DIA-4001-2002_allegati.json");
		br = new BufferedReader(new InputStreamReader(fstream));
		sbr = new StringBuilder();
		while ((jsonResult = br.readLine()) != null)   {sbr.append(jsonResult);}
		fstream.close();
		jsonResult = sbr.toString();
		String jsonArrayAllegatiPraticheDMS = jsonResult;
		 */		
		String jsonArrayAllegatiPraticheDMS = HttpClientRestGetMethodDMS(sUrlDms, "application/json", identificativo_pratica, jsonFilterPraticaDms.toString());

		System.out.println("response:" + jsonArrayAllegatiPraticheDMS);

		return jsonArrayAllegatiPraticheDMS;
	}

	public static String HttpClientRestGetMethodDMS(String url, String accepts, String identificativo_pratica, String payload) throws Exception {
		String result = "";

		String sRequest = url + "?offset=0&filter=" + encodeValue(payload);

		try (PrintWriter out = new PrintWriter("/home/lorenzo/json_request/" + identificativo_pratica + ".json")) {
			out.println(sRequest);
		}

		HttpURLConnection con = (HttpURLConnection)(new URL(sRequest)).openConnection();
		con.setRequestMethod("GET");
		con.setDoOutput(true);
		con.setRequestProperty("Accept", accepts);
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		System.out.println("TMP_REQUEST:" + sRequest);
		ByteArrayOutputStream rspBuff = new ByteArrayOutputStream();
		InputStream rspStream = con.getInputStream();

		int c;
		while((c = rspStream.read()) > 0) {
			rspBuff.write(c);
		}

		rspStream.close();
		result = new String(rspBuff.toByteArray());

		try (PrintWriter out2 = new PrintWriter("/home/lorenzo/json_response/" + identificativo_pratica + ".json")) {
			out2.println(result);
		}

		return result;
	}

	public static String HttpClientRestPostMethodDMS(String url, String accepts, String identificativo_pratica, String payload) throws Exception {
		String result = "";

		String sRequest = url;

		try (PrintWriter out = new PrintWriter("/home/lorenzo/json_request/" + identificativo_pratica + ".json")) {
			out.println(sRequest);
		}

		HttpURLConnection con = (HttpURLConnection)(new URL(sRequest)).openConnection();
		con.setRequestMethod("POST");
		con.setDoOutput(true);
		con.setRequestProperty("Accept", accepts);
		//		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		con.setRequestProperty("Content-Type", "application/json");
		System.out.println("TMP_REQUEST:" + sRequest);

		ByteArrayOutputStream rspBuff = new ByteArrayOutputStream();
		InputStream rspStream = con.getInputStream();

		int c;
		while((c = rspStream.read()) > 0) {
			rspBuff.write(c);
		}

		rspStream.close();
		result = new String(rspBuff.toByteArray());

		try (PrintWriter out2 = new PrintWriter("/home/lorenzo/json_response/" + identificativo_pratica + ".json")) {
			out2.println(result);
		}

		return result;
	}


	public static String RESTGET(String url, String accepts, String identificativo_pratica, String parameters, String minusD) throws Exception {
		String sRequest = url + parameters + encodeValue(minusD);
		try (PrintWriter out = new PrintWriter("/home/lorenzo/json_request/" + identificativo_pratica + ".json")) {
			out.println(sRequest);
		}

		HttpURLConnection con = (HttpURLConnection)(new URL(sRequest)).openConnection();
		con.setRequestMethod("GET");
		con.setDoOutput(true);
		con.setRequestProperty("Accept", accepts);
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		System.out.println("TMP_PARAMETERS:" + parameters);
		System.out.println("TMP_PAYLOAD:" + encodeValue(minusD));
		System.out.println("TMP_URL:" + url);
		System.out.println("TMP_REQUEST:" + sRequest);
		ByteArrayOutputStream rspBuff = new ByteArrayOutputStream();
		InputStream rspStream = con.getInputStream();

		int c;
		while((c = rspStream.read()) > 0) {
			rspBuff.write(c);
		}

		rspStream.close();
		return new String(rspBuff.toByteArray());
	}

	public static String downloadFile(String fileURL) throws IOException {
		URL url = new URL(fileURL);
		String encodedString = "";

		try (
				InputStream inputStream = url.openStream(); 
				BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream); 
				//FileOutputStream fileOutputStream = new FileOutputStream(outputPath);
				) {
			byte[] bucket = new byte[2048];
			int numBytesRead;

			byte[] sourceBytes = IOUtils.toByteArray(bufferedInputStream);
			encodedString = Base64.getEncoder().encodeToString(sourceBytes);

			/*
		    while ((numBytesRead = bufferedInputStream.read(bucket, 0, bucket.length)) != -1) {
		        fileOutputStream.write(bucket, 0, numBytesRead);
		    }
			 */
		}
		return encodedString;
	}
	public static String CURL_GET(String url, String accepts, String identificativo_pratica, String parameters, String minusD) throws Exception {
		String result = "";
		// Runtime rt = Runtime.getRuntime();

		String sRequest = "curl -X GET " + "\"" + url + parameters + encodeValue(minusD) + "\"";
		try (PrintWriter out = new PrintWriter("/home/lorenzo/json_request/" + identificativo_pratica + ".json")) {
			out.println(sRequest);
		}
		ProcessBuilder ps = new ProcessBuilder(sRequest.split(" "));

		ps.redirectErrorStream(true);
		Process pr = ps.start();
		pr.waitFor();
		BufferedReader reader2 = new BufferedReader(new InputStreamReader(pr.getInputStream()));
		String line2 = "";
		StringBuilder output = new StringBuilder();

		while((line2 = reader2.readLine()) != null) {
			output.append(line2 + "\n");
		}
		pr.destroy();
		System.out.println("\n\nRESPONSE===" + output.toString() + "===\n\n\n");
		result = output.toString();
		return result;
	}

	public String CURL_DMS(String url, String accepts, String identificativo_pratica, String parameters, String minusD) throws Exception {
		String result = "";
		Runtime rt = Runtime.getRuntime();

		try {
			//(new StringBuilder(String.valueOf(paramUrl))).append("?").append(parameters).append("&filter=").append(encodeValue(payloadDms)).toString();

			String[] stringGet = new String[]{"curl", "-X", "GET", url, "-H", "content-type: application/json", "-d", "offset=0&filter=" + encodeValue(minusD)};
			//String[] stringGet = new String[]{"curl", "-X", "GET", paramUrl, "-H", "content-type: application/json", "-d", "offset=0&filter=" + encodeValue(payloadDms)};

			System.out.println("\n\nREQUEST===" + stringGet.toString() + "===\n\n\n");
			ProcessBuilder ps = new ProcessBuilder(stringGet);
			ps.redirectErrorStream(true);
			Process pr = ps.start();
			pr.waitFor();
			BufferedReader reader2 = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			String line2 = "";
			StringBuilder output = new StringBuilder();

			while((line2 = reader2.readLine()) != null) {
				output.append(line2 + "\n");
			}

			System.out.println("\n\nRESPONSE===" + output.toString() + "===\n\n\n");
			result = output.toString();
		} catch (Exception var15) {
			System.out.println("===============ERROR===============\n" + var15.getMessage() + "\n\n\n");
		}

		return result;
	}

	public static String sendGetRequest(String paramUrl, String parameters, String payloadDms) {
		StringBuffer jsonString = new StringBuffer();

		try {
			String urlEncoded = paramUrl + "?" + parameters + "&filter=" + payloadDms;
			URL url = new URL(urlEncoded);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept", "application/json");
			connection.setRequestProperty("Content-Type", "application/json");
			System.out.println("TMP_PAYLOAD:" + urlEncoded);
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			String line;
			while((line = br.readLine()) != null) {
				jsonString.append(line);
				System.out.println(line);
			}

			br.close();
			connection.disconnect();
			return jsonString.toString();
		} catch (Exception var9) {
			throw new RuntimeException(var9.getMessage());
		}
	}

	public static String myk_sendGetRequest(String paramUrl, String parameters, String payloadDms) {
		String output = "";

		try {
			String urlEncoded = paramUrl + "?" + parameters + "&filter=" + encodeValue(payloadDms);
			System.out.println("TMP_PAYLOAD:" + urlEncoded);
			URL url = new URL(urlEncoded);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Content-Type", "application/json");
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			System.out.println("Output from Server .... \n");

			while((output = br.readLine()) != null) {
				System.out.println(output);
			}

			conn.disconnect();
		} catch (MalformedURLException var8) {
			var8.printStackTrace();
		} catch (IOException var9) {
			var9.printStackTrace();
		}

		return output;
	}

	public static String myk_sendPosRequest(String paramUrl, String parameters, String payloadDms) {
		String output = "";

		try {
			String urlEncoded = paramUrl + "?" + parameters;
			URL url = new URL(urlEncoded);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			String input = "filter=" + encodeValue(payloadDms);
			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();
			if (conn.getResponseCode() != 201) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			System.out.println("Output from Server .... \n");

			while((output = br.readLine()) != null) {
				System.out.println(output);
			}

			conn.disconnect();
		} catch (MalformedURLException var10) {
			var10.printStackTrace();
		} catch (IOException var11) {
			var11.printStackTrace();
		}

		return output;
	}

	public static void mainRequest(String[] args) {
		List<String> values = new ArrayList();
		values.add("TCGA-LAML");
		String requestUrl = "https://api.gdc.cancer.gov/cases";
		try {
			sendPostRequest(requestUrl, "", preparePayload(values));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static String preparePayload(List<String> values) {
		StringBuilder sb = new StringBuilder();
		Iterator var3 = values.iterator();

		String desiredValue;
		while(var3.hasNext()) {
			desiredValue = (String)var3.next();
			sb.append("\"" + desiredValue + "\",");
		}

		desiredValue = sb.toString().substring(0, sb.toString().length() - 1);
		return "{ \"filters\": {\"op\":\"in\", \"content\":{ \"field\":\"cases.project.project_id\", \"value\":[" + desiredValue + "] } } }";
	}

	public String HttpRESTFulHttpPostDms(String paramUrl, String parameters, String payloadDms) throws Exception {
		String result = "";
		HttpClient httpClient = HttpClientBuilder.create().build();

		try {
			HttpPost request = new HttpPost(paramUrl);
			StringEntity params = new StringEntity("offset=0&filter=" + payloadDms);
			request.addHeader("content-type", "application/json");
			request.setEntity(params);
			HttpResponse response = httpClient.execute(request);
			result = response.toString();
		} catch (Exception var9) {
		}

		return result;
	}

	public String HttpRESTFulGETDms__HttpClient(String paramUrl, String parameters, String payloadDms) throws Exception {
		String result = "";
		StringBuilder json = new StringBuilder();
		json.append(payloadDms);
		HttpClient client = HttpClientBuilder.create().build();
		System.out.println("STEP-1");
		HttpGet request = new HttpGet(paramUrl);
		URI uri = (new URIBuilder(request.getURI())).addParameter("offset", "0").addParameter("filter", payloadDms).build();
		System.out.println("STEP-2");
		request.setURI(uri);
		System.out.println("STEP-3");
		HttpResponse response = client.execute(request);
		System.out.println("STEP-4");
		if (response.getStatusLine().getStatusCode() != 200) {
			System.out.println("STEP-5");
			throw new Exception("Errore di comunicazione HTTP:" + response.getStatusLine().getStatusCode());
		} else {
			System.out.println("STEP-6");
			BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			System.out.println("Response  .... ");

			for(String output = ""; (output = br.readLine()) != null; result = result + output) {
			}

			System.out.println(result);
			System.out.println("STEP-7");
			return result;
		}
	}

	public String HttpRESTFulPOSTDms(String paramUrl, String parameters, String payloadDms) throws UnsupportedEncodingException {
		String result = "";
		HttpPost post = new HttpPost(paramUrl+"?"+parameters);

		StringBuilder json = new StringBuilder();
		json.append(payloadDms);

		// send a JSON data
		post.setEntity(new StringEntity(json.toString()));
		try (CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = httpClient.execute(post)) {

			result = EntityUtils.toString(response.getEntity());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;	
	}

	public String HttpRESTFulGETDms___test(String paramUrl, String parameters, String payloadDms) {

		String results = "";
		try {
			byte[] getData       = payloadDms.getBytes( StandardCharsets.UTF_8 );
			int    getDataLength = getData.length;
			String request        = paramUrl + "?" + parameters ;
			URL    url            = new URL( request );
			HttpURLConnection conn= (HttpURLConnection) url.openConnection();           
			conn.setDoOutput( true );
			conn.setInstanceFollowRedirects( false );
			conn.setRequestMethod( "GET" );
			//			conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded"); 
			conn.setRequestProperty( "Accept", "application/json");
			conn.setRequestProperty( "Content-Type", "application/json"); 
			conn.setRequestProperty( "charset", "utf-8");
			conn.setRequestProperty( "Content-Length", Integer.toString( getDataLength ));
			conn.setUseCaches( false );
			try( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
				wr.write( getData );
			}			
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				results = results + output;
			}
			System.out.println(results);
			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
		return results;

	}



	public String HttpRESTFulGETDms___ORI(String paramUrl, String parameters, String payloadDms) {

		String results = "";
		try {
			URL url = new URL (paramUrl);

			// URL url = new URL(paramUrl + "?" + parameters + payloadDms);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod("GET");

			conn.setRequestProperty( "Content-Type", "application/json"); 

			conn.setRequestProperty( "Accept", "application/json");

			conn.setDoOutput(true);

			String jsonInputString = parameters + "&filter=" + payloadDms;

			byte[] input = null;
			try(OutputStream os = conn.getOutputStream()) {
				input = jsonInputString.getBytes("utf-8");
				os.write(input, 0, input.length);			
			}

			System.out.println(" INPUT REQUEST:" + input.toString());

			try(BufferedReader br = new BufferedReader(
					new InputStreamReader(conn.getInputStream(), "utf-8"))) {

				StringBuilder response = new StringBuilder();
				String responseLine = null;
				while ((responseLine = br.readLine()) != null) {
					response.append(responseLine.trim());
				}
				System.out.println(response.toString());
				results = response.toString();
			}
			/*
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				results = results + output;
			}
			 */
			System.out.println(results);
			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
		return results;

	}


	public String HttpRESTFulGET(String paramUrl, String parameters) {
		String results = "";

		try {
			URL url = new URL(paramUrl + "?" + parameters);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			System.out.println("Output from Server .... \n");

			String output;
			while((output = br.readLine()) != null) {
				results = results + output;
			}

			System.out.println(results);
			conn.disconnect();
		} catch (MalformedURLException var8) {
			var8.printStackTrace();
		} catch (IOException var9) {
			var9.printStackTrace();
		}

		return results;
	}

	public String HttpRESTFulPOST(String paramUrl, String parameters) {
		String results = "";

		try {
			URL url = new URL(paramUrl);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			OutputStream os = conn.getOutputStream();
			os.write(parameters.getBytes());
			os.flush();
			if (conn.getResponseCode() != 201) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			System.out.println("Output from Server .... \n");

			String output;
			while((output = br.readLine()) != null) {
				results = results + output;
			}

			System.out.println(results);
			conn.disconnect();
		} catch (MalformedURLException var10) {
			var10.printStackTrace();
		} catch (IOException var11) {
			var11.printStackTrace();
		}

		return results;
	}

	public HttpResponse getRespose(String url, String your_auth_code) {
		HttpClient client = new DefaultHttpClient();
		HttpPost postForGetMethod = new HttpPost(url);
		postForGetMethod.addHeader("Content-type", "Application/JSON");
		postForGetMethod.addHeader("Authorization", your_auth_code);

		try {
			return client.execute(postForGetMethod);
		} catch (ClientProtocolException var6) {
			var6.printStackTrace();
		} catch (IOException var7) {
			var7.printStackTrace();
		}

		return null;
	}

	public String getJSON(String url, int timeout) {
		HttpURLConnection c = null;

		try {
			try {
				URL u = new URL(url);
				c = (HttpURLConnection)u.openConnection();
				c.setRequestMethod("GET");
				c.setRequestProperty("Content-length", "0");
				c.setUseCaches(false);
				c.setAllowUserInteraction(false);
				c.setConnectTimeout(timeout);
				c.setReadTimeout(timeout);
				c.connect();
				int status = c.getResponseCode();
				switch (status) {
				case 200:
				case 201:
					BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
					StringBuilder sb = new StringBuilder();

					String line;
					while((line = br.readLine()) != null) {
						sb.append(line + "\n");
					}

					br.close();
					String var10 = sb.toString();
					return var10;
				}
			} catch (MalformedURLException var21) {
				LogClass.error(var21.getMessage());
			} catch (IOException var22) {
				LogClass.error(var22.getMessage());
			}

			return null;
		} finally {
			if (c != null) {
				try {
					c.disconnect();
				} catch (Exception var20) {
					LogClass.error(var20.getMessage());
				}
			}

		}
	}

	public static void RestCallGetAndPost(String urlParam, String jsonParam) throws Exception {
		UtilityRestClientClass http = new UtilityRestClientClass();
		http.sendingGetRequest(urlParam, jsonParam);
		http.sendingPostRequest(urlParam, jsonParam);
	}

	private void sendingGetRequest(String urlParam, String jsonParam) throws Exception {
		URL url = new URL(urlParam);
		HttpURLConnection con = (HttpURLConnection)url.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		int responseCode = con.getResponseCode();
		System.out.println("Sending get request : " + url);
		System.out.println("Response code : " + responseCode);
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		StringBuffer response = new StringBuffer();

		String output;
		while((output = in.readLine()) != null) {
			response.append(output);
		}

		in.close();
		System.out.println(response.toString());
	}

	public static String sendingPostRequest(String urlParam, String jsonParam) throws Exception {
		URL obj = new URL(urlParam);
		HttpURLConnection con = (HttpURLConnection)obj.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		con.setRequestProperty("Accept-Language", "it-IT,en;q=0.5");
		con.setRequestProperty("Content-Type", "application/json");
		//String postJsonData = "{\"anno\":\"2005\",\"offset\":1,\"limit\":20}";
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(jsonParam);
		wr.flush();
		wr.close();
		int responseCode = con.getResponseCode();
		System.out.println("nSending 'POST' request to URL : " + urlParam);
		System.out.println("Post Data : " + jsonParam);
		System.out.println("Response Code : " + responseCode);
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		StringBuffer response = new StringBuffer();

		String output;
		while((output = in.readLine()) != null) {
			response.append(output);
		}

		in.close();
		System.out.println(response.toString());
		return response.toString();
	}

	private static void printUsage(String invalidOpt) {
		if (invalidOpt != null) {
			System.out.println("Invalid Option: " + invalidOpt);
		}

		System.out.println("Usage:");
		System.out.println("  UtilityClass -s [UtilityClass] -f [codiceFiscale]");
	}

	public static void main(String[] args) {
		UtilityRestClientClass utlRestClient = new UtilityRestClientClass();
		myUtility myutl = new myUtility();
		String cross_credentials = "{\"username\": \"CTTTCN57P51F205W\",\"password\": \"Cann1208!\"}";
		String cross_login = "https://192.168.10.102:9443/public/authentication";
		String cross_putPratica = "https://192.168.10.102:9443/api/pratiche/postPratica";
		cross_putPratica = myutl.getValueProperties("cross.putPratica");
		cross_login = myutl.getValueProperties("cross.login");
		cross_credentials = myutl.getValueProperties("cross.credentials");
		String tokenAccess = utlRestClient.getTokenAccessHttps(cross_login, cross_credentials);

		try {
			utlRestClient.HttpPostRestClientBearerAuth(cross_putPratica, "{    \"idPratica\": null,    \"identificativoPratica\": \"DIA-123456000-LOR\",    \"oggettoPratica\": \"\",    \"protocollo\": \"No idea\",    \"vecchioProtocollo\": \"Sto aggiornando\",    \"codRegistro\": \"Codice 102\",    \"annoRiferimento\": 1990,    \"idPraticaPadre\": 1,    \"statoPratica\": {\"idStatoPratica\": 1},    \"dataApertura\": \"2002-10-19\",    \"dataChiusura\": \"2019-10-19\",    \"recapito\": {\"idRecapito\": 6728},    \"processo\": {\"idProcesso\": 22},    \"utente\": {\"idUtente\": 1},    \"comune\": {\"idComune\": 893},    \"dataRicezione\": \"2002-10-18\",    \"idStaging\": 1,    \"idModello\": 1,    \"responsabileProcedimento\": \"Marco\",    \"dataProtocollazione\": \"2002-10-25\",    \"dataVecchiaProtocollazione\": \"2022-10-23\",    \"giorniSospensione\": 10,    \"statoEmail\": 1,    \"procedimentoEnte\": {\"idProcedimentoEnte\": 1509},    \"ufficioRiferimento\": \"Ufficio bari\",    \"identificativoEsterno\":\"boh 2.0\",    \"integrazione\": \"boh\",    \"dataProtSuap\": \"2002-11-12\",    \"protSuap\": \"boh\",    \"idTipoProcedimentoSuap\": 1,    \"idTipoInterventoSuap\": 1}", tokenAccess);
		} catch (Exception var10) {
			var10.printStackTrace();
		}

		String paramUrl = "https://9fbd7739-e8a2-4738-8316-09c45e1cf312.mock.pstmn.io";
		String parameters = "{\"id\":100}";
		String results = "";
		System.out.println("Results." + results);
	}
}
