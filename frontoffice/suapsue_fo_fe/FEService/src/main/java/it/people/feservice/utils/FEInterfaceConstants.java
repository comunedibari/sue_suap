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
 * 
 * @author Andrea Piemontese - Engineering Ingegneria Informatica S.p.A.
 * @created 02/ago/2012
 */
public interface FEInterfaceConstants {

	public static final int LIMIT_MAX_UPPERBOUND = Integer.MAX_VALUE;
	
	  public static final String COUNT_TOTAL_PAGED_INDICATORS_QUERY = "SELECT COUNT(*) totalCount FROM ( SELECT ente, attivita, procedimento, procedimento_id, CAST(SUM(NTC) AS CHAR(6)) AS NTC, CAST(SUM(NTR) AS CHAR(6)) AS NTR, CAST(SUM(NAD) AS CHAR(6)) AS NAD  FROM (   SELECT sp.oid, sp.people_protocol_id, sp.commune_id AS ente,    DATE(sp.submitted_time) submit_date, TIME(sp.submitted_time) submit_time, sp.delegate,    pp.process_name AS procedimento_id, se.attivita, pp.content_name AS procedimento, 1 AS NTC,    IF (((TIME(sp.submitted_time) NOT BETWEEN CAST(? AS TIME) AND CAST(? AS TIME))) OR (DAYOFWEEK(DATE(sp.submitted_time)) = 1), 1 , 0) AS NTR,    IFNULL(info.information_value, '0') AS NAD   FROM submitted_process sp    JOIN commune c ON c.oid =  sp.commune_id    JOIN pending_process pp ON sp.editable_process_id = pp.oid    LEFT JOIN submitted_process_info info ON ((sp.oid = info.sbmt_process_id) AND (info.information_key = 'TOTAL_ATTACHMENTS'))    LEFT JOIN fedb.service se ON se.package = pp.process_name AND pp.commune_id = se.communeid   WHERE pp.process_name IS NOT NULL AND se.attivita IS NOT NULL    AND NOT pp.PROCESS_NAME = 'it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico'    AND ( (?) OR (sp.commune_id IN (%s)))    AND ( (?) OR (se.attivita IN (%s)))    AND (NOT se.package = 'it.people.fsl.servizi.admin.sirac.accreditamento' AND NOT se.package = 'it.people.fsl.servizi.praticheOnLine.visura.myPage')    AND DATE(sp.submitted_time) BETWEEN CAST(? AS DATE) AND CAST(? AS DATE)    AND 0 = (SELECT COUNT(*)        FROM not_submittable_process nsp       WHERE nsp.content_id = sp.people_protocol_id      )   UNION   SELECT sp.oid, sp.people_protocol_id, IF(dp.istat_ente = '', '******', dp.istat_ente) AS ente,    DATE(sp.submitted_time) submit_date, TIME(sp.submitted_time) submit_time, sp.delegate,    pp.process_name AS procedimento_id, se.attivita, pp.content_name AS procedimento, 1 AS NTC,    IF (((TIME(sp.submitted_time) NOT BETWEEN CAST(? AS TIME) AND CAST(? AS TIME))) OR (DAYOFWEEK(DATE(sp.submitted_time)) = 1), 1 , 0) AS NTR,    IFNULL(info.information_value, '0') AS NAD   FROM submitted_process sp    JOIN commune c ON c.oid =  sp.commune_id    JOIN pending_process pp ON sp.editable_process_id = pp.oid    JOIN detail_process dp ON dp.process_data_id = pp.PROCESS_DATA_ID    LEFT JOIN submitted_process_info info ON ((sp.oid = info.sbmt_process_id) AND (info.information_key = 'TOTAL_ATTACHMENTS'))    LEFT JOIN fedb.service se ON se.package = pp.process_name AND pp.commune_id = se.communeid   WHERE pp.process_name IS NOT NULL AND se.attivita IS NOT NULL    AND pp.PROCESS_NAME = 'it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico'    AND ( (?) OR (sp.commune_id IN (%s)))    AND ( (?) OR (se.attivita IN (%s)))    AND DATE(sp.submitted_time) BETWEEN CAST(? AS DATE) AND CAST(? AS DATE)    AND 0 = (SELECT COUNT(*)        FROM not_submittable_process nsp       WHERE nsp.content_id = sp.people_protocol_id      )   UNION   SELECT nsp.oid, nsp.content_id, nsp.commune_id, DATE(nsp.creation_time), TIME(nsp.creation_time), nsp.delegate,    nsp.process_name AS procedimento_id, se.attivita, nsp.content_name, 1 AS NTC,    IF (((TIME(nsp.creation_time) NOT BETWEEN CAST(? AS TIME) AND CAST(? AS TIME))) OR (DAYOFWEEK(DATE(nsp.creation_time)) = 1), 1 , 0) AS NTR,    IF (nsp.process_name IN ('it.people.fsl.servizi.demografici.anagrafe.richiestacertificatotd','it.people.fsl.servizi.demografici.anagrafe.richiestacertificatotdsenzabollo'), 1, 0) AS NAD   FROM not_submittable_process nsp    LEFT JOIN fedb.service se ON se.package = nsp.process_name AND nsp.commune_id = se.communeid    JOIN commune c ON c.oid =  nsp.commune_id   WHERE nsp.process_name IS NOT NULL AND se.attivita IS NOT NULL    AND ( (?) OR (nsp.commune_id IN (%s)))    AND ( (?) OR (se.attivita IN (%s)))    AND (NOT se.package = 'it.people.fsl.servizi.admin.sirac.accreditamento' AND NOT se.package = 'it.people.fsl.servizi.praticheOnLine.visura.myPage')    AND DATE(nsp.creation_time) BETWEEN CAST(? AS DATE) AND CAST(? AS DATE)      ) AS inner_table GROUP BY ente, attivita, procedimento ) AS result";
	  public static final String PAGED_INDICATORS_QUERY = "SELECT ente, nome, attivita, procedimento, procedimento_id, CAST(SUM(NTC) AS CHAR(6)) AS NTC, CAST(SUM(NTR) AS CHAR(6)) AS NTR, CAST(SUM(NAD) AS CHAR(6)) AS NAD FROM (  SELECT sp.oid, sp.people_protocol_id, sp.commune_id AS ente, c.name as nome,  DATE(sp.submitted_time) submit_date, TIME(sp.submitted_time) submit_time, sp.delegate,   pp.process_name AS procedimento_id, se.attivita, pp.content_name AS procedimento, 1 AS NTC,   IF (((TIME(sp.submitted_time) NOT BETWEEN CAST(? AS TIME) AND CAST(? AS TIME))) OR (DAYOFWEEK(DATE(sp.submitted_time)) = 1), 1 , 0) AS NTR,   IFNULL(info.information_value, '0') AS NAD  FROM submitted_process sp   JOIN commune c ON c.oid =  sp.commune_id   JOIN pending_process pp ON sp.editable_process_id = pp.oid   LEFT JOIN submitted_process_info info ON ((sp.oid = info.sbmt_process_id) AND (info.information_key = 'TOTAL_ATTACHMENTS'))   LEFT JOIN fedb.service se ON se.package = pp.process_name AND pp.commune_id = se.communeid  WHERE pp.process_name IS NOT NULL AND se.attivita IS NOT NULL   AND NOT pp.PROCESS_NAME = 'it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico'   AND ( (?) OR (sp.commune_id IN (%s)))   AND ( (?) OR (se.attivita IN (%s)))   AND (NOT se.package = 'it.people.fsl.servizi.admin.sirac.accreditamento' AND NOT se.package = 'it.people.fsl.servizi.praticheOnLine.visura.myPage')   AND DATE(sp.submitted_time) BETWEEN CAST(? AS DATE) AND CAST(? AS DATE)   AND 0 = (SELECT COUNT(*)       FROM not_submittable_process nsp      WHERE nsp.content_id = sp.people_protocol_id     )  UNION  SELECT sp.oid, sp.people_protocol_id, IF(dp.istat_ente = '', '******', dp.istat_ente) AS ente, IF(dp.des_ente = '', '******', dp.des_ente) AS nome,   DATE(sp.submitted_time) submit_date, TIME(sp.submitted_time) submit_time, sp.delegate,   pp.process_name AS procedimento_id, se.attivita, pp.content_name AS procedimento, 1 AS NTC,   IF (((TIME(sp.submitted_time) NOT BETWEEN CAST(? AS TIME) AND CAST(? AS TIME))) OR (DAYOFWEEK(DATE(sp.submitted_time)) = 1), 1 , 0) AS NTR,   IFNULL(info.information_value, '0') AS NAD  FROM submitted_process sp   JOIN commune c ON c.oid =  sp.commune_id   JOIN pending_process pp ON sp.editable_process_id = pp.oid   JOIN detail_process dp ON dp.process_data_id = pp.PROCESS_DATA_ID   LEFT JOIN submitted_process_info info ON ((sp.oid = info.sbmt_process_id) AND (info.information_key = 'TOTAL_ATTACHMENTS'))   LEFT JOIN fedb.service se ON se.package = pp.process_name AND pp.commune_id = se.communeid  WHERE pp.process_name IS NOT NULL AND se.attivita IS NOT NULL   AND pp.PROCESS_NAME = 'it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico'   AND ( (?) OR (sp.commune_id IN (%s)))   AND ( (?) OR (se.attivita IN (%s)))   AND DATE(sp.submitted_time) BETWEEN CAST(? AS DATE) AND CAST(? AS DATE)   AND 0 = (SELECT COUNT(*)       FROM not_submittable_process nsp      WHERE nsp.content_id = sp.people_protocol_id     )  UNION  SELECT nsp.oid, nsp.content_id, nsp.commune_id, c.name as nome, DATE(nsp.creation_time), TIME(nsp.creation_time), nsp.delegate,   nsp.process_name AS procedimento_id, se.attivita, nsp.content_name, 1 AS NTC,   IF (((TIME(nsp.creation_time) NOT BETWEEN CAST(? AS TIME) AND CAST(? AS TIME))) OR (DAYOFWEEK(DATE(nsp.creation_time)) = 1), 1 , 0) AS NTR,   IF (nsp.process_name IN ('it.people.fsl.servizi.demografici.anagrafe.richiestacertificatotd','it.people.fsl.servizi.demografici.anagrafe.richiestacertificatotdsenzabollo'), 1, 0) AS NAD  FROM not_submittable_process nsp   LEFT JOIN fedb.service se ON se.package = nsp.process_name AND nsp.commune_id = se.communeid   JOIN commune c ON c.oid =  nsp.commune_id  WHERE nsp.process_name IS NOT NULL AND se.attivita IS NOT NULL   AND ( (?) OR (nsp.commune_id IN (%s)))   AND ( (?) OR (se.attivita IN (%s)))   AND (NOT se.package = 'it.people.fsl.servizi.admin.sirac.accreditamento' AND NOT se.package = 'it.people.fsl.servizi.praticheOnLine.visura.myPage')   AND DATE(nsp.creation_time) BETWEEN CAST(? AS DATE) AND CAST(? AS DATE)    ) AS inner_table GROUP BY ente, attivita, procedimento LIMIT ?, ?";
	  public static final String INDICATORS_TO_SEND_QUERY = "SELECT ente, submit_date, attivita, IF(delegate = 0, 'N', 'S') AS delegate, procedimento, procedimento_id,  LPAD(CAST(SUM(NTC) AS CHAR(6)), 6, '0') AS NTC, LPAD(CAST(SUM(NTR) AS CHAR(6)), 6, '0') AS NTR, LPAD(CAST(SUM(NAD) AS CHAR(6)), 6, '0') AS NAD  FROM (  SELECT sp.oid, sp.people_protocol_id, sp.commune_id AS ente, DATE(sp.submitted_time) submit_date, TIME(sp.submitted_time) submit_time, sp.delegate AS delegate,   RPAD(SUBSTRING(pp.process_name, 1, 128),128,' ') AS procedimento_id, RPAD(SUBSTRING(se.attivita, 1, 32), 32, ' ') AS attivita,   RPAD(SUBSTRING(pp.content_name, 1, 128),128,' ') AS procedimento, 1 AS NTC,   IF (((TIME(sp.submitted_time) NOT BETWEEN CAST(? AS TIME) AND CAST(? AS TIME))) OR (DAYOFWEEK(DATE(sp.submitted_time)) = 1), 1 , 0) AS NTR,   IFNULL(info.information_value, '0') AS NAD  FROM submitted_process sp   JOIN commune c ON c.oid =  sp.commune_id   JOIN pending_process pp ON sp.editable_process_id = pp.oid   LEFT JOIN submitted_process_info info ON ((sp.oid = info.sbmt_process_id) AND (info.information_key = 'TOTAL_ATTACHMENTS'))   LEFT JOIN fedb.service se ON se.package = pp.process_name AND pp.commune_id = se.communeid  WHERE pp.process_name IS NOT NULL AND se.attivita IS NOT NULL   AND NOT pp.PROCESS_NAME = 'it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico'   AND ( (?) OR (sp.commune_id IN (%s)))   AND ( (?) OR (se.attivita IN (%s)))   AND DATE(sp.submitted_time) BETWEEN CAST(? AS DATE) AND CAST(? AS DATE)   AND 0 = (SELECT COUNT(*)      FROM not_submittable_process nsp      WHERE nsp.content_id = sp.people_protocol_id     ) UNION  SELECT sp.oid, sp.people_protocol_id, IF(dp.istat_ente = '', '******', dp.istat_ente) AS ente,   DATE(sp.submitted_time) submit_date, TIME(sp.submitted_time) submit_time, sp.delegate,   RPAD(SUBSTRING(pp.process_name, 1, 128),128,' ') AS procedimento_id, RPAD(SUBSTRING(se.attivita, 1, 32), 32, ' ') AS attivita,    RPAD(SUBSTRING(pp.content_name, 1, 128),128,' ') AS procedimento, 1 AS NTC,   IF (((TIME(sp.submitted_time) NOT BETWEEN CAST(? AS TIME) AND CAST(? AS TIME))) OR (DAYOFWEEK(DATE(sp.submitted_time)) = 1), 1 , 0) AS NTR,   IFNULL(info.information_value, '0') AS NAD  FROM submitted_process sp   JOIN commune c ON c.oid =  sp.commune_id   JOIN pending_process pp ON sp.editable_process_id = pp.oid   JOIN detail_process dp ON dp.process_data_id = pp.PROCESS_DATA_ID   LEFT JOIN submitted_process_info info ON ((sp.oid = info.sbmt_process_id) AND (info.information_key = 'TOTAL_ATTACHMENTS'))   LEFT JOIN fedb.service se ON se.package = pp.process_name AND pp.commune_id = se.communeid  WHERE pp.process_name IS NOT NULL AND se.attivita IS NOT NULL   AND pp.PROCESS_NAME = 'it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico'   AND ( (?) OR (sp.commune_id IN (%s)))   AND ( (?) OR (se.attivita IN (%s)))   AND DATE(sp.submitted_time) BETWEEN CAST(? AS DATE) AND CAST(? AS DATE)   AND 0 = (SELECT COUNT(*)       FROM not_submittable_process nsp      WHERE nsp.content_id = sp.people_protocol_id     ) UNION  SELECT nsp.oid, nsp.content_id, nsp.commune_id, DATE(nsp.creation_time), TIME(nsp.creation_time), nsp.delegate,   RPAD(SUBSTRING(nsp.process_name, 1, 128),128,' ') AS procedimento_id, RPAD(SUBSTRING(se.attivita, 1, 32), 32, ' ') AS attivita,   RPAD(SUBSTRING(nsp.content_name, 1, 128), 128, ' '), 1 AS NTC,   IF (((TIME(nsp.creation_time) NOT BETWEEN CAST(? AS TIME) AND CAST(? AS TIME))) OR (DAYOFWEEK(DATE(nsp.creation_time)) = 1), 1 , 0) AS NTR,   IF (nsp.process_name IN ('it.people.fsl.servizi.demografici.anagrafe.richiestacertificatotd','it.people.fsl.servizi.demografici.anagrafe.richiestacertificatotdsenzabollo'), 1, 0) AS NAD  FROM not_submittable_process nsp   LEFT JOIN fedb.service se ON se.package = nsp.process_name  AND nsp.commune_id = se.communeid   JOIN commune c ON c.oid =  nsp.commune_id  WHERE nsp.process_name IS NOT NULL AND se.attivita IS NOT NULL   AND ( (?) OR (nsp.commune_id IN (%s)))   AND ( (?) OR (se.attivita IN (%s)))   AND (NOT se.package = 'it.people.fsl.servizi.admin.sirac.accreditamento' AND NOT se.package = 'it.people.fsl.servizi.praticheOnLine.visura.myPage')   AND DATE(nsp.creation_time) BETWEEN CAST(? AS DATE) AND CAST(? AS DATE) ) AS inner_table GROUP BY ente, submit_date, attivita, procedimento, delegate;";

	
	/// Processes (pratiche) deletion 
	
