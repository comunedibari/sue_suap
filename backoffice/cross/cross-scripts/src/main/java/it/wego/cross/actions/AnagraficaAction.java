package it.wego.cross.actions;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import com.google.common.base.Strings;

import it.wego.cross.beans.grid.GridAnagraficaBean;
import it.wego.cross.constants.Constants;
import it.wego.cross.dao.AnagraficheDao;
import it.wego.cross.dao.LookupDao;
import it.wego.cross.dao.PraticaDao;
import it.wego.cross.dao.StagingDao;
import it.wego.cross.dto.AnagraficaDTO;
import it.wego.cross.dto.CittadinanzaDTO;
import it.wego.cross.dto.ComuneDTO;
import it.wego.cross.dto.NazionalitaDTO;
import it.wego.cross.dto.RecapitoDTO;
import it.wego.cross.entity.Anagrafica;
import it.wego.cross.entity.AnagraficaRecapiti;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.entity.LkProvincie;
import it.wego.cross.entity.LkTipoCollegio;
import it.wego.cross.entity.LkTipoIndirizzo;
import it.wego.cross.entity.LkTipoRuolo;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticaAnagrafica;
import it.wego.cross.entity.Recapiti;
import it.wego.cross.entity.Staging;
import it.wego.cross.entity.Utente;
import it.wego.cross.service.AnagraficheServiceImpl;
import it.wego.cross.service.LookupServiceImpl;
import it.wego.cross.service.UsefulServiceImpl;
import it.wego.cross.utils.AnagraficaUtils;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.PraticaUtils;
import it.wego.cross.utils.RecapitoUtils;
import it.wego.cross.utils.Utils;
import it.wego.cross.xml.Anagrafiche;

public class AnagraficaAction {
	private AnagraficheServiceImpl anagraficheService;
	private LookupServiceImpl lookupService;
	private LookupDao lookupDao;
	private PraticaDao praticaDao;
	private StagingDao stagingDao;
	private AnagraficheDao anagraficheDao;
	private AnagraficaUtils anagraficaUtils;
	private UsefulServiceImpl usefulService;
	
    public AnagraficaAction(AnagraficheServiceImpl anagraficheService, LookupServiceImpl lookupService,
			LookupDao lookupDao, PraticaDao praticaDao) {
		super();
		this.anagraficheService = anagraficheService;
		this.lookupService = lookupService;
		this.lookupDao = lookupDao;
		this.praticaDao = praticaDao;
	}

	public AnagraficaAction(AnagraficheServiceImpl anagraficheService, LookupServiceImpl lookupService,
			LookupDao lookupDao, PraticaDao praticaDao, StagingDao stagingDao, AnagraficheDao anagraficheDao,
			AnagraficaUtils anagraficaUtils, UsefulServiceImpl usefulService) {
		super();
		this.anagraficheService = anagraficheService;
		this.lookupService = lookupService;
		this.lookupDao = lookupDao;
		this.praticaDao = praticaDao;
		this.stagingDao = stagingDao;
		this.anagraficheDao = anagraficheDao;
		this.anagraficaUtils = anagraficaUtils;
		this.usefulService = usefulService;
	}



