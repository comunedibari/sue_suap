package it.wego.cross.plugins.documenti;

import it.wego.cross.entity.Allegati;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.entity.Pratica;
import it.wego.cross.plugins.commons.beans.Allegato;
import it.wego.cross.service.ConfigurationService;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.Utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class GestioneAllegatiFS implements it.wego.cross.plugins.documenti.GestioneAllegati {

    private static final String FOLDER_KEY = "documenti.fs.folder";
    private static String folder;
    @Autowired
    private ConfigurationService configurationService;
    private static final Logger logger = LoggerFactory.getLogger(GestioneAllegatiFS.class.getName());
    @Override
    public void add(Allegati allegato, Enti ente, LkComuni comune) throws Exception {
        if (StringUtils.isEmpty(allegato.getPathFile())) {
            folder = configurationService.getCachedConfiguration(GestioneAllegatiFS.FOLDER_KEY, ente.getIdEnte(), comune.getIdComune());
            if (Utils.e(folder)) {
                throw new Exception("Sulla tabella di configurazione non è stata valorizzata la chiave '" + FOLDER_KEY + "'");
            }
            File folderPath = new File(folder);
            if( !folderPath.exists() )
            {
            	boolean directoryCreate = folderPath.mkdirs();
            	if(logger.isInfoEnabled() )
            	{
            		logger.info("DIRECTORY {} NON ESISTENTE. CREATA CON SUCCESSO ? ", folder, directoryCreate);
            	}
            }
            String pathFile = folder + File.separator + UUID.randomUUID() + "_" + allegato.getNomeFile();
            FileOutputStream fileOuputStream = new FileOutputStream(pathFile);
            fileOuputStream.write(allegato.getFile());
            fileOuputStream.close();

            allegato.setPathFile(pathFile);
            allegato.setFile(null);
        }
    }

    @Override
    public byte[] getFileContent(Allegati allegato, Enti ente, LkComuni comune) throws Exception {
        File f = new File(allegato.getPathFile());
        InputStream stream = new FileInputStream(f);
        return IOUtils.toByteArray(stream);
    }

    @Override
    public Allegato getFile(String idFileEsterno, Enti ente, LkComuni comune) throws Exception {
        //NOT SUPPORTED
        return null;
    }

    public byte[] getFile(String filePath) throws Exception {
        File f = new File(filePath);
        InputStream stream = new FileInputStream(f);
        return IOUtils.toByteArray(stream);
    }

    @Override
    public String uploadFile(Pratica pratica, List<Allegati> allegati) throws Exception {
        //NOT SUPPORTED
        return null;
    }

    @Override
    public String uploadAttachmentsOnMyPage(Pratica pratica, List<Allegati> allegati) throws Exception {
        //NOT SUPPORTED
        return "";
    }

    public String createTempFile(String fileName, byte[] fileContent, Enti ente, LkComuni comune) throws Exception {
        Integer idEnte = ente != null ? ente.getIdEnte() : null;
        Integer idComune = comune != null ? comune.getIdComune() : null;

        folder = configurationService.getCachedConfiguration(GestioneAllegatiFS.FOLDER_KEY, idEnte, idComune);
        if (Utils.e(folder)) {
            throw new Exception("Sulla tabella di configurazione non è stata valorizzata la chiave '" + FOLDER_KEY + "'");
        }

        String pathFile = folder + File.separator + UUID.randomUUID() + "_" + fileName;

        FileOutputStream fileOuputStream = null;
        try {
            fileOuputStream = new FileOutputStream(pathFile);
            fileOuputStream.write(fileContent);
        } finally {
            if (fileOuputStream != null) {
                fileOuputStream.close();
            }
        }

        return pathFile;
    }
}
