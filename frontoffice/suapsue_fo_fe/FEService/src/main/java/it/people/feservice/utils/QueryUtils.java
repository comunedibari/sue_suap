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

import it.people.feservice.beans.IndicatorFilter;
import it.people.feservice.beans.ProcessFilter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * 
 * @author Andrea Piemontese - Engineering Ingegneria Informatica S.p.A.
 * @created 17/set/2012
 * 
 */
public class QueryUtils {

	//Unbound value for comboboxes usually means "everything"
	private static final String UNBOUND_VALUE = "9999";
	
	private static Logger logger = LoggerFactory.getLogger(QueryUtils.class);
	
	
	//Static initializer to escape special charachers in SQL longtext
	private static final HashMap<String,String> sqlTokens;
	private static Pattern sqlTokenPattern;

	static {           
	    String[][] search_regex_replacement = new String[][]
	    {
	           //search string   search regex   sql replacement regex
	            {   "\u0000"    ,  "\\x00"     ,   "\\\\0"    },
	            {   "'"         ,  "'"         ,   "\\\\'"    },
	            {   "\""        ,  "\""        ,   "\\\\\""   },
	            {   "\b"        ,  "\\x08"     ,   "\\\\b"    },
	            {   "\n"        ,  "\\n"       ,   "\\\\n"    },
	            {   "\r"        ,  "\\r"       ,   "\\\\r"    },
	            {   "\t"        ,  "\\t"       ,   "\\\\t"    },
	            {   "\u001A"    ,  "\\x1A"     ,   "\\\\Z"    },
	            {   "\\"        ,  "\\\\"      ,   "\\\\\\\\" }
	    };

	    sqlTokens = new HashMap<String,String>();
	    String patternStr = "";
	    for (String[] srr : search_regex_replacement)
	    {
	        sqlTokens.put(srr[0], srr[2]);
	        patternStr += ((patternStr.length() == 0) ? "" : "|") + srr[1];            
	    }
	    sqlTokenPattern = Pattern.compile('(' + patternStr + ')');
	}

	/**
	 * 
	 * @param s the string including the special character not supported in MySQL
	 * @return the string with special characters escaped and ready to be used in INSERT MySql statements.
	 */
	public static String escapeSQLSpecialChars(String s)
	{
	    Matcher matcher = sqlTokenPattern.matcher(s);
	    StringBuffer sb = new StringBuffer();
	    while(matcher.find())
	    {
	        matcher.appendReplacement(sb, sqlTokens.get(matcher.group(1)));
	    }
	    matcher.appendTail(sb);
	    return sb.toString();
	}
	
	
	
	
	
	/**
	 * Return a string composed of a variable number of PreparedStatement placeholder
	 * Example: with input 3 will return "?, ?, ?"
	 * @param length the number of placeholders in the output string
	 * @return
	 */
	public static String preparePlaceHolders(int length) {
	    StringBuilder builder = new StringBuilder();
	    for (int i = 0; i < length;) {
	        builder.append("?");
	        if (++i < length) {
	            builder.append(", ");
	        }
	    }
	    return builder.toString();
	}
	
	
	/**
	 * Format the Processes Query string inserting placeholders to match params number
	 * @param query
	 * @param indicatorFilter
	 */
	 public static String buildProcessesQueryPlaceholders(String query, String[] selectedUsers, String[] selectedNodes, boolean partialQuery) {
		
		String nodesPlaceholders = StringUtils.preparePlaceHolders(selectedNodes.length);
		String usersPlaceholders = StringUtils.preparePlaceHolders(selectedUsers.length);

		String result = null;
		
		//Format query string 
		    if (partialQuery)
			      result = String.format(query, new Object[] { nodesPlaceholders, usersPlaceholders });
			    else {
			      result = String.format(query, new Object[] { nodesPlaceholders, usersPlaceholders, 
			        nodesPlaceholders, usersPlaceholders, nodesPlaceholders, usersPlaceholders });
			    }

		return result;
	}
	 
 
	/**
	 * Format the query Indicator Query string inserting placeholders to match params number
	 * @param query
	 * @param indicatorFilter
	 */
	public static String buildIndicatorsQueryPlaceholders(String query, String[] selectedEnti, String[] selectedAttivita) {
		
		String attivitaPlaceholders = StringUtils.preparePlaceHolders(selectedAttivita.length);
		String entiPlaceholders = StringUtils.preparePlaceHolders(selectedEnti.length);

		//Format query string 
		String result = String.format(query, new Object[] { entiPlaceholders, attivitaPlaceholders, entiPlaceholders, 
			      attivitaPlaceholders, entiPlaceholders, attivitaPlaceholders });

		return result;
	}
	

