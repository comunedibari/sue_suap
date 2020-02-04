/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.constants;

/**
 *
 * @author giuseppe
 */
public class Error {

    public static final String ERRORE_GENERICO_PROTOCOLLO = "GEPR";
    public static final String ERRORE_POPOLAMENTO_STAGING = "PDST";
    public static final String ERRORE_POPOLAMENTO_DATABASE = "PDATA";
    public static final String ERRORE_GENERAZIONE_RICEVUTA = "WRNF";
    public static final String ERRORE_GRAVE_RICEZIONE = "GENR";
    public static final String ERRORE_PARSING_PRATICA = "PREX";
    public static final String ERRORE_PRATICA_DOPPIA = "PDOP";
    public static final String ERRORE_PRATICA_CLEAR = "CLPR";
    public static final String ERRORE_WEBSERVICE_MYPAGE = "WSMPD";
    public static final String ERRORE_UPLOAD_MYPAGE = "WSMPR";
    //ComunicaController
    public static final String ERRORE_COMUNICA_SALVA = "SAPC";
    public static final String ERRORE_COMUNICA_UPLOAD = "SAPC";
    //TemplateController
    public static final String ERRORE_TEMPLATE_EVENTI_INDEX = "TMPL_EVT_IDX";
    public static final String ERRORE_GESTIONE_EVENTOTEMPLATE_LISTA = "GEST_EVTEMP_LIST";
    public static final String ERRORE_GESTIONE_EVENTOTEMPLATE_LISTA_AJAX = "GEST_EVTEMP_LIS_AJX";
    public static final String ERRORE_GESTIONE_TEMPLATE_LISTA = "GEST_TEMP_LIST";
    public static final String ERRORE_GESTIONE_TEMPLATE_LISTA_AJAX = "GEST_TEMP_LIST_AJ";
    public static final String ERRORE_GESTIONE_TEMPLATE_MODIFICA = "GEST_TEMP_MOD";
    public static final String ERRORE_GESTIONE_TEMPLATE_SALVA = "GEST_TEMP_SAL";
    //ProcedimentiController
    public static final String ERRORE_GESTIONE_PROCEDIMENTI_LISTA_AJAX = "GEST_PROC_LIST_AJ";
    public static final String ERRORE_GESTIONE_PROCEDIMENTI_LISTA = "GEST_PROC_LIST";
    public static final String ERRORE_GESTIONE_PROCEDIMENTI_ELIMINA = "GEST_PROC_EL";
    public static final String ERRORE_GESTIONE_PROCEDIMENTI_MODIFICA = "GEST_PROC_MOD";
    public static final String ERRORE_GESTIONE_PROCEDIMENTI_SALVA = "GEST_PROC_MOD";
    //SearchController
    public static final String ERRORE_SEARCH_DESTINATARI = "SEARCH_DEST";
    public static final String ERRORE_SEARCH_UTENTI = "SEARCH_UT";
    public static final String ERRORE_SEARCH_PROVINCE = "SEARCH_PROV";
    public static final String ERRORE_SEARCH_FORMAGIURIDICA = "SEARCH_FORM_GIUR";
    public static final String ERRORE_SEARCH_CITTADINANZA = "SEARCH_CITTAD";
    public static final String ERRORE_SEARCH_TIPOCOLLEGIO = "SEARCH_TIPO_COLLEG";
    public static final String ERRORE_SEARCH_COMUNE = "SEARCH_COMUNE";
    public static final String ERRORE_SEARCH_PROCEDIMENTICOMUNI = "SEARCH_PROC_COM";
    public static final String ERRORE_SEARCH_PROCEDIMENTICOLLEGATI = "SEARCH_PROC_COLL";
    public static final String ERRORE_SEARCH_COMUNIENTI = "SEARCH_COMUNIENTI";
    public static final String ERRORE_SEARCH_ENTE = "SEARCH_ENTE";
    public static final String ERRORE_SEARCH_ENTIPROCEDIMENTI = "SEARCH_ENTI_PROC";
    //ProcessiController
    public static final String ERRORE_PROCESSI_LISTA_AJAX = "PROCES_LIST_AJ";
    public static final String ERRORE_PROCESSI_SALVAPROCESSO = "PROCES_SALVA_PROCES";
    public static final String ERRORE_PROCESSI_MOD = "PROCES_MOD";
    public static final String ERRORE_PROCESSI_EVENTI_LISTA = "PROCES_EVEN_LIST";
    public static final String ERRORE_PROCESSI_EVENTI_LISTA_AJAX = "PROCES_EVEN_LIST_AJ";
    public static final String ERRORE_PROCESSI_EVENTI_AGGIUNGI = "PROCES_EVEN_AGG";
    public static final String ERRORE_PROCESSI_EVENTI_AGGIUNGI_SALVAEVENTO = "PROCES_EVEN_AGG_SAL";
    public static final String ERRORE_PROCESSI_EVENTI_MODIFICA = "PROCES_EVEN_MOD";
    public static final String ERRORE_PROCESSI_EVENTI_FLUSSO = "PROCES_EVEN_FLUSS";
    public static final String ERRORE_PROCESSI_EVENTI_FLUSSO_AJAX = "PROCES_EVEN_FLUSS_AJ";
    public static final String ERRORE_PROCESSI_EVENTI_FLUSSO_SALVA = "PROCES_EVEN_FLUSS_SAL";
    public static final String ERRORE_PROCESSI_PROCESSO_DEPLOY = "PROCESS_DEPLOY";
    public static final String ERRORE_PROCESSI_PROCESSO_START = "PROCESS_START";
    public static final String ERRORE_PROCESSI_PROCESSO_TASK_COMPLETE = "PROCESS_TASK_COMPLETE";
//GestioneEntiController
    public static final String ERRORE_ENTE_INDEX_AJAX = "ENTE_INDEX_AJ";
    public static final String ERRORE_ENTE_INDEX = "ENTE_INDEX";
    public static final String ERRORE_ENTE_AGGIUNGI = "ENTE_AGG";
    public static final String ERRORE_ENTE_MODIFICA = "ENTE_MOD";
    public static final String ERRORE_ENTE_COMUNI_AJAX = "ENTE_COM_AJ";
    public static final String ERRORE_ENTE_COMUNI_RICERCA = "ENTE_COM_RIC";
    public static final String ERRORE_ENTE_COMUNI_RICERCA_AJAX = "ENTE_COM_RICERCA_AJ";
    public static final String ERRORE_ENTE_PROCEDIMENTI = "ENTE_PROC";
    public static final String ERRORE_ENTE_PROCEDIMENTI_AJAX = "ENTE_PROC_AJ";
    public static final String ERRORE_ENTE_SELEZIONAPROCEDIMENTO = "ENTE_SEL_PROC";
    public static final String ERRORE_ENTE_ELIMINA = "ENTE_ELIMINA";
    public static final String ERRORE_ENTE_COMUNI_SELEZIONA = "ENTE_COM_SEL";
    public static final String ERRORE_ENTE_COMUNI_CANCELLA = "ERRORE_ENTE_COM_CANC";
    public static final String ERRORE_ENTE_COLLEGAENTE = "ERRORE_ENTE_COLL_ENTE";
    public static final String ERRORE_ENTE_COLLEGAPADRE = "ERRORE_ENTE_COLL_PAD";
    public static final String ERRORE_ENTE_SELEZIONAPROCESSO_AJAX = "ERRORE_ENTE_SEL_PROCES_AJ";
    public static final String ERRORE_ENTE_SALVAPROCESSO = "ERRORE_ENTE_SAL_PROCES";
    public static final String ERRORE_ENTE_ENDOPROCEDIMENTI = "ERRORE_ENTE_ENDO";
    public static final String ERRORE_ENTE_ENDOPROCEDIMENTI_SALVA = "ERRORE_ENTE_ENDO_SALVA";
    //ScadenziarioController
    public static final String ERRORE_PRATICHE_SCADENZARIO = "PRAT_SCAD";
    public static final String ERRORE_PRATICHE_SCADENZARIO_AJAX = "PRAT_SCAD_AJ";
    //CaricamentoManualeController
    public static final String ERRORE_PRATICHE_NUOVE_PROTOCOLLO = "PRAT_NUOVE_PROT";
    public static final String ERRORE_PRATICHE_NUOVE_CARICAMENTO = "PRAT_NUOVE_CARIC";
    public static final String ERRORE_PRATICHE_NUOVE_PROTOCOLLO_AJAX = "PRAT_NUOVE_PROT_AJ";
    public static final String ERRORE_PRATICHE_CARICAMENTO_MANUALE = "PRAT_CARIC_MAN";
    public static final String ERRORE_PRATICHE_CARICAMENTO_DANUMEROPROTOCOLLO = "PRAT_CARIC_DANUMEROPROT";
    public static final String ERRORE_DOCUMENTI_NUOVI_PROTOCOLLO = "DOC_NUOVI_PROT";
    public static final String ERRORE_DOCUMENTI_NUOVI_PROTOCOLLO_AJAX = "DOC_NUOVI_PROT_AJ";
    public static final String ERRORE_DOCUMENTI_PROTOCOLLO_PRATICA = "DOC_PROTO_PRAT";
    public static final String ERRORE_DOCUMENTI_PROTOCOLLO_PRATICA_AJAX = "DOC_PROT_PRAT_AJ";
    public static final String ERRORE_DOCUMENTI_PROTOCOLLO_PRATICA_EVENTI = "DOC_PROT_PRAT_EV";
    public static final String ERRORE_DOCUMENTI_PROTOCOLLO_PRATICA_EVENTI_AJAX = "DOC_PROT_PRAT_EV_AJ";
    public static final String ERRORE_DOCUMENTI_PROTOCOLLO_PRATICA_EVENTI_VISUALIZZA = "DOC_PROT_PRAT_EV_VIS";
    public static final String ERRORE_DOCUMENTI_PROTOCOLLO_PRATICA_EVENTI_SALVA = "DOC_PROT_PRAT_EVI_SAL";
    public static final String ERRORE_PRATICHE_CARICAMENTO_SELEZIONA = "PRAT_CARIC_SEL";
    public static final String ERRORE_PRATICA_NUOVE_PROTOCOLLO_STEP1 = "PRAT_NUOVE_PROT_STEP1";
    public static final String ERRORE_PRATICHE_NUOVE_ANAGRAFICA_AGGIUNGI = "PRATE_NUOVE_ANAG_AGGI";
    public static final String ERRORE_PRATICHE_NUOVE_ANAGRAFICA_AGGIUNGI_AJAX = "PRAT_NUOVE_ANAG_AGG_AJ";
    public static final String ERRORE_PRATICHE_NUOVE_ANAGRAFICA_AGGIUNGI_INDIETRO = "PRAT_NUOVE_ANAG_AGGI_IND";
    public static final String ERRORE_PRATICA_NUOVE_PROTOCOLLO_STEP2 = "PRAT_NUOVE_PROT_STEP2";
//UtentiController
    public static final String ERRORE_UTENTI_INDEX_AJAX = "UT_INDEX_AJ";
    public static final String ERRORE_DUTENTI_INDEX = "UT_INDEX";
    public static final String ERRORE_UTENTI_AGGIUNGI = "UT_AGG";
    public static final String ERRORE_UTENTI_MODIFICA = "UT_MOD";
    public static final String ERRORE_UTENTI_MODIFICA_ENTI = "UT_MOD_ENTI";
    public static final String ERRORE_UTENTI_MODIFICA_ENTI_AJAX = "UT_MOD_ENTI_AJ";
    public static final String ERRORE_UTENTI_ELIMINA = "UT_EL";
    public static final String ERRORE_UTENTI_PROCEDIMENTI_SELECT = "UT_PROC_SEL";
    public static final String ERRORE_UTENTI_PROCEDIMENTI_SELECT_AJAX = "UT_PROC_SEL_AJ";
    public static final String ERRORE_UTENTI_SELEZIONAPROCEDIMENTO = "UT_SELEZIONAPROC";
//AnagraficheController
    public static final String ERRORE_GESTIONE_ANAGRAFICHE_LIST = "GEST_ANAG_LIST";
    public static final String ERRORE_GESTIONE_ANAGRAFICHE_LIST_AJAX = "GEST_ANAG_LIST_AJ";
    public static final String ERRORE_GESTIONE_ANAGRAFICHE_SALVA = "GEST_ANAG_SALVA";
    public static final String ERRORE_GESTIONE_ANAGRAFICHE_MODIFICA = "GEST_ANAG_MOD";
    public static final String ERRORE_GESTIONE_ANAGRAFICHE_MODIFICA_AJAX = "GESTE_ANAG_MOD_AJ";
    public static final String ERRORE_GESTIONE_ANAGRAFICHE_AGGIORNA = "GEST_ANAG_AGG";
    public static final String ERRORE_GESTIONE_ANAGRAFICHE_AGGIORNAVARIANTE = "GEST_ANAG_AGGIORNAVAR";
    public static final String ERRORE_GESTIONE_ANAGRAFICHE_PERSONAFISICA = "GEST_ANAG_PERSONAFIS";
    public static final String ERRORE_GESTIONE_ANAGRAFICHE_PERSONAGIURIDICA = "GEST_ANAG_PERSONAGIUR";
    public static final String ERRORE_PRATICA_COMUNICAZIONE_DETTAGLIO_ANAGRAFICA = "PRAT_COMUN_DETT_ANAG";
    public static final String ERRORE_GESTIONE_ANAGRAFICHE_CONTROLLA_AJAX = "GEST_ANAG_CONTR_AJ";
//ComunicazioniController
    public static final String ERRORE_COMUNICAZIONI_AJAX = "COMUNIC_AJ";
    public static final String ERRORE_COMUNICAZIONI_GESTISCI_AJAX = "COMUNIC_GEST_AJ";
//EventoController
    public static final String ERRORE_PRATICA_EVENTO_INDEX = "PRAT_EV_IND";
    public static final String ERRORE_PRATICA_EVENTO_CREA = "PRATICA_EVENTO_CREA";
    //FileController
    public static final String ERRORE_DOWNLOAD_POST = "DLOAD_POST";
    public static final String ERRORE_DOWNLOAD_PROTOCOLLO_POST = "DLOAD_PROT_POST";
    public static final String ERRORE_DOWNLOAD_GET = "DLOAD_GET";
    public static final String ERRORE_PROTOCOLLO_DOWNLOAD_GET = "PROT_DLOAD_GET";
    public static final String ERRORE_DOWNLOAD_PRATICA = "DLOAD_PRAT";
    public static final String ERRORE_DOWNLOAD_GENERETED = "DLOAD_GEN";
    public static final String ERRORE_DOWNLOAD_TEMPLATE_GET = "DLOAD_TEM_GET";
    public static final String ERRORE_GENERAXMLSURI = "GENXMLSURI";
    //NoteController
    public static final String ERRORE_PRATICA_NOTE_AGGIUNGI = "PRAT_NOT_AGG";
    public static final String ERRORE_PRATICA_NOTE_AGGIUNGI_AJAX = "PRAT_NOT_AGG_AJ";
    //AbstractController
    public static final String ERRORE__ABSTRACT_ACL = "ABS_ACL";
    public static final String ERRORE__ABSTRACT_INFOBAR = "ABS_INFOBAR";
    public static final String ERRORE__ABSTRACT_PATH = "ABS_PATH";
    public static final String ERRORE__ABSTRACT_URL = "ABS_URL";
    public static final String ERRORE__ABSTRACT_MENU = "ABS_MUNU";
    public static final String ERRORE__ABSTRACT_RICERCARIOK = "ABS_RICERCARIOK";
    //PraticheController
    public static final String ERRORE_PRATICHE_NUOVE = "PRAT_N";
    public static final String ERRORE_PRATICHE_NUOVE_AJAX = "PRAT_NE_AJ";
    public static final String ERRORE_PRATICHE_NUOVE_ASSEGNA = "PRAT_N_ASS";
    public static final String ERRORE_PRATICHE_NUOVE_ASSEGNA_AJAX = "PRATE_N_ASS_AJ";
    public static final String ERRORE_PRATICHE_NUOVE_APERTURA = "PRAT_N_AP";
    public static final String ERRORE_PRATICHE_NUOVE_APERTURA_AJAX = "PRAT_N_AP_AJ";
    public static final String ERRORE_PRATICHE_NUOVE_APERTURA_DETTAGLIO = "PRAT_N_AP_DET";
    public static final String ERRORE_PRATICHE_NUOVE_APERTURA_DETTAGLIO_AJAX = "PRAT_N_AP_DET_AJ";
    public static final String ERRORE_PRATICHE_NUOVE_APERTURA_CREA = "PRAT_N_AP_CR";
    public static final String ERRORE_PRATICHE_NUOVE_APERTURA_CREA_AJAX = "PRAT_N_AP_CR_AJ";
    public static final String ERRORE_PRATICHE_GESTISCI = "PRAT_GEST";
    public static final String ERRORE_PRATICHE_GESTISCI_AJAX = "PRAT_GEST_AJ";
    public static final String ERRORE_PRATICHE_DETTAGLIO = "PRAT_DETT";
    public static final String ERRORE_PRATICHE_DETTAGLIO_AGGIUNGIPROCEDIMENTO_INSERISCI = "PRAT_DETT_AGGIUNGIPROC_INS";
    public static final String ERRORE_PRATICHE_DETTAGLIO_AGGIUNGIPROCEDIMENTO = "PRAT_DET_AGGIUNGIPROC";
    public static final String ERRORE_PRATICHE_DETTAGLIO_AGGIUNGIPROCEDIMENTO_AJAX = "PRAT_DETT_AGGIUNGIPROC_AJ";
    public static final String ERRORE_PRATICHE_DETTAGLIO_AGGIUNGIPROCEDIMENTO_LISTAENTI = "PRAT_DETT_AGGIUNGIPROC_LISTAEN";
    public static final String ERRORE_PRATICHE_DETTAGLIO_AGGIUNGIPROCEDIMENTO_LISTAENTI_AJAX = "PRAT_DETT_AGGIUNGIPROC_LISTAEN_AJ";
    public static final String ERRORE_PRATICA_DETTAGLIO_MESSAGGI = "PRAT_DETT_MESS";
    public static final String ERRORE_PRATICA_DETTAGLIO_COLLEGAANAGRAFICA = "PRAT_DETT_COLLEGAAN";
    public static final String ERRORE_PRATICA_DETTAGLIO_COLLEGAANAGRAFICA_AJAX = "PRAT_DETT_COLLEGAAN_AJ";
    public static final String ERRORE_PRATICA_DETTAGLIO_COLLEGAANAGRAFICA_SELEZIONA = "PRAT_DETT_COLLEGAAN_SEL";
    public static final String ERRORE_PRATICA_DETTAGLIO_COLLEGAANAGRAFICA_CONFERMA = "PRAT_DETT_COLLEGAAN_CONF";
    public static final String ERRORE_PRATICA_DETTAGLIO_COLLEGAANAGRAFICA_SCOLLEGA = "PRAT_DETT_COLLEGAAN_SCOL";
    //ComunicazioneController(in events)
    public static final String ERRORE_AJAX_UPLOAD = "AJAX_UPLOAD";
    public static final String ERRORE_PRATICA_COMUNICAZIONE_INDEX = "_PRATICA_COMUNICAZIONE_INDEX";
    public static final String ERRORE_PRATICA_COMUNICAZIONE_AZIONE_SALVA = "_PRATICA_COMUNICAZIONE_AZIONE_SALVA";
    public static final String ERRORE_PRATICA_COMUNICAZIONE_DETTAGLIO_EVENTO = "PRATICA_COMUNICAZIONE_DETTAGLIO_EVENTO";
    public static final String ERRORE_PRATICA_COMUNICAZIONE_DETTAGLIO_EVENTO_AJAX = "_PRATICA_COMUNICAZIONE_DETTAGLIO_EVENTO_AJAX";
    //ComunicazioneReaController(in events)
    public static final String ERRORE_PRATICA_COMUNICAZIONEREA_ISTRUTTORIA_INDEX = "_PRATICA_COMUNICAZIONEREA_ISTRUTTORIA_INDEX";
    public static final String ERRORE_PRATICA_COMUNICAZIONEREA_EVASA_INDEX = "_PRATICA_COMUNICAZIONEREA_EVASA_INDEX";
    public static final String ERRORE_PRATICA_COMUNICAZIONEREA_RIFIUTATA_INDEX = "_PRATICA_COMUNICAZIONEREA_RIFIUTATA_INDEX";
    public static final String ERRORE_PRATICA_COMUNICAZIONEREA_SOSPESA_INDEX = "_PRATICA_COMUNICAZIONEREA_SOSPESA_INDEX";
    public static final String ERRORE_PRATICA_COMUNICAZIONEREA_SUBMIT = "_PRATICA_COMUNICAZIONEREA_SUBMIT";
    public static final String ERRORE_UG_SELECT = "ERRORE_UG_SELECT";
    public static final String ERRORE_WORKFLOW_TASKLIST_INDEX_AJAX = "ERRORE_WORKFLOW_TASKLIST_INDEX_AJAX";
    public static final String ERRORE_WORKFLOW_PROTOCOLLA = "ERRORE_WORKFLOW_PROTOCOLLA";
    public static final String ERRORE_WORKFLOW_MAIL = "ERRORE_WORKFLOW_MAIL";
    public static final String ERRORE_WORKFLOW_EXECUTE_EVENT = "ERRORE_WORKFLOW_EXECUTE";
    public static final String ERRORE_WORKFLOW_AUTOMATIC_EVENT = "ERRORE_WORKFLOW_AUTOMATIC_EVENT";
    public static final String ERRORE_WORKFLOW_EXECUTE_AUTOMATIC_EVENT = "ERRORE_WORKFLOW_EXECUTE_AUTOMATIC_EVENT";
    public static final String ERRORE_WORKFLOW_EXECUTE_CREAZIONE_SOTTOPRATICHE_EVENT = "ERRORE_WORKFLOW_EXECUTE_CREAZIONE_SOTTOPRATICHE_EVENT";
}
