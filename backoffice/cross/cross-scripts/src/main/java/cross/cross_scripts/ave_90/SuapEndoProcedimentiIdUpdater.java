package cross.cross_scripts.ave_90;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigInteger;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import cross.cross_scripts.BaseHandler;
import cross.cross_scripts.CrossJpa;
import cross.cross_scripts.MySql;
import cross.cross_scripts.ave_90.utils.FileReader;
import cross.cross_scripts.ave_90.utils.ProcedureIdPair;
import cross.cross_scripts.ave_90.utils.XPathReader;
import it.gov.impresainungiorno.schema.suap.pratica.AdempimentoSUAP;
import it.gov.impresainungiorno.schema.suap.pratica.EstremiEnte;
import it.gov.impresainungiorno.schema.suap.pratica.RiepilogoPraticaSUAP;
import it.wego.cross.dao.EntiDao;
import it.wego.cross.dao.LookupDao;
import it.wego.cross.dao.PraticaDao;
import it.wego.cross.dao.ProcedimentiDao;
import it.wego.cross.dao.UsefulDao;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.LkTipoEndoProcedimentoSuap;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticaProcedimenti;
import it.wego.cross.entity.PraticaProcedimentiPK;
import it.wego.cross.entity.ProcedimentiEnti;
import it.wego.cross.serializer.ProcedimentiSerializer;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.Utils;
import it.wego.cross.xml.Procedimenti;
import it.wego.cross.xml.Procedimento;

public class SuapEndoProcedimentiIdUpdater  extends BaseHandler{

	String FILE_EXTRACTOR_QUERY = "select p.id_pratica, a.path_file "
			+ "from pratica p inner join pratiche_eventi pe on p.id_pratica = pe.id_pratica "
			+ "inner join pratiche_eventi_allegati pea on pea.id_pratica_evento = pe.id_pratica_evento "
			+ "inner join allegati a on a.id = pea.id_allegato "
			+ "where p.id_processo in (36,37,38,39) and a.nome_file like '%.SUAP.XML' "
			+ "and exists(select 1 from pratica_procedimenti ppr where ppr.id_pratica = p.id_pratica and ppr.id_endoprocedimento_suap = -1) "
			+ "order by p.id_pratica asc";

	private LookupDao lookupDao;
	private PraticaDao praticaDao;
	private ProcedimentiDao procedimentiDao;
	private EntiDao entiDao;
	private UsefulDao usefulDao;
	public SuapEndoProcedimentiIdUpdater(MySql mysql, CrossJpa jpa) {
		super(mysql, jpa);
	}

	@Override
	public void run() throws Exception {
		System.out.println("RUN " + SuapEndoProcedimentiIdUpdater.class.getName());
		EntityManager em = getJpa().get();
		lookupDao = new LookupDao(em);
		procedimentiDao = new ProcedimentiDao(em);
		entiDao = new EntiDao(em);
		praticaDao = new PraticaDao(em);
		usefulDao = new UsefulDao(em);
		System.out.println("execute " + FILE_EXTRACTOR_QUERY);
		try(Statement stmt = mysql.getStatement()){
			consumeSelect(FILE_EXTRACTOR_QUERY, set -> {
				Integer paperworkId = -1;
				try {
					paperworkId = set.getInt(1);
					String xml = new FileReader(set.getString(2)).getXml();
					String suapIdentifierAttribute= new XPathReader(xml, XPathReader.IDENTIFICATIVO_SUAP_XPATH).getAttributeValue();
					System.out.println("elaborate: " + paperworkId);
					process(paperworkId, suapIdentifierAttribute, xml);

				} 
				catch (Exception e) {
					System.out.println("Error on paperwork " + paperworkId);
					e.printStackTrace();
				}
			});
		}

	}

	public void process(Integer idPratica, String identificativoSuap, String fileSuapXml) throws Exception {
		Pratica pratica = praticaDao.findPratica(idPratica);
		List<ProcedureIdPair> procedimentiList = fromCreazionePraticaCross(identificativoSuap, fileSuapXml);
		fromPopolaProcedimentiSUAP(pratica, procedimentiList);

	}

