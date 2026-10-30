/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto.dozer;

import java.util.Date;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author Gabriele
 */
public class ProtocolloDTO {

    private String numero;
    private String codFascicolo;
    private String codRegistro;
    private Integer annoRiferimento;
    private Date dataProtocollazione;

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCodFascicolo() {
        return codFascicolo;
    }

    public void setCodFascicolo(String codFascicolo) {
        this.codFascicolo = codFascicolo;
    }

    public String getCodRegistro() {
        return codRegistro;
    }

    public void setCodRegistro(String codRegistro) {
        this.codRegistro = codRegistro;
    }

    public Integer getAnnoRiferimento() {
        return annoRiferimento;
    }

    public void setAnnoRiferimento(Integer annoRiferimento) {
        this.annoRiferimento = annoRiferimento;
    }

    public Date getDataProtocollazione() {
        return dataProtocollazione;
    }

    public void setDataProtocollazione(Date dataProtocollazione) {
        this.dataProtocollazione = dataProtocollazione;
    }

    public String getSegnatura() {
        String[] tokens = new String[]{codRegistro, annoRiferimento != null ? String.valueOf(annoRiferimento) : "", numero != null ? String.valueOf(numero) : ""};
        tokens[0] = StringUtils.isEmpty(tokens[0]) ? "" : tokens[0];
        tokens[1] = StringUtils.isEmpty(tokens[1]) ? "" : tokens[1];
        tokens[2] = StringUtils.isEmpty(tokens[2]) ? "" : tokens[2];
        return StringUtils.join(tokens, "/");
    }
}
