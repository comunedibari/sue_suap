/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.plugins;

import it.wego.cross.dto.dozer.DatiCatastaliDTO;
import it.wego.cross.dto.dozer.IndirizzoInterventoDTO;
import it.wego.cross.entity.Procedimenti;
import it.wego.cross.plugins.pratica.GestionePratica;
import it.wego.cross.xml.Allegati;
import it.wego.cross.xml.Pratica;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gabriele
 */
public class GestionePraticaFvg implements GestionePratica {

    @Override
    public Pratica execute(Object pratica) throws Exception {
        return null;
    }

    @Override
    public String getOggettoPratica(Object pratica) throws Exception {
        return null;
    }

    @Override
    public String getIdentificativoPratica(Object pratica) throws Exception {
        return null;
    }

    @Override
    public Allegati getAllegati(Object pratica) throws Exception {
        return null;
    }

    @Override
    public Procedimenti getProcedimentoRiferimento(List<Procedimenti> procedimenti) {
        return procedimenti == null ? null : procedimenti.get(0);
    }

    @Override
    public String notifica(it.wego.cross.entity.Pratica pratica, String descrizioneEvento) throws Exception {
        return null;
    }

    @Override
    public String getUrlCatasto(Object dato, it.wego.cross.entity.Pratica pratica) throws Exception {
        return null;
    }

    @Override
    public String getUrlCatastoIndirizzo(Object dato, it.wego.cross.entity.Pratica pratica) throws Exception {
        return null;
    }

    @Override
    public List<IndirizzoInterventoDTO> getDatiToponomastica(IndirizzoInterventoDTO indirizzoInterventoDTO, it.wego.cross.entity.Pratica pratica) throws Exception {
        return new ArrayList<IndirizzoInterventoDTO>();
    }

    @Override
    public List<DatiCatastaliDTO> getDatiCatastali(DatiCatastaliDTO datiCatastaliDTO, it.wego.cross.entity.Pratica pratica) throws Exception {
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
    public String getEsistenzaStradario(it.wego.cross.entity.Pratica pratica) throws Exception {
        return null;
    }

    @Override
    public String getEsistenzaRicercaCatasto(it.wego.cross.entity.Pratica pratica) throws Exception {
        return null;
    }

    @Override
    public void postCreazionePratica(it.wego.cross.entity.Pratica pratica, String data) throws Exception {

    }
}
