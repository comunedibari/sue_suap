package it.wego.cross.plugins.documenti;

import it.wego.cross.entity.Allegati;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.entity.Pratica;
import it.wego.cross.plugins.commons.beans.Allegato;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class GestioneAllegatiDB implements it.wego.cross.plugins.documenti.GestioneAllegati {
   
    @Override
    public void add(Allegati allegato, Enti ente, LkComuni comune) throws Exception {
    }

    @Override
    public byte[] getFileContent(Allegati allegato, Enti ente, LkComuni comun) throws Exception {
        return allegato.getFile();
    }

    @Override
    public Allegato getFile(String idFileEsterno, Enti ente, LkComuni comune) throws Exception {
        //NOT SUPPORTED
        return null;
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
}
