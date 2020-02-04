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
package it.people.backend.webservice.providers.codiciistat;

import it.people.backend.webservice.common.AbstractProvider;
import it.people.backend.webservice.common.Constants; 
import it.people.backend.webservice.persistence.PersistenceException;
import it.people.backend.webservice.utils.XMLUtils;
import it.people.servizi.condivisi.ricercacodiceattivitaistat.oggetti.CodiceAttivitaISTAT;
import it.people.servizi.condivisi.ricercacodiceattivitaistat.oggetti.ProcessData;
 
import java.io.IOException; 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InvalidPropertiesFormatException;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A. - Sede di Genova
 *
 */
public class RicercaCodiciAttivitaISTATProvider extends AbstractProvider  {

	private static Logger _logger = LoggerFactory.getLogger(RicercaCodiciAttivitaISTATProvider.class);
	
	public String process(String xmlIn) {

		ProcessData data = (ProcessData)XMLUtils.unmarshall(ProcessData.class, xmlIn);
		
		Connection connection = null;
		data.getCodiceAttivitaISTAT().clear();
		
		_logger.debug("Searched ISTAT activity code = " + data.getTestoRicerca());

		try {

			String queryKeyToUse = Constants.ISTAT_CODES_QUERY_KEY;
			_logger.debug("Query = " + getQuery(queryKeyToUse));
			
			connection = getConnection();
			
			PreparedStatement preparedStatement = connection.prepareStatement(getQuery(queryKeyToUse));
			
			preparedStatement.setString(1, "%" + data.getTestoRicerca() + "%");
			
			ResultSet resultSet = preparedStatement.executeQuery();

			while(resultSet.next()) {
				
				CodiceAttivitaISTAT codiceAttivitaISTAT = new CodiceAttivitaISTAT();
				codiceAttivitaISTAT.setCodiceAttivita(resultSet.getString("codice"));
				codiceAttivitaISTAT.setDescrizioneAttivita(resultSet.getString("descrizione"));
				
				data.addCodiceAttivitaISTAT(codiceAttivitaISTAT);
				
			}
			
		} catch (InvalidPropertiesFormatException e) {
			_logger.error("", e);
		} catch (SQLException e) {
			_logger.error("", e);
		} catch (IOException e) {
			_logger.error("", e);
		} catch (PersistenceException e) {
			_logger.error("", e);
		}
		finally {
			try {
				if (connection != null && !connection.isClosed()) {
						connection.close();
				}
			} catch (SQLException e) {
				_logger.error("", e);
			}
		}

		return XMLUtils.marshall(data) ;

    }

}
