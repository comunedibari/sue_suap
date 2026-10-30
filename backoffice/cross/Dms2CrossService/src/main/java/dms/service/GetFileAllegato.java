package dms.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.net.URL;

import dms.utilities.myUtility;

public class GetFileAllegato {
	private String id_allegato;
	private String request;
	private String response;

	public GetFileAllegato(String id_allegato) {
		super();
		this.id_allegato = id_allegato;
	}

	public String getId_allegato() {
		return id_allegato;
	}

	public void setId_allegato(String id_allegato) {
		this.id_allegato = id_allegato;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getResponse() {
		String content = "";
		try {
//			File fileallegato = getFileFromResource("file_allegato_pratica.csv");
			myUtility utl = new myUtility();
			String fileAllegato = utl.getValueProperties("allegatopratica");
		
			File fileallegato = new File(fileAllegato);
			
			BufferedReader reader = new BufferedReader(new FileReader(fileallegato));
			StringBuilder stringBuilder = new StringBuilder();
			String line = null;
			String ls = System.getProperty("line.separator");
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line);
				stringBuilder.append(ls);
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			reader.close();

			content = stringBuilder.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content;
	}

	private File getFileFromResource(String fileName) throws URISyntaxException{

		ClassLoader classLoader = getClass().getClassLoader();
		URL resource = classLoader.getResource(fileName);
		if (resource == null) {
			throw new IllegalArgumentException("file not found! " + fileName);
		} else {
			String resourceURI = resource.toURI().toString();
			System.out.println("ALLEGATO:" + resourceURI);
			return new File(resource.toURI());
		}

	}

	public void setResponse(String response) {
		this.response = response;
	}

	public static void main(String[] args) {
		GetFileAllegato alp = new GetFileAllegato("1");
		String resp = alp.getResponse();
	}
}
