/**
 * Copyright (c) 2011, Regione Emilia-Romagna, Italy
 * 
 * Licensed under the EUPL, Version 1.1 or - as soon they
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the
 * Licence.
 * 
 * For convenience a plain text copy of the English version
 * of the Licence can be found in the file LICENCE.txt in
 * the top-level directory of this software distribution.
 * 
 * You may obtain a copy of the Licence in any of 22 European
 * Languages at:
 * 
 * http://www.osor.eu/eupl
 * 
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
**/
package it.wego.dynamicodt.impl;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;

import it.wego.dynamicodt.DynamicOdtService;
import it.wego.dynamicodt.transformation.ReportGenerator;
import it.wego.dynamicodt.transformation.html.HTools;
import it.wego.dynamicodt.transformation.html.OdtTableGenerator;
import it.wego.dynamicodt.transformation.util.ConfigUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

@WebService(serviceName = "DynamicOdtService", portName = "DynamicOdtServiceSOAP11port_http", endpointInterface = "it.wego.dynamicodt.DynamicOdtService", targetNamespace = "http://webservice.backend.people.it/")
public class DynamicOdtServiceImpl implements DynamicOdtService {

    private static Log log = LogFactory.getLog(DynamicOdtServiceImpl.class);
    private ReportGenerator generator = new ReportGenerator();
    private DataSource dataSource;
    private Connection connection = null;
    private HashMap xmlParametersHashMap = null;
    public boolean azioneTemplateEnabled = false;

    private void isAzioneTemplateEnabled() {
        String azioneTemplate = (String) xmlParametersHashMap.get("azioneTemplate");
        azioneTemplateEnabled = !"".equals(azioneTemplate) && azioneTemplate != null;
    }

