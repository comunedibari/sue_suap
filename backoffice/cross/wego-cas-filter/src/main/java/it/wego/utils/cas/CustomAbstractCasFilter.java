/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.utils.cas;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jasig.cas.client.util.AbstractConfigurationFilter;
import org.jasig.cas.client.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author aleph
 */

public abstract class CustomAbstractCasFilter extends AbstractConfigurationFilter {

	//    /**
	//     * Represents the constant for where the assertion will be located in
	//     * memory.
	//     */
	//    public static final String CONST_CAS_ASSERTION = "_const_cas_assertion_";
	//
	//    /**
	//     * Instance of commons logging for logging purposes.
	//     */
	//    protected final Log log = LogFactory.getLog(getClass());
	//
	//    /**
	//     * Defines the parameter to look for for the artifact.
	//     */
	//    private String artifactParameterName = "ticket";
	//
	//    /**
	//     * Defines the parameter to look for for the service.
	//     */
	//    private String serviceParameterName = "service";
	//
	//    /**
	//     * Sets where response.encodeUrl should be called on service urls when
	//     * constructed.
	//     */
	//    private boolean encodeServiceUrl = true;
	//
	//    /**
	//     * The name of the server. Should be in the following format:
	//     * {protocol}:{hostName}:{port}. Standard ports can be excluded.
	//     */
	//    private String serverName;
	//    private Map<String, String> serverNameMap = Collections.emptyMap();
	//
	//    /**
	//     * The exact url of the service.
	//     */
	//    private String service;
	//
	//    @Override
	//    public final void init(final FilterConfig filterConfig) throws ServletException {
	//        setServerName(getPropertyFromInitParams(filterConfig, "serverName", null));
	//        
	//        setService(getPropertyFromInitParams(filterConfig, "service", null));
	//        setArtifactParameterName(getPropertyFromInitParams(filterConfig, "artifactParameterName", "ticket"));
	//        setServiceParameterName(getPropertyFromInitParams(filterConfig, "serviceParameterName", "service"));
	//        setEncodeServiceUrl(Boolean.parseBoolean(getPropertyFromInitParams(filterConfig, "encodeServiceUrl", "true")));
	//
	//        initInternal(filterConfig);
	//        init();
	//    }
	//
	//    /**
	//     * Controls the ordering of filter initialization and checking by defining a
	//     * method that runs before the init.
	//     */
	//    protected void initInternal(final FilterConfig filterConfig) throws ServletException {
	//        // template method
	//    }
	//
	//    /**
	//     * Initialization method. Called by Filter's init method or by Spring.
	//     */
	//    public void init() {
	//        CommonUtils.assertNotNull(this.artifactParameterName, "artifactParameterName cannot be null.");
	//        CommonUtils.assertNotNull(this.serviceParameterName, "serviceParameterName cannot be null.");
	//        CommonUtils.assertTrue(CommonUtils.isNotEmpty(this.serverName) || CommonUtils.isNotEmpty(this.service) || !serverNameMap.isEmpty(), "serverName or service must be set.");
	//    }
	//
	//    @Override
	//    public final void destroy() {
	//        // nothing to do
	//    }
	//
	//    protected final String constructServiceUrl(final HttpServletRequest request, final HttpServletResponse response) {
	//        String actualServerName = getFromMapByRequest(serverNameMap, request);
	//        if (actualServerName == null) {
	//            actualServerName = serverName;
	//        }
	//        return CommonUtils.constructServiceUrl(request, response, this.service, actualServerName, this.artifactParameterName, this.encodeServiceUrl);
	//    }
	//
	//    protected String getFromMapByRequest(Map<String, String> map, HttpServletRequest request) {
	//        String res = null;
	//        for (Map.Entry<String, String> entry : map.entrySet()) {
	//            if (request.getRemoteAddr().matches(entry.getKey()) || request.getRemoteHost().matches(entry.getKey()) || request.getServerName().matches(entry.getKey())) {
	//                res = entry.getValue();
	//                log.info("request = " + request + " (server = " + request.getServerName() + ") resource = " + res);
	//                break;
	//            }
	//        }
	//        return res;
	//    }
	//
	//    protected Map<String, String> parseMapParam(String param) {
	//        if (param == null) {
	//            return Collections.emptyMap();
	//        } else {
	//            String[] split = param.split(" +");
	//            CommonUtils.assertTrue(split.length % 2 == 0, "param map = '" + param + "' must match '[^ ]+ +[^ ]+( +[^ ]+ +[^ ]+)*'");
	//            Map<String, String> map = new LinkedHashMap<String, String>();
	//            for (int i = 0; i < split.length; i += 2) {
	//                map.put(split[i], split[i + 1]);
	//            }
	//            return map;
	//        }
	//    }
	//
	//    public Map<String, String> getServerNameMap() {
	//        return serverNameMap;
	//    }
	//
	//    public void setServerNameMap(Map<String, String> serverNameMap) {
	//        this.serverNameMap = serverNameMap;
	//    }
	//
	//    public void setServerNameMap(String serverNameMapString) {
	//        setServerNameMap(parseMapParam(serverNameMapString));
	//        log.info("setServerNameMap = " + serverNameMap);
	//    }
	//
	//    public final void setServerName(final String serverName) {
	//        this.serverName = serverName;
	//    }
	//
	//    public final void setService(final String service) {
	//        this.service = service;
	//    }
	//
	//    public final void setArtifactParameterName(final String artifactParameterName) {
	//        this.artifactParameterName = artifactParameterName;
	//    }
	//
	//    public final void setServiceParameterName(final String serviceParameterName) {
	//        this.serviceParameterName = serviceParameterName;
	//    }
	//
	//    public final void setEncodeServiceUrl(final boolean encodeServiceUrl) {
	//        this.encodeServiceUrl = encodeServiceUrl;
	//    }
	//
	//    public final String getArtifactParameterName() {
	//        return this.artifactParameterName;
	//    }
	//
	//    public final String getServiceParameterName() {
	//        return this.serviceParameterName;
	//    }
	//    
	//    
	//    
	//    
	//    
	//    
	//    
	//    



