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

import it.people.dbm.utility.Utility;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *
 * @author Piergiorgio
 */
public class LeggiRecords {

    public JSONObject leggiClassiEnti(String key) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        PreparedStatement stLang = null;
        ResultSet rsLang = null;
        JSONObject ret = null;
        try {
            conn = Utility.getConnection();
            String queryLang = "select des_classe_ente, cod_lang from classi_enti_testi where cod_classe_ente = ?";
            String query = "select a.cod_classe_ente,b.des_classe_ente,a.flg_com "
                    + "from classi_enti a join classi_enti_testi b on a.cod_classe_ente=b.cod_classe_ente "
                    + "where b.cod_lang='it' and a.cod_classe_ente=?";
            st = conn.prepareStatement(query);
            st.setString(1, key);
            rs = st.executeQuery();
            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_classe_ente", rs.getString("cod_classe_ente"));
                loopObj.put("flg_com", rs.getString("flg_com"));
                stLang = conn.prepareStatement(queryLang);
                stLang.setString(1, rs.getString("cod_classe_ente"));
                rsLang = stLang.executeQuery();
                while (rsLang.next()) {
                    loopObj.put("des_classe_ente_" + rsLang.getString("cod_lang"), Utility.testoDaDb(rsLang.getString("des_classe_ente")));
                }
                Utility.chiusuraJdbc(rsLang);
                Utility.chiusuraJdbc(stLang);
                riga.add(loopObj);

            }

            ret = new JSONObject();
            JSONObject tab = new JSONObject();
            tab.put("classi_enti", riga);
            ret.put("success", tab);
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(rsLang);
            Utility.chiusuraJdbc(stLang);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public JSONObject leggiCud(String key) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        PreparedStatement stLang = null;
        ResultSet rsLang = null;
        JSONObject ret = null;
        try {
            conn = Utility.getConnection();
            String queryLang = "select des_cud, cod_lang from cud_testi where cod_cud = ?";
            String query = "select a.cod_cud,b.des_cud "
                    + "from cud a join cud_testi b on a.cod_cud=b.cod_cud "
                    + "where b.cod_lang='it' and a.cod_cud=?";
            st = conn.prepareStatement(query);
            st.setString(1, key);
            rs = st.executeQuery();
            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_cud", rs.getString("cod_cud"));
                stLang = conn.prepareStatement(queryLang);
                stLang.setString(1, rs.getString("cod_cud"));
                rsLang = stLang.executeQuery();
                while (rsLang.next()) {
                    loopObj.put("des_cud_" + rsLang.getString("cod_lang"), Utility.testoDaDb(rsLang.getString("des_cud")));
                }
                Utility.chiusuraJdbc(rsLang);
                Utility.chiusuraJdbc(stLang);
                riga.add(loopObj);
            }

            ret = new JSONObject();
            JSONObject tab = new JSONObject();
            tab.put("cud", riga);
            ret.put("success", tab);
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(rsLang);
            Utility.chiusuraJdbc(stLang);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public JSONObject leggiDestinatari(String key) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        PreparedStatement stLang = null;
        ResultSet rsLang = null;
        JSONObject ret = null;
        try {
            conn = Utility.getConnection();
            String queryLang = "select intestazione,nome_dest, cod_lang from destinatari_testi where cod_dest = ?";
            String query = "select a.cod_dest, b.intestazione, b.nome_dest, a.cap, a.indirizzo, a.citta, a.prov, "
                    + "a.tel, a.fax, a.email, a.cod_ente, d.des_ente "
                    + "from destinatari a "
                    + "join destinatari_testi b "
                    + "on a.cod_dest=b.cod_dest "
                    + "join enti_comuni c "
                    + "on a.cod_ente=c.cod_ente "
                    + "join enti_comuni_testi d "
                    + "on a.cod_ente = d.cod_ente "
                    + "and d.cod_lang=b.cod_lang "
                    + "where b.cod_lang='it' and a.cod_dest = ?";
            st = conn.prepareStatement(query);
            st.setString(1, key);
            rs = st.executeQuery();
            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_dest", rs.getString("cod_dest"));
                loopObj.put("cap", Utility.testoDaDb(rs.getString("cap")));
                loopObj.put("indirizzo", Utility.testoDaDb(rs.getString("indirizzo")));
                loopObj.put("citta", Utility.testoDaDb(rs.getString("citta")));
                loopObj.put("prov", Utility.testoDaDb(rs.getString("prov")));
                loopObj.put("tel", Utility.testoDaDb(rs.getString("tel")));
                loopObj.put("fax", Utility.testoDaDb(rs.getString("fax")));
                loopObj.put("email", Utility.testoDaDb(rs.getString("email")));
                loopObj.put("cod_ente", Utility.testoDaDb(rs.getString("cod_ente")));
                loopObj.put("des_ente", Utility.testoDaDb(rs.getString("des_ente")));
                stLang = conn.prepareStatement(queryLang);
                stLang.setString(1, rs.getString("cod_dest"));
                rsLang = stLang.executeQuery();
                while (rsLang.next()) {
                    loopObj.put("intestazione_" + rsLang.getString("cod_lang"), Utility.testoDaDb(rsLang.getString("intestazione")));
                    loopObj.put("nome_dest_" + rsLang.getString("cod_lang"), Utility.testoDaDb(rsLang.getString("nome_dest")));
                }
                Utility.chiusuraJdbc(rsLang);
                Utility.chiusuraJdbc(stLang);
                riga.add(loopObj);
            }

