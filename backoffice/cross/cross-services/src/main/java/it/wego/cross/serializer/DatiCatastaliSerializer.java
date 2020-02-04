/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.serializer;

import it.wego.cross.dao.LookupDao;
import it.wego.cross.dto.dozer.DatiCatastaliDTO;
import it.wego.cross.dto.dozer.IndirizzoInterventoDTO;
import it.wego.cross.entity.*;
import it.wego.cross.plugins.pratica.GestionePratica;
import it.wego.cross.service.PluginService;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.Utils;
import java.util.List;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Gabriele
 */
@Component
public class DatiCatastaliSerializer {

    @Autowired
    private Mapper mapper;
    @Autowired
    private LookupDao lookupDao;
    @Autowired
    private PluginService pluginService;

    public it.wego.cross.xml.DatiCatastali serialize(Pratica pratica) {
        List<it.wego.cross.entity.DatiCatastali> datiCatastaliList = pratica.getDatiCatastaliList();
        it.wego.cross.xml.DatiCatastali datiCatastaliXml = new it.wego.cross.xml.DatiCatastali();
        it.wego.cross.xml.Immobile immobile;
        for (it.wego.cross.entity.DatiCatastali datiCatastali : datiCatastaliList) {
            immobile = new it.wego.cross.xml.Immobile();
            mapper.map(datiCatastali, immobile);
            immobile.setIdImmobile(Utils.bi(datiCatastali.getIdImmobile()));

            if (datiCatastali.getIdTipoSistemaCatastale() != null) {
                immobile.setIdTipoSistemaCatastale(Utils.bi(datiCatastali.getIdTipoSistemaCatastale().getIdTipoSistemaCatastale()));
                immobile.setDesTipoSistemaCatastale(datiCatastali.getIdTipoSistemaCatastale().getDescrizione());
            }
            if (datiCatastali.getIdTipoUnita() != null) {
                immobile.setIdTipoUnita(Utils.bi(datiCatastali.getIdTipoUnita().getIdTipoUnita()));
                immobile.setDesTipoUnita(datiCatastali.getIdTipoUnita().getDescrizione());
            }
            if (datiCatastali.getIdTipoParticella() != null) {
                immobile.setIdTipoParticella(Utils.bi(datiCatastali.getIdTipoParticella().getIdTipoParticella()));
                immobile.setDesTipoParticella(datiCatastali.getIdTipoParticella().getDescrizione());
            }
            immobile.setComuneCensuario(datiCatastali.getComuneCensuario());
            datiCatastaliXml.getImmobile().add(immobile);
        }
        return datiCatastaliXml;
    }

    public void serialize(it.wego.cross.xml.Immobile immobile, it.wego.cross.entity.DatiCatastali immobileEntity) {
        mapper.map(immobile, immobileEntity);
    }

    public void serialize(DatiCatastaliDTO dto, it.wego.cross.entity.DatiCatastali db) {
        mapper.map(dto, db);
    }

    public void serialize(it.wego.cross.entity.DatiCatastali db, DatiCatastaliDTO dto) {
        mapper.map(db, dto);
    }

    public void serialize(it.wego.cross.entity.Pratica praticaEnitity, it.wego.cross.xml.Immobile xml, DatiCatastaliDTO dto) throws Exception {
        mapper.map(xml, dto);
        if (xml.getIdTipoSistemaCatastale() != null) {
            LkTipoSistemaCatastale lk = lookupDao.findIdTipoCatastaleById(Utils.ib(xml.getIdTipoSistemaCatastale()));
            if (lk != null) {
                dto.setIdTipoSistemaCatastale(lk.getIdTipoSistemaCatastale());
                dto.setDesSistemaCatastale(lk.getDescrizione());
            }
        }
        if (xml.getIdTipoUnita() != null) {
            LkTipoUnita lk = lookupDao.findTipoUnitaById(Utils.ib(xml.getIdTipoUnita()));
            if (lk != null) {
                dto.setIdTipoUnita(lk.getIdTipoUnita());
                dto.setDesTipoUnita(lk.getDescrizione());
            }
        }
        if (xml.getIdTipoParticella() != null) {
            LkTipoParticella lk = lookupDao.findTipoParticellaById(Utils.ib(xml.getIdTipoParticella()));
            if (lk != null) {
                dto.setIdTipoParticella(lk.getIdTipoParticella());
                dto.setDesTipoParticella(lk.getDescrizione());
            }
        }
        GestionePratica gp = pluginService.getGestionePratica(praticaEnitity.getIdProcEnte().getIdEnte().getIdEnte());
        dto.setUrlCatasto(gp.getUrlCatasto(xml, praticaEnitity));

    }

