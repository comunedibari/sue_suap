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
package it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.controllers;


import it.people.core.exception.ServiceException;
import it.people.util.payment.EsitoPagamento;
import it.people.util.payment.IPaymentObserver;
import it.people.util.payment.PaymentException;
import it.people.util.payment.PaymentProcess;

public class PaymentObserver implements IPaymentObserver
{
    protected static final boolean SEND_MAIL = false;
    
    /**
     * Il metodo � invocato dal framework a fronte di un esito certo sul pagamento
     * il pagamento pu� essere andato a buon fine oppure essere stato rifiutato
     */
    public void service(EsitoPagamento esitoPagamento, PaymentProcess process) 
    throws PaymentException {
        
        String codiceIstatEnte = process.getCommune().getKey(); 
        
        String logMessage = "PaymentObserver, risultato pagamento '"
            + esitoPagamento.getDescrizioneEsito() + "', codice istat ente '" 
            + codiceIstatEnte + "'";

        process.debug(logMessage);
        
        String mailObject = "Questa email � stata inviata dal metodo observer del servizio"
            + " relativamente al pagamento con identificativo '" + esitoPagamento.getIDOrdine() + "'\n."
            + "Il pagamento ha avuto come esito '" + esitoPagamento.getEsito() + "'";
        
        //try {
            String webServiceResponse = "Non � stato chiamato alcun ws: configurazione da effettuare";//process.callService("WS", "<pagamento>prova</pagamento>");
            process.debug("Risposta del web-service:\n" + webServiceResponse);
        /*} catch(ServiceException ex) {
            throw new PaymentException("Errore nell'invocazione del web-service");
        } catch(Exception ex) {
            throw new PaymentException("Errore nell'invocazione del web-service");
        }*/
            
        try {
            // Nota bene il framework gi� prevede l'invio di un mail di conferma
            // dell'avvenuto pagamento. Il testo del mail inviato dal framework  � 
            // personalizzabile per ente in base a tutte le informazioni ricevute 
            // dal sistema di pagamento 
            // (si veda il file it\people\resources\FormLabels.properties).
            // Il seguente esempio � riportato solo per i casi in cui il servizio
            // preveda comportamente particolari che non possano essere soddisfatti
            // dal meccanismo illustrato sopra.
            
            if (SEND_MAIL)
                process.sendMail("Pagamento dall'Observer del servizio", mailObject);

            // esite anche una versione che permette di specificare l'indirizzo di mail
            // del destinatario
            // process.sendMail("mi.fabbri@cedaf.it", "Pagamento dall'Observer del servizio con specifica del'indirizzo", mailObject);
        } catch(Exception ex) {
            throw new PaymentException("Errore nell'invio del mail al cittadino");
        }
    }
}
