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
package it.people.util;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA. User: acuffaro Date: 24-set-2003 Time: 11.37.38 To
 * change this template use Options | File Templates.
 */
public class RelazioneParentela implements java.io.Serializable {

    private RelazioneParentela(String p_str_name, int p_i_code) {

	if (p_str_name == null)
	    throw new NullPointerException(
		    "Unable to instantiate RelazioneParentela with null name.");

	m_type = p_i_code;
	m_name = p_str_name;
    }

    public final int getType() {
	return m_type;
    }

    public final String getName() {
	return m_name;
    }

    public static final RelazioneParentela valueOf(String p_name) {
	// Mettere if per ogni status return oggetto
	if (NO_INFORMATION_KEY.equals(p_name))
	    return RelazioneParentela.NO_INFORMATION;
	else if (CONIUGE_KEY.equals(p_name))
	    return RelazioneParentela.CONIUGE;
	else if (CONVIVENTE_KEY.equals(p_name))
	    return RelazioneParentela.CONVIVENTE;
	else if (FIGLIO_KEY.equals(p_name))
	    return RelazioneParentela.FIGLIO;
	else if (FIGLIO_SOLO_RICHIEDENTE_KEY.equals(p_name))
	    return RelazioneParentela.FIGLIO_SOLO_RICHIEDENTE;
	else if (FIGLIO_SOLO_PARTNER_KEY.equals(p_name))
	    return RelazioneParentela.FIGLIO_SOLO_PARTNER;
	else if (GENITORE_SOLO_RICHIEDENTE_KEY.equals(p_name))
	    return RelazioneParentela.GENITORE_SOLO_RICHIEDENTE;
	else if (SUOCERO_RICHIEDENTE_KEY.equals(p_name))
	    return RelazioneParentela.SUOCERO_RICHIEDENTE;
	else if (FRATELLO_RICHIEDENTE_KEY.equals(p_name))
	    return RelazioneParentela.FRATELLO_RICHIEDENTE;
	else if (FRATELLO_PARTNER_KEY.equals(p_name))
	    return RelazioneParentela.FRATELLO_PARTNER;
	else if (CONIUGE_FRATELLO_RICH_O_PART_KEY.equals(p_name))
	    return RelazioneParentela.CONIUGE_FRATELLO_RICH_O_PART;
	else if (PARTNER_DI_FIGLIO_KEY.equals(p_name))
	    return RelazioneParentela.PARTNER_DI_FIGLIO;
	else if (FIGLIO_DI_FIGLIO_KEY.equals(p_name))
	    return RelazioneParentela.FIGLIO_DI_FIGLIO;
	else if (FIGLIO_DI_FRATELLO_KEY.equals(p_name))
	    return RelazioneParentela.FIGLIO_DI_FRATELLO;
	else if (ALTRO_PARENTE_KEY.equals(p_name))
	    return RelazioneParentela.ALTRO_PARENTE;
	else if (ALTRA_PERSONA_KEY.equals(p_name))
	    return RelazioneParentela.ALTRA_PERSONA;
	else
	    return null;
    }

    public static final RelazioneParentela get(int p_code) {
	// Mettere if per ogni RelazioneParentela
	switch (p_code) {
	case NO_INFORMATION_CODE:
	    return RelazioneParentela.NO_INFORMATION;
	case CONIUGE_CODE:
	    return RelazioneParentela.CONIUGE;
	case CONVIVENTE_CODE:
	    return RelazioneParentela.CONVIVENTE;
	case FIGLIO_CODE:
	    return RelazioneParentela.FIGLIO;
	case FIGLIO_SOLO_RICHIEDENTE_CODE:
	    return RelazioneParentela.FIGLIO_SOLO_RICHIEDENTE;
	case FIGLIO_SOLO_PARTNER_CODE:
	    return RelazioneParentela.FIGLIO_SOLO_PARTNER;
	case GENITORE_SOLO_RICHIEDENTE_CODE:
	    return RelazioneParentela.GENITORE_SOLO_RICHIEDENTE;
	case SUOCERO_RICHIEDENTE_CODE:
	    return RelazioneParentela.SUOCERO_RICHIEDENTE;
	case FRATELLO_RICHIEDENTE_CODE:
	    return RelazioneParentela.FRATELLO_RICHIEDENTE;
	case FRATELLO_PARTNER_CODE:
	    return RelazioneParentela.FRATELLO_PARTNER;
	case CONIUGE_FRATELLO_RICH_O_PART_CODE:
	    return RelazioneParentela.CONIUGE_FRATELLO_RICH_O_PART;
	case PARTNER_DI_FIGLIO_CODE:
	    return RelazioneParentela.PARTNER_DI_FIGLIO;
	case FIGLIO_DI_FIGLIO_CODE:
	    return RelazioneParentela.FIGLIO_DI_FIGLIO;
	case FIGLIO_DI_FRATELLO_CODE:
	    return RelazioneParentela.FIGLIO_DI_FRATELLO;
	case ALTRO_PARENTE_CODE:
	    return RelazioneParentela.ALTRO_PARENTE;
	case ALTRA_PERSONA_CODE:
	    return RelazioneParentela.ALTRA_PERSONA;
	default:
	    return null;
	}
    }

    public String toString() {
	return m_name;
    }

