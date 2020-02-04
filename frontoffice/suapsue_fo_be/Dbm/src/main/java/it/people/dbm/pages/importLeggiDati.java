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
package it.people.dbm.pages;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import it.people.dbm.utility.Utility;

/**
 *
 * @author giuseppe
 */
public class importLeggiDati {
	
	private Map<String,Map<String,String>> errori;

    public String leggiCud(String key) throws Exception {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            conn = Utility.getConnection();
            String query = "select count(*) righe "
                    + "from cud "
                    + "where cod_cud=?";
            st = conn.prepareStatement(query);
            st.setString(1, key);
            rs = st.executeQuery();
            int righe = 0;
            while (rs.next()) {
                righe = rs.getInt("righe");
            }
            if (righe > 0) {
                return "D";
            } else {
                return "N";
            }
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
    }

    public String leggiDocumenti(String key, String aggregazione,HttpServletRequest request) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        String aggregazioneDaDb=null;
        try {
        	HttpSession session = request.getSession();
        	errori=(Map<String, Map<String, String>>) session.getAttribute("ERRORIIMPORT");
        	if (errori == null){
        		errori = new HashMap<String,Map<String,String>>();
        	}
            conn = Utility.getConnection();
            String query = "select cod_doc, tip_aggregazione "
                    + "from documenti "
                    + "where cod_doc=?";
            st = conn.prepareStatement(query);
            st.setString(1, key);
            rs = st.executeQuery();
            int righe = 0;
            while (rs.next()) {
            	righe=1;
            	aggregazioneDaDb = rs.getString("tip_aggregazione");
            }
            String ret;
            if (aggregazioneDaDb != null && !aggregazioneDaDb.equals(aggregazione)){
            	ret= "X";
            } else if (righe > 0) {
                ret= "D";
            } else {
                ret= "N";
            }
            Map <String,String> e =  errori.get("documenti");
            if (e == null){
            	e=new HashMap<String,String>();
            }
            e.put(key, ret);
            errori.put("documenti", e);
            session.setAttribute("ERRORIIMPORT", errori);
            return ret;
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
    }

    public String leggiInterventi(String key,String aggregazione,HttpServletRequest request) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        String aggregazioneDaDb=null;
        try {
        	HttpSession session = request.getSession();
        	errori=(Map<String, Map<String, String>>) session.getAttribute("ERRORIIMPORT");
        	if (errori == null){
        		errori = new HashMap<String,Map<String,String>>();
        	}
            conn = Utility.getConnection();
            String query = "select cod_int,tip_aggregazione "
                    + "from interventi "
                    + "where cod_int=?";
            st = conn.prepareStatement(query);
            st.setInt(1, Integer.parseInt(key));
            rs = st.executeQuery();
            int righe = 0;
            String ret;
            while (rs.next()) {
            	righe=1;
            	aggregazioneDaDb = rs.getString("tip_aggregazione");
            }
            Map <String,String> e =  errori.get("interventi");
            if (e == null){
            	e=new HashMap<String,String>();
            }
            if (aggregazioneDaDb != null && !aggregazioneDaDb.equals(aggregazione)){
            	ret = "X";
            } else if (righe > 0) {
                ret = "D";
            } else {
                ret = "N";
            }
            e.put(key, ret);
            errori.put("interventi", e);
            session.setAttribute("ERRORIIMPORT", errori);
            return ret;
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
    }

    public String leggiNormative(String key, String aggregazione,HttpServletRequest request) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        String aggregazioneDaDb=null;
        try {
        	HttpSession session = request.getSession();
        	errori=(Map<String, Map<String, String>>) session.getAttribute("ERRORIIMPORT");
        	if (errori == null){
        		errori = new HashMap<String,Map<String,String>>();
        	}
            conn = Utility.getConnection();
            String query = "select cod_rif, tip_aggregazione "
                    + "from normative "
                    + "where cod_rif=?";
            st = conn.prepareStatement(query);
            st.setString(1, key);
            rs = st.executeQuery();
            int righe = 0;
            while (rs.next()) {
            	righe=1;
            	aggregazioneDaDb = rs.getString("tip_aggregazione");
            }
            String ret;
            Map <String,String> e =  errori.get("normative");
            if (e == null){
            	e=new HashMap<String,String>();
            }
            if (aggregazioneDaDb != null && !aggregazioneDaDb.equals(aggregazione)){
            	ret = "X";
            } else if (righe > 0) {
                ret = "D";
            } else {
                ret = "N";
            }
            e.put(key, ret);
            errori.put("normative", e);
            session.setAttribute("ERRORIIMPORT", errori);
            return ret;
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
    }

