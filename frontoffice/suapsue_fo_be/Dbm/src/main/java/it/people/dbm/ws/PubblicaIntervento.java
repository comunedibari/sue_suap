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
package it.people.dbm.ws;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.ws.BindingProvider;

import org.slf4j.Logger;

import it.people.dbm.dao.ImportExportLocal;
import it.people.dbm.utility.Configuration;
import it.people.dbm.utility.Utility;
import it.people.dbm.xsd.ResponseRoot;
import org.slf4j.LoggerFactory;
/**
 *
 * @author Piergiorgio
 */
public class PubblicaIntervento {

    private static Logger log = LoggerFactory.getLogger(PubblicaIntervento.class);
    private ImportExportLocal ie;
    private String qnameService;
    private String qnamePort;

    public void XmlPubblicazione() throws Exception {
        ie = new ImportExportLocal();
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        PreparedStatement st1 = null;
        ResultSet rs1 = null;
        String risposta = null;
        try {
            conn = Utility.getConnection();
            String sql = "select cod_int from interventi where flg_pubblicazione = 'S'";
            st = conn.prepareStatement(sql);
            rs = st.executeQuery();

            while (rs.next()) {
                sql = "select a.cod_ente from enti_comuni a join classi_enti b  "
                        + "on a.cod_classe_ente = b.cod_classe_ente and b.flg_com = 'S'";
                st1 = conn.prepareStatement(sql);
                rs1 = st1.executeQuery();
                boolean aggiorna = false;
                while (rs1.next()) {
                    risposta = caricaXml(rs.getString("cod_int"), rs1.getString("cod_ente"), conn);
                    if (risposta.equals("OK")) {
                        aggiorna = true;
                    }
                }
                Utility.chiusuraJdbc(rs1);
                Utility.chiusuraJdbc(st1);
                if (aggiorna) {
                    sql = "update interventi set flg_pubblicazione = 'N', data_pubblicazione=current_timestamp, note_pubblicazione = null, note_generali_pubblicazione = null where cod_int = ?";
                    st1 = conn.prepareStatement(sql);
                    st1.setInt(1, rs.getInt("cod_int"));
                    st1.executeUpdate();
                    Utility.chiusuraJdbc(st1);
                }
            }
        } catch (Exception e) {
            log.error("Errore select ", e);
            throw e;
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(rs1);
            Utility.chiusuraJdbc(st1);
            Utility.chiusuraJdbc(conn);
        }
    }

    private String caricaXml(String codInt, String codCom, Connection conn) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        String ret = null;
        try {

            String url = Configuration.getConfigurationParameter("urlTCR");
            DocumentRoot doc = new DocumentRoot();
            PubblicaIntervento x = new it.people.dbm.ws.PubblicaIntervento();
            it.people.dbm.ws.Pubblicazione service = new it.people.dbm.ws.Pubblicazione();

            it.people.dbm.ws.PubblicazionePortType port = service.getPubblicazioneSOAP11PortHttp();
            ((BindingProvider) port).getRequestContext().put("javax.xml.ws.service.endpoint.address", url);
            ie.caricaIntervento(doc, codInt, codCom, conn, false);

            it.people.dbm.ws.GenerateDocumentResponse response = port.generateDocument(doc);
            JAXBContext jc = JAXBContext.newInstance(ResponseRoot.class);
            Unmarshaller um = jc.createUnmarshaller();
            InputStream is = new ByteArrayInputStream(response.getReturn());
            ResponseRoot docRisposta = (ResponseRoot) um.unmarshal(is);
            is.close();
            ret = docRisposta.getStatus();
            log.info(docRisposta.getMessage());

        } catch (Exception e) {
            log.error("Errore chiamata WS ", e);
            throw e;
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
        }
        return ret;
    }
}
