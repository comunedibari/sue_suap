/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.serializer;

import com.google.common.base.Strings;
import it.wego.cross.dao.AnagraficheDao;
import it.wego.cross.dao.LookupDao;
import it.wego.cross.dao.UsefulDao;
import it.wego.cross.dto.AnagraficaDTO;
import it.wego.cross.dto.AnagraficaDaCollegareDTO;
import it.wego.cross.dto.CittadinanzaDTO;
import it.wego.cross.dto.ComuneDTO;
import it.wego.cross.dto.NazionalitaDTO;
import it.wego.cross.dto.ProvinciaDTO;
import it.wego.cross.dto.RecapitoDTO;
import it.wego.cross.dto.RuoloAnagraficaDTO;
import it.wego.cross.dto.TipoQualificaAnagraficaDTO;
import it.wego.cross.entity.Anagrafica;
import it.wego.cross.entity.AnagraficaRecapiti;
import it.wego.cross.entity.LkCittadinanza;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.entity.LkFormeGiuridiche;
import it.wego.cross.entity.LkNazionalita;
import it.wego.cross.entity.LkProvincie;
import it.wego.cross.entity.LkTipoCollegio;
import it.wego.cross.entity.LkTipoIndirizzo;
import it.wego.cross.entity.LkTipoQualifica;
import it.wego.cross.entity.LkTipoRuolo;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticaAnagrafica;
import it.wego.cross.entity.PraticaAnagraficaPK;
import it.wego.cross.entity.Recapiti;
import it.wego.cross.plugins.protocollo.beans.SoggettoProtocollo;
import it.wego.cross.utils.AnagraficaUtils;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.Utils;
import it.wego.cross.xml.Anagrafiche;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Gabriele
 */
@Component
public class AnagraficheSerializer {

    @Autowired
    private LookupDao lookupDao;
    @Autowired
    private AnagraficheDao anagraficheDao;
    @Autowired
    private RecapitiSerializer recapitiSerializer;
    @Autowired
    private UsefulDao usefulDao;
    @Autowired
    private AnagraficaUtils anagraficaUtils;

//    public PraticaAnagrafica serialize(it.wego.cross.xml.Anagrafiche xml, Pratica pratica) throws Exception {
//        it.wego.cross.xml.Anagrafica axml = xml.getAnagrafica();
//        Anagrafica a = serializeAnagrafica(axml);
//        anagraficheDao.insert(a);
//        //Se ho una nuova anagrafica gli aggiungo il recapito
//        if (a.getIdAnagrafica() == null
//                && axml.getRecapiti() != null
//                && axml.getRecapiti().getRecapito() != null
//                && !axml.getRecapiti().getRecapito().isEmpty()) {
//            usefulDao.flush();
//            for (it.wego.cross.xml.Recapito recapito : axml.getRecapiti().getRecapito()) {
//                Recapiti r = recapitiSerializer.serializeEntity(recapito);
//                anagraficheDao.insert(r);
//                usefulDao.flush();
//                AnagraficaRecapiti ar = new AnagraficaRecapiti();
//                ar.setIdAnagrafica(a);
//                ar.setIdRecapito(r);
//                LkTipoIndirizzo tipoIndirizzo = lookupDao.findTipoIndirizzoById(Utils.ib(recapito.getIdTipoIndirizzo()));
//                ar.setIdTipoIndirizzo(tipoIndirizzo);
//                anagraficheDao.insert(ar);
//            }
//        }
//
//        PraticaAnagrafica p = new PraticaAnagrafica();
//        PraticaAnagraficaPK key = new PraticaAnagraficaPK();
//        key.setIdPratica(pratica.getIdPratica());
//        key.setIdAnagrafica(a.getIdAnagrafica());
//        if(a.getIdAnagrafica()!=null) {
//        	key.setIdAnagrafica(a.getIdAnagrafica());
//        }
//        LkTipoRuolo ruolo = lookupDao.findTipoRuoloByCodRuolo(xml.getCodTipoRuolo());
//        //se il ruolo non esiste nel file xml si inserisce ruolo 1 che significa richiedente
//        if(ruolo!=null)
//        	key.setIdTipoRuolo(ruolo.getIdTipoRuolo());
//        else
//        	key.setIdTipoRuolo(1);
////        LkTipoRuolo ruolo = lookupDao.findTipoRuoloByCodRuolo(xml.getCodTipoRuolo());
////        key.setIdTipoRuolo(ruolo.getIdTipoRuolo());
//        p.setPraticaAnagraficaPK(key);
//        p.setLkTipoRuolo(ruolo);
//        p.setAnagrafica(a);
//        p.setFlgDittaIndividuale(axml.getFlgIndividuale());
//        if (xml.getIdTipoQualifica() != null) {
//            LkTipoQualifica qualifica = lookupDao.findTipoQualificaById(Utils.ib(xml.getIdTipoQualifica()));
//            p.setIdTipoQualifica(qualifica);
//        }
//        return p;
//    }
    public PraticaAnagrafica serialize(it.wego.cross.xml.Anagrafiche xml, Pratica pratica) throws Exception {
        it.wego.cross.xml.Anagrafica axml = xml.getAnagrafica();
        Anagrafica a = serializeAnagrafica(axml);
        anagraficheDao.insert(a);
        usefulDao.flush();
        //Se ho una nuova anagrafica gli aggiungo il recapito
        if (a.getIdAnagrafica() == null
                && axml.getRecapiti() != null
                && axml.getRecapiti().getRecapito() != null
                && !axml.getRecapiti().getRecapito().isEmpty()) {
            //usefulDao.flush();
            for (it.wego.cross.xml.Recapito recapito : axml.getRecapiti().getRecapito()) {
                Recapiti r = recapitiSerializer.serializeEntity(recapito);
                anagraficheDao.insert(r);
                usefulDao.flush();
                AnagraficaRecapiti ar = new AnagraficaRecapiti();
                ar.setIdAnagrafica(a);
                ar.setIdRecapito(r);
                LkTipoIndirizzo tipoIndirizzo = lookupDao.findTipoIndirizzoById(Utils.ib(recapito.getIdTipoIndirizzo()));
                ar.setIdTipoIndirizzo(tipoIndirizzo);
                anagraficheDao.insert(ar);
            }
        }
        //04/09/2019 inseriti flush e refresh per committare l'inserimento dell'anagrafica
        //usefulDao.flush();
        //usefulDao.refresh(a);
        PraticaAnagrafica p = new PraticaAnagrafica();
        PraticaAnagraficaPK key = new PraticaAnagraficaPK();
        key.setIdPratica(pratica.getIdPratica());
        if(a.getIdAnagrafica()!=null) {
        	key.setIdAnagrafica(a.getIdAnagrafica());
        }
        LkTipoRuolo ruolo = lookupDao.findTipoRuoloByCodRuolo(xml.getCodTipoRuolo());
        //se il ruolo non esiste nel file xml si inserisce ruolo 1 che significa richiedente
        if(ruolo!=null)
        	key.setIdTipoRuolo(ruolo.getIdTipoRuolo());
        else
        	key.setIdTipoRuolo(1);
        p.setPraticaAnagraficaPK(key);
        p.setLkTipoRuolo(ruolo);
        p.setAnagrafica(a);
        p.setFlgDittaIndividuale(axml.getFlgIndividuale());
        if (xml.getIdTipoQualifica() != null) {
            LkTipoQualifica qualifica = lookupDao.findTipoQualificaById(Utils.ib(xml.getIdTipoQualifica()));
            p.setIdTipoQualifica(qualifica);
        }
        return p;
    }


