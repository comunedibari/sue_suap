/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.utils.cas;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jasig.cas.client.authentication.AuthenticationRedirectStrategy;
import org.jasig.cas.client.authentication.ContainsPatternUrlPatternMatcherStrategy;
import org.jasig.cas.client.authentication.DefaultAuthenticationRedirectStrategy;
import org.jasig.cas.client.authentication.DefaultGatewayResolverImpl;
import org.jasig.cas.client.authentication.ExactUrlPatternMatcherStrategy;
import org.jasig.cas.client.authentication.GatewayResolver;
import org.jasig.cas.client.authentication.RegexUrlPatternMatcherStrategy;
import org.jasig.cas.client.authentication.UrlPatternMatcherStrategy;
import org.jasig.cas.client.util.CommonUtils;
import org.jasig.cas.client.util.ReflectUtils;
import org.jasig.cas.client.validation.Assertion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author aleph
 */
public class CustomAuthenticationFilter extends CustomAbstractCasFilter {

	//    public static final String CONST_CAS_GATEWAY = "_const_cas_gateway_";
	//
	////    /**
	////     * The URL to the CAS Server login.
	////     */
	//    private String casServerLoginUrl;
	//    private Map<String, String> casServerLoginUrlMap = Collections.emptyMap();
	//
	//    /**
	//     * Whether to send the renew request or not.
	//     */
	//    private boolean renew = false;
	//
	//    /**
	//     * Whether to send the gateway request or not.
	//     */
	//    private boolean gateway = false;
	//
	//    
	//    /**
	//     * Initialization method. Called by Filter's init method or by Spring.
	//     */
	//    @Override
	//    public void init() {
	//        super.init();
	//        CommonUtils.assertTrue(CommonUtils.isNotEmpty(this.casServerLoginUrl) || !this.casServerLoginUrlMap.isEmpty(), "casServerLoginUrlMap cannot be null.");
	//    }
	//
	//    @Override
	//    protected void initInternal(final FilterConfig filterConfig) throws ServletException {
	//        super.initInternal(filterConfig);
	//        setCasServerLoginUrl(getPropertyFromInitParams(filterConfig, "casServerLoginUrl", null));
	//        setCasServerLoginUrlMap(getPropertyFromInitParams(filterConfig, "casServerLoginUrlMap", null));
	//        setRenew(Boolean.parseBoolean(getPropertyFromInitParams(filterConfig, "renew", "false")));
	//        setGateway(Boolean.parseBoolean(getPropertyFromInitParams(filterConfig, "gateway", "false")));
	//    }
	//
	//
	//
	//    @Override
	//    public final void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain) throws IOException, ServletException {
	//        final HttpServletRequest request = (HttpServletRequest) servletRequest;
	//        final HttpServletResponse response = (HttpServletResponse) servletResponse;
	//        final HttpSession session = request.getSession(false);
	//        final String ticket = request.getParameter(getArtifactParameterName());
	//        final Assertion assertion = session != null ? (Assertion) session
	//                .getAttribute(CONST_CAS_ASSERTION) : null;
	//        final boolean wasGatewayed = session != null
	//                && session.getAttribute(CONST_CAS_GATEWAY) != null;
	//
	//        if (CommonUtils.isBlank(ticket) && assertion == null && !wasGatewayed) {
	//            log.debug("no ticket and no assertion found");
	//            if (this.gateway) {
	//                log.debug("setting gateway attribute in session");
	//                request.getSession(true).setAttribute(CONST_CAS_GATEWAY, "yes");
	//            }
	//
	//            final String serviceUrl = constructServiceUrl(request, response);
	//
	//            String actualCasServerLoginUrl = getFromMapByRequest(casServerLoginUrlMap, request);
	//            if (actualCasServerLoginUrl == null) {
	//                actualCasServerLoginUrl = casServerLoginUrl;
	//            }
	//            if (actualCasServerLoginUrl == null) {
	//                throw new ServletException("cannot find cas login url for client host = " + request.getRemoteHost());
	//            }
	//
	//            final String urlToRedirectTo = CommonUtils.constructRedirectUrl(actualCasServerLoginUrl, getServiceParameterName(), serviceUrl, this.renew, this.gateway);
	//
	//            if (log.isDebugEnabled()) {
	//                log.debug("redirecting to \"" + urlToRedirectTo + "\"");
	//            }
	//
	//            response.sendRedirect(urlToRedirectTo);
	//            return;
	//        }
	//
	//        if (session != null) {
	//            log.debug("removing gateway attribute from session");
	//            session.setAttribute(CONST_CAS_GATEWAY, null);
	//        }
	//
	//        filterChain.doFilter(request, response);
	//    }
	//
	//    public final void setRenew(final boolean renew) {
	//        this.renew = renew;
	//    }
	//
	//    public final void setGateway(final boolean gateway) {
	//        this.gateway = gateway;
	//    }
	//
	//
	//    public void setCasServerLoginUrlMap(String casServerLoginUrlMapString) {
	//        setCasServerLoginUrlMap(parseMapParam(casServerLoginUrlMapString));
	//        log.info("setCasServerLoginUrlMap = " + casServerLoginUrlMap);
	//    }
	//
	//    public Map<String, String> getCasServerLoginUrlMap() {
	//        return casServerLoginUrlMap;
	//    }
	//
	//    public void setCasServerLoginUrlMap(Map<String, String> casServerLoginUrlMap) {
	//        this.casServerLoginUrlMap = casServerLoginUrlMap;
	//    }
	//
	//    public String getCasServerLoginUrl() {
	//        return casServerLoginUrl;
	//    }
	//
	//    public void setCasServerLoginUrl(String casServerLoginUrl) {
	//        this.casServerLoginUrl = casServerLoginUrl;
	//    }




















