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
 * Creato il 12-giu-2007 da Cedaf s.r.l.
 *
 */
package it.people.util.payment.request;

/**
 * @author Michele Fabbri - Cedaf s.r.l.
 * 
 *         La classe contiene le informazioni aggiuntive che il servizio pu�
 *         passare alla Observer del pagamento, al momento � previsto il solo
 *         campo data che � trasferito alla classe observer senza interventi
 *         da parte del framework. Il valore del campo � libero e deciso dal
 *         servizio stesso.
 * 
 */
public class ObserverData {

    String data;

    public ObserverData() {
	// data = "";
    }

    public ObserverData(String data) {
	this.data = data;
    }

    /**
     * Ritorna i dati impostati all'avvio del pagamento dal servizio
     * 
     * @return
     */
    public String getData() {
	return data;
    }

    /**
     * Imposta i dati specifici del servizio di front-end che potranno essere
     * recuperati durante l'esecuzione della classe observer. Il formato dei
     * dati � libero e deciso dal servizio stesso.
     * 
     * @param data
     */
    public void setData(String data) {
	this.data = data;
    }
}
