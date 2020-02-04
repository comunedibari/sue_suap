/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.actions;

import com.google.common.base.Strings;
import it.wego.cross.constants.CDSConstants;
import it.wego.cross.dao.AnagraficheDao;
import it.wego.cross.dao.EntiDao;
import it.wego.cross.dao.LookupDao;
import it.wego.cross.dao.OrganiCollegialiDao;
import it.wego.cross.dao.PraticaOrganiCollegialiDao;
import it.wego.cross.dao.TemplateDao;
import it.wego.cross.dto.filters.Filter;
import it.wego.cross.entity.Anagrafica;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.LkRuoliCommissione;
import it.wego.cross.entity.LkStatiSedute;
import it.wego.cross.entity.OcPraticaCommissione;
import it.wego.cross.entity.OcPraticaCommissionePK;
import it.wego.cross.entity.OcSedutePratiche;
import it.wego.cross.entity.OrganiCollegiali;
import it.wego.cross.entity.OrganiCollegialiCommissione;
import it.wego.cross.entity.OrganiCollegialiCommissionePK;
import it.wego.cross.entity.OrganiCollegialiSedute;
import it.wego.cross.entity.OrganiCollegialiTemplate;
import it.wego.cross.entity.PraticaOrganiCollegiali;
import it.wego.cross.entity.Templates;
import it.wego.cross.service.UsefulService;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author giuseppe
 */
@Component
public class CdsAction {
    
    @Autowired
    private TemplateDao templateDao;
    @Autowired
    private OrganiCollegialiDao organiCollegialiDao;
    @Autowired
    private UsefulService usefulService;
    @Autowired
    private EntiDao entiDao;
    @Autowired
    private LookupDao lookupDao;
    @Autowired
    private AnagraficheDao anagraficheDao;
    @Autowired
    private PraticaOrganiCollegialiDao praticaOrganiCollegialiDao;
    
    @Transactional
    public void salvaOrganoCollegiale(HashMap<String, Object> parametri) throws Exception {
        OrganiCollegiali oc = organiCollegialiDao.findById(Integer.valueOf((String) parametri.get("idOrganoCollegiale")));
        boolean findOdg = false;
        boolean findConvocazione = false;
        boolean findVerbale = false;
        Templates odg = (Templates) parametri.get(CDSConstants.TEMPLATE_ODG);
        Templates convocazione = (Templates) parametri.get(CDSConstants.TEMPLATE_CONVOCAZIONE);
        Templates verbale = (Templates) parametri.get(CDSConstants.TEMPLATE_VERBALE);
        if (!CollectionUtils.isEmpty(oc.getOrganiCollegialiTemplateList())) {
            for (OrganiCollegialiTemplate oct : oc.getOrganiCollegialiTemplateList()) {
                if (CDSConstants.TEMPLATE_ODG.equals(oct.getTipologiaTemplate())) {
                    oct.getIdTemplate().setDescrizione(odg.getDescrizione());
                    oct.getIdTemplate().setFileOutput(odg.getFileOutput());
                    oct.getIdTemplate().setMimeType(odg.getMimeType());
                    oct.getIdTemplate().setNomeFile(odg.getNomeFile());
                    oct.getIdTemplate().setNomeFileOriginale(odg.getNomeFileOriginale());
                    oct.getIdTemplate().setTemplate(odg.getTemplate());
                    templateDao.update(oct.getIdTemplate());
                    findOdg = true;
                }
                if (CDSConstants.TEMPLATE_CONVOCAZIONE.equals(oct.getTipologiaTemplate())) {
                    oct.getIdTemplate().setDescrizione(convocazione.getDescrizione());
                    oct.getIdTemplate().setFileOutput(convocazione.getFileOutput());
                    oct.getIdTemplate().setMimeType(convocazione.getMimeType());
                    oct.getIdTemplate().setNomeFile(convocazione.getNomeFile());
                    oct.getIdTemplate().setNomeFileOriginale(convocazione.getNomeFileOriginale());
                    oct.getIdTemplate().setTemplate(convocazione.getTemplate());
                    templateDao.update(oct.getIdTemplate());
                    findConvocazione = true;
                }
                if (CDSConstants.TEMPLATE_VERBALE.equals(oct.getTipologiaTemplate())) {
                    oct.getIdTemplate().setDescrizione(verbale.getDescrizione());
                    oct.getIdTemplate().setFileOutput(verbale.getFileOutput());
                    oct.getIdTemplate().setMimeType(verbale.getMimeType());
                    oct.getIdTemplate().setNomeFile(verbale.getNomeFile());
                    oct.getIdTemplate().setNomeFileOriginale(verbale.getNomeFileOriginale());
                    oct.getIdTemplate().setTemplate(verbale.getTemplate());
                    templateDao.update(oct.getIdTemplate());
                    findVerbale = true;
                }
            }
        };
        if (!findOdg) {
            OrganiCollegialiTemplate oct = new OrganiCollegialiTemplate();
            oct.setIdOrganiCollegiali(oc);
            oct.setIdTemplate(odg);
            oct.setTipologiaTemplate(CDSConstants.TEMPLATE_ODG);
            oc.getOrganiCollegialiTemplateList().add(oct);
        }
        if (!findConvocazione) {
            OrganiCollegialiTemplate oct = new OrganiCollegialiTemplate();
            oct.setIdOrganiCollegiali(oc);
            oct.setIdTemplate(convocazione);
            oct.setTipologiaTemplate(CDSConstants.TEMPLATE_CONVOCAZIONE);
            oc.getOrganiCollegialiTemplateList().add(oct);
        }
        if (!findVerbale) {
            OrganiCollegialiTemplate oct = new OrganiCollegialiTemplate();
            oct.setIdOrganiCollegiali(oc);
            oct.setIdTemplate(verbale);
            oct.setTipologiaTemplate(CDSConstants.TEMPLATE_VERBALE);
            oc.getOrganiCollegialiTemplateList().add(oct);
        }
        
        oc.setDesOrganoCollegiale((String) parametri.get("descrizione"));
        organiCollegialiDao.mergeOrganoCollegiale(oc);
    }
    
