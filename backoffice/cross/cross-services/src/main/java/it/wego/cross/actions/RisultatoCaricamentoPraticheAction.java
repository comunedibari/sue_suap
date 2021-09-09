package it.wego.cross.actions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import it.wego.cross.beans.grid.GridRisultatoCaricamentoPraticaBean;
import it.wego.cross.beans.layout.JqgridPaginator;
import it.wego.cross.dao.RisultatoCaricamentoPraticheDao;
import it.wego.cross.dto.RisultatoCaricamentoPraticheDTO;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.RisultatoCaricamentoPratiche;
import it.wego.cross.entity.Utente;
import it.wego.cross.serializer.RisultatoCaricamentoPraticheSerializer;
import it.wego.cross.service.RisultatoCaricamentoPraticheService;
import it.wego.cross.service.UsefulService;
@Component
public class RisultatoCaricamentoPraticheAction {
	@Autowired
    private RisultatoCaricamentoPraticheDao risultatoCaricamentoPraticheDao;
	@Autowired
	private RisultatoCaricamentoPraticheSerializer risultatoCaricamentoPraticheSerializer;
	
    
	@Transactional(rollbackFor = Exception.class)
	public void popolaRisultatoCaricamentoPratiche(String esito, String errore,Pratica pratica,Utente utente,String identificativoPratica,String nomeFileCaricato) throws Exception {
		try {
		RisultatoCaricamentoPratiche risultatoCaricamentoPratiche = new RisultatoCaricamentoPratiche();
        
        risultatoCaricamentoPratiche.setDescrizioneErrore(errore);
        risultatoCaricamentoPratiche.setEsitoCaricamento(esito);
        if(pratica!=null) {
        	risultatoCaricamentoPratiche.setIdentificativoPratica(pratica.getIdentificativoPratica());
        	risultatoCaricamentoPratiche.setIdPratica(pratica);
        	risultatoCaricamentoPratiche.setDataCaricamento(pratica.getDataRicezione());
        }else {
        	risultatoCaricamentoPratiche.setIdentificativoPratica(identificativoPratica);
        }
        risultatoCaricamentoPratiche.setIdUtente(utente);
        risultatoCaricamentoPratiche.setNomeFileCaricato(nomeFileCaricato);
        risultatoCaricamentoPraticheDao.insert(risultatoCaricamentoPratiche);
        risultatoCaricamentoPraticheDao.flush();
		}catch(Exception e) {
			throw e;
		}
	}
	
	public GridRisultatoCaricamentoPraticaBean getListaCaricamentoPratiche(HttpServletRequest request, JqgridPaginator paginator) throws Exception {
		GridRisultatoCaricamentoPraticaBean json = new GridRisultatoCaricamentoPraticaBean();
        try {
		
        	String sord = paginator.getSord();
            Boolean order = true;
            if ("asc".equals(sord)) {
                order = false;
            }
            Integer maxResult = Integer.parseInt(paginator.getRows());
            Integer page = Integer.parseInt(paginator.getPage());
            Integer firstRecord = (page * maxResult) - maxResult;
        	
        
        //request.getSession().setAttribute("praticheGestisci", filter);
        Long countRighe = risultatoCaricamentoPraticheDao.countAllRisultatoCaricamentoPratiche();
        List<RisultatoCaricamentoPraticheDTO> praticheInserite = new ArrayList<RisultatoCaricamentoPraticheDTO>();
        Integer lastRecord = (int) ((firstRecord + maxResult < countRighe) ? firstRecord + maxResult : countRighe);
        Integer totalRecords = countRighe.intValue();
        totalRecords = (totalRecords / maxResult == 0) ? 1 : ((totalRecords % maxResult == 0) ? (totalRecords / maxResult) : (totalRecords / maxResult + 1));
        json.setPage(page);
        json.setRecords(countRighe.intValue());
        json.setTotal(totalRecords);
        List<RisultatoCaricamentoPratiche> rPraticheInserite  = risultatoCaricamentoPraticheDao.findAll();//praticheService.findFiltrate(filter);
        for (Integer i = firstRecord; i < lastRecord; i++) {
        	RisultatoCaricamentoPraticheDTO dto = new RisultatoCaricamentoPraticheDTO();
        	dto = risultatoCaricamentoPraticheSerializer.serialize(rPraticheInserite.get(i));
        	praticheInserite.add(dto);
        }
        
        
        json.setRows(praticheInserite);
        
        
        }catch(Exception e) {
        	throw e;
        }
        return json;
    }


}