	/**
	 * 
	 * @param ps
	 * @param indicatorFilter
	 * @param lowerPageLimit
	 * @param pageSize
	 * @param selectedEnti
	 * @param selectedAttivita
	 * @param setLimitParams 
	 * @throws SQLException
	 */
	  public static void setIndicatorsQueryParams(PreparedStatement ps, IndicatorFilter indicatorFilter, int lowerPageLimit, int pageSize, String[] selectedEnti, String[] selectedAttivita, boolean setLimitParams)
		    throws SQLException
		  {
		    int filledPlaceholder = 0;
		    String mornOpenTime = indicatorFilter.getOfficeMorningOpeningTime();
		    String mornCloseTime = indicatorFilter.getOfficeMorningClosingTime();

		    ps.setString(1, mornOpenTime);
		    ps.setString(2, mornCloseTime);
		    filledPlaceholder += 2;

		    filledPlaceholder++;
		    if (selectedEnti[0].equals("*"))
		      ps.setBoolean(filledPlaceholder, true);
		    else {
		      ps.setBoolean(filledPlaceholder, false);
		    }

		    for (int i = 0; i < selectedEnti.length; i++) {
		      filledPlaceholder++;
		      ps.setString(filledPlaceholder, selectedEnti[i]);
		    }

		    filledPlaceholder++;
		    if (selectedAttivita[0].equals("*"))
		      ps.setBoolean(filledPlaceholder, true);
		    else {
		      ps.setBoolean(filledPlaceholder, false);
		    }

		    for (int i = 0; i < selectedAttivita.length; i++) {
		      filledPlaceholder++;
		      ps.setString(filledPlaceholder, selectedAttivita[i]);
		    }

		    ps.setString(filledPlaceholder + 1, indicatorFilter.getFromData());
		    ps.setString(filledPlaceholder + 2, indicatorFilter.getToData());
		    filledPlaceholder += 2;

		    ps.setString(filledPlaceholder + 1, mornOpenTime);
		    ps.setString(filledPlaceholder + 2, mornCloseTime);
		    filledPlaceholder += 2;

		    filledPlaceholder++;
		    if (selectedEnti[0].equals("*"))
		      ps.setBoolean(filledPlaceholder, true);
		    else {
		      ps.setBoolean(filledPlaceholder, false);
		    }

		    for (int i = 0; i < selectedEnti.length; i++) {
		      filledPlaceholder++;
		      ps.setString(filledPlaceholder, selectedEnti[i]);
		    }

		    filledPlaceholder++;
		    if (selectedAttivita[0].equals("*"))
		      ps.setBoolean(filledPlaceholder, true);
		    else {
		      ps.setBoolean(filledPlaceholder, false);
		    }

		    for (int i = 0; i < selectedAttivita.length; i++) {
		      filledPlaceholder++;
		      ps.setString(filledPlaceholder, selectedAttivita[i]);
		    }

		    ps.setString(filledPlaceholder + 1, indicatorFilter.getFromData());
		    ps.setString(filledPlaceholder + 2, indicatorFilter.getToData());
		    filledPlaceholder += 2;

		    ps.setString(filledPlaceholder + 1, mornOpenTime);
		    ps.setString(filledPlaceholder + 2, mornCloseTime);
		    filledPlaceholder += 2;

		    filledPlaceholder++;
		    if (selectedEnti[0].equals("*"))
		      ps.setBoolean(filledPlaceholder, true);
		    else {
		      ps.setBoolean(filledPlaceholder, false);
		    }

		    for (int i = 0; i < selectedEnti.length; i++) {
		      filledPlaceholder++;
		      ps.setString(filledPlaceholder, selectedEnti[i]);
		    }

		    filledPlaceholder++;
		    if (selectedAttivita[0].equals("*"))
		      ps.setBoolean(filledPlaceholder, true);
		    else {
		      ps.setBoolean(filledPlaceholder, false);
		    }

		    for (int i = 0; i < selectedAttivita.length; i++) {
		      filledPlaceholder++;
		      ps.setString(filledPlaceholder, selectedAttivita[i]);
		    }

		    ps.setString(filledPlaceholder + 1, indicatorFilter.getFromData());
		    ps.setString(filledPlaceholder + 2, indicatorFilter.getToData());
		    filledPlaceholder += 2;

		    if (setLimitParams) {
		      ps.setInt(filledPlaceholder + 1, lowerPageLimit);
		      ps.setInt(filledPlaceholder + 2, pageSize);
		      filledPlaceholder += 2;
		    }
		  }

	
	
