/**
 * Copyright (c) 2011, Regione Emilia-Romagna, Italy
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they will be approved by
 * the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 *
 * For convenience a plain text copy of the English version of the Licence can
 * be found in the file LICENCE.txt in the top-level directory of this software
 * distribution.
 *
 * You may obtain a copy of the Licence in any of 22 European Languages at:
 *
 * http://www.osor.eu/eupl
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" basis, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * Licence for the specific language governing permissions and limitations under
 * the Licence.
 *
 */
package it.people.dbm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.people.core.PplUserData;
import it.people.dbm.model.Utente;
import it.people.dbm.utility.Configuration;
import it.people.dbm.utility.UploadFile;
import it.people.dbm.utility.Utility;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *
 * @author Piergiorgio
 */
public class DbmDao {

    HashMap<String, String> testi;
    private static Logger log = LoggerFactory.getLogger(DbmDao.class);
    List<String> lu;
    private JSONArray jArrayGerarchia;
    private List<String> listaCampiInFormula;
    UploadFile uploadFile = new UploadFile();

    /**
     * Chiusura degli oggetti Jdbc
     */
    public HashMap leggiTestiPortale() throws Exception {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        testi = new HashMap<String, String>();
        try {
            conn = Utility.getConnection();
            String query = "select * from html_testi where cod_lang='it'";
            st = conn.prepareStatement(query);
            rs = st.executeQuery();
            while (rs.next()) {
                testi.put(rs.getString("cod_testo"),
                        Utility.testoDaDb(rs.getString("des_testo")));
            }
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return testi;
    }

    public void initSession(HttpSession hs) throws Exception {
        hs.setAttribute("testiPortale", leggiTestiPortale());
        PplUserData user = (PplUserData) hs.getAttribute("it.people.sirac.authenticated_user_data");
        hs.setAttribute("userId", user.getCodiceFiscale());

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            conn = Utility.getConnection();
            String query = "select * from utenti where cod_utente=?";
            st = conn.prepareStatement(query);
            st.setString(1, (String) hs.getAttribute("userId"));
            rs = st.executeQuery();
            Utente ut = null;
            if (rs.next()) {
                ut = new Utente();
                ut.setCodUtente(rs.getString("cod_utente"));
                ut.setCodUtentePadre(rs.getString("cod_utente_padre"));
                ut.setCognomeNome(Utility.testoDaDb(rs.getString("cognome_nome")));
                ut.setEmail(rs.getString("email"));
                ut.setRuolo(rs.getString("ruolo"));
                ut.setTipAggregazione(rs.getString("tip_aggregazione"));
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            if (ut != null) {

                query = "select * from lingue_aggregazioni where tip_aggregazione = ?";
                st = conn.prepareStatement(query);
                st.setString(1, ut.getTipAggregazione());
                rs = st.executeQuery();
                List<String> l = new ArrayList<String>();
                while (rs.next()) {
                    l.add(rs.getString("cod_lang"));
                }
                ut.setLingue(l);
            }

            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            List<String> lingue = new ArrayList<String>();
            query = "select distinct cod_lang from lingue_aggregazioni";
            st = conn.prepareStatement(query);
            rs = st.executeQuery();
            while (rs.next()) {
                lingue.add(rs.getString("cod_lang"));
            }
            hs.setAttribute("lingueTotali", lingue);

            if (ut.getTipAggregazione() == null) {
                ut.setLingue(lingue);
            }

            hs.setAttribute("utente", ut);
            String value = Configuration.getConfigurationParameter("territorialitaAllargata");
            value = (value == null ? "false" : value);
            hs.setAttribute("territorialitaNew", Boolean.parseBoolean(value.toLowerCase()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
    }

    public JSONObject decodificaHelp(String key) throws Exception {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject jo = new JSONObject();
        String ret = "";
        try {
            conn = Utility.getConnection();
            String query = "select * from help_online where id_help = ? and cod_lang='it'";
            st = conn.prepareStatement(query);
            st.setString(1, key);
            rs = st.executeQuery();
            ret = key;
            if (rs.next()) {
                String testo = Utility.testoDaDb(rs.getString("testo_help"));
                if (testo.equals("")) {
                    ret = key;
                } else {
                    ret = testo;
                }
            }
            JSONObject jo1 = new JSONObject();
            jo1.put("help", ret);
            jo.put("success", jo1);
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return jo;
    }
}
