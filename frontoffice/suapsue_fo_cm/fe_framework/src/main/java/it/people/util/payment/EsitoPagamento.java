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
//------------------------------------------------------------------------------
// Interfaccia verso il server di pagamento esterno Regulus.
// Implementazione allineata alle specifiche del documento: 
// 	"Server di Pagamento, Protocollo di attivazione v. 1.4"
//	di Sirio Candini, Data:			Gennaio 20
//------------------------------------------------------------------------------
package it.people.util.payment;

import it.people.process.common.entity.AbstractEntity;
import java.util.Date;
import org.apache.xerces.parsers.DOMParser;
import it.people.util.ActivityLogger;
import org.w3c.dom.*;
import org.xml.sax.*;
import java.io.*;
import java.text.SimpleDateFormat;

//import it.regulus.pagamenti.gestoreincassi.oggetticondivisi.utilities;

public class EsitoPagamento extends AbstractEntity {
    // Esiti pagamento
    public static final String ES_PAGAMENTO_OK = "OK"; // Successo
						       // nell'operazione
    public static final String ES_PAGAMENTO_KO = "KO"; // Autorizzazione negata
						       // dal circuito
    public static final String ES_PAGAMENTO_OP = "OP"; // Operazione in
						       // pagamento
    public static final String ES_PAGAMENTO_UK = "UK"; // Operazione sconosciuta
    public static final String ES_PAGAMENTO_ER = "ER"; // Operazione andata in
						       // errore per problemi di
						       // comunicazione con il
						       // sistema di pagamento

    protected static final String RX_DATETIME_FORMAT = "yyyyMMddHHmmss";

    protected ActivityLogger m_oTracer;

    protected String m_sNumeroOperazione;
    protected String m_sIDTransazione;
    protected Date m_dtDataTransazione;
    protected String m_sSistemaPagamento;
    protected String m_sSistemaPagamentoD;
    protected String m_sCircuitoAutorizzativo;
    protected String m_sCircuitoAutorizzativoD;
    protected String m_sCircuitoSelezionato;
    protected String m_sCircuitoSelezionatoD;
    protected long m_lImportoPagato;
    protected long m_lImportoCommissioni;
    protected String m_sIDOrdine;
    protected Date m_dtDataOrdine;
    protected String m_sEsito;
    protected String m_sDescrizioneEsito;
    protected Date m_dtDataAutorizzazione;
    protected String m_sNumeroAutorizzazione;
    protected String m_sDatiSpecifici;

    public String getNumeroOperazione() {
	return m_sNumeroOperazione;
    }

    public String getIDTransazione() {
	return m_sIDTransazione;
    }

    public Date getDataTransazione() {
	return m_dtDataTransazione;
    }

    public String getSistemaPagamento() {
	return m_sSistemaPagamento;
    }

    public String getDescrizioneSistemaPagamento() {
	return m_sSistemaPagamentoD;
    }

    public String getCircuitoAutorizzativo() {
	return m_sCircuitoAutorizzativo;
    }

    public String getDescrizioneCircuitoAutorizzativo() {
	return m_sCircuitoAutorizzativoD;
    }

    public String getCircuitoSelezionato() {
	return m_sCircuitoSelezionato;
    }

    public String getDescrizioneCircuitoSelezionato() {
	return m_sCircuitoSelezionatoD;
    }

    public long getImportoPagato() {
	return m_lImportoPagato;
    }

    public long getImportoCommissioni() {
	return m_lImportoCommissioni;
    }

    public String getIDOrdine() {
	return m_sIDOrdine;
    }

    public Date getDataOrdine() {
	return m_dtDataOrdine;
    }

    public String getEsito() {
	return m_sEsito;
    }

    public String getDescrizioneEsito() {
	return m_sDescrizioneEsito;
    }

    public Date getDataAutorizzazione() {
	return m_dtDataAutorizzazione;
    }

    public String getNumeroAutorizzazione() {
	return m_sNumeroAutorizzazione;
    }

    public String getDatiSpecifici() {
	return m_sDatiSpecifici;
    }

