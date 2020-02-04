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
package it.people.console.persistence.dao;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.xml.crypto.NoSuchMechanismException;
import org.springframework.util.LinkedCaseInsensitiveMap;

import it.people.console.persistence.jdbc.core.ColumnHeaderInformation;
import it.people.console.persistence.jdbc.core.ColumnMetaData;
import it.people.console.persistence.jdbc.core.EditableRow;
import it.people.console.persistence.jdbc.core.RowMapperResultSetEditableRowWithMetaData;
import it.people.console.persistence.jdbc.core.RowMapperResultSetWithMetaData;
import it.people.console.system.AbstractLogger;
import it.people.core.Logger;

/**
 * 
 * @author Andrea Piemontese - Engineering Ingegneria Informatica S.p.A.
 * @created 30/lug/2012 12:09:54
 *
 */
public class ObjectQueryRunner extends AbstractLogger {


	/**
	 * 
	 * @param objectsList
	 * @param rowColumnsIdentifiers
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> queryForList(List<?> objectsList,
			final List<String> rowColumnsIdentifiers) {

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		
		try {
			
			//Build data
			result = buildData(objectsList, rowColumnsIdentifiers);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 
	 * @param objectsList
	 * @param rowColumnsIdentifiers
	 * @return
	 */
	public static List<EditableRow> queryForListEditableRow(
			List<?> objectsList, final List<String> rowColumnsIdentifiers) {

		List<EditableRow> result = null;
		try {
			
			//Build editable data
			result = buildEditableData(objectsList, rowColumnsIdentifiers);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 
	 * @param objectsList
	 * @param rowColumnsIdentifiers
	 * @param rowColumnsVisibleNames
	 * @return
	 */
	public static RowMapperResultSetWithMetaData queryForListWithMetaData(
			List<?> objectsList, final List<String> rowColumnsIdentifiers,
			final List<String> rowColumnsVisibleNames) {

		RowMapperResultSetWithMetaData result = new RowMapperResultSetWithMetaData();
		
		try {
			result.setData(buildData(objectsList, rowColumnsIdentifiers));
			result.setColumnsAliases(buildColumnAliases(rowColumnsIdentifiers, rowColumnsVisibleNames));
			
			if (!objectsList.isEmpty()) {
				result.setMetaData(buildMetaData(objectsList.get(0), rowColumnsIdentifiers, rowColumnsVisibleNames));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		//result = jdbcTemplate.query(normalizeQuery(query), new RowMapperResultSetExtractorWithMetaData(new ColumnMapRowMapper()));
		
		return result;
	}

	/**
	 * 
	 * @param objectsList a list of object
	 * @param rowColumnsIdentifiers
	 * @param rowColumnsVisibleNames
	 * @return
	 */
	public static RowMapperResultSetEditableRowWithMetaData queryForListEditableRowWithMetaData(
			List <?> objectsList, final List<String> rowColumnsIdentifiers, final List<String> rowColumnsVisibleNames) {

		RowMapperResultSetEditableRowWithMetaData result = new RowMapperResultSetEditableRowWithMetaData();
		
		try {
			result.setData(buildEditableData(objectsList, rowColumnsIdentifiers));
			result.setMetaData(buildMetaData(objectsList.get(0), rowColumnsIdentifiers, rowColumnsVisibleNames));
			result.setColumnsAliases(buildColumnAliases(rowColumnsIdentifiers, rowColumnsVisibleNames));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	

	
	/**
	 * This method builds the "SQL" metadata using an object and its attributes names
	 * 
	 * @param objectList A list of object used for metadata extraction
	 * @param attributesNames the attributes to extract to build metadata (Setter needed)
	 * @param attributesLabels the visible names for attributes
	 * @return 
	 */
	private static Map<String, ColumnMetaData> buildMetaData(Object objInstance,
			List<String> attributesNames, List<String> attributesLabels) throws SecurityException {
		
		//Get class
		Class aclass = objInstance.getClass();

		//Init Metadata MAP
		Map <String, ColumnMetaData> metaData = new HashMap <String, ColumnMetaData>();
		
		Iterator<String> attribIter = attributesNames.iterator();
		Iterator<String> namesIter = attributesLabels.iterator();
		
		while (attribIter.hasNext()) {
			
			String currAttrib = attribIter.next();
			String methodName = "get" + currAttrib.substring(0, 1).toUpperCase() + currAttrib.substring(1);
			String booleanMethodName = "is" + currAttrib.substring(0, 1).toUpperCase() + currAttrib.substring(1);
			
			//GET method return type and return Object
			Method amethod = null;
			try {
				amethod = aclass.getDeclaredMethod(methodName);
			} catch (SecurityException e) {
				throw new SecurityException("Security Exception building metadata for an Object: " 
						+ aclass.toString(), e);
			} catch (NoSuchMethodException e) {
				
				//Try with boolean Name
				try {
					amethod = aclass.getDeclaredMethod(booleanMethodName);
				} catch (SecurityException e1) {
					throw new SecurityException("Security Exception building metadata for an Object: " 
							+ aclass.toString(), e1);
				} catch (NoSuchMethodException e1) {
					throw new NoSuchMechanismException("No such method Exception building metadata for an Object: " 
							+ aclass.toString(), e1);
				}
			}
			
			String returnType = amethod.getReturnType().getCanonicalName();
			
			//Get attributes labels (visible names)
			String visibleName = currAttrib;
			if (namesIter.hasNext()) {
				visibleName = namesIter.next();
			}

			//Add to metadata MAP
			//TODO Gestire java.sql.Types mapping con java returnType, display size, type name
			ColumnMetaData columnMetaData = new ColumnMetaData(currAttrib, visibleName, 0, returnType, 5, "typeName", 0, 0);
			metaData.put(currAttrib, columnMetaData);
			
		}
		
		return metaData;
	}
	
	
	
	
	/**
	 * build columns headers list
	 * 
	 * @param rowColumnsIdentifiers
	 * @return
	 */
	private static List<ColumnHeaderInformation> buildColumnAliases(
			List<String> rowColumnsIdentifiers, List<String> rowColumnsVisibleNames) {
		
		List <ColumnHeaderInformation> result = new ArrayList<ColumnHeaderInformation>();
		Iterator<String> attribIter = rowColumnsIdentifiers.iterator();
		Iterator<String> labelsIter = rowColumnsVisibleNames.iterator();
		
		while (attribIter.hasNext()) {
			String currAttrib = attribIter.next();
			
			//getlabel
			String visibleName = currAttrib;
			if (labelsIter.hasNext()) {
				visibleName = labelsIter.next();
			}
			
			result.add(new ColumnHeaderInformation(currAttrib, visibleName));
		}
		
		return result;
	}

	
	
	/**
	 * This method bulds a raw "data" list from a list of objects and its attributes names
	 * 
	 * @param objectList the list of object for data extraction
	 * @param rowColumnsIdentifiers the columns identifiers (the fields names)
	 * @return
	 * @throws Exception 
	 * 
	 */
	private static List<Map<String, Object>> buildData(List<?> objectList,
			List<String> attributesNames) throws Exception {
		
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		
		//Iterate on objectList and map each column for all rows 
		Iterator <?> objListIter = objectList.iterator();
		LinkedCaseInsensitiveMap <Object> dataRow = null;
		
		while (objListIter.hasNext()) {
			
			Object currObj = objListIter.next();
			try {
				dataRow = buildDataRow(currObj, attributesNames);
			} catch (Exception e) {
				throw new Exception("Exception building data.", e);
			}		
			result.add(dataRow);
		}
		
		return result;
	}


	/**
	 * This method bulds a "editable data" list from a list of objects and its attributes names
	 * 
	 * @param objectList
	 * @param attributesNames
	 * @return
	 * @throws Exception 
	 */
	private static List<EditableRow> buildEditableData(List <?> objectList,
			List<String> attributesNames) throws Exception {
		
		List <EditableRow> result = new ArrayList<EditableRow>();
		
		//Iterate on objectList and map each column for all rows 
		Iterator<?> objListIter = objectList.iterator();
		LinkedCaseInsensitiveMap <Object> dataRow = null;
		
		while (objListIter.hasNext()) {
			
			Object currObj = objListIter.next();
			try {
				dataRow = buildDataRow(currObj, attributesNames);
			} catch (Exception e) {
				throw new Exception("Exception building editable data.", e);
			}		
			//build an editableRow
			EditableRow editableRow = new EditableRow((Map<String,Object>) dataRow);
			editableRow.setEditEnabled(false);
			
			result.add(editableRow);
		}
		return result;
	}
	

	/**
	 * Build a dataRow
	 * 
	 * @param currObj
	 * @param attributesNames
	 * @throws Exception 
	 */
	private static LinkedCaseInsensitiveMap <Object> buildDataRow(Object currObj, List<String> attributesNames) throws Exception {
		
		LinkedCaseInsensitiveMap <Object> row = new LinkedCaseInsensitiveMap<Object>(attributesNames.size());
		
		//Iterate over row attributeNames to map columns in row
		Iterator<String> attribIter = attributesNames.iterator();
		while (attribIter.hasNext()) {
			
			//Get class
			Class aclass = currObj.getClass();
			
			String currAttrib = attribIter.next();
			String methodName = "get" + currAttrib.substring(0, 1).toUpperCase() + currAttrib.substring(1);
			String booleanMethodName = "is" + currAttrib.substring(0, 1).toUpperCase() + currAttrib.substring(1);
			
			//GET method
			Object methodResultObject = null;
			try {
				methodResultObject = reflectionCall(currObj, methodName, new Class[] {}, new Object[] {});
			} catch (Exception e) {
				//Try booleanMethod
				try {
					methodResultObject = reflectionCall(currObj, booleanMethodName, new Class[] {}, new Object[] {});
				} catch (Exception e1) {
					throw new Exception("Exception building Data row.", e);
				}
			}
			
			row.put(currAttrib, methodResultObject);
		}
		return row;
	}
	
	
	/**
	 * Allow for instance call, avoiding certain class circular dependencies. <br />
	 * This method do not call private methods.l
	 * @param objInstance instance on which method is invoked (if null, static call)
	 * @param classname name of the class containing the method 
	 * (can be null - ignored, actually - if instance if provided, must be provided if static call)
	 * @param amethodname name of the method to invoke
	 * @param parameterTypes array of Classes
	 * @param parameters array of Object
	 * @return resulting ArrayList <Object>
	 * @throws Exception if any problem
	 */
	private static Object reflectionCall(final Object objInstance, final String amethodname, final Class[] parameterTypes, final Object[] parameters) throws Exception {
		Object res = null;
		
	    try {
	        
	    	Class aclass = objInstance.getClass();
	        final Method amethod = aclass.getDeclaredMethod(amethodname, parameterTypes);
	    	res = amethod.invoke(objInstance, parameters);
	    
	    } catch (final SecurityException e) {
	        throw new Exception("reflectionCall: Security Exception ", e);
	    } catch (final NoSuchMethodException e) {
	        throw new Exception("reflectionCall: NoSuchMethodException ", e);
	    } catch (final IllegalArgumentException e) {
	        throw new Exception("reflectionCall: IllegalArgumentException ", e);
	    } catch (final IllegalAccessException e) {
	        throw new Exception("reflectionCall: IllegalAccessException ", e);
	    } catch (final InvocationTargetException e) {
	        throw new Exception("reflectionCall: InvocationTargetException", e);
	    } 
	    
	    return res;
	}
	
	
	
}
