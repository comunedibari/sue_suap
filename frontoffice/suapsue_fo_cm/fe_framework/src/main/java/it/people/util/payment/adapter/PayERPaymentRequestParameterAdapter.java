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
package it.people.util.payment.adapter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import it.people.util.payment.PaymentException;
import it.people.util.payment.request.EnteDestinatario;
import it.people.util.payment.request.PaymentRequestParameter;

/**
 * @author Riccardo Foraf� - Engineering Ingegneria Informatica - Genova
 *         05/set/2011 15.07.39
 */
public class PayERPaymentRequestParameterAdapter {

    public static final String FUNZIONE = "PAGAMENTO";
    public static final String COMMIT_NOTIFICA = "S";
    public static final String VALUTA = "EUR";
    public static final String ENTE_SEPARATORE_IDENTIFICATICO_DESCRIZIONE = "##|##";

    private static final String CODICE_ENTE_NULL = "000000";
    private static final String DESCRIZIONE_ENTE_NULL = "descrizione non presente nella banca dati";

    public final static String getBufferData(
	    Properties paymentSystemConfiguration,
	    PaymentRequestParameter paymentParameter, String sNumOperazione,
	    String sCodFisc, String communeId, HttpServletRequest request,
	    String returnUrl, String notifyUrl, String backUrl)
	    throws PaymentException {

	boolean riversamentoAutomatico = Boolean.parseBoolean(paymentParameter
		.getPaymentManagerSpecificData().get("RiversamentoAutomatico"));

	if (paymentParameter.getUserData() != null
		&& paymentParameter.getUserData().getPersonaFisica() != null
		&& paymentParameter.getUserData().getPersonaFisica()
			.getCodiceFiscale() != null) {
	    sCodFisc = paymentParameter.getUserData().getPersonaFisica()
		    .getCodiceFiscale();
	}

	String result = "<PaymentRequest>";
	result += "	<PortaleID>"
		+ paymentSystemConfiguration
			.getProperty("paymentsystem.portalid") + "</PortaleID>";
	result += "	<Funzione>" + FUNZIONE + "</Funzione>";
	result += "	<URLDiRitorno>"
		+ paymentSystemConfiguration.getProperty("service.url")
		+ returnUrl + "</URLDiRitorno>";
	result += "	<URLDiNotifica>"
		+ paymentSystemConfiguration.getProperty("service.url")
		+ notifyUrl + "</URLDiNotifica>";
	result += "	<URLBack>"
		+ paymentSystemConfiguration.getProperty("service.url")
		+ backUrl + "</URLBack>";
	result += "	<CommitNotifica>" + COMMIT_NOTIFICA + "</CommitNotifica>";
	result += "	<UserData>";
	result += "		<EmailUtente>"
		+ paymentParameter.getUserData().getEmailUtente()
			.toUnicodeString() + "</EmailUtente>";
	result += "		<IdentificativoUtente>" + sCodFisc
		+ "</IdentificativoUtente>";
	result += "	</UserData>";
	result += "	<ServiceData>";
	result += "		<CodiceUtente>"
		+ String.valueOf(paymentParameter
			.getPaymentManagerSpecificData().get("CodiceUtente"))
		+ "</CodiceUtente>";
	result += "		<CodiceEnte>"
		+ String.valueOf(paymentParameter
			.getPaymentManagerSpecificData().get("CodiceEnte"))
		+ "</CodiceEnte>";
	if (paymentParameter.getPaymentManagerSpecificData().get("TipoUfficio") != null) {
	    result += "		<TipoUfficio>"
		    + String.valueOf(paymentParameter
			    .getPaymentManagerSpecificData().get("TipoUfficio"))
		    + "</TipoUfficio>";
	} else {
	    result += "		<TipoUfficio/>";
	}
	if (paymentParameter.getPaymentManagerSpecificData().get(
		"CodiceUfficio") != null) {
	    result += "		<CodiceUfficio>"
		    + String.valueOf(paymentParameter
			    .getPaymentManagerSpecificData().get(
				    "CodiceUfficio")) + "</CodiceUfficio>";
	} else {
	    result += "		<CodiceUfficio/>";
	}
	result += "		<TipologiaServizio>"
		+ String.valueOf(paymentParameter
			.getPaymentManagerSpecificData().get(
				"TipologiaServizio")) + "</TipologiaServizio>";
	result += "		<NumeroOperazione>"
		+ nullStringToEmptyString(sNumOperazione)
		+ "</NumeroOperazione>";
	if (paymentParameter.getServiceData().getNumeroDocumento() != null) {
	    result += "		<NumeroDocumento>"
		    + getNumeroDocumento(paymentParameter.getServiceData()
			    .getNumeroDocumento()) + "</NumeroDocumento>";
	}
	if (paymentParameter.getServiceData().getAnnoDocumento() != null) {
	    result += "		<AnnoDocumento>"
		    + paymentParameter.getServiceData().getAnnoDocumento()
		    + "</AnnoDocumento>";
	}
	result += "		<Valuta>" + VALUTA + "</Valuta>";
	result += "		<Importo>"
		+ paymentParameter.getPaymentData().getImporto() + "</Importo>";
	if (paymentParameter.getServiceData().getDatiSpecifici() != null) {
	    result += "		<DatiSpecifici><![CDATA["
		    + paymentParameter.getServiceData().getDatiSpecifici()
		    + "]]></DatiSpecifici>";
	} else {
	    result += "		<DatiSpecifici/>";
	}
	result += "	</ServiceData>";

	if (hasAccountingData(paymentParameter)) {

	    result += "	<AccountingData>";
	    if (riversamentoAutomatico) {
		result += "		<RiversamentoAutomatico>true</RiversamentoAutomatico>";
	    }

	    result += "		<ImportiContabili/>";
	    // if (hasImportiContabili(paymentParameter)) {
	    // result += "		<ImportiContabili>";
	    // Iterator importiContabiliIterator =
	    // paymentParameter.getAccountingData().getImportiContabili().iterator();
	    // while(importiContabiliIterator.hasNext()) {
	    // ImportoContabile importoContabile =
	    // (ImportoContabile)importiContabiliIterator.next();
	    // result += "			<ImportoContabile>";
	    // if (importoContabile.getIdentificativo() != null) {
	    // result += "				<Identificativo>" +
	    // importoContabile.getIdentificativo() + "</Identificativo>";
	    // }
	    // if (importoContabile.getValore() != null) {
	    // result += "				<Valore>" + importoContabile.getValore() +
	    // "</Valore>";
	    // }
	    // result += "			</ImportoContabile>";
	    // }
	    //
	    // result += "		</ImportiContabili>";
	    // }
	    if (hasEntiDestinatari(paymentParameter)) {
		result += "		<EntiDestinatari>";

		@SuppressWarnings("rawtypes")
		Iterator enteDestinatarioIterator = paymentParameter
			.getAccountingData().getEntiDestinatari().iterator();
		while (enteDestinatarioIterator.hasNext()) {
		    EnteDestinatario enteDestinatario = (EnteDestinatario) enteDestinatarioIterator
			    .next();
		    result += "			<EnteDestinatario>";
		    if (enteDestinatario.getIdentificativo() != null) {
			if (enteDestinatario.getIdentificativo().indexOf(
				ENTE_SEPARATORE_IDENTIFICATICO_DESCRIZIONE) > 0) {

			    HashMap identificativoTokens = tokenizeIdentificativo(enteDestinatario
				    .getIdentificativo());

			    String codDestinatario = String
				    .valueOf(identificativoTokens
					    .get("codDestinatario"));
			    String desDestinatario = String
				    .valueOf(identificativoTokens
					    .get("desDestinatario"));
			    String aeCodiceUtente = String
				    .valueOf(identificativoTokens
					    .get("aeCodiceUtente"));
			    String aeCodiceEnte = String
				    .valueOf(identificativoTokens
					    .get("aeCodiceEnte"));
			    String aeTipoUfficio = String
				    .valueOf(identificativoTokens
					    .get("aeTipoUfficio"));
			    String aeCodiceUfficio = String
				    .valueOf(identificativoTokens
					    .get("aeCodiceUfficio"));
			    boolean riversamentoAutomaticoOnere = Boolean
				    .parseBoolean(String.valueOf(identificativoTokens
					    .get("riversamentoAutomatico")));

			    result += "				<CodiceEntePortaleEsterno>"
				    + sanitizeCodiceEntePortaleEsterno(codDestinatario)
				    + "</CodiceEntePortaleEsterno>";
			    result += "				<DescrEntePortaleEsterno><![CDATA["
				    + sanitizeDescrizioneEntePortaleEsterno(desDestinatario)
				    + "]]></DescrEntePortaleEsterno>";

			    if (riversamentoAutomatico
				    && riversamentoAutomaticoOnere) {
				result += "				<CodiceUtenteBeneficiario>"
					+ aeCodiceUtente
					+ "</CodiceUtenteBeneficiario>";
				result += "				<CodiceEnteBeneficiario>"
					+ aeCodiceEnte
					+ "</CodiceEnteBeneficiario>";
				if (!aeTipoUfficio.equalsIgnoreCase("")) {
				    result += "				<TipoUfficioBeneficiario>"
					    + aeTipoUfficio
					    + "</TipoUfficioBeneficiario>";
				} else {
				    result += "				<TipoUfficioBeneficiario/>";
				}
				if (!aeCodiceUfficio.equalsIgnoreCase("")) {
				    result += "				<CodiceUfficioBeneficiario>"
					    + aeCodiceUfficio
					    + "</CodiceUfficioBeneficiario>";
				} else {
				    result += "				<CodiceUfficioBeneficiario/>";
				}
			    } else if (riversamentoAutomatico
				    && !riversamentoAutomaticoOnere) {
				result += "				<CodiceUtenteBeneficiario>"
					+ String.valueOf(paymentParameter
						.getPaymentManagerSpecificData()
						.get("CodiceUtente"))
					+ "</CodiceUtenteBeneficiario>";
				result += "				<CodiceEnteBeneficiario>"
					+ String.valueOf(paymentParameter
						.getPaymentManagerSpecificData()
						.get("CodiceEnte"))
					+ "</CodiceEnteBeneficiario>";

				String tipoUfficio = nullStringToEmpty(paymentParameter
					.getPaymentManagerSpecificData().get(
						"TipoUfficio"));
				String codiceUfficio = nullStringToEmpty(paymentParameter
					.getPaymentManagerSpecificData().get(
						"CodiceUfficio"));

				if (!tipoUfficio.equalsIgnoreCase("")) {
				    result += "				<TipoUfficioBeneficiario>"
					    + String.valueOf(paymentParameter
						    .getPaymentManagerSpecificData()
						    .get("TipoUfficio"))
					    + "</TipoUfficioBeneficiario>";
				} else {
				    result += "				<TipoUfficioBeneficiario/>";
				}
				if (!codiceUfficio.equalsIgnoreCase("")) {
				    result += "				<CodiceUfficioBeneficiario>"
					    + String.valueOf(paymentParameter
						    .getPaymentManagerSpecificData()
						    .get("CodiceUfficio"))
					    + "</CodiceUfficioBeneficiario>";
				} else {
				    result += "				<CodiceUfficioBeneficiario/>";
				}
			    }

			} else {

			    result += "				<CodiceEntePortaleEsterno>"
				    + sanitizeCodiceEntePortaleEsterno(enteDestinatario
					    .getIdentificativo())
				    + "</CodiceEntePortaleEsterno>";
			    result += "				<DescrEntePortaleEsterno><![CDATA["
				    + sanitizeDescrizioneEntePortaleEsterno(enteDestinatario
					    .getIdentificativo())
				    + "]]></DescrEntePortaleEsterno>";

			}
		    }
		    if (enteDestinatario.getValore() != null) {
			result += "				<Valore>" + enteDestinatario.getValore()
				+ "</Valore>";
		    }
		    if (enteDestinatario.getCausale() != null) {
			result += "				<Causale><![CDATA["
				+ verificaLunghezza(
					enteDestinatario.getCausale(), 256,
					" ...") + "]]></Causale>";
		    } else {
			result += "				<Causale/>";
		    }
		    result += "				<ImportoContabileIngresso/>";
		    result += "				<ImportoContabileUscita/>";
		    result += "			</EnteDestinatario>";
		}
		result += "		</EntiDestinatari>";
	    }

	    result += "	</AccountingData>";

	}
	result += "</PaymentRequest>";
	result += "";

	return result;

    }