    protected void initFields() {
	m_sNumeroOperazione = "";
	m_sIDTransazione = "";
	m_sSistemaPagamento = "";
	m_sSistemaPagamentoD = "";
	m_sCircuitoAutorizzativo = "";
	m_sCircuitoAutorizzativoD = "";
	m_sCircuitoSelezionato = "";
	m_sCircuitoSelezionatoD = "";
	m_sIDOrdine = "";
	m_sEsito = "";
	m_sDescrizioneEsito = "";
	m_sNumeroAutorizzazione = "";
	m_dtDataOrdine = null;
	m_dtDataTransazione = null;
	m_dtDataAutorizzazione = null;
	m_lImportoPagato = 0;
	m_lImportoCommissioni = 0;
	m_sDatiSpecifici = "";
    }

    public EsitoPagamento() {
	m_oTracer = ActivityLogger.getInstance();
	initFields();
    }

    public EsitoPagamento(String sXml) {
	m_oTracer = ActivityLogger.getInstance();
	m_oTracer.log(this.getClass(), "EsitoPagamento(): xml - " + sXml,
		ActivityLogger.DEBUG);
	Document oDoc = createDocument(sXml);
	if (oDoc != null) {
	    try {
		Element oDatiPag = (Element) oDoc.getDocumentElement();
		m_sNumeroOperazione = readSectionElement(oDatiPag,
			"NumeroOperazione");
		m_sIDTransazione = readSectionElement(oDatiPag, "IDTransazione");
		m_dtDataTransazione = String2Date(readSectionElement(oDatiPag,
			"DataOraTransazione"));
		m_sSistemaPagamento = readSectionElement(oDatiPag,
			"SistemaPagamento");
		m_sSistemaPagamentoD = readSectionElement(oDatiPag,
			"SistemaPagamentoD");
		m_sCircuitoAutorizzativo = readSectionElement(oDatiPag,
			"CircuitoAutorizzativo");
		m_sCircuitoAutorizzativoD = readSectionElement(oDatiPag,
			"CircuitoAutorizzativoD");
		m_sCircuitoSelezionato = readSectionElement(oDatiPag,
			"CircuitoSelezionato");
		m_sCircuitoSelezionatoD = readSectionElement(oDatiPag,
			"CircuitoSelezionatoD");
		m_lImportoPagato = XMLValueToLong(oDatiPag, "ImportoTransato");
		m_lImportoCommissioni = XMLValueToLong(oDatiPag,
			"ImportoCommissioni");
		m_sEsito = readSectionElement(oDatiPag, "Esito");
		m_sDescrizioneEsito = readSectionElement(oDatiPag, "EsitoD");
		m_sIDOrdine = readSectionElement(oDatiPag, "IDOrdine");
		m_dtDataOrdine = String2Date(readSectionElement(oDatiPag,
			"DataOraOrdine"));
		m_dtDataAutorizzazione = String2Date(readSectionElement(
			oDatiPag, "DataOra"));
		m_sNumeroAutorizzazione = readSectionElement(oDatiPag,
			"Autorizzazione");
		m_sDatiSpecifici = readSectionElement(oDatiPag, "DatiSpecifici");
	    } catch (Exception exc) {
		initFields();
		m_oTracer.log(
			this.getClass(),
			"EsitoPagamento(): Generic Exception - "
				+ exc.toString(), ActivityLogger.ERROR);
	    }
	} else
	    initFields();
    }