	private String casServerLoginUrl;
	private Map<String, String> casServerLoginUrlMap = Collections.emptyMap();
	private boolean renew = false;
	private boolean gateway = false;
	private GatewayResolver gatewayStorage = new DefaultGatewayResolverImpl();
	private AuthenticationRedirectStrategy authenticationRedirectStrategy = new DefaultAuthenticationRedirectStrategy();
	private UrlPatternMatcherStrategy ignoreUrlPatternMatcherStrategyClass = null;
	private static final Map<String, Class<? extends UrlPatternMatcherStrategy>> PATTERN_MATCHER_TYPES = new HashMap();
	private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationFilter.class.getName());
	static
	{
		PATTERN_MATCHER_TYPES.put("CONTAINS", ContainsPatternUrlPatternMatcherStrategy.class);
		PATTERN_MATCHER_TYPES.put("REGEX", RegexUrlPatternMatcherStrategy.class);
		PATTERN_MATCHER_TYPES.put("EXACT", ExactUrlPatternMatcherStrategy.class);
	}

	protected void initInternal(FilterConfig filterConfig)
			throws ServletException
	{
		if (!isIgnoreInitConfiguration())
		{
			super.initInternal(filterConfig);
			setCasServerLoginUrl(getPropertyFromInitParams(filterConfig, "casServerLoginUrl", null));
			logger.trace("Loaded CasServerLoginUrl parameter: {}", this.casServerLoginUrl);
			setCasServerLoginUrlMap(getPropertyFromInitParams(filterConfig, "casServerLoginUrlMap", null));
			logger.trace("Loaded CasServerLoginUrlMap parameter: {}", this.casServerLoginUrlMap);
			setRenew(parseBoolean(getPropertyFromInitParams(filterConfig, "renew", "false")));
			logger.trace("Loaded renew parameter: {}", Boolean.valueOf(this.renew));
			setGateway(parseBoolean(getPropertyFromInitParams(filterConfig, "gateway", "false")));
			logger.trace("Loaded gateway parameter: {}", Boolean.valueOf(this.gateway));

			String ignorePattern = getPropertyFromInitParams(filterConfig, "ignorePattern", null);
			logger.trace("Loaded ignorePattern parameter: {}", ignorePattern);

			String ignoreUrlPatternType = getPropertyFromInitParams(filterConfig, "ignoreUrlPatternType", "REGEX");
			logger.trace("Loaded ignoreUrlPatternType parameter: {}", ignoreUrlPatternType);
			if (ignorePattern != null)
			{
				Class<? extends UrlPatternMatcherStrategy> ignoreUrlMatcherClass = (Class)PATTERN_MATCHER_TYPES.get(ignoreUrlPatternType);
				if (ignoreUrlMatcherClass != null) {
					this.ignoreUrlPatternMatcherStrategyClass = ((UrlPatternMatcherStrategy)ReflectUtils.newInstance(ignoreUrlMatcherClass.getName(), new Object[0]));
				} else {
					try
					{
						logger.trace("Assuming {} is a qualified class name...", ignoreUrlPatternType);
						this.ignoreUrlPatternMatcherStrategyClass = ((UrlPatternMatcherStrategy)ReflectUtils.newInstance(ignoreUrlPatternType, new Object[0]));
					}
					catch (IllegalArgumentException e)
					{
						logger.error("Could not instantiate class [{}]", ignoreUrlPatternType, e);
					}
				}
				if (this.ignoreUrlPatternMatcherStrategyClass != null) {
					this.ignoreUrlPatternMatcherStrategyClass.setPattern(ignorePattern);
				}
			}
			String gatewayStorageClass = getPropertyFromInitParams(filterConfig, "gatewayStorageClass", null);
			if (gatewayStorageClass != null) {
				this.gatewayStorage = ((GatewayResolver)ReflectUtils.newInstance(gatewayStorageClass, new Object[0]));
			}
			String authenticationRedirectStrategyClass = getPropertyFromInitParams(filterConfig, "authenticationRedirectStrategyClass", null);
			if (authenticationRedirectStrategyClass != null) {
				this.authenticationRedirectStrategy = ((AuthenticationRedirectStrategy)ReflectUtils.newInstance(authenticationRedirectStrategyClass, new Object[0]));
			}
		}
	}

	public void init()
	{
		super.init();
		CommonUtils.assertTrue(CommonUtils.isNotEmpty(this.casServerLoginUrl) || !this.casServerLoginUrlMap.isEmpty(), "casServerLoginUrlMap cannot be null.");
		//CommonUtils.assertNotNull(this.casServerLoginUrl, "casServerLoginUrl cannot be null.");
	}

	public final void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException
	{
		HttpServletRequest request = (HttpServletRequest)servletRequest;
		HttpServletResponse response = (HttpServletResponse)servletResponse;
		if (isRequestUrlExcluded(request))
		{
			logger.debug("Request is ignored.");
			filterChain.doFilter(request, response);
			return;
		}
		HttpSession session = request.getSession(false);
		Assertion assertion = session != null ? (Assertion)session.getAttribute("_const_cas_assertion_") : null;
		if (assertion != null)
		{
			filterChain.doFilter(request, response);
			return;
		}
		String serviceUrl = constructServiceUrl(request, response);

		String actualCasServerLoginUrl = getFromMapByRequest(casServerLoginUrlMap, request);
		if (actualCasServerLoginUrl == null) {
			actualCasServerLoginUrl = casServerLoginUrl;
		}
		if (actualCasServerLoginUrl == null) {
			throw new ServletException("cannot find cas login url for client host = " + request.getRemoteHost());
		}


		String ticket = retrieveTicketFromRequest(request);
		boolean wasGatewayed = (this.gateway) && (this.gatewayStorage.hasGatewayedAlready(request, serviceUrl));
		if ((CommonUtils.isNotBlank(ticket)) || (wasGatewayed))
		{
			filterChain.doFilter(request, response);
			return;
		}
		logger.debug("no ticket and no assertion found");
		String modifiedServiceUrl;
		// String modifiedServiceUrl;
		if (this.gateway)
		{
			logger.debug("setting gateway attribute in session");
			modifiedServiceUrl = this.gatewayStorage.storeGatewayInformation(request, serviceUrl);
		}
		else
		{
			modifiedServiceUrl = serviceUrl;
		}
		logger.debug("Constructed service url: {}", modifiedServiceUrl);

		//String urlToRedirectTo = CommonUtils.constructRedirectUrl(this.casServerLoginUrl, getServiceParameterName(), modifiedServiceUrl, this.renew, this.gateway);
		String urlToRedirectTo = CommonUtils.constructRedirectUrl(actualCasServerLoginUrl, getServiceParameterName(), modifiedServiceUrl, this.renew, this.gateway);

		logger.debug("redirecting to \"{}\"", urlToRedirectTo);
		this.authenticationRedirectStrategy.redirect(request, response, urlToRedirectTo);
	}

	public final void setRenew(boolean renew)
	{
		this.renew = renew;
	}

	public final void setGateway(boolean gateway)
	{
		this.gateway = gateway;
	}

	public final void setCasServerLoginUrl(String casServerLoginUrl)
	{
		this.casServerLoginUrl = casServerLoginUrl;
	}

	public final void setGatewayStorage(GatewayResolver gatewayStorage)
	{
		this.gatewayStorage = gatewayStorage;
	}

	public void setCasServerLoginUrlMap(String casServerLoginUrlMapString) {
		setCasServerLoginUrlMap(parseMapParam(casServerLoginUrlMapString));
		//log.info("setCasServerLoginUrlMap = " + casServerLoginUrlMap);
	}

	public void setCasServerLoginUrlMap(Map<String, String> casServerLoginUrlMap) {
		this.casServerLoginUrlMap = casServerLoginUrlMap;
	}

	private boolean isRequestUrlExcluded(HttpServletRequest request)
	{
		if (this.ignoreUrlPatternMatcherStrategyClass == null) {
			return false;
		}
		StringBuffer urlBuffer = request.getRequestURL();
		if (request.getQueryString() != null) {
			urlBuffer.append("?").append(request.getQueryString());
		}
		String requestUri = urlBuffer.toString();
		return this.ignoreUrlPatternMatcherStrategyClass.matches(requestUri);
	}

}
