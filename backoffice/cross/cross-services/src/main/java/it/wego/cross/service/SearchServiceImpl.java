/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.service;

import it.wego.cross.beans.AnagraficaRecapito;
import it.wego.cross.beans.AttoriComunicazione;
import it.wego.cross.constants.Constants;
import it.wego.cross.dao.AnagraficheDao;
import it.wego.cross.dao.EntiDao;
import it.wego.cross.dao.LookupDao;
import it.wego.cross.dao.UtentiDao;
import it.wego.cross.dto.CittadinanzaDTO;
import it.wego.cross.dto.ComuneDTO;
import it.wego.cross.dto.EventoDTO;
import it.wego.cross.dto.ProvinciaDTO;
import it.wego.cross.dto.UtenteMinifiedDTO;
import it.wego.cross.dto.search.DestinatariDTO;
import it.wego.cross.dto.search.FormaGiuridicaDTO;
import it.wego.cross.dto.search.TipoCollegioDTO;
import it.wego.cross.entity.Anagrafica;
import it.wego.cross.entity.AnagraficaRecapiti;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.LkCittadinanza;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.entity.LkFormeGiuridiche;
import it.wego.cross.entity.LkProvincie;
import it.wego.cross.entity.LkTipoCollegio;
import it.wego.cross.entity.LkTipoIndirizzo;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.Recapiti;
import it.wego.cross.entity.Utente;
import it.wego.cross.serializer.CittadinanzeSerializer;
import it.wego.cross.serializer.FormeGiuridicheSerializer;
import it.wego.cross.serializer.LuoghiSerializer;
import it.wego.cross.serializer.ProvincieSerializer;
import it.wego.cross.serializer.TipoCollegioSerializer;
import it.wego.cross.serializer.UtentiSerializer;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.Utils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

