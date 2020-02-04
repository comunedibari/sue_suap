package it.asitech.webservice.protocollo;

import java.io.File;

import javax.activation.DataHandler;
import javax.xml.soap.SOAPException;

public class PriAttachmentPart extends org.apache.axis.attachments.AttachmentPart  {

    public PriAttachmentPart() {
        super();
    }

    public PriAttachmentPart(DataHandler arg0) {
        super( arg0 );
    }

    @Override
    public String getAttachmentFile() {
        try {
            return this.getDataHandler().getDataSource().getName();
        } catch( SOAPException e ) {
            return null;
        }
    }
    
}
