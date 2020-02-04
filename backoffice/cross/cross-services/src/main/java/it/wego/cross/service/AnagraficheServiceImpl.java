/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.service;

import it.wego.cross.constants.Constants;
import it.wego.cross.dao.AnagraficheDao;
import it.wego.cross.dao.LookupDao;
import it.wego.cross.dao.PraticaDao;
import it.wego.cross.dao.StagingDao;
import it.wego.cross.dto.AnagraficaDTO;
import it.wego.cross.dto.CittadinanzaDTO;
import it.wego.cross.dto.NazionalitaDTO;
import it.wego.cross.dto.RecapitoDTO;
import it.wego.cross.dto.filters.Filter;
import it.wego.cross.entity.*;
import it.wego.cross.serializer.AnagraficheSerializer;
import it.wego.cross.serializer.RecapitiSerializer;
import it.wego.cross.utils.AnagraficaUtils;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.PraticaUtils;
import it.wego.cross.utils.RecapitoUtils;
import it.wego.cross.utils.Utils;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service per la gestione della pratiche
 *
 * @author CS
 */
@Service
public class AnagraficheServiceImpl implements AnagraficheService {

    @Autowired
    private PraticaDao praticaDao;
    @Autowired
    private LookupDao lookupDao;
    @Autowired
    private AnagraficheDao anagraficheDao;
    @Autowired
    private StagingDao stagingDao;
    @Autowired
    private AnagraficaUtils anagraficaUtils;
    @Autowired
    private Mapper mapper;

    @Override
    public AnagraficaDTO getAnagrafica(AnagraficaDTO anagraficaDTO) throws Exception {
        AnagraficaDTO dto = new AnagraficaDTO();
        Anagrafica anagrafica = null;
        if (anagraficaDTO.getIdAnagrafica() != null) {
            anagrafica = anagraficheDao.findById(anagraficaDTO.getIdAnagrafica());
        } else {
            anagrafica = anagraficheDao.findByCodiceFiscale(anagraficaDTO.getCodiceFiscale());
        }
        if (anagrafica != null) {
            anagraficaUtils.copyEntity2Anagrafica(anagrafica, dto);
            if (dto.getRecapiti() == null) {
                dto.setRecapiti(new ArrayList<RecapitoDTO>());
            }
            for (AnagraficaRecapiti recapito : anagrafica.getAnagraficaRecapitiList()) {
                RecapitoDTO recapitoDTO = new RecapitoDTO();
                RecapitoUtils.copyEntity2Recapito(recapito.getIdRecapito(), recapitoDTO);
                if (recapito.getIdTipoIndirizzo() != null) {
                    recapitoDTO.setDescTipoIndirizzo(recapito.getIdTipoIndirizzo().getDescrizione());
                    recapitoDTO.setIdTipoIndirizzo(recapito.getIdTipoIndirizzo().getIdTipoIndirizzo());
                }
                dto.getRecapiti().add(recapitoDTO);
            }
        }
        return dto;
    }

    @Override
    public AnagraficaDTO getAnagraficaXML(AnagraficaDTO anagraficaDTO) throws Exception {
        Pratica pratica = praticaDao.findPratica(anagraficaDTO.getIdPratica());
        Staging stg = stagingDao.findByCodStaging(pratica.getIdStaging().getIdStaging());
        it.wego.cross.xml.Pratica praticaXml = PraticaUtils.getPraticaFromXML(new String(stg.getXmlPratica()));
        for (it.wego.cross.xml.Anagrafiche anaXML : praticaXml.getAnagrafiche()) {
            if (anagraficaUtils.equals(anagraficaDTO, anaXML.getAnagrafica())) {
                anagraficaUtils.copyXML2Anagrafica(anaXML, anagraficaDTO, pratica);
                break;
            }
        }
        return anagraficaDTO;
    }

    @Override
    public Recapiti findRecapitoById(Integer idRecapito) {
        Recapiti recapito = anagraficheDao.findRecapitoById(idRecapito);
        return recapito;
    }

    @Override
    public Anagrafica findAnagraficaById(Integer idAnagrafica) {
        Anagrafica anagrafica = anagraficheDao.findById(idAnagrafica);
        return anagrafica;
    }

    @Override
    public Map<Integer, String> getListaTipoRuolo() {
        Map<Integer, String> list = new HashMap<Integer, String>();
        List<LkTipoRuolo> ruoli = lookupDao.findAllTipoRuolo();
        if (ruoli != null && ruoli.size() > 0) {
            for (LkTipoRuolo ruolo : ruoli) {
                list.put(ruolo.getIdTipoRuolo(), ruolo.getDescrizione());
            }
        }
        return list;
    }