	public static final String CONST_CAS_ASSERTION = "_const_cas_assertion_";
	private String artifactParameterName = "ticket";
	private String serviceParameterName = "service";
	private boolean encodeServiceUrl = true;
	private String serverName;
	private Map<String, String> serverNameMap = Collections.emptyMap();
	private String service;
	private static final Logger logger = LoggerFactory.getLogger(CustomAbstractCasFilter.class.getName());
	private static final Map<String, String> costanti;
	static
	{
		costanti = new HashMap<String, String>();
		costanti.put("${suap.sue.backend.filters.casServerLoginUrlMap}", "localhost http://localhost:8080/cas/login ");
		costanti.put("${suap.sue.backend.filters.serverNameMap}", "localhost http://localhost:8080  ");
		costanti.put("${suap.sue.backend.filters.casServerUrlPrefix}", "http://localhost:8080/cas");
	}
	public final void init(FilterConfig filterConfig)
			throws ServletException
	{
		if (!isIgnoreInitConfiguration())
		{
			setServerName(getPropertyFromInitParams(filterConfig, "serverName", null));
			setServerNameMap(getPropertyFromInitParams(filterConfig, "serverNameMap", null));
			logger.trace("Loading serverName property: {}", this.serverName);
			setService(getPropertyFromInitParams(filterConfig, "service", null));
			logger.trace("Loading service property: {}", this.service);
			setArtifactParameterName(getPropertyFromInitParams(filterConfig, "artifactParameterName", "ticket"));
			logger.trace("Loading artifact parameter name property: {}", this.artifactParameterName);
			setServiceParameterName(getPropertyFromInitParams(filterConfig, "serviceParameterName", "service"));
			logger.trace("Loading serviceParameterName property: {} ", this.serviceParameterName);
			setEncodeServiceUrl(parseBoolean(getPropertyFromInitParams(filterConfig, "encodeServiceUrl", "true")));
			logger.trace("Loading encodeServiceUrl property: {}", Boolean.valueOf(this.encodeServiceUrl));

			initInternal(filterConfig);
		}
		init();
	}

	protected void initInternal(FilterConfig filterConfig)
			throws ServletException
	{}