	/*
	 * method created reading method popolaProcedimentiSUAP in PddServiceEnteWS.java
	 */
	private void fromPopolaProcedimentiSUAP(Pratica paperwork, List<ProcedureIdPair> proceedingPairs) throws Exception {

		for (ProcedureIdPair pair : proceedingPairs) {
			
				it.wego.cross.xml.Procedimento xmlProceeding = pair.getProcedimento();
				String xmlCodIntervento = pair.getCodIntervento();
				int xmlTownId = xmlProceeding.getIdEnteDestinatario().intValue();
				ProcedimentiEnti procedimentiEnti = procedimentiDao.findUfficiByProcEnte(xmlProceeding.getIdProcedimento(),xmlTownId);

//				System.out.println("XML procedimento: " + procedimento.getIdProcedimento());
//				System.out.println("XML ente: " + procedimento.getIdEnteDestinatario().intValue());

				PraticaProcedimenti currentProcedure = null;

				for(PraticaProcedimenti praticaProcedimento: paperwork.getPraticaProcedimentiList()) {
					Integer paperworkTownId = procedimentiEnti.getIdEnte().getIdEnte();
					Integer paperworkProcedureId = praticaProcedimento.getProcedimenti().getIdProc();
//					System.out.println("pratica procedimento: " + praticaIdProc);
//					System.out.println("pratica ente: " +praticaIdEnte);

					if(paperworkProcedureId.equals(xmlProceeding.getIdProcedimento())
							&& paperworkTownId.equals(xmlTownId))
						currentProcedure = praticaProcedimento;
				}
				
				if(currentProcedure == null){
//					throw new RuntimeException("Pratica " + paperwork.getIdPratica() + " - Procedimento " + pair.getProcedimento().getIdProcedimento().toString() + " NOT FOUND");
					System.out.println("\tPratica " + paperwork.getIdPratica() + " - Procedimento " + pair.getProcedimento().getIdProcedimento().toString() + " NOT FOUND");
					PraticaProcedimenti praticaProcedimenti = new PraticaProcedimenti();
					PraticaProcedimentiPK key = new PraticaProcedimentiPK();
		            if(procedimentiEnti!=null) {
			            if(procedimentiEnti.getIdUfficio()!=null) {
			            	key.setIdEnte(procedimentiEnti.getIdUfficio().getIdEnte());
			            }else {
			            	key.setIdEnte((xmlProceeding.getIdEnteDestinatario()).intValue());
			            }
		            }
		            key.setIdPratica(paperwork.getIdPratica());
		            it.wego.cross.entity.Procedimenti proc = procedimentiDao.findProcedimentoByCodProc(xmlProceeding.getCodProcedimento());
		            

		            if (proc == null) {
		                Log.WS.warn("ATTENZIONE! Procedimento con codice '" + xmlProceeding.getCodProcedimento() + "' non trovato.");
		            }else {
			            key.setIdProcedimento(proc.getIdProc());
			            praticaProcedimenti.setPraticaProcedimentiPK(key);
			            
			            LkTipoEndoProcedimentoSuap lkTipoEndoProcedimentoSuap = lookupDao.findTipoEndoProcedimentoSuapByCod(xmlCodIntervento);
			            praticaProcedimenti.setLkTipoEndoProcedimentoSuap(lkTipoEndoProcedimentoSuap);
			            key.setIdEndoprocedimentoSuap(lkTipoEndoProcedimentoSuap.getIdTipoEndoProcedimento());

//			            praticheService.salvaPraticaProcedimento(praticaProcedimenti);
//			            praticaDao.insert(praticaProcedimenti);
			            insert(praticaProcedimenti);
//			            usefulDao.flush();
//			            usefulDao.refresh(praticaProcedimenti);
//			            String descProc = procedimentiService.getDescrizioneProcedimentoLingua(1, proc.getIdProc());
//			            Log.WS.warn("pratica.endoprocedimenti.aggiungi.note" + descProc);
//
//					    praticaProcedimentiList.add(praticaProcedimenti);
		 
				    }
				}
				else {
					LkTipoEndoProcedimentoSuap lkTipoEndoProcedimentoSuap = lookupDao.findTipoEndoProcedimentoSuapByCod(xmlCodIntervento);
					//				currentPraticaProcedimento.setLkTipoEndoProcedimentoSuap(lkTipoEndoProcedimentoSuap);
					//				currentPraticaProcedimento.getPraticaProcedimentiPK().setIdEndoprocedimentoSuap(lkTipoEndoProcedimentoSuap.getIdTipoEndoProcedimento());
					save(paperwork.getIdPratica(),
							currentProcedure.getPraticaProcedimentiPK().getIdProcedimento(), 
							currentProcedure.getPraticaProcedimentiPK().getIdEnte(), 
							lkTipoEndoProcedimentoSuap.getIdTipoEndoProcedimento());
				}
			
		}
	}

	private void insert(PraticaProcedimenti praticaProcedimenti) throws SQLException {
		getMysql().setAutocommit(true);
		Statement stmt = getMysql().getStatement();
		String query = "INSERT INTO pratica_procedimenti (id_pratica,id_procedimento, id_ente,id_endoprocedimento_suap) VALUES "
				+ "("+praticaProcedimenti.getPraticaProcedimentiPK().getIdPratica()+","+
					  praticaProcedimenti.getPraticaProcedimentiPK().getIdProcedimento()+","+
					  praticaProcedimenti.getPraticaProcedimentiPK().getIdEnte()+","+
					  praticaProcedimenti.getPraticaProcedimentiPK().getIdEndoprocedimentoSuap()+")";
		System.out.println("\tINSERT: " +query);
		stmt.execute(query);
		stmt.close();
	}

