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

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.gruppoinit.commons.DBCPManager;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility.Costant;
import it.people.util.ServiceParameters;
import it.people.wrappers.IRequestWrapper;

public class PuDAO {
	

	public final Log logger = LogFactory.getLog(this.getClass());

    protected HttpSession session;
    
    protected DBCPManager db;
	
	public boolean initialise(IRequestWrapper request) {
		if (request==null || request.getUnwrappedRequest()==null  || request.getUnwrappedRequest().getSession()==null) {
    		return false;
    	} else {
    		try {
    			session = request.getUnwrappedRequest().getSession();
    			ServiceParameters params = (ServiceParameters) session.getAttribute("serviceParameters");
		
    			String jndi = "java:comp/env/jdbc/FEDB";
    			this.db = new DBCPManager(jndi);
    			
    		} catch (Exception e) {return false;}
    	}
		return true;
	}
	
	
	public void getParametriPU(){
		ResultSet rs = null;
		Connection conn = null;
		try {
            conn = db.open();

            String sql = "select * from configuration";
            PreparedStatement ps = db.getPreparedStmt(conn, sql);
            rs = ps.executeQuery();
            
            while (rs.next()){
            }
		} catch (Exception e){
            try { rs.close(); } catch (Exception e_) {}
            try { conn.close();} catch (Exception e_) {}
		}
	}



	public void setParametroTypePU(IRequestWrapper request, ProcessData dataForm) {
		ResultSet rs = null;
		Connection conn = null;
		PreparedStatement ps = null;
		try {
            String tipologiaServizio = request.getParameter("TB_pu");
//            String tipologiaFirma = request.getParameter("FP_pu");
//            String tipologiaPagamento = request.getParameter("AP_pu");
//            String conInvioString = request.getParameter("INVIO_pu");
			
            
            conn = db.open();

            String sql = "SELECT configuration.id as id, configuration.name, configuration.value FROM service " 
            	.concat(" LEFT OUTER JOIN configuration ON service.id=configuration.serviceid ")
            	.concat(" WHERE communeid=? AND configuration.name=? AND package=? ")
            	.concat(" AND attivita=? AND sottoattivita=?");
            
            ps = db.getPreparedStmt(conn, sql);
            ps.setString(1, dataForm.getIdentificatoreUnivoco().getCodiceSistema().getCodiceAmministrazione());
            ps.setString(2, "type");
            ps.setString(3, "it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico");
            ps.setString(4, "concessioniedautorizzazioni");
            ps.setString(5, "servizicondivisi");
            rs = ps.executeQuery();
            if (rs.next()) {
            	String id = rs.getString("id");
            	if (id!=null && !id.equalsIgnoreCase("")){
            		String updateSql = "update configuration set value=? where id=? and name=? ";
            		ps = db.getPreparedStmt(conn, updateSql);
            		if (tipologiaServizio!=null && tipologiaServizio.equalsIgnoreCase("1")) {	
            			ps.setString(1, Costant.bookmarkTypeCortesiaLabel);
            		} else if (tipologiaServizio!=null && tipologiaServizio.equalsIgnoreCase("2")) {	
            			ps.setString(1, Costant.bookmarkTypeLivello2Label);
            		} else {
            			ps.setString(1, Costant.bookmarkTypeCompleteLabel);
            		}
            		ps.setString(2,id);
            		ps.setString(3, "type");
            		ps.executeUpdate();
            	}
            } else {
            	String selectServiceIdQuery = "select id from service where communeid=? and package=? and attivita=? and sottoattivita=? ";
            	ps = db.getPreparedStmt(conn, selectServiceIdQuery);
            	ps.setString(1,dataForm.getIdentificatoreUnivoco().getCodiceSistema().getCodiceAmministrazione());
            	ps.setString(2,"it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico");
            	ps.setString(3,"concessioniedautorizzazioni");
            	ps.setString(4,"servizicondivisi");
            	rs = ps.executeQuery();
            	String serviceId = null;
            	if (rs.next()){
            		serviceId = rs.getString("id");
            		String maxIdQuery = "select max(id) AS idmassimo from configuration"; // where serviceid=?";
            		ps = db.getPreparedStmt(conn, maxIdQuery);
            		//ps.setString(1, serviceId);
            		rs = ps.executeQuery();
            		String id = "1";
            		if (rs.next()){
            			int max = rs.getInt("idmassimo");
            			max++;
            			id = String.valueOf(max);
            		}
            		String insertSql = "insert into configuration values (?,?,?,?)";
            		ps = db.getPreparedStmt(conn, insertSql);
            		ps.setString(1, id);
            		ps.setString(2, serviceId);
            		ps.setString(3, "type");
            		if (tipologiaServizio!=null && tipologiaServizio.equalsIgnoreCase("1")) {	
            			ps.setString(4, Costant.bookmarkTypeCortesiaLabel);
            		} else if (tipologiaServizio!=null && tipologiaServizio.equalsIgnoreCase("2")) {	
            			ps.setString(4, Costant.bookmarkTypeLivello2Label);
            		} else {
            			ps.setString(4, Costant.bookmarkTypeCompleteLabel);
            		}
            		ps.executeQuery();
            	}
            	
            }
            
		} catch (Exception e){
            try { rs.close(); } catch (Exception e_) {}
            try { ps.close(); } catch (Exception e_) {}
            try { conn.close();} catch (Exception e_) {}
		}
		
	}


