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
import org.slf4j.LoggerFactory;

import it.people.dbm.model.Modifica;
import it.people.dbm.model.Utente;
import it.people.dbm.utility.Configuration;
import it.people.dbm.utility.RisaliGerarchiaUtenti;
import it.people.dbm.utility.Utility;
import it.people.dbm.xsd.sqlNotifiche.DocumentRoot;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject; 
  
/**
 *
 * @author Piergiorgio
 */
public class Allegati {

	private static Logger log = LoggerFactory.getLogger(Allegati.class);
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

            String tipoSearch = request.getParameter("search_cod");
            query = "select count(*) righe "
                    + "from allegati a "
                    + "join interventi i "
                    + "on a.cod_int = i.cod_int "
                    + "join interventi_testi b "
                    + "on a.cod_int = b.cod_int "
                    + "and b.cod_lang = 'it' "
                    + "join documenti d "
                    + "on a.cod_doc = d.cod_doc "
                    + "join documenti_testi c "
                    + "on a.cod_doc=c.cod_doc "
                    + "and c.cod_lang='it' "
                    + "left join testo_condizioni e "
                    + "on a.cod_cond=e.cod_cond "
                    + "and e.cod_lang='it' "
                    + "left join href f "
                    + "on d.href = f.href "
                    + "left join href_testi g "
                    + "on f.href=g.href "
                    + "and g.cod_lang='it' where 1=1 ";
            if (utente.getRuolo().equals("A") || utente.getRuolo().equals("B")) {
                query = query + "and a.cod_int in (select distinct cod_int from utenti_interventi  "
                        + "where cod_utente='" + utente.getCodUtente() + "' ) ";
            }
            if (tipoSearch.equals("default")) {
                query = query + "and (convert(a.cod_int,CHAR) like ? or b.tit_int like ? or c.tit_doc like ? or testo_cond like ? or c.cod_doc like ? or e.cod_cond like ? or f.href like ? or g.tit_href like ? ) ";
            }
            if (tipoSearch.equals("href")) {
                query = query + "and (f.href like ? or g.tit_href like ? ) ";
            }
            if (tipoSearch.equals("intervento")) {
                query = query + "and (convert(a.cod_int,CHAR) like ? or b.tit_int like ? ) ";
            }
            if (tipoSearch.equals("condizione")) {
                query = query + "and ( testo_cond like ?  or e.cod_cond like ?) ";
            }
            if (tipoSearch.equals("documento")) {
                query = query + "and (c.tit_doc like ? or c.cod_doc like ?) ";
            }
            if (codiceIntervento != null) {
                query = query + "and (a.cod_int = ?) ";
            }
            if (utente.getTipAggregazione() != null) {
                query = query + "and i.tip_aggregazione=? ";
            }
            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            indice++;
            st.setString(2, "%" + param + "%");
            indice++;
            if (tipoSearch.equals("default")) {
                st.setString(3, "%" + param + "%");
                indice++;
                st.setString(4, "%" + param + "%");
                indice++;
                st.setString(5, "%" + param + "%");
                indice++;
                st.setString(6, "%" + param + "%");
                indice++;
                st.setString(7, "%" + param + "%");
                indice++;
                st.setString(8, "%" + param + "%");
                indice++;
            }
            if (codiceIntervento != null) {
                st.setInt(indice, Integer.parseInt(codiceIntervento));
                indice++;
            }
            if (utente.getTipAggregazione() != null) {
                st.setString(indice, utente.getTipAggregazione());
                indice++;
            }
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            indice = 1;

