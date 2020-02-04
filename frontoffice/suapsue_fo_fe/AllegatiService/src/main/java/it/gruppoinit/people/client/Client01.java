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
package it.gruppoinit.people.client;



import java.io.FileOutputStream;
import java.net.URL;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.axis.encoding.ser.BeanDeserializerFactory;
import org.apache.axis.encoding.ser.BeanSerializerFactory;
import org.apache.commons.codec.binary.Base64;

import it.gruppoinit.people.allegatiManager.AllegatiManager;
import it.gruppoinit.people.oggetti.BaseBean;

public class Client01 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		AllegatiManager am = new AllegatiManager();
//		BaseBean[] lista = am.getListaAllegati("000001", "CTTTCN57P51F205W-A001503-3972262", null);
//		for (int i = 0; i < lista.length; i++) {
//			BaseBean baseBean = lista[i];
//			System.out.println(" - "+baseBean.getNomeFile()+ "("+baseBean.getCodice()+")");
//		}
//		String wsImportResponse="";
		try {
			
//		      //Creo il canale per effettuare la richiesta
//		      Call call = (Call) new Service().createCall();
//		      //Conosciamo l'url dove il servizio risponde
//		      call.setTargetEndpointAddress(new URL("http://10.10.45.158:8080/AllegatiService/services/listaAllegatiService"));
//		      //Definiamo il metodo da chiamare
//		      call.setOperationName(new QName("getListaAllegati"));
//		      //Chiamiamo il metodo sul web service, passando un parametro
//		      Object rispostaWS = call.invoke(new Object[]{"000001","CTTTCN57P51F205W-A001503-3972262",""});
//		      //Facciamo l'operazione di cast e mostriamo il messaggio
//		      String messaggio = (String)rispostaWS; 
//		      System.out.println(">> "+messaggio);
			
//			Call call = (Call) new Service().createCall();
//			call.setTargetEndpointAddress(new URL("http://10.10.45.158:8080/AllegatiService/services/listaAllegatiService"));
//			QName qnameProd = new QName("listaAllegatiService", "BaseBean");
//			Class classeProd = BaseBean.class;
//			call.registerTypeMapping(classeProd, qnameProd, BeanSerializerFactory.class,BeanDeserializerFactory.class);
//			call.setOperationName(new QName("listaAllegatiService", "getListaAllegati"));
//			Object rispostaWS = call.invoke(new Object[]{"000001","CTTTCN57P51F205W-A001503-3972262","1"});
//			BaseBean[] files = (BaseBean[]) rispostaWS;

			
			
//			Call call2 = (Call) new Service().createCall();
//			call2.setTargetEndpointAddress(new URL("http://10.10.45.158:8080/AllegatiService/services/listaAllegatiService"));
//			call2.setOperationName(new QName("allegatiService", "getFile"));
//			for (int i=0; i<files.length; i++){
//				Object rispostaWS2 = call2.invoke(new Object[]{"000001","CTTTCN57P51F205W-A001503-3972262",files[i].getCodice()});
//				String r = (String) rispostaWS2;
//				byte[] ret = Base64.decodeBase64(r.getBytes());
//	            String file = "c:\\Temp\\temp\\"+files[i].getNomeFile();
//	            FileOutputStream fileoutputstream = new FileOutputStream(file);
//	            fileoutputstream.write(ret);
//	            fileoutputstream.close();
//			}
//			System.out.println("END");
			

		      //Creo il canale per effettuare la richiesta
		      Call call = (Call) new Service().createCall();
		      //Conosciamo l'url dove il servizio risponde
		      call.setTargetEndpointAddress(new URL("http://people.rer.ri:8080/AllegatiService/services/listaAllegatiService"));
		      //Definiamo il metodo da chiamare
		      call.setOperationName(new QName("getFile"));
		      //Chiamiamo il metodo sul web service, passando un parametro
		      Object rispostaWS = call.invoke(new Object[]{"000001","RFRCR71P26A326O-AA01537-5242240","1376062228584"});
		      //Facciamo l'operazione di cast e mostriamo il messaggio
		      String messaggio = (String)rispostaWS; 
		      System.out.println(">> "+messaggio);
			
		} catch (Exception e){
			System.out.println("Errore");
		}
	}

}