	public void setParametroFirmaPU(IRequestWrapper request,ProcessData dataForm) {
		ResultSet rs = null;
		Connection conn = null;
		PreparedStatement ps = null;
		try {

            String tipologiaFirma = request.getParameter("FP_pu");
			
            
            conn = db.open();

            String sql = "SELECT configuration.id as id, configuration.name, configuration.value FROM service " 
            	.concat(" LEFT OUTER JOIN configuration ON service.id=configuration.serviceid ")
            	.concat(" WHERE communeid=? AND configuration.name=? AND package=? ")
            	.concat(" AND attivita=? AND sottoattivita=?");
            
            ps = db.getPreparedStmt(conn, sql);
            ps.setString(1, dataForm.getIdentificatoreUnivoco().getCodiceSistema().getCodiceAmministrazione());
            ps.setString(2, "firmadigitale");
            ps.setString(3, "it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico");
            ps.setString(4, "concessioniedautorizzazioni");
            ps.setString(5, "servizicondivisi");
            rs = ps.executeQuery();
            if (rs.next()) {
            	String id = rs.getString("id");
            	if (id!=null && !id.equalsIgnoreCase("")){
            		String updateSql = "update configuration set value=? where id=? and name=? ";
            		ps = db.getPreparedStmt(conn, updateSql);
            		if (tipologiaFirma!=null && tipologiaFirma.equalsIgnoreCase("1")) {	
            			ps.setString(1, Costant.senzaFirmaLabel);
            		} else {
            			ps.setString(1, Costant.conFirmaLabel);
            		}
            		ps.setString(2,id);
            		ps.setString(3, "firmadigitale");
            		ps.executeUpdate();
            	}
            } else {
            	String selectServiceIdQuery = "select id from service where communeid=? and package=? and attivita=? and sottoattivita=? ";
            	ps = db.getPreparedStmt(conn, selectServiceIdQuery);
            	ps.setString(1,dataForm.getIdentificatoreUnivoco().getCodiceSistema().getCodiceAmministrazione());
            	ps.setString(2,"it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico");
            	ps.setString(3,"concessioniedautorizzazioni");
            	ps.setString(4,"servizicondivisi");
            	rs = ps.executeQuery();
            	String serviceId = null;
            	if (rs.next()){
            		serviceId = rs.getString("id");
            		String maxIdQuery = "select max(id) AS idmassimo from configuration "; // where serviceid=?";
            		ps = db.getPreparedStmt(conn, maxIdQuery);
            		//ps.setString(1, serviceId);
            		rs = ps.executeQuery();
            		String id = "1";
            		if (rs.next()){
            			int max = rs.getInt("idmassimo");
            			max++;
            			id = String.valueOf(max);
            		}
            		String insertSql = "insert into configuration values (?,?,?,?)";
            		ps = db.getPreparedStmt(conn, insertSql);
            		ps.setString(1, id);
            		ps.setString(2, serviceId);
            		ps.setString(3, "firmadigitale");
            		if (tipologiaFirma!=null && tipologiaFirma.equalsIgnoreCase("1")) {	
            			ps.setString(4, Costant.senzaFirmaLabel);
            		} else {
            			ps.setString(4, Costant.conFirmaLabel);
            		}
            		ps.executeQuery();
            	}
            	
            }
            
		} catch (Exception e){
            try { rs.close(); } catch (Exception e_) {}
            try { ps.close(); } catch (Exception e_) {}
            try { conn.close();} catch (Exception e_) {}
		}
		
	}


