package it.wego.cross.utils;

import it.wego.cross.constants.TipoRuolo;
import it.wego.cross.dao.LookupDao;
import it.wego.cross.dto.dozer.IndirizzoInterventoDTO;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.xml.Anagrafiche;
import it.wego.cross.xml.Pratica;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PraticaUtils {

    @Autowired
    private LookupDao lookupDao;
    @Autowired
    private Mapper mapper;

    public static Pratica getPraticaFromXML(String xml) throws Exception {
        return getPraticaFromXML(xml.getBytes("UTF-8"));
    }

    public static Pratica getPraticaFromXML(byte[] xmlBytes) throws Exception {
        Pratica pratica = null;
        InputStream is = null;
        try {
            JAXBContext jc = JAXBContext.newInstance(Pratica.class);
            Unmarshaller um = jc.createUnmarshaller();
            is = new ByteArrayInputStream(xmlBytes);
            pratica = (Pratica) um.unmarshal(is);
        } finally {
            if (is != null) {
                is.close();
            }
        }
        return pratica;
    }

    public static String getXmlFromPratica(it.wego.cross.xml.Pratica pratica) throws PropertyException, JAXBException {
        JAXBContext jc = JAXBContext.newInstance(it.wego.cross.xml.Pratica.class);
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        StringWriter sw = new StringWriter();
        marshaller.marshal(pratica, sw);
        return sw.toString();
    }

    public static Anagrafiche getRichiedente(Pratica pratica) {
        Anagrafiche anagrafiche = null;
        if (pratica.getAnagrafiche() != null) {
            for (Anagrafiche a : pratica.getAnagrafiche()) {
                if (a != null) {
                    if (a.getCodTipoRuolo() != null && a.getCodTipoRuolo().equals(TipoRuolo.RICHIEDENTE)) {
                        anagrafiche = a;
                        break;
                    } else if (a.getCodTipoRuolo() != null && a.getCodTipoRuolo().equals(TipoRuolo.PROFESSIONISTA)) {
                        anagrafiche = a;
                        break;
                    }
                }
            }
        }
        return anagrafiche;
    }

    public static List<Anagrafiche> getRichiedenteBeneficiari(Pratica pratica) {
        List<Anagrafiche> anagrafiche = new ArrayList<Anagrafiche>();
        if (pratica.getAnagrafiche() != null) {
            for (Anagrafiche a : pratica.getAnagrafiche()) {
                if (a != null) {
                    if ((a.getCodTipoRuolo() != null && a.getCodTipoRuolo().equals(TipoRuolo.RICHIEDENTE))
                            || (a.getDesTipoRuolo() != null && a.getDesTipoRuolo().equalsIgnoreCase("RICHIEDENTE"))) {
                        anagrafiche.add(a);
                    } else if ((a.getCodTipoRuolo() != null && a.getCodTipoRuolo().equals(TipoRuolo.BENEFICIARIO))
                            || (a.getDesTipoRuolo() != null && a.getDesTipoRuolo().equalsIgnoreCase("BENEFICIARIO"))) {
                        anagrafiche.add(a);
                    }
                }
            }
        }
        return anagrafiche;
    }
    
    public static List<Anagrafiche> getTecniciDaXml(Pratica pratica) {
        List<Anagrafiche> anagrafiche = new ArrayList<Anagrafiche>();
        if (pratica.getAnagrafiche() != null) {
            for (Anagrafiche a : pratica.getAnagrafiche()) {
                if (a != null && a.getCodTipoRuolo()!=null && a.getCodTipoRuolo().equalsIgnoreCase(TipoRuolo.PROFESSIONISTA) ) {
                	anagrafiche.add(a);
                }
            }
        }
        return anagrafiche;
}
}