            ret = new JSONObject();
            JSONObject tab = new JSONObject();
            tab.put("destinatari", riga);
            ret.put("success", tab);
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(rsLang);
            Utility.chiusuraJdbc(stLang);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public JSONObject leggiEntiComuni(String key) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        PreparedStatement stLang = null;
        ResultSet rsLang = null;
        JSONObject ret = null;
        try {
            conn = Utility.getConnection();
            String queryLang = "select des_ente, cod_lang from enti_comuni_testi where cod_ente = ?";
            String query = "select a.cod_ente, a.cod_classe_ente, b.des_ente, d.des_classe_ente, "
                    + "a.indirizzo, a.cap, a.citta, a.prov, a.tel, a.fax, a.cod_istat, a.cod_bf, a.src_pyth, a.email, a.cod_nodo "
                    + "from enti_comuni a "
                    + "join enti_comuni_testi b "
                    + "on a.cod_ente=b.cod_ente "
                    + "join classi_enti c "
                    + "on a.cod_classe_ente=c.cod_classe_ente "
                    + "join classi_enti_testi d "
                    + "on a.cod_classe_ente = d.cod_classe_ente "
                    + "and d.cod_lang=b.cod_lang "
                    + "where b.cod_lang='it' and a.cod_ente = ?";
            st = conn.prepareStatement(query);
            st.setString(1, key);
            rs = st.executeQuery();
            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_ente", rs.getString("cod_ente"));
                loopObj.put("cod_classe_ente", rs.getString("cod_classe_ente"));
                loopObj.put("des_classe_ente", rs.getString("des_classe_ente"));
                loopObj.put("indirizzo", Utility.testoDaDb(rs.getString("indirizzo")));
                loopObj.put("cap", Utility.testoDaDb(rs.getString("cap")));
                loopObj.put("citta", Utility.testoDaDb(rs.getString("citta")));
                loopObj.put("prov", Utility.testoDaDb(rs.getString("prov")));
                loopObj.put("tel", Utility.testoDaDb(rs.getString("tel")));
                loopObj.put("fax", Utility.testoDaDb(rs.getString("fax")));
                loopObj.put("cod_istat", Utility.testoDaDb(rs.getString("cod_istat")));
                loopObj.put("cod_bf", Utility.testoDaDb(rs.getString("cod_bf")));
                loopObj.put("email", Utility.testoDaDb(rs.getString("email")));
                loopObj.put("src_pyth", Utility.testoDaDb(rs.getString("src_pyth")));
                loopObj.put("cod_nodo", Utility.testoDaDb(rs.getString("cod_nodo")));
                stLang = conn.prepareStatement(queryLang);
                stLang.setString(1, rs.getString("cod_ente"));
                rsLang = stLang.executeQuery();
                while (rsLang.next()) {
                    loopObj.put("des_ente_" + rsLang.getString("cod_lang"), Utility.testoDaDb(rsLang.getString("des_ente")));
                }
                Utility.chiusuraJdbc(rsLang);
                Utility.chiusuraJdbc(stLang);
                riga.add(loopObj);
            }

            ret = new JSONObject();
            JSONObject tab = new JSONObject();
            tab.put("enti_comuni", riga);
            ret.put("success", tab);
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(rsLang);
            Utility.chiusuraJdbc(stLang);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public JSONObject leggiDocumenti(String key) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        PreparedStatement stLang = null;
        ResultSet rsLang = null;
        JSONObject ret = null;
        try {
            conn = Utility.getConnection();
            String queryLang = "select tit_doc, cod_lang from documenti_testi where cod_doc = ?";
            String query = "select a.cod_doc cod_doc, a.flg_dic ,a.href href,b.tit_doc tit_doc,b.des_doc des_doc,d.tit_href tit_href,e.tip_doc tip_doc,e.nome_file nome_file, a.tip_aggregazione, h.des_aggregazione "
                    + "from documenti a "
                    + "join documenti_testi b "
                    + "on a.cod_doc=b.cod_doc "
                    + "left join tipi_aggregazione h "
                    + "on a.tip_aggregazione=h.tip_aggregazione "                    
                    + "left join href c "
                    + "on a.href=c.href "
                    + "left join href_testi d "
                    + "on a.href=d.href "
                    + "and b.cod_lang=d.cod_lang "
                    + "left join documenti_documenti e "
                    + "on a.cod_doc=e.cod_doc "
                    + "and b.cod_lang=e.cod_lang where b.cod_lang='it' and a.cod_doc=?";
            st = conn.prepareStatement(query);
            st.setString(1, key);
            rs = st.executeQuery();
            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_doc", rs.getString("cod_doc"));
                loopObj.put("tip_doc", Utility.testoDaDb(rs.getString("tip_doc")));
                loopObj.put("flg_dic", rs.getString("flg_dic"));
                loopObj.put("des_doc", Utility.testoDaDb(rs.getString("des_doc")));
                loopObj.put("href", rs.getString("href"));
                loopObj.put("tit_href", Utility.testoDaDb(rs.getString("tit_href")));
                loopObj.put("nome_file", rs.getString("nome_file"));
                loopObj.put("tip_aggregazione", rs.getString("tip_aggregazione"));
                loopObj.put("des_aggregazione", rs.getString("des_aggregazione"));                
                if (rs.getString("nome_file") != null && !rs.getString("nome_file").equals("")) {
                    loopObj.put("nome_file", "<a href=\"ScaricaFile?tipo=documento&codDoc=" + rs.getString("cod_doc") + "&tipDoc=" + rs.getString("tip_doc") + "\"target=\"_blank\" alt=\"" + rs.getString("nome_file") + "\">" + rs.getString("nome_file") + "</a>");
                }
                stLang = conn.prepareStatement(queryLang);
                stLang.setString(1, rs.getString("cod_doc"));
                rsLang = stLang.executeQuery();
                while (rsLang.next()) {
                    loopObj.put("tit_doc_" + rsLang.getString("cod_lang"), Utility.testoDaDb(rsLang.getString("tit_doc")));
                }
                Utility.chiusuraJdbc(rsLang);
                Utility.chiusuraJdbc(stLang);
                riga.add(loopObj);
            }

