/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.webservices.cxf;

import it.wego.cross.avbari.actions.InvioPraticaSitAction;
import it.wego.cross.avbari.sit.client.request.xsd.PraticaSIT;
import it.wego.cross.dao.EntiDao;
import it.wego.cross.dao.PraticaDao;
import it.wego.cross.dto.filters.Filter;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.IndirizziIntervento;
import it.wego.cross.entity.Pratica;
import it.wego.cross.service.PraticheService;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.Utils;
import it.wego.cross.webservices.cxf.cripal.AnagraficaSIT;
import it.wego.cross.webservices.cxf.cripal.DatoCatastaleSIT;
import it.wego.cross.webservices.cxf.cripal.ElencoPraticheResponse;
import it.wego.cross.webservices.cxf.cripal.IndirizzoInterventoSIT;
import it.wego.cross.webservices.cxf.cripal.PraticaDetailResponse;
import it.wego.cross.webservices.cxf.cripal.ProcedimentoSIT;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.jws.WebService;
import org.apache.commons.lang.StringUtils;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Gabriele
 */
@Component("cripalPraticheService")
@WebService(
        serviceName = "CripalPraticheService", 
        portName = "CripalPraticheService", 
        endpointInterface = "it.wego.cross.webservices.cxf.cripal.CripalPraticheService", 
        targetNamespace = "http://www.wego.it/cross", 
        wsdlLocation = "wsdl/cripal-pratiche-service.wsdl")
public class CripalPraticheServiceImpl implements it.wego.cross.webservices.cxf.cripal.CripalPraticheService{

    @Autowired
    private PraticheService praticheService;
    @Autowired
    private PraticaDao praticaDao;
    @Autowired
    private EntiDao entiDao;
    @Autowired
    private InvioPraticaSitAction invioPraticaSitAction;
    @Autowired
    private Mapper mapper;

    @Override
    public it.wego.cross.webservices.cxf.cripal.PraticaDetailResponse getPraticaDetail(it.wego.cross.webservices.cxf.cripal.PraticaDetailRequest parameters) {
        try {
            Pratica pratica = praticheService.getPratica(parameters.getIdPratica());
            PraticaSIT praticaBariSIT = invioPraticaSitAction.creaRichiesta(pratica, null, pratica.getIdProcEnte().getIdEnte().getIdEnte());
            it.wego.cross.webservices.cxf.cripal.PraticaDetailResponse response = new PraticaDetailResponse();
            it.wego.cross.webservices.cxf.cripal.PraticaSIT praticaSIT = mapper.map(praticaBariSIT, it.wego.cross.webservices.cxf.cripal.PraticaSIT.class);
            if (praticaBariSIT.getProcedimentiSIT() != null && praticaBariSIT.getProcedimentiSIT().getProcedimentoSIT() != null && !praticaBariSIT.getProcedimentiSIT().getProcedimentoSIT().isEmpty()) {
                for (it.wego.cross.avbari.sit.client.request.xsd.ProcedimentoSIT p : praticaBariSIT.getProcedimentiSIT().getProcedimentoSIT()) {
                    ProcedimentoSIT proc = mapper.map(p, ProcedimentoSIT.class);
                    praticaSIT.getProcedimentiSIT().getProcedimentoSIT().add(proc);
                }
            }
           if (praticaBariSIT.getDatiCatastali() != null && praticaBariSIT.getDatiCatastali().getDatoCatastaleSIT() != null && !praticaBariSIT.getDatiCatastali().getDatoCatastaleSIT().isEmpty()) {
                for (it.wego.cross.avbari.sit.client.request.xsd.DatoCatastaleSIT p : praticaBariSIT.getDatiCatastali().getDatoCatastaleSIT()) {
                    DatoCatastaleSIT proc = mapper.map(p, DatoCatastaleSIT.class);
                    praticaSIT.getDatiCatastali().getDatoCatastaleSIT().add(proc);
                }
            }
           if (praticaBariSIT.getIndirizziInterventoSIT() != null && praticaBariSIT.getIndirizziInterventoSIT().getIndirizzoInterventoSIT() != null && !praticaBariSIT.getIndirizziInterventoSIT().getIndirizzoInterventoSIT().isEmpty()) {
                for (it.wego.cross.avbari.sit.client.request.xsd.IndirizzoInterventoSIT p : praticaBariSIT.getIndirizziInterventoSIT().getIndirizzoInterventoSIT()) {
                    IndirizzoInterventoSIT proc = mapper.map(p, IndirizzoInterventoSIT.class);
                    praticaSIT.getIndirizziInterventoSIT().getIndirizzoInterventoSIT().add(proc);
                }
            }    
           if (praticaBariSIT.getAnagraficheSIT()!= null && praticaBariSIT.getAnagraficheSIT().getAnagraficaSIT() != null && !praticaBariSIT.getAnagraficheSIT().getAnagraficaSIT().isEmpty()) {
                for (it.wego.cross.avbari.sit.client.request.xsd.AnagraficaSIT p : praticaBariSIT.getAnagraficheSIT().getAnagraficaSIT()) {
                    AnagraficaSIT proc = mapper.map(p, AnagraficaSIT.class);
                    praticaSIT.getAnagraficheSIT().getAnagraficaSIT().add(proc);
                }
            } 
            response.setPratica(praticaSIT);
            
            return response;
        } catch (Exception ex) {
            Log.WS.error("Si è verificato un errore durante il recupero della pratica", ex);
            throw new RuntimeException(ex);
        }
    }

