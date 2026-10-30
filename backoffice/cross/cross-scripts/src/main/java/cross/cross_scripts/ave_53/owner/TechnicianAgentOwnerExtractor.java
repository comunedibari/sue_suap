package cross.cross_scripts.ave_53.owner;

import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cross.cross_scripts.MySql;
import cross.cross_scripts.ave_53.TechnicianAgentBase;
import cross.cross_scripts.ave_53.entities.PraticaExt;

/**
 * extract all of owner from xml
 * 24-07-31 add a temporary table to save owner, removed file
 */
public class TechnicianAgentOwnerExtractor extends TechnicianAgentBase {

	private static final Logger logger = LogManager.getLogger(TechnicianAgentOwnerExtractor.class);

	public TechnicianAgentOwnerExtractor(MySql openCrossMySql) {
		super(openCrossMySql);
	} 

	public void run() throws Exception {
		logger.warn("run " + this.getClass().getName());
		
		this.openCrossMySql.setAutocommit(true);
		
		int min = min();
		int max = max();
		int increment = 500;
		
		logger.info("min: " + min);
		logger.info("max: " + max);

		for(int i = max; i > min; i-=increment) {
			process(i-increment , i);
		}
		this.openCrossMySql.setAutocommit(false);
		logger.warn("END " + this.getClass().getName());
	}

	private void process(int a, int b) throws SQLException {
		String query = SELECT_FROM_PRATICA_AND_TMP.replace(":V_A", Integer.toString(a)).replace(":V_B", Integer.toString(b));

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

	private void handlePaperwork(PraticaExt paperwork) throws SQLException {
		logger.warn("paperwork = " + paperwork.getIdPratica() + " - " + paperwork.getIdentificativoPratica());

		String ownerTaxCode = getOwnerTaxCodeFromXml(paperwork.getStagingId());
		if(ownerTaxCode != null && ownerTaxCode.length() > 0) {
			try(Statement stmt = openCrossMySql.getStatement()){
				String queryInsert = "insert into opencross.tmp_pratica_owner(id_pratica, technician_tax_code)"
						+ "values("+paperwork.getIdPratica()+", '"+ownerTaxCode+"');";
				stmt.execute(queryInsert);
			}
		} else {
			try(Statement stmt = openCrossMySql.getStatement()){
				String queryInsert = "insert into opencross.tmp_pratica_owner(id_pratica)"
						+ "values("+paperwork.getIdPratica()+");";
				stmt.execute(queryInsert);
			}
		}
	}
}
