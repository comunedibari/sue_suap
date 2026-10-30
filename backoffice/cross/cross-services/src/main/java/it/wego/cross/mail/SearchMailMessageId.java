package it.wego.cross.mail;

import java.io.IOException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.search.SearchTerm;

public class SearchMailMessageId extends SearchTerm {
	private String messageId;

	public SearchMailMessageId(String messageId) {
		this.messageId = messageId;
	}

	
	@Override
	public boolean match(Message message) {
		try {
			String values[] = message.getHeader("Message-ID");
			if (values != null) {
				for (int i = 0; i < values.length; i++) {
					String value = values[i];
					if (messageId.equals(value)) {
						return true;
					}
				}
			}
			return false;
		} catch (MessagingException e) {
			return false;
		}
	}

/*
	@Override
	public boolean match(Message message) {
		try {
			String contentType = message.getContentType().toLowerCase();
			if (contentType.contains("text/plain")
					|| contentType.contains("text/html")) {
				String messageContent = message.getContent().toString();
				if (messageContent.contains(messageId)) {
					return true;
				}
			}
		} catch (MessagingException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return false;
	}
 */
}