    public Anagrafica serializeAnagrafica(it.wego.cross.xml.Anagrafica x) {
        Anagrafica a = anagraficheDao.findByCodiceFiscale(x.getCodiceFiscale());
        //Se non lo trovo per codice fiscale lo inserisco comunque. Se ci sono 2 partite IVA uguali me lo segnala il sistema in apertura pratiche
        //e devo correggerle tramite la rubrica
//        if (a == null && x.getPartitaIva() != null && !"".equals(x.getPartitaIva())) {
//            a = anagraficheDao.findByPartitaIva(x.getPartitaIva());
//        }
        if (a == null) {
            a = new Anagrafica();
            a.setCodiceFiscale(!Utils.e(x.getCodiceFiscale()) ? x.getCodiceFiscale() : null);
            a.setCognome(x.getCognome());
            a.setDataIscrizione(x.getDataIscrizione() != null ? Utils.xmlGregorianCalendarToDate(x.getDataIscrizione()) : null);
            a.setDataIscrizioneRea(x.getDataIscrizioneRea() != null ? Utils.xmlGregorianCalendarToDate(x.getDataIscrizioneRea()) : null);
            a.setDataIscrizioneRi(x.getDataIscrizioneRi() != null ? Utils.xmlGregorianCalendarToDate(x.getDataIscrizioneRi()) : null);
            a.setDataNascita(x.getDataNascita() != null ? Utils.xmlGregorianCalendarToDate(x.getDataNascita()) : null);
            a.setDenominazione(x.getDenominazione());
            LkCittadinanza cittadinanaza = null;
            if (!Utils.e(x.getIdCittadinanza())) {
                cittadinanaza = lookupDao.findCittadinanzaByID(Utils.ib(x.getIdCittadinanza()));
            }
            if (cittadinanaza == null && !Utils.e(x.getCodCittadinanza()) && Utils.isInteger(x.getCodCittadinanza())) {
                cittadinanaza = lookupDao.findCittadinanzaByCod(x.getCodCittadinanza());
            }
            if (cittadinanaza == null && !Utils.e(x.getDesCittadinanza())) {
                List<LkCittadinanza> cittadinanazaList = lookupDao.findCittadinanzaByDescrizionePrecisa(x.getDesCittadinanza().toUpperCase());
                if (cittadinanazaList != null && cittadinanazaList.size() > 0) {
                    cittadinanaza = cittadinanazaList.get(0);
                }
            }
            a.setIdCittadinanza(cittadinanaza);
//        a.setIdComuneNascita(null);
            if (x.getIdFormaGiuridica() != null) {
                LkFormeGiuridiche f = lookupDao.findLkFormeGiuridicheById(Utils.ib(x.getIdFormaGiuridica()));
                a.setIdFormaGiuridica(f);
            }

            LkNazionalita nazionalita = null;
            if (!Utils.e(x.getIdNazionalita())) {
                nazionalita = lookupDao.findNazionalitaByID(Utils.ib(x.getIdNazionalita()));
            }
            if (nazionalita == null && !Utils.e(x.getCodNazionalita()) && Utils.isInteger(x.getCodNazionalita())) {
                nazionalita = lookupDao.findNazionalitaByCod(x.getCodNazionalita());
            }
            if (nazionalita == null && !Utils.e(x.getDesCittadinanza())) {
                List<LkNazionalita> nazionalitaList = lookupDao.findNazionalitaByDescrizionePrecisa(x.getDesNazionalita().toUpperCase());
                if (nazionalitaList != null && nazionalitaList.size() > 0) {
                    nazionalita = nazionalitaList.get(0);
                }
            }

            LkProvincie provinciaCciaa = getProvincia(x.getIdProvinciaCciaa() != null ? Utils.ib(x.getIdProvinciaCciaa()) : null, x.getCodProvinciaCciaa(), x.getDesProvinciaCciaa(), true);
            a.setIdProvinciaCciaa(provinciaCciaa);

            LkProvincie provinciaIscrizione = getProvincia(x.getIdProvinciaIscrizione() != null ? Utils.ib(x.getIdProvinciaIscrizione()) : null, x.getCodProvinciaIscrizione(), x.getDesProvinciaIscrizione(), true);
            a.setIdProvinciaIscrizione(provinciaIscrizione);

            a.setIdNazionalita(nazionalita);
//        a.setIdProvinciaCciaa(null);
//        a.setIdProvinciaIscrizione(null);
            if (x.getIdTipoCollegio() != null) {
                LkTipoCollegio c = lookupDao.findLookupTipoCollegioById(Utils.ib(x.getIdTipoCollegio()));
                a.setIdTipoCollegio(c);
            }
            a.setLocalitaNascita(x.getLocalitaNascita());
            a.setNIscrizioneRea(x.getNIscrizioneRea());
            a.setNIscrizioneRi(x.getNIscrizioneRi());
            a.setNome(x.getNome());
            a.setNumeroIscrizione(x.getNumeroIscrizione());
            a.setPartitaIva(!Strings.isNullOrEmpty(x.getPartitaIva()) ? x.getPartitaIva() : null);
            if (!Utils.e(x.getSesso())) {
                a.setSesso(x.getSesso().charAt(0));
            }
            if(x.getTipoAnagrafica()!=null) {
            	 a.setTipoAnagrafica(x.getTipoAnagrafica().charAt(0));
            }
           
            a.setVarianteAnagrafica(x.getVarianteAnagrafica());
            a.setFlgIndividuale(!Strings.isNullOrEmpty(x.getFlgIndividuale()) ? x.getFlgIndividuale().charAt(0) : 'N');
            if (x.isFlgAttesaIscrizioneRi() != null) {
            	/*Patch produzione RI e REA */
                //a.setFlgAttesaIscrizioneRi(x.isFlgAttesaIscrizioneRi() ? 'S' : 'N');
            	a.setFlgAttesaIscrizioneRi('S');
            }
            if (x.isFlgAttesaIscrizioneRea() != null) {
            	/*Patch produzione RI e REA */
                //a.setFlgAttesaIscrizioneRea(x.isFlgAttesaIscrizioneRea() ? 'S' : 'N');
            	a.setFlgAttesaIscrizioneRea('S');
            }

            a.setFlgObbligoIscrizioneRi('N');
        }

        return a;
    }

    private LkProvincie getProvincia(Integer idProvincia, String codProvincia, String desProvincia, boolean isProvinciaCciaa) {
        LkProvincie provincia = null;
        if (idProvincia != null) {
            provincia = lookupDao.findLkProvinciaById(idProvincia);
        } else if (provincia == null && !Strings.isNullOrEmpty(codProvincia)) {
            provincia = lookupDao.findLkProvinciaByCod(codProvincia, isProvinciaCciaa);
        } else if (provincia == null && !Strings.isNullOrEmpty(desProvincia)) {
            provincia = lookupDao.findLkProvinciaByDescPrecisa(desProvincia, isProvinciaCciaa);
        }
        return provincia;
    }

