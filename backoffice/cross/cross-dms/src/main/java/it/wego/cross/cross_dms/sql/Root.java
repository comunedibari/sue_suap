package it.wego.cross.cross_dms.sql;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
/*
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
*/
public class Root {
	public String name;
    public String id;
    public String owner;
    public int version;
    @JsonProperty("private") 
    public boolean myprivate;
    public int typeId;
    public TypeMetadata typeMetadata;
    
    public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
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
	public boolean isMyprivate() {
		return myprivate;
	}
	public void setMyprivate(boolean myprivate) {
		this.myprivate = myprivate;
	}
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	public TypeMetadata getTypeMetadata() {
		return typeMetadata;
	}
	public void setTypeMetadata(TypeMetadata typeMetadata) {
		this.typeMetadata = typeMetadata;
	}
	
	public static void main(String[] args) {
		
		String fileName = "json/jsonPraticaDms.json";
		try {
			ObjectMapper om = new ObjectMapper();
			String jsonResult = new String(Files.readAllBytes(Paths.get(fileName)), StandardCharsets.UTF_8);
			Root root = om.readValue(jsonResult, Root.class);
			System.out.println("Name:" + root.getName());
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
