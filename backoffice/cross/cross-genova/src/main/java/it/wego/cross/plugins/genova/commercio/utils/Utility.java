/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.plugins.genova.commercio.utils;

import it.wego.cross.plugins.genova.commercio.bean.ResponseWsBean;
import it.wego.cross.plugins.genova.commercio.xml.errore.risposta.RispostaValidazione;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author piergiorgio
 */
public class Utility {

    public ResponseWsBean checkRisposta(String result) {
        ResponseWsBean ret = new ResponseWsBean();
        ret.setOperazioneOK(Boolean.FALSE);
        RispostaValidazione rispostaValidazione = null;
        try {
            InputStream is = null;
            JAXBContext jc = null;
            Unmarshaller um = null;
            if (result.contains("risposta_validazione")) {
                jc = JAXBContext.newInstance(RispostaValidazione.class);
                um = jc.createUnmarshaller();
                is = new ByteArrayInputStream(result.getBytes());
                rispostaValidazione = (RispostaValidazione) um.unmarshal(is);
                ret.setOperazioneOK(Boolean.FALSE);
                ret.setMessaggio("Errore generico WebService COmmercio");
            }
            if (rispostaValidazione == null) {
                ret.setOperazioneOK(Boolean.TRUE);
            }
        } catch (Exception ex) {
            ret.setOperazioneOK(Boolean.FALSE);
            ret.setMessaggio("Errore in fase di chiamata al servizio commercio - " + ex.toString());
        }
        return ret;
    }
}
