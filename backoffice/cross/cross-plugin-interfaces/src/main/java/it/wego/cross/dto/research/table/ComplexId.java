package it.wego.cross.dto.research.table;

import java.io.Serializable;

import it.wego.cross.dto.AbstractDTO;

/**
 *
 * @author CS
 */
public class ComplexId extends AbstractDTO implements Serializable {

    private Integer idPratica;
    private String fascicolo;
    private String identificativoPratica;
    
	public Integer getIdPratica() {
		return idPratica;
	}
	public void setIdPratica(Integer idPratica) {
		this.idPratica = idPratica;
	}
	public String getFascicolo() {
		return fascicolo;
	}
	public void setFascicolo(String fascicolo) {
		this.fascicolo = fascicolo;
	}
	public String getIdentificativoPratica() {
		return identificativoPratica;
	}
	public void setIdentificativoPratica(String identificativoPratica) {
		this.identificativoPratica = identificativoPratica;
	} 
}
