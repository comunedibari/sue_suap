package it.wego.cross.utils;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

public class LazyJoin<T> {

	private String id;
	private JoinType type;
	private Root<T> root;
	private Join join;
	
	public LazyJoin(Root<T> root, String id, JoinType type) {
		super();
		this.id = id;
		this.type = type;
	}

	public Join get() {
		if(join == null) 
			join = root.join(id, type);
		return join;
	}
	
}
