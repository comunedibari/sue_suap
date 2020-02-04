/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.webservices.cxf.interoperability;

import it.wego.cross.utils.Utils;
import it.wego.cross.validator.impl.CodiceFiscaleValidatorImpl;
import it.wego.cross.validator.impl.PartitaIvaValidatorImpl;

/**
 *
 * @author giuseppe
 */
public class InteroperabilityValidator {

    public static boolean isValidEvento(Evento evento) throws Exception {
        CodiceFiscaleValidatorImpl cfValid = new CodiceFiscaleValidatorImpl();
        PartitaIvaValidatorImpl pivaValid = new PartitaIvaValidatorImpl();
        if (evento != null) {
            if (Utils.e(evento.getIdPratica())) {
                throw new Exception("Non e' stato specificato l'identificativo della pratica");
            }
            if (Utils.e(evento.getCodiceEnte())) {
                throw new Exception("Non e' stato specificato il codice dell'Ente");
            }
            if (Utils.e(evento.getCodiceEvento())) {
                throw new Exception("Non e' stato specificato il codice dell'evento");
            }
            if (evento.getSoggetto() == null
                    || evento.getSoggetto().getSoggetti() == null
                    || (evento.getSoggetto().getSoggetti() != null && evento.getSoggetto().getSoggetti().isEmpty())) {
                throw new Exception("Non sono stati specificati i soggetti coinvolti");
            } else {
                for (Soggetto soggetto : evento.getSoggetto().getSoggetti()) {
                    if (Utils.e(soggetto.getCodiceFiscale())) {
                        throw new Exception("Non e' stato specificato il codice fiscale del soggetto");
                    } else {
                        if (!cfValid.Controlla(soggetto.getCodiceFiscale()) && !pivaValid.Controlla(soggetto.getCodiceFiscale())) {
                            throw new Exception("Il codice fiscale " + soggetto.getCodiceFiscale() + " per il soggetto non è valido");
                        }
                    }
                    if (Utils.e(soggetto.getTipoSoggetto())) {
                        throw new Exception("Non e' stata specificata la tipologia di soggetto");
                    } else {
                        if (!soggetto.getTipoSoggetto().equals(TipoSoggetto.ENTE) && soggetto.getTipoSoggetto().equals(TipoSoggetto.ANAGRAFICA)) {
                            throw new Exception("La tipologia di soggetto deve essere del tipo ENTE o ANAGRAFICA");
                        }
                    }
                }
            }
        } else {
            throw new Exception("Non e' stata valorizzata la struttura dati della pratica");
        }
        return true;
    }
}
