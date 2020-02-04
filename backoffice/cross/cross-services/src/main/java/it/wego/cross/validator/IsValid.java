package it.wego.cross.validator;

import com.google.common.base.Strings;
import it.wego.cross.utils.Utils;
import it.wego.cross.validator.impl.PartitaIvaValidatorImpl;
import it.wego.cross.dto.UtenteDTO;
import it.wego.cross.dto.RecapitoDTO;
import it.wego.cross.dto.PraticaDTO;
import it.wego.cross.dto.EnteDTO;
import it.wego.cross.dto.ProtocolloDTO;
import it.wego.cross.dto.AllegatoDTO;
import it.wego.cross.dto.AnagraficaDTO;
import it.wego.cross.dto.ProcedimentoDTO;
import it.wego.cross.utils.AnagraficaUtils;
import it.wego.cross.constants.Constants;
import it.wego.cross.entity.LkTipoIndirizzo;
import it.wego.cross.service.LookupService;
import java.text.SimpleDateFormat;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

/**
 *
 * @author CS
 */
@Component
public class IsValid {

    @Autowired
    private MessageSource messageSource;
    @Autowired
    private AnagraficaUtils anagraficaUtils;
    @Autowired
    private LookupService lookupService;

    public List<String> AnagraficaRecapiti(Validator validator, AnagraficaDTO anagrafica) {
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(anagrafica, "anagrafica");
        validator.validate(anagrafica, result);
        List<String> errors = this.Anagrafica(result, anagrafica);

        for (RecapitoDTO recapito : anagrafica.getRecapiti()) {
            BeanPropertyBindingResult br = new BeanPropertyBindingResult(recapito, "recapito");
            validator.validate(recapito, br);
            List<String> validatorErrors = this.recapito(br, recapito);
            if (validatorErrors != null) {
                errors.addAll(validatorErrors);
            }
        }
        return errors;
    }

    public List<String> notifica(Validator validator, RecapitoDTO notifica) {
        BeanPropertyBindingResult br = new BeanPropertyBindingResult(notifica, "recapito");
        validator.validate(notifica, br);
        List<String> errors = this.recapito(br, notifica);
        return errors;
    }

    public List<String> Anagrafica(BindingResult result, AnagraficaDTO anagrafica) {
        List<String> errors = new ArrayList();
        if (anagrafica.getIdTipoPersona() != null
                && (anagrafica.getIdTipoPersona().equals(Constants.PERSONA_FISICA)
                || anagrafica.getIdTipoPersona().equals(Constants.PERSONA_GIURIDICA))) {
            //Valida informazioni per persona fisica
            if (anagrafica.getIdTipoPersona().equals(Constants.PERSONA_FISICA)) {
                List<String> errori = datiPersonaFisica(result, anagrafica);
                if (errori != null) {
                    errors.addAll(errori);
                }
                if (!Strings.isNullOrEmpty(anagrafica.getFlgIndividuale()) && anagrafica.getFlgIndividuale().equalsIgnoreCase("S")) {
                    List<String> erroriDittaIndividuale = datiPersonaDittaIndividuale(result, anagrafica);
                    if (errori != null) {
                        errors.addAll(erroriDittaIndividuale);
                    }
                }
            }

            if (anagrafica.getIdTipoPersona().equals(Constants.PERSONA_GIURIDICA)) {
                List<String> errori = datiPersonaGiuridica(result, anagrafica);
                if (errori != null) {
                    errors.addAll(errori);
                }
            }
        } else {
            String msgError = messageSource.getMessage("error.tipoAnagrafica", null, Locale.getDefault());
            errors.add(msgError);
        }
//        if (errors.size() > 0) {
        return errors;
//        }
//        return null;
    }

