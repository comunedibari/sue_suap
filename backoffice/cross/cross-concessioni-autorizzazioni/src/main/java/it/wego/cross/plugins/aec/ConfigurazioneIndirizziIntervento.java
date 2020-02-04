package it.wego.cross.plugins.aec;

import java.util.List;

public class ConfigurazioneIndirizziIntervento {

    private List<String> codiceDichiarazione;
    private List<IndirizzoIntervento> indirizzoIntervento;

    public List<String> getCodiceDichiarazione() {
        return codiceDichiarazione;
    }

    public void setCodiceDichiarazione(List<String> codiceDichiarazione) {
        this.codiceDichiarazione = codiceDichiarazione;
    }

    public List<IndirizzoIntervento> getIndirizzoIntervento() {
        return indirizzoIntervento;
    }

    public void setIndirizzoIntervento(List<IndirizzoIntervento> indirizzoIntervento) {
        this.indirizzoIntervento = indirizzoIntervento;
    }
}
