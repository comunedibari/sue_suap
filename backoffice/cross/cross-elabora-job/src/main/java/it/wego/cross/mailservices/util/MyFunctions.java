package it.wego.cross.mailservices.util;

import static it.wego.cross.mailservices.component.MyGetMailConsegnate.store;

import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

import it.wego.cross.mailservices.component.MyGetMailConsegnate;

/**
 *
 * @author Oliver
 */
public class MyFunctions {

    public static int TestUser(String pop3host, String Username, String Password) throws NoSuchProviderException, MessagingException {
        try {
            Session session = Session.getInstance(MyGetMailConsegnate.props);
            store = session.getStore("imaps");
            store.connect(pop3host, Username, Password);
            return 0;
        } catch (MessagingException e) {
            System.out.println(e + "User Name Invalid");
            return 1;
        }
    }

    public static Folder TestFolder(Store store, String Path) throws MessagingException {
        Folder emailFolder;
        emailFolder = store.getFolder(Path);
        emailFolder.open(Folder.READ_ONLY);
        MyGetMailConsegnate.fTest = true;
        emailFolder.close(false);
        return emailFolder;
    }

    public static int MBSize(String pop3Host, String userName, String password, String Path) {
        int Size = 0;
        Session session = Session.getInstance(MyGetMailConsegnate.props);
        try {
            Store store = session.getStore("imaps");
            store.connect(pop3Host, userName, password);
            Folder emailFolder = store.getFolder(Path);
            emailFolder.open(Folder.READ_ONLY);
            Size = emailFolder.getMessageCount();
            emailFolder.close(false);
            store.close();
            return Size;
        } catch (MessagingException e) {
            System.out.println(e + "MBSize");
        }
        return Size;
    }
}