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

/**
 * 
 * Classe di utilit� utilizzata per la conversione di interi long da base
 * decimale a base 62 e viceversa.
 * 
 * @author M. Pianciamore
 * @version 1.0
 */
public class Base62Converter {

    /** Base minima utilizzabile per la conversione */
    public static final int MIN_RADIX = 2;

    /** Base massima utilizzabile per la conversione */
    public static final int MAX_RADIX = 62;

    /**
     * Array contenente tutti i possibili caratteri utilizzabili per
     * rappresentare un numero decimale come stringa
     */
    private final static char[] digits = { '0', '1', '2', '3', '4', '5', '6',
	    '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
	    'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
	    'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
	    'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
	    'X', 'Y', 'Z' };

    /**
     * Stringa contenente tutti i possibili caratteri utilizzabili per
     * rappresentare un numero decimale in base 62
     */
    private static final String base62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * Converte il numero decimale long passato come parametro in una stringa in
     * base62
     * 
     * @param long10
     *            il numero decimale da convertire.
     * @return una stringa che rappresenta il numero convertito in base 62
     */
    public static String toBase62(long long10) {
	return toString(long10, 62);
    }

    /**
     * Converte il numero decimale long passato come parametro in una stringa in
     * base62 di lunghezza minima specificata, aggiungendo degli zeri in testa
     * se necessario. Se la lunghezza della stringa contenente il numero
     * convertito in Base62 � maggiore della lunghezza minima specificata, la
     * stringa viene restituita inalterata.
     * 
     * @param long10
     *            il numero decimale da convertire.
     * @param minLen
     *            la lunghezza minima per la stringa contenente il numero
     *            convertito in base62
     * @return una stringa che rappresenta il numero convertito in base 62 con
     *         un numero di caratteri minimo pari a quello indicato come
     *         parametro.
     */
    public static String toBase62(long long10, int minLen) {
	String base62String = toString(long10, 62);
	return zeroPad(base62String, minLen);
    }

    /**
     * Converte una stringa che rappresenta un intero in base62 in un numero
     * decimale long
     * 
     * @param base62String
     *            la stringa contenente il numero in base62 da convertire.
     * @return il numero convertito in base decimale
     */
    public static long fromBase62(String base62String) {
	long result = 0;
	int len = base62String.length();
	char[] base62CharArray = base62String.toCharArray();
	// StringBuffer expr = new StringBuffer();
	for (int i = 0; i < len; i++) {
	    char c = base62CharArray[len - i - 1];
	    int pos = base62.indexOf(c);
	    result += pos * Math.pow(62, i);
	    // if (i>0) expr.append("+");
	    // expr.append(pos + "*62^" + i);
	}
	// System.out.println(base62String + " = " + expr);
	return result;
    }

    /**
     * Converte il long passato come parametro in una stringa nella base
     * specificata dal secondo argomento.
     * <p>
     * Se la base specificata � minore di <code>MIN_RADIX</code> o maggiore di
     * <code>MAX_RADIX</code>, viene utilizzata la base <code>10</code>.
     * <p>
     * Se il primo argomento � negativo, il primo carattere della stringa
     * risultato � il segno meno '-'</code>. Se il primo argomento non �
     * negativo nel risultato non compare il segno. I caratteri ASCII utilizzati
     * per la conversione sono:
     * <ul>
     * <code>
     *   0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ
     * </code>
     * </ul>
     * 
     * @param i
     *            numero decimale da convertire.
     * @param radix
     *            base di conversione.
     * @return una stringa che rappresenta il numero decimale passato come
     *         parametro nella nuova base.
     */
    public static String toString(long i, int radix) {
	if (radix < MIN_RADIX || radix > MAX_RADIX)
	    radix = 10;

	char[] buf = new char[65];
	int charPos = 64;
	boolean negative = (i < 0);

	if (!negative) {
	    i = -i;
	}

	while (i <= -radix) {
	    buf[charPos--] = digits[(int) (-(i % radix))];
	    i = i / radix;
	}
	buf[charPos] = digits[(int) (-i)];

	if (negative) {
	    buf[--charPos] = '-';
	}

	return new String(buf, charPos, (65 - charPos));
    }

    /**
     * Aggiunge in testa alla stringa passata come parametro il carattere di
     * riempimento specificato fino a raggiungere la lunghezza minima richiesta.
     * Se la lunghezza della stringa originaria � maggiore della lunghezza
     * minima richiesta la stringa originaria viene restituita inalterata.
     * 
     * @param s
     *            Stringa priginaria
     * @param c
     *            Carattere di riempimento
     * @param minLen
     *            Lunghezza minima della stringa risultato
     * @return Stringa ottenuta dalla stringa originaria aggiungendo in testa il
     *         carattere di riempimento fino a raggiungere la lunghezza minima
     *         specificata.
     */
    public static String pad(String s, char c, int minLen) {
	StringBuffer sb = new StringBuffer();
	int len = s.length();
	for (int i = 0; i < minLen - len; i++) {
	    sb.append(c);
	}
	return sb.append(s).toString();
    }

    /**
     * Aggiunge in testa alla stringa passata come parametro il carattere '0'
     * fino a raggiungere la lunghezza minima richiesta. Se la lunghezza della
     * stringa originaria � maggiore della lunghezza minima richiesta la
     * stringa originaria viene restituita inalterata.
     * 
     * @param s
     *            Stringa priginaria
     * @param minLen
     *            Lunghezza minima della stringa risultato
     * @return Stringa ottenuta dalla stringa originaria aggiungendo in testa il
     *         carattere '0' fino a raggiungere la lunghezza minima specificata.
     */
    public static String zeroPad(String s, int minLen) {
	return pad(s, '0', minLen);
    }

    public static void main(String[] args) {
	StringBuffer sb = new StringBuffer();
	for (int i = 0; i < 63; i++) {
	    sb.append("1");
	    String s = sb.toString();
	    long n = Long.parseLong(sb.toString(), 2);
	    String nBase62 = Base62Converter.toBase62(n);
	    System.out.println("Binario: " + s + " (" + s.length() + ")"
		    + "\tDecimale: " + n + "\tBase62: " + nBase62 + " ("
		    + nBase62.length() + ")");
	}
    }

}
