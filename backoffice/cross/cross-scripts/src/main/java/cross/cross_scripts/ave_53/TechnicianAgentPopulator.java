package cross.cross_scripts.ave_53;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cross.cross_scripts.MySql;
import cross.cross_scripts.ave_53.entities.PraticaExt;
import cross.cross_scripts.ave_53.entities.PraticheEventiExt;

public class TechnicianAgentPopulator extends TechnicianAgentBase{

	private static final Logger logger = LogManager.getLogger(TechnicianAgentPopulator.class);

	public TechnicianAgentPopulator(MySql openCrossMySql) {
		super(openCrossMySql);
	} 

	public void run() throws Exception {
		logger.warn("run " + this.getClass().getName());
		setModProcIds();
		int min = min();
		int max = max();
		int increment = 500;
		logger.warn("EXE min: " + min);
		logger.warn("EXE max: " + max);

		for(int i = min; i < max; i+=increment) {
			process(i , i+increment);
		}
		openCrossMySql.close();
		logger.warn("END " + this.getClass().getName());
	}

	protected void process(int a, int b) throws SQLException, IOException {
		logger.warn("EXE min: " + a + ", max: " + b);
		String query = SELECT_FROM_PRATICA_AND_PRATICA_OWNER.replace(":V_A", Integer.toString(a)).replace(":V_B", Integer.toString(b));

		
		consumeSelect(query, set ->{
			PraticaExt paperwork = new PraticaExt();
			try {
				paperwork.setIdPratica(set.getInt(1));
				paperwork.setIdentificativoPratica(set.getString(2));
				paperwork.setStagingId(set.getInt(3));
				paperwork.setReceivingDate(set.getDate(4));
				handlePaperwork(paperwork);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		});
	}

	protected void handlePaperwork(PraticaExt paperwork) throws SQLException {
		logger.info("paperwork = " + paperwork.getIdPratica() + " - " + paperwork.getIdentificativoPratica());

		String ownerTaxCode = getOwnerTaxCodeFromTable(paperwork.getIdPratica());
		String postTechnicianCode = null;
		if(ownerTaxCode != null && ownerTaxCode.length() > 0) {
			logger.info("\townerTaxCode = " + ownerTaxCode);
			insertTechnicianAgent(paperwork, ownerTaxCode);
			
			List<PraticheEventiExt> events = extractPaperworkEvents(paperwork.getIdPratica());

			if(!events.isEmpty()) {
				boolean isFirst = true;
				for(PraticheEventiExt event:events) {
					String pattern = CHANGE_TECHNICIAN_AGENT_1 + "(\\w+?)" + CHANGE_TECHNICIAN_AGENT_2 + "(\\w+)";
					Matcher m = Pattern.compile(pattern).matcher(event.getNote());
					if(m.find()) {
						String prevTechnicianCode = m.group(1);
						postTechnicianCode = m.group(2);
						if(isFirst) {
							if(!ownerTaxCode.equalsIgnoreCase(prevTechnicianCode)) {
								PraticheEventiExt temp = new PraticheEventiExt();
								temp.setEventDate(new Timestamp(paperwork.getReceivingDate().getTime()));
								updateTechnicianAgent(paperwork.getIdPratica(), temp, ownerTaxCode, prevTechnicianCode);		
							}
							isFirst = false;
						}
						updateTechnicianAgent(paperwork.getIdPratica(), event, prevTechnicianCode, postTechnicianCode);
					}
				}
			}

			List<Object> crossBoFoTechnicalAgent = extractTechnicalAgent(paperwork.getIdentificativoPratica());
			if(crossBoFoTechnicalAgent.size() > 0) {
				logger.info("\tcrossBoFoTechnicalAgent = " + crossBoFoTechnicalAgent);
				if (crossBoFoTechnicalAgent.size() > 1)
					logger.error("too much tax codes, check " + paperwork.getIdentificativoPratica());

				String crossBoFoTaxCode = (String) crossBoFoTechnicalAgent.get(0);
				String taxCodeToCheck = events.isEmpty()?ownerTaxCode: postTechnicianCode;
				if(!taxCodeToCheck.equalsIgnoreCase(crossBoFoTaxCode)) {
					PraticheEventiExt temp = new PraticheEventiExt();
					temp.setEventDate(new Timestamp(paperwork.getReceivingDate().getTime()));
					updateTechnicianAgent(paperwork.getIdPratica(), temp, taxCodeToCheck, crossBoFoTaxCode);
				}
			}
		}
	}


	/* ****************************
	 * pratica_procuratore
	 * ****************************/

	protected void updateTechnicianAgent(Integer paperworkId, PraticheEventiExt event, String prevTechnicianCode, String postTechnicianCode) throws SQLException {
		logger.info("\t"+prevTechnicianCode+"->"+postTechnicianCode);

		openCrossMySql.setAutocommit(true);
		try(Statement stmt = openCrossMySql.getStatement()){
			Integer prevPersonalDataId = extractPersonalDataId(prevTechnicianCode);
			Integer postPersonalDataId = extractPersonalDataId(postTechnicianCode);
			Timestamp newDate = event.getEventDate();

			String queryUpdate = "UPDATE pratica_procuratore SET data_fine_incarico = '"+newDate
					+"' WHERE id_pratica = " + paperworkId + " and id_anagrafica = "+prevPersonalDataId+";";
			stmt.execute(queryUpdate);


			String queryInsert = "insert into pratica_procuratore(id_pratica, id_anagrafica, data_inizio_incarico)"
					+ "values("+paperworkId+", "+postPersonalDataId+", '"+newDate+"');";

			stmt.execute(queryInsert);
		}
	}

	protected void insertTechnicianAgent(PraticaExt paperwork, String postTechnicianCode) throws SQLException {
		openCrossMySql.setAutocommit(true);
		try(Statement stmt = openCrossMySql.getStatement()){
			Integer postPersonalDataId = extractPersonalDataId(postTechnicianCode);
			String queryInsert = "insert into pratica_procuratore(id_pratica, id_anagrafica, data_inizio_incarico)"
					+ "values("+paperwork.getIdPratica()+", "+postPersonalDataId+", '"+new java.sql.Timestamp(paperwork.getReceivingDate().getTime())+"');";
			stmt.execute(queryInsert);
		}
	}

	protected Integer extractPersonalDataId(final String prevTechnicianCode) throws SQLException{
		String query = "select a.id_anagrafica from anagrafica a where a.codice_fiscale = '" +prevTechnicianCode + "';";

		return executeSelect(query, set ->{
			int number = -1;
			try{
				number = set.getInt(1);
			} catch (SQLException e) {
				throw new RuntimeException("Error in extractPersonalDataId with " + prevTechnicianCode);
			}
			return number;

		}).get(0);

	}

	protected List<PraticheEventiExt> extractPaperworkEvents(Integer paperworkId) throws SQLException{
		String query = SELECT_PRATICHE_EVENTI_RAW
				.replace(":ID_EVENTS","("+this.modProcIdsString+")" )
				.replace(":PAPERWORK_ID", paperworkId.toString());

		query += " order by data_evento asc";

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