	public GridAnagraficaBean getListaAnagraficheDaConfrontare(AnagraficaDTO anagrafica, AnagraficaDTO anagraficaDB) throws Exception, NumberFormatException {
        GridAnagraficaBean json = new GridAnagraficaBean();
        List<AnagraficaDTO> anagrafiche = new ArrayList<AnagraficaDTO>();
        AnagraficaDTO anagraficaDto = anagraficheService.getAnagraficaXML(anagrafica);
        String desComuneNascita = "default";
        Integer idComuneNascita = -1;
        if (anagraficaDto.getComuneNascita() != null) {
            desComuneNascita = anagraficaDto.getComuneNascita().getDescrizione();
            idComuneNascita = anagraficaDto.getComuneNascita().getIdComune();
        }
        String desCittadinanza = "default";
        Integer idCittadinanza;
        if (anagraficaDto.getDesCittadinanza() != null) {
            desCittadinanza = anagraficaDto.getDesCittadinanza();
        }
        idCittadinanza = anagraficaDto.getIdCittadinanza();
        String desNazionalita = "default";
        Integer idNazionalita;
        if (anagraficaDto.getDesNazionalita() != null) {
            desNazionalita = anagraficaDto.getDesNazionalita();
        }
        idNazionalita = anagraficaDto.getIdNazionalita();
        ComuneDTO comuneDB;
        if (idComuneNascita != null) {
            comuneDB = lookupService.getComuneDTOFromId(idComuneNascita);
        } else {
            comuneDB = lookupService.getComuneDTOFromNomePreciso(desComuneNascita);
        }
        CittadinanzaDTO cittadinanzaDB;
        if (idCittadinanza != null) {
            cittadinanzaDB = lookupService.getCittadinanzaFromId(idCittadinanza);
        } else {
            cittadinanzaDB = lookupService.getCittadinanzaFromDescrizionePrecisa(desCittadinanza);
        }
        NazionalitaDTO nazionalitaDB;
        if (idNazionalita != null) {
            nazionalitaDB = lookupService.getNazionalitaFromId(idNazionalita);
        } else {
            nazionalitaDB = lookupService.getNazionalitaFromDescrizionePrecisa(desNazionalita);
        }
        if (comuneDB != null) {
            anagraficaDto.setComuneNascita(comuneDB);
        }

        if (anagraficaDto.getProvinciaCciaa() != null) {
            if (anagraficaDto.getProvinciaCciaa().getIdProvincie() == null) {
                if (!Strings.isNullOrEmpty(anagraficaDto.getProvinciaCciaa().getCodCatastale())) {
                    LkProvincie provincia = lookupDao.findLkProvinciaByCod(anagraficaDto.getProvinciaCciaa().getCodCatastale(), Boolean.TRUE);
                    if (provincia != null) {
                        anagraficaDto.getProvinciaCciaa().setIdProvincie(provincia.getIdProvincie());
                        anagraficaDto.getProvinciaCciaa().setDescrizione(provincia.getDescrizione());
                    } else {
                        provincia = lookupDao.findLkProvinciaByDescPrecisa(anagraficaDto.getProvinciaCciaa().getCodCatastale(), Boolean.TRUE);
                        if (provincia != null) {
                            anagraficaDto.getProvinciaCciaa().setIdProvincie(provincia.getIdProvincie());
                            anagraficaDto.getProvinciaCciaa().setDescrizione(provincia.getDescrizione());
                        }
                    }
                } else if (Strings.isNullOrEmpty(anagraficaDto.getProvinciaCciaa().getCodCatastale())
                        && !Strings.isNullOrEmpty(anagraficaDto.getProvinciaCciaa().getDescrizione())) {
                    LkProvincie provincia = lookupDao.findLkProvinciaByDescPrecisa(anagraficaDto.getProvinciaCciaa().getDescrizione(), Boolean.TRUE);
                    if (provincia != null) {
                        anagraficaDto.getProvinciaCciaa().setIdProvincie(provincia.getIdProvincie());
                        anagraficaDto.getProvinciaCciaa().setDescrizione(provincia.getDescrizione());
                    }
                }
            }
        }

        if (cittadinanzaDB != null) {
            anagraficaDto.setDesCittadinanza(cittadinanzaDB.getDescrizione());
            anagraficaDto.setIdCittadinanza(cittadinanzaDB.getIdCittadinanza());
        }
        if (nazionalitaDB != null) {
            anagraficaDto.setDesNazionalita(nazionalitaDB.getDescrizione());
            anagraficaDto.setIdNazionalita(nazionalitaDB.getIdNazionalita());
        }
        if (anagraficaDto.getRecapiti() != null) {
            for (RecapitoDTO recapito : anagraficaDto.getRecapiti()) {
                String nomeComune = recapito.getDescComune();
                Integer idComune = recapito.getIdComune();
                ComuneDTO comuneRecapitoDB = null;
                if (recapito.getIdComune() != null) {
                    comuneRecapitoDB = lookupService.getComuneDTOFromId(idComune);
                }
                if (comuneRecapitoDB == null && !Strings.isNullOrEmpty(nomeComune)) {
                    comuneRecapitoDB = lookupService.getComuneDTOFromNomePreciso(nomeComune);
                }
                if (comuneRecapitoDB != null) {
                    recapito.setDescComune(comuneRecapitoDB.getDescrizione());
                    recapito.setDescProvincia(comuneRecapitoDB.getProvincia().getDescrizione());
                    recapito.setDescStato(comuneRecapitoDB.getStato().getDescrizione());
                    recapito.setIdComune(comuneRecapitoDB.getIdComune());
                    recapito.setIdProvincia(comuneRecapitoDB.getProvincia().getIdProvincie());
                    recapito.setIdStato(comuneRecapitoDB.getStato().getIdStato());
                }
                if (recapito.getIdTipoIndirizzo() != null && recapito.getDescTipoIndirizzo() == null) {
                    LkTipoIndirizzo tipoIndirizzo = lookupDao.findTipoIndirizzoById(recapito.getIdTipoIndirizzo());
                    recapito.setDescTipoIndirizzo(tipoIndirizzo.getDescrizione());
                }
            }
            mergeRecapiti(anagraficaDto, anagraficaDB);
        }

        PraticaAnagrafica collegamentoEsistente = praticaDao.findPraticaAnagraficaByKey(anagrafica.getIdPratica(), anagraficaDB.getIdAnagrafica(), anagraficaDto.getIdTipoRuolo());
        if (anagraficaDB.getVarianteAnagrafica() != null && anagraficaDB.getVarianteAnagrafica() != "G") {
            if (collegamentoEsistente != null && !Utils.e(collegamentoEsistente.getFlgDittaIndividuale())) {
                String varianteAnagrafica = collegamentoEsistente.getFlgDittaIndividuale().equalsIgnoreCase("S") ? "I" : "F";
                anagraficaDto.setVarianteAnagrafica(varianteAnagrafica);
                anagraficaDB.setVarianteAnagrafica(varianteAnagrafica);
            }
        }
        if (collegamentoEsistente != null) {
            anagraficaDB.setIdTipoRuoloOriginale(collegamentoEsistente.getLkTipoRuolo().getIdTipoRuolo());
        }
        //Allinea ruoli tra le 2 anangrafiche (in anagrafica da db non ho il ruolo)
        anagraficaDB.setIdTipoRuolo(anagraficaDto.getIdTipoRuolo());
        anagraficaDB.setDesTipoRuolo(anagraficaDto.getDesTipoRuolo());
        anagraficaDB.setCounter(anagraficaDto.getCounter());
        if (Utils.e(anagraficaDB.getTipoAnagrafica())) {
            anagraficaDB.setTipoAnagrafica(anagraficaDto.getTipoAnagrafica());
        }
        if (Utils.e(anagraficaDB.getVarianteAnagrafica())) {
            anagraficaDB.setVarianteAnagrafica(anagraficaDto.getVarianteAnagrafica());
        }
        anagraficaDB.setFlgIndividuale(anagraficaDto.getFlgIndividuale());
        anagrafiche.add(anagraficaDto);//^^CS anagraficaDB DEVE!!! essere sempre 0
        anagrafiche.add(anagraficaDB);//^^CS anagraficaXML DEVE!!! essere sempre 1
        json.setRows(anagrafiche);
        return json;
    }
    
