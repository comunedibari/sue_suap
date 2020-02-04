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

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.HashMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.axis.encoding.Base64;
import org.slf4j.Logger;

import net.sf.json.util.JSONUtils;
import org.slf4j.LoggerFactory;
/**
 *
 * @author Piergiorgio
 */
public class Utility {

    private static Logger log = LoggerFactory.getLogger(Utility.class);

    public static Connection getConnection() {

        try {
            Context ctx = new InitialContext();
            if (ctx == null) {
                throw new Exception("Boom - No Context");
            }

            log.debug("ctx " + ctx.getNameInNamespace());


            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/puDS");

            return ds.getConnection();

        } catch (Exception e) {
            log.error("Errore DS", e);
            return null;
        }
    }

    public static void chiusuraJdbc(Object obj) throws Exception {
        if (obj != null) {
            try {
                if (obj instanceof ResultSet) {
                    ((ResultSet) obj).close();
                } else if (obj instanceof Statement) {
                    ((Statement) obj).close();
                } else if (obj instanceof PreparedStatement) {
                    ((PreparedStatement) obj).close();
                } else if (obj instanceof Connection) {
                    ((Connection) obj).close();
                } else {
                    throw new Exception("Chiusura JDBC: Classe non riconosciuta:" + obj.getClass());
                }

            } catch (Exception ex) {
                throw new Exception(ex.getMessage());
            }
        }
    }

    public static String testoDaDb(String st) {
        return st;
    }

    public static String testoDaDbJSonSafeSingleQuoted(String valore) {
    	return JSONUtils.quote("'" + valore + "'");
    }
    
    public static String testoDaDbJSonSafeDoubleQuoted(String valore) {
    	return JSONUtils.quote("''" + valore + "''");
    }
    
    //<RF - Aggiunta per parametri PEC nuova gestione Connects
    /**
     * @param st
     * @return
     */
    public static String testoDaDbBase64(String st) {
        String result = "";
        if (st != null && st.length() > 0) {
            try {
                ByteArrayOutputStream stArrayOutputStream = new ByteArrayOutputStream();
                stArrayOutputStream.write(Base64.decode(st));
                stArrayOutputStream.flush();
                result = stArrayOutputStream.toString();
                stArrayOutputStream.close();
            } catch (IOException e) {
                log.error("Errore nella conversione della stringa " + st + " da Base64.", e);
            }
        }
        return result;
    }
    //RF - Aggiunta per parametri PEC nuova gestione Connects>

    //<RF - Aggiunta per parametri PEC nuova gestione Connects
    /**
     * @param st
     * @return
     */
    public static String testoADbBase64(String st) {

        return Base64.encode(st.getBytes());

    }
    //RF - Aggiunta per parametri PEC nuova gestione Connects>

    public static String testoADb(String st) {
        String str;
        if (st != null) {
            str = st.replaceAll("'", "\\'");
//            str = str.replaceAll("&", "&amp;");
//            str = str.replaceAll("<", "&lt;");
//            str = str.replaceAll(">", "&gt;");
        } else {
            str = "";
        }
        return str;
    }

    public static String testoADbNotifica(String st) {
        String str;
        if (st != null) {
            str = st.replaceAll("'", "\\\\'");
        } else {
            str = "";
        }
        return str;
    }

    public static byte[] byteArrayInputStreamToByteArray(InputStream in) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        final int BUF_SIZE = 1 << 8; //1KiB buffer
        byte[] buffer = new byte[BUF_SIZE];
        int bytesRead = -1;
        while ((bytesRead = in.read(buffer)) > -1) {
            out.write(buffer, 0, bytesRead);
        }
        in.close();
        byte[] bytes = out.toByteArray();

        return bytes;
    }

    public static void scriviSessione(HttpServletRequest request) throws Exception {
        HashMap<String, String> param = null;

        param = new HashMap<String, String>();
        Enumeration names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            String value = (String) request.getParameter(name);
            param.put(name, value);
        }
        HttpSession session = request.getSession();
        HashMap<String, Object> funzRequest = null;
        if (session.getAttribute("sessioneTabelle") != null) {
            funzRequest = (HashMap<String, Object>) session.getAttribute("sessioneTabelle");
        } else {
            funzRequest = new HashMap<String, Object>();
        }
        funzRequest.put(param.get("table_name"), param);
        session.setAttribute("sessioneTabelle", funzRequest);

    }

    public static String inputStreamToString(InputStream is) throws Exception {
        String out = null;
        StringBuilder buffer = new StringBuilder();
        InputStreamReader isr = new InputStreamReader(is, "UTF8");
        Reader in = new BufferedReader(isr);
        int ch;
        while ((ch = in.read()) > -1) {
            buffer.append((char) ch);
        }
        in.close();
        out = buffer.toString();
        return out;
    }
}
