package it.exprivia.pal.avbari.suapsue.portlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;

import it.exprivia.pal.avbari.suapsue.dto.SportelloEnte;
import it.exprivia.pal.avbari.suapsue.dto.TipoPratica;
import it.exprivia.pal.avbari.suapsue.service.ServiceLocator;

public class ServiziPortlet extends MainPortlet {
	
	private static final Logger logger = LoggerFactory.getLogger(ServiziPortlet.class);
	
	
	public void doView(RenderRequest request, RenderResponse response) throws IOException, PortletException {
		if (logger.isDebugEnabled())
		{
			logger.debug("Metodo doView - inizio");
		}
		final ThemeDisplay td = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		final TipoPratica tipo = TipoPratica.decode(td.getLayout().getName(td.getLocale()));
		
		Collection<SportelloEnte> sportelliAttiviList = null;
		try {
			sportelliAttiviList = ServiceLocator.getSportelloService().findByTipoAndStato(tipo, true);
		} catch (SQLException e) {
			logger.error("Errore nel metodo doView {}", e.getMessage(), e);
			SessionErrors.add(request, "errore.comuni");
		}
		
		request.setAttribute("sportelliAttiviList", sportelliAttiviList);
		
		inviaPagina(viewJSP, request, response);
		
		if (logger.isDebugEnabled())
		{
			logger.debug("Metodo doView - fine");
		}
	}
}