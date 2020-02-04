/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.wego.utils.cas;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.net.ssl.HostnameVerifier;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jasig.cas.client.util.CommonUtils;
import org.jasig.cas.client.util.ReflectUtils;
import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.TicketValidationException;
import org.jasig.cas.client.validation.TicketValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author aleph
 */
public abstract class CustomAbstractTicketValidationFilter extends CustomAbstractCasFilter{
//  /** The TicketValidator we will use to validate tickets. */
//    private TicketValidator ticketValidator;
//
//    /**
//     * Specify whether the filter should redirect the user agent after a
//     * successful validation to remove the ticket parameter from the query
//     * string.
//     */
//    private boolean redirectAfterValidation = false;
//
//    /** Determines whether an exception is thrown when there is a ticket validation failure. */
//    private boolean exceptionOnValidationFailure = true;
//
//    private boolean useSession = true;
//
//    /**
//     * Template method to return the appropriate validator.
//     *
//     * @param filterConfig the FilterConfiguration that may be needed to construct a validator.
//     * @return the ticket validator.
//     */
//    protected TicketValidator getTicketValidator(FilterConfig filterConfig) {
//        return this.ticketValidator;
//    }
//
//    @Override
//    protected void initInternal(final FilterConfig filterConfig) throws ServletException {
//        super.initInternal(filterConfig);
//        setExceptionOnValidationFailure(Boolean.parseBoolean(getPropertyFromInitParams(filterConfig, "exceptionOnValidationFailure", "true")));
//        setRedirectAfterValidation(Boolean.parseBoolean(getPropertyFromInitParams(filterConfig, "redirectAfterValidation", "false")));
//        setUseSession(Boolean.parseBoolean(getPropertyFromInitParams(filterConfig, "useSession", "true")));
//        setTicketValidator(getTicketValidator(filterConfig));
//    }
//
//    @Override
//    public void init() {
//        super.init();
//        CommonUtils.assertNotNull(this.ticketValidator, "ticketValidator cannot be null.");
//    }
//
//    /**
//     * Pre-process the request before the normal filter process starts.  This could be useful for pre-empting code.
//     *
//     * @param servletRequest The servlet request.
//     * @param servletResponse The servlet response.
//     * @param filterChain the filter chain.
//     * @return true if processing should continue, false otherwise.
//     * @throws IOException if there is an I/O problem
//     * @throws ServletException if there is a servlet problem.
//     */
//    protected boolean preFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain) throws IOException, ServletException {
//        return true;
//    }
//
//    /**
//     * Template method that gets executed if ticket validation succeeds.  Override if you want additional behavior to occur
//     * if ticket validation succeeds.  This method is called after all ValidationFilter processing required for a successful authentication
//     * occurs.
//     *
//     * @param request the HttpServletRequest.
//     * @param response the HttpServletResponse.
//     * @param assertion the successful Assertion from the server.
//     */
//    protected void onSuccessfulValidation(final HttpServletRequest request, final HttpServletResponse response, final Assertion assertion) {
//        // nothing to do here.                                                                                            
//    }
//
//    /**
//     * Template method that gets executed if validation fails.  This method is called right after the exception is caught from the ticket validator
//     * but before any of the processing of the exception occurs.
//     *
//     * @param request the HttpServletRequest.
//     * @param response the HttpServletResponse.
//     */
//    protected void onFailedValidation(final HttpServletRequest request, final HttpServletResponse response) {
//        // nothing to do here.
//    }
//
//    @Override
//    public final void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain) throws IOException, ServletException {
//
//        if (!preFilter(servletRequest, servletResponse, filterChain)) {
//            return;
//        }
//
//        final HttpServletRequest request = (HttpServletRequest) servletRequest;
//        final HttpServletResponse response = (HttpServletResponse) servletResponse;
//        final String ticket = request.getParameter(getArtifactParameterName());
//
//        if (CommonUtils.isNotBlank(ticket)) {
//            if (log.isDebugEnabled()) {
//                log.debug("Attempting to validate ticket: " + ticket);
//            }
//
//            try {
//                final Assertion assertion = this.ticketValidator.validate(
//                        ticket, constructServiceUrl(request,
//                        response));
//
//                if (log.isDebugEnabled()) {
//                    log.debug("Successfully authenticated user: "
//                            + assertion.getPrincipal().getName());
//                }
//
//                request.setAttribute(CONST_CAS_ASSERTION, assertion);
//
//                if (this.useSession) {
//                    request.getSession().setAttribute(CONST_CAS_ASSERTION,
//                            assertion);
//                }
//                onSuccessfulValidation(request, response, assertion);
//            } catch (final TicketValidationException e) {
//                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//                log.warn(e, e);
//
//                onFailedValidation(request, response);
//
//                if (this.exceptionOnValidationFailure) {
//                    throw new ServletException(e);
//                }
//            }
//
//            if (this.redirectAfterValidation) {
//                response.sendRedirect(response
//                        .encodeRedirectURL(constructServiceUrl(request, response)));
//                return;
//            }
//        }
//
//        filterChain.doFilter(request, response);
//
//    }
//
//    public final void setTicketValidator(final TicketValidator ticketValidator) {
//    this.ticketValidator = ticketValidator;
//}
//
//    public final void setRedirectAfterValidation(final boolean redirectAfterValidation) {
//        this.redirectAfterValidation = redirectAfterValidation;
//    }
//
//    public final void setExceptionOnValidationFailure(final boolean exceptionOnValidationFailure) {
//        this.exceptionOnValidationFailure = exceptionOnValidationFailure;
//    }
//
//    public final void setUseSession(final boolean useSession) {
//        this.useSession = useSession;
//    }
	
	
	private TicketValidator ticketValidator;
	  private boolean redirectAfterValidation = true;
	  private boolean exceptionOnValidationFailure = false;
	  private boolean useSession = true;
	  private static final Logger logger = LoggerFactory.getLogger(CustomAbstractTicketValidationFilter.class.getName());
	  
