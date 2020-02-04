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

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

import it.people.dbm.model.HrefStampaSchede;
import it.people.dbm.model.StampaSchede.AllegatoStampaSchede;
import it.people.dbm.model.StampaSchede.DocumentoStampaSchede;
import it.people.dbm.model.StampaSchede.InterventoStampaSchede;
import it.people.dbm.model.StampaSchede.NormativaStampaSchede;
import it.people.dbm.model.StampaSchede.OnereStampaSchede;
import it.people.dbm.model.StampaSchede.ProcedimentoStampaSchede;
import it.people.dbm.model.StampaSchede.RelazioneEnteStampaSchede;
import it.people.dbm.utility.Utility;

public class StampaSchedaDao {

    public List<InterventoStampaSchede> selectInterventi(int codiceIntervento) throws Exception {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        List<InterventoStampaSchede> interventi = new LinkedList<InterventoStampaSchede>();
        try {
            conn = Utility.getConnection();
            String query = "select a.cod_int,b.tit_int,a.cod_proc,a.flg_obb"
                    + " from interventi a join interventi_testi b"
                    + " on a.cod_int=b.cod_int"
                    + " where a.cod_int=?"
                    + " and b.cod_lang='it'";
            st = conn.prepareStatement(query);
            st.setInt(1, codiceIntervento);
            rs = st.executeQuery();
            while (rs.next()) {
                InterventoStampaSchede intervento = new InterventoStampaSchede();
                intervento.setCodiceIntervento(rs.getInt("cod_int"));
                intervento.setCodiceProcedimento(nullable(rs.getString("cod_proc")));
                intervento.setObbligatorio(nullable(rs.getString("flg_obb")));
                intervento.setTitoloIntervento(nullable(rs.getString("tit_int")));
                interventi.add(intervento);
            }
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return interventi;
    }

    public List<AllegatoStampaSchede> selectAllegati(String codiceComune, int codiceIntervento) throws Exception {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        List<AllegatoStampaSchede> allegati = new LinkedList<AllegatoStampaSchede>();
        try {
            conn = Utility.getConnection();
            String query = "select p.cod_doc, p.cod_int, p.copie, p.flg_autocert, p.cod_cond, p.flg_obb, p.tipologie, p.num_max_pag, p.dimensione_max,"
                    + " d.flg_dic, d.href,"
                    + " dt.tit_doc, dt.des_doc,"
                    + " dd.nome_file,"
                    + " t.testo_cond"
                    + " from"
                    + " ("
                    + " select    a.cod_doc, a.cod_int, a.copie, a.flg_autocert, a.cod_cond, a.flg_obb, a.tipologie, a.num_max_pag, a.dimensione_max"
                    + " from      allegati a"
                    + " join      allegati_comuni b"
                    + " on        a.cod_doc = b.cod_doc"
                    + " where     b.cod_com = ?"
                    + " and       a.cod_int = ?"
                    + " and       b.flg = 'S'"
                    + " union"
                    + " select    a.cod_doc, a.cod_int, a.copie, a.flg_autocert, a.cod_cond, a.flg_obb, a.tipologie, a.num_max_pag, a.dimensione_max"
                    + " from      allegati a"
                    + " left join allegati_comuni b"
                    + " on        a.cod_doc = b.cod_doc"
                    + " where     b.cod_doc is null "
                    + " and       a.cod_int = ? "
                    + " union"
                    + " select    a.cod_doc, a.cod_int, a.copie, a.flg_autocert, a.cod_cond, a.flg_obb, a.tipologie, a.num_max_pag, a.dimensione_max"
                    + " from      allegati a"
                    + " left join allegati_comuni b"
                    + " on        a.cod_doc = b.cod_doc"
                    + " where     b.cod_com != ? "
                    + " and       b.flg = 'N'"
                    + " and       a.cod_int = ?) p "
                    + " join documenti d"
                    + " on p.cod_doc = d.cod_doc"
                    + " join documenti_testi dt"
                    + " on p.cod_doc = dt.cod_doc"
                    + " left join documenti_documenti dd"
                    + " on p.cod_doc=dd.cod_doc"
                    + " and dd.cod_lang=dt.cod_lang"
                    + " left join testo_condizioni t"
                    + " on p.cod_cond=t.cod_cond"
                    + " and t.cod_lang=dt.cod_lang"
                    + " where dt.cod_lang='it'"
                    + " and d.flg_dic='N'";
            st = conn.prepareStatement(query);
            st.setString(1, codiceComune);
            st.setInt(2, codiceIntervento);
            st.setInt(3, codiceIntervento);
            st.setString(4, codiceComune);
            st.setInt(5, codiceIntervento);

            rs = st.executeQuery();
            while (rs.next()) {
                AllegatoStampaSchede allegato = new AllegatoStampaSchede();
                allegato.setAutocertificazione(rs.getInt("flg_autocert"));
                allegato.setCodiceCondizione(nullable(rs.getString("cod_cond")));
                allegato.setCodiceDocumento(nullable(rs.getString("cod_doc")));
                allegato.setCodiceIntervento(nullable(rs.getString("cod_int")));
                allegato.setCopie(rs.getInt("copie"));
                allegato.setDescrizioneDocumento(nullable(rs.getString("des_doc")));
                allegato.setDichiarazione(nullable(rs.getString("flg_dic")));
                allegato.setDimensioneMassima(rs.getInt("dimensione_max"));
                allegato.setHref(nullable(rs.getString("href")));
                allegato.setNomeFile(nullable(rs.getString("nome_file")));
                allegato.setNumeroMassimoPagine(rs.getInt("num_max_pag"));
                allegato.setObbligatorio(nullable(rs.getString("flg_obb")));
                allegato.setTestoCondizione(nullable(rs.getString("testo_cond")));
                allegato.setTipologie(nullable(rs.getString("tipologie")));
                allegato.setTitoloDocumento(nullable(rs.getString("tit_doc")));
                allegati.add(allegato);
            }
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return allegati;
    }

    public List<DocumentoStampaSchede> selectDocumenti(String codiceComune, int codiceIntervento) throws Exception {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        List<DocumentoStampaSchede> documenti = new LinkedList<DocumentoStampaSchede>();
        try {
            conn = Utility.getConnection();
            String query = "select p.cod_doc, p.cod_int, p.copie, p.flg_autocert, p.cod_cond, p.flg_obb, p.tipologie, p.num_max_pag, p.dimensione_max,"
                    + " d.flg_dic, d.href,"
                    + " dt.tit_doc, dt.des_doc,"
                    + " dd.nome_file,"
                    + " t.testo_cond,"
                    + " ht.tit_href, ht.piede_href "
                    + "  from"
                    + " ("
                    + " select    a.cod_doc, a.cod_int, a.copie, a.flg_autocert, a.cod_cond, a.flg_obb, a.tipologie, a.num_max_pag, a.dimensione_max"
                    + " from      allegati a"
                    + " join      allegati_comuni b"
                    + " on        a.cod_doc = b.cod_doc"
                    + " where     b.cod_com = ?"
                    + " and       a.cod_int = ?"
                    + " and       b.flg = 'S'"
                    + " "
                    + " union  "
                    + " select    a.cod_doc, a.cod_int, a.copie, a.flg_autocert, a.cod_cond, a.flg_obb, a.tipologie, a.num_max_pag, a.dimensione_max"
                    + " from      allegati a"
                    + " left join allegati_comuni b"
                    + " on        a.cod_doc = b.cod_doc"
                    + " where     b.cod_doc is null "
                    + " and       a.cod_int = ? "
                    + " "
                    + " union  "
                    + " select    a.cod_doc, a.cod_int, a.copie, a.flg_autocert, a.cod_cond, a.flg_obb, a.tipologie, a.num_max_pag, a.dimensione_max"
                    + " from      allegati a"
                    + " left join allegati_comuni b"
                    + " on        a.cod_doc = b.cod_doc"
                    + " where     b.cod_com != ? "
                    + " and       b.flg = 'N'"
                    + " and       a.cod_int = ?) p "
                    + " join documenti d"
                    + " on p.cod_doc = d.cod_doc"
                    + " join documenti_testi dt"
                    + " on p.cod_doc = dt.cod_doc"
                    + " left join documenti_documenti dd"
                    + " on p.cod_doc=dd.cod_doc"
                    + " and dd.cod_lang=dt.cod_lang"
                    + " left join testo_condizioni t"
                    + " on p.cod_cond=t.cod_cond"
                    + " and t.cod_lang=dt.cod_lang"
                    + " left join href h"
                    + " on d.href=h.href"
                    + " left join href_testi ht"
                    + " on d.href=ht.href"
                    + " and ht.cod_lang=dt.cod_lang"
                    + " where dt.cod_lang='it'"
                    + " and d.flg_dic='S'";
            st = conn.prepareStatement(query);
            st.setString(1, codiceComune);
            st.setInt(2, codiceIntervento);
            st.setInt(3, codiceIntervento);
            st.setString(4, codiceComune);
            st.setInt(5, codiceIntervento);
            rs = st.executeQuery();
            while (rs.next()) {
                DocumentoStampaSchede documento = new DocumentoStampaSchede();
                documento.setAutocertificazione(rs.getInt("flg_autocert"));
                documento.setCodiceCondizione(nullable(rs.getString("cod_cond")));
                documento.setCodiceDocumento(nullable(rs.getString("cod_doc")));
                documento.setCodiceIntervento(nullable(rs.getString("cod_int")));
                documento.setCopie(rs.getInt("copie"));
                documento.setDescrizioneDocumento(nullable(rs.getString("des_doc")));
                documento.setDichiarazione(nullable(rs.getString("flg_dic")));
                documento.setDimensioneMassima(rs.getInt("dimensione_max"));
                documento.setHref(nullable(rs.getString("href")));
                documento.setNomeFile(nullable(rs.getString("nome_file")));
                documento.setNumeroMassimoPagine(rs.getInt("num_max_pag"));
                documento.setObbligatorio(nullable(rs.getString("flg_obb")));
                documento.setTestoCondizione(nullable(rs.getString("testo_cond")));
                documento.setTipologie(nullable(rs.getString("tipologie")));
                documento.setTitoloDocumento(nullable(rs.getString("tit_doc")));
                documento.setTitoloHref(nullable(rs.getString("tit_href")));
                documento.setPiedeHref(nullable(rs.getString("piede_href")));
                documenti.add(documento);
            }
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return documenti;
    }

    public List<HrefStampaSchede> selectHrefCampi(String codiceHref) throws Exception {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        List<HrefStampaSchede> listaHref = new LinkedList<HrefStampaSchede>();
        try {
            conn = Utility.getConnection();
            String query = "select"
                    + " a.contatore,"
                    + " a.href,"
                    + " a.nome,"
                    + " a.riga,"
                    + " a.posizione,"
                    + " a.tp_riga,"
                    + " a.tipo,"
                    + " a.valore,"
                    + " a.controllo,"
                    + " b.des_campo,"
                    + " a.tp_controllo,"
                    + " a.lunghezza,"
                    + " a.decimali,"
                    + " a.edit,"
                    + " a.web_serv,"
                    + " a.nome_xsd,"
                    + " a.campo_key,"
                    + " a.campo_dati,"
                    + " a.campo_xml_mod,"
                    + " a.raggruppamento_check,"
                    + " a.campo_collegato,"
                    + " a.val_campo_collegato"
                    + " from"
                    + " href_campi a,"
                    + " href_campi_testi b"
                    + " where"
                    + " a.href = ?"
                    + " and"
                    + " a.href = b.href"
                    + " and"
                    + " a.contatore = b.contatore"
                    + " and"
                    + " a.nome = b.nome"
                    + " and"
                    + " b.cod_lang = 'it'"
                    + " order by"
                    + " a.href,"
                    + " a.riga,"
                    + " a.posizione,"
                    + " a.tp_riga,"
                    + " a.nome";
            st = conn.prepareStatement(query);
            st.setString(1, codiceHref);
            rs = st.executeQuery();
            while (rs.next()) {
                HrefStampaSchede href = new HrefStampaSchede();
                href.setCampoCollegato(nullable(rs.getString("campo_collegato")));
                href.setCampoDati(nullable(rs.getString("campo_dati")));
                href.setCampoXmlMod(nullable(rs.getString("campo_xml_mod")));
                href.setChiave(nullable(rs.getString("campo_key")));
                href.setCodice(nullable(rs.getString("href")));
                href.setContatore(rs.getInt("contatore"));
                href.setDecimali(rs.getInt("decimali"));
                href.setDescrizione(nullable(rs.getString("des_campo")));
                href.setEditabile(nullable(rs.getString("edit")));
                href.setLunghezza(rs.getInt("lunghezza"));
                href.setNome(nullable(rs.getString("nome")));
                href.setNomeXsd(nullable(rs.getString("nome_xsd")));
                href.setObbligatorio(nullable(rs.getString("controllo")));
                href.setPosizione(rs.getInt("posizione"));
                href.setRaggruppamentoCheck(nullable(rs.getString("raggruppamento_check")));
                href.setRiga(rs.getInt("riga"));
                href.setTipo(nullable(rs.getString("tipo")));
                listaHref.add(href);
            }
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return listaHref;
    }

    public List<InterventoStampaSchede> selectInterventiCollegati(int codiceInterventoPadre) throws Exception {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        List<InterventoStampaSchede> interventi = new LinkedList<InterventoStampaSchede>();
        try {
            conn = Utility.getConnection();
            String query = "SELECT a.cod_int_padre,a.cod_int,a.cod_cond,"
                    + " e.tit_int,"
                    + " d.cod_proc,"
                    + " g.tit_proc,"
                    + " h.testo_cond"
                    + " FROM interventi_collegati a"
                    + " JOIN interventi b"
                    + " ON a.cod_int_padre=b.cod_int"
                    + " JOIN interventi_testi c"
                    + " ON a.cod_int_padre=c.cod_int"
                    + " JOIN interventi d"
                    + " ON a.cod_int=d.cod_int"
                    + " JOIN interventi_testi e"
                    + " ON a.cod_int=e.cod_int"
                    + " AND e.cod_lang=c.cod_lang"
                    + " JOIN procedimenti f"
                    + " ON d.cod_proc=f.cod_proc"
                    + " JOIN procedimenti_testi g"
                    + " ON d.cod_proc=g.cod_proc"
                    + " AND g.cod_lang=c.cod_lang"
                    + " LEFT JOIN testo_condizioni h"
                    + " ON a.cod_cond = h.cod_cond"
                    + " AND c.cod_lang=h.cod_lang "
                    + " WHERE a.cod_int_padre=?"
                    + " AND c.cod_lang='it'";
            st = conn.prepareStatement(query);
            st.setInt(1, codiceInterventoPadre);
            rs = st.executeQuery();
            while (rs.next()) {
                InterventoStampaSchede intervento = new InterventoStampaSchede();
                intervento.setCodiceInterventoPadre(rs.getInt("cod_int_padre"));
                intervento.setCodiceIntervento(rs.getInt("cod_int"));
                intervento.setCodiceCondizione(nullable(rs.getString("cod_cond")));
                intervento.setTitoloIntervento(nullable(rs.getString("tit_int")));
                intervento.setCodiceProcedimento(nullable(rs.getString("cod_proc")));
                intervento.setTitoloProcedimento(nullable(rs.getString("tit_proc")));
                intervento.setTestoCondizione(nullable(rs.getString("testo_cond")));
                interventi.add(intervento);
            }
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return interventi;
    }

    public InterventoStampaSchede selectIntervento(int codiceIntervento) throws Exception {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        InterventoStampaSchede intervento = null;
        try {
            conn = Utility.getConnection();
            String query = "select a.cod_int,b.tit_int,a.cod_proc,a.flg_obb"
                    + " from interventi a "
                    + " join interventi_testi b "
                    + " on a.cod_int=b.cod_int "
                    + " where a.cod_int=? "
                    + " and b.cod_lang='it'";
            st = conn.prepareStatement(query);
            st.setInt(1, codiceIntervento);
            rs = st.executeQuery();
            while (rs.next()) {
                intervento = new InterventoStampaSchede();
                intervento.setCodiceIntervento(rs.getInt("cod_int"));
                intervento.setTitoloIntervento(nullable(rs.getString("tit_int")));
                intervento.setCodiceProcedimento(nullable(rs.getString("cod_proc")));
                intervento.setObbligatorio(nullable(rs.getString("flg_obb")));
            }
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return intervento;
    }

    public int selectMaxHrefRiga(String href) throws Exception {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        int rigaMax = 0;
        try {
            conn = Utility.getConnection();
            String query = "select"
                    + " max(riga) as riga_max"
                    + " from"
                    + " href_campi where href=?";
            st = conn.prepareStatement(query);
            st.setString(1, href);
            rs = st.executeQuery();
            if (rs.next()) {
                rigaMax = rs.getInt("riga_max");
            }
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return rigaMax;
    }

    public int selectMinHrefRiga(String href) throws Exception {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        int rigaMin = 0;
        try {
            conn = Utility.getConnection();
            String query = "select"
                    + " min(riga) as riga_min"
                    + " from"
                    + " href_campi where href=?";
            st = conn.prepareStatement(query);
            st.setString(1, href);
            rs = st.executeQuery();
            if (rs.next()) {
                rigaMin = rs.getInt("riga_min");
            }
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return rigaMin;
    }

    public int selectMaxHrefPosizione(String href) throws Exception {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        int posMax = 0;
        try {
            conn = Utility.getConnection();
            String query = "select"
                    + " max(posizione) as pos_max"
                    + " from"
                    + " href_campi where href=?";
            st = conn.prepareStatement(query);
            st.setString(1, href);
            rs = st.executeQuery();
            if (rs.next()) {
                posMax = rs.getInt("pos_max");
            }
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return posMax;
    }

    public int selectMinHrefPosizione(String href) throws Exception {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        int posMin = 0;
        try {
            conn = Utility.getConnection();
            String query = "select"
                    + " min(posizione) as pos_min"
                    + " from"
                    + " href_campi where href=?";
            st = conn.prepareStatement(query);
            st.setString(1, href);
            rs = st.executeQuery();
            if (rs.next()) {
                posMin = rs.getInt("pos_min");
            }
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return posMin;
    }

    public List<NormativaStampaSchede> selectNormative(String codiceComune, int codiceIntervento) throws Exception {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        List<NormativaStampaSchede> normative = new LinkedList<NormativaStampaSchede>();
        try {
            conn = Utility.getConnection();
            String query = "SELECT    op.cod_int, a.cod_rif, b.tit_rif, b.cod_lang, op.art_rif, a.cod_tipo_rif, a.nome_rif, tr.tipo_rif, nd.nome_file "
                    + "FROM      normative a "
                    + "JOIN      normative_testi b ON        a.cod_rif = b.cod_rif "
                    + "JOIN      norme_interventi op ON        a.cod_rif = op.cod_rif "
                    + "JOIN	 tipi_rif tr ON tr.cod_tipo_rif = a.cod_tipo_rif and b.cod_lang = tr.cod_lang "
                    + "JOIN      norme_comuni c ON a.cod_rif = c.cod_rif "
                    + "LEFT JOIN normative_documenti nd ON a.cod_rif=nd.cod_rif and nd.cod_lang=b.cod_lang "
                    + "where     c.cod_com = ? AND op.cod_int = ? AND  c.flg = 'S' and b.cod_lang='it' "
                    + "UNION   "
                    + "SELECT    op.cod_int, a.cod_rif,  b.tit_rif, b.cod_lang, op.art_rif, a.cod_tipo_rif, a.nome_rif, tr.tipo_rif, nd.nome_file "
                    + "FROM      normative a "
                    + "JOIN      normative_testi b ON a.cod_rif = b.cod_rif "
                    + "JOIN      norme_interventi op ON  a.cod_rif = op.cod_rif "
                    + "JOIN	 tipi_rif tr ON tr.cod_tipo_rif = a.cod_tipo_rif and b.cod_lang = tr.cod_lang "
                    + "LEFT JOIN norme_comuni c ON a.cod_rif = c.cod_rif "
                    + "LEFT JOIN normative_documenti nd ON a.cod_rif=nd.cod_rif and nd.cod_lang=b.cod_lang "
                    + "WHERE     c.cod_rif IS NULL  AND  op.cod_int = ? and b.cod_lang='it' "
                    + "UNION "
                    + "SELECT    op.cod_int, a.cod_rif,  b.tit_rif, b.cod_lang, op.art_rif, a.cod_tipo_rif, a.nome_rif, tr.tipo_rif, nd.nome_file "
                    + "FROM      normative a "
                    + "JOIN      normative_testi b ON        a.cod_rif = b.cod_rif "
                    + "JOIN      norme_interventi op ON        a.cod_rif = op.cod_rif "
                    + "JOIN	 tipi_rif tr ON tr.cod_tipo_rif = a.cod_tipo_rif and b.cod_lang = tr.cod_lang "
                    + "LEFT JOIN norme_comuni c ON        a.cod_rif = c.cod_rif "
                    + "LEFT JOIN normative_documenti nd ON a.cod_rif=nd.cod_rif and nd.cod_lang=b.cod_lang "
                    + "WHERE     c.cod_com != ?  AND       op.cod_int = ?  and c.flg='N' and b.cod_lang='it'" ;

            st = conn.prepareStatement(query);
            st.setString(1, codiceComune);
            st.setInt(2, codiceIntervento);
            st.setInt(3, codiceIntervento);
            st.setString(4, codiceComune);
            st.setInt(5, codiceIntervento);

            rs = st.executeQuery();
            while (rs.next()) {
                NormativaStampaSchede normativa = new NormativaStampaSchede();
                normativa.setCodiceIntervento(rs.getInt("cod_int"));
                normativa.setCodiceRiferimento(nullable(rs.getString("cod_rif")));
                normativa.setArticoloRiferimento(nullable(rs.getString("art_rif")));
                normativa.setCodiceTipoRiferimento(nullable(rs.getString("cod_tipo_rif")));
                normativa.setNomeRiferimento(nullable(rs.getString("nome_rif")));
                normativa.setTitoloRiferimento(nullable(rs.getString("tit_rif")));
                normativa.setTipoRiferimento(nullable(rs.getString("tipo_rif")));
                normativa.setNomeFile(nullable(rs.getString("nome_file")));
                normative.add(normativa);
            }
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return normative;
    }

    public List<OnereStampaSchede> selectOneri(int codiceIntervento, String codiceComune) throws Exception {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        List<OnereStampaSchede> oneri = new LinkedList<OnereStampaSchede>();
        try {
            conn = Utility.getConnection();
            String query = "select p.cod_int, p.cod_oneri, p.cod_doc_onere, p.imp_acc, p.cod_padre, p.cod_cud, p.des_oneri, p.note,"
                    + " odt.des_doc_onere,"
                    + " od.nome_file,"
                    + " e.cod_ente,"
                    + " et.des_ente "
                    + " from"
                    + " ("
                    + " select    op.cod_int, a.cod_oneri, a.cod_doc_onere, a.imp_acc, a.cod_padre, a.cod_cud, b.des_oneri, b.note, b.cod_lang"
                    + " from      oneri a"
                    + " join      oneri_testi b"
                    + " on        a.cod_oneri = b.cod_oneri"
                    + " join      oneri_interventi op"
                    + " on        a.cod_oneri = op.cod_oneri"
                    + " join      oneri_comuni c"
                    + " on        a.cod_oneri = c.cod_oneri"
                    + " where     c.cod_com = ?"
                    + " and       op.cod_int = ?"
                    + " and       c.flg = 'S'"
                    + " union  "
                    + " select    op.cod_int, a.cod_oneri, a.cod_doc_onere, a.imp_acc, a.cod_padre, a.cod_cud, b.des_oneri, b.note, b.cod_lang"
                    + " from      oneri a"
                    + " join      oneri_testi b"
                    + " on        a.cod_oneri = b.cod_oneri"
                    + " join      oneri_interventi op"
                    + " on        a.cod_oneri = op.cod_oneri"
                    + " left join oneri_comuni c"
                    + " on        a.cod_oneri = c.cod_oneri"
                    + " where     c.cod_oneri is null "
                    + " and       op.cod_int = ?"
                    + " union "
                    + " select    op.cod_int, a.cod_oneri, a.cod_doc_onere, a.imp_acc, a.cod_padre, a.cod_cud, b.des_oneri, b.note, b.cod_lang "
                    + " from      oneri a "
                    + " join      oneri_testi b "
                    + " on        a.cod_oneri = b.cod_oneri "
                    + " join      oneri_interventi op "
                    + " on        a.cod_oneri = op.cod_oneri "
                    + " left join oneri_comuni c "
                    + " on        a.cod_oneri = c.cod_oneri "
                    + " where     c.cod_com != ? "
                    + " and       c.flg = 'N' "
                    + " and       op.cod_int = ? "
                    + ") p"
                    + " left join relazioni_enti r"
                    + " on p.cod_cud=r.cod_cud"
                    + " and r.cod_com=?"
                    + " left join destinatari d"
                    + " on r.cod_dest=d.cod_dest "
                    + " left join destinatari_testi dt "
                    + " on dt.cod_dest=d.cod_dest "
                    + " and dt.cod_lang=p.cod_lang "
                    + " left join enti_comuni e"
                    + " on d.cod_ente=e.cod_ente"
                    + " left join enti_comuni_testi et"
                    + " on d.cod_ente=et.cod_ente"
                    + " and p.cod_lang=et.cod_lang"
                    + " left join oneri_documenti od"
                    + " on p.cod_doc_onere=od.cod_doc_onere"
                    + " and od.cod_lang=p.cod_lang"
                    + " left join oneri_documenti_testi odt"
                    + " on p.cod_doc_onere=odt.cod_doc_onere"
                    + " and p.cod_lang=odt.cod_lang"
                    + " where p.cod_lang='it'";
            st = conn.prepareStatement(query);
            st.setString(1, codiceComune);
            st.setInt(2, codiceIntervento);
            st.setInt(3, codiceIntervento);
            st.setString(4, codiceComune);
            st.setInt(5, codiceIntervento);
            st.setString(6, codiceComune);
            rs = st.executeQuery();
            while (rs.next()) {
                OnereStampaSchede onere = new OnereStampaSchede();
                onere.setCodiceIntervento(rs.getInt("cod_int"));
                onere.setCodiceOneri(nullable(rs.getString("cod_oneri")));
                onere.setCodiceDocumentoOnere(nullable(rs.getString("cod_doc_onere")));
                BigDecimal importo = rs.getBigDecimal("imp_acc");
                if (importo == null) {
                    importo = BigDecimal.ZERO;
                }
                onere.setImportoAcconto(importo);
                onere.setCodicePadre(nullable(rs.getString("cod_padre")));
                onere.setCodiceCud(nullable(rs.getString("cod_cud")));
                onere.setDescrizioneOneri(nullable(rs.getString("des_oneri")));
                onere.setNote(nullable(rs.getString("note")));
                onere.setDescrizioneDocumentoOnere(nullable(rs.getString("des_doc_onere")));
                onere.setNomeFile(nullable(rs.getString("nome_file")));
                onere.setCodiceEnte(nullable(rs.getString("cod_ente")));
                onere.setDescrizioneEnte(nullable(rs.getString("des_ente")));
                oneri.add(onere);
            }
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return oneri;
    }

    public ProcedimentoStampaSchede selectProcedimento(String codiceProcedimento) throws Exception {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        ProcedimentoStampaSchede procedimento = null;
        try {
            conn = Utility.getConnection();
            String query = "SELECT a.cod_proc,b.tit_proc,a.flg_tipo_proc,a.flg_bollo,a.cod_cud,a.ter_eva"
                    + " FROM procedimenti a"
                    + " JOIN procedimenti_testi b"
                    + " ON a.cod_proc=b.cod_proc"
                    + " WHERE a.cod_proc=?"
                    + " AND b.cod_lang='it'";
            st = conn.prepareStatement(query);
            st.setString(1, codiceProcedimento);
            rs = st.executeQuery();
            while (rs.next()) {
                procedimento = new ProcedimentoStampaSchede();
                procedimento.setCodiceProcedimento(nullable(rs.getString("cod_proc")));
                procedimento.setTitoloProcedimento(nullable(rs.getString("tit_proc")));
                procedimento.setTipoProcedimento(rs.getInt("flg_tipo_proc"));
                procedimento.setBollo(nullable(rs.getString("flg_bollo")));
                procedimento.setCodiceCud(nullable(rs.getString("cod_cud")));
                procedimento.setTerminiEvasione(rs.getInt("ter_eva"));
            }
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return procedimento;
    }

    public RelazioneEnteStampaSchede selectRelazioniEnti(String codiceCud, String codiceComune) throws Exception {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        RelazioneEnteStampaSchede relazione = null;
        try {
            conn = Utility.getConnection();
            String query = "select a.cod_com,a.cod_cud,a.cod_dest,a.cod_sport,"
                    + " c.des_ente des_com,"
                    + " e.des_cud,"
                    + " dt.intestazione,dt.nome_dest,f.cod_ente,"
                    + " h.des_sport,"
                    + " l.des_ente"
                    + " from relazioni_enti a"
                    + " join enti_comuni b"
                    + " on a.cod_com=b.cod_ente"
                    + " join enti_comuni_testi c"
                    + " on a.cod_com=c.cod_ente"
                    + " join cud d"
                    + " on a.cod_cud=d.cod_cud"
                    + " join cud_testi e"
                    + " on a.cod_cud=e.cod_cud"
                    + " and c.cod_lang=e.cod_lang"
                    + " join destinatari f"
                    + " on a.cod_dest=f.cod_dest "
                    + " join destinatari_testi dt "
                    + " on f.cod_dest=dt.cod_dest "
                    + " and c.cod_lang=dt.cod_lang "
                    + " join sportelli g"
                    + " on a.cod_sport=g.cod_sport"
                    + " join sportelli_testi h"
                    + " on a.cod_sport=h.cod_sport"
                    + " and c.cod_lang=h.cod_lang"
                    + " join enti_comuni i"
                    + " on f.cod_ente=i.cod_ente"
                    + " join enti_comuni_testi l"
                    + " on f.cod_ente=l.cod_ente"
                    + " and c.cod_lang=l.cod_lang"
                    + " where a.cod_com=?"
                    + " and a.cod_cud=?"
                    + " and c.cod_lang='it'";
            st = conn.prepareStatement(query);
            st.setString(1, codiceComune);
            st.setString(2, codiceCud);
            rs = st.executeQuery();
            while (rs.next()) {
                relazione = new RelazioneEnteStampaSchede();
                relazione.setCodiceComune(nullable(rs.getString("cod_com")));
                relazione.setCodiceCud(nullable(rs.getString("cod_cud")));
                relazione.setCodiceDestinatario(nullable(rs.getString("cod_dest")));
                relazione.setCodiceSportello(nullable(rs.getString("cod_sport")));
                relazione.setDescrizioneComune(nullable(rs.getString("des_com")));
                relazione.setDescrizioneCud(nullable(rs.getString("des_cud")));
                relazione.setIntestazione(nullable(rs.getString("intestazione")));
                relazione.setNomeDestinatario(nullable(rs.getString("nome_dest")));
                relazione.setCodiceEnte(nullable(rs.getString("cod_ente")));
                relazione.setDescrizioneSportello(nullable(rs.getString("des_sport")));
                relazione.setDescrizioneEnte(nullable(rs.getString("des_ente")));
            }
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return relazione;
    }

    private String nullable(String value) {
        if (value == null) {
            return "";
        } else {
            return Utility.testoDaDb(value);
        }
    }
}
