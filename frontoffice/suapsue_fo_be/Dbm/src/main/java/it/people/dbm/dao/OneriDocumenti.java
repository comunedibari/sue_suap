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
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;

import it.people.dbm.model.FileUploadModel;
import it.people.dbm.model.OneriDocumentiModel;
import it.people.dbm.model.Tariffario;
import it.people.dbm.model.Utente;
import it.people.dbm.utility.Utility;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.LoggerFactory;
/**
 *
 * @author Piergiorgio
 */
public class OneriDocumenti {

    private static Logger log = LoggerFactory.getLogger(OneriDocumenti.class);

    public JSONObject action(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        int righe = 0;
        int qsi = 1;
        try {
            conn = Utility.getConnection();
            String offset = request.getParameter("start");
            String size = request.getParameter("size");
            String sort = request.getParameter("sort");
            String order = request.getParameter("dir");
            String href = request.getParameter("href");
            String param = Utility.testoADb(request.getParameter("query"));

            String query = "select count(*) righe "
                    + "from oneri_documenti a "
                    + "join oneri_documenti_testi b "
                    + "on a.cod_doc_onere=b.cod_doc_onere "
                    + "and a.cod_lang=b.cod_lang "
                    + "where a.cod_lang='it' "
                    + "and (b.des_doc_onere like ? or a.nome_file like ? or a.cod_doc_onere like ? ) ";
            if (!(Boolean) session.getAttribute("territorialitaNew")) {
                if (utente.getTipAggregazione() != null) {
                    query = query + "and a.tip_aggregazione=? ";
                }
            }
            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            st.setString(2, "%" + param + "%");
            st.setString(3, "%" + param + "%");
            if (!(Boolean) session.getAttribute("territorialitaNew")) {
                if (utente.getTipAggregazione() != null) {
                    st.setString(4, utente.getTipAggregazione());
                }
            }
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);

            query = "select a.cod_doc_onere,a.tip_doc,a.nome_file,b.des_doc_onere, a.tip_aggregazione, e.des_aggregazione "
                    + "from oneri_documenti a "
                    + "join oneri_documenti_testi b "
                    + "on a.cod_doc_onere=b.cod_doc_onere "
                    + "and a.cod_lang=b.cod_lang "
                    + "left join tipi_aggregazione e "
                    + "on a.tip_aggregazione = e.tip_aggregazione "
                    + "where a.cod_lang='it' "
                    + "and (b.des_doc_onere like ? or a.nome_file like ? or a.cod_doc_onere like ? ) ";
            if (!(Boolean) session.getAttribute("territorialitaNew")) {
                if (utente.getTipAggregazione() != null) {
                    query = query + "and a.tip_aggregazione=? ";
                }
            }
            query = query + "order by " + sort + " " + order + " limit ? , ?";

            st = conn.prepareStatement(query);
            st.setString(qsi, "%" + param + "%");
            qsi++;
            st.setString(qsi, "%" + param + "%");
            qsi++;
            st.setString(qsi, "%" + param + "%");
            qsi++;
            if (!(Boolean) session.getAttribute("territorialitaNew")) {
                if (utente.getTipAggregazione() != null) {
                    st.setString(qsi, utente.getTipAggregazione());
                    qsi++;
                }
            }
            st.setInt(qsi, Integer.parseInt(offset));
            qsi++;
            st.setInt(qsi, Integer.parseInt(size));
            rs = st.executeQuery();

            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_doc_onere", rs.getString("cod_doc_onere"));
                loopObj.put("des_doc_onere", Utility.testoDaDb(rs.getString("des_doc_onere")));
                loopObj.put("nome_file", Utility.testoDaDb(rs.getString("nome_file")));
                loopObj.put("tip_doc", Utility.testoDaDb(rs.getString("tip_doc")));
                loopObj.put("tip_aggregazione", rs.getString("tip_aggregazione"));
                loopObj.put("des_aggregazione", rs.getString("des_aggregazione"));
                if (!rs.getString("nome_file").equals("")) {
                    loopObj.put("nome_file", "<a href=\"ScaricaFile?tipo=tariffari&codDoc=" + rs.getString("cod_doc_onere") + "\"target=\"_blank\" alt=\"" + rs.getString("nome_file") + "\">" + rs.getString("nome_file") + "</a>");
                }
                riga.add(loopObj);
            }
            ret = new JSONObject();
            ret.put("totalCount", righe);
            ret.put("oneri_documenti", riga);
        } finally {

            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public Tariffario leggi(String codDocOnere) throws Exception {
        Tariffario tariffario = null;
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        int righe = 0;
        try {
            conn = Utility.getConnection();
            String query = "select a.cod_doc_onere, a.cod_lang, a.tip_doc, a.nome_file, a.doc_blob, b.des_doc_onere "
                    + "from oneri_documenti a "
                    + "join oneri_documenti_testi b "
                    + "on a.cod_doc_onere=b.cod_doc_onere "
                    + "and a.cod_lang=b.cod_lang "
                    + "where a.cod_lang='it' "
                    + "and a.cod_doc_onere = ?";
            st = conn.prepareStatement(query);
            st.setString(1, codDocOnere);
            rs = st.executeQuery();
            while (rs.next()) {
                tariffario = new Tariffario();
                tariffario.setCod_doc_onere(rs.getString("cod_doc_onere"));
                tariffario.setCod_lang(rs.getString("cod_lang"));
                tariffario.setDes_doc_onere(Utility.testoDaDb(rs.getString("des_doc_onere")));
                tariffario.setNome_file(Utility.testoDaDb(rs.getString("nome_file")));
                tariffario.setTip_doc(rs.getString("tip_doc"));
                tariffario.setDoc_blob(rs.getBlob("doc_blob"));
            }
        } finally {

            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return tariffario;
    }

    public JSONObject inserisci(OneriDocumentiModel odm, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        String query;
        try {
            conn = Utility.getConnection();
            ret = new JSONObject();
            FileUploadModel fum = odm.getFileUpload();
            query = "insert into oneri_documenti (cod_doc_onere,cod_lang,nome_file,tip_doc,doc_blob,tip_aggregazione) "
                    + "values (?,'it',?,?,?,?)";
            st = conn.prepareStatement(query);
            st.setString(1, odm.getCodDocOnere());
            st.setString(2, fum.getNomeFile());
            st.setString(3, fum.getTipDoc());

            st.setBinaryStream(4, fum.getDocBlob(), fum.getLength().intValue());
            st.setString(5, request.getParameter("tip_aggregazione"));
            st.executeUpdate();
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);

            query = "insert into oneri_documenti_testi (cod_doc_onere,des_doc_onere,cod_lang) "
                    + "values (?,?,'it')";
            st = conn.prepareStatement(query);
            st.setString(1, odm.getCodDocOnere());
            st.setString(2, odm.getDesDocOnere());
            st.executeUpdate();
            ret.put("success", "Inserimento effettuato");
            ret.put("nome_file", fum.getNomeFile());
            ret.put("codice", odm.getCodDocOnere());
        } catch (SQLException e) {
            log.error("Errore insert ", e);
            ret = new JSONObject();
            ret.put("failure", e.getMessage());
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public JSONObject aggiorna(OneriDocumentiModel odm, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        String query;
        try {
            conn = Utility.getConnection();
            ret = new JSONObject();
            FileUploadModel fum = odm.getFileUpload();
            query = "update oneri_documenti "
                    + "set nome_file=?, "
                    + "tip_doc=?, "
                    + "doc_blob=? "
                    + "where cod_doc_onere=? and cod_lang='it'";
            st = conn.prepareStatement(query);
            st.setString(1, fum.getNomeFile());
            st.setString(2, fum.getTipDoc());
            st.setBinaryStream(3, fum.getDocBlob(), fum.getLength().intValue());
            st.setString(4, odm.getCodDocOnere());

            st.executeUpdate();
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            if ((Boolean) session.getAttribute("territorialitaNew")) {
//                if (utente.getTipAggregazione() != null) {
                    if (odm.getTipAggregazione() != null) {
                        query = "update oneri_documenti "
                                + "set tip_aggregazione = ?"
                                + "where cod_doc_onere =? and tip_aggregazione is null";
                        st = conn.prepareStatement(query);
                        st.setString(1, odm.getTipAggregazione());
                        st.setString(2, odm.getCodDocOnere());
                        st.executeUpdate();
                        Utility.chiusuraJdbc(rs);
                        Utility.chiusuraJdbc(st);
                    }
//                }
            }
            query = "update oneri_documenti_testi "
                    + "set des_doc_onere=?  "
                    + "where cod_doc_onere = ? and cod_lang='it'";
            st = conn.prepareStatement(query);
            st.setString(1, Utility.testoADb(odm.getDesDocOnere()));
            st.setString(2, odm.getCodDocOnere());
            st.executeUpdate();
            ret.put("success", "Aggiornamento effettuato");
            ret.put("nome_file", fum.getNomeFile());
            ret.put("codice", odm.getCodDocOnere());
        } catch (SQLException e) {
            log.error("Errore update ", e);
            ret = new JSONObject();
            ret.put("failure", e.getMessage());
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public boolean controlloCancella(String key, Connection conn) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        boolean controllo = true;
        int righe = 0;
        try {
            String query = "select count(*) righe, 'oneri' tabella from oneri where cod_doc_onere=?";
            st = conn.prepareStatement(query);
            st.setString(1, key);
            rs = st.executeQuery();
            while (rs.next()) {
                righe = righe + rs.getInt("righe");
            }
            if (righe > 0) {
                controllo = false;
            }
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
        }
        return controllo;
    }

    public JSONObject cancella(OneriDocumentiModel odm) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        int righe = 0;
        boolean controllo = true;
        String query;
        JSONObject jo = null;
        try {
            conn = Utility.getConnection();
            ret = new JSONObject();
            controllo = controlloCancella(odm.getCodDocOnere(), conn);
            if (controllo) {
                query = "delete from oneri_documenti where cod_doc_onere=?";
                st = conn.prepareStatement(query);
                st.setString(1, odm.getCodDocOnere());
                st.executeUpdate();
                Utility.chiusuraJdbc(rs);
                Utility.chiusuraJdbc(st);

                query = "delete from oneri_documenti_testi where cod_doc_onere=?";
                st = conn.prepareStatement(query);
                st.setString(1, odm.getCodDocOnere());
                st.executeUpdate();
                Utility.chiusuraJdbc(rs);
                Utility.chiusuraJdbc(st);
                ret.put("success", "Cancellazione effettuato");
            } else {
                ret.put("failure", "Tariffario in uso");
            }
        } catch (SQLException e) {
            log.error("Errore delete ", e);
            ret = new JSONObject();
            ret.put("failure", e.getMessage());
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }
}
