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
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;

import it.people.dbm.utility.Utility;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.LoggerFactory;
/**
 *
 * @author Piergiorgio
 */
public class Sportelli {

    private static Logger log = LoggerFactory.getLogger(Sportelli.class);

    public JSONObject action(HttpServletRequest request) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        PreparedStatement stLang = null;
        ResultSet rsLang = null;
        JSONObject ret = null;
        int righe = 0;
        try {
            conn = Utility.getConnection();
            String offset = request.getParameter("start");
            String size = request.getParameter("size");
            String sort = request.getParameter("sort");
            String order = request.getParameter("dir");
            String href = request.getParameter("href");
            String param = Utility.testoADb(request.getParameter("query"));

            String query = "select count(*) righe "
                    + "from sportelli a "
                    + "join sportelli_testi b "
                    + "on a.cod_sport = b.cod_sport "
                    + "where b.cod_lang='it' and (a.cod_sport like ? or a.nome_rup like ? or b.des_sport like ?)";
            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            st.setString(2, "%" + param + "%");
            st.setString(3, "%" + param + "%");

            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);

            //<RF - Aggiunta per spostamento parametri PEC, nuova gestione Connects ed integrazione PayER - Aggiunti parametri da template_oggetto_ricevuta in poi
            query = "select a.cod_sport cod_sport, a.nome_rup nome_rup, a.flg_attivo, a.cap, a.indirizzo, a.citta, a.prov, "
                    + "a.tel, a.fax, a.email, a.email_cert, a.flg_pu, a.flg_su, a.riversamento_automatico, b.des_sport des_sport, a.id_mail_server id_mail_server,"
                    + "a.id_protocollo id_protocollo,a.id_backoffice id_backoffice, a.template_oggetto_ricevuta template_oggetto_ricevuta, "
                    + "a.template_corpo_ricevuta template_corpo_ricevuta,a.template_nome_file_zip template_nome_file_zip, "
                    + "a.send_zip_file send_zip_file,a.send_single_files send_single_files, a.send_xml send_xml, a.send_signature send_signature, "
                    + "a.send_ricevuta_dopo_protocollazione send_ricevuta_dopo_protocollazione,a.send_ricevuta_dopo_invio_bo send_ricevuta_dopo_invio_bo, "
                    + "a.template_oggetto_mail_suap template_oggetto_mail_suap, a.ae_codice_utente, a.ae_codice_ente, a.ae_tipo_ufficio, a.ae_codice_ufficio, a.ae_tipologia_servizio, "
                    + "a.allegati_dimensione_max allegati_dimensione_max, a.allegati_dimensione_max_um allegati_dimensione_max_um, a.visualizza_stampa_modello_in_bianco visualizza_stampa_modello_in_bianco, "
                    + "a.visualizza_versione_pdf visualizza_versione_pdf, a.firma_on_line firma_on_line, a.firma_off_line firma_off_line, a.send_protocollo_param send_protocollo_param, "
                    + "a.flg_option_allegati flg_option_allegati, a.flg_oggetto_ricevuta flg_oggetto_ricevuta, a.flg_oggetto_riepilogo flg_oggetto_riepilogo, c.href href, d.tit_href tit_href "
                    + "from sportelli a "
                    + "join sportelli_testi b "                    
                    + "on a.cod_sport = b.cod_sport "
                    + "left join href c "
                    + "on a.href_oggetto=c.href "
                    + "left join href_testi d "
                    + "on c.href=d.href "
                    + "and d.cod_lang='it' "                    
                    + "where b.cod_lang='it' and (a.cod_sport like ? or a.nome_rup like ? or b.des_sport like ?) "
                    + "order by " + sort + " " + order + " limit ? , ?";
            //RF - Aggiunta per spostamento parametri PEC, nuova gestione Connects ed integrazione PayER - Aggiunti parametri da template_oggetto_ricevuta in poi>

            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            st.setString(2, "%" + param + "%");
            st.setString(3, "%" + param + "%");
            st.setInt(4, Integer.parseInt(offset));
            st.setInt(5, Integer.parseInt(size));
            rs = st.executeQuery();

