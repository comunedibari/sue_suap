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

 Licenza:	    Licenza Progetto PEOPLE
 Fornitore:    CEFRIEL
 Autori:       M. Pianciamore, P. Selvini

 Questo codice sorgente � protetto dalla licenza valida nell'ambito del
 progetto PEOPLE. La propriet� intellettuale di questo codice � e rester�
 esclusiva di "CEFRIEL Societ� Consortile a Responsabilit� Limitata" con
 sede legale in via Renato Fucini 2, 20133 Milano (MI).

 Disclaimer:

 COVERED CODE IS PROVIDED UNDER THIS LICENSE ON AN "AS IS" BASIS, WITHOUT
 WARRANTY OF ANY KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING, WITHOUT 
 LIMITATION, WARRANTIES THAT THE COVERED CODE IS FREE OF DEFECTS, MERCHANTABLE,
 FIT FOR A PARTICULAR PURPOSE OR NON-INFRINGING. THE ENTIRE RISK AS TO THE
 QUALITY AND PERFORMANCE OF THE COVERED CODE IS WITH YOU. SHOULD ANY COVERED
 CODE PROVE DEFECTIVE IN ANY RESPECT, YOU (NOT THE INITIAL DEVELOPER OR ANY
 OTHER CONTRIBUTOR) ASSUME THE COST OF ANY NECESSARY SERVICING, REPAIR OR
 CORRECTION.

 */

/*
 * Created on 21-dec-2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package it.people.fsl.servizi.oggetticondivisi.profili;

import java.io.Serializable;

public class ProfiloTitolare implements Serializable {

    protected ProfiloPersonaFisica profiloTitolarePF = null;
    protected ProfiloPersonaGiuridica profiloTitolarePG = null;

    public ProfiloTitolare() {
	super();
    }

    public boolean isPersonaFisica() {
	return (profiloTitolarePF != null && profiloTitolarePG == null);
    }

    public boolean isPersonaGiuridica() {
	return (profiloTitolarePF == null && profiloTitolarePG != null);
    }

    public ProfiloPersonaFisica getProfiloTitolarePF() {
	return profiloTitolarePF;
    }

    public void setProfiloTitolarePF(ProfiloPersonaFisica profiloTitolarePF) {
	this.profiloTitolarePF = profiloTitolarePF;
    }

    public ProfiloPersonaGiuridica getProfiloTitolarePG() {
	return profiloTitolarePG;
    }

    public void setProfiloTitolarePG(ProfiloPersonaGiuridica profiloTitolarePG) {
	this.profiloTitolarePG = profiloTitolarePG;
    }

}
