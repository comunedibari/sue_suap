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

package it.people.sirac.test.mock;

import it.people.sirac.accr.beans.Accreditamento;
import it.people.sirac.accr.beans.Delega;
import it.people.sirac.accr.beans.ProfiloLocale;
import it.people.sirac.accr.beans.Qualifica;
import it.people.sirac.accr.beans.Qualifica2Persona;
import it.people.sirac.dao.ISiracDao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Mock dao per unit testing
 * 
 * @author r.p.
 */
public class MockSiracDao implements ISiracDao {

    List profili, accreditamenti;
    Map qualifiche;
    
    public MockSiracDao() {
        profili = new ArrayList();
        accreditamenti = new ArrayList();
        qualifiche = new HashMap();
        qualifiche.put("ARC", new Qualifica("ARC", "Architetto", "Professionista"));
        qualifiche.put("AVV", new Qualifica("AVV", "Avvocato", "Professionista"));
        qualifiche.put("GEO", new Qualifica("GEO", "Geometra", "Professionista"));
        qualifiche.put("ING", new Qualifica("ING", "Ingegnere", "Professionista"));
        qualifiche.put("LEG", new Qualifica("LEG", "Rappresentante Legale Societ�", 
                                                   "Carica Societaria"));
        qualifiche.put("PRC", new Qualifica("PRC", "Procuratore Societ�", 
                                                   "Carica Societaria"));
        qualifiche.put("CAF", new Qualifica("CAF", "Rappresentante CAF", 
                                                   "Intermediario"));

    }
    
    /** 
     * @see it.people.sirac.dao.ISiracDao#getQualifiche()
     */
    public Qualifica[] getQualifiche() {
        Collection c = qualifiche.values();
        Qualifica[] q = new Qualifica[c.size()];
        c.toArray(q);
        return q;
    }

    /** 
     * @see it.people.sirac.dao.ISiracDao#getQualifica(java.lang.String)
     */
    public Qualifica getQualifica(String idQualifica) {
        Object o = qualifiche.get(idQualifica);
        return (o == null) ? null : (Qualifica) o;
    }

    /** 
     * @see it.people.sirac.dao.ISiracDao#getQualificheAbilitate(java.lang.String, java.lang.String)
     */
    public Qualifica[] getQualificheAbilitate(String codiceFiscale, String idComune) {
        Accreditamento a = null;
        List abilitate = new ArrayList();
        Iterator i = accreditamenti.iterator();
        
        while (i.hasNext()) {
            a = (Accreditamento) i.next();
            if (a.getCodiceFiscale().equalsIgnoreCase(codiceFiscale)) {
                Qualifica q = a.getQualifica();
                abilitate.add(q);
            }
        }
        
        Qualifica[] retval = new Qualifica[abilitate.size()];
        abilitate.toArray(retval);
        return retval;
    }



    /** 
     * @see it.people.sirac.dao.ISiracDao#creaAccreditamento(it.people.sirac.accr.Accreditamento)
     */
    public void creaAccreditamento(Accreditamento acc) {
        accreditamenti.add(acc);
    }

    /** 
     * @see it.people.sirac.dao.ISiracDao#creaProfiloLocale(it.people.sirac.accr.ProfiloLocale)
     */
    public void creaProfiloLocale(ProfiloLocale profilo) {
        profili.add(profilo);
    }
    
    /** 
     * @see it.people.sirac.dao.ISiracDao#creaProfiloLocale(it.people.sirac.accr.ProfiloLocale, boolean)
     */
    public void creaProfiloLocale(ProfiloLocale profilo, boolean checkDeleghe) {
        throw new UnsupportedOperationException("Non implementato");

    }

    /** 
     * @see it.people.sirac.dao.ISiracDao#esisteProfiloLocale(java.lang.String, java.lang.String)
     */
    public boolean esisteProfiloLocale(String codiceFiscale, String idComune) {
        if (getProfiloLocale(codiceFiscale, idComune) != null) 
            return true;
        
        return false;
    }

    /** 
     * TODO: controllare anche l'id comune
     * @see it.people.sirac.dao.ISiracDao#esisteAccreditamento(java.lang.String, java.lang.String, java.lang.String)
     */
    public boolean esisteAccreditamento(String codiceFiscale, String idComune, String qualifica) {
        
        Accreditamento a = null;
        String cf = null;
        String q = null;
        
        Iterator i = accreditamenti.iterator();
        
        while (i.hasNext()) {
            a = (Accreditamento) i.next();
            cf = a.getCodiceFiscale();
            q = a.getQualifica().getIdQualifica();
            
            if (q.equalsIgnoreCase(qualifica))
                return true;
        }
        
        return false;
    }

