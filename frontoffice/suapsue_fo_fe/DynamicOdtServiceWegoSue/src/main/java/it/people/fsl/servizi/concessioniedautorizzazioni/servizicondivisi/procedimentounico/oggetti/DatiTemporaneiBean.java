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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author massimof
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DatiTemporaneiBean {
    private Set stackRami;
    private List alberoOneriList;
    private List opzioniSettoreAttivitaDescr;
    private String scelte;
    private List comuni;
    private List servizi;
    private List eventiVita;
    private String jspChiamante;
    private String eventoVitaSel;
    private String servizioSel;
    private RiepilogoOneri riepilogoOneri;
    private ProcedimentoSempliceBean procSemplice;
    private List allegati;
    private List normative;
    private List arrInt;
    private List arrSettOpe;
    private String[] opsVec;
    private String[] oneriVec;
    private String codiceRamo;
    private boolean saltatoStepPrecedente;
    private Set padriOperazioni;
    private boolean attivaOneri;
    private boolean attivaPagamento;
    private double totalePagato;
    private double totalePagatoCommissioni;
    private String serviceIdPagamenti;
    private List tmpCodiciCheckPerRoot;
    private List listaMappeCheckPerRoot;
    private HashMap comboAnagrafica;
    // Se settata true vengono saltati alcuni step
    private boolean iterVeloce;
    
    /**
     * @return Returns the codiceRamo.
     */
    public String getCodiceRamo() {
        return codiceRamo;
    }
    /**
     * @param codiceRamo The codiceRamo to set.
     */
    public void setCodiceRamo(String codiceRamo) {
        this.codiceRamo = codiceRamo;
    }
    /**
     * 
     */
    public DatiTemporaneiBean() {
        stackRami = new TreeSet();
        alberoOneriList = new ArrayList();
        opzioniSettoreAttivitaDescr = new ArrayList();
//        scelte = new String();
        comuni = new ArrayList();
        servizi = new ArrayList();
        eventiVita = new ArrayList();
//        jspChiamante = new String();
//        eventoVitaSel = new String();
//        servizioSel = new String();
        riepilogoOneri = new RiepilogoOneri();
        procSemplice = new ProcedimentoSempliceBean();
        allegati = new ArrayList();
        normative = new ArrayList();
        arrInt = new ArrayList();
        arrSettOpe = new ArrayList();
        opsVec = new String[0];
        saltatoStepPrecedente = false;
        padriOperazioni = new TreeSet(new OperazioneBeanComparator());
        attivaOneri = true;
        tmpCodiciCheckPerRoot = new ArrayList();
        listaMappeCheckPerRoot = new ArrayList();
        iterVeloce = false;
    }
    /**
     * @return Returns the arrSettOpe.
     */
    public List getArrSettOpe() {
        return arrSettOpe;
    }
    /**
     * @param arrSettOpe The arrSettOpe to set.
     */
    public void setArrSettOpe(List arrSettOpe) {
        this.arrSettOpe = arrSettOpe;
    }
    /**
     * @return Returns the allegati.
     */
    public List getAllegati() {
        return allegati;
    }
    /**
     * @param allegati The allegati to set.
     */
    public void setAllegati(List allegati) {
        this.allegati = allegati;
    }
    /**
     * @return Returns the arrInt.
     */
    public List getArrInt() {
        return arrInt;
    }
    /**
     * @param arrInt The arrInt to set.
     */
    public void setArrInt(List arrInt) {
        this.arrInt = arrInt;
    }
    /**
     * @return Returns the normative.
     */
    public List getNormative() {
        return normative;
    }
    /**
     * @param normative The normative to set.
     */
    public void setNormative(List normative) {
        this.normative = normative;
    }
    /**
     * @return Returns the procSemplice.
     */
    public ProcedimentoSempliceBean getProcSemplice() {
        return procSemplice;
    }
    /**
     * @param procSemplice The procSemplice to set.
     */
    public void setProcSemplice(ProcedimentoSempliceBean procSemplice) {
        this.procSemplice = procSemplice;
    }
    /**
     * @return Returns the alberoOneriList.
     */
    public List getAlberoOneriList() {
        return alberoOneriList;
    }
    /**
     * @param alberoOneriList The alberoOneriList to set.
     */
    public void setAlberoOneriList(List alberoOneriList) {
        this.alberoOneriList = alberoOneriList;
    }
    /**
     * @return Returns the comuni.
     */
    public List getComuni() {
        return comuni;
    }
    /**
     * @param comuni The comuni to set.
     */
    public void setComuni(List comuni) {
        this.comuni = comuni;
    }
    /**
     * @return Returns the eventiVita.
     */
    public List getEventiVita() {
        return eventiVita;
    }
    /**
     * @param eventiVita The eventiVita to set.
     */
    public void setEventiVita(List eventiVita) {
        this.eventiVita = eventiVita;
    }
    /**
     * @return Returns the eventoVitaSel.
     */
    public String getEventoVitaSel() {
        return eventoVitaSel;
    }
    /**
     * @param eventoVitaSel The eventoVitaSel to set.
     */
    public void setEventoVitaSel(String eventoVitaSel) {
        this.eventoVitaSel = eventoVitaSel;
    }
    /**
     * @return Returns the jspChiamante.
     */
    public String getJspChiamante() {
        return jspChiamante;
    }
    /**
     * @param jspChiamante The jspChiamante to set.
     */
    public void setJspChiamante(String jspChiamante) {
        this.jspChiamante = jspChiamante;
    }
    /**
     * @return Returns the ozioniSettoreAttivitaDescr.
     */
    public List getOzioniSettoreAttivitaDescr() {
        return opzioniSettoreAttivitaDescr;
    }
    /**
     * @param ozioniSettoreAttivitaDescr The ozioniSettoreAttivitaDescr to set.
     */
    public void setOzioniSettoreAttivitaDescr(List ozioniSettoreAttivitaDescr) {
        this.opzioniSettoreAttivitaDescr = ozioniSettoreAttivitaDescr;
    }
    /**
     * @return Returns the riepilogoOneri.
     */
    public RiepilogoOneri getRiepilogoOneri() {
        return riepilogoOneri;
    }
    /**
     * @param riepilogoOneri The riepilogoOneri to set.
     */
    public void setRiepilogoOneri(RiepilogoOneri riepilogoOneri) {
        this.riepilogoOneri = riepilogoOneri;
    }
    /**
     * @return Returns the scelte.
     */
    public String getScelte() {
        return scelte;
    }
    /**
     * @param scelte The scelte to set.
     */
    public void setScelte(String scelte) {
        this.scelte = scelte;
    }
    /**
     * @return Returns the servizi.
     */
    public List getServizi() {
        return servizi;
    }
    /**
     * @param servizi The servizi to set.
     */
    public void setServizi(List servizi) {
        this.servizi = servizi;
    }
    /**
     * @return Returns the stackRami.
     */
    public Set getStackRami() {
        return stackRami;
    }
    /**
     * @param stackRami The stackRami to set.
     */
    public void setStackRami(Set stackRami) {
        this.stackRami = stackRami;
    }
    
    public void addStackRami(String str){
        stackRami.add(str);
    }

    public void addAlberoOneriList(OneriBean bean){
        alberoOneriList.add(bean);
    }

    public void addOpzioniSettoreAttivitaDescr(String str){
        opzioniSettoreAttivitaDescr.add(str);
    }
    
    public void addComuni(ComuneBean bean){
        comuni.add(bean);
    }
    
    public void addServizi(ServiziBean bean){
        servizi.add(bean);
    }

    public void addEventiVita(EventiVitaBean bean){
        eventiVita.add(bean);
    }
    /**
     * @return Returns the opzioniSettoreAttivitaDescr.
     */
    public List getOpzioniSettoreAttivitaDescr() {
        return opzioniSettoreAttivitaDescr;
    }
    /**
     * @param opzioniSettoreAttivitaDescr The opzioniSettoreAttivitaDescr to set.
     */
    public void setOpzioniSettoreAttivitaDescr(List opzioniSettoreAttivitaDescr) {
        this.opzioniSettoreAttivitaDescr = opzioniSettoreAttivitaDescr;
    }
    /**
     * @return Returns the servizioSel.
     */
    public String getServizioSel() {
        return servizioSel;
    }
    /**
     * @param servizioSel The servizioSel to set.
     */
    public void setServizioSel(String servizioSel) {
        this.servizioSel = servizioSel;
    }
    
    public void addAllegati(AllegatiBean bean){
        allegati.add(bean);
    }

    public void addNormative(NormativaBean bean){
        normative.add(bean);
    }

    public void addArrInt(InterventoBean bean){
        arrInt.add(bean);
    }
    
    public void addArrSettOpe(BaseBean bean){
        arrSettOpe.add(bean);
    }
    /**
     * @return Returns the opsVec.
     */
    public String[] getOpsVec() {
        return opsVec;
    }
    /**
     * @param opsVec The opsVec to set.
     */
    public void setOpsVec(String[] opsVec) {
        this.opsVec = opsVec;
    }
    
    public void addOpsVec(String str) {
        int size = opsVec.length + 1;
        String[] temp = new String[size];
    
        try {
            for (int i = 0; i < opsVec.length; i++) {
                temp[i] = opsVec[i];
            }
            temp[size - 1] = str;
            opsVec = temp;
            //opsVec=vec;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * @return Returns the saltatoStepPrecedente.
     */
    public boolean isSaltatoStepPrecedente() {
        return saltatoStepPrecedente;
    }
    /**
     * @param saltatoStepPrecedente The saltatoStepPrecedente to set.
     */
    public void setSaltatoStepPrecedente(boolean saltatoStepPrecedente) {
        this.saltatoStepPrecedente = saltatoStepPrecedente;
    }
    /**
     * @return Returns the oneriVec.
     */
    public String[] getOneriVec() {
        return oneriVec;
    }
    /**
     * @param oneriVec The oneriVec to set.
     */
    public void setOneriVec(String[] oneriVec) {
        this.oneriVec = oneriVec;
    }
    
    public void addOneriVec(String str) {
        int size = oneriVec.length + 1;
        String[] temp = new String[size];
    
        try {
            for (int i = 0; i < oneriVec.length; i++) {
                temp[i] = oneriVec[i];
            }
            temp[size - 1] = str;
            oneriVec = temp;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * @return Returns the padriOperazioni.
     */
    public Set getPadriOperazioni() {
        return padriOperazioni;
    }
    /**
     * @param padriOperazioni The padriOperazioni to set.
     */
    public void setPadriOperazioni(Set padriOperazioni) {
        this.padriOperazioni = padriOperazioni;
    }
    
    public void addPadriOperazioni(OperazioneBean bean){
        padriOperazioni.add(bean);
    }
    /**
     * @return Returns the attivaOneri.
     */
    public boolean isAttivaOneri() {
        return attivaOneri;
    }
    /**
     * @param attivaOneri The attivaOneri to set.
     */
    public void setAttivaOneri(boolean attivaOneri) {
        this.attivaOneri = attivaOneri;
    }
    /**
     * @return Returns the attivaPagamento.
     */
    public boolean isAttivaPagamento() {
        return attivaPagamento;
    }
    /**
     * @param attivaPagamento The attivaPagamento to set.
     */
    public void setAttivaPagamento(boolean attivaPagamento) {
        this.attivaPagamento = attivaPagamento;
    }
    public double getTotalePagato() {
        return totalePagato;
    }
    public void setTotalePagato(double totalePagato) {
        this.totalePagato = totalePagato;
    }
    public double getTotalePagatoCommissioni() {
        return totalePagatoCommissioni;
    }
    public void setTotalePagatoCommissioni(double totalePagatoCommissioni) {
        this.totalePagatoCommissioni = totalePagatoCommissioni;
    }
    public String getServiceIdPagamenti() {
        return serviceIdPagamenti;
    }
    public void setServiceIdPagamenti(String serviceIdPagamenti) {
        this.serviceIdPagamenti = serviceIdPagamenti;
    }
    public List getListaMappeCheckPerRoot() {
        return listaMappeCheckPerRoot;
    }
    public void setListaMappeCheckPerRoot(List listaMappeCheckPerRoot) {
        this.listaMappeCheckPerRoot = listaMappeCheckPerRoot;
    }
    public List getTmpCodiciCheckPerRoot() {
        return tmpCodiciCheckPerRoot;
    }
    public void setTmpCodiciCheckPerRoot(List tmpCodiciCheckPerRoot) {
        this.tmpCodiciCheckPerRoot = tmpCodiciCheckPerRoot;
    }
    public HashMap getComboAnagrafica() {
        return comboAnagrafica;
    }
    public void setComboAnagrafica(HashMap comboAnagrafica) {
        this.comboAnagrafica = comboAnagrafica;
    }
    public boolean isIterVeloce() {
        return iterVeloce;
    }
    public void setIterVeloce(boolean iterVeloce) {
        this.iterVeloce = iterVeloce;
    }
}
