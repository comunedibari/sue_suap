package dms.service;

public class RicercaPratiche {
	private static final String RESPONSE = "{ "
	
	+ "}}";
	
	private String filter;
	private String offset;
	private String limit;
	private String sort;
	private String response;
	
	public RicercaPratiche(String filter, String offset, String limit, String sort) {
		super();
		this.filter = filter;
		this.offset = offset;
		this.limit = limit;
		this.sort = sort;
	}
	public String getFilter() {
		return filter;
	}
	public void setFilter(String filter) {
		this.filter = filter;
	}
	public String getOffset() {
		return offset;
	}
	public void setOffset(String offset) {
		this.offset = offset;
	}
	public String getLimit() {
		return limit;
	}
	public void setLimit(String limit) {
		this.limit = limit;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public static String getResponse() {
		return RESPONSE;
	}
}
