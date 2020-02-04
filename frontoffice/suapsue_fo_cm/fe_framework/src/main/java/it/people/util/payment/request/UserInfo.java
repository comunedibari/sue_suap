// FrontEnd Plus GUI for JAD
// DeCompiled : UserInfo.class

// Personalizzazione installazione comune di Bologna

package it.people.util.payment.request;

public class UserInfo {

    protected String m_sIdentificativoUtente;
    protected String m_sNome;
    protected String m_sCognome;

    public String getCognome() {
	return m_sCognome;
    }

    public void setCognome(String sCognome) {
	m_sCognome = sCognome;
    }

    public String getIdentificativoUtente() {
	return m_sIdentificativoUtente;
    }

    public void setIdentificativoUtente(String sIdentificativoUtente) {
	m_sIdentificativoUtente = sIdentificativoUtente;
    }

    public String getNome() {
	return m_sNome;
    }

    public void setNome(String sNome) {
	m_sNome = sNome;
    }

    public UserInfo() {
	m_sIdentificativoUtente = m_sNome = m_sCognome = null;
    }

    public UserInfo(String sIdentificativoUtente, String sNome, String sCognome) {
	m_sIdentificativoUtente = sIdentificativoUtente;
	m_sNome = sNome;
	m_sCognome = sCognome;
    }
}