	public static int SUBMITTED_PROCESSES_QUERY_ID = 0;
	public static final int PENDING_PROCESSES_QUERY_ID = 1;
	public static final int NOT_SUBMITTABLE_PROCESSES_QUERY_ID = 2;
	public static final int COUNT_PROCESSES_QUERY_ID = 3;
	public static final int PAGED_PROCESSES_QUERY_ID = 4;
	
	
	public static final String PROCESSES_USERS_QUERY = "SELECT user_id FROM (" +
					"SELECT DISTINCT user_id FROM pending_process " +
					"UNION SELECT DISTINCT user_id FROM submitted_process " +
					"UNION SELECT DISTINCT user_id FROM not_submittable_process" +
					") AS r ORDER BY user_id";
	
	
	public static final String PAGED_PROCESSES_QUERY = "SELECT sp.OID, sp.COMMUNE_ID, se.attivita, se.sottoattivita, se.nome AS procedimento, sp.user_id, DATE(sp.SUBMITTED_TIME) AS creation_date, pp.process_name, DATEDIFF(NOW(), DATE(sp.SUBMITTED_TIME)) AS days_old, 'submitted' process_type " +
			"FROM submitted_process sp " +
			"LEFT JOIN pending_process pp ON sp.editable_process_id = pp.oid " +
			"LEFT JOIN fedb.service se ON se.package = pp.process_name  AND sp.commune_id = se.communeid " +
			"WHERE ? " +
			"AND ((?) OR (DATE(sp.submitted_time) BETWEEN CAST(? AS DATE) AND CAST(? AS DATE))) " +
			"AND ((?) OR  (DATEDIFF(NOW(), DATE(sp.SUBMITTED_TIME)) >= ?)) " +
			"AND ((?) OR (sp.commune_id IN (%s))) " +
			"AND ((?) OR (sp.user_id IN (%s))) " +
			"UNION ALL " +
			"SELECT pp.OID, pp.COMMUNE_ID, se.attivita, se.sottoattivita, se.nome, pp.user_id, DATE(pp.CREATION_TIME),  pp.process_name, DATEDIFF(NOW(), DATE(pp.CREATION_TIME)) AS days_old, 'pending' process_type " +
			"FROM pending_process pp " +
			"LEFT JOIN fedb.service se ON se.package = pp.process_name AND pp.commune_id = se.communeid " +
			"WHERE ? " +
			"AND ((?) OR (DATE(pp.creation_time) BETWEEN CAST(? AS DATE) AND CAST(? AS DATE))) " +
			"AND ((?) OR (DATEDIFF(NOW(), DATE(pp.CREATION_TIME)) >= ?)) " + 
			"AND ((?) OR (pp.commune_id IN (%s))) " +
			"AND ((?) OR (pp.user_id IN (%s))) " +
			"UNION ALL " +
			"SELECT nsp.OID, nsp.COMMUNE_ID, se.attivita, se.sottoattivita, se.nome, nsp.user_id, DATE(nsp.CREATION_TIME), nsp.process_name, DATEDIFF(NOW(), DATE(nsp.CREATION_TIME)) AS days_old, 'not_sumbittable' process_type " +
			"FROM not_submittable_process nsp " +
			"LEFT JOIN fedb.service se ON se.package = nsp.process_name AND nsp.commune_id = se.communeid " +
			"WHERE ? " +
			"AND ((?) OR (DATE(nsp.creation_time) BETWEEN CAST(? AS DATE) AND CAST(? AS DATE))) " +
			"AND ((?) OR (DATEDIFF(NOW(), DATE(nsp.CREATION_TIME)) >= ?)) " +
			"AND ((?) OR (nsp.commune_id IN (%s))) " +
			"AND ((?) OR (nsp.user_id IN (%s))) " +
			"LIMIT ?, ?";
	
	
	public static final String COUNT_PROCESSES_QUERY = "SELECT COUNT(*) totalCount FROM (" +
			"SELECT sp.OID, sp.COMMUNE_ID, se.attivita, se.sottoattivita, se.nome AS procedimento, sp.user_id, DATE(sp.SUBMITTED_TIME) AS creation_date, pp.process_name,  DATEDIFF(NOW(), DATE(sp.SUBMITTED_TIME)) AS days_old, 'submitted' process_type " +
			"FROM submitted_process sp " +
			"LEFT JOIN pending_process pp ON sp.editable_process_id = pp.oid " +
			"LEFT JOIN fedb.service se ON se.package = pp.process_name  AND sp.commune_id = se.communeid " +
			"WHERE ? " +
			"AND ((?) OR (DATE(sp.submitted_time) BETWEEN CAST(? AS DATE) AND CAST(? AS DATE))) " +
			"AND ((?) OR  (DATEDIFF(NOW(), DATE(sp.SUBMITTED_TIME)) >= ?)) " +
			"AND ((?) OR (sp.commune_id IN (%s))) " +
			"AND ((?) OR (sp.user_id IN (%s))) " +
			"UNION ALL " +
			"SELECT pp.OID, pp.COMMUNE_ID, se.attivita, se.sottoattivita, se.nome, pp.user_id, DATE(pp.CREATION_TIME),  pp.process_name, DATEDIFF(NOW(), DATE(pp.CREATION_TIME)) AS days_old, 'pending' process_type " +
			"FROM pending_process pp " +
			"LEFT JOIN fedb.service se ON se.package = pp.process_name AND pp.commune_id = se.communeid " +
			"WHERE ? " +
			"AND ((?) OR (DATE(pp.creation_time) BETWEEN CAST(? AS DATE) AND CAST(? AS DATE))) " +
			"AND ((?) OR (DATEDIFF(NOW(), DATE(pp.CREATION_TIME)) >= ?)) " + 
			"AND ((?) OR (pp.commune_id IN (%s))) " +
			"AND ((?) OR (pp.user_id IN (%s))) " +
			"UNION ALL " +
			"SELECT nsp.OID, nsp.COMMUNE_ID, se.attivita, se.sottoattivita, se.nome, nsp.user_id, DATE(nsp.CREATION_TIME), nsp.process_name, DATEDIFF(NOW(), DATE(nsp.CREATION_TIME)) AS days_old, 'not_sumbittable' process_type " +
			"FROM not_submittable_process nsp " +
			"LEFT JOIN fedb.service se ON se.package = nsp.process_name AND nsp.commune_id = se.communeid " +
			"WHERE ? " +
			"AND ((?) OR (DATE(nsp.creation_time) BETWEEN CAST(? AS DATE) AND CAST(? AS DATE))) " +
			"AND ((?) OR (DATEDIFF(NOW(), DATE(nsp.CREATION_TIME)) >= ?)) " +
			"AND ((?) OR (nsp.commune_id IN (%s))) " +
			"AND ((?) OR (nsp.user_id IN (%s))) " +
			") AS result";
	
			
	
