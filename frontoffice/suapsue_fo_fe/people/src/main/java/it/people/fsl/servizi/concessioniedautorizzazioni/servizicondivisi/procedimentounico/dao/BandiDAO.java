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
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.gruppoinit.commons.DBCPManager;
import it.gruppoinit.commons.Utilities;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.BandoBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.BaseBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.BaseBeanRows;

public class BandiDAO extends ProcedimentoUnicoDAO{
	private Log log = LogFactory.getLog(this.getClass());
	private String lang;
	
	public BandiDAO(DBCPManager dbm, String nazionalita) {
		super(dbm, nazionalita);
		this.lang = nazionalita;
	}
	
	public ArrayList getListaBookmarkPerBandi(String codCom) throws SQLException {
	    ResultSet rs = null;
	    Connection conn = null;
	    String sql = null;
	    ArrayList lista = new ArrayList();

        try {
            sql = "SELECT DISTINCT D.des_eve_vita as descrizione ,A.cod_eve_vita as codice "
            	.concat("FROM servizi A ") 
            	.concat("INNER JOIN eventi_vita D ON D.cod_eve_vita=A.cod_eve_vita AND D.cod_lang=? ")
            	.concat("WHERE A.cod_com=? ")
            	.concat("ORDER BY D.des_eve_vita");
    	    String sql2 = "SELECT B.nome_servizio,A.cod_servizio, DATE_FORMAT(C.dalla_data,'%d/%m/%Y') AS dalgiorno, DATE_FORMAT(C.alla_data,'%d/%m/%Y') AS algiorno, C.flg_bando AS isBando "
    	    	.concat("FROM servizi A ") 
    	    	.concat("INNER JOIN servizi_testi B ON B.cod_servizio = A.cod_servizio AND B.cod_com = A.cod_com AND B.cod_eve_vita = A.cod_eve_vita ")
    	    	.concat("LEFT OUTER JOIN servizi_validita C ON B.cod_servizio = C.cod_servizio ")
    	    	.concat("WHERE A.cod_com=? AND A.cod_eve_vita=? AND B.cod_lang=?")
    	    	.concat("ORDER BY B.nome_servizio ");
            conn = db.open();
            PreparedStatement ps = db.getPreparedStmt(conn, sql);
            ps.setString(1, lang);
            ps.setString(2, codCom);
            log.info("getListaBookmarkPerBandi: " + ps);
            rs = ps.executeQuery();
            while (rs.next()){
            	BaseBeanRows bbr = new BaseBeanRows();
            	bbr.setCodice(rs.getString("codice"));
            	bbr.setDescrizione(rs.getString("descrizione"));
            	ArrayList listaServizi = new ArrayList();
            	PreparedStatement ps2 = db.getPreparedStmt(conn, sql2);
                ps2.setString(1, codCom);
                ps2.setString(2, bbr.getCodice());
                ps2.setString(3, lang);
                ResultSet rs2 = ps2.executeQuery();
                while (rs2.next()){
                	BandoBean bb = new BandoBean();
                	bb.setCod_servizio(rs2.getString("cod_servizio"));
                	bb.setTitoloServizio(rs2.getString("nome_servizio"));
                	bb.setDallaData(rs2.getString("dalgiorno"));
                	bb.setAllaData(rs2.getString("algiorno"));
                	String isBandoString = rs2.getString("isBando");
               		bb.setFlg_bando(Utilities.checked(isBandoString));
                	listaServizi.add(bb);
                }
                rs2.close();
                ps2.close();
                bbr.setRows(listaServizi);
                lista.add(bbr);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            try { rs.close(); } catch (Exception e) {}
            try { conn.close(); } catch (Exception e) {}
        }
        return lista;
  
	}

	public BandoBean getBookmarkDettaglioPerBandi(String codEnte,String cod_servizio) throws SQLException {
	    ResultSet rs = null;
	    Connection conn = null;
	    BandoBean bb = null;
	    String sql = "SELECT B.des_servizio,B.nome_servizio,A.cod_servizio, DATE_FORMAT(C.dalla_data,'%d/%m/%Y') AS dalgiorno, DATE_FORMAT(C.alla_data,'%d/%m/%Y') AS algiorno, C.flg_bando AS isBando "
	    	.concat("FROM servizi A ") 
	    	.concat("INNER JOIN servizi_testi B ON B.cod_servizio = A.cod_servizio AND B.cod_com = A.cod_com AND B.cod_eve_vita = A.cod_eve_vita ")
	    	.concat("LEFT OUTER JOIN servizi_validita C ON B.cod_servizio = C.cod_servizio ")
	    	.concat("WHERE A.cod_com=? AND A.cod_servizio=? AND B.cod_lang=?")
	    	.concat("ORDER BY B.nome_servizio ");
	    try {
            conn = db.open();
            PreparedStatement ps = db.getPreparedStmt(conn, sql);
            ps.setString(1, codEnte);
            ps.setString(2, cod_servizio);
            ps.setString(3, lang);
            log.info("getBookmarkDettaglioPerBandi: " + ps);
            rs = ps.executeQuery();
            if (rs.next()){
            	bb = new BandoBean();
            	bb.setCod_servizio(rs.getString("cod_servizio"));
            	bb.setDescrizioneServizio(Utilities.NVL(rs.getString("des_servizio"),""));
            	bb.setTitoloServizio(rs.getString("nome_servizio"));
            	bb.setDallaData(Utilities.NVL(rs.getString("dalgiorno"),""));
            	bb.setAllaData(Utilities.NVL(rs.getString("algiorno"),""));
            	String isBandoString = rs.getString("isBando");
           		bb.setFlg_bando(Utilities.checked(isBandoString));
            	sql = "select * from servizi_accesslist where cod_servizio=?";
            	ps = db.getPreparedStmt(conn, sql);
            	ps.setString(1, cod_servizio);
            	rs = ps.executeQuery();
            	while (rs.next()){
            		BaseBean baseB = new BaseBean();
            		String cod_fisc = rs.getString("codfisc");
            		baseB.setCodice(cod_servizio+"#"+cod_fisc);
            		baseB.setDescrizione(cod_fisc);
            		bb.addAccessList(baseB);
            	}
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            try { rs.close(); } catch (Exception e) {}
            try { conn.close(); } catch (Exception e) {}
        }
		return bb;
	}

	public void addUtenteAccessList(String cod_servizio, String newUtente) throws SQLException {
	    ResultSet rs = null;
	    Connection conn = null;
	    String sql = "insert into servizi_accesslist (cod_servizio,codfisc) values(?,?)";
	    try {
            conn = db.open();
            PreparedStatement ps = db.getPreparedStmt(conn, sql);
            ps.setString(1, cod_servizio);
            ps.setString(2, newUtente);
            log.info("addUtenteAccessList: " + ps);
            ps.execute();
        } catch (SQLException e) {
            throw e;
        } finally {
            try { rs.close(); } catch (Exception e) {}
            try { conn.close(); } catch (Exception e) {}
        }   
		
	}

	public void removeUtenteAccessList(String cod_servizio, String cf) throws SQLException {
	    ResultSet rs = null;
	    Connection conn = null;
	    String sql = "delete from servizi_accesslist where cod_servizio=? and codfisc=?";
	    try {
            conn = db.open();
            PreparedStatement ps = db.getPreparedStmt(conn, sql);
            ps.setString(1, cod_servizio);
            ps.setString(2, cf);
            log.info("removeUtenteAccessList: " + ps);
            ps.execute();
        } catch (SQLException e) {
            throw e;
        } finally {
            try { rs.close(); } catch (Exception e) {}
            try { conn.close(); } catch (Exception e) {}
        } 
		
	}

	public void updateInfoBando(String cod_servizio, boolean isBando,Date dallaData, Date allaData) throws SQLException {
	    ResultSet rs = null;
	    Connection conn = null;
	    PreparedStatement ps=null;
	    String sql = "";
	    try {
            conn = db.open();
            sql = "select * from servizi_validita where cod_servizio=?";
            ps = db.getPreparedStmt(conn, sql);
            ps.setString(1, cod_servizio);
            log.info("updateInfoBando 1: " + ps);
            rs = ps.executeQuery();
            if (rs.next()){ // il bando esisteva
            	if (!isBando && dallaData==null && allaData==null){
            		sql = "delete from servizi_validita where cod_servizio=? ";
            		ps = db.getPreparedStmt(conn, sql);
            		ps.setString(1, cod_servizio);
            		ps.execute();
            	} else {
	            	sql = "update servizi_validita set dalla_data=?, alla_data=?, flg_bando=? where cod_servizio=? ";
	            	
	            	ps = db.getPreparedStmt(conn, sql);
	            	if (dallaData!=null){
	            		ps.setDate(1, dallaData);
	            	} else {
	            		ps.setNull(1, Types.DATE);
	            	}
	            	if (allaData!=null) {
	            		ps.setDate(2, allaData);
	            	} else {
	            		ps.setNull(2, Types.DATE);
	            	}
	            	ps.setString(3, isBando?"1":"0");
	                ps.setString(4, cod_servizio);
	                log.info("updateInfoBando 2: " + ps);
	                ps.execute();
            	}
            } else { // il bando non esisteva
            	if (!isBando && dallaData==null && allaData==null){
            		// non devo fare nulla
            	} else {
	        		sql = "insert into servizi_validita (cod_servizio,flg_bando,dalla_data,alla_data) values (?,?,?,?) ";
	        		ps = db.getPreparedStmt(conn, sql);
	                ps.setString(1, cod_servizio);
	                ps.setString(2, isBando?"1":"0");
	            	if (dallaData!=null){
	            		ps.setDate(3, dallaData);
	            	} else {
	            		ps.setNull(3, Types.DATE);
	            	}
	            	if (allaData!=null) {
	            		ps.setDate(4, allaData);
	            	} else {
	            		ps.setNull(4, Types.DATE);
	            	}
	            	log.info("updateInfoBando 3: " + ps);
	                ps.execute();
            	}
            }
        } catch (SQLException e) {
            throw e;
        } finally {
        	try { ps.close(); } catch (Exception e) {}
            try { rs.close(); } catch (Exception e) {}
            try { conn.close(); } catch (Exception e) {}
        }
		
	}

}
