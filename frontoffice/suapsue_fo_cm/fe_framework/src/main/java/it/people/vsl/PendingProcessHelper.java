/**
 * 
 */
package it.people.vsl;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import it.people.core.PeopleContext;
import it.people.core.ProcessManager;
import it.people.core.persistence.exception.peopleException;
import it.people.process.AbstractPplProcess;

/**
 * @author Riccardo Forafò - Engineering Ingegneria Informatica - Genova
 *         07/lug/2012 16:59:18
 */
public class PendingProcessHelper {

    private static Logger logger = LogManager
	    .getLogger(PendingProcessHelper.class);

    private PendingProcessHelper() {

    }

    public static void updateOffLineSignData(PipelineDataHolder p_itemToProcess) {

	PendingProcessHelper
		.updateOffLineSignData((AbstractPplProcess) p_itemToProcess
			.getPlineData().getAttribute(
				PipelineDataImpl.EDITABLEPROCESS_PARAMNAME));

    }

    public static void updateOffLineSignData(AbstractPplProcess process) {

	try {
	    PeopleContext peopleContext = process.getContext();

	    // Clear off line sign data columns
	    process.setOffLineSignDownloadedDocumentHash(null);
	    process.setWaitingForOffLineSignedDocument(false);
	    ProcessManager.getInstance().set(peopleContext, process);
	} catch (peopleException e) {
	    logger.error("Errore while cleaning off line sign data.", e);
	} catch (Exception e) {
	    logger.error("Errore while cleaning off line sign data.", e);
	}

    }

}