    @Override
    public Map<Integer, String> getListaTipoIndirizzo() {
        Map<Integer, String> list = new HashMap<Integer, String>();
        List<LkTipoIndirizzo> ruoli = lookupDao.findAllTipoIndirizzo();
        if (ruoli != null && ruoli.size() > 0) {
            for (LkTipoIndirizzo ruolo : ruoli) {
                list.put(ruolo.getIdTipoIndirizzo(), ruolo.getDescrizione());
            }
        }
        return list;
    }

    @Override
    public void eliminaAnagrafiadXML(AnagraficaDTO anagrafica) throws Exception {
        Pratica pratica = praticaDao.findPratica(anagrafica.getIdPratica());
        Staging stg = stagingDao.findByCodStaging(pratica.getIdStaging().getIdStaging());
        it.wego.cross.xml.Pratica praticaXml = PraticaUtils.getPraticaFromXML(new String(stg.getXmlPratica()));
        int i = 0;
        for (it.wego.cross.xml.Anagrafiche anagraficaxml : praticaXml.getAnagrafiche()) {
            if (anagraficaUtils.equals(anagrafica, anagraficaxml.getAnagrafica())) {
                praticaXml.getAnagrafiche().remove(i);
                break;
            }
            i++;
        }
        JAXBContext contextObj = JAXBContext.newInstance(it.wego.cross.xml.Pratica.class);
        Marshaller marshallerObj = contextObj.createMarshaller();
        marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        StringWriter praticaSTR = new StringWriter();
        marshallerObj.marshal(praticaXml, praticaSTR);

        stg.setXmlPratica(praticaSTR.toString().getBytes());
        Log.APP.debug("stgdao.merge:");
        stagingDao.merge(stg);

    }

    @Override
    public List<CittadinanzaDTO> findCittadinanze(String nome) {
        List<it.wego.cross.entity.LkCittadinanza> cittadinanzeDB = lookupDao.findCittadinanzeByDesc(nome);
        List<CittadinanzaDTO> cittadinanzeDTO = new ArrayList<CittadinanzaDTO>();
        if (cittadinanzeDB != null) {
            for (it.wego.cross.entity.LkCittadinanza cittadinanzaDB : cittadinanzeDB) {
                CittadinanzaDTO cittadinanza = new CittadinanzaDTO();
                AnagraficheSerializer.serialize(cittadinanzaDB, cittadinanza);
                cittadinanzeDTO.add(cittadinanza);
            }
        }
        return cittadinanzeDTO;
    }

    @Override
    public List<NazionalitaDTO> findNazionalita(String nome) {
        List<it.wego.cross.entity.LkNazionalita> nazionalitaDB = lookupDao.findNazionalitaByDesc(nome);
        List<NazionalitaDTO> nazionalitaDTO = new ArrayList<NazionalitaDTO>();
        if (nazionalitaDB != null && !nazionalitaDB.isEmpty()) {
            for (it.wego.cross.entity.LkNazionalita nazionalita : nazionalitaDB) {
                NazionalitaDTO dto = AnagraficheSerializer.serialize(nazionalita);
                nazionalitaDTO.add(dto);
            }
        }
        return nazionalitaDTO;
    }

    @Override
    public Long countAnagrafiche(Filter filter) {
        Long count = anagraficheDao.countAnagrafiche(filter);
        return count;
    }

    @Override
    public List<AnagraficaDTO> searchAnagrafiche(Filter filter) {
        List<Anagrafica> anagrafiche = anagraficheDao.findAnagrafiche(filter);
        List<AnagraficaDTO> result = new ArrayList<AnagraficaDTO>();
        for (Anagrafica anagrafica : anagrafiche) {
            AnagraficaDTO a = AnagraficheSerializer.serializeAnagrafica(anagrafica);
            result.add(a);
        }
        return result;
    }

    @Override
    public Anagrafica findAnagraficaByCodFiscale(String codiceFiscale) {
        return anagraficheDao.findByCodiceFiscale(codiceFiscale);
    }

    @Override
    public Anagrafica findAnagraficaByCodFiscaleNotId(String codiceFiscale, Integer idAnagrafica) {
        return anagraficheDao.findByCodiceFiscaleNotId(codiceFiscale, idAnagrafica);
    }

    @Override
    public Anagrafica findAnagraficaByPartitaIvaNotId(String partitaIva, Integer idAnagrafica) {
        return anagraficheDao.findByPartitaIvaNotId(partitaIva, idAnagrafica);
    }

    @Override
    public Anagrafica findAnagraficaByPartitaIva(String partitaIva) {
        return anagraficheDao.findByPartitaIva(partitaIva);
    }

