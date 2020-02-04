/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.avbari.actions;

import com.google.common.base.Strings;
import it.wego.cross.avbari.catasto.client.Catasto;
import it.wego.cross.avbari.catasto.client.ICatasto;
import it.wego.cross.avbari.catasto.client.xsd.Input;
import it.wego.cross.avbari.catasto.client.xsd.Output;
import it.wego.cross.avbari.catasto.client.xsd.PrecompilazioneBean;
import it.wego.cross.avbari.constants.AvBariConstants;
import it.wego.cross.dao.LookupDao;
import it.wego.cross.dto.dozer.DatiCatastaliDTO;
import it.wego.cross.entity.LkTipoUnita;
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
public class RicercaDatiCatastaliAction {

    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private LookupDao lookupDao;
    private static final QName SERVICE = new QName("http://tempuri.org/", "Catasto");

    public List<DatiCatastaliDTO> RicercaDatiCatastali(DatiCatastaliDTO datiCatastaliDTO, Pratica pratica) throws Exception {
        if (!datiCatastaliDTO.getIdTipoSistemaCatastale().equals(1)) {
            Log.APP.info("ricerca dati catastali - tipo catasto errato");
            throw new CrossException("ricerca dati catastali - tipo catasto errato");
        }
        if (Strings.isNullOrEmpty(datiCatastaliDTO.getFoglio())) {
            Log.APP.info("ricerca dati catastali - inserire almeno il foglio");
            throw new CrossException("ricerca dati catastali - inserire almeno il foglio");
        }
        Integer idEnte = pratica.getIdProcEnte().getIdEnte().getIdEnte();
        PrecompilazioneBean precompilazioneBean = new PrecompilazioneBean();
        LkTipoUnita tu = lookupDao.findTipoUnitaById(datiCatastaliDTO.getIdTipoUnita());
        precompilazioneBean.setInput(new Input());
        Input.CampoPrecompilazioneBean cp = new Input.CampoPrecompilazioneBean();
        cp.setCodice("TIPO_RICERCA");
        if (tu != null) {
            cp.setDescrizione(tu.getCodTipoUnita());
        } else {
            cp.setDescrizione("");
        }
        precompilazioneBean.getInput().getCampoPrecompilazioneBean().add(cp);
        cp = new Input.CampoPrecompilazioneBean();
        cp.setCodice("COD_COMUNE");
        cp.setDescrizione(pratica.getIdComune().getCodCatastale());
        precompilazioneBean.getInput().getCampoPrecompilazioneBean().add(cp);
        cp = new Input.CampoPrecompilazioneBean();
        cp.setCodice("FOGLIO");
        cp.setDescrizione(datiCatastaliDTO.getFoglio() == null ? "" : datiCatastaliDTO.getFoglio());
        precompilazioneBean.getInput().getCampoPrecompilazioneBean().add(cp);
        cp = new Input.CampoPrecompilazioneBean();
        cp.setCodice("PARTICELLA");
        cp.setDescrizione(datiCatastaliDTO.getMappale() == null ? "" : datiCatastaliDTO.getMappale());
        precompilazioneBean.getInput().getCampoPrecompilazioneBean().add(cp);
        precompilazioneBean.setCodEnte(pratica.getIdComune().getCodCatastale());
        String address = configurationService.getCachedPluginConfiguration(AvBariConstants.CATASTO_ENDPOINT, idEnte, null);

        URL wsdlURL = Catasto.class.getResource("Catasto.svc.wsdl");
        Catasto ss = new Catasto(wsdlURL, SERVICE);

        ICatasto port = ss.getBasicHttpBindingICatasto();
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

    private List<DatiCatastaliDTO> CaricaDTO(PrecompilazioneBean risposta) {
        List<DatiCatastaliDTO> lista = new ArrayList<DatiCatastaliDTO>();
        Output valori = risposta.getOutput();
        if (valori != null && valori.getCampoPrecompilazioneBean() != null && !valori.getCampoPrecompilazioneBean().isEmpty()) {
            List<Output.CampoPrecompilazioneBean> campi = valori.getCampoPrecompilazioneBean();
            //Indica quanti sono i risultati che mi ha dato la query
            int total = campi.get(0).getDescrizione().size();
            for (int i = 0; i < total; i++) {
                DatiCatastaliDTO dto = new DatiCatastaliDTO();
                for (Output.CampoPrecompilazioneBean campo : campi) {
                    if (campo.getCodice().equals("TIPO_IMMOBILE")) {
                        String value = campo.getDescrizione().get(i);
                        LkTipoUnita tu = lookupDao.findTipoUnitaByCod(value);
                        if (tu != null) {
                            dto.setIdTipoUnita(tu.getIdTipoUnita());
                            dto.setDesTipoUnita(tu.getDescrizione());
                            dto.setCodTipoUnita(tu.getCodTipoUnita());
                        }
                    } else if (campo.getCodice().equals("SEZIONE")) {
                        String value = campo.getDescrizione().get(i);
                        dto.setSezione(value);
                    } else if (campo.getCodice().equals("FOGLIO")) {
                        String value = campo.getDescrizione().get(i);
                        dto.setFoglio(value);
                    } else if (campo.getCodice().equals("PARTICELLA")) {
                        String value = campo.getDescrizione().get(i);
                        dto.setMappale(value);
                    } else if (campo.getCodice().equals("SUBALTERNO")) {
                        String value = campo.getDescrizione().get(i);
                        dto.setSubalterno(value);
                    } else if (campo.getCodice().equals("ID")) {
                        String value = campo.getDescrizione().get(i);
                        dto.setCodImmobile(value);
                    }
                }
                dto.setIdTipoSistemaCatastale(1);
                dto.setDesSistemaCatastale("ORDINARIO");
                lista.add(dto);
            }
        }
        return lista;
    }

}
