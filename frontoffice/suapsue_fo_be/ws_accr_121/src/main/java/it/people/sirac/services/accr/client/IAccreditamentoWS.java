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
package it.people.sirac.services.accr.client;

public interface IAccreditamentoWS extends java.rmi.Remote {
    public it.people.sirac.accr.beans.ProfiloLocale creaProfiloLocale(java.lang.String codiceFiscale, java.lang.String idComune, java.lang.String idCARegistrazione, java.lang.String domicilioElettronico) throws java.rmi.RemoteException;
    public void accreditaIntermediario(java.lang.String codiceFiscale, java.lang.String idComune, java.lang.String qualifica, it.people.sirac.accr.beans.ProfiloAccreditamento profilo) throws java.rmi.RemoteException;
    public void creaDelega(it.people.sirac.accr.beans.Delega delega) throws java.rmi.RemoteException;
    public boolean esisteProfiloLocale(java.lang.String codiceFiscale, java.lang.String idComune) throws java.rmi.RemoteException;
    public it.people.sirac.accr.beans.Accreditamento[] getAccreditamenti(java.lang.String codiceFiscale, java.lang.String idComune) throws java.rmi.RemoteException;
    public it.people.sirac.accr.beans.Delega[] getDeleghe(java.lang.String codiceFiscale, java.lang.String idComune, int idAccreditamento) throws java.rmi.RemoteException;
    public void eliminaDelega(it.people.sirac.accr.beans.Delega delega) throws java.rmi.RemoteException;
    public boolean esisteQualifica(java.lang.String codiceFiscale, java.lang.String idComune, java.lang.String[] qualifiche) throws java.rmi.RemoteException;
    public it.people.sirac.accr.beans.Accreditamento getAccreditamentoById(int idAccreditamento, java.lang.String codiceFiscale, java.lang.String idComune) throws java.rmi.RemoteException;
    public it.people.sirac.accr.beans.Accreditamento getAccreditamentoByCodiceIntermediario(java.lang.String codiceFiscale, java.lang.String idComune, java.lang.String codiceFiscaleIntermediario, java.lang.String partitaIvaIntermediario, java.lang.String idQualifica) throws java.rmi.RemoteException;
    public it.people.sirac.accr.beans.ProfiloLocale getProfiloLocale(java.lang.String codiceFiscale, java.lang.String idComune) throws java.rmi.RemoteException;
    public it.people.sirac.accr.beans.Qualifica[] getQualifiche() throws java.rmi.RemoteException;
    public it.people.sirac.accr.beans.Qualifica[] getQualificheAbilitate(java.lang.String codiceFiscale, java.lang.String idComune) throws java.rmi.RemoteException;
    public it.people.sirac.accr.beans.Qualifica[] getQualificheAccreditabili(java.lang.String codiceFiscale, java.lang.String idComune) throws java.rmi.RemoteException;
    public it.people.sirac.accr.beans.Qualifica getQualificaById(java.lang.String idQualifica) throws java.rmi.RemoteException;
    public it.people.sirac.accr.beans.Qualifica2Persona[] getQualifiche2Persona(java.lang.String tipoQualifica) throws java.rmi.RemoteException;
    public boolean canCreateDelega(java.lang.String codiceFiscale, java.lang.String idComune, java.lang.String idQualifica) throws java.rmi.RemoteException;
    public java.lang.String getAutoCertTemplate(java.lang.String tipoqualifica) throws java.rmi.RemoteException;
    public java.lang.String getDelegaTemplate(java.lang.String tipoDelega) throws java.rmi.RemoteException;
    public it.people.sirac.accr.beans.Qualifica2Persona getQualifica2Persona(java.lang.String tipoQualifica) throws java.rmi.RemoteException;
}
