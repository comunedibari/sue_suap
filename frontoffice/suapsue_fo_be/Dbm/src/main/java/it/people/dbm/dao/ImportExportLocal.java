/**
 * Copyright (c) 2011, Regione Emilia-Romagna, Italy
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they will be approved by
 * the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 *
 * For convenience a plain text copy of the English version of the Licence can
 * be found in the file LICENCE.txt in the top-level directory of this software
 * distribution.
 *
 * You may obtain a copy of the Licence in any of 22 European Languages at:
 *
 * http://www.osor.eu/eupl
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" basis, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * Licence for the specific language governing permissions and limitations under
 * the Licence.
 *
 */
package it.people.dbm.dao;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

import it.people.dbm.model.Utente;
import it.people.dbm.utility.Configuration;
import it.people.dbm.utility.Utility;
import it.people.dbm.ws.AllegatoType;
import it.people.dbm.ws.AllegatoType.Documento;
import it.people.dbm.ws.AllegatoType.Documento.Dichiarazione;
import it.people.dbm.ws.AllegatoType.Documento.Dichiarazione.Campi;
import it.people.dbm.ws.AllegatoType.Documento.Dichiarazione.Campi.Campo;
import it.people.dbm.ws.AllegatoType.Documento.Dichiarazione.ValoriListBox;
import it.people.dbm.ws.AllegatoType.Documento.Dichiarazione.ValoriListBox.ValoreListBox;
import it.people.dbm.ws.CondizioneType;
import it.people.dbm.ws.CudType;
import it.people.dbm.ws.DescrizioneType;
import it.people.dbm.ws.DocumentRoot;
import it.people.dbm.ws.DocumentRoot.InterventiCollegati;
import it.people.dbm.ws.DocumentoType;
import it.people.dbm.ws.InterventoType;
import it.people.dbm.ws.InterventoType.Allegati;
import it.people.dbm.ws.InterventoType.Normative;
import it.people.dbm.ws.InterventoType.Normative.Normativa;
import it.people.dbm.ws.InterventoType.Procedimento;
import it.people.dbm.ws.TipoNormativa;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.LoggerFactory;
/**
 *
 * @author Piergiorgio
 */
public class ImportExportLocal {

    private static Logger log = LoggerFactory.getLogger(ImportExportLocal.class);
    private String scelta;
    private Integer codIntNew;
    private String duplicazioneLocale = "1";
    private String importazioneDaXml = "3";
    private JSONObject json = null;
    private HashMap<String, HashMap<String, String>> importXMLVariables;
    private String aggregazione;
    private String nameSpace = "http://people.dbm.it";
    private boolean caricaLingua = false;
    private List<String> listaLingue;
    private HashMap<String, ArrayList<String>> importExcludes;
    private HashMap<String, HashMap<String, String>> importExistences;

    public String caricaDocumento(String codInt, String tipoScelta, String codCom, boolean collegati, boolean lingua) throws Exception {
        caricaLingua = lingua;
        String xml = null;
        Connection conn = null;
        try {
            conn = Utility.getConnection();
            conn.setAutoCommit(false);
            codIntNew = null;
            scelta = tipoScelta;
            DocumentRoot doc = new DocumentRoot();
            InterventoType in = new InterventoType();
            caricaIntervento(in, codInt, codCom, conn);
            doc.setIntervento(in);
            if (collegati) {
                caricaInterventiCollegati(doc, codInt, codCom, conn);
            }
            JAXBContext jc = JAXBContext.newInstance(DocumentRoot.class); // JAXBContext.newInstance("pippo");
            Marshaller m = jc.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            m.marshal(doc, os);
            xml = os.toString("UTF-8");
            conn.commit();
        } catch (Exception e) {
            log.error("Carica documento ");
            conn.rollback();
            throw e;
        } catch (Throwable t) {
            log.error("Errore non catturabile", t);
            conn.rollback();
        } finally {
            Utility.chiusuraJdbc(conn);
        }
        return xml;
    }

    public Boolean caricaDocumento(String codInt, String tipoScelta, String interventoNuovo, String codCom, HttpServletRequest request, boolean collegati, boolean lingua)
            throws Exception {
        caricaLingua = lingua;
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        aggregazione = utente.getTipAggregazione();
        String xml = null;
        Connection conn = null;
        try {
            conn = Utility.getConnection();
            listaLingue = cercaLingue(aggregazione, conn);
            conn.setAutoCommit(false);
            scelta = tipoScelta;
            codIntNew = Integer.valueOf(interventoNuovo);
            DocumentRoot doc = new DocumentRoot();
            InterventoType in = new InterventoType();
            caricaIntervento(in, codInt, codCom, conn);
            doc.setIntervento(in);
            if (collegati) {
                caricaInterventiCollegati(doc, codInt, codCom, conn);
            }
            JAXBContext jc = JAXBContext.newInstance(DocumentRoot.class); // JAXBContext.newInstance("pippo");
            Marshaller m = jc.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            m.marshal(doc, os);
            xml = os.toString("UTF-8");
            scriviDati(xml, null, conn);
            conn.commit();
        } catch (Exception e) {
            log.error("Carica documento ", e);
            conn.rollback();
            throw e;
        } finally {
            Utility.chiusuraJdbc(conn);
        }
        return true;
    }

    public JSONObject verificaDati(HttpServletRequest request, String xml, boolean collegati)
            throws Exception {
        HttpSession session = request.getSession();
        session.setAttribute("ERRORIIMPORT", null);
        Utente utente = (Utente) session.getAttribute("utente");
        aggregazione = utente.getTipAggregazione();
        importXMLVariables = (HashMap<String, HashMap<String, String>>) request.getSession().getAttribute("newVariables");
        if (importXMLVariables == null) {
            importXMLVariables = new HashMap<String, HashMap<String, String>>();

            importXMLVariables.put("interventi", new HashMap<String, String>());
            importXMLVariables.put("procedimenti", new HashMap<String, String>());
            importXMLVariables.put("cud", new HashMap<String, String>());
            importXMLVariables.put("documenti", new HashMap<String, String>());
            importXMLVariables.put("dichiarazioni", new HashMap<String, String>());
            importXMLVariables.put("testo_condizioni", new HashMap<String, String>());
            importXMLVariables.put("normative", new HashMap<String, String>());
            importXMLVariables.put("tipi_rif", new HashMap<String, String>());

            request.getSession().setAttribute("newVariables",
                    importXMLVariables);
        }
        initExcludes(request);
        initExistences(request);

        json = new JSONObject();
        JSONObject jsonIntervento = new JSONObject();
        JAXBContext jc = JAXBContext.newInstance(DocumentRoot.class);
        Unmarshaller um = jc.createUnmarshaller();
        InputStream is = new ByteArrayInputStream(xml.getBytes("UTF-8"));
        DocumentRoot document = (DocumentRoot) um.unmarshal(is);
        document.getIntervento();
        verificaInterventi(jsonIntervento, document.getIntervento(), document, collegati);
        json.put("success", jsonIntervento);
        return json;
    }

    private void initExcludes(HttpServletRequest request) {
        importExcludes = (HashMap<String, ArrayList<String>>) request.getSession().getAttribute("excludes");
        if (importExcludes == null) {
            importExcludes = new HashMap<String, ArrayList<String>>();

            importExcludes.put("interventi", new ArrayList<String>());
            importExcludes.put("procedimenti", new ArrayList<String>());
            importExcludes.put("cud", new ArrayList<String>());
            importExcludes.put("documenti", new ArrayList<String>());
            importExcludes.put("dichiarazioni", new ArrayList<String>());
            importExcludes.put("testo_condizioni", new ArrayList<String>());
            importExcludes.put("normative", new ArrayList<String>());
            importExcludes.put("tipi_rif", new ArrayList<String>());

            request.getSession().setAttribute("excludes", importExcludes);
        }
    }

    private void initExistences(HttpServletRequest request) {
        importExistences = (HashMap<String, HashMap<String, String>>) request.getSession().getAttribute("existences");
        if (importExistences == null) {
            importExistences = new HashMap<String, HashMap<String, String>>();

            importExistences.put("interventi", new HashMap<String, String>());
            importExistences.put("procedimenti", new HashMap<String, String>());
            importExistences.put("cud", new HashMap<String, String>());
            importExistences.put("documenti", new HashMap<String, String>());
            importExistences.put("dichiarazioni", new HashMap<String, String>());
            importExistences.put("testo_condizioni", new HashMap<String, String>());
            importExistences.put("normative", new HashMap<String, String>());
            importExistences.put("tipi_rif", new HashMap<String, String>());

            request.getSession().setAttribute("existences", importExistences);
        }
    }

    public void addEditableColumn(JSONObject json, String value)
            throws Exception {
        json.put("new_code", value);
        if (value != null && !"".equals(value)) {
            json.put("change", true);
        } else {
            json.put("change", false);
        }
    }

    public void scriviDati(String xml, String tipoScelta,
            HashMap<String, HashMap<String, String>> importXMLVariables,
            HttpServletRequest request, boolean collegati, HashMap<String, ArrayList<String>> importExcl) throws Exception {
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        aggregazione = utente.getTipAggregazione();
        Connection conn = null;
        boolean ok = true;
        Object tmp = request.getAttribute("ERRORIIMPORT");
        Map<String, Map<String, String>> errori = (Map<String, Map<String, String>>) session.getAttribute("ERRORIIMPORT");
        if (errori != null) {
            for (String s : errori.keySet()) {
                Map<String, String> singoloErrore = errori.get(s);
                for (String st : singoloErrore.keySet()) {
                    if (singoloErrore.get(st).equalsIgnoreCase("X")) {
                        ok = false;
                    }
                }
            }
        }
        if (ok) {
            try {
                scelta = tipoScelta;
                codIntNew = null;
                conn = Utility.getConnection();
                conn.setAutoCommit(false);
                listaLingue = cercaLingue(aggregazione, conn);
                importExcludes = importExcl;
                initExistences(request);
                scriviDati(xml, importXMLVariables, conn);
                conn.commit();
            } catch (Exception e) {
                log.error("Carica documento ", e);
                conn.rollback();
                throw e;
            } finally {
                Utility.chiusuraJdbc(conn);
            }
        } else {
            throw new Exception(
                    "Non posso caricare, conflitti di profilazione territoriale");
        }

    }

    public void scriviDati(String xml,
            HashMap<String, HashMap<String, String>> importXMLVariables,
            Connection conn) throws Exception {
        JAXBContext jc = JAXBContext.newInstance(DocumentRoot.class);
        Unmarshaller um = jc.createUnmarshaller();
        InputStream is = new ByteArrayInputStream(xml.getBytes("UTF-8"));
        DocumentRoot doc = (DocumentRoot) um.unmarshal(is);
        updateDoc(doc, importXMLVariables);
        scriviInterventi(doc.getIntervento(), conn);
        scriviInterventiCollegati(doc.getInterventiCollegati(), conn, codIntNew);
    }

    public void verificaInterventi(JSONObject jsonIntervento, InterventoType in, DocumentRoot doc, boolean collegati)
            throws Exception {
        Connection conn = null;
        JSONObject ji = new JSONObject();
        JSONArray jcod = new JSONArray();
        JSONObject jsonInt = new JSONObject();

        try {
            conn = Utility.getConnection();
            conn.setAutoCommit(false);
            verificaIntervento(jsonInt, in, conn);
            verificaProcedimento(jsonInt, in.getProcedimento(), conn);
            verificaAllegati(jsonInt, in.getAllegati(), conn);
            verificaNormativeInterventi(jsonInt, in.getNormative(), conn);
            jcod.add(in.getCodice().toString());
            if (collegati) {
                verificaInterventiCollegati(ji, jcod, doc.getInterventiCollegati(), conn);
            }
            ji.put(in.getCodice().toString(), jsonInt);
            jsonIntervento.put("interventi", ji);
            jsonIntervento.put("interventiCodici", jcod);
            conn.commit();
        } catch (Exception e) {
            log.error("Errore verifica interventi ", e);
            conn.rollback();
            throw e;
        } finally {
            Utility.chiusuraJdbc(conn);
        }
    }

