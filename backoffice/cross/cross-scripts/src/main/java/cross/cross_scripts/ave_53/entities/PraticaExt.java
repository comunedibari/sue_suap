package cross.cross_scripts.ave_53.entities;

import it.wego.cross.entity.Pratica;

public class PraticaExt extends Pratica{

	private java.sql.Date receivingDate;
	private Integer stagingId;
	
	public java.sql.Date getReceivingDate() {
		return receivingDate;
	}
	public void setReceivingDate(java.sql.Date receivingDate) {
		this.receivingDate = receivingDate;
	}
	public Integer getStagingId() {
		return stagingId;
	}
	public void setStagingId(Integer stagingId) {
		this.stagingId = stagingId;
	}
}
