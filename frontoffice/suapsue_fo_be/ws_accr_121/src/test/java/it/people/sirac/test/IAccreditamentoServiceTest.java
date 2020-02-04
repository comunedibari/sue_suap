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
 * Created on 9-feb-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package it.people.sirac.test;

import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.people.sirac.accr.beans.Accreditamento;
import it.people.sirac.accr.beans.Delega;
import it.people.sirac.accr.beans.ProfiloAccreditamento;
import it.people.sirac.accr.beans.ProfiloLocale;
import it.people.sirac.accr.beans.Qualifica;
import it.people.sirac.accr.beans.Qualifica2Persona;
import it.people.sirac.accr.beans.RappresentanteLegale;
import it.people.sirac.core.SiracConstants;
import it.people.sirac.services.accr.IAccreditamentoClientAdapter;
import it.people.sirac.test.mock.MockSiracDao;
import it.people.sirac.util.DataValidator;

/**
 * @author max
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class IAccreditamentoServiceTest extends IAccreditamentoLocalTest {
  /**
   * Logger for this class
   */
  protected static final Logger logger = LoggerFactory.getLogger(IAccreditamentoLocalTest.class);

  protected it.people.sirac.services.accr.IAccreditamentoClientAdapter accr;

  protected MockSiracDao mockSiracDao;
  
  /**
   * @param name
   */
  public IAccreditamentoServiceTest(String name) {
    super(name);
    // TODO Auto-generated constructor stub
  }
  
  protected void setUp() {
    try {
      //logger.debug("Comune : " + )
/*      logger.debug("Autoattivazione Accreditamenti per il comune: " +
          (SiracRegistry.getSirac()).autoAttivazioneAccrEnabled("IDCOMUNE"));  
*/      //accr = SiracRegistry.getAccreditamento();
      
      accr = new IAccreditamentoClientAdapter("http://localhost:8000/ws_accr121/services/IAccreditamentoWS");
      mockSiracDao = new MockSiracDao();
    } catch (Exception e) {
      throw new RuntimeException("Exception during setup", e);
    }

  }
    
    public void test3_AccreditaIntermediario() {
      
      ProfiloAccreditamento pa = new ProfiloAccreditamento();
      pa.setCodiceFiscaleIntermediario("CODICEFISCALEINT");
      pa.setPartitaIvaIntermediario("PARTITAIVAINT");
      pa.setDescrizione("DESC");
      pa.setDenominazione("RAGSOCINTERMEDIARIO");
      pa.setDescrizione("DESCRIZIONE");
      pa.setSedeLegale("SEDELEGALE");
      pa.setDomicilioElettronico("aa@bb.cc");
      try {
        pa.setAutoCert("abc".getBytes(SiracConstants.DEFAULT_CHARSET));
      } catch (UnsupportedEncodingException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
        fail("testAccreditaIntermediario Failed.");
      }
      pa.setTimestampAutoCert(String.valueOf(System.currentTimeMillis()));
      
      RappresentanteLegale rl = new RappresentanteLegale();
      rl.setCodiceFiscale("CFRAPPRLEGALE");
      rl.setNome("NOMERL");
      rl.setCognome("COGNOMERL");
      rl.setCodiceFiscaleIntermediario("CODFISCINTERMEDIARIO");
      rl.setPartitaIvaIntermediario("PIVAINTERMEDIARIO");
      
      SimpleDateFormat df = new SimpleDateFormat(DataValidator.DATE_PATTERN);
      try {
        rl.setDataNascita(df.parse("10/05/1967"));
      } catch (ParseException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
        fail("testAccreditaIntermediario Failed.");
      }
      rl.setIndirizzoResidenza("RLINDIRIZZORESIDENZA");
      rl.setLuogoNascita("RLLUOGONASCITA");
      rl.setProvinciaNascita("RLPROVINCIANASCITA");
      rl.setSesso("M");
      
      pa.setRappresentanteLegale(rl);
      
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
    
    public void test1_CreaProfiloLocale() {
        
      
         ProfiloLocale p1 = new ProfiloLocale(
             "CODFISCALE", "IDCOMUNE", "IDCA", "DOMELETTR");
         ProfiloLocale p2 = null;
         try {
          p2 = accr.creaProfiloLocale("CODFISCALE", "IDCOMUNE", "IDCA", "DOMELETTR");
        } catch (Exception e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
          fail(e.getMessage());
        }

      if (logger.isDebugEnabled()) {
        logger
            .debug("test2_CreaProfiloLocale() - Profilo utente creato:  : p2 = "
                + p2);
      }
         
         
    }
    
    public void test5_CreaDelegatoIntermediario() {
      Accreditamento accrDelegante = null;
      try {
        accrDelegante = 
          accr.getAccreditamentoByCodiceIntermediario("CODFISCALE", "IDCOMUNE", "CODICEFISCALEINT", "PARTITAIVAINT", "CAF");
      } catch (RemoteException e2) {
        // TODO Auto-generated catch block
        //e2.printStackTrace();
        fail(e2.getMessage());
      }

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

    public void test7_EsisteQualifica1() {
      
      boolean result=false;
      try {
        result = accr.esisteQualifica("CODFISCALE", "IDCOMUNE", new String[]{"CAF"});
      } catch (RemoteException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      assertEquals(true, result);  
      logger.info("testEsisteQualifica1 OK.");
         
    }

    public void test7_EsisteQualifica2() {
      
      boolean result=false;
      try {
        result = accr.esisteQualifica("CODFISCALE1", "IDCOMUNE", new String[]{"CAF"});
      } catch (RemoteException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      assertEquals(false, result);  
      logger.info("testEsisteQualifica2 OK.");
         
    }
    
    public void testC_GetAccreditamentoByCodiceIntermediario() {
      Accreditamento result = null;
      try {
        result = accr.getAccreditamentoByCodiceIntermediario("CODFISCALE", "IDCOMUNE", "CODICEFISCALEINT", "PARTITAIVAINT", "CAF");
      } catch (RemoteException e) {
        // TODO Auto-generated catch block
        //e.printStackTrace();
        fail(e.getMessage());
      }
      if (result == null) fail("Accreditamento non trovato");
      logger.info("Accreditamento : " + result);
      logger.info("Profilo Accreditamento : " + result.getProfilo());
      
    }

    public void testC_GetAccreditamenti() {
      Accreditamento[] accreditamenti = null;
      try {
        accreditamenti = accr.getAccreditamenti("CODFISCALE", "IDCOMUNE");
        for(int i=0; i<accreditamenti.length; i++) {
          int idAccr = accreditamenti[i].getId();
          String codFiscale = accreditamenti[i].getCodiceFiscale();
          String idComune = accreditamenti[i].getIdComune();
          Accreditamento a = accr.getAccreditamentoById(idAccr, codFiscale, idComune);
          logger.info("Accreditamento " + i + ": " + accreditamenti[i]);
          //logger.info("Accreditamento " + i + "Profilo Dettagliato : " + accreditamenti[i].getProfilo());;
        }
      } catch (RemoteException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      logger.info("testGetAccreditamentoById OK.");
         
    }
    
    public void testC_GetAccreditamentoByCodiceIntermediario2() {
      Accreditamento[] accreditamenti = null;
      try {
        accreditamenti = accr.getAccreditamenti("CODFISCALE1", "IDCOMUNE");
      } catch (RemoteException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      assertEquals(0, accreditamenti.length);
      logger.info("testGetAccreditamento2 OK.");
    }

    public void test2_EsisteProfiloLocale1() {
      boolean result=false;
      try {
        result = accr.esisteProfiloLocale("CODFISCALE", "IDCOMUNE");
      } catch (RemoteException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      assertEquals(true, result);  
      logger.info("testEsisteProfiloLocale1 OK.");
    
    }
    
    public void test2_EsisteProfiloLocale2() {
      boolean result=false;
      try {
        result = accr.esisteProfiloLocale("CODFISCALE1", "IDCOMUNE");
      } catch (RemoteException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      assertEquals(false, result);  
      logger.info("testEsisteProfiloLocale2 OK.");
    
    }
    
    public void test6_EliminaDelega() {
      Accreditamento accrDelegante = null;
      try {
        accrDelegante = 
          accr.getAccreditamentoByCodiceIntermediario("CODFISCALE", "IDCOMUNE", "CODICEFISCALEINT", "PARTITAIVAINT", "CAF");
      } catch (RemoteException e2) {
        // TODO Auto-generated catch block
        //e2.printStackTrace();
        fail(e2.getMessage());
      }

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
      d.setIdAccreditamento(accrDelegante.getId());
      d.setIdQualifica("OAI");
      //d.setNome("NOMEDELEGATO");
      //d.setCognome("COGNOMEDELEGATO");
      //d.setTimestampCertificazione(Long.toString(System.currentTimeMillis()));
      
      try {
        accr.eliminaDelega(d);
      } catch (Exception e) {
        // TODO Auto-generated catch block
        //e.printStackTrace();
        fail(e.getMessage());
      }

    }
    
//-------------------------------------------------------------------------
    public void test8_Getqualifiche1() {
      Qualifica[] qualifiche = null;
      try {
        qualifiche = accr.getQualifiche();
      } catch (Exception e) {
        // TODO Auto-generated catch block
        //e.printStackTrace();
        fail(e.getMessage());
      }
       
      for(int i=0; i<qualifiche.length; i++) {
        logger.info("Qualifica " + i + ": " + qualifiche[i]);
        
      }
    }
    
    public void testA_GetQualificheAbilitate1() {
      Qualifica[] qualifiche = null;
      try {
        qualifiche = accr.getQualificheAbilitate("CODFISCALE", "IDCOMUNE");
      } catch (Exception e) {
        // TODO Auto-generated catch block
        //e.printStackTrace();
        fail(e.getMessage());
      }
      for(int i=0; i<qualifiche.length; i++) {
        logger.info("Qualifica " + i + ": " + qualifiche[i]);
        
      }
      
    }

    public void testB_GetQualificheAccreditabili1() {
      Qualifica[] qualifiche = null;
      try {
        qualifiche = accr.getQualificheAccreditabili("CODFISCALE", "IDCOMUNE");
      } catch (Exception e) {
        // TODO Auto-generated catch block
        //e.printStackTrace();
        fail(e.getMessage());
      }
      for(int i=0; i<qualifiche.length; i++) {
        logger.info("Qualifica " + i + ": " + qualifiche[i]);
        
      }
      
    }

    public void test9_GetQualificaById1() {
      Qualifica qualifica = null;
      String idQualifica = "CAF";
      try {
        qualifica = accr.getQualificaById(idQualifica);
      } catch (Exception e) {
        // TODO Auto-generated catch block
        //e.printStackTrace();
        fail(e.getMessage());
      }
        logger.info("Qualifica corrispondente a id " + idQualifica + ": " + qualifica);
        
      
    }

    public void testG_GetQualifiche2Persona1() {
      Qualifica2Persona[] qualifiche2Persona = null;
      String tipoQualifica = "Intermediario";
      logger.info("Requesting Qualifica2Persona for tipoQualifica = " + tipoQualifica);
      try {
        qualifiche2Persona = accr.getQualifiche2Persona(tipoQualifica);
      } catch (Exception e) {
        // TODO Auto-generated catch block
        //e.printStackTrace();
        fail(e.getMessage());
      }
      for(int i=0; i<qualifiche2Persona.length; i++) {
        logger.info("Qualifica2Persona " + i + ": " + qualifiche2Persona[i]);
        
      }
    }
        
      public void testG_GetQualifiche2Persona2() {
        Qualifica2Persona[] qualifiche2Persona = null;
        String tipoQualifica = "Professionista";
        logger.info("Requesting Qualifica2Persona for tipoQualifica = " + tipoQualifica);
        try {
          qualifiche2Persona = accr.getQualifiche2Persona(tipoQualifica);
        } catch (Exception e) {
          // TODO Auto-generated catch block
          //e.printStackTrace();
          fail(e.getMessage());
        }
        for(int i=0; i<qualifiche2Persona.length; i++) {
          logger.info("Qualifica2Persona " + i + ": " + qualifiche2Persona[i]);
          
        }
      
      }


      public void testG_GetQualifiche2Persona3() {
        Qualifica2Persona[] qualifiche2Persona = null;
        String tipoQualifica = "Rappresentante Persona Giuridica";
        logger.info("Requesting Qualifica2Persona for tipoQualifica = " + tipoQualifica);
        try {
          qualifiche2Persona = accr.getQualifiche2Persona(tipoQualifica);
        } catch (Exception e) {
          // TODO Auto-generated catch block
          //e.printStackTrace();
          fail(e.getMessage());
        }
        for(int i=0; i<qualifiche2Persona.length; i++) {
          logger.info("Qualifica2Persona " + i + ": " + qualifiche2Persona[i]);
          
        }
        assertTrue(qualifiche2Persona.length>0);
      }

      public void testG_GetQualifiche2Persona4() {
        Qualifica2Persona[] qualifiche2Persona = null;
        String tipoQualifica = "XXXXXX";
        logger.info("Requesting Qualifica2Persona for tipoQualifica = " + tipoQualifica);
        try {
          qualifiche2Persona = accr.getQualifiche2Persona(tipoQualifica);
        } catch (Exception e) {
          // TODO Auto-generated catch block
          //e.printStackTrace();
          fail(e.getMessage());
        }
        
        assertTrue(qualifiche2Persona.length==0);
      
      }
      
      public void test4_CanCreateDelega1() {
        boolean result = false;
        
        try {
          result = accr.canCreateDelega("CODFISCALE", "IDCOMUNE", null);
        } catch (Exception e) {
          // TODO Auto-generated catch block
          //e.printStackTrace();
          fail(e.getMessage());
        }
        assertTrue(result);
      }

      public void test4_CanCreateDelega2() {
        boolean result = false;
        
        try {
          result = accr.canCreateDelega("CODFISCALE1", "IDCOMUNE", null);
        } catch (Exception e) {
          // TODO Auto-generated catch block
          //e.printStackTrace();
          fail(e.getMessage());
        }
        assertFalse(result);
      }

      public void test4_CanCreateDelega3() {
        boolean result = false;
        
        try {
          result = accr.canCreateDelega("AAAAAAAAAAAAAAAA", "IDCOMUNE", null);
        } catch (Exception e) {
          // TODO Auto-generated catch block
          //e.printStackTrace();
          fail(e.getMessage());
        }
        assertFalse(result);
      }
      
      public void testE_GetAutoCertTemplate1() {
        String template = null;
        String[] tipiQualifiche = {"Professionista", "Intermediario", "Rappresentante Persona Giuridica", "XYZ"};
        try {
          for (int i=0; i<tipiQualifiche.length; i++) {
            template = accr.getAutoCertTemplate(tipiQualifiche[i]);
            logger.info("Template autocertificazione per tipoQualifica = " + tipiQualifiche[i] + "\n");
            logger.info("\n" + template + "\n");
          }
        } catch (Exception e) {
          // TODO Auto-generated catch block
          //e.printStackTrace();
          fail(e.getMessage());
        }
        
      }

      public void testF_GetDelegaTemplate1() {
        String template = null;
        String[] tipiDeleghe = {"Intermediario", "XYZ"};
        try {
          for (int i=0; i<tipiDeleghe.length; i++) {
            template = accr.getDelegaTemplate(tipiDeleghe[i]);
            logger.info("Template autocertificazione per tipoDelega = " + tipiDeleghe[i] + "\n");
            logger.info("\n" + template + "\n");
          }
        } catch (Exception e) {
          // TODO Auto-generated catch block
          //e.printStackTrace();
          fail(e.getMessage());
        }
        
      }
      
}
