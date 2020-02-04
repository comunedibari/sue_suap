package it.people.fsl.servizi.praticheOnLine.visura.myPage.oggetti;


public final class TipologiaPraticheSelezionabili {

    private TipologiaPraticheSelezionabili(String s, int i, String codice)
    {
        setCodice(codice);
    }

    public final String getCodice()
    {
        return codice;
    }

    private void setCodice(String codice)
    {
        this.codice = codice;
    }

    public static TipologiaPraticheSelezionabili[] values()
    {
        TipologiaPraticheSelezionabili atipologiapraticheselezionabili[];
        int i;
        TipologiaPraticheSelezionabili atipologiapraticheselezionabili1[];
        System.arraycopy(atipologiapraticheselezionabili = ENUM$VALUES, 0, atipologiapraticheselezionabili1 = new TipologiaPraticheSelezionabili[i = atipologiapraticheselezionabili.length], 0, i);
        return atipologiapraticheselezionabili1;
    }

    public static TipologiaPraticheSelezionabili valueOf(String s)
    {
        return (TipologiaPraticheSelezionabili)TipologiaPraticheSelezionabili.valueOf(s);
    }

    public static final TipologiaPraticheSelezionabili inCompilazione;
    public static final TipologiaPraticheSelezionabili inviate;
    public static final TipologiaPraticheSelezionabili erroriInvio;
    private String codice;
    private static final TipologiaPraticheSelezionabili ENUM$VALUES[];

    static 
    {
        inCompilazione = new TipologiaPraticheSelezionabili("inCompilazione", 0, "C");
        inviate = new TipologiaPraticheSelezionabili("inviate", 1, "I");
        erroriInvio = new TipologiaPraticheSelezionabili("erroriInvio", 2, "E");
        ENUM$VALUES = (new TipologiaPraticheSelezionabili[] {
            inCompilazione, inviate, erroriInvio
        });
    }
}