            String queryLang = "select des_sport, cod_lang from sportelli_testi where cod_sport = ?";

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
                loopObj.put("riversamento_automatico", Utility.testoDaDb(rs.getString("riversamento_automatico")).equalsIgnoreCase("s") ? "true" : "false");
                loopObj.put("id_mail_server", Utility.testoDaDb(rs.getString("id_mail_server")));
                loopObj.put("id_protocollo", Utility.testoDaDbBase64(rs.getString("id_protocollo")));
                loopObj.put("id_backoffice", Utility.testoDaDbBase64(rs.getString("id_backoffice")));
                //<RF - Aggiunta per spostamento parametri PEC e nuova gestione Connects
                loopObj.put("template_oggetto_ricevuta", Utility.testoDaDbBase64(rs.getString("template_oggetto_ricevuta")));
                loopObj.put("template_corpo_ricevuta", Utility.testoDaDbBase64(rs.getString("template_corpo_ricevuta")));
                loopObj.put("template_nome_file_zip", Utility.testoDaDbBase64(rs.getString("template_nome_file_zip")));
                loopObj.put("send_zip_file", Utility.testoDaDb(rs.getString("send_zip_file")));
                loopObj.put("send_single_files", Utility.testoDaDb(rs.getString("send_single_files")));
                loopObj.put("send_xml", Utility.testoDaDb(rs.getString("send_xml")));
                loopObj.put("send_signature", Utility.testoDaDb(rs.getString("send_signature")));
                loopObj.put("send_ricevuta_dopo_protocollazione", Utility.testoDaDb(rs.getString("send_ricevuta_dopo_protocollazione")));
                loopObj.put("send_ricevuta_dopo_invio_bo", Utility.testoDaDb(rs.getString("send_ricevuta_dopo_invio_bo")));
                loopObj.put("template_oggetto_mail_suap", Utility.testoDaDbBase64(rs.getString("template_oggetto_mail_suap")));
                //RF - Aggiunta per spostamento parametri PEC e nuova gestione Connects>
                //<RF - Aggiunta per integrazione PayER
                loopObj.put("ae_codice_utente", Utility.testoDaDb(rs.getString("ae_codice_utente")));
                loopObj.put("ae_codice_ente", Utility.testoDaDb(rs.getString("ae_codice_ente")));
                loopObj.put("ae_tipo_ufficio", Utility.testoDaDb(rs.getString("ae_tipo_ufficio")));
                loopObj.put("ae_codice_ufficio", Utility.testoDaDb(rs.getString("ae_codice_ufficio")));
                loopObj.put("ae_tipologia_servizio", Utility.testoDaDb(rs.getString("ae_tipologia_servizio")));
                //RF - Aggiunta per integrazione PayER>
                loopObj.put("allegati_dimensione_max", Utility.testoDaDb(rs.getString("allegati_dimensione_max")));
                loopObj.put("allegati_dimensione_max_um", Utility.testoDaDb(rs.getString("allegati_dimensione_max_um")));
                loopObj.put("visualizza_stampa_modello_in_bianco", Utility.testoDaDb(rs.getString("visualizza_stampa_modello_in_bianco")).equalsIgnoreCase("s") ? "true" : "false");
                loopObj.put("visualizza_versione_pdf", Utility.testoDaDb(rs.getString("visualizza_versione_pdf")).equalsIgnoreCase("s") ? "true" : "false");
                loopObj.put("firma_on_line", Utility.testoDaDb(rs.getString("firma_on_line")).equalsIgnoreCase("s") ? "true" : "false");
                loopObj.put("firma_off_line", Utility.testoDaDb(rs.getString("firma_off_line")).equalsIgnoreCase("s") ? "true" : "false");
                loopObj.put("send_protocollo_param", Utility.testoDaDb(rs.getString("send_protocollo_param")));
                loopObj.put("flg_option_allegati", Utility.testoDaDb(rs.getString("flg_option_allegati")));
                loopObj.put("flg_oggetto_ricevuta", Utility.testoDaDb(rs.getString("flg_oggetto_ricevuta")));
                loopObj.put("flg_oggetto_riepilogo", Utility.testoDaDb(rs.getString("flg_oggetto_riepilogo")));
                loopObj.put("href", rs.getString("href"));
                loopObj.put("tit_href", Utility.testoDaDb(rs.getString("tit_href")));

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
            ret.put("totalCount", righe);
            ret.put("sportelli", riga);
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

