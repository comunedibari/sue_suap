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
/*
 * Creato il 31-ago-2006 da Cedaf s.r.l.
 *
 */
package it.people.peopleservice.dal;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * @author Michele Fabbri - Cedaf s.r.l.
 *
 */
public class ServiceSoapDao {
    Connection connection = null;

    /**
     * 
     */
    public ServiceSoapDao() {
    }

    public void open() throws ServiceSoapDaoException {
        try {
	        if (this.connection == null || connection.isClosed())
	            this.connection = getConnection(getEviromentContext());
        } catch(SQLException sqlEx) {
            throw new ServiceSoapDaoException(sqlEx);
        } catch(NamingException namEx) {
            throw new ServiceSoapDaoException(namEx);            
        }
    }
    
    public void close() throws ServiceSoapDaoException {
        try {
	        if (this.connection != null && !this.connection.isClosed()) 
	            this.connection.close();
        } catch(SQLException sqlEx) {
            throw new ServiceSoapDaoException(sqlEx);
        }
    }

    public BENode getBENode(String nodobe, String communeKey) 
    throws ServiceSoapDaoException {
        
    	//Modifica associazione be a nodi
//        String queryBackend  = "SELECT * FROM benode WHERE nodobe='" 
//            + nodobe + "'";
        int communeId = getFENode(communeKey).getId();
        if (communeId == -1)
            throw new ServiceSoapDaoException("getReference() - Codice Ente '" + communeKey + "' non valido");
        
        String queryBackend  = "SELECT * FROM benode WHERE nodobe='" 
            + nodobe + "' and nodeid=" + communeId;

        Statement stat = null;
        ResultSet rs = null;
        
        try {
	        stat = connection.createStatement();
	        rs = stat.executeQuery(queryBackend);
	     
	        if (rs.next()) {
	            BENode beNode = new BENode();
	            beNode.setURL(rs.getString("reference"));
	            beNode.setNodoBE(rs.getString("nodobe"));
	            beNode.setEnvelopEnabled(rs.getInt("useenvelope") != 0);
	            beNode.setDisableCheckDelegate(rs.getInt("disablecheckdelegate") != 0);
	            return beNode;
	        } else {
	            throw new ServiceSoapDaoException("Impossibile trovare un nodo di back-end corrispondente al nome = '" + nodobe + "'");
	        }
        } catch(SQLException sqlEx) {
            throw new ServiceSoapDaoException(sqlEx);            
        } finally {
            if (rs != null)
                try {rs.close();} catch(Exception ex) {};
            if (stat != null)
                try {stat.close();} catch(Exception ex) {};
        }        
    }
    
    /**
     * Ritorna l'oggetto Reference
     * @param communeKey codice istat del comune
     * @param _package nome completo del servizio
     * @param beService nome logico del back-end utilizzato dal servizio
     * @return Reference del nodo di back-end
     * @throws ServiceSoapDaoException
     */
    public Reference getReference(String communeKey, String _package, String beService) 
    throws ServiceSoapDaoException {
        Statement stat = null;
        ResultSet rs = null;
        
        try {
            int communeId = getFENode(communeKey).getId();
            if (communeId == -1)
                throw new ServiceSoapDaoException("getReference() - Codice Ente '" + communeKey + "' non valido");
                        
	        // recupera la chiave del servizio a partire da comune e package
	        // la ricerca deve essere fatta in funzione del nome del servizio 
            // e del nodo di appartenenza.
	        String queryServizi = "SELECT * " 
	            + " FROM service "
	            + " WHERE package = '"+  _package + "'"
	            + " AND nodeid = " + communeId;            
	        
	        stat = this.connection.createStatement();
	        rs = stat.executeQuery(queryServizi);
	        int idServizio = -1;
	        
	        if (rs.next()) {
	            idServizio = rs.getInt("id");
	        } else {
	            String errorMessage = "getReference() - impossibile trovare il servizio"
	                + " con package = '" + _package + "' "
	                + " e codice ente = '" + communeKey + "'";
	            throw new ServiceSoapDaoException(errorMessage);
	        }
	        
	        rs.close();
	        stat.close();
	        
	        String queryReference = "SELECT * FROM reference WHERE serviceid='"
	                + idServizio + "' AND name='" + beService + "'";
	        
	        stat = connection.createStatement();
	        rs = stat.executeQuery(queryReference);
	        
	        Reference reference = null;
	        if (rs.next()) {
	            reference = new Reference();
	            reference.setName(rs.getString("name"));
	            reference.setValue(rs.getString("value"));
	            reference.setDump(rs.getInt("dump") == 0 ? false : true);
	        } else {
	            String errorMessage = "getReference() - impossibile trovare il riferimento"
	                + " per il servizio = '" + _package + "' "
	                + " e il back-end = '" + beService + "'";
	            throw new ServiceSoapDaoException(errorMessage);
	        }
	        
	        return reference;
	        
        } catch(SQLException sqlEx) {
            throw new ServiceSoapDaoException(sqlEx);            
        } finally {
            if (rs != null)
                try {rs.close();} catch(Exception ex) {};
            if (stat != null)
                try {stat.close();} catch(Exception ex) {};
        }
        
    }
    
    public FENode getFENode(String communeKey) 
    throws ServiceSoapDaoException {
        String queryNode = "SELECT * FROM fenode " 
            + " WHERE codicecomune = '" + communeKey + "'";

        Statement stat = null;
        ResultSet rs = null;
        
        try {
	        stat = connection.createStatement();
	        rs = stat.executeQuery(queryNode);
	        
	        if (rs.next()) {
		        FENode feNode = new FENode();
	            feNode.setId(rs.getInt("id"));
	            feNode.setCommune(rs.getString("comune"));
	            feNode.setCommuneCode(rs.getString("codicecomune"));
	            feNode.setFeserviceURL(rs.getString("nodofe"));
	            feNode.setCheckDelegate(rs.getInt("controllodelegheattivo")!=0);
	            feNode.setCheckDelegateURL(rs.getString("controllodelegheurl"));
	            return feNode;
	        } else {
	            String errorMessage = "getFENode() - impossibile trovare l'FENode"
	                + " per il comune con codice = '" + communeKey + "' ";
	            throw new ServiceSoapDaoException(errorMessage);	            
	        }
	        
        } catch(SQLException sqlEx) {
            throw new ServiceSoapDaoException(sqlEx);            
        } finally {
            if (rs != null)
                try {rs.close();} catch(Exception ex) {};
            if (stat != null)
                try {stat.close();} catch(Exception ex) {};
        }
    }

    protected Context getEviromentContext() 
    throws NamingException {
        Context initContext = new InitialContext();
        return (Context)initContext.lookup("java:comp/env");        
    }

    protected Connection getConnection(Context eviromentContext) 
    throws NamingException, SQLException {
        DataSource ds = (DataSource) eviromentContext.lookup("jdbc/PeopleDB");            
        return ds.getConnection();        
    }
}
