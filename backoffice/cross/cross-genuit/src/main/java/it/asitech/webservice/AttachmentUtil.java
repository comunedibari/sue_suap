package it.asitech.webservice;


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Iterator;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import org.apache.axis.AxisFault;
import org.apache.axis.Message;
import org.apache.axis.MessageContext;
import org.apache.axis.attachments.AttachmentPart;
import org.apache.axis.attachments.Attachments;
import org.apache.axis.client.Call;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AttachmentUtil {
    private static Logger log = LoggerFactory.getLogger(AttachmentUtil.class);

    /**
     * This class helps clients add attachements to there SOAP requests and
     * services to save attachments to local disk....
     *
     * <pre>
     *       Here is sample client code:
     *       --- snip -------
     *       String[] localAbsFileNames = { &quot;/tmp/test.jpg&quot;, &quot;/tmp/test.pdf&quot; };
     *       String[] attachmentFileNames =    new AttachmentUtil().addFile(stub,
     *       localAbsFileNames);
     *   //     make remote call w/ attachments
     *       System.out.println(Arrays.asList(stub.saveFile(attachmentFileNames)));
     *       --- snip -------
     *
     *       Here is sample service code:
     *
     *       --- snip -------
     *       AttachmentUtil util = new AttachmentUtil();
     *       util.setAttachmentsDir(&quot;/tmp/attachments/&quot;);
     *   //     write attachemnts to /tmp/attachments/
     *       System.out.println(Arrays.asList(util.saveFile(attachmentFileNames));
     *       --- snip -------
     * </pre>
     *
     */

    private String attachmentsDir = "";
    private final String attachmentsFormat;

    /*
     * Reusing the method implementation for AttachmentPart[]
     * getMessageAttachments() as provided by Steve Loughran in his mail to
     * axis-user group
     * http://www.mail-archive.com/axis-user@xml.apache.org/msg08732.html
     */

    public AttachmentUtil() {
        this.attachmentsFormat = Call.ATTACHMENT_ENCAPSULATION_FORMAT_MIME;
    }

    /**
     * extract attachments from the current request
     *
     * @return a list of attachmentparts or an empty array for no attachments
     *         support in this axis buid/runtime
     */
    public static AttachmentPart[] getMessageAttachments() throws AxisFault {
        MessageContext msgContext = MessageContext.getCurrentContext();
        Message reqMsg = msgContext.getRequestMessage();
        Attachments messageAttachments = reqMsg.getAttachmentsImpl();
        if( null == messageAttachments ) {
            if( log.isDebugEnabled()) {
                log.debug( "no attachment support" );
            }
            return new AttachmentPart[0];
        }
        int attachmentCount = messageAttachments.getAttachmentCount();
        AttachmentPart attachments[] = new AttachmentPart[attachmentCount];
        Iterator it = messageAttachments.getAttachments().iterator();
        int count = 0;
        while( it.hasNext() ) {
            AttachmentPart part = (AttachmentPart)it.next();
            attachments[count++] = part;
        }
        return attachments;
    }

    public void setAttachmentsDir( String attachmentsDir ) {
        this.attachmentsDir = attachmentsDir;
    }

    public String getAttachmentsDir() {
        return this.attachmentsDir;
    }

    /**
     * This method is used for adding files as attachments for SOAP calls
     *
     * @param stub
     * @param localAbsFileNames list of files to be attached from the local
     *        client file system
     * @return attachment file names
     */
    public synchronized String[] addFile( org.apache.axis.client.Stub stub,
            String[] localAbsFileNames ) {
        String[] fileNames = new String[localAbsFileNames.length];
        for( int i = 0; i < localAbsFileNames.length; i++ ) {
            File file = new File( localAbsFileNames[i] );
            String fileName = file.getName();
            fileNames[i] = fileName;
            // Use classes from the Java Activation Framework
            // (import activation.jar) to wrap the attachment.
            DataHandler attachmentFile = new DataHandler( new FileDataSource(
                    file ) );

            // Tell the stub that the message being formed also
            // contains an attachment, and it is of type MIME
            // encoding.
            stub._setProperty( Call.ATTACHMENT_ENCAPSULATION_FORMAT,
                    this.attachmentsFormat );

            // Add the attachment to the message
            stub.addAttachment( attachmentFile );
        }
        return fileNames;
    }

    /**
     * This method receives the MIME encoded attachment from the client and
     * saves it on the server.
     *
     * @return String[] containing the remote abs path of file names of the +
     *         attachments.
     * @param String[] containing the file names of the attachments.
     */
    public synchronized String[] saveFile( String[] attachmentFileNames )
            throws java.rmi.RemoteException {
        InputStream is;
        FileOutputStream os;
        try {
            // Get all the attachments
            AttachmentPart[] attachments = getMessageAttachments();
            int totalAttachments = attachments.length;
            String[] out = new String[totalAttachments];
            log.info("Total Attachments" + " Received Are: " + totalAttachments );

            // Extract each attachment.
            for( int i = 0; i < totalAttachments; i++ ) {
                DataHandler dh = attachments[i].getDataHandler();
                // Extract the file name of the first attachment.
                String name = attachmentFileNames[i];
                log.info("File received on server is: " + name );

                is = dh.getInputStream();
                File file = new File( this.attachmentsDir + name );
                os = new FileOutputStream( file );
                int j = 0;
                while( j != -1 ) {
                    j = is.read();
                    os.write( j );
                }
                is.close();
                os.close();
                out[i] = file.getAbsolutePath();
                log.info(
                        "File written to: " + file.getAbsolutePath() );
            }
            return out;
        } catch( Exception e ) {
            log.error( "Unable to save file(s): " + e );
            AxisFault af = new AxisFault();
            af.setFaultCodeAsString( "Server.UnableToSave" );
            af.setFaultReason( "Unable to save file(s): " + e );
            throw af;
        }
    }
}
