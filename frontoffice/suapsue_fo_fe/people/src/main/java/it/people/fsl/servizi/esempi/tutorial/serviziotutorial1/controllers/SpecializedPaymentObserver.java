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
 * Created on 11-mag-2005
 *
 */
package it.people.fsl.servizi.esempi.tutorial.serviziotutorial1.controllers;

import it.people.util.payment.EsitoPagamento;
import it.people.util.payment.PaymentException;
import it.people.util.payment.PaymentProcess;

/**
 * @author Michele Fabbri - Cedaf s.r.l.
 * Questa classe ha l'unico scopo di verificare il corretto funzionamento
 * del framework nel determinare le interfacce implementate in una classe
 * derivata. 
 */
public class SpecializedPaymentObserver extends PaymentObserver
{
	public void service(EsitoPagamento esitoPagamento, PaymentProcess process) 
	throws PaymentException	{
	    super.service(esitoPagamento, process);
	}
}
