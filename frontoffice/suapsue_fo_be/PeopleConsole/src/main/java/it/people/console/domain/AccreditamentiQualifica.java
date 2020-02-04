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
package it.people.console.domain;

import it.people.sirac.accr.beans.Qualifica;

/**
 * @author Luca Barbieri - Pradac Informatica S.r.l.
 * @created 16/nov/2011 10.31.53
 *
 */
public class AccreditamentiQualifica extends AbstractBaseBean implements
		Clearable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6558079321889752608L;
	private String id_qualifica;
	private String descrizione;
	private String tipo_qualifica;
	private String titolare;
	private boolean intermediario_professionista;
	private boolean auto_certificabile;
	private boolean has_rappresentante_legale;
	
	private boolean qualifica_utilizzata;
	
	private String editLink;
	private String deleteLink;
	private String deleteLinkJS;
	
	private String error;
	
	
	public AccreditamentiQualifica() {
	}

	public AccreditamentiQualifica(Qualifica qualifica) {
		this.id_qualifica = qualifica.getIdQualifica();
		this.descrizione = qualifica.getDescrizione();
		this.tipo_qualifica = qualifica.getTipoQualifica();
		this.has_rappresentante_legale = qualifica.getHasRappresentanteLegale();
	}
		
	
	public String getId_qualifica() {
		return id_qualifica;
	}

	public void setId_qualifica(String id_qualifica) {
		this.id_qualifica = id_qualifica;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getTipo_qualifica() {
		return tipo_qualifica;
	}

	public void setTipo_qualifica(String tipo_qualifica) {
		this.tipo_qualifica = tipo_qualifica;
	}

	public String getTitolare() {
		return titolare;
	}

	public void setTitolare(String titolare) {
		this.titolare = titolare;
	}



	public void setIntermediario_professionista(boolean intermediario_professionista) {
		this.intermediario_professionista = intermediario_professionista;
	}


	public boolean isIntermediario_professionista() {
		return intermediario_professionista;
	}


	public void setAuto_certificabile(boolean auto_certificabile) {
		this.auto_certificabile = auto_certificabile;
	}


	public boolean isAuto_certificabile() {
		return auto_certificabile;
	}


	public void setHas_rappresentante_legale(boolean has_rappresentante_legale) {
		this.has_rappresentante_legale = has_rappresentante_legale;
	}


	public boolean isHas_rappresentante_legale() {
		return has_rappresentante_legale;
	}


	public void setQualifica_utilizzata(boolean qualifica_utilizzata) {
		this.qualifica_utilizzata = qualifica_utilizzata;
	}


	public boolean isQualifica_utilizzata() {
		return qualifica_utilizzata;
	}


	public String getEditLink() {
		return editLink;
	}

	public void setEditLink(String editLink) {
		this.editLink = editLink;
	}

	public String getDeleteLink() {
		return deleteLink;
	}
	
	public void setDeleteLink(String deleteLink) {
		this.deleteLink = deleteLink;
	}
	
	public String getDeleteLinkJS() {
		return deleteLinkJS;
	}

	public void setDeleteLinkJS(String deleteLinkJS) {
		this.deleteLinkJS = deleteLinkJS;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}


	
	/* (non-Javadoc)
	 * @see it.people.console.domain.Clearable#clear()
	 */
	public void clear() {
		this.setId_qualifica(null);
		this.setDescrizione(null);
		this.setTipo_qualifica(null);
		this.setTitolare(null);
		this.setIntermediario_professionista(new Boolean(null));
		this.setAuto_certificabile(new Boolean(null));
		this.setEditLink(null);
		this.setDeleteLink(null);
		this.setDeleteLinkJS(null);
	}



}