    /**
     * @param paymentParameter
     * @return
     */
    private static boolean hasAccountingData(
	    PaymentRequestParameter paymentParameter) {

	boolean result = false;

	if (paymentParameter.getAccountingData() != null) {
	    result = (paymentParameter.getAccountingData().getEntiDestinatari() != null && paymentParameter
		    .getAccountingData().getEntiDestinatari().size() > 0)
		    || (paymentParameter.getAccountingData()
			    .getImportiContabili() != null && paymentParameter
			    .getAccountingData().getImportiContabili().size() > 0);
	}

	return result;

    }

    /**
     * @param paymentParameter
     * @return
     */
    @SuppressWarnings("unused")
    private static boolean hasImportiContabili(
	    PaymentRequestParameter paymentParameter) {

	return paymentParameter.getAccountingData().getImportiContabili() != null
		&& paymentParameter.getAccountingData().getImportiContabili()
			.size() > 0;

    }

    /**
     * @param paymentParameter
     * @return
     */
    private static boolean hasEntiDestinatari(
	    PaymentRequestParameter paymentParameter) {

	return paymentParameter.getAccountingData().getEntiDestinatari() != null
		&& paymentParameter.getAccountingData().getEntiDestinatari()
			.size() > 0;

    }

    /**
     * @param codicePeople
     * @return
     */
    private static String getNumeroDocumento(String codicePeople) {

	String result = "";

	if (codicePeople != null) {
	    result = codicePeople.substring(codicePeople.indexOf('-') + 1);
	}

	return result;

    }

