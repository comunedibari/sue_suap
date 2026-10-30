package it.wego.cross.webservices.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.wego.cross.dao.LookupDao;
import it.wego.cross.entity.LkTipoPratica;
import it.wego.cross.entity.Pratica;

@Component
public class CustomPraticaHandlerImpl implements CustomPraticaHandler{

	@Autowired 
	private LookupDao lookupDao;

	private static final String CUSTOM_1_CADASTRAL_CODE = "E038";

	private static final String CUSTOM_1_SUBJECT_1 = "Comunicazione di fine lavori, Segnalazione Certificata per Agibilità";
	private static final String CUSTOM_1_SUBJECT_2 = "Comunicazione di fine lavori";
	private static final String CUSTOM_1_SUBJECT_3 = "Comunicazione Inizio Lavori art. 6, comma 1, lett. e-bis del d.P.R. n. 380/2001 e n. 26 Tabella A, Sez. II del d.lgs  n. 222/2016";

	private final Integer CUSTOM_1_PROCESS_1 = 8;
	private final Integer CUSTOM_1_PROCESS_2 = 9;
	private final Integer CUSTOM_1_PROCESS_3 = 9;

	private final String CUSTOM_1_NEW_TYPE_1 = "AGIB";
	private final String CUSTOM_1_NEW_TYPE_2 = "FINLAV";
	private final String CUSTOM_1_NEW_TYPE_3 = "CIL";
	
	public CustomPraticaHandlerImpl() {}
	
	public CustomPraticaHandlerImpl(LookupDao lookupDao) {
		this.lookupDao = lookupDao;
	}
	
	public LkTipoPratica searchNewType(Pratica pratica) {
		LkTipoPratica newType = getNewType(pratica, CUSTOM_1_NEW_TYPE_1, CUSTOM_1_CADASTRAL_CODE, CUSTOM_1_PROCESS_1, CUSTOM_1_SUBJECT_1);
		newType = newType != null ? newType : getNewType(pratica, CUSTOM_1_NEW_TYPE_2, CUSTOM_1_CADASTRAL_CODE, CUSTOM_1_PROCESS_2, CUSTOM_1_SUBJECT_2);
		newType = newType != null ? newType : getNewType(pratica, CUSTOM_1_NEW_TYPE_3, CUSTOM_1_CADASTRAL_CODE, CUSTOM_1_PROCESS_3, CUSTOM_1_SUBJECT_3);
		return newType;
	}
	
	private LkTipoPratica getNewType(Pratica pratica, String newType, String cadastralCode, Integer type, String subject) {
		LkTipoPratica lkTipoPratica = null;
		if(compare(pratica, cadastralCode, type, subject))
			lkTipoPratica = lookupDao.findLkTipoPraticaByType(newType);
		return lkTipoPratica;
	}
	
	private boolean compare(Pratica pratica, String cadastralCode, Integer type, String subject) {
		String townId = pratica.getIdComune().getCodCatastale();
		boolean check = cadastralCode.equals(townId);
		check &= subject.equalsIgnoreCase(pratica.getOggettoPratica());
		check &= type.equals(pratica.getIdProcesso().getIdProcesso());
		return check;
	}
}
