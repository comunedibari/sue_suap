package it.wego.cross.utils;

import com.google.common.base.Strings;
import it.wego.cross.constants.Constants;
import it.wego.cross.dao.LookupDao;
import it.wego.cross.dto.AnagraficaDTO;
import it.wego.cross.dto.ComuneDTO;
import it.wego.cross.dto.ProvinciaDTO;
import it.wego.cross.dto.RecapitoDTO;
import it.wego.cross.dto.StatoDTO;
import it.wego.cross.entity.AnagraficaRecapiti;
import it.wego.cross.entity.LkCittadinanza;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.entity.LkFormeGiuridiche;
import it.wego.cross.entity.LkNazionalita;
import it.wego.cross.entity.LkProvincie;
import it.wego.cross.entity.LkTipoCollegio;
import it.wego.cross.entity.LkTipoIndirizzo;
import it.wego.cross.entity.LkTipoRuolo;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticaAnagrafica;
import it.wego.cross.entity.Recapiti;
import it.wego.cross.service.LookupService;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import it.wego.cross.service.PluginService;

/**
 *
 * @author CS
 */
@Component
public class AnagraficaUtils {

    @Autowired
    private LookupDao lookupDao;
    @Autowired
    private RecapitoUtils recapitoUtils;

    public AnagraficaDTO getAnagraficaXML(it.wego.cross.xml.Pratica praticaXml, AnagraficaDTO anagraficaDTO, Pratica pratica) throws Exception {
        for (it.wego.cross.xml.Anagrafiche anaXML : praticaXml.getAnagrafiche()) {
            if (equals(anagraficaDTO, anaXML.getAnagrafica())) {
                copyXML2Anagrafica(anaXML, anagraficaDTO, pratica);
                break;
            }
        }
        return anagraficaDTO;
    }

    /**
     *
     * @author CS
     * @param anagraficaEntity
     * @param anagraficaDTO
     * @throws java.lang.Exception
     */
    public void copyEntity2Anagrafica(it.wego.cross.entity.Anagrafica anagraficaEntity, AnagraficaDTO anagraficaDTO) throws Exception {
        anagraficaDTO.setVarianteAnagrafica(anagraficaEntity.getVarianteAnagrafica());
        anagraficaDTO.setIdAnagrafica(anagraficaEntity.getIdAnagrafica());
        anagraficaDTO.setNome(anagraficaEntity.getNome());
        anagraficaDTO.setTipoAnagrafica(Utils.Char(anagraficaEntity.getTipoAnagrafica()));
        anagraficaDTO.setIdTipoPersona(Utils.Char(anagraficaEntity.getTipoAnagrafica()));
        anagraficaDTO.setCodiceFiscale(anagraficaEntity.getCodiceFiscale());
        anagraficaDTO.setDataNascita(anagraficaEntity.getDataNascita());
        anagraficaDTO.setLocalitaNascita(anagraficaEntity.getLocalitaNascita());
        anagraficaDTO.setSesso(Utils.Char(anagraficaEntity.getSesso()));
        anagraficaDTO.setCognome(anagraficaEntity.getCognome());
        if (anagraficaEntity.getIdCittadinanza() != null) {
            anagraficaDTO.setIdCittadinanza(anagraficaEntity.getIdCittadinanza().getIdCittadinanza());
            anagraficaDTO.setDesCittadinanza(anagraficaEntity.getIdCittadinanza().getDescrizione());
        }
        if (anagraficaEntity.getIdNazionalita() != null) {
            anagraficaDTO.setIdNazionalita(anagraficaEntity.getIdNazionalita().getIdNazionalita());
            anagraficaDTO.setDesNazionalita(anagraficaEntity.getIdNazionalita().getDescrizione());
        }
        if (anagraficaEntity.getIdComuneNascita() != null) {

            if (anagraficaEntity.getIdComuneNascita().getIdStato() != null
                    && anagraficaEntity.getIdComuneNascita().getIdProvincia() != null) {
                if (anagraficaDTO.getComuneNascita() == null) {
                    anagraficaDTO.setComuneNascita(new ComuneDTO());
                }
                RecapitoUtils.copyEntity2Comune(anagraficaEntity.getIdComuneNascita(), anagraficaDTO.getComuneNascita());
            }
        }

        anagraficaDTO.setDataIscrizione(anagraficaEntity.getDataIscrizione());
        anagraficaDTO.setNumeroIscrizione(anagraficaEntity.getNumeroIscrizione());
        if (Utils.e(anagraficaEntity.getPartitaIva())) {
            anagraficaDTO.setPartitaIva(null);
        } else {
            anagraficaDTO.setPartitaIva(anagraficaEntity.getPartitaIva());
        }

        if (anagraficaEntity.getIdTipoCollegio() != null) {
            anagraficaDTO.setIdTipoCollegio(anagraficaEntity.getIdTipoCollegio().getIdTipoCollegio());
            anagraficaDTO.setDesTipoCollegio(anagraficaEntity.getIdTipoCollegio().getDescrizione());
        }

        if (anagraficaEntity.getIdProvinciaIscrizione() != null) {
            ProvinciaDTO provincia = new ProvinciaDTO();
            provincia.setCodCatastale(anagraficaEntity.getIdProvinciaIscrizione().getCodCatastale());
            provincia.setDataFineValidita(anagraficaEntity.getIdProvinciaIscrizione().getDataFineValidita());
            provincia.setDescrizione(anagraficaEntity.getIdProvinciaIscrizione().getDescrizione());
            provincia.setFlgInfocamere(Utils.flag(anagraficaEntity.getIdProvinciaIscrizione().getFlgInfocamere()));
            provincia.setIdProvincie(anagraficaEntity.getIdProvinciaIscrizione().getIdProvincie());
            anagraficaDTO.setProvinciaIscrizione(provincia);
        }

        anagraficaDTO.setDataIscrizioneRea(anagraficaEntity.getDataIscrizioneRea());
        anagraficaDTO.setDataIscrizioneRi(anagraficaEntity.getDataIscrizioneRi());
        anagraficaDTO.setFlgAttesaIscrizioneRea(Utils.flag(anagraficaEntity.getFlgAttesaIscrizioneRea()));
        anagraficaDTO.setFlgAttesaIscrizioneRi(Utils.flag(anagraficaEntity.getFlgAttesaIscrizioneRi()));
//        anagraficaDTO.setFlgIndividuale(Utils.flag(anagraficaEntity.getFlgIndividuale()));
        anagraficaDTO.setFlgObbligoIscrizioneRi(Utils.flag(anagraficaEntity.getFlgObbligoIscrizioneRi()));
        anagraficaDTO.setnIscrizioneRea(anagraficaEntity.getNIscrizioneRea());
        anagraficaDTO.setnIscrizioneRi(anagraficaEntity.getNIscrizioneRi());
        anagraficaDTO.setDenominazione(anagraficaEntity.getDenominazione());
        if (anagraficaEntity.getIdFormaGiuridica() != null) {
            anagraficaDTO.setIdFormaGiuridica(anagraficaEntity.getIdFormaGiuridica().getIdFormeGiuridiche());
            anagraficaDTO.setDesFormaGiuridica(anagraficaEntity.getIdFormaGiuridica().getDescrizione());
        }
        if (anagraficaEntity.getIdProvinciaCciaa() != null) {
            anagraficaDTO.setProvinciaCciaa(serializeProvincia(anagraficaEntity.getIdProvinciaCciaa()));
        }

    }