    public List<it.wego.cross.xml.Anagrafiche> serialize(Pratica pratica) throws DatatypeConfigurationException {
        List<it.wego.cross.xml.Anagrafiche> anagraficheXmlList = new ArrayList<Anagrafiche>();

        it.wego.cross.xml.Anagrafiche anagraficheXml;
        it.wego.cross.xml.Anagrafica anagraficaXml;
        it.wego.cross.xml.Recapito recapitoXml;
        it.wego.cross.xml.Recapiti recapitiAnagraficaXmlList;
        it.wego.cross.xml.Recapito recapitoAnagraficaXml;
        Recapiti recapitoAnagraficaPratica;
        Anagrafica anagrafica;

        List<PraticaAnagrafica> praticaAnagraficaList = pratica.getPraticaAnagraficaList();
        if (praticaAnagraficaList != null) {
            //LOOP SU TUTTE LE ANAGRAFICHE ASSOCIATE ALLA PRATICA
            for (PraticaAnagrafica praticaAnagrafica : praticaAnagraficaList) {
                //Salto la pratica se non ha un recapito per la notifica
                if (praticaAnagrafica.getIdRecapitoNotifica() != null) {
                    anagraficheXml = new it.wego.cross.xml.Anagrafiche();
                    recapitoXml = new it.wego.cross.xml.Recapito();
                    anagraficaXml = new it.wego.cross.xml.Anagrafica();
                    recapitiAnagraficaXmlList = new it.wego.cross.xml.Recapiti();

                    //PER L'ANAGRAFICA CORRENTE RECUPERO IL RECAPITO ASSOCIATO ALLA PRATICA
                    recapitoAnagraficaPratica = praticaAnagrafica.getIdRecapitoNotifica();
                    recapitoXml.setIdRecapito(Utils.bi(recapitoAnagraficaPratica.getIdRecapito()));
                    if (recapitoAnagraficaPratica.getIdComune() != null) {
                        recapitoXml.setIdComune(Utils.bi(recapitoAnagraficaPratica.getIdComune().getIdComune()));
                        recapitoXml.setDesComune(recapitoAnagraficaPratica.getIdComune().getDescrizione());

                        if (recapitoAnagraficaPratica.getIdComune().getIdProvincia() != null) {
                            recapitoXml.setIdProvincia(Utils.bi(recapitoAnagraficaPratica.getIdComune().getIdProvincia().getIdProvincie()));
                            recapitoXml.setDesProvincia(recapitoAnagraficaPratica.getIdComune().getIdProvincia().getDescrizione());
                        }

                        if (recapitoAnagraficaPratica.getIdComune().getIdStato() != null) {
                            recapitoXml.setIdStato(Utils.bi(recapitoAnagraficaPratica.getIdComune().getIdStato().getIdStato()));
                            recapitoXml.setDesStato(recapitoAnagraficaPratica.getIdComune().getIdStato().getDescrizione());
                        }
                    }
                    recapitoXml.setLocalita(recapitoAnagraficaPratica.getLocalita());
                    if (recapitoAnagraficaPratica.getIdDug() != null) {
                        recapitoXml.setIdDug(Utils.bi(recapitoAnagraficaPratica.getIdDug().getIdDug()));
                    }
                    recapitoXml.setIndirizzo(recapitoAnagraficaPratica.getIndirizzo());
                    recapitoXml.setNCivico(recapitoAnagraficaPratica.getNCivico());
                    recapitoXml.setCap(recapitoAnagraficaPratica.getCap());
                    recapitoXml.setCasellaPostale(recapitoAnagraficaPratica.getCasellaPostale());
                    recapitoXml.setAltreInfoIndirizzo(recapitoAnagraficaPratica.getAltreInfoIndirizzo());
                    recapitoXml.setTelefono(recapitoAnagraficaPratica.getTelefono());
                    recapitoXml.setCellulare(recapitoAnagraficaPratica.getCellulare());
                    recapitoXml.setFax(recapitoAnagraficaPratica.getFax());
                    recapitoXml.setEmail(recapitoAnagraficaPratica.getEmail());
                    recapitoXml.setPec(recapitoAnagraficaPratica.getPec());

                    //Il tipo di recapito è stato spostato in AnagraficaRecapiti
//                    if (recapitoAnagraficaPratica.getIdTipoIndirizzo() != null) {
//                        recapitoXml.setIdTipoIndirizzo(Utils.bi(recapitoAnagraficaPratica.getIdTipoIndirizzo().getIdTipoIndirizzo()));
//                        recapitoXml.setDesTipoIndirizzo(recapitoAnagraficaPratica.getIdTipoIndirizzo().getDescrizione());
//                    }
                    //TODO: Prendo, se presente, il recapito dell'anagrafica
                    anagrafica = praticaAnagrafica.getAnagrafica();
                    //PER L'ANAGRAFICA CORRENTE LOOP SU TUTTI I RECAPITI
                    for (AnagraficaRecapiti anagraficaRecapito : anagrafica.getAnagraficaRecapitiList()) {
                        Recapiti recapito = anagraficaRecapito.getIdRecapito();

                        recapitoAnagraficaXml = new it.wego.cross.xml.Recapito();
                        recapitoAnagraficaXml.setIdRecapito(Utils.bi(recapito.getIdRecapito()));
                        if (recapito.getIdComune() != null) {
                            recapitoAnagraficaXml.setIdComune(Utils.bi(recapito.getIdComune().getIdComune()));
                            recapitoAnagraficaXml.setDesComune(recapito.getIdComune().getDescrizione());
                            if (recapito.getIdComune().getIdProvincia() != null) {
                                recapitoAnagraficaXml.setIdProvincia(Utils.bi(recapito.getIdComune().getIdProvincia().getIdProvincie()));
                                recapitoAnagraficaXml.setDesProvincia(recapito.getIdComune().getIdProvincia().getDescrizione());
                            }
                            if (recapito.getIdComune().getIdStato() != null) {
                                recapitoAnagraficaXml.setIdStato(Utils.bi(recapito.getIdComune().getIdStato().getIdStato()));
                                recapitoAnagraficaXml.setDesStato(recapito.getIdComune().getIdStato().getDescrizione());
                            }
                        }
                        recapitoAnagraficaXml.setLocalita(recapito.getLocalita());
                        if (recapito.getIdDug() != null) {
                            recapitoAnagraficaXml.setIdDug(Utils.bi(recapito.getIdDug().getIdDug()));
                        }
                        recapitoAnagraficaXml.setIndirizzo(recapito.getIndirizzo());
                        recapitoAnagraficaXml.setNCivico(recapito.getNCivico());
                        recapitoAnagraficaXml.setCap(recapito.getCap());
                        recapitoAnagraficaXml.setCasellaPostale(recapito.getCasellaPostale());
                        recapitoAnagraficaXml.setAltreInfoIndirizzo(recapito.getAltreInfoIndirizzo());
                        recapitoAnagraficaXml.setTelefono(recapito.getTelefono());
                        recapitoAnagraficaXml.setCellulare(recapito.getCellulare());
                        recapitoAnagraficaXml.setFax(recapito.getFax());
                        recapitoAnagraficaXml.setEmail(recapito.getEmail());
                        recapitoAnagraficaXml.setPec(recapito.getPec());
                        if (anagraficaRecapito.getIdTipoIndirizzo() != null) {
                            recapitoAnagraficaXml.setIdTipoIndirizzo(Utils.bi(anagraficaRecapito.getIdTipoIndirizzo().getIdTipoIndirizzo()));
                            recapitoAnagraficaXml.setDesTipoIndirizzo(anagraficaRecapito.getIdTipoIndirizzo().getDescrizione());
                        }
                        recapitiAnagraficaXmlList.getRecapito().add(recapitoAnagraficaXml);

                    }

                    anagraficaXml.setIdAnagrafica(Utils.bi(anagrafica.getIdAnagrafica()));
                    //^^CS AGGIUNTA
                    anagraficaXml.setVarianteAnagrafica(anagraficaUtils.getTipoAnagrafica(anagrafica));
                    anagraficaXml.setTipoAnagrafica(String.valueOf(anagrafica.getTipoAnagrafica()));
                    anagraficaXml.setCognome(anagrafica.getCognome());
                    anagraficaXml.setDenominazione(anagrafica.getDenominazione());
                    anagraficaXml.setNome(anagrafica.getNome());
                    anagraficaXml.setCodiceFiscale(anagrafica.getCodiceFiscale());
                    anagraficaXml.setPartitaIva(anagrafica.getPartitaIva());
//                anagraficaXml.setFlgIndividuale(String.valueOf(anagrafica.getFlgIndividuale()));
                    anagraficaXml.setDataNascita(Utils.dateToXmlGregorianCalendar(anagrafica.getDataNascita()));
                    if (anagrafica.getIdCittadinanza() != null) {
                        anagraficaXml.setIdCittadinanza(Utils.bi(anagrafica.getIdCittadinanza().getIdCittadinanza()));
                        anagraficaXml.setDesCittadinanza(anagrafica.getIdCittadinanza().getDescrizione());
                        anagraficaXml.setIdComuneNascita(Utils.bi(anagrafica.getIdComuneNascita().getIdComune()));
                    }
                    if (anagrafica.getIdNazionalita() != null) {
                        anagraficaXml.setIdNazionalita(Utils.bi(anagrafica.getIdNazionalita().getIdNazionalita()));
                        anagraficaXml.setDesNazionalita(anagrafica.getIdNazionalita().getDescrizione());
                    }
                    if (anagrafica.getIdComuneNascita() != null) {
                        anagraficaXml.setDesComuneNascita(anagrafica.getIdComuneNascita().getDescrizione());
                    }
                    anagraficaXml.setLocalitaNascita(anagrafica.getLocalitaNascita());
                    anagraficaXml.setSesso(String.valueOf(anagrafica.getSesso()));
                    if (anagrafica.getIdTipoCollegio() != null) {
                        anagraficaXml.setIdTipoCollegio(Utils.bi(anagrafica.getIdTipoCollegio().getIdTipoCollegio()));
                        anagraficaXml.setDesTipoCollegio(anagrafica.getIdTipoCollegio().getDescrizione());
                    }
                    anagraficaXml.setNumeroIscrizione(anagrafica.getNumeroIscrizione());
                    anagraficaXml.setDataIscrizione(Utils.dateToXmlGregorianCalendar(anagrafica.getDataIscrizione()));
                    if (anagrafica.getIdProvinciaIscrizione() != null) {
                        anagraficaXml.setIdProvinciaIscrizione(Utils.bi(anagrafica.getIdProvinciaIscrizione().getIdProvincie()));
                        anagraficaXml.setDesProvinciaIscrizione(anagrafica.getIdProvinciaIscrizione().getDescrizione());
                    }
                    if (anagrafica.getIdProvinciaCciaa() != null) {
                        anagraficaXml.setIdProvinciaCciaa(Utils.bi(anagrafica.getIdProvinciaCciaa().getIdProvincie()));
                        anagraficaXml.setDesProvinciaCciaa(anagrafica.getIdProvinciaCciaa().getDescrizione());
                    }
                    if (anagrafica.getFlgAttesaIscrizioneRi() != null) {
                    	/*Patch produzione RI e REA */
                        //anagraficaXml.setFlgAttesaIscrizioneRi(String.valueOf(anagrafica.getFlgAttesaIscrizioneRi()).equalsIgnoreCase("S") ? Boolean.TRUE : Boolean.FALSE);
                    	anagraficaXml.setFlgAttesaIscrizioneRi(true);
                    }
                    if (anagrafica.getFlgObbligoIscrizioneRi() != null) {
                        anagraficaXml.setFlgObbligoIscrizioneRi(String.valueOf(anagrafica.getFlgObbligoIscrizioneRi()).equalsIgnoreCase("S") ? Boolean.TRUE : Boolean.FALSE);
                    }
                    anagraficaXml.setDataIscrizioneRi(Utils.dateToXmlGregorianCalendar(anagrafica.getDataIscrizioneRi()));
                    anagraficaXml.setNIscrizioneRi(anagrafica.getNIscrizioneRi());
                    if (anagrafica.getFlgAttesaIscrizioneRea() != null) {
                    	/*Patch produzione RI e REA */
                        //anagraficaXml.setFlgAttesaIscrizioneRea(String.valueOf(anagrafica.getFlgAttesaIscrizioneRea()).equalsIgnoreCase("S") ? Boolean.TRUE : Boolean.FALSE);
                    	anagraficaXml.setFlgAttesaIscrizioneRea(true);
                    }
                    anagraficaXml.setDataIscrizioneRea(Utils.dateToXmlGregorianCalendar(anagrafica.getDataIscrizioneRea()));
                    anagraficaXml.setNIscrizioneRea(anagrafica.getNIscrizioneRea());
                    anagraficaXml.setRecapiti(recapitiAnagraficaXmlList);

                    anagraficheXml.setNotifica(recapitoXml);
                    anagraficheXml.setAnagrafica(anagraficaXml);
                    if (praticaAnagrafica.getLkTipoRuolo() != null) {
                        anagraficheXml.setIdTipoRuolo(Utils.bi(praticaAnagrafica.getLkTipoRuolo().getIdTipoRuolo()));
                        anagraficheXml.setDesTipoRuolo(praticaAnagrafica.getLkTipoRuolo().getDescrizione());
                    }
                    anagraficheXml.setDataInizioValidita(Utils.dateToXmlGregorianCalendar(praticaAnagrafica.getDataInizioValidita()));
                    if (praticaAnagrafica.getIdTipoQualifica() != null) {
                        anagraficheXml.setIdTipoQualifica(Utils.bi(praticaAnagrafica.getIdTipoQualifica().getIdTipoQualifica()));
                        anagraficheXml.setDesTipoQualifica(praticaAnagrafica.getIdTipoQualifica().getDescrizione());
                    }
                    anagraficheXml.setDescrizioneTitolarita(praticaAnagrafica.getDescrizioneTitolarita());
                    if (praticaAnagrafica.getAssensoUsoPec() != null) {
                        anagraficheXml.setAssensoUsoPec(String.valueOf(praticaAnagrafica.getAssensoUsoPec()).equalsIgnoreCase("S") ? Boolean.TRUE : Boolean.FALSE);
                    }
                    anagraficheXml.setPec(praticaAnagrafica.getPec());

                    anagraficheXmlList.add(anagraficheXml);
                }
            }
        }
        return anagraficheXmlList;
    }

