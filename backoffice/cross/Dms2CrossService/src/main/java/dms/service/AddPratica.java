package dms.service;

public class AddPratica {
	private int statusCode;
	private String request;
	private String response;

	
	public AddPratica(String request) {
		super();
		this.request = request;
	}
	
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getRequest() {
		return request;
	}
	public void setRequest(String request) {
		this.request = request;
	}
	public String getResponse() {
		String risposta = "{\"result\" = \"OK\"}";
		return risposta;
	}
	public void setResponse(String response) {
		this.response = response;
	}
}
