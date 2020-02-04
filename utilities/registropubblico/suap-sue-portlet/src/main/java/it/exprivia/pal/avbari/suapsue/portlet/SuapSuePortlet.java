package it.exprivia.pal.avbari.suapsue.portlet;

import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;

import it.exprivia.pal.avbari.suapsue.dto.FiltriRicercaPratica;
import it.exprivia.pal.avbari.suapsue.dto.Pratica;
import it.exprivia.pal.avbari.suapsue.dto.SportelloEnte;
import it.exprivia.pal.avbari.suapsue.dto.TipoPratica;
import it.exprivia.pal.avbari.suapsue.service.ServiceLocator;
import it.wego.cross.PraticaSIT;

public class SuapSuePortlet extends MainPortlet  {
	
	private static final String EDIT_PAGE = "/html/suapsue/edit.jsp";
	
	private static final Logger logger = LoggerFactory.getLogger(SuapSuePortlet.class);
	
	
	public void doView(RenderRequest request, RenderResponse response) {
		if( logger.isDebugEnabled() )
		{
		
			logger.debug("METODO doView.");
		}
		aggiornaStato(request);

		setComuneAttivo(request);

		final String azione = ParamUtil.getString(request, "azione");
		if (azione.equalsIgnoreCase("dettaglio"))
			dettaglioPratica(request, response);
		else {
			if (azione.equalsIgnoreCase("ricerca"))
				ricercaPratiche(request, response);

			inviaPagina(viewJSP, request, response);
		}

	}
	
	private void aggiornaStato(RenderRequest request) {


		final String en = getParamString(request, "en");
		final String st = getParamString(request, "st");	
		if( logger.isDebugEnabled() )
		{
			logger.debug("METODO aggiornaStato. ente {}, stato {}", en, st);
		}
		if (en != null && st != null && !en.isEmpty() && !st.isEmpty()) {
			try {
				ServiceLocator.getSportelloService().updateStatoSportello(Integer.parseInt(en), Boolean.parseBoolean(st));
				
				request.setAttribute("aggStato", String.format("Stato aggiornato per l'ente con idEnte: %s", en));
			} catch (Throwable e) {
				logger.error("Errore nel metodo aggiornaStato {}", e.getMessage(), e);
				
				String msg = e.getLocalizedMessage();
				if (e instanceof NumberFormatException)
					msg = "E6";
				
				request.setAttribute("aggStato", String.format("Errore nella modifica dello stato dell'ente con id: %s %s", en, msg));
			}
		}
	}
	
	private void setComuneAttivo(RenderRequest request) {
		
		final String codComune = getParamString(request, "comune");
		if( logger.isDebugEnabled() )
		{
			logger.debug("METODO setComuneAttico. codice comune {}", codComune);
		}
		final ThemeDisplay td = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		final TipoPratica tipo = TipoPratica.decode(td.getLayout().getName(td.getLocale()));
		
		try {
			List<SportelloEnte> listaSportelli = (List<SportelloEnte>) ServiceLocator.getSportelloService().findByTipo(tipo);

			if (codComune == null || codComune.equals("")) {
				request.setAttribute("listaSportelli", listaSportelli);
				
				final String idSportelloString = ParamUtil.getString(request, "idSportello");
				
				SportelloEnte se = null;
				if (idSportelloString != null && !idSportelloString.isEmpty())
					se = ServiceLocator.getSportelloService().findById(Long.parseLong(idSportelloString));
				else
					se = listaSportelli.get(0);
				
				request.setAttribute("idSportello", se.getId());
				request.setAttribute("urlServizio", se.getUrlPagina());
				request.setAttribute("urlPeople", se.getUrl());
				request.setAttribute("flagAttivo", se.isFlagAttivo());
			} else {
				final Integer comune = Integer.valueOf(codComune);
				
				for (SportelloEnte se: listaSportelli) {
					if (se.getComuneEgov().compareTo(comune) == 0) {
						request.setAttribute("idSportello", se.getId());
						request.setAttribute("nomeComune", se.getDenominazione());
						request.setAttribute("urlServizio", se.getUrlPagina());
						request.setAttribute("urlPeople", se.getUrl());
						request.setAttribute("comuneEgov", se.getComuneEgov());	
						request.setAttribute("flagAttivo", se.isFlagAttivo());
						break;
					}
				}
			}
		} catch (Throwable e) {
			logger.error("Errore in setComuneAttivo {}", e.getMessage(), e);
			SessionErrors.add(request, "errore.comuni");
		}
	}
	
