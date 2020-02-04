/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.plugins.protocollo;

import it.eng.people.connects.interfaces.protocollo.beans.IdentificatoreDiProtocollo;
import it.wego.cross.beans.EGrammataIdentificatoreDiProtocollo;
import it.wego.cross.client.EGrammataProtocol;
import it.wego.cross.plugins.protocollo.beans.DocumentoProtocolloResponse;
import java.io.IOException;
import java.util.HashMap;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.xml.sax.SAXException;

/**
 *
 * @author Giuseppe
 */
public class TestProtocollo {

    @Test
    @Ignore
    public void ricercaPerEstremiProtocollo() {
        HashMap<String, Object> configurazioneProtocollo = new HashMap<String, Object>();
        configurazioneProtocollo.put("ID_POSTAZ_LAVORO", "100013");
        configurazioneProtocollo.put("INDIRIZZO_WS_RICERCA_PROTOCOLLO", "http://127.0.0.1:1234/axis/services/WSRicercaProtocollo");
        configurazioneProtocollo.put("CODICE_ENTE", "1");
        configurazioneProtocollo.put("USERNAME", "PEOPLE1");
        configurazioneProtocollo.put("PASSWORD", "");
        configurazioneProtocollo.put("TIMEOUT", "600000");
        EGrammataProtocol protocol = new EGrammataProtocol();
        DocumentoProtocolloResponse response = protocol.findByEstremiProtocolloGenerale(2013, "842", configurazioneProtocollo);
        Assert.assertNotNull(response);
    }
    
    @Test
    public void testGetIdentificatoreDiProtocollo(){
        try {
            String xmlToManage = "<?xml version=\"1.0\" ?><!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\"><Risposta>    <Stato>        <Codice>0</Codice>        <Messaggio>Protocollazione con allegati OK! E' stato protocollato il documento PG/2014/221835 in data 24/07/2014 18:09 - L'allegato n.3 associato al documento non � stato salvato correttamente nel repository![DMPK_CORE.AddDoc] Il nome di un file non puo contenere i caratteri  / \\ : \" | * < ></Messaggio>    </Stato><NumeroProtocollo tipo=\"PG\" anno=\"2014\" numero=\"221835\" data=\"24/07/2014 18:09\"/></Risposta>";
            EGrammataProtocol protocol = new EGrammataProtocol();
            EGrammataIdentificatoreDiProtocollo egidp = protocol.getIdentificatoreDiProtocollo(xmlToManage);
            IdentificatoreDiProtocollo identificatoreDiProtocollo = egidp.getIdentificatoreDiProtocollo();
            Assert.assertNotNull(egidp.getMessage());
            Assert.assertNotNull(identificatoreDiProtocollo);
            Assert.assertEquals("PG", identificatoreDiProtocollo.getCodiceAOO());
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        } catch (SAXException ex) {
            ex.printStackTrace();
        }
    }
}
