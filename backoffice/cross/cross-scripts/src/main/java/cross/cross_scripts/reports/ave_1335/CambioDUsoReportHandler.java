package cross.cross_scripts.reports.ave_1335;
import java.io.IOException;
import java.io.StringReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import cross.cross_scripts.BaseHandler;
import cross.cross_scripts.CrossJpa;
import cross.cross_scripts.MySql;

public class CambioDUsoReportHandler extends BaseHandler {

	private static final Logger logger = LogManager.getLogger(CambioDUsoReportHandler.class);

	public CambioDUsoReportHandler(MySql mysql, CrossJpa jpa) {
		super(mysql, jpa);
	}
	Report report = new Report();
	public void run() throws Exception {

		String query_1 = "select  "
				+ "p.id_pratica as ID_PRATICA,  "
				+ "p.descrizione_intervento as INTERVENTO, "
				+ "CONVERT( s.xml_ricevuto using utf8) AS XML_RICEVUTO, "
				+ "p.numero_pratica as NUMERO_PRATICA,  "
				+ "p.anno as ANNO,  "
				+ "tp.descrizione as TIPO_PRATICA, "
				+ "concat(ii.indirizzo, ' ', ii.civico) as INDIRIZZO, "
				+ "ii.piano as PIANO "
				+ "from pratica p  "
				+ "inner join lk_tipo_pratica tp on p.id_tipo_pratica = tp.id_tipo_pratica "
				+ "inner join pratica_operazione po on p.id_pratica = po.id_pratica "
				+ "inner join operazione o on po.id_operazione = o.id "
				+ "inner join indirizzi_intervento ii on p.id_pratica = ii.id_pratica "
				+ "inner join staging s on s.id_staging = p.id_staging "
				+ "where tp.tipo_pratica in ('PDC', 'SCIA', 'SCIALT') AND o.id = 28 and p.data_ricezione >= 'ANNO_INIZIALE-01-01' and p.data_ricezione <= 'ANNO_FINALE-12-31' and p.id_comune = 893 "
				+ "order by p.anno asc, p.id_pratica asc";
		String query_2 = "select  "
				+ "p.id_pratica as ID_PRATICA,  "
				+ "p.descrizione_intervento as INTERVENTO, "
				+ "CONVERT( s.xml_ricevuto using utf8) AS XML_RICEVUTO, "
				+ "p.numero_pratica as NUMERO_PRATICA,  "
				+ "p.anno as ANNO,  "
				+ "tp.descrizione as TIPO_PRATICA, "
				+ "concat(ii.indirizzo, ' ', ii.civico) as INDIRIZZO, "
				+ "ii.piano as PIANO "
				+ "from pratica p  "
				+ "inner join lk_tipo_pratica tp on p.id_tipo_pratica = tp.id_tipo_pratica "
				+ "inner join pratica_operazione po on p.id_pratica = po.id_pratica "
				+ "inner join operazione o on po.id_operazione = o.id "
				+ "inner join indirizzi_intervento ii on p.id_pratica = ii.id_pratica "
				+ "inner join staging s on s.id_staging = p.id_staging "
				+ "where tp.tipo_pratica in ('PDC', 'SCIA', 'SCIALT') AND (p.descrizione_intervento like '% cambio %' or p.descrizione_intervento like '%\\'uso %' or p.descrizione_intervento like '% uso %') and p.data_ricezione >= 'ANNO_INIZIALE-01-01' and p.data_ricezione <= 'ANNO_FINALE-12-31' and p.id_comune = 893 "
				+ "order by p.anno asc, p.id_pratica asc";
		report.createSheet("Estrazione ");
		
		exeQuery(query_1, 1);
		exeQuery(query_2, 2);
		report.createSheet("query");
		report.createRow(query_1);
		report.createRow(query_2);
		report.close("Report cambio d'uso");
	}



