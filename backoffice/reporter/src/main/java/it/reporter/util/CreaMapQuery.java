/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.reporter.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathExpressionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import it.reporter.xsd.data.DocumentRoot.Definitions;
import it.reporter.xsd.data.DocumentRoot.Definitions.MetaDato;
import it.reporter.xsd.data.DocumentRoot.Queryes;
import it.reporter.xsd.data.DocumentRoot.Queryes.Query;
import it.reporter.xsd.inputParameters.InputParametersRoot;

/**
 *
 * @author Piergiorgio
 */
public class CreaMapQuery {

    Document doc;
    Definitions def;
    List parentList;
    Iterator itmd;
    List<String> listParm;
    List parametersList;
    Iterator itpl;
    File tmpDirImage;
    File tmpDirDocument;
    private static Logger log = LoggerFactory.getLogger(CreaMapQuery.class.getName());

    public Map<String, QueryModel> PopolaMapQuery(Queryes queryes) throws Exception {

        Map<String, QueryModel> ret = null;
        String id;
        String child;
        String padre;
        String query;
        QueryModel qm;
        QueryModel qmu;
        String[] figli;
        if (queryes != null) {
            ret = new LinkedHashMap<String, QueryModel>();
            List<Query> lq = queryes.getQuery();
            String[] ch;
            for (int i = 0; i < lq.size(); i++) {
                ch = null;
                qm = new QueryModel();
                id = lq.get(i).getId();
                child = lq.get(i).getChild();
                query = lq.get(i).getValue();
                qm.setQuery(query);
                if (child != null) {
                    ch = child.split(",");
                    qm.setFiglio(ch);
                    ret.put(id, qm);

                } else {
                    ret.put(id, qm);
                }
            }

            for (Map.Entry entry : ret.entrySet()) {
                qm = (QueryModel) entry.getValue();
                if (qm.getFiglio() != null) {
                    figli = qm.getFiglio();
                    for (int i = 0; i < figli.length; i++) {
                        qmu = ret.get(figli[i]);
                        qmu.setPadre(entry.getKey().toString());
                        ret.put(figli[i], qmu);
                    }
                }
            }

        }
        return ret;
    }

    public String PopolaXmlQuery(Connection conn, Map<String, QueryModel> mapQuery, Definitions definitions, InputParametersRoot ipr, File tmpDirectoryImage, File tmpDirectoryDocument, HashMap<String, String> listaDefault) throws Exception {
        doc = null;
        QueryModel qm;
        def = definitions;
        parentList = definitions.getMetaDato();
        parametersList = ipr.getInputParameters();
        tmpDirImage = tmpDirectoryImage;
        tmpDirDocument = tmpDirectoryDocument;

        if (!mapQuery.isEmpty()) {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            DOMImplementation impl = builder.getDOMImplementation();

            doc = impl.createDocument(null, null, null);
            Element e1 = doc.createElement("documentRoot");
            doc.appendChild(e1);

            Element eldef = doc.createElement("definitions");
            e1.appendChild(eldef);
            Text text;
            for (MetaDato md : definitions.getMetaDato()) {
                Element elmd = doc.createElement("metaDato");
                eldef.appendChild(elmd);
                Element elns = doc.createElement("nomeSimbolico");
                elmd.appendChild(elns);
                text = doc.createTextNode(md.getNomeSimbolico());
                elns.appendChild(text);

                Element eltp = doc.createElement("tipo");
                elmd.appendChild(eltp);
                text = doc.createTextNode(md.getTipo());
                eltp.appendChild(text);

                Element elds = doc.createElement("descrizione");
                elmd.appendChild(elds);
                text = doc.createTextNode(md.getDescrizione().getValue());
                elds.appendChild(text);

                Element elpt = doc.createElement("path");
                elmd.appendChild(elpt);
                text = doc.createTextNode(md.getPath().getValue());
                elpt.appendChild(text);

                if (md.getFormat() != null) {
                    Element elft = doc.createElement("format");
                    elmd.appendChild(elft);
                    text = doc.createTextNode(md.getFormat().getValue());
                    elft.appendChild(text);
                }
            }
            for (Map.Entry entry : mapQuery.entrySet()) {
                if (((QueryModel) entry.getValue()).getPadre() == null) {
                    generaStruttura(e1, entry.getKey().toString(), (QueryModel) entry.getValue(), conn, mapQuery, listaDefault);

                }
            }
            generaTabImmaginiDaFile(e1, ipr);
        }

        DOMSource domSource = new DOMSource(doc);
        StringWriter stringWriter = new StringWriter();
        Result result = new StreamResult(stringWriter);
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
        transformer.transform(domSource, result);
        String xml = stringWriter.getBuffer().toString();
        log.debug("XML : " + xml);

        return xml;
    }

