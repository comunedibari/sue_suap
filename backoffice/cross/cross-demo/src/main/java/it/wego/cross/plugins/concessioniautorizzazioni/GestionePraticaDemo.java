package it.wego.cross.plugins.concessioniautorizzazioni;

import it.wego.cross.dto.dozer.DatiCatastaliDTO;
import it.wego.cross.dto.dozer.IndirizzoInterventoDTO;
import it.wego.cross.entity.DatiCatastali;
import it.wego.cross.entity.IndirizziIntervento;
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

public class GestionePraticaDemo extends AeCGestionePratica {

    @Autowired
    private ProcedimentiService procedimentiService;
    @Autowired
    private MessageSource messageSource;

    @Override
    public Procedimenti getProcedimentoRiferimento(List<Procedimenti> procedimenti) {
        if (procedimenti != null && procedimenti.size() > 0) {
            Procedimenti procedimentoRiferimento = null;
            int counter = 0;
            for (Procedimenti procedimento : procedimenti) {
                //Se è edilizia esco subito dando priorità all'ordinario
                counter++;
                if (procedimento.getTipoProc().equals("EO")) {
                    procedimentoRiferimento = procedimentiService.findProcedimentoByCodProc("EDILIZIA_ORDINARIO");
                    break;
                } else if (procedimento.getTipoProc().equals("EA")) {
                    procedimentoRiferimento = procedimentiService.findProcedimentoByCodProc("EDILIZIA_AUTOMATICO");
                } else {
                    //Verifico se è commercio.
                    //Do la priorità al commercio ordinario ma solo se non c'è una edilizia
                    if (procedimento.getTipoProc().equals("CO")) {
                        //Se non c'è edilizia automatizzato, allora metto commercio ordinario
                        //Altrimenti verifico se  non ho procedimenti di riferimento.
                        if (procedimentoRiferimento == null || (!procedimentoRiferimento.getTipoProc().equals("EA"))) {
                            procedimentoRiferimento = procedimentiService.findProcedimentoByCodProc("COMMERCIO_ORDINARIO");
                        }
                    } else if (procedimento.getTipoProc().equals("CA")) {
                        //Se è commercio automatico, verifico se era già stato individuato un procedimento di riferimento
                        //ed in tal caso lo aggiorno
                        if (procedimentoRiferimento != null) {
                            //Ho commercio automatizzato se non ho un commecio ordinario o non ho un edilizia automatizzata
                            if (!procedimentoRiferimento.getTipoProc().equals("CO") && !procedimentoRiferimento.getTipoProc().equals("EA")) {
                                procedimentoRiferimento = procedimentiService.findProcedimentoByCodProc("COMMERCIO_AUTOMATIZZATO");
                            }
                        } else {
                            //se non ho procedimenti di riferimento, allora setti automatizzato
                            procedimentoRiferimento = procedimentiService.findProcedimentoByCodProc("COMMERCIO_AUTOMATIZZATO");
                        }
                    } else {
                        //Ho un procedimento delle telecomunicazioni ordinario se non ho in lista nessun procedimento dell'edilizia o del commercio
                        //Ho un TLC ordinario se procedimento riferimento è null o non è edilizia o commercio
                        if (procedimento.getTipoProc().equals("SC")
                                && (procedimentoRiferimento != null
                                && !procedimentoRiferimento.getTipoProc().startsWith("E")
                                && !procedimentoRiferimento.getTipoProc().startsWith("C")) || procedimentoRiferimento == null) {
                            procedimentoRiferimento = procedimentiService.findProcedimentoByCodProc("SERVIZI_CITTADINI");
                        } else {
                            //Mantengo quello che ho già selezionato
                            if (counter == procedimenti.size()) {
                                //Se non ho proprio nessun procedimento, il default è COMMERCIO AUTOMATIZZATO
                                procedimentoRiferimento = procedimentiService.findProcedimentoByCodProc("COMMERCIO_AUTOMATIZZATO");
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
        return null;
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