    private void mergeRecapiti(AnagraficaDTO anagraficaXML, AnagraficaDTO anagraficaDB) {
        List<RecapitoDTO> recapitiXML = new ArrayList<RecapitoDTO>();
        List<RecapitoDTO> recapitiDB = new ArrayList<RecapitoDTO>();
        for (String tipo : Constants.ORDINE_RECAPITI) {
            LkTipoIndirizzo tipoIndirizzo = lookupDao.findTipoIndirizzoByDesc(tipo);
            if (tipoIndirizzo != null) {
                Integer idTipoIndirizzo = tipoIndirizzo.getIdTipoIndirizzo();
                Boolean trovatoXML = false;
                Boolean trovatoDB = false;
                int indexXML = 0;
                if (anagraficaXML.getRecapiti() != null) {
                    for (RecapitoDTO recapito : anagraficaXML.getRecapiti()) {
                        if ((recapito.getIdTipoIndirizzo() != null && recapito.getIdTipoIndirizzo().equals(idTipoIndirizzo))
                                || (recapito.getDescTipoIndirizzo() != null && recapito.getDescTipoIndirizzo().equalsIgnoreCase(tipo))) {
                            trovatoXML = true;
                            recapitiXML.add(recapito);
                            break;
                        }
                        indexXML++;
                    }
                }
                int indexDB = 0;
                if (anagraficaDB.getRecapiti() != null) {
                    for (RecapitoDTO recapito : anagraficaDB.getRecapiti()) {
                        if ((recapito.getIdTipoIndirizzo() != null && recapito.getIdTipoIndirizzo().equals(idTipoIndirizzo))
                                || (recapito.getDescTipoIndirizzo() != null && recapito.getDescTipoIndirizzo().equalsIgnoreCase(tipo))) {
                            trovatoDB = true;
                            recapitiDB.add(recapito);
                            break;
                        }
                        indexDB++;
                    }
                }
                if (trovatoDB || trovatoXML) {
                    if (!trovatoDB) {
                        RecapitoDTO recapito = new RecapitoDTO();
                        recapitiDB.add(recapito);
                    }
                    if (!trovatoXML) {
                        RecapitoDTO recapito = new RecapitoDTO();
                        recapitiXML.add(recapito);
                    }
                }
            }
        }
        anagraficaXML.setRecapiti(recapitiXML);
        anagraficaDB.setRecapiti(recapitiDB);
    }
    