    public List<String> datiPersonaFisica(BindingResult result, AnagraficaDTO anagrafica) {
        List<String> errors = new ArrayList();
        if (Strings.isNullOrEmpty(anagrafica.getNome())) {
            errors.add(messageSource.getMessage("error.nomeEmpty", null, Locale.getDefault()));
        }
        if (Strings.isNullOrEmpty(anagrafica.getCognome())) {
            errors.add(messageSource.getMessage("error.cognomeEmpty", null, Locale.getDefault()));
        }
        if (result.hasFieldErrors("codiceFiscale")) {
            errors.add(result.getFieldErrors("codiceFiscale").get(0).getDefaultMessage());
        }
        if (!anagrafica.getManuale()) {
            //TODO: è sempre obbligatorio?
            if (anagrafica.getComuneNascita() == null || anagrafica.getComuneNascita().getIdComune() == null) {
                errors.add(messageSource.getMessage("error.comuneNascitaEmpty", null, Locale.getDefault()));
            }
            if (anagrafica.getIdCittadinanza() == null) {
                errors.add(messageSource.getMessage("error.desCittadinanza", null, Locale.getDefault()));
            }
        }
        if (Strings.isNullOrEmpty(anagrafica.getSesso())) {
            errors.add(messageSource.getMessage("error.desCittadinanza", null, Locale.getDefault()));
        }
        //Tolgo il vincolo di obbligatorietà della località in caso di comune estero di nascita
//        if (anagrafica.getComuneNascita() != null
//                && anagrafica.getComuneNascita().getProvincia() != null
//                && anagrafica.getComuneNascita().getProvincia().getDescrizione() != null
//                && anagrafica.getComuneNascita().getProvincia().getDescrizione().equals(Constants.STATO_ESTERO)
//                && Strings.isNullOrEmpty(anagrafica.getLocalitaNascita())) {
//            errors.add(messageSource.getMessage("error.localitaStatoEsteroEmpty", null, Locale.getDefault()));
//        }

        if (anagrafica.getRecapiti() != null && anagrafica.getRecapiti().size() > 0) {
            int countRecapitiObbligatori = 0;
            for (RecapitoDTO rec : anagrafica.getRecapiti()) {
                LkTipoIndirizzo tipoIndirizzo = lookupService.findByIdTipoIndirizzo(rec.getIdTipoIndirizzo());
                if (Constants.INDIRIZZO_RESIDENZA.equals(tipoIndirizzo.getDescrizione())) {
                    countRecapitiObbligatori++;
                }
                if (!Arrays.asList(Constants.RECAPITI_ANA_FISICA).contains(tipoIndirizzo.getDescrizione())) {
                    errors.add("Il recapito di " + tipoIndirizzo.getDescrizione() + " non e' valido per l'anagrafica di tipo fisica");
                    break;
                }
            }
            if (countRecapitiObbligatori == 0) {
                errors.add(messageSource.getMessage("error.recapitoResidenzaEmpty", null, Locale.getDefault()));
            }
            if (countRecapitiObbligatori > 1) {
                errors.add(messageSource.getMessage("error.recapitoResidenzaTooMuch", null, Locale.getDefault()));
            }
        }

        return errors;
    }

