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

import javax.servlet.http.HttpServletRequest;

import it.people.dbm.utility.Utility;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *
 * @author Piergiorgio
 */
public class LeggiEsistenza {

    public JSONObject leggiUtenti(HttpServletRequest request) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        int righe = 0;
        try {
            conn = Utility.getConnection();
            String param = Utility.testoADb(request.getParameter("tab_key"));

            String query = "Select count(*) righe"
                    + " from utenti"
                    + " where cod_utente = ?";
            st = conn.prepareStatement(query);
            st.setString(1, param);
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            ret = new JSONObject();
            if (righe > 0) {
                ret.put("failure", "Record già presente");
            } else {
                ret.put("success", new JSONArray());
            }
        } finally {

            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public JSONObject leggiTestiPortale(HttpServletRequest request) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        int righe = 0;
        try {
            conn = Utility.getConnection();
            String param = Utility.testoADb(request.getParameter("tab_key"));

            String query = "Select count(*) righe"
                    + " from html_testi"
                    + " where cod_testo = ?";
            st = conn.prepareStatement(query);
            st.setString(1, param);
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            ret = new JSONObject();
            if (righe > 0) {
                ret.put("failure", "Record già presente");
            } else {
                ret.put("success", new JSONArray());
            }
        } finally {

            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public JSONObject leggiHelp(HttpServletRequest request) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        int righe = 0;
        try {
            conn = Utility.getConnection();
            String param = Utility.testoADb(request.getParameter("tab_key"));

            String query = "Select count(*) righe"
                    + " from help_online"
                    + " where id_help = ?";
            st = conn.prepareStatement(query);
            st.setString(1, param);
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            ret = new JSONObject();
            if (righe > 0) {
                ret.put("failure", "Record già presente");
            } else {
                ret.put("success", new JSONArray());
            }
        } finally {

            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public JSONObject leggiAnagrafica(HttpServletRequest request) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        int righe = 0;
        try {
            conn = Utility.getConnection();
            String param = Utility.testoADb(request.getParameter("tab_key"));
            String param1 = Utility.testoADb(request.getParameter("tab_key1"));
            if (!param1.equals("R")) {
                String query = "Select count(*) righe"
                        + " from anagrafica "
                        + " where nome = ?";
                st = conn.prepareStatement(query);
                st.setString(1, param);
                rs = st.executeQuery();
                if (rs.next()) {
                    righe = rs.getInt("righe");
                }
            }
            ret = new JSONObject();

            if (righe > 0) {
                ret.put("failure", "Record già presente");
            } else {
                ret.put("success", new JSONArray());
            }
        } finally {

            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public JSONObject leggiTipiAggregazione(HttpServletRequest request) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        int righe = 0;
        try {
            conn = Utility.getConnection();
            String param = Utility.testoADb(request.getParameter("tab_key"));
            String query = "Select count(*) righe"
                    + " from anagrafica "
                    + " where nome = ?";
            st = conn.prepareStatement(query);
            st.setString(1, param);
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            ret = new JSONObject();

            if (righe > 0) {
                ret.put("failure", "Record già presente");
            } else {
                ret.put("success", new JSONArray());
            }
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public JSONObject leggiCud(HttpServletRequest request) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        int righe = 0;
        try {
            conn = Utility.getConnection();
            String param = Utility.testoADb(request.getParameter("tab_key"));
            String query = "Select count(*) righe"
                    + " from cud "
                    + " where cod_cud = ?";
            st = conn.prepareStatement(query);
            st.setString(1, param);
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            ret = new JSONObject();

            if (righe > 0) {
                ret.put("failure", "Record già presente");
            } else {
                ret.put("success", new JSONArray());
            }
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public JSONObject leggiClassiEnti(HttpServletRequest request) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        int righe = 0;
        try {
            conn = Utility.getConnection();
            String param = Utility.testoADb(request.getParameter("tab_key"));
            String query = "Select count(*) righe"
                    + " from classi_enti "
                    + " where cod_classe_ente = ?";
            st = conn.prepareStatement(query);
            st.setString(1, param);
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            ret = new JSONObject();

            if (righe > 0) {
                ret.put("failure", "Record già presente");
            } else {
                ret.put("success", new JSONArray());
            }
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public JSONObject leggiEntiComuni(HttpServletRequest request) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        int righe = 0;
        try {
            conn = Utility.getConnection();
            String param = Utility.testoADb(request.getParameter("tab_key"));
            String query = "Select count(*) righe"
                    + " from enti_comuni "
                    + " where cod_ente = ?";
            st = conn.prepareStatement(query);
            st.setString(1, param);
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            ret = new JSONObject();

            if (righe > 0) {
                ret.put("failure", "Record già presente");
            } else {
                ret.put("success", new JSONArray());
            }
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public JSONObject leggiDestinatari(HttpServletRequest request) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        int righe = 0;
        try {
            conn = Utility.getConnection();
            String param = Utility.testoADb(request.getParameter("tab_key"));
            String query = "Select count(*) righe"
                    + " from destinatari "
                    + " where cod_dest = ?";
            st = conn.prepareStatement(query);
            st.setString(1, param);
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            ret = new JSONObject();

            if (righe > 0) {
                ret.put("failure", "Record già presente");
            } else {
                ret.put("success", new JSONArray());
            }
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public JSONObject leggiSportelli(HttpServletRequest request) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        int righe = 0;
        try {
            conn = Utility.getConnection();
            String param = Utility.testoADb(request.getParameter("tab_key"));
            String query = "Select count(*) righe"
                    + " from sportelli "
                    + " where cod_sport = ?";
            st = conn.prepareStatement(query);
            st.setString(1, param);
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            ret = new JSONObject();

            if (righe > 0) {
                ret.put("failure", "Record già presente");
            } else {
                ret.put("success", new JSONArray());
            }
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public JSONObject leggiSettoriAttivita(HttpServletRequest request) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        int righe = 0;
        try {
            conn = Utility.getConnection();
            String param = Utility.testoADb(request.getParameter("tab_key"));
            String query = "Select count(*) righe"
                    + " from settori_attivita "
                    + " where cod_sett = ?";
            st = conn.prepareStatement(query);
            st.setString(1, param);
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            ret = new JSONObject();

            if (righe > 0) {
                ret.put("failure", "Record già presente");
            } else {
                ret.put("success", new JSONArray());
            }
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public JSONObject leggiOperazioni(HttpServletRequest request) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        int righe = 0;
        try {
            conn = Utility.getConnection();
            String param = Utility.testoADb(request.getParameter("tab_key"));
            String query = "Select count(*) righe"
                    + " from operazioni "
                    + " where cod_ope = ?";
            st = conn.prepareStatement(query);
            st.setString(1, param);
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            ret = new JSONObject();

            if (righe > 0) {
                ret.put("failure", "Record già presente");
            } else {
                ret.put("success", new JSONArray());
            }
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public JSONObject leggiInterventi(HttpServletRequest request) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        int righe = 0;
        try {
            conn = Utility.getConnection();
            String param = Utility.testoADb(request.getParameter("tab_key"));
            String query = "Select count(*) righe"
                    + " from interventi "
                    + " where cod_int = ?";
            st = conn.prepareStatement(query);
            st.setInt(1, Integer.parseInt(param));
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            ret = new JSONObject();

            if (righe > 0) {
                ret.put("failure", "Record già presente");
            } else {
                ret.put("success", new JSONArray());
            }
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public JSONObject leggiProcedimenti(HttpServletRequest request) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        int righe = 0;
        try {
            conn = Utility.getConnection();
            String param = Utility.testoADb(request.getParameter("tab_key"));
            String query = "Select count(*) righe"
                    + " from procedimenti "
                    + " where cod_proc = ?";
            st = conn.prepareStatement(query);
            st.setString(1, param);
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            ret = new JSONObject();

            if (righe > 0) {
                ret.put("failure", "Record già presente");
            } else {
                ret.put("success", new JSONArray());
            }
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public JSONObject leggiTipiRif(HttpServletRequest request) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        int righe = 0;
        try {
            conn = Utility.getConnection();
            String param = Utility.testoADb(request.getParameter("tab_key"));
            String query = "Select count(*) righe"
                    + " from tipi_rif "
                    + " where cod_tipo_rif = ?";
            st = conn.prepareStatement(query);
            st.setString(1, param);
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            ret = new JSONObject();

            if (righe > 0) {
                ret.put("failure", "Record già presente");
            } else {
                ret.put("success", new JSONArray());
            }
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public JSONObject leggiOneri(HttpServletRequest request) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        int righe = 0;
        try {
            conn = Utility.getConnection();
            String param = Utility.testoADb(request.getParameter("tab_key"));
            String query = "Select count(*) righe"
                    + " from oneri "
                    + " where cod_oneri = ?";
            st = conn.prepareStatement(query);
            st.setString(1, param);
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            ret = new JSONObject();

            if (righe > 0) {
                ret.put("failure", "Record già presente");
            } else {
                ret.put("success", new JSONArray());
            }
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public JSONObject leggiOneriGerarchia(HttpServletRequest request) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        int righe = 0;
        try {
            conn = Utility.getConnection();
            String param = Utility.testoADb(request.getParameter("tab_key"));
            String query = "select count(*) righe "
                    + "from oneri_gerarchia a "
                    + "join oneri_gerarchia_testi b "
                    + "on a.cod_figlio=b.cod_figlio "
                    + "and b.cod_lang='it' where a.cod_figlio  = ?";
            st = conn.prepareStatement(query);
            st.setInt(1, Integer.parseInt(param));
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            ret = new JSONObject();

            if (righe > 0) {
                ret.put("failure", "Record già presente");
            } else {
                ret.put("success", new JSONArray());
            }
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public JSONObject leggiGerarchiaSettori(HttpServletRequest request) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        int righe = 0;
        try {
            conn = Utility.getConnection();
            String param = Utility.testoADb(request.getParameter("tab_key"));
            String query = "select count(*) righe "
                    + "from gerarchia_settori a "
                    + "join gerarchia_settori_testi b "
                    + "on a.cod_ramo=b.cod_ramo "
                    + "and b.cod_lang='it' where a.cod_ramo  = ?";
            st = conn.prepareStatement(query);
            st.setString(1, param);
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            ret = new JSONObject();

            if (righe > 0) {
                ret.put("failure", "Record già presente");
            } else {
                ret.put("success", new JSONArray());
            }
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public JSONObject leggiGerarchiaOperazioni(HttpServletRequest request) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        int righe = 0;
        try {
            conn = Utility.getConnection();
            String param = Utility.testoADb(request.getParameter("tab_key"));
            String query = "select count(*) righe "
                    + "from gerarchia_operazioni a "
                    + "join gerarchia_operazioni_testi b "
                    + "on a.tip_aggregazione=b.tip_aggregazione "
                    + "and a.cod_ramo=b.cod_ramo "
                    + "and b.cod_lang='it' where a.tip_aggregazione= ? and a.cod_ramo  = ?";
            st = conn.prepareStatement(query);
            st.setString(1, request.getParameter("tip_aggregazione"));
            st.setString(2, param);
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            ret = new JSONObject();

            if (righe > 0) {
                ret.put("failure", "Record già presente");
            } else {
                ret.put("success", new JSONArray());
            }
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public JSONObject leggiOneriFormula(HttpServletRequest request) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        int righe = 0;
        try {
            conn = Utility.getConnection();
            String param = Utility.testoADb(request.getParameter("tab_key"));
            String query = "select count(*) righe "
                    + "from oneri_formula "
                    + "where cod_onere_formula= ?";
            st = conn.prepareStatement(query);
            st.setString(1, param);
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            ret = new JSONObject();

            if (righe > 0) {
                ret.put("failure", "Record già presente");
            } else {
                ret.put("success", new JSONArray());
            }
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public JSONObject leggiOneriCampi(HttpServletRequest request) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        int righe = 0;
        try {
            conn = Utility.getConnection();
            String param = Utility.testoADb(request.getParameter("tab_key"));
            String query = "select count(*) righe "
                    + "from oneri_campi a "
                    + "join oneri_campi_testi b "
                    + "on a.cod_onere_campo=b.cod_onere_campo "
                    + "where b.cod_lang='it' "
                    + "and a.cod_onere_campo=?";
            st = conn.prepareStatement(query);
            st.setString(1, param);
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            ret = new JSONObject();

            if (righe > 0) {
                ret.put("failure", "Record già presente");
            } else {
                ret.put("success", new JSONArray());
            }
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public JSONObject leggiOneriDocumenti(HttpServletRequest request) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        int righe = 0;
        try {
            conn = Utility.getConnection();
            String param = Utility.testoADb(request.getParameter("tab_key"));
            String query = "select count(*) righe "
                    + "from oneri_documenti a "
                    + "join oneri_documenti_testi b "
                    + "on a.cod_doc_onere=b.cod_doc_onere "
                    + "and a.cod_lang=b.cod_lang "
                    + "where b.cod_lang='it' "
                    + "and a.cod_doc_onere=?";
            st = conn.prepareStatement(query);
            st.setString(1, param);
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            ret = new JSONObject();

            if (righe > 0) {
                ret.put("failure", "Record già presente");
            } else {
                ret.put("success", new JSONArray());
            }
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public JSONObject leggiImmaginiTemplate(HttpServletRequest request) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        int righe = 0;
        try {
            conn = Utility.getConnection();
            String param = Utility.testoADb(request.getParameter("tab_key"));
            String query = "select count(*) righe "
                    + "from templates_immagini where nome_immagine =?";
            st = conn.prepareStatement(query);
            st.setString(1, param);
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            ret = new JSONObject();

            if (righe > 0) {
                ret.put("failure", "Record già presente");
            } else {
                ret.put("success", new JSONArray());
            }
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public JSONObject leggiTestoCondizioni(HttpServletRequest request) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        int righe = 0;
        try {
            conn = Utility.getConnection();
            String param = Utility.testoADb(request.getParameter("tab_key"));
            String query = "select count(*) righe "
                    + "from testo_condizioni where cod_cond =?";
            st = conn.prepareStatement(query);
            st.setString(1, param);
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            ret = new JSONObject();

            if (righe > 0) {
                ret.put("failure", "Record già presente");
            } else {
                ret.put("success", new JSONArray());
            }
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public JSONObject leggiHref(HttpServletRequest request) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        int righe = 0;
        try {
            conn = Utility.getConnection();
            String param = Utility.testoADb(request.getParameter("tab_key"));
            String query = "select count(*) righe "
                    + "from href where href =?";
            st = conn.prepareStatement(query);
            st.setString(1, param);
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            ret = new JSONObject();

            if (righe > 0) {
                ret.put("failure", "Record già presente");
            } else {
                ret.put("success", new JSONArray());
            }
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public JSONObject leggiNormative(HttpServletRequest request) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        int righe = 0;
        try {
            conn = Utility.getConnection();
            String param = Utility.testoADb(request.getParameter("tab_key"));
            String query = "select count(*) righe "
                    + "from normative where cod_rif =?";
            st = conn.prepareStatement(query);
            st.setString(1, param);
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            ret = new JSONObject();

            if (righe > 0) {
                ret.put("failure", "Record già presente");
            } else {
                ret.put("success", new JSONArray());
            }
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public JSONObject leggiDocumento(HttpServletRequest request) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        int righe = 0;
        try {
            conn = Utility.getConnection();
            String param = Utility.testoADb(request.getParameter("tab_key"));
            String query = "select count(*) righe "
                    + "from documenti where cod_doc =?";
            st = conn.prepareStatement(query);
            st.setString(1, param);
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            ret = new JSONObject();

            if (righe > 0) {
                ret.put("failure", "Record già presente");
            } else {
                ret.put("success", new JSONArray());
            }
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public JSONObject leggiTemplatesVari(HttpServletRequest request) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        int righe = 0;
        try {
            conn = Utility.getConnection();
            String param = Utility.testoADb(request.getParameter("tab_key"));
            String query = "select count(*) righe "
                    + "from templates_vari where nome_template =?";
            st = conn.prepareStatement(query);
            st.setString(1, param);
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            ret = new JSONObject();

            if (righe > 0) {
                ret.put("failure", "Record già presente");
            } else {
                ret.put("success", new JSONArray());
            }
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public JSONObject leggiLingueAggregazioni(HttpServletRequest request) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        int righe = 0;
        try {
            conn = Utility.getConnection();
            String param = Utility.testoADb(request.getParameter("tab_key"));
            String param1 = Utility.testoADb(request.getParameter("tab_key1"));
            String query = "select count(*) righe "
                    + "from lingue_aggregazioni where tip_aggregazione =? and cod_lang = ?";
            st = conn.prepareStatement(query);
            st.setString(1, param);
            st.setString(2, param1);
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            ret = new JSONObject();

            if (righe > 0) {
                ret.put("failure", "Record già presente");
            } else {
                ret.put("success", new JSONArray());
            }
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }
}
