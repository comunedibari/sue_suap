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

package it.people.sirac.test;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.people.sirac.accr.IAccreditamento;
import it.people.sirac.accr.beans.Accreditamento;
import it.people.sirac.accr.beans.Delega;
import it.people.sirac.accr.beans.ProfiloAccreditamento;
import it.people.sirac.accr.beans.ProfiloLocale;
import it.people.sirac.accr.beans.Qualifica;
import it.people.sirac.core.SiracConstants;
import it.people.sirac.core.SiracRegistry;
import it.people.sirac.test.mock.MockSiracDao;
import junit.framework.TestCase;

/**
 * <p>TODO: Test per l'interfaccia di accreditamento chiamata localmente</p>
 * 
 * @author r.p.
 * 
 * @see it.people.sirac.accr.IAccreditamento 
 * @see it.people.sirac.accr.AccrManager
 */
public class IAccreditamentoLocalTest extends TestCase {
  /**
   * Logger for this class
   */
  protected static final Logger logger = LoggerFactory.getLogger(IAccreditamentoLocalTest.class);
  
    protected IAccreditamento accr = null;

    protected MockSiracDao mockSiracDao;
    
    public IAccreditamentoLocalTest(String name) {
        super(name);
    }
    
    protected void setUp() {
      InputStream stream = SiracRegistry.class.getResourceAsStream("/it/people/sirac/test/sirac-config.xml");
      BufferedInputStream bi = new BufferedInputStream(stream);
      try {
        SiracRegistry.initSirac(stream);
        //logger.debug("Comune : " + )
        logger.debug("Autoattivazione Accreditamenti per il comune: " +
            (SiracRegistry.getSirac()).autoAttivazioneAccrEnabled("IDCOMUNE"));  
        accr = SiracRegistry.getAccreditamento();
        mockSiracDao = new MockSiracDao();
      } catch (Exception e) {
        throw new RuntimeException("Exception during setup", e);
      }

    }
    
    public void testAccreditaUtentePeople() {
      
    
       ProfiloLocale p1 = new ProfiloLocale(
           "CODFISCALE", "IDCOMUNE", "IDCA", "DOMELETTR");
       ProfiloLocale p2 = null;
       try {
        p2 = accr.creaProfiloLocale("CODFISCALE", "IDCOMUNE", "IDCA", "DOMELETTR");
      } catch (Exception e) {
        // TODO Auto-generated catch block
        //e.printStackTrace();
        fail(e.getMessage());
      }

    if (logger.isDebugEnabled()) {
      logger
          .debug("testAccreditaUtentePeople() - Profilo utente creato:  : p2 = "
              + p2);
    }
       
       
  }

    public void testAccreditaIntermediario() {
      
      ProfiloAccreditamento pa = new ProfiloAccreditamento();
      pa.setCodiceFiscaleIntermediario("CODICEFISCALEINT");
      pa.setDescrizione("DESC");
      pa.setDenominazione("RAGSOCINTERMEDIARIO");
      try {
        pa.setAutoCert("abc".getBytes(SiracConstants.DEFAULT_CHARSET));
      } catch (UnsupportedEncodingException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
        fail("testAccreditaIntermediario Failed.");
      }
      pa.setDescrizione("DESCRIZIONE");
      pa.setSedeLegale("SEDELEGALE");
      pa.setDomicilioElettronico("aa@bb.cc");
      pa.setTimestampAutoCert(String.valueOf(System.currentTimeMillis()));
      
      ProfiloLocale pl = new ProfiloLocale(
          "CODFISCALE", "IDCOMUNE", "IDCA", "DOMELETTR");
    //Qualifica q = mockSiracDao.getQualifica("AVV");
      Qualifica q = new Qualifica("CAF", null, null);

      try {
        accr.accreditaIntermediario(pl.getCodiceFiscale(), 
                    pl.getIdComune(), q.getIdQualifica(), pa);
      } catch (Exception e) {
        // TODO Auto-generated catch block
        //e.printStackTrace();
        fail(e.getMessage());
      }

      if (logger.isDebugEnabled()) {
        logger
            .debug("testAccreditaIntermediario() - Creato Accreditamento : pa = "
                + pa);
      }
        
    }
    
  
    public void testCreaDelegatoIntermediario() {
      Accreditamento accrDelegante = 
        accr.getAccreditamentoByCodiceIntermediario("CODFISCALE", "IDCOMUNE", "CODICEINT", null, "CAF");

        
        Delega d = new Delega();
        try {
          d.setCertificazione("abc".getBytes(SiracConstants.DEFAULT_CHARSET));
        } catch (UnsupportedEncodingException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
          fail("testAccreditaIntermediario Failed.");
        }
        d.setCodiceFiscaleDelegante(accrDelegante.getCodiceFiscale());
        d.setIdAccreditamento(accrDelegante.getId());
        d.setCodiceFiscaleDelegato("AAAAAAAAAAAAAAAA");
        d.setIdQualifica("OAI");
        d.setNome("NOMEDELEGATO");
        d.setCognome("COGNOMEDELEGATO");
        d.setTimestampCertificazione(Long.toString(System.currentTimeMillis()));
        
        try {
          accr.creaDelega(d);
        } catch (Exception e) {
          // TODO Auto-generated catch block
          //e.printStackTrace();
          fail(e.getMessage());
        }

         
    }

