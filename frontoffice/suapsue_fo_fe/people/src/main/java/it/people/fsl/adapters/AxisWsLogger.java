package it.people.fsl.adapters;
import org.apache.axis.AxisFault;
import org.apache.axis.MessageContext;
import org.apache.axis.handlers.BasicHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class AxisWsLogger extends BasicHandler
{
	private static final long serialVersionUID = 9142316616529304933L;
	private static final Logger logger = LoggerFactory.getLogger(AxisWsLogger.class.getName());
	@Override
	public void invoke(MessageContext msgContext) throws AxisFault
	{
		if( logger.isDebugEnabled() )
		{
			String requestMessage = "";
			String responseMessage = "";
			String uriMessage = "";
			if(msgContext.getRequestMessage() != null && StringUtils.hasText(msgContext.getRequestMessage().getSOAPPartAsString())) 
			{
				uriMessage = msgContext.getTargetService();
				requestMessage = msgContext.getRequestMessage().getSOAPPartAsString();
				logger.debug("Request [{}] [{}] [{}]", uriMessage, msgContext.getOperation().getName(), requestMessage);
			} 
			if(msgContext.getResponseMessage() != null && StringUtils.hasText(msgContext.getResponseMessage().getSOAPPartAsString())) 
			{
				responseMessage = msgContext.getResponseMessage().getSOAPPartAsString();
				logger.debug("Response [{}] [{}] [{}]", uriMessage, msgContext.getOperation().getName(), responseMessage);
			}
//			if( StringUtils.hasText(requestMessage) && StringUtils.hasText(responseMessage) )
//			{
//			
//				logger.debug("Ricevuta questa response [{}] per questa request [{}]", responseMessage, requestMessage);
//			}
		}
	}
}