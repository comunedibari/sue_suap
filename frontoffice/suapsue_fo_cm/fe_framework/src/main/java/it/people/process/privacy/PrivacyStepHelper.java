/**
 * 
 */
package it.people.process.privacy;

import java.util.Locale;

import javax.servlet.http.HttpSession;

import it.people.City;
import it.people.IActivity;
import it.people.IStep;
import it.people.IView;
import it.people.PeopleConstants;
import it.people.Step;
import it.people.StepState;
import it.people.db.fedb.Service;
import it.people.layout.multicommune.MultiCommunePath;
import it.people.util.MessageBundleHelper;
import it.people.Process;

/**
 * @author Riccardo Forafò - Engineering Ingegneria Informatica - Genova
 *         22/giu/2012 18:12:30
 */
public class PrivacyStepHelper {

    @SuppressWarnings("unchecked")
    public static void initializePivacyStep(Service serviceConf,
	    Process process, HttpSession session, City commune) {

	if (serviceConf.isShowPrivacyDisclaimer()) {

	    String jspPath = PrivacyStep.DEFAULT_JSP_PATH;
	    if (session != null) {
		MultiCommunePath multiCommunePath = new MultiCommunePath(
			session, PrivacyStep.JSP_PATH_ROOT);
		String buffer = multiCommunePath.getPath(
			PrivacyStep.JSP_NESTED_PATH, PrivacyStep.JSP_NAME);
		if (buffer != null && !buffer.equalsIgnoreCase("")) {
		    jspPath = buffer;
		}
	    }

	    String stepName = PrivacyStep.STEP_DEFAULT_NAME;
	    if (process != null && commune != null) {
		Locale locale = (Locale) session
			.getAttribute(PeopleConstants.USER_LOCALE_KEY);
		if (locale == null) {
		    locale = Locale.getDefault();
		}
		String buffer = MessageBundleHelper.message(
			PrivacyStep.STEP_NAME_KEY, null, process.getName(),
			commune.getKey(), locale);
		if (buffer != null && !buffer.equalsIgnoreCase("")) {
		    stepName = buffer;
		}
	    }

	    IView firstView = process.getView();
	    IActivity firstActivity = (IActivity) process.getView()
		    .getActivities().get(0);

	    IStep prePrivacyDisclaimerStep = firstActivity
		    .getStepById(Step.PRE_PRIVACY_DISCLAIMER_STEP_ID);

	    IStep privacyStep = new PrivacyStep();
	    privacyStep.setId(Step.PRIVACY_DISCLAIMER_STEP_ID);
	    privacyStep.setJspPath(jspPath);
	    privacyStep.setName(stepName);
	    privacyStep.setParentView(firstView);
	    privacyStep.setDto(PrivacyStep.STEP_DTO_CLASS_NAME);
	    firstActivity.getStepList().ensureCapacity(
		    firstActivity.getStepList().size() + 1);
	    if (prePrivacyDisclaimerStep != null) {
		firstActivity.getStepList().add(1, privacyStep);
	    } else {
		firstActivity.getStepList().add(0, privacyStep);
	    }
	    privacyStep.setState(StepState.ACTIVE);

	    String newStepOrder = "";
	    if (prePrivacyDisclaimerStep != null) {
		newStepOrder = prePrivacyDisclaimerStep.getId() + ", ";
	    }

	    newStepOrder += privacyStep.getId();
	    for (int index = 0; index < firstActivity.getStepOrder().length; index++) {
		String stepId = firstActivity.getStepOrder()[index];
		if (!stepId
			.equalsIgnoreCase(Step.PRE_PRIVACY_DISCLAIMER_STEP_ID)) {
		    newStepOrder += "," + stepId;
		}
	    }
	    firstActivity.setStepOrder(newStepOrder);
	}

    }

}