    @Transactional
    public void insertOrganoCollegiale(HashMap<String, Object> parametri) throws Exception {
        OrganiCollegiali oc = new OrganiCollegiali();
        oc.setDesOrganoCollegiale((String) parametri.get("descrizione"));
        Enti ente = entiDao.findByIdEnte(Integer.valueOf((String) parametri.get("ente")));
        oc.setIdEnte(ente);
        organiCollegialiDao.insertOrganoCollegiale(oc);
        usefulService.flush();
        usefulService.refresh(oc);
        Templates odg = (Templates) parametri.get(CDSConstants.TEMPLATE_ODG);
        Templates convocazione = (Templates) parametri.get(CDSConstants.TEMPLATE_CONVOCAZIONE);
        Templates verbale = (Templates) parametri.get(CDSConstants.TEMPLATE_VERBALE);
        
        templateDao.insert(odg);
        usefulService.flush();
        templateDao.insert(convocazione);
        usefulService.flush();
        templateDao.insert(verbale);
        usefulService.flush();
        
        OrganiCollegialiTemplate oct = new OrganiCollegialiTemplate();
        oct.setIdOrganiCollegiali(oc);
        oct.setIdTemplate(odg);
        oct.setTipologiaTemplate(CDSConstants.TEMPLATE_ODG);
        organiCollegialiDao.insertOrganoCollegialeTemplate(oct);
        usefulService.flush();
        oc.getOrganiCollegialiTemplateList().add(oct);
        
        OrganiCollegialiTemplate octc = new OrganiCollegialiTemplate();
        octc.setIdOrganiCollegiali(oc);
        octc.setIdTemplate(convocazione);
        octc.setTipologiaTemplate(CDSConstants.TEMPLATE_CONVOCAZIONE);
        organiCollegialiDao.insertOrganoCollegialeTemplate(octc);
        usefulService.flush();
        oc.getOrganiCollegialiTemplateList().add(octc);
        
        OrganiCollegialiTemplate octv = new OrganiCollegialiTemplate();
        octv.setIdOrganiCollegiali(oc);
        octv.setIdTemplate(verbale);
        octv.setTipologiaTemplate(CDSConstants.TEMPLATE_VERBALE);
        organiCollegialiDao.insertOrganoCollegialeTemplate(octv);
        usefulService.flush();
        oc.getOrganiCollegialiTemplateList().add(octv);

        //organiCollegialiDao.insertOrganoCollegiale(oc);
    }
    
