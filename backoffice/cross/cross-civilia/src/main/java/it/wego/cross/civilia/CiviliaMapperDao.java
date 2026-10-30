package it.wego.cross.civilia;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import it.wego.cross.civilia.sql.Dao;
import it.wego.cross.civilia.utilities.LogClass;
import it.wego.cross.civilia.utilities.myUtility;
import it.wego.cross.dto.AllegatoDTO;
import it.wego.cross.entity.Anagrafica;
import it.wego.cross.entity.DatiCatastali;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticaAnagrafica;
import it.wego.cross.entity.PraticaProcedimenti;

@Component
public class CiviliaMapperDao {

	public static LogClass logger;

	public CiviliaMapperDao() {
		//checkIsRunnig();
	}

	public void checkIsRunnig(){
		myUtility utl = new myUtility();
		String sAbilitazione_civilia = utl.getValueProperties("abilitazione_civilia");

		if(sAbilitazione_civilia.equals("SI")) {
			
			Dao dao = new Dao();dao.openConnection(); 
					
			try {
				String data_ora = dao.queryString("SELECT NOW() AS data_ora", "data_ora");
				LogClass.info("CIVILIA DAO is running!" + data_ora);
				
				System.out.println("CIVILIA DAO is running!" + data_ora);
			} catch (Exception e) {
				LogClass.info("CIVILIA DAO is not running!");
				
				System.out.println("CIVILIA DAO is not running!");
	
				e.printStackTrace();
			}
			
			dao.closeConnection();
		}
		
	}
	
	public List<PraticaAnagrafica> getListAnagraficheById(String idPratica){
		List<PraticaAnagrafica> praticaAnagrafica = new ArrayList<PraticaAnagrafica>();

		myUtility utl = new myUtility();
		String sAbilitazione_civilia = utl.getValueProperties("abilitazione_civilia");
		if(sAbilitazione_civilia.equals("SI")) {

			LogClass.trace("CiviliaMapperDao::getListAnagraficheById START idPratica:" + idPratica);
			System.out.println("CiviliaMapperDao::getListAnagraficheById START idPratica:" + idPratica);
			
			Dao dao = new Dao();dao.openConnection(); 
			
			LogClass.trace("CiviliaMappeDao::getListAnagraficheById GET CONNECTION -OK-");
	
			try {
				LogClass.trace("CiviliaMappeDao::getListAnagraficheById Start listaAnagrafiche");
				praticaAnagrafica = dao.getListAnagraficheById(idPratica);
				int listavuota = praticaAnagrafica != null ? praticaAnagrafica.size():0;
				LogClass.trace("CiviliaMappeDao::getListAnagraficheById end listaAnagrafiche, size:" + listavuota);
			} catch (SQLException e) {
				e.printStackTrace();
				LogClass.error(e.getMessage());
			}
			
			dao.closeConnection();
			
			LogClass.trace("CiviliaMappeDao::getListAnagraficheById END");
		}
		return praticaAnagrafica;		
	}
	public List<PraticaProcedimenti> getListEndoprocedimentiById(String idPratica) {
		List<PraticaProcedimenti> listPraticaProcedimenti = new ArrayList<PraticaProcedimenti>();

		myUtility utl = new myUtility();
		String sAbilitazione_civilia = utl.getValueProperties("abilitazione_civilia");
		if(sAbilitazione_civilia.equals("SI")) {
			
			Dao dao = new Dao();dao.openConnection(); 
			
			try {
				listPraticaProcedimenti = dao.getListEndoprocedimentiById(idPratica);
			} catch (SQLException e) {
				e.printStackTrace();
				LogClass.error(e.getMessage());
			}
			
			dao.closeConnection();
		}

		return listPraticaProcedimenti;
	
	}
	public List<DatiCatastali> getListDatiCatastaliById(String idPratica) {
		List<DatiCatastali> listDatiCatastali = new ArrayList<DatiCatastali>();

		myUtility utl = new myUtility();
		String sAbilitazione_civilia = utl.getValueProperties("abilitazione_civilia");
		if(sAbilitazione_civilia.equals("SI")) {
			
			Dao dao = new Dao();dao.openConnection(); 		
	
			try {
				listDatiCatastali = dao.getListDatiCatastaliById(idPratica);
			} catch (SQLException e) {
				e.printStackTrace();
				LogClass.error(e.getMessage());
			}
			
			dao.closeConnection();
		}
		return listDatiCatastali;
	
	}
	public List<AllegatoDTO> getListAllegatiPratica(String idPratica) {
		List<AllegatoDTO> listAllegati = new ArrayList<AllegatoDTO>();

		myUtility utl = new myUtility();
		String sAbilitazione_civilia = utl.getValueProperties("abilitazione_civilia");
		if(sAbilitazione_civilia.equals("SI")) {

			Dao dao = new Dao();dao.openConnection(); 		
	
			try {
				listAllegati = dao.getListAllegatiPratica(idPratica);
			} catch (SQLException e) {
				e.printStackTrace();
				LogClass.error(e.getMessage());
			}
			
			dao.closeConnection();
		}
		return listAllegati;
	
	}
	
