/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.interceptor;

import com.google.common.collect.ImmutableMap;
import it.wego.cross.service.ConfigurationService;
import it.wego.cross.utils.Utils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 *
 * @author Gabriele
 */
@Component
public class SelectUGInterceptor extends HandlerInterceptorAdapter {

    public static final String CURRENT_SELECTED_UG = "CURRENT_SELECTED_UG";
    public static final String PREVIOUS_URL = "previous_url";

    @Autowired
    private ConfigurationService configurationService;
    private String selectUGUrl;

    public void setSelectUGUrl(String selectUGUrl) {
        this.selectUGUrl = selectUGUrl;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        if (Utils.e(session.getAttribute(CURRENT_SELECTED_UG))) {
            String previousUrl = request.getRequestURI();
            String crossApplicationName = "/"+configurationService.getCachedConfiguration("name.application",null,null);
            previousUrl = previousUrl.replaceFirst(crossApplicationName, "");
            
            response.sendRedirect(Utils.createRedirectUrl(request, selectUGUrl, new ImmutableMap.Builder<String, String>().put(PREVIOUS_URL, Utils.encodeB64(previousUrl)).build()));
            return false;
        } else {
            return true;
        }
    }
}
