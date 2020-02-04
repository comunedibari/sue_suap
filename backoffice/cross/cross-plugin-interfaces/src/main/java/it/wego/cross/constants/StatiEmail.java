package it.wego.cross.constants;

/**
 *
 * @author CS
 */
public class StatiEmail {

    /**
     * stato della mail (su evento e pratica) nel caso di situazioni non di
     * errore o di mail confermata
     */
    public static final String STATO_GENERICO = "X";

    /**
     * l'email e' stata inviata alla pec ed e' in attesa che la pec la accetti
     */
    public static final String PRESO_IN_CARICO_DA_SERVER_PEC = "D";
    /**
     * l'email e' stata accettata dal server PEC ACCETTAZIONE
     */
    public static final String INVIATA = "I";
    /**
     * Il server PEC ha consegnato l'email CONSEGNA
     */
    public static final String CONFERMATA = "C";
    /**
     * Non è stato possibile interpretare il messaggio di risposta
     */
    public static final String ERRORE_GENERICO = "E";
    /**
     * Il server PEC ha dato un errore di mancata consegna AVVISO DI MANCATA
     * CONSEGNA
     */
    public static final String ERRORE_SERVER = "M";

    /**
     * L'email e' pronta per essere inviata
     */
    public static final String DA_INVIARE = "S";
    
    /**
     * L'email e' pronta ma deve essere eseguito il flusso di protocollazione
     */
    public static final String DA_PROTOCOLLARE = "P";

    /**
     * Non è previsto un invio tramite email ma una comunicazione cartacea
     */
    public static final String SPEDIZIONE_MANUALE = "O";
    /**
     * L'email è stata rispedita
     */
    public static final String RISPEDITA = "R";
    /**
     * L'email è stata marcata forzatamente come consegnata
     */
    public static final String MARCATA_COME_CONSEGNATA = "F";
    /**
     * 'email quando viene creata e non ha ricevuto ancora la conferma di
     * accettazione dal server pec
     */
    public static final String SCONOSCIUTA = "SCONOSCIUTA";
    /**
     * 'email quando viene accettata dal server mi informa se la casella di
     * posta di detinazione è crtificata o meno
     */
    public static final String POSTA_ORDINARIA = "POSTA ORDINARIA";

    public static final String STRING_POSTA_ORDINARIA = "(\"posta ordinaria\")";

    public static final String POSTA_CERTIFICATA = "POSTA CERTIFICATA";
    public static final String STRING_POSTA_CERTIFICATA = "(\"posta certificata\")";
    /**
     * 'email quando viene accettata dal server mi restituisce come oggetto la
     * strina ACCETTAZIONE
     */
    public static final String ACCETTAZIONE = "ACCETTAZIONE";
    /**
     * 'email quando viene consegnata dal server mi restituisce come oggetto la
     * strina CONSEGNA
     */
    public static final String CONSEGNA = "CONSEGNA";

    public static final String MANCATA_CONSEGNA = "AVVISO DI MANCATA CONSEGNA";

    public static final String MANCATA_CONSEGNA_TEMPO_MASSIMO = "AVVISO DI MANCATA CONSEGNA PER SUP. TEMPO MASSIMO";

}