    public static AnagraficaDTO serializeAnagrafica(Anagrafica anagraficaEntity) {
        AnagraficaDTO anagraficaDTO = new AnagraficaDTO();
        anagraficaDTO.setIdAnagrafica(anagraficaEntity.getIdAnagrafica());
        anagraficaDTO.setTipoAnagrafica(String.valueOf(anagraficaEntity.getTipoAnagrafica()));
        anagraficaDTO.setIdTipoPersona(String.valueOf(anagraficaEntity.getTipoAnagrafica()));
        //^^cs aggiunta
        //anagraficaDTO.setVarianteAnagrafica(anagraficaEntity.getVarianteAnagrafica());
        anagraficaDTO.setVarianteAnagrafica(anagraficaEntity.getVarianteAnagrafica());
        anagraficaDTO.setNome(anagraficaEntity.getNome());
        anagraficaDTO.setCognome(anagraficaEntity.getCognome());
        anagraficaDTO.setCodiceFiscale(anagraficaEntity.getCodiceFiscale());
        anagraficaDTO.setDenominazione(anagraficaEntity.getDenominazione());
        anagraficaDTO.setPartitaIva(anagraficaEntity.getPartitaIva());
        anagraficaDTO.setFlgIndividuale(String.valueOf(anagraficaEntity.getFlgIndividuale()));
        if (anagraficaEntity.getIdFormaGiuridica() != null) {
            anagraficaDTO.setIdFormaGiuridica(anagraficaEntity.getIdFormaGiuridica().getIdFormeGiuridiche());
            anagraficaDTO.setDesFormaGiuridica(anagraficaEntity.getIdFormaGiuridica().getDescrizione());
        }
        anagraficaDTO.setDataNascita(anagraficaEntity.getDataNascita());
        if (anagraficaEntity.getIdCittadinanza() != null) {
            anagraficaDTO.setIdCittadinanza(anagraficaEntity.getIdCittadinanza().getIdCittadinanza());
            anagraficaDTO.setDesCittadinanza(anagraficaEntity.getIdCittadinanza().getDescrizione());
        }
        if (anagraficaEntity.getIdNazionalita() != null) {
            anagraficaDTO.setIdNazionalita(anagraficaEntity.getIdNazionalita().getIdNazionalita());
            anagraficaDTO.setDesNazionalita(anagraficaEntity.getIdNazionalita().getDescrizione());
        }
        if (anagraficaEntity.getIdComuneNascita() != null) {
            ComuneDTO comune = LuoghiSerializer.serialize(anagraficaEntity.getIdComuneNascita());
            anagraficaDTO.setComuneNascita(comune);
//            anagraficaDTO.getComuneNascita().setDescrizione(anagraficaEntity.getIdComuneNascita().getDescrizione());
        }
        anagraficaDTO.setLocalitaNascita(anagraficaEntity.getLocalitaNascita());
        if (anagraficaEntity.getSesso() != null) {
            anagraficaDTO.setSesso(Utils.Char(anagraficaEntity.getSesso()));
        }
        if (anagraficaEntity.getIdTipoCollegio() != null) {
            anagraficaDTO.setCodTipoCollegio(anagraficaEntity.getIdTipoCollegio().getIdTipoCollegio());
            anagraficaDTO.setDesTipoCollegio(anagraficaEntity.getIdTipoCollegio().getDescrizione());
        }
        anagraficaDTO.setNumeroIscrizione(anagraficaEntity.getNumeroIscrizione());
        anagraficaDTO.setDataIscrizione(anagraficaEntity.getDataIscrizione());
        if (anagraficaEntity.getIdProvinciaIscrizione() != null) {
//            if (anagraficaDTO.getProvinciaIscrizione() == null) {
//                anagraficaDTO.setProvinciaIscrizione(new ProvinciaDTO());
//            } else {
            ProvinciaDTO provincia = LuoghiSerializer.serialize(anagraficaEntity.getIdProvinciaIscrizione());
            anagraficaDTO.setProvinciaIscrizione(provincia);
//            }
//            anagraficaDTO.getProvinciaIscrizione().setIdProvincie(anagraficaEntity.getIdProvinciaIscrizione().getIdProvincie());
//            anagraficaDTO.getProvinciaIscrizione().setDescrizione(anagraficaEntity.getIdProvinciaIscrizione().getDescrizione());
//            anagraficaDTO.getProvinciaIscrizione().setCodCatastale(anagraficaEntity.getIdProvinciaIscrizione().getCodCatastale());
//            anagraficaDTO.getProvinciaIscrizione().setFlgInfocamere(Utils.flag(anagraficaEntity.getIdProvinciaIscrizione().getFlgInfocamere()));
        }
        if (anagraficaEntity.getIdProvinciaCciaa() != null) {
            ProvinciaDTO provincia = LuoghiSerializer.serialize(anagraficaEntity.getIdProvinciaCciaa());
            anagraficaDTO.setProvinciaCciaa(provincia);
        }
        if (anagraficaEntity.getFlgAttesaIscrizioneRi() != null) {
            anagraficaDTO.setFlgAttesaIscrizioneRi(String.valueOf(anagraficaEntity.getFlgAttesaIscrizioneRi()));
        }
        anagraficaDTO.setDataIscrizioneRi(anagraficaEntity.getDataIscrizioneRi());
        anagraficaDTO.setnIscrizioneRi(anagraficaEntity.getNIscrizioneRi());
        if (anagraficaEntity.getFlgAttesaIscrizioneRea() != null) {
            anagraficaDTO.setFlgAttesaIscrizioneRea(String.valueOf(anagraficaEntity.getFlgAttesaIscrizioneRea()));
        }
        anagraficaDTO.setDataIscrizioneRea(anagraficaEntity.getDataIscrizioneRea());
        anagraficaDTO.setnIscrizioneRea(anagraficaEntity.getNIscrizioneRea());
        if (anagraficaEntity.getIdFormaGiuridica() != null) {
            anagraficaDTO.setDesFormaGiuridica(anagraficaEntity.getIdFormaGiuridica().getDescrizione());
        }

        List<RecapitoDTO> recapitiDto = new ArrayList<RecapitoDTO>();
        if (anagraficaEntity.getAnagraficaRecapitiList() != null && anagraficaEntity.getAnagraficaRecapitiList().size() > 0) {
            for (AnagraficaRecapiti r : anagraficaEntity.getAnagraficaRecapitiList()) {
                RecapitoDTO recapito = RecapitiSerializer.serialize(r.getIdRecapito(), r.getIdTipoIndirizzo());
                recapitiDto.add(recapito);
            }
        }
        anagraficaDTO.setRecapiti(recapitiDto);

        return anagraficaDTO;
    }

