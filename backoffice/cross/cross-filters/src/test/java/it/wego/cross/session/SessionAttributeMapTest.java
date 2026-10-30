package it.wego.cross.session;

import static org.hamcrest.MatcherAssert.assertThat;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hamcrest.core.Is;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Before;
import org.junit.Test;


public class SessionAttributeMapTest {

	HttpSession session;
	HttpServletRequestMock request;

	@Before
	public void init() {
		session = new HttpSessionMock();
		request = new HttpServletRequestMock(session);
	}
	
	@Test
	public void test() throws SubSessionException {
		/*
		String subkey1 = MultiSubSessionManager.generateId();
		String subkey2 = MultiSubSessionManager.generateId();
		
		request.setParameter("s_code", subkey1);
		MultiSubSessionManager.setAttribute(request, "key2", "subprova1");
		assertThat(session.getAttribute("key"), IsInstanceOf.instanceOf(SessionAttributeMap.class));
		assertThat((String)((SessionAttributeMap)session.getAttribute("key2")).get(subkey1), Is.is("subprova1"));

		request.setParameter("s_code", subkey2);
		MultiSubSessionManager.setAttribute(request, "key3", "subprova2");
		assertThat((String)((SessionAttributeMap)session.getAttribute("key3")).get(subkey2), Is.is("subprova2"));
		*/
	}


}
