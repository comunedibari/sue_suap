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

public class Costant {
	
	public final static String BookmarkType = "TB";
	public final static int bookmarkTypeCompleteCod = 0;
	public final static String bookmarkTypeCompleteLabel = "COMPLETO";
	public final static int bookmarkTypeCortesiaCod = 1;
	public final static String bookmarkTypeCortesiaLabel = "CORTESIA";
	public final static int bookmarkTypeLivello2Cod = 2;
	public final static String bookmarkTypeLivello2Label = "LIVELLO2";
	
	public final static String FirmaPraticaType = "FP";
	public final static int conFirmaCod = 0;
	public final static String conFirmaLabel = "TRUE";
	public final static int senzaFirmaCod = 1;
	public final static String senzaFirmaLabel = "FALSE";


	public final static String PagamentiType = "AP";
	public final static int disabilitaPagamentoCod = 0;
	public final static String disabilitaPagamentoLabel = "DISABILITA";
	public final static int forzaPagamentoCod = 1;
	public final static String forzaPagamentoLabel = "FORZA_PAGAMENTO";
	public final static int pagamentoOpzionaleCod = 2;
	public final static String pagamentoOpzionaleLabel = "OPZIONALE";

	public final static String modalitPagamentoType = "AP_MP";
	public final static String modalitPagamentoOpzionaleType = "AP_MP";
	public final static int modalitPagamentoSoloOnlineCod = 1;
	public final static String modalitaPagamentoSoloOnlineLabel = "SOLO_ONLINE";
	public final static int modalitaPagamentoOneOfflineCod = 2;
	public final static String modalitaPagamentoOneOfflineLabel = "ONLINE_OFFLINE";
	
	public final static String ConInvioType = "invioB";
	public final static int conInvioCod = 0;
	public final static String conInvioLabel = "TRUE";
	public final static int senzaInvioCod = 1;
	public final static String senzaInvioLabel = "FALSE";
	
	public static final String CODICE_ATTESTAZIONE_VERSAMENTO_ONERI = "SYSCPVERS";
	public static final String TITOLO_ATTETAZIONE_VERSAMENTO_ONERI = "copia del versamento oneri anticipati";
	
	public static final String ATTIVAZIONE_ONERI_PAGAMENTO_ON_LINE = "1";
	public static final String ATTIVAZIONE_ONERI_PAGAMENTO_OFF_LINE = "2";
	public static final String ATTIVAZIONE_ONERI_SOLO_CALCOLO = "3";
	
	public static final String MODALITA_PAGAMENTO_ON_LINE = "ON_LINE";
	public static final String MODALITA_PAGAMENTO_OFF_LINE = "OFF_LINE";
	
	public class PplUser {
		
		public static final String AMMINISTRATORE_BOOKMARK = "AMMINISTRATORE_BOOKMARK";

		public static final String AMMINISTRATORE_STAMPE = "AMMINISTRATORE_STAMPE";

		public static final String AMMINISTRATORE_PROCEDIMENTO_UNICO = "AMMINISTRATORE_PROCEDIMENTO_UNICO";
		
	}
	
}