    public Integer aggiungiAnagraficadaXML(AnagraficaDTO anagrafica) throws Exception {
        Pratica pratica = praticaDao.findPratica(anagrafica.getIdPratica());
        Staging stg = stagingDao.findByCodStaging(pratica.getIdStaging().getIdStaging());
        it.wego.cross.xml.Pratica praticaXml = PraticaUtils.getPraticaFromXML(new String(stg.getXmlPratica()));
        Integer counter = praticaXml.getAnagrafiche().size() + 1;
        anagrafica.setCounter(counter);
        it.wego.cross.xml.Anagrafiche anagraficheXML = new it.wego.cross.xml.Anagrafiche();
        LkTipoRuolo ruolo = lookupDao.findTipoRuoloById(anagrafica.getIdTipoRuolo());
        anagrafica.setDaRubrica(anagrafica.getDaRubrica());
        anagrafica.setCodTipoRuolo(ruolo.getCodRuolo());
        anagrafica.setIdTipoRuolo(ruolo.getIdTipoRuolo());
        anagrafica.setDesTipoRuolo(ruolo.getDescrizione());
        anagrafica.setVarianteAnagrafica(anagrafica.getVarianteAnagrafica());
        anagraficaUtils.copyAnagrafica2XML(anagrafica, anagraficheXML);
        praticaXml.getAnagrafiche().add(anagraficheXML);
        int cnt = 0;
        for (Anagrafiche a : praticaXml.getAnagrafiche()) {
            a.getAnagrafica().setCounter(Utils.bi(cnt));
            cnt++;
        }
        JAXBContext contextObj = JAXBContext.newInstance(it.wego.cross.xml.Pratica.class);
        Marshaller marshallerObj = contextObj.createMarshaller();
        marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        StringWriter praticaSTR = new StringWriter();
        marshallerObj.marshal(praticaXml, praticaSTR);
        stg.setXmlPratica(praticaSTR.toString().getBytes());
        Log.APP.info("stgdao.merge:");
        stagingDao.merge(stg);
        return counter;
    }
    
