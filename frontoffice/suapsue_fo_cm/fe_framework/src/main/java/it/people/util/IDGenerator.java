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
/*
 * Created on Jan 5, 2005
 *
 */
package it.people.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Classe di utilita utilizzata per la generazione automatica di ID time-based
 * e/o IP-based.
 * <p>
 * Nel caso pi� generale l'ID generato � composto da un prefisso, una parte
 * IP-based e una parte time-based:<br>
 * 
 * <code>"prefisso"-"parte IP-based"-"parte time-based"</code>
 * <p>
 * 
 * Il prefisso � passato come argomento al metodo di generazione dell'ID e
 * pu� essere utilizzato per identificare univocamente il richiedente (ad
 * esempio si pu� utilizzare il suo codice fiscale).
 * <p>
 * La parte IP-based � una stringa contenente una codifica in base 62 dell'IP
 * della macchina o di una sua parte. Specificando in modo opportuno una bitmask
 * � possibile generare la parte IP-based dell'ID considerando il byte meno
 * significativo, i due byte meno significativi, i tre byte meno significativi o
 * l'intero IP.
 * <p>
 * La parte time-based � ricavata sulla base del valore corrente restituito da
 * <code>System.currentTimeMillis()</code> considerando una precisione in msec e
 * un numero di bit specificati con una bitmask opportuna.
 * <p>
 * <br>
 * 0x0 Non viene generata la parte time-based dell'ID n� la parte IP-based <br>
 * 0x1 Richiesta la generazione della parte time-based dell'ID con precisione
 * 256 msec e lunghezza 32 bit. <br>
 * 0x2 Richiesta la generazione della parte time-based dell'ID con precisione
 * 256 msec e lunghezza 35 bit. <br>
 * 0x3 Richiesta la generazione della parte time-based dell'ID con precisione 1
 * msec e lunghezza 40 bit. <br>
 * 0x4 Richiesta la generazione della parte IP-based dell'ID considerando solo
 * gli 8 bit meno significativi dell'indirizzo IP della macchina. <br>
 * 0x8 Richiesta la generazione della parte IP-based dell'ID considerando solo i
 * 16 bit meno significativi dell'indirizzo IP della macchina. <br>
 * 0xc Richiesta la generazione della parte IP-based dell'ID considerando solo i
 * 24 bit meno significativi dell'indirizzo IP della macchina. <br>
 * 0x10 Richiesta la generazione della parte IP-based dell'ID considerando per
 * intero (32 bit) l'indirizzo IP della macchina.
 * 
 * <P>
 * La classe definisce alcune costanti utili associate ai diversi valori base
 * della bitmask.
 * 
 * <p>
 * Le bitmask possono essere combinate in OR per ottenere la generazione delle
 * due parti IP-Based e Time-Based:
 * <p>
 * Esempio: <code>IPBASED_LOW2_MASK | ID_TIMEBASED_35_256_MASK</code>
 * <p>
 * Se non viene specificata nessuna bitmask (invocando il metodo
 * <code>generateID(String prefix)</code>) si utilizza il valore di default che
 * corrisponde alla generazione della sola parte Time-based con precisione 256
 * msec e lunghezza 35 bit (<code>ID_TIMEBASED_35_256_MASK</code>).
 * <p>
 * La bitmask di default pu� essere modificata invocando il metodo
 * <code>setDefaultID_Mask()</code>
 * <p>
 * Esempio: <br>
 * <code>...
 * <br>IDGenerator.setDefaultID_Mask(D_TIMEBASED_35_256_MASK);  //Impostazione di default al momento del caricamento della classe
 * <br>                                                         // Pu� essere omesso se non � richiesta una politica differente di generazione degli ID
 * <br>System.out.println(IDGenerator.generateID(PRZSTI34D12F205K));
 * <br>...
 * </code>
 * <p>
 * Produce come risultato: <br>
 * <code>PRZSTI34D12F205K-4IbPpq
 * </code>
 * 
 */
public class IDGenerator {

    /**
     * Costante utilizzata per specificare una precisione di 256 msec nella
     * generazione dell'ID Time-based
     */
    public static final long TIMEBASED_PRECISION_256MSEC = 256;
    /**
     * Costante utilizzata per specificare una precisione di 100 msec nella
     * generazione dell'ID Time-based
     */
    public static final long TIMEBASED_PRECISION_100MSEC = 100;
    /**
     * Costante utilizzata per specificare una precisione di 1 msec nella
     * generazione dell'ID Time-based
     */
    public static final long TIMEBASED_PRECISION_1MSEC = 1;