	/// Processes: partial query to create dump file
	
	public static final String SUBMITTED_PROCESSES_QUERY = "SELECT sp.* " +
			"FROM submitted_process sp " +
			"LEFT JOIN pending_process pp ON sp.editable_process_id = pp.oid " +
			"LEFT JOIN fedb.service se ON se.package = pp.process_name  AND sp.commune_id = se.communeid " +
			"WHERE ? " +
			"AND ((?) OR (DATE(sp.submitted_time) BETWEEN CAST(? AS DATE) AND CAST(? AS DATE))) " +
			"AND ((?) OR  (DATEDIFF(NOW(), DATE(sp.SUBMITTED_TIME)) >= ?)) " +
			"AND ((?) OR (sp.commune_id IN (%s))) " +
			"AND ((?) OR (sp.user_id IN (%s)))";
			
	public static final String PENDING_PROCESSES_QUERY = "SELECT pp.* " +
			"FROM pending_process pp " +
			"LEFT JOIN fedb.service se ON se.package = pp.process_name AND pp.commune_id = se.communeid " +
			"WHERE ? " +
			"AND ((?) OR (DATE(pp.creation_time) BETWEEN CAST(? AS DATE) AND CAST(? AS DATE))) " +
			"AND ((?) OR (DATEDIFF(NOW(), DATE(pp.CREATION_TIME)) >= ?)) " + 
			"AND ((?) OR (pp.commune_id IN (%s))) " +
			"AND ((?) OR (pp.user_id IN (%s)))";
	
	
	public static final String PENDING_PROCESSES_ACL_QUERY = "SELECT ppacl.* " +
			"FROM pending_process_acl ppacl WHERE ppacl.process_id IN " +
			"(SELECT pp.oid " +
			"FROM pending_process pp " +
			"LEFT JOIN fedb.service se ON se.package = pp.process_name AND pp.commune_id = se.communeid " +
			"WHERE ? " +
			"AND((?) OR (DATE(pp.creation_time) BETWEEN CAST(? AS DATE) AND CAST(? AS DATE))) " +
			"AND ((?) OR (DATEDIFF(NOW(), DATE(pp.CREATION_TIME)) >= ?)) " + 
			"AND ((?) OR (pp.commune_id IN (%s))) " +
			"AND ((?) OR (pp.user_id IN (%s)))" +
			")";
	