    public static AnagraficaDTO serializeAnagrafica(it.wego.cross.plugins.commons.beans.Anagrafica anagrafica) {
        AnagraficaDTO anagraficaDTO = new AnagraficaDTO();
        anagraficaDTO.setTipoAnagrafica(String.valueOf(anagrafica.getTipoAnagrafica()));
        anagraficaDTO.setNome(anagrafica.getNome());
        anagraficaDTO.setCognome(anagrafica.getCognome());
        anagraficaDTO.setCodiceFiscale(anagrafica.getCodiceFiscale());
        anagraficaDTO.setDenominazione(anagrafica.getDenominazione());
        anagraficaDTO.setPartitaIva(anagrafica.getPartitaIva());
        anagraficaDTO.setFlgIndividuale((anagrafica.getIsDittaIndividuale() != null && anagrafica.getIsDittaIndividuale()) ? "S" : "N");
        anagraficaDTO.setDesFormaGiuridica(anagrafica.getFormaGiuridica());
        anagraficaDTO.setDataNascita(anagrafica.getDataNascita());
        anagraficaDTO.setDesCittadinanza(anagrafica.getCittadinanza());
        ComuneDTO comune = new ComuneDTO();
        comune.setDescrizione(anagrafica.getComuneNascita());
        anagraficaDTO.setComuneNascita(comune);
        anagraficaDTO.setLocalitaNascita(anagrafica.getLocalitaNascita());
        anagraficaDTO.setSesso(anagrafica.getSesso());
        anagraficaDTO.setDesTipoCollegio(anagrafica.getCollegio());
        anagraficaDTO.setNumeroIscrizione(anagrafica.getNumeroIscrizione());
        anagraficaDTO.setDataIscrizione(anagrafica.getDataIscrizione());
        ProvinciaDTO provincia = new ProvinciaDTO();
        provincia.setDescrizione(anagrafica.getProvinciaRI());
        anagraficaDTO.setProvinciaCciaa(provincia);
        anagraficaDTO.setFlgAttesaIscrizioneRi((anagrafica.getInAttesaIscrizioneRI() != null && anagrafica.getInAttesaIscrizioneRI()) ? "S" : "N");
        anagraficaDTO.setDataIscrizioneRi(anagrafica.getDataIscrizioneRI());
        anagraficaDTO.setnIscrizioneRi(anagrafica.getNumeroIscrizioneRI());
        anagraficaDTO.setFlgAttesaIscrizioneRea((anagrafica.getInAttesaIscrizioneRea() != null && anagrafica.getInAttesaIscrizioneRea()) ? "S" : "N");
        anagraficaDTO.setDataIscrizioneRea(anagrafica.getDataIscrizioneRea());
        anagraficaDTO.setnIscrizioneRea(anagrafica.getNumeroIscrizioneRea());
        anagraficaDTO.setDesFormaGiuridica(anagrafica.getFormaGiuridica());
        List<RecapitoDTO> recapitiDto = new ArrayList<RecapitoDTO>();
        RecapitoDTO recapito = RecapitiSerializer.serialize(anagrafica.getRecapito());
        recapitiDto.add(recapito);
        anagraficaDTO.setRecapiti(recapitiDto);
        return anagraficaDTO;
    }

    public static CittadinanzaDTO serialize(it.wego.cross.entity.LkCittadinanza cittadinanzaDB, CittadinanzaDTO cittadinanzaDTO) {
        if (cittadinanzaDTO == null) {
            cittadinanzaDTO = new CittadinanzaDTO();
        }
        cittadinanzaDTO.setCodCittadinanza(cittadinanzaDB.getCodCittadinanza());
        cittadinanzaDTO.setDescrizione(cittadinanzaDB.getDescrizione());
        cittadinanzaDTO.setIdCittadinanza(cittadinanzaDB.getIdCittadinanza());
        return cittadinanzaDTO;
    }