        try {
            conn = Utility.getConnection();
            ret = new JSONObject();
            //<RF - Aggiunta per spostamento parametri PEC, nuova gestione Connects ed integrazione PayER - Aggiunti i parametri da template_oggetto_ricevuta in poi
            String query = "insert into sportelli (cod_sport,nome_rup,indirizzo,cap,citta,prov,tel,fax,email,email_cert,flg_attivo,flg_pu,flg_su,riversamento_automatico"
                    + ",id_mail_server,id_protocollo,id_backoffice,template_oggetto_ricevuta,template_corpo_ricevuta,template_nome_file_zip,send_zip_file,send_single_files,"
                    + "send_xml,send_signature,send_ricevuta_dopo_protocollazione,send_ricevuta_dopo_invio_bo,template_oggetto_mail_suap, ae_codice_utente, ae_codice_ente, "
                    + "ae_tipo_ufficio, ae_codice_ufficio, ae_tipologia_servizio, allegati_dimensione_max, allegati_dimensione_max_um, visualizza_stampa_modello_in_bianco, "
                    + "visualizza_versione_pdf, firma_on_line, firma_off_line, send_protocollo_param, flg_option_allegati, flg_oggetto_riepilogo, flg_oggetto_ricevuta, href_oggetto) "
                    + "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )";
            //RF - Aggiunta per spostamento parametri PEC, nuova gestione Connects ed integrazione PayER>
            st = conn.prepareStatement(query);
            st.setString(1, request.getParameter("cod_sport"));
            st.setString(2, request.getParameter("nome_rup"));
            st.setString(3, (request.getParameter("indirizzo") == null || request.getParameter("indirizzo").equals("") ? null : Utility.testoADb(request.getParameter("indirizzo"))));
            st.setString(4, (request.getParameter("cap") == null || request.getParameter("cap").equals("") ? null : Utility.testoADb(request.getParameter("cap"))));
            st.setString(5, (request.getParameter("citta") == null || request.getParameter("citta").equals("") ? null : Utility.testoADb(request.getParameter("citta"))));
            st.setString(6, (request.getParameter("prov") == null || request.getParameter("prov").equals("") ? null : Utility.testoADb(request.getParameter("prov"))));
            st.setString(7, (request.getParameter("tel") == null || request.getParameter("tel").equals("") ? null : Utility.testoADb(request.getParameter("tel"))));
            st.setString(8, (request.getParameter("fax") == null || request.getParameter("fax").equals("") ? null : Utility.testoADb(request.getParameter("fax"))));
            st.setString(9, (request.getParameter("email") == null || request.getParameter("email").equals("") ? null : Utility.testoADb(request.getParameter("email"))));
            st.setString(10, (request.getParameter("email_cert") == null || request.getParameter("email_cert").equals("") ? null : Utility.testoADb(request.getParameter("email_cert"))));
            st.setString(11, request.getParameter("flg_attivo_hidden"));
            st.setString(12, request.getParameter("flg_pu_hidden"));
            st.setString(13, request.getParameter("flg_su_hidden"));
            st.setString(14, (request.getParameter("riversamento_automatico") == null
                    || request.getParameter("riversamento_automatico").equals("") ? "N"
                    : "S"));
            st.setString(15, (request.getParameter("id_mail_server") == null || request.getParameter("id_mail_server").equals("") ? null : Utility.testoADb(request.getParameter("id_mail_server"))));
            st.setString(16, (request.getParameter("id_protocollo") == null || request.getParameter("id_protocollo").equals("") ? null : Utility.testoADbBase64(request.getParameter("id_protocollo"))));
            st.setString(17, (request.getParameter("id_backoffice") == null || request.getParameter("id_backoffice").equals("") ? null : Utility.testoADbBase64(request.getParameter("id_backoffice"))));
            //<RF - Aggiunta per spostamento parametri PEC e nuova gestione Connects
            st.setString(18, (request.getParameter("template_oggetto_ricevuta") == null
                    || request.getParameter("template_oggetto_ricevuta").equals("") ? null
                    : Utility.testoADbBase64(request.getParameter("template_oggetto_ricevuta"))));
            st.setString(19, (request.getParameter("template_corpo_ricevuta") == null
                    || request.getParameter("template_corpo_ricevuta").equals("") ? null
                    : Utility.testoADbBase64(request.getParameter("template_corpo_ricevuta"))));
            st.setString(20, (request.getParameter("template_nome_file_zip") == null
                    || request.getParameter("template_nome_file_zip").equals("") ? null
                    : Utility.testoADbBase64(request.getParameter("template_nome_file_zip"))));
            st.setString(21, (request.getParameter("send_zip_file") == null
                    || request.getParameter("send_zip_file").equals("") ? "false"
                    : "true"));
            st.setString(22, (request.getParameter("send_single_files") == null
                    || request.getParameter("send_single_files").equals("") ? "false"
                    : "true"));
            st.setString(23, (request.getParameter("send_xml") == null
                    || request.getParameter("send_xml").equals("") ? "false"
                    : "true"));
            st.setString(24, (request.getParameter("send_signature") == null
                    || request.getParameter("send_signature").equals("") ? "false"
                    : "true"));
            st.setString(25, (request.getParameter("send_ricevuta_dopo_protocollazione") == null
                    || request.getParameter("send_ricevuta_dopo_protocollazione").equals("") ? "false"
                    : "true"));
            st.setString(26, (request.getParameter("send_ricevuta_dopo_invio_bo") == null
                    || request.getParameter("send_ricevuta_dopo_invio_bo").equals("") ? "false"
                    : "true"));
            st.setString(27, (request.getParameter("template_oggetto_mail_suap") == null
                    || request.getParameter("template_oggetto_mail_suap").equals("") ? null
                    : Utility.testoADbBase64(request.getParameter("template_oggetto_mail_suap"))));
            //RF - Aggiunta per spostamento parametri PEC e nuova gestione Connects>
            //<RF - Aggiunta per integrazione PayER
            st.setString(28, (request.getParameter("ae_codice_utente") == null
                    || request.getParameter("ae_codice_utente").equals("") ? null
                    : Utility.testoADb(request.getParameter("ae_codice_utente"))));
            st.setString(29, (request.getParameter("ae_codice_ente") == null
                    || request.getParameter("ae_codice_ente").equals("") ? null
                    : Utility.testoADb(request.getParameter("ae_codice_ente"))));
            st.setString(30, (request.getParameter("ae_tipo_ufficio") == null
                    || request.getParameter("ae_tipo_ufficio").equals("") ? null
                    : Utility.testoADb(request.getParameter("ae_tipo_ufficio"))));
            st.setString(31, (request.getParameter("ae_codice_ufficio") == null
                    || request.getParameter("ae_codice_ufficio").equals("") ? null
                    : Utility.testoADb(request.getParameter("ae_codice_ufficio"))));
            st.setString(32, (request.getParameter("ae_tipologia_servizio") == null
                    || request.getParameter("ae_tipologia_servizio").equals("") ? null
                    : Utility.testoADb(request.getParameter("ae_tipologia_servizio"))));
            //RF - Aggiunta per integrazione PayER>
            st.setString(33, (request.getParameter("allegati_dimensione_max") == null
                    || request.getParameter("allegati_dimensione_max").equals("") ? "0"
                    : Utility.testoADb(request.getParameter("allegati_dimensione_max"))));
            st.setString(34, Utility.testoADb(request.getParameter("allegati_dimensione_max_um_hidden")));

            st.setString(35, (request.getParameter("visualizza_stampa_modello_in_bianco") == null
                    || request.getParameter("visualizza_stampa_modello_in_bianco").equals("") ? "N"
                    : "S"));
            st.setString(36, (request.getParameter("visualizza_versione_pdf") == null
                    || request.getParameter("visualizza_versione_pdf").equals("") ? "N"
                    : "S"));
            st.setString(37, (request.getParameter("firma_on_line") == null
                    || request.getParameter("firma_on_line").equals("") ? "N"
                    : "S"));
            st.setString(38, (request.getParameter("firma_off_line") == null
                    || request.getParameter("firma_off_line").equals("") ? "N"
                    : "S"));
            st.setString(39, (request.getParameter("send_protocollo_param") == null
                    || request.getParameter("send_protocollo_param").equals("") ? "false"
                    : "true"));
            st.setString(40, request.getParameter("flg_option_allegati_hidden"));
            st.setString(41, request.getParameter("flg_oggetto_riepilogo_hidden"));
            st.setString(42, request.getParameter("flg_oggetto_ricevuta_hidden"));
            if (request.getParameter("href") != null && !request.getParameter("href").equals("")) {
                st.setString(43, request.getParameter("href"));
            } else {
                st.setString(43, null);
            }
            st.executeUpdate();
            st.close();

            query = "insert into sportelli_testi (cod_sport,cod_lang,des_sport) "
                    + "values (?,?,? )";
            Set<String> parmKey = request.getParameterMap().keySet();
            for (String key : parmKey) {
                String[] parSplit = key.split("des_sport_");
                if (parSplit.length > 1 && key.contains("des_sport_")) {
                    String cod_lang = parSplit[1];
                    if (cod_lang != null) {
                        st = conn.prepareStatement(query);
                        st.setString(1, request.getParameter("cod_sport"));
                        st.setString(2, cod_lang);
                        st.setString(3, Utility.testoADb(request.getParameter("des_sport_" + cod_lang)));
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

        try {
            conn = Utility.getConnection();
            ret = new JSONObject();
            //<RF - Aggiunta per spostamento parametri PEC, nuova gestione Connects ed integrazione PayER - Aggiunti parametri da template_oggetto_ricevuta in poi
            String query = "update sportelli "
                    + "set nome_rup = ?,"
                    + "indirizzo = ?, "
                    + "cap=?, "
                    + "citta= ?, "
                    + "prov=?, "
                    + "tel=?, "
                    + "fax=?, "
                    + "email=?, "
                    + "email_cert=?, "
                    + "flg_attivo=?, "
                    + "flg_pu=?, "
                    + "flg_su=?, "
                    + "riversamento_automatico=?, "
                    + "id_mail_server =?,"
                    + "id_protocollo=?, "
                    + "id_backoffice=?, "
                    + "template_oggetto_ricevuta=?, "
                    + "template_corpo_ricevuta=?, "
                    + "template_nome_file_zip=?, "
                    + "send_zip_file=?, "
                    + "send_single_files=?, "
                    + "send_xml=?, "
                    + "send_signature=?, "
                    + "send_ricevuta_dopo_protocollazione=?, "
                    + "send_ricevuta_dopo_invio_bo=?, "
                    + "template_oggetto_mail_suap=?, "
                    + "ae_codice_utente=?, "
                    + "ae_codice_ente=?, "
                    + "ae_tipo_ufficio=?, "
                    + "ae_codice_ufficio=?, "
                    + "ae_tipologia_servizio=?, "
                    + "allegati_dimensione_max=?, "
                    + "allegati_dimensione_max_um=?, "
                    + "visualizza_stampa_modello_in_bianco=?, "
                    + "visualizza_versione_pdf=?, "
                    + "firma_on_line=?, "
                    + "firma_off_line=?, "
                    + "send_protocollo_param=?, "
                    + "flg_option_allegati=?, "
                    + "flg_oggetto_riepilogo=?, "
                    + "flg_oggetto_ricevuta=?, "
                    + "href_oggetto=? "
                    + "where cod_sport =?";
            //RF - Aggiunta per spostamento parametri PEC, nuova gestione Connects ed integrazione PayER>
            st = conn.prepareStatement(query);
            st.setString(1, request.getParameter("nome_rup"));
            st.setString(2, (request.getParameter("indirizzo") == null || request.getParameter("indirizzo").equals("") ? null : Utility.testoADb(request.getParameter("indirizzo"))));
            st.setString(3, (request.getParameter("cap") == null || request.getParameter("cap").equals("") ? null : Utility.testoADb(request.getParameter("cap"))));
            st.setString(4, (request.getParameter("citta") == null || request.getParameter("citta").equals("") ? null : Utility.testoADb(request.getParameter("citta"))));
            st.setString(5, (request.getParameter("prov") == null || request.getParameter("prov").equals("") ? null : Utility.testoADb(request.getParameter("prov"))));
            st.setString(6, (request.getParameter("tel") == null || request.getParameter("tel").equals("") ? null : Utility.testoADb(request.getParameter("tel"))));
            st.setString(7, (request.getParameter("fax") == null || request.getParameter("fax").equals("") ? null : Utility.testoADb(request.getParameter("fax"))));
            st.setString(8, (request.getParameter("email") == null || request.getParameter("email").equals("") ? null : Utility.testoADb(request.getParameter("email"))));
            st.setString(9, (request.getParameter("email_cert") == null || request.getParameter("email_cert").equals("") ? null : Utility.testoADb(request.getParameter("email_cert"))));
            st.setString(10, request.getParameter("flg_attivo_hidden"));
            st.setString(11, request.getParameter("flg_pu_hidden"));
            st.setString(12, request.getParameter("flg_su_hidden"));
            st.setString(13, (request.getParameter("riversamento_automatico") == null
                    || request.getParameter("riversamento_automatico").equals("") ? "N"
                    : "S"));
            st.setString(14, (request.getParameter("id_mail_server") == null || request.getParameter("id_mail_server").equals("") ? null : Utility.testoADb(request.getParameter("id_mail_server"))));
            st.setString(15, (request.getParameter("id_protocollo") == null || request.getParameter("id_protocollo").equals("") ? null : Utility.testoADbBase64(request.getParameter("id_protocollo"))));
            st.setString(16, (request.getParameter("id_backoffice") == null || request.getParameter("id_backoffice").equals("") ? null : Utility.testoADbBase64(request.getParameter("id_backoffice"))));
            //<RF - Aggiunta per spostamento parametri PEC e nuova gestione Connects
            st.setString(17, (request.getParameter("template_oggetto_ricevuta") == null
                    || request.getParameter("template_oggetto_ricevuta").equals("") ? null
                    : Utility.testoADbBase64(request.getParameter("template_oggetto_ricevuta"))));
            st.setString(18, (request.getParameter("template_corpo_ricevuta") == null
                    || request.getParameter("template_corpo_ricevuta").equals("") ? null
                    : Utility.testoADbBase64(request.getParameter("template_corpo_ricevuta"))));
            st.setString(19, (request.getParameter("template_nome_file_zip") == null
                    || request.getParameter("template_nome_file_zip").equals("") ? null
                    : Utility.testoADbBase64(request.getParameter("template_nome_file_zip"))));
            st.setString(20, (request.getParameter("send_zip_file") == null
                    || request.getParameter("send_zip_file").equals("") ? "false"
                    : "true"));
            st.setString(21, (request.getParameter("send_single_files") == null
                    || request.getParameter("send_single_files").equals("") ? "false"
                    : "true"));
            st.setString(22, (request.getParameter("send_xml") == null
                    || request.getParameter("send_xml").equals("") ? "false"
                    : "true"));
            st.setString(23, (request.getParameter("send_signature") == null
                    || request.getParameter("send_signature").equals("") ? "false"
                    : "true"));
            st.setString(24, (request.getParameter("send_ricevuta_dopo_protocollazione") == null
                    || request.getParameter("send_ricevuta_dopo_protocollazione").equals("") ? "false"
                    : "true"));
            st.setString(25, (request.getParameter("send_ricevuta_dopo_invio_bo") == null
                    || request.getParameter("send_ricevuta_dopo_invio_bo").equals("") ? "false"
                    : "true"));
            st.setString(26, (request.getParameter("template_oggetto_mail_suap") == null
                    || request.getParameter("template_oggetto_mail_suap").equals("") ? null
                    : Utility.testoADbBase64(request.getParameter("template_oggetto_mail_suap"))));
            //RF - Aggiunta per spostamento parametri PEC e nuova gestione Connects>
            //<RF - Aggiunta per integrazione PayER
            st.setString(27, (request.getParameter("ae_codice_utente") == null
                    || request.getParameter("ae_codice_utente").equals("") ? null
                    : Utility.testoADb(request.getParameter("ae_codice_utente"))));
            st.setString(28, (request.getParameter("ae_codice_ente") == null
                    || request.getParameter("ae_codice_ente").equals("") ? null
                    : Utility.testoADb(request.getParameter("ae_codice_ente"))));
            st.setString(29, (request.getParameter("ae_tipo_ufficio") == null
                    || request.getParameter("ae_tipo_ufficio").equals("") ? null
                    : Utility.testoADb(request.getParameter("ae_tipo_ufficio"))));
            st.setString(30, (request.getParameter("ae_codice_ufficio") == null
                    || request.getParameter("ae_codice_ufficio").equals("") ? null
                    : Utility.testoADb(request.getParameter("ae_codice_ufficio"))));
            st.setString(31, (request.getParameter("ae_tipologia_servizio") == null
                    || request.getParameter("ae_tipologia_servizio").equals("") ? null
                    : Utility.testoADb(request.getParameter("ae_tipologia_servizio"))));
            //RF - Aggiunta per integrazione PayER>
            st.setString(32, (request.getParameter("allegati_dimensione_max") == null
                    || request.getParameter("allegati_dimensione_max").equals("") ? "0"
                    : Utility.testoADb(request.getParameter("allegati_dimensione_max"))));
            st.setString(33, Utility.testoADb(request.getParameter("allegati_dimensione_max_um_hidden")));

            st.setString(34, (request.getParameter("visualizza_stampa_modello_in_bianco") == null
                    || request.getParameter("visualizza_stampa_modello_in_bianco").equals("") ? "N"
                    : "S"));
            st.setString(35, (request.getParameter("visualizza_versione_pdf") == null
                    || request.getParameter("visualizza_versione_pdf").equals("") ? "N"
                    : "S"));
            st.setString(36, (request.getParameter("firma_on_line") == null
                    || request.getParameter("firma_on_line").equals("") ? "N"
                    : "S"));
            st.setString(37, (request.getParameter("firma_off_line") == null
                    || request.getParameter("firma_off_line").equals("") ? "N"
                    : "S"));
            st.setString(38, (request.getParameter("send_protocollo_param") == null
                    || request.getParameter("send_protocollo_param").equals("") ? "false"
                    : "true"));
            st.setString(39, request.getParameter("flg_option_allegati_hidden"));
            st.setString(40, request.getParameter("flg_oggetto_riepilogo_hidden"));
            st.setString(41, request.getParameter("flg_oggetto_ricevuta_hidden"));            
            if (request.getParameter("href") != null && !request.getParameter("href").equals("")) {
                st.setString(42, request.getParameter("href"));
            } else {
                st.setString(42, null);
            }
            st.setString(43, request.getParameter("cod_sport"));
            st.executeUpdate();
            Utility.chiusuraJdbc(st);
            String querySelect = "select * from sportelli_testi where cod_sport = ? and cod_lang = ?";
            String queryInsert = "insert into sportelli_testi (cod_sport,cod_lang,des_sport) values (?,?,? )";


            query = "update sportelli_testi "
                    + "set des_sport=? "
                    + "where cod_sport =? and cod_lang=?";
            Set<String> parmKey = request.getParameterMap().keySet();
            for (String key : parmKey) {
                String[] parSplit = key.split("des_sport_");
                if (parSplit.length > 1 && key.contains("des_sport_")) {
                    String cod_lang = parSplit[1];
                    if (cod_lang != null) {
                        stRead = conn.prepareStatement(querySelect);
                        stRead.setString(1, request.getParameter("cod_sport"));
                        stRead.setString(2, cod_lang);
                        rsRead = stRead.executeQuery();
                        if (rsRead.next()) {
                            st = conn.prepareStatement(query);
                            st.setString(1, Utility.testoADb(request.getParameter(key)));
                            st.setString(2, request.getParameter("cod_sport"));
                            st.setString(3, cod_lang);
                            st.executeUpdate();
                        } else {
                            st = conn.prepareStatement(queryInsert);
                            st.setString(1, request.getParameter("cod_sport"));
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
            ret.put("success", "Aggiornamento effettuato");
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

    public boolean controlloCancella(String key, Connection conn) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        boolean controllo = true;
        int righe = 0;
        try {
            String query = "select count(*) righe from relazioni_enti where cod_sport=? "
                    + "union "
                    + "select count(*) righe from templates where cod_sport=? "
                    + "union "
                    + "select count(*) righe from templates_immagini where nome_immagine in (select nome_immagine from templates_immagini_immagini where cod_sport=?) "
                    + "union "
                    + "select count(*) righe from templates_immagini_immagini where cod_sport=? "
                    + "union "
                    + "select count(*) righe from templates_vari where nome_template in (select nome_template from templates_vari_risorse where cod_sport=?) "
                    + "union "
                    + "select count(*) righe from templates_vari_risorse where cod_sport=? ";

            st = conn.prepareStatement(query);
            st.setString(1, key);
            st.setString(2, key);
            st.setString(3, key);
            st.setString(4, key);
            st.setString(5, key);
            st.setString(6, key);
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
        boolean controllo = true;
        try {
            conn = Utility.getConnection();
            ret = new JSONObject();
            controllo = controlloCancella(request.getParameter("cod_sport"), conn);
            if (controllo) {
                String query = "delete from sportelli where cod_sport= ?";
                st = conn.prepareStatement(query);
                st.setString(1, request.getParameter("cod_sport"));
                st.executeUpdate();
                Utility.chiusuraJdbc(rs);
                Utility.chiusuraJdbc(st);

                query = "delete from sportelli_testi where cod_sport= ?";
                st = conn.prepareStatement(query);
                st.setString(1, request.getParameter("cod_sport"));
                st.executeUpdate();
                Utility.chiusuraJdbc(rs);
                Utility.chiusuraJdbc(st);

                query = "delete from sportelli_param_prot_pec where ref_sport= ?";
                st = conn.prepareStatement(query);
                st.setString(1, request.getParameter("cod_sport"));
                st.executeUpdate();
                Utility.chiusuraJdbc(rs);
                Utility.chiusuraJdbc(st);

                query = "delete from sportelli_param_prot_ws where ref_sport= ?";
                st = conn.prepareStatement(query);
                st.setString(1, request.getParameter("cod_sport"));
                st.executeUpdate();
                Utility.chiusuraJdbc(rs);
                Utility.chiusuraJdbc(st);
                ret.put("success", "Cancellazione effettuata");
            } else {
                ret.put("failure", "Sportello in uso");
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
