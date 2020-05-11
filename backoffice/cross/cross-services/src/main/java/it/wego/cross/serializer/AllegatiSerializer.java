/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.serializer;

import com.google.common.base.Strings;

import it.gov.impresainungiorno.schema.suap.ente.AllegatoCooperazione;
import it.wego.cross.dto.AllegatoDTO;
import it.wego.cross.dto.AllegatoRicezioneDTO;
import it.wego.cross.entity.Allegati;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.entity.PraticheEventiAllegati;
import it.wego.cross.entity.view.PraticheAllegatiView;
import it.wego.cross.plugins.commons.beans.Allegato;
import it.wego.cross.utils.FileUtils;
import it.wego.cross.utils.Utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

/**
 *
 * @author Gabriele
 */
public class AllegatiSerializer {

    public static it.wego.cross.xml.Allegati serialize(Pratica pratica) {
        List<Allegati> allegatiTotali = new ArrayList<Allegati>();

        for (PraticheEventi praticaEvento : pratica.getPraticheEventiList()) {
            for (PraticheEventiAllegati pea : praticaEvento.getPraticheEventiAllegatiList()) {
                Allegati allegato = pea.getAllegati();
                if (!allegatiTotali.contains(allegato)) {
                    allegatiTotali.add(allegato);
                }
            }
        }

        it.wego.cross.xml.Allegati allegatiXml = new it.wego.cross.xml.Allegati();
        it.wego.cross.xml.Allegato allegatoXml;
        for (Allegati allegato : allegatiTotali) {
            allegatoXml = new it.wego.cross.xml.Allegato();
            allegatoXml.setIdAllegato(Utils.bi(allegato.getId()));
            allegatoXml.setDescrizione(allegato.getDescrizione());
            allegatoXml.setNomeFile(allegato.getNomeFile());
            allegatoXml.setTipoFile(allegato.getTipoFile());
            allegatoXml.setPathFile(allegato.getPathFile());
            allegatoXml.setIdFileEsterno(allegato.getIdFileEsterno());

            allegatiXml.getAllegato().add(allegatoXml);
        }
        return allegatiXml;
    }

