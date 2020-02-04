package it.exprivia.pal.avbari.suapsue.service.impl;

import java.io.IOException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Properties;

import javax.xml.ws.WebServiceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.exprivia.pal.avbari.suapsue.dto.FiltriRicercaPratica;
import it.exprivia.pal.avbari.suapsue.dto.Pratica;
import it.exprivia.pal.avbari.suapsue.dto.SportelloEnte;
import it.exprivia.pal.avbari.suapsue.service.PraticaService;
import it.exprivia.pal.avbari.suapsue.service.ServiceLocator;
import it.exprivia.pal.avbari.suapsue.service.SportelloNonAttivoException;
import it.exprivia.pal.avbari.suapsue.service.SportelloService;
import it.exprivia.pal.util.PropertiesUtil;
import it.wego.cross.CripalPraticheService;
import it.wego.cross.CripalPraticheServiceProxy;
import it.wego.cross.ElencoPraticheRequest;
import it.wego.cross.ElencoPraticheResponse;
import it.wego.cross.ObjectFactory;
import it.wego.cross.PraticaDetailRequest;
import it.wego.cross.PraticaDetailResponse;
import it.wego.cross.PraticaSIT;

public class PraticaServiceImpl implements PraticaService {
	
	protected CripalPraticheService service;
	
	protected SportelloService sportelloService;
	
	protected final ObjectFactory factory;
	
	protected final static DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	private static final Logger logger = LoggerFactory.getLogger(PraticaServiceImpl.class);
	
	
	/**
	 * Costruttore
	 * @throws SQLException 
	 */
	public PraticaServiceImpl() throws SQLException {
		sportelloService = ServiceLocator.getSportelloService();
		
		factory = new ObjectFactory();
	}
	
	@Override
	public Collection<Pratica> find(FiltriRicercaPratica filtri) throws RemoteException {
		
		
		if (logger.isDebugEnabled())
		{
			logger.debug("METODO find. FILTRI SELEZIONATI {} ",filtri);
		}
		try {
			chkSportelloAttivo(filtri);
			
			final ElencoPraticheRequest request = adaptToElencoPraticheRequest(filtri);
			
			if (logger.isDebugEnabled())
				logger.debug(String.format("Chiamata di getPraticheList: %s", request));
			
			initProxy();
			final ElencoPraticheResponse response = service.getPraticheList(request);
			
			if (logger.isDebugEnabled())
				logger.debug(String.format("Ottenuta response con %d pratiche", response.getPratica() != null ? response.getPratica().size() : 0));
			
			final Collection<Pratica> elenco = adaptToPraticaCollection(response);
			
			if (logger.isDebugEnabled())
				logger.debug(String.format("Pratiche ritornate: %d", elenco.size()));
			
			return elenco;
		} catch (SportelloNonAttivoException e) {
			logger.warn("METODO FIND. ECCEZIONE {} MESSAGGIO {}", e.getClass().getName(), e.getMessage(), e);
			return Collections.emptyList();
		} catch (Throwable e) {
			logger.error("METODO FIND. ECCEZIONE {} MESSAGGIO {}", e.getClass().getName(), e.getMessage(), e);
			throw new RemoteException("", e);
		}
	}
	
	private void chkSportelloAttivo(FiltriRicercaPratica filtri) throws SportelloNonAttivoException {
		try {
			final SportelloEnte sp = sportelloService.findById(filtri.getIdSportello());
			
			if (!sp.isFlagAttivo())
				throw new SportelloNonAttivoException(filtri.getIdSportello());
		} catch (SQLException e) {
			throw new SportelloNonAttivoException(e);
		}
	}
	
	private ElencoPraticheRequest adaptToElencoPraticheRequest(final FiltriRicercaPratica filtri) throws SQLException {
		
		
		final SportelloEnte sportello = sportelloService.findById(filtri.getIdSportello().longValue());
		if (sportello == null)
			throw new IllegalArgumentException("sportello non trovato");
		
		final ElencoPraticheRequest request = factory.createElencoPraticheRequest();
		request.setIdEnte(new BigInteger(sportello.getCodSportello().toString()));
		request.setIdComune(new BigInteger(sportello.getCodComune().toString()));
		request.setDataRicezioneDa(df.format(filtri.getDataInizio()));
		request.setDataRicezioneA(df.format(filtri.getDataFine()));
		
		if (logger.isDebugEnabled())
			logger.debug("FiltriRicercaPratica convertiti in ElencoPraticheRequest");
		
		return request;
	}
	
	private void initProxy() throws RemoteException, MalformedURLException, IOException {
		try {
			final Properties prop = PropertiesUtil.getProperties();
			final String wsdlLocation = prop.getProperty("wsUrl");
			
			if (logger.isDebugEnabled())
				logger.debug(wsdlLocation);
			
			CripalPraticheServiceProxy proxy = new CripalPraticheServiceProxy(new URL(wsdlLocation));
			
			if (logger.isDebugEnabled())
				logger.debug("proxy WS Cross inizializzato");
			
			service = proxy.getCripalPraticheService();
			
			if (logger.isDebugEnabled())
				logger.debug("client WS Cross inizializzato");
		} catch (WebServiceException e) {
			throw new RemoteException("endpoint WS non raggiungibile", e);
		}
	}
	
	private Collection<Pratica> adaptToPraticaCollection(final ElencoPraticheResponse response) {

		Collection<Pratica> elenco = new LinkedList<Pratica>();
		if (response != null && response.getPratica() != null && !response.getPratica().isEmpty()) {
			for (ElencoPraticheResponse.Pratica p: response.getPratica()) {
				Pratica pratica = new Pratica();
				pratica.setIdPratica(p.getIdPratica());
				pratica.setIdEnte(p.getIdEnte());
				pratica.setDescrizioneEnte(p.getDescrizioneEnte());
				pratica.setIdComune(p.getIdComune());
				pratica.setDescrizioneComune(p.getDescrizioneComune());
				pratica.setIdentificativoPratica(p.getIdentificativoPratica());
				pratica.setDataRicezione(p.getDataRicezione());
				pratica.setCodiceStatoPratica(p.getCodiceStatoPratica());
				pratica.setDescrizioneStatoPratica(p.getDescrizioneStatoPratica());
				pratica.setOggetto(p.getOggetto());
				pratica.setUbicazione(p.getUbicazione());
				
				elenco.add(pratica);
			}
			
			if (logger.isDebugEnabled())
				logger.debug("ElencoPraticheResponse convertita in Collection<Pratica>");
		} else
			logger.warn("nessuna pratica da convertire");
		
		return elenco;
	}
	
	@Override
	public PraticaSIT findById(Long idPratica) throws RemoteException {

		if (logger.isDebugEnabled())
			logger.debug("METODO findById. RICERCA PRATICA CON ID {}",idPratica);
		
		try {
			final PraticaDetailRequest request = factory.createPraticaDetailRequest();
			request.setIdPratica(new Integer(idPratica.intValue()));
			
			if (logger.isDebugEnabled())
				logger.debug(String.format("Chiamata di getPraticaDetail: %s", request));
			
			initProxy();
			final PraticaDetailResponse response = service.getPraticaDetail(request);
			
			if (logger.isDebugEnabled())
				logger.debug(String.format("Ottenuta response con %d pratica", response.getPratica() != null ? 1 : 0));
			
			return response.getPratica();
		} catch(Throwable e) {
			logger.error("METODO findById; ERRORE RICERCA PRATICA ID {}; {}", idPratica, e.getMessage(), e);
			throw new RemoteException("METODO findById; ERRORE RICERCA PRATICA", e);
		}
	}
}