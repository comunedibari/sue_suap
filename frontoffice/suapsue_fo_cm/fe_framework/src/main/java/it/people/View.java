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
/*
 * Created on 27-apr-04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package it.people;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * @author thweb4
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class View implements IView {

    private String id;
    private String name;
    private String home;
    private String activityOrder;
    private ArrayList activities;
    private String[] activityOrderArray;

    public View() {
	id = null;
	name = null;
	home = null;
	activityOrder = null;
	activities = new ArrayList();
	activityOrderArray = null;
    }

    public String[] getActivityOrder() {
	return activityOrderArray;
    }

    public String getHome() {
	return home;
    }

    public String getId() {
	return id;
    }

    public String getName() {
	return name;
    }

    public ArrayList getActivities() {
	return activities;
    }

    public void setActivityOrder(String string) {

	activityOrder = string;
	StringTokenizer tokenizer = new StringTokenizer(string, ",");
	activityOrderArray = new String[tokenizer.countTokens()];
	int idx = 0;
	while (tokenizer.hasMoreTokens()) {
	    String token = tokenizer.nextToken();
	    activityOrderArray[idx] = token.trim();
	    idx++;
	}
    }

    public void setHome(String string) {
	home = string;
    }

    public void setId(String string) {
	id = string;
    }

    public void setName(String string) {
	name = string;
    }

    public void setActivities(ArrayList list) {
	activities = list;
    }

    public IActivity getActivityById(String id) {
	for (int i = 0; i < activities.size(); i++) {
	    IActivity a = (IActivity) activities.get(i);
	    if (a.getId().equals(id))
		return a;
	}
	return null;
    }
}
