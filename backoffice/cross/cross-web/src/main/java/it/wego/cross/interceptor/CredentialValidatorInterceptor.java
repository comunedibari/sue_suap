package it.wego.cross.interceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import it.wego.cross.annotations.RequiresGrant;
import it.wego.cross.constants.SessionConstants;
import it.wego.cross.utils.Log;

@Component
public class CredentialValidatorInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        boolean check = true;
        HandlerMethod hm = (HandlerMethod) handler;
        RequiresGrant ann = hm.getMethodAnnotation(RequiresGrant.class);

        if (ann == null) {
            //do notihng
        }
        else if ((boolean)session.getAttribute(SessionConstants.ABILITAZIONE_CONSULTAZIONE)) {
        	Log.APP.error("invalid operation, return to " + request.getContextPath());
        	response.sendRedirect(request.getContextPath());
            check = false; 
        }

        return check; 
    }

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}
}