    public void verificaIntervento(JSONObject jsonIntervento, InterventoType in, Connection conn)
            throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        String cod_int = null;
        JSONObject ji = new JSONObject();
        HashMap<String, String> interventi = importXMLVariables.get("interventi");
        HashMap<String, String> intExistences = importExistences.get("interventi");
        ji.put("codExt", in.getCodice().toString());
        String descrizione = cercaDescrizione(in.getDescrizione(), "it");
        if (descrizione != null) {
            descrizione = StringEscapeUtils.escapeXml(descrizione).replaceAll("&quot;", "\"");
        }
        ji.put("desExt", descrizione);
        ji.put("exclude", "");
        try {
            ji.put("esistenza", "N");
            String sql = "select a.cod_int, b.tit_int, a.tip_aggregazione from interventi a join interventi_testi b on a.cod_int = b.cod_int where a.cod_int=? and b.cod_lang='it'";
            st = conn.prepareStatement(sql);
            st.setInt(1, in.getCodice().intValue());
            rs = st.executeQuery();
            while (rs.next()) {
                ji.put("esistenza", "D");
                addEditableColumn(ji, Integer.toString(rs.getInt("cod_int")));
                cod_int = Integer.toString(rs.getInt("cod_int"));
                ji.put("codInt", cod_int);
                descrizione = rs.getString("tit_int");
                if (descrizione != null) {
                    descrizione = StringEscapeUtils.escapeXml(descrizione).replaceAll("&quot;", "\"");
                }
                ji.put("desInt", descrizione);
                interventi.put(cod_int, cod_int);
            }
            JSONArray ja = new JSONArray();
            ja.add(ji);
            jsonIntervento.put("interventi", ja);
        } catch (Exception e) {
            log.error("Errore verifica intervento ", e);
            throw e;
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
        }
        intExistences.put(ji.getString("codExt"), ji.getString("esistenza"));
    }

    public void verificaProcedimento(JSONObject jsonIntervento, Procedimento pr, Connection conn)
            throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        String cod_proc = null;
        HashMap<String, String> procedimenti = importXMLVariables.get("procedimenti");
        HashMap<String, String> procExistences = importExistences.get("procedimenti");
        JSONObject jp = new JSONObject();
        jp.put("procExt", pr.getCodice());
        String descrizione = cercaDescrizione(pr.getDescrizione(), "it");
        if (descrizione != null) {
            descrizione = StringEscapeUtils.escapeXml(descrizione).replaceAll("&quot;", "\"");
        }
        jp.put("desExt", descrizione);
        jp.put("exclude", "");
        try {
            verificaCud(jsonIntervento, pr.getCud(), conn);
            jp.put("esistenza", "N");
            String sql = "select a.cod_proc, b.tit_proc from procedimenti a join procedimenti_testi b on a.cod_proc=b.cod_proc where a.cod_proc=? and b.cod_lang='it'";
            st = conn.prepareStatement(sql);
            st.setString(1, pr.getCodice());
            rs = st.executeQuery();
            while (rs.next()) {
                cod_proc = rs.getString("cod_proc");
                addEditableColumn(jp, cod_proc);
                procedimenti.put(cod_proc, cod_proc);
                jp.put("procInt", cod_proc);
                descrizione = rs.getString("tit_proc");
                if (descrizione != null) {
                    descrizione = StringEscapeUtils.escapeXml(descrizione).replaceAll("&quot;", "\"");
                }
                jp.put("desInt", descrizione);
                jp.put("esistenza", "D");

            }
            JSONArray ja = new JSONArray();
            ja.add(jp);
            jsonIntervento.put("procedimenti", ja);
        } catch (Exception e) {
            log.error("Errore verifica procedimento ", e);
            throw e;
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
        }
        procExistences.put(jp.getString("procExt"), jp.getString("esistenza"));
    }

    public void verificaCud(JSONObject jsonIntervento, CudType cu, Connection conn) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        String cod_cud = null;
        HashMap<String, String> cud = importXMLVariables.get("cud");
        HashMap<String, String> cudExistences = importExistences.get("cud");
        JSONObject jc = new JSONObject();
        jc.put("cudExt", cu.getCodice());
        String descrizione = cercaDescrizione(cu.getDescrizione(), "it");
        if (descrizione != null) {
            descrizione = StringEscapeUtils.escapeXml(descrizione).replaceAll("&quot;", "\"");
        }
        jc.put("desExt", descrizione);
        jc.put("exclude", "");
        try {
            jc.put("esistenza", "N");
            String sql = "select a.cod_cud, b.des_cud from cud a join cud_testi b on a.cod_cud=b.cod_cud where a.cod_cud=? and b.cod_lang='it'";
            st = conn.prepareStatement(sql);
            st.setString(1, cu.getCodice());
            rs = st.executeQuery();
            while (rs.next()) {
                cod_cud = rs.getString("cod_cud");
                addEditableColumn(jc, cod_cud);
                cud.put(cod_cud, cod_cud);
                jc.put("cudInt", cod_cud);
                descrizione = rs.getString("des_cud");
                if (descrizione != null) {
                    descrizione = StringEscapeUtils.escapeXml(descrizione).replaceAll("&quot;", "\"");
                }
                jc.put("desInt", descrizione);
                jc.put("esistenza", "D");
            }
            JSONArray ja = new JSONArray();
            ja.add(jc);
            jsonIntervento.put("cud", ja);
        } catch (Exception e) {
            log.error("Errore verifica cud ", e);
            throw e;
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
        }
        cudExistences.put(jc.getString("cudExt"), jc.getString("esistenza"));
    }

    public void verificaAllegati(JSONObject jsonIntervento,
            Allegati al,
            Connection conn) throws Exception {
        JSONArray jsonArrayDocumenti = new JSONArray();
        JSONArray jsonArrayDichiarazioni = new JSONArray();
        JSONArray jsonArrayCondizioni = new JSONArray();
        for (int i = 0; i < al.getAllegato().size(); i++) {
            verificaAllegato(jsonIntervento, jsonArrayDocumenti, jsonArrayDichiarazioni, jsonArrayCondizioni, al.getAllegato().get(i), conn);
        }
        jsonIntervento.put("documenti", jsonArrayDocumenti);
        jsonIntervento.put("dichiarazioni", jsonArrayDichiarazioni);
        jsonIntervento.put("testo_condizioni", jsonArrayCondizioni);
    }

    public void verificaAllegato(JSONObject jsonIntervento, JSONArray jsonArrayDocumenti, JSONArray jsonArrayDichiarazioni, JSONArray jsonArrayCondizioni, AllegatoType at, Connection conn)
            throws Exception {
        verificaDocumento(jsonIntervento, jsonArrayDocumenti, jsonArrayDichiarazioni, jsonArrayCondizioni, at.getDocumento(), conn);
        if (at.getCondizione().getCodice() != null) {
            verificaCondizione(jsonIntervento, jsonArrayCondizioni, at.getCondizione(), conn);
        }
    }

    public void verificaDocumento(JSONObject jsonIntervento, JSONArray jsonArrayDocumenti, JSONArray jsonArrayDichiarazioni, JSONArray jsonArrayCondizioni, Documento doc,
            Connection conn)
            throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        HashMap<String, String> documenti = importXMLVariables.get("documenti");
        HashMap<String, String> docExistences = importExistences.get("documenti");
        JSONObject jd = new JSONObject();
        jd.put("docExt", doc.getCodice());
        String descrizione = cercaDescrizione(doc.getTitolo(), "it");
        if (descrizione != null) {
            descrizione = StringEscapeUtils.escapeXml(descrizione).replaceAll("&quot;", "\"");
        }
        jd.put("desExt", descrizione);
        jd.put("exclude", "");
        String cod_doc = null;
        if (doc.getDichiarazione() != null) {
            verificaDichiarazione(jsonIntervento, jsonArrayDichiarazioni, doc.getDichiarazione(), conn);
        }
        try {
            jd.put("esistenza", "N");
            String sql = "select a.cod_doc, b.tit_doc from documenti a join documenti_testi b on a.cod_doc=b.cod_doc where a.cod_doc=? and b.cod_lang='it'";
            st = conn.prepareStatement(sql);
            st.setString(1, doc.getCodice());
            rs = st.executeQuery();
            while (rs.next()) {
                cod_doc = rs.getString("cod_doc");
                addEditableColumn(jd, cod_doc);
                documenti.put(cod_doc, cod_doc);
                jd.put("docInt", cod_doc);
                descrizione = rs.getString("tit_doc");
                if (descrizione != null) {
                    descrizione = StringEscapeUtils.escapeXml(descrizione).replaceAll("&quot;", "\"");
                }
                jd.put("desInt", descrizione);
                jd.put("esistenza", "D");
            }
            jsonArrayDocumenti.add(jd);
        } catch (Exception e) {
            log.error("Errore verifica documento ", e);
            throw e;
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
        }
        docExistences.put(jd.getString("docExt"), jd.getString("esistenza"));
    }

    public void verificaDichiarazione(JSONObject jsonIntervento, JSONArray jsonArrayDichiarazioni, Dichiarazione doc,
            Connection conn)
            throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        HashMap<String, String> dichiarazioni = importXMLVariables.get("dichiarazioni");
        HashMap<String, String> dichExistences = importExistences.get("dichiarazioni");
        JSONObject jd = new JSONObject();
        jd.put("dicExt", doc.getCodice());
        String descrizione = cercaDescrizione(doc.getDescrizione(), "it");
        if (descrizione != null) {
            descrizione = StringEscapeUtils.escapeXml(descrizione).replaceAll("&quot;", "\"");
        }
        jd.put("desExt", descrizione);
        jd.put("exclude", "");
        String href = null;
        try {
            jd.put("esistenza", "N");
            String sql = "select a.href, b.tit_href from href a join href_testi b on a.href=b.href where a.href=? and b.cod_lang='it'";
            st = conn.prepareStatement(sql);
            st.setString(1, doc.getCodice());
            rs = st.executeQuery();
            while (rs.next()) {
                href = rs.getString("href");
                addEditableColumn(jd, href);
                dichiarazioni.put(href, href);
                jd.put("dicInt", href);
                descrizione = rs.getString("tit_href");
                if (descrizione != null) {
                    descrizione = StringEscapeUtils.escapeXml(descrizione).replaceAll("&quot;", "\"");
                }
                jd.put("desInt", descrizione);
                jd.put("esistenza", "D");
            }
            jsonArrayDichiarazioni.add(jd);
        } catch (Exception e) {
            log.error("Errore verifica dichiarazione ", e);
            throw e;
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
        }
        dichExistences.put(jd.getString("dicExt"), jd.getString("esistenza"));
    }

    public void verificaCondizione(JSONObject jsonIntervento, JSONArray jsonArrayCondizioni, CondizioneType doc, Connection conn)
            throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        HashMap<String, String> condizioni = importXMLVariables.get("testo_condizioni");
        HashMap<String, String> condExistences = importExistences.get("testo_condizioni");
        JSONObject jd = new JSONObject();
        jd.put("condExt", doc.getCodice());
        String descrizione = cercaDescrizione(doc.getDescrizione(), "it");
        if (descrizione != null) {
            descrizione = StringEscapeUtils.escapeXml(descrizione).replaceAll("&quot;", "\"");
        }
        jd.put("desExt", descrizione);
        jd.put("exclude", "");
        String cod_cond = null;
        try {
            jd.put("esistenza", "N");
            String sql = "select cod_cond,testo_cond from testo_condizioni where cod_cond=? and cod_lang='it'";
            st = conn.prepareStatement(sql);
            st.setString(1, doc.getCodice());
            rs = st.executeQuery();
            while (rs.next()) {
                cod_cond = rs.getString("cod_cond");
                addEditableColumn(jd, cod_cond);
                condizioni.put(cod_cond, cod_cond);
                jd.put("condInt", rs.getString("cod_cond"));
                descrizione = rs.getString("testo_cond");
                if (descrizione != null) {
                    descrizione = StringEscapeUtils.escapeXml(descrizione).replaceAll("&quot;", "\"");
                }
                jd.put("desInt", descrizione);
                jd.put("esistenza", "D");
            }

            jsonArrayCondizioni.add(jd);
        } catch (Exception e) {
            log.error("Errore verifica condizione ", e);
            throw e;
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
        }
        condExistences.put(jd.getString("condExt"), jd.getString("esistenza"));
    }

    public void verificaNormativeInterventi(JSONObject jsonIntervento,
            Normative no,
            Connection conn) throws Exception {
        JSONArray jsonArrayNormative = new JSONArray();
        JSONArray jsonArrayTipiNormative = new JSONArray();
        for (int i = 0; i < no.getNormativa().size(); i++) {
            verificaNormativa(jsonIntervento, jsonArrayNormative, jsonArrayTipiNormative, no.getNormativa().get(i), conn);
        }
        jsonIntervento.put("normative", jsonArrayNormative);
        jsonIntervento.put("tipi_rif", jsonArrayTipiNormative);
    }

    public void verificaNormativa(JSONObject jsonIntervento, JSONArray jsonArrayNormative, JSONArray jsonArrayTipiNormative, Normativa no, Connection conn)
            throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        HashMap<String, String> normative = importXMLVariables.get("normative");
        HashMap<String, String> normExistences = importExistences.get("normative");
        JSONObject jd = new JSONObject();
        jd.put("rifExt", no.getCodice());
        String descrizione = cercaDescrizione(no.getDescrizione(), "it");
        if (descrizione != null) {
            descrizione = StringEscapeUtils.escapeXml(descrizione).replaceAll("&quot;", "\"");
        }
        jd.put("desExt", descrizione);
        jd.put("exclude", "");
        verificaCodRif(jsonIntervento, jsonArrayTipiNormative, no.getTipoNormativa(), conn);
        String cod_rif = null;
        try {
            jd.put("esistenza", "N");
            String sql = "select  a.cod_rif, b.tit_rif from normative a join normative_testi b on a.cod_rif=b.cod_rif where a.cod_rif=? and b.cod_lang='it'";
            st = conn.prepareStatement(sql);
            st.setString(1, no.getCodice());
            rs = st.executeQuery();
            while (rs.next()) {
                cod_rif = rs.getString("cod_rif");
                normative.put(cod_rif, cod_rif);
                addEditableColumn(jd, cod_rif);
                jd.put("rifInt", rs.getString("cod_rif"));
                descrizione = rs.getString("tit_rif");
                if (descrizione != null) {
                    descrizione = StringEscapeUtils.escapeXml(descrizione).replaceAll("&quot;", "\"");
                }
                jd.put("desInt", descrizione);
                jd.put("esistenza", "D");
            }
            jsonArrayNormative.add(jd);
        } catch (Exception e) {
            log.error("Errore verifica norma ", e);
            throw e;
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
        }
        normExistences.put(jd.getString("rifExt"), jd.getString("esistenza"));
    }

    public void verificaCodRif(JSONObject jsonIntervento, JSONArray jsonArrayTipiNormative, TipoNormativa doc,
            Connection conn)
            throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        HashMap<String, String> tipo_normative = importXMLVariables.get("tipi_rif");
        HashMap<String, String> tipExistences = importExistences.get("tipi_rif");
        JSONObject jd = new JSONObject();
        jd.put("tipExt", doc.getCodice());
        String descrizione = cercaDescrizione(doc.getDescrizione(), "it");
        if (descrizione != null) {
            descrizione = StringEscapeUtils.escapeXml(descrizione).replaceAll("&quot;", "\"");
        }
        jd.put("desExt", descrizione);
        jd.put("exclude", "");
        String cod_tipo_rif = null;
        try {
            jd.put("esistenza", "N");
            if (!tipo_normative.containsKey(doc.getCodice())) {
                String sql = "select cod_tipo_rif,tipo_rif from tipi_rif where cod_tipo_rif=? and cod_lang='it'";
                st = conn.prepareStatement(sql);
                st.setString(1, doc.getCodice());
                rs = st.executeQuery();
                while (rs.next()) {
                    cod_tipo_rif = rs.getString("cod_tipo_rif");
                    addEditableColumn(jd, cod_tipo_rif);
                    tipo_normative.put(cod_tipo_rif, cod_tipo_rif);
                    jd.put("tipInt", cod_tipo_rif);
                    descrizione = rs.getString("tipo_rif");
                    if (descrizione != null) {
                        descrizione = StringEscapeUtils.escapeXml(descrizione).replaceAll("&quot;", "\"");
                    }
                    jd.put("desInt", descrizione);
                    jd.put("esistenza", "D");
                }
                jsonArrayTipiNormative.add(jd);
            }
        } catch (Exception e) {
            log.error("Errore verifica tipo norma ", e);
            throw e;
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
        }
        tipExistences.put(jd.getString("tipExt"), jd.getString("esistenza"));
    }

    public void scriviInterventi(InterventoType in, Connection conn)
            throws Exception {
        if (!scelta.equals(duplicazioneLocale)) {
            codIntNew = in.getCodice().intValue();
        }
        scriviProcedimento(in.getProcedimento(), conn);
        scriviAllegati(in.getAllegati(), conn);
        scriviNormativeInterventi(in.getNormative(), conn);
        scriviIntervento(in, conn);
        aggiornaPubblicazione(codIntNew, conn);
    }

    public void scriviIntervento(InterventoType in, Connection conn)
            throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        int righe = 0;

        if (checkExclussion(in.getCodice().toString(), "interventi")) {
            return;
        }

        try {
            String sql = "select count(*) righe from interventi where cod_int=?";
            st = conn.prepareStatement(sql);

            st.setInt(1, codIntNew);

            rs = st.executeQuery();
            while (rs.next()) {
                righe = rs.getInt("righe");
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            if (righe < 1) {
                sql = "insert into interventi (cod_int,flg_obb,cod_proc,cod_ente_origine,cod_int_origine,ordinamento,tip_aggregazione) values (?,?,?,?,?,?,?)";
                st = conn.prepareStatement(sql);
                st.setInt(1, codIntNew);
                st.setString(2, in.getFlgObbligatorio());
                st.setString(3, in.getProcedimento().getCodice());
                if (in.getCodiceEnteOrigine() != null) {
                    st.setString(4, in.getCodiceEnteOrigine().getValue());
                } else {
                    st.setNull(4, java.sql.Types.VARCHAR);
                }
                if (in.getCodiceOrigine() != null) {
                    st.setInt(5, ((Number) in.getCodiceOrigine().getValue()).intValue());
                } else {
                    st.setNull(5, java.sql.Types.INTEGER);
                }
// pc - add interventi ordinamento                
                if ((in.getOrdinamento() != null && in.getOrdinamento().equals(in.getCodice())) || in.getOrdinamento() == null) {
                    st.setInt(6, codIntNew);
                } else {
                    st.setInt(6, in.getOrdinamento().intValue());
                }
                if (aggregazione != null) {
                    st.setString(7, aggregazione);
                } else {
                    st.setNull(7, java.sql.Types.VARCHAR);
                }
                st.executeUpdate();
                Utility.chiusuraJdbc(rs);
                Utility.chiusuraJdbc(st);
                sql = "insert into interventi_testi (cod_int,tit_int,cod_lang) values (?,?,'it')";
                st = conn.prepareStatement(sql);
                st.setInt(1, codIntNew);
                st.setString(2, cercaDescrizione(in.getDescrizione(), "it"));
                st.executeUpdate();
                Utility.chiusuraJdbc(rs);
                Utility.chiusuraJdbc(st);
                if (!listaLingue.isEmpty()) {
                    String desc = null;
                    sql = "insert into interventi_testi (cod_int,tit_int,cod_lang) values (?,?,?)";
                    for (Iterator it = listaLingue.iterator(); it.hasNext();) {
                        String lng = (String) it.next();
                        desc = cercaDescrizione(in.getDescrizione(), lng);
                        if (desc != null) {
                            st = conn.prepareStatement(sql);
                            st.setInt(1, codIntNew);
                            st.setString(2, desc);
                            st.setString(3, lng);
                            st.executeUpdate();
                            Utility.chiusuraJdbc(rs);
                            Utility.chiusuraJdbc(st);
                        }

                    }
                }
            } else {
                sql = "update interventi set flg_obb=?, cod_proc=? , cod_ente_origine=?,cod_int_origine=?, tip_aggregazione=? , ordinamento = ? where cod_int=?";
                st = conn.prepareStatement(sql);
                st.setString(1, in.getFlgObbligatorio());
                st.setString(2, in.getProcedimento().getCodice());
                if (in.getCodiceEnteOrigine() != null) {
                    st.setString(3, in.getCodiceEnteOrigine().getValue());
                } else {
                    st.setNull(3, java.sql.Types.VARCHAR);
                }
                if (in.getCodiceOrigine() != null) {
                    st.setInt(4, ((Number) in.getCodiceOrigine().getValue()).intValue());
                } else {
                    st.setNull(4, java.sql.Types.INTEGER);
                }
                if (aggregazione != null) {
                    st.setString(5, aggregazione);
                } else {
                    st.setNull(5, java.sql.Types.VARCHAR);
                }
// pc - add interventi ordinamento                
                if ((in.getOrdinamento() != null && in.getOrdinamento().equals(in.getCodice())) || in.getOrdinamento() == null) {
                    st.setInt(6, codIntNew);
                } else {
                    st.setInt(6, in.getOrdinamento().intValue());
                }                
                st.setInt(7, codIntNew);
                st.executeUpdate();
                Utility.chiusuraJdbc(rs);
                Utility.chiusuraJdbc(st);
                sql = "update interventi_testi set tit_int = ? where cod_int=? and cod_lang='it'";
                st = conn.prepareStatement(sql);
                st.setString(1, cercaDescrizione(in.getDescrizione(), "it"));
                st.setInt(2, codIntNew);
                st.executeUpdate();
                Utility.chiusuraJdbc(rs);
                Utility.chiusuraJdbc(st);
                if (!listaLingue.isEmpty()) {
                    String desc = null;
                    sql = "insert into interventi_testi (cod_int,tit_int,cod_lang) values (?,?,?)";
                    String sqlUpdate = "update interventi_testi set tit_int = ? where cod_int=? and cod_lang=?";
                    String sqlSelect = "select tit_int from interventi_testi where cod_int=? and cod_lang=?";
                    for (Iterator it = listaLingue.iterator(); it.hasNext();) {
                        String lng = (String) it.next();
                        desc = cercaDescrizione(in.getDescrizione(), lng);
                        if (desc != null) {
                            st = conn.prepareStatement(sqlSelect);
                            st.setInt(1, codIntNew);
                            st.setString(2, lng);
                            rs = st.executeQuery();
                            if (rs.next()) {
                                Utility.chiusuraJdbc(rs);
                                Utility.chiusuraJdbc(st);
                                st = conn.prepareStatement(sqlUpdate);
                                st.setString(1, desc);
                                st.setInt(2, codIntNew);
                                st.setString(3, lng);
                                st.executeUpdate();
                                Utility.chiusuraJdbc(rs);
                                Utility.chiusuraJdbc(st);
                            } else {
                                Utility.chiusuraJdbc(rs);
                                Utility.chiusuraJdbc(st);
                                st = conn.prepareStatement(sql);
                                st.setInt(1, codIntNew);
                                st.setString(2, desc);
                                st.setString(3, lng);
                                st.executeUpdate();
                                Utility.chiusuraJdbc(rs);
                                Utility.chiusuraJdbc(st);
                            }
                        }

                    }
                }
            }
        } catch (Exception e) {
            log.error("Errore insert intervento", e);
            throw e;
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
        }
    }

    private boolean checkExclussion(String codice, String type) {
        return importExcludes != null && importExcludes.get(type) != null && importExcludes.get(type).contains(codice);
    }

    private boolean checkNewExistence(String codice, String type) {
        return importExistences != null && importExistences.get(type) != null && "N".equalsIgnoreCase(importExistences.get(type).get(codice));
    }

    public void scriviProcedimento(Procedimento pr, Connection conn)
            throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        int righe = 0;
        try {
            scriviCud(pr.getCud(), conn);
            if (checkExclussion(pr.getCodice().toString(), "procedimenti")) {
                return;
            }
            String sql = "select count(*) righe from procedimenti where cod_proc=?";
            st = conn.prepareStatement(sql);
            st.setString(1, pr.getCodice());
            rs = st.executeQuery();
            while (rs.next()) {
                righe = rs.getInt("righe");
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            if (righe < 1) {
                sql = "insert into procedimenti (cod_proc, cod_cud, ter_eva, flg_bollo, flg_tipo_proc, tip_aggregazione) "
                        + "values (?,?,?,?,?,?)";
                st = conn.prepareStatement(sql);
                st.setString(1, pr.getCodice());
                st.setString(2, pr.getCud().getCodice());
                st.setInt(3, pr.getTerminiEvasione().intValue());
                st.setString(4, pr.getFlgBollo());
                st.setString(5, pr.getTipoProc());
                if (aggregazione != null) {
                    st.setString(6, aggregazione);
                } else {
                    st.setNull(6, java.sql.Types.VARCHAR);
                }
                st.executeUpdate();
                Utility.chiusuraJdbc(rs);
                sql = "insert into procedimenti_testi (cod_proc, tit_proc, cod_lang) "
                        + "values (?,?,'it')";
                st = conn.prepareStatement(sql);
                st.setString(1, pr.getCodice());
                st.setString(2, cercaDescrizione(pr.getDescrizione(), "it"));
                st.executeUpdate();
                Utility.chiusuraJdbc(rs);
                Utility.chiusuraJdbc(st);
                if (!listaLingue.isEmpty()) {
                    String desc = null;
                    sql = "insert into procedimenti_testi (cod_proc, tit_proc, cod_lang) values (?,?,?)";
                    for (Iterator it = listaLingue.iterator(); it.hasNext();) {
                        String lng = (String) it.next();
                        desc = cercaDescrizione(pr.getDescrizione(), lng);
                        if (desc != null) {
                            st = conn.prepareStatement(sql);
                            st.setString(1, pr.getCodice());
                            st.setString(2, desc);
                            st.setString(3, lng);
                            st.executeUpdate();
                            Utility.chiusuraJdbc(rs);
                            Utility.chiusuraJdbc(st);
                        }

                    }
                }
            } else {
                sql = "update procedimenti set cod_cud=?, ter_eva=?, flg_bollo=?, flg_tipo_proc=?, tip_aggregazione=? where cod_proc=?";
                st = conn.prepareStatement(sql);
                st.setString(1, pr.getCud().getCodice());
                st.setInt(2, pr.getTerminiEvasione().intValue());
                st.setString(3, pr.getFlgBollo());
                st.setString(4, pr.getTipoProc());
                if (aggregazione != null) {
                    st.setString(5, aggregazione);
                } else {
                    st.setNull(5, java.sql.Types.VARCHAR);
                }
                st.setString(6, pr.getCodice());
                st.executeUpdate();
                Utility.chiusuraJdbc(rs);
                sql = "update procedimenti_testi set tit_proc=? where cod_proc=? and cod_lang='it'";
                st = conn.prepareStatement(sql);
                st.setString(1, cercaDescrizione(pr.getDescrizione(), "it"));
                st.setString(2, pr.getCodice());
                st.executeUpdate();
                Utility.chiusuraJdbc(rs);
                Utility.chiusuraJdbc(st);
                if (!listaLingue.isEmpty()) {
                    String desc = null;
                    sql = "insert into procedimenti_testi (cod_proc, tit_proc, cod_lang) values (?,?,?)";
                    String sqlUpdate = "update procedimenti_testi set tit_proc = ? where cod_proc=? and cod_lang=?";
                    String sqlSelect = "select tit_proc from procedimenti_testi where cod_proc=? and cod_lang=?";
                    for (Iterator it = listaLingue.iterator(); it.hasNext();) {
                        String lng = (String) it.next();
                        desc = cercaDescrizione(pr.getDescrizione(), lng);
                        if (desc != null) {
                            st = conn.prepareStatement(sqlSelect);
                            st.setString(1, pr.getCodice());
                            st.setString(2, lng);
                            rs = st.executeQuery();
                            if (rs.next()) {
                                Utility.chiusuraJdbc(rs);
                                Utility.chiusuraJdbc(st);
                                st = conn.prepareStatement(sqlUpdate);
                                st.setString(1, desc);
                                st.setString(2, pr.getCodice());
                                st.setString(3, lng);
                                st.executeUpdate();
                                Utility.chiusuraJdbc(rs);
                                Utility.chiusuraJdbc(st);
                            } else {
                                Utility.chiusuraJdbc(rs);
                                Utility.chiusuraJdbc(st);
                                st = conn.prepareStatement(sql);
                                st.setString(1, pr.getCodice());
                                st.setString(2, desc);
                                st.setString(3, lng);
                                st.executeUpdate();
                                Utility.chiusuraJdbc(rs);
                                Utility.chiusuraJdbc(st);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("Errore insert procedimento", e);
            throw e;
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
        }
    }

    public void scriviCud(CudType cu, Connection conn) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        int righe = 0;
        if (checkExclussion(cu.getCodice().toString(), "cud")) {
            return;
        }
        try {
            String sql = "select count(*) righe from cud where cod_cud = ?";
            st = conn.prepareStatement(sql);
            st.setString(1, cu.getCodice());
            rs = st.executeQuery();
            while (rs.next()) {
                righe = rs.getInt("righe");
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            if (righe < 1) {
                sql = "insert into cud (cod_cud) " + "values (?)";
                st = conn.prepareStatement(sql);
                st.setString(1, cu.getCodice());
                st.executeUpdate();
                Utility.chiusuraJdbc(st);
                sql = "insert into cud_testi (cod_cud, des_cud, cod_lang) "
                        + "values (?,?,'it')";
                st = conn.prepareStatement(sql);
                st.setString(1, cu.getCodice());
                st.setString(2, cercaDescrizione(cu.getDescrizione(), "it"));
                st.executeUpdate();
                Utility.chiusuraJdbc(rs);
                Utility.chiusuraJdbc(st);
                if (!listaLingue.isEmpty()) {
                    String desc = null;
                    sql = "insert into cud_testi (cod_cud, des_cud, cod_lang) values (?,?,?)";
                    for (Iterator it = listaLingue.iterator(); it.hasNext();) {
                        String lng = (String) it.next();
                        desc = cercaDescrizione(cu.getDescrizione(), lng);
                        if (desc != null) {
                            st = conn.prepareStatement(sql);
                            st.setString(1, cu.getCodice());
                            st.setString(2, desc);
                            st.setString(3, lng);
                            st.executeUpdate();
                            Utility.chiusuraJdbc(rs);
                            Utility.chiusuraJdbc(st);
                        }
                    }
                }
            } else {
                sql = "update cud_testi set des_cud = ? where cod_cud =? and cod_lang='it'";
                st = conn.prepareStatement(sql);
                st.setString(1, cercaDescrizione(cu.getDescrizione(), "it"));
                st.setString(2, cu.getCodice());
                st.executeUpdate();
                Utility.chiusuraJdbc(rs);
                Utility.chiusuraJdbc(st);
                if (!listaLingue.isEmpty()) {
                    String desc = null;
                    sql = "insert into cud_testi (cod_cud, des_cud, cod_lang) values (?,?,?)";
                    String sqlUpdate = "update cud_testi set des_cud = ? where cod_cud =? and cod_lang=?";
                    String sqlSelect = "select des_cud from cud_testi where cod_cud=? and cod_lang=?";
                    for (Iterator it = listaLingue.iterator(); it.hasNext();) {
                        String lng = (String) it.next();
                        desc = cercaDescrizione(cu.getDescrizione(), lng);
                        if (desc != null) {
                            st = conn.prepareStatement(sqlSelect);
                            st.setString(1, cu.getCodice());
                            st.setString(2, lng);
                            rs = st.executeQuery();
                            if (rs.next()) {
                                Utility.chiusuraJdbc(rs);
                                Utility.chiusuraJdbc(st);
                                st = conn.prepareStatement(sqlUpdate);
                                st.setString(1, desc);
                                st.setString(2, cu.getCodice());
                                st.setString(3, lng);
                                st.executeUpdate();
                                Utility.chiusuraJdbc(rs);
                                Utility.chiusuraJdbc(st);
                            } else {
                                Utility.chiusuraJdbc(rs);
                                Utility.chiusuraJdbc(st);
                                st = conn.prepareStatement(sql);
                                st.setString(1, cu.getCodice());
                                st.setString(2, desc);
                                st.setString(3, lng);
                                st.executeUpdate();
                                Utility.chiusuraJdbc(rs);
                                Utility.chiusuraJdbc(st);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("Errore insert cud", e);
            throw e;
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
        }
    }

    public void scriviAllegati(
            Allegati al,
            Connection conn) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            String sql = "delete from allegati where cod_int = ? and cod_doc not in (select cod_doc from allegati_comuni)";
            st = conn.prepareStatement(sql);
            st.setInt(1, codIntNew);
            st.executeUpdate();
            for (int i = 0; i < al.getAllegato().size(); i++) {
                scriviAllegato(al.getAllegato().get(i), conn);
            }
        } catch (Exception e) {
            log.error("Errore insert allegato", e);
            throw e;
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
        }
    }

    public void scriviAllegato(AllegatoType at, Connection conn)
            throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        int righe = 0;
        try {
            scriviCondizione(at.getCondizione(), conn);
            scriviDocumento(at.getDocumento(), conn);
            String sql = "select count(*) righe from allegati where cod_int = ? and cod_doc = ?";
            st = conn.prepareStatement(sql);
            st.setInt(1, codIntNew);
            st.setString(2, at.getDocumento().getCodice());
            rs = st.executeQuery();
            while (rs.next()) {
                if (rs.getObject("righe") != null) {
                    righe = rs.getInt("righe");
                }
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            if (righe == 0) {
                if ((checkExclussion(codIntNew.toString(), "interventi") && checkNewExistence(codIntNew.toString(), "interventi"))
                        || (checkExclussion(at.getDocumento().getCodice(), "documenti") && checkNewExistence(at.getDocumento().getCodice(), "documenti"))) {
                    return;
                }
// pc - add flg_verifica_firma                
                sql = "insert into allegati (cod_int,cod_doc,copie,cod_cond,flg_autocert,flg_obb,tipologie,num_max_pag,dimensione_max,ordinamento,flg_verifica_firma) "
                        + "values (?,?,?,?,?,?,?,?,?,?,?)";
                st = conn.prepareStatement(sql);
                st.setInt(1, codIntNew);
                st.setString(2, at.getDocumento().getCodice());
                st.setInt(3, at.getCopie().intValue());
                if (at.getCondizione() != null && !(checkExclussion(at.getCondizione().getCodice(), "testo_condizioni")
                        && checkNewExistence(at.getCondizione().getCodice(), "testo_condizioni"))) {
                    st.setString(4, at.getCondizione().getCodice());
                } else {
                    st.setNull(4, java.sql.Types.VARCHAR);
                }
                st.setString(5, at.getFlgAutocertificazione());
                st.setString(6, at.getFlgObbligatorieta());
                if (at.getTipologie() != null) {
                    st.setString(7, at.getTipologie().getValue());
                } else {
                    st.setNull(7, java.sql.Types.VARCHAR);
                }
                if (at.getNumPagineMax() != null) {
                    st.setInt(8, at.getNumPagineMax().getValue().intValue());
                } else {
                    st.setNull(8, java.sql.Types.INTEGER);
                }
                if (at.getDimensioneMax() != null) {
                    st.setInt(9, at.getDimensioneMax().getValue().intValue());
                } else {
                    st.setNull(9, java.sql.Types.INTEGER);
                }
                if (at.getOrdinamento() != null) {
                    st.setInt(10, at.getOrdinamento().intValue());
                } else {
                    st.setInt(10, 9999);
                }
// pc - add flg_verifica_firma                
                if (at.getFlgVerificaFirma() != null) {
                    st.setInt(11, at.getFlgVerificaFirma().intValue());
                } else {
                    st.setInt(11, 0);
                }
                st.executeUpdate();
            } else {
// pc - add flg_verifica_firma                
                sql = "update allegati set copie = ?, " + "cod_cond = ?, "
                        + "flg_autocert = ?, " + "flg_obb = ?, "
                        + "tipologie = ?, " + "num_max_pag = ?, "
                        + "dimensione_max = ?, " + "ordinamento = ? "
                        + "flg_verifica_firma = ? "
                        + "where cod_int = ? " + "and cod_doc = ?";
                st = conn.prepareStatement(sql);
                st.setInt(1, at.getCopie().intValue());
                if (at.getCondizione() != null) {
                    st.setString(2, at.getCondizione().getCodice());
                } else {
                    st.setNull(2, java.sql.Types.VARCHAR);
                }
                st.setString(3, at.getFlgAutocertificazione());
                st.setString(4, at.getFlgObbligatorieta());
                if (at.getTipologie() != null) {
                    st.setString(5, at.getTipologie().getValue());
                } else {
                    st.setNull(5, java.sql.Types.VARCHAR);
                }
                if (at.getNumPagineMax() != null) {
                    st.setInt(6, at.getNumPagineMax().getValue().intValue());
                } else {
                    st.setNull(6, java.sql.Types.INTEGER);
                }
                if (at.getDimensioneMax() != null) {
                    st.setInt(7, at.getDimensioneMax().getValue().intValue());
                } else {
                    st.setNull(7, java.sql.Types.INTEGER);
                }
                if (at.getOrdinamento() != null) {
                    st.setInt(8, at.getOrdinamento().intValue());
                } else {
                    st.setInt(8, 9999);
                }
// pc - add flg_verifica_firma                
                if (at.getFlgVerificaFirma() != null) {
                    st.setInt(9, at.getFlgVerificaFirma().intValue());
                } else {
                    st.setInt(9, 0);
                }
                st.setInt(10, codIntNew);
                st.setString(11, at.getDocumento().getCodice());
                st.executeUpdate();
            }
        } catch (Exception e) {
            log.error("Errore insert allegato", e);
            throw e;
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
        }
    }

    public void scriviCondizione(CondizioneType co, Connection conn)
            throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        int righe = 0;
        if (co.getCodice() != null) {
            if (checkExclussion(co.getCodice().toString(), "testo_condizioni")) {
                return;
            }
            try {
                String sql = "select count(*) righe from testo_condizioni where cod_cond=?";
                st = conn.prepareStatement(sql);
                st.setString(1, co.getCodice());
                rs = st.executeQuery();
                while (rs.next()) {
                    righe = rs.getInt("righe");
                }
                Utility.chiusuraJdbc(rs);
                Utility.chiusuraJdbc(st);
                if (righe < 1) {
                    sql = "insert into testo_condizioni (cod_cond,testo_cond,cod_lang,tip_aggregazione) values (?,?,'it',?)";
                    st = conn.prepareStatement(sql);
                    st.setString(1, co.getCodice());
                    st.setString(2, cercaDescrizione(co.getDescrizione(), "it"));
                    if (aggregazione != null) {
                        st.setString(3, aggregazione);
                    } else {
                        st.setNull(3, java.sql.Types.VARCHAR);
                    }
                    st.executeUpdate();
                    Utility.chiusuraJdbc(rs);
                    Utility.chiusuraJdbc(st);
                    if (!listaLingue.isEmpty()) {
                        String desc = null;
                        sql = "insert into testo_condizioni (cod_cond,testo_cond,cod_lang,tip_aggregazione) values (?,?,?,?)";
                        for (Iterator it = listaLingue.iterator(); it.hasNext();) {
                            String lng = (String) it.next();
                            desc = cercaDescrizione(co.getDescrizione(), lng);
                            if (desc != null) {
                                st = conn.prepareStatement(sql);
                                st.setString(1, co.getCodice());
                                st.setString(2, desc);
                                st.setString(3, lng);
                                if (aggregazione != null) {
                                    st.setString(4, aggregazione);
                                } else {
                                    st.setNull(4, java.sql.Types.VARCHAR);
                                }
                                st.executeUpdate();
                                Utility.chiusuraJdbc(rs);
                                Utility.chiusuraJdbc(st);
                            }
                        }
                    }
                } else {
                    sql = "update testo_condizioni set testo_cond = ?, tip_aggregazione=? where cod_cond=? and cod_lang='it'";
                    st = conn.prepareStatement(sql);
                    st.setString(1, cercaDescrizione(co.getDescrizione(), "it"));
                    if (aggregazione != null) {
                        st.setString(2, aggregazione);
                    } else {
                        st.setNull(2, java.sql.Types.VARCHAR);
                    }
                    st.setString(3, co.getCodice());
                    st.executeUpdate();
                    Utility.chiusuraJdbc(rs);
                    Utility.chiusuraJdbc(st);
                    if (!listaLingue.isEmpty()) {
                        String desc = null;
                        sql = "insert into testo_condizioni (cod_cond,testo_cond,cod_lang,tip_aggregazione) values (?,?,?,?)";
                        String sqlUpdate = "update testo_condizioni set testo_cond = ?, tip_aggregazione=? where cod_cond=? and cod_lang=?";
                        String sqlSelect = "select testo_cond from testo_condizioni where cod_cond=? and cod_lang=?";
                        for (Iterator it = listaLingue.iterator(); it.hasNext();) {
                            String lng = (String) it.next();
                            desc = cercaDescrizione(co.getDescrizione(), lng);
                            if (desc != null) {
                                st = conn.prepareStatement(sqlSelect);
                                st.setString(1, co.getCodice());
                                st.setString(2, lng);
                                rs = st.executeQuery();
                                if (rs.next()) {
                                    Utility.chiusuraJdbc(rs);
                                    Utility.chiusuraJdbc(st);
                                    st = conn.prepareStatement(sqlUpdate);
                                    st.setString(1, desc);
                                    if (aggregazione != null) {
                                        st.setString(2, aggregazione);
                                    } else {
                                        st.setNull(2, java.sql.Types.VARCHAR);
                                    }
                                    st.setString(3, co.getCodice());
                                    st.setString(4, lng);
                                    st.executeUpdate();
                                    Utility.chiusuraJdbc(rs);
                                    Utility.chiusuraJdbc(st);
                                } else {
                                    Utility.chiusuraJdbc(rs);
                                    Utility.chiusuraJdbc(st);
                                    st = conn.prepareStatement(sql);
                                    st.setString(1, co.getCodice());
                                    st.setString(2, desc);
                                    st.setString(3, lng);
                                    if (aggregazione != null) {
                                        st.setString(4, aggregazione);
                                    } else {
                                        st.setNull(4, java.sql.Types.VARCHAR);
                                    }
                                    st.executeUpdate();
                                    Utility.chiusuraJdbc(rs);
                                    Utility.chiusuraJdbc(st);
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                log.error("Errore insert condizione", e);
                throw e;
            } finally {
                Utility.chiusuraJdbc(rs);
                Utility.chiusuraJdbc(st);
            }
        }
    }

    public void scriviDocumento(Documento doc, Connection conn)
            throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        int righe = 0;
        if (doc != null) {
            scriviDichiarazione(doc.getDichiarazione(), conn);
            if (checkExclussion(doc.getCodice().toString(), "documenti")) {
                return;
            }
            try {
                String sql = "select count(*) righe from documenti where cod_doc=?";
                st = conn.prepareStatement(sql);
                st.setString(1, doc.getCodice());
                rs = st.executeQuery();
                while (rs.next()) {
                    righe = rs.getInt("righe");
                }
                Utility.chiusuraJdbc(rs);
                Utility.chiusuraJdbc(st);
                if (righe < 1) {
                    sql = "insert into documenti (cod_doc,flg_dic,href,tip_aggregazione) values (?,?,?,?)";
                    st = conn.prepareStatement(sql);
                    st.setString(1, doc.getCodice());
                    st.setString(2, doc.getFlgDichiarazione());
                    if (doc.getDichiarazione() != null) {
                        st.setString(3, doc.getDichiarazione().getCodice());
                    } else {
                        st.setNull(3, java.sql.Types.VARCHAR);
                    }
                    if (aggregazione != null) {
                        st.setString(4, aggregazione);
                    } else {
                        st.setNull(4, java.sql.Types.VARCHAR);
                    }
                    st.executeUpdate();
                    Utility.chiusuraJdbc(rs);
                    Utility.chiusuraJdbc(st);
                    sql = "insert into documenti_testi (cod_doc,cod_lang,tit_doc,des_doc) values (?,'it',?,?)";
                    st = conn.prepareStatement(sql);
                    st.setString(1, doc.getCodice());
                    st.setString(2, cercaDescrizione(doc.getTitolo(), "it"));
                    if (doc.getDescrizione() != null) {
                        st.setString(3, cercaDescrizione(doc.getDescrizione(), "it"));
                    } else {
                        st.setNull(3, java.sql.Types.VARCHAR);
                    }
                    st.executeUpdate();
                    if (doc.getDocumentoFisico() != null) {
                        scriviDocumentoFisico(doc.getDocumentoFisico(),
                                doc.getCodice(), conn);
                    }
                    Utility.chiusuraJdbc(rs);
                    Utility.chiusuraJdbc(st);
                    if (!listaLingue.isEmpty()) {
                        String desc = null;
                        String tit = null;
                        sql = "insert into documenti_testi (cod_doc,cod_lang,tit_doc,des_doc) values (?,?,?,?)";
                        for (Iterator it = listaLingue.iterator(); it.hasNext();) {
                            String lng = (String) it.next();
                            desc = cercaDescrizione(doc.getDescrizione(), lng);
                            tit = cercaDescrizione(doc.getTitolo(), lng);
                            if (desc != null || tit != null) {
                                st = conn.prepareStatement(sql);
                                st.setString(1, doc.getCodice());
                                st.setString(2, lng);
                                st.setString(3, tit);
                                st.setString(4, desc);
                                st.executeUpdate();
                                Utility.chiusuraJdbc(rs);
                                Utility.chiusuraJdbc(st);
                            }
                        }
                    }
                } else {
                    sql = "update documenti set flg_dic = ?, href = ?, tip_aggregazione=? where cod_doc=?";
                    st = conn.prepareStatement(sql);
                    st.setString(1, doc.getFlgDichiarazione());
                    if (doc.getDichiarazione() != null) {
                        st.setString(2, doc.getDichiarazione().getCodice());
                    } else {
                        st.setNull(2, java.sql.Types.VARCHAR);
                    }
                    if (aggregazione != null) {
                        st.setString(3, aggregazione);
                    } else {
                        st.setNull(3, java.sql.Types.VARCHAR);
                    }
                    st.setString(4, doc.getCodice());
                    st.executeUpdate();
                    Utility.chiusuraJdbc(rs);
                    Utility.chiusuraJdbc(st);
                    sql = "update documenti_testi set tit_doc = ?, des_doc = ? where cod_doc=? and cod_lang='it'";
                    st = conn.prepareStatement(sql);
                    st.setString(1, cercaDescrizione(doc.getTitolo(), "it"));
                    if (doc.getDescrizione() != null) {
                        st.setString(2, cercaDescrizione(doc.getDescrizione(), "it"));
                    } else {
                        st.setNull(2, java.sql.Types.VARCHAR);
                    }
                    st.setString(3, doc.getCodice());
                    st.executeUpdate();
                    if (doc.getDocumentoFisico() != null) {
                        scriviDocumentoFisico(doc.getDocumentoFisico(),
                                doc.getCodice(), conn);
                    }
                    Utility.chiusuraJdbc(rs);
                    Utility.chiusuraJdbc(st);
                    if (!listaLingue.isEmpty()) {
                        String desc = null;
                        String tit = null;
                        sql = "insert into documenti_testi (cod_doc,cod_lang,tit_doc,des_doc) values (?,?,?,?)";
                        String sqlUpdate = "update documenti_testi set tit_doc = ?, des_doc = ? where cod_doc=? and cod_lang=?";
                        String sqlSelect = "select tit_doc,des_doc from documenti_testi where cod_doc=? and cod_lang=?";
                        for (Iterator it = listaLingue.iterator(); it.hasNext();) {
                            String lng = (String) it.next();
                            desc = cercaDescrizione(doc.getDescrizione(), lng);
                            tit = cercaDescrizione(doc.getTitolo(), lng);
                            if (desc != null || tit != null) {
                                st = conn.prepareStatement(sqlSelect);
                                st.setString(1, doc.getCodice());
                                st.setString(2, lng);
                                rs = st.executeQuery();
                                if (rs.next()) {
                                    Utility.chiusuraJdbc(rs);
                                    Utility.chiusuraJdbc(st);
                                    st = conn.prepareStatement(sqlUpdate);
                                    st.setString(1, tit);
                                    st.setString(2, desc);
                                    st.setString(3, doc.getCodice());
                                    st.setString(4, lng);
                                    st.executeUpdate();
                                    Utility.chiusuraJdbc(rs);
                                    Utility.chiusuraJdbc(st);
                                } else {
                                    Utility.chiusuraJdbc(rs);
                                    Utility.chiusuraJdbc(st);
                                    st = conn.prepareStatement(sql);
                                    st.setString(1, doc.getCodice());
                                    st.setString(2, tit);
                                    st.setString(3, desc);
                                    st.setString(4, lng);
                                    st.executeUpdate();
                                    Utility.chiusuraJdbc(rs);
                                    Utility.chiusuraJdbc(st);
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                log.error("Errore insert documento", e);
                throw e;
            } finally {
                Utility.chiusuraJdbc(rs);
                Utility.chiusuraJdbc(st);
            }
        }
    }

    public void scriviDocumentoFisico(DocumentoType docf, String codDoc,
            Connection conn) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        int righe = 0;
        try {
            String sql = "select count(*) righe from documenti_documenti where cod_doc=?";
            st = conn.prepareStatement(sql);
            st.setString(1, codDoc);
            rs = st.executeQuery();
            while (rs.next()) {
                righe = rs.getInt("righe");
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            if (righe < 1) {
                sql = "insert into documenti_documenti (cod_doc,tip_doc,nome_file,doc_blob,cod_lang) values (?,?,?,?,'it')";
                st = conn.prepareStatement(sql);
                st.setString(1, codDoc);
                st.setString(2, docf.getTipoDocumento());
                st.setString(3, docf.getNomeFile());
                if (docf.getBlob() != null) {
                    st.setBytes(4, docf.getBlob());
                } else {
                    st.setNull(4, java.sql.Types.BLOB);
                }
                st.executeUpdate();
            } else {
                sql = "update documenti_documenti set tip_doc = ?, nome_file=?, doc_blob=? where cod_doc=? and cod_lang='it'";
                st = conn.prepareStatement(sql);
                st.setString(1, docf.getTipoDocumento());
                st.setString(2, docf.getNomeFile());
                if (docf.getBlob() != null) {
                    st.setBytes(3, docf.getBlob());
                } else {
                    st.setNull(3, java.sql.Types.BLOB);
                }
                st.setString(4, codDoc);
                st.executeUpdate();
            }
        } catch (Exception e) {
            log.error("Errore insert documento fisico", e);
            throw e;
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
        }
    }

    public void scriviDichiarazione(Dichiarazione dic, Connection conn)
            throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        int righe = 0;
        if (dic != null) {
            if (checkExclussion(dic.getCodice().toString(), "dichiarazioni")) {
                return;
            }
            try {
                String sql = "select count(*) righe from href where href=?";
                st = conn.prepareStatement(sql);
                st.setString(1, dic.getCodice());
                rs = st.executeQuery();
                while (rs.next()) {
                    righe = rs.getInt("righe");
                }
                Utility.chiusuraJdbc(rs);
                Utility.chiusuraJdbc(st);
                if (righe < 1) {
                    scriviHrefCampi(dic.getCampi(), dic.getCodice(), conn);
                    scriviHrefCampiSelect(dic.getValoriListBox(),
                            dic.getCodice(), conn);
                    sql = "insert into href (href,tp_href,tip_aggregazione) values (?,'S',?)";
                    st = conn.prepareStatement(sql);
                    st.setString(1, dic.getCodice());
                    if (aggregazione != null) {
                        st.setString(2, aggregazione);
                    } else {
                        st.setNull(2, java.sql.Types.VARCHAR);
                    }
                    st.executeUpdate();
                    Utility.chiusuraJdbc(rs);
                    Utility.chiusuraJdbc(st);
                    sql = "insert into href_testi (href,cod_lang,tit_href,piede_href) values (?,'it',?,?)";
                    st = conn.prepareStatement(sql);
                    st.setString(1, dic.getCodice());
                    st.setString(2, cercaDescrizione(dic.getDescrizione(), "it"));
                    if (dic.getTestoPiede() != null) {
                        st.setString(3, cercaDescrizione(dic.getTestoPiede(), "it"));
                    } else {
                        st.setNull(3, java.sql.Types.VARCHAR);
                    }
                    st.executeUpdate();
                    Utility.chiusuraJdbc(rs);
                    Utility.chiusuraJdbc(st);
                    if (!listaLingue.isEmpty()) {
                        String desc = null;
                        String piede = null;
                        sql = "insert into href_testi (href,cod_lang,tit_href,piede_href) values (?,?,?,?)";
                        for (Iterator it = listaLingue.iterator(); it.hasNext();) {
                            String lng = (String) it.next();
                            desc = cercaDescrizione(dic.getDescrizione(), lng);
                            piede = cercaDescrizione(dic.getTestoPiede(), lng);
                            if (desc != null || piede != null) {
                                st = conn.prepareStatement(sql);
                                st.setString(1, dic.getCodice());
                                st.setString(2, lng);
                                st.setString(3, desc);
                                if (piede != null) {
                                    st.setString(4, piede);
                                } else {
                                    st.setNull(4, java.sql.Types.VARCHAR);
                                }
                                st.executeUpdate();
                                Utility.chiusuraJdbc(rs);
                                Utility.chiusuraJdbc(st);
                            }
                        }
                    }
                } else {
                    scriviHrefCampi(dic.getCampi(), dic.getCodice(), conn);
                    scriviHrefCampiSelect(dic.getValoriListBox(),
                            dic.getCodice(), conn);
                    sql = "update href_testi set tit_href = ?, piede_href= ? where href=? and cod_lang='it'";
                    st = conn.prepareStatement(sql);
                    st.setString(1, cercaDescrizione(dic.getDescrizione(), "it"));
                    if (dic.getTestoPiede() != null) {
                        st.setString(2, cercaDescrizione(dic.getTestoPiede(), "it"));
                    } else {
                        st.setNull(2, java.sql.Types.VARCHAR);
                    }
                    st.setString(3, dic.getCodice());
                    st.executeUpdate();
                    Utility.chiusuraJdbc(rs);
                    Utility.chiusuraJdbc(st);
                    if (!listaLingue.isEmpty()) {
                        String desc = null;
                        String piede = null;
                        sql = "insert into href_testi (href,cod_lang,tit_href,piede_href) values (?,?,?,?)";
                        String sqlUpdate = "update href_testi set tit_href = ?, piede_href= ? where href=? and cod_lang=?";
                        String sqlSelect = "select tit_href,piede_href from href_testi where href=? and cod_lang=?";
                        for (Iterator it = listaLingue.iterator(); it.hasNext();) {
                            String lng = (String) it.next();
                            desc = cercaDescrizione(dic.getDescrizione(), lng);
                            piede = cercaDescrizione(dic.getTestoPiede(), lng);
                            if (desc != null || piede != null) {
                                st = conn.prepareStatement(sqlSelect);
                                st.setString(1, dic.getCodice());
                                st.setString(2, lng);
                                rs = st.executeQuery();
                                if (rs.next()) {
                                    Utility.chiusuraJdbc(rs);
                                    Utility.chiusuraJdbc(st);
                                    st = conn.prepareStatement(sqlUpdate);
                                    st.setString(1, desc);
                                    if (piede != null) {
                                        st.setString(2, piede);
                                    } else {
                                        st.setNull(2, java.sql.Types.VARCHAR);
                                    }
                                    st.setString(3, dic.getCodice());
                                    st.setString(4, lng);
                                    st.executeUpdate();
                                    Utility.chiusuraJdbc(rs);
                                    Utility.chiusuraJdbc(st);
                                } else {
                                    Utility.chiusuraJdbc(rs);
                                    Utility.chiusuraJdbc(st);
                                    st = conn.prepareStatement(sql);
                                    st.setString(1, dic.getCodice());
                                    st.setString(2, lng);
                                    st.setString(3, desc);
                                    if (piede != null) {
                                        st.setString(4, piede);
                                    } else {
                                        st.setNull(4, java.sql.Types.VARCHAR);
                                    }
                                    st.executeUpdate();
                                    Utility.chiusuraJdbc(rs);
                                    Utility.chiusuraJdbc(st);
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                log.error("Errore insert dichiarazione", e);
                throw e;
            } finally {
                Utility.chiusuraJdbc(rs);
                Utility.chiusuraJdbc(st);
            }
        }
    }

    public void scriviHrefCampi(Campi c, String href, Connection conn)
            throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            String sql = "delete from href_campi where href = ?";
            st = conn.prepareStatement(sql);
            st.setString(1, href);
            st.executeUpdate();
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            sql = "delete from href_campi_testi where href = ?";
            st = conn.prepareStatement(sql);
            st.setString(1, href);
            st.executeUpdate();
            for (int i = 0; i < c.getCampo().size(); i++) {
                scriviHrefCampo(c.getCampo().get(i), href, conn);
            }
        } catch (Exception e) {
            log.error("Errore insert campi dichiarazione", e);
            throw e;
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
        }
    }

    public void scriviHrefCampo(Campo c, String href, Connection conn)
            throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
// pc - add pattern            
            String sql = "insert into href_campi ( href, contatore, nome, riga, posizione, tp_riga, tipo, valore, controllo,"
                    + " tp_controllo, lunghezza, decimali, edit, web_serv, nome_xsd, campo_key,"
                    + " campo_dati, campo_xml_mod, raggruppamento_check, campo_collegato, val_campo_collegato,"
                    + " marcatore_incrociato, precompilazione, pattern) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            st = conn.prepareStatement(sql);
            st.setString(1, href);
            st.setInt(2, c.getContatore().intValue());
            st.setString(3, c.getNome());
            st.setInt(4, c.getRiga().intValue());
            st.setInt(5, c.getPosizione().intValue());
            st.setString(6, c.getTipoRiga());
            st.setString(7, c.getTipoCampo());
            st.setString(8, c.getValore());
            st.setString(9, c.getControllo());
            st.setString(10, c.getTipoControllo());
            st.setInt(11, c.getLunghezza().intValue());
            st.setInt(12, c.getDecimali().intValue());
            st.setString(13, c.getEditabile());
            st.setString(14, c.getNomeWebService());
            st.setString(15, c.getNomeXsd());
            st.setString(16, c.getCampoKey());
            st.setString(17, c.getCampoDati());
            st.setString(18, c.getCampoXmlMod());
            st.setString(19, c.getRaggruppamentoCheck());
            st.setString(20, c.getCampoCollegato());
            st.setString(21, c.getValoreCampoCollegato());
            st.setString(22, c.getMarcatoreIncrociato());
            st.setString(23, c.getPrecompilazione());
// pc - add pattern             
            st.setString(24, c.getPattern());
            st.executeUpdate();
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
// pc - add err_msg             
            sql = "insert into href_campi_testi (contatore, href, nome, cod_lang, des_campo, err_msg) values (?,?, ?, 'it', ?, ?)";
            st = conn.prepareStatement(sql);
            st.setInt(1, c.getContatore().intValue());
            st.setString(2, href);
            st.setString(3, c.getNome());
            st.setString(4, Utility.testoADb(cercaDescrizione(c.getDescrizione(), "it")));
            st.setString(5, Utility.testoADb(cercaDescrizione(c.getErrMsg(), "it")));
            st.executeUpdate();
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            if (!listaLingue.isEmpty()) {
                String desc = null;
// pc - add err_msg                  
                String msg = null;
// pc - add err_msg                  
                sql = "insert into href_campi_testi (contatore, href, nome, cod_lang, des_campo, err_msg) values (?,?, ?, ?, ?, ?)";
                for (Iterator it = listaLingue.iterator(); it.hasNext();) {
                    String lng = (String) it.next();
                    desc = cercaDescrizione(c.getDescrizione(), lng);
// pc - add err_msg                      
                    msg = cercaDescrizione(c.getErrMsg(), lng);
                    if (desc != null) {
                        st = conn.prepareStatement(sql);
                        st.setInt(1, c.getContatore().intValue());
                        st.setString(2, href);
                        st.setString(3, c.getNome());
                        st.setString(4, lng);
                        st.setString(5, desc);
// pc - add err_msg                          
                        st.setString(6, msg);
                        st.executeUpdate();
                        Utility.chiusuraJdbc(rs);
                        Utility.chiusuraJdbc(st);
                    }
                }
            }
        } catch (Exception e) {
            log.error("Errore insert campi dichiarazioni", e);
            throw e;
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
        }
    }

    public void scriviHrefCampiSelect(ValoriListBox vl, String href,
            Connection conn) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            String sql = "delete from href_campi_valori where href = ?";
            st = conn.prepareStatement(sql);
            st.setString(1, href);
            st.executeUpdate();
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            sql = "delete from href_campi_valori_testi where href = ?";
            st = conn.prepareStatement(sql);
            st.setString(1, href);
            st.executeUpdate();
            for (int i = 0; i < vl.getValoreListBox().size(); i++) {
                scriviHrefCampoSelect(vl.getValoreListBox().get(i), href, conn);
            }
        } catch (Exception e) {
            log.error("Errore insert campi dichiarazioni select", e);
            throw e;
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
        }
    }

    public void scriviHrefCampoSelect(ValoreListBox v, String href,
            Connection conn) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            String sql = "insert into href_campi_valori (href, nome, val_select) "
                    + "values (?, ?, ?)";
            st = conn.prepareStatement(sql);
            st.setString(1, href);
            st.setString(2, v.getNome());
            st.setString(3, v.getValoreListBox());
            st.executeUpdate();
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            sql = "insert into href_campi_valori_testi (href, nome, val_select, des_valore, cod_lang) "
                    + "values (?, ?, ?, ?, 'it')";
            st = conn.prepareStatement(sql);
            st.setString(1, href);
            st.setString(2, v.getNome());
            st.setString(3, v.getValoreListBox());
            st.setString(4, Utility.testoADb(cercaDescrizione(v.getDescrizioneListBox(), "it")));
            st.executeUpdate();
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            if (!listaLingue.isEmpty()) {
                String desc = null;
                sql = "insert into href_campi_valori_testi (href, nome, val_select, des_valore, cod_lang) values (?,?, ?, ?, ?)";
                for (Iterator it = listaLingue.iterator(); it.hasNext();) {
                    String lng = (String) it.next();
                    desc = cercaDescrizione(v.getDescrizioneListBox(), lng);
                    if (desc != null) {
                        st = conn.prepareStatement(sql);
                        st.setString(1, href);
                        st.setString(2, v.getNome());
                        st.setString(3, v.getValoreListBox());
                        st.setString(4, desc);
                        st.setString(5, lng);
                        st.executeUpdate();
                        Utility.chiusuraJdbc(rs);
                        Utility.chiusuraJdbc(st);
                    }
                }
            }
        } catch (Exception e) {
            log.error("Errore insert campi dichiarazioni select", e);
            throw e;
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
        }
    }

    public void scriviNormativeInterventi(
            Normative no,
            Connection conn) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            String sql = "delete from norme_interventi where cod_int = ? and cod_rif not in (select cod_rif from norme_comuni)";
            st = conn.prepareStatement(sql);
            st.setInt(1, codIntNew);
            st.executeUpdate();
            for (int i = 0; i < no.getNormativa().size(); i++) {
                scriviNormativaIntervento(no.getNormativa().get(i), conn);
            }
        } catch (Exception e) {
            log.error("Errore insert norme intervento", e);
            throw e;
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
        }
    }

    public void scriviNormativaIntervento(Normativa no, Connection conn)
            throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        int righe = 0;
        try {
            scriviNormativa(no, conn);
            String sql = "select count(*) righe from norme_interventi where cod_int = ? and cod_rif = ?";
            st = conn.prepareStatement(sql);
            st.setInt(1, codIntNew);
            st.setString(2, no.getCodice());
            rs = st.executeQuery();
            while (rs.next()) {
                if (rs.getObject("righe") != null) {
                    righe = rs.getInt("righe");
                }
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            if (righe == 0) {
                if ((checkExclussion(codIntNew.toString(), "interventi") && checkNewExistence(codIntNew.toString(), "interventi")) || (checkExclussion(no.getCodice(), "normative") && checkNewExistence(no.getCodice(), "normative"))) {
                    return;
                }
                sql = "insert into norme_interventi (cod_int,cod_rif,art_rif) "
                        + "values (?,?,?)";
                st = conn.prepareStatement(sql);
                st.setInt(1, codIntNew);
                st.setString(2, no.getCodice());
                st.setString(3, no.getArticoloRiferimento());
                st.executeUpdate();
            } else {
                sql = "update norme_interventi set art_rif = ? "
                        + "where cod_int = ? " + "and cod_rif = ? ";
                st = conn.prepareStatement(sql);
                st.setString(1, no.getArticoloRiferimento());
                st.setInt(2, codIntNew);
                st.setString(3, no.getCodice());
                st.executeUpdate();
            }
        } catch (Exception e) {
            log.error("Errore insert norme intervento", e);
            throw e;
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
        }
    }

    public void scriviNormativa(Normativa no, Connection conn) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        int righe = 0;
        try {
            scriviTipoNormativa(no.getTipoNormativa(), conn);
            if (checkExclussion(no.getCodice().toString(), "normative")) {
                return;
            }
            String sql = "select count(*) righe from normative where cod_rif=?";
            st = conn.prepareStatement(sql);
            st.setString(1, no.getCodice());
            rs = st.executeQuery();
            while (rs.next()) {
                righe = rs.getInt("righe");
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            if (righe < 1) {
                sql = "insert into normative (cod_rif,nome_rif,cod_tipo_rif,tip_aggregazione) values (?,?,?,?)";
                st = conn.prepareStatement(sql);
                st.setString(1, no.getCodice());
                st.setString(2, no.getNomeRiferimento());
                st.setString(3, no.getTipoNormativa().getCodice());
                if (aggregazione != null) {
                    st.setString(4, aggregazione);
                } else {
                    st.setNull(4, java.sql.Types.VARCHAR);
                }
                st.executeUpdate();
                Utility.chiusuraJdbc(rs);
                Utility.chiusuraJdbc(st);
                sql = "insert into normative_testi (cod_rif,tit_rif,cod_lang) values (?,?,'it')";
                st = conn.prepareStatement(sql);
                st.setString(1, no.getCodice());
                st.setString(2, cercaDescrizione(no.getDescrizione(), "it"));
                st.executeUpdate();
                if (no.getDocumentoFisico() != null) {
                    scriviNormativaFisico(no.getDocumentoFisico(),
                            no.getCodice(), conn);
                }
                Utility.chiusuraJdbc(rs);
                Utility.chiusuraJdbc(st);
                if (!listaLingue.isEmpty()) {
                    String desc = null;
                    sql = "insert into normative_testi (cod_rif,tit_rif,cod_lang) values (?,?,?)";
                    for (Iterator it = listaLingue.iterator(); it.hasNext();) {
                        String lng = (String) it.next();
                        desc = cercaDescrizione(no.getDescrizione(), lng);
                        if (desc != null) {
                            st = conn.prepareStatement(sql);
                            st.setString(1, no.getCodice());
                            st.setString(2, desc);
                            st.setString(3, lng);
                            st.executeUpdate();
                            Utility.chiusuraJdbc(rs);
                            Utility.chiusuraJdbc(st);
                        }
                    }
                }
            } else {
                sql = "update normative set nome_rif = ?, cod_tipo_rif=?, tip_aggregazione=? where cod_rif=?";
                st = conn.prepareStatement(sql);
                st.setString(1, no.getNomeRiferimento());
                st.setString(2, no.getTipoNormativa().getCodice());
                if (aggregazione != null) {
                    st.setString(3, aggregazione);
                } else {
                    st.setNull(3, java.sql.Types.VARCHAR);
                }
                st.setString(4, no.getCodice());
                st.executeUpdate();
                Utility.chiusuraJdbc(rs);
                Utility.chiusuraJdbc(st);
                sql = "update normative_testi set tit_rif = ? where cod_rif=? and cod_lang='it'";
                st = conn.prepareStatement(sql);
                st.setString(1, cercaDescrizione(no.getDescrizione(), "it"));
                st.setString(2, no.getCodice());
                st.executeUpdate();
                if (no.getDocumentoFisico() != null) {
                    scriviNormativaFisico(no.getDocumentoFisico(),
                            no.getCodice(), conn);
                }
                Utility.chiusuraJdbc(rs);
                Utility.chiusuraJdbc(st);
                if (!listaLingue.isEmpty()) {
                    String desc = null;
                    sql = "insert into normative_testi (cod_rif,tit_rif,cod_lang) values (?,?,?)";
                    String sqlUpdate = "update normative_testi set tit_rif = ? where cod_rif=? and cod_lang=?";
                    String sqlSelect = "select tit_rif from normative_testi where cod_rif=? and cod_lang=?";
                    for (Iterator it = listaLingue.iterator(); it.hasNext();) {
                        String lng = (String) it.next();
                        desc = cercaDescrizione(no.getDescrizione(), lng);
                        if (desc != null) {
                            st = conn.prepareStatement(sqlSelect);
                            st.setString(1, no.getCodice());
                            st.setString(2, lng);
                            rs = st.executeQuery();
                            if (rs.next()) {
                                Utility.chiusuraJdbc(rs);
                                Utility.chiusuraJdbc(st);
                                st = conn.prepareStatement(sqlUpdate);
                                st.setString(1, desc);
                                st.setString(2, no.getCodice());
                                st.setString(3, lng);
                                st.executeUpdate();
                                Utility.chiusuraJdbc(rs);
                                Utility.chiusuraJdbc(st);
                            } else {
                                Utility.chiusuraJdbc(rs);
                                Utility.chiusuraJdbc(st);
                                st = conn.prepareStatement(sql);
                                st.setString(1, no.getCodice());
                                st.setString(2, desc);
                                st.setString(3, lng);
                                st.executeUpdate();
                                Utility.chiusuraJdbc(rs);
                                Utility.chiusuraJdbc(st);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("Errore insert norma", e);
            throw e;
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
        }
    }

    public void scriviNormativaFisico(DocumentoType docf, String codRif,
            Connection conn) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        int righe = 0;
        try {
            String sql = "select count(*) righe from normative_documenti where cod_rif=?";
            st = conn.prepareStatement(sql);
            st.setString(1, codRif);
            rs = st.executeQuery();
            while (rs.next()) {
                righe = rs.getInt("righe");
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            if (righe < 1) {
                sql = "insert into normative_documenti (cod_rif,tip_doc,nome_file,doc_blob,cod_lang) values (?,?,?,?,'it')";
                st = conn.prepareStatement(sql);
                st.setString(1, codRif);
                st.setString(2, docf.getTipoDocumento());
                st.setString(3, docf.getNomeFile());
                if (docf.getBlob() != null) {
                    st.setBytes(4, docf.getBlob());
                } else {
                    st.setNull(4, java.sql.Types.BLOB);
                }
                st.executeUpdate();
            } else {
                sql = "update normative_documenti set tip_doc = ?, nome_file=?, doc_blob=? where cod_rif=? and cod_lang='it'";
                st = conn.prepareStatement(sql);
                st.setString(1, docf.getTipoDocumento());
                st.setString(2, docf.getNomeFile());
                if (docf.getBlob() != null) {
                    st.setBytes(3, docf.getBlob());
                } else {
                    st.setNull(3, java.sql.Types.BLOB);
                }
                st.setString(4, codRif);
                st.executeUpdate();
            }
        } catch (Exception e) {
            log.error("Errore insert norma fisica", e);
            throw e;
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
        }
    }

    public void scriviTipoNormativa(TipoNormativa tn, Connection conn)
            throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        if (checkExclussion(tn.getCodice().toString(), "tipi_rif")) {
            return;
        }
        int righe = 0;
        try {
            String sql = "select count(*) righe from tipi_rif where cod_tipo_rif=?";
            st = conn.prepareStatement(sql);
            st.setString(1, tn.getCodice());
            rs = st.executeQuery();
            while (rs.next()) {
                righe = rs.getInt("righe");
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            if (righe < 1) {
                sql = "insert into tipi_rif (cod_tipo_rif,tipo_rif, cod_lang) "
                        + "values (?,?,'it')";
                st = conn.prepareStatement(sql);
                st.setString(1, tn.getCodice());
                st.setString(2, cercaDescrizione(tn.getDescrizione(), "it"));
                st.executeUpdate();
                Utility.chiusuraJdbc(rs);
                Utility.chiusuraJdbc(st);
                if (!listaLingue.isEmpty()) {
                    String desc = null;
                    sql = "insert into tipi_rif (cod_tipo_rif,tipo_rif, cod_lang) values (?,?,?)";
                    for (Iterator it = listaLingue.iterator(); it.hasNext();) {
                        String lng = (String) it.next();
                        desc = cercaDescrizione(tn.getDescrizione(), lng);
                        if (desc != null) {
                            st = conn.prepareStatement(sql);
                            st.setString(1, tn.getCodice());
                            st.setString(2, desc);
                            st.setString(3, lng);
                            st.executeUpdate();
                            Utility.chiusuraJdbc(rs);
                            Utility.chiusuraJdbc(st);
                        }
                    }
                }
            } else {
                sql = "update tipi_rif set tipo_rif = ? where cod_tipo_rif=? and cod_lang='it'";
                st = conn.prepareStatement(sql);
                st.setString(1, cercaDescrizione(tn.getDescrizione(), "it"));
                st.setString(2, tn.getCodice());
                st.executeUpdate();
                Utility.chiusuraJdbc(rs);
                Utility.chiusuraJdbc(st);
                if (!listaLingue.isEmpty()) {
                    String desc = null;
                    sql = "insert into tipi_rif (cod_tipo_rif,tipo_rif, cod_lang) values (?,?,?)";
                    String sqlUpdate = "update tipi_rif set tipo_rif = ? where cod_tipo_rif=? and cod_lang=?";
                    String sqlSelect = "select tipo_rif from tipi_rif where cod_tipo_rif=? and cod_lang=?";
                    for (Iterator it = listaLingue.iterator(); it.hasNext();) {
                        String lng = (String) it.next();
                        desc = cercaDescrizione(tn.getDescrizione(), lng);
                        if (desc != null) {
                            st = conn.prepareStatement(sqlSelect);
                            st.setString(1, tn.getCodice());
                            st.setString(2, lng);
                            rs = st.executeQuery();
                            if (rs.next()) {
                                Utility.chiusuraJdbc(rs);
                                Utility.chiusuraJdbc(st);
                                st = conn.prepareStatement(sqlUpdate);
                                st.setString(1, desc);
                                st.setString(2, tn.getCodice());
                                st.setString(3, lng);
                                st.executeUpdate();
                                Utility.chiusuraJdbc(rs);
                                Utility.chiusuraJdbc(st);
                            } else {
                                Utility.chiusuraJdbc(rs);
                                Utility.chiusuraJdbc(st);
                                st = conn.prepareStatement(sql);
                                st.setString(1, tn.getCodice());
                                st.setString(2, desc);
                                st.setString(3, lng);
                                st.executeUpdate();
                                Utility.chiusuraJdbc(rs);
                                Utility.chiusuraJdbc(st);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("Errore insert tipo norma", e);
            throw e;
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
        }
    }

    public void caricaIntervento(DocumentRoot doc, String codInt,
            String codCom, Connection conn, boolean lingua) throws Exception {
        caricaLingua = lingua;
        doc.setCodiceComune(codCom);
        InterventoType in = new InterventoType();
        caricaIntervento(in, codInt, codCom, conn);
        doc.setIntervento(in);
    }

    public void caricaIntervento(InterventoType in, String codInt,
            String codCom, Connection conn) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        PreparedStatement stLingua = null;
        ResultSet rsLingua = null;
        String sqlLingua = null;

        try {
// pc - add ordinamento interventi            
            String sql = "select a.cod_int, b.tit_int, a.flg_obb, a.cod_ente_origine, a.cod_int_origine, "
                    + "a.cod_proc, a.note_pubblicazione, a.note_generali_pubblicazione, c.cod_cud, c.ter_eva, c.flg_tipo_proc, c.flg_bollo, "
                    + "d.tit_proc, f.des_cud , a.ordinamento "
                    + "from interventi a "
                    + "join interventi_testi b "
                    + "on a.cod_int = b.cod_int "
                    + "join procedimenti c "
                    + "on a.cod_proc = c.cod_proc "
                    + "join procedimenti_testi d "
                    + "on c.cod_proc = d.cod_proc "
                    + "and b.cod_lang=d.cod_lang "
                    + "join cud e "
                    + "on c.cod_cud = e.cod_cud "
                    + "join cud_testi f "
                    + "on f.cod_cud = e.cod_cud "
                    + "and f.cod_lang = d.cod_lang "
                    + "where a.cod_int = ? and b.cod_lang='it'";
            st = conn.prepareStatement(sql);
            st.setInt(1, Integer.parseInt(codInt));
            rs = st.executeQuery();
            while (rs.next()) {
                in.setCodice(BigInteger.valueOf(rs.getInt("cod_int")));
                in.setFlgObbligatorio(rs.getString("flg_obb"));
// pc - add ordinamento interventi                 
                if (rs.getObject("ordinamento") != null) {
                    in.setOrdinamento(BigInteger.valueOf(rs.getInt("ordinamento")));
                } else {
                    in.setOrdinamento(in.getCodice());
                }
                in.getDescrizione().add(scriviDescrizione(Utility.testoDaDb(rs.getString("tit_int")), "it"));
                if (rs.getObject("cod_ente_origine") != null) {
                    JAXBElement<String> s = new JAXBElement(new QName(nameSpace,
                            "codiceEnteOrigine"), String.class,
                            rs.getString("cod_ente_origine"));
                    in.setCodiceEnteOrigine(s);
                }
                String notePubblicazioneObject = (String) rs.getObject("note_pubblicazione");
                if (notePubblicazioneObject != null && !StringUtils.isEmpty(notePubblicazioneObject)) {
                    JAXBElement<String> s = new JAXBElement(new QName(nameSpace,
                            "notePubblicazione"), String.class,
                            notePubblicazioneObject);
                    in.setNotePubblicazione(s);
                }
                String noteGeneraliPubblicazioneObject = (String) rs.getObject("note_generali_pubblicazione");
                if (noteGeneraliPubblicazioneObject != null && !StringUtils.isEmpty(noteGeneraliPubblicazioneObject)) {
                    JAXBElement<String> s = new JAXBElement(new QName(nameSpace,
                            "noteGeneraliPubblicazione"), String.class,
                            noteGeneraliPubblicazioneObject);
                    in.setNoteGeneraliPubblicazione(s);
                }
                JAXBElement<Integer> v;
                if (rs.getObject("cod_int_origine") != null) {
                    v = new JAXBElement(new QName(nameSpace, "codiceOrigine"), Integer.class, Integer.valueOf(rs.getInt("cod_int_origine")));
                    in.setCodiceOrigine(v);
                }
                if (caricaLingua) {
                    sqlLingua = "select tit_int, cod_lang from interventi_testi where cod_int = ? and cod_lang <> 'it'";
                    stLingua = conn.prepareStatement(sqlLingua);
                    stLingua.setInt(1, Integer.parseInt(codInt));
                    rsLingua = stLingua.executeQuery();
                    while (rsLingua.next()) {
                        in.getDescrizione().add(scriviDescrizione(Utility.testoDaDb(rsLingua.getString("tit_int")), rsLingua.getString("cod_lang")));
                    }
                    Utility.chiusuraJdbc(rsLingua);
                    Utility.chiusuraJdbc(stLingua);
                }

                Procedimento pm = new Procedimento();
                pm.setCodice(rs.getString("cod_proc"));
                pm.getDescrizione().add(scriviDescrizione(Utility.testoDaDb(rs.getString("tit_proc")), "it"));
                pm.setTipoProc(rs.getString("flg_tipo_proc"));
                pm.setTerminiEvasione(BigInteger.valueOf(rs.getInt("ter_eva")));
                pm.setFlgBollo(rs.getString("flg_bollo"));
                if (caricaLingua) {
                    sqlLingua = "select tit_proc, cod_lang from procedimenti_testi where cod_proc = ? and cod_lang <> 'it'";
                    stLingua = conn.prepareStatement(sqlLingua);
                    stLingua.setString(1, rs.getString("cod_proc"));
                    rsLingua = stLingua.executeQuery();
                    while (rsLingua.next()) {
                        pm.getDescrizione().add(scriviDescrizione(Utility.testoDaDb(rsLingua.getString("tit_proc")), rsLingua.getString("cod_lang")));
                    }
                    Utility.chiusuraJdbc(rsLingua);
                    Utility.chiusuraJdbc(stLingua);
                }

                CudType cm = new CudType();
                cm.setCodice(rs.getString("cod_cud"));
                cm.getDescrizione().add(scriviDescrizione(Utility.testoDaDb(rs.getString("des_cud")), "it"));
                if (caricaLingua) {
                    sqlLingua = "select des_cud, cod_lang from cud_testi where cod_cud = ? and cod_lang <> 'it'";
                    stLingua = conn.prepareStatement(sqlLingua);
                    stLingua.setString(1, rs.getString("cod_cud"));
                    rsLingua = stLingua.executeQuery();
                    while (rsLingua.next()) {
                        cm.getDescrizione().add(scriviDescrizione(Utility.testoDaDb(rsLingua.getString("des_cud")), rsLingua.getString("cod_lang")));
                    }
                    Utility.chiusuraJdbc(rsLingua);
                    Utility.chiusuraJdbc(stLingua);
                }
                pm.setCud(cm);
                in.setProcedimento(pm);
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
// pc - add flg_verifica_firma            
            sql = "select p.cod_doc, p.cod_int, p.copie, p.flg_autocert, p.cod_cond, p.flg_obb, p.tipologie, p.num_max_pag, p.dimensione_max, p.ordinamento, p.flg_verifica_firma, "
                    + " d.flg_dic, d.href,"
                    + " dt.tit_doc, dt.des_doc,"
                    + " dd.nome_file,"
                    + " t.testo_cond"
                    + " from"
                    + " ("
                    + " select    a.cod_doc, a.cod_int, a.copie, a.flg_autocert, a.cod_cond, a.flg_obb, a.tipologie, a.num_max_pag, a.dimensione_max, a.ordinamento, a.flg_verifica_firma"
                    + " from      allegati a"
                    + " join      allegati_comuni b"
                    + " on        a.cod_doc = b.cod_doc"
                    + " where     b.cod_com = ?"
                    + " and       a.cod_int = ?"
                    + " and       b.flg = 'S'"
                    + " union"
                    + " select    a.cod_doc, a.cod_int, a.copie, a.flg_autocert, a.cod_cond, a.flg_obb, a.tipologie, a.num_max_pag, a.dimensione_max, a.ordinamento, a.flg_verifica_firma"
                    + " from      allegati a"
                    + " left join allegati_comuni b"
                    + " on        a.cod_doc = b.cod_doc"
                    + " where     b.cod_doc is null "
                    + " and       a.cod_int = ?"
                    + " union"
                    + " select    a.cod_doc, a.cod_int, a.copie, a.flg_autocert, a.cod_cond, a.flg_obb, a.tipologie, a.num_max_pag, a.dimensione_max, a.ordinamento, a.flg_verifica_firma"
                    + " from      allegati a"
                    + " left join allegati_comuni b"
                    + " on        a.cod_doc = b.cod_doc"
                    + " where     b.cod_com != ? "
                    + " and       a.cod_int = ? "
                    + " and       b.flg='N') p"
                    + " join documenti d"
                    + " on p.cod_doc = d.cod_doc"
                    + " join documenti_testi dt"
                    + " on p.cod_doc = dt.cod_doc"
                    + " left join documenti_documenti dd"
                    + " on p.cod_doc=dd.cod_doc"
                    + " and dd.cod_lang=dt.cod_lang"
                    + " left join testo_condizioni t"
                    + " on p.cod_cond=t.cod_cond"
                    + " and t.cod_lang=dt.cod_lang" + " where dt.cod_lang='it'";
            st = conn.prepareStatement(sql);
            st.setString(1, codCom);
            st.setInt(2, Integer.parseInt(codInt));
            st.setInt(3, Integer.parseInt(codInt));
            st.setString(4, codCom);
            st.setInt(5, Integer.parseInt(codInt));
            rs = st.executeQuery();
            Allegati al = new Allegati();
            while (rs.next()) {
                AllegatoType am = new AllegatoType();
                am.setCopie(BigInteger.valueOf(rs.getInt("copie")));
                am.setFlgAutocertificazione(rs.getString("flg_autocert"));
                am.setFlgObbligatorieta(rs.getString("flg_obb"));
                am.setOrdinamento(BigInteger.valueOf(rs.getInt("ordinamento")));
                JAXBElement<BigInteger> v;
                if (rs.getObject("dimensione_max") != null) {
                    v = new JAXBElement(new QName(nameSpace, "dimensioneMax"),
                            BigInteger.class, BigInteger.valueOf(rs.getInt("dimensione_max")));
                    am.setDimensioneMax(v);
                }
                if (rs.getObject("num_max_pag") != null) {
                    v = new JAXBElement(new QName(nameSpace, "numPagineMax"),
                            BigInteger.class, BigInteger.valueOf(rs.getInt("num_max_pag")));
                    am.setNumPagineMax(v);
                }
// pc - add flg_verifica_firma                  
                am.setFlgVerificaFirma(BigInteger.valueOf(rs.getInt("flg_verifica_firma")));
                am.setFlgAutocertificazione(am.getFlgAutocertificazione());
                if (rs.getObject("tipologie") != null) {
                    JAXBElement<String> s = new JAXBElement(new QName(nameSpace,
                            "tipologie"), String.class,
                            rs.getString("tipologie"));
                    am.setTipologie(s);
                }
                CondizioneType tc = new CondizioneType();
                caricaTestoCondizione(tc, rs.getString("cod_cond"), conn);
                if (tc != null) {
                    am.setCondizione(tc);
                }
                Documento dm = new Documento();
                caricaDocumento(dm, rs.getString("cod_doc"), conn);
                am.setDocumento(dm);
                al.getAllegato().add(am);
            }
            if (al.getAllegato() != null) {
                in.setAllegati(al);
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            sql = "SELECT    op.cod_int, a.cod_rif, b.tit_rif, b.cod_lang, op.art_rif, a.cod_tipo_rif, a.nome_rif, tr.tipo_rif, nd.nome_file, "
                    + "nd.tip_doc, nd.doc_blob "
                    + "FROM      normative a JOIN      normative_testi b ON        a.cod_rif = b.cod_rif "
                    + "JOIN      norme_interventi op ON        a.cod_rif = op.cod_rif "
                    + "JOIN      norme_comuni c ON        a.cod_rif = c.cod_rif "
                    + "JOIN	  tipi_rif tr ON tr.cod_tipo_rif = a.cod_tipo_rif "
                    + "LEFT JOIN normative_documenti nd ON a.cod_rif=nd.cod_rif and nd.cod_lang = b.cod_lang "
                    + "where       c.cod_com = ? AND       op.cod_int = ? "
                    + "AND       c.flg = 'S' "
                    + "UNION   "
                    + "SELECT    op.cod_int, a.cod_rif,  b.tit_rif, b.cod_lang, op.art_rif, a.cod_tipo_rif, a.nome_rif, tr.tipo_rif, nd.nome_file, "
                    + "nd.tip_doc, nd.doc_blob "
                    + "FROM      normative a JOIN      normative_testi b ON        a.cod_rif = b.cod_rif "
                    + "JOIN      norme_interventi op ON        a.cod_rif = op.cod_rif "
                    + "JOIN	  tipi_rif tr ON tr.cod_tipo_rif = a.cod_tipo_rif "
                    + "LEFT JOIN norme_comuni c ON        a.cod_rif = c.cod_rif "
                    + "LEFT JOIN normative_documenti nd ON a.cod_rif=nd.cod_rif and nd.cod_lang = b.cod_lang "
                    + "WHERE     c.cod_rif IS NULL  AND       op.cod_int = ? "
                    + "UNION   "
                    + "SELECT    op.cod_int, a.cod_rif,  b.tit_rif, b.cod_lang, op.art_rif, a.cod_tipo_rif, a.nome_rif, tr.tipo_rif, nd.nome_file, "
                    + "nd.tip_doc, nd.doc_blob "
                    + "FROM      normative a JOIN      normative_testi b ON        a.cod_rif = b.cod_rif "
                    + "JOIN      norme_interventi op ON        a.cod_rif = op.cod_rif "
                    + "JOIN	  tipi_rif tr ON tr.cod_tipo_rif = a.cod_tipo_rif "
                    + "LEFT JOIN norme_comuni c ON        a.cod_rif = c.cod_rif "
                    + "LEFT JOIN normative_documenti nd ON a.cod_rif=nd.cod_rif and nd.cod_lang = b.cod_lang "
                    + "WHERE     c.cod_com != ?  AND       op.cod_int = ?  AND c.flg='N'";
            st = conn.prepareStatement(sql);
            st.setString(1, codCom);
            st.setInt(2, Integer.parseInt(codInt));
            st.setInt(3, Integer.parseInt(codInt));
            st.setString(4, codCom);
            st.setInt(5, Integer.parseInt(codInt));
            rs = st.executeQuery();
            Normative nme = new Normative();
            while (rs.next()) {
                Normativa nm = new Normativa();
                nm.setArticoloRiferimento(Utility.testoDaDb(rs.getString("art_rif")));
                nm.setCodice(rs.getString("cod_rif"));
                nm.getDescrizione().add(scriviDescrizione(Utility.testoDaDb(rs.getString("tit_rif")), "it"));
                nm.setNomeRiferimento(Utility.testoDaDb(rs.getString("nome_rif")));
                if (rs.getObject("nome_file") != null) {
                    DocumentoType fu = new DocumentoType();
                    fu.setNomeFile(rs.getString("nome_file"));
                    fu.setTipoDocumento(rs.getString("tip_doc"));
                    if (rs.getObject("doc_blob") != null) {
                        fu.setBlob(rs.getBlob("doc_blob").getBytes(1,
                                (int) rs.getBlob("doc_blob").length()));
                    }
                    nm.setDocumentoFisico(fu);
                }
                if (caricaLingua) {
                    sqlLingua = "select tit_rif, cod_lang from normative_testi where cod_rif = ? and cod_lang <> 'it'";
                    stLingua = conn.prepareStatement(sqlLingua);
                    stLingua.setString(1, rs.getString("cod_rif"));
                    rsLingua = stLingua.executeQuery();
                    while (rsLingua.next()) {
                        nm.getDescrizione().add(scriviDescrizione(Utility.testoDaDb(rsLingua.getString("tit_rif")), rsLingua.getString("cod_lang")));
                    }
                    Utility.chiusuraJdbc(rsLingua);
                    Utility.chiusuraJdbc(stLingua);
                }

                TipoNormativa tr = new TipoNormativa();
                tr.setCodice(rs.getString("cod_tipo_rif"));
                tr.getDescrizione().add(scriviDescrizione(Utility.testoDaDb(rs.getString("tipo_rif")), "it"));
                if (caricaLingua) {
                    sqlLingua = "select tipo_rif, cod_lang from tipi_rif where cod_tipo_rif = ? and cod_lang <> 'it'";
                    stLingua = conn.prepareStatement(sqlLingua);
                    stLingua.setString(1, rs.getString("cod_tipo_rif"));
                    rsLingua = stLingua.executeQuery();
                    while (rsLingua.next()) {
                        tr.getDescrizione().add(scriviDescrizione(Utility.testoDaDb(rsLingua.getString("tipo_rif")), rsLingua.getString("cod_lang")));
                    }
                    Utility.chiusuraJdbc(rsLingua);
                    Utility.chiusuraJdbc(stLingua);
                }
                nm.setTipoNormativa(tr);
                nme.getNormativa().add(nm);
            }
            if (nme.getNormativa() != null) {
                in.setNormative(nme);
            }

        } catch (Exception e) {
            log.error("Errore caricamento intervento ", e);
            throw e;
        } finally {
            Utility.chiusuraJdbc(rsLingua);
            Utility.chiusuraJdbc(stLingua);
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
        }
    }

    public void caricaTestoCondizione(CondizioneType tc, String codCond,
            Connection conn) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        PreparedStatement stLingua = null;
        ResultSet rsLingua = null;
        String sqlLingua = null;
        try {
            String sql = "select cod_cond,testo_cond from testo_condizioni where cod_cond=? and cod_lang='it'";
            if (codCond != null) {
                st = conn.prepareStatement(sql);
                st.setString(1, codCond);
                rs = st.executeQuery();
                while (rs.next()) {
                    tc.setCodice(rs.getString("cod_cond"));
                    tc.getDescrizione().add(scriviDescrizione(Utility.testoDaDb(rs.getString("testo_cond")), "it"));
                    if (caricaLingua) {
                        sqlLingua = "select testo_cond, cod_lang from testo_condizioni where cod_cond=? and cod_lang <> 'it'";
                        stLingua = conn.prepareStatement(sqlLingua);
                        stLingua.setString(1, rs.getString("cod_cond"));
                        rsLingua = stLingua.executeQuery();
                        while (rsLingua.next()) {
                            tc.getDescrizione().add(scriviDescrizione(Utility.testoDaDb(rsLingua.getString("testo_cond")), rsLingua.getString("cod_lang")));
                        }
                        Utility.chiusuraJdbc(rsLingua);
                        Utility.chiusuraJdbc(stLingua);
                    }
                }
            }
        } catch (Exception e) {
            log.error("Errore caricamento condizione ", e);
            throw e;
        } finally {
            Utility.chiusuraJdbc(rsLingua);
            Utility.chiusuraJdbc(stLingua);
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
        }
    }

    public void caricaDocumento(Documento dm, String codDoc, Connection conn)
            throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        PreparedStatement stLingua = null;
        ResultSet rsLingua = null;
        String sqlLingua = null;
        try {
            String sql = "select a.cod_doc,a.flg_dic, a.href, "
                    + "b.tit_doc, b.des_doc, "
                    + "c.nome_file, c.tip_doc, c.doc_blob "
                    + "from documenti a " + "join documenti_testi b "
                    + "on a.cod_doc=b.cod_doc "
                    + "left join documenti_documenti c "
                    + "on a.cod_doc=c.cod_doc " + "and b.cod_lang=c.cod_lang "
                    + "where a.cod_doc=? and b.cod_lang='it'";
            st = conn.prepareStatement(sql);
            st.setString(1, codDoc);
            rs = st.executeQuery();
            while (rs.next()) {
                dm.setCodice(rs.getString("cod_doc"));
                dm.getDescrizione().add(scriviDescrizione(Utility.testoDaDb(rs.getString("des_doc")), "it"));
                dm.getTitolo().add(scriviDescrizione(Utility.testoDaDb(rs.getString("tit_doc")), "it"));
                if (caricaLingua) {
                    sqlLingua = "select des_doc, tit_doc, cod_lang from documenti_testi where cod_doc=? and cod_lang <> 'it'";
                    stLingua = conn.prepareStatement(sqlLingua);
                    stLingua.setString(1, rs.getString("cod_doc"));
                    rsLingua = stLingua.executeQuery();
                    while (rsLingua.next()) {
                        dm.getDescrizione().add(scriviDescrizione(Utility.testoDaDb(rsLingua.getString("des_doc")), rsLingua.getString("cod_lang")));
                        dm.getTitolo().add(scriviDescrizione(Utility.testoDaDb(rsLingua.getString("tit_doc")), rsLingua.getString("cod_lang")));
                    }
                    Utility.chiusuraJdbc(rsLingua);
                    Utility.chiusuraJdbc(stLingua);
                }
                dm.setFlgDichiarazione(rs.getString("flg_dic"));
                if (rs.getObject("nome_file") != null) {
                    DocumentoType dt = new DocumentoType();
                    dt.setNomeFile(rs.getString("nome_file"));
                    dt.setTipoDocumento(rs.getString("tip_doc"));
                    if (rs.getObject("doc_blob") != null) {
                        dt.setBlob(rs.getBlob("doc_blob").getBytes(1,
                                (int) rs.getBlob("doc_blob").length()));
                    }
                    dm.setDocumentoFisico(dt);
                }
                if (rs.getObject("href") != null) {
                    Dichiarazione hm = new Dichiarazione();
                    caricaHref(hm, rs.getString("href"), conn);
                    dm.setDichiarazione(hm);
                }
            }
        } catch (Exception e) {
            log.error("Errore caricamento documento ", e);
            throw e;
        } finally {
            Utility.chiusuraJdbc(rsLingua);
            Utility.chiusuraJdbc(stLingua);
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
        }
    }

    public void caricaHref(Dichiarazione hm, String codHref, Connection conn)
            throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        PreparedStatement stLingua = null;
        ResultSet rsLingua = null;
        String sqlLingua = null;
        try {
            String sql = "select a.href, b.tit_href, b.piede_href "
                    + "from href a " + "join href_testi b "
                    + "on a.href=b.href "
                    + "where a.href=? and b.cod_lang='it'";
            st = conn.prepareStatement(sql);
            st.setString(1, codHref);
            rs = st.executeQuery();
            while (rs.next()) {
                hm.setCodice(codHref);
                hm.getDescrizione().add(scriviDescrizione(Utility.testoDaDb(rs.getString("tit_href")), "it"));
                hm.getTestoPiede().add(scriviDescrizione(Utility.testoDaDb(rs.getString("piede_href")), "it"));
                if (caricaLingua) {
                    sqlLingua = "select tit_href, piede_href, cod_lang from href_testi where href=? and cod_lang <> 'it'";
                    stLingua = conn.prepareStatement(sqlLingua);
                    stLingua.setString(1, codHref);
                    rsLingua = stLingua.executeQuery();
                    while (rsLingua.next()) {
                        hm.getDescrizione().add(scriviDescrizione(Utility.testoDaDb(rsLingua.getString("tit_href")), rsLingua.getString("cod_lang")));
                        hm.getTestoPiede().add(scriviDescrizione(Utility.testoDaDb(rsLingua.getString("piede_href")), rsLingua.getString("cod_lang")));
                    }
                    Utility.chiusuraJdbc(rsLingua);
                    Utility.chiusuraJdbc(stLingua);
                }
                Campi lch = new Campi();
                caricaHrefCampi(lch, codHref, conn);
                hm.setCampi(lch);
                ValoriListBox lchs = new ValoriListBox();
                caricaHrefCampiSelect(lchs, codHref, conn);
                if (lchs != null) {
                    hm.setValoriListBox(lchs);
                }
            }
        } catch (Exception e) {
            log.error("Errore carica dichiarazione ", e);
            throw e;
        } finally {
            Utility.chiusuraJdbc(rsLingua);
            Utility.chiusuraJdbc(stLingua);
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
        }
    }

    public void caricaHrefCampi(Campi ch, String codHref, Connection conn)
            throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        PreparedStatement stLingua = null;
        ResultSet rsLingua = null;
        String sqlLingua = null;
        try {
// pc - add err_msg  , add pattern            
            String sql = "select a.contatore, a.nome, a.riga, a.posizione, a.tp_riga, "
                    + "a.tipo, a.controllo, a.tp_controllo, a.valore, a.lunghezza, a.decimali, "
                    + "a.edit, a.raggruppamento_check, a.precompilazione, a.marcatore_incrociato, "
                    + "a.web_serv, a.nome_xsd, a.campo_key, a.campo_dati, a.campo_xml_mod, "
                    + "a.campo_collegato, a.val_campo_collegato, a.pattern, b.des_campo, b.err_msg "
                    + "from href_campi a "
                    + "join href_campi_testi b "
                    + "on a.href=b.href "
                    + "and a.contatore=b.contatore "
                    + "and a.nome=b.nome "
                    + "where a.href=? and b.cod_lang='it'";
            st = conn.prepareStatement(sql);
            st.setString(1, codHref);
            rs = st.executeQuery();
            while (rs.next()) {
                Campo chm = new Campo();
                chm.setCampoCollegato(rs.getString("campo_collegato"));
                chm.setCampoDati(rs.getString("campo_dati"));
                chm.setCampoKey(rs.getString("campo_key"));
                chm.setCampoXmlMod(rs.getString("campo_xml_mod"));
                chm.setContatore(BigInteger.valueOf(rs.getInt("contatore")));
                chm.setControllo(rs.getString("controllo"));
                chm.setDecimali(BigInteger.valueOf(rs.getInt("decimali")));
                chm.setEditabile(rs.getString("edit"));
                chm.setLunghezza(BigInteger.valueOf(rs.getInt("lunghezza")));
                chm.setMarcatoreIncrociato(rs.getString("marcatore_incrociato"));
                chm.setNome(rs.getString("nome"));
                chm.setNomeWebService(rs.getString("web_serv"));
                chm.setNomeXsd(rs.getString("nome_xsd"));
                chm.setPosizione(BigInteger.valueOf(rs.getInt("posizione")));
                chm.setPrecompilazione(rs.getString("precompilazione"));
                chm.setRaggruppamentoCheck(rs.getString("raggruppamento_check"));
                chm.setRiga(BigInteger.valueOf(rs.getInt("riga")));
                chm.setTipoCampo(rs.getString("tipo"));
                chm.setTipoControllo(rs.getString("tp_controllo"));
                chm.setTipoRiga(rs.getString("tp_riga"));
                chm.setValore(Utility.testoDaDb(rs.getString("valore")));
                chm.setValoreCampoCollegato(Utility.testoDaDb(rs.getString("val_campo_collegato")));
// pc - add pattern                  
                chm.setPattern(rs.getString("pattern"));
                chm.getDescrizione().add(scriviDescrizione(Utility.testoDaDb(rs.getString("des_campo")), "it"));
// pc - add err_msg                  
                chm.getErrMsg().add(scriviDescrizione(Utility.testoDaDb(rs.getString("err_msg")), "it"));
                if (caricaLingua) {
                    sqlLingua = "select des_campo, cod_lang from href_campi_testi where href=? and nome=? and contatore=? and cod_lang <> 'it'";
                    stLingua = conn.prepareStatement(sqlLingua);
                    stLingua.setString(1, codHref);
                    stLingua.setString(2, rs.getString("nome"));
                    stLingua.setInt(3, rs.getInt("contatore"));
                    rsLingua = stLingua.executeQuery();
                    while (rsLingua.next()) {
                        chm.getDescrizione().add(scriviDescrizione(Utility.testoDaDb(rsLingua.getString("des_campo")), rsLingua.getString("cod_lang")));
                        chm.getErrMsg().add(scriviDescrizione(Utility.testoDaDb(rsLingua.getString("err_msg")), rsLingua.getString("cod_lang")));
                    }
                    Utility.chiusuraJdbc(rsLingua);
                    Utility.chiusuraJdbc(stLingua);
                }
                ch.getCampo().add(chm);
            }
        } catch (Exception e) {
            log.error("Errore carica campi dichiarazione ", e);
            throw e;
        } finally {
            Utility.chiusuraJdbc(rsLingua);
            Utility.chiusuraJdbc(stLingua);
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
        }
    }

    public void caricaHrefCampiSelect(ValoriListBox ch, String codHref,
            Connection conn) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        PreparedStatement stLingua = null;
        ResultSet rsLingua = null;
        String sqlLingua = null;
        try {
            String sql = "select a.nome, a.val_select, b.des_valore "
                    + "from href_campi_valori a "
                    + "join href_campi_valori_testi b "
                    + "on a.href=b.href "
                    + "and a.nome=b.nome "
                    + "and a.val_select = b.val_select "
                    + "join (select distinct nome from href_campi where href = ?) c "
                    + "on a.nome = c.nome "
                    + "where a.href=? and b.cod_lang='it'";
            st = conn.prepareStatement(sql);
            st.setString(1, codHref);
            st.setString(2, codHref);
            rs = st.executeQuery();
            ValoreListBox chm;
            while (rs.next()) {
                chm = new ValoreListBox();
                chm.setNome(rs.getString("nome"));
                chm.setValoreListBox(rs.getString("val_select"));
                chm.getDescrizioneListBox().add(scriviDescrizione(Utility.testoDaDb(rs.getString("des_valore")), "it"));
                if (caricaLingua) {
                    sqlLingua = "select des_valore, cod_lang from href_campi_valori_testi where href=? and nome=? and val_select=? and cod_lang <> 'it'";
                    stLingua = conn.prepareStatement(sqlLingua);
                    stLingua.setString(1, codHref);
                    stLingua.setString(2, rs.getString("nome"));
                    stLingua.setString(3, rs.getString("val_select"));
                    rsLingua = stLingua.executeQuery();
                    while (rsLingua.next()) {
                        chm.getDescrizioneListBox().add(scriviDescrizione(Utility.testoDaDb(rsLingua.getString("des_valore")), rsLingua.getString("cod_lang")));
                    }
                    Utility.chiusuraJdbc(rsLingua);
                    Utility.chiusuraJdbc(stLingua);
                }
                ch.getValoreListBox().add(chm);
            }
        } catch (Exception e) {
            log.error("Errore carica campi select dichiarazione ", e);
            throw e;
        } finally {
            Utility.chiusuraJdbc(rsLingua);
            Utility.chiusuraJdbc(stLingua);
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
        }
    }

    private void updateDoc(DocumentRoot doc,
            HashMap<String, HashMap<String, String>> importXMLVariables)
            throws Exception {
        if (importXMLVariables != null) {
            HashMap<String, String> interventiDaModificare = importXMLVariables.get("interventi");
            updateIntervento(interventiDaModificare, doc.getIntervento());
            HashMap<String, String> documentiDaModificare = importXMLVariables.get("documenti");
            updateDocumenti(documentiDaModificare, doc.getIntervento());
            HashMap<String, String> dichiarazioniDaModificare = importXMLVariables.get("dichiarazioni");
            updateDichiarazioni(dichiarazioniDaModificare, doc.getIntervento());
            HashMap<String, String> normativeDaModificare = importXMLVariables.get("normative");
            updateNormative(normativeDaModificare, doc.getIntervento());
            HashMap<String, String> cudDaModificare = importXMLVariables.get("cud");
            updateCud(cudDaModificare, doc.getIntervento());
            HashMap<String, String> procedimentoDaModificare = importXMLVariables.get("procedimenti");
            updateProcedimento(procedimentoDaModificare, doc.getIntervento());
            HashMap<String, String> condizioniDaModificare = importXMLVariables.get("testo_condizioni");
            updateCondizioni(condizioniDaModificare, doc.getIntervento());
            HashMap<String, String> tipoNormativaDaModificare = importXMLVariables.get("tipi_rif");
            updateTipiRif(tipoNormativaDaModificare, doc.getIntervento());
            if (doc.getInterventiCollegati() != null && !doc.getInterventiCollegati().getInterventoCollegato().isEmpty()) {
                for (Iterator it = doc.getInterventiCollegati().getInterventoCollegato().iterator(); it.hasNext();) {
                    InterventoType in = (InterventoType) it.next();
                    updateIntervento(interventiDaModificare, in);
                    updateDocumenti(documentiDaModificare, in);
                    updateDichiarazioni(dichiarazioniDaModificare, in);
                    updateNormative(normativeDaModificare, in);
                    updateCud(cudDaModificare, in);
                    updateProcedimento(procedimentoDaModificare, in);
                    updateCondizioni(condizioniDaModificare, in);
                    updateTipiRif(tipoNormativaDaModificare, in);
                    updateInterventoCollegato(condizioniDaModificare, in);
                }
            }
        }
    }

    private void updateDocumenti(HashMap<String, String> documentiDaModificare,
            InterventoType intervento) throws Exception {
        if (documentiDaModificare != null) {
            Iterator iterator = documentiDaModificare.entrySet().iterator();
            List<AllegatoType> allegati = intervento.getAllegati().getAllegato();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                String key = (String) entry.getKey();
                String value = (String) entry.getValue();
                if (allegati != null) {
                    for (AllegatoType allegato : allegati) {
                        Documento documento = allegato.getDocumento();
                        if (documento != null && documento.getCodice() != null
                                && documento.getCodice().equals(key)) {
                            allegato.getDocumento().setCodice(value);
                        }
                    }
                }
            }
        }
    }

    private void updateDichiarazioni(
            HashMap<String, String> dichiarazioniDaModificare,
            InterventoType intervento) throws Exception {
        if (dichiarazioniDaModificare != null) {
            Iterator iterator = dichiarazioniDaModificare.entrySet().iterator();
            List<AllegatoType> allegati = intervento.getAllegati().getAllegato();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                String key = (String) entry.getKey();
                String value = (String) entry.getValue();
                if (allegati != null) {
                    for (AllegatoType allegato : allegati) {
                        Documento documento = allegato.getDocumento();
                        if (documento != null) {
                            Dichiarazione dichiarazione = documento.getDichiarazione();
                            if (dichiarazione != null
                                    && dichiarazione.getCodice() != null
                                    && dichiarazione.getCodice().equals(key)) {
                                allegato.getDocumento().getDichiarazione().setCodice(value);
                            }
                        }
                    }
                }
            }
        }
    }

    private void updateNormative(HashMap<String, String> normativeDaModificare,
            InterventoType intervento) throws Exception {
        if (normativeDaModificare != null) {
            Iterator iterator = normativeDaModificare.entrySet().iterator();
            List<Normativa> normative = intervento.getNormative().getNormativa();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                String key = (String) entry.getKey();
                String value = (String) entry.getValue();
                if (normative != null) {
                    for (Normativa normativa : normative) {
                        if (normativa != null && normativa.getCodice() != null
                                && normativa.getCodice().equals(key)) {
                            normativa.setCodice(value);
                        }
                    }
                }
            }
        }
    }

    private void updateCud(HashMap<String, String> cudDaModificare,
            InterventoType intervento) throws Exception {
        if (cudDaModificare != null) {
            Iterator iterator = cudDaModificare.entrySet().iterator();
            CudType cud = intervento.getProcedimento().getCud();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                String key = (String) entry.getKey();
                String value = (String) entry.getValue();
                if (cud != null && cud.getCodice() != null
                        && cud.getCodice().equals(key)) {
                    cud.setCodice(value);
                }
            }
        }
    }

    private void updateProcedimento(
            HashMap<String, String> procedimentoDaModificare,
            InterventoType intervento) throws Exception {
        if (procedimentoDaModificare != null) {
            Iterator iterator = procedimentoDaModificare.entrySet().iterator();
            Procedimento procedimento = intervento.getProcedimento();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                String key = (String) entry.getKey();
                String value = (String) entry.getValue();
                if (procedimento != null && procedimento.getCodice() != null
                        && procedimento.getCodice().equals(key)) {
                    procedimento.setCodice(value);
                }
            }
        }
    }

    private void updateCondizioni(
            HashMap<String, String> condizioniDaModificare,
            InterventoType intervento) throws Exception {
        if (condizioniDaModificare != null) {
            Iterator iterator = condizioniDaModificare.entrySet().iterator();
            List<AllegatoType> allegati = intervento.getAllegati().getAllegato();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                String key = (String) entry.getKey();
                String value = (String) entry.getValue();
                if (allegati != null) {
                    for (AllegatoType allegato : allegati) {
                        CondizioneType condizione = allegato.getCondizione();
                        if (condizione != null
                                && condizione.getCodice() != null
                                && condizione.getCodice().equals(key)) {
                            condizione.setCodice(value);
                        }
                    }
                }
            }
        }
    }

    private void updateIntervento(
            HashMap<String, String> interventoDaModificare,
            InterventoType intervento) throws Exception {
        if (interventoDaModificare != null) {
            Iterator iterator = interventoDaModificare.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                String key = (String) entry.getKey();
                String value = (String) entry.getValue();
                if (intervento != null && intervento.getCodice() != null
                        && intervento.getCodice().equals(new BigInteger(key))) {
                    intervento.setCodice(new BigInteger(value));
                }
            }
        }
    }

    private void updateInterventoCollegato(
            HashMap<String, String> condizioneDaModificare,
            InterventoType intervento) throws Exception {
        if (condizioneDaModificare != null) {
            Iterator iterator = condizioneDaModificare.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                String key = (String) entry.getKey();
                String value = (String) entry.getValue();
                if (intervento != null && intervento.getCondizione() != null
                        && intervento.getCondizione().getCodice() != null && intervento.getCondizione().getCodice().equals(key)) {
                    intervento.getCondizione().setCodice(value);
                }
            }
        }
    }

    private void updateTipiRif(
            HashMap<String, String> tipoNormativaDaModificare,
            InterventoType intervento) throws Exception {
        if (tipoNormativaDaModificare != null) {
            Iterator iterator = tipoNormativaDaModificare.entrySet().iterator();
            while (iterator.hasNext()) {
                List<Normativa> normative = intervento.getNormative().getNormativa();
                while (iterator.hasNext()) {
                    Map.Entry entry = (Map.Entry) iterator.next();
                    String key = (String) entry.getKey();
                    String value = (String) entry.getValue();
                    if (normative != null) {
                        for (Normativa normativa : normative) {
                            TipoNormativa tipoNormativa = normativa.getTipoNormativa();
                            if (tipoNormativa != null
                                    && tipoNormativa.getCodice() != null
                                    && tipoNormativa.getCodice().equals(key)) {
                                tipoNormativa.setCodice(value);
                            }
                        }
                    }
                }
            }
        }
    }

    public void aggiornaPubblicazione(Integer codInt, Connection conn)
            throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        String sql = null;
        try {
            sql = "update interventi set data_modifica=current_timestamp where cod_int=?";
            st = conn.prepareStatement(sql);
            st.setInt(1, codInt);
            st.executeUpdate();
            Boolean aggiorna = Boolean.parseBoolean(Configuration.getConfigurationParameter("updateTCR").toLowerCase());
            if (aggiorna) {
                sql = "update interventi set flg_pubblicazione='S' where cod_int=?";
                st = conn.prepareStatement(sql);
                st.setInt(1, codInt);
                st.executeUpdate();
            }
        } catch (Exception e) {
            log.error("Errore aggiornamento notifica per TCR", e);
            throw e;
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
        }
    }

    private void caricaInterventiCollegati(DocumentRoot doc, String codInt, String codCom, Connection conn) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        String sql = null;
        try {
            sql = "select a.cod_int, b.cod_cond from interventi_collegati a "
                    + "join interventi i "
                    + "on a.cod_int = i.cod_int "
                    + "join interventi_testi it "
                    + "on a.cod_int = it.cod_int and it.cod_lang= 'it' "
                    + "left join testo_condizioni b on a.cod_cond = b.cod_cond and b.cod_lang='it' where a.cod_int_padre=?";
            st = conn.prepareStatement(sql);
            st.setInt(1, Integer.parseInt(codInt));
            rs = st.executeQuery();
            it.people.dbm.ws.DocumentRoot.InterventiCollegati ic = null;
            while (rs.next()) {
                if (rs.isFirst()) {
                    ic = new DocumentRoot.InterventiCollegati();
                }
                InterventoType in = new InterventoType();
                caricaIntervento(in, String.valueOf(rs.getInt("cod_int")), codCom, conn);
                if (rs.getString("cod_cond") != null) {
                    CondizioneType tc = new CondizioneType();
                    caricaTestoCondizione(tc, rs.getString("cod_cond"), conn);
                    in.setCondizione(tc);
                }
                ic.getInterventoCollegato().add(in);
            }
            if (ic != null) {
                doc.setInterventiCollegati(ic);
            }
        } catch (Exception e) {
            log.error("Errore aggiornamento notifica per TCR", e);
            throw e;
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
        }
    }

    private String cercaDescrizione(List<DescrizioneType> descrizioni, String lang) {
        String descrizione = null;
        for (Iterator it = descrizioni.iterator(); it.hasNext();) {
            DescrizioneType des = (DescrizioneType) it.next();
            if (des.getLang() == null && lang.equalsIgnoreCase("it")) {
                descrizione = des.getValue();
            } else if (des.getLang() != null && des.getLang().equalsIgnoreCase(lang)) {
                descrizione = des.getValue();
            }
        }
        return descrizione;

    }

    private DescrizioneType scriviDescrizione(String descrizione, String lang) {
        DescrizioneType descrizioneType = new DescrizioneType();
        descrizioneType.setValue(descrizione);
        if (!lang.equalsIgnoreCase("it")) {
            descrizioneType.setLang(lang);
        }
        return descrizioneType;
    }

    private List<String> cercaLingue(String aggregazione, Connection conn) throws Exception {
        List<String> lingue = new ArrayList<String>();
        PreparedStatement st = null;
        ResultSet rs = null;
        String sql = null;
        try {
            if (aggregazione != null) {
                sql = "select cod_lang from lingue_aggregazioni where tip_aggregazione=?";
                st = conn.prepareStatement(sql);
                st.setString(1, aggregazione);
                rs = st.executeQuery();
                while (rs.next()) {
                    lingue.add(rs.getString("cod_lang"));
                }
            }
            return lingue;
        } catch (Exception e) {
            log.error("Errore aggiornamento notifica per TCR", e);
            throw e;
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
        }
    }

    private void scriviInterventiCollegati(InterventiCollegati interventiCollegati, Connection conn, Integer codIntOrigine) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        PreparedStatement st1 = null;
        ResultSet rs1 = null;
        String sqlInsert;
        String sqlRead;
        String sqlUpdate;
        try {

            sqlRead = "select cod_int_padre from interventi_collegati where cod_int_padre = ? and cod_int = ?";

            sqlInsert = "insert into interventi_collegati (cod_int_padre, cod_int, cod_cond) values (?,?,?)";
            sqlUpdate = "update interventi_collegati set cod_cond = ? where cod_int_padre = ? and  cod_int = ?";
            if (interventiCollegati != null) {
                interventiCollegati.getInterventoCollegato().iterator();
                for (Iterator it = interventiCollegati.getInterventoCollegato().iterator(); it.hasNext();) {
                    InterventoType in = (InterventoType) it.next();

                    if (scelta.equals(importazioneDaXml)) {
                        scriviInterventi(in, conn);
                    }

                    st = conn.prepareStatement(sqlRead);
                    st.setInt(1, codIntOrigine);
                    st.setInt(2, in.getCodice().intValue());
                    rs = st.executeQuery();
                    if (!(checkNewExistence(codIntOrigine.toString(), "interventi") && checkExclussion(codIntOrigine.toString(), "interventi"))
                            && !(checkExclussion(in.getCodice().toString(), "interventi") && checkNewExistence(in.getCodice().toString(), "interventi"))) {
                        if (rs.next()) {
                            st1 = conn.prepareStatement(sqlUpdate);
                            if (in.getCondizione() != null && !(checkNewExistence(in.getCondizione().getCodice(), "testo_condizioni") && checkExclussion(in.getCondizione().getCodice(), "test_condizioni"))) {
                                st1.setString(1, in.getCondizione().getCodice());
                            } else {
                                st1.setNull(1, java.sql.Types.VARCHAR);
                            }
                            st1.setInt(2, codIntOrigine);
                            st1.setInt(3, in.getCodice().intValue());
                            st1.executeUpdate();
                        } else {
                            st1 = conn.prepareStatement(sqlInsert);
                            st1.setInt(1, codIntOrigine);
                            st1.setInt(2, in.getCodice().intValue());
                            if (in.getCondizione() != null && !(checkNewExistence(in.getCondizione().getCodice(), "testo_condizioni") && checkExclussion(in.getCondizione().getCodice(), "test_condizioni"))) {
                                st1.setString(3, in.getCondizione().getCodice());
                            } else {
                                st1.setNull(3, java.sql.Types.VARCHAR);
                            }
                            st1.executeUpdate();
                        }
                    }
                    Utility.chiusuraJdbc(rs1);
                    Utility.chiusuraJdbc(st1);
                    Utility.chiusuraJdbc(rs);
                    Utility.chiusuraJdbc(st);
                }
            }
        } catch (Exception e) {
            log.error("Errore inserimento collegamento intervento collegato", e);
            throw e;
        } finally {
            Utility.chiusuraJdbc(rs1);
            Utility.chiusuraJdbc(st1);
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
        }
    }

    private void verificaInterventiCollegati(JSONObject jsonIntervento, JSONArray jcod, InterventiCollegati interventiCollegati, Connection conn) throws Exception {
        JSONObject ji;
        JSONArray ja = new JSONArray();
        JSONObject jsonCollegati = new JSONObject();
        if (interventiCollegati != null) {
            for (Iterator it = interventiCollegati.getInterventoCollegato().iterator(); it.hasNext();) {
                ji = new JSONObject();
                InterventoType in = (InterventoType) it.next();

                verificaIntervento(jsonCollegati, in, conn);
                verificaProcedimento(jsonCollegati, in.getProcedimento(), conn);
                verificaAllegati(jsonCollegati, in.getAllegati(), conn);
                verificaNormativeInterventi(jsonCollegati, in.getNormative(), conn);
                verificaCondizioniCollegati(jsonCollegati, in, conn);
                jsonIntervento.put(in.getCodice().toString(), jsonCollegati);
                ja.add(ji);
                jcod.add(in.getCodice().toString());
            }
        }

    }

    private void verificaCondizioniCollegati(JSONObject jsonCollegati, InterventoType intervento, Connection conn) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        String sql = null;
        try {
            JSONArray condizioni = jsonCollegati.getJSONArray("testo_condizioni");
            List verificaCondizioni = new ArrayList();
            if (condizioni != null && !condizioni.isEmpty()) {
                for (Iterator it = condizioni.iterator(); it.hasNext();) {
                    JSONObject cond = (JSONObject) it.next();
                    verificaCondizioni.add(cond.getString("codice"));
                }
            }
            if (intervento.getCondizione() != null && !verificaCondizioni.contains(intervento.getCondizione().getCodice())) {
                verificaCondizioni.add(intervento.getCondizione().getCodice());
                HashMap<String, String> condizioniVariables = importXMLVariables.get("testo_condizioni");
                JSONObject jd = new JSONObject();
                jd.put("condExt", intervento.getCondizione().getCodice());
                jd.put("desExt", cercaDescrizione(intervento.getCondizione().getDescrizione(), "it"));
                String cod_cond = null;

                jd.put("esistenza", "N");
                sql = "select cod_cond,testo_cond from testo_condizioni where cod_cond=? and cod_lang='it'";
                st = conn.prepareStatement(sql);
                st.setString(1, intervento.getCondizione().getCodice());
                rs = st.executeQuery();
                while (rs.next()) {
                    cod_cond = rs.getString("cod_cond");
                    addEditableColumn(jd, cod_cond);
                    condizioniVariables.put(cod_cond, cod_cond);
                    verificaCondizioni.add(cod_cond);
                    jd.put("condInt", rs.getString("cod_cond"));
                    jd.put("desInt", rs.getString("testo_cond"));
                    jd.put("esistenza", "D");
                }
                condizioni.add(jd);
            }
        } catch (Exception e) {
            log.error("Errore inserimento condizioni intervento collegato", e);
            throw e;
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
        }
    }

    public JSONObject excludeEntity(HttpServletRequest request) {
        initExcludes(request);
        JSONObject ret = new JSONObject();
        if (importExcludes != null) {
            String type = request.getParameter("type");
            String esistenza = request.getParameter("esistenza");
            String status = request.getParameter("status");
            String code = request.getParameter("code");
            ArrayList<String> excludes = importExcludes.get(type);
            if (excludes != null) {
                if (status.equalsIgnoreCase("on")) {
                    excludes.add(code);
                } else {
                    excludes.remove(code);
                }
            }
            try {
                ret.put("propagation", propagateExclussion(request));
            } catch (Exception e) {
                log.error("Propagation Failed", e);
            }
        }
        return ret;
    }

    private JSONObject propagateExclussion(HttpServletRequest request) throws Exception {
        JSONObject result = new JSONObject();
        String xml = (String) request.getSession().getAttribute("UPXML");
        importXMLVariables = (HashMap<String, HashMap<String, String>>) request.getSession().getAttribute("newVariables");
        importExistences = (HashMap<String, HashMap<String, String>>) request.getSession().getAttribute("existences");
        if (xml != null) {
            JAXBContext jc = JAXBContext.newInstance(DocumentRoot.class);
            Unmarshaller um = jc.createUnmarshaller();
            InputStream is = new ByteArrayInputStream(xml.getBytes("UTF-8"));
            DocumentRoot doc = (DocumentRoot) um.unmarshal(is);
            if (importXMLVariables != null) {
                updateDoc(doc, importXMLVariables);
            }
            propagateIntervento(doc.getIntervento());
            if (doc.getInterventiCollegati() != null) {
                for (InterventoType interventoType : doc.getInterventiCollegati().getInterventoCollegato()) {
                    propagateIntervento(interventoType);
                }
            }

            populateJSONObject(result);
        }
        return result;
    }

    private void populateJSONObject(JSONObject result) {
        result.put("interventi", populateEntityJSONArray("interventi"));
        result.put("procedimenti", populateEntityJSONArray("procedimenti"));
        result.put("cud", populateEntityJSONArray("cud"));
        result.put("documenti", populateEntityJSONArray("documenti"));
        result.put("dichiarazioni", populateEntityJSONArray("dichiarazioni"));
        result.put("testo_condizioni", populateEntityJSONArray("testo_condizioni"));
        result.put("normative", populateEntityJSONArray("normative"));
        result.put("tipi_rif", populateEntityJSONArray("tipi_rif"));
    }

    private JSONArray populateEntityJSONArray(String entityType) {
        JSONArray array = new JSONArray();
        if (importExcludes.get(entityType) != null) {
            for (String code : importExcludes.get(entityType)) {
                array.add(code);
            }
        }
        return array;
    }

    public void setExclude(String AentityType, Object Acode, String BentityType, Object Bcode) {
        if (importExistences.get(BentityType).get(Bcode.toString()).equalsIgnoreCase("N") && importExcludes.get(BentityType).contains(Bcode.toString())) {
            if (!importExcludes.get(AentityType).contains(Acode.toString())) {
                importExcludes.get(AentityType).add(Acode.toString());
            }
        }
    }

    private void propagateIntervento(InterventoType intervento) {
        propagateProcedimento(intervento.getProcedimento());
        setExclude("interventi", intervento.getCodice(), "procedimenti", intervento.getProcedimento().getCodice());

        propagateNormative(intervento.getNormative());
        propagateAllegati(intervento.getAllegati());
    }

    private void propagateAllegati(Allegati allegati) {
        for (AllegatoType allegatoType : allegati.getAllegato()) {
            propagateDocumento(allegatoType.getDocumento());
        }
    }

    private void propagateDocumento(Documento documento) {
        if (documento.getDichiarazione() != null) {
            setExclude("documenti", documento.getCodice(), "dichiarazioni", documento.getDichiarazione().getCodice());
        }
    }

    private void propagateNormative(Normative normative) {
        for (Normativa normativa : normative.getNormativa()) {
            propagateNormativa(normativa);
        }
    }

    private void propagateNormativa(Normativa normativa) {
        setExclude("normative", normativa.getCodice(), "tipi_rif", normativa.getTipoNormativa().getCodice());
    }

    private void propagateProcedimento(Procedimento procedimento) {
        setExclude("procedimenti", procedimento.getCodice(), "cud", procedimento.getCud().getCodice());
    }
}