    /**
     *
     * @param result
     * @param anagrafica
     * @return errors
     */
    public List<String> datiPersonaGiuridica(BindingResult result, AnagraficaDTO anagrafica) {
        List<String> errors = new ArrayList();

        if (!Strings.isNullOrEmpty(anagrafica.getPartitaIva())) {
            if (result.hasFieldErrors("partitaIva")) {
                errors.add(result.getFieldErrors("partitaIva").get(0).getDefaultMessage());
            }
        } else {
            errors.add(messageSource.getMessage("error.partitaIvaEmpty", null, Locale.getDefault()));
        }

        if (!Strings.isNullOrEmpty(anagrafica.getCodiceFiscale())) {
            if (!result.getFieldErrors("codiceFiscale").isEmpty()) {
                if (!new PartitaIvaValidatorImpl().Controlla(anagrafica.getCodiceFiscale())) {
                    errors.add(result.getFieldErrors("codiceFiscale").get(0).getDefaultMessage());
                }
            }
        } else {
            errors.add(messageSource.getMessage("error.codiceFiscaleEmpty", null, Locale.getDefault()));
        }

        if (Strings.isNullOrEmpty(anagrafica.getDenominazione())) {
            errors.add(messageSource.getMessage("error.denominazioneEmpty", null, Locale.getDefault()));
        }
        if (anagrafica.getIdFormaGiuridica() == null) {
            errors.add(messageSource.getMessage("error.formaGiuridicaEmpty", null, Locale.getDefault()));
        }

        if (!Strings.isNullOrEmpty(anagrafica.getFlgAttesaIscrizioneRea()) && anagrafica.getFlgAttesaIscrizioneRea().equalsIgnoreCase("N")) {
            if (Strings.isNullOrEmpty(anagrafica.getnIscrizioneRea())) {
                errors.add(messageSource.getMessage("error.rea.datiassenti", null, Locale.getDefault()));
            }
            if (anagrafica.getProvinciaCciaa() == null || anagrafica.getProvinciaCciaa().getIdProvincie() == null) {
                errors.add(messageSource.getMessage("error.idProvinciaCciaa", null, Locale.getDefault()));
            }
        }

        if (!Strings.isNullOrEmpty(anagrafica.getFlgAttesaIscrizioneRi()) && anagrafica.getFlgAttesaIscrizioneRi().equalsIgnoreCase("N")) {
            if (Strings.isNullOrEmpty(anagrafica.getnIscrizioneRi())) {
                errors.add(messageSource.getMessage("error.ri.datiassenti", null, Locale.getDefault()));
            }
            if (anagrafica.getProvinciaCciaa() == null || anagrafica.getProvinciaCciaa().getIdProvincie() == null) {
                errors.add(messageSource.getMessage("error.idProvinciaCciaa", null, Locale.getDefault()));
            }
        }

        errors.addAll(validAttesaIscrizone(anagrafica));
        if (anagrafica.getRecapiti() != null && anagrafica.getRecapiti().size() > 0) {
            int countRecapitiObbligatori = 0;
            for (RecapitoDTO rec : anagrafica.getRecapiti()) {
                LkTipoIndirizzo tipoIndirizzo = lookupService.findByIdTipoIndirizzo(rec.getIdTipoIndirizzo());
                if (Constants.INDIRIZZO_SEDE.equals(tipoIndirizzo.getDescrizione())) {
                    countRecapitiObbligatori++;
                }
                if (!Arrays.asList(Constants.RECAPITI_GIURIDICA).contains(tipoIndirizzo.getDescrizione())) {
                    errors.add("il recapito di " + tipoIndirizzo.getDescrizione() + " non e' valido per l'anagrafica di tipo giuridica");
                    break;
                }
            }
            if (countRecapitiObbligatori == 0) {
                errors.add(messageSource.getMessage("error.recapitoSedeEmpty", null, Locale.getDefault()));
            }
            if (countRecapitiObbligatori > 1) {
                errors.add(messageSource.getMessage("error.recapitoSedeTooMuch", null, Locale.getDefault()));
            }
        }

        return errors;
    }

