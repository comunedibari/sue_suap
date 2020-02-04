// FrontEnd Plus GUI for JAD
// DeCompiled : UserDataExt.class

package it.people.util.payment.request;

// Referenced classes of package it.people.util.payment.request:
//            UserInfo

// Personalizzazione installazione comune di Bologna

public class UserDataExt {

    protected UserInfo m_oUtenteLogin;
    protected UserInfo m_oUtenteOperante;
    protected UserInfo m_oUtenteMandante;

    public UserInfo getUtenteLogin() {
	return m_oUtenteLogin;
    }

    public void setUtenteLogin(UserInfo oUtenteLogin) {
	m_oUtenteLogin = oUtenteLogin;
    }

    public UserInfo getUtenteMandante() {
	return m_oUtenteMandante;
    }

    public void setUtenteMandante(UserInfo oUtenteMandante) {
	m_oUtenteMandante = oUtenteMandante;
    }

    public UserInfo getUtenteOperante() {
	return m_oUtenteOperante;
    }

    public void setUtenteOperante(UserInfo oUtenteOperante) {
	m_oUtenteOperante = oUtenteOperante;
    }

    public UserDataExt() {
	m_oUtenteLogin = m_oUtenteOperante = m_oUtenteMandante = null;
    }

    public UserDataExt(UserInfo oUtenteLogin, UserInfo oUtenteOperante,
	    UserInfo oUtenteMandante) {
	m_oUtenteLogin = oUtenteLogin;
	m_oUtenteOperante = oUtenteOperante;
	m_oUtenteMandante = oUtenteMandante;
    }
}