	public static final String PENDING_PROCESSES_DELEGATE_QUERY = "SELECT ppd.* " +
			"FROM pending_process_delegate ppd WHERE ppd.process_id IN " +
			"(SELECT pp.oid " +
			"FROM pending_process pp " +
			"LEFT JOIN fedb.service se ON se.package = pp.process_name AND pp.commune_id = se.communeid " +
			"WHERE ? " +
			"AND ((?) OR (DATE(pp.creation_time) BETWEEN CAST(? AS DATE) AND CAST(? AS DATE))) " +
			"AND ((?) OR (DATEDIFF(NOW(), DATE(pp.CREATION_TIME)) >= ?)) " + 
			"AND ((?) OR (pp.commune_id IN (%s))) " +
			"AND ((?) OR (pp.user_id IN (%s)))" +
			")";
	
	public static final String SUBMITTED_PROCESSES_INFO_QUERY = "SELECT spi.* " +
			"FROM submitted_process_info spi WHERE spi.sbmt_process_id IN " +
			"(SELECT sp.oid " +
			"FROM submitted_process sp " +
			"LEFT JOIN pending_process pp ON sp.editable_process_id = pp.oid " +
			"LEFT JOIN fedb.service se ON se.package = pp.process_name  AND sp.commune_id = se.communeid " +
			"WHERE ? " +
			"AND ((?) OR (DATE(sp.submitted_time) BETWEEN CAST(? AS DATE) AND CAST(? AS DATE))) " +
			"AND ((?) OR  (DATEDIFF(NOW(), DATE(sp.SUBMITTED_TIME)) >= ?)) " +
			"AND ((?) OR (sp.commune_id IN (%s))) " +
			"AND ((?) OR (sp.user_id IN (%s)))" +
			")";
			