    public List<String> datiPersonaDittaIndividuale(BindingResult result, AnagraficaDTO anagrafica) {
        List<String> errors = new ArrayList();
        if (Strings.isNullOrEmpty(anagrafica.getDenominazione())) {
            errors.add(messageSource.getMessage("error.denominazioneEmpty", null, Locale.getDefault()));
        }
        if (!Strings.isNullOrEmpty(anagrafica.getPartitaIva())) {
            if (result.hasFieldErrors("partitaIva")) {
                errors.add(result.getFieldErrors("partitaIva").get(0).getDefaultMessage());
            }
        } else {
            errors.add(messageSource.getMessage("error.partitaIvaEmpty", null, Locale.getDefault()));
        }
        if (anagrafica.getFlgAttesaIscrizioneRea() == null || "N".equalsIgnoreCase(anagrafica.getFlgAttesaIscrizioneRea())) {
            if (Strings.isNullOrEmpty(anagrafica.getnIscrizioneRea())) {
                errors.add(messageSource.getMessage("error.rea.datiassenti", null, Locale.getDefault()));
            }
        }

        if (anagrafica.getFlgAttesaIscrizioneRi() == null || "N".equalsIgnoreCase(anagrafica.getFlgAttesaIscrizioneRi())) {
            if (Strings.isNullOrEmpty(anagrafica.getnIscrizioneRi())) {
                errors.add(messageSource.getMessage("error.ri.datiassenti", null, Locale.getDefault()));
            }
        }

        if (anagrafica.getProvinciaCciaa() == null || anagrafica.getProvinciaCciaa().getIdProvincie() == null) {
            errors.add(messageSource.getMessage("error.idProvinciaCciaa", null, Locale.getDefault()));
        }
        errors.addAll(validAttesaIscrizone(anagrafica));
        return errors;
    }

    private List<String> validAttesaIscrizone(AnagraficaDTO anagrafica) {
        List<String> errors = new ArrayList<String>();
        if (anagrafica.getFlgAttesaIscrizioneRea() != null) {
            if (Utils.flagString(anagrafica.getFlgAttesaIscrizioneRea()).equals("S")) {
                if (!Utils.e(anagrafica.getnIscrizioneRea())) {
                    String err = messageSource.getMessage("error.rea.datipresenti", null, Locale.getDefault());
                    errors.add(err);
                }
            } else if (Utils.flagString(anagrafica.getFlgAttesaIscrizioneRea()).equals("N")) {
                if (Utils.e(anagrafica.getnIscrizioneRea())) {
                    String err = messageSource.getMessage("error.rea.datiassenti", null, Locale.getDefault());
                    errors.add(err);
                }
            }
        }
        if (anagrafica.getFlgAttesaIscrizioneRi() != null) {
            if (Utils.flagString(anagrafica.getFlgAttesaIscrizioneRi()).equals("S")) {
                if (!Utils.e(anagrafica.getnIscrizioneRi())) {
                    String err = messageSource.getMessage("error.ri.datipresenti", null, Locale.getDefault());
                    errors.add(err);
                }
            } else if (Utils.flagString(anagrafica.getFlgAttesaIscrizioneRi()).equals("N")) {
                if (Utils.e(anagrafica.getnIscrizioneRi())) {
                    String err = messageSource.getMessage("error.ri.datiassenti", null, Locale.getDefault());
                    errors.add(err);
                }
            }
        }
        return errors;
    }

    public List<String> recapito(BindingResult result, RecapitoDTO recapito) {
        List<String> errors = new ArrayList();

        if (recapito.getIdTipoIndirizzo() == null) {
            errors.add(messageSource.getMessage("error.descTipoIndirizzo", null, Locale.getDefault()));
        }
        if (Strings.isNullOrEmpty(recapito.getPec()) && Strings.isNullOrEmpty(recapito.getEmail()) && recapito.getIdComune() == null) {
            errors.add(messageSource.getMessage("error.recapitoEmpty", null, Locale.getDefault()));
        }
        if (recapito.getIdComune() != null) {
            String intestazione = "RECAPITO " + recapito.getDescTipoIndirizzo() + ": ";
            if (result.hasFieldErrors("telefono")) {
                String reject = recapito.getTelefono();
                if (reject != null && reject.length() > 0) {
                    errors.add(intestazione + result.getFieldErrors("telefono").get(0).getDefaultMessage());
                }
            }
            if (result.hasFieldErrors("fax")) {
                String reject = recapito.getFax();
                if (reject != null && reject.length() > 0) {
                    errors.add(intestazione + result.getFieldErrors("fax").get(0).getDefaultMessage());
                }
            }
            if (result.hasFieldErrors("cellulare")) {
                String reject = recapito.getCellulare();
                if (reject != null && reject.length() > 0) {
                    errors.add(intestazione + result.getFieldErrors("cellulare").get(0).getDefaultMessage());
                }
            }

            if (result.hasFieldErrors("cap")) {
                String reject = recapito.getCap();
                if (reject != null && reject.length() > 0) {
                    if (recapito.getDescProvincia() != null && !recapito.getDescProvincia().equals(Constants.STATO_ESTERO)) {
                        errors.add(intestazione + result.getFieldErrors("cap").get(0).getDefaultMessage());
                    }
                }
            }
            if (Strings.isNullOrEmpty(recapito.getIndirizzo())) {
                errors.add(intestazione + messageSource.getMessage("error.indirizzo", null, Locale.getDefault()));
            }
            if (Strings.isNullOrEmpty(recapito.getnCivico())) {
                errors.add(intestazione + messageSource.getMessage("error.nCivico", null, Locale.getDefault()));
            }
        }

        if (!Strings.isNullOrEmpty(recapito.getDescComune()) && recapito.getIdComune() == null) {
            errors.add(messageSource.getMessage("error.descComune", null, Locale.getDefault()));
        }
        if (errors.size() > 0) {
            return errors;
        }
        return null;
    }

