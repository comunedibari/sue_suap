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
package it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.dao;

import it.gruppoinit.commons.DBCPManager;
import it.gruppoinit.commons.Utilities;
import it.people.core.Logger;
import it.people.core.ServiceProfileStore;
import it.people.core.ServiceProfileStoreManager;
import it.people.core.persistence.exception.peopleException;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.BaseBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.ComuneBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.EventiVitaBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.HrefCampiBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.Input;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.InterventoBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.NormativaBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.OperazioneBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.Output;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.ParametriPUBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.ProcedimentoBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.ProcedimentoUnicoException;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.ServiziBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.SezioneCompilabileBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.SportelloBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility.Costant;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility.ManagerAllegati;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility.XPathReader;
import it.people.process.AbstractPplProcess;
import it.people.sirac.serviceprofile.xml.PeopleServiceProfile;
import it.people.sirac.serviceprofile.xml.PeopleServiceProfileDocument;
import it.people.sirac.serviceprofile.xml.Service;
import it.people.wrappers.IRequestWrapper;

import java.beans.IntrospectionException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.DataFormatException;
 
import org.apache.commons.betwixt.io.BeanReader;
import org.apache.commons.betwixt.io.BeanWriter;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xmlbeans.XmlException;
import org.python.core.PyException;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;
import org.xml.sax.SAXException;

public class BookmarkDAO {
	
		
		private Log log = LogFactory.getLog(this.getClass());
		private String language = "";
		protected DBCPManager db;
		private boolean isMySqlDb = true;
		
	    public BookmarkDAO(DBCPManager dbm, String nazionalita) {
	        this.db = dbm;
	        String dbType = this.db.getDATABASE();
	        
	        //se l'url del data source contiene mysql attiva il flag per questo database
	        if(dbType.toLowerCase().indexOf("mysql") != -1){
	            isMySqlDb = true;
	        //negli altri casi (Oracle al momento)     
	        }else {
	            isMySqlDb = false;
	        }
	        this.language = nazionalita;
	    }
	