    public String marshall() {
	String xml = "<PaymentData>" + "<PortaleID>PeopleFwk</PortaleID>"
		+ "<NumeroOperazione>"
		+ this.getNumeroOperazione()
		+ "</NumeroOperazione>"
		+ "<IDOrdine>"
		+ this.getIDOrdine()
		+ "</IDOrdine>"
		+ "<DataOraOrdine>"
		+ Date2String(this.getDataOrdine())
		+ "</DataOraOrdine>"
		+ "<IDTransazione>"
		+ this.getIDTransazione()
		+ "</IDTransazione>"
		+ "<DataOraTransazione>"
		+ Date2String(this.getDataTransazione())
		+ "</DataOraTransazione>"
		+ "<SistemaPagamento>"
		+ this.getSistemaPagamento()
		+ "</SistemaPagamento>"
		+ "<SistemaPagamentoD>"
		+ this.getDescrizioneSistemaPagamento()
		+ "</SistemaPagamentoD>"
		+ "<CircuitoAutorizzativo>"
		+ this.getCircuitoAutorizzativo()
		+ "</CircuitoAutorizzativo>"
		+ "<CircuitoAutorizzativoD>"
		+ this.getDescrizioneCircuitoAutorizzativo()
		+ "</CircuitoAutorizzativoD>"
		+ "<CircuitoSelezionato>"
		+ this.getCircuitoSelezionato()
		+ "</CircuitoSelezionato>"
		+ "<CircuitoSelezionatoD>"
		+ this.getDescrizioneCircuitoSelezionato()
		+ "</CircuitoSelezionatoD>"
		+ "<ImportoTransato>"
		+ LongToXMLValue(this.getImportoPagato())
		+ "</ImportoTransato>"
		+ "<ImportoAutorizzato></ImportoAutorizzato>"
		+ "<ImportoCommissioni>"
		+ LongToXMLValue(this.getImportoCommissioni())
		+ "</ImportoCommissioni>"
		+ "<Esito>"
		+ this.getEsito()
		+ "</Esito>"
		+ "<EsitoD>"
		+ this.getDescrizioneEsito()
		+ "</EsitoD>"
		+ "<DataOra>"
		+ Date2String(this.getDataAutorizzazione())
		+ "</DataOra>"
		+ "<Autorizzazione>"
		+ this.getNumeroAutorizzazione()
		+ "</Autorizzazione>"
		+ "<DatiSpecifici><![CDATA["
		+ this.getDatiSpecifici()
		+ "]]></DatiSpecifici>"
		+ "</PaymentData>";
	return xml;
    }

    protected String Date2String(java.util.Date date) {
	try {
	    return (new SimpleDateFormat(RX_DATETIME_FORMAT)).format(date);
	} catch (Exception ex) {
	}
	return "";
    }

    protected String LongToXMLValue(long val) {
	return (new Long(val)).toString();
    }

    protected Document createDocument(String sXmlMsg) {
	Document oDoc = null;
	try {
	    DOMParser parser = new DOMParser();
	    parser.setFeature("http://xml.org/sax/features/validation", false);
	    parser.parse(new InputSource(new StringReader(sXmlMsg)));
	    oDoc = parser.getDocument();
	} catch (SAXException exc) {
	    m_oTracer.log(this.getClass(),
		    "createDocument: SAXException - input string[" + sXmlMsg
			    + "] " + exc.toString(), ActivityLogger.ERROR);
	    oDoc = null;
	} catch (IOException exc) {
	    m_oTracer.log(this.getClass(),
		    "createDocument: IOException - input string[" + sXmlMsg
			    + "] " + exc.toString(), ActivityLogger.ERROR);
	    oDoc = null;
	}
	return oDoc;
    }

    protected String readSectionElement(Element oElem, String tagXml) {
	String sText = "";
	try {
	    NodeList nodeList = oElem.getElementsByTagName(tagXml);
	    if (nodeList.getLength() > 0)
		sText = getNodeValue(nodeList.item(0));
	} catch (NullPointerException exc) {
	    m_oTracer.log(this.getClass(),
		    "readSectionElement: NullPointerException - input tag["
			    + tagXml + "] ", ActivityLogger.ERROR);
	} catch (Exception exc) {
	    m_oTracer.log(this.getClass(),
		    "readSectionElement: Generic Exception - input tag["
			    + tagXml + "] " + exc.toString(),
		    ActivityLogger.ERROR);
	}
	return sText;
    }

    protected String getNodeValue(Node oNode) {
	Node oChildNode = oNode.getFirstChild();
	if (null != oChildNode)
	    return oChildNode.getNodeValue();
	else
	    return "";
    }