    public List<String> DatiCatastali(BindingResult result) {
        List<String> errors = new ArrayList();
        if (result.hasFieldErrors("idTipoSistemaCatastale")) {
            errors.add(result.getFieldErrors("idTipoSistemaCatastale").get(0).getDefaultMessage());
        }
        if (errors.size() > 0) {
            return errors;
        }
        return null;
    }

    public List<String> PraticaProtocollo(BindingResult result, PraticaDTO pratica) {
        List<String> errors = new ArrayList();
        if (result.hasFieldErrors("destinatario.idEnte")) {
            errors.add(result.getFieldErrors("destinatario.idEnte").get(0).getDefaultMessage());
        }
        if (result.hasFieldErrors("comune.descrizione")) {
            errors.add(result.getFieldErrors("comune.descrizione").get(0).getDefaultMessage());
        }
        if (pratica.getProcedimentoPratica() == null) {
            errors.add("Selezionare il procedimento di riferimento della pratica");
        }

        if (pratica.getProcedimentiList() != null && pratica.getProcedimentiList().size() > 0) {
            for (int i = 0; i < pratica.getProcedimentiList().size(); i++) {
                if (result.hasFieldErrors("procedimentiList[" + i + "].desProcedimento")) {
                    errors.add(result.getFieldErrors("procedimentiList[" + i + "].desProcedimento").get(0).getDefaultMessage());
                }
            }
        }
        if (errors.size() > 0) {
            return errors;
        }
        return null;
    }

