/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.wego.utils.cas;

import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jasig.cas.client.proxy.AbstractEncryptedProxyGrantingTicketStorageImpl;
import org.jasig.cas.client.proxy.Cas20ProxyRetriever;
import org.jasig.cas.client.proxy.CleanUpTimerTask;
import org.jasig.cas.client.proxy.ProxyGrantingTicketStorage;
import org.jasig.cas.client.proxy.ProxyGrantingTicketStorageImpl;
import org.jasig.cas.client.ssl.HttpURLConnectionFactory;
import org.jasig.cas.client.ssl.HttpsURLConnectionFactory;
import org.jasig.cas.client.util.CommonUtils;
import org.jasig.cas.client.util.ReflectUtils;
import org.jasig.cas.client.validation.Cas20ProxyTicketValidator;
import org.jasig.cas.client.validation.Cas20ServiceTicketValidator;
import org.jasig.cas.client.validation.TicketValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 *
 * @author aleph
 */
public class CustomCas20ProxyReceivingTicketValidationFilter extends CustomAbstractTicketValidationFilter{
///**
//     * Constant representing the ProxyGrantingTicket IOU Request Parameter.
//     */
//    private static final String PARAM_PROXY_GRANTING_TICKET_IOU = "pgtIou";
//
//    /**
//     * Constant representing the ProxyGrantingTicket Request Parameter.
//     */
//    private static final String PARAM_PROXY_GRANTING_TICKET = "pgtId";
//
//    /**
//     * The URL to send to the CAS server as the URL that will process proxying requests on the CAS client. 
//     */
//    private String proxyReceptorUrl;
//
//    /**
//     * Storage location of ProxyGrantingTickets and Proxy Ticket IOUs.
//     */
//    private ProxyGrantingTicketStorage proxyGrantingTicketStorage = new ProxyGrantingTicketStorageImpl();
//
//    @Override
//    protected void initInternal(final FilterConfig filterConfig) throws ServletException {
//        super.initInternal(filterConfig);
//        setProxyReceptorUrl(getPropertyFromInitParams(filterConfig, "proxyReceptorUrl", null));
//    }
//
//    @Override
//    public void init() {
//        super.init();
//        CommonUtils.assertNotNull(this.proxyGrantingTicketStorage, "proxyGrantingTicketStorage cannot be null.");
//    }
//
//    /**
//     * Constructs a Cas20ServiceTicketValidator or a Cas20ProxyTicketValidator based on supplied parameters.
//     *
//     * @param filterConfig the Filter Configuration object.
//     * @return a fully constructed TicketValidator.
//     */
//    @Override
//    protected final TicketValidator getTicketValidator(final FilterConfig filterConfig) {
//        final String allowAnyProxy = getPropertyFromInitParams(filterConfig, "acceptAnyProxy", null);
//        final String allowedProxyChains = getPropertyFromInitParams(filterConfig, "allowedProxyChains", null);
//        final String casServerUrlPrefix = getPropertyFromInitParams(filterConfig, "casServerUrlPrefix", null);
//        final Cas20ServiceTicketValidator validator;
//
//        if (CommonUtils.isNotBlank(allowAnyProxy) || CommonUtils.isNotBlank(allowedProxyChains)) {
//            final Cas20ProxyTicketValidator v = new Cas20ProxyTicketValidator(casServerUrlPrefix);
//            v.setAcceptAnyProxy(Boolean.parseBoolean(allowAnyProxy));
//            v.setAllowedProxyChains(constructListOfProxies(allowedProxyChains));
//            validator = v;
//        } else {
//            validator = new Cas20ServiceTicketValidator(casServerUrlPrefix);
//        }
//        validator.setProxyCallbackUrl(getPropertyFromInitParams(filterConfig, "proxyCallbackUrl", null));
//        validator.setProxyGrantingTicketStorage(this.proxyGrantingTicketStorage);
//        validator.setProxyRetriever(new Cas20ProxyRetriever(casServerUrlPrefix));
//        validator.setRenew(Boolean.parseBoolean(getPropertyFromInitParams(filterConfig, "renew", "false")));
//        return validator;
//    }
//
//    protected final List constructListOfProxies(final String proxies) {
//        if (CommonUtils.isBlank(proxies)) {
//            return new ArrayList();
//        }
//
//        final String[] splitProxies = proxies.split("\n");
//        final List items = Arrays.asList(splitProxies);
//        final ProxyListPropertyEditor editor = new ProxyListPropertyEditor();
//        editor.setValue(items);
//        return (List) editor.getValue();
//    }
//
//    /**
//     * This processes the ProxyReceptor request before the ticket validation code executes.
//     */
//    @Override
//    protected final boolean preFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain) throws IOException, ServletException {
//        final HttpServletRequest request = (HttpServletRequest) servletRequest;
//        final HttpServletResponse response = (HttpServletResponse) servletResponse;
//        final String requestUri = request.getRequestURI();
//
//        if (CommonUtils.isEmpty(this.proxyReceptorUrl) || !requestUri.endsWith(this.proxyReceptorUrl)) {
//            return true;
//        }
//
//        final String proxyGrantingTicketIou = request
//                .getParameter(PARAM_PROXY_GRANTING_TICKET_IOU);
//
//        final String proxyGrantingTicket = request
//                .getParameter(PARAM_PROXY_GRANTING_TICKET);
//
//        if (CommonUtils.isBlank(proxyGrantingTicket)
//                || CommonUtils.isBlank(proxyGrantingTicketIou)) {
//            response.getWriter().write("");
//            return false;
//        }
//
//        if (log.isDebugEnabled()) {
//            log.debug("Received proxyGrantingTicketId ["
//                    + proxyGrantingTicket + "] for proxyGrantingTicketIou ["
//                    + proxyGrantingTicketIou + "]");
//        }
//
//        this.proxyGrantingTicketStorage.save(proxyGrantingTicketIou,
//                proxyGrantingTicket);
//
//        response.getWriter().write("<?xml version=\"1.0\"?>");
//        response.getWriter().write("<casClient:proxySuccess xmlns:casClient=\"http://www.yale.edu/tp/casClient\" />");
//        return false;
//    }
//
//    public final void setProxyReceptorUrl(final String proxyReceptorUrl) {
//        this.proxyReceptorUrl = proxyReceptorUrl;
//    }
//
//    public final void setProxyGrantingTicketStorage(final ProxyGrantingTicketStorage proxyGrantingTicketStorage) {
//        this.proxyGrantingTicketStorage = proxyGrantingTicketStorage;
//    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private static final String[] RESERVED_INIT_PARAMS = { "proxyGrantingTicketStorageClass", "proxyReceptorUrl", "acceptAnyProxy", "allowedProxyChains", "casServerUrlPrefix", "proxyCallbackUrl", "renew", "exceptionOnValidationFailure", "redirectAfterValidation", "useSession", "serverName", "service", "artifactParameterName", "serviceParameterName", "encodeServiceUrl", "millisBetweenCleanUps", "hostnameVerifier", "encoding", "config", "ticketValidatorClass" };
	  private static final int DEFAULT_MILLIS_BETWEEN_CLEANUPS = 60000;
	  private String proxyReceptorUrl;
	  private Timer timer;
	  private TimerTask timerTask;
	  private int millisBetweenCleanUps;
	  private static final Logger logger = LoggerFactory.getLogger(CustomCas20ProxyReceivingTicketValidationFilter.class.getName());
	  private ProxyGrantingTicketStorage proxyGrantingTicketStorage = new ProxyGrantingTicketStorageImpl();
	  