    /**
     *
     * @author CS
     * @param anagraficaDTO
     * @param anagraficaEntity
     * @throws java.lang.Exception
     */
    public void copyAnagrafica2Entity(AnagraficaDTO anagraficaDTO, it.wego.cross.entity.Anagrafica anagraficaEntity) throws Exception {
        //Applico una politica conservativa: se ho un nuovo dato, aggiorno, altrimenti mantengo l'esistente. Se voglio eliminare un dato, devo passare da Rubrica
        if (!Strings.isNullOrEmpty(anagraficaDTO.getVarianteAnagrafica())) {
            anagraficaEntity.setVarianteAnagrafica(anagraficaDTO.getVarianteAnagrafica());
        }

        if (anagraficaDTO.getIdProvinciaIscrizione() != null) {
            Log.APP.info("getIdProvinciaIscrizione: " + anagraficaDTO.getIdProvinciaIscrizione());
            LkProvincie provincia = lookupDao.findLkProvinciaById(anagraficaDTO.getIdProvinciaIscrizione());
            if (provincia != null && provincia.getIdProvincie() != null) {
                anagraficaEntity.setIdProvinciaIscrizione(provincia);
            }

        }
        if (anagraficaDTO.getIdCittadinanza() != null) {
            Log.APP.info("getDesCittadinanza: " + anagraficaDTO.getDesCittadinanza());
            LkCittadinanza lkCittadinanza = lookupDao.findCittadinanzaByID(anagraficaDTO.getIdCittadinanza());
            if (lkCittadinanza != null && lkCittadinanza.getIdCittadinanza() != null) {
                anagraficaEntity.setIdCittadinanza(lkCittadinanza);
            }

        }
        if (anagraficaDTO.getIdNazionalita() != null) {
            Log.APP.info("getDesNazionalita: " + anagraficaDTO.getDesNazionalita());
            LkNazionalita lkNazionalita = lookupDao.findNazionalitaByID(anagraficaDTO.getIdNazionalita());
            if (lkNazionalita != null && lkNazionalita.getIdNazionalita() != null) {
                anagraficaEntity.setIdNazionalita(lkNazionalita);
            }

        }
        if (anagraficaDTO.getComuneNascita() != null && anagraficaDTO.getComuneNascita().getIdComune() != null) {
            Log.APP.info("comuneNascita: " + anagraficaDTO.getComuneNascita().getDescrizione());
            LkComuni comune = lookupDao.findComuneById(anagraficaDTO.getComuneNascita().getIdComune());
            if (comune != null && comune.getIdComune() != null) {
                anagraficaEntity.setIdComuneNascita(comune);
            }
        }
        if (anagraficaDTO.getIdFormaGiuridica() != null) {
            LkFormeGiuridiche fg = lookupDao.findLkFormeGiuridicheById(anagraficaDTO.getIdFormaGiuridica());
            if (fg != null && fg.getIdFormeGiuridiche() != null) {
                anagraficaEntity.setIdFormaGiuridica(fg);
            }
        }

        if (anagraficaDTO.getProvinciaCciaa() != null) {
            LkProvincie cciaa = lookupDao.findLkProvinciaById(anagraficaDTO.getProvinciaCciaa().getIdProvincie());
            if (cciaa != null && cciaa.getIdProvincie() != null) {
                anagraficaEntity.setIdProvinciaCciaa(cciaa);
            }
        }

        if (anagraficaDTO.getIdTipoCollegio() != null) {
            LkTipoCollegio tipocollegio = lookupDao.findLookupTipoCollegioById(anagraficaDTO.getIdTipoCollegio());
            if (tipocollegio != null && tipocollegio.getIdTipoCollegio() != null) {
                anagraficaEntity.setIdTipoCollegio(tipocollegio);
            }

        }
        anagraficaEntity.setCodiceFiscale(anagraficaDTO.getCodiceFiscale());
        anagraficaEntity.setTipoAnagrafica(Utils.Char(anagraficaDTO.getIdTipoPersona()));

        if (anagraficaDTO.getIdTipoPersona().equals(Constants.PERSONA_FISICA)) {
            anagraficaEntity.setNome(anagraficaDTO.getNome());
            anagraficaEntity.setCognome(anagraficaDTO.getCognome());
            anagraficaEntity.setDataNascita(anagraficaDTO.getDataNascita());
            anagraficaEntity.setLocalitaNascita(anagraficaDTO.getLocalitaNascita());
            anagraficaEntity.setSesso(Utils.Char(anagraficaDTO.getSesso()));
            anagraficaEntity.setCodiceFiscale(anagraficaDTO.getCodiceFiscale());
        }
        if (!Utils.e(anagraficaDTO.getDataIscrizione())) {
            anagraficaEntity.setDataIscrizione(anagraficaDTO.getDataIscrizione());
        }
        if (!Utils.e(anagraficaDTO.getDataNascita())) {
            anagraficaEntity.setDataNascita(anagraficaDTO.getDataNascita());
        }
        if (!Strings.isNullOrEmpty(anagraficaDTO.getNumeroIscrizione())) {
            anagraficaEntity.setNumeroIscrizione(anagraficaDTO.getNumeroIscrizione());
        }
        if (!Strings.isNullOrEmpty(anagraficaDTO.getDenominazione())) {
            anagraficaEntity.setDenominazione(anagraficaDTO.getDenominazione());
        }
        if (!Strings.isNullOrEmpty(anagraficaDTO.getPartitaIva())) {
            anagraficaEntity.setPartitaIva(anagraficaDTO.getPartitaIva());
        }
        anagraficaEntity.setFlgAttesaIscrizioneRea(!Strings.isNullOrEmpty(anagraficaDTO.getnIscrizioneRea()) ? Utils.flag("N") : Utils.flag("S"));
        if (anagraficaDTO.getDataIscrizioneRea() != null) {
            anagraficaEntity.setDataIscrizioneRea(anagraficaDTO.getDataIscrizioneRea());
        }
        if (!Strings.isNullOrEmpty(anagraficaDTO.getnIscrizioneRea())) {
            anagraficaEntity.setNIscrizioneRea(anagraficaDTO.getnIscrizioneRea());
        }
        if (!Strings.isNullOrEmpty(anagraficaDTO.getnIscrizioneRi())) {
            anagraficaEntity.setNIscrizioneRi(anagraficaDTO.getnIscrizioneRi());
        }
        anagraficaEntity.setFlgAttesaIscrizioneRi(!Strings.isNullOrEmpty(anagraficaDTO.getnIscrizioneRi()) ? Utils.flag("N") : Utils.flag("S"));
        if (anagraficaDTO.getDataIscrizioneRi() != null) {
            anagraficaEntity.setDataIscrizioneRi(anagraficaDTO.getDataIscrizioneRi());
        }
        //Non sapendo come calcolare il valore, viene sempre posto a N di default. Il dato non è solo informativo
        anagraficaEntity.setFlgObbligoIscrizioneRi('N');
    }

