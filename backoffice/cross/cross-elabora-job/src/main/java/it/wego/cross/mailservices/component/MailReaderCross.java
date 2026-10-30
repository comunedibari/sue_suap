package it.wego.cross.mailservices.component;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.FetchProfile;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.search.AndTerm;
import javax.mail.search.ComparisonTerm;
import javax.mail.search.ReceivedDateTerm;
import javax.mail.search.SearchTerm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.wego.cross.mailservices.sql.DaoConfigCross;
import it.wego.cross.mailservices.utilities.ParserXml;
import it.wego.cross.mailservices.utilities.myIndexableMap;
import it.wego.cross.mailservices.utilities.myUtility;

public class MailReaderCross {

	public static final int INBOX_MASSIVO = 0;
	public static final int INBOX_GIORNALIERO = 1;
	public static final int POP3 = 995;
	public static final int IMAP = 993;
		
	private static final Logger logger = LoggerFactory.getLogger(MailReaderCross.class.getName());

	DaoConfigCross dao;

	Folder inbox;

	public MailReaderCross() {
		System.setProperty("log.name", "CrossElaboraJob.log");
	}

	public static Date parseDate(String date) {
		try {
			return new SimpleDateFormat("dd/MM/yyyy").parse(date);
		} catch (ParseException e) {
			return null;
		}
	}

	private static Date getDate(int giorno, int mese, int anno) {
		
		String sData = padLeftZero(giorno) + "/" + padLeftZero(mese) + "/" + anno;
		logger.info("Calcolo data SData:" + sData);		

		Date data = parseDate(sData);
		logger.info("Calcolo data Date:" + data);		
		return data;				
	}
	
	private static Date getDateDa(int mese, int anno) {
		int giorno = 1;
		if(mese>1) { giorno = 2;}
		
		String sDa = padLeftZero(giorno) + "/" + padLeftZero(mese+1) + "/" + anno;
		logger.debug("SDataDa:" + sDa);		

		Date da = parseDate(sDa);
		logger.debug("DataDa:" + da);		
		return da;				
	}
	private static Date getDateA(int mese, int anno) {
		int giorno = 1;
		if(mese>10) { giorno = 31; mese = 10;}
		
		String sA = padLeftZero(giorno) + "/" + padLeftZero(mese+2) + "/" + anno;
		logger.debug("SDataA:" + sA);		

		Date a = parseDate(sA);
		logger.debug("DataA:" + a);				
		return a;				
	}	
	public static String padRight(String s, int n) {
	     return String.format("%-" + n + "s", s);  
	}

	public static String padLeftZero(int n) {
		String result = String.valueOf(n);		
		if(n<9) {
			result = String.format("0%d", n);
		}  
		return result;
	}

	public static String padLeft(String s, int n) {
	    return String.format("%" + n + "s", s);  
	}

	/**
	 * @return the dao
	 */
	public DaoConfigCross getDao() {
		return dao;
	}

	/**
	 * @param dao the dao to set
	 */
	public void setDao(DaoConfigCross dao) {
		this.dao = dao;
	}

