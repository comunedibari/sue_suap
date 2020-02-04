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

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.slf4j.Logger;

import it.people.dbm.model.Modifica;
import it.people.dbm.model.Utente;
import it.people.dbm.utility.Configuration;
import it.people.dbm.utility.RisaliGerarchiaUtenti;
import it.people.dbm.utility.Utility;
import it.people.dbm.xsd.sqlNotifiche.DocumentRoot;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.LoggerFactory;
/**
 *
 * @author Piergiorgio
 */
public class TestoCondizioni {

    private static Logger log = LoggerFactory.getLogger(TestoCondizioni.class);

    public JSONObject action(HttpServletRequest request) throws Exception {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        PreparedStatement stLang = null;
        ResultSet rsLang = null;
        JSONObject ret = null;
        int righe = 0;
        String query = null;
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        int qsi = 1;
        try {
            conn = Utility.getConnection();
            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            String offset = request.getParameter("start");
            String size = request.getParameter("size");
            String sort = request.getParameter("sort");
            String order = request.getParameter("dir");
            String href = request.getParameter("href");
            String param = Utility.testoADb(request.getParameter("query"));

            query = "select count(*) righe from"
                    + "( select a.cod_cond, a.testo_cond, a.cod_lang, a.tip_aggregazione from testo_condizioni a ";
            if (utente.getRuolo().equals("A") || utente.getRuolo().equals("B")) {
                query = query + "where a.cod_cond in "
                        + "(select distinct x.cod_cond from allegati x "
                        + "join utenti_interventi y "
                        + "on x.cod_int=y.cod_int "
                        + "where y.cod_utente='" + utente.getCodUtente() + "' "
                        + "and cod_cond is not null "
                        + "union "
                        + "select distinct x.cod_cond from interventi_collegati x "
                        + "join utenti_interventi y "
                        + "on x.cod_int_padre=y.cod_int "
                        + "where y.cod_utente='" + utente.getCodUtente() + "' "
                        + "and cod_cond is not null) "
                        + "union "
                        + "select a.cod_cond, a.testo_cond, a.cod_lang, a.tip_aggregazione "
                        + "from testo_condizioni a "
                        + "where a.cod_cond not in "
                        + "(select distinct cod_cond from allegati where cod_cond is not null "
                        + "union "
                        + "select distinct cod_cond from interventi_collegati   where  cod_cond is not null) ";
            }
            query = query + ") p "
                    + "where p.cod_lang='it' and (p.testo_cond like ? or p.cod_cond like ? ) ";
            if (!(Boolean) session.getAttribute("territorialitaNew")) {
                if (utente.getTipAggregazione() != null) {
                    query = query + "and p.tip_aggregazione=? ";
                }
            }
            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            st.setString(2, "%" + param + "%");
            if (!(Boolean) session.getAttribute("territorialitaNew")) {
                if (utente.getTipAggregazione() != null) {
                    st.setString(3, utente.getTipAggregazione());
                }
            }
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            query = "select  p.cod_cond cod_cond, p.testo_cond testo_cond, p.tip_aggregazione, p.des_aggregazione from"
                    + "( select a.cod_cond, a.testo_cond, a.cod_lang, a.tip_aggregazione, e.des_aggregazione "
                    + "from testo_condizioni a "
                    + "left join tipi_aggregazione e "
                    + "on a.tip_aggregazione = e.tip_aggregazione ";
            if (utente.getRuolo().equals("A") || utente.getRuolo().equals("B")) {
                query = query + "where a.cod_cond in "
                        + "(select distinct x.cod_cond from allegati x "
                        + "join utenti_interventi y "
                        + "on x.cod_int=y.cod_int "
                        + "where y.cod_utente='" + utente.getCodUtente() + "' "
                        + "and cod_cond is not null "
                        + "union "
                        + "select distinct x.cod_cond from interventi_collegati x "
                        + "join utenti_interventi y "
                        + "on x.cod_int_padre=y.cod_int "
                        + "where y.cod_utente='" + utente.getCodUtente() + "' "
                        + "and cod_cond is not null) "
                        + "union "
                        + "select a.cod_cond, a.testo_cond, a.cod_lang, a.tip_aggregazione, e.des_aggregazione "
                        + "from testo_condizioni a "
                        + "left join tipi_aggregazione e "
                        + "on a.tip_aggregazione = e.tip_aggregazione "
                        + "where a.cod_cond not in "
                        + "(select distinct cod_cond from allegati where cod_cond is not null "
                        + "union "
                        + "select distinct cod_cond from interventi_collegati   where  cod_cond is not null) ";
            }
            query = query + ") p "
                    + "where p.cod_lang='it' and (p.testo_cond like ? or p.cod_cond like ? ) ";
            if (!(Boolean) session.getAttribute("territorialitaNew")) {
                if (utente.getTipAggregazione() != null) {
                    query = query + "and p.tip_aggregazione=? ";
                }
            }
            query = query + "order by " + sort + " " + order + " limit ? , ?";
            st = conn.prepareStatement(query);
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
            String queryLang = "select testo_cond, cod_lang from testo_condizioni where cod_cond = ?";
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_cond", rs.getString("cod_cond"));
                stLang = conn.prepareStatement(queryLang);
                stLang.setString(1, rs.getString("cod_cond"));
                loopObj.put("tip_aggregazione", rs.getString("tip_aggregazione"));
                loopObj.put("des_aggregazione", rs.getString("des_aggregazione"));
                rsLang = stLang.executeQuery();
                while (rsLang.next()) {
                    loopObj.put("testo_cond_" + rsLang.getString("cod_lang"), Utility.testoDaDb(rsLang.getString("testo_cond")));
                }
                Utility.chiusuraJdbc(rsLang);
                Utility.chiusuraJdbc(stLang);
                riga.add(loopObj);

            }
            ret = new JSONObject();
            ret.put("totalCount", righe);
            ret.put("testo_condizioni", riga);
        } catch (Exception e) {
            log.error("Errore select ", e);
            ret = new JSONObject();
            ret.put("failure", e.getMessage());
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(rsLang);
            Utility.chiusuraJdbc(stLang);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public JSONObject inserisci(HttpServletRequest request) throws Exception {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        String query;
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        try {
            conn = Utility.getConnection();
            ret = new JSONObject();
            query = "insert into testo_condizioni (cod_cond,testo_cond,cod_lang,tip_aggregazione) "
                    + "values (?,?,?,?)";
            Set<String> parmKey = request.getParameterMap().keySet();
            for (String key : parmKey) {
                String[] parSplit = key.split("testo_cond_");
                if (parSplit.length > 1 && key.contains("testo_cond_")) {
                    String cod_lang = parSplit[1];
                    if (cod_lang != null) {
                        st = conn.prepareStatement(query);
                        st.setString(1, request.getParameter("cod_cond"));
                        st.setString(2, Utility.testoADb(request.getParameter("testo_cond_" + cod_lang)));
                        st.setString(3, cod_lang);
                        st.setString(4, request.getParameter("tip_aggregazione"));
                        st.executeUpdate();
                        Utility.chiusuraJdbc(rs);
                        Utility.chiusuraJdbc(st);
                    }
                }
            }
            ret.put("success", "Inserimento effettuato");
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

    public JSONObject aggiorna(HttpServletRequest request) throws Exception {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        PreparedStatement stRead = null;
        ResultSet rsRead = null;
        JSONObject ret = null;
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        String query;
        try {
            conn = Utility.getConnection();
            Modifica verifica = verificaNotifica(request.getParameter("cod_cond"), request, conn);
            ret = new JSONObject();
            if (!verifica.isNotifica()) {
                String querySelect = "select * from testo_condizioni where cod_cond = ? and cod_lang = ?";
                String queryInsert = "insert into testo_condizioni (cod_cond,cod_lang,testo_cond,tip_aggregazione) values (?,?,?,? )";

                query = "update testo_condizioni set testo_cond=? "
                        + "where cod_cond=? and cod_lang=?";
                String queryAggregazione = "update testo_condizioni "
                        + "set tip_aggregazione = ?"
                        + "where cod_cond =? and cod_lang = ? and tip_aggregazione is null";
                Set<String> parmKey = request.getParameterMap().keySet();
                for (String key : parmKey) {
                    String[] parSplit = key.split("testo_cond_");
                    if (parSplit.length > 1 && key.contains("testo_cond_")) {
                        String cod_lang = parSplit[1];
                        if (cod_lang != null) {
                            stRead = conn.prepareStatement(querySelect);
                            stRead.setString(1, request.getParameter("cod_cond"));
                            stRead.setString(2, cod_lang);
                            rsRead = stRead.executeQuery();
                            if (rsRead.next()) {
                                st = conn.prepareStatement(query);
                                st.setString(1, Utility.testoADb(request.getParameter(key)));
                                st.setString(2, request.getParameter("cod_cond"));
                                st.setString(3, cod_lang);
                                st.executeUpdate();
                                Utility.chiusuraJdbc(rs);
                                Utility.chiusuraJdbc(st);
                                if ((Boolean) session.getAttribute("territorialitaNew")) {
//                                    if (utente.getTipAggregazione() != null) {
                                        if (request.getParameter("tip_aggregazione") != null) {
                                            st = conn.prepareStatement(queryAggregazione);
                                            st.setString(1, request.getParameter("tip_aggregazione"));
                                            st.setString(2, request.getParameter("cod_cond"));
                                            st.setString(3, cod_lang);
                                            st.executeUpdate();
                                            Utility.chiusuraJdbc(rs);
                                            Utility.chiusuraJdbc(st);
                                        }
//                                    }
                                }
                            } else {
                                st = conn.prepareStatement(queryInsert);
                                st.setString(1, request.getParameter("cod_cond"));
                                st.setString(2, cod_lang);
                                st.setString(3, Utility.testoADb(request.getParameter(key)));
                                st.setString(4, utente.getTipAggregazione());
                                st.execute();
                            }
                            Utility.chiusuraJdbc(rs);
                            Utility.chiusuraJdbc(st);
                            Utility.chiusuraJdbc(rsRead);
                            Utility.chiusuraJdbc(stRead);
                        }
                    }
                }

                scriviTCR(verifica, request, conn);
                ret.put("success", "Aggiornamento effettuato");

            } else {
                Utility.scriviSessione(request);
                JSONObject msg = new JSONObject();
                msg.put("notifica", "La risorsa è condivisa(non effettuo la modifica); Vuoi inviare una notifica?");
                ret.put("failure", msg);
            }

        } catch (SQLException e) {
            log.error("Errore aggiornamento ", e);
            ret = new JSONObject();
            ret.put("failure", e.getMessage());
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(rsRead);
            Utility.chiusuraJdbc(stRead);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public boolean cancellaControllo(String key, Connection conn) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        String query;
        boolean controllo = true;
        int righe = 0;
        try {
            query = "select count(*) righe from allegati where cod_cond = ? "
                    + "union "
                    + "select count(*) righe from interventi_collegati where cod_cond = ? "
                    + "union "
                    + "select count(*) righe from condizioni_normative where cod_cond = ? ";

            st = conn.prepareStatement(query);
            st.setString(1, key);
            st.setString(2, key);
            st.setString(3, key);
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

    public JSONObject cancella(HttpServletRequest request) throws Exception {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        String query;
        boolean controllo = false;
        try {
            conn = Utility.getConnection();
            ret = new JSONObject();
            controllo = cancellaControllo(request.getParameter("cod_cond"), conn);
            if (controllo) {
                query = "delete from testo_condizioni where cod_cond=?";
                st = conn.prepareStatement(query);
                st.setString(1, request.getParameter("cod_cond"));
                st.executeUpdate();

                ret.put("success", "Cancellazione effettuata");
            } else {
                ret.put("failure", "Condizione in uso");
            }
        } catch (SQLException e) {
            log.error("Errore cancellazione ", e);
            ret = new JSONObject();
            ret.put("failure", e.getMessage());
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public Modifica verificaNotifica(String codCond, HttpServletRequest request, Connection conn) throws Exception {
        Modifica ret = new Modifica();
        ret.init();
        PreparedStatement st = null;
        ResultSet rs = null;
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        int righe = 0;
        String sql = null;
        try {
            sql = "select cod_cond,testo_cond from testo_condizioni where cod_cond = ? and cod_lang='it'";
            st = conn.prepareStatement(sql);
            st.setString(1, codCond);
            rs = st.executeQuery();
            if (rs.next()) {
                if (!request.getParameter("testo_cond_it").equals(rs.getString("testo_cond"))) {
                    ret.setModificaIntervento(true);
                }
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            if (utente.getRuolo().equals("A") || utente.getRuolo().equals("B")) {
                if (ret.isModificaIntervento()) {
                    sql = "select count(*) righe from (select distinct cod_int from "
                            + "(select cod_int from allegati where cod_cond = ? "
                            + "union "
                            + "select cod_int from interventi_collegati "
                            + "where cod_cond = ? "
                            + "union "
                            + "select b.cod_int from interventi_collegati a "
                            + "join interventi b "
                            + "on a.cod_int_padre=b.cod_int "
                            + "where a.cod_cond = ? ) a "
                            + ") b "
                            + "join utenti_interventi x "
                            + "on b.cod_int = x.cod_int "
                            + "where x.cod_utente <> ?";
                    st = conn.prepareStatement(sql);
                    st.setString(1, codCond);
                    st.setString(2, codCond);
                    st.setString(3, codCond);
                    st.setString(4, utente.getCodUtente());
                    rs = st.executeQuery();
                    if (rs.next()) {
                        righe = rs.getInt("righe");
                    }
                    if (righe > 0) {
                        ret.setNotifica(true);
                    }
                }
            }
        } catch (SQLException e) {
            log.error("Errore verifica per notifica ", e);
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
        }

        return ret;
    }

    public JSONObject scriviNotifica(HttpServletRequest request) throws Exception {
        DocumentRoot doc = new DocumentRoot();

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        String sql;
        boolean controllo = false;
        HashMap<String, String> param = null;
        HashMap<String, Object> funzRequest = null;
        if (session.getAttribute("sessioneTabelle") != null) {
            funzRequest = (HashMap<String, Object>) session.getAttribute("sessioneTabelle");
        } else {
            funzRequest = new HashMap<String, Object>();
        }
        if (funzRequest.containsKey(request.getParameter("table_name"))) {
            param = (HashMap<String, String>) funzRequest.get(request.getParameter("table_name"));
        } else {
            param = new HashMap<String, String>();
        }
        HashMap<String, List<String>> listaUtentiInterventi = new HashMap<String, List<String>>();
        List<String> listaInterventi = new ArrayList<String>();
        List<String> li = null;

        try {
            conn = Utility.getConnection();
            ret = new JSONObject();
            String codCond = param.get("cod_cond");
            sql = "select distinct x.cod_utente, x.cod_int from "
                    + "(select distinct cod_int from "
                    + "(select cod_int from allegati "
                    + "where cod_cond = ? "
                    + "union "
                    + "select cod_int from interventi_collegati "
                    + "where cod_cond = ? "
                    + "union "
                    + "select b.cod_int from interventi_collegati a "
                    + "join interventi b "
                    + "on a.cod_int_padre=b.cod_int "
                    + "where a.cod_cond = ?) a "
                    + ") b "
                    + "join utenti_interventi x "
                    + "on b.cod_int = x.cod_int "
                    + "order by x.cod_utente,x.cod_int";
            st = conn.prepareStatement(sql);
            st.setString(1, codCond);
            st.setString(2, codCond);
            st.setString(3, codCond);
            rs = st.executeQuery();
            while (rs.next()) {
                if (!listaInterventi.contains(String.valueOf(rs.getInt("cod_int")))) {
                    listaInterventi.add(String.valueOf(rs.getInt("cod_int")));
                }
                if (listaUtentiInterventi.containsKey(rs.getString("cod_utente"))) {
                    if (!listaUtentiInterventi.get(rs.getString("cod_utente")).contains(String.valueOf(rs.getInt("cod_int")))) {
                        listaUtentiInterventi.get(rs.getString("cod_utente")).add(String.valueOf(rs.getInt("cod_int")));
                    }
                } else {
                    li = new ArrayList<String>();
                    li.add(String.valueOf(rs.getInt("cod_int")));
                    listaUtentiInterventi.put(rs.getString("cod_utente"), li);
                }
                if (listaUtentiInterventi.size() > 0) {
                    RisaliGerarchiaUtenti rgu = new RisaliGerarchiaUtenti();
                    rgu.risaliGerarchiaUtenti(rs.getString("cod_utente"), listaUtentiInterventi);
                }
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            if (listaUtentiInterventi.size() > 0) {
                // componi testo notifica
                String msg = "Id Condizione = " + codCond + "\n";
                msg = msg + "Nuovo testo Condizione = " + param.get("testo_cond") + "\n";
                msg = msg + request.getParameter("textMsg");
                // componi stringa sql
                String sqlDeferred = "update testo_condizioni set testo_cond = '" + Utility.testoADbNotifica(param.get("testo_cond_it")) + "' where cod_cond = '" + codCond + "' and cod_lang='it';";
                // carica stringa sql nell'xml
                doc.getSqlString().add(sqlDeferred.getBytes());
                // inserisco la string si modifica intervento
                sqlDeferred = "update interventi set data_modifica=current_timestamp where cod_int in (select distinct cod_int from allegati where cod_cond = '" + codCond + "')";
                doc.getSqlString().add(sqlDeferred.getBytes());
                // inserisco in notifica
                sql = "insert into notifiche (cod_utente_origine,testo_notifica,stato_notifica,cod_elemento) values (?,?,?,?)";
                st = conn.prepareStatement(sql);
                st.setString(1, utente.getCodUtente());
                st.setString(2, msg);
                st.setString(3, "N");
                st.setString(4, "TestoCondizione=" + codCond);

                st.executeUpdate();
                Utility.chiusuraJdbc(rs);
                Utility.chiusuraJdbc(st);
                // recupero in nuovo id
                sql = "select max(last_insert_id()) last_insert from notifiche";
                int contatore = 0;
                st = conn.prepareStatement(sql);
                rs = st.executeQuery();
                while (rs.next()) {
                    contatore = rs.getInt("last_insert");
                }
                Utility.chiusuraJdbc(rs);
                Utility.chiusuraJdbc(st);

                // inserisco la lista degli interventi interessati
                Iterator it = listaUtentiInterventi.keySet().iterator();
                while (it.hasNext()) {
                    String key = (String) it.next();
                    if (listaUtentiInterventi.get(key).size() > 0) {
                        Iterator itl = listaUtentiInterventi.get(key).iterator();
                        while (itl.hasNext()) {
                            sql = "insert into notifiche_utenti_interventi (cnt,cod_int,cod_utente) values (?,?,?)";
                            st = conn.prepareStatement(sql);
                            st.setInt(1, contatore);
                            st.setInt(2, Integer.valueOf((String) itl.next()));
                            st.setString(3, key);
                            st.executeUpdate();
                            Utility.chiusuraJdbc(rs);
                            Utility.chiusuraJdbc(st);
                        }
                    }
                }
                Utility.chiusuraJdbc(rs);
                Utility.chiusuraJdbc(st);
                // inserisco la riga nella tabella degli sql
                String xml;
                JAXBContext jc = JAXBContext.newInstance(DocumentRoot.class);
                Marshaller m = jc.createMarshaller();
                m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                m.marshal(doc, os);
                xml = os.toString("UTF-8");
                sql = "insert into notifiche_sql (cnt,sql_text) values (?,?)";
                st = conn.prepareStatement(sql);
                st.setInt(1, contatore);
                st.setString(2, xml);
                st.executeUpdate();
            }
            // cancella la session

            if (funzRequest.containsKey(request.getParameter("table_name"))) {
                funzRequest.remove(request.getParameter("table_name"));
            }

            ret.put("success", "Invio notifica effettuata");
        } catch (SQLException e) {
            log.error("Errore invio notifica ", e);
            ret = new JSONObject();
            ret.put("failure", e.getMessage());
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public void scriviTCR(Modifica verifica, HttpServletRequest request, Connection conn) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        String sql;
        try {
            if (verifica.isModificaIntervento()) {
                sql = "update interventi set data_modifica=current_timestamp where cod_int in (select distinct cod_int from allegati where cod_cond = ?)";
                st = conn.prepareStatement(sql);
                st.setString(1, request.getParameter("cod_cond"));
                st.executeUpdate();
                Boolean aggiorna = Boolean.parseBoolean(Configuration.getConfigurationParameter("updateTCR").toLowerCase());
                if (aggiorna) {
                    sql = "update interventi set flg_pubblicazione='S' where cod_int in (select distinct cod_int from allegati where cod_cond = ?)";
                    st = conn.prepareStatement(sql);
                    st.setString(1, request.getParameter("cod_cond"));
                    st.executeUpdate();
                }
            }

        } catch (SQLException e) {
            log.error("Errore verifica per notifica per TCR", e);
            throw e;
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
        }
    }
}
