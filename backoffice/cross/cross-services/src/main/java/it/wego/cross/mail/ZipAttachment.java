/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.mail;

import it.wego.cross.utils.Log;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.activation.DataSource;
import javax.mail.util.ByteArrayDataSource;
import org.apache.commons.lang.math.RandomUtils;

/**
 *
 * @author emanuela
 */
public class ZipAttachment {

    private String idPratica;
    private String codSport;
    private List<DataSource> allegati;
    private String xmlRiepilogo;

    public ZipAttachment() {
    }

    public ZipAttachment(String idPratica, String codSport, List<DataSource> allegati, String xmlRiepilogo) {
        this.idPratica = idPratica;
        this.codSport = codSport;
        this.allegati = allegati;
        this.xmlRiepilogo = xmlRiepilogo;
    }

    public DataSource createZipFile() throws IOException {
        List<String> filenames = new ArrayList<String>();
        String outFilename = idPratica.replace("/", "_") + ".zip";
        byte[] buf = new byte[1024];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream out = new ZipOutputStream(baos);
        if (allegati != null) {
            for (DataSource bean : allegati) {
                InputStream in = bean.getInputStream();
                if (filenames.contains(bean.getName())) {
                    out.putNextEntry(new ZipEntry(RandomUtils.nextInt(100) + "_" + bean.getName()));
                    filenames.add(RandomUtils.nextInt(100) + "_" + bean.getName());
                } else {
                    out.putNextEntry(new ZipEntry(bean.getName()));
                    filenames.add(bean.getName());
                }

                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                out.closeEntry();
                in.close();
            }
        }
        if (xmlRiepilogo != null) {
            String nomeFile = "riepilogo_" + idPratica.replace("/", "_") + ".xml";
            ByteArrayOutputStream os = new ByteArrayOutputStream(xmlRiepilogo.length());
            os.write(xmlRiepilogo.getBytes());
            InputStream is = new ByteArrayInputStream(os.toByteArray());
            out.putNextEntry(new ZipEntry(nomeFile));
            int len;
            while ((len = is.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.closeEntry();
            is.close();
        }
        out.close();
        String mimeType = "application/zip";
        ByteArrayDataSource ds = new ByteArrayDataSource(baos.toByteArray(), mimeType);
        ds.setName(outFilename);
        return ds;
    }

    public List<DataSource> getAllegati() {
        return allegati;
    }

    public void setAllegati(List<DataSource> allegati) {
        this.allegati = allegati;
    }

    public String getCodSport() {
        return codSport;
    }

    public void setCodSport(String codSport) {
        this.codSport = codSport;
    }

    public String getIdPratica() {
        return idPratica;
    }

    public void setIdPratica(String idPratica) {
        this.idPratica = idPratica;
    }

    public String getXmlRiepilogo() {
        return xmlRiepilogo;
    }

    public void setXmlRiepilogo(String xmlRiepilogo) {
        this.xmlRiepilogo = xmlRiepilogo;
    }
}