    @Transactional
    public void inserisciOrganoCollegialeSeduta(HashMap<String, Object> parametri) throws Exception {
        OrganiCollegialiSedute ocs = new OrganiCollegialiSedute();
        OrganiCollegiali oc = organiCollegialiDao.findById(Integer.valueOf((String) parametri.get("idOrganoCollegiale")));
        ocs.setDataPrevista((Date) parametri.get("dataPrevista"));
        ocs.setIdOrganoCollegiale(oc);
        LkStatiSedute lk = lookupDao.findStatiSeduteById(1);
        ocs.setIdStatoSeduta(lk);
        organiCollegialiDao.insertOrganoCollegialeSedute(ocs);
        List<OrganiCollegialiCommissione> lista = organiCollegialiDao.findAllOrganiCollegiali(oc.getIdOrganiCollegiali());
        boolean insert = false;
        OcSedutePratiche ocsc = null;
        for (OrganiCollegialiCommissione ocl : lista) {
            if (!insert) {
                ocsc = new OcSedutePratiche();
                ocsc.setIdSeduta(ocs);
                organiCollegialiDao.insertOrganoCollegialeSedutePratiche(ocsc);
                usefulService.flush();
                usefulService.refresh(ocs);
                insert = true;
            }
            OcPraticaCommissionePK ocpcpk = new OcPraticaCommissionePK(ocsc.getIdSedutaPratica(), ocl.getAnagrafica().getIdAnagrafica());
            OcPraticaCommissione opc = new OcPraticaCommissione();
            opc.setOcPraticaCommissionePK(ocpcpk);
            opc.setIdRuoloCommissione(ocl.getIdRuoloCommissione());
            organiCollegialiDao.insertOrganoCollegialeSedutePratiche(opc);
        }
    }
    
    @Transactional
    public void modificaOrganoCollegialeSeduta(HashMap<String, Object> parametri) throws Exception {
        OrganiCollegialiSedute ocs = organiCollegialiDao.findByIdSeduta(Integer.valueOf((String) parametri.get("idSeduta")));
        ocs.setDataPrevista((Date) parametri.get("dataPrevista"));
        ocs.setDataConvocazione((Date) parametri.get("dataConvocazione"));
        ocs.setOraConvocazione((String) parametri.get("oraConclusione"));
        ocs.setDataInizio((Date) parametri.get("dataInizio"));
        ocs.setOraInizio((String) parametri.get("oraInizio"));
        ocs.setDataConclusione((Date) parametri.get("dataConclusione"));
        ocs.setOraConclusione((String) parametri.get("oraConclusione"));
        LkStatiSedute lk = lookupDao.findStatiSeduteById(Integer.valueOf((String) parametri.get("idStatoSeduta")));
        ocs.setIdStatoSeduta(lk);
        organiCollegialiDao.mergeOrganoCollegialeSedute(ocs);
    }
    
    @Transactional
    public void modificaOrganoCollegialeCommissione(HashMap<String, Object> parametri) throws Exception {
        OrganiCollegialiCommissione ocs = organiCollegialiDao.findByIdOrganoIdAnagrafica(Integer.valueOf((String) parametri.get("idOrganiCollegiali")), Integer.valueOf((String) parametri.get("idAnagrafica")));
        LkRuoliCommissione lk = lookupDao.findRuoloCommissione(Integer.valueOf((String) parametri.get("idRuoloCommissione")));
        ocs.setIdRuoloCommissione(lk);
        organiCollegialiDao.mergeOrganoCollegialeCommissione(ocs);
    }
    
    @Transactional
    public void inserisciOrganoCollegialeCommissione(HashMap<String, Object> parametri) throws Exception {
        LkRuoliCommissione lk = lookupDao.findRuoloCommissione(Integer.valueOf((String) parametri.get("idRuoloCommissione")));
        OrganiCollegialiCommissionePK ocpk = new OrganiCollegialiCommissionePK();
        ocpk.setIdAnagrafica(Integer.valueOf((String) parametri.get("idAnagrafica")));
        ocpk.setIdOrganiCollegiali(Integer.valueOf((String) parametri.get("idOrganiCollegiali")));
        OrganiCollegialiCommissione ocs = new OrganiCollegialiCommissione();
        ocs.setOrganiCollegialiCommissionePK(ocpk);
        ocs.setIdRuoloCommissione(lk);
        organiCollegialiDao.insertOrganoCollegialeCommissione(ocs);
    }
    
