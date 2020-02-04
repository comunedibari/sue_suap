/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.entity;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author Gabriele
 */
@Entity
@Table(name = "pratica_pratica")
public class PraticheAssociate implements Serializable {

    @EmbeddedId
    protected PraticheAssociatePK praticheAssociatePK;

    public PraticheAssociatePK getPraticheAssociatePK() {
        return praticheAssociatePK;
    }

    public void setPraticheAssociatePK(PraticheAssociatePK praticheAssociatePK) {
        this.praticheAssociatePK = praticheAssociatePK;
    }

    public PraticheAssociate() {
    }

}
