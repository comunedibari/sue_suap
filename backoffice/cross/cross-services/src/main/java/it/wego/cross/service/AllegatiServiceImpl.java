/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.service;

import it.wego.cross.dao.AllegatiDao;
import it.wego.cross.dto.AllegatoDTO;
import it.wego.cross.entity.Allegati;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.entity.Pratica;
import it.wego.cross.plugins.commons.beans.Allegato;
import it.wego.cross.plugins.documenti.GestioneAllegati;
import it.wego.cross.plugins.documenti.GestioneAllegatiDB;
import it.wego.cross.plugins.documenti.GestioneAllegatiFS;
import it.wego.cross.serializer.AllegatiSerializer;
import it.wego.cross.utils.Utils;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author giuseppe
 */
@Service
public class AllegatiServiceImpl implements AllegatiService {

    @Autowired
    private AllegatiDao allegatiDao;
    @Autowired
    private PluginService pluginService;
    @Autowired
    private EntiService entiService;
    private final Map<String, String> temporaryPathCodes = new HashMap<String, String>();
    @Autowired
    private GestioneAllegatiFS gestioneAllegatiFS;
    @Autowired
    private GestioneAllegatiDB gestioneAllegatiDB;

    @Override
    public String putFile(String path) {
        String code = UUID.randomUUID().toString();
        temporaryPathCodes.put(code, path);
        return code;
    }

    @Override
    public String getFile(String code) {
        return temporaryPathCodes.get(code);
    }

    @Override
    public void deleteFileMap() {
        temporaryPathCodes.clear();
    }

    @Override
    public AllegatoDTO findAllegatoByIdNullSafe(Integer idAllegato, Enti ente) throws Exception {
        Integer idEnte = ente != null ? ente.getIdEnte() : null;
        AllegatoDTO allegato;
        allegato = getAllegato(idAllegato, idEnte);
        return allegato;
    }

    @Override
    public AllegatoDTO getAllegato(Integer idAllegato, Integer idEnte) throws Exception {
        Enti ente = entiService.findByIdEnte(idEnte);
        Allegati allegato = allegatiDao.getAllegato(idAllegato);
        AllegatoDTO a = new AllegatoDTO();
        a.setDescrizione(allegato.getDescrizione());
        a.setIdAllegato(allegato.getId());
        a.setNomeFile(allegato.getNomeFile());
        a.setTipoFile(allegato.getTipoFile());

        if (allegato.getFile() != null) {
            a.setFileContent(allegato.getFile());
        } else if (!Utils.e(allegato.getPathFile())) {
            a.setFileContent(gestioneAllegatiFS.getFileContent(allegato, ente, null));
        } else {
            GestioneAllegati ga = pluginService.getGestioneAllegati(idEnte, null);
            a.setFileContent(ga.getFileContent(allegato, ente, null));
        }

        return a;
    }

    @Override
    public Allegati findAllegatoById(Integer idAllegato) throws Exception {
        Allegati allegato = allegatiDao.getAllegato(idAllegato);
        return allegato;
    }

    @Override
    public boolean checkAllegato(Pratica pratica, Integer idFile) {
        boolean isAllegatoPratica = allegatiDao.checkPraticaAllegato(pratica, idFile);
        return isAllegatoPratica;
    }

    @Override
    public void aggiornaAllegato(Allegati allegato) throws Exception {
        allegatiDao.mergeAllegato(allegato);
    }

    @Override
    public Allegati findAllegatoByIdFileEsterno(String idFileEsterno) throws Exception {
        Allegati allegato = allegatiDao.findByIdFileEsterno(idFileEsterno);
        return allegato;
    }

//    @Override
//    public void salvaAllegato(Allegati allegato) throws Exception {
//        allegatiDao.insertAllegato(allegato);
//    }
    @Override
    public Allegati salvaAllegatoFS(AllegatoDTO allegato, Enti ente, LkComuni comune) throws Exception {
        Integer idEnte = ente != null ? ente.getIdEnte() : null;
        Integer idComune = comune != null ? comune.getIdComune() : null;

        if (allegato.getFile() != null) {
            allegato.setFileContent(allegato.getFile().getBytes());
            allegato.setTipoFile(allegato.getFile().getContentType());
            allegato.setNomeFile(allegato.getFile().getOriginalFilename());
        }
        if (!Utils.e(allegato.getIdFileEsterno())) {
            GestioneAllegati gestioneAllegati = pluginService.getGestioneAllegati(idEnte, idComune);
            Allegato file = gestioneAllegati.getFile(allegato.getIdFileEsterno(), ente, comune);
            allegato.setFileContent(file.getFile());
        }

        Allegati a = AllegatiSerializer.serialize(allegato);
        if (Utils.e(allegato.getPathFile())) {
            gestioneAllegatiFS.add(a, ente, comune);
            allegato.setPathFile(a.getPathFile());
        }
        return a;
    }
    
    @Override
    public Allegati salvaAllegatoDB(AllegatoDTO allegato, Enti ente, LkComuni comune) throws Exception {
        Integer idEnte = ente != null ? ente.getIdEnte() : null;
        Integer idComune = comune != null ? comune.getIdComune() : null;

        if (allegato.getFile() != null) {
            allegato.setFileContent(allegato.getFile().getBytes());
            allegato.setTipoFile(allegato.getFile().getContentType());
            allegato.setNomeFile(allegato.getFile().getOriginalFilename());
        }
        if (!Utils.e(allegato.getIdFileEsterno())) {
            GestioneAllegati gestioneAllegati = pluginService.getGestioneAllegati(idEnte, idComune);
            Allegato file = gestioneAllegati.getFile(allegato.getIdFileEsterno(), ente, comune);
            allegato.setFileContent(file.getFile());
        }

        Allegati a = AllegatiSerializer.serialize(allegato);
        return a;
    }

    @Override
    public it.wego.cross.plugins.commons.beans.Allegato getAllegatoDaProtocollo(String idFile, Enti ente) throws Exception {
        GestioneAllegati gestioneAllegati;
        gestioneAllegati = pluginService.getGestioneAllegati(ente == null ? null : ente.getIdEnte(), null);

        //TODO: passare anche LkComune?
        it.wego.cross.plugins.commons.beans.Allegato allegatoProtocollo = gestioneAllegati.getFile(idFile, ente, null);
        return allegatoProtocollo;
    }
}