    public AnagraficaDTO aggiornaAnagraficaStg(AnagraficaDTO anagraficaDTO, Utente utenteConnesso) throws Exception {

        //Anagrafica anagrafica = anagraficheDao.findByCodiceFiscale(anagraficaDTO.getCodiceFiscale());
        Anagrafica anagrafica = null;
        if (anagraficaDTO.getIdAnagrafica() != null) {
            anagrafica = anagraficheDao.findById(anagraficaDTO.getIdAnagrafica());
        }
        if (anagrafica == null) {
            anagrafica = anagraficheDao.findByCodiceFiscale(anagraficaDTO.getCodiceFiscale());
        }
        if (anagrafica == null && !Strings.isNullOrEmpty(anagraficaDTO.getPartitaIva())) {
            anagrafica = anagraficheDao.findByPartitaIva(anagraficaDTO.getPartitaIva());
        }
        if (anagrafica == null) {
            anagrafica = new Anagrafica();
            Log.APP.info("Calcolo la variante anagrafica");
            String varianteAnagrafica = anagraficaDTO.getIdTipoPersona();
            if (Constants.PERSONA_FISICA.equals(anagraficaDTO.getIdTipoPersona()) && anagraficaDTO.getFlgIndividuale().equalsIgnoreCase("S")) {
                varianteAnagrafica = "I";
            }
            anagraficaDTO.setVarianteAnagrafica(varianteAnagrafica);
            anagraficaUtils.copyAnagrafica2Entity(anagraficaDTO, anagrafica);
            Log.APP.info("Insert Anagrafica:");
            anagraficheDao.insert(anagrafica);
            usefulService.flush();
            List<AnagraficaRecapiti> anagraficaRecapitiList = new ArrayList<AnagraficaRecapiti>();
            Log.APP.info("Trovati " + anagraficaDTO.getRecapiti() + " recapiti");
            for (RecapitoDTO recapitoDTO : anagraficaDTO.getRecapiti()) {
                AnagraficaRecapiti anagraficaRecapito = new AnagraficaRecapiti();
                it.wego.cross.entity.Recapiti recapitoDB = new it.wego.cross.entity.Recapiti();
                if (recapitoDTO.getIdTipoIndirizzo() != null) {
                    LkTipoIndirizzo tipind = lookupDao.findTipoIndirizzoById(recapitoDTO.getIdTipoIndirizzo());
                    if (tipind != null) {
                        recapitoDTO.setIdTipoIndirizzo(tipind.getIdTipoIndirizzo());
                        recapitoDTO.setDescTipoIndirizzo(tipind.getDescrizione());
                        anagraficaRecapito.setIdTipoIndirizzo(tipind);
                    }
                }
                if (recapitoDTO.getIdComune() != null) {
                    LkComuni comune = null;
                    if (recapitoDTO.getIdComune() != null) {
                        comune = lookupDao.findComuneById(recapitoDTO.getIdComune());
                    } else {
                        List<LkComuni> comuni = lookupDao.findComuniByDescrizione(recapitoDTO.getDescComune(), new Date());
                        if (comuni != null && comuni.size() > 0) {
                            comune = comuni.get(0);
                        }
                    }
                    recapitoDB.setIdComune(comune);
                }
                if (recapitoDTO.getIdDug() != null) {
                    recapitoDB.setIdDug(lookupDao.findDugById(recapitoDTO.getIdDug()));
                }
                RecapitoUtils.copyRecapito2Entity(recapitoDTO, recapitoDB);
                lookupDao.insert(recapitoDB);
                usefulService.flush();
                anagraficaRecapito.setIdRecapito(recapitoDB);
                anagraficaRecapito.setIdAnagrafica(anagrafica);
                recapitoDTO.setIdRecapito(recapitoDB.getIdRecapito());
                recapitoDTO.setIdAnagrafica(anagrafica.getIdAnagrafica());
                anagraficheDao.insert(anagraficaRecapito);
                usefulService.flush();
                anagraficaRecapitiList.add(anagraficaRecapito);

            }
            anagrafica.setAnagraficaRecapitiList(anagraficaRecapitiList);
            //Aggiorno l'XML dell'anagrafica
            aggiornaAnagraficaXML(anagraficaDTO);
        } else {
            // caso cambio codice fiscale su anagraficaDTO e angrafica già collegata, scollego la vecchia angrafica e la sostituisco con quella corrispondente
            // per codice fiscale - salvo il vecchio id per scollegarlo
            Integer oldIdAnagrafica = anagraficaDTO.getIdAnagrafica();
            anagraficaDTO.setIdAnagrafica(anagrafica.getIdAnagrafica());
            Log.APP.info("AGGIORNA Anagrafica id:" + anagrafica.getIdAnagrafica());
            for (RecapitoDTO recapitoDTO : anagraficaDTO.getRecapiti()) {
                boolean trovato = false;
                if (recapitoDTO.getIdTipoIndirizzo() == null) {
                    LkTipoIndirizzo tipind = lookupDao.findTipoIndirizzoByDesc(recapitoDTO.getDescTipoIndirizzo());
                    if (tipind != null) {
                        recapitoDTO.setIdTipoIndirizzo(tipind.getIdTipoIndirizzo());
                    }
                }
                LkTipoIndirizzo tipoIndirizzo = lookupDao.findTipoIndirizzoById(recapitoDTO.getIdTipoIndirizzo());
                for (AnagraficaRecapiti anagraficaRecapito : anagrafica.getAnagraficaRecapitiList()) {
//                    if (RecapitoUtils.equals(recapitoDTO, anagraficaRecapito.getIdRecapito())) {
                    if (anagraficaRecapito.getIdRecapito() != null && anagraficaRecapito.getIdRecapito().getIdRecapito().equals(recapitoDTO.getIdRecapito())) {
                        Recapiti recapitoDB = anagraficaRecapito.getIdRecapito();
                        RecapitoUtils.copyRecapito2Entity(recapitoDTO, recapitoDB);
                        if (recapitoDTO.getIdDug() != null) {
                            recapitoDB.setIdDug(lookupDao.findDugById(recapitoDTO.getIdDug()));
                        }
                        LkComuni comune = null;
                        if (recapitoDTO.getIdComune() != null) {
                            comune = lookupDao.findComuneById(recapitoDTO.getIdComune());
                        } else if (!Strings.isNullOrEmpty(recapitoDTO.getDescComune())) {
                            List<LkComuni> comuni = lookupDao.findComuniByDescrizione(recapitoDTO.getDescComune(), new Date());
                            if (comuni != null && comuni.size() > 0) {
                                comune = comuni.get(0);
                            }
                        }
                        recapitoDB.setIdComune(comune);
                        trovato = true;
                        recapitoDTO.setIdRecapito(recapitoDB.getIdRecapito());
                        recapitoDTO.setIdAnagrafica(anagrafica.getIdAnagrafica());
                        anagraficaRecapito.setIdTipoIndirizzo(tipoIndirizzo);
                        break;
                    }
                }
                if (!trovato) {
                    Recapiti recapitoDB = new Recapiti();
                    LkComuni comune = null;
                    if (recapitoDTO.getIdComune() != null) {
                        comune = lookupDao.findComuneById(recapitoDTO.getIdComune());
                    } else {
                        List<LkComuni> comuni = lookupDao.findComuniByDescrizione(recapitoDTO.getDescComune(), new Date());
                        if (comuni != null && comuni.size() > 0) {
                            comune = comuni.get(0);
                        }
                    }
                    recapitoDB.setIdComune(comune);
                    RecapitoUtils.copyRecapito2Entity(recapitoDTO, recapitoDB);
                    lookupDao.insert(recapitoDB);
                    usefulService.flush();
                    AnagraficaRecapiti anagraficaRecapiti = new AnagraficaRecapiti();
                    anagraficaRecapiti.setIdAnagrafica(anagrafica);
                    anagraficaRecapiti.setIdRecapito(recapitoDB);
                    anagraficaRecapiti.setIdTipoIndirizzo(tipoIndirizzo);
                    Log.APP.info("Aggiungo il recapito collegato con l'anagrafica con id recapito " + recapitoDB.getIdRecapito() + " e id anagrafica " + anagrafica.getIdAnagrafica());
                    recapitoDTO.setIdRecapito(recapitoDB.getIdRecapito());
                    recapitoDTO.setIdAnagrafica(anagrafica.getIdAnagrafica());
                    if (anagrafica.getAnagraficaRecapitiList() == null) {
                        anagrafica.setAnagraficaRecapitiList(new ArrayList<AnagraficaRecapiti>());
                    }
                    anagrafica.getAnagraficaRecapitiList().add(anagraficaRecapiti);
                }
            }
            //idTipoCollegio
            if (anagraficaDTO.getIdTipoCollegio() != null) {
                LkTipoCollegio tipocollegio = lookupDao.findLookupTipoCollegioById(anagraficaDTO.getIdTipoCollegio());
                if (tipocollegio != null) {
                    anagrafica.setIdTipoCollegio(tipocollegio);
                }
            }
            if (anagraficaDTO.getProvinciaCciaa() != null) {
                LkProvincie provincia = lookupDao.findLkProvinciaById(anagraficaDTO.getProvinciaCciaa().getIdProvincie());
                if (provincia != null && provincia.getIdProvincie() != null) {
                    anagrafica.setIdProvinciaCciaa(provincia);
                }
            }
            if (anagraficaDTO.getProvinciaIscrizione() != null) {
                LkProvincie provincia = lookupDao.findLkProvinciaById(anagraficaDTO.getProvinciaIscrizione().getIdProvincie());
                if (provincia != null && provincia.getIdProvincie() != null) {
                    anagrafica.setIdProvinciaIscrizione(provincia);
                }
            }
            String varianteAnagrafica = anagraficaDTO.getIdTipoPersona();
            String flgIndividuale = anagraficaDTO.getFlgIndividuale();
            if (flgIndividuale.equalsIgnoreCase("S")) {
                varianteAnagrafica = "I";
            }
            //L'anagrafica è marcata come persona fisica ma in banca dati è ditta individuale, allora devo mantenere la varianteAnagrafica ad I
            if (anagrafica.getVarianteAnagrafica() != null && anagrafica.getVarianteAnagrafica().equals("I")) {
                varianteAnagrafica = "I";
            }
            anagraficaDTO.setVarianteAnagrafica(varianteAnagrafica);
            //^^CS sembra sbagliato ma non lo e'
            anagraficaUtils.copyAnagrafica2Entity(anagraficaDTO, anagrafica);
            anagraficaUtils.copyEntity2Anagrafica(anagrafica, anagraficaDTO);
            Log.APP.info("DB : angraficaID " + anagrafica.getIdAnagrafica() + ", CF " + anagrafica.getCodiceFiscale() + ", PIVA " + anagrafica.getPartitaIva() + ", variabileAnagrafica " + anagrafica.getVarianteAnagrafica() + ", tipoAnagrafica " + anagrafica.getTipoAnagrafica());
            anagraficheDao.update(anagrafica);
//            usefulService.flush();
            anagraficaDTO.setFlgIndividuale(flgIndividuale);
            // butto via il collegamento alla vecchia anagrafica
//            if (!oldIdAnagrafica.equals(anagraficaDTO.getIdAnagrafica())) {
//                PraticaAnagrafica pa = praticaDao.findPraticaAnagraficaByKey(anagraficaDTO.getIdPratica(), oldIdAnagrafica, anagraficaDTO.getIdTipoRuolo());
//                systemEventAction.scollegaAnagrafica(pa, utenteConnesso);
//                Log.APP.info("Elimino Pratica Anagrafica " + oldIdAnagrafica);
//            }
        }
        Log.APP.info("XML : angraficaID " + anagrafica.getIdAnagrafica() + " CF " + anagrafica.getCodiceFiscale() + " PIVA " + anagrafica.getPartitaIva() + " variabileAnagrafica " + anagrafica.getVarianteAnagrafica() + " tipoAnagrafica " + anagrafica.getTipoAnagrafica());
        anagraficaDTO.setIdAnagrafica(anagrafica.getIdAnagrafica());
        aggiornaAnagraficaXML(anagraficaDTO);
        Log.APP.info("return : fine salvataggio anagrafica");
        return anagraficaDTO;
    }
    
