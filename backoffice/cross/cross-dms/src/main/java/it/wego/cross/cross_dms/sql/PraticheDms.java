package it.wego.cross.cross_dms.sql;

import it.wego.cross.cross_dms.sql.TypeMetadata;

public class PraticheDms {
	private String name;
	private String id;
	private String owner;
	private float version;
	private boolean privato;
	private float typeId;
	TypeMetadata TypeMetadataObject;

	public TypeMetadata getTypeMetadata() {
		return TypeMetadataObject;
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}

	public String getOwner() {
		return owner;
	}

	public float getVersion() {
		return version;
	}

	public boolean getPrivate() {
		return privato;
	}

	public float getTypeId() {
		return typeId;
	}


	// Setter Methods 

	public void setName(String name) {
		this.name = name;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public void setVersion(float version) {
		this.version = version;
	}

	public void setPrivate(boolean privato) {
		this.privato = privato;
	}

	public void setTypeId(float typeId) {
		this.typeId = typeId;
	}

	public void setTypeMetadata(TypeMetadata typeMetadataObject) {
		this.TypeMetadataObject = typeMetadataObject;
	}
}