	public void executeMassivo(int anno, int mese, int giorno) {
		String msgInfo = "";
		int idEnte = 0;

		myIndexableMap<Integer, String> enti;
		try {
			enti = dao.getListEnti(MailReaderCross.INBOX_MASSIVO);				
			msgInfo = "MailReaderCross::execute Totale enti da elborare: " + enti.size();
			System.out.println(msgInfo);
			logger.info(msgInfo);

			for(int i=0; i < enti.getKeyListSize(); i++)
			{
				idEnte =  enti.getKeyAt(i);
				msgInfo = "MailReaderCross::execute Elaborazione ente:" + idEnte + " " + enti.getValueAt(i);
				System.out.println(msgInfo);
				logger.info(msgInfo);
				dao.loggerDB(msgInfo);

				elaboraInboxMassivo(idEnte, anno, mese, giorno);
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void executeGiornaliero(int anno, int mese, int giorno) {
		String msgInfo = "";
		int idEnte = 0;

		myIndexableMap<Integer, String> enti;
		try {
			enti = dao.getListEnti(MailReaderCross.INBOX_GIORNALIERO);				
			msgInfo = "MailReaderCross::execute Totale enti da elborare: " + enti.size();
			System.out.println(msgInfo);
			logger.info(msgInfo);

			for(int i=0; i < enti.getKeyListSize(); i++)
			{
				idEnte =  enti.getKeyAt(i);
							
				//LOR Solo x Test				
				// if(idEnte != 2001) {continue;}
				
				msgInfo = "MailReaderCross::execute Elaborazione ente:" + idEnte + " " + enti.getValueAt(i);
				System.out.println(msgInfo);
				logger.info(msgInfo);
				dao.loggerDB(msgInfo);

				msgInfo = "MailReaderCross::execute Sto elaborando ente:" + idEnte + " " + enti.getValueAt(i) + 
						" anno:" + anno + " mese:" + mese + " giorno:" + giorno;
				
				logger.info(msgInfo);
				dao.loggerDB(msgInfo);

				elaboraInboxMassivo(idEnte, anno, mese, giorno);
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private void testServerMail() throws MessagingException {
		final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

		Date today = new Date();
		Calendar cal = new GregorianCalendar();
		cal.setTime(today);
		cal.add(Calendar.DAY_OF_MONTH, -10);
		Date today10 = cal.getTime();

		/* ORI
		String userName = "sportellosue.comunebari@pec.rupar.puglia.it";
		String password = "Prat!che.2022";

		String userName = "sue.gioiadelcolle@pec.rupar.puglia.it";
		String password = "Segyy*34b7";
		 */
		//LOR store.connect("webmail.comune.bari.it" , "pec.sportellosue@comune.bari.it", "Carnico@11");

		// String host = "imaps.pec.rupar.puglia.it";
		//String host = "mail.pec.rupar.puglia.it";
		String userName = "sportellosue.comunebari@pec.rupar.puglia.it";
		String password = "Prat!che.2022";

		//String host = "pop3s.pec.rupar.puglia.it";
		// String host = "imaps.pec.rupar.puglia.it";
		String host = "mail.pec.rupar.puglia.it";		
		int port = 993; //IMAP
		// int port = 995; //POP3
		Properties properties = System.getProperties();     

		/* POP3S *
		properties.setProperty("mail.pop3.socketFactory.class", SSL_FACTORY);
		properties.setProperty("mail.pop3.socketFactory.fallback", "false");
		properties.setProperty("mail.pop3.port", String.valueOf(port));
		properties.setProperty("mail.pop3.socketFactory.port", String.valueOf(port));
		 * */

		/* IMAPS *
		properties.setProperty("mail.store.protocol", "imaps");
		properties.setProperty("mail.imaps.socketFactory.class", SSL_FACTORY);
		properties.setProperty("mail.imaps.socketFactory.fallback", "false");
		properties.setProperty("mail.imaps.port", String.valueOf(port));
		properties.setProperty("mail.imaps.socketFactory.port", String.valueOf(port));
		properties.put("mail.imaps.host", host);
		 **/
		Session session = Session.getDefaultInstance( System.getProperties(), null);
		Store store = null;
		Folder inbox = null;
		try {
			store = session.getStore("imaps");
			//store = session.getStore("pop3s");
			store.connect(host, port, userName, password);
			inbox = store.getFolder("INBOX");
			inbox.open(Folder.READ_ONLY);

			//ReceivedDateTerm dateTerm = new ReceivedDateTerm(ComparisonTerm.EQ, new Date());
			ReceivedDateTerm dateTerm = new ReceivedDateTerm(ComparisonTerm.GT, today10);
			Message[] messages = inbox.search(dateTerm);			

			// ALL messages   Message messages[] = inbox.getMessages();
			System.out.println("No. of Messages : " + messages.length);
			printAllMessages(messages);

		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			inbox.close(true);
			store.close();
		}
	}
	
	private void __elaboraInboxGiornaliero(int idEnte, int tipo_elab) throws SQLException {
		System.out.println("Inside MyMailReader()...");

		final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

		final String keyword = "CONSEGNA";

		String host = "";
		int port = 0;
		String userName = "";
		String password = "";

		Date today = new Date();
		Calendar cal = new GregorianCalendar();
		cal.setTime(today);
		cal.add(Calendar.DAY_OF_MONTH, -10);
		Date today10 = cal.getTime();

		try {
			Message messages[] = null;
			Store store = null;
			Session session = null;

			dao.getConfiguration(idEnte);

			host = dao.getsServerImap(); 
			port = Integer.valueOf(dao.getsServerImapPort()).intValue();
			userName = dao.getsUsernameServerImap();
			password = dao.getsPasswordServerImap();
	
			/*
			MailReaderPop3 pop3Conn = new MailReaderPop3();
			pop3Conn.connect(host, userName, password);
			inbox = pop3Conn.openFolder("INBOX");			
			session = pop3Conn.getSession();
			store = pop3Conn.getStore();
			messages = pop3Conn.getMessage();
			 */

			MailReaderImap imapConn = new MailReaderImap();
			imapConn.connect(host, userName, password);
			inbox = imapConn.openFolder("INBOX");			
			session = imapConn.getSession();
			store = imapConn.getStore();

			Properties configProperties = myUtility.readConfigProperties("application.properties");				
						
			ReceivedDateTerm dateTerm = new ReceivedDateTerm(ComparisonTerm.GT, today10);

			messages = inbox.search(dateTerm);			
			// Message messages[] = inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));

			logger.info("No. of Messages : " + messages.length);

			elaboraRicevutePec(idEnte, messages, session);
			
			inbox.close(true);
			store.close();
		} catch (Exception ex) {
			logger.info("Exception arise at the time of read mail");
			ex.printStackTrace();
		}

	}

	public void elaboraInboxMassivo(int idEnte, int anno, int mese, int giorno) throws SQLException {
		System.out.println("Inside MyMailReader()...");

		final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

		final String keyword = "CONSEGNA";

		String host = "";
		int port = 0;
		String userName = "";
		String password = "";

		Date today = new Date();
		Calendar cal = new GregorianCalendar();
		cal.setTime(today);
		cal.add(Calendar.DAY_OF_MONTH, -10);
		Date today10 = cal.getTime();

		dao.getConfiguration(idEnte);

		host = dao.getsServerImap(); 
		port = Integer.valueOf(dao.getsServerImapPort()).intValue();
		userName = dao.getsUsernameServerImap();
		password = dao.getsPasswordServerImap();

		try {
			Message messages[] = null;
			Store store = null;
			Session session = null;

			Date dataDa=null;
			Date data=null;
			Date dataA=null;

			data = getDate(giorno, mese, anno);	
			
			dao.loggerDB("ElaboraInbox data di ricerca mail:" + data.toString());
			SearchTerm st = new ReceivedDateTerm(ComparisonTerm.EQ, data);

			port = Integer.valueOf(dao.getsServerImapPort()).intValue();
			
			if(port == IMAP) {			
				host = dao.getsServerImapWebMail(); 
				userName = dao.getsServerImapWebMailUsername();
				password = dao.getsServerImapWebMailPassowrd();
				MailReaderImap imapConn = new MailReaderImap();
				imapConn.connect(host, userName, password);
				inbox = imapConn.openFolder("INBOX");			
				session = imapConn.getSession();
				store = imapConn.getStore();
				messages = inbox.search(st);				
			} else { //POP3
				host = dao.getsMailHost(); 
				userName = dao.getsUsernameServerImap();
				password = dao.getsPasswordServerImap();
				MailReaderPop3 pop3Conn = new MailReaderPop3();
				inbox = pop3Conn.authenticate(host, userName, password);
				session = pop3Conn.getSession();
				store = pop3Conn.getStore();				
				messages = inbox.getMessages();				
			}
			
			logger.info(" ElaboraInbox data di ricerca mail:" + data.toString()
			+ " IdEnte:" + idEnte 
			+ " host:" + host + " userName:" + userName + " password:" + password
			+ " port:" + port
			+ " Anno:" + anno
			+ " Mese:" + mese
			+ " Giorno:" + giorno
			+ " No. of Messages : " + messages.length);

			dao.loggerDB( "ElaboraInbox data di ricerca mail:" + data.toString()
					+ " IdEnte:" + idEnte 
					+ " Anno:" + anno
					+ " Mese:" + mese
					+ " Giorno:" + giorno
					+ " No. of Messages : " + messages.length);
			
			if(messages.length>0) {
				elaboraRicevutePec(idEnte, messages, session);
			}

			inbox.close(true);
			store.close();
			
		} catch (Exception ex) {
			logger.info("Exception arise at the time of read mail");
			ex.printStackTrace();
		}

	}

	protected void elaboraRicevutePec(int idEnte, Message[] messages, Session session) throws Exception {
		FetchProfile fp = new FetchProfile();
		fp.add(FetchProfile.Item.ENVELOPE);
		fp.add(FetchProfile.Item.CONTENT_INFO);

		inbox.fetch(messages, fp);

		// 1 printAllMessages(messages);
		for (int i = 0; i < messages.length; i++) 
		{
			String subject = messages[i].getSubject();

			if(subject.contains("CONSEGNA")) {

				String corpoRisposta = "";
				String msgid = "";

				String idMessaggio = messages[i].getHeader("Message-ID")[0];
				
				msgid = messages[i].getHeader("X-Riferimento-Message-ID")[0];

				logger.info("------------ Message " + (i + 1) + " ------------");
				logger.info("------------ Message-ID:" + idMessaggio + " ------------");
				logger.info("------------ X-Riferimento-Message-ID: " + msgid + " ------------");

				String from = InternetAddress.toString(messages[i].getFrom());
				if (from != null) {
					// System.out.println("From: " + from);
				}

				String replyTo = InternetAddress.toString(messages[i].getReplyTo());
				if (replyTo != null) {
					//logger.info("Reply-to: " + replyTo);
				}
				
				String to = InternetAddress.toString(messages[i].getRecipients(Message.RecipientType.TO));
				if (to != null) {
					//logger.info("To: " + to);
				}

				Date receivedDate = messages[i].getReceivedDate();

				Date sentDate = messages[i].getSentDate(); 

				if (receivedDate != null) {
					//logger.info("Received Date : " + receivedDate.toString());
				}

				if (receivedDate != null) {
					//logger.info("sentDate Date : " + sentDate.toString());

				}

				subject = messages[i].getSubject();
				if (subject != null) {
					//logger.info("Subject: " + subject);
				}
				Date sent = messages[i].getSentDate();
				if (sent != null) {
					//logger.info("Sent: " + sent);
				}

				System.out.println();

				corpoRisposta = getCorpoEmail(messages[i]);
				
				dao.insertRicevuta(idEnte, idMessaggio, msgid, sentDate, subject, corpoRisposta);

/* GESTIONE ATTACHMENTS 
 * 
 * **********************************************************************************************

				String body = "";

				InputStream inputStreamObj = new ByteArrayInputStream(org.apache.commons.io.IOUtils.toByteArray(messages[i].getInputStream()));

				MimeMessage mimeMessageObj = new MimeMessage(session, inputStreamObj);

				Object content = mimeMessageObj.getContent();
				//Object content = messages[i].getContent();					

				String contentType = messages[i].getContentType();

				logger.info("contentType: " + contentType.toString());

				Multipart multipart = (Multipart) messages[i].getContent();

				for (int x = 0; x < multipart.getCount(); x++) {
					BodyPart bodyPart = multipart.getBodyPart(x);

					String disposition = bodyPart.getDisposition();

					if (disposition != null && (disposition.equals(BodyPart.ATTACHMENT))) {
						logger.info("Mail have some attachment : ");

						DataHandler handler = bodyPart.getDataHandler();

						logger.info("file name attachment: " + handler.getName());							
					}
				}
				if (content instanceof java.lang.String) {
					body = (String) content;
				} else if (content instanceof Multipart) {

					Multipart multiPart = (Multipart)content;
					for (int y = 0; y < multiPart.getCount(); y++) {
						BodyPart part = (MimeBodyPart) multiPart.getBodyPart(y);
						if (part.getFileName() != null) {
							logger.info("Filename:"+part.getFileName());									
							String filename = part.getFileName();
							contentType = part.getContentType();

							if(filename.toLowerCase().equals("daticert.xml")) {

								String output = org.apache.commons.io.IOUtils.toString(part.getInputStream());

								logger.info("Multipart output file(" + filename + ") : contentType:" + contentType + " dati putput:" + output);
								contentType = part.getContentType();

								ParserXml p = new ParserXml();

								String xml = p.getTagValue(output, "dati");
								logger.info("Multipart output xml(" + xml +")");

								msgid = p.getTagValue(xml, "msgid");

								msgid = msgid.replace("&lt;", "<").replace("&gt;", ">");

								logger.info("Multipart output msgid(" + msgid + ")");

								dao.insertRicevuta(idEnte, idMessaggio, msgid, sentDate, subject, corpoRisposta);
							}
						} else if (part.getContent() instanceof Multipart) {
							logger.info("Multipart");
						} else if (part.getContent() instanceof String) {
							logger.info("Message text: "+part.getContent());
						} else {
							logger.info("NOT RECOGNIZED TYPE");
						}
					}
				}

				if (contentType.contains("text/plain") || contentType.contains("text/html")) {
					if (content != null) {
						String messageContent = content.toString();
						logger.info("messageContent text/plain or text/html " + messageContent.toString());
					}
				}
*------------------------------------------------------------------------------------------------------------------------***/
				System.out.println();
			}
		}
	}
	public String getCorpoEmail(Message email) throws Exception {
		Object content = email.getContent();
		String corpoEmail = "";
		if (content instanceof String) {
			corpoEmail = (String) content;
		} else if (content instanceof Multipart) {
			corpoEmail = getCorpoEmailRicorsivo((Multipart) content);
		}
		return corpoEmail;
	}

	private String getCorpoEmailRicorsivo(Multipart body) throws Exception {
		for (int i = 0; i < body.getCount(); i++) {
			BodyPart bodyPart = body.getBodyPart(i);
			String disposition = bodyPart.getDisposition();
			if (disposition == null && bodyPart instanceof MimeBodyPart) {
				MimeBodyPart mimeBodyPart = (MimeBodyPart) bodyPart;
				if (bodyPart.getContent() instanceof Multipart) {
					return getCorpoEmailRicorsivo((Multipart) mimeBodyPart.getContent());
				} else {
					String corpoEmail = "";
					if (bodyPart.isMimeType("text/plain")) {
						corpoEmail = (String) mimeBodyPart.getContent();
					} else if (bodyPart.isMimeType("text/html")) {
						corpoEmail = (String) mimeBodyPart.getContent();
					}
					return corpoEmail;
				}
			}
			if (Part.INLINE.equalsIgnoreCase(disposition)) {
				return (String) ((MimeBodyPart) bodyPart).getContent();
			}
		}
		return "";
	}
	public static int receiveEmail(Message message) throws IOException, MessagingException {
		int Counter = 0;

		try {
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
							logger.info("starting copy of - " + fileName);
						}
						String destFilePath = "D:/Scans/";
						bodyPart.saveFile(destFilePath + bodyPart.getFileName());
						long stopTime = System.currentTimeMillis();
						logger.info("finished copying of - " + fileName + "  -  " + (stopTime - startTime) + " miliseconds.");
						System.out.println(Counter);
						Counter++;
					} else {
					}
				}

			}
		}catch (MessagingException e) {
			System.out.println(e);
		}
		return 1;
	}
	public String[] getDeliveryStatus(Message email, Object content, Session session) throws Exception {
		String[] messageIds = null;
		String mailError = null;

		if (content instanceof Multipart) {
			Multipart body = (Multipart) content;
			for (int i = 0; i < body.getCount(); i++) {
				BodyPart bodyPart = body.getBodyPart(i);
				String contentType = bodyPart.getContentType();
				String string = org.apache.commons.io.IOUtils.toString(bodyPart.getInputStream());
				if (contentType.toLowerCase().contains("message/delivery-status")) {
					if (Pattern.compile("^Action: *failed$", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE).matcher(string).find()) {
						Matcher matcher = Pattern.compile("^Final-Recipient: *(.* )?([^ ]+) *$", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE).matcher(string);
						if (matcher.find()) {
							mailError = matcher.group(2);
						}
					}
				}
				if (contentType.toLowerCase().contains("message/rfc822")) {
					Message cmsg = new MimeMessage(session, bodyPart.getInputStream());
					messageIds = cmsg.getHeader("X-Riferimento-Message-ID");
					if (messageIds == null) {
						messageIds = cmsg.getHeader("Message-ID");
					}
				}
				if (contentType.toLowerCase().contains("text/rfc822-headers")) {
					Message cmsg = new MimeMessage(session, bodyPart.getInputStream());
					messageIds = cmsg.getHeader("X-Riferimento-Message-ID");
					if (messageIds == null) {
						messageIds = cmsg.getHeader("Message-ID");
					}
				}
			}
			if (mailError == null || messageIds == null || messageIds.length == 0) {
				messageIds=null;
			}
		} else if (content instanceof String) {
			Matcher matcher = Pattern.compile("^Message-ID: *(.* )?([^ ]+) *$", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE).matcher(content.toString());
			if (matcher.find()) {
				messageIds = new String[1];
				messageIds[0] = matcher.group(2);
			} else {
				messageIds = new String[0];
			}

		} else {
			messageIds = new String[0];
		}
		return messageIds;
	}

	public void printAllMessages(Message[] msgs) throws Exception
	{
		for (int i = 0; i < msgs.length; i++)
		{
			String subject = msgs[i].getSubject();

			if(subject.contains("CONSEGNA")) {
				String messageID = msgs[i].getHeader("Message-ID")[0];
				logger.info("MESSAGE ID:" + messageID);
				logger.info("MESSAGE #" + (i + 1) + ":");
				writeEnvelope(msgs[i]);
			}
		}

	}

	public void printAllMimeMessages(MimeMessage[] msgs) throws Exception
	{
		for (int i = 0; i < msgs.length; i++)
		{
			String messageID = msgs[i].getMessageID();
			logger.info("MESSAGE #" + (i + 1) + ":");
			writeEnvelope(msgs[i]);
		}

	}

	public void writeEnvelope(Message message) throws Exception
	{
		Address[] a;

		String messageID = message.getHeader("Message-ID")[0];

		// FROM
		if ((a = message.getFrom()) != null) {
			for (int j = 0; j < a.length; j++) {
				logger.info("FROM: " + a[j].toString());
			}
		}
		// TO
		if ((a = message.getRecipients(Message.RecipientType.TO)) != null) {
			for (int j = 0; j < a.length; j++) {
				logger.info("TO: " + a[j].toString());
			}
		}
		String subject = message.getSubject();

		Date receivedDate = message.getReceivedDate();
		Date sentDate = message.getSentDate(); // receivedDate

		String content = message.getContent().toString();
		logger.info("Subject : " + subject);
		if (receivedDate != null) {
			logger.info("Received Date : " + receivedDate.toString());
		}
		logger.info("Sent Date : " + sentDate.toString());
		logger.info("Content : " + content);

		// LOR String content2 =  getContent(message);
		// dao.insertRicevuta(messageID, content, messageID, subject, content);
	}

	public String getContent(Message msg)
	{
		String content = "";
		try {
			String contentType = msg.getContentType();
			logger.info("Content Type : " + contentType);
			Multipart mp = (Multipart) msg.getContent();
			int count = mp.getCount();
			for (int i = 0; i < count; i++) {
				String dumpPartstr = dumpPart(mp.getBodyPart(i));				
			}
		} catch (Exception ex) {
			logger.info("Exception arise at get Content");
			ex.printStackTrace();
		}
		return content;
	}

	public String dumpPart(Part p) throws Exception {
		InputStream is = p.getInputStream();
		if (!(is instanceof BufferedInputStream)) {
			is = new BufferedInputStream(is);
		}
		int c;
		StringBuilder str = new StringBuilder();

		logger.info("Message : ");
		while ((c = is.read()) != -1) {
			str.append(c);
			System.out.write(c);
		}
		return str.toString();
	}

	public static void main(String args[]) {
		int anno = 2024, mese = 5, giorno = 31;		
		
		Date data=null;
		data = getDate(giorno, mese, anno);
		System.out.println("finalDate:" + data.toString());
		
	}
}