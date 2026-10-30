package it.wego.cross.dao;

import javax.persistence.EntityManager;

public class PraticaDaoTest extends PraticaDao{

	public PraticaDaoTest(EntityManager em) {
		super(em);
	}
	
	public String getSql() {
		return this.sql;
	}
}