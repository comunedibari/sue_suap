/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.service;

import it.gov.impresainungiorno.schema.suap.ente.CooperazioneEnteSUAP;
import it.gov.impresainungiorno.schema.suap.ri.ProtocolloSUAP;
import it.gov.impresainungiorno.schema.suap.ri.RichiestaIscrizioneImpresaRiSPC;
import it.gov.impresainungiorno.schema.suap.ri.spc.IscrizioneImpresaRiSpcResponse;
import it.wego.cross.dto.dozer.forms.ComunicazioneDTO;
import it.wego.cross.entity.Pratica;

import java.io.IOException;
import java.text.ParseException;

import javax.xml.datatype.DatatypeConfigurationException;

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
    public CooperazioneEnteSUAP creaCES(Pratica pratica, ComunicazioneDTO comunicazione) throws Exception;
    public   CooperazioneEnteSUAP creaCES2(Pratica pratica) throws ParseException, IOException;
    public   CooperazioneEnteSUAP creaCES3(Pratica pratica, ComunicazioneDTO comunicazione) throws ParseException, IOException,DatatypeConfigurationException;
}
