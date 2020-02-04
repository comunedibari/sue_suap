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
 * http://www.osor.eu/eupl
 * 
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
**/
package it.people.dbm.utility;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 * @author Riccardo Forafò - Engineering Ingegneria Informatica - Genova
 * 19/apr/2012 18:13:28
 */
public class RevenueAgencyFormularyImporter {

	private static final String EMPTY_STRING = "";
	
	private static Date defaultValidityValue = null;

	static {
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 30);
		calendar.set(Calendar.MONTH, 12);
		calendar.set(Calendar.YEAR, 1900);
		defaultValidityValue = new Date(calendar.getTimeInMillis());
		
	}
	
	public static boolean importFormulary(InputStream xlsToImport, Connection dbConnection) {
		
		boolean result = false;

		PreparedStatement preparedStatement = null;
		PreparedStatement tmpPreparedStatement = null;
		Statement statement = null;
		
		try {

			String cleanQuery = "delete from formulario_ae";
			String insertQuery = "insert into formulario_ae(data_validita, codice_ente, tipologia_ufficio, codice_ufficio, tipologia_ente, denominazione, codice_catastale_comune, data_decorrenza, ufficio_statale) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			String insertTmpQuery = "insert into formulario_ae_tmp(data_validita, codice_ente, tipologia_ufficio, codice_ufficio, tipologia_ente, denominazione, codice_catastale_comune, data_decorrenza, ufficio_statale) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

			String createTempTable = "CREATE TEMPORARY TABLE `formulario_ae_tmp` (";
			createTempTable += "`id` int(10) unsigned NOT NULL AUTO_INCREMENT,";
			createTempTable += "`data_validita` date NOT NULL,";
			createTempTable += "`codice_ente` varchar(10) NOT NULL,";
			createTempTable += "`tipologia_ufficio` varchar(10) DEFAULT NULL,";
			createTempTable += "`codice_ufficio` varchar(10) DEFAULT NULL,";
			createTempTable += "`tipologia_ente` varchar(10) DEFAULT NULL,";
			createTempTable += "`denominazione` varchar(4000) DEFAULT NULL,";
			createTempTable += "`codice_catastale_comune` varchar(10) NOT NULL,";
			createTempTable += "`data_decorrenza` date NOT NULL,";
			createTempTable += "`ufficio_statale` varchar(2) NOT NULL,";
			createTempTable += "PRIMARY KEY (`id`)";
			createTempTable += ") DEFAULT CHARSET=utf8;";			
			
			preparedStatement = dbConnection.prepareStatement(insertQuery);
			tmpPreparedStatement = dbConnection.prepareStatement(insertTmpQuery);
			statement = dbConnection.createStatement();
			statement.executeUpdate(createTempTable);
			
			HSSFWorkbook workBook = new HSSFWorkbook(xlsToImport);
			xlsToImport.close();

			HSSFSheet sheet = workBook.getSheetAt(0);

			int lastRowNum = sheet.getLastRowNum();
			int rowCounter = 0;
			int readRow = 0;

			Iterator<Row> sheetRowsIterator = sheet.rowIterator();
			while(sheetRowsIterator.hasNext()) {
				Row sheetRow = sheetRowsIterator.next();

				if (rowCounter > 5) {
					Date validityValue = getTimestampCellValue(sheetRow.getCell(0));
					String entityCodeValue = getEntityCodeCellValue(sheetRow.getCell(1));
					String officeTypeCell = getOfficeTypeCellValue(sheetRow.getCell(2));
					String officeCodeCell = getOfficeCodeCellValue(sheetRow.getCell(3));
					String entityTypeCell = getEntityTypeCellValue(sheetRow.getCell(4));
					String denominationCell = getDenominationCellValue(sheetRow.getCell(5));
					String municipalityLandRegistryCodeCell = getMunicipalityLandRegistryCodeCellValue(sheetRow.getCell(6));
					Date effectDateCell = getTimestampCellValue(sheetRow.getCell(7));
					String stateOfficeCell = getStateOfficeCellValue(sheetRow.getCell(8));
					
					preparedStatement.setDate(1, validityValue);
					preparedStatement.setString(2, entityCodeValue);
					preparedStatement.setString(3, officeTypeCell);
					preparedStatement.setString(4, officeCodeCell);
					preparedStatement.setString(5, entityTypeCell);
					preparedStatement.setString(6, denominationCell);
					preparedStatement.setString(7, municipalityLandRegistryCodeCell);
					preparedStatement.setDate(8, effectDateCell);
					preparedStatement.setString(9, stateOfficeCell);
					preparedStatement.addBatch();
	
					
					tmpPreparedStatement.setDate(1, validityValue);
					tmpPreparedStatement.setString(2, entityCodeValue);
					tmpPreparedStatement.setString(3, officeTypeCell);
					tmpPreparedStatement.setString(4, officeCodeCell);
					tmpPreparedStatement.setString(5, entityTypeCell);
					tmpPreparedStatement.setString(6, denominationCell);
					tmpPreparedStatement.setString(7, municipalityLandRegistryCodeCell);
					tmpPreparedStatement.setDate(8, effectDateCell);
					tmpPreparedStatement.setString(9, stateOfficeCell);
					tmpPreparedStatement.addBatch();
					readRow++;
				}
				rowCounter++;
				
			}
			
			int[] tmpExecuteResult = tmpPreparedStatement.executeBatch();
			if (tmpExecuteResult.length == readRow) {
				statement.executeUpdate(cleanQuery);
				preparedStatement.executeBatch();
				result = true;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (tmpPreparedStatement != null) {
					tmpPreparedStatement.close();
				}
				if (statement != null) {
					statement.close();
				}
			} catch(Exception e) {
				
			}
			
		}
		
		return result;
		
	}
	
	private static Date getTimestampCellValue(Cell cell) {
		
		if (cell == null || (cell != null && cell.getStringCellValue() == null)) {
			return defaultValidityValue;
		} else {

			String cellValue = cell.getStringCellValue().replace("9999", "1800");
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
			Calendar calendar = Calendar.getInstance();
			try {
				calendar.setTime(dateFormat.parse(cellValue));
				return new Date(calendar.getTimeInMillis());
			} catch (ParseException e) {
				return defaultValidityValue;
			}
		}
		
	}

	private static String getEntityCodeCellValue(Cell cell) {
		
		return getStringCellValue(cell);
		
	}

	private static String getOfficeTypeCellValue(Cell cell) {
		
		return getStringCellValue(cell);
		
	}

	private static String getOfficeCodeCellValue(Cell cell) {
		
		return getStringCellValue(cell);
		
	}

	private static String getEntityTypeCellValue(Cell cell) {
		
		return getStringCellValue(cell);
		
	}

	private static String getDenominationCellValue(Cell cell) {
		
		return getStringCellValue(cell);
		
	}

	private static String getMunicipalityLandRegistryCodeCellValue(Cell cell) {
		
		return getStringCellValue(cell);
		
	}

	private static String getStateOfficeCellValue(Cell cell) {
		
		return getStringCellValue(cell);
		
	}
	
	private static String getStringCellValue(Cell cell) {
		
		if (cell == null) {
			return EMPTY_STRING;
		} else {
			return cell.getStringCellValue();
		}
		
	}
	
}
