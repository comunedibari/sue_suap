package CiviliaDao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import CiviliaDao.sql.Dao;
import CiviliaDao.utilities.LogClass;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticaAnagrafica;

public class CiviliaMapperDao {

	public static LogClass logger;

	public CiviliaMapperDao() {}

//	public List<PraticaAnagraficaDTO> getListAnagraficheById(String idPratica) throws SQLException {
	public List<PraticaAnagrafica> getListAnagraficheById(String idPratica) throws SQLException {

		Dao dao = new Dao();dao.openConnection(); 
		
		List<PraticaAnagrafica> praticaAnagrafica = null;

		praticaAnagrafica = dao.getListAnagraficheById(idPratica);
		
		dao.closeConnection();
		
		return praticaAnagrafica;		
	}
	public static void main(String[] args) {
		CiviliaMapperDao civiliaMapper = new CiviliaMapperDao();
		String idPratica = "CIL-538-2018";
		try {							
			Pratica pratica = new Pratica();
	        List<PraticaAnagrafica> praticaAnagraficaList = new ArrayList<PraticaAnagrafica>();
	        //List<PraticaAnagrafica> praticaAnagraficaCross = pratica.getPraticaAnagraficaList();
	        List<PraticaAnagrafica> praticaAnagraficaCross = new ArrayList<PraticaAnagrafica>();
	        List<PraticaAnagrafica> praticaAnagraficaCivilia = civiliaMapper.getListAnagraficheById(idPratica);
	        praticaAnagraficaList.addAll(praticaAnagraficaCross);
	        praticaAnagraficaList.addAll(praticaAnagraficaCivilia);

	        for (PraticaAnagrafica pa : praticaAnagraficaList) {
							
				LogClass.debug(" NOME:" + pa.getAnagrafica().getNome() 
						+ " COGNOME:" + pa.getAnagrafica().getCognome()
						+ " CODICE FISCALE:" + pa.getAnagrafica().getCodiceFiscale()
						+ " DENOMINAZIONE:" + pa.getAnagrafica().getDenominazione()
						+ " P.IVA:" + pa.getAnagrafica().getPartitaIva()						
						+ " TIPOANAGRAFICA:"  + pa.getAnagrafica().getTipoAnagrafica()
						+ " VARIANTE ANAGRAFICA:"  + pa.getAnagrafica().getVarianteAnagrafica()
						+ " ID ANAGRAFICA:"  + pa.getAnagrafica().getIdAnagrafica()
						+ " PROVENIENZA:"  + pa.getAnagrafica().getProvenienza()
						+ " FLG INDIVIDUALE:"  + pa.getFlgDittaIndividuale() 
						+ " RUOLO:" + pa.getLkTipoRuolo().getDescrizione() );								
			}
						
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