    public String leggiProcedimenti(String key, String aggregazione,HttpServletRequest request) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        String aggregazioneDaDb=null;
        try {
        	HttpSession session = request.getSession();
        	errori=(Map<String, Map<String, String>>) session.getAttribute("ERRORIIMPORT");
        	if (errori == null){
        		errori = new HashMap<String,Map<String,String>>();
        	}
            conn = Utility.getConnection();
            String query = "select cod_proc, tip_aggregazione "
                    + "from procedimenti "
                    + "where cod_proc=?";
            st = conn.prepareStatement(query);
            st.setString(1, key);
            rs = st.executeQuery();
            int righe = 0;
            String ret;
            while (rs.next()) {
            	righe=1;
            	aggregazioneDaDb = rs.getString("tip_aggregazione");
            }
            Map <String,String> e =  errori.get("procedimenti");
            if (e == null){
            	e=new HashMap<String,String>();
            }
            if (aggregazioneDaDb != null && !aggregazioneDaDb.equals(aggregazione)){
            	ret = "X";
            } else if (righe > 0) {
                ret = "D";
            } else {
                ret = "N";
            }
            e.put(key, ret);
            errori.put("procedimenti", e);
            session.setAttribute("ERRORIIMPORT", errori);
            return ret;
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
    }

    public String leggiHref(String key, String aggregazione,HttpServletRequest request) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        String aggregazioneDaDb=null;
        try {
        	HttpSession session = request.getSession();
        	errori=(Map<String, Map<String, String>>) session.getAttribute("ERRORIIMPORT");
        	if (errori == null){
        		errori = new HashMap<String,Map<String,String>>();
        	}
            conn = Utility.getConnection();
            String query = "select href, tip_aggregazione "
                    + "from href "
                    + "where href=? ";
            st = conn.prepareStatement(query);
            st.setString(1, key);
            rs = st.executeQuery();
            int righe = 0;
            String ret;
            while (rs.next()) {
            	righe=1;
            	aggregazioneDaDb = rs.getString("tip_aggregazione");
            }
            Map <String,String> e =  errori.get("href");
            if (e == null){
            	e=new HashMap<String,String>();
            }
            if (aggregazioneDaDb != null && !aggregazioneDaDb.equals(aggregazione)){
            	ret = "X";
            } else if (righe > 0) {
                ret = "D";
            } else {
                ret = "N";
            }
            e.put(key, ret);
            errori.put("href", e);
            session.setAttribute("ERRORIIMPORT", errori);
            return ret;
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
    }

    public String leggiTipiRif(String key) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            conn = Utility.getConnection();
            String query = "select count(*) righe "
                    + "from tipi_rif "
                    + "where cod_tipo_rif=?";
            st = conn.prepareStatement(query);
            st.setString(1, key);
            rs = st.executeQuery();
            int righe = 0;
            while (rs.next()) {
                righe = rs.getInt("righe");
            }
            if (righe > 0) {
                return "D";
            } else {
                return "N";
            }
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
    }

     public String leggiTestoCondizioni(String key, String aggregazione,HttpServletRequest request) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        String aggregazioneDaDb=null;
        try {
        	HttpSession session = request.getSession();
        	errori=(Map<String, Map<String, String>>) session.getAttribute("ERRORIIMPORT");
        	if (errori == null){
        		errori = new HashMap<String,Map<String,String>>();
        	}
            conn = Utility.getConnection();
            String query = "select cod_cond,tip_aggregazione righe "
                    + "from testo_condizioni "
                    + "where cod_cond=?";
            st = conn.prepareStatement(query);
            st.setString(1, key);
            rs = st.executeQuery();
            int righe = 0;
            String ret;
            while (rs.next()) {
            	righe=1;
            	aggregazioneDaDb = rs.getString("tip_aggregazione");
            }
            Map <String,String> e =  errori.get("condizioni");
            if (e == null){
            	e=new HashMap<String,String>();
            }
            if (aggregazioneDaDb != null && !aggregazioneDaDb.equals(aggregazione)){
            	ret = "X";
            } else if (righe > 0) {
                ret = "D";
            } else {
                ret = "N";
            }
            e.put(key, ret);
            errori.put("condizioni", e);
            session.setAttribute("ERRORIIMPORT", errori);
            return ret;
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
    }
}