    /**
     * Restituisce una lista di eventi della vita creati per il servizio
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    public List getEventiVita() throws SQLException {
        ResultSet rs = null;
        Connection conn = null;
        ArrayList eventiVita = new ArrayList();

        try {
            conn = db.open();

            String sql = "select * from eventi_vita where cod_lang = ? order by des_eve_vita";

            PreparedStatement ps = db.getPreparedStmt(conn, sql);
            ps.setString(1, language);

            if (log.isDebugEnabled()) {
            	log.debug("EventiVita query:" + sql);
            }
            log.info("getEventiVita: " + ps);
            rs = db.select(conn, ps, sql);
 
            while (rs.next()) {
                EventiVitaBean eventiVitaBean = new EventiVitaBean();
                eventiVitaBean.setCodiceEventoVita(rs.getInt("cod_eve_vita"));
                eventiVitaBean.setDescrizione(rs.getString("des_eve_vita"));
                eventiVita.add(eventiVitaBean);
            }
            if (eventiVita.size() == 0) {
                EventiVitaBean eventiVitaBean = new EventiVitaBean();
                eventiVitaBean.setDescrizione("Nessun evento della vita creato");
                eventiVita.add(eventiVitaBean);
            }
            return eventiVita;
        } catch (SQLException e) {
            log.error(e);
            throw e;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                conn.close();
            } catch (Exception e) {
            }
        }
    }

    /**
     * Restituisce una lista di servizi (bookmark) creati per eventoVita
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    public List getServiziPerEventiVita(int eventoVita, String codComune, String serviceName) throws SQLException {
        ResultSet rs = null;
        Connection conn = null;
        ArrayList servizi = new ArrayList();

        try {
            conn = db.open();

            String sql = "select servizi_testi.cod_servizio,servizi_testi.nome_servizio,servizi_testi.des_servizio,servizi.cod_com,servizi.cod_eve_vita,servizi.configuration from servizi inner join servizi_testi on servizi_testi.cod_servizio = servizi.cod_servizio and ";
            sql += "servizi_testi.cod_com = servizi.cod_com and servizi_testi.cod_eve_vita = servizi.cod_eve_vita ";
            sql += "and servizi_testi.cod_lang = ? ";
            sql += "where servizi.cod_eve_vita = ? and servizi.cod_com = ? order by nome_servizio";

            PreparedStatement ps = db.getPreparedStmt(conn, sql);
            ps.setString(1, language);
            ps.setInt(2, eventoVita);
            ps.setString(3, codComune);

            log.info("getServiziPerEventiVita:" + ps);
            rs = db.select(conn, ps, sql);

            while (rs.next()) {
                ServiziBean serviziBean = new ServiziBean();
                serviziBean.setCodiceServizio(rs.getInt("cod_servizio"));
                serviziBean.setNome(rs.getString("nome_servizio"));
                serviziBean.setDescrizione(rs.getString("des_servizio"));
                serviziBean.setCodiceComune(rs.getString("cod_com"));
                serviziBean.setCodiceEventoVita(rs.getString("cod_eve_vita"));
                //serviziBean.setBookmarkPointer(rs.getString("bookmark_pointer"));
                String configuration = rs.getString("configuration");
                String configurationEncod = encodingXMLConfiguration(configuration);
                serviziBean.setCheckAltriParametri(configurationEncod);
                
                try {
                    PeopleServiceProfile serviceProfile = caricaServiceProfile(serviceName, ""
                                                                                            + serviziBean.getCodiceServizio());
                    if (serviceProfile != null) {
                        Service service = serviceProfile.getService();
                        boolean intermediari = service.getAuthorizationProfile().getAccessoIntermediari().getEnabled();
                        boolean utenteReg = service.getAuthorizationProfile().getAccessoUtentePeopleRegistrato().getEnabled();
                        boolean autenticazioneForte = service.getSecurityProfile().getStrongAuthentication();
                        boolean autenticazioneDebole = service.getSecurityProfile().getWeakAuthentication();
                        serviziBean.setCheckStatus(autenticazioneForte + "|"
                                                   + autenticazioneDebole + "|"
                                                   + intermediari + "|"
                                                   + utenteReg);
                    } else {
                        serviziBean.setCheckStatus("false|true|false|true");
                    }
                } catch (peopleException e) {
                    log.debug("PeopleServiceProfile: errore nel reperimento dello stato dei check");
                } catch (XmlException e) {
                    log.debug("PeopleServiceProfile: errore nel reperimento dello stato dei check");
                }
                // Non implementato
                serviziBean.setMaxCodServizi(0);
                servizi.add(serviziBean);
            }
            if (servizi.size() == 0) {
                ServiziBean servizioBean = new ServiziBean();
                servizioBean.setDescrizione("Nessun servizio associato");
                servizi.add(servizioBean);
            }
            return servizi;
        } catch (SQLException e) {
            log.error(e);
            throw e;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                conn.close();
            } catch (Exception e) {
            }
        }
    }

    /**
     * Effettua l'insert di un nuovo servizio (bookmark)
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    public int[] insertServizio(IRequestWrapper request,String codComune, int codEventoVita, String bookmarkPointer, String nomeServizio, String descrizioneServizio, String[] listaCodComuni) throws SQLException {
        ResultSet rs = null;
        Connection conn = null;
        if (codEventoVita == 0)
            return new int[0];
        if (listaCodComuni==null || listaCodComuni.length==0){
        	listaCodComuni = new String[1];
        	listaCodComuni[0]=codComune;
        } else {
            int size = listaCodComuni.length + 1;
            String[] temp = new String[size];
            try {
                for (int i = 0; i < listaCodComuni.length; i++) {
                    temp[i] = listaCodComuni[i];
                }
                temp[size - 1] = codComune;
                listaCodComuni = temp;
            } catch (Exception e) {
            	log.error(e);
            }
        }
        int[] codServizio = new int[listaCodComuni.length];
        try {
            conn = db.open();
            Iterator itt = request.getParameterMap().keySet().iterator();
            for (Iterator iterator = itt; iterator.hasNext();) {
				String key = (String) iterator.next();
				String t = (String) request.getParameter(key);
				if (log.isDebugEnabled()) {
					log.debug("KEY = "+key+" --> VALUE ="+t);
				}
			}
            String sss = (String)request.getAttribute("AP");
            
            
            String tipologiaServizio = request.getParameter(Costant.BookmarkType);
            String tipologiaFirma = "";
            String tipologiaPagamento = "";
            String modalitaPagamento = "";
            String modalitaPagamentoOpzionale = "";
            if (tipologiaServizio.equalsIgnoreCase(String.valueOf(Costant.bookmarkTypeCortesiaCod)) || tipologiaServizio.equalsIgnoreCase(String.valueOf(Costant.bookmarkTypeLivello2Cod)) ){
            	tipologiaFirma = String.valueOf(Costant.senzaFirmaCod);
            	tipologiaPagamento = String.valueOf(Costant.disabilitaPagamentoCod);
            	modalitaPagamento = String.valueOf(Costant.modalitPagamentoSoloOnlineCod);
            	modalitaPagamentoOpzionale = String.valueOf(Costant.modalitPagamentoSoloOnlineCod);
            } else {
            	tipologiaFirma = request.getParameter(Costant.FirmaPraticaType);
            	tipologiaPagamento = request.getParameter(Costant.PagamentiType);
            	modalitaPagamento = String.valueOf(Costant.modalitPagamentoSoloOnlineCod);
            	modalitaPagamentoOpzionale = String.valueOf(Costant.modalitPagamentoSoloOnlineCod);
            }

            
            String conInvioString = request.getParameter(Costant.ConInvioType);
            modalitaPagamento = request.getParameter(Costant.modalitPagamentoType);
            modalitaPagamentoOpzionale = request.getParameter(Costant.modalitPagamentoOpzionaleType);
            
            String xmlParametri = buildXMLConfiguration(tipologiaServizio,tipologiaFirma,tipologiaPagamento,modalitaPagamento,modalitaPagamentoOpzionale,conInvioString);
            for (int i = 0; i < listaCodComuni.length; i++) {
				
			
	            // Faccio la insert nella tabella servizi
	            String sql = "insert into servizi (cod_servizio, cod_com, cod_eve_vita, bookmark_pointer,configuration) ";
	            sql += "values (?, ?, ?, ?, ?) ";
	
	            PreparedStatement ps = db.getPreparedStmt(conn, sql);
	            codServizio[i] = getMaxCodServizio() + 1;
	            ps.setInt(1, codServizio[i]);
	            ps.setString(2, listaCodComuni[i]);
	            ps.setInt(3, codEventoVita);
	            if (listaCodComuni[i].equalsIgnoreCase(codComune)){
	            	ps.setString(4, bookmarkPointer);
	            } else {
	            	ComuneBean comune = new ComuneBean();
	                String sql2 = "SELECT * FROM enti_comuni  ";
	                sql2 += " INNER JOIN classi_enti ON enti_comuni.cod_classe_ente=classi_enti.cod_classe_ente ";
	                sql2 += " INNER JOIN comuni_aggregazione  ON comuni_aggregazione.cod_ente = enti_comuni.cod_ente ";
	                sql2 += " INNER JOIN enti_comuni_testi ON enti_comuni.cod_ente = enti_comuni_testi.cod_ente ";
	                sql2 += " WHERE classi_enti.flg_com='S' AND enti_comuni_testi.cod_lang='"+language+"' AND enti_comuni.cod_ente='"+listaCodComuni[i]+"' ORDER BY des_ente ";
	            	PreparedStatement ps2 = db.getPreparedStmt(conn, sql2);
	            	log.info("insertServizio 2: " + ps2);
	            	ResultSet rs2 = db.select(ps2);
	            	if (rs2.next()){
	            		comune.setAoo(rs2.getString("aoo"));
	            		comune.setCap(rs2.getString("cap"));
	            		comune.setCcb(rs2.getString("ccb"));
	            		comune.setCcp(rs2.getString("ccp"));
	            		comune.setCitta(rs2.getString("citta"));
	            		comune.setCodBf(rs2.getString("cod_bf"));
	            		comune.setCodClasseEnte(rs2.getString("cod_classe_ente"));
	            		comune.setCodEnte(rs2.getString("cod_ente"));
	            		comune.setCodIstat(rs2.getString("cod_istat"));
	            		comune.setCodice(null);
	            		comune.setDescrizione(rs2.getString("des_ente"));
	            		comune.setEmail(rs2.getString("email"));
	            		comune.setFax(rs2.getString("fax"));
	            		comune.setNumero(null);
	            		comune.setPrg_href(rs2.getString("prg_href"));
	            		comune.setProvincia(rs2.getString("prov"));
	            		comune.setStato(null);
	            		comune.setTelefono(rs2.getString("tel"));
	            		comune.setTipAggregazione(rs2.getString("tip_aggregazione"));
	            		comune.setTp_prg(rs2.getString("tp_prg"));
	            		comune.setVia(rs2.getString("indirizzo"));
	            	}
	            	ps2.close();
	            	rs2.close();
	            	String bookmark = rigeneraXML(bookmarkPointer,comune);
	            	ps.setString(4, bookmark);
	            }
	            ps.setString(5, xmlParametri);
	            log.info("insertServizio 2: " + ps);
	            
				if (log.isDebugEnabled()) {
					log.debug("Insert servizi query:" + sql);
				}
	            db.insert(conn, ps, sql);
	
	            // Faccio la insert nella tabella servizi_testi
	            sql = "insert into servizi_testi (cod_servizio, cod_com, cod_eve_vita, cod_lang, nome_servizio, des_servizio) ";
	            sql += "values (?, ?, ?, ?, ?, ?) ";
	
	            ps = db.getPreparedStmt(conn, sql);
	            ps.setInt(1, codServizio[i]);
	            ps.setString(2, listaCodComuni[i]);
	            ps.setInt(3, codEventoVita);
	            ps.setString(4, language);
	            ps.setString(5, nomeServizio);
	            ps.setString(6, Utilities.NVL(descrizioneServizio,""));
	            log.info("insertServizio 3: " + ps);
	            log.debug("Insert servizi_testi query:" + sql);
	            db.insert(conn, ps, sql);
            }
            return codServizio;
        } catch (SQLException e) {
            log.error(e);
            return new int[0];
        } finally {
            try {
                db.close(conn);
                rs.close();
            } catch (Exception e) {
            }
            try {
                conn.close();
            } catch (Exception e) {
            }
        }
    }



	private String rigeneraXML(String bookmarkPointer, ComuneBean comune) {
		String xmlComune = comune.toXML();
		String[] head = bookmarkPointer.split("<comuneSelezionato>");
		String[] tail = head[1].split("</comuneSelezionato>");
		String ret = head[0]+xmlComune+tail[1];
		return ret;
	}

	public static String buildXMLConfiguration(String tipologiaServizio,String tipologiaFirma, String tipologiaPagamento,String modalitaPagamento,String modalitaPagamentoOpzionale,String conInvioString) {
		String xml ="";
		xml +="<CONFIGURAZIONE>\n";
		xml +="  <TYPE_OPZ>"+Costant.bookmarkTypeCompleteLabel+"|"+Costant.bookmarkTypeCortesiaLabel+"|"+Costant.bookmarkTypeLivello2Label+"</TYPE_OPZ>\n";
		if (Integer.parseInt(tipologiaServizio)==Costant.bookmarkTypeCompleteCod){
			xml +="  <TYPE>"+Costant.bookmarkTypeCompleteLabel+"</TYPE>\n";
		} else if (Integer.parseInt(tipologiaServizio)==Costant.bookmarkTypeCortesiaCod){
			xml +="  <TYPE>"+Costant.bookmarkTypeCortesiaLabel+"</TYPE>\n";
		} else {
			xml +="  <TYPE>"+Costant.bookmarkTypeLivello2Label+"</TYPE>\n";
		}
		
		// <CON_INVIO_OPZ>TRUE|FALSE</CON_INVIO_OPZ>
		xml +="  <CON_INVIO_OPZ>"+Costant.conInvioLabel+"|"+Costant.senzaInvioLabel+"</CON_INVIO_OPZ>\n";
		if (conInvioString == null || conInvioString.equalsIgnoreCase("")){
			conInvioString = new String(String.valueOf(Costant.conInvioCod));
		}
		if (Integer.parseInt(conInvioString)==Costant.conInvioCod){
			xml +="  <CON_INVIO>"+Costant.conInvioLabel+"</CON_INVIO>\n";
		} else {
			xml +="  <CON_INVIO>"+Costant.senzaInvioLabel+"</CON_INVIO>\n";
		}
		
		if (Integer.parseInt(tipologiaServizio)==Costant.bookmarkTypeLivello2Cod || Integer.parseInt(tipologiaServizio)==Costant.bookmarkTypeCortesiaCod){
			tipologiaFirma = String.valueOf(Costant.senzaFirmaCod);
			tipologiaPagamento = String.valueOf(Costant.disabilitaPagamentoCod);
		}
		
		xml +="  <FIRMADIGITALE_OPZ>"+Costant.conFirmaLabel+"|"+Costant.senzaFirmaLabel+"</FIRMADIGITALE_OPZ>\n";
		if (Integer.parseInt(tipologiaFirma)==Costant.conFirmaCod){
			xml +="  <FIRMADIGITALE>"+Costant.conFirmaLabel+"</FIRMADIGITALE>\n";
		} else {
			xml +="  <FIRMADIGITALE>"+Costant.senzaFirmaLabel+"</FIRMADIGITALE>\n";
		}
		
		xml +="  <PAGAMENTO_OPZ>"+Costant.disabilitaPagamentoLabel+"|"+Costant.forzaPagamentoLabel+"|"+Costant.pagamentoOpzionaleLabel+"</PAGAMENTO_OPZ>\n";
		if (Integer.parseInt(tipologiaPagamento)==Costant.disabilitaPagamentoCod){
			xml +="  <PAGAMENTO>"+Costant.disabilitaPagamentoLabel+"</PAGAMENTO>\n";
		} else if (Integer.parseInt(tipologiaPagamento)==Costant.forzaPagamentoCod){
			xml +="  <PAGAMENTO>"+Costant.forzaPagamentoLabel+"</PAGAMENTO>\n";
		} else {
			xml +="  <PAGAMENTO>"+Costant.pagamentoOpzionaleLabel+"</PAGAMENTO>\n";
		}

		if (modalitaPagamento != null) {
			if (Integer.parseInt(modalitaPagamento)==Costant.modalitPagamentoSoloOnlineCod){
				xml +="  <MODALITA_PAGAMENTO>"+Costant.modalitaPagamentoSoloOnlineLabel+"</MODALITA_PAGAMENTO>\n";
			} else if (Integer.parseInt(modalitaPagamento)==Costant.modalitaPagamentoOneOfflineCod){
				xml +="  <MODALITA_PAGAMENTO>"+Costant.modalitaPagamentoOneOfflineLabel+"</MODALITA_PAGAMENTO>\n";
			} else {
				xml +="  <MODALITA_PAGAMENTO>"+Costant.modalitaPagamentoSoloOnlineLabel+"</MODALITA_PAGAMENTO>\n";
			}
		}

		if (modalitaPagamentoOpzionale != null) {
			if (Integer.parseInt(modalitaPagamentoOpzionale)==Costant.modalitPagamentoSoloOnlineCod){
				xml +="  <MODALITA_PAGAMENTO_OPZIONALE>"+Costant.modalitaPagamentoSoloOnlineLabel+"</MODALITA_PAGAMENTO_OPZIONALE>\n";
			} else if (Integer.parseInt(modalitaPagamentoOpzionale)==Costant.modalitaPagamentoOneOfflineCod){
				xml +="  <MODALITA_PAGAMENTO_OPZIONALE>"+Costant.modalitaPagamentoOneOfflineLabel+"</MODALITA_PAGAMENTO_OPZIONALE>\n";
			} else {
				xml +="  <MODALITA_PAGAMENTO_OPZIONALE>"+Costant.modalitaPagamentoSoloOnlineLabel+"</MODALITA_PAGAMENTO_OPZIONALE>\n";
			}
		}
		
		xml +="</CONFIGURAZIONE>";
		return xml;
	}

	
	
	
	public static String encodingXMLConfiguration(String xmlConfiguration){
		XPathReader xpr = new XPathReader(xmlConfiguration);
		String tipoBookmark = Utilities.NVL(xpr.readElementString("/CONFIGURAZIONE/TYPE"),"");
		String tipoFirma = Utilities.NVL(xpr.readElementString("/CONFIGURAZIONE/FIRMADIGITALE"),"");
		String tipoPagamenti = Utilities.NVL(xpr.readElementString("/CONFIGURAZIONE/PAGAMENTO"),"");
		String modalitaPagamenti = Utilities.NVL(xpr.readElementString("/CONFIGURAZIONE/MODALITA_PAGAMENTO"),"");
		String conInvioString = Utilities.NVL(xpr.readElementString("/CONFIGURAZIONE/CON_INVIO"),"");
		
		boolean conInvio = Utilities.checked(conInvioString.equalsIgnoreCase("")?"true":conInvioString);
		
		return tipoBookmark+"|"+(conInvio?"CONINVIO":"SENZAINVIO")+"|"+tipoFirma+"|"+tipoPagamenti+"|"+modalitaPagamenti;
	}
	
	
	/**
     * Effettua l'eliminazione di un servizio (bookmark)
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    public boolean deleteServizio(int codServizio, String codComune, int codEventoVita) throws SQLException {
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = db.open();

            // Faccio la delete nella tabella servizi
            String sql = "delete from servizi where cod_servizio = ? and cod_com = ? and cod_eve_vita = ? ";

            PreparedStatement ps = db.getPreparedStmt(conn, sql);
            ps.setInt(1, codServizio);
            ps.setString(2, codComune);
            ps.setInt(3, codEventoVita);

            log.debug("Delete servizi query:" + sql);
            db.delete(conn, ps, sql);

            // Faccio la delete nella tabella servizi_testi
            sql = "delete from servizi_testi where cod_servizio = ? and cod_com = ? and cod_eve_vita = ? ";
//            log.info
            ps = db.getPreparedStmt(conn, sql);
            ps.setInt(1, codServizio);
            ps.setString(2, codComune);
            ps.setInt(3, codEventoVita);

            log.debug("Delete servizi_testi query:" + sql);
            db.delete(conn, ps, sql);
            
            // cancellazione info tabella validità bookmark
            sql = "delete from servizi_validita where cod_servizio = ?";
            ps = db.getPreparedStmt(conn, sql);
            ps.setInt(1, codServizio);
            log.debug("Delete servizi_testi query:" + sql);
            db.delete(conn, ps, sql);
            
         // cancellazione info accesslist bookmark
            sql = "delete from servizi_accesslist where cod_servizio = ?";
            ps = db.getPreparedStmt(conn, sql);
            ps.setInt(1, codServizio);
            log.debug("Delete servizi_testi query:" + sql);
            db.delete(conn, ps, sql);
            
            //db.commit(conn);

            return true;
        } catch (SQLException e) {
            log.error(e);
            return false;
        } finally {
            try {
                db.close(conn);
                rs.close();
            } catch (Exception e) {
            }
            try {
                conn.close();
            } catch (Exception e) {
            }
        }
    }

    /**
     * Effettua la modifica di un servizio (bookmark)
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     * @param updateSoloDescrizione TODO
     */
    public boolean updateServizio(IRequestWrapper request,int codServizio, String codComune, int codEventoVita, String bookmarkPointer, String descServizio, String nomeServizio, boolean updateSoloDescrizione) throws SQLException {
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = db.open();
            PreparedStatement ps = null;
            String sql = "";
            String tipologiaServizio = request.getParameter(Costant.BookmarkType);
            String tipologiaFirma = request.getParameter(Costant.FirmaPraticaType);
            String tipologiaPagamento = request.getParameter(Costant.PagamentiType);
            String modalitaPagamento = request.getParameter(Costant.modalitPagamentoType);
            String modalitaPagamentoOpzionale = request.getParameter(Costant.modalitPagamentoOpzionaleType);
            String conInvioString = request.getParameter(Costant.ConInvioType);
            // Faccio la insert nella tabella servizi
            if(!updateSoloDescrizione){
            	String xmlParametri = buildXMLConfiguration(tipologiaServizio,tipologiaFirma,tipologiaPagamento,modalitaPagamento,modalitaPagamentoOpzionale,conInvioString);
                sql = "update servizi set bookmark_pointer = ? , configuration = ? ";
                sql += "where cod_servizio = ? and cod_com = ? and cod_eve_vita = ? ";
    
                ps = db.getPreparedStmt(conn, sql);
                ps.setString(1, bookmarkPointer);
                ps.setString(2, xmlParametri);
                ps.setInt(3, codServizio);
                ps.setString(4, codComune);
                ps.setInt(5, codEventoVita);
    
                log.debug("Update servizi query:" + sql);
                db.update(conn, ps, sql);
            }
                
            sql = "update servizi_testi set des_servizio = ?, nome_servizio = ? ";
            sql += "where cod_servizio = ? and cod_com = ? and cod_eve_vita = ? and cod_lang = ? ";

            ps = db.getPreparedStmt(conn, sql);
            ps.setString(1, Utilities.NVL(descServizio,""));
            ps.setString(2, nomeServizio);
            ps.setInt(3, codServizio);
            ps.setString(4, codComune);
            ps.setInt(5, codEventoVita);
            ps.setString(6, language);

            log.debug("Update servizi_testi query:" + sql);
            db.update(conn, ps, sql);

            return true;
        } catch (SQLException e) {
            log.error(e);
            return false;
        } finally {
            try {
                db.close(conn);
                rs.close();
            } catch (Exception e) {
            }
            try {
                conn.close();
            } catch (Exception e) {
            }
        }
    }

    /**
     * Restituisce l'id max dei servizi (bookmark) creati fino a quel momento
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    public int getMaxCodServizio() throws SQLException {
        ResultSet rs = null;
        Connection conn = null;
        int retVal = 0;

        try {
            conn = db.open();

            String sql = "select max(cod_servizio) as maxCod from servizi ";

            PreparedStatement ps = db.getPreparedStmt(conn, sql);

            log.debug("MaxServizi query:" + sql);
            rs = db.select(conn, ps, sql);

            if (rs.next()) {
                retVal = rs.getInt("maxCod");
            }
            return retVal;
        } catch (SQLException e) {
            log.error(e);
            throw e;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                conn.close();
            } catch (Exception e) {
            }
        }
    }
    
    /**
     * Restituisce l'id max degli eventi della vita creati fino a quel momento
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    public int getMaxCodEventoVita() throws SQLException {
        ResultSet rs = null;
        Connection conn = null;
        int retVal = 0;

        try {
            conn = db.open();

            String sql = "select max(cod_eve_vita) as maxCod from eventi_vita ";

            PreparedStatement ps = db.getPreparedStmt(conn, sql);

            log.debug("MaxEventoVita query:" + sql);
            rs = db.select(conn, ps, sql);

            if (rs.next()) {
                retVal = rs.getInt("maxCod");
            }
            return retVal;
        } catch (SQLException e) {
            log.error(e);
            throw e;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                conn.close();
            } catch (Exception e) {
            }
        }
    }

    /**
     * Effettua l'inserimento di un nuovo evento della vita
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    public boolean insertEventoVita(String desEventoVita) throws SQLException {
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = db.open();

            // Faccio la insert nella tabella servizi
            String sql = "insert into eventi_vita (cod_eve_vita, cod_lang, des_eve_vita) ";
            sql += "values (?, ?, ?) ";

            PreparedStatement ps = db.getPreparedStmt(conn, sql);
            int codEveVita = getMaxCodEventoVita() + 1;
            ps.setInt(1, codEveVita);
            ps.setString(2, language);
            ps.setString(3, desEventoVita);

            log.debug("Insert eventiVita query:" + sql);
            log.debug("des_evento_vita=" + desEventoVita);
            db.insert(conn, ps, sql);

            return true;
        } catch (SQLException e) {
            log.error(e);
            return false;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                conn.close();
            } catch (Exception e) {
            }
        }
    }

    /**
     * Effettua l'eliminazione di un evento della vita
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    public boolean deleteEventoVita(int codEventoVita) throws SQLException {
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = db.open();

            // Faccio la insert nella tabella servizi
            String sql = "delete from eventi_vita where cod_eve_vita = ? and cod_lang = ? ";

            PreparedStatement ps = db.getPreparedStmt(conn, sql);
            ps.setInt(1, codEventoVita);
            ps.setString(2, language);

            log.debug("deleteEventoVita query:" + sql);
            db.delete(conn, ps, sql);

            sql = "delete from servizi where cod_eve_vita = ? ";

            ps = db.getPreparedStmt(conn, sql);
            ps.setInt(1, codEventoVita);

            log.debug("deleteServizio di eventiVita query:" + sql);
            db.delete(conn, ps, sql);

            sql = "delete from servizi_testi where cod_eve_vita = ? ";

            ps = db.getPreparedStmt(conn, sql);
            ps.setInt(1, codEventoVita);

            log.debug("deleteServizioTesti di eventiVita query:" + sql);
            db.delete(conn, ps, sql);

            return true;
        } catch (SQLException e) {
            log.error(e);
            return false;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                conn.close();
            } catch (Exception e) {
            }
        }
    }

    /**
     * Restituisce l'xml rappresentativo di un servizio (bookmark)
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    /*    public String getBookmarkXML(String id) {
        ResultSet rs = null;
        Connection conn = null;
        String xml = null;

        try {
            conn = db.open();

            String sql = "select bookmark_pointer from servizi where cod_servizio=?";

            PreparedStatement ps = db.getPreparedStmt(conn, sql);
            ps.setInt(1, Integer.parseInt(id));

            log.debug("getBookmarkXML query:" + sql);
            rs = db.select(conn, ps, sql);

            if (rs.next()) {
                xml = rs.getString("bookmark_pointer");
            }
            return xml;

        } catch (SQLException e) {
            log.error(e);
            return null;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                conn.close();
            } catch (Exception e) {
            }
        }
    }*/
    
    /**
     * Restituisce l'xml rappresentativo di un servizio (bookmark)
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    public String getBookmarkXML(String id, String codEnte, String codEveVita) {
        ResultSet rs = null;
        Connection conn = null;
        String xml = null;

        try {
            conn = db.open();

            String sql = "select bookmark_pointer from servizi where cod_servizio=?";
            
            if (codEnte != null) {
            	sql += " and cod_com = ?";
                if (codEveVita != null) {
                	sql += " and cod_eve_vita = ?";
                }
            }

            PreparedStatement ps = db.getPreparedStmt(conn, sql);
            ps.setInt(1, Integer.parseInt(id));
            if (codEnte != null) {
            	ps.setString(2, codEnte);
                if (codEveVita != null) {
                	ps.setInt(3, Integer.parseInt(codEveVita));
                }
            }

            log.info("getBookmarkXML:" + ps);
            rs = db.select(conn, ps, sql);

            if (rs.next()) {
                xml = rs.getString("bookmark_pointer");
            }
            return xml;

        } catch (SQLException e) {
            log.error(e);
            return null;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                conn.close();
            } catch (Exception e) {
            }
        }
    }
    
    
    /**
     * Restituisce il PeopleServiceProfile di un servizio (bookmark)
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    public PeopleServiceProfile caricaServiceProfile(String processName, String bookmarkId) throws XmlException, peopleException {
        ServiceProfileStore serviceProfileStore = ServiceProfileStoreManager.getInstance().get(processName, bookmarkId);
        if (serviceProfileStore != null) {
            String xmlProfile = serviceProfileStore.getProfile();
            PeopleServiceProfileDocument serviceProfileDocument;
            serviceProfileDocument = PeopleServiceProfileDocument.Factory.parse(xmlProfile);
            return serviceProfileDocument.getPeopleServiceProfile();
        }
        return null;
    }

	public ArrayList getListaComuniCreazioneBookmark2(String codEnte) throws Exception{
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement ps=null;
        ArrayList listaCom = new ArrayList();
        try {
            conn = db.open();
            String sql = "SELECT cod_ente FROM enti_comuni "+
            		" WHERE cod_ente = ? "+
            		" UNION "+
            		" SELECT a.cod_ente FROM comuni_aggregazione a JOIN comuni_aggregazione b ON b.tip_aggregazione=a.tip_aggregazione  "+
            		" WHERE b.cod_ente=? "+
            		" AND a.cod_ente NOT IN "+
            		" (SELECT DISTINCT cod_com FROM interventi_comuni a JOIN comuni_aggregazione b ON a.cod_com=b.cod_ente JOIN comuni_aggregazione c ON b.tip_aggregazione=c.tip_aggregazione "+
            		" WHERE c.cod_ente=?) "+
            		" AND a.cod_ente NOT IN "+
            		" (SELECT DISTINCT cod_com FROM settori_attivita_comuni a JOIN comuni_aggregazione b ON a.cod_com=b.cod_ente JOIN comuni_aggregazione c ON b.tip_aggregazione=c.tip_aggregazione "+
            		" WHERE c.cod_ente=?) "+
            		" AND a.cod_ente NOT IN "+
            		" (SELECT DISTINCT cod_com FROM allegati_comuni a JOIN comuni_aggregazione b ON a.cod_com=b.cod_ente JOIN comuni_aggregazione c ON b.tip_aggregazione=c.tip_aggregazione "+
            		" WHERE c.cod_ente=?) "+
            		" AND a.cod_ente NOT IN "+
            		" (SELECT DISTINCT cod_com FROM oneri_comuni a JOIN comuni_aggregazione b ON a.cod_com=b.cod_ente JOIN comuni_aggregazione c ON b.tip_aggregazione=c.tip_aggregazione "+
            		" WHERE c.cod_ente=?)" ;

            ps = db.getPreparedStmt(conn, sql);
            ps.setString(1, codEnte);
            ps.setString(2, codEnte);
            ps.setString(3, codEnte);
            ps.setString(4, codEnte);
            ps.setString(5, codEnte);
            ps.setString(6, codEnte);
            log.info("getListaComuniCreazioneBookmark2:" + ps);
            rs = ps.executeQuery();
            while (rs.next()){
            	String codice_ente = rs.getString("cod_ente");
            	if (!codice_ente.equalsIgnoreCase(codEnte)) {
					BaseBean bb = new BaseBean();
					bb.setCodice(codice_ente);
					//bb.setDescrizione(rs.getString("des_ente"));
					listaCom.add(bb);
            	}
            }
            rs.close();
            ps.close();
            if (listaCom!=null && listaCom.size()>0){
            	String str = "select des_ente from enti_comuni_testi where cod_ente=? and cod_lang=?";
            	for (Iterator iterator = listaCom.iterator(); iterator.hasNext();) {
					BaseBean bb = (BaseBean) iterator.next();
					if (Utilities.isset(bb.getCodice())){
						ps = db.getPreparedStmt(conn, str);
						ps.setString(1, bb.getCodice());
						ps.setString(2, language);
						rs = ps.executeQuery();
						if (rs.next()){
							bb.setDescrizione(rs.getString("des_ente"));
						}
					}
				}
            }
        } catch (SQLException e) {
            log.error(e);
            return null;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                conn.close();
            } catch (Exception e) {
            }
        }
		return listaCom;
	}
	
	
	
	public ArrayList getListaComuniCreazioneBookmark1(String codiceAggregazione,String codiceSettore, String codEnte) throws Exception{
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement ps=null;
        ArrayList listaCom = new ArrayList();
        try {
            conn = db.open();
            String sql = "SELECT t2.cod_ente FROM "+
            	" (SELECT cod_ente,COUNT(*) conta "+
            	"  FROM ( "+
            	"        SELECT DISTINCT b.cod_ente, IF(c.flg IS NULL,'X',c.flg) flg " +
            	"        FROM condizioni_di_attivazione a "+
            	"        JOIN comuni_aggregazione b ON a.tip_aggregazione=b.tip_aggregazione "+
            	" 		 LEFT JOIN interventi_comuni c ON a.cod_int=c.cod_int AND b.cod_ente=c.cod_com "+
            	" 		 WHERE a.tip_aggregazione = ? AND a.cod_sett=? " +
            	"        ) a "+
            	" GROUP BY cod_ente "+
            	" HAVING conta=1) t1 "+
            	" JOIN "+ 
            	" ( "+
            	" SELECT cod_ente,COUNT(*) conta "+
            	" FROM ( "+
            	" SELECT DISTINCT b.cod_ente, IF(c.flg IS NULL,'X',c.flg) flg FROM condizioni_di_attivazione a "+
            	" JOIN comuni_aggregazione b "+
            	" ON a.tip_aggregazione=b.tip_aggregazione "+
            	" LEFT JOIN interventi_comuni c "+
            	" ON a.cod_int=c.cod_int "+
            	" AND b.cod_ente=c.cod_com "+
            	" WHERE a.tip_aggregazione = ? "+ 
            	" AND a.cod_sett=? ) a "+
            	" GROUP BY cod_ente "+
            	" HAVING conta=1) t2 "+
            	" ON t1.conta=t2.conta "+
            	" WHERE t1.cod_ente=? "+ 	
            	" UNION "+
            	" SELECT ? FROM DUAL";
            
            ps = db.getPreparedStmt(conn, sql);
            ps.setString(1, codiceAggregazione);
            ps.setString(2, codiceSettore);
            ps.setString(3, codiceAggregazione);
            ps.setString(4, codiceSettore);
            ps.setString(5, codEnte);
            ps.setString(6, codEnte);
            rs = ps.executeQuery();
            while (rs.next()){
            	String codice_ente = rs.getString("cod_ente");
            	if (!codice_ente.equalsIgnoreCase(codEnte)) {
					BaseBean bb = new BaseBean();
					bb.setCodice(codice_ente);
					listaCom.add(bb);
            	}
            }
            rs.close();
            ps.close();
            if (listaCom!=null && listaCom.size()>0){
            	String str = "select des_ente from enti_comuni_testi where cod_ente=? and cod_lang=?";
            	for (Iterator iterator = listaCom.iterator(); iterator.hasNext();) {
					BaseBean bb = (BaseBean) iterator.next();
					if (Utilities.isset(bb.getCodice())){
						ps = db.getPreparedStmt(conn, str);
						ps.setString(1, bb.getCodice());
						ps.setString(2, language);
						rs = ps.executeQuery();
						if (rs.next()){
							bb.setDescrizione(rs.getString("des_ente"));
						}
					}
				}
            }
        } catch (SQLException e) {
            log.error(e);
            return null;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                conn.close();
            } catch (Exception e) {
            }
        }
		return listaCom;
	}


	/**
	 * <p>Utilizzata dal <code>ProcessData</code> per aggiornare i dati dello sportello nel caso di utilizzo di un bookmark.
	 * @param sportelloDaXml
	 * @return
	 */
	public SportelloBean updateDatiSportello(SportelloBean sportelloDaXml, ProcessData dataForm) {
		
        ResultSet resultSet = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String datiSportelloQuery = "select * from sportelli where cod_sport = ?";
    	//Caricamento dati per segnatura cittadino
    	String datiSegnaturaQuery = "select name, value from sportelli_param_prot_pec where ref_sport = ?";
        SportelloBean result = sportelloDaXml;
        
        try {
        	
        	connection = db.open();
        	
        	preparedStatement = connection.prepareStatement(datiSportelloQuery);
        	preparedStatement.setString(1, sportelloDaXml.getCodiceSportello());
        	resultSet = preparedStatement.executeQuery();
        	if (resultSet.next()) {
        		ManagerAllegati managerAllegati = new ManagerAllegati();
        		result.setRup(resultSet.getString("nome_rup"));
        		result.setTelefono(resultSet.getString("tel"));
        		result.setFax(resultSet.getString("fax"));
        		result.setEmail(resultSet.getString("email"));
        		result.setIndirizzo(resultSet.getString("indirizzo"));
        		result.setCap(resultSet.getString("cap"));
        		result.setCitta(resultSet.getString("citta"));
        		result.setProvincia(resultSet.getString("prov"));
        		result.setPec(resultSet.getString("email_cert"));
        		result.setId_mail_server(resultSet.getString("id_mail_server"));
        		result.setId_protocollo(resultSet.getString("id_protocollo"));
        		result.setId_backoffice(resultSet.getString("id_backoffice"));
        		result.setTemplate_oggetto_ricevuta(resultSet.getString("template_oggetto_ricevuta"));
        		result.setTemplate_corpo_ricevuta(resultSet.getString("template_corpo_ricevuta"));
        		result.setTemplate_nome_file_zip(resultSet.getString("template_nome_file_zip"));
        		result.setSend_zip_file(resultSet.getString("send_zip_file"));
        		result.setSend_single_files(resultSet.getString("send_single_files"));
        		result.setSend_xml(resultSet.getString("send_xml"));
        		result.setSend_signature(resultSet.getString("send_signature"));
        		result.setSend_ricevuta_dopo_protocollazione(resultSet.getString("send_ricevuta_dopo_protocollazione"));
        		result.setSend_ricevuta_dopo_invio_bo(resultSet.getString("send_ricevuta_dopo_invio_bo"));
        		result.setTemplate_oggetto_mail_suap(resultSet.getString("template_oggetto_mail_suap"));
        		result.setAe_codice_utente(resultSet.getString("ae_codice_utente"));
        		result.setAe_codice_ente(resultSet.getString("ae_codice_ente"));
        		result.setAe_tipo_ufficio(resultSet.getString("ae_tipo_ufficio"));
        		result.setAe_codice_ufficio(resultSet.getString("ae_codice_ufficio"));
        		result.setAe_tipologia_servizio(resultSet.getString("ae_tipologia_servizio"));
        		result.setAttachmentsUploadUM(resultSet.getString("allegati_dimensione_max_um"));
        		result.setShowPrintBlankTemplate(resultSet.getString("visualizza_stampa_modello_in_bianco").equalsIgnoreCase("s"));
        		result.setShowPDFVersion(resultSet.getString("visualizza_versione_pdf").equalsIgnoreCase("s"));
        		result.setOnLineSign(resultSet.getString("firma_on_line").equalsIgnoreCase("s"));
        		result.setOffLineSign(resultSet.getString("firma_off_line").equalsIgnoreCase("s"));        		
        		managerAllegati.setAttachmentsMaxTotalSize(dataForm, resultSet.getInt("allegati_dimensione_max"), resultSet.getString("allegati_dimensione_max_um"), 
                		(resultSet.getString("id_mail_server") != null && !resultSet.getString("id_mail_server").equalsIgnoreCase("")), 
                		(resultSet.getString("id_backoffice") != null && !resultSet.getString("id_backoffice").equalsIgnoreCase("")));

        		if (dataForm.getAttachmentsMaximunSize() == ProcessData.UNLIMITED_ATTACHMENTS_MAX_TOTAL_SIZE) {
        			result.setAttachmentsUploadMaximumSize("illimitata");
        		} else {
        			result.setAttachmentsUploadMaximumSize(managerAllegati.convertAttachmentsMaximumTotalSize(dataForm.getAttachmentsMaximunSize(), result.getAttachmentsUploadUM()) + " " + result.getAttachmentsUploadUM());
        		}
        		
        	}

        	preparedStatement = connection.prepareStatement(datiSegnaturaQuery);
        	preparedStatement.setString(1, sportelloDaXml.getCodiceSportello());
        	resultSet = preparedStatement.executeQuery();
        	while(resultSet.next()) {
        		result.addDatiSegnaturaCittadino(resultSet.getString(1), resultSet.getString(2));
        	}
        	
        } catch(SQLException e) {
        	
        } finally {
        	try {
        		if (resultSet != null) {
        			resultSet.close();
        		}
        		if (preparedStatement != null) {
        			preparedStatement.close();
        		}
        		if (connection != null) {
        			connection.close();
        		}
        	} catch(SQLException ignore) {
        		
        	}
        }
		
        return result;
        
	}
	
    public ArrayList getListaBookmarkUrl(String nodoPeople) throws Exception {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement2 = null;
        ResultSet resultSet2 = null;
        PreparedStatement preparedStatement3 = null;
        ResultSet resultSet3 = null;
        ArrayList list = new ArrayList();

        try {
            String sql = "SELECT distinct enti_comuni.cod_ente as codice_ente, enti_comuni_testi.des_ente as descrizione_ente"
                    + " FROM enti_comuni"
                    + " JOIN classi_enti ON enti_comuni.cod_classe_ente=classi_enti.cod_classe_ente "
                    + " JOIN comuni_aggregazione  ON comuni_aggregazione.cod_ente = enti_comuni.cod_ente "
                    + " JOIN enti_comuni_testi ON enti_comuni.cod_ente = enti_comuni_testi.cod_ente "
                    + " JOIN servizi ON servizi.cod_com = enti_comuni.cod_ente "
                    + " WHERE classi_enti.flg_com='S' AND enti_comuni.cod_ente=comuni_aggregazione.cod_ente "
                    + " AND enti_comuni_testi.cod_lang=? "
                    + " AND enti_comuni.cod_nodo = ? "
                    + " ORDER BY des_ente ";
            String sqlEventi = "SELECT DISTINCT D.des_eve_vita as descrizione ,A.cod_eve_vita as codice "
                    + "FROM servizi A "
                    + "JOIN eventi_vita D ON D.cod_eve_vita=A.cod_eve_vita AND D.cod_lang=? "
                    + "WHERE A.cod_com=? "
                    + "ORDER BY D.des_eve_vita";
            String sqlServizi = "select servizi_testi.cod_servizio,servizi_testi.nome_servizio,servizi_testi.des_servizio "
                    + "from servizi join servizi_testi "
                    + "on servizi_testi.cod_servizio = servizi.cod_servizio and servizi_testi.cod_com = servizi.cod_com and servizi_testi.cod_eve_vita = servizi.cod_eve_vita "
                    + "and servizi_testi.cod_lang = ? "
                    + "where servizi.cod_eve_vita = ? and servizi.cod_com = ? order by nome_servizio";
            connection = db.open();
            preparedStatement = db.getPreparedStmt(connection, sql);
            preparedStatement.setString(1, language);
            preparedStatement.setString(2, nodoPeople);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                HashMap comuni = new HashMap();
                comuni.put("codice", resultSet.getString("codice_ente"));
                comuni.put("descrizione", resultSet.getString("descrizione_ente"));
                preparedStatement2 = db.getPreparedStmt(connection, sqlEventi);
                preparedStatement2.setString(1, language);
                preparedStatement2.setString(2, resultSet.getString("codice_ente"));
                resultSet2 = preparedStatement2.executeQuery();
                ArrayList listEventi = new ArrayList();
                while (resultSet2.next()) {
                    HashMap evento = new HashMap();
                    evento.put("codice", String.valueOf(resultSet2.getInt("codice")));
                    evento.put("descrizione", resultSet2.getString("descrizione"));
                    listEventi.add(evento);
                    preparedStatement3 = db.getPreparedStmt(connection, sqlServizi);
                    preparedStatement3.setString(1, language);
                    preparedStatement3.setInt(2, resultSet2.getInt("codice"));
                    preparedStatement3.setString(3, resultSet.getString("codice_ente"));
                    resultSet3 = preparedStatement3.executeQuery();
                    ArrayList listServizi = new ArrayList();
                    while (resultSet3.next()) {
                        HashMap servizio = new HashMap();
                        servizio.put("codice",String.valueOf(resultSet3.getInt("cod_servizio")));
                        servizio.put("descrizione",resultSet3.getString("des_servizio"));
                        servizio.put("nome",resultSet3.getString("nome_servizio"));
                        listServizi.add(servizio);
                    }
                    evento.put("listaServizi",listServizi);
                    resultSet3.close();
                    preparedStatement3.close();

                }
                resultSet2.close();
                preparedStatement2.close();
                comuni.put("listaEventi", listEventi);
                list.add(comuni);
            }
            return list;
        } catch (Exception e) {
            log.error(e);
            throw e;
        } finally {
            try {
                if (resultSet3 != null) {
                    resultSet3.close();
                }
                if (preparedStatement3 != null) {
                    preparedStatement3.close();
                }
                if (resultSet2 != null) {
                    resultSet2.close();
                }
                if (preparedStatement2 != null) {
                    preparedStatement2.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ignore) {
            }
        }
    }
}
