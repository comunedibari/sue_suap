/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.plugins.genova.commercio.actions;

/**
 *
 * @author piergiorgio
 */
public class Constants {

    public static String COMMERCIO_ENDPOINT_AVVIO = "commercio.endpoint.avvio";
    public static String COMMERCIO_ENDPOINT_CANCELLAZIONE = "commercio.endpoint.cancellazione";
    public static String COMMERCIO_ENDPOINT_CHIUSURA = "commercio.endpoint.chiusura";
    public static String COMMERCIO_USER = "commercio.user";
    public static String COMMERCIO_PASSWORD = "commercio.password";
    public static String COMMERCIO_DESCRIZIONE_COMUNE = "GENOVA";
    public static String COMMERCIO_PROVINCIA = "GE";
    public static String COMMERCIO_CAP = "16100";
    public static String COMMERCIO_DITTA = "D";
    public static String COMMERCIO_PF = "F";
    public static String COMMERCIO_CODICE_COMUNE = "010025";
    public static String CODICE_OGGETTO_DATO_ESTESO_COMMERCIO = "COMMERCIO_GENOVA";
    public static String CODICE_TIPOLOGIA_COMMERCIO = "TIPO_COMMERCIO";
    public static String CODICE_TIPO_PROCEDIMENTO = "TIPO_PROCEDIMENTO";
    public static String COMMERCIO_TIPOCOMMERCIO_TEST = "commercio.tipocommercio.test";
    public static String COMMERCIO_TIPOPROCEDIMENTO_TEST = "commercio.tipoprocedimento.test";

    public static String USER_ERMES = "user.ermes";
    public static String PASSWORD_ERMES = "password.ermes";
    public static String ERMES_OK_CODE = "0";
    public static String ERMES_OK_DESC = "Aggiornamento Riuscito";
    public static String ERROR_USER_PASSWORD_CODE = "COM-ERR_01";
    public static String ERROR_USER_PASSWORD_DESC = "Credenziali errate";
    public static String ERROR_ERMES_PRATICA_NOT_FOUND_CODE = "COM-ERR_02";
    public static String ERROR_ERMES_PRATICA_NOT_FOUND_DESC = "Pratica Legacy non trovata";
    public static String ERROR_ERMES_NON_TROVATO_EVENTO_CHIUSURA_POSITIVA_CODE = "COM-ERR_03";
    public static String ERROR_ERMES_NON_TROVATO_EVENTO_CHIUSURA_POSITIVA_DESC = "Non trovato evento chiusura positiva sul processo";
    public static String ERROR_ERMES_NON_TROVATO_EVENTO_CHIUSURA_NEGATIVA_CODE = "COM-ERR_04";
    public static String ERROR_ERMES_NON_TROVATO_EVENTO_CHIUSURA_NEGATIVA_DESC = "Non trovato evento chiusura negativa sul processo";
    public static String ERROR_ERMES_NON_TROVATO_EVENTO_COMUNICAZIONE_CODE = "COM-ERR_05";
    public static String ERROR_ERMES_NON_TROVATO_EVENTO_COMUNICAZIONE_DESC = "Non trovato evento comunicazione sul processo";
    public static String ERROR_ERMES_NON_TROVATO_EVENTO_SOSPENSIONE_CODE = "COM-ERR_06";
    public static String ERROR_ERMES_NON_TROVATO_EVENTO_SOSPENSIONE_DESC = "Non trovato evento sospensione sul processo";    
    public static String ERROR_ERMES_TIPO_OPERAZIONE_ERRATA_CODE = "COM-ERR_07";
    public static String ERROR_ERMES_TIPO_OPERAZIONE_ERRATA_DESC = "Tipo operazione non prevista";    

    
    public static String ERMES_EVENTO_CAMBIO_STATO_CHIUSO_POSITIVAMENTE = "ERMESCHIPOS";
    public static String ERMES_EVENTO_CAMBIO_STATO_CHIUSO_NEGATIVAMENTE = "ERMESCHINEG";
    public static String ERMES_EVENTO_CCOMUNICAZIONE = "ERMESCOMUNICAZIONE";
    public static String ERMES_EVENTO_CAMBIO_STATO_SOSPENSIONE = "ERMESSOSPENSIONE";

    public static String ERMES_STATO_CHIUSURA_POSITIVA = "P";
    public static String ERMES_STATO_CHIUSURA_NEGATIVA = "N";
    public static String ERMES_STATO_SOSPENSIONE = "S";
    public static String ERMES_COMUNICAZIONE = "C";

}
