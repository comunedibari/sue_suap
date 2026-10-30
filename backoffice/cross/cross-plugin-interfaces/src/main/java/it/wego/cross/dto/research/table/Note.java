package it.wego.cross.dto.research.table;

import java.io.Serializable;
import java.util.Date;

import it.wego.cross.dto.AbstractDTO;

public class Note extends AbstractDTO implements Serializable{
	private Date date;
	private String user;
	private String content;
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