    /**
     * Costante utilizzata per specificare una generazione di ID IP-based
     * effettuata considerando il bit meno significativo dell'IP della macchina
     */
    public static final byte IPBASED_LOW1_MASK = 0x04;

    /**
     * Costante utilizzata per specificare una generazione di ID IP-based
     * effettuata considerando i due bit meno significativi dell'IP della
     * macchina
     */
    public static final byte IPBASED_LOW2_MASK = 0x08;

    /**
     * Costante utilizzata per specificare una generazione di ID IP-based
     * effettuata considerando i tre bit meno significativi dell'IP della
     * macchina
     */
    public static final byte IPBASED_LOW3_MASK = 0x0c;

    /**
     * Costante utilizzata per specificare una generazione di ID IP-based
     * effettuata considerando per intero l'IP della macchina
     */
    public static final byte IPBASED_FULL_MASK = 0x10;

    /**
     * Bitmask utilizzata per specificare la generazione di ID time-based con
     * precisione 256 msec e ottenuti considerandodi lunghezza 32 bitindividuare
     * i 32 bit meno significativi di System.currentTimeMillis()
     */
    public static final byte ID_TIMEBASED_32_256_MASK = 0x01;
    /**
     * Bitmask utilizzata per specificare la generazione di ID time-based con
     * precisione 256 msec e ottenuti considerandodi lunghezza 32 bitindividuare
     * i 35 bit meno significativi di System.currentTimeMillis()
     */
    public static final byte ID_TIMEBASED_35_256_MASK = 0x02;
    /**
     * Bitmask utilizzata per specificare la generazione di ID time-based con
     * precisione 1 msec e ottenuti considerandodi lunghezza 32 bitindividuare i
     * 40 bit meno significativi di System.currentTimeMillis()
     */
    public static final byte ID_TIMEBASED_40_1_MASK = 0x03;

    /**
     * Bitmask utilizzata per verificare la necessit� di generazione di ID
     * time-based
     */
    public static final byte ID_TIMEBASED_MASK = 0x03;
    /**
     * Bitmask utilizzata per verificare la necessit� di generazione di ID
     * IP-based
     */
    public static final byte ID_IPBASED_MASK = 0x1c;

    /**
     * Costante utilizzata per specificare l'intervallo di tempo minimo di
     * generaizone di 2 ID Time-based successivi
     */
    public final static int THREAD_SLEEP_TIME = 50;

    /** Costante utilizzata per individuare i 32 bit meno significativi */
    public static final int N_LOWBITS32 = 32;
    /** Costante utilizzata per individuare i 35 bit meno significativi */
    public static final int N_LOWBITS35 = 35;
    /** Costante utilizzata per individuare i 40 bit meno significativi */
    public static final int N_LOWBITS40 = 40;

    /** Bitmask di default utilizzata per la generazione degli ID */
    protected static byte ID_Default_Mask = ID_TIMEBASED_35_256_MASK;

    // Gestione nuovo formato stringa con ID Operazione
    // Restituisce una stringa decimale 10 10 cifre in due parti: xxx-xxxxxxx
    public static final int OUTPUT_RADIX_DECIMAL = 10;
    public static final int OUTPUT_RADIX_BASE62 = 62;

    public static final int SINGLE_DECIMAL_STRING_OUTPUT_STYLE = 1;
    public static final int DOUBLE_DECIMAL_STRING_OUTPUT_STYLE = 2;

    protected static int default_output_radix = OUTPUT_RADIX_DECIMAL;
    protected static int default_decimal_string_output_style = DOUBLE_DECIMAL_STRING_OUTPUT_STYLE;
    // Fine variabili e costanti per gestione nuovo formato ID Operazione

    /* Contiene l'Hostname della macchina. */
    private static String localIPAddressString;

    /* Contiene l'indirizzo IP della macchina */
    private static InetAddress localIPAddress = null;

    /* Array di bytes contenente l'indirizzo IP della macchina */
    private static byte[] localIPAddressBytes4 = { 0x0, 0x0, 0x0, 0x0 };

    /* Intero contenente l'indirizzo IP della macchina */
    private static int localIPAddressInt = 0;
    /* Long positivo contenente l'indirizzo IP della macchina */
    private static long localIPAddressLong = 0;

