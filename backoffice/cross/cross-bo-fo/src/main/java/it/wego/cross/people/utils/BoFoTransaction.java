package it.wego.cross.people.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;


public abstract class BoFoTransaction {
	
	public static LogClass logger;
	
	private EntityManager em;

	public BoFoTransaction(EntityManager em){
		this.em = em;
	}
	
	public abstract void operations();
	
	public void execute() {
		EntityTransaction entityTransaction = em.getTransaction();
		entityTransaction.begin();

		try {
			operations();
			em.getTransaction().commit();
		}catch(Exception e) {
			LogClass.error(e.getMessage());
			em.getTransaction().rollback();
			throw new RuntimeException(e);
		}
		em.close();

	}
}