    /**
     *
     * @author CS
     * @param anagraficaXML
     * @param anagraficaDTO
     * @throws java.lang.Exception
     */
    public void copyXML2Anagrafica(it.wego.cross.xml.Anagrafiche anagraficaXML, AnagraficaDTO anagraficaDTO, Pratica pratica) throws Exception {
        if (anagraficaXML != null && anagraficaXML.getAnagrafica() != null) {
            anagraficaDTO.setDaRubrica(anagraficaXML.getAnagrafica().getDaRubrica());
            anagraficaDTO.setCodiceFiscale(anagraficaXML.getAnagrafica().getCodiceFiscale());
            anagraficaDTO.setVarianteAnagrafica(anagraficaXML.getAnagrafica().getVarianteAnagrafica());
            anagraficaDTO.setPartitaIva(anagraficaXML.getAnagrafica().getPartitaIva());
            if (anagraficaXML.getAnagrafica().getDataIscrizione() != null) {
                anagraficaDTO.setDataIscrizione(anagraficaXML.getAnagrafica().getDataIscrizione().toGregorianCalendar().getTime());
            }
            if (Utils.e(anagraficaXML.getAnagrafica().getConfermata())) {
                anagraficaDTO.setConfermata(null);
            } else {
                anagraficaDTO.setConfermata(anagraficaXML.getAnagrafica().getConfermata());
            }
            anagraficaDTO.setCounter(Utils.ib(anagraficaXML.getAnagrafica().getCounter()));
            anagraficaDTO.setIdAnagrafica(Utils.ib(anagraficaXML.getAnagrafica().getIdAnagrafica()));
            anagraficaDTO.setDesCittadinanza(anagraficaXML.getAnagrafica().getDesCittadinanza());
            anagraficaDTO.setIdCittadinanza(Utils.ib(anagraficaXML.getAnagrafica().getIdCittadinanza()));
            anagraficaDTO.setDesNazionalita(anagraficaXML.getAnagrafica().getDesNazionalita());
            anagraficaDTO.setIdNazionalita(Utils.ib(anagraficaXML.getAnagrafica().getIdNazionalita()));
            anagraficaDTO.setIdProvinciaIscrizione(Utils.ib(anagraficaXML.getAnagrafica().getIdProvinciaIscrizione()));

            if (anagraficaDTO.getComuneNascita() == null) {
                anagraficaDTO.setComuneNascita(new ComuneDTO());
            }
            anagraficaDTO.getComuneNascita().setIdComune(Utils.ib(anagraficaXML.getAnagrafica().getIdComuneNascita()));
            anagraficaDTO.getComuneNascita().setDescrizione(anagraficaXML.getAnagrafica().getDesComuneNascita());
            if (anagraficaDTO.getComuneNascita().getStato() == null) {
                anagraficaDTO.getComuneNascita().setStato(new StatoDTO());
            }
            anagraficaDTO.getComuneNascita().getStato().setIdStato(Utils.ib(anagraficaXML.getAnagrafica().getIdStatoNascita()));
            anagraficaDTO.getComuneNascita().getStato().setDescrizione(anagraficaXML.getAnagrafica().getDesStatoNascita());

            if (anagraficaDTO.getComuneNascita().getProvincia() == null) {
                anagraficaDTO.getComuneNascita().setProvincia(new ProvinciaDTO());
            }
            anagraficaDTO.getComuneNascita().getProvincia().setIdProvincie(Utils.ib(anagraficaXML.getAnagrafica().getIdProvinciaNascita()));
            anagraficaDTO.getComuneNascita().getProvincia().setDescrizione(anagraficaXML.getAnagrafica().getDesProvinciaNascita());

            if (anagraficaXML.getAnagrafica().getDataIscrizioneRea() != null) {
                anagraficaDTO.setDataIscrizioneRea(anagraficaXML.getAnagrafica().getDataIscrizioneRea().toGregorianCalendar().getTime());
            }

            anagraficaDTO.setIdTipoRuolo(Utils.ib(anagraficaXML.getIdTipoRuolo()));
            anagraficaDTO.setDesTipoRuolo(anagraficaXML.getDesTipoRuolo());
            anagraficaDTO.setCodTipoRuolo(anagraficaXML.getCodTipoRuolo());
            if (anagraficaXML.getAnagrafica().getDataIscrizioneRi() != null) {
                anagraficaDTO.setDataIscrizioneRi(anagraficaXML.getAnagrafica().getDataIscrizioneRi().toGregorianCalendar().getTime());
            }
            if (anagraficaXML.getAnagrafica().getDataNascita() != null) {
                anagraficaDTO.setDataNascita(anagraficaXML.getAnagrafica().getDataNascita().toGregorianCalendar().getTime());
            }

            anagraficaDTO.setFlgIndividuale(Utils.flagString(anagraficaXML.getAnagrafica().getFlgIndividuale()));
            Log.APP.info("Localita Nascita: " + anagraficaXML.getAnagrafica().getLocalitaNascita());
            anagraficaDTO.setLocalitaNascita(anagraficaXML.getAnagrafica().getLocalitaNascita());
            anagraficaDTO.setnIscrizioneRea(anagraficaXML.getAnagrafica().getNIscrizioneRea());
            anagraficaDTO.setnIscrizioneRi(anagraficaXML.getAnagrafica().getNIscrizioneRi());
            anagraficaDTO.setNome(anagraficaXML.getAnagrafica().getNome());
            anagraficaDTO.setNumeroIscrizione(anagraficaXML.getAnagrafica().getNumeroIscrizione());
            anagraficaDTO.setPartitaIva(anagraficaXML.getAnagrafica().getPartitaIva());
            anagraficaDTO.setSesso(anagraficaXML.getAnagrafica().getSesso());
            anagraficaDTO.setTipoAnagrafica(anagraficaXML.getAnagrafica().getTipoAnagrafica());
            anagraficaDTO.setDenominazione(anagraficaXML.getAnagrafica().getDenominazione());
            anagraficaDTO.setCognome(anagraficaXML.getAnagrafica().getCognome());

            anagraficaDTO.setFlgAttesaIscrizioneRea(Utils.flagString(anagraficaXML.getAnagrafica().isFlgAttesaIscrizioneRea()));
            anagraficaDTO.setFlgAttesaIscrizioneRi(Utils.flagString(anagraficaXML.getAnagrafica().isFlgAttesaIscrizioneRi()));

            if (anagraficaDTO.getProvinciaIscrizione() == null) {
                anagraficaDTO.setProvinciaIscrizione(new ProvinciaDTO());
            }
            anagraficaDTO.getProvinciaIscrizione().setDescrizione(anagraficaXML.getAnagrafica().getDesProvinciaIscrizione());
            anagraficaDTO.getProvinciaIscrizione().setCodCatastale(anagraficaXML.getAnagrafica().getCodProvinciaIscrizione());
            anagraficaDTO.getProvinciaIscrizione().setIdProvincie(Utils.ib(anagraficaXML.getAnagrafica().getIdProvinciaIscrizione()));
            anagraficaDTO.setNumeroIscrizione(anagraficaXML.getAnagrafica().getNumeroIscrizione());

            anagraficaDTO.setIdTipoCollegio(Utils.ib(anagraficaXML.getAnagrafica().getIdTipoCollegio()));
            anagraficaDTO.setDesTipoCollegio(anagraficaXML.getAnagrafica().getDesTipoCollegio());

            if (anagraficaDTO.getProvinciaCciaa() == null) {
                anagraficaDTO.setProvinciaCciaa(new ProvinciaDTO());
            }
            anagraficaDTO.getProvinciaCciaa().setIdProvincie(Utils.ib(anagraficaXML.getAnagrafica().getIdProvinciaCciaa()));
            anagraficaDTO.getProvinciaCciaa().setCodCatastale(anagraficaXML.getAnagrafica().getCodProvinciaCciaa());
            anagraficaDTO.getProvinciaCciaa().setDescrizione(anagraficaXML.getAnagrafica().getDesProvinciaCciaa());

            anagraficaDTO.setIdFormaGiuridica(Utils.ib(anagraficaXML.getAnagrafica().getIdFormaGiuridica()));
            anagraficaDTO.setDesFormaGiuridica(anagraficaXML.getAnagrafica().getDesFormaGiuridica());

            anagraficaDTO.setPartitaIva(anagraficaXML.getAnagrafica().getPartitaIva());

            //^^CS RECAPITI
            if (anagraficaXML.getAnagrafica().getRecapiti() != null
                    && anagraficaXML.getAnagrafica().getRecapiti().getRecapito() != null) {
                if (anagraficaDTO.getRecapiti() == null) {
                    anagraficaDTO.setRecapiti(new ArrayList<RecapitoDTO>());
                }
                for (it.wego.cross.xml.Recapito recapito : anagraficaXML.getAnagrafica().getRecapiti().getRecapito()) {
                    RecapitoDTO recapitoCS = new RecapitoDTO();
                    recapitoUtils.copyXML2Recapito(recapito, recapitoCS);
                    anagraficaDTO.getRecapiti().add(recapitoCS);
                }
                //^^CS NOTIFICA!!!!!
                if (anagraficaXML.getNotifica() != null) {
                    RecapitoDTO notifica = new RecapitoDTO();
                    recapitoUtils.copyXML2Recapito(anagraficaXML.getNotifica(), notifica);
                    notifica.setCounter(0);
                    anagraficaDTO.getRecapiti().add(notifica);
                }
            }
            //^^CS NOTIFICA
            if (anagraficaXML.getNotifica() != null) {
                if (anagraficaDTO.getNotifica() == null) {
                    anagraficaDTO.setNotifica(new RecapitoDTO());
                }
                recapitoUtils.copyXML2Recapito(anagraficaXML.getNotifica(), anagraficaDTO.getNotifica());
            }
        }
    }

