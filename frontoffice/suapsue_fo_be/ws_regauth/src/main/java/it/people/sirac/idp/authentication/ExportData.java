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

  Licenza:	    Licenza Progetto PEOPLE
  Fornitore:    CEFRIEL
  Autori:       M. Pianciamore, P. Selvini

  Questo codice sorgente � protetto dalla licenza valida nell'ambito del
  progetto PEOPLE. La propriet� intellettuale di questo codice � e rester�
  esclusiva di "CEFRIEL Societ� Consortile a Responsabilit� Limitata" con
  sede legale in via Renato Fucini 2, 20133 Milano (MI).

  Disclaimer:

  COVERED CODE IS PROVIDED UNDER THIS LICENSE ON AN "AS IS" BASIS, WITHOUT
  WARRANTY OF ANY KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING, WITHOUT 
  LIMITATION, WARRANTIES THAT THE COVERED CODE IS FREE OF DEFECTS, MERCHANTABLE,
  FIT FOR A PARTICULAR PURPOSE OR NON-INFRINGING. THE ENTIRE RISK AS TO THE
  QUALITY AND PERFORMANCE OF THE COVERED CODE IS WITH YOU. SHOULD ANY COVERED
  CODE PROVE DEFECTIVE IN ANY RESPECT, YOU (NOT THE INITIAL DEVELOPER OR ANY
  OTHER CONTRIBUTOR) ASSUME THE COST OF ANY NECESSARY SERVICING, REPAIR OR
  CORRECTION.
    
*/

package it.people.sirac.idp.authentication;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExportData {
  /**
   * Logger for this class
   */
  private static final Logger logger = LoggerFactory.getLogger(ExportData.class);

   private String connectionString = "jdbc:mysql://localhost:3306/capeople?user=people_demo&password=people_demo";
  //private String driverClassName = "oracle.jdbc.OracleDriver";

  private String driverClassName = "org.gjt.mm.mysql.Driver";
  
  private Connection getConnection() throws NamingException, SQLException
  {
  	   try {
				Class.forName(driverClassName);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
  	   return DriverManager.getConnection(connectionString);
  }
  
  public void exportData() {
    
    Connection userdbConn = null;
    ResultSet userdbResultset = null;
    String dbQuery = "SELECT * FROM COMUNI";

    try {
      if (logger.isDebugEnabled()) {
        logger.debug("executeBasicAuthentication() - DEBUG: PRIMA CONNESSIONE");
      }
      userdbConn = getConnection();
      if (logger.isDebugEnabled()) {
        logger.debug("executeBasicAuthentication() - DEBUG: DOPO CONNESSIONE");
      }
      PreparedStatement statement = userdbConn.prepareStatement(dbQuery);
      if (logger.isDebugEnabled()) {
        logger.debug("executeBasicAuthentication() - DEBUG: ESEGUO QUERY");
      }
      FileOutputStream fos = new FileOutputStream("C:\\temp\\elenco_comuni.ldif");

      userdbResultset = statement.executeQuery();
      //Controllo che la query abbia restituito l'utente cercato
      while(userdbResultset.next()) {
        String nomeComune = userdbResultset.getString("COMUNE");
        String provinciaComune = userdbResultset.getString("PROVINCIA");
        String regioneComune = userdbResultset.getString("REGIONE");
        String capComune = userdbResultset.getString("CAP");
        String prefissoComune = userdbResultset.getString("PREFISSO");
        String codiceBelfioreComune = userdbResultset.getString("CODICE_COMUNE");
        String codiceIstatComune = userdbResultset.getString("CODICE_ISTAT");
        
        String comuneLDIF = "dn: cn=" + nomeComune + ",ou=comune,o=people,c=it";
        comuneLDIF+="\nobjectClass: top\nobjectClass: comune";
        comuneLDIF+="\ncn: " + nomeComune;
        comuneLDIF+="\nnomeComune: " + nomeComune;
        comuneLDIF+="\nprovinciaComune: " + provinciaComune;
        comuneLDIF+="\nregioneComune: " + regioneComune;
        comuneLDIF+="\ncapComune: " + capComune;
        comuneLDIF+="\nprefissoComune: " + prefissoComune;
        comuneLDIF+="\ncodiceBelfioreComune: " + codiceBelfioreComune;
        comuneLDIF+="\ncodiceIstatComune: " + codiceIstatComune;
        comuneLDIF+="\n\n";

        fos.write(comuneLDIF.getBytes());
      }
      userdbResultset.close();
      statement.close();
      fos.flush();
      fos.close();
    } catch (Exception e) {
      logger.error("executeBasicAuthentication() - Error creating connection: " + e.getMessage());
    } finally {
      try {
        userdbConn.close();
      } catch (SQLException e) {
          logger.error("executeBasicAuthentication() - Can't close connection: " + e.getMessage());
      }
    }
  }

  public static void main(String[] args){
  	
  	new ExportData().exportData();
  }
  
  
}
