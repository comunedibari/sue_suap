package it.wego.cross.plugins.concessioniautorizzazioni;

import it.wego.cross.dto.dozer.DatiCatastaliDTO;
import it.wego.cross.dto.dozer.IndirizzoInterventoDTO;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.Procedimenti;
import it.wego.cross.plugins.aec.AeCGestionePratica;
import it.wego.cross.service.ProcedimentiService;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.apache.cxf.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

public class GestionePraticaDatasiel extends AeCGestionePratica {

    @Autowired
    private ProcedimentiService procedimentiService;
    @Autowired
    private MessageSource messageSource;

    @Override
    public Procedimenti getProcedimentoRiferimento(List<Procedimenti> procedimenti) {
        if (procedimenti != null && procedimenti.size() > 0) {
            Procedimenti procedimentoRiferimento = null;
//            int counter = 0;
            int minProc = 10;
            for (Procedimenti procedimento : procedimenti) {
                if (procedimento.getTipoProc() != null) {
                    if (Integer.valueOf(procedimento.getTipoProc()).intValue() == 0) {
                        procedimentoRiferimento = procedimentiService.findProcedimentoByCodProc("ORDINARIO");
                        break;
                    } else {
                        if (Integer.valueOf(procedimento.getTipoProc()).intValue() < minProc) {
                            minProc = Integer.valueOf(procedimento.getTipoProc()).intValue();
                            if (minProc == 1) {
                                procedimentoRiferimento = procedimentiService.findProcedimentoByCodProc("SCIA");
                            } else if (minProc == 2) {
                                procedimentoRiferimento = procedimentiService.findProcedimentoByCodProc("COMUNICAZDIA");
                            }
                        }
                    }
                }
            }
            return procedimentoRiferimento;
        } else {
            return null;
        }
    }

    @Override
    public String getUrlCatasto(Object dato, Pratica pratica) throws Exception {
        return null;
    }

    @Override
    public String getUrlCatastoIndirizzo(Object dato, Pratica pratica) throws Exception {
        return null;
    }

    @Override
    public List<IndirizzoInterventoDTO> getDatiToponomastica(IndirizzoInterventoDTO indirizzoInterventoDTO, Pratica pratica) throws Exception {
        return null;
    }

    @Override
    public List<DatiCatastaliDTO> getDatiCatastali(DatiCatastaliDTO datiCatastaliDTO, Pratica pratica) throws Exception {
        return null;
    }

    @Override
    public List<String> controllaDatoCatastale(Object datoInput, Integer idEnte) throws Exception {
        return null;
    }

    @Override
    public List<String> controllaIndirizzoIntervento(Object datoInput, Integer idEnte) throws Exception {
        List<String> errori = new ArrayList<String>();
        if (datoInput instanceof it.wego.cross.xml.IndirizzoIntervento) {
            it.wego.cross.xml.IndirizzoIntervento indirizzo = (it.wego.cross.xml.IndirizzoIntervento) datoInput;
            if (StringUtils.isEmpty(indirizzo.getLongitudine())) {
                errori.add(messageSource.getMessage("error.datiCatastali.indirizzo", null, Locale.getDefault()));
            }
        }
        if (datoInput instanceof it.wego.cross.dto.dozer.IndirizzoInterventoDTO) {
            it.wego.cross.dto.dozer.IndirizzoInterventoDTO indirizzo = (it.wego.cross.dto.dozer.IndirizzoInterventoDTO) datoInput;
            if (StringUtils.isEmpty(indirizzo.getLongitudine())) {
                errori.add(messageSource.getMessage("error.datiCatastali.indirizzo", null, Locale.getDefault()));
            }
        }
        if (errori.isEmpty()) {
            return null;
        } else {
            return errori;
        }

    }

    @Override
    public String getEsistenzaStradario(Pratica pratica) throws Exception {
        return null;
    }

    @Override
    public String getEsistenzaRicercaCatasto(Pratica pratica) throws Exception {
        return null;
    }

    @Override
    public void postCreazionePratica(it.wego.cross.entity.Pratica pratica, String data) throws Exception {

    }
}
