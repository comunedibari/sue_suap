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
 * Menu.java
 *
 * Created on November 9, 2004, 4:31 PM
 */

package it.people.layout;

import it.people.*;
import it.people.db.DBConnector;
import it.people.process.view.ConcreteView;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import org.apache.log4j.Category;

/**
 * 
 * @author manelli
 */
public class LayoutMenu extends LayoutObject {
    private Category cat = Category.getInstance(LayoutMenu.class.getName());

    /** Creates a new instance of Menu */
    public LayoutMenu() {
    }

    public LayoutMenu(int idServizio, ConcreteView theCurrentView) {
	setActivityList(theCurrentView.getActivities());
	setConnectedServices(idServizio);
	setContextElements(idServizio);
    }

    private ArrayList connectedServices;
    private ArrayList activityList;
    private ArrayList contextElements;

    public ArrayList getConnectedServices() {
	return connectedServices;
    }

    public void setConnectedServices(ArrayList connectedServices) {
	this.connectedServices = connectedServices;
    }

    protected void setConnectedServices(int serviceId) {
	Connection conn = null;
	ResultSet rs = null;
	Statement stat = null;

	this.connectedServices = new ArrayList();

	// devo cercare sul db in base al serviceId quali sono i servizi
	// connessi
	String queryString;

	try {
	    conn = DBConnector.getInstance().connect(DBConnector.FEDB);

	    queryString = "SELECT * FROM connected_services WHERE "
		    + "serviceid = " + serviceId;
	    stat = conn.createStatement();
	    rs = stat.executeQuery(queryString);

	    while (rs.next()) {
		ConnectedService temp = new ConnectedService();
		temp.setLabel(rs.getString("label"));
		temp.setUri(rs.getString("uri"));
		connectedServices.add(temp);
	    }
	} catch (Exception ex) {
	    cat.error(ex);
	} finally {
	    if (rs != null) {
		try {
		    rs.close();
		} catch (Exception ex) {
		}
	    }
	    if (stat != null) {
		try {
		    stat.close();
		} catch (Exception ex) {
		}
	    }
	    try {
		if (conn != null) {
		    conn.close();
		}
	    } catch (Exception ex) {
	    }
	}
    }

    public ArrayList getContextElements() {
	return contextElements;
    }

    public void setContextElements(ArrayList contextElements) {
	this.contextElements = contextElements;
    }

    protected void setContextElements(int serviceId) {
	Connection conn = null;
	Statement stat = null;
	ResultSet rs = null;

	this.contextElements = new ArrayList();

	String queryString;

	try {
	    conn = DBConnector.getInstance().connect(DBConnector.FEDB);

	    queryString = "SELECT * FROM context_elements WHERE "
		    + "serviceid = " + serviceId;
	    stat = conn.createStatement();
	    rs = stat.executeQuery(queryString);

	    while (rs.next()) {
		ContextElement temp = new ContextElement();
		temp.setName(rs.getString("name"));
		contextElements.add(temp);
	    }
	} catch (Exception ex) {
	    cat.error(ex);
	} finally {
	    if (rs != null) {
		try {
		    rs.close();
		} catch (Exception ex) {
		}
	    }
	    if (stat != null) {
		try {
		    stat.close();
		} catch (Exception ex) {
		}
	    }
	    try {
		if (conn != null) {
		    conn.close();
		}
	    } catch (Exception ex) {
	    }
	}
    }

    public ArrayList getActivityList() {
	return activityList;
    }

    public void setActivityList(Activity[] activityArray) {
	this.activityList = new ArrayList();

	for (int i = 0; i < activityArray.length; i++) {
	    this.activityList.add(activityArray[i]);
	}
    }

    public Activity getCurrentActivity() {
	return this.getView().getCurrentActivity();
    }

    public ArrayList getCompletedActivities() {
	ArrayList ret = new ArrayList();
	setActivityList(view.getActivities());

	Iterator iterator;

	iterator = activityList.iterator();
	Activity temp;

	while (iterator.hasNext()) {
	    temp = (Activity) iterator.next();
	    if (temp.isCompleted()) {
		ret.add(temp);
	    }
	}

	return ret;
    }

    /**
     * Ridefinisce il comportamento dell'update di base per includere
     * l'aggiornamento delle activities
     */
    public void update(ConcreteView view) {
	super.update(view);
	this.setActivityList(view.getActivities());
    }
}
