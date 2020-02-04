/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti;

import java.io.Serializable;
import java.sql.Blob;

/**
 *
 * @author piergiorgio
 */
public class DocumentoFisicoBean implements Serializable {

    private static final long serialVersionUID = 8351701101364860145L;
    private String nomeFile;
    private String contentType;
    private Blob documentoFisico;

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Blob getDocumentoFisico() {
        return documentoFisico;
    }

    public void setDocumentoFisico(Blob documentoFisico) {
        this.documentoFisico = documentoFisico;
    }

    public String getNomeFile() {
        return nomeFile;
    }

    public void setNomeFile(String nomeFile) {
        this.nomeFile = nomeFile;
    }
}