            ret = new JSONObject();
            JSONObject tab = new JSONObject();
            tab.put("documenti", riga);
            ret.put("success", tab);
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(rsLang);
            Utility.chiusuraJdbc(stLang);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public JSONObject leggiInterventi(String key) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        PreparedStatement stLang = null;
        ResultSet rsLang = null;
        JSONObject ret = null;
        try {
            conn = Utility.getConnection();
            String queryLang = "select tit_int, cod_lang from interventi_testi where cod_int = ?";
            String query = "select a.cod_int cod_int, a.flg_obb flg_obb, a.cod_proc cod_proc, b.tit_int tit_int, a.ordinamento ordinamento, "
                    + "d.tit_proc tit_proc, a.tip_aggregazione, e.des_aggregazione "
                    + "from interventi a "
                    + "join interventi_testi b "
                    + "on a.cod_int=b.cod_int "
                    + "join procedimenti c "
                    + "on a.cod_proc=c.cod_proc "
                    + "join procedimenti_testi d "
                    + "on a.cod_proc = d.cod_proc "
                    + "and d.cod_lang=b.cod_lang "
                    + "left join tipi_aggregazione e "
                    + "on a.tip_aggregazione=e.tip_aggregazione "
                    + "where b.cod_lang='it' and a.cod_int=?";
            st = conn.prepareStatement(query);
            st.setInt(1, Integer.parseInt(key));
            rs = st.executeQuery();
            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_int", rs.getInt("cod_int"));
                loopObj.put("cod_proc", rs.getString("cod_proc"));
                loopObj.put("tit_proc", Utility.testoDaDb(rs.getString("tit_proc")));
                loopObj.put("flg_obb", Utility.testoDaDb(rs.getString("flg_obb")));
                loopObj.put("ordinamento", rs.getInt("ordinamento"));
                loopObj.put("tip_aggregazione", rs.getString("tip_aggregazione"));
                loopObj.put("des_aggregazione", rs.getString("des_aggregazione"));
                stLang = conn.prepareStatement(queryLang);
                stLang.setInt(1, rs.getInt("cod_int"));
                rsLang = stLang.executeQuery();
                while (rsLang.next()) {
                    loopObj.put("tit_int_" + rsLang.getString("cod_lang"), Utility.testoDaDb(rsLang.getString("tit_int")));
                }
                Utility.chiusuraJdbc(rsLang);
                Utility.chiusuraJdbc(stLang);
                riga.add(loopObj);
            }

            ret = new JSONObject();
            JSONObject tab = new JSONObject();
            tab.put("interventi", riga);
            ret.put("success", tab);
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(rsLang);
            Utility.chiusuraJdbc(stLang);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public JSONObject leggiNormative(String key) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        PreparedStatement stLang = null;
        ResultSet rsLang = null;
        JSONObject ret = null;
        try {
            conn = Utility.getConnection();
            String queryLang = "select tit_rif, cod_lang from normative_testi where cod_rif = ?";
            String query = "select a.cod_rif cod_rif,a.nome_rif nome_rif,a.cod_tipo_rif cod_tipo_rif, "
                    + "b.tit_rif tit_rif,c.tipo_rif tipo_rif,d.tip_doc tip_doc,d.nome_file nome_file, "
                    + "a.tip_aggregazione,h.des_aggregazione  "
                    + "from normative a "
                    + "join normative_testi b "
                    + "on a.cod_rif=b.cod_rif "
                    + "join tipi_rif c "
                    + "on a.cod_tipo_rif=c.cod_tipo_rif "
                    + "and c.cod_lang=b.cod_lang "
                    + "left join normative_documenti d "
                    + "on a.cod_rif=d.cod_rif "
                    + "and b.cod_lang=d.cod_lang "
                    + "left join tipi_aggregazione h "
                    + "on a.tip_aggregazione=h.tip_aggregazione "
                    + "where b.cod_lang='it' and  a.cod_rif=?";
            st = conn.prepareStatement(query);
            st.setString(1, key);
            rs = st.executeQuery();
            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_rif", rs.getString("cod_rif"));
                loopObj.put("nome_rif", Utility.testoDaDb(rs.getString("nome_rif")));
                loopObj.put("cod_tipo_rif", rs.getString("cod_tipo_rif"));
                loopObj.put("tipo_rif", rs.getString("tipo_rif"));
                loopObj.put("tip_doc", rs.getString("tip_doc"));
                loopObj.put("nome_file", rs.getString("nome_file"));
                loopObj.put("tip_aggregazione", rs.getString("tip_aggregazione"));
                loopObj.put("des_aggregazione", rs.getString("des_aggregazione"));                  
                if (rs.getString("nome_file") != null && !rs.getString("nome_file").equals("")) {
                    loopObj.put("nome_file", "<a href=\"ScaricaFile?tipo=normativa&codNorma=" + rs.getString("cod_rif") + "&tipDoc=" + rs.getString("tip_doc") + "\"target=\"_blank\" alt=\"" + rs.getString("nome_file") + "\">" + rs.getString("nome_file") + "</a>");
                }
                stLang = conn.prepareStatement(queryLang);
                stLang.setString(1, rs.getString("cod_rif"));
                rsLang = stLang.executeQuery();
                while (rsLang.next()) {
                    loopObj.put("tit_rif_" + rsLang.getString("cod_lang"), Utility.testoDaDb(rsLang.getString("tit_rif")));
                }
                Utility.chiusuraJdbc(rsLang);
                Utility.chiusuraJdbc(stLang);
                riga.add(loopObj);
            }

