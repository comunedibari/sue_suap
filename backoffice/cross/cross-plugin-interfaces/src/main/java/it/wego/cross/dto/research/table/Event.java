package it.wego.cross.dto.research.table;

import java.io.Serializable;
import java.util.Date;

import it.wego.cross.dto.AbstractDTO;

public class Event extends AbstractDTO implements Serializable {
	private Integer id;
	private String type;
	private Date date;
	private String protocolNumber;
	private Date protocolDate;
	private String operator;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getProtocolNumber() {
		return protocolNumber;
	}
	public void setProtocolNumber(String protocolNumber) {
		this.protocolNumber = protocolNumber;
	}
	public Date getProtocolDate() {
		return protocolDate;
	}
	public void setProtocolDate(Date protocolDate) {
		this.protocolDate = protocolDate;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	
}