	/**
	 * 
	 * @param ps
	 * @param indicatorFilter
	 * @param lowerPageLimit
	 * @param pageSize
	 * @param selectedUsers
	 * @param selectedNodes
	 * @param setLimitParams
	 * @param partialQuery if true
	 * @param queryToFill use QUERY_NAME_ID to fill parameters for the right Query
	 * @throws SQLException
	 */
	public static void setProcessesQueryParams(PreparedStatement ps, ProcessFilter indicatorFilter,
			int lowerPageLimit, int pageSize, String[] selectedUsers,
			String[] selectedNodes, boolean setLimitParams, int idQueryToFill) throws SQLException {
		
	    	int filledPlaceholder = 0;
	    	
	    	//Query to fill
	    	int queryToFill = 1; 
	    	switch (idQueryToFill) {
	    	
				case FEInterfaceConstants.SUBMITTED_PROCESSES_QUERY_ID: 
				case FEInterfaceConstants.PENDING_PROCESSES_QUERY_ID: 
				case FEInterfaceConstants.NOT_SUBMITTABLE_PROCESSES_QUERY_ID:
					queryToFill = 1;
					break;
				
				case FEInterfaceConstants.COUNT_PROCESSES_QUERY_ID:
					queryToFill = 3;
					
					break;
				case FEInterfaceConstants.PAGED_PROCESSES_QUERY_ID: 	
					queryToFill = 3;
					break;
				default:
					queryToFill = 3;
					break;
			}
	    	
	    	//Fill params
	    	for (int queryNum = 0; queryNum < queryToFill; queryNum++) {
	    		
				// Enable or disable first Query using process type
				if ( ((queryNum == 0) && (queryToFill == 3)) 
						|| (idQueryToFill == FEInterfaceConstants.SUBMITTED_PROCESSES_QUERY_ID)) {
					if (indicatorFilter.getOnlySubmitted() == 1) {
						ps.setBoolean(filledPlaceholder + 1, true);
						filledPlaceholder += 1;
					} else {
						ps.setBoolean(filledPlaceholder + 1, false);
						filledPlaceholder += 1;
					}
				}
	
				// Enable or second first Query using process type
				if ( ((queryNum == 1) && (queryToFill == 3)) 
						|| (idQueryToFill == FEInterfaceConstants.PENDING_PROCESSES_QUERY_ID)) {
					if (indicatorFilter.getOnlyPending() == 1) {
						ps.setBoolean(filledPlaceholder + 1, true);
						filledPlaceholder += 1;
					} else {
						ps.setBoolean(filledPlaceholder + 1, false);
						filledPlaceholder += 1;
					}
				}
	
				// Enable or third first Query using process type
				if (((queryNum == 2) && (queryToFill == 3))  
						|| (idQueryToFill == FEInterfaceConstants.NOT_SUBMITTABLE_PROCESSES_QUERY_ID)) {
					if (indicatorFilter.getOnlyNotSubmittable() == 1) {
						ps.setBoolean(filledPlaceholder + 1, true);
						filledPlaceholder += 1;
					} else {
						ps.setBoolean(filledPlaceholder + 1, false);
						filledPlaceholder += 1;
					}
				}

				//Insert date and time (and boolean to enable or disable filter)
	    		if (indicatorFilter.getFromData().isEmpty() && indicatorFilter.getToData().isEmpty()) {
	    			ps.setBoolean(filledPlaceholder+1, true);
	    		} 
	    		else {
	    			ps.setBoolean(filledPlaceholder+1, false);
	    		}
	    		filledPlaceholder +=1;
	    		
				ps.setString(filledPlaceholder + 1, indicatorFilter.getFromData());
				ps.setString(filledPlaceholder + 2, indicatorFilter.getToData());
				filledPlaceholder +=2;
				
				// "older than X days" filter
				if (indicatorFilter.getOlderThanDays() < 0) {
					ps.setBoolean(filledPlaceholder+1, true);
					ps.setInt(filledPlaceholder+2, indicatorFilter.getOlderThanDays());
				}
				else {
	    			ps.setBoolean(filledPlaceholder+1, false);
	    			ps.setInt(filledPlaceholder+2, indicatorFilter.getOlderThanDays());
	    		}
	    		filledPlaceholder +=2;
				
		    	
				//Query for all Enti?
				filledPlaceholder +=1;
				if (selectedNodes[0].equals(UNBOUND_VALUE)) {
					ps.setBoolean(filledPlaceholder, true);
				} else {
					ps.setBoolean(filledPlaceholder, false);
				}
				//Set selectedEnti
				for (int i=0; i < selectedNodes.length; i++) {
					filledPlaceholder +=1;
					ps.setString(filledPlaceholder, selectedNodes[i]);
				}
				
				//Query for all Users?
				filledPlaceholder +=1;
				if (selectedUsers[0].equals(UNBOUND_VALUE)) {
					ps.setBoolean(filledPlaceholder, true);
				} else {
					ps.setBoolean(filledPlaceholder, false);
				}
				//Set selectedUsers
				for (int i=0; i < selectedUsers.length; i++) {
					filledPlaceholder +=1;
					ps.setString(filledPlaceholder, selectedUsers[i]);
				}
	    	}
			
			//Set lower limit and pagesize
			if (setLimitParams) {
				ps.setInt(filledPlaceholder + 1, lowerPageLimit);
				ps.setInt(filledPlaceholder + 2, pageSize);
				filledPlaceholder +=2;
			}
	}

