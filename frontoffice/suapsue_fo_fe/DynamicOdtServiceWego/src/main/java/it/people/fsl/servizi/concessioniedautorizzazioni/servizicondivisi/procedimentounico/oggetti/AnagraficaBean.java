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

public class AnagraficaBean extends BaseBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 0L;

    public AnagraficaBean() {
        dichiarante = new DichiaranteBean();
        professionista = new ProfessionistaBean();
        attivita = new AttivitaBean();
        tribunale = new LuogoBean();
        cameraCommercio = new LuogoBean();
        datiPersonaDeleganteBean = new DatiPersonaDeleganteBean();
        datiAziendaNoProfitDeleganteBean = new DatiAziendaNoProfitDeleganteBean();
        datiProfessionistaBean = new ProfessionistaBean();
        complete = false;
    }

    private DichiaranteBean dichiarante;

    private ProfessionistaBean professionista;

    private AttivitaBean attivita;

    private LuogoBean tribunale;

    private LuogoBean cameraCommercio;

    private DatiPersonaDeleganteBean datiPersonaDeleganteBean;

    private DatiAziendaNoProfitDeleganteBean datiAziendaNoProfitDeleganteBean;
    
    private ProfessionistaBean datiProfessionistaBean;

    private boolean complete;

    public void setDichiarante(DichiaranteBean dichiarante) {
        this.dichiarante = dichiarante;
    }

    public DichiaranteBean getDichiarante() {
        return dichiarante;
    }

    public void setProfessionista(ProfessionistaBean professionista) {
        this.professionista = professionista;
    }

    public ProfessionistaBean getProfessionista() {
        return professionista;
    }

    public void setAttivita(AttivitaBean attivita) {
        this.attivita = attivita;
    }

    public AttivitaBean getAttivita() {
        return attivita;
    }

    public void setTribunale(LuogoBean tribunale) {
        this.tribunale = tribunale;
    }

    public LuogoBean getTribunale() {
        return tribunale;
    }

    public void setCameraCommercio(LuogoBean cameraCommercio) {
        this.cameraCommercio = cameraCommercio;
    }

    public LuogoBean getCameraCommercio() {
        return cameraCommercio;
    }

    /**
     * @return Returns the datiAziendaNoProfitDeleganteBean.
     */
    public DatiAziendaNoProfitDeleganteBean getDatiAziendaNoProfitDeleganteBean() {
        return datiAziendaNoProfitDeleganteBean;
    }

    /**
     * @param datiAziendaNoProfitDeleganteBean
     *            The datiAziendaNoProfitDeleganteBean to set.
     */
    public void setDatiAziendaNoProfitDeleganteBean(DatiAziendaNoProfitDeleganteBean datiAziendaNoProfitDeleganteBean) {
        this.datiAziendaNoProfitDeleganteBean = datiAziendaNoProfitDeleganteBean;
    }

    /**
     * @return Returns the datiPersonaDeleganteBean.
     */
    public DatiPersonaDeleganteBean getDatiPersonaDeleganteBean() {
        return datiPersonaDeleganteBean;
    }

    /**
     * @param datiPersonaDeleganteBean
     *            The datiPersonaDeleganteBean to set.
     */
    public void setDatiPersonaDeleganteBean(DatiPersonaDeleganteBean datiPersonaDeleganteBean) {
        this.datiPersonaDeleganteBean = datiPersonaDeleganteBean;
    }

    /**
     * @return Returns the complete.
     */
    public boolean isComplete() {
        return complete;
    }

    /**
     * @param complete
     *            The complete to set.
     */
    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public ProfessionistaBean getDatiProfessionistaBean() {
        return datiProfessionistaBean;
    }

    public void setDatiProfessionistaBean(ProfessionistaBean datiProfessionistaBean) {
        this.datiProfessionistaBean = datiProfessionistaBean;
    }

}
