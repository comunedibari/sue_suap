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
package it.people.console.web.controllers.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.sql.DataSource;

import org.springframework.validation.ObjectError;

import it.people.console.domain.FENodeDeployedServicesRegistration;
import it.people.console.domain.FEServiceRegistration;
import it.people.console.domain.NodeDeployedServicesResult;
import it.people.console.utils.StringUtils;
import it.people.console.web.servlet.mvc.AbstractController;
import it.people.feservice.FEInterface;
import it.people.feservice.beans.ConfigParameter;
import it.people.feservice.beans.DependentModule;
import it.people.feservice.beans.ServiceVO;
import it.people.feservice.client.FEInterfaceServiceLocator;
import it.people.feservice.utils.Base64;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 31/gen/2011 22.52.48
 *
 */
public class FeServiceRegister extends AbstractController {

	public FeServiceRegister() {
	}
	
	public Vector<ObjectError> registerService(DataSource peopleDb, FEServiceRegistration feServiceRegistration) {
		return  _registerService(peopleDb, feServiceRegistration.getFeServicePackage(), 
				feServiceRegistration.getSelectedFeNode(), null, null, null, null, null);
	}

	public List<NodeDeployedServicesResult> registerNodeDeployedServices(DataSource peopleDb, FENodeDeployedServicesRegistration 
			feNodeDeployedServicesRegistration) {
		
		List<NodeDeployedServicesResult> result = new ArrayList<NodeDeployedServicesResult>();
		
		for(String _package : feNodeDeployedServicesRegistration.getSelectedServicesPackages()) {
			result.add(new NodeDeployedServicesResult(_package, 
					_registerService(peopleDb, _package, 
							String.valueOf(feNodeDeployedServicesRegistration.getSelectedNodeId()),
							feNodeDeployedServicesRegistration.getBackEndURL(), 
							feNodeDeployedServicesRegistration.getLogicalNamesPrefix(),
							feNodeDeployedServicesRegistration.getLogicalNamesSuffix(),
							feNodeDeployedServicesRegistration.getServicesPrefix(),
							feNodeDeployedServicesRegistration.getServicesSuffix())));
		}
		
		return result;
		
	}
	