	/**
	 * Write insert statements in a file for submitted_process table
	 * 
	 * @param writer the buffered writer used to append insert statements
	 * @param res resulSet the result set containing 
	 * @param writeUseStatement write also a "USE db_name" statement
	 * @param closeWriter close the bufferedWriter
	 * @throws IOException 
	 * @throws SQLException 
	 */
	public static void writeSubmittedProcessesDump(BufferedWriter writer, ResultSet res, 
			boolean writeUseStatement, boolean closeWriter) throws IOException, SQLException {
		
		if (writeUseStatement) {
		  writer.write("USE people;");
		}
		
		writer.newLine();

        if (res.next()) {
	        writer.write("/*Data for the table submitted_process */");
	        writer.newLine();
        	
	        writer.write("insert into submitted_process (OID, EDITABLE_PROCESS_ID, USER_ID, PEOPLE_PROTOCOL_ID, " +
					"COMMUNE_PROTOCOL_ID, COMMUNE_ID, SUBMITTED_TIME, COMPLETED, TRANSPORT_TRACKINGNUMBER, DELEGATE, " +
					"SIGN_ON_LINE_ENABLED, SIGN_OFF_LINE_ENABLED, SIGN_REQUIRED, ON_LINE_SIGNED, OFF_LINE_SIGNED) ");
	        writer.newLine();
	        writer.write("VALUES ");

	        do {
				Calendar cal = Calendar.getInstance();
				cal.setTime(res.getTimestamp("SUBMITTED_TIME", cal));
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				writer.write("(");
	        	writer.write(res.getInt("OID") + ", ");
	        	writer.write(res.getInt("EDITABLE_PROCESS_ID") + ", ");
	        	writer.write((res.getString("USER_ID") == null) ? "null," : "'" + res.getString("USER_ID") + "', ");
	        	writer.write((res.getString("PEOPLE_PROTOCOL_ID") == null) ? "null," : "'" + res.getString("PEOPLE_PROTOCOL_ID") + "', ");
	        	writer.write((res.getString("COMMUNE_PROTOCOL_ID") == null) ? "null," : "'" + res.getString("COMMUNE_PROTOCOL_ID") + "', ");
	        	writer.write("'" + res.getString("COMMUNE_ID") + "', ");
	        	writer.write((res.getTimestamp("SUBMITTED_TIME", cal) == null) ? "null," : "'" + format.format(cal.getTime()) + "', ");
	        	writer.write(res.getInt("COMPLETED") + ", ");
	        	writer.write((res.getString("TRANSPORT_TRACKINGNUMBER") == null) ? "null," : "'" + res.getString("TRANSPORT_TRACKINGNUMBER") + "', ");
	        	writer.write(res.getInt("DELEGATE") + ", ");
	        	writer.write(res.getInt("SIGN_ON_LINE_ENABLED") + ", ");
	        	writer.write(res.getInt("SIGN_OFF_LINE_ENABLED") + ", ");
	        	writer.write(res.getInt("SIGN_REQUIRED") + ", ");
	        	writer.write(res.getInt("ON_LINE_SIGNED") + ", ");
	        	writer.write(res.getInt("OFF_LINE_SIGNED") + " ");
	        	
	        	if (!res.isLast()) {
	        		writer.write("),");
	        	}
	        	else {
	        		writer.write(");");
	        	}
	        	writer.newLine();
	        } while (res.next());
    	}
        
        if (closeWriter) {
        	writer.close();
        }
	}