	public Anagrafica findAnagraficaById(Integer idAnagrafica) {
		Anagrafica angrafica = new Anagrafica();

		myUtility utl = new myUtility();
		String sAbilitazione_civilia = utl.getValueProperties("abilitazione_civilia");
		if(sAbilitazione_civilia.equals("SI")) {
			
			Dao dao = new Dao();dao.openConnection(); 
			
			try {
				angrafica = dao.getAnagraficaById(idAnagrafica);
			} catch (SQLException e) {
				e.printStackTrace();
				LogClass.error(e.getMessage());
			} finally {
				dao.closeConnection();
			}
		}		
		return angrafica;
	
	}
		
	public static void main(String[] args) {
		
/*		
 * 
 		CiviliaMapperDao civiliaMapper = new CiviliaMapperDao();
		civiliaMapper.checkIsRunnig();
		String idPratica = "SCIA-999-2013";
		Pratica pratica = new Pratica();
        List<PraticaAnagrafica> praticaAnagraficaList = new ArrayList<PraticaAnagrafica>();
        //List<PraticaAnagrafica> praticaAnagraficaCross = pratica.getPraticaAnagraficaList();
        List<PraticaAnagrafica> praticaAnagraficaCross = new ArrayList<PraticaAnagrafica>();
        List<PraticaAnagrafica> praticaAnagraficaCivilia = civiliaMapper.getListAnagraficheById(idPratica);
        praticaAnagraficaList.addAll(praticaAnagraficaCross);
        praticaAnagraficaList.addAll(praticaAnagraficaCivilia);

        for (PraticaAnagrafica pa : praticaAnagraficaList) {
						
			LogClass.debug("getListAnagraficheById ANAGRAFICA ID:" + pa.getAnagrafica().getIdAnagrafica() 
			        + " NOME:" + pa.getAnagrafica().getNome() 
					+ " COGNOME:" + pa.getAnagrafica().getCognome()
					+ " CODICE FISCALE:" + pa.getAnagrafica().getCodiceFiscale()
					+ " DENOMINAZIONE:" + pa.getAnagrafica().getDenominazione()
					+ " P.IVA:" + pa.getAnagrafica().getPartitaIva()						
					+ " TIPOANAGRAFICA:"  + pa.getAnagrafica().getTipoAnagrafica()
					+ " VARIANTE ANAGRAFICA:"  + pa.getAnagrafica().getVarianteAnagrafica()
					+ " ID ANAGRAFICA:"  + pa.getAnagrafica().getIdAnagrafica()
//						+ " PROVENIENZA:"  + pa.getAnagrafica().getProvenienza()
					+ " FLG INDIVIDUALE:"  + pa.getFlgDittaIndividuale() 
					+ " RUOLO CROSS:" + pa.getLkTipoRuolo().getDescrizione()
					+ " RUOLO CIVILIA:" + pa.getAnagrafica().getRuolo_civilia() );
			
		}
        int idAnagrafica = 615873;
        Anagrafica a = civiliaMapper.findAnagraficaById(idAnagrafica);
        LogClass.debug("findAnagraficaById ANAGRAFICA ID:" + a.getIdAnagrafica() 
		        + " NOME:" + a.getNome() 
				+ " COGNOME:" + a.getCognome()
				+ " CODICE FISCALE:" + a.getCodiceFiscale()
				+ " DENOMINAZIONE:" + a.getDenominazione()
				+ " P.IVA:" + a.getPartitaIva()						
				+ " TIPOANAGRAFICA:"  + a.getTipoAnagrafica()
				+ " VARIANTE ANAGRAFICA:"  + a.getVarianteAnagrafica()
				+ " ID ANAGRAFICA:"  + a.getIdAnagrafica()
				+ " RUOLO CIVILIA:" + a.getRuolo_civilia() );
        
        List<DatiCatastali> daticatastaliList = civiliaMapper.getListDatiCatastaliById(idPratica);
        for (DatiCatastali dt : daticatastaliList) {
			LogClass.debug(" DATI CATASTALI - CATEGORIA:" + dt.getCategoria()
					+ " tipoParticella:" + dt.getIdTipoParticella().getDescrizione()
					+ " foglio:" + dt.getFoglio()
					+ " mappale:" + dt.getMappale()
					+ " sezione:" + dt.getSezione()						
					+ " tipoUnita:"  + dt.getIdTipoUnita().getDescrizione()
					+ " estensioneParticella:"  + dt.getEstensioneParticella()
					+ " subalterno:"  + dt.getSubalterno()
					+ " comuneCensuario:"  + dt.getComuneCensuario());								
		}
        List<PraticaProcedimenti> datiprocedimentiList = civiliaMapper.getListEndoprocedimentiById(idPratica);
        for (PraticaProcedimenti dp : datiprocedimentiList) {
			LogClass.debug(" PROCEDIMENTI - descrizione:" + dp.getProcedimenti().getTipoProc()
					+ " Ente:" + dp.getEnti().getDescrizione()
					+ " termini:" + dp.getProcedimenti().getTermini()
					);								
		}
        
        List<AllegatoDTO> allegatiList = civiliaMapper.getListAllegatiPratica(idPratica);
        for (AllegatoDTO al : allegatiList) {
			LogClass.debug(" ALLEGATI - descrizione:" + al.getDescrizione()
					+ " nomeFile:" + al.getNomeFile()
					+ " path:" + al.getPathFile()
					);								
		}	   
* */
	}

}
