package cross.cross_scripts.ave_53.report;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cross.cross_scripts.MySql;
import cross.cross_scripts.ave_53.TechnicianAgentBase;
import cross.cross_scripts.ave_53.entities.PraticaExt;
import cross.cross_scripts.ave_53.entities.PraticheEventiExt;

/**
 * create a report to indicate witch paperworks have technician agent wrong
 */
public class TechnicianAgentReport extends TechnicianAgentBase{


	private static final Logger logger = LogManager.getLogger(TechnicianAgentReport.class);
	private PrintWriter pw;
	private PrintWriter pwNotFound;
	public TechnicianAgentReport(MySql openCrossMySql) {
		super(openCrossMySql);
	} 

	public void run() throws Exception {
		logger.warn("run " + this.getClass().getName());
		setModProcIds();
		int min = min();
		int max = max();
		int increment = 500;
		logger.info("min: " + min);
		logger.info("max: " + max);

		for(int i = max; i > min; i-=increment) {
			process(i-increment , i);
		}
		logger.warn("END " + this.getClass().getName());
	}

	private void process(int a, int b) throws SQLException, IOException {
		String query = SELECT_FROM_PRATICA.replace(":V_A", Integer.toString(a)).replace(":V_B", Integer.toString(b));
		pw = new PrintWriter(new BufferedWriter(new FileWriter(".\\report_tech_agent.txt", true)));
		pwNotFound = new PrintWriter(new BufferedWriter(new FileWriter(".\\report_tech_agent-NOT-FOUND.txt", true)));

		consumeSelect(query, set ->{
			PraticaExt paperwork = new PraticaExt();
			try {
				paperwork.setIdPratica(set.getInt(1));
				paperwork.setIdentificativoPratica(set.getString(2));
				paperwork.setStagingId(set.getInt(3));
				paperwork.setReceivingDate(set.getDate(4));
				handlePaperwork(paperwork);
			} catch (SQLException | IOException e) {
				throw new RuntimeException(e);
			}
		});
		pw.close();
		pwNotFound.close();
	}

	private void handlePaperwork(PraticaExt paperwork) throws SQLException, IOException {
		logger.warn("paperwork = " + paperwork.getIdPratica() + " - " + paperwork.getIdentificativoPratica());  
		String ownerTaxCode = getOwnerTaxCodeFromTable(paperwork.getIdPratica());
		String postTechnicianCode = null;
		if(ownerTaxCode != null && ownerTaxCode.length() > 0) {

			List<PraticheEventiExt> events = extractPaperworkEvents(paperwork.getIdPratica());

			if(!events.isEmpty()) {
				PraticheEventiExt event = events.get(0);
				String pattern = CHANGE_TECHNICIAN_AGENT_1 + "(\\w+?)" + CHANGE_TECHNICIAN_AGENT_2 + "(\\w+)";
				Matcher m = Pattern.compile(pattern).matcher(event.getNote());
				if(m.find()) {
					postTechnicianCode = m.group(2);
				}
			}

			List<Object> crossBoFoTechnicalAgent = extractTechnicalAgent(paperwork.getIdentificativoPratica());
			if(crossBoFoTechnicalAgent.size() > 0) {
				if (crossBoFoTechnicalAgent.size() > 1) {
					pw.println("Errore troppi procuratori per " + paperwork.getIdentificativoPratica());
				}
				String crossBoFoTaxCode = (String) crossBoFoTechnicalAgent.get(0);
				String taxCodeToCheck = events.isEmpty() ? ownerTaxCode : postTechnicianCode;
				if(taxCodeToCheck != null && !taxCodeToCheck.equalsIgnoreCase(crossBoFoTaxCode)) {
					pw.println(paperwork.getIdPratica() + " - " + paperwork.getIdentificativoPratica());
				}
			}else
				pwNotFound.println("Non trovata nel frontoffice: " + paperwork.getIdentificativoPratica());
		}
	}


	/* ****************************
	 * pratica_procuratore
	 * ****************************/

	private List<PraticheEventiExt> extractPaperworkEvents(Integer paperworkId) throws SQLException{
		String query = SELECT_PRATICHE_EVENTI_RAW
				.replace(":ID_EVENTS","("+this.modProcIdsString+")" )
				.replace(":PAPERWORK_ID", paperworkId.toString());
		
		query += " order by data_evento desc LIMIT 1";

		return executeSelect(query, set ->{
			PraticheEventiExt pe = new PraticheEventiExt();
			try {
				pe.setIdPraticaEvento(set.getInt(1));
				pe.setEventDate(set.getTimestamp(2));
				pe.setNote(set.getString(3));
			} catch (SQLException e) {
				throw new RuntimeException("Error in extractPaperworkEvents with " + paperworkId);
			}	
			return pe;
		});
	}
}