	/**
	 * 
	 * @param writer
	 * @param res
	 * @param writeUseStatement
	 * @param closeWriter
	 * @throws IOException
	 * @throws SQLException
	 */
	public static void writeNotSubmittableProcessesDump(BufferedWriter writer, ResultSet res, 
			boolean writeUseStatement, boolean closeWriter) throws IOException, SQLException {
		
		if (writeUseStatement) {
		  writer.write("USE people;");
		}
		
		if (res.next()) {
	    	
	        writer.write("/*Data for the table not_submittable_process */");
	        writer.newLine();
	        
	        writer.write("insert into not_submittable_process (OID, USER_ID, COMMUNE_ID, PROCESS_CLASSNAME, CREATION_TIME, CONTENT_NAME, CONTENT_ID, PROCESS_NAME, DELEGATE) ");
	        writer.newLine();
	        writer.write("VALUES ");
	
	        do {
				Calendar cal = Calendar.getInstance();
				cal.setTime(res.getTimestamp("CREATION_TIME", cal));
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
				writer.write("(");
	        	writer.write(res.getInt("OID") + ", ");
	        	writer.write((res.getString("USER_ID") == null) ? "null," : "'" + res.getString("USER_ID") + "', ");
	        	writer.write(res.getInt("COMMUNE_ID") + ", ");
	        	writer.write((res.getString("PROCESS_CLASSNAME") == null) ? "null," : "'" + res.getString("PROCESS_CLASSNAME") + "', ");
	        	writer.write((res.getTimestamp("CREATION_TIME", cal) == null) ? "null," : "'" + format.format(cal.getTime()) + "', ");
	        	writer.write((res.getString("CONTENT_NAME") == null) ? "null," : "'" + QueryUtils.escapeSQLSpecialChars(res.getString("CONTENT_NAME")) + "', ");
	        	writer.write((res.getString("CONTENT_ID") == null) ? "null," : "'" + res.getString("CONTENT_ID") + "', ");
	        	writer.write((res.getString("PROCESS_NAME") == null) ? "null," : "'" + res.getString("PROCESS_NAME") + "', ");
	        	writer.write(res.getInt("DELEGATE") +" ");
	        	
	        	if (!res.isLast()) {
	        		writer.write("),");
	        	}
	        	else {
	        		writer.write(");");
	        	}
	        	writer.newLine();
	        	
	        } while (res.next());
	    }
		
        if (closeWriter) {
        	writer.close();
        }
	}
		
