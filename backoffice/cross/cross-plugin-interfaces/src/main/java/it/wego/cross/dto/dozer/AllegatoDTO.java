/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto.dozer;

import java.util.Date;

import it.wego.cross.entity.Utente;

/**
 *
 * @author Giuseppe
 */
public class AllegatoDTO {

    private Integer id;
    private String descrizione;
    private String nomeFile;
    private String tipoFile;
    private String pathFile;
    private String idFileEsterno;
    private byte[] file;
    private Date dataEvento;
    private String descrizioneEvento;
    private String provenienza;
    private Integer idPraticaEvento;
    private String protocollo;
    private Date dataProtocollo;
    private Utente operatore;
    private String principale;
    private Long dimensione;

    public String getProvenienza() {
		return provenienza;
	}

	public void setProvenienza(String provenienza) {
		this.provenienza = provenienza;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getNomeFile() {
        return nomeFile;
    }

    public void setNomeFile(String nomeFile) {
        this.nomeFile = nomeFile;
    }

    public String getTipoFile() {
        return tipoFile;
    }

    public void setTipoFile(String tipoFile) {
        this.tipoFile = tipoFile;
    }

    public String getPathFile() {
        return pathFile;
    }

    public void setPathFile(String pathFile) {
        this.pathFile = pathFile;
    }

    public String getIdFileEsterno() {
        return idFileEsterno;
    }

    public void setIdFileEsterno(String idFileEsterno) {
        this.idFileEsterno = idFileEsterno;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

	public Date getDataEvento() {
		return dataEvento;
	}

	public void setDataEvento(Date dataEvento) {
		this.dataEvento = dataEvento;
	}

	public String getDescrizioneEvento() {
		return descrizioneEvento;
	}

	public void setDescrizioneEvento(String descrizioneEvento) {
		this.descrizioneEvento = descrizioneEvento;
	}


	public Integer getIdPraticaEvento() {
		return idPraticaEvento;
	}

	public void setIdPraticaEvento(Integer idPraticaEvento) {
		this.idPraticaEvento = idPraticaEvento;
	}

	public String getPrincipale() {
		return principale;
	}

	public String getProtocollo() {
		return protocollo;
	}

	public void setProtocollo(String protocollo) {
		this.protocollo = protocollo;
	}

	public Date getDataProtocollo() {
		return dataProtocollo;
	}

	public void setDataProtocollo(Date dataProtocollo) {
		this.dataProtocollo = dataProtocollo;
	}

	public Utente getOperatore() {
		return operatore;
	}

	public void setOperatore(Utente operatore) {
		this.operatore = operatore;
	}

	public String isPrincipale() {
		return principale;
	}

	public void setPrincipale(String principale) {
		this.principale = principale;
	}

	public Long getDimensione() {
		return dimensione;
	}

	public void setDimensione(Long dimensione) {
		this.dimensione = dimensione;
	}
}
