package dms.service;

public class GetDettaglioPratica {
	private static final String RESPONSE = "{ "
	
	+ "}}}";
	
	private String id;
	private String request;
	private String response;
	
	public GetDettaglioPratica(String id) {
		super();
		this.id = id;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRequest() {
		return request;
	}
	public void setRequest(String request) {
		this.request = request;
	}
	public String getResponse() {
		return RESPONSE;
	}
	public void setResponse(String response) {
		this.response = response;
	}
}
