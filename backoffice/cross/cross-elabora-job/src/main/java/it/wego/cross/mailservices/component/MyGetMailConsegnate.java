package it.wego.cross.mailservices.component;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeBodyPart;

import it.wego.cross.mailservices.util.MyFunctions;

/**
 *
 * @author Charlie
 */
public class MyGetMailConsegnate extends Thread {
        public static int Lower = 0;
        public static int Upper = 0;
        public static int Counter = 0;
        public static Session session;
        public static Store store;
        public static Properties props = new Properties();
        public static boolean fTest = false;
        MyGetMailConsegnate(int i, int ii) {
        Lower = i;
        Upper = ii;
    }
        MyGetMailConsegnate() throws UnknownHostException {
        super();
    }
    @Override
    public void run() {
        String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        String pop3Host = "outlook.office365.com";
        String mailStoreType = "imap";
        String path = "Inbox/Scans to file";
        String userName = "XXX@XXX.com";
        String password = "XXXXXXX";
        Folder emailFolder;
        try {
            props.setProperty("mail.imaps.socketFactory.class", SSL_FACTORY);
            props.setProperty("mail.imaps.socketFactory.fallback", "false");
            props.setProperty("mail.imaps.port", "993");
            props.setProperty("mail.imaps.socketFactory.port", "993");
            props.put("mail.imaps.host", "outlook.office365.com");
            session = Session.getInstance(props);
            int Size = MyFunctions.MBSize(pop3Host, userName, password, path);
            System.out.println(Size);
            store = session.getStore("imaps");
            store.connect(pop3Host, userName, password);
            emailFolder = store.getFolder(path);
            emailFolder.open(Folder.READ_ONLY);
            try {
                Message[] messages;
                messages = emailFolder.getMessages(Lower, Upper);
                System.out.println("starting thread for - " + Lower + " - " + Upper);
                int ASuc = receiveEmail(messages);
            } catch (MessagingException | IOException ex) {
                Logger.getLogger(MyGetMailConsegnate.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(MyGetMailConsegnate.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(MyGetMailConsegnate.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static int receiveEmail(Message messagesarr[]) throws IOException, MessagingException {
            for (Message messagesarr1 : messagesarr) {
                try {
                    Message message = messagesarr1;
                    Object content = message.getContent();
                    if (content instanceof String) {
                    } else if (content instanceof Multipart) {
                        Multipart multipart = (Multipart) message.getContent();
                        for (int k = 0; k < multipart.getCount(); k++) {
                            MimeBodyPart bodyPart = (MimeBodyPart) multipart.getBodyPart(k);
                            if (Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition())) {
                                long startTime = System.currentTimeMillis();
                                int ran = (int) startTime;
                                String fileName;
                                String fName = bodyPart.getFileName();
                                if (fName != null && !fName.isEmpty()) {
                                    fileName = fName.replaceAll("\\s+", "");
                                } else {
                                    continue;
                                }
                                if ("ATT00001.txt".equals(fileName)) {
                                    continue;
                                } else {
                                    System.out.println("starting copy of - " + fileName);
                                }
                                String destFilePath = "D:/Scans/";
                                bodyPart.saveFile(destFilePath + bodyPart.getFileName());
                                long stopTime = System.currentTimeMillis();
                                System.out.println("finished copying of - " + fileName + "  -  " + (stopTime - startTime) + " miliseconds.");
                                System.out.println(Counter);
                                Counter++;
                            } else {
                            }
                        }

                    }
                }catch (MessagingException e) {
                    System.out.println(e);
                }
            }
        return 1;
    }
}