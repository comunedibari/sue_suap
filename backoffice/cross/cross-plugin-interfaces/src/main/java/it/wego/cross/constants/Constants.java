/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.constants;

/**
 *
 * @author giuseppe
 */
public class Constants {

    public static final String TIPO_ENTE_SUAP = "SUAP";
    public static final String GRUPPO_STATO_PRATICA_C = "C";
    public static final String STATO_PRATICA_IN_PROGRESS = "IP";
    public static final String UTENTE_ATTIVO = "ATTIVO";
    public static final String UTENTE_NON_ATTIVO = "NON_ATTIVO";
    public static final String VISIBILE = "S";
    public static final String NON_VISIBILE = "N";
    public static final String PERSISTENCE_UNIT = "crossPU";
    public static final String PERSONA_FISICA = "F";
    public static final String PERSONA_GIURIDICA = "G";
//    public static final String PERSONA_PROFESSIONISTA = "P";
    public static final String PERSONA_DITTAINDIVIDUALE = "I";
    //TODO: Ma i tre seguenti flag...saranno corretti?! Ai posteri l'ardua sentenza...
    public static final char FLAG_ANAGRAFICA_FISICA = 'F';
//    public static final char FLAG_ANAGRAFICA_PROFESSIONISTA = 'P';
    public static final char FLAG_ANAGRAFICA_AZIENDA = 'G';
    public static final char FLAG_ANAGRAFICA_ENTE = 'E';
    public static final String ANAGRAFICA_ENTE = "Ente";
    public static final String ANAGRAFICA_ENTE_DEFAULT = "Ente Default";
    public static final String ANAGRAFICA_GENERICA = "Anagrafica";
    public static final String ANAGRAFICA_GENERICA_DEFAULT = "Anagrafica Default";
    public static final String ANAGRAFICA_NOTIFICA = "Notifica pratica";

    public static final String WEBSERVICE_AEC = "AEC";
    public static final String WEBSERVICE_CSE = "Cooperazione_Suap_Ente";
    public static final String COMUNICA = "COMUNICA";
    public static final String CARICAMENTO_MANUALE = "CARICAMENTO_MANUALE";
    public static final String CARICAMENTO_MODALITA_MANUALE = "MANUALE";
    public static final String CARICAMENTO_MODALITA_DANUMERO = "DANUMERO";
    public static final String CARICAMENTO_MODALITA_AUTOMATICA = "AUTOMATICA";
    public static final String CARICAMENTO_MANUALE_PRATICA = "PRATICA_MANUALE_CROSS";
    public static final String CARICAMENTO_MANUALE_DOCUMENTO = "DOCUMENTO_MANUALE_CROSS";
    public static final String INDIRIZZO_NOTIFICA = "NOTIFICA";
    public static final String INDIRIZZO_SEDE = "SEDE";
    public static final String INDIRIZZO_DOMICILIO = "DOMICILIO";
    public static final String INDIRIZZO_RESIDENZA = "RESIDENZA";
    public static final String STATO_ESTERO = "Stato estero";
    // AGGIUNTA per l'ordinamento dei recapiti nella schermata di confronto anagrafiche
    public static final String ORDINE_RECAPITI[] = new String[]{"SEDE", "RESIDENZA", "STUDIO", "DOMICILIO", "NOTIFICA", "SEDE OPERATIVA"};
    public static final String ORDINE_RECAPITI_CODE[] = new String[]{"SED", "RES", "STU", "DOM", "NOT", "SEDOP"};
    //Tipo di recapito permesso per tipo di anagrafica
    public static final String RECAPITI_ANA_FISICA[] = new String[]{"RESIDENZA", "DOMICILIO", "SEDE OPERATIVA"};
    public static final String RECAPITI_DITTA_INDIVIDUALE[] = new String[]{"RESIDENZA", "DOMICILIO", "SEDE OPERATIVA"};
    public static final String RECAPITI_PROFESSIONISTA[] = new String[]{"RESIDENZA", "STUDIO", "DOMICILIO", "SEDE OPERATIVA"};
    public static final String RECAPITI_GIURIDICA[] = new String[]{"SEDE", "SEDE OPERATIVA"};

    public static final String RUOLO_COD_RICHIEDENTE = "R";
    public static final String RUOLO_COD_BENEFICIARIO = "B";

    public static final String DEFAULT_LANGAUGE = "it";

    public static final String NOP_EVENT = "nop_event";

    public static final Integer IDENTIFICATIVO_CATASTO = 1;
    public static final Integer IDENTIFICATIVO_TAVOLARE = 2;
    public static final String SISTEMA_CATASTALE_ORDINARIO = "ORDINARIO";
    public static final String SISTEMA_CATASTALE_TAVOLARE = "TAVOLARE";

}