	public static final String SUBMITTED_PROCESSES_HISTORY_QUERY = "SELECT sph.* " +
			"FROM submitted_process_history sph WHERE sph.sbmt_process_id IN " +
			"(SELECT sp.oid " +
			"FROM submitted_process sp " +
			"LEFT JOIN pending_process pp ON sp.editable_process_id = pp.oid " +
			"LEFT JOIN fedb.service se ON se.package = pp.process_name  AND sp.commune_id = se.communeid " +
			"WHERE ? " +
			"AND ((?) OR (DATE(sp.submitted_time) BETWEEN CAST(? AS DATE) AND CAST(? AS DATE))) " +
			"AND ((?) OR  (DATEDIFF(NOW(), DATE(sp.SUBMITTED_TIME)) >= ?)) " +
			"AND ((?) OR (sp.commune_id IN (%s))) " +
			"AND ((?) OR (sp.user_id IN (%s)))" +
			")";
	
	
	
	
	
	
	
	
	public static final String NOT_SUBMITTABLE_PROCESSES_QUERY = "SELECT nsp.* " +
			"FROM not_submittable_process nsp " +
			"LEFT JOIN fedb.service se ON se.package = nsp.process_name AND nsp.commune_id = se.communeid " +
			"WHERE ? " +
			"AND ((?) OR (DATE(nsp.creation_time) BETWEEN CAST(? AS DATE) AND CAST(? AS DATE))) " +
			"AND ((?) OR (DATEDIFF(NOW(), DATE(nsp.CREATION_TIME)) >= ?)) " +
			"AND ((?) OR (nsp.commune_id IN (%s))) " +
			"AND ((?) OR (nsp.user_id IN (%s)))";
	
	
	
	
	/// Processes: delete queries
	
