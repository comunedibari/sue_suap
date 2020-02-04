/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.selenium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *
 * @author GabrieleM
 */
public class WebElMap {

    public static enum inputTypes {

        SIMPLEINPUT,
        READONLYINPUT,
        HIDDEN,
        SIMPLESELECT,
        AUTOCOMPLETEINPUT
    }
    //MENU

    //ACCETTAZIONE
    //GESTIONE
    public static final Integer MENU_GESTIONE_APERTURA_PRATICHE = 0;
    public static final Integer MENU_GESTIONE_GESTIONE_EVENTI = 1;

    public static void click_Gestione_Voce(WebDriver driver, Integer voce) {
        driver.findElement(By.cssSelector("div.apertura-pratiche > div.box-testo > h3")).click();
        WebElement gestione = driver.findElement(By.id("menu_apertura"));
        List<WebElement> aperturapratiche = gestione.findElements(By.tagName("li"));
        aperturapratiche.get(voce).click();
    }

    public static final String AP_PRAT_TABELLA_ID = "list";
    public static final String AP_PRAT_GESTIONE_BUTTON_CSSSELECTOR = "div.apertura-pratiche > div.box-testo > h3";
    public static final String AP_PRAT_ORGANIZZA_PER_DATA_BUTTON_ID = "jqgh_list_dataRicezione";
    public static final String AP_PRAT_ORGANIZZA_PER_OGGETTO_ID = "jqgh_list_oggettoPratica";
    public static final String AP_PRAT_APRI_PANNELLO_RICERCA_BUTTON_CSSSELECTOR = "span.ui-icon.ui-icon-triangle-1-e";
    public static final String AP_PRAT_RICERCA_PER_ID_INPUT_ID = "search_id_pratica";
    public static final String AP_PRAT_START_RICERCA_BUTTON_ID = "ricerca_button";
    public static final String AP_PRAT_VISUALIZZA_TUTTE_PARTICHE_CHECKBOX_ID = "search_all";
    public static final String AP_PRAT_RICERCA_SELEZIONA_DATA_DA_INPUT_ID = "search_data_from";
    public static final String AP_PRAT_SELEZIONA_MESE_INPUT_CSSSELECTOR = "ui-datepicker-month";
    public static final String AP_PRAT_SELEZIONA_ANNO_INPUT_CSSSELECTOR = "ui-datepicker-year";
    public static final String AP_PRAT_SELEZIONA_GIORNO_INPUT_CSSSELECTOR = "non usato";//pero ora uso bylinked text...
    public static final String AP_PRAT_RICERCA_SELEZIONA_ENTE_SELECTOR_ID = "search_ente";
    public static final String AP_PRAT_RICERCA_SELEZIONA_STATO_SELECTOR_ID = "search_stato";
    public static final String AP_PRAT_CONTAPAGINE_ID = "sp_1_pager";
    public static final String AP_PRAT_VAI_ALLA_PRIMA_PAGINA_BUTTON_CSSSELECTOR = "span.ui-icon.ui-icon-seek-first";
    public static final String AP_PRAT_VAI_ALLA_PAGINA_SUCCESSIVA_BUTTON_CSSSELECTOR = "span.ui-icon.ui-icon-seek-next";
    public static final String AP_PRAT_TABELLA_CAMPO_DA_LEGGERE = "title";
    public static final Integer AP_PRAT_COLONNAANAGRAFICHE_TR = 7;
    public static final Integer AP_PRAT_COLONNA_DATA = 1;
    public static final Integer AP_PRAT_COLONNA_OGGETTO = 2;
    public static final Integer AP_PRAT_COLONNA_ID = 3;
    public static final Integer AP_PRAT_COLONNA_ENTE = 4;
    public static final Integer AP_PRAT_COLONNA_COMUNE = 5;
    public static final Integer AP_PRAT_COLONNA_ANAGRAFICHE = 6;
    public static final Integer AP_PRAT_COLONNA_OPERATORE = 7;
    public static final Integer AP_PRAT_COLONNA_AZIONE = 8;
    public static final String AP_EVEN_CSS_GESTIONE_BUTTON_CSSSELECTOR = "div.apertura-pratiche > div.box-testo > h3";
    public static final String AP_EVEN_APERTURA_EVENTI_BUTTON_XPATH = "(//a[contains(text(),'Gestione eventi')])[2]";
    public static final String AP_EVEN_ORGANIZZA_PER_STATO_PRATICA_BUTTON_ID = "jqgh_list_statoPratica";
    public static final String AP_EVEN_CONTAPAGINE_ID = "sp_1_pager";
    public static final String AP_EVEN_VAI_ALLA_PAGINA_SUCCESSIVA_BUTTON_CSSSELECTOR = "span.ui-icon.ui-icon-seek-next";
    public static final String AP_EVEN_VAI_ALLA_PRIMA_PAGINA_BUTTON_CSSSELECTOR = "span.ui-icon.ui-icon-seek-first";
    public static final String AP_EVEN_START_RICERCA_BUTTON_ID = "ricerca_button";
    public static final String AP_EVEN_TABELLA_ID = "list";
    public static final String AP_EVEN_TABELLA_CAMPO_DA_LEGGERE = "title";
    public static final String AP_EVEN_APRI_PANNELLO_RICERCA_BUTTON_CSSSELECTOR = "span.ui-icon.ui-icon-triangle-1-e";
    public static final String AP_EVEN_RICERCA_PER_ID_INPUT_ID = "search_id_pratica";
    public static final String AP_EVEN_RICERCA_PER_ANAGRAFE_INPUT_ID = "ricercaAnagraficaCF";
    public static final String AP_EVEN_RICERCA_PER_NOMINATIVO_INPUT_ID = "ricercaAnagraficaNome";
    public static final String AP_EVEN_RICERCA_SELEZIONA_DATA_INPUT_ID = "ui-datepicker-div";
    public static final String AP_EVEN_RICERCA_SELEZIONA_DATA_DA_INPUT_ID = "search_data_from";
    public static final String AP_EVEN_RICERCA_SELEZIONA_DATA_A_INPUT_ID = "search_data_to";
    public static final String AP_EVEN_RICERCA_PER_FOGLIO_INPUT_ID = "search_foglio";
    public static final String AP_EVEN_RICERCA_PER_MAPPALE_INPUT_ID = "search_mappale";
    public static final String AP_EVEN_RICERCA_PER_SUBALTERNO_INPUT_ID = "search_subalterno";
    public static final String AP_EVEN_RICERCA_SELEZIONA_ENTE_SELECTOR_ID = "search_ente";
    public static final String AP_EVEN_RICERCA_SELEZIONA_STATO_SELECTOR_ID = "search_stato";
    public static final String AP_EVEN_RICERCA_PER_COMUNE_INPUT_ID = "search_des_comune";
    public static final String AP_EVEN_VISUALIZZA_TUTTE_PARTICHE_CHECKBOX_ID = "search_all";
    public static final String AP_EVEN_ORGANIZZA_PER_DATA_BUTTON_ID = "jqgh_list_dataRicezione";
    public static final String AP_EVEN_ORGANIZZA_PER_OGGETTO_BUTTON_ID = "jqgh_list_oggettoPratica";
    public static final String AP_EVEN_CLEAN_COMUNE_BUTTON_CSS = "button[type=\"button\"]";
    public static final String AP_EVEN_BACK_BUTTON_EVENTI_CSS = "div.timelineCard.tl > div.timeline_items_holder > div.t_controles > div.t_left";
    public static final String AP_EVEN_READ_EVENTI_CSS = ".timelineCard > div:nth-child(2) > div:nth-child(1) > div:nth-child(1)";
    public static final String AP_EVEN_DETTAGLIO_BUTTON_EVENTI_CSS = "input.button.ui-state-default.ui-corner-all.cerca_lente_rosso";
    public static final String AP_EVEN_READ_DETTAGLIO_EVENTI_CSS = "#dettaglio_pratica > div:nth-child(2)";
    public static final String AP_EVEN_READ_NUMBER_EVENTI_CSS = ".timelineCard > div:nth-child(2) > div:nth-child(1)";
    public static final String AP_RUBR_APERTURA_RUBRICA_BUTTON_XPATH = "(//a[contains(text(),'Rubrica')])[2]";
    public static final String AP_RUBR_ORGANIZZA_PIVA_ID = "jqgh_list_partitaIva";
    public static final String AP_ENTI_APERTURA_ENTI_BUTTON_CSS = "div.impostazioni_home > div.box-testo > h3";
    public static final String AP_ENTI_APERTURA_ENTI_2_BUTTON_CSS = "ul.menu_sotto > li > a[title=\"Gestione enti\"]";
    public static final String AP_ENTI_APERTURA_TEMPLATES_BUTTON_XPATH = "(//a[contains(text(),'Gestione templates')])[2]";
    public static final String AP_RUBR_ORGANIZZA_CFIS_ID = "jqgh_list_codiceFiscale";
    public static final String AP_RUBR_CLEAN_NOME_ID = "search_nome";
    public static final String AP_RUBR_CLEAN_COGNOME_ID = "search_cognome";
    public static final String AP_RUBR_CLEAN_DENOM_ID = "search_denominazione";
    public static final String AP_RUBR_CLEAN_CODFIS_ID = "search_codice_fiscale";
    public static final String AP_RUBR_CLEAN_PIVA_ID = "search_partita_iva";
    public static final String AP_RUBR_CLEANBTN_ID = "ricerca_button";
    public static final String AP_RUBR_PANNELLO_SEARCH = "span.ui-icon.ui-icon-triangle-1-e";
    public static final String AP_GESUT_IMPOSTAZIONI_BUTTON_CSSSELECTOR = "div.impostazioni_home > div.box-testo > h3";
    public static final String AP_GESUT_APERTURA_GESTIONE_BUTTON_XPATH = "(//a[contains(text(),'Gestione utenti')])[2]";
    public static final String AP_PROC_APERTURA_GESTIONE_BUTTON_XPATH = "(//a[contains(text(),'Gestione procedimenti')])[2]";
    public static final String AP_PRAT_ANAGRAFICA_DETTAGLIO_ANAGRAFICHE_CONTAINER_SEZIONEPRIMORECAPITO_CLASS = "recapito0";
    public static final String AP_PRAT_ANAGRAFICA_DETTAGLIO_ANAGRAFICHE_TIPOINPUT_CLASSE_AUTOCOMPLETE_RECAPITO = "div[class = 'recapito-nCivico ui-widget ui-widget-content ui-corner-all' ] ";
    public static final String AP_PRAT_ANAGRAFICA_CONFERMATA_TITLE = "confermataBG";