    protected int XMLValueToInt(Element oElem, String sTag) {
	int iValue = 0;
	String sValue = readSectionElement(oElem, sTag);
	if (sValue.length() > 0) {
	    try {
		iValue = Integer.parseInt(sValue);
	    } catch (Exception exc) {
		iValue = 0;
	    }
	}
	return iValue;
    }

    protected long XMLValueToLong(Element oElem, String sTag) {
	long dValue = 0;
	String sValue = readSectionElement(oElem, sTag);
	if (sValue.length() > 0) {
	    try {
		dValue = Long.parseLong(sValue);
	    } catch (Exception exc) {
		dValue = 0;
	    }
	}
	return dValue;
    }

    protected boolean XMLValueToFlag(Element oElem, String sTag) {
	String sValue = readSectionElement(oElem, sTag);
	return (sValue.equalsIgnoreCase("S")) ? true : false;
    }

    public java.util.Date String2Date(String sDate) {
	java.util.Date date = null;
	if ((sDate != null) && (sDate.trim().length() > 0)) {
	    try {
		date = new SimpleDateFormat(RX_DATETIME_FORMAT).parse(sDate);
	    } catch (Exception exc) {
		date = null;
	    }
	}
	return date;
    }

    public static void main(String[] args) {

	String document = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>";
	document += "<pag:RichiestadiPagamentoRataICIinModalitaAnonima xmlns:pag=\"http://egov.diviana.it/b119/Fiscali/PagamentoRataICIinModalitaAnonima\" xmlns:ogg=\"http://egov.diviana.it/b119/OggettiCondivisi\" xmlns:fis=\"http://egov.diviana.it/b119/Fiscali\" xmlns:ser=\"http://egov.diviana.it/b119/ServiziCondivisi\" xmlns:doc=\"http://egov.diviana.it/b119/DocumentiUfficiali\">";
	document += "  <ogg:IdentificatoreUnivoco>";
	document += "    <ogg:CodiceProgetto>AX23</ogg:CodiceProgetto>";
	document += "    <ogg:CodiceSistema>";
	document += "      <ogg:NomeServer>192.168.10.219</ogg:NomeServer>";
	document += "      <ogg:CodiceAmministrazione>111111</ogg:CodiceAmministrazione>";
	document += "    </ogg:CodiceSistema>";
	document += "    <ogg:DatadiRegistrazione>2011-09-28T12:00:00.000+02:00</ogg:DatadiRegistrazione>";
	document += "    <ogg:CodiceIdentificativoOperazione>NNMNNM70A01H536W-h199514-5295243</ogg:CodiceIdentificativoOperazione>";
	document += "  </ogg:IdentificatoreUnivoco>";
	document += "  <fis:TitolarenonAutenticato>";
	document += "    <ser:EstremidellaPersona>";
	document += "      <ogg:Cognome>zignani</ogg:Cognome>";
	document += "      <ogg:Nome>luca</ogg:Nome>";
	document += "      <ogg:CodiceFiscale>zgnlcu82p15h199e</ogg:CodiceFiscale>";
	document += "    </ser:EstremidellaPersona>";
	document += "  </fis:TitolarenonAutenticato>";
	document += "  <fis:PagamentoRataICI>";
	document += "    <fis:TipodiPagamentoRataICI>Acconto di una Rata</fis:TipodiPagamentoRataICI>";
	document += "    <fis:AnnodiApplicazione>2011</fis:AnnodiApplicazione>";
	document += "    <fis:ImportoTotale>";
	document += "      <ogg:Decimale>10.0</ogg:Decimale>";
	document += "      <ogg:Valuta>EUR</ogg:Valuta>";
	document += "    </fis:ImportoTotale>";
	document += "    <fis:RataICI>";
	document += "      <fis:ImportoperAbitazionePrincipale>";
	document += "        <ogg:Decimale>0.0</ogg:Decimale>";
	document += "        <ogg:Valuta>EUR</ogg:Valuta>";
	document += "      </fis:ImportoperAbitazionePrincipale>";
	document += "      <fis:ImportoperAltriFabbricati>";
	document += "        <ogg:Decimale>10.0</ogg:Decimale>";
	document += "        <ogg:Valuta>EUR</ogg:Valuta>";
	document += "      </fis:ImportoperAltriFabbricati>";
	document += "      <fis:ImportoperTerreniAgricoli>";
	document += "        <ogg:Decimale>0.0</ogg:Decimale>";
	document += "        <ogg:Valuta>EUR</ogg:Valuta>";
	document += "      </fis:ImportoperTerreniAgricoli>";
	document += "      <fis:ImportoperAreeFabbricabili>";
	document += "        <ogg:Decimale>0.0</ogg:Decimale>";
	document += "        <ogg:Valuta>EUR</ogg:Valuta>";
	document += "      </fis:ImportoperAreeFabbricabili>";
	document += "      <fis:NumeroFabbricati>0</fis:NumeroFabbricati>";
	document += "      <fis:DetrazioneperAbitazionePrincipale>";
	document += "        <ogg:Decimale>0.0</ogg:Decimale>";
	document += "        <ogg:Valuta>EUR</ogg:Valuta>";
	document += "      </fis:DetrazioneperAbitazionePrincipale>";
	document += "    </fis:RataICI>";
	document += "  </fis:PagamentoRataICI>";
	document += "  <fis:RicevutadiPagamentoOnLine>";
	document += "    <ogg:IdentificatorediRichiesta>";
	document += "      <ogg:IdentificatoreUnivoco>";
	document += "        <ogg:CodiceProgetto>AX23</ogg:CodiceProgetto>";
	document += "        <ogg:CodiceSistema>";
	document += "          <ogg:NomeServer>192.168.10.219</ogg:NomeServer>";
	document += "          <ogg:CodiceAmministrazione>111111</ogg:CodiceAmministrazione>";
	document += "        </ogg:CodiceSistema>";
	document += "        <ogg:DatadiRegistrazione>2011-09-28T12:00:00.000+02:00</ogg:DatadiRegistrazione>";
	document += "        <ogg:CodiceIdentificativoOperazione>NNMNNM70A01H536W-h199514-5294850</ogg:CodiceIdentificativoOperazione>";
	document += "      </ogg:IdentificatoreUnivoco>";
	document += "    </ogg:IdentificatorediRichiesta>";
	document += "    <ser:DatadelPagamento>2011-09-28T12:00:00.000+02:00</ser:DatadelPagamento>";
	document += "    <ser:BeneficiariodelPagamento>";
	document += "      <ser:EstremiPersonaGiuridica>";
	document += "        <ogg:DenominazioneoRagioneSociale>INFORMAZIONE NON DISPONIBILE</ogg:DenominazioneoRagioneSociale>";
	document += "        <ogg:CodiceFiscalePersonaGiuridica>00000000000</ogg:CodiceFiscalePersonaGiuridica>";
	document += "      </ser:EstremiPersonaGiuridica>";
	document += "    </ser:BeneficiariodelPagamento>";
	document += "    <ser:ImportoTotale>";
	document += "      <ogg:Decimale>11.9</ogg:Decimale>";
	document += "      <ogg:Valuta>EUR</ogg:Valuta>";
	document += "    </ser:ImportoTotale>";
	document += "    <ser:MotivazionedelPagamento>PortaleID=";
	document += "NumeroOperazione=NNMNNM70A01H536W-h199514-52948501317195512569";
	document += "IDOrdine=O20110928093834893DW";
	document += "DataOraOrdine=28/09/2011";
	document += "IDTransazione=";
	document += "DataOraTransazione=";
	document += "SistemaPagamento=XPAYL";
	document += "SistemaPagamentoD=Pag. Carta SI XPay";
	document += "CircuitoAutorizzativo=CCANY";
	document += "CircuitoAutorizzativoD=Carta di credito BankPass";
	document += "CircuitoSelezionato=CVISA";
	document += "CircuitoSelezionatoD=Carta VISA";
	document += "ImportoTransato=1190.0";
	document += "ImportoAutorizzato=";
	document += "ImportoCommissioni=190.0";
	document += "Esito=OK";
	document += "EsitoD=Successo";
	document += "DataOra=";
	document += "Autorizzazione=834776";
	document += "URLCSS=";
	document += "DatiSpecifici=</ser:MotivazionedelPagamento>";
	document += "    <ser:Commissioni>";
	document += "      <ogg:Decimale>1.9</ogg:Decimale>";
	document += "      <ogg:Valuta>EUR</ogg:Valuta>";
	document += "    </ser:Commissioni>";
	document += "    <ser:RichiestaApplicativadiPagamento>";
	document += "      <ogg:CodiceProgetto>AX23</ogg:CodiceProgetto>";
	document += "      <ogg:CodiceSistema>";
	document += "        <ogg:NomeServer>192.168.10.219</ogg:NomeServer>";
	document += "        <ogg:CodiceAmministrazione>111111</ogg:CodiceAmministrazione>";
	document += "      </ogg:CodiceSistema>";
	document += "      <ogg:DatadiRegistrazione>2011-09-28T12:00:00.000+02:00</ogg:DatadiRegistrazione>";
	document += "      <ogg:CodiceIdentificativoOperazione>NNMNNM70A01H536W-h199514-5294850</ogg:CodiceIdentificativoOperazione>";
	document += "    </ser:RichiestaApplicativadiPagamento>";
	document += "    <ser:TitolaredelPagamento>";
	document += "      <ser:EstremiPersonaGiuridica>";
	document += "        <ogg:DenominazioneoRagioneSociale>INFORMAZIONE NON DISPONIBILE</ogg:DenominazioneoRagioneSociale>";
	document += "        <ogg:CodiceFiscalePersonaGiuridica>00000000000</ogg:CodiceFiscalePersonaGiuridica>";
	document += "      </ser:EstremiPersonaGiuridica>";
	document += "    </ser:TitolaredelPagamento>";
	document += "    <ser:ImportoContabile>";
	document += "      <ogg:PoliticadiCosto>INFORMAZIONE NON DISPONIBILE</ogg:PoliticadiCosto>";
	document += "      <ogg:Importo>";
	document += "        <ogg:Decimale>0.0</ogg:Decimale>";
	document += "        <ogg:Valuta>EUR</ogg:Valuta>";
	document += "      </ogg:Importo>";
	document += "    </ser:ImportoContabile>";
	document += "    <ser:RicevutadiPagamentoOnLine>";
	document += "      <doc:Formato>INFORMAZIONE NON DISPONIBILE</doc:Formato>";
	document += "      <doc:URI>www.progettopeople.it</doc:URI>";
	document += "    </ser:RicevutadiPagamentoOnLine>";
	document += "  </fis:RicevutadiPagamentoOnLine>";
	document += "  <fis:Domicilio>";
	document += "    <ogg:IndirizzoStrutturatoCompleto>";
	document += "      <ogg:CivicoEsternoNumerico>";
	document += "        <ogg:Numero>999999</ogg:Numero>";
	document += "      </ogg:CivicoEsternoNumerico>";
	document += "      <ogg:Toponimo>";
	document += "        <ogg:DUG>Via</ogg:DUG>";
	document += "        <ogg:Denominazione>magazzini anteriori</ogg:Denominazione>";
	document += "      </ogg:Toponimo>";
	document += "      <ogg:CAP>48121</ogg:CAP>";
	document += "      <ogg:Comune>";
	document += "        <ogg:Nome>ravenna</ogg:Nome>";
	document += "        <ogg:SigladiProvinciaISTAT>RA</ogg:SigladiProvinciaISTAT>";
	document += "      </ogg:Comune>";
	document += "    </ogg:IndirizzoStrutturatoCompleto>";
	document += "  </fis:Domicilio>";
	document += "</pag:RichiestadiPagamentoRataICIinModalitaAnonima>";

	EsitoPagamento ep = new EsitoPagamento(document);

    }

}