	public void exeQuery(String query, int index) throws SQLException {

		for(int annoDaCercare = 2019; annoDaCercare <=2025;annoDaCercare++) {
			List<PraticaReport> reportList = new ArrayList<>();
			String newQuery =query
					.replace("ANNO_INIZIALE", Integer.toString(annoDaCercare))
					.replace("ANNO_FINALE", Integer.toString(annoDaCercare));

			System.out.println("anni: " + annoDaCercare);
			System.out.println("newQuery: " + newQuery);
			Statement stmt = mysql.getStatement();
			ResultSet set = stmt.executeQuery(newQuery);

			PraticaReport pratica = null;
			try {
				while(set.next()) {
					int idPratica = set.getInt("ID_PRATICA");
					int numero = set.getInt("NUMERO_PRATICA");
					int anno = set.getInt("ANNO");
					String xml = set.getString("XML_RICEVUTO");
					String tipoPratica = set.getString("TIPO_PRATICA");
					String indirizzo = set.getString("INDIRIZZO");
					String descrizioneIntervento = set.getString("INTERVENTO");
					String piano = set.getString("PIANO");

//					logger.info("lettura Pratica: " + idPratica);
					if(pratica != null && pratica.getIdPratica() != idPratica) {
						handlePaperwork(report, pratica);
						pratica = null;
					}
					if(pratica == null) {
						pratica = new PraticaReport();
						reportList.add(pratica);
						pratica.setIdPratica(idPratica);
						pratica.setNumero(numero);
						pratica.setAnno(anno);
						pratica.setXml(xml);
						pratica.setTipoPratica(tipoPratica);
						pratica.setDescrizioneIntervento(descrizioneIntervento);
						pratica.addIndirizzo(indirizzo);
						pratica.addPiano(piano);
					}
					else {
						pratica.addIndirizzo(indirizzo);
						pratica.addPiano(piano);
					}
				}
				handlePaperwork(report, pratica);
				stmt.close();
				
					
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private void handlePaperwork(Report report, PraticaReport praticaReport) throws Exception {
//		System.out.println(praticaReport.getIdPratica());

		int index = 0;
		for(String indirizzo: praticaReport.indirizzi) {
			String suffix = (index == 0) ? "": "_"+(index+1);
			//String valueDescrizione = readFromXml(praticaReport.getXml(), "//campoHref[nome=\"d001\" and descrizione=\"la descrizione sintetica dell'intervento\"]/valoreUtente");
			String valueDestinazioneDuso = readFromXml(praticaReport.getXml(), "//campoHref[nome=\"d001\" and descrizione=\"destinazione d\'uso esistente (Ad es. residenziale, industriale, commerciale, ecc.)\"]/valoreUtente");
			String valueEDiProgetto = readFromXml(praticaReport.getXml(), "//campoHref[nome=\"d002\" and descrizione=\"e di progetto\"]/valoreUtente");
			//String valuePianoEIndirizzo = readFromXml(praticaReport.getXml(), "//campoHref[nome=\"d003\" and descrizione=\"piano ed indirizzo\"]/valoreUtente");
			String valueZona = readFromXml(praticaReport.getXml(), "//campoHref[nome=\"d003"+suffix+"\" and descrizione=\"zona\"]/valoreUtente");
			report.createRow(
					praticaReport.getIdPratica(), 
					praticaReport.getTipoPratica(), 
					praticaReport.getNumero(), 
					praticaReport.getAnno(), 
					praticaReport.getDescrizioneIntervento(), 
					valueDestinazioneDuso, 
					valueEDiProgetto, 
					indirizzo, 
					praticaReport.getPiani().get(index), 
					valueZona);
			index++;
		}
//		System.out.println("END");
	}

	public String readFromXml(String xml,String xpathExpression) throws SAXException, IOException, XPathExpressionException, ParserConfigurationException	{

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(false); // true se ci sono namespace
		DocumentBuilder builder = factory.newDocumentBuilder();

		InputSource is = new InputSource(new StringReader(xml));
		Document document = builder.parse(is);

		// XPath
		XPath xpath = XPathFactory.newInstance().newXPath();

		// XPath expression
		String nome = xpath.evaluate(xpathExpression, document);

		return nome;
	}
}