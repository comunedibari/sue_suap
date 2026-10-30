package it.wego.cross.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.wego.cross.dao.RisultatoCaricamentoPraticheDao;
import it.wego.cross.dto.AllegatoDTO;
import it.wego.cross.entity.DatiCatastali;
import it.wego.cross.entity.Pratica;
import it.wego.cross.utils.Utils;

@Component
public class CiviliaServiceImpl implements CiviliaService{
	
	@Autowired 
	RisultatoCaricamentoPraticheDao risultatoCaricamentoPraticheDao;
	
	public final static String CIVILIA_DATE_RECOGNITION = "2019-01-01";
	
	public List<AllegatoDTO> filterAttachmentsByDescription(List<AllegatoDTO> allegatiCivilia, List<AllegatoDTO> allegatiCross, List<AllegatoDTO> allegatiDMS){
		List<AllegatoDTO> filteredAllegati = new LinkedList<>();
		for(AllegatoDTO allegatoCivilia: allegatiCivilia) {
        	boolean found = false;
        	int i = 0;
        	String descrizione = allegatoCivilia.getDescrizione();
        	if(!Utils.e(descrizione)) {
	        	while(!found && i<allegatiCross.size()) {
	        		found = descrizione.equals((allegatiCross.get(i++).getDescrizione())); 
	        	}
	        	i=0;
	        	while(!found && i<allegatiDMS.size()) {
	        		found = descrizione.equals((allegatiDMS.get(i++).getDescrizione())); 
	        	}
        	}
        	if(!found)
        		filteredAllegati.add(allegatoCivilia);
        }
		return filteredAllegati;
	}
	
	public List<DatiCatastali> filterCadastralData(List<DatiCatastali> civilia, List<DatiCatastali> cross, List<DatiCatastali> dms){
		List<DatiCatastali> cadastralDataFiltered = new LinkedList<>();
		for(DatiCatastali currentCivilia: civilia) {
        	boolean found = false;
        	int i = 0;
        	while(!found && i<cross.size()) {
        		found = cadastralDataEquals(currentCivilia, cross.get(i++));
        	}
        	i=0;
        	while(!found && i<dms.size()) {
        		found = cadastralDataEquals(currentCivilia, dms.get(i++));
        	}
        	
        	if(!found)
        		cadastralDataFiltered.add(currentCivilia);
        }
		return cadastralDataFiltered;
	}
	
	private boolean cadastralDataEquals(DatiCatastali a, DatiCatastali b) {
		String foglioA = a.getFoglio() == null? "" : a.getFoglio();
    	String mappaleA = a.getMappale() == null? "" : a.getMappale();
    	String subalternoA = a.getSubalterno() == null? "" : a.getSubalterno();
    	String foglioB = b.getFoglio() == null? "" : b.getFoglio();
    	String mappaleB = b.getMappale() == null? "" : b.getMappale();
    	String subalternoB = b.getSubalterno() == null? "" : b.getSubalterno();
    	
    	return foglioA.equals(foglioB) && mappaleA.equals(mappaleB) && subalternoA.equals(subalternoB);
	}
	
	public boolean isCivilia(Pratica pratica) {
		return risultatoCaricamentoPraticheDao.findRisultatoByPratica(pratica) != null;
	}
}