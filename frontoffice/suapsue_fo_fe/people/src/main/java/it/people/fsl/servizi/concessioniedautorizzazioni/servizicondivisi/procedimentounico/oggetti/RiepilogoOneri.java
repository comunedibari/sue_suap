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
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author riccardob
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class RiepilogoOneri implements Serializable{
	private List oneriBean;
	private double totale;

	public List getOneriBean() {
		return oneriBean;
	}
	public void setOneriBean(List oneriBean) {
		this.oneriBean = oneriBean;
	}
	public double getTotale() {
	    NumberFormat nf = NumberFormat.getInstance(java.util.Locale.ITALIAN);
	    nf.setMaximumFractionDigits(2);
		try {
//            return Double.parseDouble(nf.format(totale));
		    return (nf.parse(nf.format(totale))).doubleValue();
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return totale;
        }catch (ParseException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return totale;
        }
//	    return totale;
	}
	public void setTotale(double totale) {
		this.totale = totale;
	}
	/**
	 * 
	 */
	public RiepilogoOneri() {
		oneriBean =new ArrayList();
		totale=0;
		// 
	}
	
	public void addOneriBean(OneriBean bean){
	    oneriBean.add(bean);
	}
}
