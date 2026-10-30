package it.wego.cross.beans.allegati;

//UploadInfo.java

import java.util.UUID;

public class UploadInfo {
	private UUID uuid;
	private String name;
	private long size;
	private String type;
	private long progress;
	private String repository;
	private UploadInfoTypeMetadata typeMetadata;

	public UUID getUUID() { return uuid; }
	public void setUUID(UUID value) { this.uuid = value; }

	public String getName() { return name; }
	public void setName(String value) { this.name = value; }

	public long getSize() { return size; }
	public void setSize(long value) { this.size = value; }

	public String getType() { return type; }
	public void setType(String value) { this.type = value; }

	public long getProgress() { return progress; }
	public void setProgress(long value) { this.progress = value; }

	public String getRepository() { return repository; }
	public void setRepository(String value) { this.repository = value; }

	public UploadInfoTypeMetadata getTypeMetadata() { return typeMetadata; }
	public void setTypeMetadata(UploadInfoTypeMetadata value) { this.typeMetadata = value; }
}
