/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.plugins.protocollo.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author gabriele
 */
public class FilterProtocollo {

    private String numeroProtocollo;
    private String anno;
    private String tipoDocumento;
    private Date dataDocumentoDa;
    private Date dataDocumentoA;
    private Integer pagina = 1;

    public Integer getPagina() {
        return pagina;
    }

    public void setPagina(Integer pagina) {
        this.pagina = pagina;
    }

    public String getNumeroProtocollo() {
        return numeroProtocollo;
    }

    public void setNumeroProtocollo(String numeroProtocollo) {
        this.numeroProtocollo = numeroProtocollo;
    }

    public String getAnno() {
        return anno;
    }

    public void setAnno(String anno) {
        this.anno = anno;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public Date getDataDocumentoDa() {
        return dataDocumentoDa;
    }

    public void setDataDocumentoDa(Date dataDocumentoDa) {
        this.dataDocumentoDa = dataDocumentoDa;
    }

    public Date getDataDocumentoA() {
        return dataDocumentoA;
    }

    public void setDataDocumentoA(Date dataDocumentoA) {
        this.dataDocumentoA = dataDocumentoA;
    }
}
