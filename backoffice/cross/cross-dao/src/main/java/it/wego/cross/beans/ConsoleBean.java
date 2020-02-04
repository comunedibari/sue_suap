/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.beans;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 *
 * @author Giuseppe
 */
public class ConsoleBean {

    private Long emailNonInviate;
    private Long praticheNonProtocollate;

    public Long getEmailNonInviate() {
        return emailNonInviate;
    }

    public void setEmailNonInviate(Long emailNonInviate) {
        this.emailNonInviate = emailNonInviate;
    }

    public Long getPraticheNonProtocollate() {
        return praticheNonProtocollate;
    }

    public void setPraticheNonProtocollate(Long praticheNonProtocollate) {
        this.praticheNonProtocollate = praticheNonProtocollate;
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
        return gson.toJson(this);
    }
}