    /**
     *
     * @author CS
     * @param anagraficaDTO
     * @param anagraficaXML
     * @return
     * @throws java.lang.Exception
     *
     */
    public it.wego.cross.xml.Anagrafiche copyAnagrafica2XML(AnagraficaDTO anagraficaDTO, it.wego.cross.xml.Anagrafiche anagraficaXML) throws Exception {

        GregorianCalendar c = new GregorianCalendar();
        XMLGregorianCalendar Xmldate;

        if (anagraficaDTO != null) {
            if (anagraficaXML.getAnagrafica() == null) {
                anagraficaXML.setAnagrafica(new it.wego.cross.xml.Anagrafica());
                anagraficaXML.getAnagrafica().setDaRubrica(anagraficaDTO.getDaRubrica());
            }
            if (anagraficaDTO.getCounter() != null) {
                anagraficaXML.getAnagrafica().setCounter(Utils.bi(anagraficaDTO.getCounter()));

            }
            anagraficaXML.getAnagrafica().setCodiceFiscale(anagraficaDTO.getCodiceFiscale());
            anagraficaXML.getAnagrafica().setVarianteAnagrafica(anagraficaDTO.getVarianteAnagrafica());
            if (Utils.e(anagraficaDTO.getConfermata())) {
                anagraficaXML.getAnagrafica().setConfermata(null);
            } else {
                anagraficaXML.getAnagrafica().setConfermata(anagraficaDTO.getConfermata());
            }
            //Non è mai vuoto
            if (!Strings.isNullOrEmpty(anagraficaDTO.getIdTipoPersona())) {
                anagraficaXML.getAnagrafica().setTipoAnagrafica(anagraficaDTO.getIdTipoPersona());
                LkTipoRuolo tipoRuolo = lookupDao.findTipoRuoloById(anagraficaDTO.getIdTipoRuolo());
                anagraficaXML.setIdTipoRuolo(Utils.bi(anagraficaDTO.getIdTipoRuolo()));
                anagraficaXML.setCodTipoRuolo(tipoRuolo.getCodRuolo());
                anagraficaXML.setDesTipoRuolo(tipoRuolo.getDescrizione());
                anagraficaXML.getAnagrafica().setIdAnagrafica(Utils.bi(anagraficaDTO.getIdAnagrafica()));
                //if (anagraficaDTO.getTipoAnagrafica().equals(Constants.PERSONA_FISICA)) 
                //{
                if (anagraficaDTO.getDataNascita() != null) {
                    c.setTime(anagraficaDTO.getDataNascita());
                    Xmldate = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
                    anagraficaXML.getAnagrafica().setDataNascita(Xmldate);
                }
                anagraficaXML.getAnagrafica().setLocalitaNascita(anagraficaDTO.getLocalitaNascita());
                anagraficaXML.getAnagrafica().setNome(anagraficaDTO.getNome());
                anagraficaXML.getAnagrafica().setSesso(anagraficaDTO.getSesso());
                anagraficaXML.getAnagrafica().setCognome(anagraficaDTO.getCognome());
                anagraficaXML.getAnagrafica().setDesCittadinanza(anagraficaDTO.getDesCittadinanza());
                anagraficaXML.getAnagrafica().setIdCittadinanza(Utils.bi(anagraficaDTO.getIdCittadinanza()));
                anagraficaXML.getAnagrafica().setDesNazionalita(anagraficaDTO.getDesNazionalita());
                anagraficaXML.getAnagrafica().setIdNazionalita(Utils.bi(anagraficaDTO.getIdNazionalita()));
                if (anagraficaDTO.getComuneNascita() != null) {
                    anagraficaXML.getAnagrafica().setIdComuneNascita(Utils.bi(anagraficaDTO.getComuneNascita().getIdComune()));
                    anagraficaXML.getAnagrafica().setDesComuneNascita(anagraficaDTO.getComuneNascita().getDescrizione());
                    anagraficaXML.getAnagrafica().setIdStatoNascita(Utils.bi(anagraficaDTO.getComuneNascita().getStato().getIdStato()));
                    anagraficaXML.getAnagrafica().setDesStatoNascita(anagraficaDTO.getComuneNascita().getStato().getDescrizione());
                    anagraficaXML.getAnagrafica().setIdProvinciaNascita(Utils.bi(anagraficaDTO.getComuneNascita().getProvincia().getIdProvincie()));
                    anagraficaXML.getAnagrafica().setDesProvinciaNascita(anagraficaDTO.getComuneNascita().getProvincia().getDescrizione());
                }

                anagraficaXML.getAnagrafica().setPartitaIva(anagraficaDTO.getPartitaIva());
                anagraficaXML.getAnagrafica().setNumeroIscrizione(anagraficaDTO.getNumeroIscrizione());
                if (anagraficaDTO.getProvinciaIscrizione() != null) {
                    anagraficaXML.getAnagrafica().setDesProvinciaIscrizione(anagraficaDTO.getProvinciaIscrizione().getDescrizione());
                    anagraficaXML.getAnagrafica().setIdProvinciaIscrizione(Utils.bi(anagraficaDTO.getProvinciaIscrizione().getIdProvincie()));
                }
                if (anagraficaDTO.getDataIscrizione() != null) {
                    c.setTime(anagraficaDTO.getDataIscrizione());
                    Xmldate = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
                    anagraficaXML.getAnagrafica().setDataIscrizione(Xmldate);
                }
                anagraficaXML.getAnagrafica().setDenominazione(anagraficaDTO.getDenominazione());
                if (anagraficaDTO.getDataIscrizioneRea() != null) {
                    c.setTime(anagraficaDTO.getDataIscrizioneRea());
                    Xmldate = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
                    anagraficaXML.getAnagrafica().setDataIscrizioneRea(Xmldate);
                }
                if (anagraficaDTO.getDataIscrizioneRi() != null) {
                    c.setTime(anagraficaDTO.getDataIscrizioneRi());
                    Xmldate = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
                    anagraficaXML.getAnagrafica().setDataIscrizioneRi(Xmldate);
                }
                if (anagraficaDTO.getProvinciaCciaa() != null) {
                    anagraficaXML.getAnagrafica().setIdProvinciaCciaa(Utils.bi(anagraficaDTO.getProvinciaCciaa().getIdProvincie()));
                    anagraficaXML.getAnagrafica().setDesProvinciaCciaa(anagraficaDTO.getProvinciaCciaa().getDescrizione());
                }
                anagraficaXML.getAnagrafica().setNIscrizioneRi(anagraficaDTO.getnIscrizioneRi());
                anagraficaXML.getAnagrafica().setNIscrizioneRea(anagraficaDTO.getnIscrizioneRea());
//                anagraficaXML.getAnagrafica().setNIscrizioneRi(anagraficaDTO.getnIscrizioneRi());
                anagraficaXML.getAnagrafica().setPartitaIva(anagraficaDTO.getPartitaIva());
                anagraficaXML.getAnagrafica().setFlgAttesaIscrizioneRea(Utils.flagB(anagraficaDTO.getFlgAttesaIscrizioneRea()));
                anagraficaXML.getAnagrafica().setFlgAttesaIscrizioneRi(Utils.flagB(anagraficaDTO.getFlgAttesaIscrizioneRi()));
                anagraficaXML.getAnagrafica().setFlgObbligoIscrizioneRi(Utils.flagB(anagraficaDTO.getFlgObbligoIscrizioneRi()));
                anagraficaXML.getAnagrafica().setFlgIndividuale(Utils.flagString(anagraficaDTO.getFlgIndividuale()));
                if (anagraficaDTO.getIdTipoCollegio() != null) {
                    anagraficaXML.getAnagrafica().setIdTipoCollegio(Utils.bi(anagraficaDTO.getIdTipoCollegio()));
                }
                if (anagraficaDTO.getDesTipoCollegio() != null) {
                    anagraficaXML.getAnagrafica().setDesTipoCollegio(anagraficaDTO.getDesTipoCollegio());
                }

                if (anagraficaDTO.getIdFormaGiuridica() != null) {
                    anagraficaXML.getAnagrafica().setIdFormaGiuridica(Utils.bi(anagraficaDTO.getIdFormaGiuridica()));

                }
                if (anagraficaDTO.getDesFormaGiuridica() != null) {
                    anagraficaXML.getAnagrafica().setDesFormaGiuridica(anagraficaDTO.getDesFormaGiuridica());
                }

                if (anagraficaDTO.getRecapiti() != null) {
//                    if (anagraficaXML.getAnagrafica().getRecapiti() == null) {
                    //Re-inserisco tutti i recapiti
                    anagraficaXML.getAnagrafica().setRecapiti(new it.wego.cross.xml.Recapiti());
//                    }

                    for (RecapitoDTO recapito : anagraficaDTO.getRecapiti()) {
//                        boolean found = false;
//                        LkTipoIndirizzo tipoIndirizzo = lookupDao.findTipoIndirizzoById(recapito.getIdTipoIndirizzo());
//                        for (it.wego.cross.xml.Recapito recapitoXML : anagraficaXML.getAnagrafica().getRecapiti().getRecapito()) {
//
////                            if ((tipoIndirizzo.getDescrizione().equals(recapitoXML.getDesTipoIndirizzo()))
////                                    && (tipoIndirizzo.getDescrizione().equals(Constants.INDIRIZZO_RESIDENZA)
////                                    || tipoIndirizzo.getDescrizione().equals(Constants.INDIRIZZO_SEDE))) {
////                                //L'indirizzo di residenza e di sede sono unici
////                                RecapitoUtils.copyRecapito2XML(recapito, recapitoXML);
////                                found = true;
////                                break;
////                            } else if (RecapitoUtils.equals(recapito, recapitoXML)) {
////                                RecapitoUtils.copyRecapito2XML(recapito, recapitoXML);
////                                found = true;
////                                break;
////                            }
//                            if (recapito.getDescTipoIndirizzo() != null && recapito.getDescTipoIndirizzo().equals(Constants.INDIRIZZO_NOTIFICA)) {
//                                RecapitoUtils.copyRecapito2XML(recapito, anagraficaXML.getNotifica());
//                            } else {
//                                it.wego.cross.xml.Recapito recapitoXML = new it.wego.cross.xml.Recapito();
//                                RecapitoUtils.copyRecapito2XML(recapito, recapitoXML);
//                                anagraficaXML.getAnagrafica().getRecapiti().getRecapito().add(recapitoXML);
//                            }
//                        }
//                        if (!found) {
                        LkTipoIndirizzo tipoIndirizzo = lookupDao.findTipoIndirizzoById(recapito.getIdTipoIndirizzo());
                        if (recapito.getDescTipoIndirizzo() != null && recapito.getDescTipoIndirizzo().equals(Constants.INDIRIZZO_NOTIFICA)) {
                            RecapitoUtils.copyRecapito2XML(recapito, anagraficaXML.getNotifica(), tipoIndirizzo);
                        } else {
                            it.wego.cross.xml.Recapito recapitoXML = new it.wego.cross.xml.Recapito();
                            RecapitoUtils.copyRecapito2XML(recapito, recapitoXML, tipoIndirizzo);
                            anagraficaXML.getAnagrafica().getRecapiti().getRecapito().add(recapitoXML);
                        }
//                        }
                    }
                }
            }
        }

        return anagraficaXML;
    }