	  protected void initInternal(FilterConfig filterConfig)
	    throws ServletException
	  {
	    setProxyReceptorUrl(getPropertyFromInitParams(filterConfig, "proxyReceptorUrl", null));
	    
	    String proxyGrantingTicketStorageClass = getPropertyFromInitParams(filterConfig, "proxyGrantingTicketStorageClass", null);
	    if (proxyGrantingTicketStorageClass != null)
	    {
	      this.proxyGrantingTicketStorage = ((ProxyGrantingTicketStorage)ReflectUtils.newInstance(proxyGrantingTicketStorageClass, new Object[0]));
	      if ((this.proxyGrantingTicketStorage instanceof AbstractEncryptedProxyGrantingTicketStorageImpl))
	      {
	        AbstractEncryptedProxyGrantingTicketStorageImpl p = (AbstractEncryptedProxyGrantingTicketStorageImpl)this.proxyGrantingTicketStorage;
	        String cipherAlgorithm = getPropertyFromInitParams(filterConfig, "cipherAlgorithm", "DESede");
	        
	        String secretKey = getPropertyFromInitParams(filterConfig, "secretKey", null);
	        
	        p.setCipherAlgorithm(cipherAlgorithm);
	        try
	        {
	          if (secretKey != null) {
	            p.setSecretKey(secretKey);
	          }
	        }
	        catch (Exception e)
	        {
	          throw new RuntimeException(e);
	        }
	      }
	    }
	    logger.trace("Setting proxyReceptorUrl parameter: {}", this.proxyReceptorUrl);
	    this.millisBetweenCleanUps = Integer.parseInt(getPropertyFromInitParams(filterConfig, "millisBetweenCleanUps", Integer.toString(60000)));
	    
	    super.initInternal(filterConfig);
	  }
	  
	  public void init()
	  {
	    super.init();
	    CommonUtils.assertNotNull(this.proxyGrantingTicketStorage, "proxyGrantingTicketStorage cannot be null.");
	    if (this.timer == null) {
	      this.timer = new Timer(true);
	    }
	    if (this.timerTask == null) {
	      this.timerTask = new CleanUpTimerTask(this.proxyGrantingTicketStorage);
	    }
	    this.timer.schedule(this.timerTask, this.millisBetweenCleanUps, this.millisBetweenCleanUps);
	  }
	  
