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
import java.util.*;

import it.people.process.PplData;

/**
 * @author federicog
 * 
 * StepBean.java
 * 
 * @date 7-ott-2005
 * 
 */
public class StepBean extends BaseBean implements Serializable {
    private int activityIndex;
    private int stepIndex;
    private int subStepIndex;
    private String activityId;
    private String stepId;
    private String[] opsVec;
    private String[] oneriVec;
    private String codiceRamo;
    private Set oneriAnticipati;
    private Map sezioniCompilabiliBean;
    private List dichiarazioniStatiche;
//    private AnagraficaBean anagrafica;
//    private AssociazioneDiCategoriaBean datiAssociazionecategoria;

    //by Cedaf - INIZIO
    public StepBean(){
        this.opsVec=new String[0];
        this.oneriVec=new String[0];
//        this.oneriAnticipati=new TreeSet(new BaseBeanComparator());
        this.sezioniCompilabiliBean=new HashMap();
        this.dichiarazioniStatiche=new ArrayList();
//        this.anagrafica = new AnagraficaBean();
//        this.datiAssociazionecategoria=new AssociazioneDiCategoriaBean();
    }
    //by Cedaf - FINE


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
    private String data;

    /**
     * @return Returns the activityIndex.
     */
    public int getActivityIndex() {
        return activityIndex;
    }
    /**
     * @param activityIndex The activityIndex to set.
     */
    public void setActivityIndex(int activityIndex) {
        this.activityIndex = activityIndex;
    }
    /**
     * @return Returns the stepIndex.
     */
    public int getStepIndex() {
        return stepIndex;
    }
    /**
     * @param stepIndex The stepIndex to set.
     */
    public void setStepIndex(int stepIndex) {
        this.stepIndex = stepIndex;
    }
    /**
     * @return Returns the data.
     */
    public String getData() {
        return data;
    }
    /**
     * @param data The data to set.
     */
    public void setData(String data) {
        this.data = data;
    }
    /**
     * @return Returns the subStepIndex.
     */
    public int getSubStepIndex() {
        return subStepIndex;
    }
    /**
     * @param subStepIndex The subStepIndex to set.
     */
    public void setSubStepIndex(int subStepIndex) {
        this.subStepIndex = subStepIndex;
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
     * @return Returns the oneriBean.
     */
    public Set getOneriAnticipati() {
        return oneriAnticipati;
    }
    /**
     * @param oneriBean The oneriBean to set.
     */
    public void setOneriAnticipati(Set oneriAnticipati) {
        this.oneriAnticipati = oneriAnticipati;
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
     * @return Returns the sezioniCompilabiliBean.
     */
    public Map getSezioniCompilabiliBean() {
        return sezioniCompilabiliBean;
    }
    /**
     * @param sezioniCompilabiliBean The sezioniCompilabiliBean to set.
     */
    public void setSezioniCompilabiliBean(Map sezioniCompilabiliBean) {
        this.sezioniCompilabiliBean = sezioniCompilabiliBean;
    }
    
//    public void addSezioniCompilabiliBean(String key, SezioniBean bean) {
//        try {
//            sezioniCompilabiliBean.put(key, bean);
//        } catch (Exception e) {
//        }
//    }
    /**
     * @return Returns the activityId.
     */
    public String getActivityId() {
        return activityId;
    }
    /**
     * @param activityId The activityId to set.
     */
    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }
    /**
     * @return Returns the stepId.
     */
    public String getStepId() {
        return stepId;
    }
    /**
     * @param stepId The stepId to set.
     */
    public void setStepId(String stepId) {
        this.stepId = stepId;
    }
//    public AnagraficaBean getAnagrafica() {
//        return anagrafica;
//    }
//    public void setAnagrafica(AnagraficaBean anagrafica) {
//        this.anagrafica = anagrafica;
//    }
//    public List getDichiarazioniStatiche() {
//        return dichiarazioniStatiche;
//    }
//    public void setDichiarazioniStatiche(List dichiarazioniStatiche) {
//        this.dichiarazioniStatiche = dichiarazioniStatiche;
//    }
//    public AssociazioneDiCategoriaBean getDatiAssociazionecategoria() {
//        return datiAssociazionecategoria;
//    }
//    public void setDatiAssociazionecategoria(AssociazioneDiCategoriaBean datiAssociazionecategoria) {
//        this.datiAssociazionecategoria = datiAssociazionecategoria;
//    }
}
