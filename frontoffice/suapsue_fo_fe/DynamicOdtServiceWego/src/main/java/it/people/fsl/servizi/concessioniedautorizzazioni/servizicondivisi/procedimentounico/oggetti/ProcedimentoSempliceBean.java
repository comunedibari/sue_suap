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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ProcedimentoSempliceBean 
{
  public static final int PROCEDIMENTO_NONAUTOCERTIFICABILE = 0;
  public static final int PROCEDIMENTO_AUTOCERTIFICABILE = 1;
  public static final int PROCEDIMENTO_DIA_COMUNICAZIONE = 2;
  private String nome;
  private String terminiEvasione;
  private String ente;
  private List interventi;
  private String titoloIntervento;
  private String codice;
  private int tipo;
  private double oneriDovuti;
  private Log logger = LogFactory.getLog(this.getClass());
  private List oneriPadri;
  private String codSportello;
  private String desSportello;
  private String flg_bollo;


    /**
 * @return Returns the flg_bollo.
 */
public String getFlg_bollo() {
    return flg_bollo;
}
/**
 * @param flg_bollo The flg_bollo to set.
 */
public void setFlg_bollo(String flg_bollo) {
    this.flg_bollo = flg_bollo;
}
    public ProcedimentoSempliceBean() {

        interventi = new ArrayList();
    }
  public void setNome(String nome)
  {
    this.nome = nome;
  }


  public String getNome()
  {
    return nome;
  }


  public void setTerminiEvasione(String terminiEvasione)
  {
    this.terminiEvasione = terminiEvasione;
  }


  public String getTerminiEvasione()
  {
    return terminiEvasione;
  }


  public void setEnte(String ente)
  {
    this.ente = ente;
  }


  public String getEnte()
  {
    return ente;
  }


  public void setTitoloIntervento(String titoloIntervento)
  {
    this.titoloIntervento = titoloIntervento;
  }


  public String getTitoloIntervento()
  {
    return titoloIntervento;
  }


  public void setCodice(String codice)
  {
    this.codice = codice;
  }


  public String getCodice()
  {
    return codice;
  }


  public void setTipo(int tipo)
  {
    this.tipo = tipo;
  }


  public int getTipo()
  {
    return tipo;
  }
  
  public void setOneriDovuti(double oneriDovuti)
  {
    this.oneriDovuti = oneriDovuti;
  }


  public double getOneriDovuti()
  {
    return oneriDovuti;
  }
  
	/**
	 * @return Returns the interventi.
	 */
	public List getInterventi() {
	    return interventi;
	}
	/**
	 * @param interventi The interventi to set.
	 */
	public void setInterventi(List interventi) {
	    this.interventi = interventi;
	}
	
	public void addInterventi(InterventoBean bean){
		try {
		    interventi.add(bean);
		} catch (Exception e) {
		    logger.error(e);
		}       
	}

	public List getOneriPadri() {
	    return oneriPadri;
	}
	
	public void setOneriPadri(List oneriPadri) {
	    this.oneriPadri = oneriPadri;
	}
	
	public void addOneriPadri(OneriBean bean){
		try {
		    oneriPadri.add(bean);
		} catch (Exception e) {
		    logger.error(e);
		}       
	}
    /**
     * @return Returns the codSportello.
     */
    public String getCodSportello() {
        return codSportello;
    }
    /**
     * @param codSportello The codSportello to set.
     */
    public void setCodSportello(String codSportello) {
        this.codSportello = codSportello;
    }
    /**
     * @return Returns the desSportello.
     */
    public String getDesSportello() {
        return desSportello;
    }
    /**
     * @param desSportello The desSportello to set.
     */
    public void setDesSportello(String desSportello) {
        this.desSportello = desSportello;
    }
}