    /**
     * @param value
     * @return
     */
    private static String nullStringToEmptyString(String value) {

	return (value == null) ? "" : value.trim();

    }

    private static String sanitizeCodiceEntePortaleEsterno(String codice) {

	return (codice == null) ? CODICE_ENTE_NULL : codice.trim();

    }

    private static String sanitizeDescrizioneEntePortaleEsterno(
	    String descrizione) {

	return (descrizione == null) ? DESCRIZIONE_ENTE_NULL
		: verificaLunghezza(descrizione.trim(), 256, " ...");

    }

    private static HashMap tokenizeIdentificativo(String identificativo)
	    throws PaymentException {

	HashMap result = new HashMap();

	StringTokenizer tokenizer = new StringTokenizer(identificativo, "##|##");
	if (tokenizer.countTokens() != 7) {
	    throw new PaymentException("Stringa identificativo errata.");
	}

	result.put("codDestinatario", nullStringToEmpty(tokenizer.nextToken()));
	result.put("desDestinatario", nullStringToEmpty(tokenizer.nextToken()));
	result.put("aeCodiceUtente", nullStringToEmpty(tokenizer.nextToken()));
	result.put("aeCodiceEnte", nullStringToEmpty(tokenizer.nextToken()));
	result.put("aeTipoUfficio", nullStringToEmpty(tokenizer.nextToken()));
	result.put("aeCodiceUfficio", nullStringToEmpty(tokenizer.nextToken()));
	result.put("riversamentoAutomatico", tokenizer.nextToken());

	return result;

    }

    private static String nullStringToEmpty(String value) {
	return ((value != null && value.equalsIgnoreCase("null")) || value == null) ? ""
		: value;
    }

    private static String verificaLunghezza(String valore,
	    int lunghezzaMassima, String suffisso) {

	String result = valore;

	if (valore != null) {

	    String bufferSuffisso = suffisso == null ? "" : suffisso;
	    if (valore.length() > lunghezzaMassima) {
		String buffer = valore.substring(0, lunghezzaMassima
			- bufferSuffisso.length() - 2);
		if (suffisso != null) {
		    buffer += suffisso;
		}
		result = buffer;
	    }

	}

	return result;

    }

}