    public Anagrafica serialize(AnagraficaDTO a, Anagrafica r) {
        if (r == null) {
            r = new Anagrafica();
        }
        r.setVarianteAnagrafica(a.getVarianteAnagrafica());
        r.setTipoAnagrafica(a.getIdTipoPersona().charAt(0));
        r.setCognome(a.getCognome());
        r.setNome(a.getNome());
        r.setDenominazione(a.getDenominazione());
        if (!Strings.isNullOrEmpty(a.getPartitaIva())) {
            r.setPartitaIva(a.getPartitaIva());
        } else {
            r.setPartitaIva(null);
        }
        r.setCodiceFiscale(a.getCodiceFiscale());

//        r.setVarianteAnagrafica(anagraficaUtils.getVarianteAnagrafica(a));
        if (!Strings.isNullOrEmpty(a.getFlgIndividuale())) {
            r.setFlgIndividuale(a.getFlgIndividuale().charAt(0));
        } else {
            r.setFlgIndividuale('N');
        }
        r.setDataNascita(a.getDataNascita());

        if (a.getIdCittadinanza() != null) {
            LkCittadinanza cittadinanza = lookupDao.findCittadinanzaByID(a.getIdCittadinanza());
            r.setIdCittadinanza(cittadinanza);
        }

        if (a.getIdNazionalita() != null) {
            LkNazionalita nazionalita = lookupDao.findNazionalitaByID(a.getIdNazionalita());
            r.setIdNazionalita(nazionalita);
        }

        if (a.getComuneNascita() != null && a.getComuneNascita().getIdComune() != null) {
            LkComuni comune = lookupDao.findComuneById(a.getComuneNascita().getIdComune());
            r.setIdComuneNascita(comune);
        }

        r.setLocalitaNascita(a.getLocalitaNascita());

        if (!Strings.isNullOrEmpty(a.getSesso())) {
            r.setSesso(a.getSesso().charAt(0));
        }
        if (a.getCodTipoCollegio() != null) {
            LkTipoCollegio tipoCollegio = lookupDao.findLookupTipoCollegioById(a.getCodTipoCollegio());
            r.setIdTipoCollegio(tipoCollegio);
        }
        r.setNumeroIscrizione(a.getNumeroIscrizione());
        r.setDataIscrizione(a.getDataIscrizione());
        if (a.getProvinciaIscrizione() != null && a.getProvinciaIscrizione().getIdProvincie() != null) {
            LkProvincie provincia = lookupDao.findLkProvinciaById(a.getProvinciaIscrizione().getIdProvincie());
            r.setIdProvinciaIscrizione(provincia);
        }
        if (a.getProvinciaCciaa() != null && a.getProvinciaCciaa().getIdProvincie() != null) {
            LkProvincie provincia = lookupDao.findLkProvinciaById(a.getProvinciaCciaa().getIdProvincie());
            r.setIdProvinciaCciaa(provincia);
        }
        if (!Strings.isNullOrEmpty(a.getFlgAttesaIscrizioneRi())) {
            r.setFlgAttesaIscrizioneRi(a.getFlgAttesaIscrizioneRi().charAt(0));
        } else {
            /*Patch produzione RI e REA */
        	//r.setFlgAttesaIscrizioneRi('N');
            r.setFlgAttesaIscrizioneRi('S');
        }
        if (a.getFlgObbligoIscrizioneRi() != null) {
            r.setFlgObbligoIscrizioneRi(a.getFlgObbligoIscrizioneRi().charAt(0));
        }
        if (!Strings.isNullOrEmpty(a.getFlgAttesaIscrizioneRea())) {
            r.setFlgAttesaIscrizioneRea(a.getFlgAttesaIscrizioneRea().charAt(0));
        } else {
        	/*Patch produzione RI e REA */
            //r.setFlgAttesaIscrizioneRea('N');
            r.setFlgAttesaIscrizioneRea('S');
        }
        r.setDataIscrizioneRi(a.getDataIscrizioneRi());
        r.setNIscrizioneRi(a.getnIscrizioneRi());
        r.setDataIscrizioneRea(a.getDataIscrizioneRea());
        r.setNIscrizioneRea(a.getnIscrizioneRea());
        if (a.getIdFormaGiuridica() != null) {
            LkFormeGiuridiche formaGiuridica = lookupDao.findLkFormeGiuridicheById(a.getIdFormaGiuridica());
            r.setIdFormaGiuridica(formaGiuridica);
        }

        return r;
    }

//    public static AnagraficaDTO serializeAnagrafica(it.wego.cross.xml.Anagrafiche anagrafiche) {
//        AnagraficaDTO dto = new AnagraficaDTO();
//        
//        it.wego.cross.xml.Anagrafica anagrafica = anagrafiche.getAnagrafica();
//        AnagraficaUtils.getTipologiaAnagrafica(anagrafica);
//        
//        if (anagrafica.getIdTipoCollegio() != null) {
//            dto.setCodTipoCollegio(anagrafica.getIdTipoCollegio().intValue());
//        }
//        dto.setCodTipoRuolo(anagrafiche.getCodTipoRuolo());
//        if (Utils.e(anagrafica.getConfermata())) {
//            dto.setConfermata(null);
//        } else {
//            dto.setConfermata(anagrafica.getConfermata());
//        }
//
//
//        
//        dto.setVarianteAnagrafica(anagrafica.getVarianteAnagrafica());
//        dto.setCodiceFiscale(anagrafica.getCodiceFiscale());
//        dto.setCognome(anagrafica.getCognome());
//        if (anagrafica.getDesComuneNascita() != null) {
//            ComuneDTO comune = new ComuneDTO();
//            comune.setDescrizione(anagrafica.getDesComuneNascita());
//            if (anagrafica.getIdComuneNascita() != null) {
//                comune.setIdComune(anagrafica.getIdComuneNascita().intValue());
//            }
//            dto.setComuneNascita(comune);
//        }
//        if (anagrafica.getCounter() != null) {
//            dto.setCounter(anagrafica.getCounter().intValue());
//        }
//        if (anagrafiche.getDataInizioValidita() != null) {
//            Date dataInizioValidita = Utils.xmlGregorianCalendarToDate(anagrafiche.getDataInizioValidita());
//            dto.setDataInizioValidita(dataInizioValidita);
//        }
//        if (anagrafica.getDataIscrizione() != null) {
//            Date dataIscrizione = Utils.xmlGregorianCalendarToDate(anagrafica.getDataIscrizione());
//            dto.setDataIscrizione(dataIscrizione);
//        }
//        if (anagrafica.getDataIscrizioneRea() != null) {
//            Date dataIscrizioneRea = Utils.xmlGregorianCalendarToDate(anagrafica.getDataIscrizioneRea());
//            dto.setDataIscrizioneRea(dataIscrizioneRea);
//        }
//        if (anagrafica.getDataIscrizioneRi() != null) {
//            Date dataIscrizioneRi = Utils.xmlGregorianCalendarToDate(anagrafica.getDataIscrizioneRi());
//            dto.setDataIscrizioneRi(dataIscrizioneRi);
//        }
//        if (anagrafica.getDataNascita() != null) {
//            Date dataNascita = Utils.xmlGregorianCalendarToDate(anagrafica.getDataNascita());
//            dto.setDataNascita(dataNascita);
//        }
//        dto.setDenominazione(anagrafica.getDenominazione());
//        dto.setDesCittadinanza(anagrafica.getDesCittadinanza());
//        dto.setDesNazionalita(anagrafica.getDesNazionalita());
//        dto.setDesFormaGiuridica(anagrafica.getDesFormaGiuridica());
//        dto.setDesTipoCollegio(anagrafica.getDesTipoCollegio());
//        dto.setDesTipoRuolo(anagrafiche.getDesTipoRuolo());
//        dto.setDescrizioneTitolarita(anagrafiche.getDescrizioneTitolarita());
//        dto.setFlgIndividuale(anagrafica.getFlgIndividuale());
//        if (anagrafica.getIdAnagrafica() != null) {
//            dto.setIdAnagrafica(anagrafica.getIdAnagrafica().intValue());
//        }
//        if (anagrafica.getIdCittadinanza() != null) {
//            dto.setIdCittadinanza(anagrafica.getIdCittadinanza().intValue());
//        }
//        if (anagrafica.getIdNazionalita() != null) {
//            dto.setIdNazionalita(anagrafica.getIdNazionalita().intValue());
//        }
//        if (anagrafica.getIdFormaGiuridica() != null) {
//            dto.setIdFormaGiuridica(anagrafica.getIdFormaGiuridica().intValue());
//        }
//        if (anagrafica.getIdProvinciaIscrizione() != null) {
//            dto.setIdProvinciaIscrizione(anagrafica.getIdProvinciaIscrizione().intValue());
//        }
//        if (anagrafica.getIdTipoCollegio() != null) {
//            dto.setIdTipoCollegio(anagrafica.getIdTipoCollegio().intValue());
//        }
//        if (anagrafiche.getIdTipoRuolo() != null) {
//            dto.setIdTipoRuolo(anagrafiche.getIdTipoRuolo().intValue());
//        }
//        dto.setLocalitaNascita(anagrafica.getLocalitaNascita());
//        dto.setNome(anagrafica.getNome());
//        dto.setNumeroIscrizione(anagrafica.getNumeroIscrizione());
//        dto.setPartitaIva(anagrafica.getPartitaIva());
//        if (anagrafica.getDesProvinciaCciaa() != null) {
//            ProvinciaDTO provincia = new ProvinciaDTO();
//            provincia.setDescrizione(anagrafica.getDesProvinciaCciaa());
//            dto.setProvinciaCciaa(provincia);
//        }
//        if (anagrafica.getDesProvinciaIscrizione() != null) {
//            ProvinciaDTO provincia = new ProvinciaDTO();
//            provincia.setDescrizione(anagrafica.getDesProvinciaIscrizione());
//            if (anagrafica.getIdProvinciaIscrizione() != null) {
//                provincia.setIdProvincie(anagrafica.getIdProvinciaIscrizione().intValue());
//            }
//            dto.setProvinciaIscrizione(provincia);
//        }
//        dto.setSesso(anagrafica.getSesso());
//        dto.setTipoAnagrafica(anagrafica.getTipoAnagrafica());
//        dto.setnIscrizioneRea(anagrafica.getNIscrizioneRea());
//        dto.setnIscrizioneRi(anagrafica.getNIscrizioneRi());
//        //Per ora non la gestisco
//        //dto.setNotifica(null);
//        if (anagrafica.getRecapiti() != null && anagrafica.getRecapiti().getRecapito() != null && anagrafica.getRecapiti().getRecapito().size() > 0) {
//            List<it.wego.cross.xml.Recapito> recapitiXml = anagrafica.getRecapiti().getRecapito();
//            List<RecapitoDTO> recapiti = new ArrayList<RecapitoDTO>();
//            for (it.wego.cross.xml.Recapito r : recapitiXml) {
//                RecapitoDTO recapito = RecapitiSerializer.serialize(r);
//                recapiti.add(recapito);
//            }
//            dto.setRecapiti(recapiti);
//        }
//
//        return dto;
//    }
    public static AnagraficaDTO serialize(SoggettoProtocollo anagrafica, Integer codice) {
        AnagraficaDTO dto = new AnagraficaDTO();
        Log.APP.info("Serializzo il soggetto da protocollo");
        Log.APP.info("Codice fiscale: " + anagrafica.getCodiceFiscale());
        dto.setCodiceFiscale(anagrafica.getCodiceFiscale());
        Log.APP.info("Partita IVA: " + anagrafica.getPartitaIva());
        dto.setPartitaIva(anagrafica.getPartitaIva());
        Log.APP.info("Denominazione: " + anagrafica.getDenominazione());
        dto.setDenominazione(anagrafica.getDenominazione());
        Log.APP.info("Nome: " + anagrafica.getNome());
        dto.setNome(anagrafica.getNome());
        Log.APP.info("Cognome: " + anagrafica.getCognome());
        dto.setCognome(anagrafica.getCognome());
        Log.APP.info("Cittadinanza: " + anagrafica.getCittadinanza());
        dto.setDesCittadinanza(anagrafica.getCittadinanza());
        Log.APP.info("Data di nascita: " + anagrafica.getDataNascita());
        dto.setDataNascita(anagrafica.getDataNascita());
        Log.APP.info("Comune di nascita: " + anagrafica.getComuneNascita());
        if (anagrafica.getComuneNascita() != null && !"".equals(anagrafica.getComuneNascita())) {
            ComuneDTO comune = new ComuneDTO();
            Log.APP.info("");
            comune.setDescrizione(anagrafica.getComuneNascita());
            dto.setComuneNascita(comune);
        }
        //Identificativo necessario per il layout: non coincide con l'id dell'anagrafica
        dto.setCounter(codice);
        List<RecapitoDTO> recapiti = new ArrayList<RecapitoDTO>();
        RecapitoDTO recapito = new RecapitoDTO();
        Log.APP.info("Indirizzo: " + anagrafica.getIndirizzo());
        recapito.setIndirizzo(anagrafica.getIndirizzo());
        Log.APP.info("Citta: " + anagrafica.getCitta());
        recapito.setDescComune(anagrafica.getCitta());
        Log.APP.info("CAP: " + anagrafica.getCap());
        recapito.setCap(anagrafica.getCap());
        Log.APP.info("Email: " + anagrafica.getMail());
        recapito.setEmail(anagrafica.getMail());
        Log.APP.info("PEC: " + anagrafica.getMail());
        recapito.setPec(anagrafica.getMail());
        recapiti.add(recapito);
        dto.setRecapiti(recapiti);
        return dto;
    }

    public static RuoloAnagraficaDTO serializeTipoRuolo(LkTipoRuolo tipoRuolo) {
        RuoloAnagraficaDTO dto = new RuoloAnagraficaDTO();
        dto.setDesTipoRuolo(tipoRuolo.getDescrizione());
        dto.setIdTipoRuolo(tipoRuolo.getIdTipoRuolo());
        return dto;
    }

    public static TipoQualificaAnagraficaDTO serializeTipoQualifica(LkTipoQualifica qualifica) {
        TipoQualificaAnagraficaDTO dto = new TipoQualificaAnagraficaDTO();
        dto.setDesTipoQualifica(qualifica.getDescrizione());
        dto.setIdTipoQualifica(qualifica.getIdTipoQualifica());
        return dto;
    }