    public static String AP_PRAT_ANAGRAFICA_DIV_XPATH(Integer nanagrafica) {
        String fhalf = "//div[@id='anagrafica";
        String shalf = "']";
        return fhalf + nanagrafica.toString() + shalf;
    }

    public static List<String> getObjectFromSideBar(List<String> input, Integer objectPosition, Integer protocolloPosizion) {
        List<String> result = new ArrayList<String>();
        result.add(input.get(objectPosition));
        // Numero di protocollo #numero
        List<String> idWords = Arrays.asList(input.get(protocolloPosizion).split(" "));
        String protocollo = idWords.get(idWords.size() - 1);
        result.add(protocollo);
        return result;
    }

    //da cambiare in modo che accetti un intero che specifica la anagrafica da cliccare
    public static void clickAnagraficaDettaglio(WebDriver driver) {
        WebElement divButton = driver.findElement(By.className("showdettaglio"));
        WebElement confrontoButton = divButton.findElement(By.tagName("div"));
        confrontoButton.click();
    }
    
        //da cambiare in modo che accetti un intero che specifica la anagrafica da cliccare
    public static void clickFirstAnagraficaDettaglio(WebDriver driver) {
        WebElement divButton = driver.findElement(By.className("showdettaglio"));
        WebElement confrontoButton = divButton.findElement(By.tagName("div"));
        confrontoButton.click();
    }
//    public static final String AP_PRAT_DETTAGLIO_ANAGRAFICA_TIPOANAGRAFICA_CLASS = "stringTipoAnagrafica";
//        public static final String AP_PRAT_DETTAGLIO_ANAGRAFICA_DITTAINDIVIDUALEFLAG_CLASS = "dittaIndividuale F hidden";
//     public static final String AP_PRAT_DETTAGLIO_ANAGRAFICA_TIPORUOLO_CLASS = "tipoRuolo";
//      public static final String AP_PRAT_DETTAGLIO_ANAGRAFICA_CODICEFISCALE_CLASS = "codiceFiscale";
//       public static final String AP_PRAT_DETTAGLIO_ANAGRAFICA_PARTITAIVA_CLASS = "partitaIva G F";
//        public static final String AP_PRAT_DETTAGLIO_ANAGRAFICA_NOME_CLASS = "nome F";
//         public static final String AP_PRAT_DETTAGLIO_ANAGRAFICA_COGNOME_CLASS = "cognome F";
//          public static final String AP_PRAT_DETTAGLIO_ANAGRAFICA__CLASS = "";
    public static final String AP_PRAT_ANAGRAFICA_DETTAGLIO_FIELD_CONTAINER_TAG = "li";
    public static final String AP_PRAT_ANAGRAFICA_DETTAGLIO_ANAGRAFICHE_CONTAINER_CLASS = "containerAnagrafiche";
    public static final String AP_PRAT_ANAGRAFICA_DETTAGLIO_ANAGRAFICHE_CONTAINER_INANAGRAFICA_CSSSELECTOR = "div[class = 'destinazione ui-corner-all ' ] ";
    public static final String AP_PRAT_ANAGRAFICA_DETTAGLIO_ANAGRAFICHE_CONTAINER_PERVENUTI_CSSSELECTOR = "div[class = 'sorgente ui-corner-all ' ] ";
    public static final String AP_PRAT_ANAGRAFICA_DETTAGLIO_ANAGRAFICHE_CONTAINER_SEZIONEDETTAGLIO_CLASS = "dettaglio";
    public static final String AP_PRAT_ANAGRAFICA_DETTAGLIO_ANAGRAFICHE_LABEL_TIPOANAGRAFICA = "Tipo anagrafica:";
    public static final String AP_PRAT_ANAGRAFICA_DETTAGLIO_ANAGRAFICHE_LABEL_RUOLO = "Ruolo:";
    public static final String AP_PRAT_ANAGRAFICA_DETTAGLIO_ANAGRAFICHE_LABEL_CODICEFISCALE = "Codice fiscale:";
    public static final String AP_PRAT_ANAGRAFICA_DETTAGLIO_ANAGRAFICHE_LABEL_PIVA = "P.IVA:";
    public static final String AP_PRAT_ANAGRAFICA_DETTAGLIO_ANAGRAFICHE_LABEL_NOME = "Nome:";
    public static final String AP_PRAT_ANAGRAFICA_DETTAGLIO_ANAGRAFICHE_LABEL_COGNOME = "Cognome:";
    public static final String AP_PRAT_ANAGRAFICA_DETTAGLIO_ANAGRAFICHE_LABEL_SESSO = "Sesso:";
    public static final String AP_PRAT_ANAGRAFICA_DETTAGLIO_ANAGRAFICHE_LABEL_CITTADINANZA = "Cittadinanza:";
    public static final String AP_PRAT_ANAGRAFICA_DETTAGLIO_ANAGRAFICHE_LABEL_NAZIONALITA = "Nazionalita:";
    public static final String AP_PRAT_ANAGRAFICA_DETTAGLIO_ANAGRAFICHE_LABEL_DATADINASCITA = "Data di nascita:";
    public static final String AP_PRAT_ANAGRAFICA_DETTAGLIO_ANAGRAFICHE_LABEL_COMUNEDINASCITA = "Comune di nascita:";
    public static final String AP_PRAT_ANAGRAFICA_DETTAGLIO_ANAGRAFICHE_LABEL_STATO = "Stato:";
    public static final String AP_PRAT_ANAGRAFICA_DETTAGLIO_ANAGRAFICHE_LABEL_PROVINCIANASCITA = "Provincia di nascita:";
    public static final String AP_PRAT_ANAGRAFICA_DETTAGLIO_ANAGRAFICHE_VALUES_TIPOAN_PERSONAFISICA = "Persona fisica";
    public static final String AP_PRAT_ANAGRAFICA_DETTAGLIO_ANAGRAFICHE_VALUES_RUOLO_RICHIEDENTE = "Richiedente";
    public static final String AP_GESPR_APERTURA_PROCESSI_BUTTON_XPATH = "(//a[contains(text(),'Gestione processi')])[2]";
    public static final List<String> AP_PRAT_ANAGRAFICA_DETTAGLIO_INANAGRAFICA_SELECTS_ID = new ArrayList<String>() {
        {
            add("sesso1");
        }
    };
    public static final List<String> AP_PRAT_ANAGRAFICA_DETTAGLIO_PERVENUTI_SELECTS_ID = new ArrayList<String>() {
        {
            add("sesso");
        }
    };
    public static final List<String> AP_PRAT_ANAGRAFICA_DETTAGLIO_SELECTS_DEFAULT_OPTIONS = new ArrayList<String>() {
        {
            add("Maschio");
        }
    };

    public static final String AP_PRAT_ANAGRAFICA_DETTAGLIO_ANAGRAFICHE_TIPOINPUT_CLASSE_AUTOCOMPLETE = "div[class = 'ui-widget-overlay' ] ";
}