	/**
	 * 
	 * @param writer
	 * @param res
	 * @param writeUseStatement
	 * @param closeWriter
	 * @throws IOException
	 * @throws SQLException
	 */
	public static void writePendingProcessesDump(BufferedWriter writer, ResultSet res, 
			boolean writeUseStatement, boolean closeWriter) throws IOException, SQLException {
		
		if (writeUseStatement) {
		  writer.write("USE people;");
		}
	
		if (res.next()) {
	    	
	        writer.write("/*Data for the table pending_process */");
	        writer.newLine();
	        
	        writer.write("insert into pending_process (OID, PROCESS_DATA_ID, USER_ID, COMMUNE_ID, PROCESS_CLASSNAME, " +
	        		"PROCESS_VALUE, LAST_MODIFIED_TIME , CREATION_TIME, SENT ,STATUS ,CONTENT_NAME , CONTENT_ID, " +
	        		"PROCESS_NAME, OFF_LINE_SIGN_DOCUMENT_HASH , OFF_LINE_SIGN_WAITING) ");
	        writer.newLine();
	        writer.write("VALUES ");
	
	        do {
				Calendar cal = Calendar.getInstance();
				cal.setTime(res.getTimestamp("LAST_MODIFIED_TIME", cal));
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
				Calendar cal2 = Calendar.getInstance();
				cal2.setTime(res.getTimestamp("CREATION_TIME", cal2));
				
				String valueString = new String(res.getBytes("PROCESS_VALUE"));
				valueString = QueryUtils.escapeSQLSpecialChars(valueString);
				
				writer.write("(");
	        	writer.write(res.getInt("OID") + ", ");
	        	writer.write((res.getString("PROCESS_DATA_ID") == null) ? "null," : "'" + res.getString("PROCESS_DATA_ID") + "', ");
	        	writer.write((res.getString("USER_ID") == null) ? "null," : "'" + res.getString("USER_ID") + "', ");
	        	writer.write((res.getString("COMMUNE_ID") == null) ? "null," : "'" + res.getString("COMMUNE_ID") + "', ");
	        	writer.write((res.getString("PROCESS_CLASSNAME") == null) ? "null," : "'" + res.getString("PROCESS_CLASSNAME") + "', ");
	        	writer.write((res.getBytes("PROCESS_VALUE") == null) ? "null," : "'" + valueString + "', ");
	        	writer.write((res.getTimestamp("LAST_MODIFIED_TIME", cal) == null) ? "null," : "'" + format.format(cal.getTime()) + "', ");
	        	writer.write((res.getTimestamp("CREATION_TIME", cal2) == null) ? "null," : "'" + format.format(cal2.getTime()) + "', ");
	        	writer.write(res.getInt("SENT") + ", ");
	        	writer.write(res.getInt("STATUS") + ", ");
	        	writer.write((res.getString("CONTENT_NAME") == null) ? "null," : "'" + QueryUtils.escapeSQLSpecialChars(res.getString("CONTENT_NAME")) + "', ");
	        	writer.write((res.getString("CONTENT_ID") == null) ? "null," : "'" + res.getString("CONTENT_ID") + "', ");
	        	writer.write((res.getString("PROCESS_NAME") == null) ? "null," : "'" + res.getString("PROCESS_NAME") + "', ");
	        	writer.write((res.getString("OFF_LINE_SIGN_DOCUMENT_HASH") == null) ? "null," : "'" + res.getString("OFF_LINE_SIGN_DOCUMENT_HASH") + "', ");
	        	writer.write(res.getInt("OFF_LINE_SIGN_WAITING") + " ");
	        	
	        	if (!res.isLast()) {
	        		writer.write("),");
	        	}
	        	else {
	        		writer.write(");");
	        	}
	        	writer.newLine();
	        } while (res.next());
	    }
		
        if (closeWriter) {
        	writer.close();
        };
	}

	/**
	 * 
	 * @param writer the BufferedWirter used to write dump file 
	 * @param res the resultset
	 * @param writeUseStatement
	 * @param closeWriter
	 * @throws IOException
	 * @throws SQLException
	 */
	public static void writePendingProcessesAclDump(BufferedWriter writer, ResultSet res, 
			boolean writeUseStatement, boolean closeWriter) throws IOException, SQLException {
		
		if (writeUseStatement) {
		  writer.write("USE people;");
		}
		
		if (res.next()) {
	    	
	        writer.write("/*Data for the table pending_process_acl */");
	        writer.newLine();
	        
	        writer.write("insert into pending_process_acl (PROCESS_ID, USER_ID, ROLE) ");
	        writer.newLine();
	        writer.write("VALUES ");
	
	        do {
				writer.write("(");
	        	writer.write(res.getInt("PROCESS_ID") + ", ");
	        	writer.write((res.getString("USER_ID") == null) ? "null," : "'" + res.getString("USER_ID") + "', ");
	        	writer.write((res.getString("ROLE") == null) ? "null," : "'" + res.getString("ROLE") + "' ");
	        	
	        	if (!res.isLast()) {
	        		writer.write("),");
	        	}
	        	else {
	        		writer.write(");");
	        	}
	        	writer.newLine();
	        } while (res.next());
	    }
		
        if (closeWriter) {
        	writer.close();
        }
	}
	
