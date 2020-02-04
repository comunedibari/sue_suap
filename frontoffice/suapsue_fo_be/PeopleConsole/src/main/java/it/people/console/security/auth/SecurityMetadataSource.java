/**
 * Copyright (c) 2011, Regione Emilia-Romagna, Italy
 *  
 * Licensed under the EUPL, Version 1.1 or - as soon they
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the
 * Licence.
 * 
 * For convenience a plain text copy of the English version
 * of the Licence can be found in the file LICENCE.txt in
 * the top-level directory of this software distribution.
 * 
 * You may obtain a copy of the Licence in any of 22 European
 * Languages at:
 * 
 * http://joinup.ec.europa.eu/software/page/eupl
 * 
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * 
 * This product includes software developed by Yale University
 * 
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
**/
package it.people.console.security.auth;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.AntUrlPathMatcher;
import org.springframework.security.web.util.UrlMatcher;

import it.people.console.persistence.utils.DataSourceFactory;
import it.people.console.system.AbstractLogger;
import it.people.console.utils.Constants;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 23/giu/2011 18.36.17
 *
 */
public class SecurityMetadataSource extends AbstractLogger implements FilterInvocationSecurityMetadataSource {

    private static final Set<String> HTTP_METHODS = new HashSet<String>(Arrays.asList("DELETE", "GET", "HEAD", "OPTIONS", "POST", "PUT", "TRACE"));

    private Map<String, Map<Object, Collection<ConfigAttribute>>> httpMethodMap =
        new HashMap<String, Map<Object, Collection<ConfigAttribute>>>();

    private UrlMatcher urlMatcher;

    private boolean stripQueryStringFromUrls;
    
	public SecurityMetadataSource() {

		if (logger.isDebugEnabled()) {
			logger.debug("Initializing url matcher to AntUrlPathMatcher...");
		}
		this.setUrlMatcher(new AntUrlPathMatcher());
		if (logger.isDebugEnabled()) {
			logger.debug("AntUrlPathMatcher initialized.");
		}
		if (logger.isDebugEnabled()) {
			logger.debug("Reading meta data from database...");
		}
		this.readMetadata();
		if (logger.isDebugEnabled()) {
			logger.debug("Meta data read.");
		}
		
	}

