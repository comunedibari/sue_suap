package it.wego.cross.webservices.cxf.infocamere;

import java.util.Collection;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Attachment;
import org.apache.cxf.phase.Phase;

public class PDDServiceAttachmentInterceptor extends AbstractSoapInterceptor{
	public PDDServiceAttachmentInterceptor()
	{
		super(Phase.USER_PROTOCOL);
		//super(Phase.USER_LOGICAL);
	}

	@Override
	public void handleMessage(SoapMessage message) throws Fault {
		Collection<Attachment> attachments = message.getAttachments();
		if (attachments != null)
			PDDServiceAttachmentManager.set(attachments);
		
	}

}