	public static void writePendingProcessesDelegateDump(BufferedWriter writer, ResultSet res, 
			boolean writeUseStatement, boolean closeWriter) throws IOException, SQLException {
		
		if (writeUseStatement) {
		  writer.write("USE people;");
		}
		
		if (res.next()) {
	    	
	        writer.write("/*Data for the table pending_process_delegate */");
	        writer.newLine();
	        
	        writer.write("insert into pending_process_delegate (PROCESS_ID, USER_ID, DELEGATE_ID) ");
	        writer.newLine();
	        writer.write("VALUES ");
	
	        do {
				writer.write("(");
	        	writer.write(res.getInt("PROCESS_ID") + ", ");
	        	writer.write((res.getString("USER_ID") == null) ? "null," : "'" + res.getString("USER_ID") + "', ");
	        	writer.write((res.getString("DELEGATE_ID") == null) ? "null," : "'" + res.getString("DELEGATE_ID") + "' ");
	        	
	        	if (!res.isLast()) {
	        		writer.write("),");
	        	}
	        	else {
	        		writer.write(");");
	        	}
	        	writer.newLine();
	        } while (res.next());
	    }
		
        if (closeWriter) {
        	writer.close();
        }
	}

	public static void writeSubmittedProcessesHistoryDump(BufferedWriter writer, ResultSet res, 
			boolean writeUseStatement, boolean closeWriter) throws IOException, SQLException {
		
		if (writeUseStatement) {
		  writer.write("USE people;");
		}
		
		writer.newLine();

        if (res.next()) {
	        writer.write("/*Data for the table submitted_process_history */");
	        writer.newLine();
        	
	        writer.write("insert into submitted_process_history (SBMT_PROCESS_ID, TRANSACTION_TIME, STATUS_ID) ");
	        writer.newLine();
	        writer.write("VALUES ");

	        do {
				Calendar cal = Calendar.getInstance();
				cal.setTime(res.getTimestamp("TRANSACTION_TIME", cal));
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				writer.write("(");
	        	writer.write(res.getInt("SBMT_PROCESS_ID") + ", ");
	        	writer.write((res.getTimestamp("TRANSACTION_TIME", cal) == null) ? "null," : "'" + format.format(cal.getTime()) + "', ");
	        	writer.write(res.getInt("STATUS_ID") + " ");
	        	if (!res.isLast()) {
	        		writer.write("),");
	        	}
	        	else {
	        		writer.write(");");
	        	}
	        	writer.newLine();
	        	
	        } while (res.next());
    	}
        
        if (closeWriter) {
        	writer.close();
        }
	}
	
	
	public static void writeSubmittedProcessesInfoDump(BufferedWriter writer, ResultSet res, 
			boolean writeUseStatement, boolean closeWriter) throws IOException, SQLException {
		
		if (writeUseStatement) {
		  writer.write("USE people;");
		}
		
		writer.newLine();

        if (res.next()) {
	        writer.write("/*Data for the table submitted_process_info */");
	        writer.newLine();
        	
	        writer.write("insert into submitted_process_info (SBMT_PROCESS_ID, INFORMATION_KEY," +
	        		" INFORMATION_VALUE, INFORMATION_PATH) ");
	        writer.newLine();
	        writer.write("VALUES ");

	        do {
				Calendar cal = Calendar.getInstance();
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
				String valueString = new String(res.getBytes("INFORMATION_VALUE"));
				valueString = QueryUtils.escapeSQLSpecialChars(valueString);

				writer.write("(");
	        	writer.write(res.getInt("SBMT_PROCESS_ID") + ", ");
	        	writer.write((res.getString("INFORMATION_KEY") == null) ? "null," : "'" + res.getString("INFORMATION_KEY") + "' ");
	        	writer.write((res.getBytes("INFORMATION_VALUE") == null) ? "null," : "'" + valueString + "' ");
	        	writer.write((res.getString("INFORMATION_PATH") == null) ? "null," : "'" + res.getString("INFORMATION_PATH") + "' ");
	        	if (!res.isLast()) {
	        		writer.write("),");
	        	}
	        	else {
	        		writer.write(");");
	        	}
	        	writer.newLine();
	        	
	        } while (res.next());
    	}
        
        if (closeWriter) {
        	writer.close();
        }
	}
	
	
	
	
	
	

}
