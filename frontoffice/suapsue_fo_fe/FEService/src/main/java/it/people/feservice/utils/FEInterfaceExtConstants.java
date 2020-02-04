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
package it.people.feservice.utils;

/**
 * @author Riccardo Foraf� - Engineering Ingegneria Informatica S.p.A. - Sede di Genova
 *
 */
public interface FEInterfaceExtConstants {

	public static final String WINDOWS_PATH_SEPARATOR = "\\";
	
	public static final String NIX_PATH_SEPARATOR = "/";
		
	public static final String COMMUNI_QUERY = "SELECT oid AS communeId, NAME AS communeName, label AS communeLabel, provincia AS communeProv, aooprefix AS communeAOOPrefix FROM commune";

	public static final String COMMUNE_REGISTERED_SERVICES = "SELECT id AS id, nome AS serviceName, attivita AS activity, sottoattivita AS subActivity FROM service WHERE communeid = ?";

	public static final String COMMUNE_QUERY_BY_OID = "SELECT NAME AS communeName, label AS communeLabel, provincia AS communeProv, aooprefix AS communeAOOPrefix FROM commune WHERE oid = ?";

	public static final String GET_BUNDLE_ID = "select id from bundles where bundle = ? and _locale #localevalue# and nodeId #nodeidvalue#";
			
	public static final String SEARCH_BUNDLE_KEY = "select count(id) from bundles_properties where bundleRef = ? and _key = ?";
	
	public static final String INSERT_BUNDLE_PROPERTY = "insert into bundles_properties(bundleRef, _key, _value, active, _group) values(?, ?, ?, ?, ?)";

	public static final String UPDATE_BUNDLE_PROPERTY = "update bundles_properties set _value = ?, active = ?, _group = ? where bundleRef = ? and _key = ?";

	public static final String REGISTER_BUNDLE = "insert into bundles(bundle, nodeId, _locale, active, _group) values(?, ?, ?, ?, ?)";

	public static final String DELETE_BUNDLE = "delete from bundles where bundle = ? and _locale #localevalue# and nodeId #nodeidvalue#";

	public static final String DELETE_BUNDLE_PROPERTIES = "delete from bundles_properties where bundleRef = ?";
	
	public static final String DELETE_BE_REFERENCE_BY_PACKAGE = "delete from reference where serviceid = ? and value = ?";

	public static final String SEARCH_SERVICE_ID = "select id from service where communeid = ? and package = ? and attivita = ? and sottoattivita = ? and process = ?";

	public static final String SEARCH_SERVICE_ID_FOR_PKG_AND_COMMUNE = "select id from service where communeid = ? and package = ?";
	
	public static final String SEARCH_SERVICE_MESSAGE_BUNDLE_REF_BY_NODE_ID_LOCALE = "select bundles.id from bundles where bundles.bundle = ? and bundles.nodeId ! and bundles._locale $";

	//TABLEVALUES QUERIES
	public static final String INSERT_TABLEVALUE_PROPERTY = "insert into tablevalues_properties (value, tablevalueRef) values( ?, ?)";

	public static final String UPDATE_TABLEVALUE_PROPERTY = "update tablevalues_properties set value = ?, tablevalueRef = ? where value = ? and tablevalueRef = ?";
	
	public static final String DELETE_TABLEVALUE_PROPERTIES = "delete from tablevalues_properties where value = ? and tablevalueRef = ?";
	
	public static final String CHECK_TABLEVALUE_PROPERTY_ID = "select * from tablevalues_properties where value = ? and tablevalueRef = ?";
	
	
	//PEOPLE ADMINISTRATORS QUERIES
	public static final String CHECK_PEOPLE_ADMINISTRATOR = "select count(oid) from user_profile where user_id = ?";

	public static final String GET_PEOPLE_ADMINISTRATOR_OID = "select oid from user_profile where user_id = ?";

	public static final String GET_PEOPLE_ADMINISTRATOR_DATA = "select * from user_profile where user_id = ?";

	public static final String GET_PEOPLE_ADMINISTRATOR_ALLOWED_COMMUNE = "select commune_id from amministratore_commune where user_ref = ?";
	
	public static final String INSERT_PARTIAL_USER_PROFILE = "insert into user_profile(user_id, receiver_type_flags) values(?, ?)";

	public static final String INSERT_COMPLETE_USER_PROFILE = "insert into user_profile(user_id, e_mail, user_name, receiver_type_flags) values(?, ?, ?, ?)";

	public static final String INSERT_AMMINISTRATORE_COMMUNE = "insert into amministratore_commune(user_ref, commune_id) values(?, ?)";
	
