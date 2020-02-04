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

import it.people.dbm.model.Utente;
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
public class InterventiCollegati {

    private static Logger log = LoggerFactory.getLogger(InterventiCollegati.class);
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
        String codiceIntervento = null;
        if (request.getParameter("cod_int") != null && !request.getParameter("cod_int").equals("")) {
            codiceIntervento = request.getParameter("cod_int");
        }
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

            query = "select count(*) righe "
                    + "from interventi_collegati a "
                    + "join interventi b "
                    + "on a.cod_int_padre=b.cod_int "
                    + "join interventi_testi c "
                    + "on a.cod_int_padre=c.cod_int "
                    + "join interventi d "
                    + "on a.cod_int=d.cod_int "
                    + "join interventi_testi e "
                    + "on a.cod_int=e.cod_int "
                    + "and c.cod_lang=e.cod_lang "
                    + "left join testo_condizioni f "
                    + "on a.cod_cond=f.cod_cond "
                    + "and c.cod_lang=f.cod_lang "
                    + "where c.cod_lang='it' ";
            if (utente.getRuolo().equals("A") || utente.getRuolo().equals("B")) {
                query = query + "and a.cod_int_padre in (select distinct cod_int from utenti_interventi  "
                        + "where cod_utente='" + utente.getCodUtente() + "') ";
            }
            query = query + "and ( convert(a.cod_int_padre,CHAR) like ? or convert(a.cod_int,CHAR) like ? or  c.tit_int like ? or e.tit_int like ? or f.cod_cond like ? or f.testo_cond like ? ) ";
            if (codiceIntervento != null) {
                query = query + "and (a.cod_int_padre = ?) ";
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
            query = "select a.cod_int_padre cod_int_padre,c.tit_int tit_int_padre,a.cod_int cod_int, e.tit_int tit_int, a.cod_cond cod_cond, f.testo_cond testo_cond "
                    + "from interventi_collegati a "
                    + "join interventi b "
                    + "on a.cod_int_padre=b.cod_int "
                    + "join interventi_testi c "
                    + "on a.cod_int_padre=c.cod_int "
                    + "join interventi d "
                    + "on a.cod_int=d.cod_int "
                    + "join interventi_testi e "
                    + "on a.cod_int=e.cod_int "
                    + "and c.cod_lang=e.cod_lang "
                    + "left join testo_condizioni f "
                    + "on a.cod_cond=f.cod_cond "
                    + "and c.cod_lang=f.cod_lang "
                    + "where c.cod_lang='it' ";
            if (utente.getRuolo().equals("A") || utente.getRuolo().equals("B")) {
                query = query + "and a.cod_int_padre in (select distinct cod_int from utenti_interventi  "
                        + "where cod_utente='" + utente.getCodUtente() + "') ";
            }
            query = query + "and ( convert(a.cod_int_padre,CHAR) like ? or convert(a.cod_int,CHAR) like ? or  c.tit_int like ? or e.tit_int like ? or f.cod_cond like ? or f.testo_cond like ? ) ";
            if (codiceIntervento != null) {
                query = query + "and (a.cod_int_padre = ?) ";
            }
            if (utente.getTipAggregazione() != null) {
                query = query + "and b.tip_aggregazione=? ";
            }
            query = query + "order by " + sort + " " + order + " limit ? , ?";
            indice=1;
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
            st.setString(indice, "%" + param + "%");
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
                loopObj.put("cod_int_padre", rs.getInt("cod_int_padre"));
                loopObj.put("tit_int_padre", Utility.testoDaDb(rs.getString("tit_int_padre")));
                loopObj.put("tit_int", Utility.testoDaDb(rs.getString("tit_int")));
                loopObj.put("cod_int", rs.getInt("cod_int"));
                loopObj.put("cod_cond", rs.getString("cod_cond"));
                loopObj.put("testo_cond", Utility.testoDaDb(rs.getString("testo_cond")));
                riga.add(loopObj);
            }
            ret = new JSONObject();
            ret.put("totalCount", righe);
            ret.put("interventi_collegati", riga);
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
        boolean verifica = false;
        try {
            conn = Utility.getConnection();
            ret = new JSONObject();
            verifica = verificaNotifica(request, conn);
            if (!verifica) {
                query = "insert into interventi_collegati (cod_int_padre,cod_int,cod_cond) values (?,?,?)";
                st = conn.prepareStatement(query);
                st.setInt(1, Integer.parseInt(request.getParameter("cod_int_padre")));
                st.setInt(2, Integer.parseInt(request.getParameter("cod_int")));
                st.setString(3, (request.getParameter("cod_cond") != null && !request.getParameter("cod_cond").equals("") ? request.getParameter("cod_cond") : null));
                st.executeUpdate();
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
        boolean verifica = false;
        operazione = "modifica";
        try {
            conn = Utility.getConnection();
            ret = new JSONObject();
            verifica = verificaNotifica(request, conn);
            if (!verifica) {
                query = "update interventi_collegati set cod_cond = ? "
                        + "where cod_int_padre=? and cod_int=?";
                st = conn.prepareStatement(query);
                st.setString(1, (request.getParameter("cod_cond") != null && !request.getParameter("cod_cond").equals("") ? request.getParameter("cod_cond") : null));
                st.setInt(2, Integer.parseInt(request.getParameter("cod_int_padre")));
                st.setInt(3, Integer.parseInt(request.getParameter("cod_int")));
                st.executeUpdate();

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
        boolean verifica = false;
        try {
            conn = Utility.getConnection();
            ret = new JSONObject();
            verifica = verificaNotifica(request, conn);
            if (!verifica) {
                query = "delete from interventi_collegati where cod_int_padre=? and cod_int=?";
                st = conn.prepareStatement(query);
                st.setInt(1, Integer.parseInt(request.getParameter("cod_int_padre")));
                st.setInt(2, Integer.parseInt(request.getParameter("cod_int")));
                st.executeUpdate();
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

    public boolean verificaNotifica(HttpServletRequest request, Connection conn) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        Boolean ret = false;
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        int righe = 0;
        String sql = null;

        if (utente.getRuolo().equals("A") || utente.getRuolo().equals("B")) {
            try {
                sql = "select count(*) conta from utenti_interventi where cod_utente <> ? and cod_int = ?";
                st = conn.prepareStatement(sql);
                st.setString(1, utente.getCodUtente());
                st.setInt(2, Integer.parseInt(request.getParameter("cod_int_padre")));
                rs = st.executeQuery();
                if (rs.next()) {
                    righe = rs.getInt("conta");
                }
                Utility.chiusuraJdbc(rs);
                Utility.chiusuraJdbc(st);
                if (righe > 0) {
                    if (operazione.equals("inserimento")) {
                        ret = true;
                    }
                    if (operazione.equals("cancellazione")) {
                        ret = true;
                    }
                    if (operazione.equals("modifica")) {
                        sql = "select * from interventi_collegati where cod_int=? and cod_int_padre=?";
                        st = conn.prepareStatement(sql);
                        st.setInt(1, Integer.parseInt(request.getParameter("cod_int")));
                        st.setInt(2, Integer.parseInt(request.getParameter("cod_int_padre")));
                        rs = st.executeQuery();
                        if (rs.next()) {
                            String wsCodCond = "";
                            if (rs.getObject("cod_cond") != null) {
                                wsCodCond = rs.getString("cod_cond");
                            }
                            if (!request.getParameter("cod_cond").equals(wsCodCond)) {
                                ret = true;
                            }
                        }
                        Utility.chiusuraJdbc(rs);
                        Utility.chiusuraJdbc(st);
                    }
                }
            } catch (SQLException e) {
                log.error("Errore verifica per notifica ", e);
            } finally {
                Utility.chiusuraJdbc(rs);
                Utility.chiusuraJdbc(st);
            }
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
            int codInt = Integer.parseInt(param.get("cod_int"));
            int codIntPadre = Integer.parseInt(param.get("cod_int_padre"));
            sql = "select distinct x.cod_utente, x.cod_int from utenti_interventi x "
                    + "where x.cod_int = ? "
                    + "order by x.cod_utente,x.cod_int";
            st = conn.prepareStatement(sql);
            st.setInt(1, codIntPadre);
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
                    msg = msg + "Id Intervento = " + codIntPadre + "\n";
                    msg = msg + "Id Intervento Collegato = " + codInt + "\n";
                    msg = msg + request.getParameter("textMsg");
                    // componi stringa sql
                    sqlDeferred = "update interventi_collegati "
                            + "set cod_cond = " + (param.get("cod_cond") == null || param.get("cod_cond").equals("") ? null : "'" + param.get("cod_cond") + "'") + " "
                            + "where cod_int = " + codInt + " and cod_int_padre='" + codIntPadre + "';";
                    // carica stringa sql nell'xml
                    doc.getSqlString().add(sqlDeferred.getBytes());
                }
                if (request.getParameter("azione").equals("inserimento")) {
                    msg = "Inserimento \n";
                    msg = msg + "Id Intervento = " + codIntPadre + "\n";
                    msg = msg + "Id Intervento Collegato = " + codInt + "\n";
                    msg = msg + request.getParameter("textMsg");
                    // componi stringa sql
                    sqlDeferred = "insert into interventi_collegati (cod_int_padre,cod_int,cod_cond) values ("
                            + codIntPadre + "," + codInt + "," + (param.get("cod_cond") == null || param.get("cod_cond").equals("") ? null : "'" + param.get("cod_cond") + "'") + ");";
                    // carica stringa sql nell'xml
                    doc.getSqlString().add(sqlDeferred.getBytes());
                }
                if (request.getParameter("azione").equals("cancellazione")) {
                    msg = "Cancellazione \n";
                    msg = msg + "Id Intervento = " + codIntPadre + "\n";
                    msg = msg + "Id Intervento Collegato = " + codInt + "\n";
                    msg = msg + request.getParameter("textMsg");
                    // componi stringa sql
                    sqlDeferred = "delete from interventi_collegati "
                            + "where cod_int = " + codInt + " and cod_int_padre='" + codIntPadre + "';";
                    // carica stringa sql nell'xml
                    doc.getSqlString().add(sqlDeferred.getBytes());
                }
                // inserisco in notifica
                sql = "insert into notifiche (cod_utente_origine,testo_notifica,stato_notifica,cod_elemento) values (?,?,?,?)";
                st = conn.prepareStatement(sql);
                st.setString(1, utente.getCodUtente());
                st.setString(2, msg);
                st.setString(3, "N");
                st.setString(4, "InterventiCollegati=" + codInt + "|" + codIntPadre);

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
}