    public static final int NO_INFORMATION_CODE = 0;
    public static final int CONIUGE_CODE = 1;
    public static final int CONVIVENTE_CODE = 2;
    public static final int FIGLIO_CODE = 3;
    public static final int FIGLIO_SOLO_RICHIEDENTE_CODE = 4;
    public static final int FIGLIO_SOLO_PARTNER_CODE = 5;
    public static final int GENITORE_SOLO_RICHIEDENTE_CODE = 6; // vale anche
								// per il
								// coniuge del
								// genitore del
								// richiedente
    public static final int SUOCERO_RICHIEDENTE_CODE = 7;
    public static final int FRATELLO_RICHIEDENTE_CODE = 8;
    public static final int FRATELLO_PARTNER_CODE = 9;
    public static final int CONIUGE_FRATELLO_RICH_O_PART_CODE = 10; // vale per
								    // fratello
								    // del
								    // richiedente/partner
    public static final int PARTNER_DI_FIGLIO_CODE = 11;
    public static final int FIGLIO_DI_FIGLIO_CODE = 12;
    public static final int FIGLIO_DI_FRATELLO_CODE = 13;
    public static final int ALTRO_PARENTE_CODE = 14;
    public static final int ALTRA_PERSONA_CODE = 15;

    public static final String NO_INFORMATION_KEY = "informazione non disponibile";
    public static final String CONIUGE_KEY = "coniuge dell'intestatario";
    public static final String CONVIVENTE_KEY = "convivente dell'intestatario";
    public static final String FIGLIO_KEY = "figlio del richiedente e del partner";
    public static final String FIGLIO_SOLO_RICHIEDENTE_KEY = "figlio del solo richiedente";
    public static final String FIGLIO_SOLO_PARTNER_KEY = "figlio del solo partner";
    public static final String GENITORE_SOLO_RICHIEDENTE_KEY = "genitore del richiedente"; // vale
											   // anche
											   // per
											   // il
											   // coniuge
											   // del
											   // genitore
											   // del
											   // richiedente
    public static final String SUOCERO_RICHIEDENTE_KEY = "suocero del richiedente";
    public static final String FRATELLO_RICHIEDENTE_KEY = "fratello del richiedente";
    public static final String FRATELLO_PARTNER_KEY = "fratello del partner";
    public static final String CONIUGE_FRATELLO_RICH_O_PART_KEY = "coniuge di fratello del richiedente o partner"; // vale
														   // per
														   // fratello
														   // del
														   // richiedente/partner
    public static final String PARTNER_DI_FIGLIO_KEY = "partner di figlio";
    public static final String FIGLIO_DI_FIGLIO_KEY = "figlio di figlio";
    public static final String FIGLIO_DI_FRATELLO_KEY = "figlio di fratello";
    public static final String ALTRO_PARENTE_KEY = "altro parente";
    public static final String ALTRA_PERSONA_KEY = "altra persona";

    public static final RelazioneParentela NO_INFORMATION = new RelazioneParentela(
	    NO_INFORMATION_KEY, NO_INFORMATION_CODE);
    public static final RelazioneParentela CONIUGE = new RelazioneParentela(
	    CONIUGE_KEY, CONIUGE_CODE);
    public static final RelazioneParentela CONVIVENTE = new RelazioneParentela(
	    CONVIVENTE_KEY, CONVIVENTE_CODE);
    public static final RelazioneParentela FIGLIO = new RelazioneParentela(
	    FIGLIO_KEY, FIGLIO_CODE);
    public static final RelazioneParentela FIGLIO_SOLO_RICHIEDENTE = new RelazioneParentela(
	    FIGLIO_SOLO_RICHIEDENTE_KEY, FIGLIO_SOLO_RICHIEDENTE_CODE);
    public static final RelazioneParentela FIGLIO_SOLO_PARTNER = new RelazioneParentela(
	    FIGLIO_SOLO_PARTNER_KEY, FIGLIO_SOLO_PARTNER_CODE);
    public static final RelazioneParentela GENITORE_SOLO_RICHIEDENTE = new RelazioneParentela(
	    GENITORE_SOLO_RICHIEDENTE_KEY, GENITORE_SOLO_RICHIEDENTE_CODE);
    public static final RelazioneParentela SUOCERO_RICHIEDENTE = new RelazioneParentela(
	    SUOCERO_RICHIEDENTE_KEY, SUOCERO_RICHIEDENTE_CODE);
    public static final RelazioneParentela FRATELLO_RICHIEDENTE = new RelazioneParentela(
	    FRATELLO_RICHIEDENTE_KEY, FRATELLO_RICHIEDENTE_CODE);
    public static final RelazioneParentela FRATELLO_PARTNER = new RelazioneParentela(
	    FRATELLO_PARTNER_KEY, FRATELLO_PARTNER_CODE);
    public static final RelazioneParentela CONIUGE_FRATELLO_RICH_O_PART = new RelazioneParentela(
	    CONIUGE_FRATELLO_RICH_O_PART_KEY, CONIUGE_FRATELLO_RICH_O_PART_CODE);
    public static final RelazioneParentela PARTNER_DI_FIGLIO = new RelazioneParentela(
	    PARTNER_DI_FIGLIO_KEY, PARTNER_DI_FIGLIO_CODE);
    public static final RelazioneParentela FIGLIO_DI_FIGLIO = new RelazioneParentela(
	    FIGLIO_DI_FIGLIO_KEY, FIGLIO_DI_FIGLIO_CODE);
    public static final RelazioneParentela FIGLIO_DI_FRATELLO = new RelazioneParentela(
	    FIGLIO_DI_FRATELLO_KEY, FIGLIO_DI_FRATELLO_CODE);
    public static final RelazioneParentela ALTRO_PARENTE = new RelazioneParentela(
	    ALTRO_PARENTE_KEY, ALTRO_PARENTE_CODE);
    public static final RelazioneParentela ALTRA_PERSONA = new RelazioneParentela(
	    ALTRA_PERSONA_KEY, ALTRA_PERSONA_CODE);

    private int m_type;
    private String m_name;
    private ArrayList m_values;
}