	public void setParametroPagamentiPU(IRequestWrapper request,ProcessData dataForm) {
		ResultSet rs = null;
		Connection conn = null;
		PreparedStatement ps = null;
		try {

            String tipologiaPagamento = request.getParameter("AP_pu");
            
            conn = db.open();

            String sql = "SELECT configuration.id as id, configuration.name, configuration.value FROM service " 
            	.concat(" LEFT OUTER JOIN configuration ON service.id=configuration.serviceid ")
            	.concat(" WHERE communeid=? AND configuration.name=? AND package=? ")
            	.concat(" AND attivita=? AND sottoattivita=?");
            
            ps = db.getPreparedStmt(conn, sql);
            ps.setString(1, dataForm.getIdentificatoreUnivoco().getCodiceSistema().getCodiceAmministrazione());
            ps.setString(2, "pagamento");
            ps.setString(3, "it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico");
            ps.setString(4, "concessioniedautorizzazioni");
            ps.setString(5, "servizicondivisi");
            rs = ps.executeQuery();
            if (rs.next()) {
            	String id = rs.getString("id");
            	if (id!=null && !id.equalsIgnoreCase("")){
            		String updateSql = "update configuration set value=? where id=? and name=? ";
            		ps = db.getPreparedStmt(conn, updateSql);
            		if (tipologiaPagamento!=null && tipologiaPagamento.equalsIgnoreCase("0")) {	
            			ps.setString(1, Costant.disabilitaPagamentoLabel);
            		} else if (tipologiaPagamento!=null && tipologiaPagamento.equalsIgnoreCase("1")) {	
            			ps.setString(1, Costant.forzaPagamentoLabel);
            		} else {
            			ps.setString(1, Costant.pagamentoOpzionaleLabel);
            		}
            		ps.setString(2,id);
            		ps.setString(3, "pagamento");
            		ps.executeUpdate();
            	}
            } else {
            	String selectServiceIdQuery = "select id from service where communeid=? and package=? and attivita=? and sottoattivita=? ";
            	ps = db.getPreparedStmt(conn, selectServiceIdQuery);
            	ps.setString(1,dataForm.getIdentificatoreUnivoco().getCodiceSistema().getCodiceAmministrazione());
            	ps.setString(2,"it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico");
            	ps.setString(3,"concessioniedautorizzazioni");
            	ps.setString(4,"servizicondivisi");
            	rs = ps.executeQuery();
            	String serviceId = null;
            	if (rs.next()){
            		serviceId = rs.getString("id");
            		String maxIdQuery = "select max(id) AS idmassimo from configuration "; // where serviceid=?";
            		ps = db.getPreparedStmt(conn, maxIdQuery);
            		// ps.setString(1, serviceId);
            		rs = ps.executeQuery();
            		String id = "1";
            		if (rs.next()){
            			int max = rs.getInt("idmassimo");
            			max++;
            			id = String.valueOf(max);
            		}
            		String insertSql = "insert into configuration values (?,?,?,?)";
            		ps = db.getPreparedStmt(conn, insertSql);
            		ps.setString(1, id);
            		ps.setString(2, serviceId);
            		ps.setString(3, "pagamento");
            		if (tipologiaPagamento!=null && tipologiaPagamento.equalsIgnoreCase("0")) {	
            			ps.setString(4, Costant.disabilitaPagamentoLabel);
            		} else if (tipologiaPagamento!=null && tipologiaPagamento.equalsIgnoreCase("1")) {	
            			ps.setString(4, Costant.forzaPagamentoLabel);
            		} else {
            			ps.setString(4, Costant.pagamentoOpzionaleLabel);
            		}
            		ps.executeQuery();
            	}
            	
            }
            
		} catch (Exception e){
            try { rs.close(); } catch (Exception e_) {}
            try { ps.close(); } catch (Exception e_) {}
            try { conn.close();} catch (Exception e_) {}
		}
		
	}


