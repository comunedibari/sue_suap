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
package it.people.sirac.services.accr.management;

import java.rmi.RemoteException;

import it.people.sirac.accr.beans.VisibilitaQualifica;



public interface IAccreditamentiManagementWS extends java.rmi.Remote {
    
	public it.people.sirac.accr.beans.Qualifica getQualifica(java.lang.String id_qualifica) throws java.rmi.RemoteException;
    public it.people.sirac.accr.beans.Qualifica[] getQualifiche() throws java.rmi.RemoteException;
    public it.people.sirac.accr.beans.Qualifica[] getQualifiche(java.lang.String startingPoint, java.lang.String pageSize) throws java.rmi.RemoteException;
    public java.lang.String[] getComuni() throws java.rmi.RemoteException;
    public java.lang.String[] getTipiQualifica() throws java.rmi.RemoteException;
    public it.people.sirac.accr.beans.Accreditamento[] getAccreditamentiBySearch(
    		java.lang.String idQualifica,
    		java.lang.String idComune, 
    		java.lang.String codiceFiscaleUtenteAccreditato,
    		java.lang.String codiceFiscaleIntermediario, 
    		java.lang.String partitaIvaIntermediario,
    		java.lang.String domicilioElettronicoIntermediario,
    		java.lang.String denominazioneIntermediario, 
    		java.lang.String sedeLegale,
    		java.lang.String statoAccreditamento,
    		java.lang.String startingPoint, 
    		java.lang.String pageSize) throws java.rmi.RemoteException;
    public it.people.sirac.accr.beans.Accreditamento getAccreditamentoById(int idAccreditamento) throws java.rmi.RemoteException;
    public void setAccreditamentoDeleted(int idAccreditamento, java.lang.String status) throws java.rmi.RemoteException;
    public void setAccreditamentoAttivo(int idAccreditamento, java.lang.String status) throws java.rmi.RemoteException;    
    public it.people.sirac.accr.beans.Accreditamento[] getOperatoriAssociazione(
    		java.lang.String idComune, 
    		java.lang.String codiceFiscaleIntermediario, 
    		java.lang.String partitaIvaIntermediario,
    		java.lang.String startingPoint, 
    		java.lang.String pageSize) throws java.rmi.RemoteException;
    public java.lang.String getNumAccreditamentiBySearch(
    		java.lang.String idQualifica,
    		java.lang.String idComune, 
    		java.lang.String codiceFiscaleUtenteAccreditato,
    		java.lang.String codiceFiscaleIntermediario, 
    		java.lang.String partitaIvaIntermediario,
    		java.lang.String domicilioElettronicoIntermediario,
    		java.lang.String denominazioneIntermediario, 
    		java.lang.String sedeLegale,
    		java.lang.String statoAccreditamento) throws java.rmi.RemoteException;
    public boolean isAccreditamentoDeleted(int idAccreditamento) throws java.rmi.RemoteException;
    public boolean setAccreditamenti_Attivo(String[] attivo, String[] non_attivo) throws java.rmi.RemoteException;
    public boolean setAccreditamenti_Deleted(String[] deleted, String[] non_deleted) throws java.rmi.RemoteException;
    public java.lang.String[] getAutocertificazione(int idAccreditamento) throws java.rmi.RemoteException;
    public void insertQualifica(java.lang.String id_qualifica,
			java.lang.String descrizione, java.lang.String tipo_qualifica,
			java.lang.String auto_certificabile,
			java.lang.String has_rappresentante_legale)
			throws java.rmi.RemoteException;
    public void updateQualifica(java.lang.String id_qualifica,
    		java.lang.String descrizione, java.lang.String tipo_qualifica,
    		java.lang.String auto_certificabile,
    		java.lang.String has_rappresentante_legale)
    throws java.rmi.RemoteException;
    public void deleteQualifica(java.lang.String id_qualifica) throws java.rmi.RemoteException;
    public String[] getInfoQualifica(String id_qualifica) throws java.rmi.RemoteException;

    /**
     * @param codiceComune
     * @return
     * @throws java.rmi.RemoteException
     */
    public VisibilitaQualifica[] getVisibilitaQualifiche(final String codiceComune) throws java.rmi.RemoteException;
    
    /**
     * @param codiceComune
     * @param visibilitaQualifiche
     * @return
     * @throws java.rmi.RemoteException
     */
    public boolean setVisibilitaQualifiche(final String codiceComune, final VisibilitaQualifica[] visibilitaQualifiche) throws java.rmi.RemoteException;

}
