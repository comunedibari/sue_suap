package cross.cross_scripts.ave_74;

import java.io.IOException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cross.cross_scripts.BaseHandler;
import cross.cross_scripts.CrossJpa;
import cross.cross_scripts.MySql;
import it.wego.cross.dao.PraticaDao;
import it.wego.cross.entity.DatiCatastali;
import it.wego.cross.entity.IndirizziIntervento;
import it.wego.cross.entity.LkCittadinanza;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.entity.LkFormeGiuridiche;
import it.wego.cross.entity.LkNazionalita;
import it.wego.cross.entity.LkProvincie;
import it.wego.cross.entity.LkStatoPratica;
import it.wego.cross.entity.LkTipoCollegio;
import it.wego.cross.entity.LkTipoPratica;
import it.wego.cross.entity.LkTipoQualifica;
import it.wego.cross.entity.LkTipoRuolo;
import it.wego.cross.entity.LkTipoSistemaCatastale;
import it.wego.cross.entity.LkTipoUnita;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticaAnagrafica;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.entity.PraticheEventiAllegati;
import it.wego.cross.entity.Processi;
import it.wego.cross.entity.ProcessiEventi;
import it.wego.cross.entity.Utente;

public class RuvoDiPugliaExporter extends BaseHandler{

	private int idComune = 10233;
	private EntityManager em;
	PraticaDao praticaDao;
	String pattern = "MM/dd/yyyy HH:mm:ss";
	DateFormat df = new SimpleDateFormat(pattern);
	private static final Logger logger = LogManager.getLogger(RuvoDiPugliaExporter.class);

	public RuvoDiPugliaExporter(MySql mysql, CrossJpa jpa) {
		super(mysql, jpa);
		em = jpa.get();
		praticaDao = new PraticaDao(em);
	}

	@Override
	public void run() throws Exception {
		logger.warn("Start " + this.getClass().getName());
		List<Integer> ruvoDiPugliaPaperworkIds = null;
		String query = Constants.QUERY_PRATICHE_ID_COMUNE.replaceAll(":idComune", Integer.toString(idComune));
		try(Statement stmt = mysql.getStatement()){
			ruvoDiPugliaPaperworkIds = executeSelect(query, set -> {
				Integer value = null;
				try {
					value = set.getInt("id_pratica");
				}catch(Exception e) {

				}
				return value;
			});
		}
		logger.warn("Find " + ruvoDiPugliaPaperworkIds.size() + " paperworks");

		List<Pratica> list = new ArrayList<>();

		for(Integer idPratica: ruvoDiPugliaPaperworkIds) {
			Pratica pratica = praticaDao.findPratica(idPratica);
			list.add(pratica);

		}
		generatePraticheEventiAndPraticheEventiAllegatiCSV(list);

		generatePraticaCSV(list);
		generateDatiCatastaliCSV(list);
		generateIndirizziInterventoCSV(list);
		generatePraticaAnagraficaCSV(list);
		//		generatePraticheEventiCSV(list);
		//		generatePraticheEventiAllegatiCSV(list);
		logger.warn("end " + this.getClass().getName());

	}

	private void generatePraticaCSV(List<Pratica> ruvoDiPugliaPaperworks) throws IOException {
		logger.warn("Generate pratica CVS");
		CsvWriter csv = new CsvWriter("pratica");
		List<String[]> rows = new ArrayList();
		rows.add(Constants.paperworksColumns.toArray(new String[0]));
		csv.append(rows);

		for(Pratica paperwork: ruvoDiPugliaPaperworks) {
			rows = new ArrayList();
			List<String> row = new ArrayList();
			row.add(convert(paperwork.getIdPratica()));
			row.add(convert(paperwork.getIdentificativoPratica()));
			row.add(convert(paperwork.getOggettoPratica()));
			row.add(convert(paperwork.getProtocollo()));
			row.add(convert(paperwork.getVecchioProtocollo()));
			row.add(convert(paperwork.getCodRegistro()));
			row.add(convert(paperwork.getAnnoRiferimento()));
			row.add(convert(paperwork.getIdStatoPratica()));
			row.add(convert(paperwork.getDataApertura()));
			row.add(convert(paperwork.getDataChiusura()));
			row.add(convert(paperwork.getIdProcesso()));
			//			row.add(convert(paperwork.getIdUtente()));
			//			row.add(convert(paperwork.getIdComune()));
			row.add(convert(paperwork.getDataRicezione()));
			row.add(convert(paperwork.getResponsabileProcedimento()));
			row.add(convert(paperwork.getDataProtocollazione()));
//			row.add(convert(paperwork.getDataVecchiaProtocollazione()));
			row.add(convert(paperwork.getGiorniSospensione()));
			row.add(convert(paperwork.getTipoPratica()));
			row.add(convert(paperwork.getNumeroPratica()));
			row.add(convert(paperwork.getAnno()));
			row.add(convert(paperwork.getDescrizioneIntervento()));
			row.add(convert(paperwork.getDataUltimoEventoEsterno()));


			rows.add(row.toArray(new String[0]));
			csv.append(rows);
		}

		csv.close();
	}