    public it.wego.cross.xml.Anagrafica serialize(AnagraficaDTO anagrafica) throws DatatypeConfigurationException {
        it.wego.cross.xml.Anagrafica anagraficaXml = new it.wego.cross.xml.Anagrafica();
        if (anagrafica.getIdAnagrafica() != null) {
            anagraficaXml.setIdAnagrafica(Utils.bi(anagrafica.getIdAnagrafica()));
        }
        anagraficaXml.setCounter(Utils.bi(anagrafica.getCounter()));
        anagraficaXml.setTipoAnagrafica(String.valueOf(anagrafica.getTipoAnagrafica()));
        anagraficaXml.setVarianteAnagrafica(anagraficaUtils.getTipoAnagrafica(anagrafica));
        anagraficaXml.setCognome(anagrafica.getCognome());
        anagraficaXml.setDenominazione(anagrafica.getDenominazione());
        anagraficaXml.setNome(anagrafica.getNome());
        anagraficaXml.setCodiceFiscale(anagrafica.getCodiceFiscale());
        anagraficaXml.setPartitaIva(anagrafica.getPartitaIva());
        if (Utils.e(anagrafica.getConfermata())) {
            anagraficaXml.setConfermata(null);
        } else {
            anagraficaXml.setConfermata(anagrafica.getConfermata());
        }
        if (anagrafica.getDataNascita() != null) {
            anagraficaXml.setDataNascita(Utils.dateToXmlGregorianCalendar(anagrafica.getDataNascita()));
        }
        if (anagrafica.getIdCittadinanza() != null) {
            anagraficaXml.setIdCittadinanza(Utils.bi(anagrafica.getIdCittadinanza()));
        }
        anagraficaXml.setDesCittadinanza(anagrafica.getDesCittadinanza());
        if (anagrafica.getIdNazionalita() != null) {
            anagraficaXml.setIdNazionalita(Utils.bi(anagrafica.getIdNazionalita()));
            anagraficaXml.setDesNazionalita(anagrafica.getDesNazionalita());
        }
        if (anagrafica.getComuneNascita() != null) {
            ComuneDTO comune = anagrafica.getComuneNascita();
            if (comune.getIdComune() != null) {
                anagraficaXml.setIdComuneNascita(Utils.bi(comune.getIdComune()));
            }
            anagraficaXml.setDesComuneNascita(comune.getDescrizione());
        }

        anagraficaXml.setLocalitaNascita(anagrafica.getLocalitaNascita());
        if (anagrafica.getSesso() != null) {
            anagraficaXml.setSesso(String.valueOf(anagrafica.getSesso()));
        }
        if (anagrafica.getIdTipoCollegio() != null) {
            anagraficaXml.setIdTipoCollegio(Utils.bi(anagrafica.getIdTipoCollegio()));
        }
        anagraficaXml.setDesTipoCollegio(anagrafica.getDesTipoCollegio());
        anagraficaXml.setNumeroIscrizione(anagrafica.getNumeroIscrizione());
        if (anagrafica.getDataIscrizione() != null) {
            anagraficaXml.setDataIscrizione(Utils.dateToXmlGregorianCalendar(anagrafica.getDataIscrizione()));
        }
        if (anagrafica.getProvinciaIscrizione() != null) {
            ProvinciaDTO provincia = anagrafica.getProvinciaIscrizione();
            if (provincia.getIdProvincie() != null) {
                anagraficaXml.setIdProvinciaIscrizione(Utils.bi(provincia.getIdProvincie()));
            }
            anagraficaXml.setDesProvinciaIscrizione(provincia.getDescrizione());
        }
        if (anagrafica.getProvinciaCciaa() != null) {
            ProvinciaDTO provincia = anagrafica.getProvinciaCciaa();
            if (provincia.getIdProvincie() != null) {
                anagraficaXml.setIdProvinciaCciaa(Utils.bi(provincia.getIdProvincie()));
            }
            anagraficaXml.setDesProvinciaCciaa(provincia.getDescrizione());
        }
        if (anagrafica.getFlgAttesaIscrizioneRi() != null) {
            anagraficaXml.setFlgAttesaIscrizioneRi(String.valueOf(anagrafica.getFlgAttesaIscrizioneRi()).equalsIgnoreCase("S") ? Boolean.TRUE : Boolean.FALSE);
        }
        if (anagrafica.getFlgObbligoIscrizioneRi() != null) {
            anagraficaXml.setFlgObbligoIscrizioneRi(String.valueOf(anagrafica.getFlgObbligoIscrizioneRi()).equalsIgnoreCase("S") ? Boolean.TRUE : Boolean.FALSE);
        }
        if (anagrafica.getDataIscrizioneRi() != null) {
            anagraficaXml.setDataIscrizioneRi(Utils.dateToXmlGregorianCalendar(anagrafica.getDataIscrizioneRi()));
        }
        anagraficaXml.setNIscrizioneRi(anagrafica.getnIscrizioneRi());
        if (anagrafica.getFlgAttesaIscrizioneRea() != null) {
            anagraficaXml.setFlgAttesaIscrizioneRea(String.valueOf(anagrafica.getFlgAttesaIscrizioneRea()).equalsIgnoreCase("S") ? Boolean.TRUE : Boolean.FALSE);
        }
        if (anagrafica.getDataIscrizioneRea() != null) {
            anagraficaXml.setDataIscrizioneRea(Utils.dateToXmlGregorianCalendar(anagrafica.getDataIscrizioneRea()));
        }
        anagraficaXml.setNIscrizioneRea(anagrafica.getnIscrizioneRea());
        return anagraficaXml;
    }

