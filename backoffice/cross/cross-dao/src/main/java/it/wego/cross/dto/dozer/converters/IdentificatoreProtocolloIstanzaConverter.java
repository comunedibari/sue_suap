/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto.dozer.converters;

import it.wego.cross.constants.AnaTipiEvento;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.utils.Utils;
import java.math.BigInteger;
import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;
import org.dozer.ConfigurableCustomConverter;
import org.dozer.Mapper;
import org.dozer.MapperAware;
import org.dozer.MappingException;

/**
 *
 * @author piergiorgio
 */
public class IdentificatoreProtocolloIstanzaConverter implements ConfigurableCustomConverter, MapperAware {

    private Mapper mapper;

    @Override
    public Object convert(Object destinationFieldValue, Object sourceFieldValue, Class<?> destinationClass, Class<?> sourceClass) {
        if (sourceFieldValue == null) {
            return null;
        }

        try {
            Pratica pratica = (Pratica) sourceFieldValue;
            PraticheEventi eventoRicezionePratica = IdentificatoreProtocolloIstanzaConverter.getEventoRicezionePratica(pratica);

            if (eventoRicezionePratica == null) {
                return "";
            }

            String protocolloIstanza = "";
            if (eventoRicezionePratica.getProtocollo() != null) {
                String[] protoSplit = eventoRicezionePratica.getProtocollo().split("/");
                if (protoSplit != null && protoSplit.length == 3) {
                    protocolloIstanza = protoSplit[2];
                }
            }

            return StringUtils.join(Arrays.asList(protocolloIstanza, pratica.getProtocollo(), pratica.getAnnoRiferimento()), "/");
        } catch (Exception e) {
            throw new MappingException("Errore durante il mapping", e);
        }
    }

    @Override
    public void setParameter(String string) {

    }

    @Override
    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }

    public static PraticheEventi getEventoRicezionePratica(Pratica praticaCross) throws Exception {
        PraticheEventi eventoRicezione = null;
        for (PraticheEventi eventoPratica : praticaCross.getPraticheEventiList()) {
            if (eventoPratica.getIdEvento().getCodEvento().equals(AnaTipiEvento.RICEZIONE_PRATICA)) {
                eventoRicezione = eventoPratica;
            }
        }
        if (eventoRicezione == null) {
            throw new Exception("Impossibile utilizzare una pratica sulla quale non esiste l'evento di ricezione.");
        }
        return eventoRicezione;
    }
}
