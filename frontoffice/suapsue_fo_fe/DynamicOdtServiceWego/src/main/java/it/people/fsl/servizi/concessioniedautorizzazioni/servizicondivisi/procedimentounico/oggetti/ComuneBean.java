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
 * http://www.osor.eu/eupl
 * 
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
**/
package it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti;

import java.io.Serializable;

/**
 * @author federicog
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ComuneBean extends IndirizzoBean  implements Serializable{
	private String codEnte = null;
	private String codClasseEnte = null;
	private String ccb = null;
	private String ccp = null;
	private String tp_prg = null;
	private String prg_href = null;
	private String aoo = null;
	private String tipAggregazione = null;
    private Sportello sportello = null;
    private String codBf = null;
    private String codIstat = null;
	
	/**
	 * 
	 */
	public ComuneBean() {
		super();
		codEnte = "";
		codClasseEnte = "";
		ccb = "";
		ccp = "";
		tp_prg = "";
		prg_href = "";
		aoo = "";
		tipAggregazione ="";
        sportello = new Sportello();
        codBf = "";
        codIstat = "";
	}
	/**
	 * @return Returns the aoo.
	 */
	public String getAoo() {
		return aoo;
	}
	/**
	 * @param aoo The aoo to set.
	 */
	public void setAoo(String aoo) {
		this.aoo = aoo;
	}
	/**
	 * @return Returns the ccb.
	 */
	public String getCcb() {
		return ccb;
	}
	/**
	 * @param ccb The ccb to set.
	 */
	public void setCcb(String ccb) {
		this.ccb = ccb;
	}
	/**
	 * @return Returns the ccp.
	 */
	public String getCcp() {
		return ccp;
	}
	/**
	 * @param ccp The ccp to set.
	 */
	public void setCcp(String ccp) {
		this.ccp = ccp;
	}
	/**
	 * @return Returns the codClasseEnte.
	 */
	public String getCodClasseEnte() {
		return codClasseEnte;
	}
	/**
	 * @param codClasseEnte The codClasseEnte to set.
	 */
	public void setCodClasseEnte(String codClasseEnte) {
		this.codClasseEnte = codClasseEnte;
	}
	/**
	 * @return Returns the codEnte.
	 */
	public String getCodEnte() {
		return codEnte;
	}
	/**
	 * @param codEnte The codEnte to set.
	 */
	public void setCodEnte(String codEnte) {
		this.codEnte = codEnte;
	}
	/**
	 * @return Returns the prg_href.
	 */
	public String getPrg_href() {
		return prg_href;
	}
	/**
	 * @param prg_href The prg_href to set.
	 */
	public void setPrg_href(String prg_href) {
		this.prg_href = prg_href;
	}
	/**
	 * @return Returns the tp_prg.
	 */
	public String getTp_prg() {
		return tp_prg;
	}
	/**
	 * @param tp_prg The tp_prg to set.
	 */
	public void setTp_prg(String tp_prg) {
		this.tp_prg = tp_prg;
	}
	/**
	 * @return Returns the tipAggregazione.
	 */
	public String getTipAggregazione() {
		return tipAggregazione;
	}
	/**
	 * @param tipAggregazione The tipAggregazione to set.
	 */
	public void setTipAggregazione(String tipAggregazione) {
		this.tipAggregazione = tipAggregazione;
	}
    /**
     * @return Returns the sportello.
     */
    public Sportello getSportello() {
        return sportello;
    }
    /**
     * @param sportello The sportello to set.
     */
    public void setSportello(Sportello sportello) {
        this.sportello = sportello;
    }
    public String getCodBf() {
        return codBf;
    }
    public void setCodBf(String codBf) {
        this.codBf = codBf;
    }
    public String getCodIstat() {
        return codIstat;
    }
    public void setCodIstat(String codIstat) {
        this.codIstat = codIstat;
    }
}
