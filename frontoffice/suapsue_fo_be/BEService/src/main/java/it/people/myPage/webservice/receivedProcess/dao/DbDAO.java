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
 * http://joinup.ec.europa.eu/software/page/eupl
 * 
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * 
 * This product includes software developed by Yale University
 * 
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
**/
package it.people.myPage.webservice.receivedProcess.dao;

import it.people.myPage.webservice.receivedProcess.oggetti.InfoBean;
import java.sql.Connection;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import org.apache.log4j.Category;


public class DbDAO {
	
	static Category cat = Category.getRoot();
	Connection conn = null;
	private String dataSource;
	
	public DbDAO(String dataSource){
		try {
			this.dataSource=dataSource;
		} catch (Exception e) {
			cat.error("DbDAO (costruttore dataSource : "+dataSource+") ");
			cat.error(e.getMessage());
		}
	}
	
	private void open(){
		try {
			InitialContext initCtx = new InitialContext();
			DataSource ds = (DataSource)initCtx.lookup(dataSource);
			this.conn = ds.getConnection();
		} catch (Exception e) {
			cat.error("Allegati Service - DbDAO (open connection) ");
			cat.error(e.getMessage());
		}
	}
	
	public String setInfo(InfoBean info) throws Exception{
		String ret = "KO";
		ResultSet rs = null; 
		PreparedStatement ps = null;
        try {
            open();   
            String sql ="insert into eventi_process " +
            		"(PROCESS_DATA_ID,DATE_RECEIVED,DESCRIPTION_EVENT,ID_BO,URL_VISURA,VISIBILE,NOTE) values (?,?,?,?,?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, info.getProcess_data_id());
            Date d = new Date(info.getTimestamp_evento());
            SimpleDateFormat fechabase = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String strDate = fechabase.format(d);
            ps.setString(2, strDate);
            ps.setString(3, info.getDescrizione_evento());
            ps.setString(4, info.getId_bo());
            ps.setString(5, info.getUrl_visura());
            ps.setString(6, info.isVisibilita()?"1":"0");
            ps.setString(7, info.getAltreInfo());
	        ps.execute();
	        ret="OK";
        } catch (Exception e){
        	cat.error("Allegati Service - DbDAO (getParametroConfigurazione) ");
            cat.error("", e);
            throw e;
        } finally {
            try { rs.close(); } catch (Exception e) {}
            try { ps.close(); } catch (Exception e) {}
            try { conn.close(); } catch (Exception e) {}
        } 
		return ret;
	}
}
