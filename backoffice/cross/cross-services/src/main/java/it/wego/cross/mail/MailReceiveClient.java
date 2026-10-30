package it.wego.cross.mail;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

// @Component
public final class MailReceiveClient {

    private final String protocol;
    private final String host;
    private final String port;
    private final String user;
    private final String password;

    public MailReceiveClient(@Value("${mail.store.protocol}") String protocol,
                             @Value("${mail.store.host}") String host,
                             @Value("${mail.store.port}") String port,
                             @Value("${spring.mail.username}") String user,
                             @Value("${spring.mail.password}") String password) {
        this.protocol = protocol;
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
    }

    public List<Mail> receive() {
        Store emailStore = null;
        Folder emailFolder = null;

        Properties properties = new Properties();
        properties.put("mail.store.protocol", protocol);
        properties.put("mail." + protocol + ".host", host);
        properties.put("mail." + protocol + ".port", port);
        Session emailSession = Session.getInstance(properties);

        try {
            emailStore = emailSession.getStore();
            emailStore.connect(user, password);

            emailFolder = emailStore.getFolder("INBOX");
            emailFolder.open(Folder.READ_WRITE);

            return getNewMails(emailFolder);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (emailFolder != null && emailFolder.isOpen()) {
                    emailFolder.close(false);
                }
                if (emailStore != null && emailStore.isConnected()) {
                    emailStore.close();
                }
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void readMailInbox(String host, String storeType, String user, String password) {
		try {

			// create properties
			Properties properties = new Properties();

			properties.put("mail.imap.host", host);
			properties.put("mail.imap.port", "993");
			properties.put("mail.imap.starttls.enable", "true");
			properties.put("mail.imap.ssl.trust", host);

			Session emailSession = Session.getDefaultInstance(properties);

			// create the imap store object and connect to the imap server
			Store store = emailSession.getStore("imaps");

			store.connect(host, user, password);

			// create the inbox object and open it
			Folder inbox = store.getFolder("Inbox");
//			inbox.open(Folder.READ_WRITE);
			inbox.open(Folder.READ_ONLY);

			// retrieve the messages from the folder in an array and print it
			Message[] messages = inbox.search(new FlagTerm(new Flags(Flag.SEEN), false));
			System.out.println("messages.length---" + messages.length);

			for (int i = 0, n = messages.length; i < n; i++) {
				Message message = messages[i];
				message.setFlag(Flag.SEEN, true);
				System.out.println("---------------------------------");
				System.out.println("Email Number " + (i + 1));
				System.out.println("Subject: " + message.getSubject());
				System.out.println("From: " + message.getFrom()[0]);
				System.out.println("Text: " + message.getContent().toString());

			}

			inbox.close(false);
			store.close();

		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    private List<Mail> getNewMails(Folder emailFolder) throws MessagingException {
        List<Mail> mails = new ArrayList<>();
        for (Message message : emailFolder.getMessages()) {
            if (!message.getFlags().contains(Flags.Flag.SEEN)) {
                message.setFlags(new Flags(Flags.Flag.SEEN), true);
                //Mail mail = MailMapper.map(message, user);
                //mails.add(mail);
            }
        }
        return mails;
    }
}
