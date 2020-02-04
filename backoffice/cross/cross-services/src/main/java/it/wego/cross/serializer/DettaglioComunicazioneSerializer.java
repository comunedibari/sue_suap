/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.serializer;

import it.wego.cross.dto.dozer.IndirizzoInterventoDTO;
import it.wego.cross.dto.ProcedimentoDTO;
import it.wego.cross.dto.comunicazione.AnagraficaMinifiedDTO;
import it.wego.cross.dto.dozer.DatiCatastaliDTO;
import it.wego.cross.entity.Anagrafica;
import it.wego.cross.entity.DatiCatastali;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.IndirizziIntervento;
import it.wego.cross.entity.Procedimenti;
import it.wego.cross.entity.ProcedimentiTesti;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.Utils;
import java.math.BigInteger;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author giuseppe
 */
@Component
public class DettaglioComunicazioneSerializer {

    @Autowired
    private Mapper mapper;

    public AnagraficaMinifiedDTO serializeAnagrafica(Anagrafica anagrafica) {
        AnagraficaMinifiedDTO a = new AnagraficaMinifiedDTO();
        if (!Utils.e(anagrafica.getNome())) {
            a.setNome(anagrafica.getNome());
        }
        if (!Utils.e(anagrafica.getCognome())) {
            a.setCognome(anagrafica.getCognome());
        }
        if (!Utils.e(anagrafica.getCodiceFiscale())) {
            a.setCodiceFiscale(anagrafica.getCodiceFiscale());
        }
        if (!Utils.e(anagrafica.getDenominazione())) {
            a.setRagioneSociale(anagrafica.getDenominazione());
        }
        if (!Utils.e(anagrafica.getPartitaIva())) {
            a.setPartitaIVA(anagrafica.getPartitaIva());
        }
        a.setTipoAnagrafica(Utils.Char(anagrafica.getTipoAnagrafica()));
        a.setVarianteAnagrafica(anagrafica.getVarianteAnagrafica());
        a.setIdAnagrafica(anagrafica.getIdAnagrafica());
        return a;
    }

    public ProcedimentoDTO serializeProcedimento(Procedimenti procedimento, Enti ente) {
        ProcedimentoDTO p = new ProcedimentoDTO();
        p.setCodProcedimento(procedimento.getCodProc());
        for (ProcedimentiTesti testi : procedimento.getProcedimentiTestiList()) {
            if (testi.getLingue().getCodLang().equalsIgnoreCase("it")) {
                p.setDesProcedimento(testi.getDesProc());
                break;
            }
        }
        p.setDesEnteDestinatario(ente.getDescrizione());
        p.setIdEnteDestinatario(ente.getIdEnte());
        p.setIdProcedimento(procedimento.getTermini());
        p.setTermini(procedimento.getTermini());
        return p;
    }

    public DatiCatastaliDTO serializeDatoCatastale(DatiCatastali c) {
        DatiCatastaliDTO d = mapper.map(c, DatiCatastaliDTO.class);
//        Log.APP.info("Comune " + c.getComuneCensuario());
        d.setComuneCensuario(c.getComuneCensuario());
        if (c.getIdTipoSistemaCatastale() != null) {
//            Log.APP.info("TipoSistemaCatastale " + c.getIdTipoSistemaCatastale().getDescrizione());
            d.setDesSistemaCatastale(c.getIdTipoSistemaCatastale().getDescrizione());
            d.setIdTipoSistemaCatastale(c.getIdTipoSistemaCatastale().getIdTipoSistemaCatastale());
        }
        if (c.getIdTipoParticella() != null) {
//            Log.APP.info("TipoParticella " + c.getIdTipoParticella().getDescrizione());
            d.setDesTipoParticella(c.getIdTipoParticella().getDescrizione());
            d.setIdTipoParticella(c.getIdTipoParticella().getIdTipoParticella());
        }
        if (c.getIdTipoUnita() != null) {
//            Log.APP.info("TipoUnita " + c.getIdTipoUnita().getDescrizione());
            d.setDesTipoUnita(c.getIdTipoUnita().getDescrizione());
            d.setIdTipoUnita(c.getIdTipoUnita().getIdTipoUnita());
        }
        if (c.getIdPratica() != null) {
//            Log.APP.info("Id Pratica " + c.getIdPratica().getIdPratica());
            d.setIdPratica(c.getIdPratica().getIdPratica());
        }
        return d;
    }

    public IndirizzoInterventoDTO serializeIndirizziIntervento(IndirizziIntervento c) {
        IndirizzoInterventoDTO d = mapper.map(c, IndirizzoInterventoDTO.class);
        if (c.getIdPratica() != null) {
            Log.APP.info("Id Pratica " + c.getIdPratica().getIdPratica());
            d.setIdPratica(c.getIdPratica().getIdPratica());
        }
        return d;
    }
}
