/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto.dozer;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Gabriele
 */
public class UtenteDTO {

    private Integer idUtente;
    private String nome;
    private String cognome;
    private String codiceFiscale;
    private String username;
    private String email;
    private String telefono;
    private String note;
    private Date dataAttivazione;
    private String status;
    private String superuser;
    private List<UtenteRuoloEnteDTO> utenteRuoloEnteList;
    private String denominazione;
    
    public UtenteDTO(){
        denominazione = nome + " " + cognome;
    }
    
    public Integer getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(Integer idUtente) {
        this.idUtente = idUtente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
        this.denominazione = nome + " " + cognome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
         this.denominazione = nome + " " + cognome;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Date getDataAttivazione() {
        return dataAttivazione;
    }

    public void setDataAttivazione(Date dataAttivazione) {
        this.dataAttivazione = dataAttivazione;
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

    public List<UtenteRuoloEnteDTO> getUtenteRuoloEnteList() {
        return utenteRuoloEnteList;
    }

    public void setUtenteRuoloEnteList(List<UtenteRuoloEnteDTO> utenteRuoloEnteList) {
        this.utenteRuoloEnteList = utenteRuoloEnteList;
    }

    public String getDenominazione() {
        return denominazione;
    }
    
}
