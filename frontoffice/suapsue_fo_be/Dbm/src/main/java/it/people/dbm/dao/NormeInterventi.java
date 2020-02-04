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
public class NormeInterventi {

    private static Logger log = LoggerFactory.getLogger(NormeInterventi.class);
    private String operazione = null;

    public JSONObject action(HttpServletRequest request) throws Exception {
        Connection conn = null;
        HttpSession session = request.getSession();
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        int righe = 0;
        String query = null;
        Utente utente = (Utente) session.getAttribute("utente");
        int indice = 1;
        try {
            conn = Utility.getConnection();
            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            String offset = request.getParameter("start");
            String size = request.getParameter("size");
            String sort = request.getParameter("sort");
            String order = request.getParameter("dir");
            String param = Utility.testoADb(request.getParameter("query"));
            String codiceIntervento = null;
            if (request.getParameter("cod_int") != null && !request.getParameter("cod_int").equals("")) {
                codiceIntervento = request.getParameter("cod_int");
            }
            query = "select count(*) righe "
                    + "from norme_interventi a "
                    + "join interventi b "
                    + "on a.cod_int=b.cod_int "
                    + "join interventi_testi c "
                    + "on a.cod_int=c.cod_int "
                    + "join normative d "
                    + "on a.cod_rif=d.cod_rif "
                    + "join normative_testi e "
                    + "on a.cod_rif=e.cod_rif "
                    + "and c.cod_lang=e.cod_lang "
                    + "join tipi_rif f "
                    + "on d.cod_tipo_rif=f.cod_tipo_rif "
                    + "and c.cod_lang = f.cod_lang "
                    + "left join normative_documenti g "
                    + "on a.cod_rif = g.cod_rif "
                    + "and g.cod_lang=c.cod_lang "
                    + "where c.cod_lang='it' ";
            if (utente.getRuolo().equals("A") || utente.getRuolo().equals("B")) {
                query = query + "and a.cod_int in (select distinct cod_int from utenti_interventi "
                        + " where cod_utente='" + utente.getCodUtente() + "') ";
            }

            query = query + "and ( convert(a.cod_int,CHAR) like ? or a.cod_rif like ? or c.tit_int like ? or e.tit_rif like ? or a.art_rif like ? ) ";
            if (codiceIntervento != null) {
                query = query + "and (a.cod_int = ?) ";
            }
            if (utente.getTipAggregazione() != null) {
                query = query + "and b.tip_aggregazione=? ";
            }
            st = conn.prepareStatement(query);
            st.setString(indice, "%" + param + "%");
            indice++;
            st.setString(indice, "%" + param + "%");
            indice++;
            st.setString(indice, "%" + param + "%");
            indice++;
            st.setString(indice, "%" + param + "%");
            indice++;
            st.setString(indice, "%" + param + "%");
            indice++;
            if (codiceIntervento != null) {
                st.setInt(indice, Integer.parseInt(codiceIntervento));
                indice++;
            }
            if (utente.getTipAggregazione() != null) {
                st.setString(indice, utente.getTipAggregazione());
            }
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            query = "select a.cod_int cod_int,c.tit_int tit_int,a.cod_rif cod_rif,e.tit_rif tit_rif, a.art_rif art_rif, d.nome_rif nome_rif, "
                    + "d.cod_tipo_rif cod_tipo_rif, f.tipo_rif tipo_rif, g.nome_file nome_file, g.tip_doc tip_doc "
                    + "from norme_interventi a "
                    + "join interventi b "
                    + "on a.cod_int=b.cod_int "
                    + "join interventi_testi c "
                    + "on a.cod_int=c.cod_int "
                    + "join normative d "
                    + "on a.cod_rif=d.cod_rif "
                    + "join normative_testi e "
                    + "on a.cod_rif=e.cod_rif "
                    + "and c.cod_lang=e.cod_lang "
                    + "join tipi_rif f "
                    + "on d.cod_tipo_rif=f.cod_tipo_rif "
                    + "and c.cod_lang = f.cod_lang "
                    + "left join normative_documenti g "
                    + "on a.cod_rif = g.cod_rif "
                    + "and g.cod_lang=c.cod_lang "
                    + "where c.cod_lang='it' ";
            if (utente.getRuolo().equals("A") || utente.getRuolo().equals("B")) {
                query = query + "and a.cod_int in (select distinct cod_int from utenti_interventi "
                        + " where cod_utente='" + utente.getCodUtente() + "') ";
            }

            query = query + "and ( convert(a.cod_int,CHAR) like ? or a.cod_rif like ? or c.tit_int like ? or e.tit_rif like ? or a.art_rif like ? ) ";
            if (codiceIntervento != null) {
                query = query + "and (a.cod_int = ?) ";
            }
            if (utente.getTipAggregazione() != null) {
                query = query + "and b.tip_aggregazione=? ";
            }
            query = query + "order by " + sort + " " + order + " limit ? , ?";

            st = conn.prepareStatement(query);
            indice = 1;
            st.setString(1, "%" + param + "%");
            indice++;
            st.setString(2, "%" + param + "%");
            indice++;
            st.setString(3, "%" + param + "%");
            indice++;
            st.setString(4, "%" + param + "%");
            indice++;
            st.setString(5, "%" + param + "%");
            indice++;
            if (codiceIntervento != null) {
                st.setInt(indice, Integer.parseInt(codiceIntervento));
                indice++;
            }
            if (utente.getTipAggregazione() != null) {
                st.setString(indice, utente.getTipAggregazione());
                indice++;
            }
            st.setInt(indice, Integer.parseInt(offset));
            indice++;
            st.setInt(indice, Integer.parseInt(size));
            rs = st.executeQuery();

            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_int", rs.getInt("cod_int"));
                loopObj.put("tit_int", Utility.testoDaDb(rs.getString("tit_int")));
                loopObj.put("cod_rif", rs.getString("cod_rif"));
                loopObj.put("tit_rif", Utility.testoDaDb(rs.getString("tit_rif")));
                loopObj.put("art_rif", Utility.testoDaDb(rs.getString("art_rif")));
                loopObj.put("nome_rif", Utility.testoDaDb(rs.getString("nome_rif")));
                loopObj.put("cod_tipo_rif", rs.getString("cod_tipo_rif"));
                loopObj.put("tipo_rif", rs.getString("tipo_rif"));
                loopObj.put("nome_file", rs.getString("nome_file"));
                if (rs.getString("nome_file") != null && !rs.getString("nome_file").equals("")) {
                    loopObj.put("nome_file", "<a href=\"ScaricaFile?tipo=normativa&codNorma=" + rs.getString("cod_rif") + "&tipDoc=" + rs.getString("tip_doc") + "\"target=\"_blank\" alt=\"" + rs.getString("nome_file") + "\">" + rs.getString("nome_file") + "</a>");
                }
                riga.add(loopObj);
            }
            ret = new JSONObject();
            ret.put("totalCount", righe);
            ret.put("norme_interventi", riga);
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
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
        operazione = "inserimento";
        try {
            conn = Utility.getConnection();
            ret = new JSONObject();
            Modifica verifica = verificaNotifica(request, conn);
            if (!verifica.isNotifica()) {
                query = "insert into norme_interventi (cod_int,cod_rif,art_rif) "
                        + "values (?,?,?)";
                st = conn.prepareStatement(query);
                st.setInt(1, Integer.parseInt(request.getParameter("cod_int")));
                st.setString(2, request.getParameter("cod_rif"));
                st.setString(3, (request.getParameter("art_rif") != null && !request.getParameter("art_rif").equals("") ? Utility.testoADb(request.getParameter("art_rif")) : null));
                st.executeUpdate();
                scriviTCR(verifica, Integer.parseInt(request.getParameter("cod_int")), conn);
                ret.put("success", "Inserimento effettuato");
            } else {
                Utility.scriviSessione(request);
                JSONObject msg = new JSONObject();
                msg.put("notifica", "La risorsa è condivisa(non effettuo l'aggiornamento); Vuoi inviare una notifica?");
                ret.put("failure", msg);
            }
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
        JSONObject ret = null;
        String query;
        operazione = "modifica";
        try {
            conn = Utility.getConnection();
            ret = new JSONObject();
            Modifica verifica = verificaNotifica(request, conn);
            if (!verifica.isNotifica()) {
                if (request.getParameter("az_modifica").equals("cancella")) {
                    query = "insert into norme_interventi (cod_int,cod_rif,art_rif) "
                            + "values (?,?,?)";
                    st = conn.prepareStatement(query);
                    st.setInt(1, Integer.parseInt(request.getParameter("cod_int")));
                    st.setString(2, request.getParameter("cod_rif"));
                    st.setString(3, (request.getParameter("art_rif") != null && !request.getParameter("art_rif").equals("") ? Utility.testoADb(request.getParameter("art_rif")) : null));
                    st.executeUpdate();
                    Utility.chiusuraJdbc(rs);
                    Utility.chiusuraJdbc(st);
                    query = "delete from norme_interventi "
                            + "where cod_int=? and cod_rif=?";
                    st = conn.prepareStatement(query);
                    st.setInt(1, Integer.parseInt(request.getParameter("cod_int_old")));
                    st.setString(2, request.getParameter("cod_rif_old"));
                    st.executeUpdate();
                    scriviTCR(verifica, Integer.parseInt(request.getParameter("cod_int_old")), conn);
                    Utility.chiusuraJdbc(rs);
                    Utility.chiusuraJdbc(st);
                }
                if (request.getParameter("az_modifica").equals("mantieni")) {
                    query = "insert into norme_interventi (cod_int,cod_rif,art_rif) "
                            + "values (?,?,?)";
                    st = conn.prepareStatement(query);
                    st.setInt(1, Integer.parseInt(request.getParameter("cod_int")));
                    st.setString(2, request.getParameter("cod_rif"));
                    st.setString(3, (request.getParameter("art_rif") != null && !request.getParameter("art_rif").equals("") ? Utility.testoADb(request.getParameter("art_rif")) : null));
                    st.executeUpdate();
                    Utility.chiusuraJdbc(rs);
                    Utility.chiusuraJdbc(st);
                }
                if (request.getParameter("az_modifica").equals("")) {
                    query = "update norme_interventi set art_rif=? "
                            + "where cod_int=? and cod_rif=?";
                    st = conn.prepareStatement(query);
                    st.setString(1, (request.getParameter("art_rif") != null && !request.getParameter("art_rif").equals("") ? Utility.testoADb(request.getParameter("art_rif")) : null));
                    st.setInt(2, Integer.parseInt(request.getParameter("cod_int")));
                    st.setString(3, request.getParameter("cod_rif"));
                    st.executeUpdate();
                }
                scriviTCR(verifica, Integer.parseInt(request.getParameter("cod_int")), conn);
                ret.put("success", "Aggiornamento effettuato");
            } else {
                Utility.scriviSessione(request);
                JSONObject msg = new JSONObject();
                msg.put("notifica", "La risorsa è condivisa(non effettuo l'aggiornamento); Vuoi inviare una notifica?");
                ret.put("failure", msg);
            }
        } catch (SQLException e) {
            log.error("Errore aggiornamento ", e);
            ret = new JSONObject();
            ret.put("failure", e.getMessage());
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public JSONObject cancella(HttpServletRequest request) throws Exception {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        String query;
        operazione = "cancellazione";
        try {
            conn = Utility.getConnection();
            ret = new JSONObject();
            Modifica verifica = verificaNotifica(request, conn);
            if (!verifica.isNotifica()) {
                query = "delete from norme_interventi where cod_int=? and cod_rif=?";
                st = conn.prepareStatement(query);
                st.setInt(1, Integer.parseInt(request.getParameter("cod_int")));
                st.setString(2, request.getParameter("cod_rif"));
                st.executeUpdate();
                scriviTCR(verifica, Integer.parseInt(request.getParameter("cod_int")), conn);
                ret.put("success", "Cancellazione effettuata");
            } else {
                Utility.scriviSessione(request);
                JSONObject msg = new JSONObject();
                msg.put("notifica", "La risorsa è condivisa(non effettuo l'aggiornamento); Vuoi inviare una notifica?");
                ret.put("failure", msg);
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

    public Modifica verificaNotifica(HttpServletRequest request, Connection conn) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        Modifica modificato = new Modifica();
        modificato.init();
        int righe = 0;
        String sql = null;
        try {
            if (operazione.equals("inserimento")) {
                modificato.setModificaIntervento(true);
            }
            if (operazione.equals("cancellazione")) {
                modificato.setModificaIntervento(true);
            }
            if (operazione.equals("modifica")) {
                if (request.getParameter("az_modifica").equals("cancella")) {
                    modificato.setModificaIntervento(true);
                }
                if (request.getParameter("az_modifica").equals("mantieni")) {
                    modificato.setModificaIntervento(true);
                }
                if (request.getParameter("az_modifica").equals("")) {
                    sql = "select * from norme_interventi where cod_int=? and cod_rif=?";
                    st = conn.prepareStatement(sql);
                    st.setInt(1, Integer.parseInt(request.getParameter("cod_int")));
                    st.setString(2, request.getParameter("cod_rif"));
                    rs = st.executeQuery();
                    if (rs.next()) {
                        if (!request.getParameter("art_rif").equals(rs.getString("art_rif"))) {
                            modificato.setModificaIntervento(true);
                        }
                    }
                    Utility.chiusuraJdbc(rs);
                    Utility.chiusuraJdbc(st);
                }
            }

            if (utente.getRuolo().equals("A") || utente.getRuolo().equals("B")) {
                if (modificato.isModificaIntervento()) {
                    sql = "select count(*) conta from utenti_interventi where cod_utente <> ? and cod_int = ?";
                    st = conn.prepareStatement(sql);
                    st.setString(1, utente.getCodUtente());
                    st.setInt(2, Integer.parseInt(request.getParameter("cod_int")));
                    rs = st.executeQuery();
                    if (rs.next()) {
                        righe = rs.getInt("conta");
                    }
                    Utility.chiusuraJdbc(rs);
                    Utility.chiusuraJdbc(st);
                    if (righe > 0) {
                        modificato.setNotifica(true);
                    }
                }
            }
        } catch (SQLException e) {
            log.error("Errore verifica per notifica ", e);
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
        }
        return modificato;
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
            int codInt = Integer.parseInt(param.get("cod_int"));
            String codRif = param.get("cod_rif");
            sql = "select distinct x.cod_utente, x.cod_int from utenti_interventi x "
                    + "where x.cod_int = ? "
                    + "order by x.cod_utente,x.cod_int";
            st = conn.prepareStatement(sql);
            st.setInt(1, codInt);
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
                String msg = "";
                String sqlDeferred = "";
                if (request.getParameter("azione").equals("modifica")) {
                    msg = "Modifica \n";
                    msg = msg + "Id intervento = " + codInt + "\n";
                    msg = msg + "Id Normativa = " + codRif + "\n";
                    msg = msg + request.getParameter("textMsg");
                    if (param.get("az_modifica").equals("cancella")) {
                        sqlDeferred = "delete from norme_interventi "
                                + "where cod_int = " + Integer.parseInt(param.get("cod_int_old")) + " and cod_rif='" + param.get("cod_rif_old") + "';";
                        // carica stringa sql nell'xml
                        doc.getSqlString().add(sqlDeferred.getBytes());
                        sqlDeferred = "delete from norme_interventi "
                                + "where cod_int = " + codInt + " and cod_rif='" + codRif + "';";
                        // carica stringa sql nell'xml
                        doc.getSqlString().add(sqlDeferred.getBytes());
                        sqlDeferred = "insert into norme_interventi (cod_int,cod_rif,art_rif) values ("
                                + codInt + ",'" + codRif + "'," + (param.get("art_rif") == null || param.get("art_rif").equals("") ? null : "'" + param.get("art_rif") + "'") + ");";
                        // carica stringa sql nell'xml
                        doc.getSqlString().add(sqlDeferred.getBytes());
                        sqlDeferred = "update interventi set data_modifica=current_timestamp where cod_int = " + Integer.parseInt(param.get("cod_int_old"));
                        doc.getSqlString().add(sqlDeferred.getBytes());
                    }
                    if (param.get("az_modifica").equals("mantieni")) {
                        sqlDeferred = "delete from norme_interventi "
                                + "where cod_int = " + codInt + " and cod_rif='" + codRif + "';";
                        // carica stringa sql nell'xml
                        doc.getSqlString().add(sqlDeferred.getBytes());
                        sqlDeferred = "insert into norme_interventi (cod_int,cod_rif,art_rif) values ("
                                + codInt + ",'" + codRif + "'," + (param.get("art_rif") == null || param.get("art_rif").equals("") ? null : "'" + param.get("art_rif") + "'") + ");";
                        // carica stringa sql nell'xml
                        doc.getSqlString().add(sqlDeferred.getBytes());
                    }
                    if (param.get("az_modifica").equals("")) {
                        // componi stringa sql
                        sqlDeferred = "update norme_interventi "
                                + "set art_rif = " + (param.get("art_rif") == null || param.get("art_rif").equals("") ? null : "'" + param.get("art_rif") + "'") + " "
                                + "where cod_int = " + codInt + " and cod_rif='" + codRif + "';";
                        // carica stringa sql nell'xml
                        doc.getSqlString().add(sqlDeferred.getBytes());
                    }
                }
                if (request.getParameter("azione").equals("inserimento")) {
                    msg = "Inserimento \n";
                    msg = msg + "Id intervento = " + codInt + "\n";
                    msg = msg + "Id Normativa = " + codRif + "\n";
                    msg = msg + request.getParameter("textMsg");
                    // componi stringa sql
                    sqlDeferred = "insert into norme_interventi (cod_int,cod_rif,art_rif) values ("
                            + codInt + ",'" + codRif + "'," + (param.get("art_rif") == null || param.get("art_rif").equals("") ? null : "'" + param.get("art_rif") + "'") + ");";
                    // carica stringa sql nell'xml
                    doc.getSqlString().add(sqlDeferred.getBytes());
                }
                if (request.getParameter("azione").equals("cancellazione")) {
                    msg = "Cancellazione \n";
                    msg = msg + "Id intervento = " + codInt + "\n";
                    msg = msg + "Id Normativa = " + codRif + "\n";
                    msg = msg + request.getParameter("textMsg");
                    // componi stringa sql
                    sqlDeferred = "delete from norme_interventi "
                            + "where cod_int = " + codInt + " and cod_rif='" + codRif + "';";
                    // carica stringa sql nell'xml
                    doc.getSqlString().add(sqlDeferred.getBytes());
                }
                sqlDeferred = "update interventi set data_modifica=current_timestamp where cod_int = " + codInt;
                doc.getSqlString().add(sqlDeferred.getBytes());
                // inserisco in notifica
                sql = "insert into notifiche (cod_utente_origine,testo_notifica,stato_notifica,cod_elemento) values (?,?,?,?)";
                st = conn.prepareStatement(sql);
                st.setString(1, utente.getCodUtente());
                st.setString(2, msg);
                st.setString(3, "N");
                st.setString(4, "NormeInterventi=" + codInt + "|" + codRif);

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

    public void scriviTCR(Modifica modificato, Integer codInt, Connection conn) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        String sql = null;
        try {
            if (modificato.isModificaIntervento()) {
                sql = "update interventi set data_modifica=current_timestamp where cod_int = ?";
                st = conn.prepareStatement(sql);
                st.setInt(1, codInt);
                st.executeUpdate();
                Boolean aggiorna = Boolean.parseBoolean(Configuration.getConfigurationParameter("updateTCR").toLowerCase());
                if (aggiorna) {
                    sql = "update interventi set flg_pubblicazione='S' where cod_int = ?";
                    st = conn.prepareStatement(sql);
                    st.setInt(1, codInt);
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