    private void aggiornaAnagraficaXML(AnagraficaDTO anagraficaDTO) throws Exception {
        Pratica pratica = praticaDao.findPratica(anagraficaDTO.getIdPratica());
        Staging stg = stagingDao.findByCodStaging(pratica.getIdStaging().getIdStaging());
        it.wego.cross.xml.Pratica praticaXml = PraticaUtils.getPraticaFromXML(new String(stg.getXmlPratica()));
        Log.APP.info("aggiornaAnagraficaXML: idPratica:" + anagraficaDTO.getIdPratica() + " idStaging:" + pratica.getIdStaging().getIdStaging());
        for (it.wego.cross.xml.Anagrafiche anaXML : praticaXml.getAnagrafiche()) {
            if (anagraficaUtils.equals(anagraficaDTO, anaXML.getAnagrafica())) {
                Log.APP.info("copyAnagrafica2XML: counter:" + anagraficaDTO.getCounter());
                anagraficaUtils.copyAnagrafica2XML(anagraficaDTO, anaXML);
                break;
            }
        }
        JAXBContext contextObj = JAXBContext.newInstance(it.wego.cross.xml.Pratica.class);
        Marshaller marshallerObj = contextObj.createMarshaller();
        marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        StringWriter praticaSTR = new StringWriter();
        marshallerObj.marshal(praticaXml, praticaSTR);
        stg.setXmlPratica(praticaSTR.toString().getBytes());
        Log.APP.info("stgdao.merge:");
        stagingDao.merge(stg);
    }
}
