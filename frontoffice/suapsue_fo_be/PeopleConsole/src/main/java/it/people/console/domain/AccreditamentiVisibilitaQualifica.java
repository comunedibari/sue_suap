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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import it.people.sirac.accr.beans.VisibilitaQualifica;

/**
 * @author Andrea Piemontese
 * @version 1.0
 */
public class AccreditamentiVisibilitaQualifica implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -6636562112727130534L;
	//Nodo Fe selezionato
	private String selectedNodeId;
	private String selectedNodeName;
	
	//Qualifiche per il nodo selezionato 
	private List <VisibilitaQualifica> visibilitaQualifiche;
	
	
	//Checkbox SELEZIONATE per checkbox 
	private List<String> selectedQualifiche = new ArrayList<String>();
	
	//Numero di pagine del wizard
	private int wizardPages;
	//Pagina corrente del wizard
	private int page;
	
	private boolean ripetiWizard = false;

	
	public AccreditamentiVisibilitaQualifica(final int wizardPages) {
		this.setPage(1);
		this.setWizardPages(wizardPages);
	}
	
	
	/**
	 * @return the wizardPages
	 */
	private int getWizardPages() {
		return wizardPages;
	}

	/**
	 * @param wizardPages the wizardPages to set
	 */
	private void setWizardPages(int wizardPages) {
		this.wizardPages = wizardPages;
	}

	/**
	 * @return the selectedNodeId
	 */
	public String getSelectedNodeId() {
		return selectedNodeId;
	}


	/**
	 * @param selectedNodeId the selectedNodeId to set
	 */
	public void setSelectedNodeId(String selectedNodeId) {
		this.selectedNodeId = selectedNodeId;
	}


	/**
	 * @return the selectedNodeName
	 */
	public String getSelectedNodeName() {
		return selectedNodeName;
	}


	/**
	 * @param selectedNodeName the selectedNodeName to set
	 */
	public void setSelectedNodeName(String selectedNodeName) {
		this.selectedNodeName = selectedNodeName;
	}


	/**
	 * @return the page
	 */
	public final int getPage() {
		return page;
	}

	/**
	 * @param page the page to set
	 */
	public final void setPage(int page) {
		this.page = page;
	}
	
	public final void nextPage() {
		if (this.page < this.getWizardPages()) {
			this.page++;
		}
	}

	public final void previousPage() {
		if (this.page > 1) {
			this.page--;
		}
	}


	/**
	 * @return the visibilitaQualifiche
	 */
	public List <VisibilitaQualifica> getVisibilitaQualifiche() {
		return visibilitaQualifiche;
	}


	/**
	 * @param visibilitaQualifiche the visibilitaQualifiche to set
	 */
	public void setVisibilitaQualifiche(List <VisibilitaQualifica> visibilitaQualifiche) {
		this.visibilitaQualifiche = visibilitaQualifiche;
	}


	/**
	 * @return the selectedQualifiche
	 */
	public List<String> getSelectedQualifiche() {
		return selectedQualifiche;
	}


	/**
	 * @param selectedQualifiche the selectedQualifiche to set
	 */
	public void setSelectedQualifiche(List<String> selectedQualifiche) {
		this.selectedQualifiche = selectedQualifiche;
	}


	/**
	 * Init checkbox value 
	 */
	public void initSelectedQualificheCheckbox() {
		
		//Reset selected
		selectedQualifiche.clear();
		
		Iterator<VisibilitaQualifica> qualificheIter = visibilitaQualifiche.iterator();
		
		while (qualificheIter.hasNext()) {
			VisibilitaQualifica current = qualificheIter.next();
			if (current.isVisibile()) {
				selectedQualifiche.add(current.getIdQualifica());
			}
		}	
	}


	/**
	 *  Update visibilità qualifiche from selected checkbox
	 */
	public void updateVisibilitaQualificheFromCheckbox() {
		
		Iterator<VisibilitaQualifica> qualificheIter = visibilitaQualifiche.iterator();
		
		while (qualificheIter.hasNext()) {
			VisibilitaQualifica currentQual = qualificheIter.next();
				
			if (selectedQualifiche.contains(currentQual.getIdQualifica())) {
				currentQual.setVisibile(true);
			}
			else {
				currentQual.setVisibile(false);
			}
		}
	}


	/**
	 * @return the ripetiWizard
	 */
	public boolean isRipetiWizard() {
		return ripetiWizard;
	}


	/**
	 * @param ripetiWizard the ripetiWizard to set
	 */
	public void setRipetiWizard(boolean ripetiWizard) {
		this.ripetiWizard = ripetiWizard;
	}


	
	
}
