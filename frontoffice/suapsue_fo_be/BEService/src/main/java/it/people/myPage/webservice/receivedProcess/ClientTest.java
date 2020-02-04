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
package it.people.myPage.webservice.receivedProcess;

import it.people.myPage.webservice.receivedProcess.oggetti.InfoBean;
import java.net.URL;
import java.util.Date;

import javax.xml.namespace.QName;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.ser.BeanDeserializerFactory;
import org.apache.axis.encoding.ser.BeanSerializerFactory;

public class ClientTest {

	
	public static void main(String[] args) throws Exception {
//		if (args.length!=6){
//			System.out.println("Parametri mancanti o insufficenti");
//			System.out.println("Parametri necessari :");
//			System.out.println(" 1. Url WS di notifica (es : http://localhost:8080/BEService/services/setEventService)");
//			System.out.println(" 2. Descrizione evento (es : PrasaCaricoPraticaBO");
//			System.out.println(" 3. Id BO (es : VBG)");
//			System.out.println(" 4. Id pratica People (es : CTTTCN57P51F205W-A001505-2945567)");
//			System.out.println(" 5. Url di visura (es : http://localhost:8080/vbg/visura)");
//			System.out.println(" 6. note (es : nessunaNota)");
//			return;
//		}
		Call call = (Call) new Service().createCall();
		call.setTargetEndpointAddress(new URL("http://10.10.45.158:8080/BEService/services/setEventService"));
		QName qnameProd = new QName("setEventService", "InfoBean");
		Class classeProd = InfoBean.class;
		call.registerTypeMapping(classeProd, qnameProd, BeanSerializerFactory.class,BeanDeserializerFactory.class);
		call.setOperationName(new QName("setEventService", "setInfoProcess"));
		InfoBean info = new InfoBean();
		info.setDescrizione_evento("DESCRIZIONE di TEST");
		info.setId_bo("VBG TEST");
		info.setProcess_data_id("0000000-TEST");
		Date d = new Date();
		info.setTimestamp_evento(d.getTime());
		info.setUrl_visura("http://TEST:8080/TEST");
		info.setVisibilita(true);
		info.setAltreInfo("...altre info");
		Object rispostaWS = call.invoke(new Object[]{info});
		String risp = (String) rispostaWS;
		if (risp.equalsIgnoreCase("OK")){
			System.out.println("INSERIMENTO AVVENUTO CON SUCCESSO");
		} else {
			System.out.println("QUALCOSA E' ANDATO STORTO");
		}
	} 
}