    @Override
    public Recapiti getRecapitoRiferimentoAnagrafica(Anagrafica anagrafica) throws Exception {
        AnagraficaRecapiti anagraficaRecapito = getAnagraficaRecapitoRiferimentoAnagrafica(anagrafica);
        if (anagraficaRecapito != null) {
            return anagraficaRecapito.getIdRecapito();
        }
        return null;
    }
    

    @Override
    public AnagraficaRecapiti getAnagraficaRecapitoRiferimentoAnagrafica(Anagrafica anagrafica) throws Exception {
        for (String tipoRecapito : Constants.ORDINE_RECAPITI_CODE) {
            if (anagrafica.getAnagraficaRecapitiList() != null && !anagrafica.getAnagraficaRecapitiList().isEmpty()) {
                for (AnagraficaRecapiti anagraficaRecapito : anagrafica.getAnagraficaRecapitiList()) {
                    if (anagraficaRecapito.getIdTipoIndirizzo().getCodTipoIndirizzo().equalsIgnoreCase(tipoRecapito)) {
                        return anagraficaRecapito;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public AnagraficaDTO cercaAnagraficaDuplicata(AnagraficaDTO anagraficaDTO) throws Exception {
        AnagraficaDTO dto = new AnagraficaDTO();
        if (!Utils.e(anagraficaDTO.getPartitaIva())) {
            Anagrafica anagrafica = anagraficheDao.findByPartitaIva(anagraficaDTO.getPartitaIva());
            if (anagrafica != null) {
                if (!anagrafica.getCodiceFiscale().equals(anagraficaDTO.getCodiceFiscale())) {
                    anagraficaUtils.copyEntity2Anagrafica(anagrafica, dto);
                }
            }
        }
        return dto;
    }

    @Override
    public Recapiti getRecapitoRiferimentoAnagrafica(PraticaAnagrafica praticaAnagrafica) throws Exception {
        if (praticaAnagrafica.getIdRecapitoNotifica() != null) {
            return praticaAnagrafica.getIdRecapitoNotifica();
        }
        for (String tipoRecapito : Constants.ORDINE_RECAPITI_CODE) {
            for (AnagraficaRecapiti anagraficaRecapito : praticaAnagrafica.getAnagrafica().getAnagraficaRecapitiList()) {
                if (anagraficaRecapito.getIdTipoIndirizzo().getCodTipoIndirizzo().equalsIgnoreCase(tipoRecapito)) {
                    return anagraficaRecapito.getIdRecapito();
                }
            }
        }
        return null;
    }

    @Override
    public RecapitoDTO getPrimoRecapito(Integer idAnagrafica) {
        Anagrafica anagraficaDb = findAnagraficaById(idAnagrafica);
        List<AnagraficaRecapiti> anagraficaRecapiti = anagraficaDb.getAnagraficaRecapitiList();
        RecapitoDTO dto = null;
        if (anagraficaRecapiti != null && anagraficaRecapiti.size() == 1) {
            Recapiti recapito = anagraficaRecapiti.get(0).getIdRecapito();
            dto = RecapitiSerializer.serialize(recapito, anagraficaRecapiti.get(0).getIdTipoIndirizzo());
        }
        return dto;
    }

    @Override
    public boolean isTipoPersonaModificabile(String tipoPersona, Integer idAnagrafica, String codiceFiscale) {
        Anagrafica anagrafica;
        if (idAnagrafica != null) {
            anagrafica = findAnagraficaById(idAnagrafica);
        } else {
            anagrafica = findAnagraficaByCodFiscale(codiceFiscale);
        }
        if (anagrafica == null) {
            return true;
        } else {
            Long occorrenzeAnagrafica = praticaDao.countOccorrenzeAnagraficaSuPratiche(anagrafica);
            return occorrenzeAnagrafica < 2;
        }
    }

    @Override
    public List<AnagraficaDTO> searchAnagraficheLike(String ricerca) {
        List<Anagrafica> anagrafiche = anagraficheDao.searchAnagraficheLike(ricerca);
        List<AnagraficaDTO> lista = new ArrayList<AnagraficaDTO>();
        for (Anagrafica a : anagrafiche) {
            AnagraficaDTO aDTO = mapper.map(a, AnagraficaDTO.class);
            lista.add(aDTO);
        }
        return lista;
    }

    @Override
    public AnagraficaRecapiti findAnagraficaRecapitiById(Integer idAnagraficaRecapiti) {
        return anagraficheDao.findAnagraficaRecapitoById(idAnagraficaRecapiti);
    }

}
