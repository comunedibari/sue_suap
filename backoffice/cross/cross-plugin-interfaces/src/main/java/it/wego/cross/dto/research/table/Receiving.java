package it.wego.cross.dto.research.table;

import java.io.Serializable;
import java.util.Date;

import it.wego.cross.dto.AbstractDTO;

public class Receiving extends AbstractDTO implements Serializable {
	
	private Date receivingDate;
	private Date protocolDate;
	private String protocolNumber;
	
	public Date getReceivingDate() {
		return receivingDate;
	}
	public void setReceivingDate(Date receivingDate) {
		this.receivingDate = receivingDate;
	}
	public Date getProtocolDate() {
		return protocolDate;
	}
	public void setProtocolDate(Date protocolDate) {
		this.protocolDate = protocolDate;
	}
	public String getProtocolNumber() {
		return protocolNumber;
	}
	public void setProtocolNumber(String protocolNumber) {
		this.protocolNumber = protocolNumber;
	}
}
