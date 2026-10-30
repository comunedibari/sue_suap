package it.wego.auriga.xml;

public class TipoDocAllegato
{
    private String Decodifica_Nome;

    public String getDecodifica_Nome ()
    {
        return Decodifica_Nome;
    }

    public void setDecodifica_Nome (String Decodifica_Nome)
    {
        this.Decodifica_Nome = Decodifica_Nome;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Decodifica_Nome = "+Decodifica_Nome+"]";
    }
}