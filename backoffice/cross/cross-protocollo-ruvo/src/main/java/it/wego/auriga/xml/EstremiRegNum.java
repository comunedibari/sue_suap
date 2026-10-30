package it.wego.auriga.xml;

public class EstremiRegNum {

    private String NumReg;

    private String CategoriaReg;

    private String SiglaReg;

    private String AnnoReg;

    public String getNumReg ()
    {
        return NumReg;
    }

    public void setNumReg (String NumReg)
    {
        this.NumReg = NumReg;
    }

    public String getCategoriaReg ()
    {
        return CategoriaReg;
    }

    public void setCategoriaReg (String CategoriaReg)
    {
        this.CategoriaReg = CategoriaReg;
    }

    public String getSiglaReg ()
    {
        return SiglaReg;
    }

    public void setSiglaReg (String SiglaReg)
    {
        this.SiglaReg = SiglaReg;
    }

    public String getAnnoReg ()
    {
        return AnnoReg;
    }

    public void setAnnoReg (String AnnoReg)
    {
        this.AnnoReg = AnnoReg;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [NumReg = "+NumReg+", CategoriaReg = "+CategoriaReg+", SiglaReg = "+SiglaReg+", AnnoReg = "+AnnoReg+"]";
    }
}