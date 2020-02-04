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
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;

import it.people.dbm.model.TemplateModel;
import it.people.dbm.model.TemplateModelFile;
import it.people.dbm.utility.Utility;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.LoggerFactory;
/**
 *
 * @author Piergiorgio
 */
public class Template {

    private static Logger log = LoggerFactory.getLogger(Template.class);

    public JSONObject action(HttpServletRequest request) throws Exception {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        PreparedStatement stLang = null;
        ResultSet rsLang = null;
        JSONObject ret = null;
        int righe = 0;
        String query = null;
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

            query = "select count(*) righe from "
                    + "(select '' cod_sport, 'Default' des_sport,'' cod_com ,'Default' des_ente, '' cod_servizio, '' nome_servizio, '' cod_proc, 'Default' tit_proc,  nome_file "
                    + "from templates "
                    + "where cod_sport='' "
                    + "and cod_com='' "
                    + "and tipo='<tipo><bookmark></bookmark><procedimento></procedimento></tipo>' "
                    + "and cod_lang='it' "
                    + "union "
                    + "select a.cod_sport, c.des_sport,'' cod_com, 'Default' des_ente, '' cod_servizio, '' nome_servizio,'' cod_proc, 'Deafult' tit_proc, a.nome_file "
                    + "from templates a "
                    + "join sportelli b "
                    + "on a.cod_sport=b.cod_sport "
                    + "join sportelli_testi c "
                    + "on a.cod_sport=c.cod_sport "
                    + "and c.cod_lang=a.cod_lang "
                    + "where a.cod_sport<>'' "
                    + "and a.cod_com='' "
                    + "and a.tipo='<tipo><bookmark></bookmark><procedimento></procedimento></tipo>' "
                    + "and a.cod_lang='it' "
                    + "union "
                    + "select a.cod_sport, c.des_sport, a.cod_com, e.des_ente, '' cod_servizio, '' nome_servizio,'' cod_proc, 'Default' tit_proc, nome_file "
                    + "from templates a "
                    + "join sportelli b "
                    + "on a.cod_sport=b.cod_sport "
                    + "join sportelli_testi c "
                    + "on a.cod_sport=c.cod_sport "
                    + "and c.cod_lang=a.cod_lang "
                    + "join enti_comuni d "
                    + "on a.cod_com=d.cod_ente "
                    + "join enti_comuni_testi e "
                    + "on a.cod_com=e.cod_ente  "
                    + "and c.cod_lang=e.cod_lang "
                    + "where a.cod_sport<>'' "
                    + "and a.cod_com<>'' "
                    + "and a.tipo='<tipo><bookmark></bookmark><procedimento></procedimento></tipo>' "
                    + "and a.cod_lang='it' "
                    + "union "
                    + "select a.cod_sport, 'Default' des_sport, a.cod_com, 'Default' des_ente, '' cod_servizio, '' nome_servizio, "
                    + "substring(a.tipo, instr(a.tipo,'<procedimento>') + length('<procedimento>'), "
                    + "instr(a.tipo,'</procedimento>') - instr(a.tipo,'<procedimento>') - length('<procedimento>')) cod_proc, c.tit_proc, nome_file "
                    + "from templates a "
                    + "join procedimenti b "
                    + "on substring(a.tipo,instr(a.tipo,'<procedimento>') + length('<procedimento>'), "
                    + "instr(a.tipo,'</procedimento>') - instr(a.tipo,'<procedimento>') - length('<procedimento>')) = b.cod_proc "
                    + "join procedimenti_testi c "
                    + "on b.cod_proc=c.cod_proc "
                    + "and c.cod_lang=a.cod_lang "
                    + "where a.cod_sport = '' "
                    + "and a.cod_com='' "
                    + "and substring(a.tipo, instr(a.tipo,'<procedimento>') + length('<procedimento>'), "
                    + "instr(a.tipo,'</procedimento>') - instr(a.tipo,'<procedimento>') - length('</procedimento>'))<>'' "
                    + "and a.cod_lang='it' "
                    + "union "
                    + "select a.cod_sport, e.des_sport, a.cod_com, 'Default' des_ente, '' cod_servizio, '' nome_servizio, "
                    + "substring(a.tipo, instr(a.tipo,'<procedimento>') + length('<procedimento>'), "
                    + "instr(a.tipo,'</procedimento>') - instr(a.tipo,'<procedimento>') - length('<procedimento>')) cod_proc, c.tit_proc, nome_file "
                    + "from templates a "
                    + "join procedimenti b "
                    + "on substring(a.tipo, instr(a.tipo,'<procedimento>') + length('<procedimento>'), "
                    + "instr(a.tipo,'</procedimento>') - instr(a.tipo,'<procedimento>') - length('<procedimento>')) = b.cod_proc "
                    + "join procedimenti_testi c "
                    + "on b.cod_proc=c.cod_proc "
                    + "and c.cod_lang=a.cod_lang "
                    + "join sportelli d "
                    + "on a.cod_sport = d.cod_sport "
                    + "join sportelli_testi e "
                    + "on a.cod_sport=e.cod_sport "
                    + "and c.cod_lang=e.cod_lang "
                    + "where a.cod_sport <> '' "
                    + "and a.cod_com='' "
                    + "and substring(a.tipo, instr(a.tipo,'<procedimento>') + length('<procedimento>'), "
                    + "instr(a.tipo,'</procedimento>') - instr(a.tipo,'<procedimento>') - length('</procedimento>'))<>'' "
                    + "and a.cod_lang='it' "
                    + "union "
                    + "select a.cod_sport, e.des_sport, a.cod_com, g.des_ente, '' cod_servizio, '' nome_servizio, "
                    + "substring(a.tipo, instr(a.tipo,'<procedimento>') + length('<procedimento>'), "
                    + "instr(a.tipo,'</procedimento>') - instr(a.tipo,'<procedimento>') - length('<procedimento>')) cod_proc, c.tit_proc, nome_file "
                    + "from templates a "
                    + "join procedimenti b "
                    + "on substring(a.tipo, instr(a.tipo,'<procedimento>') + length('<procedimento>'), "
                    + "instr(a.tipo,'</procedimento>') - instr(a.tipo,'<procedimento>') - length('<procedimento>')) = b.cod_proc "
                    + "join procedimenti_testi c "
                    + "on b.cod_proc=c.cod_proc "
                    + "and c.cod_lang=a.cod_lang "
                    + "join sportelli d "
                    + "on a.cod_sport = d.cod_sport "
                    + "join sportelli_testi e "
                    + "on a.cod_sport=e.cod_sport "
                    + "and c.cod_lang=e.cod_lang "
                    + "join enti_comuni f "
                    + "on a.cod_com=f.cod_ente "
                    + "join enti_comuni_testi g "
                    + "on a.cod_com=g.cod_ente "
                    + "and g.cod_lang=c.cod_lang "
                    + "where a.cod_sport <> '' "
                    + "and a.cod_com <> '' "
                    + "and substring(a.tipo, instr(a.tipo,'<procedimento>') + length('<procedimento>'), "
                    + "instr(a.tipo,'</procedimento>') - instr(a.tipo,'<procedimento>') - length('</procedimento>'))<>'' "
                    + "and a.cod_lang='it' "
                    + "union "
                    + "select '' cod_sport, 'Default' des_sport,'' cod_com ,'Default' des_ente, a.cod_servizio, b.nome_servizio,'' cod_proc, 'Default' tit_proc,c.nome_file "
                    + "from servizi a "
                    + "join servizi_testi b on a.cod_servizio=b.cod_servizio and a.cod_com=b.cod_com and a.cod_eve_vita=b.cod_eve_vita "
                    + "join templates c on c.tipo like concat('%<bookmark>',a.cod_servizio,'</bookmark>%')and b.cod_lang=c.cod_lang "
                    + "where c.cod_lang='it' ) p "
                    + "where (cod_sport like ? or cod_com like ? or cod_proc like ? or nome_file like ? or des_sport like ? or des_ente like ? or cod_servizio like ? or nome_servizio like ?) ";

            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            st.setString(2, "%" + param + "%");
            st.setString(3, "%" + param + "%");
            st.setString(4, "%" + param + "%");
            st.setString(5, "%" + param + "%");
            st.setString(6, "%" + param + "%");
            st.setString(7, "%" + param + "%");
            st.setString(8, "%" + param + "%");
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            query = "select cod_sport, des_sport, cod_com , des_ente, cod_servizio, nome_servizio, cod_proc, tit_proc,  nome_file, tipo from "
                    + "(select '' cod_sport, 'Default' des_sport,'' cod_com ,'Default' des_ente, '' cod_servizio, '' nome_servizio, '' cod_proc, 'Default' tit_proc,  nome_file, tipo "
                    + "from templates "
                    + "where cod_sport='' "
                    + "and cod_com='' "
                    + "and tipo='<tipo><bookmark></bookmark><procedimento></procedimento></tipo>' "
                    + "and cod_lang='it' "
                    + "union "
                    + "select a.cod_sport, c.des_sport,'' cod_com, 'Default' des_ente, '' cod_servizio, '' nome_servizio,'' cod_proc, 'Deafult' tit_proc, a.nome_file, a.tipo "
                    + "from templates a "
                    + "join sportelli b "
                    + "on a.cod_sport=b.cod_sport "
                    + "join sportelli_testi c "
                    + "on a.cod_sport=c.cod_sport "
                    + "and c.cod_lang=a.cod_lang "
                    + "where a.cod_sport<>'' "
                    + "and a.cod_com='' "
                    + "and a.tipo='<tipo><bookmark></bookmark><procedimento></procedimento></tipo>' "
                    + "and a.cod_lang='it' "
                    + "union "
                    + "select a.cod_sport, c.des_sport, a.cod_com, e.des_ente, '' cod_servizio, '' nome_servizio,'' cod_proc, 'Default' tit_proc, a.nome_file, a.tipo "
                    + "from templates a "
                    + "join sportelli b "
                    + "on a.cod_sport=b.cod_sport "
                    + "join sportelli_testi c "
                    + "on a.cod_sport=c.cod_sport "
                    + "and c.cod_lang=a.cod_lang "
                    + "join enti_comuni d "
                    + "on a.cod_com=d.cod_ente "
                    + "join enti_comuni_testi e "
                    + "on a.cod_com=e.cod_ente  "
                    + "and c.cod_lang=e.cod_lang "
                    + "where a.cod_sport<>'' "
                    + "and a.cod_com<>'' "
                    + "and a.tipo='<tipo><bookmark></bookmark><procedimento></procedimento></tipo>' "
                    + "and a.cod_lang='it' "
                    + "union "
                    + "select a.cod_sport, 'Default' des_sport, a.cod_com, 'Default' des_ente, '' cod_servizio, '' nome_servizio, "
                    + "substring(a.tipo, instr(a.tipo,'<procedimento>') + length('<procedimento>'), "
                    + "instr(a.tipo,'</procedimento>') - instr(a.tipo,'<procedimento>') - length('<procedimento>')) cod_proc, c.tit_proc, a.nome_file, a.tipo "
                    + "from templates a "
                    + "join procedimenti b "
                    + "on substring(a.tipo,instr(a.tipo,'<procedimento>') + length('<procedimento>'), "
                    + "instr(a.tipo,'</procedimento>') - instr(a.tipo,'<procedimento>') - length('<procedimento>')) = b.cod_proc "
                    + "join procedimenti_testi c "
                    + "on b.cod_proc=c.cod_proc "
                    + "and c.cod_lang=a.cod_lang "
                    + "where a.cod_sport = '' "
                    + "and a.cod_com='' "
                    + "and a.cod_lang='it' "
                    + "and substring(a.tipo, instr(a.tipo,'<procedimento>') + length('<procedimento>'), "
                    + "instr(a.tipo,'</procedimento>') - instr(a.tipo,'<procedimento>') - length('</procedimento>'))<>'' "
                    + "union "
                    + "select a.cod_sport, e.des_sport, a.cod_com, 'Default' des_ente, '' cod_servizio, '' nome_servizio, "
                    + "substring(a.tipo, instr(a.tipo,'<procedimento>') + length('<procedimento>'), "
                    + "instr(a.tipo,'</procedimento>') - instr(a.tipo,'<procedimento>') - length('<procedimento>')) cod_proc, c.tit_proc, a.nome_file, a.tipo "
                    + "from templates a "
                    + "join procedimenti b "
                    + "on substring(a.tipo, instr(a.tipo,'<procedimento>') + length('<procedimento>'), "
                    + "instr(a.tipo,'</procedimento>') - instr(a.tipo,'<procedimento>') - length('<procedimento>')) = b.cod_proc "
                    + "join procedimenti_testi c "
                    + "on b.cod_proc=c.cod_proc "
                    + "and c.cod_lang=a.cod_lang "
                    + "join sportelli d "
                    + "on a.cod_sport = d.cod_sport "
                    + "join sportelli_testi e "
                    + "on a.cod_sport=e.cod_sport "
                    + "and c.cod_lang=e.cod_lang "
                    + "where a.cod_sport <> '' "
                    + "and a.cod_com='' "
                    + "and substring(a.tipo, instr(a.tipo,'<procedimento>') + length('<procedimento>'), "
                    + "instr(a.tipo,'</procedimento>') - instr(a.tipo,'<procedimento>') - length('</procedimento>'))<>'' "
                    + "and a.cod_lang='it' "
                    + "union "
                    + "select a.cod_sport, e.des_sport, a.cod_com, g.des_ente, '' cod_servizio, '' nome_servizio, "
                    + "substring(a.tipo, instr(a.tipo,'<procedimento>') + length('<procedimento>'), "
                    + "instr(a.tipo,'</procedimento>') - instr(a.tipo,'<procedimento>') - length('<procedimento>')) cod_proc, c.tit_proc, a.nome_file, a.tipo "
                    + "from templates a "
                    + "join procedimenti b "
                    + "on substring(a.tipo, instr(a.tipo,'<procedimento>') + length('<procedimento>'), "
                    + "instr(a.tipo,'</procedimento>') - instr(a.tipo,'<procedimento>') - length('<procedimento>')) = b.cod_proc "
                    + "join procedimenti_testi c "
                    + "on b.cod_proc=c.cod_proc "
                    + "and c.cod_lang=a.cod_lang "
                    + "join sportelli d "
                    + "on a.cod_sport = d.cod_sport "
                    + "join sportelli_testi e "
                    + "on a.cod_sport=e.cod_sport "
                    + "and c.cod_lang=e.cod_lang "
                    + "join enti_comuni f "
                    + "on a.cod_com=f.cod_ente "
                    + "join enti_comuni_testi g "
                    + "on a.cod_com=g.cod_ente "
                    + "and g.cod_lang=c.cod_lang "
                    + "where a.cod_sport <> '' "
                    + "and a.cod_com <> '' "
                    + "and substring(a.tipo, instr(a.tipo,'<procedimento>') + length('<procedimento>'), "
                    + "instr(a.tipo,'</procedimento>') - instr(a.tipo,'<procedimento>') - length('</procedimento>'))<>'' "
                    + "and a.cod_lang='it' "
                    + "union "
                    + "select '' cod_sport, 'Default' des_sport,'' cod_com ,'Default' des_ente, a.cod_servizio, b.nome_servizio,'' cod_proc, 'Default' tit_proc,c.nome_file, c.tipo "
                    + "from servizi a "
                    + "join servizi_testi b on a.cod_servizio=b.cod_servizio and a.cod_com=b.cod_com and a.cod_eve_vita=b.cod_eve_vita  "
                    + "join templates c on c.tipo like concat('%<bookmark>',a.cod_servizio,'</bookmark>%') and b.cod_lang=c.cod_lang where c.cod_lang='it' ) p "
                    + "where (cod_sport like ? or cod_com like ? or cod_proc like ? or nome_file like ? or des_sport like ? or des_ente like ? or cod_servizio like ? or nome_servizio like ?) "
                    + "order by " + sort + " " + order + " limit ? , ?";

            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            st.setString(2, "%" + param + "%");
            st.setString(3, "%" + param + "%");
            st.setString(4, "%" + param + "%");
            st.setString(5, "%" + param + "%");
            st.setString(6, "%" + param + "%");
            st.setString(7, "%" + param + "%");
            st.setString(8, "%" + param + "%");
            st.setInt(9, Integer.parseInt(offset));
            st.setInt(10, Integer.parseInt(size));

            String queryLang = "select nome_file, cod_lang from templates where cod_sport = ? and cod_com = ? and tipo=?";

            rs = st.executeQuery();

            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_sport", rs.getString("cod_sport"));
                loopObj.put("des_sport", Utility.testoDaDb(rs.getString("des_sport")));
                loopObj.put("cod_com", rs.getString("cod_com"));
                loopObj.put("des_ente", Utility.testoDaDb(rs.getString("des_ente")));
                loopObj.put("cod_servizio", rs.getString("cod_servizio"));
                loopObj.put("nome_servizio", Utility.testoDaDb(rs.getString("nome_servizio")));
                loopObj.put("cod_proc", rs.getString("cod_proc"));
                loopObj.put("tit_proc", Utility.testoDaDb(rs.getString("tit_proc")));
                stLang = conn.prepareStatement(queryLang);
                stLang.setString(1, rs.getString("cod_sport"));
                stLang.setString(2, rs.getString("cod_com"));
                stLang.setString(3, rs.getString("tipo"));
                rsLang = stLang.executeQuery();
                while (rsLang.next()) {
//                    loopObj.put("nome_file_" + rsLang.getString("cod_lang"), Utility.testoDaDb(rsLang.getString("nome_file")));
                    if (!rs.getString("nome_file").equals("")) {
                        loopObj.put("nome_file_" + rsLang.getString("cod_lang"), "<a href=\"ScaricaFile?tipo=template&codSport=" + rs.getString("cod_sport") + "&codCom=" + rs.getString("cod_com") + "&codProc=" + rs.getString("cod_proc") + "&codServizio=" + rs.getString("cod_servizio") + "&codLang=" + rsLang.getString("cod_lang") + "\" target=\"_blank\" alt=\"" + rsLang.getString("nome_file") + "\">" + rsLang.getString("nome_file") + "</a>");
                    }
                }
                Utility.chiusuraJdbc(rsLang);
                Utility.chiusuraJdbc(stLang);
                riga.add(loopObj);

            }
            ret = new JSONObject();
            ret.put("totalCount", righe);
            ret.put("template", riga);
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

