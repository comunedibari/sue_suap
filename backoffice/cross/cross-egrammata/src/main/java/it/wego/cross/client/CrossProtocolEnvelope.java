/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.client;

import it.eng.people.connects.interfaces.envelope.beans.Allegato;
import it.eng.people.connects.interfaces.envelope.beans.CodiceSistema;
import it.eng.people.connects.interfaces.envelope.beans.CredenzialiUtenteCertificate;
import it.eng.people.connects.interfaces.envelope.beans.IdentificatorediRichiesta;
import it.eng.people.connects.interfaces.envelope.beans.PersonaFisica;
import it.eng.people.connects.interfaces.envelope.beans.PersonaGiuridica;
import it.eng.people.connects.interfaces.envelope.beans.Recapito;
import it.eng.people.connects.interfaces.envelope.beans.Richiedente;
import it.eng.people.connects.interfaces.envelope.beans.RiepilogoRichiesta;
import it.eng.people.connects.interfaces.envelope.beans.Titolare;
import it.eng.people.connects.interfaces.envelope.exceptions.XMLEnvelopeParseException;
import it.eng.people.connects.interfaces.envelope.exceptions.XMLUpdateProtocolException;
import it.eng.people.connects.interfaces.protocollo.IProtocolEnvelope;
import it.eng.people.connects.interfaces.protocollo.beans.IdentificatoreDiProtocollo;
import it.wego.cross.plugins.protocollo.beans.DocumentoProtocolloRequest;
import it.wego.cross.plugins.protocollo.beans.SoggettoProtocollo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author giuseppe
 */
public class CrossProtocolEnvelope implements IProtocolEnvelope {

    private final DocumentoProtocolloRequest documentoProtocolloRequest;
    public static final String FISICA = "F";
    public static final String GIURIDICA = "G";

    public CrossProtocolEnvelope(DocumentoProtocolloRequest documentoProtocolloRequest) {
        this.documentoProtocolloRequest = documentoProtocolloRequest;
    }

    /**
     * * RICHIESTO DALLA PROTOCOLLAZIONE **
     * @return 
     */
    @Override
    public Titolare getTitolare() {
        List<SoggettoProtocollo> soggetti = documentoProtocolloRequest.getSoggetti();
        SoggettoProtocollo soggettoTitolare = null;
        for (SoggettoProtocollo soggetto : soggetti) {
            if (soggetto.getTitolare()) {
                soggettoTitolare = soggetto;
                break;
            }
        }
        if (soggettoTitolare == null) {
            soggettoTitolare = soggetti.get(0);
        }
        Titolare titolare;
        if (soggettoTitolare.getTipoPersona().equals(FISICA)) {
            PersonaFisica pf = new PersonaFisica();
            pf.setCodiceFiscale(soggettoTitolare.getCodiceFiscale());
            pf.setCognome(soggettoTitolare.getCognome());
            pf.setNome(soggettoTitolare.getNome());
            titolare = new Titolare(pf);
        } else {
            PersonaGiuridica pg = new PersonaGiuridica();
            pg.setCodiceFiscale(soggettoTitolare.getCodiceFiscale());
            pg.setPartitaIVA(soggettoTitolare.getPartitaIva());
            pg.setDenominazione(soggettoTitolare.getDenominazione());
            pg.setDomicilioElettronico(soggettoTitolare.getMail());
            pg.setSedeLegale(soggettoTitolare.getIndirizzo());
            titolare = new Titolare(pg);
        }
        return titolare;
    }

    @Override
    public Collection getAllegati() {
        Collection allegati = new ArrayList();
        List<it.wego.cross.plugins.commons.beans.Allegato> allegatiCross = documentoProtocolloRequest.getAllegati();
        for (it.wego.cross.plugins.commons.beans.Allegato allegatoCross : allegatiCross) {
            Allegato allegato = new Allegato();
            String b64File = Base64.encodeBase64String(allegatoCross.getFile());
            allegato.setContenuto(b64File);
            allegato.setDescrizione(allegatoCross.getDescrizione());
            allegato.setMimeType(allegatoCross.getTipoFile());
            allegato.setNomeFile(allegatoCross.getNomeFile());
            allegati.add(allegato);
        }
        return allegati;
    }

    @Override
    public RiepilogoRichiesta getRiepilogo() {
        RiepilogoRichiesta riepilogo = new RiepilogoRichiesta();
        it.wego.cross.plugins.commons.beans.Allegato riepilogoRichiesta = documentoProtocolloRequest.getAllegatoOriginale();
        if (riepilogoRichiesta.getFile() != null) {
            String b64File = Base64.encodeBase64String(riepilogoRichiesta.getFile());
            riepilogo.setContenuto(b64File);
        }
        riepilogo.setDescrizione(riepilogoRichiesta.getDescrizione());
        riepilogo.setMimeType(riepilogoRichiesta.getTipoFile());
        riepilogo.setNomeFile(riepilogoRichiesta.getNomeFile());
        return riepilogo;
    }

    /**
     * * FINE RICHIESTO DALLA PROTOCOLLAZIONE **
     * @return IdentificatoreDiProtocollo
     */
    @Override
    public IdentificatoreDiProtocollo getProtocolloUscitaEnteMittente() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getContestoServizio() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getNomeServizio() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public CodiceSistema getSistemaDestinatario() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void parseXml(String string) throws XMLEnvelopeParseException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getIdentificatoreRichiesta() {
        return documentoProtocolloRequest.getIdentificativoPratica();
    }

    @Override
    public IdentificatorediRichiesta getIdentificatoreRichiestaCompleto() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public CredenzialiUtenteCertificate getCredenzialiUtenteCertificate() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Richiedente getRichiedente() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Recapito getRecapito() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getXml() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getTipoDocumento() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void updateXml(IdentificatoreDiProtocollo idp) throws XMLUpdateProtocolException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isInvioDiCortesia() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
