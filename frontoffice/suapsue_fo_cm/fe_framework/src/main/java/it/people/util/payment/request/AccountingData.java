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
 * Created on 20-apr-2006
 *
 */
package it.people.util.payment.request;

import java.util.ArrayList;

/**
 * @author Michele Fabbri - Cedaf s.r.l.
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class AccountingData {
    protected ArrayList importiContabili = new ArrayList();
    protected ArrayList entiDestinatari = new ArrayList();

    public ArrayList getEntiDestinatari() {
	return entiDestinatari;
    }

    public void setEntiDestinatari(ArrayList entiDestinatari) {
	this.entiDestinatari = entiDestinatari;
    }

    public ArrayList getImportiContabili() {
	return importiContabili;
    }

    public void setImportiContabili(ArrayList importiContabili) {
	this.importiContabili = importiContabili;
    }
}
