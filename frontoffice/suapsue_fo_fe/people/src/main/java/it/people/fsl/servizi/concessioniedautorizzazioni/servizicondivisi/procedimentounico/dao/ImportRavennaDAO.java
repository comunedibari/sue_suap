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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import it.gruppoinit.commons.DBCPManager;
import it.gruppoinit.commons.Utilities;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.BaseBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.BaseBeanRows;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility.ManagerImportRavenna;
import it.people.process.AbstractPplProcess;
import it.people.wrappers.IRequestWrapper;

public class ImportRavennaDAO extends ProcedimentoUnicoDAO{

	public ImportRavennaDAO(DBCPManager dbm, String nazionalita) {
		super(dbm, nazionalita);
	}

	public boolean saveNewBookmarkLibero(String newXML, String configuration,String codEnte,String codiceEventoVita, String descrizioneServizio,String titoloServizio) throws Exception{
        ResultSet rs = null;
        boolean esito=false;
        Connection conn = null;
        try {   	
        	conn = db.open();
        	conn.setAutoCommit(false);
        	String maxString = "0";
        	int max;
        	String strSQL = "select max(cod_servizio) as maxx from servizi where cod_com=?";	
        	PreparedStatement ps = db.getPreparedStmt(conn, strSQL);
        	ps.setString(1, codEnte);
        	rs = ps.executeQuery();
        	if (rs.next()){
        		maxString = rs.getString("maxx"); 
        	} 
        	if (maxString==null) {
        		maxString="0";
        	}
        	try {
        		max = Integer.parseInt(maxString);
        		max++;
        	} catch (Exception e){max=0;}
        	
        	
    		
        	String strInsert = "insert into servizi values (?,?,?,?,?)";
        	ps = db.getPreparedStmt(conn,strInsert);
            ps.setInt(1, max);
            ps.setString(2, codEnte);
            ps.setInt(3, Integer.parseInt(codiceEventoVita));
            ps.setString(4,newXML);
            ps.setString(5, configuration);
            
            int ret = db.insert(ps);
            if (ret==1){
            	strInsert = "insert into servizi_testi values (?,?,?,?,?,?)";
            	ps = db.getPreparedStmt(conn,strInsert);
                ps.setInt(1, max);
                ps.setString(2, codEnte);
                ps.setInt(3, Integer.parseInt(codiceEventoVita));
                ps.setString(4,"it");
                ps.setString(5, descrizioneServizio);
                ps.setString(6, titoloServizio);
                ret = db.insert(ps);
                if (ret ==1){
                	esito=true;
                }
            }
            conn.commit();
        } catch (SQLException e) {
            throw e;
        } finally {
            try { rs.close(); } catch (Exception e) {}
            try { conn.close(); } catch (Exception e) {}
        } 	
		return esito;
    	

	}

	
	public String getXMLServizio(String codEnte, String codiceEventoVita, String codiceServizio) throws Exception {
        ResultSet rs = null;
        Connection conn = null;
        String ret="";
		try {
	   		conn = db.open();
	    	String strSQL = "select * from servizi_old where cod_servizio=? and cod_com=? and cod_eve_vita=?";
	    	PreparedStatement ps = db.getPreparedStmt(conn, strSQL);
	        ps.setInt(1, Integer.parseInt(codiceServizio));
	        ps.setString(2, codEnte);
	        ps.setInt(3, Integer.parseInt(codiceEventoVita));
	        rs = ps.executeQuery();
	        if (rs.next()){
	        	ret = rs.getString("bookmark_pointer");
	        }
        } catch (SQLException e) {
            throw e;
        } finally {
            try { rs.close(); } catch (Exception e) {}
            try { conn.close(); } catch (Exception e) {}
        } 
		return ret;
	}

	
	public ArrayList getListaServiziNET(String codEnte)  throws Exception {
        ResultSet rs = null;
        Connection conn = null;
        ArrayList ret=new ArrayList();
		try {
	   		conn = db.open();
	    	String strSQL = "select * from eventi_vita";
	    	PreparedStatement ps = db.getPreparedStmt(conn, strSQL);
	    	rs = ps.executeQuery();
	    	while (rs.next()){
	    		BaseBeanRows bbr = new BaseBeanRows();
	    		bbr.setCodice(rs.getString("cod_eve_vita"));
	    		bbr.setDescrizione(rs.getString("des_eve_vita"));
	    		bbr.setRows(new ArrayList());
	    		ret.add(bbr);
	    	}
	    	Iterator it = ret.iterator();
	    	while (it.hasNext()){
	    		BaseBeanRows bbr = (BaseBeanRows) it.next();
	    		strSQL = "select * from servizi_testi_old where cod_com=? and cod_eve_vita=?";
	    		ps = db.getPreparedStmt(conn, strSQL);
		        ps.setString(1, codEnte);
		        ps.setInt(2, Integer.parseInt(bbr.getCodice()));
	    		rs = ps.executeQuery();
	    		while(rs.next()){
	    			BaseBean bb = new BaseBean();
	    			String desc = rs.getString("nome_servizio");
	    			if (desc!=null && desc.length()>160){
	    				desc = desc.substring(0, 160)+"...";
	    			}
	    			bb.setDescrizione(desc);
	    			String codice = "";
	    			codice += Utilities.NVL(rs.getString("cod_com"),"");
	    			codice += "_";
	    			codice += Utilities.NVL(rs.getString("cod_eve_vita"),"");
	    			codice += "_";
	    			codice += Utilities.NVL(rs.getString("cod_servizio"),"");
	    			bb.setCodice(codice);
	    			bbr.getRows().add(bb);
	    		}
	    	}
        } catch (SQLException e) {
            throw e;
        } finally {
            try { rs.close(); } catch (Exception e) {}
            try { conn.close(); } catch (Exception e) {}
        } 
		return ret;
	}

