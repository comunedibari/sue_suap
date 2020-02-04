package it.people.console.domain;

/**
 * @author gguidi - Jun 21, 2013
 * 
 */
public class UserNotificationProcessBean extends AbstractBaseBean implements
		Clearable {

	private static final long serialVersionUID = -7609045230774866470L;
	
	private String name;
	private String lastname;
	private String email;
	//private String municipalityCode;
	private String from;
	private String to;
	private String sort;
	private String column;
	
	private String error;
	
	public UserNotificationProcessBean() {}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

//	public String getMunicipalityCode() {
//		return municipalityCode;
//	}
//
//	public void setMunicipalityCode(String municipalityCode) {
//		this.municipalityCode = municipalityCode;
//	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.people.console.domain.Clearable#clear()
	 */
	@Override
	public void clear() {
		this.name = null;
		this.lastname = null;
		this.email = null;
//		this.municipalityCode = null;
		this.error = null;
		this.from = null;
		this.to = null;
		this.sort = null;
		this.column = null;
	}

}