    /** 
     * @see it.people.sirac.dao.ISiracDao#getAccreditamenti(java.lang.String, java.lang.String)
     */
    public Accreditamento[] getAccreditamenti(String codiceFiscale,
            String idComune) {
        Accreditamento[] a = new Accreditamento[accreditamenti.size()];
        accreditamenti.toArray(a);
        return a;
    }

    /** 
     * @see it.people.sirac.dao.ISiracDao#getProfiloLocale(java.lang.String, java.lang.String)
     */
    public ProfiloLocale getProfiloLocale(String codiceFiscale, String idComune) {
        Iterator i = profili.iterator();
        
        while (i.hasNext()) {
            ProfiloLocale p = (ProfiloLocale) i.next();
            if (p.getCodiceFiscale().equalsIgnoreCase(codiceFiscale) &&
                    p.getIdComune().equalsIgnoreCase(idComune))
                return p;
        }
        
        return null;
    }
    
    
    
    /** 
     * @see it.people.sirac.dao.ISiracDao#esisteQualifica(java.lang.String, java.lang.String, java.lang.String[])
     */
    public boolean esisteQualifica(String codiceFiscale, String idComune,
            String[] qualifiche) {
        throw new UnsupportedOperationException();
    }
    
    /** 
     * @see it.people.sirac.dao.ISiracDao#getQualificheAccreditabili(java.lang.String, java.lang.String)
     */
    public Qualifica[] getQualificheAccreditabili(String codiceFiscale,
            String idComune) {
        throw new UnsupportedOperationException();
    }
    
    /** 
     * @see it.people.sirac.dao.ISiracDao#getAccreditamentoByIdCodiceFiscaleComune(int, java.lang.String, java.lang.String)
     */
    public Accreditamento getAccreditamentoByIdCodiceFiscaleComune(int id, String codiceFiscale,
            String idComune) {
        throw new UnsupportedOperationException();
    }
    
    /** 
     * @see it.people.sirac.dao.ISiracDao#getAccreditamentoByIdCodiceFiscaleComune(int, java.lang.String, java.lang.String)
     */
    public Accreditamento getAccreditamentoByCodiceIntermediario(String codiceFiscale,
            String idComune, String codiceFiscaleIntermediario, String partitaIvaIntermediario, String idQualifica) {
        throw new UnsupportedOperationException();
    }
    
    /** 
     * @see it.people.sirac.dao.ISiracDao#getQualifiche2Persona(java.lang.String)
     */
    public Qualifica2Persona[] getQualifiche2Persona(String tipoQualifica) {
        throw new UnsupportedOperationException("Non implementato");
    }
    
    
    /**
     * @see it.people.sirac.dao.ISiracDao#canCreateDelega(java.lang.String, java.lang.String, java.lang.String)
     */
    public boolean canCreateDelega(String codiceFiscale, String idComune, String idQualifica) {
        throw new UnsupportedOperationException("Non implementato");
    }
    
    
    
    /** 
     * @see it.people.sirac.dao.ISiracDao#creaDelega(it.people.sirac.accr.Delega)
     */
    public void creaDelega(Delega dlg) {
        throw new UnsupportedOperationException("Non implementato");
    }
    
    /** 
     * @see it.people.sirac.dao.ISiracDao#esisteDelega(int, java.lang.String, java.lang.String)
     */
    public boolean esisteDelega(int idAccreditamento, String delegato,
            String idQualifica) {
        throw new UnsupportedOperationException("Non implementato");
    }
    
    /** 
     * @see it.people.sirac.dao.ISiracDao#getAccreditamentoById(int)
     */
    public Accreditamento getAccreditamentoById(int id) {
        throw new UnsupportedOperationException("Non implementato");
    }
        
    /** 
     * @see it.people.sirac.dao.ISiracDao#eliminaDelega(it.people.sirac.accr.Delega)
     */
    public void eliminaDelega(Delega delega) {
        throw new UnsupportedOperationException("Non implementato");

    }
    
    /** 
     * @see it.people.sirac.dao.ISiracDao#getDeleghe(java.lang.String, java.lang.String, int)
     */
    public Delega[] getDeleghe(String codiceFiscale, String idComune,
            int idAccreditamento) {
        throw new UnsupportedOperationException("Non implementato");
    }
   
    
    /** 
     * @see it.people.sirac.dao.ISiracDao#eliminaProfiloLocale(java.lang.String, java.lang.String)
     */
    public void eliminaProfiloLocale(String codiceFiscale, String idComune) {
        throw new UnsupportedOperationException("Non implementato");

    }
}