    /*
     * Determina il local IP-Address
     */
    static {
	try {
	    localIPAddress = InetAddress.getLocalHost();

	    localIPAddressString = localIPAddress.getHostAddress();
	    localIPAddressBytes4 = localIPAddress.getAddress();

	    localIPAddressInt = localIPAddressBytes4[3] & 0x000000FF;
	    localIPAddressInt |= (localIPAddressBytes4[2] << 8) & 0x0000FF00;
	    localIPAddressInt |= (localIPAddressBytes4[1] << 16) & 0x00FF0000;
	    localIPAddressInt |= (localIPAddressBytes4[0] << 24) & 0xFF000000;

	    localIPAddressLong = Long.parseLong(
		    Integer.toBinaryString(localIPAddressInt), 2);

	} catch (UnknownHostException e) {
	    localIPAddressString = "localhost";
	}
    }

    /**
     * Imposta la bitmask per la generazione di default dei successivi ID.
     * 
     * @param type_mask
     * 
     * @see #generateID(String, byte) generateID(String prefix, byte type_mask)
     *      {per il dettaglio della struttura della bitmask}
     */
    public static void setDefaultID_Mask(byte type_mask) {
	ID_Default_Mask = type_mask;

    }

    public static void setDefaultOutputRadix(int outputRadix) throws Exception {
	if (outputRadix != OUTPUT_RADIX_DECIMAL
		&& outputRadix != OUTPUT_RADIX_BASE62)
	    throw new Exception("Invalid output Radix specified");
	default_output_radix = outputRadix;

    }

    public static void setDefaultDecimalStringOutputStyle(int outputStyle)
	    throws Exception {
	if (outputStyle != SINGLE_DECIMAL_STRING_OUTPUT_STYLE
		&& outputStyle != DOUBLE_DECIMAL_STRING_OUTPUT_STYLE)
	    throw new Exception("Invalid decimal output style specified");
	default_decimal_string_output_style = outputStyle;

    }

    /**
     * Genera un ID con il prefisso specificato. Il tipo di ID generato �
     * definito dal valore corrente di ID_Default_Mask.<br>
     * Salvo ridefinizioni da parte dell'utente (attraverso l'invocazione del
     * metodo <code>setDefaultID_Mask()</code>) al momento del caricamento della
     * classe il tipo di ID generato � time-based con precisione di 256 msec e
     * di lunghezza 35 bit.
     * 
     * @param prefix
     *            Stringa contenente il prefisso dell'ID da generare
     * @return Stringa contenente l'ID generato
     * 
     * @see #setDefaultID_Mask(byte) setDefaultID_Mask()
     */
    public static String generateID(String prefix) {
	return generateID(prefix, ID_Default_Mask);
    }

    /**
     * Genera un ID a partire dal codice fiscale dell'utente e del prefisso aoo
     * passati. Questa � la versione del metodo utilizzata dal framework
     * People.
     * 
     * @return
     */
    public static String generateID(String codiceFiscaleUtente, String aooPrefix) {
	return IDGenerator.generateID(codiceFiscaleUtente + "-" + aooPrefix);
    }