	public boolean saveNewBookmark(String newXML, String configuration,String cod) throws Exception {
        ResultSet rs = null;
        boolean esito=false;
        Connection conn = null;
        try {
    		String codEnte = "";
    		String codiceEventoVita = "";
    		String codiceServizio = "";
    		String[] tokens = cod.split("_");
    		codEnte = tokens[0];
    		codiceEventoVita = tokens[1];
    		codiceServizio = tokens[2];
        	
        	conn = db.open();
        	conn.setAutoCommit(false);
        	String maxString = "0";
        	int max;
        	String strSQL = "select max(cod_servizio) as maxx from servizi where cod_com=?";	
        	PreparedStatement ps = db.getPreparedStmt(conn, strSQL);
        	ps.setString(1, codEnte);
        	rs = ps.executeQuery();
        	if (rs.next()){
        		maxString = rs.getString("maxx"); 
        	} 
        	if (maxString==null) {
        		maxString="0";
        	}
        	try {
        		max = Integer.parseInt(maxString);
        		max++;
        	} catch (Exception e){max=0;}
        	
    		strSQL = "select * from servizi_testi_old where cod_com=? and cod_eve_vita=? and cod_servizio=? and cod_lang='it'";
    		ps = db.getPreparedStmt(conn, strSQL);
	        ps.setString(1, codEnte);
	        ps.setString(2, codiceEventoVita);
	        ps.setString(3, codiceServizio);
    		rs = ps.executeQuery();
    		String titoloBookmark="";
    		String descBookmark="";
    		if (rs.next()){
    			titoloBookmark = rs.getString("nome_servizio");
    			descBookmark = rs.getString("des_servizio");
    		}
        	
    		
        	String strInsert = "insert into servizi values (?,?,?,?,?)";
        	ps = db.getPreparedStmt(conn,strInsert);
            ps.setInt(1, max);
            ps.setString(2, codEnte);
            ps.setInt(3, Integer.parseInt(codiceEventoVita));
            ps.setString(4,newXML);
            ps.setString(5, configuration);
            
            int ret = db.insert(ps);
            if (ret==1){
            	strInsert = "insert into servizi_testi values (?,?,?,?,?,?)";
            	ps = db.getPreparedStmt(conn,strInsert);
                ps.setInt(1, max);
                ps.setString(2, codEnte);
                ps.setInt(3, Integer.parseInt(codiceEventoVita));
                ps.setString(4,"it");
                ps.setString(5, descBookmark);
                ps.setString(6, titoloBookmark);
                ret = db.insert(ps);
                if (ret ==1){
                	esito=true;
                }
            }
            conn.commit();
        } catch (SQLException e) {
            throw e;
        } finally {
            try { rs.close(); } catch (Exception e) {}
            try { conn.close(); } catch (Exception e) {}
        } 	
		return esito;
	}

//	public int[] importaAll(IRequestWrapper request,String lang,AbstractPplProcess process) throws Exception {
//        ResultSet rs = null;
//        Connection conn = null;
//    	int[] ret = new int[3];
//    	String configuration ="<CONFIGURAZIONE>\n"+
//    						"	<TYPE_OPZ>COMPLETO|CORTESIA|LIVELLO2</TYPE_OPZ>\n"+
//    						"	<TYPE>COMPLETO</TYPE>\n"+
//    						"	<FIRMADIGITALE_OPZ>TRUE|FALSE</FIRMADIGITALE_OPZ>\n"+
//    						"	<FIRMADIGITALE>TRUE</FIRMADIGITALE>\n"+
//    						"	<PAGAMENTO_OPZ>DISABILITA|FORZA_PAGAMENTO|OPZIONALE</PAGAMENTO_OPZ>\n"+
//    						"	<PAGAMENTO>OPZIONALE</PAGAMENTO>\n"+
//    						"</CONFIGURAZIONE>"; 
//    	
//    	conn = db.open();
//    	String strSQL = "SELECT COUNT(*) as tot FROM servizi";
//    	PreparedStatement ps2 = db.getPreparedStmt(conn, strSQL);
//    	rs = ps2.executeQuery();
//    	if (rs.next()){
//    		ret[0] = rs.getInt("tot");
//    	}
//    	strSQL = "SELECT * FROM servizi WHERE cod_eve_vita!=10 and bookmark_pointer LIKE '%<it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">%'";
//    	PreparedStatement ps = db.getPreparedStmt(conn, strSQL);
//    	rs = ps.executeQuery();
//    	int totale = 0;
//    	int bookmarkImportati = 0;
//    	ManagerImportRavenna mir = new ManagerImportRavenna(request,db,lang,"",process);
//    	while (rs.next()){
//    		String codiceServizio = rs.getString("cod_servizio");
//    		String codiceComune = rs.getString("cod_com");
//    		String codEventoVita = rs.getString("cod_eve_vita");
//    		String oldBookmark = rs.getString("bookmark_pointer");
//    		mir.setXmlInput(oldBookmark);
//    		try {
//    			String newXML = mir.buildSingoloBookmark();
//    			if (saveNewBookmark(conn,newXML, configuration, codiceComune, codEventoVita, codiceServizio)){
//    				bookmarkImportati++;
//    			}
//    		} catch (Exception e){
//    			e.printStackTrace();
//    		}
//    		totale++;	
//    	}
//    	ret[1] = totale;
//    	ret[2] = bookmarkImportati;
//    	return ret;
//	}

	
	
	
//  ResultSet rs = null;
//  Connection conn = null;
//	conn = db.open();
//	String sql = "select count(*) as num from allegati";
//	PreparedStatement ps = db.getPreparedStmt(conn, sql);
//	//ps.setString(1, "");
//	rs = ps.executeQuery();
//	
//	 while (rs.next()) {
//		 String nume = rs.getString("num");
//		 System.out.println(nume);
//	 }
	
	
}
