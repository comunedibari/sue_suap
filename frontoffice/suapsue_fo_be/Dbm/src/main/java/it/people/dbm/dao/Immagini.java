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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;

import it.people.dbm.model.ImmagineModel;
import it.people.dbm.utility.Utility;
import net.sf.json.JSONObject;
import org.slf4j.LoggerFactory;
/**
 *
 * @author Piergiorgio
 */
public class Immagini {
    private static Logger log = LoggerFactory.getLogger(Immagini.class);

    public JSONObject aggiorna(ImmagineModel im) throws Exception {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        String query;
        try {
            conn = Utility.getConnection();
            ret = new JSONObject();
            query = "insert into templates_immagini_immagini (nome_immagine,cod_sport,cod_com,nome_file,immagine,cod_lang) "
                    + "values (?,?,?,?,?,?)";
            st = conn.prepareStatement(query);
            st.setString(1, im.getNomeImmagine());
            st.setString(2, (im.getCodSport()!= null && !im.getCodSport().equals("") ? im.getCodSport(): ""));
            st.setString(3, (im.getCodCom()!= null && !im.getCodCom().equals("") ? im.getCodCom(): ""));
            st.setString(4, im.getNomeFile());
            st.setString(5, new String(im.getImmagine()));
            st.setString(6, im.getCodLang());
            st.executeUpdate();

            ret.put("success", "Aggiornamento effettuato");
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
    public JSONObject cancella(ImmagineModel im) throws Exception {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        String query;
        try {
            conn = Utility.getConnection();
            ret = new JSONObject();
            query = "delete from templates_immagini_immagini where nome_immagine = ? and cod_sport= ? and cod_com= ? and cod_lang = ? ";
            st = conn.prepareStatement(query);
            st.setString(1, im.getNomeImmagine());
            st.setString(2, im.getCodSport());
            st.setString(3, im.getCodCom());
            st.setString(4, im.getCodLang());

            st.executeUpdate();

            ret.put("success", "Cancellazione effettuata");
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

}