    /**
     * ^^CS AGGIUNTA Serializzatore DB -> XML ATTENZIONE: - non setta COUNTER -
     * non setta CONFERMATA
     *
     * @param praticaAnagrafica
     * @return Anagrafiche
     */
    public static it.wego.cross.xml.Anagrafiche serialize(PraticaAnagrafica praticaAnagrafica) throws DatatypeConfigurationException {
        it.wego.cross.xml.Anagrafiche anagraficheXML = new it.wego.cross.xml.Anagrafiche();
        it.wego.cross.xml.Anagrafica anagraficaXML = new it.wego.cross.xml.Anagrafica();
        it.wego.cross.entity.Anagrafica anagraficaDB = praticaAnagrafica.getAnagrafica();
        anagraficaXML.setCodiceFiscale(anagraficaDB.getCodiceFiscale());
        anagraficaXML.setCognome(anagraficaDB.getCognome());
        //anagraficaXML.setConfermata(anagraficaDB.getCognome());
        anagraficaXML.setDataIscrizione(Utils.dateToXmlGregorianCalendar(anagraficaDB.getDataIscrizione()));
        anagraficaXML.setDataIscrizioneRea(Utils.dateToXmlGregorianCalendar(anagraficaDB.getDataIscrizioneRea()));
        anagraficaXML.setDataIscrizioneRi(Utils.dateToXmlGregorianCalendar(anagraficaDB.getDataIscrizioneRi()));
        anagraficaXML.setDataNascita(Utils.dateToXmlGregorianCalendar(anagraficaDB.getDataNascita()));
        anagraficaXML.setDenominazione(anagraficaDB.getDenominazione());
        if (anagraficaDB.getIdCittadinanza() != null) {
            anagraficaXML.setDesCittadinanza(anagraficaDB.getIdCittadinanza().getDescrizione());
            anagraficaXML.setIdCittadinanza(Utils.bi(anagraficaDB.getIdCittadinanza().getIdCittadinanza()));
        }
        if (anagraficaDB.getIdNazionalita() != null) {
            anagraficaXML.setDesNazionalita(anagraficaDB.getIdNazionalita().getDescrizione());
            anagraficaXML.setIdNazionalita(Utils.bi(anagraficaDB.getIdNazionalita().getIdNazionalita()));
        }
        if (anagraficaDB.getIdComuneNascita() != null) {
            anagraficaXML.setIdComuneNascita(Utils.bi(anagraficaDB.getIdComuneNascita().getIdComune()));
            anagraficaXML.setDesComuneNascita(anagraficaDB.getIdComuneNascita().getDescrizione());
            if (anagraficaDB.getIdComuneNascita().getIdStato() != null) {
                anagraficaXML.setDesStatoNascita(anagraficaDB.getIdComuneNascita().getIdStato().getDescrizione());
                anagraficaXML.setIdStatoNascita(Utils.bi(anagraficaDB.getIdComuneNascita().getIdStato().getIdStato()));
            }
            if (anagraficaDB.getIdComuneNascita().getIdProvincia() != null) {
                anagraficaXML.setIdProvinciaNascita(Utils.bi(anagraficaDB.getIdComuneNascita().getIdProvincia().getIdProvincie()));
                anagraficaXML.setDesProvinciaNascita(anagraficaDB.getIdComuneNascita().getIdProvincia().getDescrizione());
            }
        }
        if (anagraficaDB.getIdFormaGiuridica() != null) {
            anagraficaXML.setDesFormaGiuridica(anagraficaDB.getIdFormaGiuridica().getDescrizione());
            anagraficaXML.setIdFormaGiuridica(Utils.bi(anagraficaDB.getIdFormaGiuridica().getIdFormeGiuridiche()));
        }
        if (anagraficaDB.getIdProvinciaCciaa() != null) {
            anagraficaXML.setDesProvinciaCciaa(anagraficaDB.getIdProvinciaCciaa().getDescrizione());
            anagraficaXML.setIdProvinciaCciaa(Utils.bi(anagraficaDB.getIdProvinciaCciaa().getIdProvincie()));
        }

        if (anagraficaDB.getIdProvinciaIscrizione() != null) {
            anagraficaXML.setDesProvinciaIscrizione(anagraficaDB.getIdProvinciaIscrizione().getDescrizione());
            anagraficaXML.setIdProvinciaIscrizione(Utils.bi(anagraficaDB.getIdProvinciaIscrizione().getIdProvincie()));
        }
        if (anagraficaDB.getIdTipoCollegio() != null) {
            anagraficaXML.setIdTipoCollegio(Utils.bi(anagraficaDB.getIdTipoCollegio().getIdTipoCollegio()));
            anagraficaXML.setDesTipoCollegio(anagraficaDB.getIdTipoCollegio().getDescrizione());
        }

        anagraficaXML.setFlgAttesaIscrizioneRea(Utils.flagB(anagraficaDB.getFlgAttesaIscrizioneRea()));
        anagraficaXML.setFlgAttesaIscrizioneRi(Utils.flagB(anagraficaDB.getFlgAttesaIscrizioneRi()));
        anagraficaXML.setFlgIndividuale(Utils.flag(anagraficaDB.getFlgIndividuale()));
        anagraficaXML.setFlgObbligoIscrizioneRi(Utils.flagB(anagraficaDB.getFlgObbligoIscrizioneRi()));
        anagraficaXML.setIdAnagrafica(Utils.bi(anagraficaDB.getIdAnagrafica()));

        anagraficaXML.setLocalitaNascita(anagraficaDB.getLocalitaNascita());
        anagraficaXML.setNIscrizioneRea(anagraficaDB.getNIscrizioneRea());
        anagraficaXML.setNIscrizioneRi(anagraficaDB.getNIscrizioneRi());
        anagraficaXML.setNome(anagraficaDB.getNome());
        anagraficaXML.setNumeroIscrizione(anagraficaDB.getNumeroIscrizione());
        anagraficaXML.setPartitaIva(anagraficaDB.getPartitaIva());
        anagraficaXML.setSesso(String.valueOf(anagraficaDB.getSesso()));
        anagraficaXML.setTipoAnagrafica(String.valueOf(anagraficaDB.getTipoAnagrafica()));
        anagraficaXML.setVarianteAnagrafica(anagraficaDB.getVarianteAnagrafica());
        it.wego.cross.xml.Recapiti recapitiXML = new it.wego.cross.xml.Recapiti();

        if (anagraficaDB.getAnagraficaRecapitiList() != null && anagraficaDB.getAnagraficaRecapitiList().size() > 0) {
            int counter = 0;
            for (it.wego.cross.entity.AnagraficaRecapiti anagraficaRecapiti : anagraficaDB.getAnagraficaRecapitiList()) {
                it.wego.cross.xml.Recapito recapitoXML = RecapitiSerializer.serialize(anagraficaRecapiti.getIdRecapito());
                recapitoXML.setCounter(BigInteger.valueOf(counter));
                if (anagraficaRecapiti.getIdTipoIndirizzo() != null) {
                    recapitoXML.setDesTipoIndirizzo(anagraficaRecapiti.getIdTipoIndirizzo().getDescrizione());
                    recapitoXML.setIdTipoIndirizzo(Utils.bi(anagraficaRecapiti.getIdTipoIndirizzo().getIdTipoIndirizzo()));
                }
                recapitiXML.getRecapito().add(recapitoXML);
                counter++;
            }
        }
        anagraficaXML.setRecapiti(recapitiXML);
        anagraficheXML.setAnagrafica(anagraficaXML);
        anagraficheXML.setAssensoUsoPec(Utils.flagB(praticaAnagrafica.getAssensoUsoPec()));
        if (praticaAnagrafica.getLkTipoRuolo() != null) {
            anagraficheXML.setCodTipoRuolo(praticaAnagrafica.getLkTipoRuolo().getCodRuolo());
            anagraficheXML.setDesTipoRuolo(praticaAnagrafica.getLkTipoRuolo().getDescrizione());
            anagraficheXML.setIdTipoRuolo(Utils.bi(praticaAnagrafica.getLkTipoRuolo().getIdTipoRuolo()));
        }
        anagraficheXML.setDataInizioValidita(Utils.dateToXmlGregorianCalendar(praticaAnagrafica.getDataInizioValidita()));
        if (praticaAnagrafica.getIdTipoQualifica() != null) {
            anagraficheXML.setDesTipoQualifica(praticaAnagrafica.getIdTipoQualifica().getDescrizione());
            anagraficheXML.setIdTipoQualifica(Utils.bi(praticaAnagrafica.getIdTipoQualifica().getIdTipoQualifica()));
        }
        anagraficheXML.setDescrizioneTitolarita(praticaAnagrafica.getDescrizioneTitolarita());
        if (praticaAnagrafica.getIdRecapitoNotifica() != null) {
            anagraficheXML.setNotifica(RecapitiSerializer.serialize(praticaAnagrafica.getIdRecapitoNotifica()));
        }
        anagraficheXML.setPec(praticaAnagrafica.getPec());
        return anagraficheXML;
    }

    public static NazionalitaDTO serialize(LkNazionalita nazionalita) {
        NazionalitaDTO dto = new NazionalitaDTO();
        dto.setDescrizione(nazionalita.getDescrizione());
        dto.setIdNazionalita(nazionalita.getIdNazionalita());
        dto.setCodNazionalita(nazionalita.getCodNazionalita());
        return dto;
    }

    public static AnagraficaDaCollegareDTO serializeAnagraficaDaCollegare(Anagrafica anagrafica) {
        AnagraficaDaCollegareDTO dto = new AnagraficaDaCollegareDTO();
        dto.setTipologia(String.valueOf(anagrafica.getTipoAnagrafica()));
        dto.setNome(anagrafica.getNome());
        dto.setCognome(anagrafica.getCognome());
        dto.setCodiceFiscale(anagrafica.getCodiceFiscale());
        dto.setDenominazione(anagrafica.getDenominazione());
        dto.setPartitaIva(anagrafica.getPartitaIva());
        dto.setVariante(anagrafica.getVarianteAnagrafica());
        dto.setIdAnagrafica(anagrafica.getIdAnagrafica());
        return dto;
    }
    
    public PraticaAnagrafica serializePraticaSUAP(it.wego.cross.xml.Anagrafiche xml, Pratica pratica) throws Exception {
        it.wego.cross.xml.Anagrafica axml = xml.getAnagrafica();
        Anagrafica a = serializeAnagrafica(axml);
        anagraficheDao.insert(a);
        usefulDao.flush();
        //Se ho una nuova anagrafica gli aggiungo il recapito
        //eliminato controllo sell'anagrafica è null per la pratica suap a.getIdAnagrafica() == null && 
        if (axml.getRecapiti() != null
                && axml.getRecapiti().getRecapito() != null
                && !axml.getRecapiti().getRecapito().isEmpty()) {
            //usefulDao.flush();
            for (it.wego.cross.xml.Recapito recapito : axml.getRecapiti().getRecapito()) {
                Recapiti r = recapitiSerializer.serializeEntity(recapito);
                anagraficheDao.insert(r);
                usefulDao.flush();
                AnagraficaRecapiti ar = new AnagraficaRecapiti();
                ar.setIdAnagrafica(a);
                ar.setIdRecapito(r);
                LkTipoIndirizzo tipoIndirizzo = lookupDao.findTipoIndirizzoById(Utils.ib(recapito.getIdTipoIndirizzo()));
                ar.setIdTipoIndirizzo(tipoIndirizzo);
                anagraficheDao.insert(ar);
            }
        }
        //04/09/2019 inseriti flush e refresh per committare l'inserimento dell'anagrafica
        //usefulDao.flush();
        //usefulDao.refresh(a);
        PraticaAnagrafica p = new PraticaAnagrafica();
        PraticaAnagraficaPK key = new PraticaAnagraficaPK();
        key.setIdPratica(pratica.getIdPratica());
        if(a.getIdAnagrafica()!=null) {
        	key.setIdAnagrafica(a.getIdAnagrafica());
        }
        LkTipoRuolo ruolo = lookupDao.findTipoRuoloByCodRuolo(xml.getCodTipoRuolo());
        //se il ruolo non esiste nel file xml si inserisce ruolo 1 che significa richiedente
        if(ruolo!=null)
        	key.setIdTipoRuolo(ruolo.getIdTipoRuolo());
        else
        	key.setIdTipoRuolo(1);
        p.setPraticaAnagraficaPK(key);
        p.setLkTipoRuolo(ruolo);
        p.setAnagrafica(a);
        p.setFlgDittaIndividuale(axml.getFlgIndividuale());
        if (xml.getIdTipoQualifica() != null) {
            LkTipoQualifica qualifica = lookupDao.findTipoQualificaById(Utils.ib(xml.getIdTipoQualifica()));
            p.setIdTipoQualifica(qualifica);
        }
        return p;
    }
}
