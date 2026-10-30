//import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
//import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
/* ObjectMapper om = new ObjectMapper();
Root[] root = om.readValue(myJsonString, Root[].class); */

package it.wego.cross.cross_beans;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;


public class PraticaDMS{
	public String owner;
	public int version;
	public Object size;
	public TypeMetadata typeMetadata;
	public String id;
	public Date createdAt;
	public Object updatedAt;
	public String name;
	public int typeId;
	
	@JsonProperty("private") 
	public boolean myprivate;
	
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public Object getSize() {
		return size;
	}
	public void setSize(Object size) {
		this.size = size;
	}
	public TypeMetadata getTypeMetadata() {
		return typeMetadata;
	}
	public void setTypeMetadata(TypeMetadata typeMetadata) {
		this.typeMetadata = typeMetadata;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Object getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Object updatedAt) {
		this.updatedAt = updatedAt;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	public boolean isMyprivate() {
		return myprivate;
	}
	public void setMyprivate(boolean myprivate) {
		this.myprivate = myprivate;
	}

}