	public void init()
	{
		CommonUtils.assertNotNull(this.artifactParameterName, "artifactParameterName cannot be null.");
		CommonUtils.assertNotNull(this.serviceParameterName, "serviceParameterName cannot be null.");
		CommonUtils.assertTrue( CommonUtils.isNotEmpty(this.service) || !serverNameMap.isEmpty(), "serverNameMap or service must be set.");
		//CommonUtils.assertTrue((CommonUtils.isNotEmpty(this.serverName)) || (CommonUtils.isNotEmpty(this.service)), "serverName or service must be set.");

		//CommonUtils.assertTrue((CommonUtils.isBlank(this.serverName)) || (CommonUtils.isBlank(this.service)), "serverName and service cannot both be set.  You MUST ONLY set one.");
	}

	public void destroy() {}

	//	protected final String constructServiceUrl(HttpServletRequest request, HttpServletResponse response)
	//	{
	//		return CommonUtils.constructServiceUrl(request, response, this.service, this.serverName, this.artifactParameterName, this.encodeServiceUrl);
	//	}

	protected final String constructServiceUrl(final HttpServletRequest request, final HttpServletResponse response) {
		String actualServerName = getFromMapByRequest(serverNameMap, request);
		if (actualServerName == null) {
			actualServerName = serverName;
		}
		return CommonUtils.constructServiceUrl(request, response, this.service, actualServerName, this.artifactParameterName, this.encodeServiceUrl);
	}

	protected String getFromMapByRequest(Map<String, String> map, HttpServletRequest request) {
		String res = null;
		for (Map.Entry<String, String> entry : map.entrySet()) {
			if (request.getRemoteAddr().matches(entry.getKey()) || request.getRemoteHost().matches(entry.getKey()) || request.getServerName().matches(entry.getKey())) {
				res = entry.getValue();
				System.out.println("request = " + request + " (server = " + request.getServerName() + ") resource = " + res);
				break;
			}
		}
		return res;
	}


	public final void setServerName(String serverName)
	{
		if ((serverName != null) && (serverName.endsWith("/")))
		{
			this.serverName = serverName.substring(0, serverName.length() - 1);
			logger.info("Eliminated extra slash from serverName [{}].  It is now [{}]", serverName, this.serverName);
		}
		else
		{
			this.serverName = serverName;
		}
	}

	protected Map<String, String> parseMapParam(String param) {
		if (param == null) {
			return Collections.emptyMap();
		} else {
			if( param.indexOf("${") > -1 )
			{
				String value = costanti.get(param);
				if( logger.isWarnEnabled() )
				{
					logger.warn("VALORE {} NON SOSTITUITO DA MAVEN. UTILIZZO {}", param, value);
				}
				param = value;
			}
			String[] split = param.split(" +");
			CommonUtils.assertTrue(split.length % 2 == 0, "param map = '" + param + "' must match '[^ ]+ +[^ ]+( +[^ ]+ +[^ ]+)*'");
			Map<String, String> map = new LinkedHashMap<String, String>();
			for (int i = 0; i < split.length; i += 2) {
				map.put(split[i], split[i + 1]);
			}
			return map;
		}
	}

	public void setServerNameMap(Map<String, String> serverNameMap) {
		this.serverNameMap = serverNameMap;
	}

	public void setServerNameMap(String serverNameMapString) {
		setServerNameMap(parseMapParam(serverNameMapString));
		//log.info("setServerNameMap = " + serverNameMap);
	}

	public final void setService(String service)
	{
		this.service = service;
	}

	public final void setArtifactParameterName(String artifactParameterName)
	{
		this.artifactParameterName = artifactParameterName;
	}

	public final void setServiceParameterName(String serviceParameterName)
	{
		this.serviceParameterName = serviceParameterName;
	}

	public final void setEncodeServiceUrl(boolean encodeServiceUrl)
	{
		this.encodeServiceUrl = encodeServiceUrl;
	}

	public final String getArtifactParameterName()
	{
		return this.artifactParameterName;
	}

	public final String getServiceParameterName()
	{
		return this.serviceParameterName;
	}

	protected String retrieveTicketFromRequest(HttpServletRequest request)
	{
		return CommonUtils.safeGetParameter(request, getArtifactParameterName());
	}
}
