/**
 * 
 */
package it.people.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.MDC;

import it.people.City;
import it.people.core.PeopleContext;
import it.people.core.PplUser;

/**
 * @author Riccardo Forafò - Engineering Ingegneria Informatica - Genova
 *         18/mag/2012 12:24:19
 */
public class Log4JMDCFilter implements Filter {

    public static final String MDC_CONVERSATION_REF_KEY = "rifUtente";

    private static final String DEFAULT_ANONYMOUS_VALUE = "nobody";

    private static final String DEFAULT_NOSESSION_VALUE = "nosession";

    private static final String DEFAULT_NOCOMMUNE_VALUE = "nocity";

    private static final String ANONYMOUS_VALUE_CONFIG_KEY = "utenteAnonimo";

    private static final String NOCOMMUNE_VALUE_CONFIG_KEY = "enteNonPresente";

    private static final String NOSESSION_VALUE_CONFIG_KEY = "sessioneNonInizializzata";

    private String anonymousValue = DEFAULT_ANONYMOUS_VALUE;

    private String noCommuneValue = DEFAULT_NOCOMMUNE_VALUE;

    private String noSessionValue = DEFAULT_NOSESSION_VALUE;

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.Filter#destroy()
     */
    @Override
    public void destroy() {

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
     * javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
	    FilterChain filterChain) throws IOException, ServletException {

	StringBuilder stringBuilder = new StringBuilder().append("[MDC ");

	HttpServletRequest httpServletRequest = (HttpServletRequest) request;
	if (httpServletRequest != null) {
	    PeopleContext pplContext = PeopleContext.create(httpServletRequest);
	    if (pplContext != null) {
		PplUser pplUser = pplContext.getUser();
		City commune = pplContext.getCommune();
		if (pplUser != null) {
		    stringBuilder.append(pplUser.getUserID() + " ");
		} else {
		    stringBuilder.append(this.getAnonymousValue() + " ");
		}
		if (commune != null) {
		    stringBuilder.append(commune.getKey() + " ");
		} else {
		    stringBuilder.append(this.getNoCommuneValue() + " ");
		}
	    } else {
		stringBuilder.append(this.getAnonymousValue() + " "
			+ this.getNoCommuneValue() + " ");
	    }
	    if (httpServletRequest.getSession() != null) {
		stringBuilder.append(httpServletRequest.getSession().getId()
			+ " ");
	    } else {
		stringBuilder.append(this.getNoSessionValue() + " ");
	    }
	} else {
	    stringBuilder.append(this.getAnonymousValue() + " "
		    + this.getNoCommuneValue() + " " + this.getNoSessionValue()
		    + " ");
	}
	stringBuilder.append("MDC]");

	try {

	    MDC.put(MDC_CONVERSATION_REF_KEY, stringBuilder.toString());

	    filterChain.doFilter(request, response);

	} finally {

	    MDC.remove(MDC_CONVERSATION_REF_KEY);

	}

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

	if (filterConfig.getInitParameter(ANONYMOUS_VALUE_CONFIG_KEY) != null) {
	    this.setAnonymousValue(filterConfig
		    .getInitParameter(ANONYMOUS_VALUE_CONFIG_KEY));
	}

	if (filterConfig.getInitParameter(NOCOMMUNE_VALUE_CONFIG_KEY) != null) {
	    this.setNoCommuneValue(filterConfig
		    .getInitParameter(NOCOMMUNE_VALUE_CONFIG_KEY));
	}

	if (filterConfig.getInitParameter(NOSESSION_VALUE_CONFIG_KEY) != null) {
	    this.setNoSessionValue(filterConfig
		    .getInitParameter(NOSESSION_VALUE_CONFIG_KEY));
	}

    }

    /**
     * @return the anonymousValue
     */
    private String getAnonymousValue() {
	return this.anonymousValue;
    }

    /**
     * @param anonymousValue
     *            the anonymousValue to set
     */
    private void setAnonymousValue(String anonymousValue) {
	this.anonymousValue = anonymousValue;
    }

    /**
     * @return the noSessionValue
     */
    private String getNoSessionValue() {
	return this.noSessionValue;
    }

    /**
     * @param noSessionValue
     *            the noSessionValue to set
     */
    private void setNoSessionValue(String noSessionValue) {
	this.noSessionValue = noSessionValue;
    }

    /**
     * @return the noCommuneValue
     */
    private String getNoCommuneValue() {
	return this.noCommuneValue;
    }

    /**
     * @param noCommuneValue
     *            the noCommuneValue to set
     */
    private void setNoCommuneValue(String noCommuneValue) {
	this.noCommuneValue = noCommuneValue;
    }

}
