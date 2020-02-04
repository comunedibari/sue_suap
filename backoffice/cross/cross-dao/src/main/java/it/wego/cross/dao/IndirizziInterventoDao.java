package it.wego.cross.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class IndirizziInterventoDao {

    @PersistenceContext
    private EntityManager em;
    
}
