/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Gabriele
 */
@Embeddable
public class PraticheAssociatePK implements Serializable {

    @Basic(optional = false)
    @Column(name = "id_pratica_a")
    private int idPraticaA;
    @Basic(optional = false)
    @Column(name = "id_pratica_b")
    private int idPraticaB;

    public PraticheAssociatePK() {
    }

    public PraticheAssociatePK(int idPraticaA, int idPraticaB) {
        this.idPraticaA = idPraticaA;
        this.idPraticaB = idPraticaB;
    }

    public int getIdPraticaA() {
        return idPraticaA;
    }

    public void setIdPraticaA(int idPraticaA) {
        this.idPraticaA = idPraticaA;
    }

    public int getIdPraticaB() {
        return idPraticaB;
    }

    public void setIdPraticaB(int idPraticaB) {
        this.idPraticaB = idPraticaB;
    }

}