	private void generateDatiCatastaliCSV(List<Pratica> ruvoDiPugliaPaperworks) throws IOException {
		logger.warn("Generate dati catastali CVS");
		CsvWriter csv = new CsvWriter("dati_catastali");

		List<String[]> rows = new ArrayList();
		rows.add(Constants.cadastralDataColumns.toArray(new String[0]));
		csv.append(rows);

		for(Pratica paperwork: ruvoDiPugliaPaperworks) {
			for(DatiCatastali cadastralData: paperwork.getDatiCatastaliList()) {
				rows = new ArrayList();
				List<String> row = new ArrayList();
				row.add(convert(cadastralData.getIdImmobile()));
				row.add(convert(cadastralData.getIdPratica()));
				row.add(convert(cadastralData.getIdTipoSistemaCatastale()));
				row.add(convert(cadastralData.getSezione()));
				row.add(convert(cadastralData.getIdTipoUnita()));
				row.add(convert(cadastralData.getFoglio()));
				row.add(convert(cadastralData.getMappale()));
				row.add(convert(cadastralData.getIdTipoParticella()));
				row.add(convert(cadastralData.getEstensioneParticella()));
				row.add(convert(cadastralData.getSubalterno()));
				row.add(convert(cadastralData.getComuneCensuario()));
				row.add(convert(cadastralData.getCodImmobile()));
				row.add(convert(cadastralData.getCategoria()));
				rows.add(row.toArray(new String[0]));
				csv.append(rows);
			}
		}
		csv.close();
	}

	private void generateIndirizziInterventoCSV(List<Pratica> ruvoDiPugliaPaperworks) throws IOException {
		logger.warn("Generate indirizzi intervento CVS");
		CsvWriter csv = new CsvWriter("indirizzi_intervento");
		List<String[]> rows = new ArrayList();
		rows.add(Constants.cadastralStreetColumns.toArray(new String[0]));
		csv.append(rows);

		for(Pratica paperwork: ruvoDiPugliaPaperworks) {
			for(IndirizziIntervento cadastralAddress: paperwork.getIndirizziInterventoList()) {
				rows = new ArrayList();
				List<String> row = new ArrayList();
				//				row.add(convert(cadastralAddress.getIdIndirizzoIntervento()));
				row.add(convert(cadastralAddress.getIdPratica()));
				row.add(convert(cadastralAddress.getLocalita()));
				row.add(convert(cadastralAddress.getIdDug()));
				row.add(convert(cadastralAddress.getIndirizzo()));
				row.add(convert(cadastralAddress.getCivico()));
				row.add(convert(cadastralAddress.getCap()));
				row.add(convert(cadastralAddress.getAltreInformazioniIndirizzo()));
				row.add(convert(cadastralAddress.getCodCivico()));
				row.add(convert(cadastralAddress.getCodVia()));
				row.add(convert(cadastralAddress.getInternoNumero()));
				row.add(convert(cadastralAddress.getInternoLettera()));
				row.add(convert(cadastralAddress.getInternoScala()));
				row.add(convert(cadastralAddress.getLettera()));
				row.add(convert(cadastralAddress.getColore()));
				row.add(convert(cadastralAddress.getLatitudine()));
				row.add(convert(cadastralAddress.getLongitudine()));
				row.add(convert(cadastralAddress.getDatoEsteso1()));
				row.add(convert(cadastralAddress.getDatoEsteso2()));
				row.add(convert(cadastralAddress.getPiano()));
				rows.add(row.toArray(new String[0]));
				csv.append(rows);
			}
		}
		csv.close();
	}

