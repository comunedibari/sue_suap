package it.wego.cross.mailservices.component;


import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.FetchProfile;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;
import javax.mail.internet.ContentType;
import javax.mail.internet.ParseException;

import com.sun.mail.pop3.POP3SSLStore;

public class MailReaderPop3 {

	private Session session = null;
	private Store store = null;
	private String username, password;
	private Folder folder;
	private String host;

	public MailReaderPop3() {

	}

	/**
	 * @return the session
	 */
	public Session getSession() {
		return session;
	}


	/**
	 * @param session the session to set
	 */
	public void setSession(Session session) {
		this.session = session;
	}


	/**
	 * @return the store
	 */
	public Store getStore() {
		return store;
	}


	/**
	 * @param store the store to set
	 */
	public void setStore(Store store) {
		this.store = store;
	}


	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}


	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}


	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}


	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}


	/**
	 * @return the folder
	 */
	public Folder getFolder() {
		return folder;
	}


	/**
	 * @param folder the folder to set
	 */
	public void setFolder(Folder folder) {
		this.folder = folder;
	}


	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}


	/**
	 * @param host the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

	public void connect(String host, String username, String password) throws Exception {

		String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

		Properties pop3Props = System.getProperties();        

		pop3Props.setProperty("mail.store.protocol", "imaps");
		pop3Props.put("mail.pop3.host", host);
		pop3Props.put("mail.pop3.port", "995");
		pop3Props.put("mail.pop3.starttls.enable", "true");
		  
		Session session = Session.getDefaultInstance(pop3Props);
	    store = session.getStore();
        store.connect(host, username, password);
        
        session.setDebug(true);
	}

	public Folder openFolder(String folderName) throws Exception {

		// Open the Folder
		folder = store.getDefaultFolder();

		folder = folder.getFolder(folderName);

		if (folder == null) {
			throw new Exception("Invalid folder");
		}
		/*
		try {
			folder.open(Folder.READ_WRITE);
		} catch (MessagingException ex) {
			folder.open(Folder.READ_ONLY);

		}
		*/
		folder.open(Folder.READ_ONLY);
		
		return folder;
	}

	public Folder authenticate(String host, String username, String password) throws Exception {

		Properties properties = System.getProperties();        
		properties.setProperty("mail.store.protocol", "imaps");
		properties.put("mail.pop3.host", host);
	    properties.put("mail.pop3.port", "995");
	    properties.put("mail.pop3.starttls.enable", "true");
	    session = Session.getDefaultInstance(properties);
        store = session.getStore();
        store.connect(host, username, password);
        //session.setDebug(true);
        folder = store.getFolder("INBOX");
        folder.open(Folder.READ_ONLY);
        return folder;
	}
	
	public void closeFolder() throws Exception {
		folder.close(false);
	}

	public int getMessageCount() throws Exception {
		return folder.getMessageCount();
	}

	public int getNewMessageCount() throws Exception {
		return folder.getNewMessageCount();
	}

	public Message[] getMessage() throws Exception {
		return folder.getMessages();
	}
	
	public void disconnect() throws Exception {
		store.close();
	}

	public void printMessage(int messageNo) throws Exception {
		System.out.println("Getting message number: " + messageNo);

		Message m = null;

		try {
			m = folder.getMessage(messageNo);
			dumpPart(m);
		} catch (IndexOutOfBoundsException iex) {
			System.out.println("Message number out of range");
		}
	}

	public void printAllMessageEnvelopes() throws Exception {

		// Attributes & Flags for all messages ..
		Message[] msgs = folder.getMessages();

		// Use a suitable FetchProfile
		FetchProfile fp = new FetchProfile();
		fp.add(FetchProfile.Item.ENVELOPE);
		folder.fetch(msgs, fp);

		for (int i = 0; i < msgs.length; i++) {
			System.out.println("--------------------------");
			System.out.println("MESSAGE #" + (i + 1) + ":");
			dumpEnvelope(msgs[i]);

		}

	}

	public void printAllMessages() throws Exception {

		// Attributes & Flags for all messages ..
		Message[] msgs = folder.getMessages();

		// Use a suitable FetchProfile
		FetchProfile fp = new FetchProfile();
		fp.add(FetchProfile.Item.ENVELOPE);
		folder.fetch(msgs, fp);

		for (int i = 0; i < msgs.length; i++) {
			System.out.println("--------------------------");
			System.out.println("MESSAGE #" + (i + 1) + ":");
			dumpPart(msgs[i]);
		}


	}


	public static void dumpPart(Part p) throws Exception {
		if (p instanceof Message)
			dumpEnvelope((Message)p);

		String ct = p.getContentType();
		try {
			pr("CONTENT-TYPE: " + (new ContentType(ct)).toString());
		} catch (ParseException pex) {
			pr("BAD CONTENT-TYPE: " + ct);
		}

		/*
		 * Using isMimeType to determine the content type avoids
		 * fetching the actual content data until we need it.
		 */
		if (p.isMimeType("text/plain")) {
			pr("This is plain text");
			pr("---------------------------");
			System.out.println((String)p.getContent());
		} else {

			// just a separator
			pr("---------------------------");

		}
	}

	public static void dumpEnvelope(Message m) throws Exception {
		pr(" ");
		Address[] a;
		// FROM
		if ((a = m.getFrom()) != null) {
			for (int j = 0; j < a.length; j++)
				pr("FROM: " + a[j].toString());
		}

		// TO
		if ((a = m.getRecipients(Message.RecipientType.TO)) != null) {
			for (int j = 0; j < a.length; j++) {
				pr("TO: " + a[j].toString());
			}
		}

		// SUBJECT
		pr("SUBJECT: " + m.getSubject());

		// DATE
		Date d = m.getSentDate();
		pr("SendDate: " +
				(d != null ? d.toString() : "UNKNOWN"));


	}

	static String indentStr =
			"                                               ";
	static int level = 0;

	/**
	 * Print a, possibly indented, string.
	 */
	public static void pr(String s) {

		System.out.print(indentStr.substring(0, level * 2));
		System.out.println(s);
	}

}
