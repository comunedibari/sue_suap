package it.wego.cross.plugins.aec;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.math.BigInteger;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nullable;
import javax.xml.namespace.NamespaceContext;
import javax.xml.rpc.ServiceException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.InputSource;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import it.diviana.egov.b109.oggettiCondivisi.AllegatoFirmatoDigitalmente;
import it.diviana.egov.b109.oggettiCondivisi.AllegatononFirmato;
import it.diviana.egov.b109.oggettiCondivisi.IdentificatoreUnivoco;
import it.diviana.egov.b109.oggettiCondivisi.IdentificatorediRichiesta;
import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.CampoHrefType;
import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.DichiarazioneDinamicaType;
import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.DocumentoType;
import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.ModuloType;
import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.ProcedimentoType;
import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.RichiestaDocument;
import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.RichiestadiConcessioniEAutorizzazioniType;
import it.wego.cross.actions.ErroriAction;
import it.wego.cross.dao.LookupDao;
import it.wego.cross.dto.ErroreDTO;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.LkTipoCollegio;
import it.wego.cross.entity.LkTipoIndirizzo;
import it.wego.cross.entity.LkTipoQualifica;
import it.wego.cross.entity.LkTipoSistemaCatastale;
import it.wego.cross.entity.LkTipoUnita;
import it.wego.cross.plugins.pratica.GestionePratica;
import it.wego.cross.service.ConfigurationService;
import it.wego.cross.service.EntiService;
import it.wego.cross.service.LookupService;
import it.wego.cross.service.PluginService;
import it.wego.cross.utils.PraticaUtils;
import it.wego.cross.utils.Utils;
import it.wego.cross.webservices.mypage.fileclient.FileClient;
import it.wego.cross.webservices.mypage.mypageclient.MyPageClient;
import it.wego.cross.webservices.mypage.mypageclient.stubs.EventoBean;
import it.wego.cross.xml.Allegati;
import it.wego.cross.xml.Allegato;
import it.wego.cross.xml.Anagrafiche;
import it.wego.cross.xml.DatiCatastali;
import it.wego.cross.xml.Immobile;
import it.wego.cross.xml.IndirizziIntervento;
import it.wego.cross.xml.Pratica;

/**
 *
 * @author Gabriele
 */
public abstract class AeCGestionePratica implements it.wego.cross.plugins.pratica.GestionePratica {

    public static final String XPATH_ID_ENTE = "//cea:SportelloDestinatario/cea:CodiceSportello";
    public static final String XPATH_ID_COMUNE = "//cea:SportelloDestinatario/cea:CodiceSportello";
    static final String XPATH_OGGETTO_PRATICA = "//cea:DichiarazioneDinamica[cea:href='h00097']/cea:campiHref/cea:campoHref/cea:valoreUtente";
    private static final String XPATH_IDENTIFICATIVO_PRATICA = "//ogg:IdentificatorediRichiesta/ogg:IdentificatoreUnivoco/ogg:CodiceIdentificativoOperazione";
    public static final String XPATH_IDENTIFICATIVO_NODO = "//ogg:IdentificatorediRichiesta/ogg:IdentificatoreUnivoco/ogg:CodiceSistema/ogg:CodiceAmministrazione";
    private static final String VISURA_URL = "attachment.url.visura";
    private static final String MYPAGE_URL = "attachment.url.mypage";
    private static final String AEC_XSLT = "plugin.aec.xslt";
    public static Logger log = LoggerFactory.getLogger("plugin");
    public Map<String, String> mappaDocumenti = new HashMap<String, String>();
    @Autowired
    private LookupService lookupService;
    @Autowired
    private EntiService entiService;
    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private PluginService pluginService;
    @Autowired
    private LookupDao lookupDao;
    @Autowired
    private ErroriAction erroriAction;

    @Override
    public Pratica execute(Object pratica) throws Exception {
        String xmlPraticaPeople = (String) pratica;
        InputSource source = new InputSource(new StringReader(xmlPraticaPeople));
        XPath xpath = XPathFactory.newInstance().newXPath();
        NamespaceContext context = new ConcessioniAutorizzazioniNamespaceContext(
                "cea", "http://gruppoinit.it/b110/ConcessioniEAutorizzazioni/procedimentoUnico",
                "ogg", "http://egov.diviana.it/b109/OggettiCondivisi");
        xpath.setNamespaceContext(context);
        String codEnte = xpath.evaluate(XPATH_ID_ENTE, source);
        Enti ente = entiService.findByCodEnte(codEnte);
        String idEnte = ente.getIdEnte().toString();
        String xmlPraticaCross = getXmlFromTemplate(xmlPraticaPeople, idEnte);
        popolaMappaDocumenti(xmlPraticaPeople);
        Pratica praticaCross;
        try {
            praticaCross = PraticaUtils.getPraticaFromXML(xmlPraticaCross);
            //Verifico che le anaggrafiche provenienti dall'applicazione dell'xsl non siano le stesse per lo stesso ruolo
            eliminaDuplicatiAnagrafiche(praticaCross);
            //Gestisco le anagrafiche multiple
            List<Anagrafiche> anagraficheMultiple = getAnagraficheMultiple(xmlPraticaPeople, idEnte);
            if (anagraficheMultiple != null && anagraficheMultiple.size() > 0) {
                for (Anagrafiche a : anagraficheMultiple) {
                    praticaCross.getAnagrafiche().add(a);
                }
            }
            DatiCatastali datiCatastali = getImmobili(xmlPraticaPeople, idEnte);
            praticaCross.setDatiCatastali(datiCatastali);
            IndirizziIntervento indirizziIntervento = getIndirizziIntervento(xmlPraticaPeople, idEnte);
            praticaCross.setIndirizziIntervento(indirizziIntervento);
        } catch (Exception ex) {
            throw new Exception("Si e' verificato un errore generando la pratica", ex);
        }
        return praticaCross;
    }