	private void save(Integer idPratica, Integer idProcedure, Integer townId, Integer idTipoEndoProcedimentoSuap) throws SQLException {
		getMysql().setAutocommit(true);
		Statement stmt = getMysql().getStatement();
		String query = "update pratica_procedimenti set id_endoprocedimento_suap = " + idTipoEndoProcedimentoSuap + " "+
						"where id_pratica =" + idPratica +" and id_procedimento = "+ idProcedure + " and id_ente=" + townId ;
		System.out.println("\tUPDATE: " +query);
		stmt.executeUpdate(query);
		stmt.close();
	}

	/*
	 * method created reading method creazionePraticaCross in PddServiceEnteWS.java
	 */
	private List<ProcedureIdPair> fromCreazionePraticaCross(String identificativoSuap, String fileSuapXml) throws IOException, JAXBException {
		RiepilogoPraticaSUAP riepilogoPraticaSUAP = null;
		riepilogoPraticaSUAP = getRiepilogoPraticaSUAP(fileSuapXml);
		Enti enteSuap = entiDao.findByIdentificativoSuap(identificativoSuap);

		String codProcedimento = "";
		Procedimenti procedimentoXml = null;
		String codiceIntervento = "";
		List<String> listaInterventi = new ArrayList<String>();
		if(riepilogoPraticaSUAP!=null) {
			if(riepilogoPraticaSUAP.getStruttura()!=null) {
				if(!riepilogoPraticaSUAP.getStruttura().getModulo().isEmpty()) {
					List<AdempimentoSUAP> listAdempimenti = riepilogoPraticaSUAP.getStruttura().getModulo();

					for(AdempimentoSUAP adempimentoSUAP: listAdempimenti) {
						codiceIntervento = adempimentoSUAP.getCod();
						List<EstremiEnte> enti = adempimentoSUAP.getEnteCoinvolto();
						EstremiEnte estremiEnti = enti.get(0);
						estremiEnti.getCodiceAmministrazione();
						listaInterventi.add(codiceIntervento);
					}
				}
			}
		}
		LkTipoEndoProcedimentoSuap lkTipoEndoProcedimentoSuap = null;
		List<it.wego.cross.entity.Procedimenti> listProcedimenti = null;
		List<ProcedureIdPair> procedimentiList = new ArrayList<>();
		for(String codIntervento : listaInterventi) {
			//codProcedimento = InterventoProcedimentoDTO.INTERV_PROCEDIMENTI.get(codIntervento);
			lkTipoEndoProcedimentoSuap = lookupDao.findTipoEndoProcedimentoSuapByCod(codIntervento);
			if(lkTipoEndoProcedimentoSuap!=null) {
				codProcedimento = lkTipoEndoProcedimentoSuap.getCodProcedimento();
				if(codProcedimento!=null) {
					it.wego.cross.entity.Procedimenti procedimento = procedimentiDao.findProcedimentoByCodProc(codProcedimento);
					if(procedimento!=null) {
						ProcedimentiEnti pe = procedimentiDao.findProcedimentiEntiByProcedimentoEnte(enteSuap.getIdEnte(), procedimento.getIdProc());
						if(pe!=null) {
							procedimentoXml = new Procedimenti();

							Procedimento proc = new Procedimento();
							proc = ProcedimentiSerializer.serializeXML(pe);
							proc.setIdEnteDestinatario(BigInteger.valueOf(enteSuap.getIdEnte()));
							proc.setCodEnteDestinatario(enteSuap.getCodEnte());
							proc.setCodProcedimento(procedimento.getCodProc());
							proc.setIdProcedimento(procedimento.getIdProc());
							//codInterventoList.add(codIntervento);
							procedimentiList.add(new ProcedureIdPair(proc, codIntervento)); 
						}
					}
				}
			}
		}
		return procedimentiList;
	}

	private RiepilogoPraticaSUAP getRiepilogoPraticaSUAP(String xmlPratica) throws IOException, JAXBException {
		Log.WS.info("Unmarshal suap.xml - start");
		//		byte[] bytesArray = Utils.readByteArrayFromFile(fileXml);
		//		String xmlPratica = new String(bytesArray);
		xmlPratica = Utils.encodingXml(xmlPratica);
		JAXBContext context = JAXBContext.newInstance(RiepilogoPraticaSUAP.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		StringReader reader = new StringReader(xmlPratica);
		RiepilogoPraticaSUAP riepilogoPraticaSUAP = null;
		riepilogoPraticaSUAP = (RiepilogoPraticaSUAP) unmarshaller.unmarshal(reader);
		Log.WS.info("Unmarshal suap.xml - end");
		return riepilogoPraticaSUAP; 
	}
}
