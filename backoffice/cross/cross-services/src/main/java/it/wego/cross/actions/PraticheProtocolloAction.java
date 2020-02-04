/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.actions;

import it.wego.cross.constants.Constants;
import it.wego.cross.dao.PraticaDao;
import it.wego.cross.dao.UtentiDao;
import it.wego.cross.dto.UtenteDTO;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.entity.PraticheProtocollo;
import it.wego.cross.entity.Utente;
import it.wego.cross.plugins.protocollo.GestioneProtocollo;
import it.wego.cross.plugins.protocollo.beans.DocumentoProtocolloResponse;
import it.wego.cross.serializer.PraticheSerializer;
import it.wego.cross.service.EntiService;
import it.wego.cross.service.PluginService;
import it.wego.cross.service.PraticheProtocolloService;
import it.wego.cross.service.PraticheService;
import it.wego.cross.service.UsefulService;
import java.util.Date;
import javax.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author GabrieleM
 */
@Component
public class PraticheProtocolloAction {

    @Autowired
    private PraticheProtocolloService praticheProtocolloService;
    @Autowired
    private PraticaDao praticaDao;
    @Autowired
    private PraticheService praticheService;
    @Autowired
    private UsefulService usefulService;
    @Autowired
    private PluginService pluginService;
    @Autowired
    private UtentiDao utentiDao;
    @Autowired
    private EntiService entiService;
    @Autowired
    private PraticheSerializer praticheSerializer;

    @Nullable
    @Transactional
    public PraticheProtocollo createPraticaDaProtocollo(Integer anno, String protocollo, Integer idEnte) throws Exception {
        //idEnte è sicuramente valorizzato
        PraticheProtocollo praticaProtocollo = praticheProtocolloService.findPraticaProtocolloByAnnoProtocollo(anno, protocollo);
        if (praticaProtocollo == null) {
            it.wego.cross.entity.Pratica p = praticheService.findByAnnoProtocollo(anno, protocollo);
            if (p != null) {
                praticaProtocollo = new PraticheProtocollo();
                praticaProtocollo.setIdPratica(p);
            } else {
                Enti ente = entiService.findByIdEnte(idEnte);
                //TODO: passare anche LkComune?
                GestioneProtocollo gp = pluginService.getGestioneProtocollo(idEnte, null);
                DocumentoProtocolloResponse docPratica = gp.findByProtocollo(anno, protocollo, ente, null);
                if (docPratica == null) {
                    return null;
                }
                praticaProtocollo = praticheSerializer.serializzaPratica(docPratica, Constants.CARICAMENTO_MODALITA_DANUMERO, idEnte);
                praticaDao.insert(praticaProtocollo);
                usefulService.flush();
            }
        }
        return praticaProtocollo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Deprecated
    public void aggiornaProtocolloEvento(PraticheEventi evento, String numeroDiProtocollo, Date dataProtocollazione) throws Exception {
        evento.setProtocollo(numeroDiProtocollo);
        evento.setDataProtocollo(dataProtocollazione);
        praticaDao.update(evento);
    }

    @Transactional(rollbackFor = Exception.class)
    @Deprecated
    public void aggiornaPraticaProtocollo(PraticheProtocollo praticaProtocollo, Pratica pratica, Utente utente) throws Exception {
        praticaProtocollo.setIdPratica(pratica);
        praticaProtocollo.setDataPresaInCaricoCross(new Date());
        praticaProtocollo.setIdUtentePresaInCarico(utente);
        praticheProtocolloService.aggiorna(praticaProtocollo);
    }
}
