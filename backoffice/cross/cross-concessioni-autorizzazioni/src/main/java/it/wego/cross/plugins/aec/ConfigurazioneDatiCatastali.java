package it.wego.cross.plugins.aec;

import java.util.List;

public class ConfigurazioneDatiCatastali {

    private List<String> codiceDichiarazione;
    private List<DatoCatastale> datiCatastali;

    public List<String> getCodiceDichiarazione() {
        return codiceDichiarazione;
    }

    public void setCodiceDichiarazione(List<String> codiceDichiarazione) {
        this.codiceDichiarazione = codiceDichiarazione;
    }

    public List<DatoCatastale> getDatiCatastali() {
        return datiCatastali;
    }

    public void setDatiCatastali(List<DatoCatastale> datiCatastali) {
        this.datiCatastali = datiCatastali;
    }
}