	private Vector<ObjectError> _registerService(DataSource peopleDb, String _package, String feNodeId, String backEndURL, String logicalNamesPrefix, 
			String logicalNamesSuffix, String servicesPrefix, String servicesSuffix) {

		Vector<ObjectError> result = new Vector<ObjectError>();
		
        Connection connection = null;
        Statement stmt = null;
        boolean alreadyRegistered = false;
        boolean unavailableService = false;
        
        try {

        	String thePackage = _package;
            String codiceComune = "";
            String reference = "";        
            int serviceId = 0 ;
        	
            connection = peopleDb.getConnection();

            // query per controllo che non vi siano package uguali per lo stesso comune
            String checkQuery = "SELECT * FROM service WHERE package = '" 
            	+ _package + "' AND nodeid = " 
            	+ feNodeId;
            stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery(checkQuery);
            if(res.next()) {
            	alreadyRegistered = true;
            	ObjectError error = new ObjectError("error", 
            			new String[] {"error.feservices.listAndAdd.service.exists"}, 
            			new String[] {_package}, "Errore nella registrazione del servizio.");
            	result.add(error);
            }
            res.close();
            
            if (!alreadyRegistered) {

                // trovo il reference del web service
                String query = "SELECT reference, codicecomune FROM fenode WHERE id= "+ feNodeId;
                stmt = connection.createStatement();
                res = stmt.executeQuery(query);
                if(res.next()) {
                    reference = res.getString("reference");
                    codiceComune = res.getString("codicecomune");
                }
                res.close();

                ServiceVO theService = null;
                try {
                	FEInterfaceServiceLocator locator = new FEInterfaceServiceLocator();
                	FEInterface ss = locator.getFEInterface(new URL(reference));
                    theService = ss.registerService(codiceComune, thePackage);
                    if(theService == null ) {
                    	ObjectError error = new ObjectError("error", 
                    			new String[] {"error.feservices.listAndAdd.service.notAvailable"}, 
                    			new String[] {_package}, "Errore nella registrazione del servizio.");
                    	result.add(error);
                    	unavailableService = true;
                    }
                } catch (Exception e) {
                	ObjectError error = new ObjectError("error", 
                			new String[] {"error.feservices.listAndAdd.service.notAvailable"}, 
                			new String[] {_package}, "Errore nella registrazione del servizio.");
                	result.add(error);
                	unavailableService = true;
                }
                
                if (!unavailableService) {

                    // inserisco i campi del ServiceVo ritornato dalla registrazione del servizio
                    String query2 = "INSERT INTO service (nome,package,loglevel,stato,nodeid,attivita,sottoattivita,process) VALUES (?,?,?,?,?,?,?,?)";
                    PreparedStatement ps = connection.prepareStatement(query2);
                    ps.setString(1, theService.getNome());  //
                    ps.setString(2, theService.getServicePackage());  // package da inserire ??
                    ps.setInt(3, theService.getLogLevel());
                    ps.setInt(4, theService.getStato());
                    ps.setInt(5, Integer.parseInt(feNodeId));
                    ps.setString(6, theService.getAttivita());
                    ps.setString(7, theService.getSottoattivita());
                    ps.setString(8, theService.getProcess());
                    ps.execute();
                    ps.close();
                    
                    String query3 = "SELECT id FROM service WHERE package = '"+ thePackage +"' AND nodeid ='" 
                    + feNodeId +"'";
                    stmt = connection.createStatement();
                    res = stmt.executeQuery(query3);
                    if(res.next()) {
                        serviceId = res.getInt("id");
                    }
                    res.close();
                    
                    DependentModule[] dep = theService.getDependentModules();
                    ConfigParameter[] con = theService.getConfigParameters();
                    
                    if(dep != null) {
                        String query4 = "INSERT INTO reference (serviceid, name, value, address) VALUES (?,?,?,?)";
                        for(int i = 0 ; i < dep.length; i++) {
                        	if (!StringUtils.isEmptyString(dep[i].getValore())) { //&& !dep[i].getValore().equalsIgnoreCase("UNDEFINED")) {
                                ps = connection.prepareStatement(query4);
                                ps.setInt(1, serviceId);
                                ps.setString(2, dep[i].getName());
                                ps.setString(3, dep[i].getValore());
                                ps.setString(4, dep[i].getMailAddress());
                                ps.execute();
                                ps.close();
                                
    							if (!StringUtils.isEmptyString(backEndURL)) {
    								if (!backEndURL.endsWith("/")) {
    									backEndURL = backEndURL.concat("/");
    								}
    								
    								String logicalName = dep[i].getValore();
    								
    								String beLogicalName = logicalName;
    								
    								if (!StringUtils.isEmptyString(logicalNamesPrefix)) {
    									beLogicalName = logicalNamesPrefix + beLogicalName;
    								}
    								if (!StringUtils.isEmptyString(logicalNamesSuffix)) {
    									beLogicalName += logicalNamesSuffix;
    								}
    								
    								String beUrlServiceName = logicalName;
    								if (!StringUtils.isEmptyString(servicesPrefix)) {
    									beUrlServiceName = servicesPrefix + beUrlServiceName;
    								}
    								if (!StringUtils.isEmptyString(servicesSuffix)) {
    									beUrlServiceName += servicesSuffix;
    								}
    								String queryExistsBE = "SELECT COUNT(*) FROM benode WHERE nodeid = ? AND nodobe = ? ";

    								ps = connection.prepareStatement(queryExistsBE);
    								ps.setString(1, feNodeId);
    								ps.setString(2, beLogicalName);

    								ResultSet resultSet = ps.executeQuery();
    								resultSet.next();
    								int count = resultSet.getInt(1);
    								if (count == 0) {

    									String queryBE = "INSERT INTO benode (nodeid, nodobe, reference) VALUES (?,?,?)";
    									ps = connection.prepareStatement(queryBE);
    									ps.setString(1, feNodeId);
    									ps.setString(2, beLogicalName);
    									ps.setString(3, backEndURL.concat(beUrlServiceName));
    									ps.execute();
    									ps.close();
    								}
    							}
                        	}
                        }
                    }
                    
                    if (con != null) {
                        String query5 = "INSERT INTO configuration (serviceid,name,value) VALUES (?,?,?)";
                        for(int i = 0 ; i < con.length; i++) {
                            ps = connection.prepareStatement(query5);
                            ps.setInt(1, serviceId);
                            ps.setString(2, con[i].getParameterName());
                            ps.setString(3, con[i].getParameterValue());
                            ps.execute();
                            ps.close();
                        }
                    }          
                	
                }
                            	
            }
            
        } catch (SQLException e) {
        	ObjectError error = new ObjectError("error", 
        			new String[] {"error.feservices.listAndAdd.service.dbError"}, 
        			new String[] {_package}, "Errore nella registrazione del servizio.");
        	result.add(error);
        } finally {
        	if (connection != null)
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
        }
		
        return result;
        
	}
	
	private String getDecodedPackage(String _package) throws IOException {
		
		String result = "";
		
		Base64 base64 = new Base64();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		baos.write(base64.decode(_package));
		baos.flush();
		result = baos.toString();
		baos.close();
		
		return result;
		
	}
	
}
