package it.wego.cross.action;

import it.wego.cross.dao.UsefulDao;
import it.wego.cross.entity.Allegati;
import it.wego.cross.entity.Configuration;
import it.wego.cross.plugins.commons.beans.Allegato;
import it.wego.cross.service.ConfigurationService;
import it.wego.cross.utils.Utils;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author CS
 */
@Component
public class ProtocolloDatasielAction {
    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private UsefulDao usefulDao;

    
    public String getNumeroProtocollo() throws Exception {

        String config = get("protocollo.test.contaProtocollo");
        Integer protocollo = Integer.parseInt(config) + 1;
        aggiorna("protocollo.test.contaProtocollo", String.valueOf(protocollo));
        return String.valueOf(protocollo);
    }

    public String getNumeroFascicolo() throws Exception {

        String config = get("protocollo.test.contaFascicolo");
        Integer protocollo = Integer.parseInt(config) + 1;
        aggiorna("protocollo.test.contaFascicolo", String.valueOf(protocollo));
        return String.valueOf(protocollo);
    }

    public String salvaAllegato(Allegato allegato) throws Exception {
        return salvaAllegato(allegato.getFile());
    }

    public String salvaAllegato(Allegati allegato) throws Exception {
        if (!Utils.e(allegato.getFile())) {
            return salvaAllegato(allegato.getFile());
        } else if (!Utils.e(allegato.getPathFile())) {
            File f = new File(allegato.getPathFile());
            InputStream stream = new FileInputStream(f);
            return salvaAllegato(IOUtils.toByteArray(stream));
        } else {
            return null;
        }
    }

    private String salvaAllegato(byte[] content) throws Exception {
        String idfileEstermo;
        String config = get("protocollo.test.cartellaAllegati");
        if (config == null) {
            throw new Exception("settare protocollo.test.cartellaAllegati nella configurazione");
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

    public byte[] getAllegato(String id) throws Exception {
        String config = get("protocollo.test.cartellaAllegati");
        if (config == null) {
            throw new Exception("settare protocollo.test.cartellaAllegati nella configurazione");
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

    public String get(String key) throws Exception {
        List<Configuration> conf = configurationService.findByName(key);
        if (conf != null && conf.size() > 0) {
            for (Configuration c : conf) {
                if (c.getValue() != null && !"".equals(c.getValue())) {
                    return c.getValue();
                }
            }
        }
        return null;
    }

    public void aggiorna(String key, String value) throws Exception {
        List<Configuration> conf = configurationService.findByName(key);
        if (conf != null && conf.size() > 0) {
            for (Configuration c : conf) {
                if (c.getValue() != null && !"".equals(c.getValue())) {
                    c.setValue(value);
                    usefulDao.update(c);
                }
            }
        }
    }

}
