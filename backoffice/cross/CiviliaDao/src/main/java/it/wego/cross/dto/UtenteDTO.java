/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("user")
@Scope("session")
public class UtenteDTO extends AbstractDTO implements Serializable {

    private String nominativo;
    @NotNull(message = "{error.UtenteDTO.codiceFiscale}")
    @NotEmpty(message = "{error.UtenteDTO.codiceFiscale}")
    private String codiceFiscale;
    @NotNull(message = "{error.UtenteDTO.username}")
    @NotEmpty(message = "{error.UtenteDTO.username}")
    private String username;
//    @NotNull(message = "{error.UtenteDTO.password}")
//    @NotEmpty(message = "{error.UtenteDTO.password}")
    private String password;
    private Date dataAttivazione;
    private String permessi;
    private Integer codiceUtente;
    private Integer idUtente;
    @NotNull(message = "{error.UtenteDTO.nome}")
    @NotEmpty(message = "{error.UtenteDTO.nome}")
    private String nome;
    @NotNull(message = "{error.UtenteDTO.cognome}")
    @NotEmpty(message = "{error.UtenteDTO.cognome}")
    private String cognome;
    
    @NotNull(message = "{error.UtenteDTO.email}")
    @NotEmpty(message = "{error.UtenteDTO.email}")
    @Email(message = "{error.UtenteDTO.email.valid}")
    private String email;
    
    private String telefono;
    private String note;
    private String status;
    private String superuser;
    
    private String newPassword;
    private String confirmNewPassword;
    
    private String estrazioniUser;
    private String estrazioniCilaTodoUser;
    
    private List<UtenteRuoloEnteDTO> utenteRuoloEnte = new ArrayList<UtenteRuoloEnteDTO>();

    public Integer getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(Integer idUtente) {
        this.idUtente = idUtente;
    }

    public Integer getCodiceUtente() {
        return codiceUtente;
    }

    public void setCodiceUtente(Integer codiceUtente) {
        this.codiceUtente = codiceUtente;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public String getNominativo() {
        return nominativo;
    }

    public void setNominativo(String nominativo) {
        this.nominativo = nominativo;
    }

    public String getPermessi() {
        return permessi;
    }

    public void setPermessi(String permessi) {
        this.permessi = permessi;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDataAttivazione() {
        return dataAttivazione;
    }

    public void setDataAttivazione(Date dataAttivazione) {
        this.dataAttivazione = dataAttivazione;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSuperuser() {
        return superuser;
    }

    public void setSuperuser(String superuser) {
        this.superuser = superuser;
    }

//    public List<RuoloDTO> getRuoli() {
//        return ruoli;
//    }
//
//    public void setRuoli(List<RuoloDTO> ruoli) {
//        this.ruoli = ruoli;
//    }
    
    
    public List<UtenteRuoloEnteDTO> getUtenteRuoloEnte() {
        return utenteRuoloEnte;
    }

    public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmNewPassword() {
		return confirmNewPassword;
	}

	public void setConfirmNewPassword(String confirmNewPassword) {
		this.confirmNewPassword = confirmNewPassword;
	}

	public void setUtenteRuoloEnte(List<UtenteRuoloEnteDTO> utenteRuoloEnte) {
        this.utenteRuoloEnte = utenteRuoloEnte;
    }

	public String getEstrazioniUser() {
		return estrazioniUser;
	}

	public void setEstrazioniUser(String estrazioniUser) {
		this.estrazioniUser = estrazioniUser;
	}

	public String getEstrazioniCilaTodoUser() {
		return estrazioniCilaTodoUser;
	}

	public void setEstrazioniCilaTodoUser(String estrazioniCilaTodoUser) {
		this.estrazioniCilaTodoUser = estrazioniCilaTodoUser;
	}
	
}
