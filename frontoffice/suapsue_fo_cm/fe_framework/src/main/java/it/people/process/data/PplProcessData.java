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
package it.people.process.data;

import it.people.core.PeopleContext;
import it.people.parser.FieldValidator;
import it.people.parser.RequiredAttribute;
import it.people.process.AbstractPplProcess;
import it.people.vsl.PipelineData;
import it.people.vsl.PipelineDataImpl;

import java.util.ArrayList;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Category;

/**
 * Created by IntelliJ IDEA. User: sergio Date: Sep 15, 2003 Time: 9:14:40 AM To
 * change this template use Options | File Templates.
 */
public class PplProcessData extends AbstractData {

    private Category cat = Category.getInstance(PplProcessData.class.getName());

    private String m_firstName;
    private String m_lastName;
    private boolean m_hasChildren;
    private String m_ssn;
    private String m_residenza;
    private String m_via;
    private String m_luogoDiNascita;
    private String m_dataDiNascita;
    private String m_cittadinanza;
    private String m_telefonoFisso;
    private String m_telCellulare;
    private String m_titoloStudio;
    private ArrayList m_attachments = new ArrayList();

    public PplProcessData() {
	super();
	m_clazz = PplProcessData.class;
	m_validators.put("firstName", new RequiredAttribute());
	m_validators.put("ssn", new RequiredAttribute());

    }

    /**
     * Definisce i validatori dei dati.
     */
    public void doDefineValidators() {
    }

    /**
     * Inizializzazione dati.
     * 
     * @param context
     * @param pplProcess
     */
    public void initialize(PeopleContext context, AbstractPplProcess pplProcess) {
	addAttachment("Path1");
	addAttachment("Path2");
    }

    public boolean validate() {
	return true;
    }

    public boolean validate(String properyName) {
	FieldValidator validator = (FieldValidator) m_validators
		.get(properyName);
	try {
	    if (validator != null)

		return validator.validate(PropertyUtils.getProperty(this,
			properyName));
	} catch (Exception ex) {
	    cat.error(ex);
	}
	return true;
    }

    public void exportToPipeline(PipelineData pd) {
	ArrayList attachPaths = new ArrayList();
	attachPaths.addAll(getAttachments());
	pd.setAttribute(PipelineDataImpl.ATTACHMENT_PARAMNAME, attachPaths);
    }

    /**
     * Metodi Set/Get/Add per definire le proprieta'
     */
    public String getFirstName() {
	return m_firstName;
    }

    public void setFirstName(String p_firstName) {
	m_firstName = p_firstName;
	// changed("firstName"); // deprecato non � pi� necessario
    }

    public String getLastName() {
	return m_lastName;
    }

    public void setLastName(String p_lastName) {
	m_lastName = p_lastName;
	// changed("flastName"); // deprecato non � pi� necessario
    }

    public boolean isHasChildren() {
	return m_hasChildren;
    }

    public void setHasChildren(boolean p_hasChildren) {
	m_hasChildren = p_hasChildren;
	// changed("hasChildren"); // deprecato non � pi� necessario
    }

    public String getSsn() {
	return m_ssn;
    }

    public void setSsn(String p_ssn) {
	m_ssn = p_ssn;
	// changed("ssn"); // deprecato non � pi� necessario
    }

    public String getResidenza() {
	return m_residenza;
    }

    public void setResidenza(String p_residenza) {
	m_residenza = p_residenza;
	// changed("residenza"); // deprecato non � pi� necessario
    }

    public String getVia() {
	return m_via;
    }

    public void setVia(String p_via) {
	m_via = p_via;
	// changed("via"); // deprecato non � pi� necessario
    }

    public String getLuogoDiNascita() {
	return m_luogoDiNascita;
    }

    public void setLuogoDiNascita(String p_luogoDiNascita) {
	m_luogoDiNascita = p_luogoDiNascita;
	// changed("luogoDiNascita"); // deprecato non � pi� necessario

    }

    public String getDataDiNascita() {
	return m_dataDiNascita;
    }

    public void setDataDiNascita(String p_dataDiNascita) {
	m_dataDiNascita = p_dataDiNascita;
	// changed("dataDiNascita"); // deprecato non � pi� necessario
    }

    public String getCittadinanza() {
	return m_cittadinanza;
    }

    public void setCittadinanza(String p_cittadinanza) {
	m_cittadinanza = p_cittadinanza;
	// changed("cittadinanza"); // deprecato non � pi� necessario
    }

    public String getTelefonoFisso() {
	return m_telefonoFisso;
    }

    public void setTelefonoFisso(String p_telefonoFisso) {
	m_telefonoFisso = p_telefonoFisso;
    }

    public String getTelCellulare() {
	return m_telCellulare;
    }

    public void setTelCellulare(String p_telCellulare) {
	m_telCellulare = p_telCellulare;
    }

    public String getTitoloStudio() {
	return m_titoloStudio;
    }

    public void setTitoloStudio(String p_titoloStudio) {
	m_titoloStudio = p_titoloStudio;
    }

    public ArrayList getAttachments() {
	return m_attachments;
    }

    public void setAttachments(ArrayList p_attachments) {
	m_attachments = p_attachments;
    }

    public void addAttachment(String attachPath) {
	m_attachments.add(attachPath);
    }

    public String getAttachment(int index) {
	return (String) m_attachments.get(index);
    }

    public void setAttachment(int index, String newvalue) {
	m_attachments.set(index, newvalue);
    }
}
