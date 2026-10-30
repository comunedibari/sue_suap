package it.wego.cross.entity.people;

import javax.persistence.*;

@Entity
@Table(name = "pending_process")
@NamedQueries({
    @NamedQuery(name = "get_pending_process_by_process_data_id", query = "SELECT e from PendingProcess e where e.processDataId = :process_data_id"),  
    @NamedQuery(name = "get_technician_by_process_data_id", query = "SELECT e.userId from PendingProcess e where e.processDataId = :process_data_id"),  
})
public class PendingProcess {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "oid")
	private int oid;
	@Column(name = "process_data_id")
	private String processDataId;
	@Column(name = "user_id")
	private String userId;
	@Column(name = "commune_id")
	private String communeId;
	@Column(name = "process_classname")
	private String processClassname;
	@Column(name = "process_value")
	private String processValue;
	@Column(name = "sent")
	private int sent;
	@Column(name = "status")
	private int status;
	@Column(name = "content_name")
	private String contentName;
	@Column(name = "content_id")
	private String contentId;
	@Column(name = "process_name")
	private String processName;
	@Column(name = "off_line_sign_document_hash")
	private String offLineSignDocumentHash;
	@Column(name = "off_line_sign_waiting")
	private int offLineSignWaiting;

	public int getOid() {
		return oid;
	}
	public void setOid(int oid) {
		this.oid = oid;
	}
	public String getProcessDataId() {
		return processDataId;
	}
	public void setProcessDataId(String processDataId) {
		this.processDataId = processDataId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCommuneId() {
		return communeId;
	}
	public void setCommuneId(String communeId) {
		this.communeId = communeId;
	}
	public String getProcessClassname() {
		return processClassname;
	}
	public void setProcessClassname(String processClassname) {
		this.processClassname = processClassname;
	}
	public String getProcessValue() {
		return processValue;
	}
	public void setProcessValue(String processValue) {
		this.processValue = processValue;
	}
	public int getSent() {
		return sent;
	}
	public void setSent(int sent) {
		this.sent = sent;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getContentName() {
		return contentName;
	}
	public void setContentName(String contentName) {
		this.contentName = contentName;
	}
	public String getContentId() {
		return contentId;
	}
	public void setContentId(String contentId) {
		this.contentId = contentId;
	}
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public String getOffLineSignDocumentHash() {
		return offLineSignDocumentHash;
	}
	public void setOffLineSignDocumentHash(String offLineSignDocumentHash) {
		this.offLineSignDocumentHash = offLineSignDocumentHash;
	}
	public int getOffLineSignWaiting() {
		return offLineSignWaiting;
	}
	public void setOffLineSignWaiting(int offLineSignWaiting) {
		this.offLineSignWaiting = offLineSignWaiting;
	}
	@Override
	public String toString() {
		return "PendingProcess [oid=" + oid + ", processDataId=" + processDataId + ", userId=" + userId + ", communeId="
				+ communeId + ", processClassname=" + processClassname + ", processValue=" + processValue + ", sent="
				+ sent + ", status=" + status + ", contentName=" + contentName + ", contentId=" + contentId
				+ ", processName=" + processName + ", offLineSignDocumentHash=" + offLineSignDocumentHash
				+ ", offLineSignWaiting=" + offLineSignWaiting + "]";
	}


}
