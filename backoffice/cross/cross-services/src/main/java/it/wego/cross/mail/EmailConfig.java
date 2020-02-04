package it.wego.cross.mail;

import com.sun.mail.imap.IMAPFolder;
import it.wego.cross.entity.Allegati;
import java.util.List;
import java.util.Properties;
import javax.mail.Store;

/**
 *
 * @author CS
 */
public class EmailConfig {

    private Store store;
    private Properties properties;
    private String cartellaCross;
    private String ricercaLette;
    private String cartellaLette;
    private String from;
    private String to;
    private String subject;
    private String content;
    private String mailPathFS;
    private IMAPFolder cross;
    private IMAPFolder lette;
    private IMAPFolder inbox;
    private Integer idEnte;
    private Integer idComune;
    private List<Allegati> allegati;

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public String getCartellaCross() {
        return cartellaCross;
    }

    public void setCartellaCross(String cartellaCross) {
        this.cartellaCross = cartellaCross;
    }

    public String getRicercaLette() {
        return ricercaLette;
    }

    public void setRicercaLette(String ricercaLette) {
        this.ricercaLette = ricercaLette;
    }

    public String getCartellaLette() {
        return cartellaLette;
    }

    public void setCartellaLette(String cartellaLette) {
        this.cartellaLette = cartellaLette;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Allegati> getAllegati() {
        return allegati;
    }

    public void setAllegati(List<Allegati> allegati) {
        this.allegati = allegati;
    }

    public String getMailPathFS() {
        return mailPathFS;
    }

    public void setMailPathFS(String mailPathFS) {
        this.mailPathFS = mailPathFS;
    }

    public IMAPFolder getCross() {
        return cross;
    }

    public void setCross(IMAPFolder cross) {
        this.cross = cross;
    }

    public IMAPFolder getLette() {
        return lette;
    }

    public void setLette(IMAPFolder lette) {
        this.lette = lette;
    }

    public IMAPFolder getInbox() {
        return inbox;
    }

    public void setInbox(IMAPFolder inbox) {
        this.inbox = inbox;
    }

    public Integer getIdEnte() {
        return idEnte;
    }

    public void setIdEnte(Integer idEnte) {
        this.idEnte = idEnte;
    }

    public Integer getIdComune() {
        return idComune;
    }

    public void setIdComune(Integer idComune) {
        this.idComune = idComune;
    }
}