	  protected TicketValidator getTicketValidator(FilterConfig filterConfig)
	  {
	    return this.ticketValidator;
	  }
	  
	  protected Properties getSSLConfig(FilterConfig filterConfig)
	  {
	    Properties properties = new Properties();
	    String fileName = getPropertyFromInitParams(filterConfig, "sslConfigFile", null);
	    if (fileName != null)
	    {
	      FileInputStream fis = null;
	      try
	      {
	        fis = new FileInputStream(fileName);
	        properties.load(fis);
	        logger.trace("Loaded {} entries from {}", Integer.valueOf(properties.size()), fileName);
	      }
	      catch (IOException ioe)
	      {
	        logger.error(ioe.getMessage(), ioe);
	      }
	      finally
	      {
	        CommonUtils.closeQuietly(fis);
	      }
	    }
	    return properties;
	  }
	  
	  protected HostnameVerifier getHostnameVerifier(FilterConfig filterConfig)
	  {
	    String className = getPropertyFromInitParams(filterConfig, "hostnameVerifier", null);
	    logger.trace("Using hostnameVerifier parameter: {}", className);
	    String config = getPropertyFromInitParams(filterConfig, "hostnameVerifierConfig", null);
	    logger.trace("Using hostnameVerifierConfig parameter: {}", config);
	    if (className != null)
	    {
	      if (config != null) {
	        return (HostnameVerifier)ReflectUtils.newInstance(className, new Object[] { config });
	      }
	      return (HostnameVerifier)ReflectUtils.newInstance(className, new Object[0]);
	    }
	    return null;
	  }
	  
	  protected void initInternal(FilterConfig filterConfig)
	    throws ServletException
	  {
	    setExceptionOnValidationFailure(parseBoolean(getPropertyFromInitParams(filterConfig, "exceptionOnValidationFailure", "false")));
	    
	    logger.trace("Setting exceptionOnValidationFailure parameter: {}", Boolean.valueOf(this.exceptionOnValidationFailure));
	    setRedirectAfterValidation(parseBoolean(getPropertyFromInitParams(filterConfig, "redirectAfterValidation", "true")));
	    
	    logger.trace("Setting redirectAfterValidation parameter: {}", Boolean.valueOf(this.redirectAfterValidation));
	    setUseSession(parseBoolean(getPropertyFromInitParams(filterConfig, "useSession", "true")));
	    logger.trace("Setting useSession parameter: {}", Boolean.valueOf(this.useSession));
	    if ((!this.useSession) && (this.redirectAfterValidation))
	    {
	      logger.warn("redirectAfterValidation parameter may not be true when useSession parameter is false. Resetting it to false in order to prevent infinite redirects.");
	      setRedirectAfterValidation(false);
	    }
	    setTicketValidator(getTicketValidator(filterConfig));
	    super.initInternal(filterConfig);
	  }
	  
	  public void init()
	  {
	    super.init();
	    CommonUtils.assertNotNull(this.ticketValidator, "ticketValidator cannot be null.");
	  }
	  
	  protected boolean preFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
	    throws IOException, ServletException
	  {
	    return true;
	  }
	  
	  protected void onSuccessfulValidation(HttpServletRequest request, HttpServletResponse response, Assertion assertion) {}
	  
	  protected void onFailedValidation(HttpServletRequest request, HttpServletResponse response) {}
	  
	  public final void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
	    throws IOException, ServletException
	  {
	    if (!preFilter(servletRequest, servletResponse, filterChain)) {
	      return;
	    }
	    HttpServletRequest request = (HttpServletRequest)servletRequest;
	    HttpServletResponse response = (HttpServletResponse)servletResponse;
	    String ticket = retrieveTicketFromRequest(request);
	    if (CommonUtils.isNotBlank(ticket))
	    {
	      logger.debug("Attempting to validate ticket: {}", ticket);
	      try
	      {
	        Assertion assertion = this.ticketValidator.validate(ticket, constructServiceUrl(request, response));
	        
	        logger.debug("Successfully authenticated user: {}", assertion.getPrincipal().getName());
	        
	        request.setAttribute("_const_cas_assertion_", assertion);
	        if (this.useSession) {
	          request.getSession().setAttribute("_const_cas_assertion_", assertion);
	        }
	        onSuccessfulValidation(request, response, assertion);
	        if (this.redirectAfterValidation)
	        {
	          logger.debug("Redirecting after successful ticket validation.");
	          response.sendRedirect(constructServiceUrl(request, response));
	          return;
	        }
	      }
	      catch (TicketValidationException e)
	      {
	        logger.debug(e.getMessage(), e);
	        
	        onFailedValidation(request, response);
	        if (this.exceptionOnValidationFailure) {
	          throw new ServletException(e);
	        }
	        response.sendError(403, e.getMessage());
	        
	        return;
	      }
	    }
	    filterChain.doFilter(request, response);
	  }
	  
	  public final void setTicketValidator(TicketValidator ticketValidator)
	  {
	    this.ticketValidator = ticketValidator;
	  }
	  
	  public final void setRedirectAfterValidation(boolean redirectAfterValidation)
	  {
	    this.redirectAfterValidation = redirectAfterValidation;
	  }
	  
	  public final void setExceptionOnValidationFailure(boolean exceptionOnValidationFailure)
	  {
	    this.exceptionOnValidationFailure = exceptionOnValidationFailure;
	  }
	  
	  public final void setUseSession(boolean useSession)
	  {
	    this.useSession = useSession;
	  }
	
	
	
}
