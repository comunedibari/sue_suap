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
 * http://www.osor.eu/eupl
 * 
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
**/
package it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author riccardob
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

public class OneriBean {
	
	private Log logger = LogFactory.getLog(this.getClass());
	/**
	 * <code>codice</code> Utilizzato per identificare un onere 
	 */
	private String codice;
	
	private String descrizione;
	private double importo;
	/**
	 * <code>oneriFormula</code> contiene le formula in python
	 */
	private String oneriFormula;
	private List figli;
	/**
	 * <code>campoFormula</code> Utilizzato per tenere le informazioni 
	 * sul campo per ottenere la formula in python
	 */
	private String campoFormula;
	public String getValoreFormula() {
		return valoreFormula;
	}
	public void setValoreFormula(String valoreFormula) {
		this.valoreFormula = valoreFormula;
	}
	private String codiceAntenato;
	private String descrizioneAntenato;
	private DefinizioneCampoFormula definizione;
	private String valoreFormula;
    private String codDestinatario;
    private String desDestinatario;
    private String nota;
    private List altriOneri;
	
	
	/**
     * @return Returns the nota.
     */
    public String getNota() {
        return nota;
    }
    /**
     * @param nota The nota to set.
     */
    public void setNota(String nota) {
        this.nota = nota;
    }
    /**
	 * @return Returns the figli.
	 */
	public List getFigli() {
		return figli;
	}
	/**
	 * @param figli The figli to set.
	 */
	public void setFigli(List figli) {
		this.figli = figli;
	}
	/**
	 * @return Returns the oneriFormula.
	 */
	public String getOneriFormula() {
		return oneriFormula;
	}
	/**
	 * @param oneriFormula The oneriFormula to set.
	 */
	public void setOneriFormula(String oneriFormula) {
		this.oneriFormula = oneriFormula;
	}
	public OneriBean(){
		codice="";
		descrizione="";
		importo=0D;
		oneriFormula="";
		figli = new ArrayList();
		campoFormula="";
		codiceAntenato="";
		descrizioneAntenato="";
		definizione = new DefinizioneCampoFormula();
        altriOneri = new ArrayList();
	}

	public String toString(){
		String answer="";
		
			answer += "\ncodice= " + codice; 
			answer += "\ndescrizione= " + descrizione; 
			answer += "\nimporto= " + importo; 
			answer += "\noneriFormula= " +  oneriFormula; 
			answer += "\ncampoFormula= "+ campoFormula; 
			answer += "\ncodiceAntenato= "+ codiceAntenato; 
			answer += "\ndescrizioneAntenato= "+ descrizioneAntenato; 
		return answer ;
	
	}
	
	
	/**
	 * @return Returns the codice.
	 */
	public String getCodice() {
		return codice;
	}
	/**
	 * @param codice The codice to set.
	 */
	public void setCodice(String codice) {
		 if (!(null==codice)){	
			this.codice = codice;
		 }else{
		 	this.codice="";
		 }	
		
	}
	/**
	 * @return Returns the descrizione.
	 */
	public String getDescrizione() {
			return descrizione;
	}
	/**
	 * @param descrizione The descrizione to set.
	 */
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	/**
	 * @return Returns the importo.
	 */
	public double getImporto() {
	    NumberFormat nf = NumberFormat.getInstance();
	    nf.setMaximumFractionDigits(2);
		try {
//            return Double.parseDouble(nf.format(importo));
            return (nf.parse(nf.format(importo))).doubleValue();
        } catch (NumberFormatException e) {
            logger.error(e);
            return importo;
        }catch (ParseException e1) {
            e1.printStackTrace();
            return importo;
        }
	}
	/**
	 * @param importo The importo to set.
	 */
	public void setImporto(double importo) {
		this.importo = importo;
	}
	/**
	 * @return Returns the campoFormula.
	 */
	public String getCampoFormula() {		
		return campoFormula;
	}
	/**
	 * @param campoFormula The campoFormula to set.
	 */
	public void setCampoFormula(String campoFormula) {
		if (!(null==campoFormula)){	
			this.campoFormula = campoFormula;
		}else{
			this.campoFormula="";
		}
		
	}
	public String getCodiceAntenato() {
		return codiceAntenato;
	}
	public void setCodiceAntenato(String codiceAntenato) {
		if (!(null==codiceAntenato)){
			this.codiceAntenato = codiceAntenato;
		}else{
			this.codiceAntenato="";
		}
	}
	public String getDescrizioneAntenato() {
		return descrizioneAntenato;
	}
	public void setDescrizioneAntenato(String descrizioneAntenato) {
		if (!(null==descrizioneAntenato)){
			this.descrizioneAntenato = descrizioneAntenato;
		}else{
			this.descrizione="";
		}
		
	}
		
	public DefinizioneCampoFormula getDefinizione() {
		return definizione;
	}
	public void setDefinizione(DefinizioneCampoFormula definizione) {
		this.definizione = definizione;
	}
	
	public void addFigli(OneriBean bean){
	    logger.debug("inside addFigli: bean="+bean);
        try {
            figli.add(bean);
        } catch (Exception e) {
            logger.error(e);
        }       
	}

    public void addAltriOneri(OneriBean bean){
        logger.debug("inside addFigli: bean="+bean);
        try {
            altriOneri.add(bean);
        } catch (Exception e) {
            logger.error(e);
        }       
    }
    
    /**
     * @return Returns the codDestinatario.
     */
    public String getCodDestinatario() {
        return codDestinatario;
    }
    /**
     * @param codDestinatario The codDestinatario to set.
     */
    public void setCodDestinatario(String codDestinatario) {
        this.codDestinatario = codDestinatario;
    }
    /**
     * @return Returns the desDestinatario.
     */
    public String getDesDestinatario() {
        return desDestinatario;
    }
    /**
     * @param desDestinatario The desDestinatario to set.
     */
    public void setDesDestinatario(String desDestinatario) {
        this.desDestinatario = desDestinatario;
    }
    public List getAltriOneri() {
        return altriOneri;
    }
    public void setAltriOneri(List altriOneri) {
        this.altriOneri = altriOneri;
    }
}
