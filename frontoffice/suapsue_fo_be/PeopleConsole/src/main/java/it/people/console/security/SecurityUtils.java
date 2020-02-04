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
package it.people.console.security;

import java.util.BitSet;
import java.util.Random;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 13/lug/2011 10.38.06
 *
 */
public class SecurityUtils {

	private static String[] charBuffer = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "-", "_", "#", "@", "=", "*"};
	
	/**
	 * @param bitSetSize
	 * @return
	 */
	public static BitSet getMatchBitSet(int bitSetSize) {
		
		return getMatchBitSet(bitSetSize, true);
		
	}

	/**
	 * @param bitSetSize
	 * @param matchValue
	 * @return
	 */
	public static BitSet getMatchBitSet(int bitSetSize, boolean matchValue) {
		
		BitSet result = new BitSet(bitSetSize);
		
		for(int bitIndex = 0; bitIndex < bitSetSize; bitIndex++) {
			result.set(bitIndex, matchValue);
		}
		
		return result;
		
	}

	/**
	 * @return
	 */
	public static int getRandomInteger() {
		Random random = new Random();
		return random.nextInt(9);
	}
	
	/**
	 * @return
	 */
	public static char getRandomChar() {
		char charFromAscii = '\32';
		Random random = new Random();
		int asciiValue = random.nextInt(122);
		if ((asciiValue == 35) || (asciiValue == 61) || (asciiValue == 95) || 
			((asciiValue > 47) && (asciiValue < 58)) || ((asciiValue > 96) && (asciiValue < 123))) {
			charFromAscii = (char)asciiValue;
		}
		else {
			getRandomChar();
		}
		return charFromAscii;
	}

	/**
	 * @return
	 */
	public static String getRandomString() {
		Random random = new Random();
		return charBuffer[random.nextInt(charBuffer.length - 1)];
	}

	/**
	 * @param stringLength
	 * @return
	 */
	public static String getRandomString(int stringLength) {
		String stringBuffer = "";
		for (int index = 1; index <= stringLength; index++) {
			stringBuffer += getRandomString();
		}
		return stringBuffer;
	}
	
}