            query = "select a.cod_int cod_int,b.tit_int tit_int,a.cod_doc cod_doc,c.tit_doc tit_doc, a.flg_autocert flg_autocert, "
                    + "a.copie copie, a.cod_cond cod_cond,e.testo_cond testo_cond,a.flg_obb flg_obb,a.tipologie tipologie, "
                    + "a.num_max_pag num_max_pag,a.dimensione_max dimensione_max,f.href href,g.tit_href tit_href, a.ordinamento ordinamento, a.flg_verifica_firma flg_verifica_firma "
                    + "from allegati a "
                    + "join interventi i "
                    + "on a.cod_int = i.cod_int "
                    + "join interventi_testi b "
                    + "on a.cod_int = b.cod_int "
                    + "and b.cod_lang = 'it' "
                    + "join documenti d "
                    + "on a.cod_doc = d.cod_doc "
                    + "join documenti_testi c "
                    + "on a.cod_doc=c.cod_doc "
                    + "and c.cod_lang='it' "
                    + "left join testo_condizioni e "
                    + "on a.cod_cond=e.cod_cond "
                    + "and e.cod_lang='it' "
                    + "left join href f "
                    + "on d.href = f.href "
                    + "left join href_testi g "
                    + "on f.href=g.href "
                    + "and g.cod_lang='it' where 1=1 ";
            if (utente.getRuolo().equals("A") || utente.getRuolo().equals("B")) {
                query = query + "and a.cod_int in (select distinct cod_int from utenti_interventi  "
                        + "where cod_utente='" + utente.getCodUtente() + "' ) ";
            }
            if (tipoSearch.equals("default")) {
                query = query + "and (convert(a.cod_int,CHAR) like ? or b.tit_int like ? or c.tit_doc like ? or testo_cond like ? or c.cod_doc like ? or e.cod_cond like ? or f.href like ? or g.tit_href like ? ) ";
            }
            if (tipoSearch.equals("href")) {
                query = query + "and (f.href like ? or g.tit_href like ? ) ";
            }
            if (tipoSearch.equals("intervento")) {
                query = query + "and (convert(a.cod_int,CHAR) like ? or b.tit_int like ? ) ";
            }
            if (tipoSearch.equals("condizione")) {
                query = query + "and ( testo_cond like ?  or e.cod_cond like ?) ";
            }
            if (tipoSearch.equals("documento")) {
                query = query + "and (c.tit_doc like ? or c.cod_doc like ?) ";
            }
            if (codiceIntervento != null) {
                query = query + "and (a.cod_int = ?) ";
            }
            if (utente.getTipAggregazione() != null) {
                query = query + "and i.tip_aggregazione=? ";
            }
            query = query + "order by " + sort + " " + order + " limit ? , ?";
            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            indice++;
            st.setString(2, "%" + param + "%");
            indice++;
            if (tipoSearch.equals("default")) {
                st.setString(3, "%" + param + "%");
                indice++;
                st.setString(4, "%" + param + "%");
                indice++;
                st.setString(5, "%" + param + "%");
                indice++;
                st.setString(6, "%" + param + "%");
                indice++;
                st.setString(7, "%" + param + "%");
                indice++;
                st.setString(8, "%" + param + "%");
                indice++;
            }
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
                loopObj.put("cod_doc", rs.getString("cod_doc"));
                loopObj.put("tit_doc", Utility.testoDaDb(rs.getString("tit_doc")));
                loopObj.put("flg_autocert", rs.getString("flg_autocert"));
                loopObj.put("copie", rs.getInt("copie"));
                loopObj.put("cod_cond", rs.getString("cod_cond"));
                loopObj.put("testo_cond", Utility.testoDaDb(rs.getString("testo_cond")));
                loopObj.put("flg_obb", rs.getString("flg_obb"));
                loopObj.put("tipologie", rs.getString("tipologie"));
                if (rs.getObject("num_max_pag") != null) {
                    loopObj.put("num_max_pag", rs.getInt("num_max_pag"));
                } else {
                    loopObj.put("num_max_pag", null);
                }
                if (rs.getObject("dimensione_max") != null) {
                    loopObj.put("dimensione_max", rs.getInt("dimensione_max"));
                } else {
                    loopObj.put("dimensione_max", null);
                }
                loopObj.put("href", rs.getString("href"));
                loopObj.put("tit_href", Utility.testoDaDb(rs.getString("tit_href")));
                loopObj.put("ordinamento", rs.getInt("ordinamento"));
                loopObj.put("flg_verifica_firma", rs.getInt("flg_verifica_firma"));

