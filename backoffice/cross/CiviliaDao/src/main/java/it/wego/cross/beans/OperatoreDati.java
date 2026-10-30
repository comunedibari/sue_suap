/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.beans;

/**
 *
 * @author CS
 */
public class OperatoreDati {

    private String id;
    private String nome;
    private String cognome;
    private String mail;
    private String procedimenti;
    private String ruolo;

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getProcedimenti() {
        return procedimenti;
    }

    public void setProcedimenti(String procedimenti) {
        this.procedimenti = procedimenti;
    }
}