    public String process(String data) {

    	String xmlPeopleDatiEncoding = "ISO-8859-1";
        String returnXmlString = new String();
        try {

            log.info("Documenti Dinamici INVOCATI\n");
            log.debug("\n\n\n" + data + "\n\n\n");

            //DECODIFICA DATA E METTI IN XML
            //log.debug("Tentativo decodifica Base64 \"data\"...");
            //data = new String(Base64.decodeBase64(data.getBytes()));
            //log.debug("\n\n\n"+data+"\n\n\n");
            //log.debug("Decodifica \"data\": RIUSCITA\n");

            log.debug("Tentativo estrazione proprietà \"tag.people.root\",\"tag.people.dati\" e \"tag.people.out\"...");
            ConfigUtil cu = new ConfigUtil();
            String xmlPeopleRoot = cu.getInstance().getProperty("tag.people.root");
            String xmlPeopleDati = cu.getInstance().getProperty("tag.people.dati");
            String xmlPeopleOut = cu.getInstance().getProperty("tag.people.out");
            try {
            	String xmlPeopleDatiEncodingBuffer = cu.getInstance().getProperty("encoding");
                if (!StringUtils.isEmpty(xmlPeopleDatiEncoding)) {
                	xmlPeopleDatiEncoding = xmlPeopleDatiEncodingBuffer;
                }
            } catch(Exception ignore) {
            	
            }
            log.debug("Estrazione proprietà: RIUSCITA");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            log.debug("DocumentBuilderFactory -> OK");

            DocumentBuilder builder = factory.newDocumentBuilder();
            log.debug("DocumentBuilder -> OK");

            Document doc = builder.parse(new InputSource(new StringReader(data)));
            //XMLDATA = ESTRAI "/ROOT/XMLDATA/text()"
            log.debug("Tentativo estrazione /" + xmlPeopleRoot + "/" + xmlPeopleDati + "/text()");
            String xPath = "/" + xmlPeopleRoot + "/" + xmlPeopleDati + "/text()";
            String xmlData = XPathAPI.selectSingleNode(doc, xPath).getNodeValue();
            log.debug("Estrazione: RIUSCITA");
            
            //OUTPUTTYPE = ESTRAI "/ROOT/OUTPUTTYPE/text()"
            log.debug("Tentativo estrazione /" + xmlPeopleRoot + "/" + xmlPeopleOut + "/text()");
            xPath = "/" + xmlPeopleRoot + "/" + xmlPeopleOut + "/text()";
            String outputType = XPathAPI.selectSingleNode(doc, xPath).getNodeValue();
            log.debug("Estrazione: RIUSCITA");

            //attivita

//            System.out.println(xmlData);            

            //XMLDATA = DECODIFICA BASE64 XMLDATA
            log.debug("Tentativo decodifica Base64 \"" + xmlPeopleDati + "\"...");
//            xmlData = new String(Base64.decodeBase64(xmlData.getBytes()),"iso-8859-1");
            xmlData = new String(Base64.decodeBase64(xmlData.getBytes(xmlPeopleDatiEncoding)), xmlPeopleDatiEncoding);

//System.out.println(xmlData);            
            
            log.debug("Decodifica \"" + xmlPeopleDati + "\": RIUSCITA\n");
            // elimina lf
            xmlPeopleDati = xmlPeopleDati.replaceAll("\r\n", " ").replaceAll("\n", " ");
            xmlData = xmlData.replaceAll("\r\n", " ").replaceAll("\n", " ");

            xmlData = xmlData.replaceAll("&#147;", "\"");
            xmlPeopleDati = xmlData.replaceAll("&#147;", "\"");
            xmlData = xmlData.replaceAll("&#148;", "\"");
            xmlPeopleDati = xmlData.replaceAll("&#148;", "\"");

            xmlData = xmlData.replaceAll("&amp;#147;", "\"");
            xmlPeopleDati = xmlData.replaceAll("&amp;#147;", "\"");
            xmlData = xmlData.replaceAll("&amp;#148;", "\"");
            xmlPeopleDati = xmlData.replaceAll("&amp;#148;", "\"");
            //Azzera indice
            OdtTableGenerator.indice = 0;
            //CHIAMA generateDocument(XMLDATA, OUTPUTTYPE)
            log.debug("Tentativo di chiamata generateDocument");
            
//            xmlData = xmlData.replaceAll("à", "a'");
//            xmlData = xmlData.replaceAll("è", "e'");
//            xmlData = xmlData.replaceAll("é", "e'");
//            xmlData = xmlData.replaceAll("ì", "i'");
//            xmlData = xmlData.replaceAll("ò", "o'");
//            xmlData = xmlData.replaceAll("ù", "u'");

            byte[] dydotResponse = generateDocumento(xmlData.getBytes(), outputType.toLowerCase(), xmlPeopleDatiEncoding);
            log.debug("Chiamata riuscita");


            String dydotResponseString = new String(dydotResponse);

            if (outputType.toLowerCase().compareTo("html") == 0) {
                //int fineStartBody = dydotResponseString.indexOf("<BODY LANG=\"it-IT\" DIR=\"LTR\">")+30;
                //int inizioEndBody = dydotResponseString.indexOf("</BODY>");
                //dydotResponseString = dydotResponseString.substring(fineStartBody, inizioEndBody);

                StringBuffer sb = new StringBuffer(dydotResponseString);

                HTools.replaceAllWithJolly(sb, "<FORM[^\\<]*>", "");
                HTools.replaceAllWithJolly(sb, "</FORM>", "");
                HTools.replaceAllWithJolly(sb, "<form[^\\<]*>", "");
                HTools.replaceAllWithJolly(sb, "</form>", "");
                HTools.replaceAllWithJollyDotAllMultiline(sb, "(<DIV TYPE=FOOTER>)(.*?)(</DIV>)", "");

                dydotResponseString = sb.toString();

                dydotResponse = dydotResponseString.getBytes();

            }

            //CODIFICO BASE64 il risultato
            log.debug("Tentativo codifica Base64 RISULTATO...");
            dydotResponse = Base64.encodeBase64(dydotResponse);
            log.debug("Codifica: RIUSCITA\n");

            log.debug("Creazione xml di ritorno...");
            log.debug("DYNDOC-OK");
            //returnXmlString = "<Result><message>DYNDOC-OK</message><content>"+new String(dydotResponse)+"</content></Result>";
            returnXmlString = new String(dydotResponse);
            log.debug("Creazione: RIUSCITA\n");

        } catch (Exception e) {

            log.debug("Creazione xml di ritorno per errore...");
            log.error("Error", e);
            log.debug("<Result><message>DYNDOC-ERROR-404</message><content>" + new String(Base64.encodeBase64(e.getMessage().getBytes())) + "</content></Result>");
            //returnXmlString = "<Result><message>DYNDOC-ERROR-404</message><content>"+new String(Base64.encodeBase64(e.getMessage().getBytes()))+"</content></Result>";            
            returnXmlString = new String(Base64.encodeBase64(e.getMessage().getBytes()));
            log.debug("Creazione: RIUSCITA\n");

        }

        //log.debug("Tentativo codifica Base64 Xml Ritorno...");
        //byte[] returnXmlBytes = Base64.encodeBase64(returnXmlString.getBytes());
        //log.debug("Codifica: RIUSCITA\n");

        log.info("Documenti Dinamici Terminati\n");
        
        //return new String(returnXmlBytes);
        return returnXmlString;
    }