    /**
     * Confronta tra anagrafica xml e anagraficaDTO
     *
     * @author CS
     * @param anagraficaDTO
     * @param anagraficaXML
     * @return
     */
    public Boolean equals(AnagraficaDTO anagraficaDTO, it.wego.cross.xml.Anagrafica anagraficaXML) {
        if (anagraficaDTO == null || anagraficaXML == null) {
            return false;
        }
        if (anagraficaDTO.getCounter() != null && anagraficaXML.getCounter() != null) {
            return anagraficaDTO.getCounter().equals(Utils.ib(anagraficaXML.getCounter()));
        }
        if ((anagraficaDTO.getCounter() != null && anagraficaXML.getCounter() == null)
                || (anagraficaDTO.getCounter() == null && anagraficaXML.getCounter() != null)) {
            return false;
        }
        if (!Strings.isNullOrEmpty(anagraficaDTO.getTipoAnagrafica())
                && !Strings.isNullOrEmpty(anagraficaXML.getTipoAnagrafica())
                && anagraficaDTO.getTipoAnagrafica().equals(anagraficaXML.getTipoAnagrafica())) {
            if (getTipoAnagrafica(anagraficaDTO) != null && getTipoAnagrafica(anagraficaDTO).equals(Constants.PERSONA_FISICA)
                    && anagraficaDTO.getCodiceFiscale() != null && anagraficaDTO.getCodiceFiscale().equals(anagraficaXML.getCodiceFiscale())) {

                return true;
            }
            if (getTipoAnagrafica(anagraficaDTO) != null && getTipoAnagrafica(anagraficaDTO).equals(Constants.PERSONA_GIURIDICA)
                    && //                    anagraficaDTO.getDenominazione()!=null && anagraficaDTO.getDenominazione().equals(anagraficaXML.getDenominazione()) &&
                    anagraficaDTO.getPartitaIva() != null && anagraficaDTO.getPartitaIva().equals(anagraficaXML.getPartitaIva())) {
                return true;
            }

        }
        if (anagraficaDTO.getIdAnagrafica() == null
                && !Strings.isNullOrEmpty(anagraficaDTO.getCodiceFiscale()) && !Strings.isNullOrEmpty(anagraficaXML.getCodiceFiscale())) {
            //Faccio un ultimo tentativo con il codice fiscale. Mi aspetto di entrare in questa sezione solo nel caso in cui le anagrafiche arrivano da protocollo:
            //non sono quindi presenti in banca dati ma solo nell'XML
            return anagraficaDTO.getCodiceFiscale().equalsIgnoreCase(anagraficaXML.getCodiceFiscale());
        }
        return false;
    }

