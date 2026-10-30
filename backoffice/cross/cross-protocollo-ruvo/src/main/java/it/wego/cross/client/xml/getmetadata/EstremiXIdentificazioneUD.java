package it.wego.cross.client.xml.getmetadata;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EstremiXIdentificazioneUD", propOrder = {
    "IdUD"
})
@XmlRootElement(name = "EstremiXIdentificazioneUD")
public class EstremiXIdentificazioneUD
{

    @XmlElement(name = "IdUD")
    protected String IdUD;


    public String getIdUD ()
    {
        return IdUD;
    }

    public void setIdUD (String IdUD)
    {
        this.IdUD = IdUD;
    }

    @Override
    public String toString()
    {
        return "<EstremiXIdentificazioneUD><IdUD>"+ this.getIdUD() + "</IdUD></EstremiXIdentificazioneUD>";
    }
}
			
