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
package it.gruppoinit.people.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbDAO {

	// static Category cat = Category.getRoot();
	Connection conn = null;
	private String dataSource;

	private static Logger logger = LoggerFactory.getLogger(DbDAO.class);

	public DbDAO(String dataSource) {
		try {
			this.dataSource = dataSource;
		} catch (Exception e) {
			logger.error("Allegati Service - DbDAO (costruttore dataSource : " + dataSource + ") ");
			logger.error(e.getMessage());
		}
	}

	public void open() {
		try {
			InitialContext initCtx = new InitialContext();
			DataSource ds = (DataSource) initCtx.lookup(dataSource);
			this.conn = ds.getConnection();
		} catch (Exception e) {
			logger.error("Allegati Service - DbDAO (open connection) ");
			logger.error(e.getMessage());
		}
	}

	public String getParametroConfigurazione(String communeId, String nomeParametro) {
		String ret = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			open();
			String sql = "select value from nodeconfiguration where communeid=? and name=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, communeId);
			ps.setString(2, nomeParametro);
			rs = ps.executeQuery();
			if (rs.next()) {
				ret = rs.getString("value");
			} else {
				sql = "select value from nodeconfiguration where communeid is NULL and name=?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, nomeParametro);
				rs = ps.executeQuery();
				if (rs.next()) {
					ret = rs.getString("value");
				}
			}
		} catch (Exception e) {
			logger.error("Allegati Service - DbDAO (getParametroConfigurazione) ");
			logger.error("", e);
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
		return ret;
	}
}
