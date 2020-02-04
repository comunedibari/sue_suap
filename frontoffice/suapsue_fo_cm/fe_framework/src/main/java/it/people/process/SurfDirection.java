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
 * Created on 8-nov-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package it.people.process;

/**
 * @author FabMi
 * 
 *         Indica la direzione di navigazione degli step - forward, indica che
 *         si � entrati in uno step con il pulsante avanti - backward, indica
 *         che si � entrati in uno step con il pulsante indietro - other,
 *         indica che si � entrati nello step in uno qualsiasi altro modo,
 *         esempio saltando direttamente su una attivit� o nel caso della
 *         validazione fallita.
 */
public class SurfDirection {
    public static SurfDirection forward;
    public static SurfDirection backward;
    public static SurfDirection other;

    static {
	forward = new SurfDirection();
	backward = new SurfDirection();
	other = new SurfDirection();
    }

    private SurfDirection() {
    }
}