	private void generatePraticaAnagraficaCSV(List<Pratica> ruvoDiPugliaPaperworks) throws IOException {
		logger.warn("Generate pratica anagrafica CVS");
		CsvWriter csv = new CsvWriter("pratica_anagrafica");
		List<String[]> rows = new ArrayList();
		rows.add(Constants.personalDataColumns.toArray(new String[0]));
		csv.append(rows);

		for(Pratica paperwork: ruvoDiPugliaPaperworks) {
			for(PraticaAnagrafica personalData: paperwork.getPraticaAnagraficaList()) {
				rows = new ArrayList();
				List<String> row = new ArrayList();
				row.add(convert(personalData.getIdPratica()));
				//				row.add(convert(personalData.getPraticaAnagraficaPK().getIdAnagrafica()));
				row.add(convert(personalData.getAnagrafica().getIdAnagrafica()));
				row.add(convert(personalData.getAnagrafica().getTipoAnagrafica()));
				row.add(convert(personalData.getAnagrafica().getCognome()));
				row.add(convert(personalData.getAnagrafica().getNome()));
				row.add(convert(personalData.getAnagrafica().getCodiceFiscale()));
				row.add(convert(personalData.getAnagrafica().getDenominazione()));
				row.add(convert(personalData.getAnagrafica().getPartitaIva()));
				row.add(convert(personalData.getAnagrafica().getFlgIndividuale()));
				row.add(convert(personalData.getAnagrafica().getDataNascita()));
				row.add(convert(personalData.getAnagrafica().getIdCittadinanza()));
				row.add(convert(personalData.getAnagrafica().getIdNazionalita()));
				row.add(convert(personalData.getAnagrafica().getIdComuneNascita()));
				row.add(convert(personalData.getAnagrafica().getLocalitaNascita()));
				row.add(convert(personalData.getAnagrafica().getSesso()));
				row.add(convert(personalData.getAnagrafica().getIdTipoCollegio()));
				row.add(convert(personalData.getAnagrafica().getNumeroIscrizione()));
				row.add(convert(personalData.getAnagrafica().getDataIscrizione()));
				row.add(convert(personalData.getAnagrafica().getIdProvinciaIscrizione()));
				row.add(convert(personalData.getAnagrafica().getIdProvinciaCciaa()));
				row.add(convert(personalData.getAnagrafica().getFlgAttesaIscrizioneRi()));
				row.add(convert(personalData.getAnagrafica().getFlgObbligoIscrizioneRi()));
				row.add(convert(personalData.getAnagrafica().getDataIscrizioneRi()));
				row.add(convert(personalData.getAnagrafica().getNIscrizioneRi()));
				row.add(convert(personalData.getAnagrafica().getFlgAttesaIscrizioneRea()));
				row.add(convert(personalData.getAnagrafica().getDataIscrizioneRea()));
				row.add(convert(personalData.getAnagrafica().getNIscrizioneRea()));
				row.add(convert(personalData.getAnagrafica().getIdFormaGiuridica()));
				row.add(convert(personalData.getAnagrafica().getVarianteAnagrafica()));
				row.add(convert(personalData.getLkTipoRuolo()));
				row.add(convert(personalData.getDataInizioValidita()));
				row.add(convert(personalData.getIdTipoQualifica()));
				row.add(convert(personalData.getDescrizioneTitolarita()));
				row.add(convert(personalData.getAssensoUsoPec()));
				row.add(convert(personalData.getPec()));
				row.add(convert(personalData.getIdRecapitoNotifica()));
				row.add(convert(personalData.getFlgDittaIndividuale()));
				row.add(convert(personalData.getIdEtichetta()));
				rows.add(row.toArray(new String[0]));
				csv.append(rows);
			}
		}
		csv.close();
	}

	private void generatePraticheEventiAndPraticheEventiAllegatiCSV(List<Pratica> ruvoDiPugliaPaperworks) throws IOException {
		logger.warn("Generate pratiche eventi CVS");
		CsvWriter csvPraticheEventi = new CsvWriter("pratiche_eventi");
		CsvWriter csvAllegati = new CsvWriter("pratiche_eventi_allegati");

		List<String[]> rowsPraticheEventi = new ArrayList();
		List<String[]> rowsAllegati = new ArrayList();

		rowsPraticheEventi.add(Constants.praticheEventiColumns.toArray(new String[0]));
		csvPraticheEventi.append(rowsPraticheEventi);

		rowsAllegati.add(Constants.praticheEventiAllegatiColumns.toArray(new String[0]));
		csvAllegati.append(rowsAllegati);


		for(Pratica paperwork: ruvoDiPugliaPaperworks) {
			List<PraticheEventi> ricEvents = new ArrayList<>();
			for(PraticheEventi ev: paperwork.getPraticheEventiList()) {
				if("RIC".equals(ev.getIdEvento().getCodEvento()) && (ev.getOggetto() == null || !ev.getOggetto().contains(" test ")))
					ricEvents.add(ev);
			}

			PraticheEventi ricEventWithProtocol  = null;
			for(PraticheEventi ev: ricEvents) {
				if(ev.getProtocollo() != null && ev.getProtocollo().length() > 0)
					ricEventWithProtocol = ev;
			}

			if(ricEventWithProtocol == null) {
				if(!ricEvents.isEmpty())
					handleEvent(csvPraticheEventi, csvAllegati, ricEvents.get(0));
			}
			else 
				handleEvent(csvPraticheEventi, csvAllegati, ricEventWithProtocol);

			List<PraticheEventi> noRicEvents = new ArrayList();
			for(PraticheEventi ev: paperwork.getPraticheEventiList()) {
				if(!"RIC".equals(ev.getIdEvento().getCodEvento()) && (ev.getOggetto() == null || !ev.getOggetto().contains(" test ")))
					noRicEvents.add(ev);
			}

			for(PraticheEventi event: noRicEvents) {
				handleEvent(csvPraticheEventi, csvAllegati, event);
			}
		}

		csvPraticheEventi.close();
		csvAllegati.close();
	}