	public static final String DELETE_SUBMITTED_PROCESSES_QUERY = "DELETE sp " +
			"FROM submitted_process sp " +
			"LEFT JOIN pending_process pp ON sp.editable_process_id = pp.oid " +
			"LEFT JOIN fedb.service se ON se.package = pp.process_name  AND sp.commune_id = se.communeid " +
			"WHERE ? " +
			"AND ((?) OR (DATE(sp.submitted_time) BETWEEN CAST(? AS DATE) AND CAST(? AS DATE))) " +
			"AND ((?) OR  (DATEDIFF(NOW(), DATE(sp.SUBMITTED_TIME)) >= ?)) " +
			"AND ((?) OR (sp.commune_id IN (%s))) " +
			"AND ((?) OR (sp.user_id IN (%s)))";
			
	public static final String DELETE_PENDING_PROCESSES_QUERY = "DELETE pp " +
			"FROM pending_process pp " +
			"LEFT JOIN fedb.service se ON se.package = pp.process_name AND pp.commune_id = se.communeid " +
			"WHERE ? " +
			"AND ((?) OR (DATE(pp.creation_time) BETWEEN CAST(? AS DATE) AND CAST(? AS DATE))) " +
			"AND ((?) OR (DATEDIFF(NOW(), DATE(pp.CREATION_TIME)) >= ?)) " + 
			"AND ((?) OR (pp.commune_id IN (%s))) " +
			"AND ((?) OR (pp.user_id IN (%s)))";
	
