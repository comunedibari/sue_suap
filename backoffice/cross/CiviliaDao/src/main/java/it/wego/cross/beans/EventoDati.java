/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.beans;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author CS
 */
public class EventoDati {
    private String idPratica;
    private String mittente;
    private String descrizione;
    private String destinatari;
    private String dataAvvio;
    private String comune;
    private String aoo;
    private String numero;
    private String dataProtocollazione;
    private String versoProtocollazione;
    private String allegato;
    private String note;
    private String tipo;
    private String dataEvento;
    private String nome;
    private String url;
    private String codSportello;

    public String getCodSportello() {
        return codSportello;
    }

    public void setCodSportello(String codSportello) {
        this.codSportello = codSportello;
    }

    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDestinatari() {
        return destinatari;
    }

    public void setDestinatari(String destinatari) {
        this.destinatari = destinatari;
    }


    public String getAllegato() {
        return allegato;
    }

    public void setAllegato(String allegato) {
        this.allegato = allegato;
    }

    public String getAoo() {
        return aoo;
    }

    public void setAoo(String aoo) {
        this.aoo = aoo;
    }

    public String getComune() {
        return comune;
    }

    public void setComune(String comune) {
        this.comune = comune;
    }

    public String getDataAvvio() {
        return dataAvvio;
    }

    public void setDataAvvio(String dataAvvio) {
        this.dataAvvio = dataAvvio;
    }

    public String getDataEvento() {
        return dataEvento;
    }

    public void setDataEvento(String dataEvento) {
        this.dataEvento = dataEvento;
    }

    public String getDataProtocollazione() {
        return dataProtocollazione;
    }

    public void setDataProtocollazione(String dataProtocollazione) {
        this.dataProtocollazione = dataProtocollazione;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }


    public String getIdPratica() {
        return idPratica;
    }

    public void setIdPratica(String idPratica) {
        this.idPratica = idPratica;
    }

    public String getMittente() {
        return mittente;
    }

    public void setMittente(String mittente) {
        this.mittente = mittente;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getVersoProtocollazione() {
        return versoProtocollazione;
    }

    public void setVersoProtocollazione(String versoProtocollazione) {
        this.versoProtocollazione = versoProtocollazione;
    }
    
    
}
