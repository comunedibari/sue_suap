package it.wego.cross.webservices.cxf.infocamere;
import java.util.Collection;
import java.util.Collections;

import org.apache.cxf.message.Attachment;
public class PDDServiceAttachmentManager {
	private static ThreadLocal<Collection<Attachment>> attachments = new ThreadLocal<Collection<Attachment>>()
	{
		protected synchronized Collection<Attachment> initialValue()
		{
			return Collections.emptyList();
		}
	};

	public static void set(Collection<Attachment> attachments)
	{
		PDDServiceAttachmentManager.attachments.set(attachments);
	}

	public static Collection<Attachment> get()
	{
		return attachments.get();
	}
}
