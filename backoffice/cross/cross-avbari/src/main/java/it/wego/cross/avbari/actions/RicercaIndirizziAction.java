/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.avbari.actions;

import com.google.common.base.Strings;
import it.wego.cross.avbari.catasto.client.CatastoFabbricatiService;
import it.wego.cross.avbari.catasto.client.ICatastoFabbricatiService;
import it.wego.cross.avbari.catasto.client.xsd.Input;
import it.wego.cross.avbari.catasto.client.xsd.Output;
import it.wego.cross.avbari.catasto.client.xsd.PrecompilazioneBean;
import it.wego.cross.avbari.constants.AvBariConstants;
import it.wego.cross.dto.dozer.IndirizzoInterventoDTO;
import it.wego.cross.entity.Pratica;
import it.wego.cross.exception.CrossException;
import it.wego.cross.service.ConfigurationService;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.Utils;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author piergiorgio
 */
@Component
public class RicercaIndirizziAction {

    @Autowired
    private ConfigurationService configurationService;
    public final static QName SERVICE = new QName("http://tempuri.org/", "CatastoFabbricatiService");

    public List<IndirizzoInterventoDTO> RicercaIndirizzi(IndirizzoInterventoDTO indirizzoInterventoDTO, Pratica pratica) throws Exception {
        if (Strings.isNullOrEmpty(indirizzoInterventoDTO.getIndirizzo())) {
            Log.APP.info("ricerca indirizzi - inserire un indirizzo");
            throw new CrossException("ricerca indirizzi - inserire un indirizzo");
        }
        Integer idEnte = pratica.getIdProcEnte().getIdEnte().getIdEnte();
        PrecompilazioneBean precompilazioneBean = new PrecompilazioneBean();
        precompilazioneBean.setInput(new Input());
        Input.CampoPrecompilazioneBean cp = new Input.CampoPrecompilazioneBean();
        cp.setCodice("COD_COMUNE");
        cp.setDescrizione(pratica.getIdComune().getCodCatastale());
        precompilazioneBean.getInput().getCampoPrecompilazioneBean().add(cp);
        cp = new Input.CampoPrecompilazioneBean();
        cp.setCodice("DES_VIA");
        cp.setDescrizione(indirizzoInterventoDTO.getIndirizzo());
        precompilazioneBean.getInput().getCampoPrecompilazioneBean().add(cp);
        cp = new Input.CampoPrecompilazioneBean();
        cp.setCodice("COD_CIVICO");
        cp.setDescrizione(indirizzoInterventoDTO.getCivico() == null ? "" : indirizzoInterventoDTO.getCivico());
        precompilazioneBean.getInput().getCampoPrecompilazioneBean().add(cp);
        cp = new Input.CampoPrecompilazioneBean();
        cp.setCodice("INTERNI_SN");
        cp.setDescrizione("S");
        precompilazioneBean.getInput().getCampoPrecompilazioneBean().add(cp);
        precompilazioneBean.setCodEnte("010025");
        String address = configurationService.getCachedPluginConfiguration(AvBariConstants.INDIRIZZI_ENDPOINT, idEnte, null);

        URL wsdlURL = CatastoFabbricatiService.class.getResource("CatastoFabbricatiService.svc.wsdl");
        CatastoFabbricatiService ss = new CatastoFabbricatiService(wsdlURL, SERVICE);

        ICatastoFabbricatiService port = ss.getBasicHttpBindingICatastoFabbricatiService();
        ((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, address);

        java.lang.String _process_data = Utils.marshall(precompilazioneBean);
        java.lang.String _process__return = port.process(_process_data);
        PrecompilazioneBean risposta = null;
        InputStream is = null;
        JAXBContext jc = null;
        Unmarshaller um = null;
        jc = JAXBContext.newInstance(PrecompilazioneBean.class);
        um = jc.createUnmarshaller();
        is = new ByteArrayInputStream(_process__return.getBytes());
        risposta = (PrecompilazioneBean) um.unmarshal(is);
        if (risposta.getOutput() == null || risposta.getOutput().getCampoPrecompilazioneBean() == null || risposta.getOutput().getCampoPrecompilazioneBean().isEmpty()) {
            throw new CrossException("Ricerca errata: nessun risultato");
        }
        return CaricaDTO(risposta);
    }

    private List<IndirizzoInterventoDTO> CaricaDTO(PrecompilazioneBean risposta) {
        List<IndirizzoInterventoDTO> lista = new ArrayList<IndirizzoInterventoDTO>();
        Output valori = risposta.getOutput();
        if (valori != null && valori.getCampoPrecompilazioneBean() != null && !valori.getCampoPrecompilazioneBean().isEmpty()) {
            List<Output.CampoPrecompilazioneBean> campi = valori.getCampoPrecompilazioneBean();
            //Indica quanti sono i risultati che mi ha dato la query
            int total = campi.get(0).getDescrizione().size();
            for (int i = 0; i < total; i++) {
                IndirizzoInterventoDTO dto = new IndirizzoInterventoDTO();
                for (Output.CampoPrecompilazioneBean campo : campi) {
                    if (campo.getCodice().equals("COD_VIA")) {
                        String value = campo.getDescrizione().get(i);
                        dto.setCodVia(value);
                    } else if (campo.getCodice().equals("COD_CIVICO")) {
                        String value = campo.getDescrizione().get(i);
                        dto.setCodCivico(value);
                    } else if (campo.getCodice().equals("DES_VIA")) {
                        String value = campo.getDescrizione().get(i);
                        dto.setIndirizzo(value);
                    } else if (campo.getCodice().equals("NUMERO_INTERNO")) {
                        String value = campo.getDescrizione().get(i);
                        dto.setInternoNumero(value);
                    } else if (campo.getCodice().equals("FOGLIO")) {
                        String value = campo.getDescrizione().get(i);
                        dto.setFoglio(value);
                    } else if (campo.getCodice().equals("NUMERO")) {
                        String value = campo.getDescrizione().get(i);
                        dto.setMappale(value);
                    }
                }
                lista.add(dto);
            }
        }
        return lista;
    }

}
