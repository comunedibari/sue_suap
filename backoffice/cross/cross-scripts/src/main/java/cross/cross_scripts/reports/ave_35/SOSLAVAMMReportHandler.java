package cross.cross_scripts.reports.ave_35;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cross.cross_scripts.CrossJpa;
import cross.cross_scripts.MySql;
import it.wego.cross.dao.PraticaDao;
import it.wego.cross.entity.LkStatoPratica;
import it.wego.cross.entity.LkTipoPratica;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.entity.ProcessiEventi;
import it.wego.cross.entity.Utente;

public class SOSLAVAMMReportHandler {

//	private static String DES_EVENTO = "(amm) Invia Richiesta Conformazione e Divieto prosecuzione lavori";
	private static final Logger logger = LogManager.getLogger(SOSLAVAMMReportHandler.class);

	private EntityManager em;
	private PraticaDao praticaDao;
	private Report report;
	private MySql mysql;

	public SOSLAVAMMReportHandler(MySql mysql, CrossJpa jpa) {
		em = jpa.get();
		praticaDao = new PraticaDao(em);
		report = new Report();
		this.mysql = mysql;
	}

	public void run() throws Exception {
		logger.info("SOSLAVAMMReportHandler");
		int increment = 500;
		Integer min = min();
		Integer max = max();
		report.createSheet("Estrazione");
		for(int i = min; i < max;i+=increment) {
			Statement stmt = mysql.getStatement();
			ResultSet set = stmt.executeQuery("SELECT id_pratica "
					+ "FROM pratica p inner join lk_tipo_pratica tp on p.id_tipo_pratica = tp.id_tipo_pratica inner join procedimenti_enti proe on proe.id_proc_ente = p.id_proc_ente "
					+ "WHERE proe.id_ente = 2001 AND tp.tipo_pratica in ('SCIA', 'SCIALT','CILA') and  p.data_ricezione >= '2019-01-01' and "
					+ "exists(select 1 from pratiche_eventi pe inner join processi_eventi pre on pe.id_evento = pre.id_evento where p.id_pratica = pe.id_pratica and pre.cod_evento = 'SOSLAVAMM') "
					+ "and p.id_pratica between "+i+ " and " +(i+increment-1)+";");
			while(set.next()) {
				Integer idPratica = set.getInt(1);
				logger.info("Elaborazione Pratica: " + idPratica);
				handlePaperwork(idPratica);
			}
			stmt.close();
			
		}
		report.close("report");	
	}

	private void handlePaperwork(int idPratica) throws Exception {
		List<PraticheEventi> praticheEventi = getAscSortedPraticheEventi(idPratica);
		List<PraticheEventi> eventiSelezionati = new ArrayList<>();

		for(PraticheEventi pe: praticheEventi) {
			if(pe.getIdEvento().getCodEvento().equalsIgnoreCase("SOSLAVAMM"))
				eventiSelezionati.add(pe);
		}	
		
		PraticheEventi lastEvent = null;
		for(PraticheEventi pe:eventiSelezionati) {
			if(lastEvent == null || lastEvent.getDataEvento().compareTo(pe.getDataEvento())<0)
				lastEvent = pe;
		}
		
		boolean found = false;
		for(PraticheEventi pe: praticheEventi) {
			found = found || (pe.getDataEvento().compareTo(lastEvent.getDataEvento())> 0 && "integrazione".equalsIgnoreCase(pe.getIdUtente().getCodiceFiscale()));
		}

		if(!found) {
			report.createRow(findPratica(idPratica), lastEvent);
			
		}

	}

	private Pratica findPratica(int idPratica) throws SQLException {
		Statement stmt = mysql.getStatement();
		ResultSet set = stmt.executeQuery("select tp.descrizione, p.numero_pratica, p.anno, p.protocollo, sp.descrizione "
				+ "from pratica p inner join lk_tipo_pratica tp on p.id_tipo_pratica = tp.id_tipo_pratica inner join lk_stato_pratica sp on sp.id_stato_pratica = p.id_stato_pratica "
				+ "where p.id_pratica ="+idPratica+";");
		Pratica pratica = null;
		while(set.next()) {
			pratica = new Pratica();
			pratica.setIdPratica(idPratica);
			LkTipoPratica tp = new LkTipoPratica();
			tp.setDescrizione(set.getString(1));
			pratica.setTipoPratica(tp);
			pratica.setNumeroPratica(set.getInt(2));
			pratica.setAnno(set.getInt(3));
			pratica.setProtocollo(set.getString(4));
			LkStatoPratica sp = new LkStatoPratica();
			sp.setDescrizione(set.getString(5));
			pratica.setIdStatoPratica(sp);
			
		}
		stmt.close();
		return pratica;
	}
	private List<PraticheEventi> getAscSortedPraticheEventi(int idPratica) throws ClassNotFoundException, SQLException {
		Statement stmt = mysql.getStatement();
		ResultSet set = stmt.executeQuery("select pe.id_pratica, poe.cod_evento, pe.data_evento, u.codice_fiscale\r\n"
				+ "from pratiche_eventi pe inner join utente u on pe.id_utente = u.id_utente inner join processi_eventi poe on pe.id_evento = poe.id_evento\r\n"
				+ "where id_pratica ="+idPratica+";");
		List<PraticheEventi> pes = new ArrayList<>();
		while(set.next()) {
			PraticheEventi pe = new PraticheEventi();
			ProcessiEventi poe = new ProcessiEventi();
			poe.setCodEvento(set.getString(2));
			pe.setIdEvento(poe);
			pe.setDataEvento(new java.util.Date(set.getDate(3).getTime()));
			Utente u = new Utente();
			u.setCodiceFiscale(set.getString(4));
			pe.setIdUtente(u);
			pes.add(pe);
		}
		stmt.close();
		return pes;
	}
	
	private int min() throws ClassNotFoundException, SQLException {
		int number = -1;
		Statement stmt = mysql.getStatement();
		ResultSet set = stmt.executeQuery("SELECT min(id_pratica) from pratica");
		while(set.next()) {
			number = set.getInt(1);
		}
		stmt.close();
		return number;
	}

	private int max() throws ClassNotFoundException, SQLException {
		int number = -1;
		Statement stmt = mysql.getStatement();
		ResultSet set = stmt.executeQuery("SELECT max(id_pratica) from pratica");
		while(set.next()) {
			number = set.getInt(1);
		}
		stmt.close();
		return number;
	}
}