    /**
     * Genera un ID con un prefisso specificato. Il tipo di ID generato �
     * definito dalla bitmask specificata come parametro
     * 
     * @param prefix
     *            Stringa contenente il prefisso dell'ID da generare
     * @param type_mask
     *            bitmask per la selezione del tipo di ID da generare La bitmask
     *            � a 5 bit.<br>
     *            I 2 bit meno significativi sono utilizzati per la parte
     *            time-based dell'ID, mentre i 3 bit pi� significativi sono
     *            usati per la parte IP-based dell'ID.
     * 
     * <br>
     *            0x0 Non viene generata la parte time-based dell'ID n� la
     *            parte IP-based <br>
     *            0x1 Richiesta la generazione della parte time-based dell'ID
     *            con precisione 256 msec e lunghezza 32 bit. <br>
     *            0x2 Richiesta la generazione della parte time-based dell'ID
     *            con precisione 256 msec e lunghezza 35 bit. <br>
     *            0x3 Richiesta la generazione della parte time-based dell'ID
     *            con precisione 1 msec e lunghezza 40 bit. <br>
     *            0x4 Richiesta la generazione della parte IP-based dell'ID
     *            considerando solo gli 8 bit meno significativi dell'indirizzo
     *            IP della macchina. <br>
     *            0x8 Richiesta la generazione della parte IP-based dell'ID
     *            considerando solo i 16 bit meno significativi dell'indirizzo
     *            IP della macchina. <br>
     *            0xc Richiesta la generazione della parte IP-based dell'ID
     *            considerando solo i 24 bit meno significativi dell'indirizzo
     *            IP della macchina. <br>
     *            0x10 Richiesta la generazione della parte IP-based dell'ID
     *            considerando per intero (32 bit) l'indirizzo IP della
     *            macchina.
     * 
     * 
     * @return Stringa contenente un ID time-based e/o IP based
     */
    public static String generateID(String prefix, byte type_mask) {

	boolean time_based = ((type_mask & ID_TIMEBASED_MASK) != 0);
	boolean IP_based = ((type_mask & ID_IPBASED_MASK) != 0);
	;
	StringBuffer sb = new StringBuffer((prefix == null) ? "" : prefix);
	String IDStringBase62 = null;

	if (IP_based) {
	    // sb.append((sb.length()>0) ? "-" : "");
	    // sb.append(generateIPBasedID(type_mask & ID_IPBASED_MASK));
	    IDStringBase62 = generateIPBasedID(type_mask & ID_IPBASED_MASK);
	}
	if (time_based) {
	    // sb.append((sb.length()>0) ? "-" : "");
	    switch (type_mask & ID_TIMEBASED_MASK) {
	    // case ID_TIMEBASED_32_256_MASK:
	    // sb.append(generateTimeBasedID_32_256());break;
	    // case ID_TIMEBASED_35_256_MASK:
	    // sb.append(generateTimeBasedID_35_256());break;
	    // case ID_TIMEBASED_40_1_MASK:
	    // sb.append(generateTimeBasedID_40_1());break;
	    case ID_TIMEBASED_32_256_MASK:
		IDStringBase62 = generateTimeBasedID_32_256();
		break;
	    case ID_TIMEBASED_35_256_MASK:
		IDStringBase62 = generateTimeBasedID_35_256();
		break;
	    case ID_TIMEBASED_40_1_MASK:
		IDStringBase62 = generateTimeBasedID_40_1();
		break;
	    }
	}

	long resultBase10 = Base62Converter.fromBase62(IDStringBase62);
	String resultBase10String = String.valueOf(resultBase10);
	String resultBase10StringZeroPadded = Base62Converter.zeroPad(
		resultBase10String, 10);

	String result = null;
	if (default_output_radix == OUTPUT_RADIX_DECIMAL) {
	    if (default_decimal_string_output_style == SINGLE_DECIMAL_STRING_OUTPUT_STYLE) {
		result = resultBase10StringZeroPadded;
	    } else if (default_decimal_string_output_style == DOUBLE_DECIMAL_STRING_OUTPUT_STYLE) {
		String result_prefix = resultBase10StringZeroPadded.substring(
			0, 3);
		String result_suffix = resultBase10StringZeroPadded
			.substring(3);

		result = result_prefix + "-" + result_suffix;
	    }
	} else if (default_output_radix == OUTPUT_RADIX_BASE62) {
	    result = IDStringBase62;
	}

	sb.append(result);

	return sb.toString();
    }

    /**
     * Genera un ID time-based partendo dall'istante corrente restituito da
     * <code>System.currentTimeMillis()</code>.
     * <p>
     * Il processo di generaizone � il seguente: 1) Sulla base della
     * precisione in millisecondi specificata tronca i bit meno significativi
     * dell'istante corrente dividendo il valore dell'istante corrente per la
     * precisione. Ad esempio se la precisione specificata � 256 msec vengono
     * troncati gli 8 bit meno significativi (divisione per 256=shift a destra
     * di 8 bit). In questo modo un nuovo ID pu� essere generato ogni 256
     * msec. <br>
     * Per avere la garanzia di generazione di ID non duplicati il metodo di
     * generazione � synchronized e si utilizza Thread.sleep() per sospendere
     * l'esecuzione del thread corrente fino a quando dall'istante di
     * generaiozne dell'ID non � trascorso un numero di msec pari alla
     * precisione specificata.<br>
     * 2) Per la generazione dell'ID, si prendono i bit meno significativi del
     * valore di System.currentTimeMillis(). Il numero di bit da considerare �
     * specificato come parametro in ingresso.
     * <p>
     * 
     * @param precision
     *            - Precisione in msec richiesta per la generazione dell'ID
     * @param n_lowbits
     *            - Numero di bit meno signifactivi da estrarre dal valore
     *            corrente di System.currentTimeMillis() dopo il troncamento
     *            alla precisione specificata
     * @return Stringa in base62 contenente l'ID generato
     * 
     */
    public static synchronized String generateTimeBasedID(long precision,
	    int n_lowbits) {

	long now = System.currentTimeMillis();
	long now_lowbits = getLowBits(now / precision, n_lowbits);

	while (System.currentTimeMillis() - now < precision) {
	    try {
		Thread.sleep(THREAD_SLEEP_TIME);
	    } catch (InterruptedException ie) {
		// ignore exception
	    }
	}
	return Base62Converter.toBase62(now_lowbits);
    }

