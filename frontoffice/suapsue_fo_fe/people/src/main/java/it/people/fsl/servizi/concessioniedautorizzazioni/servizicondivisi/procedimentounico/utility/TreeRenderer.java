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
package it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.BaseBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.StepBean;

import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.OneriBean;
import it.people.wrappers.IRequestWrapper;

/**
 * @author riccardob
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class TreeRenderer {
    private static final String JSEVNTCTRLFIELDS = "onblur";

    private static final String DEFAULT_FIELD_SIZE = "10";
    
    public static String renderOneriTree(List root, ProcessData dataForm, IRequestWrapper request) {
        String answer = "<ul class='mktree' id='tree1'>\n";

        Iterator it = root.iterator();
        while (it.hasNext()) {
            OneriBean bean = (OneriBean) it.next();
            dataForm.getDatiTemporanei().setTmpCodiciCheckPerRoot(new ArrayList());
            answer += trovaFoglie(bean, true, dataForm, request);
            dataForm.getDatiTemporanei().getListaMappeCheckPerRoot().add(dataForm.getDatiTemporanei().getTmpCodiciCheckPerRoot());
        }
        answer += "\n</ul>";

        return answer;
    }

    private static String trovaFoglie(OneriBean bean, boolean firstLap, ProcessData dataForm, IRequestWrapper request) {
        
        firstLap = false;
        String answer = "";
        List alist = bean.getFigli();
        String checked = "";
        String[] oneriSelezionati = null;
        oneriSelezionati = dataForm.getOneriVec();
//        LinkedList list = (LinkedList) request.getUnwrappedRequest().getSession().getAttribute("history");
//        if(list != null && list.size() > 0){
//            StepBean sb = (StepBean) list.getLast();
//            oneriSelezionati = sb.getOneriVec();
//        }
            
        String idValueCheckBox = "";
        //..se � popoplato l'arraylist dei figli allora chiamo ricorsivamentela
        // funzione
        if (alist.size() > 0) {
            if (!(null == bean.getOneriFormula() || bean.getOneriFormula().equals(""))) {
                //.. se getOneriFormula <> "" allora deve presentare il
                // checkbox

                idValueCheckBox = bean.getCampoFormula() + "|"
                                  + bean.getOneriFormula() + "|"
                                  + bean.getCodice();

                if (!(null == oneriSelezionati)) {
                    for (int j = 0; j < oneriSelezionati.length; j++) {
                        if (oneriSelezionati[j].equalsIgnoreCase(idValueCheckBox)) {
                            checked = "checked";
                            break;
                        }
                    }
                } else {
                    checked = "";
                }
                answer += "\n<li id='"
                          + idValueCheckBox
                          + "'><input type='checkbox' name='data.oneriVec' value='"
                          + idValueCheckBox + "' onclick='controlChk();' "
                          + checked + ">&nbsp;" + bean.getDescrizione();
                dataForm.getDatiTemporanei().getTmpCodiciCheckPerRoot().add(bean.getCodice());
            } else {
                if (!firstLap) { // elimina la prima descrizione che �
                                 // ridondante
                    answer += "\n<li>&nbsp;&nbsp;&nbsp;"
                              + bean.getDescrizione();
                }
            }
            Iterator it = alist.iterator();
            while (it.hasNext()) {
                OneriBean bean1 = (OneriBean) it.next();

                if (!firstLap) {
                    answer += "\n<ul>";
                }
                answer += trovaFoglie(bean1, false, dataForm, request);
                if (!firstLap) {
                    answer += "\n</ul>";
                }
            }
            answer += "</li>";
        } else {
            if (!(null == bean.getOneriFormula() || bean.getOneriFormula().equals(""))) {
                //.. se getOneriFormula <> "" allora deve presentare il
                // checkbox

                idValueCheckBox = bean.getCampoFormula() + "|"
                                  + bean.getOneriFormula() + "|"
                                  + bean.getCodice();

                if (!(null == oneriSelezionati)) {
                    for (int j = 0; j < oneriSelezionati.length; j++) {
                        if (oneriSelezionati[j].equalsIgnoreCase(idValueCheckBox)) {
                            checked = "checked";
                            break;
                        }
                    }
                } else {
                    checked = "";
                }
                answer += "\n<li id='"
                          + idValueCheckBox
                          + "'><input type='checkbox' name='data.oneriVec' value='"
                          + idValueCheckBox + "' onclick='controlChk();' "
                          + checked + ">&nbsp;" + bean.getDescrizione();
                dataForm.getDatiTemporanei().getTmpCodiciCheckPerRoot().add(bean.getCodice());
            } else {
                answer += "\n<li>&nbsp;&nbsp;&nbsp;" + bean.getDescrizione();
            }
        }
        return answer;
    }

    public static String renderTag(String type, int pIntera, int pDecimale, String valoreDefault, String tagName, String valore, List<String> ids, List<String> descriptions) {
        if ((null == valore)) {
            valore = "";
        }
        if (type.equalsIgnoreCase("L")) {
            return renderTagSelect(tagName, valoreDefault, valore, ids, descriptions);
        } else if (type.equalsIgnoreCase("D")) {
            return renderTagDouble(tagName, pIntera, pDecimale, valore);
        } else if (type.equalsIgnoreCase("I")) {
            return renderTagInteger(tagName, pIntera, valore);
        }
        return "";
    }

    private static String renderTagSelect(String tagName, String valoreDefault, String valore, List<String> ids, List<String> descriptions) {
        String tag = "\n<select name=\"F" + tagName + "\" >\n";
        String selected = "";
        int i = 0;
        for (String id : ids) {
            selected = "";
            if (id.equalsIgnoreCase(valore)) {
                selected = "selected";
            }
            tag += "<option "+selected+" value=\"" + id + "\">" + descriptions.get(i++)
                   + "</option>\n";
        }
        tag += "</select>";
        return tag;
    }

    private static String renderTagDouble(String tagName, int pIntera, int pDecimale, String valore) {
        return "\n<input type=\"text\" name=\"F" + tagName + "\" size=\""
               + DEFAULT_FIELD_SIZE + "\" " + JSEVNTCTRLFIELDS
               + "=\"return ctrl(this, 'decimal', '" + pIntera + "', '"
               + pDecimale + "')\"  value=\"" + valore + "\" >";
    }

    private static String renderTagInteger(String tagName, int pIntera, String valore) {
        return "\n<input type=\"text\" name=\"F" + tagName + "\" size=\""
               + DEFAULT_FIELD_SIZE + "\" " + JSEVNTCTRLFIELDS
               + "=\"return ctrl(this, 'integer', '" + pIntera
               + "','0')\"  value=\"" + valore + "\">";
    }

    //////////////////////////////////////////////
    public static String renderCompileOneri(ArrayList root, String[] oneriSelezionati, ProcessData dataForm) throws SQLException {
        String answer = "";

        Iterator it = root.iterator();
        String oneriLista = "";
        for (int i = 0; i < oneriSelezionati.length; i++) {

            oneriLista += oneriSelezionati[i] + ",";
        }

        BaseBean resultBean = new BaseBean();
        BaseBean bb = new BaseBean();
        while (it.hasNext()) {
            OneriBean bean = (OneriBean) it.next();
            //answer +="<ul>\n<li>"+bean.getDescrizione()+"\n<ul>";
            bb.setCodice("true");
            recurseBean(bean, oneriLista, resultBean, bb, dataForm);
            //answer +="\n</ul>\n</li>\n</ul>" ;
            //answer.add(bean);
        }

        return resultBean.getDescrizione();
    }

    private static void recurseBean(OneriBean bean, String listaOneriSelezionati, BaseBean result, BaseBean firstLap, ProcessData dataForm) throws SQLException {
        List list = bean.getFigli();
        //ArrayList toAdd= new ArrayList();
        String resultStr = null;
        /*
         * if (!(null==result.getDescrizione())){
         * resultStr=result.getDescrizione(); }else{ resultStr=""; }
         */
        if (list.size() > 0) {
            Iterator it = list.iterator();

            while (it.hasNext()) {
                if (!(null == result.getDescrizione())) {
                    resultStr = result.getDescrizione();
                } else {
                    resultStr = "";
                }
                OneriBean dBean = (OneriBean) it.next();
                String confronto = dBean.getCampoFormula() + ("|")
                                   + (dBean.getOneriFormula()) + ("|")
                                   + (dBean.getCodice());
                
                
                // Test
                Iterator iter = dataForm.getOneriAnticipati().iterator();
                while(iter.hasNext()){
                    OneriBean sb = (OneriBean)iter.next();
                    String stringaOnere = sb.getCampoFormula() + ("|")
						                    + (sb.getOneriFormula()) + ("|")
						                    + (sb.getCodice());
                    if(confronto.equalsIgnoreCase(stringaOnere)){
                        dBean.setValoreFormula(sb.getValoreFormula());
                        break;
                    }
                }
                
                if (listaOneriSelezionati.indexOf(confronto) != -1) {
                    //ho trovato un onere selezionato
                    if (firstLap.getCodice().equalsIgnoreCase("true")) {
                        // scrivo intestazioni
                        resultStr += "<table width='100%'><tr><td colspan=2 class='title'><b>"
                                     + dBean.getDescrizioneAntenato().toUpperCase();
                        resultStr += "</b></td></tr>";
                        firstLap.setCodice("false");
                    }
                    resultStr += "\n<tr><td colspan=2><b>";
                    if (!(null == dBean.getDefinizione().getTipo() || dBean.getDefinizione().getTipo().equals(""))) {
                        resultStr += dBean.getDescrizione() + "</b></td></tr>";
                        resultStr += "<tr><td valign=top>"
                                     + dBean.getDefinizione().getLabel()
                                     + "</td><td valign=top>";
                        resultStr += renderTag(dBean.getDefinizione().getTipo(), dBean.getDefinizione().getParteIntera(), dBean.getDefinizione().getParteDecimale(), dBean.getDefinizione().getValoreDefault(), dBean.getCodice(), dBean.getValoreFormula(), dBean.getDefinizione().getIDs(), dBean.getDefinizione().getDescriptions());
                        resultStr += "</tr>\n";
                        if(dBean.getAltriOneri().size()>0){
                            // Significa che abbiamo a che fare con oneri formula con pi� di un campo formula
                            Iterator itFigli = dBean.getAltriOneri().iterator();
                            while(itFigli.hasNext()){
                                OneriBean dBeanFiglio = (OneriBean)itFigli.next();
                                resultStr += "<tr><td valign=top>"
                                    + dBeanFiglio.getDefinizione().getLabel()
                                    + "</td><td valign=top>";
                                resultStr += renderTag(dBeanFiglio.getDefinizione().getTipo(), dBeanFiglio.getDefinizione().getParteIntera(), dBeanFiglio.getDefinizione().getParteDecimale(), dBeanFiglio.getDefinizione().getValoreDefault(), dBeanFiglio.getCodice()+dBeanFiglio.getCampoFormula(), dBeanFiglio.getValoreFormula(), dBeanFiglio.getDefinizione().getIDs(), dBeanFiglio.getDefinizione().getDescriptions());
                                resultStr += "</tr>\n";
                            }
                        }
                    } else { // non � un campo imputabile ma solamente importo
                             // fisso ...
                        if (firstLap.getCodice().equalsIgnoreCase("true")) {
                            // scrivo intestazioni
                            resultStr += "<table width='100%'><tr><td colspan=2 class='title'><b>"
                                         + dBean.getDescrizioneAntenato().toUpperCase();
                            resultStr += "</b></td></tr>";
                            firstLap.setCodice("false");
                        }
                        resultStr += "<tr><td colspan=2><b>"
                                     + dBean.getDescrizione()
                                     + "</b></td></tr>\n";
                    }
                    resultStr += "\n\n";
                    result.setDescrizione(resultStr);
                } else {
                    recurseBean(dBean, listaOneriSelezionati, result, firstLap, dataForm);
                }
            }
        } else {
            String confronto1 = bean.getCampoFormula() + ("|")
                                + (bean.getOneriFormula()) + ("|")
                                + (bean.getCodice());
            if (listaOneriSelezionati.indexOf(confronto1) != -1) {
                //ho trovato un onere selezionato
                if (firstLap.getCodice().equalsIgnoreCase("true")) {
                    // scrivo intestazioni
                    resultStr += "<table width='100%'><tr><td colspan=2 class='title'><b>"
                                 + bean.getDescrizioneAntenato().toUpperCase();
                    resultStr += "</b></td></tr>";
                    firstLap.setCodice("false");
                }
                resultStr += "\n<tr><td colspan=2><b>";
                if (!(null == bean.getDefinizione().getTipo() || bean.getDefinizione().getTipo().equals(""))) {
                    resultStr += bean.getDescrizione() + "</b></td></tr>";
                    resultStr += "<tr><td valign=top>"
                                 + bean.getDefinizione().getLabel()
                                 + "</td><td valign=top>";
                    resultStr += renderTag(bean.getDefinizione().getTipo(), bean.getDefinizione().getParteIntera(), bean.getDefinizione().getParteDecimale(), bean.getDefinizione().getValoreDefault(), bean.getCodice(), bean.getValoreFormula(), bean.getDefinizione().getIDs(), bean.getDefinizione().getDescriptions());
                    resultStr += "</tr>\n";
                } else { // non � un campo imputabile ma solamente importo fisso
                         // ...
                    resultStr += "<tr><td colspan=2><b>"
                                 + bean.getDescrizione() + "</b></td></tr>\n";
                    firstLap.setCodice("false");
                }
                resultStr += "\n\n";
                result.setDescrizione(resultStr);
            } else {
                //.. recurseBean(bean,listaOneriSelezionati);
            }

        }

    }

    public class BooleanClass {
        boolean bool = true;

        protected void setBool(boolean value) {
            bool = value;
        }

        protected boolean isBool() {
            return bool;
        }
    }

}