            ret = new JSONObject();
            JSONObject tab = new JSONObject();
            tab.put("normative", riga);
            ret.put("success", tab);
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(rsLang);
            Utility.chiusuraJdbc(stLang);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public JSONObject leggiOneri(String key) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        PreparedStatement stLang = null;
        ResultSet rsLang = null;
        JSONObject ret = null;
        try {
            conn = Utility.getConnection();
            String queryLang = "select des_oneri, cod_lang from oneri_testi where cod_oneri = ?";
            String query = "select a.cod_oneri cod_oneri,a.cod_cud cod_cud,a.cod_doc_onere cod_doc_onere,a.imp_acc imp_acc, "
                    + "a.cod_padre cod_padre,b.des_oneri des_oneri,b.note note, d.des_cud des_cud, "
                    + "f.des_doc_onere des_doc_onere,h.des_gerarchia des_gerarchia, a.tip_aggregazione, i.des_aggregazione "
                    + "from oneri a "
                    + "join oneri_testi b "
                    + "on a.cod_oneri=b.cod_oneri "
                    + "join cud c "
                    + "on a.cod_cud=c.cod_cud "
                    + "join cud_testi d "
                    + "on a.cod_cud=d.cod_cud "
                    + "and b.cod_lang=d.cod_lang "
                    + "left join oneri_documenti e "
                    + "on a.cod_doc_onere = e.cod_doc_onere "
                    + "left join oneri_documenti_testi f "
                    + "on a.cod_doc_onere=f.cod_doc_onere "
                    + "and f.cod_lang=b.cod_lang "
                    + "left join oneri_gerarchia g "
                    + "on a.cod_padre=g.cod_padre "
                    + "and g.cod_padre=g.cod_figlio "
                    + "left join oneri_gerarchia_testi h "
                    + "on g.cod_figlio= h.cod_figlio "
                    + "and h.cod_lang=b.cod_lang "
                    + "left join tipi_aggregazione i "
                    + "on a.tip_aggregazione=i.tip_aggregazione "
                    + "where b.cod_lang='it' and a.cod_oneri=?";
            st = conn.prepareStatement(query);
            st.setString(1, key);
            rs = st.executeQuery();
            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_oneri", rs.getString("cod_oneri"));
                loopObj.put("cod_cud", rs.getString("cod_cud"));
                loopObj.put("cod_doc_onere", (rs.getObject("cod_doc_onere") == null ? "" : rs.getString("cod_doc_onere")));
                loopObj.put("imp_acc", (rs.getObject("imp_acc") == null ? "" : rs.getDouble("imp_acc")));
                loopObj.put("cod_padre", (rs.getObject("cod_padre") == null ? "" : rs.getInt("cod_padre")));
                loopObj.put("des_cud", Utility.testoDaDb(rs.getString("des_cud")));
                loopObj.put("des_doc_onere", (rs.getObject("des_doc_onere") == null ? "" : Utility.testoDaDb(rs.getString("des_doc_onere"))));
                loopObj.put("des_gerarchia", (rs.getObject("des_gerarchia") == null ? "" : Utility.testoDaDb(rs.getString("des_gerarchia"))));
                loopObj.put("tip_aggregazione", rs.getString("tip_aggregazione"));
                loopObj.put("des_aggregazione", rs.getString("des_aggregazione"));
                stLang = conn.prepareStatement(queryLang);
                stLang.setString(1, rs.getString("cod_oneri"));
                rsLang = stLang.executeQuery();
                while (rsLang.next()) {
                    loopObj.put("des_oneri_" + rsLang.getString("cod_lang"), Utility.testoDaDb(rsLang.getString("des_oneri")));
                    loopObj.put("note_" + rsLang.getString("cod_lang"), (rs.getObject("note") == null ? "" : rs.getString("note")));
                }
                Utility.chiusuraJdbc(rsLang);
                Utility.chiusuraJdbc(stLang);
                riga.add(loopObj);
            }
            ret = new JSONObject();
            JSONObject tab = new JSONObject();
            tab.put("oneri", riga);
            ret.put("success", tab);
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(rsLang);
            Utility.chiusuraJdbc(stLang);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public JSONObject leggiOneriCampi(String key) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        PreparedStatement stLang = null;
        ResultSet rsLang = null;
        JSONObject ret = null;
        try {
            conn = Utility.getConnection();
            String queryLang = "select testo_campo, cod_lang from oneri_campi_testi where cod_onere_campo = ?";
            String query = "select a.cod_onere_campo,a.tp_campo,a.lng_campo,a.lng_dec,b.testo_campo, a.tip_aggregazione, e.des_aggregazione "
                    + "from oneri_campi a "
                    + "join oneri_campi_testi b "
                    + "on a.cod_onere_campo=b.cod_onere_campo "
                    + "left join tipi_aggregazione e "
                    + "on a.tip_aggregazione=e.tip_aggregazione "
                    + "where b.cod_lang='it' and a.cod_onere_campo=?";
            st = conn.prepareStatement(query);
            st.setString(1, key);
            rs = st.executeQuery();
            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_onere_campo", rs.getString("cod_onere_campo"));
                loopObj.put("testo_campo", Utility.testoDaDb(rs.getString("testo_campo")));
                loopObj.put("tp_campo", rs.getString("tp_campo"));
                loopObj.put("lng_campo", rs.getInt("lng_campo"));
                loopObj.put("lng_dec", rs.getInt("lng_dec"));
                loopObj.put("tip_aggregazione", rs.getString("tip_aggregazione"));
                loopObj.put("des_aggregazione", rs.getString("des_aggregazione"));
                stLang = conn.prepareStatement(queryLang);
                stLang.setString(1, rs.getString("cod_onere_campo"));
                rsLang = stLang.executeQuery();
                while (rsLang.next()) {
                    loopObj.put("testo_campo_" + rsLang.getString("cod_lang"), Utility.testoDaDb(rsLang.getString("testo_campo")));
                }
                Utility.chiusuraJdbc(rsLang);
                Utility.chiusuraJdbc(stLang);
                riga.add(loopObj);
            }
            ret = new JSONObject();
            JSONObject tab = new JSONObject();
            tab.put("oneri_campi", riga);
            ret.put("success", tab);
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(rsLang);
            Utility.chiusuraJdbc(stLang);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public JSONObject leggiOneriDocumenti(String key) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        PreparedStatement stLang = null;
        ResultSet rsLang = null;
        JSONObject ret = null;
        try {
            conn = Utility.getConnection();
            String queryLang = "select des_doc_onere, cod_lang from oneri_documenti_testi where cod_doc_onere = ?";
            String query = "select a.cod_doc_onere,a.tip_doc,a.nome_file,b.des_doc_onere, a.tip_aggregazione, e.des_aggregazione  "
                    + "from oneri_documenti a "
                    + "join oneri_documenti_testi b "
                    + "on a.cod_doc_onere=b.cod_doc_onere "
                    + "and a.cod_lang=b.cod_lang "
                    + "left join tipi_aggregazione e "
                    + "on a.tip_aggregazione=e.tip_aggregazione "
                    + "where a.cod_lang='it' "
                    + "and a.cod_doc_onere=?";
            st = conn.prepareStatement(query);
            st.setString(1, key);
            rs = st.executeQuery();
            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_doc_onere", rs.getString("cod_doc_onere"));
                loopObj.put("nome_file", Utility.testoDaDb(rs.getString("nome_file")));
                loopObj.put("tip_doc", Utility.testoDaDb(rs.getString("tip_doc")));
                loopObj.put("tip_aggregazione", rs.getString("tip_aggregazione"));
                loopObj.put("des_aggregazione", rs.getString("des_aggregazione"));
                if (!rs.getString("nome_file").equals("")) {
                    loopObj.put("nome_file", "<a href=\"ScaricaFile?tipo=tariffari&codDoc=" + rs.getString("cod_doc_onere") + "\"target=\"_blank\" alt=\"" + rs.getString("nome_file") + "\">" + rs.getString("nome_file") + "</a>");
                }
                stLang = conn.prepareStatement(queryLang);
                stLang.setString(1, rs.getString("cod_doc_onere"));
                rsLang = stLang.executeQuery();
                while (rsLang.next()) {
                    loopObj.put("des_doc_onere_" + rsLang.getString("cod_lang"), Utility.testoDaDb(rsLang.getString("des_doc_onere")));
                }
                Utility.chiusuraJdbc(rsLang);
                Utility.chiusuraJdbc(stLang);
                riga.add(loopObj);
            }
            ret = new JSONObject();
            JSONObject tab = new JSONObject();
            tab.put("oneri_documenti", riga);
            ret.put("success", tab);
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(rsLang);
            Utility.chiusuraJdbc(stLang);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public JSONObject leggiOneriFormula(String key) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        try {
            conn = Utility.getConnection();
            String query = "select a.cod_onere_formula,a.des_formula,a.formula, a.tip_aggregazione, b.des_aggregazione "
                    + "from oneri_formula a "
                    + "left join tipi_aggregazione b "
                    + "on a.tip_aggregazione = b.tip_aggregazione "
                    + "where a.cod_onere_formula=?";
            st = conn.prepareStatement(query);
            st.setString(1, key);
            rs = st.executeQuery();
            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_onere_formula", rs.getString("cod_onere_formula"));
                loopObj.put("des_formula", Utility.testoDaDb(rs.getString("des_formula")));
                loopObj.put("formula", Utility.testoDaDb(rs.getString("formula")));
                loopObj.put("tip_aggregazione", rs.getString("tip_aggregazione"));
                loopObj.put("des_aggregazione", rs.getString("des_aggregazione"));
                riga.add(loopObj);
            }
            ret = new JSONObject();
            JSONObject tab = new JSONObject();
            tab.put("oneri_formula", riga);
            ret.put("success", tab);
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public JSONObject leggiOperazioni(String key) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        PreparedStatement stLang = null;
        ResultSet rsLang = null;
        JSONObject ret = null;
        try {
            conn = Utility.getConnection();
            String queryLang = "select des_ope, cod_lang from operazioni where cod_ope = ?";
            String query = "select des_ope , cod_ope "
                    + "from operazioni "
                    + "where cod_lang='it' and cod_ope=?";
            st = conn.prepareStatement(query);
            st.setString(1, key);
            rs = st.executeQuery();
            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_ope", Utility.testoDaDb(rs.getString("cod_ope")));
                stLang = conn.prepareStatement(queryLang);
                stLang.setString(1, rs.getString("cod_ope"));
                rsLang = stLang.executeQuery();
                while (rsLang.next()) {
                    loopObj.put("des_ope_" + rsLang.getString("cod_lang"), Utility.testoDaDb(rsLang.getString("des_ope")));
                }
                Utility.chiusuraJdbc(rsLang);
                Utility.chiusuraJdbc(stLang);
                riga.add(loopObj);
            }
            ret = new JSONObject();
            JSONObject tab = new JSONObject();
            tab.put("operazioni", riga);
            ret.put("success", tab);
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(rsLang);
            Utility.chiusuraJdbc(stLang);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public JSONObject leggiProcedimenti(String key) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        PreparedStatement stLang = null;
        ResultSet rsLang = null;
        JSONObject ret = null;
        try {
            conn = Utility.getConnection();
            String queryLang = "select tit_proc, cod_lang from procedimenti_testi where cod_proc = ?";
            String query = "select a.cod_proc cod_proc, a.flg_bollo flg_bollo, a.flg_tipo_proc flg_tipo_proc, a.cod_cud cod_cud, "
                    + "b.tit_proc tit_proc, d.des_cud, a.ter_eva ter_eva, a.tip_aggregazione, e.des_aggregazione "
                    + "from procedimenti a "
                    + "join procedimenti_testi b "
                    + "on a.cod_proc=b.cod_proc "
                    + "join cud c "
                    + "on a.cod_cud=c.cod_cud "
                    + "join cud_testi d "
                    + "on a.cod_cud = d.cod_cud "
                    + "and d.cod_lang=b.cod_lang "
                    + "left join tipi_aggregazione e "
                    + "on a.tip_aggregazione = e.tip_aggregazione "
                    + "where b.cod_lang='it' and a.cod_proc=?";
            st = conn.prepareStatement(query);
            st.setString(1, key);
            rs = st.executeQuery();
            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_proc", rs.getString("cod_proc"));
                loopObj.put("cod_cud", rs.getString("cod_cud"));
                loopObj.put("flg_bollo", rs.getString("flg_bollo"));
                loopObj.put("des_cud", Utility.testoDaDb(rs.getString("des_cud")));
                loopObj.put("flg_tipo_proc", rs.getString("flg_tipo_proc"));
                loopObj.put("ter_eva", rs.getInt("ter_eva"));
                loopObj.put("tip_aggregazione", rs.getString("tip_aggregazione"));
                loopObj.put("des_aggregazione", rs.getString("des_aggregazione"));
                stLang = conn.prepareStatement(queryLang);
                stLang.setString(1, rs.getString("cod_proc"));
                rsLang = stLang.executeQuery();
                while (rsLang.next()) {
                    loopObj.put("tit_proc_" + rsLang.getString("cod_lang"), Utility.testoDaDb(rsLang.getString("tit_proc")));
                }
                Utility.chiusuraJdbc(rsLang);
                Utility.chiusuraJdbc(stLang);
                riga.add(loopObj);
            }
            ret = new JSONObject();
            JSONObject tab = new JSONObject();
            tab.put("procedimenti", riga);
            ret.put("success", tab);
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(rsLang);
            Utility.chiusuraJdbc(stLang);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public JSONObject leggiSettoriAttivita(String key) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        PreparedStatement stLang = null;
        ResultSet rsLang = null;
        JSONObject ret = null;
        try {
            conn = Utility.getConnection();
            String queryLang = "select des_sett, cod_lang from settori_attivita where cod_sett = ?";
            String query = "select des_sett , cod_sett "
                    + "from settori_attivita "
                    + "where cod_lang='it' and cod_sett=?";
            st = conn.prepareStatement(query);
            st.setString(1, key);
            rs = st.executeQuery();
            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_sett", rs.getString("cod_sett"));
                stLang = conn.prepareStatement(queryLang);
                stLang.setString(1, rs.getString("cod_sett"));
                rsLang = stLang.executeQuery();
                while (rsLang.next()) {
                    loopObj.put("des_sett_" + rsLang.getString("cod_lang"), Utility.testoDaDb(rsLang.getString("des_sett")));
                }
                Utility.chiusuraJdbc(rsLang);
                Utility.chiusuraJdbc(stLang);
                riga.add(loopObj);
            }
            ret = new JSONObject();
            JSONObject tab = new JSONObject();
            tab.put("settori_attivita", riga);
            ret.put("success", tab);
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(rsLang);
            Utility.chiusuraJdbc(stLang);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public JSONObject leggiSportelli(String key) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        PreparedStatement stLang = null;
        ResultSet rsLang = null;
        JSONObject ret = null;
        try {
            conn = Utility.getConnection();
            String queryLang = "select des_sport, cod_lang from sportelli_testi where cod_sport = ?";
            String query = "select a.cod_sport cod_sport, a.nome_rup nome_rup, a.flg_attivo, a.cap, a.indirizzo, a.citta, a.prov, "
                    + "a.tel, a.fax, a.email, a.email_cert, a.flg_pu, a.flg_su, b.des_sport des_sport "
                    + "from sportelli a "
                    + "join sportelli_testi b "
                    + "on a.cod_sport = b.cod_sport "
                    + "where b.cod_lang='it' and a.cod_sport=?";
            st = conn.prepareStatement(query);
            st.setString(1, key);
            rs = st.executeQuery();
            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_sport", rs.getString("cod_sport"));
                loopObj.put("nome_rup", rs.getString("nome_rup"));
                loopObj.put("flg_attivo", Utility.testoDaDb(rs.getString("flg_attivo")));
                loopObj.put("indirizzo", Utility.testoDaDb(rs.getString("indirizzo")));
                loopObj.put("cap", Utility.testoDaDb(rs.getString("cap")));
                loopObj.put("citta", Utility.testoDaDb(rs.getString("citta")));
                loopObj.put("prov", Utility.testoDaDb(rs.getString("prov")));
                loopObj.put("tel", Utility.testoDaDb(rs.getString("tel")));
                loopObj.put("fax", Utility.testoDaDb(rs.getString("fax")));
                loopObj.put("email_cert", Utility.testoDaDb(rs.getString("email_cert")));
                loopObj.put("flg_pu", Utility.testoDaDb(rs.getString("flg_pu")));
                loopObj.put("email", Utility.testoDaDb(rs.getString("email")));
                loopObj.put("flg_su", Utility.testoDaDb(rs.getString("flg_su")));
                stLang = conn.prepareStatement(queryLang);
                stLang.setString(1, rs.getString("cod_sport"));
                rsLang = stLang.executeQuery();
                while (rsLang.next()) {
                    loopObj.put("des_sport_" + rsLang.getString("cod_lang"), Utility.testoDaDb(rsLang.getString("des_sport")));
                }
                Utility.chiusuraJdbc(rsLang);
                Utility.chiusuraJdbc(stLang);
                riga.add(loopObj);
            }
            ret = new JSONObject();
            JSONObject tab = new JSONObject();
            tab.put("sportelli", riga);
            ret.put("success", tab);
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(rsLang);
            Utility.chiusuraJdbc(stLang);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public JSONObject leggiTestoCondizioni(String key) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        PreparedStatement stLang = null;
        ResultSet rsLang = null;
        JSONObject ret = null;
        try {
            conn = Utility.getConnection();
            String queryLang = "select testo_cond, cod_lang from testo_condizioni where cod_cond = ?";
            String query = "select a.cod_cond cod_cond, a.testo_cond testo_cond, a.tip_aggregazione, e.des_aggregazione "
                    + "from testo_condizioni a "
                    + "left join tipi_aggregazione e "
                    + "on a.tip_aggregazione=e.tip_aggregazione "
                    + "where a.cod_lang='it' and a.cod_cond=?";
            st = conn.prepareStatement(query);
            st.setString(1, key);
            rs = st.executeQuery();
            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_cond", rs.getString("cod_cond"));
                stLang = conn.prepareStatement(queryLang);
                stLang.setString(1, rs.getString("cod_cond"));
                rsLang = stLang.executeQuery();
                while (rsLang.next()) {
                    loopObj.put("testo_cond_" + rsLang.getString("cod_lang"), Utility.testoDaDb(rsLang.getString("testo_cond")));
                    loopObj.put("tip_aggregazione", rs.getString("tip_aggregazione"));
                    loopObj.put("des_aggregazione", rs.getString("des_aggregazione"));
                }
                Utility.chiusuraJdbc(rsLang);
                Utility.chiusuraJdbc(stLang);
                riga.add(loopObj);
            }
            ret = new JSONObject();
            JSONObject tab = new JSONObject();
            tab.put("testo_condizioni", riga);
            ret.put("success", tab);
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(rsLang);
            Utility.chiusuraJdbc(stLang);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public JSONObject leggiTipiAggregazione(String key) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        try {
            conn = Utility.getConnection();
            String query = "select a.tip_aggregazione tip_aggregazione, a.des_aggregazione des_aggregazione, b.href href, c.tit_href tit_href "
                    + "from tipi_aggregazione a "
                    + "left join href b "
                    + "on a.href=b.href "
                    + "left join href_testi c "
                    + "on b.href=c.href and c.cod_lang='it' "
                    + "where a.tip_aggregazione=?";
            st = conn.prepareStatement(query);
            st.setString(1, key);
            rs = st.executeQuery();
            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("tip_aggregazione", rs.getString("tip_aggregazione"));
                loopObj.put("des_aggregazione", Utility.testoDaDb(rs.getString("des_aggregazione")));
                loopObj.put("href", rs.getString("href"));
                loopObj.put("tit_href", Utility.testoDaDb(rs.getString("tit_href")));
                riga.add(loopObj);
            }
            ret = new JSONObject();
            JSONObject tab = new JSONObject();
            tab.put("tipi_aggregazione", riga);
            ret.put("success", tab);
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public JSONObject leggiHref(String key) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        PreparedStatement stLang = null;
        ResultSet rsLang = null;
        JSONObject ret = null;
        try {
            conn = Utility.getConnection();
            String queryLang = "select tit_href, piede_href,cod_lang from href_testi where href = ?";
            String query = "select a.href href, b.tit_href tit_href, b.piede_href piede_href, a.tp_href tp_href "
                    + "from href a "
                    + "join href_testi b "
                    + "on a.href=b.href "
                    + "where a.href=? and b.cod_lang='it'";
            st = conn.prepareStatement(query);
            st.setString(1, key);
            rs = st.executeQuery();
            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("href", rs.getString("href"));
                loopObj.put("tp_href", Utility.testoDaDb(rs.getString("tp_href")));
                stLang = conn.prepareStatement(queryLang);
                stLang.setString(1, rs.getString("href"));
                rsLang = stLang.executeQuery();
                while (rsLang.next()) {
                    loopObj.put("tit_href_" + rsLang.getString("cod_lang"), Utility.testoDaDb(rsLang.getString("tit_href")));
                    loopObj.put("piede_href_" + rsLang.getString("cod_lang"), Utility.testoDaDb(rsLang.getString("piede_href")));
                }
                Utility.chiusuraJdbc(rsLang);
                Utility.chiusuraJdbc(stLang);
                riga.add(loopObj);
            }
            ret = new JSONObject();
            JSONObject tab = new JSONObject();
            tab.put("href", riga);
            ret.put("success", tab);
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(rsLang);
            Utility.chiusuraJdbc(stLang);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public JSONObject leggiTipiRif(String key) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        PreparedStatement stLang = null;
        ResultSet rsLang = null;
        JSONObject ret = null;
        try {
            conn = Utility.getConnection();
            String queryLang = "select tipo_rif,cod_lang from tipi_rif where cod_tipo_rif = ?";
            String query = "select cod_tipo_rif, tipo_rif "
                    + "from tipi_rif "
                    + "where cod_lang='it' and cod_tipo_rif=?";
            st = conn.prepareStatement(query);
            st.setString(1, key);
            rs = st.executeQuery();
            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_tipo_rif", rs.getString("cod_tipo_rif"));
                stLang = conn.prepareStatement(queryLang);
                stLang.setString(1, rs.getString("cod_tipo_rif"));
                rsLang = stLang.executeQuery();
                while (rsLang.next()) {
                    loopObj.put("tipo_rif_" + rsLang.getString("cod_lang"), Utility.testoDaDb(rsLang.getString("tipo_rif")));
                }
                Utility.chiusuraJdbc(rsLang);
                Utility.chiusuraJdbc(stLang);
                riga.add(loopObj);
            }
            ret = new JSONObject();
            JSONObject tab = new JSONObject();
            tab.put("tipi_rif", riga);
            ret.put("success", tab);
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(rsLang);
            Utility.chiusuraJdbc(stLang);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public JSONObject leggiUtenti(String key) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        try {
            conn = Utility.getConnection();
            String query = "Select a.cod_utente cod_utente,a.cognome_nome cognome_nome ,"
                    + " a.email email,a.ruolo ruolo,a.cod_utente_padre cod_utente_padre ,b.cognome_nome cognome_nome_padre"
                    + " from utenti as a left join utenti as b on a.cod_utente_padre = b.cod_utente"
                    + " where a.cod_utente=?";
            st = conn.prepareStatement(query);
            st.setString(1, key);
            rs = st.executeQuery();
            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_utente", rs.getString("cod_utente"));
                loopObj.put("cognome_nome", Utility.testoDaDb(rs.getString("cognome_nome")));
                loopObj.put("email", rs.getString("email"));
                loopObj.put("ruolo", rs.getString("ruolo"));
                loopObj.put("cod_utente_padre", rs.getString("cod_utente_padre"));
                loopObj.put("cognome_nome_padre", Utility.testoDaDb(rs.getString("cognome_nome_padre")));
                riga.add(loopObj);
            }
            ret = new JSONObject();
            JSONObject tab = new JSONObject();
            tab.put("utenti", riga);
            ret.put("success", tab);
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public JSONObject leggiAllegati(String keyInt, String keyDoc) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        try {
            conn = Utility.getConnection();
            String query = "Select a.cod_int,a.cod_doc , a.flg_obb,a.copie,a.flg_autocert,a.cod_cond,a.ordinamento,a.tipologie, "
                    + " a.num_max_pag,a.dimensione_max, c.tit_doc, d.testo_cond,b.href,f.tit_href, it.tit_int "
                    + " from allegati a join documenti b on a.cod_doc=b.cod_doc join documenti_testi c on a.cod_doc=c.cod_doc "
                    + "join interventi i on a.cod_int=i.cod_int join interventi_testi it on a.cod_int=it.cod_int and it.cod_lang=c.cod_lang "
                    + "left join testo_condizioni d on a.cod_cond=d.cod_cond and c.cod_lang=d.cod_lang "
                    + "left join href e on b.href = e.href left join href_testi f on b.href=f.href and c.cod_lang=f.cod_lang "
                    + " where a.cod_int=? and a.cod_doc=? and c.cod_lang='it'";
            st = conn.prepareStatement(query);
            st.setInt(1, Integer.parseInt(keyInt));
            st.setString(2, keyDoc);
            rs = st.executeQuery();
            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_int", rs.getInt("cod_int"));
                loopObj.put("tit_int", Utility.testoDaDb(rs.getString("tit_int")));
                loopObj.put("cod_doc", rs.getString("cod_doc"));
                loopObj.put("tit_doc", Utility.testoDaDb(rs.getString("tit_doc")));
                loopObj.put("flg_obb", rs.getString("flg_obb"));
                loopObj.put("flg_autocert", rs.getString("flg_autocert"));
                loopObj.put("copie", rs.getInt("copie"));
                loopObj.put("num_max_pag", rs.getInt("num_max_pag"));
                loopObj.put("dimensione_max", rs.getInt("dimensione_max"));
                loopObj.put("ordinamento", rs.getInt("ordinamento"));
                loopObj.put("cod_cond", rs.getString("cod_cond"));
                loopObj.put("tipologie", rs.getString("tipologie"));
                loopObj.put("testo_cond", Utility.testoDaDb(rs.getString("testo_cond")));
                loopObj.put("href", rs.getString("href"));
                loopObj.put("tit_href", Utility.testoDaDb(rs.getString("tit_href")));

                riga.add(loopObj);
            }
            ret = new JSONObject();
            JSONObject tab = new JSONObject();
            tab.put("allegati", riga);
            ret.put("success", tab);
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public JSONObject leggiOneriIntervento(String keyInt, String keyOneri) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        try {
            conn = Utility.getConnection();
            String query = "Select a.cod_int,a.cod_oneri , c.tit_int, e.des_oneri "
                    + " from oneri_interventi a join interventi b on a.cod_int=b.cod_int join interventi_testi c on a.cod_int=c.cod_int "
                    + "join oneri d on a.cod_oneri=d.cod_oneri join oneri_testi e on a.cod_oneri=e.cod_oneri and c.cod_lang=e.cod_lang "
                    + " where a.cod_int=? and a.cod_oneri=? and c.cod_lang='it'";
            st = conn.prepareStatement(query);
            st.setInt(1, Integer.parseInt(keyInt));
            st.setString(2, keyOneri);
            rs = st.executeQuery();
            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_int", rs.getInt("cod_int"));
                loopObj.put("tit_int", Utility.testoDaDb(rs.getString("tit_int")));
                loopObj.put("cod_oneri", rs.getString("cod_oneri"));
                loopObj.put("des_oneri", Utility.testoDaDb(rs.getString("des_oneri")));
                riga.add(loopObj);
            }
            ret = new JSONObject();
            JSONObject tab = new JSONObject();
            tab.put("oneri_interventi", riga);
            ret.put("success", tab);
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public JSONObject leggiNormeInterventi(String keyInt, String keyRif) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        try {
            conn = Utility.getConnection();
            String query = "Select a.cod_int,a.cod_rif , a.art_rif, c.tit_int, e.tit_rif "
                    + " from norme_interventi a join interventi b on a.cod_int=b.cod_int join interventi_testi c on a.cod_int=c.cod_int "
                    + "join normative d on a.cod_rif=d.cod_rif join normative_testi e on a.cod_rif=e.cod_rif and c.cod_lang=e.cod_lang "
                    + " where a.cod_int=? and a.cod_rif=? and c.cod_lang='it'";
            st = conn.prepareStatement(query);
            st.setInt(1, Integer.parseInt(keyInt));
            st.setString(2, keyRif);
            rs = st.executeQuery();
            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_int", rs.getInt("cod_int"));
                loopObj.put("tit_int", Utility.testoDaDb(rs.getString("tit_int")));
                loopObj.put("cod_rif", rs.getString("cod_rif"));
                loopObj.put("tit_rif", Utility.testoDaDb(rs.getString("tit_rif")));
                loopObj.put("art_rif", Utility.testoDaDb(rs.getString("art_rif")));
                riga.add(loopObj);
            }
            ret = new JSONObject();
            JSONObject tab = new JSONObject();
            tab.put("norme_interventi", riga);
            ret.put("success", tab);
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public JSONObject leggiInterventiCollegati(String keyInt, String keyIntPadre) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        try {
            conn = Utility.getConnection();
            String query = "Select a.cod_int,a.cod_int_padre , a.cod_cond, c.tit_int, e.tit_int tit_int_padre, f.testo_cond "
                    + "from interventi_collegati a "
                    + "join interventi b on a.cod_int=b.cod_int join interventi_testi c on a.cod_int=c.cod_int "
                    + "join interventi d on a.cod_int_padre=d.cod_int join interventi_testi e on a.cod_int_padre=e.cod_int and c.cod_lang=e.cod_lang "
                    + "left join testo_condizioni f on a.cod_cond=f.cod_cond and c.cod_lang=f.cod_lang "
                    + " where a.cod_int=? and a.cod_int_padre=? and c.cod_lang='it'";
            st = conn.prepareStatement(query);
            st.setInt(1, Integer.parseInt(keyInt));
            st.setString(2, keyIntPadre);
            rs = st.executeQuery();
            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_int", rs.getInt("cod_int"));
                loopObj.put("tit_int", Utility.testoDaDb(rs.getString("tit_int")));
                loopObj.put("cod_int_padre", rs.getInt("cod_int_padre"));
                loopObj.put("tit_int_padre", Utility.testoDaDb(rs.getString("tit_int_padre")));
                loopObj.put("cod_cond", rs.getString("cod_cond"));
                loopObj.put("testo_cond", Utility.testoDaDb(rs.getString("testo_cond")));
                riga.add(loopObj);
            }
            ret = new JSONObject();
            JSONObject tab = new JSONObject();
            tab.put("interventi_collegati", riga);
            ret.put("success", tab);
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }
}