    /**
     * anagrafica2.set(anagrafica1)
     *
     * @param anagrafica2
     * @param anagrafica1
     * @author
     */
    public void merge(AnagraficaDTO anagrafica1, AnagraficaDTO anagrafica2) {
        if (Utils.e(anagrafica1.getDesCittadinanza())) {
            anagrafica2.setDesCittadinanza(anagrafica1.getDesCittadinanza());
        }
        if (Utils.e(anagrafica1.getDesFormaGiuridica())) {
            anagrafica2.setDesFormaGiuridica(anagrafica1.getDesFormaGiuridica());
        }
        if (Utils.e(anagrafica1.getDesTipoCollegio())) {
            anagrafica2.setDesTipoCollegio(anagrafica1.getDesTipoCollegio());
        }
        if (Utils.e(anagrafica1.getDesTipoRuolo())) {
            anagrafica2.setDesTipoRuolo(anagrafica1.getDesTipoRuolo());
        }
        if (Utils.e(anagrafica1.getCodTipoRuolo())) {
            anagrafica2.setCodTipoRuolo(anagrafica1.getCodTipoRuolo());
        }
        if (Utils.e(anagrafica1.getDescrizioneTitolarita())) {
            anagrafica2.setDescrizioneTitolarita(anagrafica1.getDescrizioneTitolarita());
        }
        if (Utils.e(anagrafica1.getFlgAttesaIscrizioneRea())) {
            anagrafica2.setFlgAttesaIscrizioneRea(anagrafica1.getFlgAttesaIscrizioneRea());
        }
        if (Utils.e(anagrafica1.getFlgAttesaIscrizioneRi())) {
            anagrafica2.setFlgAttesaIscrizioneRi(anagrafica1.getFlgAttesaIscrizioneRi());
        }
        if (Utils.e(anagrafica1.getFlgIndividuale())) {
            anagrafica2.setFlgIndividuale(anagrafica1.getFlgIndividuale());
        }
        if (Utils.e(anagrafica1.getFlgObbligoIscrizioneRi())) {
            anagrafica2.setFlgObbligoIscrizioneRi(anagrafica1.getFlgObbligoIscrizioneRi());
        }
        if (Utils.e(anagrafica1.getIdAnagrafica())) {
            anagrafica2.setIdAnagrafica(anagrafica1.getIdAnagrafica());
        }
        if (Utils.e(anagrafica1.getIdCittadinanza())) {
            anagrafica2.setIdCittadinanza(anagrafica1.getIdCittadinanza());
        }
        if (Utils.e(anagrafica1.getIdFormaGiuridica())) {
            anagrafica2.setIdFormaGiuridica(anagrafica1.getIdFormaGiuridica());
        }
        if (Utils.e(anagrafica1.getIdPratica())) {
            anagrafica2.setIdPratica(anagrafica1.getIdPratica());
        }
        if (Utils.e(anagrafica1.getIdProvinciaIscrizione())) {
            anagrafica2.setIdProvinciaIscrizione(anagrafica1.getIdProvinciaIscrizione());
        }
        if (Utils.e(anagrafica1.getIdTipoCollegio())) {
            anagrafica2.setIdTipoCollegio(anagrafica1.getIdTipoCollegio());
        }
        if (Utils.e(anagrafica1.getIdTipoRuolo())) {
            anagrafica2.setIdTipoRuolo(anagrafica1.getIdTipoRuolo());
        }
        if (Utils.e(anagrafica1.getLocalitaNascita())) {
            anagrafica2.setLocalitaNascita(anagrafica1.getLocalitaNascita());
        }
        if (Utils.e(anagrafica1.getnIscrizioneRea())) {
            anagrafica2.setnIscrizioneRea(anagrafica1.getnIscrizioneRea());
        }
        if (Utils.e(anagrafica1.getnIscrizioneRi())) {
            anagrafica2.setnIscrizioneRi(anagrafica1.getnIscrizioneRi());
        }
        if (Utils.e(anagrafica1.getNome())) {
            anagrafica2.setNome(anagrafica1.getNome());
        }
        if (Utils.e(anagrafica1.getNumeroIscrizione())) {
            anagrafica2.setNumeroIscrizione(anagrafica1.getNumeroIscrizione());
        }
        if (Utils.e(anagrafica1.getPartitaIva())) {
            anagrafica2.setPartitaIva(anagrafica1.getPartitaIva());
        }
        if (Utils.e(anagrafica1.getSesso())) {
            anagrafica2.setSesso(anagrafica1.getSesso());
        }
        if (Utils.e(anagrafica1.getConfermata())) {
            anagrafica2.setConfermata(anagrafica1.getConfermata());
        }
        if (Utils.e(anagrafica1.getIdTipoQualifica())) {
            anagrafica2.setIdTipoQualifica(anagrafica1.getIdTipoQualifica());
        }
        if (Utils.e(anagrafica1.getDesTipoQualifica())) {
            anagrafica2.setDesTipoQualifica(anagrafica1.getDesTipoQualifica());
        }
        if (Utils.e(anagrafica1.getVarianteAnagrafica())) {
            anagrafica2.setVarianteAnagrafica(anagrafica1.getVarianteAnagrafica());
        }
    }

