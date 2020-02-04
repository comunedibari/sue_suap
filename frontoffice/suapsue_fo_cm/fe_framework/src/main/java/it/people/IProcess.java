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
public interface IProcess {

    public String getProcessModel();

    public HashMap getProcessPrint();

    public ConcreteSign getProcessSign();

    public ConcreteView getProcessView();

    public IView getView();

    public String getProcessClass();

    public String getName();

    public String getPaymentObserverClass();

    public void setProcessModel(String string);

    public void setProcessPrint(HashMap map);

    public void setProcessSign(ConcreteSign sign);

    public void setProcessView(ConcreteView view);

    public void setView(IView view);

    public void setProcessClass(String string);

    public void setName(String string);

    public void setPaymentObserverClass(String paymentObserverClass);

}