    public List<String> CaricamentoManuale(BindingResult result, PraticaDTO pratica, ProtocolloDTO protocollo) throws Exception {
        List<String> errors = new ArrayList();

        if (result.hasFieldErrors("dataRicezione")) {
            errors.add(result.getFieldErrors("dataRicezione").get(0).getDefaultMessage());
        }
        if (Utils.e(protocollo.getOggetto())) {
            errors.add("L'oggetto della pratica non puo essere nullo");
        }
        if (!Utils.e(pratica.getRegistro()) || !Utils.e(pratica.getProtocollo()) || !Utils.e(pratica.getAnnoRiferimento())) {
            if (result.hasFieldErrors("registro")) {
                errors.add(result.getFieldErrors("registro").get(0).getDefaultMessage());
            }
            if (result.hasFieldErrors("protocollo")) {
                errors.add(result.getFieldErrors("protocollo").get(0).getDefaultMessage());
            }
            if (protocollo.getAnno() == null || protocollo.getAnno() == 0) {
                errors.add("Anno di riferimento e' un campo numerico composto da 4 cifre");
            }
            Date oggi = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
            Integer anno = Integer.parseInt(dateFormat.format(oggi));
            if (protocollo.getAnno() > anno) {
                errors.add("Anno di riferimento errato. Puo solo essere l'anno attuale o antecedente.");
            }
        }

        if (pratica.getAllegatiList() != null && pratica.getAllegatiList().size() > 0) {
            int i = 0;
            boolean error = false;
            String erroreAllegato = null;
            int countAllegatoOriginale = 0;
            for (AllegatoDTO allegato : pratica.getAllegatiList()) {
                if (result.hasFieldErrors("allegatiList[" + i + "].descrizione")) {
                    errors.add(result.getFieldErrors("allegatiList[" + i + "].descrizione").get(0).getDefaultMessage());
                    error = true;
                }

                if ((allegato.getFile() == null || allegato.getFile().isEmpty()) && Utils.e(allegato.getPathFile())) {
                    if (erroreAllegato == null) {
                        erroreAllegato = "E' obbligatorio per ogni allegato indicare sia la descrizione che caricare documento";
                        errors.add(erroreAllegato);
                    }
                    error = true;
                }
                if (!Utils.e(allegato.getModelloPratica()) && allegato.getModelloPratica().equals(Boolean.TRUE)) {
                    countAllegatoOriginale++;
                }
            }
            if (countAllegatoOriginale != 1) {
                errors.add("Ci deve essere almeno 1 allegato segnato come come riepilogo pratica.");
            }
        }
        List<String> err1 = PraticaProtocollo(result, pratica);
        if (err1 != null) {
            errors.addAll(err1);
        }
        if (errors.size() > 0) {
            return errors;
        }
        return null;
    }

    public List<String> allegato(BindingResult result, AllegatoDTO allegato) {
        List<String> errors = new ArrayList();
        if (result.hasFieldErrors("descrizione")) {
            errors.add(result.getFieldErrors("descrizione").get(0).getDefaultMessage());
        }
        if ((allegato.getFile() == null || allegato.getFile().isEmpty()) && Utils.e(allegato.getPathFile())) {
            errors.add("E' obbligatorio per ogni allegato indicare sia la descrizione che caricare documento");
        }
        return errors;
    }

    public List<String> PraticaEvento(BindingResult result, PraticaDTO pratica) {
        List<String> errors = new ArrayList();
        if (result.hasFieldErrors("destinatario.idEnte")) {
            errors.add(result.getFieldErrors("destinatario.idEnte").get(0).getDefaultMessage());
        }
        if (result.hasFieldErrors("comune.descrizione")) {
            errors.add(result.getFieldErrors("comune.descrizione").get(0).getDefaultMessage());
        }
        for (int i = 0; i < pratica.getProcedimentiList().size(); i++) {
            if (result.hasFieldErrors("procedimentiList[" + i + "].desProcedimento")) {
                errors.add(result.getFieldErrors("procedimentiList[" + i + "].desProcedimento").get(0).getDefaultMessage());
            }
        }
        if (errors.size() > 0) {
            return errors;
        }
        return null;
    }

    public List<String> Evento(BindingResult result) {
        List<String> errors = new ArrayList();
        if (result.hasFieldErrors("numeroDiProtocollo")) {
            errors.add(result.getFieldErrors("numeroDiProtocollo").get(0).getDefaultMessage());
        }
        return errors;
    }