	private void readMetadata() {
		
		String query = "select url, method, configAttribute from pc_security_metadata_urls";
		
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("Getting connection to database...");
			}
			connection = DataSourceFactory.getInstance().getDataSource().getConnection();
			if (logger.isDebugEnabled()) {
				logger.debug("Preparing call for query '" + query + "'...");
			}
			callableStatement = connection.prepareCall(query);
			if (logger.isDebugEnabled()) {
				logger.debug("Executing query...");
			}
			resultSet = callableStatement.executeQuery();
			if (logger.isDebugEnabled()) {
				logger.debug("Reading result set...");
			}
			while(resultSet.next()) {
				String pattern = resultSet.getString(1);
				String method = resultSet.getString(2);
				Collection<ConfigAttribute> attributes = getConfigAttributes(resultSet.getString(3));
				if (logger.isDebugEnabled()) {
					logger.debug("Adding secure url:\n" + 
							"\tPattern = '" + pattern + "'\n" + 
							"\tMethod = '" + method + "'\n" + 
							"\tAttributes = '" + attributes + "'\n");
				}
				addSecureUrl(pattern, method, attributes);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Reading result set done.");
			}
		} catch(SQLException e) {
			
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (callableStatement != null) {
					callableStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch(SQLException e) {
				logger.warn("Error while closing objects.");
			}
		}
		
	}

    private void addSecureUrl(String pattern, String method, Collection<ConfigAttribute> attrs) {
        Map<Object, Collection<ConfigAttribute>> mapToUse = getRequestMapForHttpMethod(method);

        mapToUse.put(urlMatcher.compile(pattern), attrs);

        if (logger.isDebugEnabled()) {
            logger.debug("Added URL pattern: " + pattern + "; attributes: " + attrs +
                    (method == null ? "" : " for HTTP method '" + method + "'"));
        }
    }

    private Map<Object, Collection<ConfigAttribute>> getRequestMapForHttpMethod(String method) {
        if (method != null && !HTTP_METHODS.contains(method)) {
            throw new IllegalArgumentException("Unrecognised HTTP method: '" + method + "'");
        }

        Map<Object, Collection<ConfigAttribute>> methodRequestMap = httpMethodMap.get(method);

        if (methodRequestMap == null) {
            methodRequestMap = new LinkedHashMap<Object, Collection<ConfigAttribute>>();
            httpMethodMap.put(method, methodRequestMap);
        }

        return methodRequestMap;
    }

    public Collection<ConfigAttribute> getAllConfigAttributes() {
        Set<ConfigAttribute> allAttributes = new HashSet<ConfigAttribute>();

        for (Map.Entry<String, Map<Object, Collection<ConfigAttribute>>> entry : httpMethodMap.entrySet()) {
            for (Collection<ConfigAttribute> attrs : entry.getValue().values()) {
                allAttributes.addAll(attrs);
            }
        }

        return allAttributes;
    }

    public Collection<ConfigAttribute> getAttributes(Object object) {
        if ((object == null) || !this.supports(object.getClass())) {
            throw new IllegalArgumentException("Object must be a FilterInvocation");
        }

        String url = ((FilterInvocation) object).getRequestUrl();
        String method = ((FilterInvocation) object).getHttpRequest().getMethod();

        return lookupAttributes(url, method);
    }

    public final Collection<ConfigAttribute> lookupAttributes(String url, String method) {
        if (stripQueryStringFromUrls) {
            // Strip anything after a question mark symbol, as per SEC-161. See also SEC-321
            int firstQuestionMarkIndex = url.indexOf("?");

            if (firstQuestionMarkIndex != -1) {
                url = url.substring(0, firstQuestionMarkIndex);
            }
        }

        if (urlMatcher.requiresLowerCaseUrl()) {
            url = url.toLowerCase();

            if (logger.isDebugEnabled()) {
                logger.debug("Converted URL to lowercase, from: '" + url + "'; to: '" + url + "'");
            }
        }

        // Obtain the map of request patterns to attributes for this method and lookup the url.
        Collection<ConfigAttribute> attributes = extractMatchingAttributes(url, httpMethodMap.get(method));

        // If no attributes found in method-specific map, use the general one stored under the null key
        if (attributes == null) {
            attributes = extractMatchingAttributes(url, httpMethodMap.get(null));
        }

        return attributes;
    }

    private Collection<ConfigAttribute> extractMatchingAttributes(String url, Map<Object, Collection<ConfigAttribute>> map) {
        if (map == null) {
            return null;
        }

        final boolean debug = logger.isDebugEnabled();

        for (Map.Entry<Object, Collection<ConfigAttribute>> entry : map.entrySet()) {
            Object p = entry.getKey();
            boolean matched = urlMatcher.pathMatchesUrl(entry.getKey(), url);

            if (debug) {
                logger.debug("Candidate is: '" + url + "'; pattern is " + p + "; matched=" + matched);
            }

            if (matched) {
                return entry.getValue();
            }
        }
        return null;
    }
    
    private Collection<ConfigAttribute> getConfigAttributes(String mappedConfig) {
    	
    	Collection<ConfigAttribute> result = new Vector<ConfigAttribute>();
    	
    	StringTokenizer tokenizer = new StringTokenizer(mappedConfig, ",");
    	while(tokenizer.hasMoreTokens()) {
    		String role = tokenizer.nextToken().trim();
    		result.add(new SecurityConfig(role));
    	}
    	
    	if (logger.isDebugEnabled()) {
    		logger.debug("Verifying if root and console admin roles was added to security metadata source...");
    	}
    	SecurityConfig rootSecurityConfig = new SecurityConfig(Constants.Security.ROOT_ROLE);
    	SecurityConfig consoleAdminSecurityConfig = new SecurityConfig(Constants.Security.CONSOLE_ADMIN_ROLE);
    	if (!result.contains(rootSecurityConfig)) {
        	if (logger.isDebugEnabled()) {
        		logger.debug("Root role was not added to security metadata source, adding...");
        	}
    		result.add(rootSecurityConfig);
    	}
    	if (!result.contains(consoleAdminSecurityConfig)) {
        	if (logger.isDebugEnabled()) {
        		logger.debug("Console admin role was not added to security metadata source, adding...");
        	}
    		result.add(consoleAdminSecurityConfig);
    	}
    	
    	return result;
    	
    }

    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }

    protected UrlMatcher getUrlMatcher() {
        return urlMatcher;
    }

    protected void setUrlMatcher(UrlMatcher urlMatcher) {
        this.urlMatcher = urlMatcher;
    }
    
    public boolean isConvertUrlToLowercaseBeforeComparison() {
        return urlMatcher.requiresLowerCaseUrl();
    }

    public void setStripQueryStringFromUrls(boolean stripQueryStringFromUrls) {
        this.stripQueryStringFromUrls = stripQueryStringFromUrls;
    }

}