    @Transactional
    public void eliminaOrganoCollegialeCommissione(Integer idOrganiCollegiali, Integer idAnagrafica) throws Exception {
        OrganiCollegialiCommissione ocs = organiCollegialiDao.findByIdOrganoIdAnagrafica(idOrganiCollegiali, idAnagrafica);
        organiCollegialiDao.deleteOrganoCollegialeCommissione(ocs);
    }
    
    @Transactional
    public void eliminaOrganoCollegialeSeduta(Integer idSeduta) throws Exception {
        OrganiCollegialiSedute ocs = organiCollegialiDao.findByIdSeduta(idSeduta);
        for (PraticaOrganiCollegiali po:ocs.getPraticaOrganiCollegialiList()){
            po.setIdSeduta(null);
        }
        organiCollegialiDao.deleteOrganoCollegialeSedute(ocs);
    }
    
    @Transactional
    public void eliminaOrganoCollegialeSedutaPratiche(Integer idSedutaPratica, Integer idAnagrafica) throws Exception {
        OcPraticaCommissione ocs = organiCollegialiDao.findByIdSedutaPraticheAnagrafica(idSedutaPratica, idAnagrafica);
        organiCollegialiDao.deleteOrganoCollegialeSedutePratiche(ocs);
        Long count = organiCollegialiDao.countOrganiCollegialiSedutePraticheAnagrafiche(idSedutaPratica);
        if (count == null || count.longValue() == 0) {
            OcSedutePratiche o = organiCollegialiDao.findByIdSedutaPratica(idSedutaPratica);
            organiCollegialiDao.deleteOrganoCollegialeSedutePratiche(o);
        }
    }
    
    @Transactional
    public void inserisciOrganoCollegialeSedutePratiche(HashMap<String, Object> parametri) throws Exception {
        LkRuoliCommissione lk = lookupDao.findRuoloCommissione(Integer.valueOf((String) parametri.get("idRuoloCommissione")));
        Anagrafica a = anagraficheDao.findById(Integer.valueOf((String) parametri.get("idAnagrafica")));
        OrganiCollegialiSedute s = organiCollegialiDao.findByIdSeduta(Integer.valueOf((String) parametri.get("idSeduta")));
        OcSedutePratiche ocs = null;
        // ricerco se esiste ocsedutepratiche se no la inserisco 
        if (!Strings.isNullOrEmpty((String) parametri.get("idSedutaPratica"))) {
            Integer id = Integer.valueOf((String) parametri.get("idSedutaPratica"));
            if (id != 0) {
                ocs = organiCollegialiDao.findByIdSedutaPratica(id);
            }
        }
        PraticaOrganiCollegiali po = null;
        Integer idPraticaOrganiCollegiali = null;
        if (!Strings.isNullOrEmpty((String) parametri.get("idPraticaOrganiCollegiali"))) {
            Integer id = Integer.valueOf((String) parametri.get("idPraticaOrganiCollegiali"));
            if (id != 0) {
                idPraticaOrganiCollegiali = id;
                po = praticaOrganiCollegialiDao.findById(id);
            }
        }
        
        if (ocs == null) {
            ocs = organiCollegialiDao.findByIdSedutaIdPraticaOC(s.getIdSeduta(), idPraticaOrganiCollegiali);
            if (ocs == null) {
                // inserisci OcSedutePratiche
                ocs = new OcSedutePratiche();
                ocs.setIdSeduta(s);
                ocs.setIdPraticaOrganiCollegiali(po);
                organiCollegialiDao.insertOrganoCollegialeSedutePratiche(ocs);
                usefulService.flush();
                usefulService.refresh(ocs);
            }
        }
        OcPraticaCommissionePK opcpk = new OcPraticaCommissionePK(ocs.getIdSedutaPratica(), a.getIdAnagrafica());
        OcPraticaCommissione opc = new OcPraticaCommissione();
        opc.setOcPraticaCommissionePK(opcpk);
        opc.setIdRuoloCommissione(lk);
        organiCollegialiDao.insertOrganoCollegialeSedutePratiche(opc);
    }
    
