package it.wego.auriga.xml;

public class EstremixIdentificazioneFileUD
{
    private TipoDocAllegato TipoDocAllegato;

    private String NroVersione;

    public TipoDocAllegato getTipoDocAllegato ()
    {
        return TipoDocAllegato;
    }

    public void setTipoDocAllegato (TipoDocAllegato TipoDocAllegato)
    {
        this.TipoDocAllegato = TipoDocAllegato;
    }

    public String getNroVersione ()
    {
        return NroVersione;
    }

    public void setNroVersione (String NroVersione)
    {
        this.NroVersione = NroVersione;
    }

    @Override
    public String toString()
    {
        return "<EstremixIdentificazioneFileUD><TipoDocAllegato>"+TipoDocAllegato+"</TipoDocAllegato><NroVersione>"+NroVersione+"</NroVersione></EstremixIdentificazioneFileUD>";
    }
}