    public static AllegatoRicezioneDTO serializeAllegatoRicezione(it.wego.cross.xml.Allegato allegato) {
        AllegatoRicezioneDTO allegatoRicezione = new AllegatoRicezioneDTO();
        Allegati a = new Allegati();
        a.setDescrizione(allegato.getDescrizione());
        a.setNomeFile(allegato.getNomeFile());
        if (allegato.getIdFileEsterno() != null) {
            a.setIdFileEsterno(allegato.getIdFileEsterno());
        }
        if (allegato.getPathFile() != null) {
            a.setPathFile(allegato.getPathFile());
        }
        a.setTipoFile(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        allegatoRicezione.setAllegato(a);
        allegatoRicezione.setModelloDomanda(allegato.getRiepilogoPratica());
        return allegatoRicezione;
    }

    public static AllegatoRicezioneDTO serializeAllegatoRicezione(Allegato allegato, boolean isRiepilogoPratica) {
        AllegatoRicezioneDTO allegatoRicezione = new AllegatoRicezioneDTO();
        Allegati a = new Allegati();
        a.setDescrizione(allegato.getDescrizione());
        a.setNomeFile(allegato.getNomeFile());
        a.setFile(allegato.getFile());
        a.setTipoFile(allegato.getTipoFile());
        allegatoRicezione.setAllegato(a);
        allegatoRicezione.setModelloDomanda(isRiepilogoPratica ? "S" : "N");
        return allegatoRicezione;
    }

    //^^CS AGGIUTNA
    public static AllegatoDTO serializeAllegatoXML(it.wego.cross.xml.Allegato allegato) throws Exception {
        AllegatoDTO a = new AllegatoDTO();
        if (allegato.getIdAllegato() != null) {
            a.setIdAllegato(allegato.getIdAllegato().intValue());
        }
        a.setDescrizione(allegato.getDescrizione());
        a.setNomeFile(allegato.getNomeFile());
        a.setNomeFileB64(Utils.encodeB64(allegato.getNomeFile()));
        if (allegato.getPathFile() != null) {
            a.setPathFile(allegato.getPathFile());
            a.setPathFileB64(Utils.encodeB64(allegato.getPathFile()));
        }
        a.setTipoFile(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        a.setRiepilogoPratica(allegato.getRiepilogoPratica());
        a.setModelloPratica(Utils.flagB(allegato.getRiepilogoPratica()));
        return a;
    }

    public static AllegatoDTO serialize(Allegati allegato) throws Exception {
        AllegatoDTO dto = new AllegatoDTO();
        dto.setDescrizione(allegato.getDescrizione());
        dto.setIdAllegato(allegato.getId());
        dto.setIdFileEsterno(allegato.getIdFileEsterno());
        dto.setNomeFile(allegato.getNomeFile());
        dto.setNomeFileB64(Utils.encodeB64(allegato.getNomeFile()));
        dto.setTipoFile(allegato.getTipoFile());
        dto.setPathFile(allegato.getPathFile());
        dto.setFile(new MockMultipartFile(allegato.getNomeFile(), allegato.getFile()));
        return dto;
    }

    public static Allegato serializeAllegato(Allegati a) throws Exception {
        Allegato allegato = new Allegato();
        allegato.setDescrizione(a.getDescrizione());
        byte[] fileSuDisco;
        if (!Strings.isNullOrEmpty(a.getPathFile())) {
            fileSuDisco = FileUtils.getFileContent(new File(a.getPathFile()));
        } else {
            fileSuDisco = a.getFile();
        }
        allegato.setId(a.getId());
        allegato.setIdEsterno(a.getIdFileEsterno());
        allegato.setFile(fileSuDisco);
        allegato.setNomeFile(a.getNomeFile());
        allegato.setProtocolla(Boolean.TRUE);

        return allegato;
    }

    public static Allegato serializeAllegato(AllegatoRicezioneDTO allegato) {
        Allegato a = new Allegato();
        a.setDescrizione(allegato.getAllegato().getDescrizione());
        a.setFile(allegato.getAllegato().getFile());
        a.setId(allegato.getAllegato().getId());
        a.setIdEsterno(allegato.getAllegato().getIdFileEsterno());
        a.setNomeFile(allegato.getAllegato().getNomeFile());
        a.setPathFile(allegato.getAllegato().getPathFile());
        a.setTipoFile(allegato.getAllegato().getTipoFile());
        return a;
    }

    public static Allegati serialize(AllegatoDTO allegato) {
        Allegati a = new Allegati();
        a.setDescrizione(allegato.getDescrizione());
        a.setFile(allegato.getFileContent());
        a.setId(allegato.getIdAllegato());
        a.setIdFileEsterno(allegato.getIdFileEsterno());
        a.setNomeFile(allegato.getNomeFile());
        a.setPathFile(allegato.getPathFile());
        a.setTipoFile(allegato.getTipoFile());
        return a;
    }

    public static it.wego.cross.xml.Allegato serializeXML(AllegatoDTO allegato) {
        it.wego.cross.xml.Allegato a = new it.wego.cross.xml.Allegato();
        a.setDescrizione(allegato.getDescrizione());
        //a.setFile(allegato.getFileContent());
        a.setIdAllegato(Utils.bi(allegato.getIdAllegato()));
        a.setIdFileEsterno(allegato.getIdFileEsterno());
        a.setNomeFile(allegato.getNomeFile());
        a.setPathFile(allegato.getPathFile());
        a.setTipoFile(allegato.getTipoFile());
        a.setRiepilogoPratica((allegato.getModelloPratica() == null || !allegato.getModelloPratica()) ? "N" : "S");
        return a;
    }

    public static it.wego.cross.xml.Allegato serializeXML(Allegati allegato) {
        it.wego.cross.xml.Allegato a = new it.wego.cross.xml.Allegato();
        a.setDescrizione(allegato.getDescrizione());
        a.setIdAllegato(Utils.bi(allegato.getId()));
        a.setIdFileEsterno(allegato.getIdFileEsterno());
        a.setNomeFile(allegato.getNomeFile());
        a.setPathFile(allegato.getPathFile());
        a.setTipoFile(allegato.getTipoFile());
//        a.setRiepilogoPratica(allegato.getTipoFile());
        return a;
    }

    public static Allegato serializePlugin(AllegatoDTO allegato) throws Exception {
        Allegato a = new Allegato();
        a.setDescrizione(allegato.getDescrizione());
        a.setId(allegato.getIdAllegato());
        a.setIdEsterno(allegato.getIdFileEsterno());
        a.setNomeFile(allegato.getNomeFile());
        a.setPathFile(allegato.getPathFile());
        a.setTipoFile(allegato.getTipoFile());
        a.setFile(allegato.getFile().getBytes());
        return a;
    }

    public static Allegati serialize(Allegato allegato) {
        Allegati a = new Allegati();
        a.setDescrizione(allegato.getDescrizione());
        a.setId(allegato.getId());
        a.setIdFileEsterno(allegato.getIdEsterno());
        a.setNomeFile(allegato.getNomeFile());
        a.setPathFile(allegato.getPathFile());
        a.setTipoFile(allegato.getTipoFile());
        a.setFile(allegato.getFile());
        return a;
    }

    public static AllegatoDTO serialize(PraticheAllegatiView paw) {
        AllegatoDTO dto = new AllegatoDTO();
        dto.setIdAllegato(paw.getId());
        dto.setIdPratica(paw.getIdPratica());
        dto.setDescrizione(paw.getDescrizione());
        dto.setNomeFile(paw.getNomeFile());
        dto.setTipoFile(paw.getTipoFile());
        dto.setPathFile(paw.getPathFile());
        dto.setIdFileEsterno(paw.getIdFileEsterno());
        dto.setFileContent(paw.getFile());
        return dto;
    }

	public static AllegatoCooperazione serializeAllegatoCooperazione(it.wego.cross.dto.dozer.AllegatoDTO allegato) throws IOException {
		AllegatoCooperazione ac = new AllegatoCooperazione();
		ac.setCod("ALLEG");
		ac.setDescrizione(allegato.getDescrizione());
		File f = new File(allegato.getPathFile());
		if(f != null) {
			ac.setDimensione(BigInteger.valueOf(f.length()));
		}
		ac.setMime(allegato.getTipoFile());
		ac.setNomeFile(allegato.getNomeFile());
		ac.setNomeFileOriginale(allegato.getNomeFile());
		return ac;
	}
}
