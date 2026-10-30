/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author giuseppe
 */
public class AllegatoDTO implements Serializable {

    private Integer idAllegato;
    private Integer idPratica;
    @NotNull(message = "{error.allegato.descrizione}")
    private String descrizione;
    private String nomeFile;
    private String nomeFileB64;
    private String tipoFile;
    private String tipoFileCode;
    private String pathFile;
    private String pathFileB64;
    private String idFileEsterno;
    private String riepilogoPratica;
    private byte[] fileContent;
    private Boolean modelloPratica;
    private Boolean daAggiungere;
    private String allegaAllaMail;
    @NotNull(message = "{error.allegato.file}")
    private MultipartFile file;
    private boolean principale;
    
    

    public boolean isPrincipale() {
		return principale;
	}

	public void setPrincipale(boolean principale) {
		this.principale = principale;
	}

	public String getTipoFileCode() {
        return tipoFileCode;
    }

    public void setTipoFileCode(String tipoFileCode) {
        this.tipoFileCode = tipoFileCode;
    }

    public Integer getIdPratica() {
        return idPratica;
    }

    public void setIdPratica(Integer idPratica) {
        this.idPratica = idPratica;
    }

    public Integer getIdAllegato() {
        return idAllegato;
    }

    public void setIdAllegato(Integer idAllegato) {
        this.idAllegato = idAllegato;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getNomeFile() {
        return nomeFile;
    }

    public void setNomeFile(String nomeFile) {
        this.nomeFile = nomeFile;
    }

    public String getTipoFile() {
        return tipoFile;
    }

    public void setTipoFile(String tipoFile) {
        this.tipoFile = tipoFile;
    }

    public byte[] getFileContent() {
        return fileContent;
    }

    public void setFileContent(byte[] fileContent) {
        this.fileContent = fileContent;
    }

    public String getPathFile() {
        return pathFile;
    }

    public void setPathFile(String pathFile) {
        this.pathFile = pathFile;
    }

    public String getIdFileEsterno() {
        return idFileEsterno;
    }

    public void setIdFileEsterno(String idFileEsterno) {
        this.idFileEsterno = idFileEsterno;
    }

    public Boolean getModelloPratica() {
        return modelloPratica;
    }

    public void setModelloPratica(Boolean modelloPratica) {
        this.modelloPratica = modelloPratica;
    }

    public Boolean getDaAggiungere() {
        return daAggiungere;
    }

    public void setDaAggiungere(Boolean daAggiungere) {
        this.daAggiungere = daAggiungere;
    }

    public String getAllegaAllaMail() {
        return allegaAllaMail;
    }

    public void setAllegaAllaMail(String allegaAllaMail) {
        this.allegaAllaMail = allegaAllaMail;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getNomeFileB64() {
        return nomeFileB64;
    }

    public void setNomeFileB64(String nomeFileB64) {
        this.nomeFileB64 = nomeFileB64;
    }

    public String getPathFileB64() {
        return pathFileB64;
    }

    public void setPathFileB64(String pathFileB64) {
        this.pathFileB64 = pathFileB64;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAllegato != null ? idAllegato.hashCode() : 0);
        hash += (descrizione != null ? descrizione.hashCode() : 0);
        hash += (nomeFile != null ? nomeFile.hashCode() : 0);
        hash += (tipoFile != null ? tipoFile.hashCode() : 0);
        return hash;
    }

    public String getRiepilogoPratica() {
        return riepilogoPratica;
    }

    public void setRiepilogoPratica(String riepilogoPratica) {
        this.riepilogoPratica = riepilogoPratica;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof AllegatoDTO)) {
            return false;
        }
        AllegatoDTO other = (AllegatoDTO) object;
        if ((this.idAllegato == null && other.idAllegato != null) || (this.idAllegato != null && !this.idAllegato.equals(other.idAllegato))) {
            return false;
        }
        if ((this.descrizione == null && other.descrizione != null) || (this.descrizione != null && !this.descrizione.equals(other.descrizione))) {
            return false;
        }
        if ((this.nomeFile == null && other.nomeFile != null) || (this.nomeFile != null && !this.nomeFile.equals(other.nomeFile))) {
            return false;
        }
        if ((this.tipoFile == null && other.tipoFile != null) || (this.tipoFile != null && !this.tipoFile.equals(other.tipoFile))) {
            return false;
        }
        return true;
    }
}