    public void testEsisteQualifica1() {
      
      boolean result = accr.esisteQualifica("CODFISCALE", "IDCOMUNE", new String[]{"CAF"});
      assertEquals(true, result);  
      logger.info("testEsisteQualifica1 OK.");
         
    }

    public void testEsisteQualifica2() {
      
      boolean result = accr.esisteQualifica("CODFISCALE1", "IDCOMUNE", new String[]{"CAF"});
      assertEquals(false, result);  
      logger.info("testEsisteQualifica2 OK.");
         
    }
    
    public void testGetAccreditamentoByCodiceIntermediario() {
      Accreditamento result = 
        accr.getAccreditamentoByCodiceIntermediario("CODFISCALE", "IDCOMUNE", "CODICEINT", null, "CAF");
      if (result == null) fail("Accreditamento non trovato");
      logger.info("Accreditamento : " + result);
      logger.info("Profilo Accreditamento : " + result.getProfilo());
      
    }

    public void testGetAccreditamento1() {
      Accreditamento[] accreditamenti = accr.getAccreditamenti("CODFISCALE", "IDCOMUNE");
      for(int i=0; i<accreditamenti.length; i++) {
        int idAccr = accreditamenti[i].getId();
        String codFiscale = accreditamenti[i].getCodiceFiscale();
        String idComune = accreditamenti[i].getIdComune();
        Accreditamento a = accr.getAccreditamentoByIdCodiceFiscaleComune(idAccr, codFiscale, idComune);
        logger.info("Accreditamento " + i + ": " + accreditamenti[i]);
        logger.info("Accreditamento " + i + "Profilo Dettagliato : " + accreditamenti[i].getProfilo());;
      }
      logger.info("testGetAccreditamento2 OK.");
         
    }
    
    public void testGetAccreditamento2() {
      Accreditamento[] accreditamenti = accr.getAccreditamenti("CODFISCALE1", "IDCOMUNE");
      assertEquals(0, accreditamenti.length);
      logger.info("testGetAccreditamento2 OK.");
    }

    public void testIsUtenteRegistratoLocalmente1() {
      boolean result = accr.esisteProfiloLocale("CODFISCALE", "IDCOMUNE");
      assertEquals(true, result);  
      logger.info("testIsUtenteRegistratoLocalmente1 OK.");
    
    }
    
    public void testIsUtenteRegistratoLocalmente2() {
      boolean result = accr.esisteProfiloLocale("CODFISCALE1", "IDCOMUNE");
      assertEquals(false, result);  
      logger.info("testIsUtenteRegistratoLocalmente2 OK.");
    
    }
    
    public void testEliminaDelega() {
      Delega d = new Delega();
      try {
        d.setCertificazione("abc".getBytes(SiracConstants.DEFAULT_CHARSET));
      } catch (UnsupportedEncodingException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
        fail("testAccreditaIntermediario Failed.");
      }
      d.setCodiceFiscaleDelegante("CODFISCALE");
      d.setCodiceFiscaleDelegato("AAAAAAAAAAAAAAAA");
      d.setIdAccreditamento(23);
      d.setIdQualifica("OAI");
      d.setNome("NOMEDELEGATO");
      d.setCognome("COGNOMEDELEGATO");
      d.setTimestampCertificazione(Long.toString(System.currentTimeMillis()));
      
      try {
        accr.eliminaDelega(d);
      } catch (Exception e) {
        // TODO Auto-generated catch block
        //e.printStackTrace();
        fail(e.getMessage());
      }

    }

}
