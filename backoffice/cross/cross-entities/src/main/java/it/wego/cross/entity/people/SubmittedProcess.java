package it.wego.cross.entity.people;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "submitted_process")
@NamedQueries({
    @NamedQuery(name = "get_submitted_process_by_people_protocol_id", query = "SELECT e from SubmittedProcess e where e.peopleProtocolId = :people_protocol_id"),  
    @NamedQuery(name = "get_technician_by_people_protocol_id", query = "SELECT e.userId from SubmittedProcess e where e.peopleProtocolId = :people_protocol_id"),  
})
public class SubmittedProcess {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "oid")
	private int oid;
	@Column(name = "editable_process_id")
	private int editableProcessId;
	@Column(name = "user_id")
	private String userId;
	@Column(name = "people_protocol_id")
	private String peopleProtocolId;
	@Column(name = "commune_protocol_id")
	private String communeProtocolId;
	@Column(name = "commune_id")
	private String communeId;
//	@Column(name = "submitted_time")
//	private Datetime submittedTime;
	@Column(name = "send_error")
	private int sendError;
	@Column(name = "completed")
	private int completed;
	@Column(name = "transport_trackingnumber")
	private String transportTrackingnumber;
	@Column(name = "sign_on_line_enabled")
	private int signOnLineEnabled;
	@Column(name = "sign_off_line_enabled")
	private int signOffLineEnabled;
	@Column(name = "sign_required")
	private int signRequired;
	@Column(name = "on_line_signed")
	private int onLineSigned;
	@Column(name = "off_line_signed")
	private int offLineSigned;
	@Column(name = "delegate")
	private int delegate;
	
	public int getOid() {
		return oid;
	}
	public void setOid(int oid) {
		this.oid = oid;
	}
	public int getEditableProcessId() {
		return editableProcessId;
	}
	public void setEditableProcessId(int editableProcessId) {
		this.editableProcessId = editableProcessId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPeopleProtocolId() {
		return peopleProtocolId;
	}
	public void setPeopleProtocolId(String peopleProtocolId) {
		this.peopleProtocolId = peopleProtocolId;
	}
	public String getCommuneProtocolId() {
		return communeProtocolId;
	}
	public void setCommuneProtocolId(String communeProtocolId) {
		this.communeProtocolId = communeProtocolId;
	}
	public String getCommuneId() {
		return communeId;
	}
	public void setCommuneId(String communeId) {
		this.communeId = communeId;
	}
	public int getSendError() {
		return sendError;
	}
	public void setSendError(int sendError) {
		this.sendError = sendError;
	}
	public int getCompleted() {
		return completed;
	}
	public void setCompleted(int completed) {
		this.completed = completed;
	}
	public String getTransportTrackingnumber() {
		return transportTrackingnumber;
	}
	public void setTransportTrackingnumber(String transportTrackingnumber) {
		this.transportTrackingnumber = transportTrackingnumber;
	}
	public int getSignOnLineEnabled() {
		return signOnLineEnabled;
	}
	public void setSignOnLineEnabled(int signOnLineEnabled) {
		this.signOnLineEnabled = signOnLineEnabled;
	}
	public int getSignOffLineEnabled() {
		return signOffLineEnabled;
	}
	public void setSignOffLineEnabled(int signOffLineEnabled) {
		this.signOffLineEnabled = signOffLineEnabled;
	}
	public int getSignRequired() {
		return signRequired;
	}
	public void setSignRequired(int signRequired) {
		this.signRequired = signRequired;
	}
	public int getOnLineSigned() {
		return onLineSigned;
	}
	public void setOnLineSigned(int onLineSigned) {
		this.onLineSigned = onLineSigned;
	}
	public int getOffLineSigned() {
		return offLineSigned;
	}
	public void setOffLineSigned(int offLineSigned) {
		this.offLineSigned = offLineSigned;
	}
	public int getDelegate() {
		return delegate;
	}
	public void setDelegate(int delegate) {
		this.delegate = delegate;
	}
}
