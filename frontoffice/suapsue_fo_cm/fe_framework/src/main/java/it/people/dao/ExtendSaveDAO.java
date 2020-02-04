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
package it.people.dao;

import it.people.fsl.servizi.oggetticondivisi.PersonaFisica;
import it.people.fsl.servizi.oggetticondivisi.Titolare;
import it.people.fsl.servizi.oggetticondivisi.personagiuridica.PersonaGiuridica;
import it.people.process.data.PplPersistentData;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.log4j.Category;

public class ExtendSaveDAO {

    static Category cat = Category.getRoot();
    Connection conn = null;
    private String dataSource;

    public ExtendSaveDAO(String dataSource) {
	try {
	    this.dataSource = dataSource;
	} catch (Exception e) {
	    cat.error("ExtendSaveDAO (costruttore dataSource : " + dataSource
		    + ") ");
	    cat.error(e.getMessage());
	}
    }

    private void open() {
	try {
	    InitialContext initCtx = new InitialContext();
	    DataSource ds = (DataSource) initCtx.lookup(dataSource);
	    this.conn = ds.getConnection();
	} catch (Exception e) {
	    cat.error("open - ExtendSaveDAO (open connection) ");
	    cat.error(e.getMessage());
	}
    }

    public void setDetail(String oggetto, String descrizione,
	    String codiceProgetto, String istatEnte, String nomeEnte)
	    throws Exception {
	ResultSet rs = null;
	PreparedStatement ps = null;
	try {
	    open();
	    String sql = "select * from detail_process where process_data_id=?";
	    ps = conn.prepareStatement(sql);
	    ps.setString(1, codiceProgetto);
	    rs = ps.executeQuery();
	    if (rs.next()) {
		sql = "update detail_process set oggetto=?, descrizione=?, istat_ente = ?, des_ente = ? where process_data_id=?";
		ps = conn.prepareStatement(sql);
		ps.setString(1, oggetto);
		ps.setString(2, descrizione);
		ps.setString(3, istatEnte);
		ps.setString(4, nomeEnte);
		ps.setString(5, codiceProgetto);
		ps.executeUpdate();
	    } else {
		sql = "insert into detail_process (oggetto,descrizione,process_data_id,istat_ente,des_ente) values(?,?,?,?,?)";
		ps = conn.prepareStatement(sql);
		ps.setString(1, oggetto);
		ps.setString(2, descrizione);
		ps.setString(3, codiceProgetto);
		ps.setString(4, istatEnte);
		ps.setString(5, nomeEnte);
		ps.execute();
	    }
	} catch (Exception e) {
	    cat.error("setDetail - ExtendSaveDAO (getParametroConfigurazione) ");
	    cat.error(e);
	    throw e;
	} finally {
	    try {
		rs.close();
	    } catch (Exception e) {
	    }
	    try {
		ps.close();
	    } catch (Exception e) {
	    }
	    try {
		conn.close();
	    } catch (Exception e) {
	    }
	}
    }

    public void setAltriTitolari(ArrayList listaAltriTitolari,
	    String identificatoreProcedimento) {
	try {
	    open();
	    conn.setAutoCommit(false);
	    removeOldTitolari(conn, identificatoreProcedimento);
	    if (listaAltriTitolari != null) {
		for (Iterator iterator = listaAltriTitolari.iterator(); iterator
			.hasNext();) {
		    Titolare titolare = (Titolare) iterator.next();
		    if (titolare.getPersonaFisica() != null) {
			setAltroTitolarePersonaFisica(conn,
				titolare.getPersonaFisica(),
				identificatoreProcedimento);
		    } else {
			setAltroTitolarePersonaGiuridica(conn,
				titolare.getPersonaGiuridica(),
				identificatoreProcedimento);
		    }
		}
	    }
	    conn.commit();
	    conn.setAutoCommit(true);
	} catch (Exception e) {
	    try {
		conn.rollback();
	    } catch (Exception e2) {
	    }
	} finally {
	    try {
		conn.close();
	    } catch (Exception e2) {
	    }
	}
    }

    private void removeOldTitolari(Connection conn2,
	    String identificatoreProcedimento) throws Exception {
	PreparedStatement ps = null;
	String sql = "delete from titolari_process where process_data_id=?";
	ps = conn2.prepareStatement(sql);
	ps.setString(1, identificatoreProcedimento);
	ps.execute();
	try {
	    ps.close();
	} catch (Exception e) {
	}
    }

