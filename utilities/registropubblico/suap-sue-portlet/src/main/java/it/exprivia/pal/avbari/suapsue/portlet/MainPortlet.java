package it.exprivia.pal.avbari.suapsue.portlet;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.liferay.portal.util.PortalUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

public abstract class MainPortlet extends MVCPortlet {
	
	private static final Logger logger = LoggerFactory.getLogger(MainPortlet.class);
	
	
	protected final String getParamString(RenderRequest request, String nomeParam) {
		return PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(request)).getParameter(nomeParam);
	}
	
	protected final void inviaPagina(String pagina, RenderRequest request, RenderResponse response) {
		try {
			include(pagina, request, response);
		} catch (Exception e) {
			logger.error("Errore nel metodo inviaPagina {}", e.getMessage(),e);
		}
	}
}