    public JSONObject aggiorna(TemplateModel tm) throws Exception {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        PreparedStatement stRead = null;
        ResultSet rsRead = null;
        JSONObject ret = null;
        String query;
        try {
            ret = new JSONObject();
            conn = Utility.getConnection();
            String tipo = "<tipo><bookmark>" + (tm.getCodServizio() == null ? "" : tm.getCodServizio()) + "</bookmark><procedimento>" + (tm.getCodProc() == null ? "" : tm.getCodProc()) + "</procedimento></tipo>";

            String querySelect = "select * from templates where cod_sport = ? and  cod_com = ? and tipo = ? and cod_lang = ?";
            String queryInsert = "insert into templates (cod_sport,cod_com,tipo,nome_file,template,cod_lang) values (?,?,?,?,?,?)";

            query = "update templates "
                    + "set nome_file=?, "
                    + "template=? "
                    + "where cod_sport=? and cod_com=? and tipo=? and cod_lang=?";
            List<TemplateModelFile> listaFile = tm.getListaFile();

            for (TemplateModelFile tmf : listaFile) {
                stRead = conn.prepareStatement(querySelect);
                stRead.setString(1, tm.getCodSport());
                stRead.setString(2, tm.getCodCom());
                stRead.setString(3, tipo);
                stRead.setString(4, tmf.getLingua());
                rsRead = stRead.executeQuery();
                if (rsRead.next()) {
                    st = conn.prepareStatement(query);
                    st.setString(1, tmf.getNomeFile());
                    st.setString(2, new String(tmf.getTemplate()));
                    st.setString(3, tm.getCodSport());
                    st.setString(4, tm.getCodCom());
                    st.setString(5, tipo);
                    st.setString(6, tmf.getLingua());
                    st.executeUpdate();

                } else {
                    st = conn.prepareStatement(queryInsert);
                    st.setString(1, tm.getCodSport());
                    st.setString(2, tm.getCodCom());
                    st.setString(3, tipo);
                    st.setString(4, tmf.getNomeFile());
                    st.setString(5, new String(tmf.getTemplate()));
                    st.setString(6, tmf.getLingua());
                    st.executeUpdate();

                }
                Utility.chiusuraJdbc(rs);
                Utility.chiusuraJdbc(st);
                Utility.chiusuraJdbc(rsRead);
                Utility.chiusuraJdbc(stRead);
            }
            ret.put("cod_com", tm.getCodCom());
            ret.put("cod_sport", tm.getCodSport());
            ret.put("cod_servizio", tm.getCodServizio());
            for (TemplateModelFile tmf : listaFile) {
                ret.put("nome_file_" + tmf.getLingua(), tmf.getNomeFile());
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

    public JSONObject inserisci(TemplateModel tm) throws Exception {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        String query;
        try {
            conn = Utility.getConnection();
            String tipo = "<tipo><bookmark>" + (tm.getCodServizio() == null ? "" : tm.getCodServizio()) + "</bookmark><procedimento>" + (tm.getCodProc() == null ? "" : tm.getCodProc()) + "</procedimento></tipo>";

            ret = new JSONObject();
            query = "insert into templates (cod_sport,cod_com,tipo,nome_file,template,cod_lang) values (?,?,?,?,?,?)";
            List<TemplateModelFile> listaFile = tm.getListaFile();

            for (TemplateModelFile tmf : listaFile) {

                st = conn.prepareStatement(query);
                st.setString(1, tm.getCodSport());
                st.setString(2, tm.getCodCom());
                st.setString(3, tipo);
                st.setString(4, tmf.getNomeFile());
                st.setString(5, new String(tmf.getTemplate()));
                st.setString(6, tmf.getLingua());
                st.executeUpdate();
                Utility.chiusuraJdbc(rs);
                Utility.chiusuraJdbc(st);

            }
            ret.put("cod_com", tm.getCodCom());
            ret.put("cod_sport", tm.getCodSport());
            ret.put("cod_servizio", tm.getCodServizio());
            for (TemplateModelFile tmf : listaFile) {
                ret.put("nome_file_" + tmf.getLingua(), tmf.getNomeFile());
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

    public JSONObject cancella(TemplateModel tm) throws Exception {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        String query;
        try {
            conn = Utility.getConnection();
            String tipo = "<tipo><bookmark>" + (tm.getCodServizio() == null ? "" : tm.getCodServizio()) + "</bookmark><procedimento>" + (tm.getCodProc() == null ? "" : tm.getCodProc()) + "</procedimento></tipo>";

            ret = new JSONObject();
            query = "delete from templates where cod_sport=? and cod_com = ? and tipo=?";
            st = conn.prepareStatement(query);
            st.setString(1, tm.getCodSport());
            st.setString(2, tm.getCodCom());
            st.setString(3, tipo);
            st.executeUpdate();

            ret.put("success", "Cancellazione effettuata");

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

    public TemplateModel leggi(String codSport, String codCom, String codProc, String codServizio, String codLang) throws Exception {
        TemplateModel template = null;
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            conn = Utility.getConnection();
            String tipo = "<tipo><bookmark>" + (codServizio == null ? "" : codServizio) + "</bookmark><procedimento>" + (codProc == null ? "" : codProc) + "</procedimento></tipo>";
            String query = "select * from templates "
                    + "where cod_sport=? "
                    + "and cod_com=? "
                    + "and tipo=? and cod_lang=?";
            st = conn.prepareStatement(query);
            st.setString(1, (codSport == null ? "" : codSport));
            st.setString(2, (codCom == null ? "" : codCom));
            st.setString(3, tipo);
            st.setString(4, codLang);
            rs = st.executeQuery();
            while (rs.next()) {
                template = new TemplateModel();
                template.setCodCom(rs.getString("cod_com"));
                template.setCodSport(rs.getString("cod_sport"));
                template.setTipo(rs.getString("tipo"));
                TemplateModelFile tmf = new TemplateModelFile();
                tmf.setLingua(rs.getString("cod_lang"));
                tmf.setNomeFile(Utility.testoDaDb(rs.getString("nome_file")));
                byte[] bdata = rs.getBlob("template").getBytes(1, (int) rs.getBlob("template").length());
                tmf.setTemplate(Base64.decodeBase64(bdata));
                List<TemplateModelFile> ltmf = new ArrayList<TemplateModelFile>();
                ltmf.add(tmf);
                template.setListaFile(ltmf);

            }
        } finally {

            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return template;
    }
}
