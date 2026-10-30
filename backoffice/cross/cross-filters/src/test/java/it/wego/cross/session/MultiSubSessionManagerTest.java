package it.wego.cross.session;

import static org.hamcrest.MatcherAssert.assertThat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hamcrest.core.Is;
import org.hamcrest.core.IsNot;
import org.junit.Before;
import org.junit.Test;


public class MultiSubSessionManagerTest {

	HttpSession session;
	HttpServletRequest request;

	@Before
	public void init() throws Exception {
		session = new HttpSessionMock();
		request = new HttpServletRequestMock(session);
		
	}
	
	@Test
	public void test() throws SubSessionException {
		/*
		String subkey1 = MultiSubSessionManager.generateId();
		String subkey2 = MultiSubSessionManager.generateId();
		MultiSubSessionManager.setAttribute(request, "key", "prova");
		((HttpServletRequestMock) request).setParameter("s_code", subkey1);
		MultiSubSessionManager.setAttribute(request, "key", "subprova1");
		((HttpServletRequestMock) request).setParameter("s_code", subkey2);
		MultiSubSessionManager.setAttribute(request, "key", "subprova2");

		((HttpServletRequestMock) request).setParameter("s_code", null);
		assertThat((String)(MultiSubSessionManager.getAttribute(request, "key")), Is.is("prova"));
		((HttpServletRequestMock) request).setParameter("s_code", "");
		assertThat((String)(MultiSubSessionManager.getAttribute(request, "key")), Is.is("prova"));
		((HttpServletRequestMock) request).setParameter("s_code", subkey1);
		assertThat((String)(MultiSubSessionManager.getAttribute(request, "key")), Is.is("subprova1"));
		assertThat((String)(MultiSubSessionManager.getAttribute(request, "key")), IsNot.not("subprova2"));
		((HttpServletRequestMock) request).setParameter("s_code", subkey2);
		assertThat((String)(MultiSubSessionManager.getAttribute(request, "key")), Is.is("subprova2"));
		*/
	}

}