    //GLOBALE
    public String getXmlFromTemplate(String xmlToTransform, String idEnte) throws Exception {
        String xmlOutput = null;
        InputStream bais = null;
        try {
            String templateBase64String = configurationService.getCachedPluginConfiguration(AEC_XSLT, Integer.valueOf(idEnte), null);
            Preconditions.checkArgument(templateBase64String != null && !templateBase64String.isEmpty(), "Impossibile trovare il template XSL per trasformare la pratica. Verificare la proprieta '%s' per l'ente '%s'", AEC_XSLT, idEnte);

            InputStream template = new ByteArrayInputStream(Base64.decodeBase64(templateBase64String.getBytes("UTF-8")));

            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(template));
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            ByteArrayOutputStream writer = new ByteArrayOutputStream();
            bais = new ByteArrayInputStream(xmlToTransform.getBytes("UTF-8"));
            transformer.transform(new StreamSource(bais), new StreamResult(writer));
            xmlOutput = new String(writer.toByteArray());
        } catch (TransformerException ex) {
            throw new TransformerException("Errore eseguendo la trasformazione XSL per generare l'anagrafica", ex);
        } catch (Exception ex) {
            throw new Exception("Errore eseguendo la trasformazione XML", ex);
        } finally {
            if (bais != null) {
                bais.close();
            }
        }
        return xmlOutput;
    }

    //GLOBALE
    private void popolaMappaDocumenti(String xml) {
        try {
            RichiestaDocument richiesta = RichiestaDocument.Factory.parse(xml);
            RichiestadiConcessioniEAutorizzazioniType cea = richiesta.getRichiesta();
            if (cea.getModulistica() != null && cea.getModulistica().getModuloArray() != null && cea.getModulistica().getModuloArray().length > 0) {
                for (ModuloType modulo : cea.getModulistica().getModuloArray()) {
                    mappaDocumenti.put(modulo.getCodiceDoc(), modulo.getTitolo());
                }
            }
            if (cea.getDocumenti() != null && cea.getDocumenti().getDocumentoArray() != null && cea.getDocumenti().getDocumentoArray().length > 0) {
                for (DocumentoType documento : cea.getDocumenti().getDocumentoArray()) {
                    mappaDocumenti.put(documento.getCodice(), documento.getDescrizione());
                }
            }
        } catch (Exception ex) {
            log.error("Non sono riuscito a fare il parsing della pratica", ex);
        }
    }

    //GLOBALE
    public DatiCatastali getImmobili(String xml, String idEnte) throws Exception {
        GestionePratica gp = pluginService.getGestionePratica(Integer.parseInt(idEnte));
        DatiCatastali datiCatastali = null;
        RichiestaDocument richiesta = RichiestaDocument.Factory.parse(xml);
        RichiestadiConcessioniEAutorizzazioniType cea = richiesta.getRichiesta();
        List<DichiarazioneDinamicaType> immobili = new ArrayList<DichiarazioneDinamicaType>();

        if (cea.getDichiarazioniDinamiche() != null && cea.getDichiarazioniDinamiche().getDichiarazioneDinamicaArray() != null
                && cea.getDichiarazioniDinamiche().getDichiarazioneDinamicaArray().length > 0) {

            DichiarazioneDinamicaType[] dichiarazioni = cea.getDichiarazioniDinamiche().getDichiarazioneDinamicaArray();
            ConfigurazioneDatiCatastali configurazione = getConfiguazioneDatiCatastali(idEnte);
            for (DichiarazioneDinamicaType dichiarazione : dichiarazioni) {
                if (configurazione.getCodiceDichiarazione() != null && configurazione.getCodiceDichiarazione().contains(dichiarazione.getHref())) {
                    immobili.add(dichiarazione);
                }
            }
            if (!immobili.isEmpty()) {
                datiCatastali = new DatiCatastali();
                BigInteger contaImmobili = new BigInteger("1");
                for (DichiarazioneDinamicaType dichiarazione : immobili) {
                    DatoCatastale conf = getConfigurazioneDatoCatastale(configurazione, dichiarazione.getHref());
                    Integer numeroSezioni = getNumeroSezioni(conf, dichiarazione.getHref());
                    int numeroImmobiliCensiti = getMolteplicita(dichiarazione);
                    if (numeroSezioni != null) {
                        for (int sezione = 0; sezione < numeroSezioni; sezione++) {
                            for (int i = 1; i < numeroImmobiliCensiti + 1; i++) {
                                Immobile immobile = new Immobile();
                                String counter = "";
                                if (i > 1) {
                                    counter = "_" + i;
                                }
                                for (CampoHrefType campo : dichiarazione.getCampiHref().getCampoHrefArray()) {
                                    if (containsString(campo.getNome(), conf.getCod_tipo_sistema_catastale(), counter, sezione)) {
                                        String valueDecodificato = getValoreCampo(conf.getCod_tipo_sistema_catastale(), campo, counter, sezione);
                                        if (!Utils.e(valueDecodificato)) {
                                            if (immobile.getIdTipoSistemaCatastale() == null) {
                                                immobile.setIdTipoSistemaCatastale(new BigInteger(valueDecodificato));
                                            }
                                        }
                                    }
                                    if (containsString(campo.getNome(), conf.getSezione(), counter, sezione)) {
                                        String valueDecodificato = getValoreCampo(conf.getSezione(), campo, counter, sezione);
                                        if (Utils.e(immobile.getSezione())) {
                                            immobile.setSezione(valueDecodificato);
                                        }
                                    }
                                    if (containsString(campo.getNome(), conf.getCod_tipo_unita(), counter, sezione)) {
                                        String valueDecodificato = getValoreCampo(conf.getCod_tipo_unita(), campo, counter, sezione);
                                        if (!Utils.e(valueDecodificato)) {
                                            if (StringUtils.isNumeric(valueDecodificato)) {
                                                if (immobile.getIdTipoUnita() == null) {
                                                    immobile.setIdTipoUnita(new BigInteger(valueDecodificato));
                                                }
                                            } else {
                                                LkTipoUnita lk = lookupDao.findTipoUnitaByCod(valueDecodificato);
                                                if (lk != null) {
                                                    immobile.setIdTipoUnita(Utils.bi(lk.getIdTipoUnita()));
                                                }
                                            }
                                        }
                                    }
                                    if (containsString(campo.getNome(), conf.getFoglio(), counter, sezione)) {
                                        String valueDecodificato = getValoreCampo(conf.getFoglio(), campo, counter, sezione);
                                        if (Utils.e(immobile.getFoglio())) {
                                            immobile.setFoglio(valueDecodificato);
                                        }
                                    }
                                    if (containsString(campo.getNome(), conf.getMappale(), counter, sezione)) {
                                        String valueDecodificato = getValoreCampo(conf.getMappale(), campo, counter, sezione);
                                        if (Utils.e(immobile.getMappale())) {
                                            immobile.setMappale(valueDecodificato);
                                        }
                                    }
                                    if (containsString(campo.getNome(), conf.getCod_tipo_particella(), counter, sezione)) {
                                        String valueDecodificato = getValoreCampo(conf.getCod_tipo_particella(), campo, counter, sezione);
                                        if (!Utils.e(valueDecodificato)) {
                                            if (immobile.getIdTipoParticella() == null) {
                                                immobile.setIdTipoParticella(new BigInteger(valueDecodificato));
                                            }
                                        }
                                    }
                                    if (containsString(campo.getNome(), conf.getEstensione_particella(), counter, sezione)) {
                                        String valueDecodificato = getValoreCampo(conf.getEstensione_particella(), campo, counter, sezione);
                                        if (Utils.e(immobile.getEstensioneParticella())) {
                                            immobile.setEstensioneParticella(valueDecodificato);
                                        }
                                    }
                                    if (containsString(campo.getNome(), conf.getSubalterno(), counter, sezione)) {
                                        String valueDecodificato = getValoreCampo(conf.getSubalterno(), campo, counter, sezione);
                                        if (Utils.e(immobile.getSubalterno())) {
                                            immobile.setSubalterno(valueDecodificato);
                                        }
                                    }
                                    
                                  //Aggiunto il 21/06/2016
                                    if (containsString(campo.getNome(), conf.getCategoria(), counter, sezione)) {
                                        String valueDecodificato = getValoreCampo(conf.getCategoria(), campo, counter, sezione);
                                        if (Utils.e(immobile.getCategoria())) {
                                        	immobile.setCategoria(valueDecodificato);
                                        }
                                    }
                                    
                                    if (containsString(campo.getNome(), conf.getComuneCensuario(), counter, sezione)) {
                                        String valueDecodificato = getValoreCampo(conf.getComuneCensuario(), campo, counter, sezione);
                                        if (!Utils.e(valueDecodificato)) {
                                            if (immobile.getComuneCensuario() == null) {
                                                immobile.setComuneCensuario(valueDecodificato);
                                            }
                                        }
                                    }
                                    if (containsString(campo.getNome(), conf.getCodImmobile(), counter, sezione)) {
                                        String valueDecodificato = getValoreCampo(conf.getCodImmobile(), campo, counter, sezione);
                                        if (Utils.e(immobile.getCodImmobile())) {
                                            immobile.setCodImmobile(valueDecodificato);
                                        }
                                    }
                                }
                                if (!isEmpty(immobile)) {
                                    // loop sull'href per recuperare l'eventuale dato di default 
                                    if (Utils.e(immobile.getIdTipoSistemaCatastale())) {
                                        String value = containsDefault(conf.getCod_tipo_sistema_catastale(), sezione);
                                        if (!Utils.e(value)) {
                                            immobile.setIdTipoSistemaCatastale(new BigInteger(value));
                                        }
                                    }
                                    if (Utils.e(immobile.getSezione())) {
                                        immobile.setSezione(containsDefault(conf.getSezione(), sezione));
                                    }
                                    if (Utils.e(immobile.getIdTipoUnita())) {
                                        String value = containsDefault(conf.getCod_tipo_unita(), sezione);
                                        if (!Utils.e(value)) {
                                            immobile.setIdTipoUnita(new BigInteger(value));
                                        }
                                    }
                                    if (Utils.e(immobile.getFoglio())) {
                                        immobile.setFoglio(containsDefault(conf.getFoglio(), sezione));
                                    }
                                    if (Utils.e(immobile.getMappale())) {
                                        immobile.setMappale(containsDefault(conf.getMappale(), sezione));
                                    }
                                    if (Utils.e(immobile.getIdTipoParticella())) {
                                        String value = containsDefault(conf.getCod_tipo_particella(), sezione);
                                        if (!Utils.e(value)) {
                                            immobile.setIdTipoParticella(new BigInteger(value));
                                        }
                                    }
                                    if (Utils.e(immobile.getEstensioneParticella())) {
                                        immobile.setEstensioneParticella(containsDefault(conf.getEstensione_particella(), sezione));
                                    }
                                    if (Utils.e(immobile.getSubalterno())) {
                                        immobile.setSubalterno(containsDefault(conf.getSubalterno(), sezione));
                                    }
                                    //Aggiunto il 21/06/2016
                                    if (Utils.e(immobile.getCategoria())) {
                                    	immobile.setCategoria(containsDefault(conf.getCategoria(), sezione));
                                    }
                                    
                                    if (Utils.e(immobile.getComuneCensuario())) {
                                        String value = containsDefault(conf.getComuneCensuario(), sezione);
                                        if (!Utils.e(value)) {
                                            immobile.setComuneCensuario(value);
                                        }
                                    }
                                    if (Utils.e(immobile.getCodImmobile())) {
                                        immobile.setCodImmobile(containsDefault(conf.getCodImmobile(), sezione));
                                    }

                                    immobile.setCounter(contaImmobili);
                                    contaImmobili = contaImmobili.add(new BigInteger("1"));
                                    if (immobile.getIdTipoSistemaCatastale() == null) {
                                        immobile.setIdTipoSistemaCatastale(BigInteger.ONE);
                                    }
                                    String desSistemaCatastale = getSistemaCatastale(immobile.getIdTipoSistemaCatastale());
                                    immobile.setDesTipoSistemaCatastale(desSistemaCatastale);

                                    immobile.setConfermato("true");
                                    if (immobile.getIdTipoUnita() == null || StringUtils.isEmpty(immobile.getFoglio()) || StringUtils.isEmpty(immobile.getMappale())) {
                                        immobile.setConfermato("false");
                                    } else {
                                        // effettuo la chiamta al plugin per verificare se sono rispettati i controlli di corretteza del dato
                                        List<String> errori = gp.controllaDatoCatastale(immobile, Integer.parseInt(idEnte));
                                        if (errori != null) {
                                            immobile.setConfermato("false");
                                        }
                                        datiCatastali.getImmobile().add(immobile);
                                    }
                                }
                            }
                        }
                    } else {
                        // salto dichiarazione e segnalo errore in log e tabella errori
                        String err = "Errore in ricezione pratica - caricamento dati catastali - errata definizione - dichiarazione " + dichiarazione;
                        log.error("[Ricezione Pratica]" + err);
//                        ErroreDTO errore = erroriAction.getError(null, err, null);
                        ErroreDTO errore = erroriAction.getError(null, err, null, null, null);
                        erroriAction.saveError(errore);
                    }
                }
            }
        }
        return datiCatastali;
    }

    @Override
    public String getIdentificativoPratica(Object pratica) throws Exception {
        String xml = (String) pratica;
        InputSource source = new InputSource(new StringReader(xml));
        XPath xpath = XPathFactory.newInstance().newXPath();
        NamespaceContext context = new ConcessioniAutorizzazioniNamespaceContext(
                "cea", "http://gruppoinit.it/b110/ConcessioniEAutorizzazioni/procedimentoUnico",
                "ogg", "http://egov.diviana.it/b109/OggettiCondivisi");
        xpath.setNamespaceContext(context);
        String idPraticaPeople = xpath.evaluate(XPATH_IDENTIFICATIVO_PRATICA, source);
        String identificativoPratica = idPraticaPeople.split("/")[0];
        return identificativoPratica;
    }

    private String getSistemaCatastale(BigInteger tipoSistema) {
        LkTipoSistemaCatastale lk = lookupService.findTipoSistemaCatastaleById(Utils.ib(tipoSistema));
        return lk.getDescrizione();
    }

    protected int getMolteplicita(DichiarazioneDinamicaType dichiarazione) {
        Integer molteplicita = 1;
        for (CampoHrefType campo : dichiarazione.getCampiHref().getCampoHrefArray()) {
            int m = Integer.valueOf(campo.getMolteplicita());
            if (m > molteplicita) {
                molteplicita = m;
            }
        }
        return molteplicita;
    }

    public ConfigurazioneAnagraficheMultiple getConfiguazioneAnagraficheMultiple(String idEnte) throws Exception {
        ConfigurazioneAnagraficheMultiple conf = null;

        String codici_anagrafiche_multiple = getProperty("anagrafiche_multiple", idEnte);
        if (codici_anagrafiche_multiple != null && codici_anagrafiche_multiple.trim().length() > 0) {
            String dic[] = codici_anagrafiche_multiple.split(",");
            conf = new ConfigurazioneAnagraficheMultiple();
            conf.setCodiceDichiarazione(Arrays.asList(dic));
            List<Anagrafica> configurazioneAnagrafiche = new ArrayList<Anagrafica>();
            for (String cod : conf.getCodiceDichiarazione()) {
                Anagrafica anagrafica = new Anagrafica();
                anagrafica.setDichiarazione(cod);
                anagrafica.setCognome(getProperty(cod + ".cognome", idEnte));
                anagrafica.setNome(getProperty(cod + ".nome", idEnte));
                anagrafica.setRagioneSociale(getProperty(cod + ".ragioneSociale", idEnte));
                anagrafica.setComuneNascita(getProperty(cod + ".comuneNascita", idEnte));
                anagrafica.setProvinciaNascita(getProperty(cod + ".provinciaNascita", idEnte));
                anagrafica.setStatoNascita(getProperty(cod + ".statoNascita", idEnte));
                anagrafica.setSesso(getProperty(cod + ".sesso", idEnte));
                anagrafica.setDataNascita(getProperty(cod + ".dataNascita", idEnte));
                anagrafica.setPartitaIva(getProperty(cod + ".partitaIva", idEnte));
                anagrafica.setCodiceFiscale(getProperty(cod + ".codiceFiscale", idEnte));
                anagrafica.setSedeResidenza(getProperty(cod + ".sedeResidenza", idEnte));
                anagrafica.setProvinciaSedeResidenza(getProperty(cod + ".provinciaSedeResidenza", idEnte));
                anagrafica.setStatoSedeResidenza(getProperty(cod + ".statoSedeResidenza", idEnte));
                anagrafica.setIndirizzoSedeRedidenza(getProperty(cod + ".indirizzoSedeRedidenza", idEnte));
                anagrafica.setCivicoSedeResidenza(getProperty(cod + ".civicoSedeResidenza", idEnte));
                anagrafica.setCapSedeResidenza(getProperty(cod + ".capSedeResidenza", idEnte));
                anagrafica.setEmail(getProperty(cod + ".email", idEnte));
                anagrafica.setPec(getProperty(cod + ".pec", idEnte));
                anagrafica.setTelefono(getProperty(cod + ".telefono", idEnte));
                anagrafica.setFax(getProperty(cod + ".fax", idEnte));
                anagrafica.setProvinciaAlbo(getProperty(cod + ".privinciaAlbo", idEnte));
                anagrafica.setNumeroAlbo(getProperty(cod + ".numeroAlbo", idEnte));
                anagrafica.setDesAlbo(getProperty(cod + ".descrizioneAlbo", idEnte));
                anagrafica.setIdTipoAlbo(getProperty(cod + ".idTipoAlbo", idEnte));
                anagrafica.setTipoAnagrafica(getProperty(cod + ".tipoAnagrafica", idEnte));
                anagrafica.setIdTipoRuolo(getProperty(cod + ".idTipoRuolo", idEnte));
                anagrafica.setCodTipoRuolo(getProperty(cod + ".codTipoRuolo", idEnte));
                anagrafica.setDesTipoRuolo(getProperty(cod + ".desTipoRuolo", idEnte));
                anagrafica.setIdTipoQualifica(getProperty(cod + ".idTipoQualifica", idEnte));
                anagrafica.setDesTipoQualifica(getProperty(cod + ".desTipoQualifica", idEnte));
                anagrafica.setIdTipoIndirizzo(getProperty(cod + ".idTipoIndirizzo", idEnte));
                anagrafica.setDesTipoIndirizzo(getProperty(cod + ".desTipoIndirizzo", idEnte));
                anagrafica.setGroovyScript(getProperty(cod + ".groovyScript", idEnte));
                configurazioneAnagrafiche.add(anagrafica);
            }
            conf.setAnagrafiche(configurazioneAnagrafiche);
        }
        return conf;
    }

    private DatoCatastale getConfigurazioneDatoCatastale(ConfigurazioneDatiCatastali configurazione, String href) {
        DatoCatastale result = null;
        for (DatoCatastale dato : configurazione.getDatiCatastali()) {
            if (dato.getDichiarazione().equalsIgnoreCase(href)) {
                result = dato;
                break;
            }
        }
        return result;
    }

    private Integer getNumeroSezioni(Object dato, String dichiarazione) throws Exception {
        Integer result = null;
        Map<String, String> fieldsMap = Utils.getObjectFieldsMap(dato);
        for (Map.Entry pairs : fieldsMap.entrySet()) {
            String value = ((String) pairs.getValue()).trim();
            if (pairs.getValue() != null && !value.equals("")) {
                if (!((String) pairs.getKey()).equalsIgnoreCase("GETDICHIARAZIONE")) {
                    String[] campi = value.split("%%");
                    if (result != null && !result.equals(campi.length)) {
                        log.error("[getNumeroSezioni] Errore nella definzione delle sezioni su DatiCatatstali " + dichiarazione);
                        throw new Exception("Errore nella definzione delle sezioni su DatiCatatstali");
                    } else {
                        result = campi.length;
                    }
                }
            }
        }
        return result;
    }

    public Anagrafica getConfiguazioneAnagraficheMultiple(ConfigurazioneAnagraficheMultiple configurazione, String href) {
        Anagrafica result = null;
        for (Anagrafica dato : configurazione.getAnagrafiche()) {
            if (dato.getDichiarazione().equalsIgnoreCase(href)) {
                result = dato;
                break;
            }
        }
        return result;
    }

    @Override
    public Allegati getAllegati(Object pratica) throws Exception {
        String xml = (String) pratica;
        Allegati allegati;
        RichiestaDocument richiesta = RichiestaDocument.Factory.parse(xml);

        //Fa schifo...rivedere
        InputSource source = new InputSource(new StringReader(xml));
        XPath xpath = XPathFactory.newInstance().newXPath();
        NamespaceContext context = new ConcessioniAutorizzazioniNamespaceContext(
                "cea", "http://gruppoinit.it/b110/ConcessioniEAutorizzazioni/procedimentoUnico",
                "ogg", "http://egov.diviana.it/b109/OggettiCondivisi");
        xpath.setNamespaceContext(context);

        String codEnte = xpath.evaluate(XPATH_ID_ENTE, source);
        Enti ente = entiService.findByCodEnte(codEnte);
        String idEnte = ente.getIdEnte().toString();

        RichiestadiConcessioniEAutorizzazioniType cea = richiesta.getRichiesta();
        String idPratica = getIdPratica(richiesta);
        StringBuilder path = new StringBuilder();
        path.append(getProperty("attachment.tmp.folder", idEnte)).append(System.getProperty("file.separator")).append(idPratica).append(System.getProperty("file.separator"));
        String tmpFolder = path.toString();
        //Creo la directory per salvare i file
        (new File(path.toString())).mkdirs();
        AllegatononFirmato[] allegatiNonFirmati = cea.getAllegatononFirmatoArray();
        AllegatoFirmatoDigitalmente[] allegatiFirmati = cea.getAllegatoFirmatoDigitalmenteArray();
        allegati = new Allegati();
        if (allegatiNonFirmati != null && allegatiNonFirmati.length > 0) {
            int i = 0;
            for (AllegatononFirmato allegato : allegatiNonFirmati) {
                Allegato a = getFromMyPage(allegato, tmpFolder, i);
                if (a != null) {
                    allegati.getAllegato().add(a);
                    i++;
                }
            }
        }
        if (allegatiFirmati != null && allegatiFirmati.length > 0) {
            int i = 0;
            if (allegatiNonFirmati != null && allegatiNonFirmati.length > 0) {
                i = allegatiNonFirmati.length + 1;
            }
            for (AllegatoFirmatoDigitalmente allegato : allegatiFirmati) {
                Allegato a = getFromMyPage(allegato, tmpFolder, i);
                if (a != null) {
                    allegati.getAllegato().add(a);
                    i++;
                }
            }
        }
        if (cea.getRiepilogoRichiesta().getAllegatononFirmato() != null) {
            AllegatononFirmato allegatoNonFirmato = cea.getRiepilogoRichiesta().getAllegatononFirmato();
            StringBuilder pathToSave = new StringBuilder();
            pathToSave.append(tmpFolder).append(System.getProperty("file.separator")).append(Utils.normalizeFileName(allegatoNonFirmato.getNomeFile()));
//            writeFile(pathToSave.toString(), allegatoNonFirmato.getContenuto());
            Allegato a = getFromMyPage(allegatoNonFirmato, tmpFolder, 1000);
            a.setRiepilogoPratica("S");
            allegati.getAllegato().add(a);
        } else if (cea.getRiepilogoRichiesta().getAllegatoFirmatoDigitalmente() != null) {
            AllegatoFirmatoDigitalmente allegatoFirmato = cea.getRiepilogoRichiesta().getAllegatoFirmatoDigitalmente();
            StringBuilder pathToSave = new StringBuilder();
            pathToSave.append(tmpFolder).append(System.getProperty("file.separator")).append(allegatoFirmato.getNomeFile());
//            writeFile(pathToSave.toString(), allegatoFirmato.getContenuto());

            Allegato a = getFromMyPage(allegatoFirmato, tmpFolder, 1000);
            a.setRiepilogoPratica("S");
            allegati.getAllegato().add(a);
        }
        //Salvo anche l'XML della pratica
        StringBuilder pathToSave = new StringBuilder();
        pathToSave.append(tmpFolder).append(System.getProperty("file.separator")).append("CeA.xml").toString();
        String tipoFile = writeFile(pathToSave.toString(), "CeA.xml", xml.getBytes());
        Allegato a = new Allegato();
        a.setDescrizione("Pratica di Concessioni e Autorizzazioni");
        a.setNomeFile("CeA.xml");
        a.setRiepilogoPratica("N");
        a.setPathFile(pathToSave.toString());
        a.setTipoFile(tipoFile);
        allegati.getAllegato().add(a);
        return allegati;
    }

    private String getIdPratica(RichiestaDocument richiesta) {
        RichiestadiConcessioniEAutorizzazioniType cea = richiesta.getRichiesta();
        IdentificatorediRichiesta identificatorediRichiesta = cea.getIdentificatorediRichiesta();
        IdentificatoreUnivoco identificatoreUnivoco = identificatorediRichiesta.getIdentificatoreUnivoco();
        String codiceIdentificativoOperazione = identificatoreUnivoco.getCodiceIdentificativoOperazione();
        String idPraticaPerFE = codiceIdentificativoOperazione.split("/")[0];
        return idPraticaPerFE;
    }

    private Allegato getFromMyPage(AllegatononFirmato allegato, String tmpFolder, int index) {
        try {
            String contenutoBase64 = new String(allegato.getContenuto());
            String[] riferimenti = contenutoBase64.split("\\|\\|");
            //Controllo che contenga i riferimenti alla getFile di MyPage
            if (riferimenti.length != 5
                    || (riferimenti.length == 5 && !riferimenti[riferimenti.length - 1].equals("getFile"))) {
                //E' un file binario
                StringBuilder path = new StringBuilder();
                path.append(tmpFolder).append(System.getProperty("file.separator"))
                        .append(Utils.normalizeFileName(index + "_" + allegato.getNomeFile()));
                String tipoFile = writeFile(path.toString(), allegato.getNomeFile(), allegato.getContenuto());
                Allegato result = new Allegato();
                result.setPathFile(path.toString());
                String descrizione;
                if (mappaDocumenti.containsKey(allegato.getDescrizione())) {
                    descrizione = Utils.limitText(mappaDocumenti.get(allegato.getDescrizione()), 254);
                } else {
                    descrizione = Utils.limitText(allegato.getDescrizione(), 254);
                }
                result.setDescrizione(descrizione);
                result.setNomeFile(Utils.normalizeFileName(index + "_" + allegato.getNomeFile()));
                result.setRiepilogoPratica("N");
                result.setTipoFile(tipoFile);
                return result;
            } else {
                String codiceComune = riferimenti[0];
                String idPratica = riferimenti[1];
                String codFile = riferimenti[2];
                String url = riferimenti[3];
                FileClient fileClient = new FileClient();
                String fileDaSalvare;
                try {
                    fileDaSalvare = fileClient.getFile(url, codiceComune, idPratica, codFile);
                    StringBuilder path = new StringBuilder();
                    path.append(tmpFolder).append(System.getProperty("file.separator"))
                            .append(Utils.normalizeFileName(index + "_" + allegato.getNomeFile()));
                    String tipoFile = writeFile(path.toString(), allegato.getNomeFile(), Base64.decodeBase64(fileDaSalvare));
                    Allegato result = new Allegato();
                    result.setPathFile(path.toString());
                    String descrizione;
                    if (mappaDocumenti.containsKey(allegato.getDescrizione())) {
                        descrizione = Utils.limitText(mappaDocumenti.get(allegato.getDescrizione()), 254);
                    } else {
                        descrizione = Utils.limitText(allegato.getDescrizione(), 254);
                    }
                    result.setTipoFile(tipoFile);
                    result.setDescrizione(descrizione);
                    result.setNomeFile(Utils.normalizeFileName(index + "_" + allegato.getNomeFile()));
                    result.setRiepilogoPratica("N");
                    return result;
                } catch (RemoteException ex) {
                    log.error("[Remote Exception] Non sono riuscito a recuperare l'allegato da MyPage", ex);
                    return null;
                } catch (ServiceException ex) {
                    log.error("[ServiceException] Non sono riuscito a recuperare l'allegato da MyPage", ex);
                    return null;
                }
            }
        } catch (IOException ex) {
            log.error("Non sono riuscito a caricare il file di properties", ex);
            return null;
        }
    }

    private String writeFile(String path, String nomeFile, byte[] fileDaSalvare) throws IOException, FileNotFoundException {
        Tika tika = new Tika();
        String fileType = tika.detect(new ByteArrayInputStream(fileDaSalvare), nomeFile);
        FileOutputStream fos = new FileOutputStream(path);
        fos.write(fileDaSalvare);
        fos.close();
        return fileType;
    }

    protected boolean isEmpty(Object obj) throws Exception {
        boolean ret = true;
        for (String valore : Utils.getObjectFieldsValueList(obj)) {
            if (!Utils.e(valore)) {
                ret = false;
                break;
            }
        }
        return ret;
    }

    @Override
    public String notifica(it.wego.cross.entity.Pratica pratica, String descrizioneEvento) throws Exception {
        String visuraUrl = configurationService.getCachedConfiguration(VISURA_URL, pratica.getIdProcEnte().getIdEnte().getIdEnte(), pratica.getIdComune().getIdComune());
        String myPageUrl = configurationService.getCachedConfiguration(MYPAGE_URL, pratica.getIdProcEnte().getIdEnte().getIdEnte(), pratica.getIdComune().getIdComune());

        EventoBean infoBean = new EventoBean();
        infoBean.setUrl_visura(visuraUrl);
        infoBean.setTimestamp_evento(Calendar.getInstance().getTimeInMillis());
        infoBean.setVisibilita(true);
        infoBean.setDescrizione_evento(descrizioneEvento);
        infoBean.setProcess_data_id(pratica.getIdentificativoPratica());
        infoBean.setId_bo("CROSS");

        MyPageClient client = new MyPageClient(myPageUrl);
        return client.callMyPage(infoBean);
    }

    @Override
    public String getOggettoPratica(Object pratica) throws Exception {
        String xml = (String) pratica;
        RichiestaDocument richiesta = RichiestaDocument.Factory.parse(xml);
        RichiestadiConcessioniEAutorizzazioniType cea = richiesta.getRichiesta();
        ProcedimentoType[] procedimenti = cea.getRiepilogoDomanda().getProcedimenti().getProcedimentoArray();
        StringBuilder sb = new StringBuilder();
        if (procedimenti != null) {
            int counter = 0;
            for (ProcedimentoType procedimento : procedimenti) {
                counter++;
                sb.append(procedimento.getNome());
                if (counter < procedimenti.length) {
                    sb.append(", ");
                }
            }
        }
        String oggetto = sb.toString();
        return oggetto;
    }

    public List<Anagrafiche> getAnagraficheMultiple(String xml, String idEnte) throws Exception {
        List<Anagrafiche> anagrafiche = null;
        try {
            RichiestaDocument richiesta = RichiestaDocument.Factory.parse(xml);
            RichiestadiConcessioniEAutorizzazioniType cea = richiesta.getRichiesta();
            List<DichiarazioneDinamicaType> anagraficheMultiple = new ArrayList<DichiarazioneDinamicaType>();
            ConfigurazioneAnagraficheMultiple configurazione = getConfiguazioneAnagraficheMultiple(idEnte);
            if (configurazione != null) {
                if (cea.getDichiarazioniDinamiche() != null && cea.getDichiarazioniDinamiche().getDichiarazioneDinamicaArray() != null
                        && cea.getDichiarazioniDinamiche().getDichiarazioneDinamicaArray().length > 0) {
                    DichiarazioneDinamicaType[] dichiarazioni = cea.getDichiarazioniDinamiche().getDichiarazioneDinamicaArray();
                    for (DichiarazioneDinamicaType dichiarazione : dichiarazioni) {
                        if (configurazione.getCodiceDichiarazione().contains(dichiarazione.getHref())) {
                            anagraficheMultiple.add(dichiarazione);
                        }
                    }
                    if (anagraficheMultiple.size() > 0) {
                        anagrafiche = new ArrayList<Anagrafiche>();
                        Integer contaAnagrafiche = 0;
                        for (DichiarazioneDinamicaType dichiarazione : anagraficheMultiple) {
                            Anagrafica conf = getConfiguazioneAnagraficheMultiple(configurazione, dichiarazione.getHref());
                            int numeroAnagraficheCensite = getMolteplicita(dichiarazione);
                            Integer numeroSezioni = getNumeroSezioni(conf, dichiarazione.getHref());
                            for (int i = 1; i < numeroAnagraficheCensite + 1; i++) {
                                for (int sezione = 0; sezione < numeroSezioni; sezione++) {
                                    Anagrafiche anagraficheXml = new Anagrafiche();
                                    it.wego.cross.xml.Anagrafica a = new it.wego.cross.xml.Anagrafica();
                                    a.setDaRubrica(null);
                                    it.wego.cross.xml.Recapiti recapiti = new it.wego.cross.xml.Recapiti();
                                    it.wego.cross.xml.Recapito r = new it.wego.cross.xml.Recapito();
                                    String counter = "";
                                    //Inizializzo il contatore da 100
                                    if (i > 1) {
                                        counter = "_" + i;
                                    }
                                    String pec = "";
                                    anagraficheXml = caricaAnagraficaDaHref(anagraficheXml, a, r, recapiti, dichiarazione, conf, counter, i, pec, sezione, contaAnagrafiche);
                                    if (anagraficheXml.getAnagrafica() != null) {
                                        anagrafiche.add(anagraficheXml);
                                        contaAnagrafiche++;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            throw new Exception(ex);
        }
        return anagrafiche;
    }

    private String getProperty(String key, String idEnte) {
        return configurationService.getCachedPluginConfiguration(key, Integer.valueOf(idEnte), null);
    }

    public static String getValoreCampo(String conf, CampoHrefType campo, String counter, int sezione) {
        String result = null;
        if (!Utils.e(conf)) {
            String[] configurazione = conf.split("%%");
            if (configurazione.length > sezione) {
                String etichettaCompleta = configurazione[sezione];
                etichettaCompleta = etichettaCompleta.split("\\[")[0];
                if (!Utils.e(etichettaCompleta)) {
                    String[] split = etichettaCompleta.split(",");
                    List<String> listaEtichette = Arrays.asList(split);
                    for (int i = 0; i < listaEtichette.size(); i++) {
                        String[] le = listaEtichette.get(i).split("\\|");
                        String etichetta = le[0];
                        if (campo.getNome().equals(etichetta + counter)) {
                            String val = campo.getValoreUtente();
                            if (!Utils.e(val)) {
                                if (le.length == 1) {
                                    result = val;
                                } else {
                                    result = getValoreListbox(campo, val);
                                }
                            } else {
                                result = "";
                            }
                            break;
                        }
                    }
                }
            }
        }
        return result;
    }

    public static Boolean containsString(String src, @Nullable String referenceMul, String counter, int sezione) {
        Boolean ret = true;
        String[] split = null;
        if (Strings.isNullOrEmpty(referenceMul)) {
            ret = false;
        }
        if (ret) {
            split = referenceMul.split("%%");
            if (split.length == 0) {
                ret = false;
            }
        }
        if (ret) {
            List<String> referenceList = Arrays.asList(split);
            if (split.length > sezione) {
                String etichettaCompleta = referenceList.get(sezione);
                if (!Utils.e(etichettaCompleta)) {
                    etichettaCompleta = etichettaCompleta.split("\\[")[0];
                    if (!Utils.e(etichettaCompleta)) {
                        split = etichettaCompleta.split(",");
                        List<String> listaEtichette = Arrays.asList(split);
                        ret = false;
                        for (int i = 0; i < listaEtichette.size(); i++) {
                            String etichetta = listaEtichette.get(i).split("\\|")[0];
                            if (!Utils.e(etichetta) && (etichetta + counter).equals(src)) {
                                ret = true;
                                break;
                            }
                        }
                    }
                    // gestione con default tra [] a valle dell'eventuale etichetta
                } else {
                    ret = false;
                }
            } else {
                ret = false;
            }
        }
        return ret;
    }

    protected String containsDefault(@Nullable String referenceMul) {
        return containsDefault(referenceMul, 0);
    }

    protected String containsDefault(@Nullable String referenceMul, int sezione) {
        String ret = null;
        boolean flow = true;
        String[] split = null;
        Pattern pattern = Pattern.compile("[\\[](.*)[\\]]");
        if (Strings.isNullOrEmpty(referenceMul)) {
            flow = false;
        }
        if (flow) {
            split = referenceMul.split("%%");
            if (split.length == 0) {
                flow = false;
            }
        }
        if (flow) {
            List<String> referenceList = Arrays.asList(split);
            if (split.length > sezione) {
                Matcher matcher = pattern.matcher(referenceList.get(sezione));
                if (matcher.find()) {
                    ret = matcher.group(1);
                }
            }
        }
        return ret;
    }

    protected Anagrafiche caricaAnagraficaDaHref(Anagrafiche anagraficheXml, it.wego.cross.xml.Anagrafica a, it.wego.cross.xml.Recapito r, it.wego.cross.xml.Recapiti recapiti, DichiarazioneDinamicaType dichiarazione, Anagrafica conf, String counter, int i, String pec, Integer sezione, Integer contaAnagrafiche) throws Exception {
        for (CampoHrefType campo : dichiarazione.getCampiHref().getCampoHrefArray()) {

            if (containsString(campo.getNome(), conf.getCognome(), counter, sezione)) {
                String valueDecodificato = getValoreCampo(conf.getCognome(), campo, counter, sezione);
                if (Utils.e(a.getCognome())) {
                    a.setCognome(valueDecodificato);
                }
            }
            if (containsString(campo.getNome(), conf.getNome(), counter, sezione)) {
                String valueDecodificato = getValoreCampo(conf.getNome(), campo, counter, sezione);
                if (Utils.e(a.getNome())) {
                    a.setNome(valueDecodificato);
                }
            }
            if (containsString(campo.getNome(), conf.getRagioneSociale(), counter, sezione)) {
                String valueDecodificato = getValoreCampo(conf.getRagioneSociale(), campo, counter, sezione);
                if (Utils.e(a.getDenominazione())) {
                    a.setDenominazione(valueDecodificato);
                }
            }
            if (containsString(campo.getNome(), conf.getSesso(), counter, sezione)) {
                String valueDecodificato = getValoreCampo(conf.getSesso(), campo, counter, sezione);
                if (Utils.e(a.getSesso())) {
                    a.setSesso(valueDecodificato);
                }
            }
            if (containsString(campo.getNome(), conf.getComuneNascita(), counter, sezione)) {
                String valueDecodificato = getValoreCampo(conf.getComuneNascita(), campo, counter, sezione);
                if (Utils.e(a.getDesComuneNascita())) {
                    a.setDesComuneNascita(valueDecodificato);
                }
            }
            if (containsString(campo.getNome(), conf.getProvinciaNascita(), counter, sezione)) {
                String valueDecodificato = getValoreCampo(conf.getProvinciaNascita(), campo, counter, sezione);
                if (Utils.e(a.getDesProvinciaNascita())) {
                    a.setDesProvinciaNascita(valueDecodificato);
                }
            }
            if (containsString(campo.getNome(), conf.getStatoNascita(), counter, sezione)) {
                String valueDecodificato = getValoreCampo(conf.getStatoNascita(), campo, counter, sezione);
                if (Utils.e(a.getDesStatoNascita())) {
                    a.setDesStatoNascita(valueDecodificato);
                }
            }
            if (containsString(campo.getNome(), conf.getDataNascita(), counter, sezione)) {
                String dataNascita = getValoreCampo(conf.getDataNascita(), campo, counter, sezione);
                if (!Utils.e(dataNascita)) {
                    if (a.getDataNascita() == null) {
                        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                        Date data = df.parse(dataNascita);
                        a.setDataNascita(Utils.dateToXmlGregorianCalendar(data));
                    }
                }
            }
            if (containsString(campo.getNome(), conf.getPartitaIva(), counter, sezione)) {
                if (Utils.e(a.getPartitaIva())) {
                    a.setPartitaIva(getValoreCampo(conf.getPartitaIva(), campo, counter, sezione));
                }
            }
            if (containsString(campo.getNome(), conf.getCodiceFiscale(), counter, sezione)) {
                String value = getValoreCampo(conf.getCodiceFiscale(), campo, counter, sezione);
                if (Utils.e(a.getCodiceFiscale())) {
                    if (!Utils.e(value) && value.length() == 11 && Utils.e(a.getPartitaIva())) {
                        a.setPartitaIva(value);
                    }
                    a.setCodiceFiscale(value);
                }
            }
            if (!Utils.e(a.getPartitaIva()) && a.getPartitaIva().equals(a.getCodiceFiscale())) {
                if (Utils.e(a.getPartitaIva())) {
                    if (a.getPartitaIva().length() > 11) {
                        a.setPartitaIva(null);
                    }
                }
            }
            if (containsString(campo.getNome(), conf.getProvinciaAlbo(), counter, sezione)) {
                String valueDecodificato = getValoreCampo(conf.getProvinciaAlbo(), campo, counter, sezione);
                if (Utils.e(a.getDesProvinciaIscrizione())) {
                    a.setDesProvinciaIscrizione(valueDecodificato);
                }
            }
            if (containsString(campo.getNome(), conf.getNumeroAlbo(), counter, sezione)) {
                String valueDecodificato = getValoreCampo(conf.getNumeroAlbo(), campo, counter, sezione);
                if (Utils.e(a.getNumeroIscrizione())) {
                    a.setNumeroIscrizione(valueDecodificato);
                }
            }
            if (containsString(campo.getNome(), conf.getDesAlbo(), counter, sezione)) {
                String valueDecodificato = getValoreCampo(conf.getDesAlbo(), campo, counter, sezione);
                if (Utils.e(a.getDesTipoCollegio())) {
                    a.setDesTipoCollegio(valueDecodificato);
                }
            }
            if (containsString(campo.getNome(), conf.getIdTipoAlbo(), counter, sezione)) {
                String valueDecodificato = getValoreCampo(conf.getIdTipoAlbo(), campo, counter, sezione);
                if (Utils.e(a.getIdTipoCollegio())) {
                    if (!Utils.e(valueDecodificato)) {
                        a.setIdTipoCollegio(new BigInteger(valueDecodificato));
                    }
                }
            }
            if (containsString(campo.getNome(), conf.getSedeResidenza(), counter, sezione)) {
                String valueDecodificato = getValoreCampo(conf.getSedeResidenza(), campo, counter, sezione);
                if (Utils.e(r.getDesComune())) {
                    r.setDesComune(valueDecodificato);
                }
            }
            if (containsString(campo.getNome(), conf.getIndirizzoSedeRedidenza(), counter, sezione)) {
                String valueDecodificato = getValoreCampo(conf.getIndirizzoSedeRedidenza(), campo, counter, sezione);
                if (Utils.e(r.getIndirizzo())) {
                    r.setIndirizzo(valueDecodificato);
                }
            }
            if (containsString(campo.getNome(), conf.getCivicoSedeResidenza(), counter, sezione)) {
                String valueDecodificato = getValoreCampo(conf.getCivicoSedeResidenza(), campo, counter, sezione);
                if (Utils.e(r.getNCivico())) {
                    r.setNCivico(valueDecodificato);
                }
            }
            if (containsString(campo.getNome(), conf.getCapSedeResidenza(), counter, sezione)) {
                String valueDecodificato = getValoreCampo(conf.getCapSedeResidenza(), campo, counter, sezione);
                if (Utils.e(r.getCap())) {
                    r.setCap(valueDecodificato);
                }
            }
            if (containsString(campo.getNome(), conf.getEmail(), counter, sezione)) {
                String valueDecodificato = getValoreCampo(conf.getEmail(), campo, counter, sezione);
                if (Utils.e(r.getEmail())) {
                    r.setEmail(valueDecodificato);
                }
            }
            if (containsString(campo.getNome(), conf.getPec(), counter, sezione)) {
                String valueDecodificato = getValoreCampo(conf.getPec(), campo, counter, sezione);
                if (Utils.e(r.getPec())) {
                    r.setPec(valueDecodificato);
                }
            }
            if (containsString(campo.getNome(), conf.getTelefono(), counter, sezione)) {
                String valueDecodificato = getValoreCampo(conf.getTelefono(), campo, counter, sezione);
                if (Utils.e(r.getTelefono())) {
                    r.setTelefono(valueDecodificato);
                }
            }
            if (containsString(campo.getNome(), conf.getFax(), counter, sezione)) {
                String valueDecodificato = getValoreCampo(conf.getFax(), campo, counter, sezione);
                if (Utils.e(r.getFax())) {
                    r.setFax(valueDecodificato);
                }
            }
            if (containsString(campo.getNome(), conf.getIdTipoIndirizzo(), counter, sezione)) {
                String value = getValoreCampo(conf.getIdTipoIndirizzo(), campo, counter, sezione);
                if (r.getIdTipoIndirizzo() == null) {
                    if (!Utils.e(value)) {
                        r.setIdTipoIndirizzo(new BigInteger(value));
                    }
                }
            }
            if (containsString(campo.getNome(), conf.getDesTipoIndirizzo(), counter, sezione)) {
                String valueDecodificato = getValoreCampo(conf.getDesTipoIndirizzo(), campo, counter, sezione);
                if (Utils.e(r.getDesTipoIndirizzo())) {
                    r.setDesTipoIndirizzo(valueDecodificato);
                }
            }
            if (containsString(campo.getNome(), conf.getTipoAnagrafica(), counter, sezione)) {
                String valueDecodificato = getValoreCampo(conf.getTipoAnagrafica(), campo, counter, sezione);
                if (Utils.e(a.getTipoAnagrafica())) {
                    a.setTipoAnagrafica(valueDecodificato);
                }
            }
        }
        if (!isEmpty(r)) {
            if (Utils.e(r.getDesComune())) {
                r.setDesComune(containsDefault(conf.getSedeResidenza()));
            }
            if (Utils.e(r.getIndirizzo())) {
                r.setIndirizzo(containsDefault(conf.getIndirizzoSedeRedidenza()));
            }
            if (Utils.e(r.getNCivico())) {
                r.setNCivico(containsDefault(conf.getCivicoSedeResidenza()));
            }
            if (Utils.e(r.getCap())) {
                r.setCap(containsDefault(conf.getCapSedeResidenza()));
            }
            if (Utils.e(r.getEmail())) {
                r.setEmail(containsDefault(conf.getEmail()));
            }
            if (Utils.e(r.getPec())) {
                r.setPec(containsDefault(conf.getPec()));
            }
            if (Utils.e(r.getTelefono())) {
                r.setTelefono(containsDefault(conf.getTelefono()));
            }
            if (Utils.e(r.getFax())) {
                r.setFax(containsDefault(conf.getFax()));
            }
            if (Utils.e(r.getIdTipoIndirizzo())) {
                String value = containsDefault(conf.getIdTipoIndirizzo());
                if (!Utils.e(value)) {
                    r.setIdTipoIndirizzo(new BigInteger(value));
                }
            }
            if (Utils.e(r.getDesTipoIndirizzo())) {
                r.setDesTipoIndirizzo(containsDefault(conf.getDesTipoIndirizzo()));
            }
            if (Utils.e(r.getIdTipoIndirizzo()) && !Utils.e(r.getDesTipoIndirizzo())) {
                LkTipoIndirizzo indirizzo = lookupDao.findTipoIndirizzoByDesc(r.getDesTipoIndirizzo());
                if (indirizzo != null) {
                    r.setIdTipoIndirizzo(Utils.bi(indirizzo.getIdTipoIndirizzo()));
                }
            } else if (!Utils.e(r.getIdTipoIndirizzo())) {
                LkTipoIndirizzo indirizzo = lookupDao.findTipoIndirizzoById(Utils.ib(r.getIdTipoIndirizzo()));
                if (indirizzo != null) {
                    r.setDesTipoIndirizzo(indirizzo.getDescrizione());
                }
            }
            r.setCounter(BigInteger.valueOf(i + 1000));
            recapiti.getRecapito().add(r);
            a.setRecapiti(recapiti);
        }
        if (!isEmpty(a)) {
            if (Utils.e(a.getCognome())) {
                a.setCognome(containsDefault(conf.getCognome()));
            }
            if (Utils.e(a.getNome())) {
                a.setNome(containsDefault(conf.getNome()));
            }
            if (Utils.e(a.getSesso())) {
                a.setSesso(containsDefault(conf.getSesso()));
            }
            if (Utils.e(a.getDenominazione())) {
                a.setDenominazione(containsDefault(conf.getRagioneSociale()));
            }
            if (Utils.e(a.getDesComuneNascita())) {
                a.setDesComuneNascita(containsDefault(conf.getComuneNascita()));
            }
            if (Utils.e(a.getDesProvinciaNascita())) {
                a.setDesProvinciaNascita(containsDefault(conf.getProvinciaNascita()));
            }
            if (Utils.e(a.getDesStatoNascita())) {
                a.setDesStatoNascita(containsDefault(conf.getStatoNascita()));
            }
            if (Utils.e(a.getPartitaIva())) {
                a.setPartitaIva(containsDefault(conf.getPartitaIva()));
            }
            if (Utils.e(a.getCodiceFiscale())) {
                a.setCodiceFiscale(containsDefault(conf.getCodiceFiscale()));
            }
            if (Utils.e(a.getDesProvinciaIscrizione())) {
                a.setDesProvinciaIscrizione(containsDefault(conf.getProvinciaAlbo()));
            }
            if (Utils.e(a.getDesTipoCollegio())) {
                a.setDesTipoCollegio(containsDefault(conf.getDesAlbo()));
            }
            if (Utils.e(a.getIdTipoCollegio())) {
                String value = containsDefault(conf.getIdTipoAlbo());
                if (!Utils.e(value)) {
                    a.setIdTipoCollegio(new BigInteger(value));
                }
            }
            if (Utils.e(a.getDataNascita())) {
                String value = containsDefault(conf.getDataNascita());
                if (!Utils.e(value)) {
                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                    Date data = df.parse(value);
                    a.setDataNascita(Utils.dateToXmlGregorianCalendar(data));
                }
            }
            if (Utils.e(a.getTipoAnagrafica())) {
                a.setTipoAnagrafica(containsDefault(conf.getTipoAnagrafica()));
            }

            if (a.getIdTipoCollegio() != null || !Utils.e(a.getDesTipoCollegio())) {
                if (a.getIdTipoCollegio() != null) {
                    LkTipoCollegio tc = lookupDao.findLookupTipoCollegioById(Utils.ib(a.getIdTipoCollegio()));
                    if (tc != null) {
                        a.setDesTipoCollegio(tc.getDescrizione());
                    }
                }
            }

            a.setCounter(BigInteger.valueOf(contaAnagrafiche + 100));
            anagraficheXml.setPec(pec);
            a.setDaRubrica("N");
            anagraficheXml.setAnagrafica(a);
        }
        //Se non e' popolata l'anagrafica, non l'aggiungo alla lista perche' non ho modo di gestirla in cross
        //Vorrebbe dire che non ha un almeno il codice fiscale
        if (anagraficheXml.getAnagrafica() != null) {
            // aggiungo i dati di tipologia anagrafica
            for (CampoHrefType campo : dichiarazione.getCampiHref().getCampoHrefArray()) {
                if (containsString(campo.getNome(), conf.getIdTipoRuolo(), counter, sezione)) {
                    String valueDecodificato = getValoreCampo(conf.getIdTipoRuolo(), campo, counter, sezione);
                    if (anagraficheXml.getIdTipoRuolo() == null) {
                        if (!Utils.e(valueDecodificato)) {
                            anagraficheXml.setIdTipoRuolo(new BigInteger(valueDecodificato));
                        }
                    }
                }
                if (containsString(campo.getNome(), conf.getCodTipoRuolo(), counter, sezione)) {
                    String valueDecodificato = getValoreCampo(conf.getCodTipoRuolo(), campo, counter, sezione);
                    if (Utils.e(anagraficheXml.getCodTipoRuolo())) {
                        anagraficheXml.setCodTipoRuolo(valueDecodificato);
                    }
                }
                if (containsString(campo.getNome(), conf.getDesTipoRuolo(), counter, sezione)) {
                    String valueDecodificato = getValoreCampo(conf.getDesTipoRuolo(), campo, counter, sezione);
                    if (Utils.e(anagraficheXml.getDesTipoRuolo())) {
                        anagraficheXml.setDesTipoRuolo(valueDecodificato);
                    }
                }
                if (containsString(campo.getNome(), conf.getIdTipoQualifica(), counter, sezione)) {
                    String valueDecodificato = getValoreCampo(conf.getIdTipoQualifica(), campo, counter, sezione);
                    if (anagraficheXml.getIdTipoQualifica() == null) {
                        if (!Utils.e(valueDecodificato)) {
                            if (valueDecodificato.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
                                LkTipoQualifica lk = lookupDao.findTipoQualificaById(Integer.parseInt(valueDecodificato));
                                if (lk != null) {
                                    anagraficheXml.setIdTipoQualifica(new BigInteger(valueDecodificato));
                                    anagraficheXml.setDesTipoQualifica(lk.getDescrizione());
                                }
                            }
                        }
                    }
                }
                if (containsString(campo.getNome(), conf.getDesTipoQualifica(), counter, sezione)) {
                    String valueDecodificato = getValoreCampo(conf.getDesTipoQualifica(), campo, counter, sezione);
                    if (Utils.e(anagraficheXml.getDesTipoQualifica())) {
                        anagraficheXml.setDesTipoQualifica(valueDecodificato);
                    }
                }
            }
            if (Utils.e(anagraficheXml.getIdTipoRuolo())) {
                String value = containsDefault(conf.getIdTipoRuolo());
                if (!Utils.e(value)) {
                    anagraficheXml.setIdTipoRuolo(new BigInteger(value));
                }
            }
            if (Utils.e(anagraficheXml.getCodTipoRuolo())) {
                anagraficheXml.setCodTipoRuolo(containsDefault(conf.getCodTipoRuolo()));
            }
            if (Utils.e(anagraficheXml.getDesTipoRuolo())) {
                anagraficheXml.setDesTipoRuolo(containsDefault(conf.getDesTipoRuolo()));
            }
            if (Utils.e(anagraficheXml.getIdTipoQualifica())) {
                String value = containsDefault(conf.getIdTipoQualifica());
                if (!Utils.e(value)) {
                    anagraficheXml.setIdTipoQualifica(new BigInteger(value));
                }
            }
            if (Utils.e(anagraficheXml.getDesTipoQualifica())) {
                anagraficheXml.setDesTipoQualifica(containsDefault(conf.getDesTipoQualifica()));
            }
        }
        if (!Utils.e(conf.getGroovyScript())) {
            String scriptAnagraficaGroovy = Utils.decodeB64(conf.getGroovyScript());
            GroovyShell gs = new GroovyShell();
            Script script = gs.parse(scriptAnagraficaGroovy);
            Binding binding = new Binding();
            binding.setVariable("anagrafica", anagraficheXml);
            binding.setVariable("configurazione", conf);
            binding.setVariable("dichiarazione", dichiarazione);
            binding.setVariable("counter", counter);
            binding.setVariable("sezione", sezione);
            binding.setVariable("configuration", conf);
            script.invokeMethod("updateAnagrafica", binding);
        }
        return anagraficheXml;
    }

    private IndirizziIntervento getIndirizziIntervento(String xml, String idEnte) throws Exception {
        GestionePratica gp = pluginService.getGestionePratica(Integer.parseInt(idEnte));
        IndirizziIntervento indirizziIntervento = null;
        RichiestaDocument richiesta = RichiestaDocument.Factory.parse(xml);
        RichiestadiConcessioniEAutorizzazioniType cea = richiesta.getRichiesta();
        List<DichiarazioneDinamicaType> indirizzi = new ArrayList<DichiarazioneDinamicaType>();

        if (cea.getDichiarazioniDinamiche() != null && cea.getDichiarazioniDinamiche().getDichiarazioneDinamicaArray() != null
                && cea.getDichiarazioniDinamiche().getDichiarazioneDinamicaArray().length > 0) {

            DichiarazioneDinamicaType[] dichiarazioni = cea.getDichiarazioniDinamiche().getDichiarazioneDinamicaArray();
            ConfigurazioneIndirizziIntervento configurazione = getConfiguazioneIndirizziIntervento(idEnte);
            for (DichiarazioneDinamicaType dichiarazione : dichiarazioni) {
                if (configurazione.getCodiceDichiarazione() != null && configurazione.getCodiceDichiarazione().contains(dichiarazione.getHref())) {
                    indirizzi.add(dichiarazione);
                }
            }
            if (!indirizzi.isEmpty()) {
                indirizziIntervento = new IndirizziIntervento();
                BigInteger contaImmobili = new BigInteger("1");
                for (DichiarazioneDinamicaType dichiarazione : indirizzi) {
                    IndirizzoIntervento conf = getConfigurazioneIndirizzoIntervento(configurazione, dichiarazione.getHref());
                    Integer numeroSezioni = getNumeroSezioni(conf, dichiarazione.getHref());
                    if (numeroSezioni != null) {
                        int numeroImmobiliCensiti = getMolteplicita(dichiarazione);
                        for (int sezione = 0; sezione < numeroSezioni; sezione++) {
                            for (int i = 1; i < numeroImmobiliCensiti + 1; i++) {
                                it.wego.cross.xml.IndirizzoIntervento indirizzoIntervento = new it.wego.cross.xml.IndirizzoIntervento();
                                String counter = "";
                                if (i > 1) {
                                    counter = "_" + i;
                                }
                                for (CampoHrefType campo : dichiarazione.getCampiHref().getCampoHrefArray()) {
                                    if (containsString(campo.getNome(), conf.getAltreInformazioniIndirizzo(), counter, sezione)) {
                                        String valueDecodificato = getValoreCampo(conf.getAltreInformazioniIndirizzo(), campo, counter, sezione);
                                        if (Utils.e(indirizzoIntervento.getAltreInformazioniIndirizzo())) {
                                            indirizzoIntervento.setAltreInformazioniIndirizzo(valueDecodificato);
                                        }
                                    }
                                    if (containsString(campo.getNome(), conf.getLocalita(), counter, sezione)) {
                                        String valueDecodificato = getValoreCampo(conf.getLocalita(), campo, counter, sezione);
                                        if (Utils.e(indirizzoIntervento.getLocalita())) {
                                            indirizzoIntervento.setLocalita(valueDecodificato);
                                        }
                                    }
                                    if (containsString(campo.getNome(), conf.getDug(), counter, sezione)) {
                                        String valueDecodificato = getValoreCampo(conf.getDug(), campo, counter, sezione);
                                        if (indirizzoIntervento.getIdDug() == null) {
                                            if (!Utils.e(valueDecodificato)) {
                                                indirizzoIntervento.setIdDug(new BigInteger(valueDecodificato));
                                            }
                                        }
                                    }
                                    if (containsString(campo.getNome(), conf.getIndirizzo(), counter, sezione)) {
                                        String valueDecodificato = getValoreCampo(conf.getIndirizzo(), campo, counter, sezione);
                                        if (Utils.e(indirizzoIntervento.getIndirizzo())) {
                                            indirizzoIntervento.setIndirizzo(valueDecodificato);
                                        }
                                    }
                                    if (containsString(campo.getNome(), conf.getCivico(), counter, sezione)) {
                                        String valueDecodificato = getValoreCampo(conf.getCivico(), campo, counter, sezione);
                                        if (Utils.e(indirizzoIntervento.getCivico())) {
                                            indirizzoIntervento.setCivico(valueDecodificato);
                                        }
                                    }
                                    if (containsString(campo.getNome(), conf.getCap(), counter, sezione)) {
                                        String valueDecodificato = getValoreCampo(conf.getCap(), campo, counter, sezione);
                                        if (Utils.e(indirizzoIntervento.getCap())) {
                                            indirizzoIntervento.setCap(valueDecodificato);
                                        }
                                    }
                                    
                                    
                                    
                                    
                                    if (containsString(campo.getNome(), conf.getCod_via(), counter, sezione)) {
                                        String valueDecodificato = getValoreCampo(conf.getCod_via(), campo, counter, sezione);
                                        if (Utils.e(indirizzoIntervento.getCodVia())) {
                                            indirizzoIntervento.setCodVia(valueDecodificato);
                                        }
                                    }
                                    if (containsString(campo.getNome(), conf.getCod_civico(), counter, sezione)) {
                                        String valueDecodificato = getValoreCampo(conf.getCod_civico(), campo, counter, sezione);
                                        if (Utils.e(indirizzoIntervento.getCodCivico())) {
                                            indirizzoIntervento.setCodCivico(valueDecodificato);
                                        }
                                    }
                                    if (containsString(campo.getNome(), conf.getInterno_numero(), counter, sezione)) {
                                        String valueDecodificato = getValoreCampo(conf.getInterno_numero(), campo, counter, sezione);
                                        if (Utils.e(indirizzoIntervento.getInternoNumero())) {
                                            indirizzoIntervento.setInternoNumero(valueDecodificato);
                                        }
                                    }
                                    if (containsString(campo.getNome(), conf.getInterno_lettera(), counter, sezione)) {
                                        String valueDecodificato = getValoreCampo(conf.getInterno_lettera(), campo, counter, sezione);
                                        if (Utils.e(indirizzoIntervento.getInternoLettera())) {
                                            indirizzoIntervento.setInternoLettera(valueDecodificato);
                                        }
                                    }
                                    if (containsString(campo.getNome(), conf.getInterno_scala(), counter, sezione)) {
                                        String valueDecodificato = getValoreCampo(conf.getInterno_scala(), campo, counter, sezione);
                                        if (Utils.e(indirizzoIntervento.getInternoScala())) {
                                            indirizzoIntervento.setInternoScala(valueDecodificato);
                                        }
                                    }
                                    if (containsString(campo.getNome(), conf.getLettera(), counter, sezione)) {
                                        String valueDecodificato = getValoreCampo(conf.getLettera(), campo, counter, sezione);
                                        if (Utils.e(indirizzoIntervento.getLettera())) {
                                            indirizzoIntervento.setLettera(valueDecodificato);
                                        }
                                    }
                                    if (containsString(campo.getNome(), conf.getColore(), counter, sezione)) {
                                        String valueDecodificato = getValoreCampo(conf.getColore(), campo, counter, sezione);
                                        if (Utils.e(indirizzoIntervento.getColore())) {
                                            indirizzoIntervento.setColore(valueDecodificato);
                                        }
                                    }
                                    if (containsString(campo.getNome(), conf.getLatitudine(), counter, sezione)) {
                                        String valueDecodificato = getValoreCampo(conf.getLatitudine(), campo, counter, sezione);
                                        if (Utils.e(indirizzoIntervento.getLatitudine())) {
                                            indirizzoIntervento.setLatitudine(valueDecodificato);
                                        }
                                    }
                                    if (containsString(campo.getNome(), conf.getLongitudine(), counter, sezione)) {
                                        String valueDecodificato = getValoreCampo(conf.getLongitudine(), campo, counter, sezione);
                                        if (Utils.e(indirizzoIntervento.getLongitudine())) {
                                            indirizzoIntervento.setLongitudine(valueDecodificato);
                                        }
                                    }
                                    if (containsString(campo.getNome(), conf.getDatoEsteso1(), counter, sezione)) {
                                        String valueDecodificato = getValoreCampo(conf.getDatoEsteso1(), campo, counter, sezione);
                                        if (Utils.e(indirizzoIntervento.getDatoEsteso1())) {
                                            indirizzoIntervento.setDatoEsteso1(valueDecodificato);
                                        }
                                    }
                                    if (containsString(campo.getNome(), conf.getDatoEsteso2(), counter, sezione)) {
                                        String valueDecodificato = getValoreCampo(conf.getDatoEsteso2(), campo, counter, sezione);
                                        if (Utils.e(indirizzoIntervento.getDatoEsteso2())) {
                                            indirizzoIntervento.setDatoEsteso2(valueDecodificato);
                                        }
                                    }
                                    if (containsString(campo.getNome(), conf.getPiano(), counter, sezione)) {
                                        String valueDecodificato = getValoreCampo(conf.getPiano(), campo, counter, sezione);
                                        if (Utils.e(indirizzoIntervento.getPiano())) {
                                            indirizzoIntervento.setPiano(valueDecodificato);
                                        }
                                    }
                                }
                                if (!isEmpty(indirizzoIntervento)) {
                                    // loop sull'href per recuperare l'eventuale dato di default 
                                    if (Utils.e(indirizzoIntervento.getAltreInformazioniIndirizzo())) {
                                        indirizzoIntervento.setAltreInformazioniIndirizzo(containsDefault(conf.getAltreInformazioniIndirizzo(), sezione));
                                    }
                                    if (Utils.e(indirizzoIntervento.getLocalita())) {
                                        indirizzoIntervento.setLocalita(containsDefault(conf.getLocalita(), sezione));
                                    }
                                    if (Utils.e(indirizzoIntervento.getIdDug())) {
                                        String value = containsDefault(conf.getDug(), sezione);
                                        if (!Utils.e(value)) {
                                            indirizzoIntervento.setIdDug(new BigInteger(value));
                                        }
                                    }
                                    if (Utils.e(indirizzoIntervento.getIndirizzo())) {
                                        indirizzoIntervento.setIndirizzo(containsDefault(conf.getIndirizzo(), sezione));
                                    }
                                    if (Utils.e(indirizzoIntervento.getCivico())) {
                                        indirizzoIntervento.setCivico(containsDefault(conf.getCivico(), sezione));
                                    }
                                    if (Utils.e(indirizzoIntervento.getCap())) {
                                        indirizzoIntervento.setCap(containsDefault(conf.getCap(), sezione));
                                    }
                                                                                                      
                                    if (Utils.e(indirizzoIntervento.getCodVia())) {
                                        indirizzoIntervento.setCodVia(containsDefault(conf.getCod_via(), sezione));
                                    }
                                    if (Utils.e(indirizzoIntervento.getCodCivico())) {
                                        indirizzoIntervento.setCodCivico(containsDefault(conf.getCod_civico(), sezione));
                                    }
                                    if (Utils.e(indirizzoIntervento.getInternoNumero())) {
                                        indirizzoIntervento.setInternoNumero(containsDefault(conf.getInterno_numero(), sezione));
                                    }
                                    if (Utils.e(indirizzoIntervento.getInternoLettera())) {
                                        indirizzoIntervento.setInternoLettera(containsDefault(conf.getInterno_lettera(), sezione));
                                    }
                                    if (Utils.e(indirizzoIntervento.getInternoScala())) {
                                        indirizzoIntervento.setInternoScala(containsDefault(conf.getInterno_scala(), sezione));
                                    }
                                    if (Utils.e(indirizzoIntervento.getLettera())) {
                                        indirizzoIntervento.setLettera(containsDefault(conf.getLettera(), sezione));
                                    }
                                    if (Utils.e(indirizzoIntervento.getColore())) {
                                        indirizzoIntervento.setColore(containsDefault(conf.getColore(), sezione));
                                    }
                                    if (Utils.e(indirizzoIntervento.getLatitudine())) {
                                        indirizzoIntervento.setLatitudine(containsDefault(conf.getLatitudine(), sezione));
                                    }
                                    if (Utils.e(indirizzoIntervento.getLongitudine())) {
                                        indirizzoIntervento.setLongitudine(containsDefault(conf.getLongitudine(), sezione));
                                    }
                                    if (Utils.e(indirizzoIntervento.getDatoEsteso1())) {
                                        indirizzoIntervento.setDatoEsteso1(containsDefault(conf.getDatoEsteso1(), sezione));
                                    }
                                    if (Utils.e(indirizzoIntervento.getDatoEsteso2())) {
                                        indirizzoIntervento.setDatoEsteso2(containsDefault(conf.getDatoEsteso2(), sezione));
                                    }
                                    if (Utils.e(indirizzoIntervento.getPiano())) {
                                        indirizzoIntervento.setPiano(containsDefault(conf.getPiano(), sezione));
                                    }
                                    indirizzoIntervento.setCounter(contaImmobili);
                                    contaImmobili = contaImmobili.add(new BigInteger("1"));
                                    indirizzoIntervento.setConfermato("true");

                                    // effettuo la chiamta al plugin per verificare se sono rispettati i controlli di corretteza del dato
                                    List<String> errori = gp.controllaIndirizzoIntervento(indirizzoIntervento, Integer.parseInt(idEnte));
                                    if (errori != null) {
                                        indirizzoIntervento.setConfermato("false");
                                    }

                                    indirizziIntervento.getIndirizzoIntervento().add(indirizzoIntervento);
                                }
                            }
                        }
                    } else {
                        // salto dichiarazione e segnalo errore in log e tabella errori
                        String err = "Errore in ricezione pratica - caricamento indirizzi intetvento - errata definizione - dichiarazione " + dichiarazione;
                        log.error("[Ricezione Pratica]" + err);
                        ErroreDTO errore = erroriAction.getError(null, err, null, null, null);
                        erroriAction.saveError(errore);
                    }
                }
            }
        }
        return indirizziIntervento;
    }

    private ConfigurazioneDatiCatastali getConfiguazioneDatiCatastali(String idEnte) throws Exception {
        ConfigurazioneDatiCatastali conf = new ConfigurazioneDatiCatastali();

        String codici_dati_catastali = getProperty("dati_catastali", idEnte);
        if (codici_dati_catastali != null && codici_dati_catastali.length() > 0) {
            String[] dic = codici_dati_catastali.split(",");
            conf.setCodiceDichiarazione(Arrays.asList(dic));
            List<DatoCatastale> datiCatastali = new ArrayList<DatoCatastale>();
            for (String cod : conf.getCodiceDichiarazione()) {
                DatoCatastale dato = new DatoCatastale();
                dato.setDichiarazione(cod);
                dato.setCod_tipo_sistema_catastale(getProperty(cod + ".cod_tipo_sistema_catastale", idEnte));
                dato.setSezione(getProperty(cod + ".sezione", idEnte));
                dato.setCod_tipo_unita(getProperty(cod + ".cod_tipo_unita", idEnte));
                dato.setFoglio(getProperty(cod + ".foglio", idEnte));
                dato.setMappale(getProperty(cod + ".mappale", idEnte));
                dato.setCod_tipo_particella(getProperty(cod + ".cod_tipo_particella", idEnte));
                dato.setEstensione_particella(getProperty(cod + ".estensione_particella", idEnte));
                dato.setSubalterno(getProperty(cod + ".subalterno", idEnte));
                dato.setComuneCensuario(getProperty(cod + ".comune_censuario", idEnte));
                dato.setCodImmobile(getProperty(cod + ".cod_immobile", idEnte));
                
                //Aggiunto il 22/06/2016
                dato.setCategoria(getProperty(cod + ".categoria", idEnte));
                
                datiCatastali.add(dato);
            }
            conf.setDatiCatastali(datiCatastali);
        }
        return conf;
    }

    private ConfigurazioneIndirizziIntervento getConfiguazioneIndirizziIntervento(String idEnte) {
        ConfigurazioneIndirizziIntervento conf = new ConfigurazioneIndirizziIntervento();

        String codici_indirizzi_intervento = getProperty("indirizzi_intervento", idEnte);
        if (codici_indirizzi_intervento != null && codici_indirizzi_intervento.length() > 0) {
            String[] dic = codici_indirizzi_intervento.split(",");
            conf.setCodiceDichiarazione(Arrays.asList(dic));
            List<IndirizzoIntervento> indirizziIntervento = new ArrayList<IndirizzoIntervento>();
            for (String cod : conf.getCodiceDichiarazione()) {
                IndirizzoIntervento dato = new IndirizzoIntervento();
                dato.setDichiarazione(cod);
                dato.setAltreInformazioniIndirizzo(getProperty(cod + ".altre_informazioni_indirizzo", idEnte));
                dato.setCap(getProperty(cod + ".cap", idEnte));
                dato.setCivico(getProperty(cod + ".civico", idEnte));
                dato.setCod_civico(getProperty(cod + ".cod_civico", idEnte));
                dato.setCod_via(getProperty(cod + ".cod_via", idEnte));
                dato.setColore(getProperty(cod + ".colore", idEnte));
                dato.setDug(getProperty(cod + ".dug", idEnte));
                dato.setIndirizzo(getProperty(cod + ".indirizzo", idEnte));
                dato.setInterno_lettera(getProperty(cod + ".interno_lettera", idEnte));
                dato.setInterno_numero(getProperty(cod + ".interno_numero", idEnte));
                dato.setInterno_scala(getProperty(cod + ".interno_scala", idEnte));
                dato.setLettera(getProperty(cod + ".lettera", idEnte));
                dato.setLocalita(getProperty(cod + ".localita", idEnte));
                dato.setLatitudine(getProperty(cod + ".latitudine", idEnte));
                dato.setLongitudine(getProperty(cod + ".longitudine", idEnte));
                dato.setDatoEsteso1(getProperty(cod + ".dato_esteso_1", idEnte));
                dato.setDatoEsteso2(getProperty(cod + ".dato_esteso_2", idEnte));
                dato.setPiano(getProperty(cod + ".piano", idEnte));                                  
                indirizziIntervento.add(dato);
            }
            conf.setIndirizzoIntervento(indirizziIntervento);
        }
        return conf;
    }

    private IndirizzoIntervento getConfigurazioneIndirizzoIntervento(ConfigurazioneIndirizziIntervento configurazione, String href) {
        IndirizzoIntervento result = null;
        for (IndirizzoIntervento dato : configurazione.getIndirizzoIntervento()) {
            if (dato.getDichiarazione().equalsIgnoreCase(href)) {
                result = dato;
                break;
            }
        }
        return result;
    }

    private static String getValoreListbox(CampoHrefType campo, String value) {
        String ret = value;
        if (!Utils.e(value)) {
            if (campo.getTipo().equals("L")) {
                if (campo.getOpzioniCombo() != null && campo.getOpzioniCombo().sizeOfOpzioneComboArray() > 0) {
                    for (int i = 0; i < campo.getOpzioniCombo().getOpzioneComboArray().length; i++) {
                        if (value.equals(campo.getOpzioniCombo().getOpzioneComboArray(i).getCodice())) {
                            ret = campo.getOpzioniCombo().getOpzioneComboArray(i).getEtichetta();
                            break;
                        }
                    }
                }
            }
        }
        return ret;
    }

    private void eliminaDuplicatiAnagrafiche(Pratica praticaCross) {
        if (praticaCross != null) {
            if (praticaCross.getAnagrafiche() != null && !praticaCross.getAnagrafiche().isEmpty()) {
                List<Anagrafiche> copyAnagrafiche = new ArrayList<Anagrafiche>();
                for (Anagrafiche a : praticaCross.getAnagrafiche()) {
                    findAnagrafica(a, copyAnagrafiche);
                }
                praticaCross.setAnagrafiche(copyAnagrafiche);
            }
        }
    }

    private void findAnagrafica(Anagrafiche a, List<Anagrafiche> copyAnagrafiche) {
        if (copyAnagrafiche.isEmpty()) {
            copyAnagrafiche.add(a);
        } else {
            String codFiscale = a.getAnagrafica().getCodiceFiscale();
            BigInteger tipoRuolo = a.getIdTipoRuolo();
            String codTipoRuolo = a.getCodTipoRuolo();
            String desTipoRuolo = a.getDesTipoRuolo();
            boolean trovato = false;
            for (Anagrafiche copyAna : copyAnagrafiche) {
                if (copyAna.getAnagrafica().getCodiceFiscale().equalsIgnoreCase(codFiscale)) {
                    if (!Utils.e(codTipoRuolo) && !Utils.e(copyAna.getCodTipoRuolo()) && codTipoRuolo.equalsIgnoreCase(copyAna.getCodTipoRuolo())) {
                        trovato = true;
                        break;
                    } else if (!Utils.e(tipoRuolo) && !Utils.e(copyAna.getIdTipoRuolo()) && tipoRuolo.equals(copyAna.getIdTipoRuolo())) {
                        trovato = true;
                        break;
                    } else if (!Utils.e(desTipoRuolo) && !Utils.e(copyAna.getDesTipoRuolo()) && desTipoRuolo.equalsIgnoreCase(copyAna.getDesTipoRuolo())) {
                        trovato = true;
                        break;
                    }
                }
            }
            if (!trovato) {
                copyAnagrafiche.add(a);
            }
        }
    }
}
