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
 * Created on 8-feb-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package it.people.sirac.services.accr;

import it.people.sirac.accr.beans.Accreditamento;
import it.people.sirac.accr.beans.Delega;
import it.people.sirac.accr.beans.ProfiloAccreditamento;
import it.people.sirac.accr.beans.ProfiloLocale;
import it.people.sirac.accr.beans.Qualifica;
import it.people.sirac.accr.beans.Qualifica2Persona;

import java.net.URL;
import java.rmi.RemoteException;

/**
 * @author max
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class IAccreditamentoClientAdapter /*implements it.people.sirac.accr.IAccreditamentoWS */ {
  
  protected java.net.URL endpointURL;

  public IAccreditamentoClientAdapter(String endpointURLString) throws Exception {
    if (endpointURLString == null) 
      throw new Exception ("IAccreditamentoclientAdapter:: constructor:: Exception. Endpoint URL String Unspecified.");

    endpointURL = new URL(endpointURLString);
  }  
    
  /** 
   * @see it.people.sirac.accr.IAccreditamento#accreditaIntermediario(java.lang.String, java.lang.String, java.lang.String, it.people.sirac.accr.ProfiloAccreditamento)
   */
  public void accreditaIntermediario(String codiceFiscale, String idComune,
          String qualifica, ProfiloAccreditamento profilo) throws RemoteException {
      try {
        getAdaptee().accreditaIntermediario(codiceFiscale, idComune, qualifica, profilo);
      } catch (Exception e) {
        // FIXME Auto-generated catch block
        e.printStackTrace();
        throw new RemoteException("Remote Exception: " + e.getMessage());
      }

  }
  
  /** 
   * @see it.people.sirac.accr.IAccreditamento#creaProfiloLocale(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
   */
  public ProfiloLocale creaProfiloLocale(String codiceFiscale,
          String idComune, String idCARegistrazione,
          String domicilioElettronico) throws RemoteException {
    
      ProfiloLocale p= null;
      try {
        p = getAdaptee().creaProfiloLocale(codiceFiscale, idComune,
                                                idCARegistrazione, domicilioElettronico);
      } catch (Exception e) {
        // FIXME Auto-generated catch block
        //e.printStackTrace();
        throw new RemoteException("Remote Exception: " + e.getMessage());
      }
      return p;
    
  }
  
 
  /** 
   * @see it.people.sirac.accr.IAccreditamento#esisteQualifica(java.lang.String, java.lang.String, java.lang.String[])
   */
  public boolean esisteQualifica(String codiceFiscale, String idComune,
          String[] qualifiche) throws RemoteException {
      boolean r = false;
      /*
      Object[] o = new Object[qualifiche.length];
      System.arraycopy(qualifiche, 0, o, 0, qualifiche.length);
      //for (int i=0; i< qualifiche.length; i++) o[i]=qualifiche[i];
      */
      try {
        //r= getAdaptee().esisteQualifica(codiceFiscale, idComune,o);
        r= getAdaptee().esisteQualifica(codiceFiscale, idComune,qualifiche);
      } catch (Exception e) {
        // FIXME Auto-generated catch block
        //e.printStackTrace();
        throw new RemoteException("Remote Exception: " + e.getMessage());
      }
      return r;
  }
  
  /** 
   * @see it.people.sirac.accr.IAccreditamento#getAccreditamenti(java.lang.String, java.lang.String)
   */
  public Accreditamento[] getAccreditamenti(String codiceFiscale,
          String idComune) throws RemoteException {
    Accreditamento[] accrs = null;
      try {
        accrs = (Accreditamento[]) getAdaptee().getAccreditamenti(codiceFiscale, idComune);
        // FIX 2006-04-07: se non ci sono accreditamenti per il codiceFiscale indicato
        // Axis invece di un array vuoto restituisce null e l'applicazione va in errore
        if (accrs == null) accrs = new Accreditamento[0];
      } catch (Exception e) {
        // FIXME Auto-generated catch block
        //e.printStackTrace();
        throw new RemoteException("Remote Exception: " + e.getMessage());
      }
      return accrs;
  }
  
  /** 
   * @throws 
   * @throws RemoteException
   * @see it.people.sirac.accr.IAccreditamento#esisteProfiloLocale(java.lang.String, java.lang.String)
   */
  public boolean esisteProfiloLocale(String codiceFiscale,
          String idComune) throws RemoteException {
    boolean r = false;
      try {
        r = getAdaptee().esisteProfiloLocale(codiceFiscale, idComune);
      } catch (Exception e) {
        // TODO Auto-generated catch block
        //e.printStackTrace();
        throw new RemoteException("Remote Exception: " + e.getMessage());
      }
    return r;
  }
  
  /** 
   * @see it.people.sirac.accr.IAccreditamento#getAccreditamentoByIdCodiceFiscaleComune(int, java.lang.String, java.lang.String)
   */
  public Accreditamento getAccreditamentoById(int idAccreditamento, String codiceFiscale, 
                            String idComune) throws RemoteException {
    Accreditamento a = null;
    try {
      a = getAdaptee().getAccreditamentoById(idAccreditamento, codiceFiscale, idComune);
    } catch (Exception e) {
      // FIXME Auto-generated catch block
      //e.printStackTrace();
      throw new RemoteException("Remote Exception: " + e.getMessage());
    }
    return a;
  }

  /** 
   * @see it.people.sirac.accr.IAccreditamento#getAccreditamentoByCodiceIntermediario(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
   */
  public Accreditamento getAccreditamentoByCodiceIntermediario(String codiceFiscale, 
                            String idComune, String codiceFiscaleIntermediario, String partitaIvaIntermediario, String idQualifica ) throws RemoteException {
    Accreditamento a = null;
    try {
      a = getAdaptee().getAccreditamentoByCodiceIntermediario(codiceFiscale, idComune, codiceFiscaleIntermediario, partitaIvaIntermediario, idQualifica);
    } catch (Exception e) {
      // FIXME Auto-generated catch block
      //e.printStackTrace();
      throw new RemoteException("Remote Exception: " + e.getMessage());
    }
    return a;
  }

  /** 
   * @see it.people.sirac.accr.IAccreditamento#creaDelega(it.people.sirac.accr.Delega)
   */
  public void creaDelega(Delega delega) throws RemoteException {
      try {
        getAdaptee().creaDelega(delega);
      } catch (Exception e) {
        // FIXME Auto-generated catch block
        //e.printStackTrace();
        throw new RemoteException("Remote Exception: " + e.getMessage());
      }

  }
  
  /** 
   * @see it.people.sirac.accr.IAccreditamento#eliminaDelega(it.people.sirac.accr.Delega)
   */
  public void eliminaDelega(Delega delega) throws RemoteException {
      try {
        getAdaptee().eliminaDelega(delega);
      } catch (Exception e) {
        // FIXME Auto-generated catch block
        //e.printStackTrace();
        throw new RemoteException("Remote Exception: " + e.getMessage());
      }

  }
  
  /** 
   * @see it.people.sirac.accr.IAccreditamento#getDeleghe(java.lang.String, java.lang.String, int)
   */
  public Delega[] getDeleghe(String codiceFiscale, String idComune, int idAccreditamento) throws RemoteException {
    Delega[] deleghe = null;
    try {
      deleghe = (Delega[]) getAdaptee().getDeleghe(codiceFiscale, idComune, idAccreditamento);
    } catch (Exception e) {
      // FIXME Auto-generated catch block
      //e.printStackTrace();
      throw new RemoteException("Remote Exception: " + e.getMessage());
    }
    return deleghe;
  }
  
  public ProfiloLocale getProfiloLocale(String codiceFiscale, String idComune) throws RemoteException {
    ProfiloLocale p = null;
    try {
      p = getAdaptee().getProfiloLocale(codiceFiscale, idComune);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      //e.printStackTrace();
      throw new RemoteException("Remote Exception: " + e.getMessage());
    }
    return p;
  }

//--------------------------------------------------------------------------------------
  /** 
   * @see it.people.sirac.accr.IAccreditamentoWS#getQualifiche()
   */
  public Qualifica[] getQualifiche() throws RemoteException {
    Qualifica[] qualifiche = null;
    try {
      qualifiche = (Qualifica[])getAdaptee().getQualifiche();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      //e.printStackTrace();
      throw new RemoteException("Remote Exception: " + e.getMessage());
    }
    return qualifiche;
  }

  /**
   * @see it.people.sirac.accr.IAccreditamentoWS#getQualificheAbilitate(java.lang.String, java.lang.String)
   */
  public Qualifica[] getQualificheAbilitate (String codiceFiscale, String idComune) throws RemoteException {
    Qualifica[] qualificheAbilitate = null;
    try {
      qualificheAbilitate = (Qualifica[]) getAdaptee().getQualificheAbilitate(codiceFiscale, idComune);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      //e.printStackTrace();
      throw new RemoteException("Remote Exception: " + e.getMessage());
    }
    return qualificheAbilitate;
    
  }
  
  /** 
   * @see it.people.sirac.accr.IAccreditamentoWS#getQualificheAccreditabili(java.lang.String, java.lang.String)
   */
  public Qualifica[] getQualificheAccreditabili(String codiceFiscale, String idComune) throws RemoteException {
    Qualifica[] qualificheAccreditabili = null;
    try {
      qualificheAccreditabili = (Qualifica[]) getAdaptee().getQualificheAccreditabili(codiceFiscale, idComune);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      //e.printStackTrace();
      throw new RemoteException("Remote Exception: " + e.getMessage());
    }
    return qualificheAccreditabili;
    
  }
  
  /**
   * @see it.people.sirac.accr.IAccreditamentoWS#getQualificaById(java.lang.String)
   */
  public Qualifica getQualificaById(String idQualifica) throws RemoteException  {
    Qualifica qualifica = null;
    try {
      qualifica = getAdaptee().getQualificaById(idQualifica);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      //e.printStackTrace();
      throw new RemoteException("Remote Exception: " + e.getMessage());
    }
    return qualifica;
    
  }
  
  /** 
   * @see it.people.sirac.accr.IAccreditamentoWS#getQualifiche2Persona(java.lang.String)
   */
  public Qualifica2Persona[] getQualifiche2Persona(String tipoQualifica) throws RemoteException {
    Qualifica2Persona[] qualifica2Persona = null;
    try {
      qualifica2Persona = (Qualifica2Persona[]) getAdaptee().getQualifiche2Persona(tipoQualifica);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      //e.printStackTrace();
      throw new RemoteException("Remote Exception: " + e.getMessage());
    }
    return qualifica2Persona;
    
  }
  
  /** 
   * @see it.people.sirac.accr.IAccreditamentoWS#canCreateDelega(java.lang.String, java.lang.String, java.lang.String)
   */
  public boolean canCreateDelega(String codiceFiscale, String idComune, String idQualifica) throws RemoteException {
    boolean result = false;
    try {
      result = getAdaptee().canCreateDelega(codiceFiscale, idComune, idQualifica);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      //e.printStackTrace();
      throw new RemoteException("Remote Exception: " + e.getMessage());
    }
    return result;
  }
  
  /**
   * @see it.people.sirac.accr.IAccreditamentoWS#getAutoCertTemplate(java.lang.String)
   */
  public String getAutoCertTemplate(String tipoqualifica) throws RemoteException {
    String template = null;
    try {
      template = getAdaptee().getAutoCertTemplate(tipoqualifica);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      //e.printStackTrace();
      throw new RemoteException("Remote Exception: " + e.getMessage());
    }
    return template;
  }
  
  /** 
   * @see it.people.sirac.accr.IAccreditamentoWS#getDelegaTemplate(java.lang.String)
   */
  public String getDelegaTemplate(String tipoDelega) throws RemoteException {
    String template = null;
    try {
      template = getAdaptee().getDelegaTemplate(tipoDelega);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      //e.printStackTrace();
      throw new RemoteException("Remote Exception: " + e.getMessage());
    }
    return template;
    
  }

//--------------------------------------------------------------------------------------
  // Support methods
  /*
  private it.people.sirac.services.accr.client.IAccreditamento connect(String endpoint) throws MalformedURLException, Exception {
    IAccreditamentoService service = new IAccreditamentoServiceLocator();
    it.people.sirac.services.accr.client.IAccreditamento stub = null;
    try {
       stub = service.getIAccreditamento(new URL(endpoint));
    } catch (Exception e) {
      throw new Exception(e);
    }
    return stub;
}
*/


    private it.people.sirac.services.accr.client.IAccreditamentoWSStub getAdaptee()  throws Exception {
        it.people.sirac.services.accr.client.IAccreditamentoWSStub binding;
        try {
          if (endpointURL == null) throw new Exception ("IAccreditamentoclientAdapter:: getAdaptee:: Cannot connect to Web Service. Endpoint Unspecified.");
            binding = (it.people.sirac.services.accr.client.IAccreditamentoWSStub)
                          new it.people.sirac.services.accr.client.IAccreditamentoWSServiceLocator().getIAccreditamentoWS(endpointURL);
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new Exception("IAccreditamentoClientAdapter - getAdaptee - JAX-RPC ServiceException caught: " + jre);
        }
        //assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(120000);
        return binding;
    }

    
    
}