    public List<String> Ente(BindingResult result, EnteDTO evento) {
        List<String> errors = new ArrayList();
        if (result.hasFieldErrors("idEnte")) {
            errors.add(result.getFieldErrors("idEnte").get(0).getDefaultMessage());
        }
        if (result.hasFieldErrors("descrizione")) {
            errors.add(result.getFieldErrors("descrizione").get(0).getDefaultMessage());
        }
        if (result.hasFieldErrors("codEnte")) {
            errors.add(result.getFieldErrors("codEnte").get(0).getDefaultMessage());
        }
        if (result.hasFieldErrors("codiceFiscale")) {
            errors.add(result.getFieldErrors("codiceFiscale").get(0).getDefaultMessage());
        }
        if (result.hasFieldErrors("partitaIva")) {
            errors.add(result.getFieldErrors("partitaIva").get(0).getDefaultMessage());
        }
        if (!Utils.e(evento.getCitta()) && result.hasFieldErrors("citta")) {
            errors.add(result.getFieldErrors("citta").get(0).getDefaultMessage());
        }
        if (!Utils.e(evento.getProvincia()) && result.hasFieldErrors("provincia")) {
            errors.add(result.getFieldErrors("provincia").get(0).getDefaultMessage());
        }

        if (result.hasFieldErrors("pec")) {
            errors.add(result.getFieldErrors("pec").get(0).getDefaultMessage());
        }
        if (!result.hasFieldErrors("pec") && !Utils.e(evento.getEmail()) && result.hasFieldErrors("email")) {
            errors.add(result.getFieldErrors("email").get(0).getDefaultMessage());
        }
        if (!Utils.e(evento.getTelefono()) && result.hasFieldErrors("telefono")) {
            errors.add(result.getFieldErrors("telefono").get(0).getDefaultMessage());
        }
        if (result.hasFieldErrors("tipoEnte")) {
            errors.add(result.getFieldErrors("tipoEnte").get(0).getDefaultMessage());
        }
        if (result.hasFieldErrors("codiceAoo")) {
            errors.add(result.getFieldErrors("codiceAoo").get(0).getDefaultMessage());
        }
        if (result.hasFieldErrors("unitaOrganizzativa")) {
            errors.add(result.getFieldErrors("unitaOrganizzativa").get(0).getDefaultMessage());
        }
        String g = "";
        if (errors.size() > 0) {
            return errors;
        }
        return null;
    }

    public List<String> Pratica(BindingResult result, PraticaDTO pratica) {
        List<String> errors = new ArrayList();
        Map<String, String> ruoliAna = new HashMap<String, String>();
        for (AnagraficaDTO anagrafica : pratica.getAnagraficaList()) {
            if (ruoliAna.get(anagrafica.getCodiceFiscale() + anagrafica.getIdTipoRuolo()) == null) {
                ruoliAna.put(anagrafica.getCodiceFiscale() + anagrafica.getIdTipoRuolo(), "1");
            } else {
                errors.add("L'anagrafica " + anagrafica.getCodiceFiscale() + " e' registrata piu di una volta con lo stesso ruolo " + anagrafica.getDesTipoRuolo());
            }
        }
        if (errors.size() > 0) {
            return errors;
        }
        return null;
    }

    public List<String> Procedimento(BindingResult result, ProcedimentoDTO procedimento) {
        List<String> errors = new ArrayList();
        if (result.hasFieldErrors("codProcedimento")) {
            errors.add(result.getFieldErrors("codProcedimento").get(0).getDefaultMessage());
        }
        if (result.hasFieldErrors("termini")) {
            errors.add(result.getFieldErrors("termini").get(0).getDefaultMessage());
        }
        if (result.hasFieldErrors("tipoProc")) {
            errors.add(result.getFieldErrors("tipoProc").get(0).getDefaultMessage());
        }
        if (result.hasFieldErrors("procedimentiTestiList[0].descrizione")) {
            errors.add(result.getFieldErrors("procedimentiTestiList[0].descrizione").get(0).getDefaultMessage());
        }
        if (errors.size() > 0) {
            return errors;
        }
        return null;
    }

    public List<String> Utente(BindingResult result, UtenteDTO DTO) {
        List<String> errors = new ArrayList();
        if (result.hasFieldErrors("nome")) {
            errors.add(result.getFieldErrors("nome").get(0).getDefaultMessage());
        }
        if (result.hasFieldErrors("cognome")) {
            errors.add(result.getFieldErrors("cognome").get(0).getDefaultMessage());
        }
        if (result.hasFieldErrors("codiceFiscale")) {
            errors.add(result.getFieldErrors("codiceFiscale").get(0).getDefaultMessage());
        }
        if (result.hasFieldErrors("username")) {
            errors.add(result.getFieldErrors("username").get(0).getDefaultMessage());
        }
        return errors;
    }
}