	public void setParametroConInvioPU(IRequestWrapper request,	ProcessData dataForm) {
		ResultSet rs = null;
		Connection conn = null;
		PreparedStatement ps = null;
		try {

            String tipologiaPagamento = request.getParameter("INVIO_pu");
            
            conn = db.open();

            String sql = "SELECT configuration.id as id, configuration.name, configuration.value FROM service " 
            	.concat(" LEFT OUTER JOIN configuration ON service.id=configuration.serviceid ")
            	.concat(" WHERE communeid=? AND configuration.name=? AND package=? ")
            	.concat(" AND attivita=? AND sottoattivita=?");
            
            ps = db.getPreparedStmt(conn, sql);
            ps.setString(1, dataForm.getIdentificatoreUnivoco().getCodiceSistema().getCodiceAmministrazione());
            ps.setString(2, "con_invio");
            ps.setString(3, "it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico");
            ps.setString(4, "concessioniedautorizzazioni");
            ps.setString(5, "servizicondivisi");
            rs = ps.executeQuery();
            if (rs.next()) {
            	String id = rs.getString("id");
            	if (id!=null && !id.equalsIgnoreCase("")){
            		String updateSql = "update configuration set value=? where id=? and name=? ";
            		ps = db.getPreparedStmt(conn, updateSql);
            		if (tipologiaPagamento!=null && tipologiaPagamento.equalsIgnoreCase("1")) {	
            			ps.setString(1, Costant.senzaInvioLabel);
            		} else {
            			ps.setString(1, Costant.conInvioLabel);
            		}
            		ps.setString(2,id);
            		ps.setString(3, "con_invio");
            		ps.executeUpdate();
            	}
            } else {
            	String selectServiceIdQuery = "select id from service where communeid=? and package=? and attivita=? and sottoattivita=? ";
            	ps = db.getPreparedStmt(conn, selectServiceIdQuery);
            	ps.setString(1,dataForm.getIdentificatoreUnivoco().getCodiceSistema().getCodiceAmministrazione());
            	ps.setString(2,"it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico");
            	ps.setString(3,"concessioniedautorizzazioni");
            	ps.setString(4,"servizicondivisi");
            	rs = ps.executeQuery();
            	String serviceId = null;
            	if (rs.next()){
            		serviceId = rs.getString("id");
            		String maxIdQuery = "select max(id) AS idmassimo from configuration "; // where serviceid=?";
            		ps = db.getPreparedStmt(conn, maxIdQuery);
            		// ps.setString(1, serviceId);
            		rs = ps.executeQuery();
            		String id = "1";
            		if (rs.next()){
            			int max = rs.getInt("idmassimo");
            			max++;
            			id = String.valueOf(max);
            		}
            		String insertSql = "insert into configuration values (?,?,?,?)";
            		ps = db.getPreparedStmt(conn, insertSql);
            		ps.setString(1, id);
            		ps.setString(2, serviceId);
            		ps.setString(3, "con_invio");
            		if (tipologiaPagamento!=null && tipologiaPagamento.equalsIgnoreCase("1")) {	
            			ps.setString(4, Costant.senzaInvioLabel);
            		} else {
            			ps.setString(4, Costant.conInvioLabel);
            		}
            		ps.executeQuery();
            	}
            	
            }
            
		} catch (Exception e){
            try { rs.close(); } catch (Exception e_) {}
            try { ps.close(); } catch (Exception e_) {}
            try { conn.close();} catch (Exception e_) {}
		}		
	}
}