    public void serialize(it.wego.cross.entity.DatiCatastali datiEntity, it.wego.cross.xml.Immobile xml) {
        Log.APP.info("Salvo il dato catastale");
        Log.APP.info("Id immobile: " + datiEntity.getIdImmobile());
        xml.setIdImmobile(Utils.bi(datiEntity.getIdImmobile()));

        xml.setComuneCensuario(datiEntity.getComuneCensuario());

        if (datiEntity.getIdTipoParticella() != null) {
            xml.setDesTipoParticella(datiEntity.getIdTipoParticella().getDescrizione());
            xml.setIdTipoParticella(Utils.bi(datiEntity.getIdTipoParticella().getIdTipoParticella()));
        }

        if (datiEntity.getIdTipoSistemaCatastale() != null) {
            xml.setDesTipoSistemaCatastale(datiEntity.getIdTipoSistemaCatastale().getDescrizione());
            xml.setIdTipoSistemaCatastale(Utils.bi(datiEntity.getIdTipoSistemaCatastale().getIdTipoSistemaCatastale()));
        }
        if (datiEntity.getIdTipoUnita() != null) {
            xml.setIdTipoUnita(Utils.bi(datiEntity.getIdTipoUnita().getIdTipoUnita()));
        }
        xml.setEstensioneParticella(datiEntity.getEstensioneParticella());
        xml.setFoglio(datiEntity.getFoglio());
        xml.setMappale(datiEntity.getMappale());
        xml.setSezione(datiEntity.getSezione());
        xml.setSubalterno(datiEntity.getSubalterno());
        xml.setCodImmobile(datiEntity.getCodImmobile());
        //Aggiunto il 22/06/2016
        xml.setCategoria(datiEntity.getCategoria());
    }

    public DatiCatastali copy(DatiCatastali dato, Pratica pratica) {
        DatiCatastali r = new DatiCatastali();
        r.setIdPratica(pratica);
        r.setIdTipoSistemaCatastale(dato.getIdTipoSistemaCatastale());
        r.setSezione(dato.getSezione());
        r.setIdTipoUnita(dato.getIdTipoUnita());
        r.setFoglio(dato.getFoglio());
        r.setMappale(dato.getMappale());
        r.setIdTipoParticella(dato.getIdTipoParticella());
        r.setEstensioneParticella(dato.getEstensioneParticella());
        r.setSubalterno(dato.getSubalterno());
        r.setComuneCensuario(dato.getComuneCensuario());
        return r;
    }

    public void serialize(it.wego.cross.entity.Pratica praticaEnitity, it.wego.cross.xml.IndirizzoIntervento xml, IndirizzoInterventoDTO dto) throws Exception {
        mapper.map(xml, dto);
        if (xml.getIdDug() != null) {
            LkDug lk = lookupDao.findDugById(Utils.ib(xml.getIdDug()));
            if (lk != null) {
                dto.setIdDug(lk.getIdDug());
                dto.setDesDug(lk.getDescrizione());
            }
        }
        GestionePratica gp = pluginService.getGestionePratica(praticaEnitity.getIdProcEnte().getIdEnte().getIdEnte());
        dto.setUrlCatasto(gp.getUrlCatastoIndirizzo(xml, praticaEnitity));
    }
}
