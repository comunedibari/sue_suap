package it.wego.cross.action;

import it.wego.cross.constants.DemoConstans;
import it.wego.cross.dao.ConfigurationDao;
import it.wego.cross.dao.UsefulDao;
import it.wego.cross.entity.Allegati;
import it.wego.cross.entity.Configuration;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.plugins.commons.beans.Allegato;
import it.wego.cross.service.ConfigurationService;
import it.wego.cross.utils.Utils;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author CS
 */
@Component
public class ProtocolloDemoAction {

    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private ConfigurationDao configurationDao;
    @Autowired
    private UsefulDao usefulDao;

    public String getNumeroProtocollo(Enti ente, LkComuni comune) throws Exception {
        String config = configurationDao.getConfigurationValue(DemoConstans.NUMERO_PROTOCOLLO, null, null);
        Integer protocollo = Integer.parseInt(config) + 1;
        aggiorna(DemoConstans.NUMERO_PROTOCOLLO, String.valueOf(protocollo), ente, comune);
        return String.valueOf(protocollo);
    }

    public String getNumeroFascicolo(Enti ente, LkComuni comune) throws Exception {
        String config = configurationDao.getConfigurationValue(DemoConstans.NUMERO_FASCICOLO, null, null);
        Integer protocollo = Integer.parseInt(config) + 1;
        aggiorna(DemoConstans.NUMERO_FASCICOLO, String.valueOf(protocollo), ente, comune);
        return String.valueOf(protocollo);
    }

    public String salvaAllegato(Allegato allegato, Enti ente, LkComuni comune) throws Exception {
        return salvaAllegato(allegato.getFile(), ente, comune);
    }

    public String salvaAllegato(Allegati allegato, Enti ente, LkComuni comune) throws Exception {
        if (!Utils.e(allegato.getFile())) {
            return salvaAllegato(allegato.getFile(), ente, comune);
        } else if (!Utils.e(allegato.getPathFile())) {
            File f = new File(allegato.getPathFile());
            InputStream stream = new FileInputStream(f);
            return salvaAllegato(IOUtils.toByteArray(stream), ente, comune);
        } else {
            return null;
        }
    }

    private String salvaAllegato(byte[] content, Enti ente, LkComuni comune) throws Exception {
        String idfileEstermo;
        String config = configurationService.getCachedConfiguration(DemoConstans.ATTACHMENT_FOLDER, ente.getIdEnte(), comune.getIdComune());
        if (config == null) {
            throw new Exception("settare nella configurazione il parametro " + DemoConstans.ATTACHMENT_FOLDER);
        }
        String path = config;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        SimpleDateFormat ft = new SimpleDateFormat("yddMMSSSS");
        idfileEstermo = ft.format(new Date());
        file = new File(path + System.getProperty("file.separator") + idfileEstermo);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream out = new FileOutputStream(file);
        out.write(content);
        out.close();
        return idfileEstermo;
    }

    public byte[] getAllegato(String id, Enti ente, LkComuni comune) throws Exception {
        String config = configurationService.getCachedConfiguration(DemoConstans.ATTACHMENT_FOLDER, ente.getIdEnte(), comune.getIdComune());
        if (config == null) {
            throw new Exception("settare nella configurazione il parametro " + DemoConstans.ATTACHMENT_FOLDER);
        }
        String path = config + System.getProperty("file.separator") + id.trim();
        File file = new File(path);
        if (!file.exists()) {
            return null;
        }

        FileInputStream in = new FileInputStream(file);
        java.nio.channels.FileChannel fc = in.getChannel();
        byte[] b;
        b = new byte[(int) fc.size()];
        in.read(b);
        return b;
    }

    public void aggiorna(String key, String value, Enti ente, LkComuni comune) throws Exception {
        Configuration configuration = configurationDao.getConfiguration(key, null, null);
        if (configuration.getValue() != null && !"".equals(configuration.getValue())) {
            configuration.setValue(value);
            usefulDao.update(configuration);
        }
    }

}
