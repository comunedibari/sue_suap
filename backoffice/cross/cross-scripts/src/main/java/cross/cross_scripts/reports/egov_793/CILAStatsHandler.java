package cross.cross_scripts.reports.egov_793;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cross.cross_scripts.MySql;
import it.wego.cross.entity.LkTipoPratica;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.entity.ProcessiEventi;

public class CILAStatsHandler {

	private static final Logger logger = LogManager.getLogger(CILAStatsHandler.class);
	
	private MySql mysql;
	private Report report;

	public CILAStatsHandler(MySql mysql) {
		this.mysql = mysql;
		report = new Report();
		report.createSheet("CILA-CILAS-BARI");
	}

	public void run() throws Exception {
		logger.warn("run " + this.getClass().getName());
		
		Statement stmt = mysql.getStatement();
		ResultSet set = stmt.executeQuery("SELECT p.id_pratica FROM pratica p inner join procedimenti_enti pre on p.id_proc_ente = pre.id_proc_ente\r\n"
				+ "where p.id_tipo_pratica in (select id_tipo_pratica from lk_tipo_pratica where tipo_pratica in ('CILA', 'CILAS')) AND pre.id_ente = 2001 " 
				+ "order by p.id_pratica desc;");

		while(set.next()) {
			Integer idPratica = set.getInt(1);
			logger.info("Elaborazione Pratica: " + idPratica);
			handlePaperwork(idPratica);
		}
		stmt.close();
		report.close("CILA-CILAS-BARI");	

		logger.debug("fine elaborazione");
	}

	private void handlePaperwork(int idPratica) throws Exception {
		Pratica pratica = findPratica(idPratica);
		
		PraticheEventi firstEvent = null;
		PraticheEventi lastEvent = null;
		int counter = 0;
		for(PraticheEventi pe:pratica.getPraticheEventiList()) {
			
			if(("PROCINT").equalsIgnoreCase(pe.getIdEvento().getCodEvento())){
				
				counter++;
				if(lastEvent == null || lastEvent.getDataEvento().compareTo(pe.getDataEvento())<0)
					lastEvent = pe;
				if(firstEvent == null || firstEvent.getDataEvento().compareTo(pe.getDataEvento())>0)
					firstEvent = pe;
			}
			
		}
		report.createRow(pratica, firstEvent, lastEvent, counter);
		
	}
	
	private Pratica findPratica(int idPratica) throws SQLException {
		Statement stmt = mysql.getStatement();
		ResultSet set = stmt.executeQuery("select p.data_ricezione, ltp.tipo_pratica, p.anno, p.numero_pratica from pratica p inner join lk_tipo_pratica ltp on p.id_tipo_pratica = ltp.id_tipo_pratica where p.id_pratica =" + idPratica);
		Pratica p = new Pratica();
		while(set.next()) {
			p.setDataRicezione(set.getDate(1));
			String type = set.getString(2);
			if(type != null) {
				LkTipoPratica ltp = new LkTipoPratica();
				ltp.setTipoPratica(type);
				p.setTipoPratica(ltp);
			}
			p.setAnno(set.getInt(3));
			p.setNumeroPratica(set.getInt(4));
		}
		stmt.close();
		
		Statement stmt2 = mysql.getStatement();
		ResultSet set2 = stmt2.executeQuery("select pe.data_evento, poe.cod_evento from pratiche_eventi pe inner join processi_eventi poe on pe.id_evento = poe.id_evento where poe.cod_evento = 'PROCINT' AND pe.id_pratica =" + idPratica);
		List<PraticheEventi> list = new LinkedList<>();
		while(set2.next()) {
			Date newDate = set2.getTimestamp(1);
			PraticheEventi pe = new PraticheEventi();
			list.add(pe);
			pe.setDataEvento(newDate);
			ProcessiEventi poe = new ProcessiEventi();
			poe.setCodEvento(set2.getString(2));
			pe.setIdEvento(poe);
		}
		p.setPraticheEventiList(list);
		stmt2.close();
		return p;
	}
}
