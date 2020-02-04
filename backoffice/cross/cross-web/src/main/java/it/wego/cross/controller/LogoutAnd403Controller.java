package it.wego.cross.controller;

import it.wego.cross.service.AuthenticationService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LogoutAnd403Controller
extends AbstractController
{
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private AuthenticationService authenticationService;

	@RequestMapping({"/403"})
	public String add(Model model, HttpServletRequest request, HttpServletResponse response)
	{
		request.getSession().removeAttribute("ACL");
		return "403";
	}

	@RequestMapping({"/esci"})
	public String esci(Model model, HttpServletRequest request, HttpServletResponse response)
	{
		String authServer = this.authenticationService.getAuthServer();
		String authServerLogoutUrl = this.authenticationService.getAuthServerLogoutUrl();
		String postLogoutUrl = this.authenticationService.getPostLogoutUrl();
		this.logger.info("user logout");
		this.logger.info("user logout, authServer = {" + authServer + "}, authServerLogoutUrl = {" + authServerLogoutUrl + "}, postLogoutUrl = {" + postLogoutUrl + "}");
		String currentUrl=request.getRequestURL().toString();
		currentUrl=currentUrl.replace("/cross/esci.htm", "");
		
		//Per produzione in https
		//currentUrl=currentUrl.replace("http", "https");
		
		request.getSession().invalidate();
		String redirectUrl;
		if (authServer.equalsIgnoreCase("CAS"))
		{
			redirectUrl = "redirect:" +currentUrl+ authServerLogoutUrl + "?service=" +currentUrl+postLogoutUrl;
		}
		else
		{
			if (authServer.equalsIgnoreCase("SIRAC-SSO")) {
				redirectUrl = "redirect:" + authServerLogoutUrl + "?returnURL=" + postLogoutUrl;
			} else {
				redirectUrl = "redirect:" + postLogoutUrl;
			}
		}
		this.logger.info("redirect to = {}", redirectUrl);
		return redirectUrl;
	}
}