    @Override
    public it.wego.cross.webservices.cxf.cripal.ElencoPraticheResponse getPraticheList(it.wego.cross.webservices.cxf.cripal.ElencoPraticheRequest praticheSearch) {
        try {
            Filter filter = new Filter();
            filter.setOrderColumn("dataRicezione");
            filter.setOrderDirection("asc");

            Date dataRicezioneDa = null, dataRicezioneA = null;
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            Integer idComune = Utils.ib(praticheSearch.getIdComune());
            Integer idEnte = Utils.ib(praticheSearch.getIdEnte());
            String dataRicezioneDaString = praticheSearch.getDataRicezioneDa();
            String dataRicezioneAString = praticheSearch.getDataRicezioneA();
            if (!StringUtils.isEmpty(dataRicezioneDaString)) {
                dataRicezioneDa = sdf.parse(dataRicezioneDaString);
                filter.setDataInizio(dataRicezioneDa);
            }
            if (!StringUtils.isEmpty(dataRicezioneAString)) {
                dataRicezioneA = sdf.parse(dataRicezioneAString);
                filter.setDataFine(dataRicezioneA);
            }
            if(idComune!=null){
                filter.setIdComune(idComune);
            }
            if(idEnte!=null){
                Enti e = entiDao.findByIdEnte(idEnte);
                filter.setEnteSelezionato(e);
            }

            ElencoPraticheResponse praticheList = new ElencoPraticheResponse();

            List<Pratica> praticheFiltrate = praticaDao.findFiltrate(filter);
            for (Pratica pratica : praticheFiltrate) {
                ElencoPraticheResponse.Pratica praticaResult = new ElencoPraticheResponse.Pratica();
                praticaResult.setIdPratica(Utils.bi(pratica.getIdPratica()));
                praticaResult.setIdentificativoPratica(pratica.getIdentificativoPratica());
                praticaResult.setOggetto(pratica.getOggettoPratica());
                praticaResult.setDataRicezione(sdf.format(pratica.getDataRicezione()));
                praticaResult.setIdEnte(Utils.bi(pratica.getIdProcEnte().getIdEnte().getIdEnte()));
                praticaResult.setDescrizioneEnte(pratica.getIdProcEnte().getIdEnte().getDescrizione());
                praticaResult.setIdComune(Utils.bi(pratica.getIdComune().getIdComune()));
                praticaResult.setDescrizioneComune(pratica.getIdComune().getDescrizione());
                if (pratica.getIdStatoPratica() != null) {
                    praticaResult.setCodiceStatoPratica(pratica.getIdStatoPratica().getCodice());
                    praticaResult.setDescrizioneStatoPratica(pratica.getIdStatoPratica().getDescrizione());
                }
                StringBuilder ubicazione = new StringBuilder();
                for (IndirizziIntervento indirizzo : pratica.getIndirizziInterventoList()) {
                    ubicazione.append(indirizzo.getIndirizzo()).append(", ").append(indirizzo.getCivico()).append("  ;");
                }
                praticaResult.setUbicazione(ubicazione.toString());
                praticheList.getPratica().add(praticaResult);
            }

            return praticheList;
        } catch (ParseException ex) {
            Log.WS.error("Si è verificato un errore durante il recupero delle pratiche", ex);
            throw new RuntimeException(ex);
        }
    }

}
