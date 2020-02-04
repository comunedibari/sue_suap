/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.service;

import it.gov.impresainungiorno.schema.suap.ri.ProtocolloSUAP;
import it.gov.impresainungiorno.schema.suap.ri.RichiestaIscrizioneImpresaRiSPC;
import it.gov.impresainungiorno.schema.suap.ri.spc.IscrizioneImpresaRiSpcResponse;
import org.springframework.stereotype.Service;

/**
 *
 * @author Gabriele
 */
@Service
public interface RegistroImpreseService {

    public IscrizioneImpresaRiSpcResponse richiestaIscrizioneImpresa(String codiceFiscale) throws Exception;

    public RichiestaIscrizioneImpresaRiSPC getRichiestaIscrizioneImpresaPortType() throws Exception;

    public ProtocolloSUAP getComunicazioneSuapPortType() throws Exception;
}
