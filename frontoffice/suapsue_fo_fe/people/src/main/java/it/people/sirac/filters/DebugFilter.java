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
package it.people.sirac.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DebugFilter implements Filter {

	private static Logger logger = LoggerFactory.getLogger(DebugFilter.class);
  
  public void init(FilterConfig filterConfig) throws ServletException {
  }

  public void doFilter(ServletRequest _request, ServletResponse _response, FilterChain _chain) throws IOException, ServletException {
    
//	if (logger.isDebugEnabled()) {
//      logger.debug("doFilter - start");
//    }

	HttpServletRequest request = (HttpServletRequest) _request;
    HttpServletResponse response = (HttpServletResponse) _response;
    
    String requestURI = request.getRequestURI();
//    String contextPath = request.getContextPath();
//    String queryString = request.getQueryString();

    if (logger.isDebugEnabled()) {
      logger.debug("requestURI is: " + requestURI);
//      logger.debug("contextPath is: " + contextPath);
//      logger.debug("query string is: " + queryString);
    }
    
//    if (logger.isDebugEnabled()) {
//     	logger.debug("doFilter - end");
//    }
    _chain.doFilter(request, response);
        
  }

  public void destroy() {
  }
}