	private void dettaglioPratica(RenderRequest request, RenderResponse response) {

		
		final Long idPratica = new Long(ParamUtil.getLong(request, "idPratica"));
		if( logger.isDebugEnabled() )
		{
			logger.debug("METODO dettaglioPratica. ID Pratica {}", idPratica);
		}		
		PraticaSIT pratica = new PraticaSIT();
		try {
			pratica = ServiceLocator.getPraticaService().findById(idPratica);
		} catch (RemoteException e) {
			logger.error("errore nel raggiungimento dell'End Point {}", e.getMessage(), e);
			SessionErrors.add(request, "errore.end.point");
		} catch (SQLException e) {
			logger.error("errore durante la ricerca della pratica", e);
			SessionErrors.add(request, "errore.dettaglio.pratica");
		} catch (IOException e) {
			logger.error("IO Exception", e);
			SessionErrors.add(request,"errore.generico");
		} catch (Error e) {
			logger.error("Pratica senza protocollo", e);
			SessionErrors.add(request,"errore.pratica.protocollo");
		}
		
		request.setAttribute("pratica",pratica);
		
		final String currentURL = ParamUtil.getString(request, "currentURL");
		String cur = "1";
		if (currentURL != null && !currentURL.equals("") && currentURL.contains("cur="))
			cur = currentURL.substring(currentURL.lastIndexOf("cur=")+4, currentURL.length());
				
		final Long idSportello = new Long(ParamUtil.getLong(request, "idSportello"));				
		final String data_da = ParamUtil.getString(request, "data_da");				
		final String data_a = ParamUtil.getString(request, "data_a");				
		final String nomeComune = ParamUtil.getString(request, "nomeComune");
					
		PortletURL backLink = response.createRenderURL();
		backLink.setParameter("azione", "ricerca");
		backLink.setParameter("idSportello", idSportello.toString());
		backLink.setParameter("data_da", data_da);
		backLink.setParameter("data_a", data_a);
		backLink.setParameter("cur", cur);
		backLink.setParameter("nomeComune", nomeComune);
		
		request.setAttribute("backLink", backLink.toString());
		
		inviaPagina(EDIT_PAGE, request, response);
	}
	
	private void ricercaPratiche(RenderRequest request, RenderResponse response) {

		
		final String idSportelloString = ParamUtil.getString(request, "idSportello");
		
		Long idSportello = Long.valueOf(0);
		if (idSportelloString != null && !idSportelloString.equals(""))
			idSportello = Long.parseLong(idSportelloString);
		if( logger.isDebugEnabled() )
		{
			logger.debug("METODO ricercaPratiche. ID sportello {}", idSportello);
		}
		request.setAttribute("idSportello", idSportello);
		
		final String cur = ParamUtil.getString(request, "cur");
		request.setAttribute("cur", cur);
		
		final String nomeComune = ParamUtil.getString(request, "nomeComune");
		if (nomeComune != null && !nomeComune.isEmpty())
			request.setAttribute("nomeComune", nomeComune);
		
		final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		final String data_da = ParamUtil.getString(request, "data_da");
		request.setAttribute("data_da", data_da);
		
		Date dataInizio = null;
		if (data_da != null && !data_da.isEmpty()) {
			try {
				dataInizio = sdf.parse(data_da);
			} catch (ParseException e) {
				logger.error("data inizio non valida");
				SessionErrors.add(request, "errore.data.inizio");
			}
		}
		
		final String data_a = ParamUtil.getString(request, "data_a");
		request.setAttribute("data_a", data_a);
		
		Date dataFine = null;
		if (data_a != null && !data_a.isEmpty()) {
			try {
				dataFine = sdf.parse(data_a);
			} catch (ParseException e) {
				logger.error("data fine non valida");
				SessionErrors.add(request, "errore.data.fine");
			}
		}
		
		Collection<Pratica> listaPratiche = new ArrayList<Pratica>();
		try {
			final FiltriRicercaPratica filtri = new FiltriRicercaPratica();
			filtri.setIdSportello(idSportello);
			filtri.setDataInizio(dataInizio);
			filtri.setDataFine(dataFine);
			
			listaPratiche = ServiceLocator.getPraticaService().find(filtri);
			
			if (logger.isDebugEnabled())
				logger.debug("ricercaPratiche - lista pratiche {}",listaPratiche);
		} catch (Exception e) {
			logger.error("errore durante la ricerca delle pratiche", e);
			SessionErrors.add(request,"errore.ricerca.pratiche");
		}
		
		request.setAttribute("listaPratiche", listaPratiche);
		
		PortletURL iteratorUrl = response.createRenderURL();
		iteratorUrl.setParameter("azione", "ricerca");
		iteratorUrl.setParameter("idSportello", idSportelloString);
		iteratorUrl.setParameter("data_da", data_da);
		iteratorUrl.setParameter( "data_a", data_a);
		iteratorUrl.setParameter( "nomeComune", nomeComune);
		
		request.setAttribute("iteratorUrl", iteratorUrl);
	}
	
	public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) {

		
		final String azione = ParamUtil.get(resourceRequest, "azioneAjax", StringPool.BLANK);
		
		if (azione.equalsIgnoreCase("findStatoSportello"))
			findStatoSportello(resourceRequest, resourceResponse);
		
	}
	
	private void findStatoSportello(ResourceRequest resourceRequest, ResourceResponse resourceResponse) {

		
		final String idString = ParamUtil.get(resourceRequest, "id", StringPool.BLANK);
		
		JSONObject jsonObj = JSONFactoryUtil.createJSONObject();
		Integer id = new Integer(0);
		try {
			id = Integer.valueOf(idString);
			
			final SportelloEnte sportelloEnte = ServiceLocator.getSportelloService().findById(id);
			
			final String flagAttivo = sportelloEnte.isFlagAttivo() ? "1" : "0";
			jsonObj.put("flagAttivo", flagAttivo);
			jsonObj.put("url", sportelloEnte.getUrl());
			jsonObj.put("urlPagina", sportelloEnte.getUrlPagina());
			
			resourceResponse.getWriter().write(jsonObj.toString());
		} catch (Exception e) {
			logger.error(String.format("errore nella ricerca dell'ente con id: %s", idString), e);
		}		
	}
}