                riga.add(loopObj);
            }
            ret = new JSONObject();
            ret.put("totalCount", righe);
            ret.put("allegati", riga);
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
                query = "insert into allegati (cod_int,cod_doc,cod_cond,flg_obb,flg_autocert,tipologie,num_max_pag , dimensione_max, copie, ordinamento, flg_verifica_firma) "
                        + "values (?,?,?,?,?,?,?,?,?,?,?)";
                st = conn.prepareStatement(query);
                st.setInt(1, Integer.parseInt(request.getParameter("cod_int")));
                st.setString(2, request.getParameter("cod_doc"));
                st.setString(3, (request.getParameter("cod_cond") != null && !request.getParameter("cod_cond").equals("") ? request.getParameter("cod_cond") : null));
                st.setString(4, request.getParameter("flg_obb_hidden"));
                st.setString(5, request.getParameter("flg_autocert_hidden"));
                st.setString(6, (request.getParameter("tipologie") != null && !request.getParameter("tipologie").equals("") ? request.getParameter("tipologie") : null));
                if (request.getParameter("num_max_pag") != null && !request.getParameter("num_max_pag").equals("")) {
                    st.setInt(7, Integer.parseInt(request.getParameter("num_max_pag")));
                } else {
                    st.setNull(7, java.sql.Types.INTEGER);
                }
                if (request.getParameter("dimensione_max") != null && !request.getParameter("dimensione_max").equals("")) {
                    st.setInt(8, Integer.parseInt(request.getParameter("dimensione_max")));
                } else {
                    st.setNull(8, java.sql.Types.INTEGER);
                }
                st.setInt(9, (request.getParameter("copie") != null && !request.getParameter("copie").equals("") ? Integer.parseInt(request.getParameter("copie")) : 1));
                st.setInt(10, (request.getParameter("ordinamento") != null && !request.getParameter("ordinamento").equals("") ? Integer.parseInt(request.getParameter("ordinamento")) : 9999));
                st.setString(11, request.getParameter("flg_verifica_firma_hidden"));
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
                    query = "insert into allegati (cod_int,cod_doc,cod_cond,flg_obb,flg_autocert,tipologie,num_max_pag , dimensione_max, copie, ordinamento, flg_verifica_firma) "
                            + "values (?,?,?,?,?,?,?,?,?,?,?)";
                    st = conn.prepareStatement(query);
                    st.setInt(1, Integer.parseInt(request.getParameter("cod_int")));
                    st.setString(2, request.getParameter("cod_doc"));
                    st.setString(3, (request.getParameter("cod_cond") != null && !request.getParameter("cod_cond").equals("") ? request.getParameter("cod_cond") : null));
                    st.setString(4, request.getParameter("flg_obb_hidden"));
                    st.setString(5, request.getParameter("flg_autocert_hidden"));
                    st.setString(6, (request.getParameter("tipologie") != null && !request.getParameter("tipologie").equals("") ? request.getParameter("tipologie") : null));
                    if (request.getParameter("num_max_pag") != null && !request.getParameter("num_max_pag").equals("")) {
                        st.setInt(7, Integer.parseInt(request.getParameter("num_max_pag")));
                    } else {
                        st.setNull(7, java.sql.Types.INTEGER);
                    }
                    if (request.getParameter("dimensione_max") != null && !request.getParameter("dimensione_max").equals("")) {
                        st.setInt(8, Integer.parseInt(request.getParameter("dimensione_max")));
                    } else {
                        st.setNull(8, java.sql.Types.INTEGER);
                    }
                    st.setInt(9, (request.getParameter("copie") != null && !request.getParameter("copie").equals("") ? Integer.parseInt(request.getParameter("copie")) : 1));
                    st.setInt(10, (request.getParameter("ordinamento") != null && !request.getParameter("ordinamento").equals("") ? Integer.parseInt(request.getParameter("ordinamento")) : 9999));
                    st.setString(11, request.getParameter("flg_verifica_firma_hidden"));
                    st.executeUpdate();
                    Utility.chiusuraJdbc(rs);
                    Utility.chiusuraJdbc(st);
                    query = "delete from allegati where cod_int=? and cod_doc=?";
                    st = conn.prepareStatement(query);
                    st.setInt(1, Integer.parseInt(request.getParameter("cod_int_old")));
                    st.setString(2, request.getParameter("cod_doc_old"));
                    st.executeUpdate();
                    scriviTCR(verifica, Integer.parseInt(request.getParameter("cod_int_old")), conn);
                    Utility.chiusuraJdbc(rs);
                    Utility.chiusuraJdbc(st);
                }
                if (request.getParameter("az_modifica").equals("mantieni")) {
                    query = "insert into allegati (cod_int,cod_doc,cod_cond,flg_obb,flg_autocert,tipologie,num_max_pag , dimensione_max, copie, ordinamento, flg_verifica_firma) "
                            + "values (?,?,?,?,?,?,?,?,?,?)";
                    st = conn.prepareStatement(query);
                    st.setInt(1, Integer.parseInt(request.getParameter("cod_int")));
                    st.setString(2, request.getParameter("cod_doc"));
                    st.setString(3, (request.getParameter("cod_cond") != null && !request.getParameter("cod_cond").equals("") ? request.getParameter("cod_cond") : null));
                    st.setString(4, request.getParameter("flg_obb_hidden"));
                    st.setString(5, request.getParameter("flg_autocert_hidden"));
                    st.setString(6, (request.getParameter("tipologie") != null && !request.getParameter("tipologie").equals("") ? request.getParameter("tipologie") : null));
                    if (request.getParameter("num_max_pag") != null && !request.getParameter("num_max_pag").equals("")) {
                        st.setInt(7, Integer.parseInt(request.getParameter("num_max_pag")));
                    } else {
                        st.setNull(7, java.sql.Types.INTEGER);
                    }
                    if (request.getParameter("dimensione_max") != null && !request.getParameter("dimensione_max").equals("")) {
                        st.setInt(8, Integer.parseInt(request.getParameter("dimensione_max")));
                    } else {
                        st.setNull(8, java.sql.Types.INTEGER);
                    }
                    st.setInt(9, (request.getParameter("copie") != null && !request.getParameter("copie").equals("") ? Integer.parseInt(request.getParameter("copie")) : 1));
                    st.setInt(10, (request.getParameter("ordinamento") != null && !request.getParameter("ordinamento").equals("") ? Integer.parseInt(request.getParameter("ordinamento")) : 9999));
                    st.setString(11, request.getParameter("flg_verifica_firma_hidden"));
                    st.executeUpdate();
                    Utility.chiusuraJdbc(rs);
                    Utility.chiusuraJdbc(st);
                }
                if (request.getParameter("az_modifica").equals("")) {

                    query = "update allegati set cod_cond = ?,flg_obb = ?,flg_autocert=?,tipologie=?,num_max_pag=? , dimensione_max=?, copie=?, ordinamento = ?, flg_verifica_firma = ? "
                            + "where cod_int=? and cod_doc=?";
                    st = conn.prepareStatement(query);
                    st.setString(1, (request.getParameter("cod_cond") != null && !request.getParameter("cod_cond").equals("") ? request.getParameter("cod_cond") : null));
                    st.setString(2, request.getParameter("flg_obb_hidden"));
                    st.setString(3, request.getParameter("flg_autocert_hidden"));
                    st.setString(4, (request.getParameter("tipologie") != null && !request.getParameter("tipologie").equals("") ? request.getParameter("tipologie") : null));

                    if (request.getParameter("num_max_pag") != null && !request.getParameter("num_max_pag").equals("")) {
                        st.setInt(5, Integer.parseInt(request.getParameter("num_max_pag")));
                    } else {
                        st.setNull(5, java.sql.Types.INTEGER);
                    }
                    if (request.getParameter("dimensione_max") != null && !request.getParameter("dimensione_max").equals("")) {
                        st.setInt(6, Integer.parseInt(request.getParameter("dimensione_max")));
                    } else {
                        st.setNull(6, java.sql.Types.INTEGER);
                    }
                    st.setInt(7, (request.getParameter("copie") != null && !request.getParameter("copie").equals("") ? Integer.parseInt(request.getParameter("copie")) : 1));
                    st.setInt(8, (request.getParameter("ordinamento") != null && !request.getParameter("ordinamento").equals("") ? Integer.parseInt(request.getParameter("ordinamento")) : 9999));
                    st.setString(9, request.getParameter("flg_verifica_firma_hidden"));
                    st.setInt(10, Integer.parseInt(request.getParameter("cod_int")));
                    st.setString(11, request.getParameter("cod_doc"));
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
                query = "delete from allegati where cod_int=? and cod_doc=?";
                st = conn.prepareStatement(query);
                st.setInt(1, Integer.parseInt(request.getParameter("cod_int")));
                st.setString(2, request.getParameter("cod_doc"));
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
        Boolean ret = false;
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        int righe = 0;
        String sql = null;
        Modifica modificato = new Modifica();
        modificato.init();
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
                    sql = "select * from allegati where cod_int=? and cod_doc=?";
                    st = conn.prepareStatement(sql);
                    st.setInt(1, Integer.parseInt(request.getParameter("cod_int")));
                    st.setString(2, request.getParameter("cod_doc"));
                    rs = st.executeQuery();
                    if (rs.next()) {
                        String wsTipologie = "";
                        if (rs.getObject("tipologie") != null) {
                            wsTipologie = rs.getString("tipologie");
                        }
                        String wsCodCond = "";
                        if (rs.getObject("cod_cond") != null) {
                            wsCodCond = rs.getString("cod_cond");
                        }
                        String wsNumMaxPag = "";
                        if (rs.getObject("num_max_pag") != null) {
                            wsNumMaxPag = rs.getString("num_max_pag");
                        }
                        String wsDimMax = "";
                        if (rs.getObject("dimensione_max") != null) {
                            wsDimMax = rs.getString("dimensione_max");
                        }
                        String wsOrdine = "";
                        if (rs.getObject("ordinamento") != null) {
                            wsOrdine = rs.getString("ordinamento");
                        }

                        if (Integer.parseInt(request.getParameter("copie")) != rs.getInt("copie")
                                || !request.getParameter("cod_cond").equals(wsCodCond)
                                || !request.getParameter("flg_autocert_hidden").equals(rs.getString("flg_autocert"))
                                || !request.getParameter("flg_verifica_firma_hidden").equals(rs.getString("flg_verifica_firma"))
                                || !request.getParameter("flg_obb_hidden").equals(rs.getString("flg_obb"))
                                || !request.getParameter("tipologie").equals(wsTipologie)
                                || !request.getParameter("num_max_pag").equals(wsNumMaxPag)
                                || !request.getParameter("dimensione_max").equals(wsDimMax)
                                || !request.getParameter("ordinamento").equals(wsOrdine)) {
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
            throw e;
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
            String codDoc = param.get("cod_doc");
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
                    msg = msg + "Id documento = " + codDoc + "\n";
                    msg = msg + request.getParameter("textMsg");
                    if (param.get("az_modifica").equals("cancella")) {
                        sqlDeferred = "delete from allegati "
                                + "where cod_int = " + Integer.parseInt(param.get("cod_int_old")) + " and cod_doc='" + param.get("cod_doc_old") + "';";
                        // carica stringa sql nell'xml
                        doc.getSqlString().add(sqlDeferred.getBytes());
                        sqlDeferred = "delete from allegati "
                                + "where cod_int = " + codInt + " and cod_doc='" + codDoc + "';";
                        // carica stringa sql nell'xml
                        doc.getSqlString().add(sqlDeferred.getBytes());
                        sqlDeferred = "insert into allegati (cod_int,cod_doc,copie,flg_autocert,flg_obb,cod_cond,tipologie,dimensione_max,num_max_pag,ordinamento,flg_verifica_firma) values ("
                                + codInt + ",'" + codDoc + "'," + param.get("copie") + "'" + param.get("flg_autocert_hidden") + "','" + param.get("flg_obb_hidden") + "',"
                                + (param.get("cod_cond") == null || param.get("cod_cond").equals("") ? null : "'" + param.get("cod_cond") + "'") + ", "
                                + (param.get("tipologie") == null || param.get("tipologie").equals("") ? null : "'" + param.get("tipologie") + "'") + ", "
                                + (param.get("dimensione_max") == null || param.get("dimensione_max").equals("") ? null : param.get("dimensione_max")) + ", "
                                + (param.get("num_max_pag") == null || param.get("num_max_pag").equals("") ? null : param.get("num_max_pag")) + ", "
                                + (param.get("ordinamento") == null || param.get("ordinamento").equals("") ? null : param.get("ordinamento")) + ", '" 
                                + param.get("flg_verifica_firma_hidden") + "');";
                        // carica stringa sql nell'xml
                        doc.getSqlString().add(sqlDeferred.getBytes());
                        sqlDeferred = "update interventi set data_modifica=current_timestamp where cod_int = " + Integer.parseInt(param.get("cod_int_old"));
                        // carica stringa sql nell'xml
                        doc.getSqlString().add(sqlDeferred.getBytes());
                    }
                    if (param.get("az_modifica").equals("mantieni")) {
                        sqlDeferred = "delete from allegati "
                                + "where cod_int = " + codInt + " and cod_doc='" + codDoc + "';";
                        // carica stringa sql nell'xml
                        doc.getSqlString().add(sqlDeferred.getBytes());
                        sqlDeferred = "insert into allegati (cod_int,cod_doc,copie,flg_autocert,flg_obb,cod_cond,tipologie,dimensione_max,num_max_pag,ordinamento,flg_verifica_firma) values ("
                                + codInt + ",'" + codDoc + "'," + param.get("copie") + "'" + param.get("flg_autocert_hidden") + "','" + param.get("flg_obb_hidden") + "',"
                                + (param.get("cod_cond") == null || param.get("cod_cond").equals("") ? null : "'" + param.get("cod_cond") + "'") + ", "
                                + (param.get("tipologie") == null || param.get("tipologie").equals("") ? null : "'" + param.get("tipologie") + "'") + ", "
                                + (param.get("dimensione_max") == null || param.get("dimensione_max").equals("") ? null : param.get("dimensione_max")) + ", "
                                + (param.get("num_max_pag") == null || param.get("num_max_pag").equals("") ? null : param.get("num_max_pag")) + ", "
                                + (param.get("ordinamento") == null || param.get("ordinamento").equals("") ? null : param.get("ordinamento")) + ", '" 
                                + param.get("flg_verifica_firma_hidden") + "');";
                        // carica stringa sql nell'xml
                        doc.getSqlString().add(sqlDeferred.getBytes());
                    }
                    if (param.get("az_modifica").equals("")) {
                        // componi stringa sql
                        sqlDeferred = "update allegati "
                                + "set copie = " + param.get("copie") + ", "
                                + "flg_autocert = '" + param.get("flg_autocert_hidden") + "', "
                                + "flg_obb = '" + param.get("flg_obb_hidden") + "', "
                                + "cod_cond = " + (param.get("cod_cond") == null || param.get("cod_cond").equals("") ? null : "'" + param.get("cod_cond") + "'") + ", "
                                + "tipologie = " + (param.get("tipologie") == null || param.get("tipologie").equals("") ? null : "'" + param.get("tipologie") + "'") + ", "
                                + "dimensione_max = " + (param.get("dimensione_max") == null || param.get("dimensione_max").equals("") ? null : param.get("dimensione_max")) + ", "
                                + "num_max_pag = " + (param.get("num_max_pag") == null || param.get("num_max_pag").equals("") ? null : param.get("num_max_pag")) + ", "
                                + "ordinamento = " + (param.get("ordinamento") == null || param.get("ordinamento").equals("") ? null : param.get("ordinamento")) + ", "
                                + "flg_verifica_firma = '" + param.get("flg_verifica_firma_hidden") + "' "
                                + "where cod_int = " + codInt + " and cod_doc='" + codDoc + "';";
                        // carica stringa sql nell'xml
                        doc.getSqlString().add(sqlDeferred.getBytes());
                    }
                }
                if (request.getParameter("azione").equals("inserimento")) {
                    msg = "Inserimento \n";
                    msg = msg + "Id intervento = " + codInt + "\n";
                    msg = msg + "Id documento = " + codDoc + "\n";
                    msg = msg + request.getParameter("textMsg");
                    // componi stringa sql
                    sqlDeferred = "insert into allegati (cod_int,cod_doc,copie,flg_autocert,flg_obb,cod_cond,tipologie,dimensione_max,num_max_pag,ordinamento,flg_verifica_firma) values ("
                            + codInt + ",'" + codDoc + "'," + param.get("copie") + "'" + param.get("flg_autocert_hidden") + "','" + param.get("flg_obb_hidden") + "',"
                            + (param.get("cod_cond") == null || param.get("cod_cond").equals("") ? null : "'" + param.get("cod_cond") + "'") + ", "
                            + (param.get("tipologie") == null || param.get("tipologie").equals("") ? null : "'" + param.get("tipologie") + "'") + ", "
                            + (param.get("dimensione_max") == null || param.get("dimensione_max").equals("") ? null : param.get("dimensione_max")) + ", "
                            + (param.get("num_max_pag") == null || param.get("num_max_pag").equals("") ? null : param.get("num_max_pag")) + ", "
                            + (param.get("ordinamento") == null || param.get("ordinamento").equals("") ? null : param.get("ordinamento")) + ", '" 
                            + param.get("flg_verifica_firma_hidden") + "');";
                    // carica stringa sql nell'xml
                    doc.getSqlString().add(sqlDeferred.getBytes());
                }
                if (request.getParameter("azione").equals("cancellazione")) {
                    msg = "Cancellazione \n";
                    msg = msg + "Id intervento = " + codInt + "\n";
                    msg = msg + "Id documento = " + codDoc + "\n";
                    msg = msg + request.getParameter("textMsg");
                    // componi stringa sql
                    sqlDeferred = "delete from allegati "
                            + "where cod_int = " + codInt + " and cod_doc='" + codDoc + "';";
                    // carica stringa sql nell'xml
                    doc.getSqlString().add(sqlDeferred.getBytes());
                }
                sqlDeferred = "update interventi set data_modifica=current_timestamp where cod_int = " + codInt;
                // carica stringa sql nell'xml
                doc.getSqlString().add(sqlDeferred.getBytes());
                // inserisco in notifica
                sql = "insert into notifiche (cod_utente_origine,testo_notifica,stato_notifica,cod_elemento) values (?,?,?,?)";
                st = conn.prepareStatement(sql);
                st.setString(1, utente.getCodUtente());
                st.setString(2, msg);
                st.setString(3, "N");
                st.setString(4, "Allegati=" + codInt + "|" + codDoc);

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