    @Transactional
    public void modificaOrganoCollegialeSedutePratiche(HashMap<String, Object> parametri) throws Exception {
        OcPraticaCommissione ocs = organiCollegialiDao.findByIdSedutaPraticheAnagrafica(Integer.valueOf((String) parametri.get("idSedutaPratica")), Integer.valueOf((String) parametri.get("idAnagrafica")));
        LkRuoliCommissione lk = lookupDao.findRuoloCommissione(Integer.valueOf((String) parametri.get("idRuoloCommissione")));
        ocs.setIdRuoloCommissione(lk);
        organiCollegialiDao.mergeOrganoCollegialeSedutePratiche(ocs);
    }
    
    @Transactional
    public void inserisciSedutaPraticaSequenza(Map<String, String> parametri) throws Exception {
        OrganiCollegialiSedute s = organiCollegialiDao.findByIdSeduta(Integer.valueOf(parametri.get("idSeduta")));
        PraticaOrganiCollegiali po = praticaOrganiCollegialiDao.findById(Integer.valueOf(parametri.get("idPraticaOrganoCollegiale")));
        po.setIdSeduta(s);
        if (Strings.isNullOrEmpty(parametri.get("idSedutaPratica"))) {
            OcSedutePratiche ocs = new OcSedutePratiche();
            ocs.setIdSeduta(s);
            ocs.setIdPraticaOrganiCollegiali(po);
            ocs.setSequenza(Integer.valueOf(parametri.get("sequenza")));
            ocs.setNote(parametri.get("note"));
            organiCollegialiDao.insertOrganoCollegialeSedutePratiche(ocs);
            usefulService.flush();
            usefulService.refresh(ocs);
            
            OcSedutePratiche ocsBase = organiCollegialiDao.findByIdSedutaIdPraticaOC(Integer.valueOf(parametri.get("idSeduta")), null);
            if (ocsBase != null) {
                for (OcPraticaCommissione c : ocsBase.getOcPraticaCommissioneList()) {
                    OcPraticaCommissionePK opcpk = new OcPraticaCommissionePK(ocs.getIdSedutaPratica(), c.getAnagrafica().getIdAnagrafica());
                    OcPraticaCommissione opc = new OcPraticaCommissione();
                    opc.setOcPraticaCommissionePK(opcpk);
                    opc.setIdRuoloCommissione(c.getIdRuoloCommissione());
                    organiCollegialiDao.insertOrganoCollegialeSedutePratiche(opc);
                }
            }
        } else {
            OcSedutePratiche ocs = organiCollegialiDao.findByIdSedutaPratica(Integer.valueOf(parametri.get("idSedutaPratica")));
            ocs.setSequenza(Integer.valueOf(parametri.get("sequenza")));
            ocs.setNote(parametri.get("note"));
            organiCollegialiDao.mergeOrganoCollegialeSedutePratiche(ocs);
        }
    }
    
    @Transactional
    public void eliminaOrganoCollegialeSeduteCommissionePratiche(Integer idSedutaPratica) throws Exception {
        OcSedutePratiche ocs = organiCollegialiDao.findByIdSedutaPratica(idSedutaPratica);
        ocs.getIdPraticaOrganiCollegiali().setIdSeduta(null);
        organiCollegialiDao.deleteOrganoCollegialeSedutePratiche(ocs);
    }
    @Transactional
    public void eliminaOrganoCollegiale(Integer idOrganoCollegiale) throws Exception {
        OrganiCollegiali o = organiCollegialiDao.findById(idOrganoCollegiale);
        for (OrganiCollegialiTemplate oct:o.getOrganiCollegialiTemplateList()){
            Templates t = oct.getIdTemplate();
            oct.setIdTemplate(null);
            templateDao.delete(t);
            organiCollegialiDao.deleteOrganoCollegialeTemplate(oct);
        }
        for (OrganiCollegialiCommissione occ: o.getOrganiCollegialiCommissioneList()) {
            organiCollegialiDao.deleteOrganoCollegialeCommissione(occ);
        }
        
        for (OrganiCollegialiSedute ocs:o.getOrganiCollegialiSeduteList()){
            organiCollegialiDao.deleteOrganoCollegialeSedute(ocs);
        }

        organiCollegialiDao.deleteOrganoCollegiale(o);
//        ocs.getIdPraticaOrganiCollegiali().setIdSeduta(null);
//        organiCollegialiDao.deleteOrganoCollegialeSedutePratiche(ocs);
    }
}