	public void handleEvent(CsvWriter csvPraticheEventi, CsvWriter csvAllegati, PraticheEventi event) {
		List<String[]> rowsPraticheEventi;
		List<String[]> rowsAllegati;

		rowsPraticheEventi = new ArrayList();
		List<String> rowPraticheEventi = new ArrayList();
		rowPraticheEventi.add(convert(event.getIdPraticaEvento()));
		rowPraticheEventi.add(convert(event.getIdPratica()));
		rowPraticheEventi.add(convert(event.getIdEvento()));
		rowPraticheEventi.add(event.getIdUtente() == null? "" : event.getIdUtente().getUsername());
		rowPraticheEventi.add(convert(event.getDescrizioneEvento()));
		rowPraticheEventi.add(convert(event.getDataEvento()));
		rowPraticheEventi.add(convert(event.getProtocollo()));
		rowPraticheEventi.add(convert(event.getDataProtocollo()));
//		rowPraticheEventi.add(convert(event.getVecchioProtocollo()));
//		rowPraticheEventi.add(convert(event.getDataVecchioProtocollo()));
		rowPraticheEventi.add(convert(event.getVerso()));
		rowPraticheEventi.add(convert(event.getNote()));
		rowsPraticheEventi.add(rowPraticheEventi.toArray(new String[0]));
		csvPraticheEventi.append(rowsPraticheEventi);

		rowsAllegati = new ArrayList<>();
		for(PraticheEventiAllegati attachments: event.getPraticheEventiAllegatiList()) {
			String[] rowAllegati = new String[8];
			rowAllegati[0] = (convert(event.getIdPratica()));
			rowAllegati[1] = (convert(event.getIdPraticaEvento()));
			rowAllegati[2] = (convert(event.getIdEvento()));
			rowAllegati[3] = (convert(event.getDataEvento()));
			rowAllegati[4] = (convert(attachments.getAllegati().getDescrizione()));
			rowAllegati[5] = (convert(attachments.getAllegati().getDescrizione()));
			rowAllegati[6] = (convert(attachments.getAllegati().getTipoFile()));
			String pathFile = convert(attachments.getAllegati().getPathFile());
			rowAllegati[7] = pathFile.replaceAll(".+(\\W)(cross_files.+)", "$1$2");
			rowsAllegati.add(rowAllegati);
		}
		csvAllegati.append(rowsAllegati);

	}

	private String convert(Object value) {
		if(value == null)
			return "";
		else if(value instanceof LkProvincie)
			return ((LkProvincie)value).getDescrizione();
		else if(value instanceof LkFormeGiuridiche)
			return ((LkFormeGiuridiche)value).getDescrizione();
		else if(value instanceof LkCittadinanza)
			return ((LkCittadinanza)value).getDescrizione();
		else if(value instanceof LkNazionalita)
			return ((LkNazionalita)value).getDescrizione();
		else if(value instanceof LkTipoCollegio)
			return ((LkTipoCollegio)value).getDescrizione();
		else if(value instanceof Processi)
			return ((Processi)value).getDesProcesso();
		else if(value instanceof Utente)
			return ((Utente)value).getCodiceFiscale();
		else if(value instanceof LkComuni)
			return ((LkComuni)value).getDescrizione();
		else if(value instanceof LkTipoPratica)
			return ((LkTipoPratica)value).getDescrizione();
		else if(value instanceof LkTipoRuolo)
			return ((LkTipoRuolo)value).getDescrizione();
		else if(value instanceof LkStatoPratica)
			return ((LkStatoPratica)value).getDescrizione();
		else if(value instanceof LkTipoQualifica)
			return ((LkTipoQualifica)value).getDescrizione();
		else if(value instanceof ProcessiEventi)
			return ((ProcessiEventi)value).getCodEvento();
		else if(value instanceof LkTipoUnita)
			return ((LkTipoUnita)value).getDescrizione();
		else if(value instanceof LkTipoSistemaCatastale)
			return ((LkTipoSistemaCatastale)value).getDescrizione();
		else if(value instanceof Pratica)
			return convert(((Pratica)value).getIdPratica());
		else if(value instanceof Integer)
			return Integer.toString((Integer)value);
		else if(value instanceof Date)
			return df.format((Date)value);
		else if(value instanceof String)
			return (String) value;
		else if(value instanceof Character)
			return value.toString();
		else 
			throw new RuntimeException("fix " + value.getClass().getName());
	}
}