    private void generaStruttura(Element e2, String nodo, QueryModel queryModel, Connection conn, Map<String, QueryModel> mapQuery, HashMap<String, String> listaDefault) throws Exception {
        String query;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<String> lc = null;
        Text text = null;
        Integer xi;
        Date xd;
        Timestamp xt;
        MetaDato md;
        String id;
        try {
            query = queryModel.getQuery();
            String queryPrepared = preparaQuery(query);
            ps = conn.prepareStatement(queryPrepared);
            caricaCondizioni(ps, query, e2);
            rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int numColumns = rsmd.getColumnCount();
            // Get the column names; column indices start from 1
            lc = new ArrayList<String>();

            for (int i = 1; i < numColumns + 1; i++) {
                lc.add(rsmd.getColumnName(i));
            }

            while (rs.next()) {
                Element e3 = doc.createElement(nodo);
                e2.appendChild(e3);
                for (int i = 0; i < lc.size(); i++) {
                    md = findMetadato(lc.get(i));

                    Element e4 = doc.createElement(lc.get(i));
                    e3.appendChild(e4);
                    if (md.getTipo().equalsIgnoreCase("string")) {
                        if (rs.getObject(lc.get(i)) != null) {
                            if (md.getFormat() != null && !md.getFormat().getValue().isEmpty()) {
                                text = doc.createTextNode(String.format(md.getFormat().getValue().toString(), rs.getString(lc.get(i))));
                            } else {
                                text = doc.createTextNode(rs.getString(lc.get(i)));
                            }
                        } else if (md.getIfNull() != null && !md.getIfNull().getValue().isEmpty()) {
                            String segnaposto = "Replace=" + calcolaRandom();
                            listaDefault.put(segnaposto, md.getIfNull().getValue());
                            text = doc.createTextNode(segnaposto);
                        } else {
                            text = doc.createTextNode("");
                        }
                    }
                    if (md.getTipo().equalsIgnoreCase("integer") || md.getTipo().equalsIgnoreCase("int")) {
                        if (rs.getObject(lc.get(i)) != null) {
                            xi = rs.getInt(lc.get(i));
                            if (md.getFormat() != null && !md.getFormat().getValue().isEmpty()) {
                                try {
                                    DecimalFormat df2 = new DecimalFormat(md.getFormat().getValue().toString());
                                    text = doc.createTextNode(df2.format(xi));
                                } catch (Exception e) {
                                    log.error("Errore Format : " + lc.get(i));
                                }
                            } else {
                                text = doc.createTextNode(xi.toString());
                            }
                        } else if (md.getIfNull() != null && !md.getIfNull().getValue().isEmpty()) {
                            String segnaposto = "Replace=" + calcolaRandom();
                            listaDefault.put(segnaposto, md.getIfNull().getValue());
                            text = doc.createTextNode(segnaposto);
                        } else {
                            text = doc.createTextNode("");
                        }
                    }
                    if (md.getTipo().equalsIgnoreCase("num") || md.getTipo().equalsIgnoreCase("number")) {
                        if (rs.getObject(lc.get(i)) != null) {
                            if (md.getFormat() != null && !md.getFormat().getValue().isEmpty()) {
                                try {
                                    DecimalFormat df2 = new DecimalFormat(md.getFormat().getValue().toString());
                                    text = doc.createTextNode(df2.format(rs.getObject(lc.get(i))));
                                } catch (Exception e) {
                                    log.error("Errore Format : " + lc.get(i));
                                }
                            } else {
                                text = doc.createTextNode(rs.getString(lc.get(i)));
                            }
                        } else if (md.getIfNull() != null && !md.getIfNull().getValue().isEmpty()) {
                            String segnaposto = "Replace=" + calcolaRandom();
                            listaDefault.put(segnaposto, md.getIfNull().getValue());
                            text = doc.createTextNode(segnaposto);
                        } else {
                            text = doc.createTextNode("");
                        }
                    }
                    if (md.getTipo().equalsIgnoreCase("datetime")) {
                        if (rs.getObject(lc.get(i)) != null) {
                            SimpleDateFormat sdf;
                            if (md.getFormat() != null && !md.getFormat().getValue().isEmpty()) {
                                try {
                                    sdf = new SimpleDateFormat(md.getFormat().getValue().toString());
                                    xt = rs.getTimestamp(lc.get(i));
                                    text = doc.createTextNode(sdf.format(xt));
                                } catch (Exception e) {
                                    log.error("Errore Format : " + lc.get(i));
                                }
                            } else {
                                try {
                                    sdf = new SimpleDateFormat("d-MMM-yyyy H.mm.ss");
                                    xt = rs.getTimestamp(lc.get(i));
                                    text = doc.createTextNode(sdf.format(xt));
                                } catch (Exception e) {
                                    log.error("Errore Format : " + lc.get(i));
                                }
                            }
                        } else if (md.getIfNull() != null && !md.getIfNull().getValue().isEmpty()) {
                            String segnaposto = "Replace=" + calcolaRandom();
                            listaDefault.put(segnaposto, md.getIfNull().getValue());
                            text = doc.createTextNode(segnaposto);
                        } else {
                            text = doc.createTextNode("");
                        }
                    }
                    if (md.getTipo().equalsIgnoreCase("date")) {
                        if (rs.getObject(lc.get(i)) != null) {
                            SimpleDateFormat sdf;
                            if (md.getFormat() != null && !md.getFormat().getValue().isEmpty()) {
                                try {
                                    sdf = new SimpleDateFormat(md.getFormat().getValue().toString());
                                    xd = rs.getDate(lc.get(i));
                                    text = doc.createTextNode(sdf.format(xd));
                                } catch (Exception e) {
                                    log.error("Errore Format : " + lc.get(i));
                                }
                            } else {
                                try {
                                    sdf = new SimpleDateFormat("d-MMM-yyyy");
                                    xd = rs.getDate(lc.get(i));
                                    text = doc.createTextNode(sdf.format(xd));
                                } catch (Exception e) {
                                    log.error("Errore Format : " + lc.get(i));
                                }
                            }
                        } else if (md.getIfNull() != null && !md.getIfNull().getValue().isEmpty()) {
                            String segnaposto = "Replace=" + calcolaRandom();
                            listaDefault.put(segnaposto, md.getIfNull().getValue());
                            text = doc.createTextNode(segnaposto);
                        } else {
                            text = doc.createTextNode("");
                        }
                    }
                    if (md.getTipo().equalsIgnoreCase("image")) {
                        if (rs.getObject(lc.get(i)) != null) {
                            Blob blob = rs.getBlob(lc.get(i));
                            id = calcolaRandom();

                            File picture = new File(tmpDirImage.getAbsolutePath() + File.separator + id);

                            FileOutputStream fos = new FileOutputStream(picture);
                            fos.write(blob.getBytes(1, (int) blob.length()));
                            fos.close();
                            text = doc.createTextNode("Image=" + id);
                        } else {
                            text = doc.createTextNode("");
                        }
                    }
                    if (md.getTipo().equalsIgnoreCase("doc")) {
                        if (rs.getObject(lc.get(i)) != null) {
                            Blob blob = rs.getBlob(lc.get(i));
                            id = calcolaRandom();

                            File documento = new File(tmpDirDocument.getAbsolutePath() + File.separator + id);

                            FileOutputStream fos = new FileOutputStream(documento);
                            fos.write(blob.getBytes(1, (int) blob.length()));
                            fos.close();
                            text = doc.createTextNode("Document=" + id);
                        } else {
                            text = doc.createTextNode("");
                        }
                    }
                    e4.appendChild(text);
                }
                if (queryModel.getFiglio() != null) {
                    for (int i = 0; i < queryModel.getFiglio().length; i++) {
                        generaStruttura(e3, queryModel.getFiglio()[i], mapQuery.get(queryModel.getFiglio()[i]), conn, mapQuery, listaDefault);
                    }
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        }

    }

    private void caricaCondizioni(PreparedStatement ps, String query, Element ele) throws XPathExpressionException, SQLException {
        String valore;
        String p;
        MetaDato md;
        for (int i = 0; i < listParm.size(); i++) {
            p = listParm.get(i);
            if (p.substring(0, 1).equals("@")) {
                // parametri riferiti alla query padre
                String nome = p.substring(1).toUpperCase();
                NodeList nl = ele.getElementsByTagName(nome);
                if (nl == null) {
                    nome = p.substring(1).toLowerCase();
                    nl = ele.getElementsByTagName(nome);
                }

                Node namechild = nl.item(0);
                valore = namechild.getTextContent();
                md = findMetadato(p.substring(1));
                recuparaValore(md, ps, valore, i);
            }
            if (p.substring(0, 1).equals("#")) {
                // parametri riferiti a parametri esterni
                itpl = parametersList.iterator();
                while (itpl.hasNext()) {
                    InputParametersRoot.InputParameters ip = (InputParametersRoot.InputParameters) itpl.next();
                    if (ip.getName().equalsIgnoreCase(p.substring(1))) {
                        valore = ip.getValue();
                        md = findMetadato(p.substring(1));
                        recuparaValore(md, ps, valore, i);
                    }
                }
            }
        }
    }

    private String preparaQuery(String query) {
        listParm = new ArrayList<String>();
        String q = query;
        String so;
        String sa;

        if (query.indexOf("@") != -1 || query.indexOf("#") != -1) {
            so = query;
            sa = "";
            int len = q.length();
            int posAtt = 0;
            int posBlank;
            int i = 0;
            while (i < len) {

                posAtt = trovaParametro(so);
                if (posAtt != -1) {
                    sa = sa + so.substring(0, posAtt) + "?";
                    so = so.substring(posAtt);
                    posBlank = so.indexOf(" ");
                    if (posBlank > 0) {
                        listParm.add(so.substring(0, posBlank));
                        so = so.substring(posBlank);
                        i = i + posAtt + posBlank;
                    } else {
                        listParm.add(so.substring(0));
                        i = len;
                    }
                } else {
                    sa = sa + so;
                    i = i + so.length();
                }

            }
            q = sa;
        }

        return q;
    }

    private int trovaParametro(String query) {
        int posAtt = -1;
        int parInt = query.indexOf("@");
        int parExt = query.indexOf("#");
        if (parInt != -1) {
            if (parExt != -1) {
                if (parInt < parExt) {
                    posAtt = parInt;
                } else {
                    posAtt = parExt;
                }
            } else {
                posAtt = parInt;
            }
        } else {
            if (parExt != -1) {
                posAtt = parExt;
            }
        }
        return posAtt;
    }

    private MetaDato findMetadato(String nome) {
        MetaDato md = null;
        itmd = parentList.iterator();
        while (itmd.hasNext()) {
            md = (MetaDato) itmd.next();
            if (md.getNomeSimbolico().equalsIgnoreCase(nome)) {
                break;
            }
        }
        return md;
    }

    private String ricercaDocuemnto(MetaDato md, InputParametersRoot ipr) {
        String file = null;
        if (md.getPath() != null) {
            if (md.getPath().getValue().indexOf("#") > -1) {
                //ricerco filmd.getPath().getValue()e da parametro di input
                itpl = parametersList.iterator();
                while (itpl.hasNext()) {
                    InputParametersRoot.InputParameters ip = (InputParametersRoot.InputParameters) itpl.next();
                    if (ip.getName().equalsIgnoreCase(md.getPath().getValue().substring(1))) {
                        file = ip.getValue();
                    }
                }
            } else {
                // ricerco file da parametro assoluto
                file = md.getPath().getValue();
            }
        }
        if (file != null) {
            File f = new File(file);
            if (!f.exists()) {
                log.debug("File non trovato: " + file);
                file = null;
            }
        }
        return file;
    }

    private void recuparaValore(MetaDato md, PreparedStatement ps, String valore, int i) {
        try {
            if (md.getTipo().equalsIgnoreCase("string")) {
                ps.setString(i + 1, valore);
            }
            if (md.getTipo().equalsIgnoreCase("integer") || md.getTipo().equalsIgnoreCase("int")) {
                ps.setInt(i + 1, Integer.parseInt(valore));
            }
            if (md.getTipo().equalsIgnoreCase("num")) {
                ps.setBigDecimal(i + 1, new BigDecimal(valore));
            }
        } catch (Exception e) {
            log.error("Errore", e);
        }
    }

    public HashMap<String, String> generaListaFileEsterni(InputParametersRoot ipr) throws Exception {
        HashMap<String, String> listaFile = null;
        // genera struttura dei documenti se presenti nei metadati e presenti sul filesystem
        MetaDato md = null;
        itmd = parentList.iterator();
        String file = null;
        boolean trovato = false;
        while (itmd.hasNext()) {
            md = (MetaDato) itmd.next();
            if (md.getTipo().equalsIgnoreCase("doc")) {
                file = null;
                file = ricercaDocuemnto(md, ipr);
                if (file != null) {
                    if (!trovato) {
                        listaFile = new HashMap<String, String>();
                        trovato = true;
                    }
                    listaFile.put(md.getNomeSimbolico(), file);
                }
            }
        }
        return listaFile;
    }

    private void generaTabImmaginiDaFile(Element e1, InputParametersRoot ipr) throws Exception {
        MetaDato md = null;
        boolean trovato = false;
        Element e2 = doc.createElement("Immagini");
        Text text = null;
        itmd = parentList.iterator();
        byte[] readData = new byte[1024];
        File picture;
        File immagine;
        FileOutputStream fos;
        FileInputStream fis;
        while (itmd.hasNext()) {
            md = (MetaDato) itmd.next();
            if (md.getTipo().equalsIgnoreCase("image")) {
                if (md.getPath() != null && !md.getPath().getValue().equals("")) {
                    String file = ricercaDocuemnto(md, ipr);
                    String id = calcolaRandom();
                    if (file != null) {
                        if (!trovato) {
                            trovato = true;
                            e1.appendChild(e2);
                        }
                        Element e3 = doc.createElement(md.getNomeSimbolico());
                        e2.appendChild(e3);
                        text = doc.createTextNode("Image=" + id);
                        e3.appendChild(text);
                        picture = new File(tmpDirImage.getAbsolutePath() + File.separator + id);
                        immagine = new File(file);
                        fos = new FileOutputStream(picture);
                        fis = new FileInputStream(immagine);
                        int i = fis.read(readData);
                        while (i != -1) {
                            fos.write(readData, 0, i);
                            i = fis.read(readData);
                        }
                        fis.close();
                        fos.flush();
                        fos.close();
                    }
                }
            }
        }
    }

    private String calcolaRandom() throws Exception {
        Random r = new Random();
        String id = System.currentTimeMillis() + "" + r.nextLong();
        return id;
    }
}
