package it.wego.cross.utils;

import javax.persistence.criteria.Path;

public class SingleOrder{
	private Path path;
	private String direction;
	
	public SingleOrder(Path path, String direction) {
		super();
		this.path = path;
		this.direction = direction;
	}
	public Path getPath() {
		return path;
	}
	public String getDirection() {
		return direction;
	}

}