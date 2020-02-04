/**
 * Copyright (c) 2011, Regione Emilia-Romagna, Italy
 *  
 * Licensed under the EUPL, Version 1.1 or - as soon they
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the
 * Licence.
 * 
 * For convenience a plain text copy of the English version
 * of the Licence can be found in the file LICENCE.txt in
 * the top-level directory of this software distribution.
 * 
 * You may obtain a copy of the Licence in any of 22 European
 * Languages at:
 * 
 * http://joinup.ec.europa.eu/software/page/eupl
 * 
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * 
 * This product includes software developed by Yale University
 * 
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
**/
package it.people.feservice.utils;

import java.io.IOException;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import it.people.feservice.beans.OnlineHelpActivityData;
import it.people.feservice.beans.OnlineHelpStepData;
import it.people.feservice.beans.OnlineHelpViewData;
import it.people.feservice.beans.ServiceOnlineHelpWorkflowElements;

/**
 * @author Riccardo Forafò - Engineering Ingegneria Informatica - Genova
 *         01/set/2011 20.55.14
 */
public class WorkflowDocumentReader {

	private static Logger logger = LoggerFactory.getLogger(WorkflowDocumentReader.class);

	public static ServiceOnlineHelpWorkflowElements getServiceWorkflowForOnlineHelp(String servicePackage,
			String basePath) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {

		ServiceOnlineHelpWorkflowElements serviceOnlineHelpWorkflowElements = new ServiceOnlineHelpWorkflowElements();

		if (logger.isDebugEnabled()) {
			logger.debug("service package = " + servicePackage);
		}

		String workflowPath = basePath + servicePackage.replaceAll("\\.", "/") + "/" + "risorse" + "/" + "workflow.xml";

		if (logger.isDebugEnabled()) {
			logger.debug("workflow path = " + workflowPath);
		}

		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(workflowPath);

		NodeList views = document.getElementsByTagName("VIEW");
		Vector<OnlineHelpViewData> onlineHelpViewDataVector = new Vector<OnlineHelpViewData>();
		for (int index = 0; index < views.getLength(); index++) {
			Node view = views.item(index);

			XPath xpath = XPathFactory.newInstance().newXPath();

			String viewId = view.getAttributes().getNamedItem("id").getNodeValue();
			String viewName = xpath.evaluate("default/name", view);

			OnlineHelpViewData onlineHelpViewData = new OnlineHelpViewData();
			onlineHelpViewData.setId(viewId);
			onlineHelpViewData.setName(viewName);

			if (logger.isInfoEnabled()) {
				logger.info("view = " + viewId + " " + viewName);
			}

			NodeList viewChilds = view.getChildNodes();

			if (logger.isDebugEnabled()) {
				logger.debug("viewChilds size = " + viewChilds.getLength());
			}

			Node activities = null;
			for (int index1 = 0; index1 < viewChilds.getLength(); index1++) {
				Node viewChild = viewChilds.item(index1);

				if (logger.isDebugEnabled()) {
					logger.debug("viewChild = " + viewChild.getNodeType() + " " + viewChild.getNodeName());
				}
				if (new Short(viewChild.getNodeType()).equals(Node.ELEMENT_NODE)
						&& viewChild.getNodeName().equals("ACTIVITIES")) {
					if (logger.isDebugEnabled()) {
						logger.debug("found activities");
					}
					activities = viewChild;
					break;
				}
			}
			if (activities != null) {
				Vector<OnlineHelpActivityData> onlineHelpActivityDataVector = new Vector<OnlineHelpActivityData>();
				if (logger.isDebugEnabled()) {
					logger.debug("activities = " + activities.getChildNodes().getLength());
				}
				NodeList activitiesList = activities.getChildNodes();
				for (int index1 = 0; index1 < activitiesList.getLength(); index1++) {
					Node activitiesChild = activitiesList.item(index1);
					if (logger.isDebugEnabled()) {
						logger.debug("activitiesChild = " + activitiesChild.getNodeType() + " "
								+ activitiesChild.getNodeName());
					}
					if (new Short(activitiesChild.getNodeType()).equals(Node.ELEMENT_NODE)
							&& activitiesChild.getNodeName().equals("ACTIVITY")) {
						if (logger.isDebugEnabled()) {
							logger.debug("found activity");
						}
						Node activity = activitiesChild;
						String activityId = activity.getAttributes().getNamedItem("id").getNodeValue();
						String activityName = xpath.evaluate("default/name", activity);
						if (logger.isInfoEnabled()) {
							logger.info("found activity = " + activityId + " " + activityName);
						}
						OnlineHelpActivityData onlineHelpActivityData = new OnlineHelpActivityData();
						onlineHelpActivityData.setId(activityId);
						onlineHelpActivityData.setName(activityName);
						Node steps = null;
						NodeList activityStepsList = activity.getChildNodes();
						for (int index2 = 0; index2 < activityStepsList.getLength(); index2++) {
							Node activityStepsChild = activityStepsList.item(index2);
							if (logger.isDebugEnabled()) {
								logger.debug("activityStepsChild = " + activityStepsChild.getNodeType() + " "
										+ activityStepsChild.getNodeName());
							}
							if (new Short(activityStepsChild.getNodeType()).equals(Node.ELEMENT_NODE)
									&& activityStepsChild.getNodeName().equals("STEPS")) {
								if (logger.isDebugEnabled()) {
									logger.debug("found steps");
								}
								steps = activityStepsChild;
								break;
							}
						}
						if (steps != null) {
							Vector<OnlineHelpStepData> onlineHelpStepDataVector = new Vector<OnlineHelpStepData>();
							if (logger.isDebugEnabled()) {
								logger.debug("steps = " + steps.getChildNodes().getLength());
							}
							NodeList stepsList = steps.getChildNodes();
							for (int index2 = 0; index2 < stepsList.getLength(); index2++) {
								Node stepsChild = stepsList.item(index2);
								if (logger.isDebugEnabled()) {
									logger.debug("stepsChild = " + stepsChild.getNodeType() + " "
											+ stepsChild.getNodeName());
								}
								if (new Short(stepsChild.getNodeType()).equals(Node.ELEMENT_NODE)
										&& stepsChild.getNodeName().equals("STEP")) {
									if (logger.isDebugEnabled()) {
										logger.debug("found step");
									}
									Node step = stepsChild;
									String stepId = step.getAttributes().getNamedItem("id").getNodeValue();
									String stepName = xpath.evaluate("default/name", step);
									if (logger.isInfoEnabled()) {
										logger.info("found step = " + stepId + " " + stepName);
									}
									OnlineHelpStepData onlineHelpStepData = new OnlineHelpStepData(stepId, stepName);
									onlineHelpStepDataVector.add(onlineHelpStepData);
								}
							} // for step
							onlineHelpActivityData.setSteps(onlineHelpStepDataVector
									.toArray(new OnlineHelpStepData[onlineHelpStepDataVector.size()]));
						}
						onlineHelpActivityDataVector.add(onlineHelpActivityData);
					}
				} // for activities
				onlineHelpViewData.setActivities(onlineHelpActivityDataVector
						.toArray(new OnlineHelpActivityData[onlineHelpActivityDataVector.size()]));
			}
			onlineHelpViewDataVector.add(onlineHelpViewData);
		} // for view
		serviceOnlineHelpWorkflowElements
				.setViews(onlineHelpViewDataVector.toArray(new OnlineHelpViewData[onlineHelpViewDataVector.size()]));

		return serviceOnlineHelpWorkflowElements;

	}

}
