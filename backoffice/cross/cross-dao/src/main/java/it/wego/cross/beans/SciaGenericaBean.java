/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.beans;

/**
 *
 * @author giuseppe
 */
public class SciaGenericaBean {

    private String oggetto;
    private String ente;
    private String sedeEnte;
    private String pecEnte;
    private String onere;
    private String importo;
    private String codEnte;
    private String idPratica;
    private String contatore;
    private int codiceScia;
    private String codiceSciaString;
    public static final String OGGETTO_NOME = "dic002";
    public static final String ENTE_NOME = "dic005";
    public static final String SEDE_NOME = "dic006";
    public static final String PEC_NOME = "dic007";
    public static final String ONERE_NOME = "dic013";
    public static final String IMPORTO_NOME = "dic014";

    public SciaGenericaBean() {
    }

    public String getEnte() {
        return ente;
    }

    public void setEnte(String ente) {
        this.ente = ente;
    }

    public String getImporto() {
        return importo;
    }

    public void setImporto(String importo) {
        this.importo = importo;
    }

    public String getOggetto() {
        return oggetto;
    }

    public void setOggetto(String oggetto) {
        this.oggetto = oggetto;
    }

    public String getOnere() {
        return onere;
    }

    public void setOnere(String onere) {
        this.onere = onere;
    }

    public String getPecEnte() {
        return pecEnte;
    }

    public void setPecEnte(String pecEnte) {
        this.pecEnte = pecEnte;
    }

    public String getSedeEnte() {
        return sedeEnte;
    }

    public void setSedeEnte(String sedeEnte) {
        this.sedeEnte = sedeEnte;
    }

    public String getCodEnte() {
        return codEnte;
    }

    public void setCodEnte(String codEnte) {
        this.codEnte = codEnte;
    }

    public String getIdPratica() {
        return idPratica;
    }

    public void setIdPratica(String idPratica) {
        this.idPratica = idPratica;
    }

    public String getContatore() {
        return contatore;
    }

    public void setContatore(String contatore) {
        this.contatore = contatore;
    }

    public int getCodiceScia() {
        return codiceScia;
    }

    public void setCodiceScia(int codiceScia) {
        this.codiceScia = codiceScia;
    }

    public String getCodiceSciaString() {
        return codiceSciaString;
    }

    public void setCodiceSciaString(String codiceSciaString) {
        this.codiceSciaString = codiceSciaString;
    }

    public String getCodAutoincrement() {
        int length = contatore.length();
        //lunghezza campo cod_proc - identificativo scia (_) - lunghezza contatore
        int zeros = 8 - 1 - length;
        StringBuilder code = new StringBuilder();
//        code.append("_");
        for (int i = 0; i < zeros; i++) {
            code.append("0");
        }
        code.append(contatore);
        return code.toString();
    }

    public String getCodProc() {
        return "_" + getCodiceSciaString();
    }
}