	  private <T> T createNewTicketValidator(String ticketValidatorClass, String casServerUrlPrefix, Class<T> clazz)
	  {
	    if (CommonUtils.isBlank(ticketValidatorClass)) {
	      return (T)ReflectUtils.newInstance(clazz, new Object[] { casServerUrlPrefix });
	    }
	    return (T)ReflectUtils.newInstance(ticketValidatorClass, new Object[] { casServerUrlPrefix });
	  }
	  
	  protected final TicketValidator getTicketValidator(FilterConfig filterConfig)
	  {
	    String allowAnyProxy = getPropertyFromInitParams(filterConfig, "acceptAnyProxy", null);
	    String allowedProxyChains = getPropertyFromInitParams(filterConfig, "allowedProxyChains", null);
	    String casServerUrlPrefix = getPropertyFromInitParams(filterConfig, "casServerUrlPrefix", null);
	    String ticketValidatorClass = getPropertyFromInitParams(filterConfig, "ticketValidatorClass", null);
	    Cas20ServiceTicketValidator validator;
	   // Cas20ServiceTicketValidator validator;
	    if ((CommonUtils.isNotBlank(allowAnyProxy)) || (CommonUtils.isNotBlank(allowedProxyChains)))
	    {
	      Cas20ProxyTicketValidator v = (Cas20ProxyTicketValidator)createNewTicketValidator(ticketValidatorClass, casServerUrlPrefix, Cas20ProxyTicketValidator.class);
	      
	      v.setAcceptAnyProxy(parseBoolean(allowAnyProxy));
	      v.setAllowedProxyChains(CommonUtils.createProxyList(allowedProxyChains));
	      validator = v;
	    }
	    else
	    {
	      validator = (Cas20ServiceTicketValidator)createNewTicketValidator(ticketValidatorClass, casServerUrlPrefix, Cas20ServiceTicketValidator.class);
	    }
	    validator.setProxyCallbackUrl(getPropertyFromInitParams(filterConfig, "proxyCallbackUrl", null));
	    validator.setProxyGrantingTicketStorage(this.proxyGrantingTicketStorage);
	    
	    HttpURLConnectionFactory factory = new HttpsURLConnectionFactory(getHostnameVerifier(filterConfig), getSSLConfig(filterConfig));
	    
	    validator.setURLConnectionFactory(factory);
	    
	    validator.setProxyRetriever(new Cas20ProxyRetriever(casServerUrlPrefix, getPropertyFromInitParams(filterConfig, "encoding", null), factory));
	    
	    validator.setRenew(parseBoolean(getPropertyFromInitParams(filterConfig, "renew", "false")));
	    validator.setEncoding(getPropertyFromInitParams(filterConfig, "encoding", null));
	    
	    Map<String, String> additionalParameters = new HashMap();
	    List<String> params = Arrays.asList(RESERVED_INIT_PARAMS);
	    for (Enumeration<?> e = filterConfig.getInitParameterNames(); e.hasMoreElements();)
	    {
	      String s = (String)e.nextElement();
	      if (!params.contains(s)) {
	        additionalParameters.put(s, filterConfig.getInitParameter(s));
	      }
	    }
	    validator.setCustomParameters(additionalParameters);
	    return validator;
	  }
	  
	  public void destroy()
	  {
	    super.destroy();
	    this.timer.cancel();
	  }
	  
	  protected final boolean preFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
	    throws IOException, ServletException
	  {
	    HttpServletRequest request = (HttpServletRequest)servletRequest;
	    HttpServletResponse response = (HttpServletResponse)servletResponse;
	    String requestUri = request.getRequestURI();
	    if ((CommonUtils.isEmpty(this.proxyReceptorUrl)) || (!requestUri.endsWith(this.proxyReceptorUrl))) {
	      return true;
	    }
	    try
	    {
	      CommonUtils.readAndRespondToProxyReceptorRequest(request, response, this.proxyGrantingTicketStorage);
	    }
	    catch (RuntimeException e)
	    {
	      logger.error(e.getMessage(), e);
	      throw e;
	    }
	    return false;
	  }
	  
	  public final void setProxyReceptorUrl(String proxyReceptorUrl)
	  {
	    this.proxyReceptorUrl = proxyReceptorUrl;
	  }
	  
	  public void setProxyGrantingTicketStorage(ProxyGrantingTicketStorage storage)
	  {
	    this.proxyGrantingTicketStorage = storage;
	  }
	  
	  public void setTimer(Timer timer)
	  {
	    this.timer = timer;
	  }
	  
	  public void setTimerTask(TimerTask timerTask)
	  {
	    this.timerTask = timerTask;
	  }
	  
	  public void setMillisBetweenCleanUps(int millisBetweenCleanUps)
	  {
	    this.millisBetweenCleanUps = millisBetweenCleanUps;
	  }
	
	
	
	
	
}