	public static final String DELETE_NOT_SUBMITTABLE_PROCESSES_QUERY = "DELETE nsp " +
			"FROM not_submittable_process nsp " +
			"LEFT JOIN fedb.service se ON se.package = nsp.process_name AND nsp.commune_id = se.communeid " +
			"WHERE ? " +
			"AND ((?) OR (DATE(nsp.creation_time) BETWEEN CAST(? AS DATE) AND CAST(? AS DATE))) " +
			"AND ((?) OR (DATEDIFF(NOW(), DATE(nsp.CREATION_TIME)) >= ?)) " +
			"AND ((?) OR (nsp.commune_id IN (%s))) " +
			"AND ((?) OR (nsp.user_id IN (%s)))";
	

	public static final String DELETE_PENDING_PROCESSES_DELEGATE = "DELETE ppd " +
			"FROM pending_process_delegate ppd WHERE ppd.process_id IN " +
			"(SELECT pp.oid " +
			"FROM pending_process pp " +
			"LEFT JOIN fedb.service se ON se.package = pp.process_name AND pp.commune_id = se.communeid " +
			"WHERE ? " +
			"AND ((?) OR (DATE(pp.creation_time) BETWEEN CAST(? AS DATE) AND CAST(? AS DATE))) " +
			"AND ((?) OR (DATEDIFF(NOW(), DATE(pp.CREATION_TIME)) >= ?)) " + 
			"AND ((?) OR (pp.commune_id IN (%s))) " +
			"AND ((?) OR (pp.user_id IN (%s)))" +
			")";
			
