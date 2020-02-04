/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.reporter.util;

/**
 *
 * @author Piergiorgio
 */
public class QueryModel {

    public String[] figlio;
    public String query;
    public String padre;

    public String getPadre() {
        return padre;
    }

    public void setPadre(String padre) {
        this.padre = padre;
    }
    public String[] getFiglio() {
        return figlio;
    }

    public void setFiglio(String[] figlio) {
        this.figlio = figlio;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
