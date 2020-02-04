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
 * Creato il 27-dic-2006 da Cedaf s.r.l.
 *
 */
package it.people.db.fedb;

import it.people.exceptions.PeopleDBException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;

/**
 * @author Michele Fabbri - Cedaf s.r.l.
 * 
 */
public class AreaFactory extends AbstractFactory {

    private static Logger logger = Logger.getLogger(AreaFactory.class);

    /**
     * 
     */
    public AreaFactory() {
    }

    public Collection getEnabledAreas(String communeId)
	    throws PeopleDBException {
	try {
	    String query = "SELECT DISTINCT attivita" + " FROM service "
		    + " WHERE communeId = '" + communeId + "' "
		    + " AND statusid = 1" + " ORDER BY attivita";

	    Collection aree = getCollection(query);
	    return aree;

	} catch (Exception ex) {
	    logger.error(ex);
	    throw new PeopleDBException(
		    "Impossibile caricare l'elenco dei servizi");
	}
    }

    protected Object getFromResultSet(ResultSet resultSet, ArrayList list)
	    throws SQLException {
	Area area = new Area();
	area.setDescription(resultSet.getString("attivita"));
	return area;
    }
}
