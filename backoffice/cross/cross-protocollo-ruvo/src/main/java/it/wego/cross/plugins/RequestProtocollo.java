package it.wego.cross.plugins;

public class RequestProtocollo {
	private String XmlRequest;
	
	private Object[] attachment;
	
	private it.wego.auriga.xml.NewUD newud;
	
	/**
	 * @return the xmlRequest
	 */
	public String getXmlRequest() {
		return XmlRequest;
	}

	/**
	 * @param xmlRequest the xmlRequest to set
	 */
	public void setXmlRequest(String xmlRequest) {
		XmlRequest = xmlRequest;
	}

	/**
	 * @return the attachment
	 */
	public Object[] getAttachment() {
		return attachment;
	}

	/**
	 * @param attachment the attachment to set
	 */
	public void setAttachment(Object[] attachment) {		
		this.attachment = attachment;
	}
	
	/**
	 * @return the newud
	 */
	public it.wego.auriga.xml.NewUD getNewud() {
		return newud;
	}

	/**
	 * @param newud the newud to set
	 */
	public void setNewud(it.wego.auriga.xml.NewUD newud) {
		this.newud = newud;
	}

}