	public static final String DELETE_PENDING_PROCESSES_ACL = "DELETE ppacl " +
			"FROM pending_process_acl ppacl WHERE ppacl.process_id IN " +
			"(SELECT pp.oid " +
			"FROM pending_process pp " +
			"LEFT JOIN fedb.service se ON se.package = pp.process_name AND pp.commune_id = se.communeid " +
			"WHERE ? " +
			"AND((?) OR (DATE(pp.creation_time) BETWEEN CAST(? AS DATE) AND CAST(? AS DATE))) " +
			"AND ((?) OR (DATEDIFF(NOW(), DATE(pp.CREATION_TIME)) >= ?)) " + 
			"AND ((?) OR (pp.commune_id IN (%s))) " +
			"AND ((?) OR (pp.user_id IN (%s)))" +
			")";
	
	public static final String DELETE_SUBMITTED_PROCESSES_INFO_QUERY = "DELETE spi " +
			"FROM submitted_process_info spi WHERE spi.sbmt_process_id IN " +
			"(SELECT sp.oid " +
			"FROM submitted_process sp " +
			"LEFT JOIN pending_process pp ON sp.editable_process_id = pp.oid " +
			"LEFT JOIN fedb.service se ON se.package = pp.process_name  AND sp.commune_id = se.communeid " +
			"WHERE ? " +
			"AND ((?) OR (DATE(sp.submitted_time) BETWEEN CAST(? AS DATE) AND CAST(? AS DATE))) " +
			"AND ((?) OR  (DATEDIFF(NOW(), DATE(sp.SUBMITTED_TIME)) >= ?)) " +
			"AND ((?) OR (sp.commune_id IN (%s))) " +
			"AND ((?) OR (sp.user_id IN (%s)))" +
			")";
			
	public static final String DELETE_SUBMITTED_PROCESSES_HISTORY_QUERY = "DELETE sph " +
			"FROM submitted_process_history sph WHERE sph.sbmt_process_id IN " +
			"(SELECT sp.oid " +
			"FROM submitted_process sp " +
			"LEFT JOIN pending_process pp ON sp.editable_process_id = pp.oid " +
			"LEFT JOIN fedb.service se ON se.package = pp.process_name  AND sp.commune_id = se.communeid " +
			"WHERE ? " +
			"AND ((?) OR (DATE(sp.submitted_time) BETWEEN CAST(? AS DATE) AND CAST(? AS DATE))) " +
			"AND ((?) OR  (DATEDIFF(NOW(), DATE(sp.SUBMITTED_TIME)) >= ?)) " +
			"AND ((?) OR (sp.commune_id IN (%s))) " +
			"AND ((?) OR (sp.user_id IN (%s)))" +
			")";

}
