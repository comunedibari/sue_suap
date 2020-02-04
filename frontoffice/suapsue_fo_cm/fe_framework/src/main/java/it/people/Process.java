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
 * http://joinup.ec.europa.eu/software/page/eupl
 * 
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * 
 * This product includes software developed by Yale University
 * 
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
 **/
/*
 * Created on 18-mag-04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package it.people;

import it.people.process.sign.ConcreteSign;
import it.people.process.view.ConcreteView;

import java.util.HashMap;

/**
 * @author thweb4
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Process implements IProcess {

    private IView view = null;
    private ConcreteView processView = null;
    private ConcreteSign processSign = null;
    private HashMap processPrint = null;
    private String processModel = null;
    private String processClass = null;
    private String name = null;
    private String paymentObserverClass = null;

    /**
	 * 
	 */
    public Process() {

    }

    /**
     * @return
     */
    public String getProcessModel() {
	return processModel;
    }

    /**
     * @return
     */
    public HashMap getProcessPrint() {
	return processPrint;
    }

    /**
     * @return
     */
    public ConcreteSign getProcessSign() {
	return processSign;
    }

    /**
     * @return
     */
    public ConcreteView getProcessView() {
	return processView;
    }

    /**
     * @return
     */
    public IView getView() {
	return view;
    }

    /**
     * @return
     */
    public String getProcessClass() {
	return processClass;
    }

    /**
     * @param string
     */
    public void setProcessModel(String string) {
	processModel = string;
    }

    /**
     * @param string
     */
    public void setProcessPrint(HashMap map) {
	processPrint = map;
    }

    /**
     * @param string
     */
    public void setProcessSign(ConcreteSign sign) {
	processSign = sign;
    }

    /**
     * @param string
     */
    public void setProcessView(ConcreteView concreteView) {
	processView = concreteView;
    }

    /**
     * @param view
     */
    public void setView(IView view) {
	this.view = view;
    }

    /**
     * @param string
     */
    public void setProcessClass(String string) {
	processClass = string;
    }

    /**
     * @return
     */
    public String getName() {
	return name;
    }

    /**
     * @param string
     */
    public void setName(String string) {
	name = string;
    }

    /**
     * @return Returns the paymentObserverClass.
     */
    public String getPaymentObserverClass() {
	return paymentObserverClass;
    }

    /**
     * @param paymentObserverClass
     *            The paymentObserverClass to set.
     */
    public void setPaymentObserverClass(String paymentObserverClass) {
	this.paymentObserverClass = paymentObserverClass;
    }
}
