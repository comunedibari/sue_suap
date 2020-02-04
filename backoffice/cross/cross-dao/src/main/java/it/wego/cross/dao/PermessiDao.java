package it.wego.cross.dao;

import it.wego.cross.dto.PermessiEnteProcedimentoDTO;
import it.wego.cross.entity.Permessi;
import it.wego.cross.entity.ProcedimentiEnti;
import it.wego.cross.entity.Utente;
import it.wego.cross.entity.view.ProcedimentiLocalizzatiView;
import it.wego.cross.utils.Utils;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

//@Configurable
@Repository
public class PermessiDao {

    @Autowired
    private ProcedimentiDao procedimentiDao;
    @PersistenceContext
    private EntityManager em;

    public List<Permessi> findAll() {
        Query query = em.createNamedQuery("Permessi.findAll");
        List<Permessi> permessi = query.getResultList();
        return permessi;
    }

    public Permessi findByCodPermesso(String codPermesso) {
        Query query = em.createNamedQuery("Permessi.findByCodPermesso");
        query.setParameter("codPermesso", codPermesso);
        Permessi permesso = (Permessi) Utils.getSingleResult(query);
        return permesso;
    }

    public PermessiEnteProcedimentoDTO getPermesso(ProcedimentiLocalizzatiView procedimento, int codEnte) {
        PermessiEnteProcedimentoDTO bean = new PermessiEnteProcedimentoDTO();
        bean.setCodProc(procedimento.getCodProc());
        bean.setDesProc(procedimento.getDesProc());
        bean.setIdProc(procedimento.getIdProc());
        bean.setTermini(procedimento.getTermini());
        

        ProcedimentiEnti pe = procedimentiDao.findProcedimentiEntiByProcedimentoEnte(codEnte, procedimento.getIdProc());
        bean.setAbilitato(pe != null);
        if (pe != null && pe.getIdProcesso() != null) {
            bean.setProcesso(pe.getIdProcesso().getDesProcesso());
            bean.setResponsabileProcedimento(pe.getResponsabileProcedimento() == null ? "":pe.getResponsabileProcedimento());
        } else {
            bean.setProcesso("");
            bean.setResponsabileProcedimento("");
        }
        return bean;
    }

}