    public AnagraficaDTO cleanPartitaIvaCodiceFiscale(AnagraficaDTO anagrafica) {

        if (anagrafica.getPartitaIva() != null) {
            if (anagrafica.getPartitaIva().contains(",")) {
                String[] piva = anagrafica.getPartitaIva().split(",");
                if (piva.length > 0) {
                    for (String piva1 : piva) {
                        if (piva1 != null && !"".equals(piva1)) {
                            anagrafica.setPartitaIva(piva1);
                            break;
                        }
                    }
                }
            } else {
                anagrafica.setPartitaIva(anagrafica.getPartitaIva());
            }
        }
        if (anagrafica.getCodiceFiscale() != null) {
            if (anagrafica.getCodiceFiscale().contains(",")) {
                String[] codiceFiscale = anagrafica.getCodiceFiscale().split(",");
                if (codiceFiscale.length > 0) {
                    for (String codiceFiscale1 : codiceFiscale) {
                        if (codiceFiscale1 != null && !"".equals(codiceFiscale1)) {
                            anagrafica.setCodiceFiscale(codiceFiscale1);
                            break;
                        }
                    }
                }
            } else {
                anagrafica.setCodiceFiscale(anagrafica.getCodiceFiscale());
            }
        }
        return anagrafica;
    }