    private byte[] generateDocumento(byte[] xmlData, String outputType, String encoding) throws Exception {

        try {
            log.debug("Tentativo connessione DB...");
            initConnection();
            log.debug("Connessione DB: RIUSCITA\n");
            
            log.debug("Tentativo estrazione parametri da XML...");
            xmlParametersHashMap = getElementsFromXML(xmlData);
            log.debug("Estrazione parametri: RIUSCITA\n");

            log.debug("Tentativo ricerca Template...");
            byte[] templateOdt = null;

            templateOdt = getTemplateFromDB(xmlData);
            log.debug("Ricerca Template: RIUSCITA\n");

            log.debug("Tentativo ricarca immagini da DB...");
            HashMap base64Images = getImagerFromDB();
            log.debug("Ricerca Immagini: RIUSCITA\n");

            log.debug("Tentativo inserimento outputType in XML...");
            xmlData = insertOutputType(xmlData, outputType, encoding);
            log.debug("Inserimento inserimento outputType: RIUSCITO\n");

            log.debug("Tentativo inserimento immagini in XML...");
            xmlData = insertImageInXML(xmlData, base64Images, encoding);
            log.debug("Inserimento immagini: RIUSCITO\n");

            log.debug(new String(xmlData));

            log.debug("Tentativo chiusura connessione DB...");
            releaseConnection();
            log.debug("Chiusura connessione: RIUSCITA\n");

            log.debug("Tentativo decodifica Base64 Template...");
            templateOdt = Base64.decodeBase64(templateOdt);
            log.debug("Decodifica Template: RIUSCITA\n");


//	        log.info("-> Received dataXml [" + xmlData.length + "] template [" + templateOdt.length + "] type [" + outputType + "]");
//          log.debug("Received dataXml [" + xmlData + "] template [" + templateOdt + "] type [" + outputType + "]");

            byte[] response = null;
//	        try {

            response = generator.generateReport(templateOdt, xmlData, outputType, encoding);

            /*        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error generating report ", e);
            throw e;
            }*/
            return response;
        } catch (Exception e) {
            log.error("Error generating report ", e);
            throw e;
        } finally {
            releaseConnection();
        }

    }

