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
import java.io.File;
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

import it.people.dbm.model.FileUploadModel;
import it.people.dbm.model.Modifica;
import it.people.dbm.model.NormativaModel;
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
public class Normative {

    private static Logger log = LoggerFactory.getLogger(Normative.class);

    public JSONObject action(HttpServletRequest request) throws Exception {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        PreparedStatement stLang = null;
        ResultSet rsLang = null;
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        JSONObject ret = null;
        int righe = 0;
        int qsi = 1;
        try {
            conn = Utility.getConnection();
            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            String offset = request.getParameter("start");
            String size = request.getParameter("size");
            String sort = request.getParameter("sort");
            String order = request.getParameter("dir");
            String param = Utility.testoADb(request.getParameter("query"));

            String query = "select count(*) righe from ( "
                    + "select a.cod_rif cod_rif,a.nome_rif nome_rif, "
                    + "a.cod_tipo_rif cod_tipo_rif, b.tit_rif tit_rif, "
                    + "c.tipo_rif tipo_rif,d.tip_doc tip_doc,d.nome_file nome_file , b.cod_lang cod_lang, a.tip_aggregazione "
                    + "from normative a join normative_testi b "
                    + "on a.cod_rif=b.cod_rif "
                    + "join tipi_rif c "
                    + "on a.cod_tipo_rif=c.cod_tipo_rif "
                    + "and c.cod_lang=b.cod_lang "
                    + "left join normative_documenti d on a.cod_rif=d.cod_rif and b.cod_lang=d.cod_lang ";
            if (utente.getRuolo().equals("A") || utente.getRuolo().equals("B")) {
                query = query + "where a.cod_rif in  "
                        + "(select distinct cod_rif from norme_interventi x "
                        + "join utenti_interventi y on x.cod_int=y.cod_int where y.cod_utente='" + utente.getCodUtente() + "') "
                        + "union "
                        + "select a.cod_rif cod_rif,a.nome_rif nome_rif, "
                        + "a.cod_tipo_rif cod_tipo_rif, b.tit_rif tit_rif, "
                        + "c.tipo_rif tipo_rif,d.tip_doc tip_doc,d.nome_file nome_file , b.cod_lang cod_lang, a.tip_aggregazione "
                        + "from normative a join normative_testi b "
                        + "on a.cod_rif=b.cod_rif "
                        + "join tipi_rif c on a.cod_tipo_rif=c.cod_tipo_rif "
                        + "left join normative_documenti d on a.cod_rif=d.cod_rif and b.cod_lang=d.cod_lang "
                        + "where a.cod_rif not in "
                        + "(select cod_rif from norme_interventi "
                        + "union select cod_rif from gerarchia_operazioni where cod_rif is not null "
                        + "union select cod_rif from gerarchia_settori where cod_rif is not null)  ";
            }
            query = query + ") p "
                    + "where p.cod_lang ='it' and "
                    + "(p.tit_rif like ? or p.cod_rif like ? or p.cod_tipo_rif like ? or p.tipo_rif like ? or p.nome_file like ? ) ";
            if (!(Boolean) session.getAttribute("territorialitaNew")) {
                if (utente.getTipAggregazione() != null) {
                    query = query + "and p.tip_aggregazione=? ";
                }
            }
            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            st.setString(2, "%" + param + "%");
            st.setString(3, "%" + param + "%");
            st.setString(4, "%" + param + "%");
            st.setString(5, "%" + param + "%");
            if (!(Boolean) session.getAttribute("territorialitaNew")) {
                if (utente.getTipAggregazione() != null) {
                    st.setString(6, utente.getTipAggregazione());
                }
            }            
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);

            query = "select p.cod_rif cod_rif,p.nome_rif nome_rif,p.cod_tipo_rif cod_tipo_rif, "
                    + "p.tit_rif tit_rif,p.tipo_rif tipo_rif,p.tip_doc tip_doc,p.nome_file nome_file, "
                    + "p.cod_lang cod_lang, p.tip_aggregazione, p.des_aggregazione "
                    + "from ( "
                    + "select a.cod_rif cod_rif,a.nome_rif nome_rif, "
                    + "a.cod_tipo_rif cod_tipo_rif, b.tit_rif tit_rif, "
                    + "c.tipo_rif tipo_rif,d.tip_doc tip_doc,d.nome_file nome_file , b.cod_lang cod_lang, a.tip_aggregazione, h.des_aggregazione "
                    + "from normative a join normative_testi b "
                    + "on a.cod_rif=b.cod_rif "
                    + "join tipi_rif c "
                    + "on a.cod_tipo_rif=c.cod_tipo_rif "
                    + "and c.cod_lang=b.cod_lang "
                    + "left join normative_documenti d on a.cod_rif=d.cod_rif and b.cod_lang=d.cod_lang "
                    + "left join tipi_aggregazione h "
                    + "on a.tip_aggregazione = h.tip_aggregazione ";
            if (utente.getRuolo().equals("A") || utente.getRuolo().equals("B")) {
                query = query + "where a.cod_rif in  "
                        + "(select distinct cod_rif from norme_interventi x "
                        + "join utenti_interventi y on x.cod_int=y.cod_int where y.cod_utente='" + utente.getCodUtente() + "') "
                        + "union "
                        + "select a.cod_rif cod_rif,a.nome_rif nome_rif, "
                        + "a.cod_tipo_rif cod_tipo_rif, b.tit_rif tit_rif, "
                        + "c.tipo_rif tipo_rif,d.tip_doc tip_doc,d.nome_file nome_file , b.cod_lang cod_lang, a.tip_aggregazione, h.des_aggregazione "
                        + "from normative a join normative_testi b "
                        + "on a.cod_rif=b.cod_rif "
                        + "left join tipi_aggregazione h "
                        + "on a.tip_aggregazione = h.tip_aggregazione "
                        + "join tipi_rif c on a.cod_tipo_rif=c.cod_tipo_rif "
                        + "left join normative_documenti d on a.cod_rif=d.cod_rif and b.cod_lang=d.cod_lang "
                        + "where a.cod_rif not in "
                        + "(select cod_rif from norme_interventi "
                        + "union select cod_rif from gerarchia_operazioni where cod_rif is not null "
                        + "union select cod_rif from gerarchia_settori where cod_rif is not null) ";
            }
            query = query + ") p "
                    + "where p.cod_lang ='it' and "
                    + "(p.tit_rif like ? or p.cod_rif like ? or p.cod_tipo_rif like ? or p.tipo_rif like ? or p.nome_file like ? ) ";
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

            String queryLang = "select tit_rif, cod_lang from normative_testi where cod_rif = ?";
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_rif", rs.getString("cod_rif"));
                loopObj.put("nome_rif", Utility.testoDaDb(rs.getString("nome_rif")));
                loopObj.put("cod_tipo_rif", rs.getString("cod_tipo_rif"));
                loopObj.put("tipo_rif", rs.getString("tipo_rif"));
                loopObj.put("tip_doc", rs.getString("tip_doc"));
                loopObj.put("tip_aggregazione", rs.getString("tip_aggregazione"));
                loopObj.put("des_aggregazione", rs.getString("des_aggregazione"));                
                stLang = conn.prepareStatement(queryLang);
                stLang.setString(1, rs.getString("cod_rif"));
                rsLang = stLang.executeQuery();
                while (rsLang.next()) {
                    loopObj.put("tit_rif_" + rsLang.getString("cod_lang"), Utility.testoDaDb(rsLang.getString("tit_rif")));
                }
                Utility.chiusuraJdbc(rsLang);
                Utility.chiusuraJdbc(stLang);

                String nomeFile = rs.getString("nome_file");
                if (nomeFile != null) {
                    File file = new File(rs.getString("nome_file"));
                    nomeFile = file.getName();
                }

                loopObj.put("nome_file", nomeFile);
                if (nomeFile != null && !nomeFile.equals("")) {
                    loopObj.put("nome_file", "<a href=\"ScaricaFile?tipo=normativa&codNorma=" + rs.getString("cod_rif") + "&tipDoc=" + rs.getString("tip_doc") + "\"target=\"_blank\" alt=\"" + nomeFile + "\">" + nomeFile + "</a>");
                }

                riga.add(loopObj);
            }
            ret = new JSONObject();
            ret.put("totalCount", righe);
            ret.put("normative", riga);
        } catch (SQLException e) {
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

    public NormativaModel leggi(String codNorma, String tipDoc) throws Exception {
        NormativaModel nm = new NormativaModel();
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            conn = Utility.getConnection();
            String query = "select a.cod_rif,a.nome_file,a.tip_doc,a.doc_blob "
                    + "from normative_documenti a "
                    + "where a.cod_rif=? "
                    + "and   a.cod_lang='it' "
                    + "and   a.tip_doc=?";
            st = conn.prepareStatement(query);
            st.setString(1, codNorma);
            st.setString(2, tipDoc);
            rs = st.executeQuery();
            while (rs.next()) {
                FileUploadModel fum = new FileUploadModel();
                nm.setCodRif(codNorma);
                fum.setTipDoc(rs.getString("tip_doc"));
                fum.setNomeFile(rs.getString("nome_file"));
                fum.setDocBlob(rs.getBinaryStream("doc_blob"));
                fum.setLength(rs.getBlob("doc_blob").length());
                nm.setFileUplad(fum);
            }
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return nm;
    }

    public JSONObject inserisciDocumento(NormativaModel nm, HttpServletRequest request) throws Exception {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        try {
            conn = Utility.getConnection();
            FileUploadModel fum = nm.getFileUpload();
            Modifica verifica = verificaNotifica(nm.getCodRif(), nm, request, "caricaFile", conn);
            ret = new JSONObject();
            if (!verifica.isNotifica()) {
                String query = "delete from normative_documenti where cod_rif = ?";
                st = conn.prepareStatement(query);
                st.setString(1, nm.getCodRif());
                st.executeUpdate();
                Utility.chiusuraJdbc(st);
                query = "insert into normative_documenti (cod_rif,tip_doc,cod_lang,nome_file,doc_blob) values (?,?,'it',?,?)";
                st = conn.prepareStatement(query);
                st.setString(1, nm.getCodRif());
                st.setString(2, fum.getTipDoc());
                st.setString(3, fum.getNomeFile());
                st.setBinaryStream(4, fum.getDocBlob(), fum.getLength().intValue());
                st.executeUpdate();
                scriviTCR(verifica, nm.getCodRif(), conn);
                ret.put("success", "Inserimento effettuato");
                ret.put("nome_file", fum.getNomeFile());
                ret.put("tip_doc", fum.getTipDoc());
                ret.put("cod_rif", nm.getCodRif());
            } else {
                HttpSession session = request.getSession();
                HashMap<String, Object> funzRequest = null;
                if (session.getAttribute("sessioneTabelle") != null) {
                    funzRequest = (HashMap<String, Object>) session.getAttribute("sessioneTabelle");
                } else {
                    funzRequest = new HashMap<String, Object>();
                }
                HashMap<String, Object> param = new HashMap<String, Object>();
                param.put("fileObject", nm);
                funzRequest.put("normative", param);
                session.setAttribute("sessioneTabelle", funzRequest);

                JSONObject msg = new JSONObject();
                msg.put("notifica", "La risorsa è condivisa(non effettuo la modifica); Vuoi inviare una notifica?");
                ret.put("failure", msg);
            }
        } catch (SQLException e) {
            log.error("Errore insert norma documento fisico ", e);
            ret = new JSONObject();
            ret.put("failure", e.getMessage());
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
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        try {
            conn = Utility.getConnection();
            ret = new JSONObject();
            String query = "insert into normative (cod_rif,cod_tipo_rif,nome_rif,tip_aggregazione) values (?,?,?,?)";
            st = conn.prepareStatement(query);
            st.setString(1, request.getParameter(("cod_rif")));
            st.setString(2, request.getParameter(("cod_tipo_rif")));
            st.setString(3, Utility.testoADb(request.getParameter(("nome_rif"))));
            st.setString(4, request.getParameter("tip_aggregazione"));
            st.executeUpdate();
            Utility.chiusuraJdbc(st);
            query = "insert into normative_testi (cod_rif,tit_rif, cod_lang) values (?,?,?)";
            Set<String> parmKey = request.getParameterMap().keySet();
            for (String key : parmKey) {
                String[] parSplit = key.split("tit_rif_");
                if (parSplit.length > 1 && key.contains("tit_rif_")) {
                    String cod_lang = parSplit[1];
                    if (cod_lang != null) {
                        st = conn.prepareStatement(query);
                        st.setString(1, request.getParameter("cod_rif"));
                        st.setString(2, Utility.testoADb(request.getParameter("tit_rif_" + cod_lang)));
                        st.setString(3, cod_lang);
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
        try {
            conn = Utility.getConnection();
            Modifica verifica = verificaNotifica(request.getParameter("cod_rif"), null, request, "modificaDati", conn);
            ret = new JSONObject();
            if (!verifica.isNotifica()) {
                String query = "update normative set cod_tipo_rif = ? ,nome_rif = ? where cod_rif=?";
                st = conn.prepareStatement(query);
                st.setString(1, request.getParameter(("cod_tipo_rif")));
                st.setString(2, Utility.testoADb(request.getParameter(("nome_rif"))));
                st.setString(3, request.getParameter(("cod_rif")));
                st.executeUpdate();
                Utility.chiusuraJdbc(st);
                if ((Boolean) session.getAttribute("territorialitaNew")) {
//                    if (utente.getTipAggregazione() != null) {
                        if (request.getParameter("tip_aggregazione") != null) {
                            query = "update normative "
                                    + "set tip_aggregazione = ?"
                                    + "where cod_rif =? and tip_aggregazione is null";
                            st = conn.prepareStatement(query);
                            st.setString(1, request.getParameter("tip_aggregazione"));
                            st.setString(2, request.getParameter(("cod_rif")));
                            st.executeUpdate();
                            st.close();
                        }
//                    }
                }                
                String querySelect = "select * from normative_testi where cod_rif = ? and cod_lang = ?";
                String queryInsert = "insert into normative_testi (cod_rif,cod_lang,tit_rif) values (?,?,? )";
                query = "update normative_testi set tit_rif=? where cod_rif=? and cod_lang=?";
                Set<String> parmKey = request.getParameterMap().keySet();
                for (String key : parmKey) {
                    String[] parSplit = key.split("tit_rif_");
                    if (parSplit.length > 1 && key.contains("tit_rif_")) {
                        String cod_lang = parSplit[1];
                        if (cod_lang != null) {
                            stRead = conn.prepareStatement(querySelect);
                            stRead.setString(1, request.getParameter("cod_rif"));
                            stRead.setString(2, cod_lang);
                            rsRead = stRead.executeQuery();
                            if (rsRead.next()) {
                                st = conn.prepareStatement(query);
                                st.setString(1, Utility.testoADb(request.getParameter(key)));
                                st.setString(2, request.getParameter("cod_rif"));
                                st.setString(3, cod_lang);
                                st.executeUpdate();
                            } else {
                                st = conn.prepareStatement(queryInsert);
                                st.setString(1, request.getParameter("cod_rif"));
                                st.setString(2, cod_lang);
                                st.setString(3, Utility.testoADb(request.getParameter(key)));
                                st.execute();
                            }
                            Utility.chiusuraJdbc(rs);
                            Utility.chiusuraJdbc(st);
                            Utility.chiusuraJdbc(rsRead);
                            Utility.chiusuraJdbc(stRead);
                        }
                    }
                }

                scriviTCR(verifica, request.getParameter("cod_rif"), conn);
                ret.put("success", "Aggiornamento effettuato");
            } else {
                Utility.scriviSessione(request);
                JSONObject msg = new JSONObject();
                msg.put("notifica", "La risorsa è condivisa(non effettuo la modifica); Vuoi inviare una notifica?");
                ret.put("failure", msg);
            }
        } catch (SQLException e) {
            log.error("Errore update ", e);
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

    private boolean cancellaControllo(String key, Connection conn) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        boolean controllo = true;
        int righe = 0;
        String query = "select count(*) righe from gerarchia_operazioni where cod_rif = ? "
                + "union "
                + "select count(*) righe from gerarchia_settori where cod_rif=? "
                + "union "
                + "select count(*) righe from norme_interventi where cod_rif=? "
                + "union "
                + "select count(*) righe from norme_comuni where cod_rif=? "
                + "union "
                + "select count(*) righe from condizioni_normative where cod_rif=? ";
        try {
            st = conn.prepareStatement(query);
            st.setString(1, key);
            st.setString(2, key);
            st.setString(3, key);
            st.setString(4, key);
            st.setString(5, key);
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
        boolean controllo = false;
        try {
            conn = Utility.getConnection();
            ret = new JSONObject();
            controllo = cancellaControllo(request.getParameter("cod_rif"), conn);
            if (controllo) {
                String query = "delete from normative_documenti where cod_rif = ?";
                st = conn.prepareStatement(query);
                st.setString(1, request.getParameter("cod_rif"));
                st.executeUpdate();
                Utility.chiusuraJdbc(st);
                query = "delete from normative_testi where cod_rif=?";
                st = conn.prepareStatement(query);
                st.setString(1, request.getParameter("cod_rif"));
                st.executeUpdate();
                Utility.chiusuraJdbc(st);
                query = "delete from normative where cod_rif=?";
                st = conn.prepareStatement(query);
                st.setString(1, request.getParameter("cod_rif"));
                st.executeUpdate();
                ret.put("success", "Cancellazione effettuata");
            } else {
                ret.put("failure", "Normativa in uso");
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

    public JSONObject cancellaDocumento(HttpServletRequest request) throws Exception {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        try {
            conn = Utility.getConnection();
            Modifica verifica = verificaNotifica(request.getParameter("cod_rif"), null, request, "cancellaDocumento", conn);
            ret = new JSONObject();
            if (!verifica.isNotifica()) {
                String query = "delete from normative_documenti where cod_rif = ?";
                st = conn.prepareStatement(query);
                st.setString(1, request.getParameter("cod_rif"));
                st.executeUpdate();
                scriviTCR(verifica, request.getParameter("cod_rif"), conn);
                ret.put("success", "Cancellazione effettuata");
            } else {
                Utility.scriviSessione(request);
                JSONObject msg = new JSONObject();
                msg.put("notifica", "La risorsa è condivisa(non effettuo la modifica); Vuoi inviare una notifica?");
                ret.put("failure", msg);
            }
        } catch (SQLException e) {
            log.error("Errore delete documento fisico ", e);
            ret = new JSONObject();
            ret.put("failure", e.getMessage());
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public Modifica verificaNotifica(String codRif, NormativaModel nm, HttpServletRequest request, String azione, Connection conn) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        int righe = 0;
        Modifica modificato = new Modifica();
        modificato.init();
        String sql = null;
        try {
            if (azione.equals("modificaDati")) {
                sql = "select a.cod_rif,a.nome_rif,a.cod_tipo_rif,b.tit_rif "
                        + "from normative a "
                        + "join normative_testi b "
                        + "on a.cod_rif=b.cod_rif "
                        + "where a.cod_rif=? "
                        + "and b.cod_lang='it'";
                st = conn.prepareStatement(sql);
                st.setString(1, codRif);
                rs = st.executeQuery();
                if (rs.next()) {
                    if (!request.getParameter("tit_rif_it").equals(rs.getString("tit_rif"))
                            || !request.getParameter("nome_rif").equals(rs.getString("nome_rif"))
                            || !request.getParameter("cod_tipo_rif").equals(rs.getString("cod_tipo_rif"))) {
                        modificato.setModificaIntervento(true);
                    }
                }
                Utility.chiusuraJdbc(rs);
                Utility.chiusuraJdbc(st);
            } else if (azione.equals("caricaFile")) {
                sql = "select a.cod_rif,a.nome_file,a.tip_doc,length(a.doc_blob) len "
                        + "from normative_documenti a "
                        + "where a.cod_rif=? "
                        + "and a.cod_lang='it'";
                st = conn.prepareStatement(sql);
                st.setString(1, nm.getCodRif());
                rs = st.executeQuery();

                if (rs.next()) {
                    if (!nm.getFileUpload().getNomeFile().equals(rs.getString("nome_file"))
                            || !nm.getFileUpload().getTipDoc().equals(rs.getString("tip_doc"))
                            || nm.getFileUpload().getLength() != rs.getLong("len")) {
                        modificato.setModificaIntervento(true);
                    }

                } else {
                    modificato.setModificaIntervento(true);
                }
            } else if (azione.equals("cancellaDocumento")) {
                modificato.setModificaIntervento(true);
            }
            if (utente.getRuolo().equals("A") || utente.getRuolo().equals("B")) {
                if (modificato.isModificaIntervento()) {
                    sql = "select count(*) righe from "
                            + "(select distinct cod_int from norme_interventi "
                            + "where cod_rif = ? ) b "
                            + "join utenti_interventi x "
                            + "on b.cod_int = x.cod_int where x.cod_utente <> ?";
                    st = conn.prepareStatement(sql);
                    st.setString(1, codRif);
                    st.setString(2, utente.getCodUtente());
                    rs = st.executeQuery();
                    if (rs.next()) {
                        righe = rs.getInt("righe");
                    }
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
        HashMap<String, Object> param = null;
        HashMap<String, Object> funzRequest = null;
        if (session.getAttribute("sessioneTabelle") != null) {
            funzRequest = (HashMap<String, Object>) session.getAttribute("sessioneTabelle");
        } else {
            funzRequest = new HashMap<String, Object>();
        }
        if (funzRequest.containsKey("normative")) {
            param = (HashMap<String, Object>) funzRequest.get("normative");
        } else {
            param = new HashMap<String, Object>();
        }
        HashMap<String, List<String>> listaUtentiInterventi = new HashMap<String, List<String>>();
        List<String> listaInterventi = new ArrayList<String>();
        List<String> li = null;
        NormativaModel nm = null;
        try {
            conn = Utility.getConnection();
            ret = new JSONObject();
            String codRif = null;
            if (request.getParameter("table_name").equals("normative_documenti_upload")) {
                nm = (NormativaModel) param.get("fileObject");
                codRif = (String) nm.getCodRif();
            } else {
                codRif = (String) param.get("cod_rif");
            }
            sql = "select distinct x.cod_utente, x.cod_int from "
                    + "(select distinct cod_int from norme_interventi "
                    + "where cod_rif = ? ) b "
                    + "join utenti_interventi x "
                    + "on b.cod_int = x.cod_int "
                    + "order by x.cod_utente,x.cod_int";
            st = conn.prepareStatement(sql);
            st.setString(1, codRif);
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
            String msg = "";
            String sqlDeferred = "";
            if (listaUtentiInterventi.size() > 0) {
                if (request.getParameter("table_name").equals("normative_documenti")) {
                    msg = "Id Normativa = " + codRif + "\n";
                    msg = msg + "Nuovo titolo normativa = " + param.get("tit_rif_it") + "\n";
                    msg = msg + "Richiesta cancellazione documento fisico \n";
                    msg = msg + request.getParameter("textMsg");
                } else if (request.getParameter("table_name").equals("normative_documenti_upload")) {
                    msg = "Id Normativa = " + nm.getCodRif() + "\n";
                    msg = msg + "Nuovo documento = " + nm.getFileUpload().getNomeFile() + "\n";
                    msg = msg + request.getParameter("textMsg");
                } else {
                    // componi testo notifica
                    msg = "Id Normativa = " + codRif + "\n";
                    msg = msg + "Nuovo titolo normativa = " + param.get("tit_rif_it") + "\n";
                    msg = msg + "Nuovo nome normativa = " + param.get("nome_rif") + "\n";
                    msg = msg + "Nuovo codice tipo norma = " + param.get("cod_tipo_rif") + "\n";
                    msg = msg + request.getParameter("textMsg");
                }
                // inserisco in notifica
                sql = "insert into notifiche (cod_utente_origine,testo_notifica,stato_notifica,cod_elemento) values (?,?,?,?)";
                st = conn.prepareStatement(sql);
                st.setString(1, utente.getCodUtente());
                st.setString(2, msg);
                st.setString(3, "N");
                st.setString(4, "Normative=" + codRif);
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
                if (request.getParameter("table_name").equals("normative_documenti")) {
                    // componi stringa sql
                    sqlDeferred = "delete from normative_documenti where cod_rif = '" + codRif + "'";
                    // carica stringa sql nell'xml
                    doc.getSqlString().add(sqlDeferred.getBytes());

                } else if (request.getParameter("table_name").equals("normative_documenti_upload")) {
                    // inserisci il documento in tabella di appoggio
                    sql = "insert into notifiche_documenti (cnt,tip_doc,cod_lang,nome_file,doc_blob) values (?,?,'it',?,?)";
                    st = conn.prepareStatement(sql);
                    st.setInt(1, contatore);
                    st.setString(2, nm.getFileUpload().getTipDoc());
                    st.setString(3, nm.getFileUpload().getNomeFile());
                    st.setBinaryStream(4, nm.getFileUpload().getDocBlob(), nm.getFileUpload().getLength().intValue());
                    st.executeUpdate();
                    Utility.chiusuraJdbc(rs);
                    Utility.chiusuraJdbc(st);
                    // componi stringa sql
                    sqlDeferred = "delete from normative_documenti where cod_rif = '" + nm.getCodRif() + "'";
                    // carica stringa sql nell'xml
                    doc.getSqlString().add(sqlDeferred.getBytes());

                    sqlDeferred = "insert into normative_documenti (cod_rif,tip_doc,cod_lang,nome_file,doc_blob) select '" + nm.getCodRif() + "',"
                            + "tip_doc,cod_lang,nome_file,doc_blob from notifiche_documenti where cnt = " + contatore;
                    doc.getSqlString().add(sqlDeferred.getBytes());

                } else {
                    // componi stringa sql
                    sqlDeferred = "update normative_testi set tit_rif = '" + Utility.testoADbNotifica((String) param.get("tit_rif_it")) + "' where cod_rif = '" + codRif + "' and cod_lang='it';";
                    // carica stringa sql nell'xml
                    doc.getSqlString().add(sqlDeferred.getBytes());
                    sqlDeferred = "update normative set nome_rif = '" + Utility.testoADbNotifica((String) param.get("nome_rif")) + "', "
                            + "cod_tipo_rif = '" + param.get("cod_tipo_rif") + "' where cod_rif = '" + codRif + "'";
                    doc.getSqlString().add(sqlDeferred.getBytes());
                }
                sqlDeferred = "update interventi set data_modifica=current_timestamp where cod_int in (select distinct cod_int from norme_interventi where cod_rif = '" + codRif + "')";
                doc.getSqlString().add(sqlDeferred.getBytes());
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

            if (funzRequest.containsKey("normative")) {
                funzRequest.remove("normative");
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

    public void scriviTCR(Modifica modificato, String codRif, Connection conn) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        String sql = null;
        try {
            if (modificato.isModificaIntervento()) {
                sql = "update interventi set data_modifica=current_timestamp where cod_int in (select distinct cod_int from norme_interventi where cod_rif = ?)";
                st = conn.prepareStatement(sql);
                st.setString(1, codRif);
                st.executeUpdate();
                Boolean aggiorna = Boolean.parseBoolean(Configuration.getConfigurationParameter("updateTCR").toLowerCase());
                if (aggiorna) {
                    sql = "update interventi set flg_pubblicazione='S' where cod_int in (select distinct cod_int from norme_interventi where cod_rif = ?)";
                    st = conn.prepareStatement(sql);
                    st.setString(1, codRif);
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
