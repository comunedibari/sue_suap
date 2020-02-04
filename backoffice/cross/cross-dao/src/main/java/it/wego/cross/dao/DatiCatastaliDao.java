package it.wego.cross.dao;

import it.wego.cross.entity.LkTipoSistemaCatastale;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

@Repository
public class DatiCatastaliDao {

    @PersistenceContext
    private EntityManager em;

//        public LkTipoSistemaCatastale findSistemaCatastale(int idTipoSistemaCatastale) {
//        Query query = em.createQuery("SELECT l "
//                + "FROM LkTipoSistemaCatastale l "
//                + "WHERE l.idTipoSistemaCatastale = :idTipoSistemaCatastale");
//        query.setParameter("idTipoSistemaCatastale", idTipoSistemaCatastale);
//        
//        List<LkTipoSistemaCatastale> resultList = query.getResultList();
//        return resultList.isEmpty()?null:resultList.get(0);
//    }
//    
}