    private byte[] insertOutputType(byte[] xml, String outputType, String encoding) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(new String(xml))));

        //Node root = doc.getLastChild();
        Node outputTypeNode = doc.createElement("outputtype");
        outputTypeNode.appendChild(doc.createTextNode(outputType.toLowerCase()));

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, encoding);
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new StringWriter());
        transformer.transform(source, result);

        return result.getWriter().toString().getBytes();
    }

    private byte[] insertImageInXML(byte[] xml, HashMap images, String encoding) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(new String(xml))));

        ConfigUtil cu = new ConfigUtil();
        String tagImages = cu.getInstance().getProperty("tag.images");

        Node root = doc.getLastChild();
        Node imagesNode = doc.createElement(tagImages);

        Iterator iterator = images.keySet().iterator();
        String tag = null;
        String base64 = null;
        log.debug("Numero Immagini da inserire: " + images.size());
        while (iterator.hasNext()) {
            tag = (String) iterator.next();
            base64 = (String) images.get(tag);
            log.debug("Inserito tag:'" + tag + "' con valore: '" + base64 + "'");
            Node loopNode = doc.createElement(tag);
            loopNode.appendChild(doc.createTextNode(base64));
            imagesNode.appendChild(loopNode);
        }

        root.appendChild(imagesNode);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, encoding);
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new StringWriter());
        transformer.transform(source, result);

        return result.getWriter().toString().getBytes();

    }

    private void initConnection() throws Exception {
        try {
            Context initContext = new InitialContext();
            Context envCtx = (Context) initContext.lookup("java:/comp/env");
            this.setDataSource((DataSource) envCtx.lookup("jdbc/puDS"));
            this.setConnection(this.getDataSource().getConnection());
        } catch (Exception e) {
            log.error("Error generating report ", e);
            throw new Exception("Errore nel collegamento al database");
        }
    }

    private void setConnection(Connection connection) {
    	this.connection = connection;
    }
    
    private Connection getConnection() throws SQLException {
    	return this.connection;
    }
    
    private void releaseObjects(PreparedStatement preparedStatement, ResultSet resultSet) {
    	try {
    		if (resultSet != null) {
    			resultSet.close();
    		}
    		if (preparedStatement != null) {
    			preparedStatement.close();
    		}
    	} catch(Exception ignore) {
    		
    	}
    }

    private void releaseObjects(PreparedStatement preparedStatement) {
    	this.releaseObjects(preparedStatement, null);
    }

    private void releaseObjects(ResultSet resultSet) {
    	this.releaseObjects(null, resultSet);
    }
    
    private void releaseConnection() {
    	
    	try {
    		if (this.getConnection() != null) {
    			this.getConnection().close();
    		}
    	} catch(Exception ignore) {
    		
    	}
    	
    }

    private HashMap getImagerFromDB() throws Exception {
        HashMap base64Images = new HashMap();
        String codCom = xmlParametersHashMap.get("codCom").toString();
        String codSport = xmlParametersHashMap.get("codSport").toString();

        boolean terminato = false;
        try {
            if (!codCom.equals("") && !codSport.equals("")) {
                log.debug("----Ricerca Immagini per CodiceSportello e CodiceComune----");
                base64Images = getImagesResultSetWhere("cod_sport = '" + codSport + "' and cod_com = '" + codCom + "'");
                if (base64Images.size() > 0) {
                    terminato = true;
                }
            }
            if (!terminato && !codSport.equals("")) {
                log.debug("----Ricerca Immagini per CodiceSportello e CodiceComune generico----");
                base64Images = getImagesResultSetWhere("cod_sport = '" + codSport + "' and cod_com = ''");
                if (base64Images.size() > 0) {
                    terminato = true;
                }
            }
            if (!terminato && !codCom.equals("")) {
                log.debug("----Ricerca Immagini per CodiceSportello generico e CodiceComune----");
                base64Images = getImagesResultSetWhere("cod_sport = '' and cod_com = '" + codCom + "'");
                if (base64Images.size() > 0) {
                    terminato = true;
                }
            }
            if (!terminato) {
                log.debug("----Ricerca Immagini per CodiceSportello generico e CodiceComune generico----");
                base64Images = getImagesResultSetWhere("cod_sport = '' and cod_com = ''");
                if (base64Images.size() > 0) {
                    terminato = true;
                }
            }
            if (!terminato) {
                log.debug("----Nessuna Immagine Trovata----");
            }
            return base64Images;
        } catch (Exception e) {
            log.error("Error generating report ", e);
            throw new Exception("Errore nell'estrarre le immagini dal database");
        }
    }

    private HashMap getImagesResultSetWhere(String where) throws SQLException {
        String query = "SELECT * FROM templates_immagini_immagini WHERE " + where;
        log.debug(query);
        
        HashMap base64Images = new HashMap();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
	        ps = this.getConnection().prepareStatement(query);
	        rs = ps.executeQuery();
	        while (rs.next()) {
	            base64Images.put(rs.getString("nome_immagine"), rs.getString("immagine"));
	        }
        } catch(SQLException e) {
        	throw e;
        } finally {
        	releaseObjects(ps, rs);
        }
        
        return base64Images;
    }

    private String getSpecificTemplateFromDB() {
        String codSport = xmlParametersHashMap.get("codSport").toString();
        String azioneTemplate = xmlParametersHashMap.get("azioneTemplate").toString();
        return getTemplateFromSportello(codSport, azioneTemplate);
    }

    private byte[] getTemplateFromDB(byte[] xmlData) throws Exception {
        String template = "";
        boolean terminato = false;
        try {
            log.debug("--- Inizio ricerca template ---");
            String idBookmark = xmlParametersHashMap.get("idBookmark").toString();
            String codiceProcedimento = xmlParametersHashMap.get("codiceProcedimento").toString();
            //String moduloBianco = xmlParametersHashMap.get("moduloBianco").toString();
            String codCom = xmlParametersHashMap.get("codCom").toString();
            String codSport = xmlParametersHashMap.get("codSport").toString();
            String azioneTemplate = xmlParametersHashMap.get("azioneTemplate").toString();
            String tipo = "";

            isAzioneTemplateEnabled();
            if (azioneTemplateEnabled) {
                log.debug("Utilizzo il template indicato nell'XML: " + azioneTemplate);
                template = getSpecificTemplateFromDB();
                terminato = true;
            } else {
                //Non ho settato un template di default
                log.debug("Ricerca standard del template");
            }

            if (!idBookmark.equals("") && terminato != true) {
                tipo = "<tipo><bookmark>" + idBookmark + "</bookmark><procedimento>%</procedimento></tipo>";
                log.debug("----Ricerca Template per idBookmark uguale a: " + idBookmark + "----");
                template = getTemplateFromIdBookmark(tipo);
                if (!template.equals("")) {
                    terminato = true;
                }
            }

            if (terminato != true && (!codiceProcedimento.equals(""))) {
                tipo = "<tipo><bookmark></bookmark><procedimento>" + codiceProcedimento + "</procedimento></tipo>";
                log.debug("----Ricerca Template per Codice Procedimento uguale a: " + codiceProcedimento + "----");
                template = getTemplateFromProcedimento(tipo, codCom, codSport);
                if (!template.equals("")) {
                    terminato = true;
                }
            }

            if (terminato != true) {
                tipo = "<tipo><bookmark></bookmark><procedimento></procedimento></tipo>";
                log.debug("----Ricerca Template Generico----");
                template = getTemplateFromProcedimento(tipo, codCom, codSport);
                if (!template.equals("")) {
                    terminato = true;
                    log.debug("Trovato Template generico");
                } else {
                    log.debug("Nessun Template generico trovato");
                }
            }

            log.debug("Template: " + template);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        if (!terminato) {
            throw new Exception("Non è stato trovato un template compatibile con i dati in ingresso.");
        }
        return template.getBytes();
    }

    private String getTemplateFromSportello(String sportello, String azioneTemplate) {
        String template = "";
        try {
            template = getResultFromSportello(sportello, azioneTemplate);
        } catch (SQLException ex) {
            log.error("Error retrieving template from db", ex);
        } catch (Exception ex) {
            log.error("Error retrieving template from db", ex);
        }
        return template;
    }

    private String getTemplateFromIdBookmark(String tipo) throws SQLException {
    	
        String template = "";
        ResultSet rs = null;
        
        try {
	        rs = getResultFromTipo(tipo);
	        while (rs.next()) {
	            template = rs.getString("template");
	            break;
	        }
	        if (!template.equals("")) {
	            log.debug("\tTemplate TROVATO");
	        } else {
	            log.debug("\tTemplate NON TROVATO");
	        }
        } catch(SQLException e) {
        	throw e;
        } finally {
        	releaseObjects(rs);
        }
        
        return template;
    }

    private String getTemplateFromProcedimento(String tipo, String codCom,
            String codSport) throws SQLException {
    	
        String soloSportelloTemplate = "";
        String noComuneNoSportelloTemplate = "";

        ResultSet rs = null;        
        
        try {
	        rs = getResultFromTipo(tipo);
	        while (rs.next()) {
	            if (rs.getString("cod_com").equals(codCom) && rs.getString("cod_sport").equals(codSport)) {
	                log.debug("\tTemplate per CodiceComune: " + codCom + " - CodiceSportello: " + codSport + " TROVATO");
	                return rs.getString("template");
	            }
	            if (rs.getString("cod_sport").equals(codSport)) {
	                soloSportelloTemplate = rs.getString("template");
	            }
	            if (rs.getString("cod_com").equals("") && rs.getString("cod_sport").equals("")) {
	                noComuneNoSportelloTemplate = rs.getString("template");
	            }
	        }
        } catch(SQLException e) {
        	throw e;
        } finally {
        	releaseObjects(rs);
        }
        
        if (!soloSportelloTemplate.equals("")) {
            log.debug("\tTemplate per CodiceSportello: " + codSport + " e comune generico TROVATO");
            return soloSportelloTemplate;
        } else {
            if (!noComuneNoSportelloTemplate.equals("")) {
                log.debug("\tTemplate per sportello e comune generici TROVATO");
            } else {
                log.debug("\tTemplate per sportello e comune generici NON TROVATO");
            }
            return noComuneNoSportelloTemplate;
        }
    }

    private String getResultFromSportello(String sportello, String azioneTemplate) throws SQLException {
    	
    	String result = null;
    	
        String query = "SELECT tvr.doc_blob as template FROM templates_vari_risorse tvr JOIN templates_vari tv "
                + " on tvr.nome_template = tv.nome_template "
                + " where tvr.cod_sport=? and tvr.nome_template = ?";
        log.debug(query);

        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
	        ps = this.getConnection().prepareStatement(query);
	        ps.setString(1, sportello);
	        ps.setString(2, azioneTemplate);
	        rs = ps.executeQuery();
	        if (rs.next()) {
	            result = rs.getString("template");
	        } else {
	            query = "SELECT tvr.doc_blob as template FROM templates_vari_risorse tvr JOIN templates_vari tv "
	                    + " on tvr.nome_template = tv.nome_template "
	                    + " where tvr.cod_sport='' and tvr.nome_template = ?";
	            ps = this.getConnection().prepareStatement(query);
	            ps.setString(1, azioneTemplate);
	            rs = ps.executeQuery();
	            if (rs.next()) {
	                result = rs.getString("template");
	            }
	        }
        } catch(Exception e) {
        	throw new SQLException();
        } finally {
        	releaseObjects(ps, rs);
        }
        
        return result;
        
    }

    private ResultSet getResultFromTipo(String tipo) throws SQLException {

    	ResultSet rs = null;
    	
        PreparedStatement ps = null;
        
        try {
	        String query = "SELECT * FROM templates WHERE tipo LIKE '" + tipo + "'";
	        log.debug(query);
	        ps = this.getConnection().prepareStatement(query);
	        rs = ps.executeQuery();
        } catch(Exception e) {
        	throw new SQLException();
        } finally {
        	// Do nothing, resultset object will be closed by the caller
        }
        
        return rs;
        
    }

    private HashMap getElementsFromXML(byte[] xmlData) throws Exception {
        String idBookmark = "";
        String codiceProcedimento = "";
        //String moduloBianco = "";
        String codCom = "";
        String codSport = "";
        String azioneTemplate = "";
        HashMap hm = new HashMap();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(new String(xmlData))));

            ConfigUtil cu = new ConfigUtil();
            String nomeRootXmlData = cu.getInstance().getProperty("nomeRootXml");

            String xPath = "/" + nomeRootXmlData + "/idBookmark/text()";
            log.debug("Tentativo Estrazione: " + xPath);
            Node node = XPathAPI.selectSingleNode(doc, xPath);
            idBookmark = (node != null) ? node.getNodeValue() : "";
            log.debug("ID BOOKMARK: " + idBookmark);

            xPath = "/" + nomeRootXmlData + "/procedimenti/procedimento[1]/codice/text()";
            log.debug("Tentativo Estrazione: " + xPath);
            codiceProcedimento = XPathAPI.selectSingleNode(doc, xPath).getNodeValue();
            log.debug("CODICE PROCEDIMENTO: " + codiceProcedimento);

            /*xPath = "/"+nomeRootXmlData+"/moduloBianco/text()";
            log.debug("Tentativo Estrazione: " + xPath);
            moduloBianco = XPathAPI.selectSingleNode(doc, xPath).getNodeValue();
            log.debug("MODULOBIANCO: " + moduloBianco);*/

            xPath = "/" + nomeRootXmlData + "/comune/codice/text()";
            log.debug("Tentativo Estrazione: " + xPath);
            codCom = XPathAPI.selectSingleNode(doc, xPath).getNodeValue();
            log.debug("CODICE COMUNE: " + codCom);

            xPath = "/" + nomeRootXmlData + "/sportello/codiceSportello/text()";
            log.debug("Tentativo Estrazione: " + xPath);
            codSport = XPathAPI.selectSingleNode(doc, xPath).getNodeValue();
            log.debug("CODICE SPORTELLO: " + codSport);

            xPath = "/" + nomeRootXmlData + "/azioneTemplate/text()";
            log.debug("Tentativo Estrazione: " + xPath);
            Node n = XPathAPI.selectSingleNode(doc, xPath);
            if (n != null) {
                azioneTemplate = XPathAPI.selectSingleNode(doc, xPath).getNodeValue();
            }
            log.debug("AZIONE TEMPLATE: " + azioneTemplate);

            hm.put("idBookmark", idBookmark);
            hm.put("codiceProcedimento", codiceProcedimento);
//			hm.put("moduloBianco", moduloBianco);
            hm.put("codCom", codCom);
            hm.put("codSport", codSport);
            hm.put("azioneTemplate", azioneTemplate);

            return hm;
        } catch (Exception e) {
            throw new Exception("Errore nell'estrazione dei valori dall'xml. Controllare che siano presenti tutti i parametri richiesti.");
        }
    }

	/**
	 * @return the dataSource
	 */
	private DataSource getDataSource() {
		return this.dataSource;
	}

	/**
	 * @param dataSource the dataSource to set
	 */
	private void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
    
    
}