    public String getVarianteAnagrafica(AnagraficaDTO anagrafica) {
        String tipo = anagrafica.getTipoAnagrafica();
        if (Constants.PERSONA_FISICA.equalsIgnoreCase(tipo)) {
            tipo = Constants.PERSONA_FISICA;
            if (anagrafica.getVarianteAnagrafica() != null) {
//                if (anagrafica.getVarianteAnagrafica() != null && anagrafica.getVarianteAnagrafica().equals(Constants.PERSONA_PROFESSIONISTA)) {
//                    tipo = Constants.PERSONA_PROFESSIONISTA;
//                }
                if (anagrafica.getVarianteAnagrafica() != null && anagrafica.getVarianteAnagrafica().equals(Constants.PERSONA_DITTAINDIVIDUALE)) {
                    tipo = Constants.PERSONA_DITTAINDIVIDUALE;
                }
            }
        }
        return tipo;
    }

    public String getTipoAnagrafica(AnagraficaDTO anagrafica) {
        return anagrafica.getTipoAnagrafica();
    }

    public String getTipoAnagrafica(it.wego.cross.entity.Anagrafica anagrafica) {
        String tipo = Utils.Char(anagrafica.getTipoAnagrafica());
        if (Constants.PERSONA_FISICA.equals(String.valueOf(anagrafica.getTipoAnagrafica()))) {
            tipo = Constants.PERSONA_FISICA;
        }
        //^^CS da cambiare in futuro
        anagrafica.setVarianteAnagrafica(tipo);
        if (Constants.PERSONA_DITTAINDIVIDUALE.equals(tipo)) {
            anagrafica.setTipoAnagrafica(Constants.PERSONA_FISICA.charAt(0));
            anagrafica.setVarianteAnagrafica(Constants.PERSONA_DITTAINDIVIDUALE);
        }

        return tipo;
    }

    /**
     * ^^CS AGGIUNTA pulizia anagrafica, in quanto dalla pagina html arrivano
     * alcuni campi ,,,,
     *
     * @param anagrafica
     */
    public void ripulisci(AnagraficaDTO anagrafica) {
        anagrafica.setCodiceFiscale(ripulisci(anagrafica.getCodiceFiscale()));
        anagrafica.setPartitaIva(ripulisci(anagrafica.getPartitaIva()));
        anagrafica.setFlgAttesaIscrizioneRea(ripulisci(anagrafica.getFlgAttesaIscrizioneRea()));
        anagrafica.setFlgAttesaIscrizioneRi(ripulisci(anagrafica.getFlgAttesaIscrizioneRi()));
        anagrafica.setFlgIndividuale(ripulisci(anagrafica.getFlgIndividuale()));
        anagrafica.setFlgObbligoIscrizioneRi(ripulisci(anagrafica.getFlgObbligoIscrizioneRi()));
        anagrafica.setNumeroIscrizione(ripulisci(anagrafica.getNumeroIscrizione()));
        anagrafica.setnIscrizioneRea(ripulisci(anagrafica.getnIscrizioneRea()));
        anagrafica.setnIscrizioneRi(ripulisci(anagrafica.getnIscrizioneRi()));
        if (anagrafica.getProvinciaCciaa() != null) {
            anagrafica.getProvinciaCciaa().setCodCatastale(ripulisci(anagrafica.getProvinciaCciaa().getCodCatastale()));
            anagrafica.getProvinciaCciaa().setDescrizione(ripulisci(anagrafica.getProvinciaCciaa().getDescrizione()));
            anagrafica.getProvinciaCciaa().setFlgInfocamere(ripulisci(anagrafica.getProvinciaCciaa().getFlgInfocamere()));
        }
        anagrafica.setDenominazione(ripulisci(anagrafica.getDenominazione()));
    }

    private String ripulisci(String stringa) {
        if (stringa != null) {
            String[] stringaA = stringa.split(",");
            stringa = null;
            if (stringaA.length > 0) {
                for (String stringaA1 : stringaA) {
                    if (!Utils.e(stringaA1.trim())) {
                        stringa = stringaA1;
                        break;
                    }
                }
            }
        }
        return stringa;
    }
    //^^CS AGGIUNTA restituisce il recapito che ha una email e che segue l'ordine dei recapiti

    public it.wego.cross.entity.Recapiti cercaIlRecapito(it.wego.cross.entity.Anagrafica a, it.wego.cross.entity.Pratica pratica, Boolean conEmail) {
        Recapiti recapito = null;
        //^^CS cerca se ci sono recapiti di notifica per la pratica
        Log.APP.info(">> cerco recapito per priorita ");
        if (a.getPraticaAnagraficaList() != null) {
            for (PraticaAnagrafica pa : a.getPraticaAnagraficaList()) {
                if (pa.getIdRecapitoNotifica() != null && pa.getPratica().getIdPratica() == pratica.getIdPratica()) {
                    recapito = pa.getIdRecapitoNotifica();
                }
            }
        }
        if (recapito == null) {
            //^^CS se non ce un recapito di notifica cerca i recapiti per ordine di priorita
            for (String tipo : Constants.ORDINE_RECAPITI) {
                for (AnagraficaRecapiti anagraficaRecapito : a.getAnagraficaRecapitiList()) {
                    if (anagraficaRecapito.getIdTipoIndirizzo().getDescrizione().equals(tipo)) {
                        recapito = anagraficaRecapito.getIdRecapito();
                        //^^CS MODIFICA setta la pec , se non ce l'email, altrimenti non aggiungere
                        if (Utils.e(recapito.getPec()) && Utils.e(recapito.getEmail()) && conEmail) {
                            continue;
                        }
                        break;
                    }
                }
            }
        }
        return recapito;
    }

    public ProvinciaDTO serializeProvincia(LkProvincie provincia) {
        ProvinciaDTO p = new ProvinciaDTO();
        p.setCodCatastale(provincia.getCodCatastale());
        p.setDataFineValidita(provincia.getDataFineValidita());
        p.setDescrizione(provincia.getDescrizione());
        p.setFlgInfocamere(String.valueOf(provincia.getFlgInfocamere()));
        p.setIdProvincie(provincia.getIdProvincie());
        return p;
    }
}
