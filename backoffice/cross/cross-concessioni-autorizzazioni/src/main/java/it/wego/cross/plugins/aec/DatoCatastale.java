package it.wego.cross.plugins.aec;

public class DatoCatastale {

    private String dichiarazione;
    private String cod_tipo_sistema_catastale;
    private String sezione;
    private String cod_tipo_unita;
    private String mappale;
    private String foglio;
    private String cod_tipo_particella;
    private String estensione_particella;
    private String subalterno;
    private String comuneCensuario;
    private String codImmobile;
    
    //Aggiunto il 27/06/2016
    private String categoria;

    public String getCodImmobile() {
        return codImmobile;
    }

    public void setCodImmobile(String codImmobile) {
        this.codImmobile = codImmobile;
    }

    public String getDichiarazione() {
        return dichiarazione;
    }

    public void setDichiarazione(String dichiarazione) {
        this.dichiarazione = dichiarazione;
    }

    public String getCod_tipo_sistema_catastale() {
        return cod_tipo_sistema_catastale;
    }

    public void setCod_tipo_sistema_catastale(String cod_tipo_sistema_catastale) {
        this.cod_tipo_sistema_catastale = cod_tipo_sistema_catastale;
    }

    public String getSezione() {
        return sezione;
    }

    public void setSezione(String sezione) {
        this.sezione = sezione;
    }

    public String getCod_tipo_unita() {
        return cod_tipo_unita;
    }

    public void setCod_tipo_unita(String cod_tipo_unita) {
        this.cod_tipo_unita = cod_tipo_unita;
    }

    public String getMappale() {
        return mappale;
    }

    public void setMappale(String mappale) {
        this.mappale = mappale;
    }

    public String getFoglio() {
        return foglio;
    }

    public void setFoglio(String foglio) {
        this.foglio = foglio;
    }

    public String getCod_tipo_particella() {
        return cod_tipo_particella;
    }

    public void setCod_tipo_particella(String cod_tipo_particella) {
        this.cod_tipo_particella = cod_tipo_particella;
    }

    public String getEstensione_particella() {
        return estensione_particella;
    }

    public void setEstensione_particella(String estensione_particella) {
        this.estensione_particella = estensione_particella;
    }

    public String getSubalterno() {
        return subalterno;
    }

    public void setSubalterno(String subalterno) {
        this.subalterno = subalterno;
    }

    public String getComuneCensuario() {
        return comuneCensuario;
    }

    public void setComuneCensuario(String codComuneCensuario) {
        this.comuneCensuario = comuneCensuario;
    }

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
    
    

}
