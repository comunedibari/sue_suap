/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import it.wego.cross.dto.research.table.Address;
import it.wego.cross.dto.research.table.CadastralData;
import it.wego.cross.dto.research.table.ComplexId;
import it.wego.cross.dto.research.table.Event;
import it.wego.cross.dto.research.table.Note;
import it.wego.cross.dto.research.table.Receiving;
import it.wego.cross.dto.research.table.Subject;

/**
 *
 * @author CS
 */
public class PraticaNuova extends AbstractDTO implements Serializable {

    private String idPratica;
    private Date dataRicezione;
    private String descPratica;
    private String tipoPratica;
    private Integer codiceUtente;
    private String protocollo;
    private String fascicolo;
    private String ente;
    private String inCarico;
    private String oggettoPratica;
    private String statoPratica;
    private Date dataScadenza;
    private String comune;
    private String richiedente;
    private String richiedenteDaXml;
    private String statoEmail;
    private Integer idStatoEmail;
    private String codStatoEmail;
    private String identificativoEsterno;
    private String identificativoPratica;
    private Boolean adminOnProcEnte;
    private Boolean isSameUser;
    private String integrazione;
    
    private ComplexId complexId;
    private String type;
    private Integer number;
    private Integer year;
    private Receiving receiving;
    private List<String> operations;
    private List<Address> addresses;
    private List<CadastralData> cadastralDataList;
    private Event lastInternalEvent;
    private Event lastExternalEvent;
    private Note lastNote;
    private Long days;
    private Date dataUltimoEventoEsterno;
    private UtenteDTO utente;
    
    public Date getDataRicezione() {
        return dataRicezione;
    }

    public void setDataRicezione(Date dataRicezione) {
        this.dataRicezione = dataRicezione;
    }

    public String getDescPratica() {
        return descPratica;
    }

    public void setDescPratica(String descPratica) {
        this.descPratica = descPratica;
    }

    public String getIdPratica() {
        return idPratica;
    }

    public void setIdPratica(String idPratica) {
        this.idPratica = idPratica;
    }

    public String getTipoPratica() {
        return tipoPratica;
    }

    public void setTipoPratica(String tipoPratica) {
        this.tipoPratica = tipoPratica;
    }

    public Integer getCodiceUtente() {
        return codiceUtente;
    }

    public void setCodiceUtente(Integer codiceUtente) {
        this.codiceUtente = codiceUtente;
    }

    @Deprecated
    public String getProtocollo() {
        return protocollo;
    }

    @Deprecated
    public void setProtocollo(String protocollo) {
        this.protocollo = protocollo;
    }

    public String getEnte() {
        return ente;
    }

    public void setEnte(String ente) {
        this.ente = ente;
    }

    public String getInCarico() {
        return inCarico;
    }

    public void setInCarico(String inCarico) {
        this.inCarico = inCarico;
    }

    public String getOggettoPratica() {
        return oggettoPratica;
    }

    public void setOggettoPratica(String oggettoPratica) {
        this.oggettoPratica = oggettoPratica;
    }

    public String getStatoPratica() {
        return statoPratica;
    }

    public void setStatoPratica(String statoPratica) {
        this.statoPratica = statoPratica;
    }

    public Date getDataScadenza() {
        return dataScadenza;
    }

    public void setDataScadenza(Date dataScadenza) {
        this.dataScadenza = dataScadenza;
    }

    public String getComune() {
        return comune;
    }

    public void setComune(String comune) {
        this.comune = comune;
    }

    public String getRichiedente() {
        return richiedente;
    }

    public void setRichiedente(String richiedente) {
        this.richiedente = richiedente;
    }

    public String getStatoEmail() {
        return statoEmail;
    }

    public void setStatoEmail(String statoEmail) {
        this.statoEmail = statoEmail;
    }

    public Integer getIdStatoEmail() {
        return idStatoEmail;
    }

    public void setIdStatoEmail(Integer idStatoEmail) {
        this.idStatoEmail = idStatoEmail;
    }

    public String getCodStatoEmail() {
        return codStatoEmail;
    }

    public void setCodStatoEmail(String codStatoEmail) {
        this.codStatoEmail = codStatoEmail;
    }

    public String getIdentificativoEsterno() {
        return identificativoEsterno;
    }

    public void setIdentificativoEsterno(String identificativoEsterno) {
        this.identificativoEsterno = identificativoEsterno;
    }

    public String getIdentificativoPratica() {
        return identificativoPratica;
    }

    public void setIdentificativoPratica(String identificativoPratica) {
        this.identificativoPratica = identificativoPratica;
    }

    public String getFascicolo() {
        return fascicolo;
    }

    public void setFascicolo(String fascicolo) {
        this.fascicolo = fascicolo;
    }

    public Boolean getAdminOnProcEnte() {
        return adminOnProcEnte;
    }

    public void setAdminOnProcEnte(Boolean adminOnProcEnte) {
        this.adminOnProcEnte = adminOnProcEnte;
    }

    public Boolean getIsSameUser() {
        return isSameUser;
    }

    public void setIsSameUser(Boolean isSameUser) {
        this.isSameUser = isSameUser;
    }

    public String getRichiedenteDaXml() {
        return richiedenteDaXml;
    }

    public void setRichiedenteDaXml(String richiedenteDaXml) {
        this.richiedenteDaXml = richiedenteDaXml;
    }

	public String getIntegrazione() {
		return integrazione;
	}

	public void setIntegrazione(String integrazione) {
		this.integrazione = integrazione;
	}

	public ComplexId getComplexId() {
		return complexId;
	}

	public void setComplexId(ComplexId complexId) {
		this.complexId = complexId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Receiving getReceiving() {
		return receiving;
	}

	public void setReceiving(Receiving receiving) {
		this.receiving = receiving;
	}

	public List<String> getOperations() {
		return operations;
	}

	public void setOperations(List<String> operations) {
		this.operations = operations;
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	public List<CadastralData> getCadastralDataList() {
		return cadastralDataList;
	}

	public void setCadastralDataList(List<CadastralData> cadastralDataList) {
		this.cadastralDataList = cadastralDataList;
	}

	public Event getLastInternalEvent() {
		return lastInternalEvent;
	}

	public void setLastInternalEvent(Event lastInternalEvent) {
		this.lastInternalEvent = lastInternalEvent;
	}

	public Event getLastExternalEvent() {
		return lastExternalEvent;
	}

	public void setLastExternalEvent(Event lastExternalEvent) {
		this.lastExternalEvent = lastExternalEvent;
	}

	public Note getLastNote() {
		return lastNote;
	}

	public void setLastNote(Note lastNote) {
		this.lastNote = lastNote;
	}

	public Long getDays() {
		return days;
	}

	public void setDays(Long days) {
		this.days = days;
	}

	public Date getDataUltimoEventoEsterno() {
		return dataUltimoEventoEsterno;
	}

	public void setDataUltimoEventoEsterno(Date dataUltimoEventoEsterno) {
		this.dataUltimoEventoEsterno = dataUltimoEventoEsterno;
	}

	public UtenteDTO getUtente() {
		return utente;
	}

	public void setUtente(UtenteDTO utente) {
		this.utente = utente;
	}
}