    private void setAltroTitolarePersonaGiuridica(Connection c,
	    PersonaGiuridica personaGiuridica, String identificatoreProcedimento)
	    throws Exception {
	ResultSet rs = null;
	PreparedStatement ps = null;
	try {
	    String sql = "insert into titolari_process (process_data_id,codice_fiscale,partita_iva,nome,cognome,denominazione,persona_fisica,xml_info) values(?,?,?,?,?,?,?,?)";
	    ps = c.prepareStatement(sql);
	    ps.setString(1, identificatoreProcedimento);
	    ps.setString(2, personaGiuridica.getCodiceFiscale());
	    ps.setString(3, personaGiuridica.getPartitaIVA());
	    ps.setNull(4, java.sql.Types.VARCHAR);
	    ps.setNull(5, java.sql.Types.VARCHAR);
	    ps.setString(6, personaGiuridica.getDenominazione());
	    ps.setString(7, "N");
	    ps.setString(8, personaGiuridica.marshall());
	    ps.execute();
	} catch (Exception e) {
	    cat.error("setAltroTitolarePersonaGiuridica - ExtendSaveDAO ");
	    cat.error(e);
	    throw e;
	} finally {
	    try {
		rs.close();
	    } catch (Exception e) {
	    }
	    try {
		ps.close();
	    } catch (Exception e) {
	    }
	}

    }

    private void setAltroTitolarePersonaFisica(Connection c,
	    PersonaFisica personaFisica, String identificatoreProcedimento)
	    throws Exception {
	ResultSet rs = null;
	PreparedStatement ps = null;
	try {
	    String sql = "insert into titolari_process (process_data_id,codice_fiscale,partita_iva,nome,cognome,denominazione,persona_fisica,xml_info) values(?,?,?,?,?,?,?,?)";
	    ps = c.prepareStatement(sql);
	    ps.setString(1, identificatoreProcedimento);
	    ps.setString(2, personaFisica.getCodiceFiscale());
	    ps.setNull(3, java.sql.Types.VARCHAR);
	    ps.setString(4, personaFisica.getNome());
	    ps.setString(5, personaFisica.getCognome());
	    ps.setNull(6, java.sql.Types.VARCHAR);
	    ps.setString(7, "S");
	    ps.setString(8, personaFisica.marshall());
	    ps.execute();
	} catch (Exception e) {
	    cat.error("setAltroTitolarePersonaFisica - ExtendSaveDAO ");
	    cat.error(e);
	    throw e;
	} finally {
	    try {
		rs.close();
	    } catch (Exception e) {
	    }
	    try {
		ps.close();
	    } catch (Exception e) {
	    }
	}
    }

    public void deleteExtedData(Collection processes) {
	ResultSet rs = null;
	PreparedStatement ps = null;
	try {
	    open();
	    conn.setAutoCommit(false);
	    for (Iterator iterator = processes.iterator(); iterator.hasNext();) {
		PplPersistentData process = (PplPersistentData) iterator.next();
		String processDataID = process.getProcessDataID();
		String sql = "delete from detail_process where process_data_id=?";
		ps = conn.prepareStatement(sql);
		ps.setString(1, processDataID);
		ps.execute();
		sql = "delete from titolari_process where process_data_id=?";
		ps = conn.prepareStatement(sql);
		ps.setString(1, processDataID);
		ps.execute();
		sql = "delete from eventi_process where process_data_id=?";
		ps = conn.prepareStatement(sql);
		ps.setString(1, processDataID);
		ps.execute();
	    }
	    conn.commit();
	    conn.setAutoCommit(true);
	} catch (Exception ex) {
	    try {
		rs.close();
	    } catch (Exception e) {
	    }
	    try {
		ps.close();
	    } catch (Exception e) {
	    }
	    try {
		conn.close();
	    } catch (Exception e) {
	    }
	} finally {
	    try {
		rs.close();
	    } catch (Exception e) {
	    }
	    try {
		ps.close();
	    } catch (Exception e) {
	    }
	    try {
		conn.close();
	    } catch (Exception e) {
	    }
	}

    }

}