    /**
     * Genera un ID Time-based di 6 caratteri alfanumerici con precisione di 256
     * msec considerando i 32 bit meno significativi di
     * System.currentTimeMillis().
     * <p>
     * L'intervallo di generazione va da "000000" a "4GFfc3". L'univocit� nel
     * tempo dell'identificatore generato � garantita per circa 34 anni dal
     * momento della generazione del primo ID.
     * 
     * @return Stringa in base62 contenente l'ID generato
     */
    public static String generateTimeBasedID_32_256() {
	return Base62Converter.zeroPad(
		generateTimeBasedID(TIMEBASED_PRECISION_256MSEC, N_LOWBITS32),
		6);
    }

    /**
     * Genera un ID Time-based di 6 caratteri alfanumerici con precisione di 256
     * msec considerando i 35 bit meno significativi di
     * System.currentTimeMillis().
     * <p>
     * L'intervallo di generazione va da "000000" a "BvjXyv". L'univocit� nel
     * tempo dell'identificatore generato � garantita per circa 283 anni dal
     * momento della generazione del primo ID.
     * 
     * @return Stringa in base62 contenente l'ID generato
     */
    public static String generateTimeBasedID_35_256() {
	return Base62Converter.zeroPad(
		generateTimeBasedID(TIMEBASED_PRECISION_256MSEC, N_LOWBITS35),
		6);
    }

    /**
     * Genera un ID Time-based di 7 caratteri alfanumerici con precisione di 1
     * msec considerando i 40 bit meno significativi di
     * System.currentTimeMillis().
     * <p>
     * L'intervallo di generazione va da "0000000" a "jmaiJOv". L'univocit�
     * nel tempo dell'identificatore generato � garantita per circa 34 anni
     * dal momento della generazione del primo ID.
     * 
     * @return Stringa in base62 contenente l'ID generato
     */
    public static String generateTimeBasedID_40_1() {
	return Base62Converter.zeroPad(
		generateTimeBasedID(TIMEBASED_PRECISION_1MSEC, N_LOWBITS40), 7);
    }

    /**
     * Genera un ID come stringa in base62 utilizzando una parte dell'IP della
     * macchina su cui � in esecuzione la JVM.
     * 
     * @param idType
     *            - Intero utilizzato per specificare il tipo di ID da generare:
     *            <p>
     *            IPBASED_LOW1_MASK - Si considera solo il byte meno
     *            significativo dell'IP<br>
     *            IPBASED_LOW2_MASK - Si considerano solo i due byte meno
     *            significativi dell'IP<br>
     *            IPBASED_LOW3_MASK - Si considerano solo i tre byte meno
     *            significativi dell'IP<br>
     *            IPBASED_FULL_MASK - Si considera per intero l'IP della
     *            macchina (32 bits)
     * 
     * @return Stringa in base62 contenente l'ID generato
     */
    public static synchronized String generateIPBasedID(int idType) {
	long resultLong = 0;
	switch (idType) {
	case IPBASED_LOW1_MASK:
	    resultLong = getLowBits(localIPAddressLong, 8);
	    break;
	case IPBASED_LOW2_MASK:
	    resultLong = getLowBits(localIPAddressLong, 16);
	    break;
	case IPBASED_LOW3_MASK:
	    resultLong = getLowBits(localIPAddressLong, 24);
	    break;
	case IPBASED_FULL_MASK:
	    resultLong = localIPAddressLong;
	    break;
	default:
	    resultLong = -1;
	}

	return Base62Converter.toBase62(resultLong);
    }

    /**
     * Restituisce un long ottenuto estraendo i bit meno significativi dal long
     * passato come parametro.
     * 
     * @param n
     *            - long contenente il numero in input
     * @param nbit
     *            - int contenente il numero di bit meno significativi da
     *            estrarre
     * @return Un long contenente gli n bit meno significativi del numero
     *         passato in ingresso come parametro
     */
    public static long getLowBits(long n, int nbit) {

	String binary = Long.toBinaryString(n);
	int len = binary.length();
	if (nbit >= len)
	    return n;
	String binaryLow = binary.substring(len - nbit);
	return Long.parseLong(binaryLow, 2);
    }

}