	public static final String UPDATE_USER_PROFILE = "update user_profile set e_mail = ?, user_name = ?, receiver_type_flags = ? where user_id = ? ";
	
	public static final String DELETE_USER_PROFILE = "delete from user_profile where user_id = ?";

	public static final String DELETE_AMMINISTRATORE_COMMUNE = "delete from amministratore_commune where user_ref = ?";
	
	//VELOCITY TEMPLATES QUERIES
	public static final String CHECK_VELOCITY_TEMPLATE_EXISTS = "SELECT COUNT(*) FROM velocity_templates WHERE _communeId %s AND _serviceId %s AND _key = ?";
	
	
	
	public static final String GET_VELOCITY_TEMPLATES_DATA_FOR_SERVICE = 
			"SELECT vt.*, se.package FROM velocity_templates vt " +
			"LEFT JOIN service se ON vt._serviceId = se.id " +
			"WHERE (_communeId = ?) AND (se.package = ?) " +
			"UNION " +
			"SELECT vt2.*, se2.package FROM velocity_templates vt2 " +
			"LEFT JOIN service se2 ON vt2._serviceId = se2.id " +
			"WHERE (vt2._communeId = ? AND vt2._serviceId IS NULL) AND vt2._key " +
			"NOT IN (SELECT _key FROM velocity_templates vt2 " +
			"LEFT JOIN service se2 ON vt2._serviceId = se2.id  " +
			"WHERE (_communeId = ?) AND (se2.package = ?)) " +
			"UNION " +
			"SELECT vt2.*, se2.package FROM velocity_templates vt2 " +
			"LEFT JOIN service se2 ON vt2._serviceId = se2.id " +
			"WHERE (vt2._communeId IS NULL AND vt2._serviceId IS NULL) AND vt2._key " +
			"NOT IN (SELECT _key FROM velocity_templates vt2 " +
			"LEFT JOIN service se2 ON vt2._serviceId = se2.id " +
			"WHERE ((_communeId = ?) AND (se2.package = ?)) " +
			"OR (vt2._communeId = ? AND vt2._serviceId IS NULL))";


	public static final String GET_VELOCITY_TEMPLATES_DATA_FOR_SERVICE_OLD = 
			"SELECT vt.*, se.package FROM velocity_templates vt " +
			"LEFT JOIN service se ON vt._serviceId = se.id " +
			"WHERE (_communeId = ?) AND (se.package = ?) " +
			"UNION " +
			"SELECT vt2.*, se2.package FROM velocity_templates vt2 " +
			"LEFT JOIN service se2 ON vt2._serviceId = se2.id " +
			"WHERE (vt2._communeId IS NULL AND vt2._serviceId IS NULL) " +
			"AND vt2._key NOT IN (SELECT _key FROM velocity_templates vt2 " +
		    "LEFT JOIN service se2 ON vt2._serviceId = se2.id  " +
			"WHERE (_communeId = ?) AND (se2.package = ?))";
 
	public static final String GET_VELOCITY_TEMPLATES_DATA_FOR_NODE = 
			"SELECT vt.*, se.package FROM velocity_templates vt " +
			"LEFT JOIN service se ON vt._serviceId = se.id " +
			"WHERE (_communeId = ?) AND (se.package IS NULL) " +
			"UNION " +
			"SELECT vt2.*, se2.package FROM velocity_templates vt2 " +
			"LEFT JOIN service se2 ON vt2._serviceId = se2.id " +
			"WHERE (vt2._communeId IS NULL AND vt2._serviceId IS NULL) " +
			"AND vt2._key NOT IN (SELECT _key FROM velocity_templates vt2 " +
		    "LEFT JOIN service se2 ON vt2._serviceId = se2.id  " +
			"WHERE (_communeId = ?) AND (se2.package IS NULL))";
	
	public static final String GET_VELOCITY_TEMPLATES_DATA_GENERIC = "SELECT vt.* FROM velocity_templates vt " +
			"WHERE (vt._communeId IS NULL) AND (vt._serviceId IS NULL)";

	public static final String DELETE_VELOCITY_TEMPLATES_DATA = "DELETE FROM velocity_templates " +
			"WHERE _communeId %s AND _serviceId %s AND _key = ?";
	
	public static final String UPDATE_VELOCITY_TEMPLATES_DATA = "UPDATE velocity_templates " +
			"SET _value = ?, _description = ? WHERE _communeId %s AND _serviceId %s AND _key = ?";
	
	public static final String INSERT_VELOCITY_TEMPLATES_DATA = "INSERT INTO velocity_templates " +
			"(_communeId, _serviceId , _key, _value, _description) VALUES (?,?,?,?,?)";
	
}