/**
 *
 * @author giuseppe
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private AnagraficheDao anagraficaDao;
    @Autowired
    private EntiDao entiDao;
    @Autowired
    private LookupDao lookupDao;
    @Autowired
    private UtentiDao utentiDao;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private AnagraficheService anagraficheService;

    @Override
    public List<DestinatariDTO> findDestinatari(Pratica pratica, String description, Boolean soloEnti) {
        AttoriComunicazione destinatari = new AttoriComunicazione();
        if (soloEnti) {
            List<Enti> enti = entiDao.findByDescription(description);
            if (enti != null && !enti.isEmpty()) {
                for (Enti ente : enti) {
                    destinatari.addEnte(ente.getIdEnte());
                }
            }
        } else {
            List<Anagrafica> anagrafiche = anagraficaDao.findByDescrizioneAndPratica(description, pratica);
            List<Enti> enti = entiDao.findByDescription(description);
            if (enti != null && !enti.isEmpty()) {
                for (Enti ente : enti) {
                    destinatari.addEnte(ente.getIdEnte());
                }
            }
            List<AnagraficaRecapiti> ar = getAnagraficheRecapiti(anagrafiche);
            if (ar != null && !ar.isEmpty()) {
                for (AnagraficaRecapiti a : ar) {
                    destinatari.addAnagrafica(a);
                }
            }
            if (pratica.getIdRecapito() != null) {
                destinatari.setIdRecapitoNotifica(pratica.getIdRecapito().getIdRecapito());
            }
        }
        List<DestinatariDTO> dest = serializeDestinatari(pratica, destinatari);
        return dest;
    }

    @Override
    public List<DestinatariDTO> serializeDestinatari(Pratica pratica, AttoriComunicazione destinatari) {
        List<Integer> anagrafiche = destinatari.getIdAnagraficheRecapiti();
        List<Integer> enti = destinatari.getIdEnti();
        List<DestinatariDTO> dest = new ArrayList<DestinatariDTO>();

        Log.APP.info(">> Ci sono " + anagrafiche.size() + " anagrafiche ");

        // ordina destinatari prima vengono i default e poi gli altri
        if (!anagrafiche.isEmpty()) {
            for (Integer idAnagraficaRecapito : anagrafiche) {
                AnagraficaRecapiti anagraficaRecapito = anagraficheService.findAnagraficaRecapitiById(idAnagraficaRecapito);
                if (anagraficaRecapito.getIdAnagrafica().isAnagraficaDaEnte()) {
                    trasformaDestinatarioAnagrafica(anagraficaRecapito, dest);
                }
            }
        }

        if (!enti.isEmpty()) {
            for (Integer idEnte : enti) {
                Enti ente = entiDao.findByIdEnte(idEnte);
                if (ente.isEnteDaEvento()) {
                    trasformaDestinatarioEnti(ente, dest);
                }
            }
        }

        if (destinatari.getIdRecapitoNotifica() != null) {
            Recapiti notifica = pratica.getIdRecapito();
            if (!Utils.e(notifica.getEmail()) || !Utils.e(notifica.getPec())) {
                DestinatariDTO d = new DestinatariDTO();
                String presso = Constants.ANAGRAFICA_NOTIFICA;
                if (notifica.getPresso() != null) {
                    presso = notifica.getPresso();
                }
                d.setDescription(presso);
                if (!Utils.e(notifica.getPec())) {
                    d.setEmail(notifica.getPec());
                } else {
                    d.setEmail(notifica.getEmail());
                }
                d.setId("N|" + String.valueOf(pratica.getIdRecapito().getIdRecapito()));
                d.setType(Constants.ANAGRAFICA_NOTIFICA);
                Log.APP.info(">> Aggiunto indirizzo di notifica pratica " + d.getEmail() + "");
                dest.add(d);
            }
        }
        //TODO: perché 2 volte?
        for (Integer idAnagraficaRecapito : anagrafiche) {
            AnagraficaRecapiti anagraficaRecapito = anagraficheService.findAnagraficaRecapitiById(idAnagraficaRecapito);
            if (!anagraficaRecapito.getIdAnagrafica().isAnagraficaDaEnte()) {
                trasformaDestinatarioAnagrafica(anagraficaRecapito, dest);
            }
        }
        for (Integer idEnte : enti) {
            Enti ente = entiDao.findByIdEnte(idEnte);
            if (!ente.isEnteDaEvento()) {
                trasformaDestinatarioEnti(ente, dest);
            }
        }
        Log.APP.info(">> Ci sono " + dest.size() + "destinatari totali.");
        Log.APP.info(">> fine.");
        return dest;
    }

    @Override
    public List<ProvinciaDTO> findProvincie(String descrizione, Boolean showOnlyProvinceInfocamere) {
        List<LkProvincie> provincie;
        if (showOnlyProvinceInfocamere) {
            provincie = lookupDao.findProvinceInfocamereByDescrizione(descrizione);
        } else {
            provincie = lookupDao.findProvinceByDescrizione(descrizione);
        }
        List<ProvinciaDTO> provincieDTO = new ArrayList<ProvinciaDTO>();
        for (LkProvincie p : provincie) {
            ProvinciaDTO provincia = ProvincieSerializer.serialize(p);
            provincieDTO.add(provincia);
        }
        return provincieDTO;
    }

    @Override
    public List<FormaGiuridicaDTO> findFormeGiuridiche(String descrizione) {
        List<LkFormeGiuridiche> formeGiuridicheList = lookupDao.findLkFormeGiuridicheByDesc(descrizione);
        List<FormaGiuridicaDTO> formeGiuridiche = new ArrayList<FormaGiuridicaDTO>();
        for (LkFormeGiuridiche forma : formeGiuridicheList) {
            FormaGiuridicaDTO fg = FormeGiuridicheSerializer.serialize(forma);
            formeGiuridiche.add(fg);
        }
        return formeGiuridiche;
    }

    @Override
    public List<CittadinanzaDTO> findCittadinanza(String description) {
        List<LkCittadinanza> lookupCittadinanza = lookupDao.findCittadinanzaByDescrizione(description);
        List<CittadinanzaDTO> cittadinanze = new ArrayList<CittadinanzaDTO>();
        for (LkCittadinanza l : lookupCittadinanza) {
            CittadinanzaDTO cittadinanza = CittadinanzeSerializer.serialize(l);
            cittadinanze.add(cittadinanza);
        }
        return cittadinanze;
    }

    @Override
    public List<TipoCollegioDTO> findTipoCollegio(String description) {
        List<LkTipoCollegio> lookupTipoCollegio = lookupDao.findLookupTipoCollegioByDescrizione(description);
        List<TipoCollegioDTO> tipoCollegio = new ArrayList<TipoCollegioDTO>();
        for (LkTipoCollegio l : lookupTipoCollegio) {
            TipoCollegioDTO t = TipoCollegioSerializer.serialize(l);
            tipoCollegio.add(t);
        }
        return tipoCollegio;
    }

    @Override
    public List<ComuneDTO> findComuni(String description, Date data) {
        List<LkComuni> lookupComuni = lookupDao.findComuniByDescrizione(description, data);
        List<ComuneDTO> comuni = new ArrayList<ComuneDTO>();
        for (LkComuni lookup : lookupComuni) {
            ComuneDTO comune = LuoghiSerializer.serialize(lookup);
            comuni.add(comune);
        }
        return comuni;
    }

    private List<AnagraficaRecapiti> getAnagraficheRecapiti(List<Anagrafica> anagrafiche) {
        List<AnagraficaRecapiti> recapiti = new ArrayList<AnagraficaRecapiti>();
        if (anagrafiche != null && !anagrafiche.isEmpty()) {
            for (Anagrafica a : anagrafiche) {
                if (a.getAnagraficaRecapitiList() != null && !a.getAnagraficaRecapitiList().isEmpty()) {
                    for (AnagraficaRecapiti ar : a.getAnagraficaRecapitiList()) {
                        if ("RES".equals(ar.getIdTipoIndirizzo().getCodTipoIndirizzo()) || "SED".equals(ar.getIdTipoIndirizzo().getCodTipoIndirizzo())) {
                            //Visualizzo solo indirizzo di residenza o sede
                            recapiti.add(ar);
                        }
                    }
                }
            }
        }
        return recapiti;
    }

    @Override
    public List<UtenteMinifiedDTO> findUtenti(String query) {
        List<Utente> utenti = utentiDao.findByUsernameNomeCognome(query);
        List<UtenteMinifiedDTO> dtos = new ArrayList<UtenteMinifiedDTO>();
        if (utenti != null && !utenti.isEmpty()) {
            for (Utente utente : utenti) {
                UtenteMinifiedDTO dto = UtentiSerializer.serializeUtenteMinified(utente);
                dtos.add(dto);
            }
        }
        return dtos;
    }

    @Override
    public List<DestinatariDTO> getDestinatari(Pratica pratica, String query, EventoDTO evento) {
        List<DestinatariDTO> destinatari = findDestinatari(pratica, query, evento.getDestinatariSoloEnti().equals("S"));
        return destinatari;
    }

    @Override
    public List<ProvinciaDTO> findProvincie(String query, boolean displayOnlyProvinceInfocamere) {
        List<ProvinciaDTO> provincie = findProvincie(query, displayOnlyProvinceInfocamere);
        return provincie;
    }

    @Override
    public List<CittadinanzaDTO> findCittadinanze(String query) {
        List<CittadinanzaDTO> cittadinanze = findCittadinanza(query);
        return cittadinanze;
    }

    private void trasformaDestinatarioAnagrafica(AnagraficaRecapiti a, List<DestinatariDTO> dest) {
        Log.APP.info(">> Serialize Anagrafica " + a.getIdAnagrafica().getCodiceFiscale() + " ");
        if (a.getIdRecapito() != null) {
            DestinatariDTO d = new DestinatariDTO();
            if (a.getIdAnagrafica().getTipoAnagrafica() == Constants.FLAG_ANAGRAFICA_AZIENDA) {
                d.setDescription(HtmlUtils.htmlEscape(a.getIdAnagrafica().getDenominazione()));
            } else {
                d.setDescription(HtmlUtils.htmlEscape(a.getIdAnagrafica().getNome() + " " + a.getIdAnagrafica().getCognome()));
            }
            Log.APP.info(">> descrizione " + d.getDescription() + " ");
            if (!Utils.e(a.getIdRecapito().getPec())) {
                d.setEmail(a.getIdRecapito().getPec());
            } else if (!Utils.e(a.getIdRecapito().getEmail())) {//^^CS MODIFICA
                d.setEmail(a.getIdRecapito().getEmail());
            } else {
                String msg = messageSource.getMessage("evento.posta.ordinaria", null, Locale.getDefault());
                d.setEmail(msg);
            }
            LkTipoIndirizzo tipoIndirizzo = anagraficaDao.findTipoIndirizzoByAnagraficaRecapito(a.getIdAnagrafica(), a.getIdRecapito());
            if (tipoIndirizzo != null) {
                Log.APP.info(">> Recapito id " + a.getIdRecapito().getIdRecapito() + " di tipo " + tipoIndirizzo.getDescrizione() + " email " + d.getEmail() + "");
                d.setAddressType(tipoIndirizzo.getDescrizione());
            }

            d.setId("A|" + String.valueOf(a.getIdRecapito().getIdRecapito()));
            d.setType(Constants.ANAGRAFICA_GENERICA);
            if (a.getIdAnagrafica().isAnagraficaDaEnte()) {
                d.setType(Constants.ANAGRAFICA_GENERICA_DEFAULT);
            }
            Log.APP.info(">>  Aggiunta anagrafica " + a.getIdAnagrafica().getCodiceFiscale());
            dest.add(d);
        } else {
            Log.APP.info(">> ATTENZIONE!!! nessun recapito trovato per anagrafica  " + a.getIdAnagrafica().getCodiceFiscale());
        }
    }

    private void trasformaDestinatarioEnti(Enti ente, List<DestinatariDTO> dest) {
        DestinatariDTO d = new DestinatariDTO();
        d.setDescription(HtmlUtils.htmlEscape(ente.getDescrizione()));
        d.setEmail(ente.getPec());
        d.setId("E|" + String.valueOf(ente.getIdEnte()));
        d.setType(Constants.ANAGRAFICA_ENTE);
        if (ente.isEnteDaEvento()) {
            d.setType(Constants.ANAGRAFICA_ENTE_DEFAULT);
        }
        Log.APP.info(">> Aggiunto ente  " + d.getDescription() + "");
        dest.add(d);
    }
}
