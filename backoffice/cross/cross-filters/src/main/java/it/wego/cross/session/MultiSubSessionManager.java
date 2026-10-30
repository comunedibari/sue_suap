package it.wego.cross.session;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MultiSubSessionManager {

	public static final Logger APP = LoggerFactory.getLogger("app");

	public static String SESSION_PARAMETER = "s_code";

	private MultiSubSessionManager() {}

	public static synchronized String generateId() {
		return UUID.randomUUID().toString();
	}

	public static synchronized String getSessionId(HttpServletRequest request) {
		String sessionId = (String) request.getHeader(SESSION_PARAMETER);
		if(sessionId == null || sessionId.length() == 0)
			sessionId = (String) request.getParameter(SESSION_PARAMETER);
		return sessionId;
	}

	public static synchronized void setAttribute(HttpServletRequest request, String id, Object value) {
		String sessionValue = getSessionId(request);
		if(!verifySession(sessionValue))
			throw new SubSessionException("Session empty or not valid");
		setAttribute(request.getSession(), id, sessionValue, value);
	}

	protected static synchronized void setAttribute(HttpSession session, String id, String currentSubSession, Object value) {
		APP.info("Session: " + currentSubSession);
		SessionAttributeMap map = (SessionAttributeMap) session.getAttribute(id);
		if(map == null) {
			synchronized (MultiSubSessionManager.class) {
				if(map == null) {
					map = new SessionAttributeMap();
					session.setAttribute(id, map);
				}
			}
		}
		map.put(currentSubSession, value);
	}

	public static synchronized Object getAttribute(HttpServletRequest request, String id) {
		String sessionValue = getSessionId(request);
		Object value = null;
		if(verifySession(sessionValue))
			value = getAttribute(request.getSession(), id, sessionValue);
		return value;
	}

	public static synchronized Object getAttribute(HttpSession session, String id, String currentSubSession) {
		APP.info("Session: " + currentSubSession);
		Object sessionValue =  session.getAttribute(id);
		Object value = null;
		try {
			if(sessionValue != null) {
				if(!SessionAttributeMap.class.isInstance(sessionValue)) { 
					setAttribute(session,id,currentSubSession, sessionValue);
					value = sessionValue;
				}
				else 
					value = ((SessionAttributeMap) sessionValue).get(currentSubSession);
			}  
		} catch (Exception e) {
			throw new SubSessionException("Problems in session manager", e);
		}
		return value;
	}

	public static synchronized void removeAttribute(HttpServletRequest request, String id) {
		String sessionValue = getSessionId(request);
		if(verifySession(sessionValue))
			removeAttribute(request.getSession(), id, sessionValue);
	}
	
	protected static synchronized void  removeAttribute(HttpSession session, String id, String currentSubSessionId) {
		APP.info("Session: " + currentSubSessionId);
		Object sessionValue =  session.getAttribute(id);
		if(sessionValue != null && !SessionAttributeMap.class.isInstance(sessionValue)) 
			((SessionAttributeMap) sessionValue).remove(currentSubSessionId);
		else
			session.removeAttribute(id);
			
	}
	
	private static synchronized boolean verifySession(String sessionValue) {
		return (sessionValue != null && sessionValue.length() > 0);
	}
}
