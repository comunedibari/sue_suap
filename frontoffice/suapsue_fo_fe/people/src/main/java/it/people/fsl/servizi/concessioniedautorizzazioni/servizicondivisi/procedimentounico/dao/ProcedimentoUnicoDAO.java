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
package it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.dao;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.betwixt.io.BeanReader;
import org.apache.commons.betwixt.io.BeanWriter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ojb.broker.util.logging.Logger;
import org.python.core.PyException;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;
import org.xml.sax.SAXException;

import it.gruppoinit.commons.DBCPManager;
import it.gruppoinit.commons.Utilities;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.AllegatoBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.BaseBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.ComuneBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.DefinizioneCampoFormula;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.DestinatarioBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.DichiarazioniStaticheBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.DocumentoBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.DocumentoFisicoBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.HrefCampiBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.ImportBookmarkBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.Input;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.InterventoBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.ModulisticaBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.NormativaBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.OneriBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.OneriBeanComparator;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.OperazioneBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.Output;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.ParametriPUBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.ProcedimentoBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.ProcedimentoUnicoException;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.SettoreBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.SezioneCompilabileBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.SportelloBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility.Costant;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility.ManagerAllegati;
import it.people.process.AbstractPplProcess;
import it.people.wrappers.IRequestWrapper;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

public class ProcedimentoUnicoDAO {

    private Log log = LogFactory.getLog(this.getClass());
    private String language = "";
    protected DBCPManager db;
    private boolean isMySqlDb = true;
    PythonInterpreter interp = null;

    public ProcedimentoUnicoDAO(DBCPManager dbm, String nazionalita) {
        this.db = dbm;
        String dbType = this.db.getDATABASE();

        // se l'url del data source contiene mysql attiva il flag per questo
        // database
        if (dbType.toLowerCase().indexOf("mysql") != -1) {
            isMySqlDb = true;
            // negli altri casi (Oracle al momento)
        } else {
            isMySqlDb = false;
        }
        this.language = nazionalita;
    }

    // PC - accesso comune
    // public List getListaComuni() throws SQLException {
    // PC - accesso comune
    public List getListaComuni(String codNodo) throws SQLException {

        ArrayList list = new ArrayList();
        ResultSet rs = null;
        Connection conn = null;
        String sql = null;
        try {
            sql = "SELECT enti_comuni.cod_ente as codice_ente, enti_comuni_testi.des_ente as descrizione_ente"
                    + " FROM enti_comuni"
                    + " INNER JOIN classi_enti ON enti_comuni.cod_classe_ente=classi_enti.cod_classe_ente "
                    + " INNER JOIN comuni_aggregazione  ON comuni_aggregazione.cod_ente = enti_comuni.cod_ente "
                    + " INNER JOIN enti_comuni_testi ON enti_comuni.cod_ente = enti_comuni_testi.cod_ente "
                    + " WHERE classi_enti.flg_com='S' AND enti_comuni.cod_ente=comuni_aggregazione.cod_ente "
                    + " AND enti_comuni_testi.cod_lang=? "
                    // PC - accesso comune
                    + " AND enti_comuni.cod_nodo = ? "
                    // PC - accesso comune
                    + " ORDER BY des_ente ";
            conn = db.open();
            PreparedStatement ps = db.getPreparedStmt(conn, sql);
            ps.setString(1, language);
            // PC - accesso comune
            ps.setString(2, codNodo);
            // PC - accesso comune
            log.info("getListaComuni: " + ps);
            rs = ps.executeQuery();
            while (rs.next()) {
                BaseBean bean = new BaseBean();
                bean.setCodice(rs.getString("codice_ente"));
                bean.setDescrizione(rs.getString("descrizione_ente"));
                list.add(bean);
            }
        } catch (SQLException e) {
            log.error(e);
            throw e;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                conn.close();
            } catch (Exception e) {
            }
        }
        return list;
    }

    public boolean hasTemplateVarioForSportello(String codSportello, String azione) {
        String query = "SELECT tvr.doc_blob as template FROM templates_vari_risorse tvr JOIN templates_vari tv "
                + " on tvr.nome_template = tv.nome_template "
                + " where tvr.cod_sport=? and tvr.nome_template = ?";
        log.debug(query);

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = db.open();
            ps = conn.prepareStatement(query);
            ps.setString(1, codSportello);
            ps.setString(2, azione);
            log.info("hasTemplateVarioForSportello 1: " + ps);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            } else {
                ps.close();
                rs.close();
                query = "SELECT tvr.doc_blob as template FROM templates_vari_risorse tvr JOIN templates_vari tv "
                        + " on tvr.nome_template = tv.nome_template "
                        + " where tvr.cod_sport='' and tvr.nome_template = ?";
                ps = conn.prepareStatement(query);
                ps.setString(1, azione);
                log.info("hasTemplateVarioForSportello 2: " + ps);
                rs = ps.executeQuery();
                if (rs.next()) {
                    return true;
                }
            }
        } catch (Exception e) {
            log.error(e);
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                ps.close();
            } catch (Exception e) {
            }
            try {
                conn.close();
            } catch (Exception e) {
            }
        }

        return false;
    }

    public ComuneBean getDettaglioComune(String cod_ente) throws SQLException {
        ComuneBean comune = new ComuneBean();
        ResultSet rs = null;
        Connection conn = null;
        try {
            String sql = "SELECT * FROM enti_comuni  ";
            sql += " INNER JOIN classi_enti ON enti_comuni.cod_classe_ente=classi_enti.cod_classe_ente ";
            sql += " INNER JOIN comuni_aggregazione  ON comuni_aggregazione.cod_ente = enti_comuni.cod_ente ";
            sql += " INNER JOIN enti_comuni_testi ON enti_comuni.cod_ente = enti_comuni_testi.cod_ente ";
            sql += " WHERE classi_enti.flg_com='S' AND enti_comuni_testi.cod_lang=? AND enti_comuni.cod_ente=? ORDER BY des_ente ";
            conn = db.open();
            PreparedStatement ps = db.getPreparedStmt(conn, sql);
            ps.setString(1, language);
            ps.setString(2, cod_ente);
            log.info("getDettaglioComune: " + ps);
            rs = ps.executeQuery();
            log.debug("ps: " + ps);
            if (rs.next()) {
                comune.setCap(rs.getString("cap"));
                comune.setCitta(rs.getString("citta"));
                comune.setDescrizione(rs.getString("des_ente"));
                comune.setEmail(rs.getString("email"));
                comune.setFax(Utilities.NVL(rs.getString("fax")));
                comune.setProvincia(rs.getString("prov"));
                comune.setTelefono(rs.getString("tel"));
                comune.setVia(rs.getString("indirizzo"));
                comune.setCodClasseEnte(rs.getString("cod_classe_ente"));
                comune.setCodEnte(rs.getString("cod_ente"));
                comune.setTipAggregazione(rs.getString("tip_aggregazione"));
                if (rs.getString("cod_istat") != null
                        && !rs.getString("cod_istat").equalsIgnoreCase("")) {
                    comune.setCodIstat(rs.getString("cod_istat"));
                }
                if (rs.getString("cod_bf") != null
                        && !rs.getString("cod_bf").equalsIgnoreCase("")) {
                    comune.setCodBf(rs.getString("cod_bf"));
                }
                comune.setAoo(rs.getString("aoo"));
            } else {
                comune = null;
            }
        } catch (SQLException e) {
            log.error(e);
            throw e;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                conn.close();
            } catch (Exception e) {
            }
        }
        return comune;
    }

    public void calcolaInterventi(ProcessData dataForm) throws SQLException,
            Exception {
        ResultSet rs = null;
        ResultSet rsTestoCondizione = null;
        Connection conn = null;

        ArrayList listaCodiciOperazione = new ArrayList();
        for (Iterator iterator = dataForm.getAlberoOperazioni().iterator(); iterator.hasNext();) {
            OperazioneBean operazione = (OperazioneBean) iterator.next();
            if (operazione.isSelezionato()
                    && (operazione.getListaCodiciFigli() == null || operazione.getListaCodiciFigli().size() == 0)) {
                listaCodiciOperazione.add(operazione.getCodice());
            }
        }

        String inClause = getInClauseString(listaCodiciOperazione);
        try {
            ArrayList listaCodInt = new ArrayList();
            conn = db.open();
            String sql = "";
            // PC - ordina allegati
            sql += "select distinct a.cod_int, b.ordinamento, d.tit_int, a.cod_ope from condizioni_di_attivazione a, interventi b, interventi_comuni c, comuni_aggregazione e, interventi_testi d"
                    + " where a.cod_sett = ? and a.cod_int = b.cod_int and a.cod_int = c.cod_int and b.cod_int = d.cod_int and d.cod_lang = ? and a.cod_ope in ("
                    + inClause
                    + ")"
                    + " and c.cod_com = ? and c.flg='S' and e.cod_ente = c.cod_com and e.tip_aggregazione = a.tip_aggregazione "
                    + " union "
                    + " select distinct a.cod_int, b.ordinamento, d.tit_int, a.cod_ope from condizioni_di_attivazione a join interventi b on a.cod_int = b.cod_int "
                    + " join interventi_testi d on b.cod_int = d.cod_int and d.cod_lang = ? "
                    + " join comuni_aggregazione e on e.cod_ente = ? and a.tip_aggregazione = e.tip_aggregazione "
                    + " left join interventi_comuni c on a.cod_int = c.cod_int "
                    + " where a.cod_sett = ? and a.cod_ope in ("
                    + inClause
                    + ") and c.cod_int is null "
                    + " union "
                    + " select distinct a.cod_int, b.ordinamento, d.tit_int, a.cod_ope from  condizioni_di_attivazione a join interventi b on a.cod_int = b.cod_int "
                    + " join interventi_testi d on b.cod_int = d.cod_int and d.cod_lang = ? "
                    + " join comuni_aggregazione e on a.tip_aggregazione = e.tip_aggregazione and e.cod_ente = ? "
                    + " left join interventi_comuni c on a.cod_int = c.cod_int "
                    + " where a.cod_sett = ? and a.cod_ope in ("
                    + inClause
                    + ") and c.cod_com != ? and c.flg='N' "
                    + " and a.cod_int not in (select cod_int from interventi_comuni where cod_com = ? and flg = 'N') "
                    + " order by 1";
            // PC - ordina allegati
            PreparedStatement ps = db.getPreparedStmt(conn, sql);
            ps.setString(1, dataForm.getSettoreScelto().getCodice());
            ps.setString(2, language);
            ps.setString(3, dataForm.getComuneSelezionato().getCodEnte());
            ps.setString(4, language);
            ps.setString(5, dataForm.getComuneSelezionato().getCodEnte());
            ps.setString(6, dataForm.getSettoreScelto().getCodice());
            ps.setString(7, language);
            ps.setString(8, dataForm.getComuneSelezionato().getCodEnte());
            ps.setString(9, dataForm.getSettoreScelto().getCodice());
            ps.setString(10, dataForm.getComuneSelezionato().getCodEnte());
            ps.setString(11, dataForm.getComuneSelezionato().getCodEnte());
            log.info("calcolaInterventi 1: " + ps);
            rs = ps.executeQuery();
// PC - Reiterazione domanda inizio 
//            dataForm.setInterventiFacoltativi(new ArrayList());
// PC - Reiterazione domanda fine            
            while (rs.next()) {
                String codiceIntervento = rs.getString("cod_int");
                InterventoBean bean = new InterventoBean();
                if (codiceIntervento != null) {
                    bean.setCodice(rs.getString("cod_int"));
                    bean.setDescrizione(rs.getString("tit_int"));
                    bean.setCodiceOperazioneAttivante(rs.getString("cod_ope"));
                    // PC - ordina allegati
                    bean.setOrdinamento(rs.getInt("ordinamento"));
                    // PC - ordina allegati
                    listaCodInt.add(bean.getCodice());
                }
                InterventoBean interventoPresente = checkPresenzaIntervento(
                        dataForm.getInterventi(), bean.getCodice());
                if (interventoPresente == null) {
                    dataForm.getInterventi().add(bean);
                } else {
                    // xxx
                }
            }
            ps.close();
            rs.close();
            String lista_cod_int = getInClauseNumber(listaCodInt);
            if (!lista_cod_int.equalsIgnoreCase("")) {
                // PC - ordina allegati
                sql = "select distinct t.cod_int, t.ordinamento, t.cod_int_padre, t.tit_int from "
                        + "( "
                        + " select a.cod_int, i.ordinamento, a.cod_int_padre, c.tit_int "
                        + " from interventi_collegati a join interventi_comuni b on a.cod_int = b.cod_int and b.cod_com = ? and b.flg = 'S' "
                        + " join interventi_testi c on a.cod_int = c.cod_int and c.cod_lang = ? "
                        + " join interventi i on a.cod_int=i.cod_int "
                        + " where a.cod_int_padre in ("
                        + lista_cod_int
                        + ") and a.cod_cond is null "
                        + " union "
                        + " select a.cod_int, i.ordinamento, a.cod_int_padre, c.tit_int "
                        + " from interventi_collegati a left join interventi_comuni b on a.cod_int = b.cod_int "
                        + " join interventi_testi c on a.cod_int = c.cod_int and c.cod_lang = ? "
                        + " join interventi i on a.cod_int = i.cod_int "
                        + " where a.cod_int_padre in ("
                        + lista_cod_int
                        + ") "
                        + " and b.cod_int is null and a.cod_cond is null "
                        + " union "
                        + " select a.cod_int, i.ordinamento, a.cod_int_padre, c.tit_int "
                        + " from interventi_collegati a left join interventi_comuni b on a.cod_int = b.cod_int "
                        + " join interventi_testi c on a.cod_int = c.cod_int and c.cod_lang = ? "
                        + " join interventi i on a.cod_int = i.cod_int "
                        + " where a.cod_int_padre in ("
                        + lista_cod_int
                        + ") and b.cod_com != ? and b.flg = 'N' and a.cod_cond is null "
                        + " and a.cod_int not in (select cod_int from interventi_comuni where cod_com = ? and flg = 'N') "
                        + " ) AS t";
                // PC - ordina allegati
                PreparedStatement psX = db.getPreparedStmt(conn, sql);
                psX.setString(1, dataForm.getComuneSelezionato().getCodEnte());
                psX.setString(2, language);
                psX.setString(3, language);
                psX.setString(4, language);
                psX.setString(5, dataForm.getComuneSelezionato().getCodEnte());
                psX.setString(6, dataForm.getComuneSelezionato().getCodEnte());
                log.info("calcolaInterventi 2: " + ps);
                ResultSet rsX = psX.executeQuery();

                while (rsX.next()) {
                    if (rsX.getString("cod_int") != null) {
                        InterventoBean bean = new InterventoBean();
                        bean.setCodice(rsX.getString("cod_int"));
                        bean.setDescrizione(rsX.getString("tit_int"));
                        // PC - ordina allegati
                        bean.setOrdinamento(rsX.getInt("ordinamento"));
                        // PC - ordina allegati
                        // bean.setCodiceOperazioneAttivante(rsX.getString("cod_ope"));
                        if (checkPresenzaIntervento(dataForm.getInterventi(),
                                bean.getCodice()) == null) {
                            dataForm.getInterventi().add(bean);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            log.error(e);
            throw e;
        } catch (Exception e) {
            log.error(e);
            throw e;
        } finally {
        }
        try {
            rs.close();
        } catch (Exception e) {
            log.error(e);
            throw e;
        }
        try {
            conn.close();
        } catch (Exception e) {
            log.error(e);
            throw e;
        }
    }

    private InterventoBean checkPresenzaIntervento(ArrayList listaInterventi,
            String codiceIntervento) {
        boolean trovato = false;
        InterventoBean intervento = null;
        Iterator iterator = listaInterventi.iterator();
        while (iterator.hasNext() && !trovato) {
            intervento = (InterventoBean) iterator.next();
            if (intervento.getCodice().equalsIgnoreCase(codiceIntervento)) {
                trovato = true;
            }
        }
        if (trovato) {
            return intervento;
        } else {
            return null;
        }
    }

    public ArrayList getInterventiFacoltativi(String codEnte,
            ArrayList listaInterventiObbligatori, ProcessData dataForm) throws SQLException {
        ArrayList listaInterventiFacoltativi = new ArrayList();
        ResultSet rs = null;
        Connection conn = null;
        String sql = "";
        HashMap mappa = new HashMap();
// PC - Reiterazione domanda inizio        
        List<String> listaInterventiFacoltativiOld = new ArrayList<String>();
        for (Iterator it = dataForm.getInterventiFacoltativi().iterator(); it.hasNext();) {
            listaInterventiFacoltativiOld.add(((InterventoBean) it.next()).getCodice());
        }
        List<String> listaInterventiFacoltativiNew = new ArrayList<String>();
// PC - Reiterazione domanda fine        
        try {
            conn = db.open();
            ArrayList listaIntTmp = new ArrayList();
            for (Iterator iterator = listaInterventiObbligatori.iterator(); iterator.hasNext();) {
                InterventoBean intervento = (InterventoBean) iterator.next();
                listaIntTmp.add(intervento.getCodice());
            }
            String lista_cod_int = getInClauseNumber(listaIntTmp);
            // PC - ordina allegati
            sql = "select distinct t.cod_int, t.ordinamento, t.cod_int_padre, t.tit_int, t.cod_cond, t.testo_cond from "
                    + " ( "
                    + " select a.cod_int, i.ordinamento, a.cod_int_padre, c.tit_int, a.cod_cond, d.testo_cond "
                    + " from interventi_collegati a join interventi_comuni b on a.cod_int = b.cod_int and b.cod_com = ? and b.flg = 'S' "
                    + " join interventi_testi c on a.cod_int = c.cod_int and c.cod_lang = ? "
                    + " join interventi i on a.cod_int = i.cod_int "
                    + " join testo_condizioni d on a.cod_cond = d.cod_cond and d.cod_lang = ? "
                    + " where a.cod_int_padre in ("
                    + lista_cod_int
                    + ") and a.cod_cond is not null "
                    + " union "
                    + " select a.cod_int, i.ordinamento, a.cod_int_padre, c.tit_int, a.cod_cond, d.testo_cond "
                    + " from interventi_collegati a left join interventi_comuni b on a.cod_int = b.cod_int "
                    + " join interventi_testi c on a.cod_int = c.cod_int and c.cod_lang = ? "
                    + " join interventi i on a.cod_int=i.cod_int "
                    + " join testo_condizioni d on a.cod_cond = d.cod_cond and d.cod_lang = ? "
                    + " where a.cod_int_padre in ("
                    + lista_cod_int
                    + ") "
                    + " and b.cod_int is null and a.cod_cond is not null "
                    + " union "
                    + " select a.cod_int, i.ordinamento, a.cod_int_padre, c.tit_int, a.cod_cond, d.testo_cond "
                    + " from interventi_collegati a left join interventi_comuni b on a.cod_int = b.cod_int "
                    + " join interventi_testi c on a.cod_int = c.cod_int and c.cod_lang = ? "
                    + " join interventi i on a.cod_int = i.cod_int "
                    + " join testo_condizioni d on a.cod_cond = d.cod_cond and d.cod_lang = ? "
                    + " where a.cod_int_padre in ("
                    + lista_cod_int
                    + ") and b.cod_com != ? and b.flg = 'N' and a.cod_cond is not null "
                    + " and a.cod_int not in (select cod_int from interventi_comuni where cod_com = ? and flg = 'N') "
                    + " ) AS t";
            // PC - ordina allegati
            PreparedStatement ps = db.getPreparedStmt(conn, sql);
            ps.setString(1, codEnte);
            ps.setString(2, language);
            ps.setString(3, language);
            ps.setString(4, language);
            ps.setString(5, language);
            ps.setString(6, language);
            ps.setString(7, language);
            ps.setString(8, codEnte);
            ps.setString(9, codEnte);
            log.info("getInterventiFacoltativi 1: " + ps);
            rs = ps.executeQuery();
            while (rs.next()) {
                String codiceIntervento = rs.getString("cod_int");
                if (codiceIntervento != null
                        && !mappa.containsKey(codiceIntervento)) {
// PC - Reiterazione domanda inizio
                    if (!listaInterventiFacoltativiNew.contains(codiceIntervento)) {
                        listaInterventiFacoltativiNew.add(codiceIntervento);
                    }
                    if (!listaInterventiFacoltativiOld.contains(codiceIntervento)) {
// PC - Reiterazione domanda fine                        
                        InterventoBean bean = new InterventoBean();
                        bean.setCodice(rs.getString("cod_int"));
                        bean.setDescrizione(rs.getString("tit_int"));
                        // PC - ordina allegati
                        bean.setOrdinamento(rs.getInt("ordinamento"));
                        // PC - ordina allegati
                        bean.setCodiceCondizione(rs.getString("cod_cond"));
                        bean.setTestoCondizione(rs.getString("testo_cond"));
                        // bean.setCodiceOperazioneAttivante(rs.getString("cod_ope"));
                        if (bean.getCodiceCondizione() != null
                                && !bean.getCodiceCondizione().equalsIgnoreCase("")) {
                            String sql2 = "SELECT cod_rif FROM condizioni_normative WHERE cod_cond = ?";
                            PreparedStatement ps2 = db.getPreparedStmt(conn, sql2);
                            ps2.setString(1, bean.getCodiceCondizione());
                            log.info("getInterventiFacoltativi 2: " + ps);
                            ResultSet rs2 = ps2.executeQuery();
                            if (rs2.next()) {
                                bean.setCodiceNormativaVisualizzata(rs2.getString("cod_rif"));
                            }
                        }
                        bean.setChecked(false);
                        listaInterventiFacoltativi.add(bean);
// PC - Reiterazione domanda inizio                        
                    } else {
                        for (Iterator it = dataForm.getInterventiFacoltativi().iterator(); it.hasNext();){
                            InterventoBean intervento = (InterventoBean) it.next();
                            if (intervento.getCodice().equals(codiceIntervento)) {
                                listaInterventiFacoltativi.add(intervento);
                            }
                        }
                    }
// PC - Reiterazione domanda fine                    
                    mappa.put(codiceIntervento, codiceIntervento);
                }
            }
// PC - Reiterazione domanda inizio 
            for (Iterator it = listaInterventiFacoltativiOld.iterator(); it.hasNext();) {
                String key = (String) it.next();
                if (!listaInterventiFacoltativiNew.contains(key)) {
                    for (Iterator iter = dataForm.getInterventiFacoltativi().iterator(); iter.hasNext();) {
                        InterventoBean intervento = (InterventoBean) iter.next();
                        if (intervento.getCodice().equals(key)) {
                            listaInterventiFacoltativi.remove(intervento);
                        }
                    }
                }
            }
// PC - Reiterazione domanda fine 
        } catch (SQLException e) {
            log.error(e);
            throw e;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                conn.close();
            } catch (Exception e) {
            }
        }
        return listaInterventiFacoltativi;
    }

    public String getInClauseString(ArrayList lista) {
        String str = "";
        if (lista == null || lista.size() == 0) {
            return str;
        }
        for (Iterator iterator = lista.iterator(); iterator.hasNext();) {
            str += "'" + ((String) iterator.next()) + "'" + ",";
        }
        if (str != null && !"".equalsIgnoreCase(str)) {
            str = str.substring(0, str.lastIndexOf(","));
        }
        return str;
    }

    public String getInClauseNumber(ArrayList lista) {
        String str = "";
        if (lista == null || lista.size() == 0) {
            return str;
        }
        for (Iterator iterator = lista.iterator(); iterator.hasNext();) {
            str += ((String) iterator.next()) + ",";
        }
        if (str != null && !"".equalsIgnoreCase(str)) {
            str = str.substring(0, str.lastIndexOf(","));
        }
        return str;
    }

// PC - Reiterazione domanda inizio 
//    public ArrayList getInterventiFacoltativiDettaglio(
//            ArrayList codInterventiSel) throws Exception {
//        ArrayList listaInterventiFacoltativi = new ArrayList();
//        ResultSet rs = null;
//        Connection conn = null;
//        String inClause = getInClauseNumber(codInterventiSel);
//        try {
//            conn = db.open();
//            // PC - ordina allegati
//            // String sql = " select * from interventi_testi where cod_int in ("
//            // + inClause + ") and cod_lang=?";
//            String sql = " select a.cod_int, a.ordinamento, b.tit_int "
//                    + "from interventi a " + "join interventi_testi b "
//                    + "on a.cod_int = b.cod_int where a.cod_int in ("
//                    + inClause + ") and b.cod_lang=?";
//            // PC - ordina allegati
//            PreparedStatement ps = db.getPreparedStmt(conn, sql);
//            ps.setString(1, language);
//            rs = ps.executeQuery();
//            while (rs.next()) {
//                InterventoBean intervento = new InterventoBean();
//                intervento.setCodice(rs.getString("cod_int"));
//                intervento.setDescrizione(rs.getString("tit_int"));
//                // PC - ordina allegati
//                intervento.setOrdinamento(rs.getInt("ordinamento"));
//                // PC - ordina allegati
//                listaInterventiFacoltativi.add(intervento);
//            }
//        } catch (SQLException e) {
//            log.error(e);
//            throw e;
//        } finally {
//            try {
//                rs.close();
//            } catch (Exception e) {
//            }
//            try {
//                conn.close();
//            } catch (Exception e) {
//            }
//        }
//
//        return listaInterventiFacoltativi;
//    }
// PC - Reiterazione domanda fine 
    public void calcolaAlberoSettori2(String codiceEnte, ArrayList listaSettori)
            throws SQLException {
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = db.open();

            String sql = " select distinct gs.cod_ramo "
                    + " from gerarchia_settori gs join settori_attivita_comuni sac on  gs.cod_sett = sac.cod_sett "
                    + " where  gs.cod_sett in (select distinct b.cod_sett from gerarchia_operazioni a, condizioni_di_attivazione b, interventi c, interventi_comuni d, comuni_aggregazione e"
                    + " 	                 where a.cod_ope = b.cod_ope and a.tip_aggregazione = e.tip_aggregazione and b.tip_aggregazione = e.tip_aggregazione and e.cod_ente = ? "
                    + "                    and   b.cod_int = c.cod_int and b.cod_int = d.cod_int and d.cod_com = ? and d.flg='S' "
                    + "                    union "
                    + "                    select distinct b.cod_sett from gerarchia_operazioni a join comuni_aggregazione e on a.tip_aggregazione = e.tip_aggregazione and e.cod_ente = ? "
                    + "                    join condizioni_di_attivazione b on a.cod_ope = b.cod_ope and b.tip_aggregazione = e.tip_aggregazione  "
                    + "                    left join interventi c on b.cod_int = c.cod_int  "
                    + "                    left join interventi_comuni d on b.cod_int = d.cod_int where d.cod_int is null "
                    + "                    union "
                    + "                    select distinct b.cod_sett from gerarchia_operazioni a join comuni_aggregazione e on a.tip_aggregazione = e.tip_aggregazione and e.cod_ente = ? "
                    + "                    join  condizioni_di_attivazione b on a.cod_ope = b.cod_ope and b.tip_aggregazione = e.tip_aggregazione  "
                    + "                    left join interventi c on b.cod_int = c.cod_int  "
                    + "                    left join interventi_comuni d on b.cod_int = d.cod_int where d.cod_com != ? and d.flg='N' "
                    + "                    and  b.cod_int not in (select cod_int from interventi_comuni where cod_com = ? and flg = 'N') ) "
                    + " and sac.cod_com = ? and sac.flg = 'S' "
                    + " union "
                    + " select distinct gs.cod_ramo from gerarchia_settori gs left join settori_attivita_comuni sac on gs.cod_sett = sac.cod_sett "
                    + " where gs.cod_sett in (select distinct b.cod_sett from gerarchia_operazioni a, condizioni_di_attivazione b, interventi c, interventi_comuni d, comuni_aggregazione e "
                    + "        	      where a.cod_ope = b.cod_ope and a.tip_aggregazione = e.tip_aggregazione and b.tip_aggregazione = e.tip_aggregazione and e.cod_ente = ? "
                    + "                  and b.cod_int = c.cod_int and b.cod_int = d.cod_int and d.cod_com = ? and d.flg='S' "
                    + "                   union "
                    + "                  select distinct b.cod_sett from gerarchia_operazioni a join comuni_aggregazione e on a.tip_aggregazione = e.tip_aggregazione and e.cod_ente = ? "
                    + "                   join condizioni_di_attivazione b on a.cod_ope = b.cod_ope and b.tip_aggregazione = e.tip_aggregazione "
                    + "                  left join interventi c on b.cod_int = c.cod_int "
                    + "                 left join interventi_comuni d on b.cod_int = d.cod_int where d.cod_int is null "
                    + "                  union "
                    + "                  select distinct b.cod_sett from gerarchia_operazioni a "
                    + "                  join comuni_aggregazione e on a.tip_aggregazione = e.tip_aggregazione and e.cod_ente = ? "
                    + "                  join condizioni_di_attivazione b on a.cod_ope = b.cod_ope and b.tip_aggregazione = e.tip_aggregazione  "
                    + "                  left join interventi c on b.cod_int = c.cod_int "
                    + "                 left join interventi_comuni d on b.cod_int = d.cod_int "
                    + "                   where d.cod_com != ? and d.flg='N' "
                    + "                  and  b.cod_int not in (select cod_int from interventi_comuni where cod_com = ? and flg = 'N') ) "
                    + " and  sac.cod_sett is null "
                    + " union "
                    + " select distinct gs.cod_ramo from gerarchia_settori gs left join settori_attivita_comuni sac on gs.cod_sett = sac.cod_sett "
                    + " where gs.cod_sett in (select distinct b.cod_sett from gerarchia_operazioni a, condizioni_di_attivazione b, interventi c, interventi_comuni d, comuni_aggregazione e "
                    + "                  where a.cod_ope = b.cod_ope and a.tip_aggregazione = e.tip_aggregazione and b.tip_aggregazione = e.tip_aggregazione and e.cod_ente = ? "
                    + "                 and b.cod_int = c.cod_int and b.cod_int = d.cod_int and d.cod_com = ? and d.flg='S' "
                    + "                  union "
                    + "                 select distinct b.cod_sett from gerarchia_operazioni a join comuni_aggregazione e on a.tip_aggregazione = e.tip_aggregazione and e.cod_ente = ? "
                    + "                   join condizioni_di_attivazione b on a.cod_ope = b.cod_ope and b.tip_aggregazione = e.tip_aggregazione  "
                    + "                  left join interventi c on b.cod_int = c.cod_int  "
                    + "                  left join interventi_comuni d on b.cod_int = d.cod_int where d.cod_int is null  "
                    + "                  union "
                    + "                  select distinct b.cod_sett from gerarchia_operazioni a join comuni_aggregazione e on a.tip_aggregazione = e.tip_aggregazione and e.cod_ente = ? "
                    + "                  join condizioni_di_attivazione b on a.cod_ope = b.cod_ope and b.tip_aggregazione = e.tip_aggregazione  "
                    + "                  left join interventi c on b.cod_int = c.cod_int  "
                    + "                  left join interventi_comuni d on b.cod_int = d.cod_int "
                    + "         	       where d.cod_com != ? and d.flg='N' "
                    + "                  and b.cod_int not in (select cod_int from interventi_comuni where cod_com = ? and flg = 'N') ) "
                    + " and sac.cod_com != ? and sac.flg='N' "
                    + " and gs.cod_sett not in (select cod_sett from settori_attivita_comuni where cod_com = ? and flg = 'N') ";
            PreparedStatement ps = db.getPreparedStmt(conn, sql);
            for (int i = 1; i <= 21; i++) {
                ps.setString(i, codiceEnte);
            }
            log.info("calcolaAlberoSettori2 1: " + ps);
            rs = ps.executeQuery();
            HashMap mappa = new HashMap();
            ArrayList listaCod = new ArrayList();
            while (rs.next()) {
                String cod_ramo = rs.getString("cod_ramo");
                if (cod_ramo != null) {
                    listaCod.add(cod_ramo);
                    mappa.put(cod_ramo, cod_ramo);
                }
            }

            // query che percorre l'albero dalle foglie fino alla radice
            sql = "SELECT DISTINCT cod_ramo_prec FROM gerarchia_settori WHERE cod_ramo IN ";
            String clause = getInClauseString(listaCod);
            while (clause != null && !clause.equalsIgnoreCase("")) {
                listaCod = new ArrayList();
                PreparedStatement ps2 = db.getPreparedStmt(conn, sql + "("
                        + clause + ")");
                log.info("calcolaAlberoSettori2 2: " + ps);
                ResultSet rs2 = ps2.executeQuery();
                while (rs2.next()) {
                    String cod_ramo = rs2.getString("cod_ramo_prec");
                    if (cod_ramo != null && !mappa.containsKey(cod_ramo)) {
                        mappa.put(cod_ramo, cod_ramo);
                    }
                    listaCod.add(cod_ramo);
                }
                clause = getInClauseString(listaCod);
                try {
                    rs2.close();
                } catch (Exception e) {
                }
            }

            // query per recuperare le radici dei settori (cod_ramo_prec IS
            // NULL)
            sql = "SELECT  a.cod_ramo,  a.cod_ramo_prec,  b.des_ramo,  a.cod_sett,  a.cod_rif, c.des_sett "
                    + " FROM gerarchia_settori AS a "
                    + " LEFT OUTER JOIN gerarchia_settori_testi AS b ON a.cod_ramo = b.cod_ramo AND b.cod_lang = ? "
                    + " LEFT OUTER JOIN settori_attivita AS c ON a.cod_sett=c.cod_sett AND c.cod_lang=? "
                    + " WHERE cod_ramo_prec IS NULL ";
            PreparedStatement ps3 = db.getPreparedStmt(conn, sql);
            ps3.setString(1, language);
            ps3.setString(2, language);
            log.info("calcolaAlberoSettori2 3: " + ps);
            ResultSet rs3 = ps3.executeQuery();
            while (rs3.next()) {
                String codRamo = rs3.getString("cod_ramo");
                if (mappa.containsKey(codRamo)) {
                    SettoreBean settore = new SettoreBean();
                    settore.setCodiceRamo(codRamo);
                    settore.setCodiceRamoPadre(rs3.getString("cod_ramo_prec"));
                    settore.setCodice(rs3.getString("cod_sett"));
                    settore.setDescrizioneRamo(Utilities.NVL(
                            rs3.getString("des_ramo"), "Descrizione mancante"));
                    settore.setDescrizione(Utilities.NVL(
                            rs3.getString("des_sett"),
                            settore.getDescrizioneRamo()));
                    settore.setSelezionato(false);
                    settore.setStringPath(new ArrayList());
                    settore.setProfondita(1);
                    settore.setCod_rif_normativa(rs3.getString("cod_rif"));
                    listaSettori.add(settore);
                    if (settore.getCodice() == null) {// se la condizione e'
                        // vera significa che nn
                        // abbiamo ancora
                        // raggiunto un nodo
                        // foglia
                        ArrayList path = new ArrayList();
                        path.add(settore.getDescrizioneRamo());
                        esploraAlberoRicorsivamente(settore.getCodiceRamo(),
                                mappa, listaSettori, path, conn, 1);
                    }
                }
            }
            // System.out.println("");

        } catch (SQLException e) {
            log.error(e);
            throw e;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                conn.close();
            } catch (Exception e) {
            }
        }
    }

    private void esploraAlberoRicorsivamente(String cod_settore, HashMap mappa,
            ArrayList listaSettori, ArrayList path, Connection conn,
            int profondita) throws SQLException {
        ResultSet rs = null;
        // con questa query recupero tutti i figli che hanno come padre il nodo
        // con codice settore uguale a cod_settere
        String sql = " SELECT  a.cod_ramo,  a.cod_ramo_prec,  b.des_ramo,  a.cod_sett,  a.cod_rif , c.des_sett"
                + " FROM gerarchia_settori AS a"
                + " LEFT OUTER JOIN gerarchia_settori_testi AS b ON a.cod_ramo = b.cod_ramo AND b.cod_lang = ?"
                + " LEFT OUTER JOIN settori_attivita AS c ON a.cod_sett=c.cod_sett AND c.cod_lang=? "
                + "WHERE cod_ramo_prec = ? ";
        PreparedStatement ps = db.getPreparedStmt(conn, sql);
        ps.setString(1, language);
        ps.setString(2, language);
        ps.setString(3, cod_settore);
        log.info("esploraAlberoRicorsivamente: " + ps);
        rs = ps.executeQuery();
        while (rs.next()) {
            String codRamo = rs.getString("cod_ramo");
            if (mappa.containsKey(codRamo)) {
                SettoreBean settore = new SettoreBean();
                settore.setCodiceRamo(codRamo);
                settore.setCodiceRamoPadre(rs.getString("cod_ramo_prec"));
                settore.setCodice(rs.getString("cod_sett"));
                settore.setDescrizioneRamo(Utilities.NVL(
                        rs.getString("des_ramo"), "Descrizione mancante"));
                settore.setDescrizione(Utilities.NVL(rs.getString("des_sett"),
                        settore.getDescrizioneRamo()));
                settore.setSelezionato(false);
                settore.setStringPath(path);
                settore.setProfondita(profondita + 1);
                settore.setCod_rif_normativa(rs.getString("cod_rif"));
                listaSettori.add(settore);
                if (settore.getCodice() == null) { // non siamo ancora arrivati
                    // ad una foglia
                    ArrayList pathAlbero = new ArrayList();
                    for (Iterator iterator = path.iterator(); iterator.hasNext();) {
                        String p = (String) iterator.next();
                        pathAlbero.add(p);
                    }
                    pathAlbero.add(settore.getDescrizioneRamo());
                    esploraAlberoRicorsivamente(settore.getCodiceRamo(), mappa,
                            listaSettori, pathAlbero, conn, profondita + 1);
                }
            }
        }

    }

    public void calcolaAlberoOperazioni(String codEnte,
            String tipoAggregazione, ArrayList listaOperazioni,
            String codSettore) throws SQLException {
        ResultSet rs = null;
        Connection conn = null;
        try {
//        	log.info("db.toString(): " + db.toString());
//        	log.info("db.urlDB: " + db.urlDB);
//        	log.info("db.getUserName(): " + db.getUserName());
//        	log.info("db.getPwd(): " + db.getPwd());
//        	log.info("db.getResourceName(): " + db.getResourceName());
//        	log.info("db.getDATABASE(): " + db.getDATABASE());
//        	log.info("db.getDatabaseType(): " + db.getDatabaseType());
//        	log.info("db.getDriver(): " + db.getDriver());
//        	log.info("db.getProvider(): " + db.getProvider());
//        	log.info("db.getDs(): " + db.getDs());
//        	log.info("db.getDs().getConnection(): " + db.getDs().getConnection());
        	
        	
        	
        	
        	
        	conn = db.open();
//        	log.info("conn: " + conn);
        	
            // questa quey recupera tutte le operazioni foglia dell'albero delle
            // operazioni in base al settore scelto
            String sql = "SELECT DISTINCT a.cod_ramo, a.cod_ramo_prec "
                    + " FROM gerarchia_operazioni a, comuni_aggregazione b "
                    + " WHERE a.tip_aggregazione = b.tip_aggregazione AND b.cod_ente=? AND  a.cod_ramo IN "
                    + " 	(SELECT DISTINCT d.cod_ramo "
                    + " 	FROM condizioni_di_attivazione a "
                    + " 	INNER JOIN interventi b ON a.cod_int = b.cod_int "
                    + " 	INNER JOIN gerarchia_operazioni d ON a.cod_ope = d.cod_ope "
                    + " 	INNER JOIN comuni_aggregazione e ON d.tip_aggregazione = e.tip_aggregazione AND e.cod_ente=? "
                    + " 	LEFT JOIN interventi_comuni c ON a.cod_int = c.cod_int "
                    + " 	WHERE a.tip_aggregazione = e.tip_aggregazione AND a.cod_sett=? AND c.cod_int IS NULL "
                    + " 	UNION "
                    + " 	SELECT DISTINCT d.cod_ramo "
                    + " 	FROM condizioni_di_attivazione a, interventi b, interventi_comuni c, gerarchia_operazioni d, comuni_aggregazione e "
                    + " 	WHERE a.tip_aggregazione = e.tip_aggregazione AND a.cod_sett=? AND a.cod_int = b.cod_int AND a.cod_int = c.cod_int AND a.cod_ope = d.cod_ope AND c.cod_com=? AND c.flg='S' AND d.tip_aggregazione = e.tip_aggregazione AND e.cod_ente=? "
                    + " 	UNION "
                    + " 	SELECT DISTINCT d.cod_ramo "
                    + " 	FROM condizioni_di_attivazione a "
                    + " 	JOIN interventi b ON a.cod_int = b.cod_int "
                    + " 	JOIN gerarchia_operazioni d ON a.cod_ope = d.cod_ope "
                    + " 	JOIN comuni_aggregazione e ON d.tip_aggregazione = e.tip_aggregazione AND e.cod_ente=? "
                    + " 	LEFT JOIN interventi_comuni c ON a.cod_int = c.cod_int "
                    + " 	WHERE a.tip_aggregazione = e.tip_aggregazione AND a.cod_sett=? AND c.cod_com != ? AND c.flg='N'  AND a.cod_int NOT IN "
                    + " 		(SELECT cod_int FROM interventi_comuni WHERE cod_com=? AND flg =  'N')"
                    + " 	) " + " ORDER BY 2 ";
            
//            log.info("sql: " + sql);
            
            PreparedStatement ps = db.getPreparedStmt(conn, sql);
            ps.setString(1, codEnte);
            ps.setString(2, codEnte);
            ps.setString(3, codSettore);
            ps.setString(4, codSettore);
            ps.setString(5, codEnte);
            ps.setString(6, codEnte);
            ps.setString(7, codEnte);
            ps.setString(8, codSettore);
            ps.setString(9, codEnte);
            ps.setString(10, codEnte);
            
            log.info("calcolaAlberoOperazioni 1: " + ps);
            
            rs = ps.executeQuery();
            
//            log.info("rs: " + rs);

            HashMap mappa = new HashMap();
            ArrayList listaCod = new ArrayList();
            while (rs.next()) {
                String cod_ramo = rs.getString("cod_ramo");
                String cod_ramo_padre = rs.getString("cod_ramo_prec");
                if (cod_ramo != null) {
                    listaCod.add(cod_ramo);
                    mappa.put(cod_ramo, cod_ramo);
                }
                // salvaNodoEAntenati(mappa,cod_ramo,tipoAggregazione,conn);
            }

            String clause = getInClauseString(listaCod);

            while (clause != null && !clause.equalsIgnoreCase("")) {
                listaCod = new ArrayList();
                sql = "select distinct  cod_ramo,  cod_ramo_prec  from   gerarchia_operazioni "
                        + "where cod_ramo in ("
                        + clause
                        + ") and tip_aggregazione = (select tip_aggregazione from comuni_aggregazione where cod_ente = ?)";
                PreparedStatement ps2 = db.getPreparedStmt(conn, sql);
                ps2.setString(1, codEnte);
                log.info("calcolaAlberoOperazioni 2: " + ps);
                ResultSet rs2 = ps2.executeQuery();
                while (rs2.next()) {
                    String cod_ramo = rs2.getString("cod_ramo_prec");
                    if (cod_ramo != null && !mappa.containsKey(cod_ramo)) {
                        listaCod.add(cod_ramo);
                        mappa.put(cod_ramo, cod_ramo);
                    }
                }
                clause = getInClauseString(listaCod);
                try {
                    rs2.close();
                } catch (Exception e) {
                	log.error(e);
                }
            }

            sql = "select distinct a.cod_ramo, a.cod_ramo_prec, b.des_ramo, a.cod_ope, a.cod_rif, a.raggruppamento_check, a.flg_sino from gerarchia_operazioni a "
                    + "join gerarchia_operazioni_testi b on a.cod_ramo = b.cod_ramo and a.tip_aggregazione = b.tip_aggregazione and b.cod_lang = ? "
                    + "where a.cod_ramo_prec IS NULL "
                    + "and  a.tip_aggregazione = (select tip_aggregazione from comuni_aggregazione where cod_ente = ?) "
                    + "order by a.cod_ramo ";
            PreparedStatement ps3 = db.getPreparedStmt(conn, sql);
            ps3.setString(1, language);
            ps3.setString(2, codEnte);
            log.info("calcolaAlberoOperazioni 3: " + ps);
            ResultSet rs3 = ps3.executeQuery();
            while (rs3.next()) {
                String codRamo = rs3.getString("cod_ramo");
                if (mappa.containsKey(codRamo)) {
                    OperazioneBean operazione = new OperazioneBean();
                    operazione.setCodiceOperazione(rs3.getString("cod_ramo"));
                    operazione.setCodicePadre(rs3.getString("cod_ramo_prec"));
                    operazione.setSelezionato(false);
                    operazione.setDescrizioneOperazione(rs3.getString("des_ramo"));
                    operazione.setCodice(rs3.getString("cod_ope"));
                    operazione.setStringPath(new ArrayList());
                    operazione.setProfondita(1);
                    operazione.setCodRif(rs3.getString("cod_rif"));
                    operazione.setRaggruppamentoCheck(rs3.getString("raggruppamento_check"));

                    operazione.setSino(rs3.getString("flg_sino"));

                    listaOperazioni.add(operazione);
                    if (operazione.getCodice() == null) {
                        ArrayList pathAlbero = new ArrayList();
                        pathAlbero.add(operazione.getDescrizioneOperazione());
                        esploraAlberoOperazioniRicorsivamente(
                                operazione.getCodiceOperazione(), codEnte,
                                mappa, listaOperazioni,
                                operazione.getProfondita() + 1, pathAlbero,
                                conn);
                    }

                }
            }
			
            // System.out.println("fine calcolo albero operazioni");
        } catch (Throwable e) {
            log.error(e);
            try {
				throw e;
			} catch (Throwable e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            	log.error(e);
            }
            try {
                conn.close();
            } catch (Exception e) {
            	log.error(e);
            }
        }
    }

    private void esploraAlberoOperazioniRicorsivamente(String cod_operazione,
            String cod_ente, HashMap mappa, ArrayList listaOperazioni,
            int prof, ArrayList path, Connection conn) throws SQLException {
        ResultSet rs = null;
        String sql = "select distinct a.cod_ramo, a.cod_ramo_prec, b.des_ramo, a.cod_ope, a.cod_rif,a.raggruppamento_check, a.flg_sino from gerarchia_operazioni a "
                + "join gerarchia_operazioni_testi b on a.cod_ramo = b.cod_ramo and a.tip_aggregazione = b.tip_aggregazione and b.cod_lang = ? "
                + "where a.cod_ramo_prec= ?"
                + " and  a.tip_aggregazione = (select tip_aggregazione from comuni_aggregazione where cod_ente = ?) "
                + "order by a.cod_ramo ";
        PreparedStatement ps = db.getPreparedStmt(conn, sql);
        ps.setString(1, language);
        ps.setString(2, cod_operazione);
        ps.setString(3, cod_ente);
        log.info("esploraAlberoOperazioniRicorsivamente: " + ps);
        rs = ps.executeQuery();
        while (rs.next()) {
            String codRamo = rs.getString("cod_ramo");
            if (mappa.get(codRamo) != null) {
                OperazioneBean operazione = new OperazioneBean();
                operazione.setCodiceOperazione(codRamo);
                operazione.setCodicePadre(rs.getString("cod_ramo_prec"));
                operazione.setCodice(rs.getString("cod_ope"));
                operazione.setDescrizioneOperazione(rs.getString("des_ramo"));
                operazione.setSelezionato(false);
                operazione.setProfondita(prof);
                operazione.setCodRif(rs.getString("cod_rif"));
                operazione.setStringPath(path);
                operazione.setRaggruppamentoCheck(rs.getString("raggruppamento_check"));

                operazione.setSino(rs.getString("flg_sino"));

                listaOperazioni.add(operazione);
                if (operazione.getCodice() == null) {
                    ArrayList pathAlbero = new ArrayList();
                    for (Iterator iterator = path.iterator(); iterator.hasNext();) {
                        String p = (String) iterator.next();
                        pathAlbero.add(p);
                    }
                    pathAlbero.add(operazione.getDescrizioneOperazione());
                    esploraAlberoOperazioniRicorsivamente(
                            operazione.getCodiceOperazione(), cod_ente, mappa,
                            listaOperazioni, prof + 1, pathAlbero, conn);
                }
            }
        }
    }

    private int salvaNodoEAntenati(HashMap mappa, String cod_ramo,
            String tipoAggregazione, Connection conn) throws SQLException {
        ResultSet rs = null;
        OperazioneBean operazione = new OperazioneBean();
        try {
            String sqlDettaglio = "SELECT * FROM gerarchia_operazioni " // query
                    // per
                    // recuperare
                    // il
                    // dettaglio
                    // di
                    // ogni
                    // operazione
                    + " INNER JOIN gerarchia_operazioni_testi ON  gerarchia_operazioni.cod_ramo=gerarchia_operazioni_testi.cod_ramo "
                    + " WHERE gerarchia_operazioni.cod_ramo=?  "
                    + " AND cod_lang=? AND gerarchia_operazioni.tip_aggregazione=? "
                    + " AND gerarchia_operazioni.tip_aggregazione=gerarchia_operazioni_testi.tip_aggregazione  "
                    + " ORDER BY gerarchia_operazioni.cod_ramo ";

            PreparedStatement ps = db.getPreparedStmt(conn, sqlDettaglio);
            ps.setString(1, cod_ramo);
            ps.setString(2, language);
            ps.setString(3, tipoAggregazione);
            log.info("salvaNodoEAntenati: " + ps);
            rs = ps.executeQuery();
            if (rs.next()) {
                String cod_padre = rs.getString("cod_ramo_prec");

                operazione.setCodiceOperazione(rs.getString("cod_ramo"));
                operazione.setCodicePadre(cod_padre);
                operazione.setSelezionato(false);
                operazione.setDescrizioneOperazione(rs.getString("des_ramo"));
                operazione.setCodice(rs.getString("cod_ope"));
                if (operazione.getCodicePadre() == null) {
                    operazione.setProfondita(1);
                } else {
                    operazione.setProfondita(salvaNodoEAntenati(mappa,
                            cod_padre, tipoAggregazione, conn) + 1);
                }
                if (mappa.get(cod_ramo) == null) {
                    mappa.put(operazione.getCodiceOperazione(), operazione);
                }
            }
        } catch (SQLException e) {
            log.error(e);
            throw e;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
        }
        return operazione.getProfondita();
    }

    public ArrayList[] getListaAllegati(ProcessData dataForm) {
        ArrayList[] liste = new ArrayList[2];

        return liste;
    }

    public void calcolaAllegatiPerInterventi(ArrayList listaInterventi,
            Map listaAllegati, String codiceComune) throws Exception {
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = db.open();
            String sql = " SELECT allegati.cod_doc, cod_int, flg_autocert, copie, cod_cond, documenti_documenti.nome_file, flg_dic, "
                    + " href, tit_doc, des_doc, allegati.flg_obb,allegati.tipologie,allegati.num_max_pag,allegati.dimensione_max "
                    + // PC - nuovi campi allegati
                    " ,allegati.ordinamento "
                    + // PC - nuovi campi allegati
                    " , allegati.flg_verifica_firma FROM allegati INNER JOIN documenti ON allegati.cod_doc=documenti.cod_doc "
                    + " INNER JOIN documenti_testi ON documenti.cod_doc=documenti_testi.cod_doc "
                    + " LEFT JOIN documenti_documenti ON documenti_documenti.cod_doc = documenti.cod_doc "
                    + " JOIN ( SELECT a.cod_doc "
                    + " FROM  allegati a "
                    + " JOIN  allegati_comuni b "
                    + " ON  a.cod_doc = b.cod_doc "
                    + " AND b.cod_com = ? "
                    + " AND a.cod_int = ? "
                    + " AND b.flg = 'S' "
                    + " UNION "
                    + " SELECT a.cod_doc "
                    + " FROM allegati a "
                    + " LEFT JOIN allegati_comuni b "
                    + " ON a.cod_doc = b.cod_doc "
                    + " WHERE a.cod_int = ? "
                    + " AND b.cod_com IS NULL "
                    + " UNION "
                    + " SELECT a.cod_doc "
                    + " FROM allegati a "
                    + " LEFT JOIN allegati_comuni b "
                    + " ON a.cod_doc = b.cod_doc "
                    + " WHERE b.cod_com != ? "
                    + " AND a.cod_int = ? "
                    + " AND b.flg = 'N' "
                    + " AND a.cod_doc NOT IN (SELECT cod_doc FROM allegati_comuni WHERE cod_com = ? AND flg = 'N') "
                    + " ) alle "
                    + " ON alle.cod_doc = allegati.cod_doc "
                    + " WHERE cod_int = ? AND cod_cond IS NULL AND documenti_testi.cod_lang=? "
                    + // PC - nuovi campi allegati
                    " order by allegati.ordinamento ASC";
            ArrayList ar = listaInterventiSorted(listaInterventi);
            // for (Iterator iterator = listaInterventi.iterator();
            // iterator.hasNext();) {
            for (Iterator iterator = ar.iterator(); iterator.hasNext();) {
// indicatore scelta allagti facoltativi radio button o check
                // PC - nuovi campi allegati
                InterventoBean intervento = (InterventoBean) iterator.next();
                boolean indicatoreOptionAllegati = getSportelloDaIntervento(intervento.getCodice(), codiceComune, conn);
                PreparedStatement ps = db.getPreparedStmt(conn, sql);
                ps.setString(1, codiceComune);
                ps.setString(2, intervento.getCodice());
                ps.setString(3, intervento.getCodice());
                ps.setString(4, codiceComune);
                ps.setString(5, intervento.getCodice());
                ps.setString(6, codiceComune);
                ps.setString(7, intervento.getCodice());
                ps.setString(8, language);
                log.info("calcolaAllegatiPerInterventi: " + ps);
                rs = ps.executeQuery();
                while (rs.next()) {
                    String codiceDoc = rs.getString("cod_doc");
                    boolean isPresent = false;
                    for (Iterator iterator2 = intervento.getListaCodiciAllegati().iterator(); iterator2.hasNext();) {
                        String cod = (String) iterator2.next();
                        if (codiceDoc.equalsIgnoreCase(cod)) {
                            isPresent = true;
                        }
                    }
                    if (!isPresent) {
                        intervento.addListaCodiciAllegati(codiceDoc);
                    }
                    if (!listaAllegati.containsKey(codiceDoc)) {
                        AllegatoBean allegato = new AllegatoBean();
                        allegato.setCodice(codiceDoc);
                        allegato.setFlagAutocertificazione(rs.getString("flg_autocert"));
                        allegato.setCopie(rs.getString("copie"));
                        allegato.setCodiceCondizione(rs.getString("cod_cond"));
                        allegato.setNomeFile(rs.getString("nome_file"));
                        allegato.setFlagDic(rs.getString("flg_dic"));
                        allegato.setHref(rs.getString("href"));
                        allegato.setTitolo(rs.getString("tit_doc"));
                        allegato.setDescrizione(rs.getString("des_doc"));

                        String flg_obblString = rs.getString("flg_obb");
                        if (flg_obblString != null && flg_obblString.equalsIgnoreCase("S")) {
                            allegato.setFlg_obb(true);
                        } else {
                            allegato.setFlg_obb(false);
                        }
                        allegato.setTipologieAllegati(rs.getString("tipologie"));
                        allegato.setNum_max_pag(rs.getString("num_max_pag"));
                        allegato.setDimensione_max(rs.getString("dimensione_max"));
                        allegato.setSignVerify(rs.getInt("flg_verifica_firma") == 1);
                        // PC - nuovi campi allegati
                        allegato.setOrdinamento(rs.getInt("ordinamento"));
                        allegato.setOrdinamentoIntervento(intervento.getOrdinamento());
                        // PC - nuovi campi allegati
                        allegato.setIndicatoreOptionAllegati(indicatoreOptionAllegati);
                        listaAllegati.put(codiceDoc, allegato);
                    } else {
                        AllegatoBean allegato = (AllegatoBean) listaAllegati.get(codiceDoc);
                        if (!allegato.isIndicatoreOptionAllegati() && indicatoreOptionAllegati) {
                            allegato.setIndicatoreOptionAllegati(indicatoreOptionAllegati);
                        }
// PC - calcola corretto flg obbligatorieta' inizio
                        if (!allegato.isFlg_obb()) {
                            String flg_obblString = rs.getString("flg_obb");
                            if (flg_obblString != null && flg_obblString.equalsIgnoreCase("S")) {
                                allegato.setFlg_obb(true);
                            }
                        }
// PC - calcola corretto flg obbligatorieta' fine 						
                    }
                }
                rs.close();
                ps.close();
            }
        } catch (SQLException e) {
            log.error(e);
            throw e;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                conn.close();
            } catch (Exception e) {
            }
        }
    }

    public ArrayList getAllegatiFacoltativi(ProcessData dataForm,
            Map nuovaListaAllegatiObbligatori) throws SQLException {
        ResultSet rs = null;
        Connection conn = null;
        ArrayList listaAllegatiFacoltativi = new ArrayList();
        ArrayList listaCodiciInterventi = new ArrayList();
        for (Iterator iterator = dataForm.getInterventi().iterator(); iterator.hasNext();) {
            InterventoBean intervento = (InterventoBean) iterator.next();
            listaCodiciInterventi.add(intervento.getCodice());
        }
        for (Iterator iterator = dataForm.getInterventiFacoltativi().iterator(); iterator.hasNext();) {
            InterventoBean intervento = (InterventoBean) iterator.next();
            listaCodiciInterventi.add(intervento.getCodice());
        }

// PC - Reiterazione domanda inizio 
        List<String> listaAllegatiFacoltativiOld = new ArrayList<String>();
        for (Iterator it = dataForm.getListaAllegatiFacoltativi().keySet().iterator(); it.hasNext();) {
            listaAllegatiFacoltativiOld.add((String) it.next());
        }
        List<String> listaAllegatiFacoltativiNew = new ArrayList<String>();
// PC - Reiterazione domanda fine        

        try {
            String inClause = getInClauseNumber(listaCodiciInterventi);
            if (inClause != null && !inClause.equalsIgnoreCase("")) {
                conn = db.open();
                HashMap mappaTmp = new HashMap();
                String sql = "select allegati.cod_doc, allegati.cod_int, allegati.flg_autocert, allegati.copie, "
                        + " allegati.cod_cond,  documenti_documenti.nome_file, "
                        + " documenti.flg_dic, documenti.href, documenti_testi.tit_doc, documenti_testi.des_doc,  "
                        + " allegati.flg_obb,allegati.tipologie,allegati.num_max_pag,allegati.dimensione_max "
                        // campi aggiunti per i bandi
                        // PC - nuovi campi allegati
                        + " ,allegati.ordinamento, interventi.ordinamento ordinamento_intervento, "
                        + " s.flg_option_allegati flg_option_allegati "
                        + // PC - nuovi campi allegati
                        " , allegati.flg_verifica_firma from allegati inner join documenti on allegati.cod_doc=documenti.cod_doc "
                        + " join documenti_testi on documenti.cod_doc=documenti_testi.cod_doc "
                        + " join interventi on interventi.cod_int=allegati.cod_int "
                        + " left join documenti_documenti on documenti_documenti.cod_doc = documenti.cod_doc "
                        + " join "
                        + " ( select a.cod_doc "
                        + " from  allegati a "
                        + " join  allegati_comuni b "
                        + " on  a.cod_doc = b.cod_doc "
                        + " and b.cod_com = ? "
                        + " and a.cod_int IN ("
                        + inClause
                        + ") "
                        + " and b.flg = 'S' "
                        + " union "
                        + " select a.cod_doc "
                        + " from allegati a "
                        + " left join allegati_comuni b "
                        + " on a.cod_doc = b.cod_doc "
                        + " where a.cod_int IN ("
                        + inClause
                        + ") "
                        + " and b.cod_com is null "
                        + " union "
                        + " select a.cod_doc "
                        + " from allegati a "
                        + " left join allegati_comuni b "
                        + " on a.cod_doc = b.cod_doc "
                        + " where b.cod_com != ? "
                        + " and a.cod_int IN ("
                        + inClause
                        + ") "
                        + " and b.flg = 'N' "
                        + " and a.cod_int not in (select cod_int from allegati_comuni where cod_com = ? and flg = 'N') "
                        + " ) alle "
                        + " on alle.cod_doc = allegati.cod_doc "
                        + " join interventi i "
                        + " on i.cod_int = allegati.cod_int "
                        + " join procedimenti p "
                        + " on p.cod_proc = i.cod_proc "
                        + " join relazioni_enti re "
                        + " on re.cod_cud = p.cod_cud "
                        + " and re.cod_com = ? "
                        + " join sportelli s "
                        + " on re.cod_sport = s.cod_sport "
                        + " where allegati.cod_int IN ("
                        + inClause
                        + ") and cod_cond IS NOT NULL and documenti_testi.cod_lang=?"
                        + // PC - nuovi campi allegati
                        " order by interventi.ordinamento, allegati.ordinamento ASC";
                // PC - nuovi campi allegati

                PreparedStatement ps = db.getPreparedStmt(conn, sql);
                ps.setString(1, dataForm.getComuneSelezionato().getCodEnte());
                ps.setString(2, dataForm.getComuneSelezionato().getCodEnte());
                ps.setString(3, dataForm.getComuneSelezionato().getCodEnte());
                ps.setString(4, dataForm.getComuneSelezionato().getCodEnte());
                ps.setString(5, language);
                log.info("getAllegatiFacoltativi 1: " + ps);
                rs = ps.executeQuery();
                while (rs.next()) {
                    String codiceDoc = rs.getString("cod_doc");
                    if (!nuovaListaAllegatiObbligatori.containsKey(codiceDoc)) {
// PC - Reiterazione domanda inizio
                        if (!listaAllegatiFacoltativiNew.contains(codiceDoc)) {
                            listaAllegatiFacoltativiNew.add(codiceDoc);
                        }
//                        if (dataForm.getListaAllegatiFacoltativi().isEmpty()) {
                        if (!dataForm.getListaAllegatiFacoltativi().containsKey(codiceDoc)) {

// PC - Reiterazione damanda fine                            
                            if (!mappaTmp.containsKey(codiceDoc)/*
                                     * && !dataForm. getListaAllegati
                                     * ().containsKey( codiceDoc)
                                     */) {

                                mappaTmp.put(codiceDoc, codiceDoc);
                                AllegatoBean allegato = new AllegatoBean();
                                allegato.setCodice(codiceDoc);
                                allegato.setFlagAutocertificazione(rs.getString("flg_autocert"));
                                allegato.setCopie(rs.getString("copie"));
                                allegato.setCodiceCondizione(rs.getString("cod_cond"));
                                String strQuery = "SELECT testo_cond,cod_rif FROM testo_condizioni LEFT OUTER JOIN condizioni_normative ON condizioni_normative.cod_cond=testo_condizioni.cod_cond WHERE testo_condizioni.cod_cond=? AND testo_condizioni.cod_lang=?";
                                PreparedStatement ps2 = db.getPreparedStmt(conn,
                                        strQuery);
                                ps2.setString(1, allegato.getCodiceCondizione());
                                ps2.setString(2, language);
                                log.info("getAllegatiFacoltativi 2: " + ps);
                                ResultSet rs2 = ps2.executeQuery();
                                if (rs2.next()) {
                                    allegato.setTestoCondizione(rs2.getString("testo_cond"));
                                    allegato.setCodiceNormativaVisulizzata(rs2.getString("cod_rif"));
                                }

                                allegato.setNomeFile(rs.getString("nome_file"));
                                allegato.setFlagDic(rs.getString("flg_dic"));
                                allegato.setHref(rs.getString("href"));
                                allegato.setTitolo(rs.getString("tit_doc"));
                                allegato.setDescrizione(rs.getString("des_doc"));

                                String flg_obblString = rs.getString("flg_obb");
                                if (flg_obblString != null
                                        && flg_obblString.equalsIgnoreCase("S")) {
                                    allegato.setFlg_obb(true);
                                } else {
                                    allegato.setFlg_obb(false);
                                }
                                allegato.setTipologieAllegati(rs.getString("tipologie"));
                                allegato.setNum_max_pag(rs.getString("num_max_pag"));
                                allegato.setDimensione_max(rs.getString("dimensione_max"));
                                allegato.setSignVerify(rs.getInt("flg_verifica_firma") == 1);
                                // PC - nuovi campi allegati
                                allegato.setOrdinamento(rs.getInt("ordinamento"));
                                allegato.setOrdinamentoIntervento(rs.getInt("ordinamento_intervento"));
                                // PC - nuovi campi allegati
                                allegato.setIndicatoreOptionAllegati(rs.getString("flg_option_allegati").equalsIgnoreCase("s"));
                                listaAllegatiFacoltativi.add(allegato);
                            } else {
                                for (Iterator iterator = listaAllegatiFacoltativi.iterator(); iterator.hasNext();) {
                                    AllegatoBean allegato = (AllegatoBean) iterator.next();
                                    if (allegato.getCodice().equals(codiceDoc)) {
                                        if (!allegato.isIndicatoreOptionAllegati() && rs.getString("flg_option_allegati").equalsIgnoreCase("s")) {
                                            allegato.setIndicatoreOptionAllegati(true);
                                        }
                                        // PC - calcola corretto flg obbligatorieta' inizio
                                        if (!allegato.isFlg_obb()) {
                                            String flg_obblString = rs.getString("flg_obb");
                                            if (flg_obblString != null && flg_obblString.equalsIgnoreCase("S")) {
                                                allegato.setFlg_obb(true);
                                            }
                                        }
                                        // PC - calcola corretto flg obbligatorieta' fine 
                                    }
                                }
                            }
// PC - Reiterazione domanda inizio                        
                        } else {
                            if (!mappaTmp.containsKey(codiceDoc)) {
                                mappaTmp.put(codiceDoc, codiceDoc);
                                listaAllegatiFacoltativi.add(dataForm.getListaAllegatiFacoltativi().get(codiceDoc));
                            }
                        }
// PC - Reiterazione domanda fine                        
//                        }
                    }
                }
            }
// PC - Reiterazione domanda inizio 
            for (Iterator it = listaAllegatiFacoltativiOld.iterator(); it.hasNext();) {
                String key = (String) it.next();
                if (!listaAllegatiFacoltativiNew.contains(key)) {
                    listaAllegatiFacoltativi.remove(key);
                }
            }
// PC - Reiterazione domanda fine            
        } catch (SQLException e) {
            log.error(e);
            throw e;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                conn.close();
            } catch (Exception e) {
            }
        }
        return listaAllegatiFacoltativi;
    }

    public String calcolaProcedimenti(ProcessData dataForm,
            boolean isInterventiFacoltativi) throws Exception {
        ResultSet rs = null;
        Connection conn = null;
        String srcPython = "";
        try {
            // resetto la lista dei procedimenti
            if (!isInterventiFacoltativi) {
                // resetto la lista dei procedimenti
                dataForm.setListaProcedimenti(new HashMap());
            }
            ArrayList listaCodInterventi = new ArrayList();
            if (isInterventiFacoltativi) {
                for (Iterator iterator = dataForm.getInterventiFacoltativi().iterator(); iterator.hasNext();) {
                    InterventoBean intervento = (InterventoBean) iterator.next();
                    listaCodInterventi.add(intervento.getCodice());
                }
            } else {
                for (Iterator iterator = dataForm.getInterventi().iterator(); iterator.hasNext();) {
                    InterventoBean intervento = (InterventoBean) iterator.next();
                    listaCodInterventi.add(intervento.getCodice());
                }
            }
            String inClause = getInClauseNumber(listaCodInterventi);
            conn = db.open();
            String sql = "SELECT interventi.cod_int, interventi_testi.tit_int , procedimenti.cod_proc, procedimenti_testi.tit_proc, relazioni_enti.cod_dest, destinatari.cod_ente, "
                    + " enti_comuni_testi.des_ente, enti_comuni.cod_classe_ente, flg_com, cud.cod_cud, relazioni_enti.cod_sport, sportelli_testi.des_sport ,flg_pu, procedimenti.ter_eva, "
                    + " procedimenti.flg_tipo_proc, des_classe_ente, src_pyth, CURDATE()+0 dataodierna, procedimenti.flg_bollo, des_cud, destinatari.cod_dest as codiceDestinatario "
                    + " FROM procedimenti "
                    + " INNER JOIN procedimenti_testi ON procedimenti.cod_proc=procedimenti_testi.cod_proc AND procedimenti_testi.cod_lang = ? "
                    + " LEFT JOIN relazioni_enti ON relazioni_enti.cod_com = ? AND relazioni_enti.cod_cud = procedimenti.cod_cud "
                    + " LEFT JOIN destinatari ON destinatari.cod_dest = relazioni_enti.cod_dest "
                    + " LEFT JOIN enti_comuni ON enti_comuni.cod_ente = destinatari.cod_ente "
                    + " LEFT JOIN enti_comuni_testi ON enti_comuni_testi.cod_ente = enti_comuni.cod_ente AND enti_comuni_testi.cod_lang = ? "
                    + " LEFT JOIN cud ON cud.cod_cud = relazioni_enti.cod_cud  LEFT JOIN cud_testi ON cud.cod_cud = cud_testi.cod_cud AND cud_testi.cod_lang = ? "
                    + " LEFT JOIN classi_enti ON classi_enti.cod_classe_ente = enti_comuni.cod_classe_ente "
                    + " LEFT JOIN classi_enti_testi ON classi_enti_testi.cod_classe_ente = classi_enti.cod_classe_ente AND classi_enti_testi.cod_lang = ? "
                    + " LEFT JOIN sportelli ON sportelli.cod_sport = relazioni_enti.cod_sport "
                    + " LEFT JOIN sportelli_testi ON sportelli.cod_sport = sportelli_testi.cod_sport  AND sportelli_testi.cod_lang=? "
                    + " INNER JOIN interventi ON procedimenti.cod_proc=interventi.cod_proc "
                    + " INNER JOIN interventi_testi ON interventi.cod_int=interventi_testi.cod_int AND interventi_testi.cod_lang=? "
                    + " WHERE interventi.cod_int IN (" + inClause + ") ";

            PreparedStatement ps = db.getPreparedStmt(conn, sql);
            ps.setString(1, language);
            ps.setString(2, dataForm.getComuneSelezionato().getCodEnte());
            ps.setString(3, language);
            ps.setString(4, language);
            ps.setString(5, language);
            ps.setString(6, language);
            ps.setString(7, language);
            log.info("calcolaProcedimenti: " + ps);
            rs = ps.executeQuery();
            while (rs.next()) {
                String codiceIntervento = rs.getString("cod_int");
                String codiceProcedimento = rs.getString("cod_proc");
                if (!dataForm.getListaProcedimenti().containsKey(
                        codiceProcedimento)) {
                    ProcedimentoBean proc = new ProcedimentoBean();
                    proc.setCodiceProcedimento(codiceProcedimento);
                    proc.setCodiceCud(rs.getString("cod_cud"));
                    proc.setDescrizioneCud(rs.getString("des_cud"));
                    proc.setCodiceClasseEnte(rs.getString("cod_classe_ente"));
                    proc.setDescrizioneClasseEnte(rs.getString("des_classe_ente"));
                    proc.setCodiceEnte(rs.getString("cod_ente"));
                    proc.setCodiceSportello(rs.getString("cod_sport"));
                    proc.setDesSportello(rs.getString("des_sport"));
                    proc.setEnte(rs.getString("des_ente"));
                    proc.setFlg_bollo(rs.getString("flg_bollo"));
                    proc.setFlagComune(rs.getString("flg_com"));
                    proc.setFlagProcedimento(rs.getString("flg_tipo_proc"));
                    proc.setFlagPu(rs.getString("flg_pu"));
                    proc.setNome(rs.getString("tit_proc"));
                    proc.setTerminiEvasione(rs.getString("ter_eva"));
                    proc.setTipo(rs.getInt("flg_tipo_proc"));
                    proc.setCodDest(rs.getString("codiceDestinatario"));
                    // proc.setTitoloIntervento(rs.getString("tit_int"));
                    proc.addCodInterventi(codiceIntervento);
                    dataForm.getListaProcedimenti().put(codiceProcedimento,
                            proc);
                } else {
                    ((ProcedimentoBean) dataForm.getListaProcedimenti().get(
                            codiceProcedimento)).addCodInterventi(codiceIntervento);
                }
                String tmp = rs.getString("src_pyth");
                if ((srcPython.equalsIgnoreCase("") || srcPython == null)
                        && tmp != null) {
                    srcPython = tmp;
                }
            }

        } catch (Exception e) {
            log.error(e);
            throw e;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                conn.close();
            } catch (Exception e) {
            }
        }
        return srcPython;
    }

    public void calcolaSportelli(String pythonModule, ProcessData dataForm)
            throws Exception {
        ResultSet rs = null;
        ResultSet rs1 = null;
        Connection conn = null;
        try {
            dataForm.setListaSportelli(new HashMap());
            Input inputBean = new Input();
            inputBean.setComune(dataForm.getComuneSelezionato().getCodEnte());
            Date dataOdierna = new Date();
            long time = dataOdierna.getTime();
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTimeInMillis(time);
            String anno = String.valueOf(gc.get(Calendar.YEAR));
            String mese = String.valueOf(gc.get(Calendar.MONTH) + 1);
            if ((gc.get(Calendar.MONTH) + 1) < 10) {
                mese = "0" + String.valueOf(gc.get(Calendar.MONTH) + 1);
            }
            String giorno = String.valueOf(gc.get(Calendar.DAY_OF_MONTH));
            if (gc.get(Calendar.DAY_OF_MONTH) < 10) {
                giorno = "0" + String.valueOf(gc.get(Calendar.DAY_OF_MONTH));
            }
            inputBean.setDataOdierna(anno + mese + giorno);
            ArrayList listaProc = new ArrayList();
            Set set = dataForm.getListaProcedimenti().keySet();
            for (Iterator iterator = set.iterator(); iterator.hasNext();) {
                String codiceProcedimento = (String) iterator.next();
                ProcedimentoBean procedimento = (ProcedimentoBean) dataForm.getListaProcedimenti().get(codiceProcedimento);
                listaProc.add(procedimento);
            }
            inputBean.setProcedimenti(listaProc);

            Output output = new Output();
            if (pythonModule != null && !pythonModule.equalsIgnoreCase("")) {
                String xmlInput = marshallInput(inputBean);
                xmlInput = xmlInput.replaceAll("ProcedimentoBean", "Procedimento");
                String xmlOutput = PythonRunner(pythonModule, xmlInput);
                // aggiusto l'XML in base alla struttura del mio Bean.
                xmlOutput = xmlOutput.replaceAll("procedimenti", "codProcedimenti");
                xmlOutput = xmlOutput.replaceAll("<codiceProcedimento>", "");
                xmlOutput = xmlOutput.replaceAll("</codiceProcedimento>", "");
                log.debug("WEGO - xmlInput: " + xmlInput);
                log.debug("WEGO - xmlOutput: " + xmlOutput);
                output = unmarshallOutput(xmlOutput);
            }

            conn = db.open();
            String sql = "SELECT * "
                    + " FROM sportelli "
                    + " LEFT OUTER JOIN sportelli_testi ON sportelli.cod_sport=sportelli_testi.cod_sport AND sportelli_testi.cod_lang=? "
                    + " WHERE sportelli.cod_sport=? ";
            for (Iterator iterator = output.getSportelli().iterator(); iterator.hasNext();) {
                SportelloBean sportello = (SportelloBean) iterator.next();
                if (!dataForm.getListaSportelli().containsKey(sportello.getCodiceSportello())) {
                    PreparedStatement ps = db.getPreparedStmt(conn, sql);
                    ps.setString(1, language);
                    ps.setString(2, sportello.getCodiceSportello());
                    log.info("calcolaSportelli: " + ps);
                    rs = ps.executeQuery();
                    ManagerAllegati managerAllegati = new ManagerAllegati();

                    while (rs.next()) {
                        sportello.setCap(rs.getString("cap"));
                        sportello.setCitta(rs.getString("citta"));
                        sportello.setDescrizioneSportello(rs.getString("des_sport"));
                        sportello.setEmail(rs.getString("email"));
                        sportello.setFax(rs.getString("fax"));
                        sportello.setFlgAttivo(rs.getString("flg_attivo"));
                        sportello.setFlgPu(rs.getString("flg_pu"));
                        sportello.setFlgSu(rs.getString("flg_su"));
                        sportello.setFlgOptionAllegati(rs.getString("flg_option_allegati").equalsIgnoreCase("s"));
                        sportello.setFlgOggettoRiepilogo(rs.getString("flg_oggetto_riepilogo").equalsIgnoreCase("s"));
                        sportello.setFlgOggettoRicevuta(rs.getString("flg_oggetto_ricevuta").equalsIgnoreCase("s"));
                        sportello.setIndirizzo(rs.getString("indirizzo"));
                        sportello.setPec(rs.getString("email_cert"));
                        sportello.setProvincia(rs.getString("prov"));
                        sportello.setRup(rs.getString("nome_rup"));
                        sportello.setTelefono(rs.getString("tel"));
                        sportello.setId_mail_server(rs.getString("id_mail_server"));
                        sportello.setId_protocollo(rs.getString("id_protocollo"));
                        sportello.setId_backoffice(rs.getString("id_backoffice"));
                        // <RF - Aggiunta per spostamento parametri PEC e nuova
                        // gestione Connects
                        sportello.setTemplate_oggetto_ricevuta(rs.getString("template_oggetto_ricevuta"));
                        sportello.setTemplate_corpo_ricevuta(rs.getString("template_corpo_ricevuta"));
                        sportello.setTemplate_nome_file_zip(rs.getString("template_nome_file_zip"));
                        sportello.setSend_zip_file(rs.getString("send_zip_file"));
                        sportello.setSend_single_files(rs.getString("send_single_files"));
                        sportello.setSend_xml(rs.getString("send_xml"));
                        sportello.setSend_signature(rs.getString("send_signature"));
                        sportello.setSend_protocollo(rs.getString("send_protocollo_param"));
                        sportello.setTemplate_oggetto_mail_suap(rs.getString("template_oggetto_mail_suap"));
                        sportello.setSend_ricevuta_dopo_protocollazione(rs.getString("send_ricevuta_dopo_protocollazione"));
                        sportello.setSend_ricevuta_dopo_invio_bo(rs.getString("send_ricevuta_dopo_invio_bo"));
                        // RF - Aggiunta per spostamento parametri PEC e nuova
                        // gestione Connects>
                        // <RF - Aggiunta per integrazione PayER
                        sportello.setAe_codice_utente(rs.getString("ae_codice_utente"));
                        sportello.setAe_codice_ente(rs.getString("ae_codice_ente"));
                        sportello.setAe_tipo_ufficio(rs.getString("ae_tipo_ufficio"));
                        sportello.setAe_codice_ufficio(rs.getString("ae_codice_ufficio"));
                        sportello.setAe_tipologia_servizio(rs.getString("ae_tipologia_servizio"));
                        sportello.setAttachmentsUploadUM(rs.getString("allegati_dimensione_max_um"));
                        sportello.setAttachmentsUploadMaximumValue(rs.getInt("allegati_dimensione_max"));
                        sportello.setRiversamentoAutomatico(rs.getString("riversamento_automatico").equalsIgnoreCase("s"));
                        // RF - Aggiunta per integrazione PayER>
                        sportello.setShowPrintBlankTemplate(rs.getString("visualizza_stampa_modello_in_bianco").equalsIgnoreCase("s"));
                        sportello.setShowPDFVersion(rs.getString("visualizza_versione_pdf").equalsIgnoreCase("s"));
                        sportello.setOnLineSign(rs.getString("firma_on_line").equalsIgnoreCase("s"));
                        sportello.setOffLineSign(rs.getString("firma_off_line").equalsIgnoreCase("s"));
                        dataForm.getListaSportelli().put(sportello.getCodiceSportello(), sportello);

                        managerAllegati.setAttachmentsMaxTotalSize(dataForm, rs.getInt("allegati_dimensione_max"), rs.getString("allegati_dimensione_max_um"),
                                (rs.getString("id_mail_server") != null && !rs.getString("id_mail_server").equalsIgnoreCase("")),
                                (rs.getString("id_backoffice") != null && !rs.getString("id_backoffice").equalsIgnoreCase("")));

                        if (dataForm.getAttachmentsMaximunSize() == ProcessData.UNLIMITED_ATTACHMENTS_MAX_TOTAL_SIZE) {
                            sportello.setAttachmentsUploadMaximumSize("illimitata");
                        } else {
                            sportello.setAttachmentsUploadMaximumSize(managerAllegati.convertAttachmentsMaximumTotalSize(
                                    dataForm.getAttachmentsMaximunSize(),
                                    sportello.getAttachmentsUploadUM())
                                    + " "
                                    + sportello.getAttachmentsUploadUM());
                        }

                        for (Iterator iterator2 = sportello.getCodProcedimenti().iterator(); iterator2.hasNext();) {
                            String codProcedimento = (String) iterator2.next();
                            if (dataForm.getListaProcedimenti().containsKey(
                                    codProcedimento)) {
                                ProcedimentoBean proc = (ProcedimentoBean) dataForm.getListaProcedimenti().get(codProcedimento);
                                proc.setCodiceSportello(sportello.getCodiceSportello());
                                proc.setDesSportello(sportello.getDescrizioneSportello());
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error(e);
            throw e;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                rs1.close();
            } catch (Exception e) {
            }
            try {
                conn.close();
            } catch (Exception e) {
            }
        }
    }

    public String PythonRunner(String pythonModule, String xml)
            throws PyException {
        String retVal = "";
        try {
            PythonInterpreter interp = new PythonInterpreter();
            interp.set("par1", new PyString(xml));
            interp.exec(pythonModule);
            PyObject po = interp.get("xmlout");
            retVal = po.toString();
        } catch (PyException pe) {
            log.error(pe);
            throw pe;
        }
        return retVal;
    }

    public String marshallInput(Input obj) throws Exception {
        StringWriter sw = new StringWriter();
        BeanWriter bw = new BeanWriter(sw);
        try {
            bw.getBindingConfiguration().setMapIDs(false);
            bw.getXMLIntrospector().getConfiguration().setAttributesForPrimitives(false);
            bw.setWriteEmptyElements(true);
            bw.enablePrettyPrint();
            bw.writeXmlDeclaration("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            bw.write(obj);
            bw.flush();
            bw.close();
        } catch (Exception e) {
            log.error(e);
            throw e;
        }
        return sw.toString();
    }

    public Output unmarshallOutput(String xml) throws Exception {
        Output newData = null;
        log.debug(xml);
        try {
            BeanReader br = new BeanReader();
            // br.setMatchIDs(false);
            br.getBindingConfiguration().setMapIDs(false);
            br.registerBeanClass(Output.class);
            newData = (Output) br.parse(new StringReader(xml));
        } catch (IOException e) {
            log.error(e);
            throw e;
        } catch (IntrospectionException e) {
            log.error(e);
            throw e;
        } catch (SAXException e) {
            log.error(e);
            throw e;
        }
        return newData;
    }

    public void calcolaNormativePerInterventi(String codCom, ArrayList interventi, HashMap listaNormative) throws Exception {
        ResultSet rs = null;
        Connection conn = null;
        String sql = null;
        try {
            conn = db.open();
            sql = "SELECT    op.cod_int, a.cod_rif, b.tit_rif, b.cod_lang, op.art_rif, a.cod_tipo_rif, a.nome_rif, tr.tipo_rif, nd.nome_file "
                    + "FROM      normative a JOIN      normative_testi b ON        a.cod_rif = b.cod_rif "
                    + "JOIN      norme_interventi op ON        a.cod_rif = op.cod_rif "
                    + "JOIN   tipi_rif tr ON tr.cod_tipo_rif = a.cod_tipo_rif and tr.cod_lang=b.cod_lang "
                    + "JOIN  norme_comuni c ON        a.cod_rif = c.cod_rif "
                    + "LEFT JOIN normative_documenti nd ON a.cod_rif=nd.cod_rif "
                    // forzo normative documenti in italiano
                    + "and nd.cod_lang='it' "
                    // + "and nd.cod_lang=b.cod_lang "
                    + "where       c.cod_com = ? AND       op.cod_int = ? "
                    + "AND       c.flg = 'S' and b.cod_lang=?  UNION   "
                    + "SELECT    op.cod_int, a.cod_rif,  b.tit_rif, b.cod_lang, op.art_rif, a.cod_tipo_rif, a.nome_rif, tr.tipo_rif, nd.nome_file "
                    + "FROM      normative a JOIN      normative_testi b ON        a.cod_rif = b.cod_rif "
                    + "JOIN      norme_interventi op ON        a.cod_rif = op.cod_rif "
                    + "JOIN  tipi_rif tr ON tr.cod_tipo_rif = a.cod_tipo_rif and tr.cod_lang=b.cod_lang "
                    + "LEFT JOIN norme_comuni c ON        a.cod_rif = c.cod_rif "
                    + "LEFT JOIN normative_documenti nd ON a.cod_rif=nd.cod_rif "
                    // forzo normative documenti in italiano
                    + "and nd.cod_lang='it' "
                    // + "and nd.cod_lang=b.cod_lang "
                    + "WHERE     c.cod_rif IS NULL  AND       op.cod_int = ? and b.cod_lang=?";

            for (Iterator iterator = interventi.iterator(); iterator.hasNext();) {
                InterventoBean intervento = (InterventoBean) iterator.next();
                PreparedStatement ps = db.getPreparedStmt(conn, sql);
                ps.setString(1, codCom);
                ps.setString(2, intervento.getCodice());
                ps.setString(3, language);
                ps.setString(4, intervento.getCodice());
                ps.setString(5, language);
                log.info("calcolaNormativePerInterventi: " + ps);
                rs = ps.executeQuery();
                while (rs.next()) {
                    String codNormativa = rs.getString("cod_rif");
                    if (codNormativa != null) {
                        if (!listaNormative.containsKey(codNormativa)) {
                            NormativaBean normativa = new NormativaBean();
                            normativa.setCodRif(codNormativa);
                            normativa.setNomeFile(rs.getString("nome_file"));
                            normativa.setNomeRiferimento(rs.getString("nome_rif"));
                            normativa.setTitoloRiferimento(rs.getString("tit_rif"));
                            listaNormative.put(codNormativa, normativa);
                        }
                        boolean trovato = false;
                        for (Iterator iterator2 = intervento.getListaCodiciNormative().iterator(); iterator2.hasNext();) {
                            String cod = (String) iterator2.next();
                            if (cod.equalsIgnoreCase(codNormativa)) {
                                trovato = true;
                            }
                        }
                        if (!trovato) {
                            intervento.addListaCodiciNormative(codNormativa);
                        }
                    }
                }

            }

        } catch (SQLException e) {
            log.error(e);
            throw e;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                conn.close();
            } catch (Exception e) {
            }
        }

    }
// PC - Reiterazione domanda inizio 
//          public void getDichiarazioniDinamiche(ProcessData dataForm)
            public void getDichiarazioniDinamiche(ProcessData dataForm,boolean isAnonymus)
// PC - Reiterazione damanda fine 
            throws Exception {
        ResultSet rs = null;
        Connection conn = null;
        String sql = null;
// PC - Reiterazione domanda inizio 
        List<String> listaHrefOld = new ArrayList<String>();
        for (Iterator it = dataForm.getListaHref().keySet().iterator(); it.hasNext();) {
            listaHrefOld.add((String) it.next());
        }
        List<String> listaHrefNew = new ArrayList<String>();
// PC - Reiterazione damanda fine         
        try {
            conn = db.open();
            sql = " SELECT href_testi.tit_href, href_testi.piede_href,MAX(href_campi.riga) AS numRiga,"
                    + " MAX(href_campi.posizione) AS maxcol,MAX(href_campi.tp_riga) AS campimultipli, "
                    + " MAX(href_campi.riga) AS lastRiga, MIN(href_campi.riga) AS firstRiga"
                    + " FROM href "
                    + " LEFT OUTER JOIN href_testi ON href.href=href_testi.href AND href_testi.cod_lang=? "
                    + " LEFT OUTER JOIN href_campi ON href.href=href_campi.href "
                    + " WHERE " + " href.href=? GROUP BY href.href ";            

            String sql2 = "SELECT MAX(href_campi.riga) AS lastRiga, MIN(href_campi.riga) AS firstRiga FROM href_campi WHERE href_campi.tp_riga='1' AND href_campi.href=? ";
            Set set = dataForm.getListaAllegati().keySet();
            for (Iterator iterator = set.iterator(); iterator.hasNext();) {
                String key = (String) iterator.next();
                AllegatoBean allegato = (AllegatoBean) dataForm.getListaAllegati().get(key);
                if (allegato.getHref() != null
                        && !allegato.getHref().equalsIgnoreCase("")) {
                    PreparedStatement ps = db.getPreparedStmt(conn, sql);
                    ps.setString(1, language);
                    ps.setString(2, allegato.getHref());
                    log.info("getDichiarazioniDinamiche 1: " + ps);
                    rs = ps.executeQuery();
                    if (rs.next()) {
// PC - Reiterazione domanda inizio
                        if (!listaHrefNew.contains(allegato.getHref())) {
                            listaHrefNew.add(allegato.getHref());
                        }
                        if (dataForm.getListaHref().isEmpty() || !dataForm.getListaHref().containsKey(allegato.getHref())) {
// PC - Reiterazione damanda fine   
                            SezioneCompilabileBean scb = new SezioneCompilabileBean();
                            scb.setHref(allegato.getHref());
                            scb.setTitolo(allegato.getTitolo());
                            // scb.setTitolo(rs.getString("tit_href"));
                            scb.setDescrizione(allegato.getDescrizione());
                            scb.setPiedeHref(Utilities.NVL(
                                    rs.getString("piede_href"), ""));
                            scb.setRowCount(rs.getInt("numRiga"));
                            scb.setTdCount(rs.getInt("maxcol"));
                            // PC - ordinamento allegati
                            scb.setOrdinamento(allegato.getOrdinamento());
                            scb.setOrdinamentoIntervento(allegato.getOrdinamentoIntervento());
                            // PC - ordinamento allegati
                            if (rs.getInt("campimultipli") > 0) {
                                scb.setNumSezioniMultiple(1);
                                ps = db.getPreparedStmt(conn, sql2);
                                ps.setString(1, allegato.getHref());
                                log.info("getDichiarazioniDinamiche 2: " + ps);
                                rs = ps.executeQuery();
                                if (rs.next()) {
                                    scb.setFirstRowCampoMultiplo(rs.getInt("firstRiga"));
                                    scb.setLastRowCampoMultiplo(rs.getInt("lastRiga"));
                                }
                            }
// PC - Reiterazione domanda inizio                            
                            getDettaglioDichiarazioniDinamiche(dataForm, scb, conn, isAnonymus);
// PC - Reiterazione damanda fine                              
                            dataForm.addListaHref(allegato.getHref(), scb);
// PC - Reiterazione domanda inizio
                        }
// PC - Reiterazione damanda fine                         
                    }

                }
            }
// PC - Reiterazione domanda inizio
            for (Iterator it = listaHrefOld.iterator(); it.hasNext();) {
                String key = (String) it.next();
                if (!listaHrefNew.contains(key)) {
                    dataForm.getListaHref().remove(key);
                }
            }
// PC - Reiterazione damanda fine             
            // PC - ordinamento dichiarazioni
            ArrayList ar = listaHrefSorted(dataForm.getListaHref());
            dataForm.setListaHrefOrdered(new ArrayList());
            Iterator it = ar.iterator();
            while (it.hasNext()) {
                dataForm.addListaHrefOrdered((String) it.next());
            }
// PC - ordinamento dichiarazioni
        } catch (SQLException e) {
            log.error(e);
            throw e;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                conn.close();
            } catch (Exception e) {
            }
        }
    }

    // PC - ordinamento dichiarazioni
    public ArrayList listaHrefSorted(HashMap listaHref) {
        ArrayList list = new ArrayList(listaHref.values());
        ArrayList lhm = new ArrayList();
        Comparator customCompare = new Comparator() {

            public int compare(Object o1, Object o2) {
                SezioneCompilabileBean p1 = (SezioneCompilabileBean) o1;
                SezioneCompilabileBean p2 = (SezioneCompilabileBean) o2;
                int value = p1.getOrdinamentoIntervento().compareTo(
                        p2.getOrdinamentoIntervento());
                if (value == 0) {
                    value = p1.getOrdinamento().compareTo(p2.getOrdinamento());
                }
                return value;
            }
        };
        Collections.sort(list, customCompare);
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            SezioneCompilabileBean all = (SezioneCompilabileBean) iterator.next();
            lhm.add(cercaKey(listaHref, all));
        }
        return lhm;
    }

    public String cercaKey(HashMap map, SezioneCompilabileBean value) {
        String ret = null;
        Set ref = map.keySet();
        Iterator it = ref.iterator();
        while (it.hasNext()) {
            Object o = it.next();
            if (map.get(o).equals(value)) {
                ret = (String) o;
            }
        }
        return ret;
    }

    // PC - ordinamento dichiarazioni
    public ArrayList listaInterventiSorted(ArrayList li) {
        ArrayList list = li;
        ArrayList lhm = new ArrayList();
        Comparator customCompare = new Comparator() {

            public int compare(Object o1, Object o2) {
                InterventoBean p1 = (InterventoBean) o1;
                InterventoBean p2 = (InterventoBean) o2;
                int value = p1.getOrdinamento().compareTo(p2.getOrdinamento());
                return value;
            }
        };
        Collections.sort(list, customCompare);
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            InterventoBean all = (InterventoBean) iterator.next();
            lhm.add(all);
        }
        return lhm;
    }

    // PC - ordinamento dichiarazioni
// PC - Reiterazione domanda inizio    
//    public void getDettaglioDichiarazioniDinamiche(ProcessData dataForm,
//            boolean isAnonymus) throws Exception {
//        ResultSet rs = null;
//        Connection conn = null;
//        String sql = null;
//        try {
//            conn = db.open();
//            sql = "SELECT href_campi.*, href_campi_testi.des_campo, " // PC -
//                    // nuovi
//                    // campi
//                    // href
//                    + " href_campi_testi.err_msg, "// PC - nuovi campi href
//                    + " CONCAT(href_campi.href, href_campi.nome) nomecomposto, "
//                    + " CONCAT(href_campi.href, href_campi.campo_collegato) campo_collegato_composto "
//                    + " FROM href_campi "
//                    + " LEFT JOIN  href_campi_testi ON href_campi_testi.contatore = href_campi.contatore AND href_campi_testi.href=href_campi.href AND href_campi_testi.nome=href_campi.nome AND href_campi_testi.cod_lang=? "
//                    + " INNER JOIN href_testi ON href_testi.href=href_campi.href AND href_testi.cod_lang=? "
//                    + " WHERE  "
//                    + " href_campi.href=? ORDER BY riga , posizione";
//            Set set = dataForm.getListaHref().keySet();
//            for (Iterator iterator = set.iterator(); iterator.hasNext();) {
//                String key = (String) iterator.next();
//                SezioneCompilabileBean scb = (SezioneCompilabileBean) dataForm.getListaHref().get(key);
//                if (scb.getHref() != null
//                        && !scb.getHref().equalsIgnoreCase("")) {
//                    PreparedStatement ps = db.getPreparedStmt(conn, sql);
//                    ps.setString(1, language);
//                    ps.setString(2, language);
//                    ps.setString(3, scb.getHref());
//                    rs = ps.executeQuery();
//                    while (rs.next()) {
//                        HrefCampiBean hcb = new HrefCampiBean();
//                        // hcb.setCampiCollegati();
//                        hcb.setMolteplicita(scb.getNumSezioniMultiple());
//                        hcb.setCampo_collegato(rs.getString("campo_collegato"));
//
//                        hcb.setCampo_xml_mod(rs.getString("campo_xml_mod"));
//                        hcb.setContatore(rs.getString("contatore"));
//                        hcb.setControllo(rs.getString("controllo"));
//                        hcb.setDecimali(rs.getInt("decimali"));
//                        hcb.setDescrizione(rs.getString("des_campo"));
//                        hcb.setEdit(rs.getString("edit"));
//                        hcb.setLunghezza(rs.getInt("lunghezza"));
//                        hcb.setNome(rs.getString("nome"));
//                        hcb.setMarcatore_incrociato(rs.getString("marcatore_incrociato"));
//                        hcb.setPrecompilazione(rs.getString("precompilazione"));
//                        // PC - nuovi campi href
//                        hcb.setPattern(rs.getString("pattern"));
//                        hcb.setErr_msg(rs.getString("err_msg"));
//                        // PC - nuovi campi href
//
//                        if (isAnonymus
//                                || dataForm.getTipoBookmark().equalsIgnoreCase(
//                                        Costant.bookmarkTypeLivello2Label)) {
//                            hcb.setNome_xsd(null);
//                            hcb.setWeb_serv(null);
//                            hcb.setCampo_dati(null);
//                            hcb.setCampo_key(null);
//                        } else {
//                            hcb.setNome_xsd(rs.getString("nome_xsd"));
//                            hcb.setWeb_serv(rs.getString("web_serv"));
//                            hcb.setCampo_dati(rs.getString("campo_dati"));
//                            hcb.setCampo_key(rs.getString("campo_key"));
//                        }
//
//                        hcb.setNumCampo(rs.getInt("tp_riga"));
//                        // hcb.setOpzioniCombo(rs.getString(""));
//                        hcb.setPosizione(rs.getInt("posizione"));
//                        hcb.setRaggruppamento_check(rs.getString("raggruppamento_check"));
//                        hcb.setRiga(rs.getInt("riga"));
//                        hcb.setTipo(rs.getString("tipo"));
//                        hcb.setTp_controllo(rs.getString("tp_controllo"));
//                        hcb.setVal_campo_collegato(rs.getString("val_campo_collegato"));
//                        hcb.setValore(rs.getString("valore"));
//                        // hcb.setValoreUtente(rs.getString(""));
//
//                        if (hcb.getTipo().equalsIgnoreCase("R")
//                                || hcb.getTipo().equalsIgnoreCase("C")) {
//                            hcb.setCampiCollegati(getCampiCollegati(
//                                    scb.getHref(), rs.getString("nome")));
//                        }
//                        if (hcb.getTipo().equalsIgnoreCase("L")) {
//                            String sql2 = "SELECT href_campi_valori.val_select as cod, href_campi_valori_testi.des_valore FROM href_campi_valori "
//                                    + " INNER JOIN href_campi_valori_testi ON href_campi_valori.href=href_campi_valori_testi.href "
//                                    + " AND href_campi_valori.nome=href_campi_valori_testi.nome "
//                                    + " AND href_campi_valori.val_select=href_campi_valori_testi.val_select "
//                                    + " AND href_campi_valori_testi.cod_lang=? "
//                                    + " WHERE href_campi_valori.href=? AND href_campi_valori.nome=?";
//                            PreparedStatement ps2 = db.getPreparedStmt(conn,
//                                    sql2);
//                            ps2.setString(1, language);
//                            ps2.setString(2, scb.getHref());
//                            ps2.setString(3, hcb.getNome());
//                            ResultSet rs2 = ps2.executeQuery();
//                            while (rs2.next()) {
//                                BaseBean bb = new BaseBean();
//                                bb.setCodice(rs2.getString("cod"));
//                                bb.setDescrizione(rs2.getString("des_valore"));
//                                hcb.addOpzioniCombo(bb);
//                            }
//                            rs2.close();
//                            ps2.close();
//                        }
//                        scb.addCampi(hcb);
//                    }
//                }
//            }
//
//        } catch (SQLException e) {
//            log.error(e);
//            throw e;
//        } finally {
//            try {
//                rs.close();
//            } catch (Exception e) {
//            }
//            try {
//                conn.close();
//            } catch (Exception e) {
//            }
//        }
//
//    }
    public void getDettaglioDichiarazioniDinamiche(ProcessData dataForm, SezioneCompilabileBean scb, Connection conn, boolean isAnonymus) throws Exception {
        ResultSet rs = null;
        String sql = null;
        try {
            sql = "SELECT href_campi.*, href_campi_testi.des_campo, " // PC -
                    // nuovi
                    // campi
                    // href
                    + " href_campi_testi.err_msg, "// PC - nuovi campi href
                    + " CONCAT(href_campi.href, href_campi.nome) nomecomposto, "
                    + " CONCAT(href_campi.href, href_campi.campo_collegato) campo_collegato_composto "
                    + " FROM href_campi "
                    + " LEFT JOIN  href_campi_testi ON href_campi_testi.contatore = href_campi.contatore AND href_campi_testi.href=href_campi.href AND href_campi_testi.nome=href_campi.nome AND href_campi_testi.cod_lang=? "
                    + " INNER JOIN href_testi ON href_testi.href=href_campi.href AND href_testi.cod_lang=? "
                    + " WHERE  "
                    + " href_campi.href=? ORDER BY riga , posizione";
            if (scb.getHref() != null && !scb.getHref().equalsIgnoreCase("")) {
                PreparedStatement ps = db.getPreparedStmt(conn, sql);
                ps.setString(1, language);
                ps.setString(2, language);
                ps.setString(3, scb.getHref());
                log.info("getDettaglioDichiarazioniDinamiche 1: " + ps);
                rs = ps.executeQuery();
                while (rs.next()) {
                    HrefCampiBean hcb = new HrefCampiBean();
                    // hcb.setCampiCollegati();
                    hcb.setMolteplicita(scb.getNumSezioniMultiple());
                    hcb.setCampo_collegato(rs.getString("campo_collegato"));

                    hcb.setCampo_xml_mod(rs.getString("campo_xml_mod"));
                    hcb.setContatore(rs.getString("contatore"));
                    hcb.setControllo(rs.getString("controllo"));
                    hcb.setDecimali(rs.getInt("decimali"));
                    hcb.setDescrizione(rs.getString("des_campo"));
                    hcb.setEdit(rs.getString("edit"));
                    hcb.setLunghezza(rs.getInt("lunghezza"));
                    hcb.setNome(rs.getString("nome"));
                    hcb.setMarcatore_incrociato(rs.getString("marcatore_incrociato"));
                    hcb.setPrecompilazione(rs.getString("precompilazione"));
                    // PC - nuovi campi href
                    hcb.setPattern(rs.getString("pattern"));
                    hcb.setErr_msg(rs.getString("err_msg"));
                    // PC - nuovi campi href

                    if (isAnonymus
                            || dataForm.getTipoBookmark().equalsIgnoreCase(
                                    Costant.bookmarkTypeLivello2Label)) {
                        hcb.setNome_xsd(null);
                        hcb.setWeb_serv(null);
                        hcb.setCampo_dati(null);
                        hcb.setCampo_key(null);
                    } else {
                        hcb.setNome_xsd(rs.getString("nome_xsd"));
                        hcb.setWeb_serv(rs.getString("web_serv"));
                        hcb.setCampo_dati(rs.getString("campo_dati"));
                        hcb.setCampo_key(rs.getString("campo_key"));
                    }

                    hcb.setNumCampo(rs.getInt("tp_riga"));
                    // hcb.setOpzioniCombo(rs.getString(""));
                    hcb.setPosizione(rs.getInt("posizione"));
                    hcb.setRaggruppamento_check(rs.getString("raggruppamento_check"));
                    hcb.setRiga(rs.getInt("riga"));
                    hcb.setTipo(rs.getString("tipo"));
                    hcb.setTp_controllo(rs.getString("tp_controllo"));
                    hcb.setVal_campo_collegato(rs.getString("val_campo_collegato"));
                    hcb.setValore(rs.getString("valore"));
                    // hcb.setValoreUtente(rs.getString(""));

                    if (hcb.getTipo().equalsIgnoreCase("R")
                            || hcb.getTipo().equalsIgnoreCase("C")) {
                        hcb.setCampiCollegati(getCampiCollegati(
                                scb.getHref(), rs.getString("nome")));
                    }
                    if (hcb.getTipo().equalsIgnoreCase("L")) {
                        String sql2 = "SELECT href_campi_valori.val_select as cod, href_campi_valori_testi.des_valore FROM href_campi_valori "
                                + " INNER JOIN href_campi_valori_testi ON href_campi_valori.href=href_campi_valori_testi.href "
                                + " AND href_campi_valori.nome=href_campi_valori_testi.nome "
                                + " AND href_campi_valori.val_select=href_campi_valori_testi.val_select "
                                + " AND href_campi_valori_testi.cod_lang=? "
                                + " WHERE href_campi_valori.href=? AND href_campi_valori.nome=?";
                        PreparedStatement ps2 = db.getPreparedStmt(conn,
                                sql2);
                        ps2.setString(1, language);
                        ps2.setString(2, scb.getHref());
                        ps2.setString(3, hcb.getNome());
                        log.info("getDettaglioDichiarazioniDinamiche 2: " + ps);
                        ResultSet rs2 = ps2.executeQuery();
                        while (rs2.next()) {
                            BaseBean bb = new BaseBean();
                            bb.setCodice(rs2.getString("cod"));
                            bb.setDescrizione(rs2.getString("des_valore"));
                            hcb.addOpzioniCombo(bb);
                        }
                        rs2.close();
                        ps2.close();
                    }
                    scb.addCampi(hcb);
                }
            }
        } catch (SQLException e) {
            log.error(e);
            throw e;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }

        }
    }
    
// PC - Reiterazione domanda fine
    // Restituisce una lista di array contenenti i campi collegati ed il valore
    // del radio
    public ArrayList getCampiCollegati(String href, String nomeCampo)
            throws SQLException {
        ArrayList retVal = new ArrayList();
        String sql = "";
        sql += "select nome, val_campo_collegato from href_campi where href=? and campo_collegato=?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = db.open();
            pstmt = db.getPreparedStmt(conn, sql);
            pstmt.setString(1, href);
            pstmt.setString(2, nomeCampo);
            log.info("getCampiCollegati: " + pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                String[] campoColl = new String[2];
                campoColl[0] = rs.getString("nome");
                campoColl[1] = rs.getString("val_campo_collegato");
                retVal.add(campoColl);
            }
        } catch (SQLException e) {
            log.error(e);
            throw e;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                conn.close();
            } catch (Exception e) {
            }
        }
        return retVal;
    }

    public void getDichiarazioniStatiche(ProcessData dataForm) throws Exception {
        ResultSet rs = null;
        Connection conn = null;
        String sql = null;
        ArrayList<DichiarazioniStaticheBean> dichStats = new ArrayList<DichiarazioniStaticheBean>();
        try {
            conn = db.open();
            sql = "SELECT documenti_testi.cod_doc, documenti_testi.tit_doc, documenti_testi.des_doc FROM documenti "
                    + " INNER JOIN documenti_testi ON documenti.cod_doc=documenti_testi.cod_doc AND documenti_testi.cod_lang=?"
                    + " WHERE documenti.cod_doc=?";
            Set set = dataForm.getListaAllegati().keySet();
            for (Iterator iterator = set.iterator(); iterator.hasNext();) {
                String key = (String) iterator.next();
                AllegatoBean allegato = (AllegatoBean) dataForm.getListaAllegati().get(key);
                if (allegato.getNomeFile() == null
                        && allegato.getFlagDic() != null
                        && allegato.getFlagDic().equalsIgnoreCase("S")
                        && allegato.getHref() == null) {
                    PreparedStatement ps = db.getPreparedStmt(conn, sql);
                    ps.setString(1, language);
                    ps.setString(2, allegato.getCodice());
                    log.info("getDichiarazioniStatiche: " + ps);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        DichiarazioniStaticheBean dicStat = new DichiarazioniStaticheBean();
                        dicStat.setCodice(allegato.getCodice());
                        dicStat.setDescrizione(rs.getString("des_doc"));
                        dicStat.setTitolo(rs.getString("tit_doc"));
                        // PC - ordinamento allegati
                        dicStat.setOrdinamento(allegato.getOrdinamento());
                        dicStat.setOrdinamentoIntervento(allegato.getOrdinamentoIntervento());
                        // PC - ordinamento allegati
                        dataForm.addListaDichiarazioniStatiche(dicStat.getCodice(), dicStat);
                    }
                }
            }
        } catch (SQLException e) {
            log.error(e);
            throw e;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                conn.close();
            } catch (Exception e) {
            }
        }
    }

    public void getModulistica(ProcessData dataForm) throws Exception {
        ResultSet rs = null;
        Connection conn = null;
        String sql = null;
        try {
            conn = db.open();
            sql = "SELECT documenti_documenti.* "
                    + " FROM documenti "
                    + " INNER JOIN documenti_documenti ON documenti.cod_doc=documenti_documenti.cod_doc "
                    // forzo il documento in italiano
                    + " AND documenti_documenti.cod_lang='it' "
                    // + " AND documenti_documenti.cod_lang=? "
                    + " WHERE documenti.cod_doc=?";
            Set set = dataForm.getListaAllegati().keySet();
            for (Iterator iterator = set.iterator(); iterator.hasNext();) {
                String key = (String) iterator.next();
                AllegatoBean allegato = (AllegatoBean) dataForm.getListaAllegati().get(key);
                if (allegato.getNomeFile() != null
                        && allegato.getFlagDic() != null
                        && allegato.getFlagDic().equalsIgnoreCase("N")
                        && allegato.getHref() == null) {
                    PreparedStatement ps = db.getPreparedStmt(conn, sql);
                    // ps.setString(1, language);
                    // ps.setString(2, allegato.getCodice());
                    ps.setString(1, allegato.getCodice());
                    log.info("getModulistica: " + ps);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        ModulisticaBean modulistica = new ModulisticaBean();
                        modulistica.setCodiceDoc(allegato.getCodice());
                        modulistica.setTip_doc(rs.getString("tip_doc"));
                        modulistica.setNomeFile(rs.getString("nome_file"));
                        modulistica.setTitolo(allegato.getTitolo());
                        dataForm.addListaModulistica(allegato.getCodice(),
                                modulistica);
                    }
                }
            }
        } catch (SQLException e) {
            log.error(e);
            throw e;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                conn.close();
            } catch (Exception e) {
            }
        }
    }

    public void getDocumentiRichiesti(ProcessData dataForm) throws Exception {
        ResultSet rs = null;
        Connection conn = null;
        String sql = null;
        try {
            conn = db.open();
            sql = "SELECT documenti_testi.*"
                    + " FROM documenti  "
                    + " INNER JOIN documenti_testi ON documenti.cod_doc=documenti_testi.cod_doc AND documenti_testi.cod_lang=?"
                    + "  WHERE documenti.cod_doc=?";
            Set set = dataForm.getListaAllegati().keySet();
            for (Iterator iterator = set.iterator(); iterator.hasNext();) {
                String key = (String) iterator.next();
                AllegatoBean allegato = (AllegatoBean) dataForm.getListaAllegati().get(key);
                if (allegato.getNomeFile() == null
                        && allegato.getFlagDic() != null
                        && allegato.getFlagDic().equalsIgnoreCase("N")
                        && allegato.getHref() == null) {
                    PreparedStatement ps = db.getPreparedStmt(conn, sql);
                    ps.setString(1, language);
                    ps.setString(2, allegato.getCodice());
                    log.info("getDocumentiRichiesti: " + ps);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        DocumentoBean doc = new DocumentoBean();
                        doc.setCodiceDoc(allegato.getCodice());
                        doc.setDescrizione(rs.getString("des_doc"));
                        doc.setTitolo(rs.getString("tit_doc"));
                        dataForm.addListaDocRichiesti(allegato.getCodice(), doc);
                    }
                }
            }
        } catch (SQLException e) {
            log.error(e);
            throw e;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                conn.close();
            } catch (Exception e) {
            }
        }
    }

    /**
     * Restituisce l'xml rappresentativo di un servizio (bookmark)
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    public String getBookmarkXML(String id, String codEnte, String codEveVita) {
        ResultSet rs = null;
        Connection conn = null;
        String xml = null;

        try {
            conn = db.open();

            String sql = "select bookmark_pointer from servizi where cod_servizio=?";

            if (codEnte != null) {
                sql += " and cod_com = ?";
                if (codEveVita != null) {
                    sql += " and cod_eve_vita = ?";
                }
            }

            PreparedStatement ps = db.getPreparedStmt(conn, sql);
            ps.setInt(1, Integer.parseInt(id));
            if (codEnte != null) {
                ps.setString(2, codEnte);
                if (codEveVita != null) {
                    ps.setInt(3, Integer.parseInt(codEveVita));
                }
            }

            log.info("getBookmarkXML:" + ps);
            rs = db.select(conn, ps, sql);

            if (rs.next()) {
                xml = rs.getString("bookmark_pointer");
            }
            return xml;

        } catch (SQLException e) {
            log.error(e);
            return null;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                conn.close();
            } catch (Exception e) {
            }
        }
    }

    /*
     * Nuova versione come da query di Wego (10/10/2007)
     */
    public ArrayList getOneriAlbero(ProcessData dataForm) throws SQLException {

        ArrayList answer = new ArrayList();

        ResultSet rs = null;
        Connection conn = null;
        String inClause = "'";
        Iterator it = dataForm.getInterventi().iterator();
        while (it.hasNext()) {
            InterventoBean bean = (InterventoBean) it.next();
            inClause += bean.getCodice();
            inClause += "','";
        }
        it = dataForm.getInterventiFacoltativi().iterator();
        while (it.hasNext()) {
            InterventoBean bean = (InterventoBean) it.next();
            inClause += bean.getCodice();
            inClause += "','";
        }
        inClause = inClause.substring(0, inClause.lastIndexOf(",'"));
        try {
            conn = db.open();
            String sql = "";
            sql += "select distinct a.cod_int, d.*, f.des_oneri, f.note, g.nome_file, h.cod_dest, i.ae_codice_utente, i.ae_codice_ente, i.ae_tipo_ufficio, i.ae_codice_ufficio, h.riversamento_automatico, dt.nome_dest ";
            sql += "from interventi a  ";
            sql += "inner join  oneri_interventi c on  a.cod_int=c.cod_int ";
            sql += "inner join  oneri d on  c.cod_oneri=d.cod_oneri ";
            sql += "inner join  oneri_comuni e on  d.cod_oneri = e.cod_oneri ";
            sql += "inner join  oneri_testi f on d.cod_oneri = f.cod_oneri and f.cod_lang=? ";
            sql += "inner join  relazioni_enti h on h.cod_com = ? and h.cod_cud = d.cod_cud ";
            sql += "inner join  destinatari i on h.cod_dest = i.cod_dest ";
            sql += "inner join  destinatari_testi dt on h.cod_dest = dt.cod_dest  and dt.cod_lang=? ";
            sql += "left join oneri_documenti g on d.cod_doc_onere = g.cod_doc_onere ";
            sql += "where a.cod_int in ("
                    + inClause
                    + ") and  a.flg_obb <> 'N' and  e.cod_com = ? and  e.flg='S' ";
            sql += "union ";
            sql += "select distinct a.cod_int, d.*, f.des_oneri, f.note, g.nome_file, h.cod_dest, i.ae_codice_utente, i.ae_codice_ente, i.ae_tipo_ufficio, i.ae_codice_ufficio, h.riversamento_automatico, dt.nome_dest ";
            sql += "from interventi a  ";
            sql += "inner join  oneri_interventi c on  a.cod_int=c.cod_int ";
            sql += "inner join  oneri d on  c.cod_oneri=d.cod_oneri ";
            sql += "inner join  oneri_testi f on d.cod_oneri = f.cod_oneri and f.cod_lang=? ";
            sql += "inner join  relazioni_enti h on h.cod_com = ? and h.cod_cud = d.cod_cud ";
            sql += "inner join  destinatari i on h.cod_dest = i.cod_dest ";
            sql += "inner join  destinatari_testi dt on h.cod_dest = dt.cod_dest and dt.cod_lang=? ";
            sql += "left join  oneri_comuni e on  d.cod_oneri = e.cod_oneri ";
            sql += "left join oneri_documenti g on d.cod_doc_onere = g.cod_doc_onere ";
            sql += "where a.cod_int in (" + inClause
                    + ") and  a.flg_obb <> 'N' and  e.cod_com is null ";
            sql += "union ";
            sql += "select distinct a.cod_int, d.*, f.des_oneri, f.note, g.nome_file, h.cod_dest, i.ae_codice_utente, i.ae_codice_ente, i.ae_tipo_ufficio, i.ae_codice_ufficio, h.riversamento_automatico, dt.nome_dest ";
            sql += "from interventi a  ";
            sql += "inner join  oneri_interventi c on  a.cod_int=c.cod_int ";
            sql += "inner join oneri d on  c.cod_oneri=d.cod_oneri ";
            sql += "inner join  oneri_comuni e on  d.cod_oneri = e.cod_oneri ";
            sql += "inner join  oneri_testi f on d.cod_oneri = f.cod_oneri and f.cod_lang=? ";
            sql += "inner join  relazioni_enti h on h.cod_com = ? and h.cod_cud = d.cod_cud ";
            sql += "inner join  destinatari i on h.cod_dest = i.cod_dest ";
            sql += "inner join  destinatari_testi dt on h.cod_dest = dt.cod_dest and dt.cod_lang=? ";
            sql += "left join oneri_documenti g on d.cod_doc_onere = g.cod_doc_onere  ";
            sql += "where a.cod_int in ("
                    + inClause
                    + ") and  a.flg_obb <> 'N' and  e.cod_com <> ? and e.flg='N' ";
            sql += "and d.cod_oneri not in ( select distinct d.cod_oneri from ";
            sql += "interventi a ";
            sql += "inner join  oneri_interventi c on  a.cod_int=c.cod_int ";
            sql += "inner join oneri d on c.cod_oneri=d.cod_oneri ";
            sql += "inner join oneri_comuni e on d.cod_oneri = e.cod_oneri ";
            sql += "where ";
            sql += "e.cod_com = ? and a.cod_int in (" + inClause
                    + ") and a.flg_obb <> 'N' and e.flg = 'N') ";

            PreparedStatement ps = db.getPreparedStmt(conn, sql);
            ps.setString(1, language);
            ps.setString(2, dataForm.getComuneSelezionato().getCodEnte());
            ps.setString(3, language);

            ps.setString(4, dataForm.getComuneSelezionato().getCodEnte());
            ps.setString(5, language);
            ps.setString(6, dataForm.getComuneSelezionato().getCodEnte());
            ps.setString(7, language);

            ps.setString(8, language);
            ps.setString(9, dataForm.getComuneSelezionato().getCodEnte());
            ps.setString(10, language);

            ps.setString(11, dataForm.getComuneSelezionato().getCodEnte());
            ps.setString(12, dataForm.getComuneSelezionato().getCodEnte());

            log.debug("getOneriAlbero:" + ps);

            rs = ps.executeQuery();

// PC - Reiterazione domanda inizio            
            Set onerianticipatiOld = new TreeSet(new OneriBeanComparator());
            for (Iterator $i = dataForm.getOneriAnticipati().iterator(); $i.hasNext();) {
                onerianticipatiOld.add($i.next());
            }
            dataForm.setOneriAnticipati(new TreeSet(new OneriBeanComparator()));  
            ArrayList oneriAlberoOld = new ArrayList();
            for (Iterator $i = dataForm.getListaAlberoOneri().iterator(); $i.hasNext();){
                oneriAlberoOld.add($i.next());
            }
// PC - Reiterazione domanda inizio            
            dataForm.setOneriPosticipati(new TreeSet(new OneriBeanComparator()));
            // tiro fuori le radici degli oneri
            while (rs.next()) {
                // if(isOnereValido(dataForm, rs.getString("cod_oneri"))){
                
                
            

                    OneriBean bean = new OneriBean();
                    bean.setCodice(rs.getString("cod_oneri"));
                    bean.setDescrizione(rs.getString("des_oneri"));
                    bean.setCod_int(rs.getString("cod_int"));
                    bean.setCodDestinatario(rs.getString("cod_dest"));
                    bean.setDesDestinatario(rs.getString("nome_dest"));
                    bean.setNota(rs.getString("note"));
                    bean.setAe_codice_utente(rs.getString("ae_codice_utente"));
                    bean.setAe_codice_ente(rs.getString("ae_codice_ente"));
                    bean.setAe_tipo_ufficio(rs.getString("ae_tipo_ufficio"));
                    bean.setAe_codice_ufficio(rs.getString("ae_codice_ufficio"));
                    bean.setRiversamentoAutomatico(rs.getString(
                            "riversamento_automatico").equalsIgnoreCase("s") ? true
                                    : false);
                    bean.setCumulabile(rs.getString(
                            "cumulabile").equalsIgnoreCase("s") ? true
                                    : false);
                // Se imp_acc = null e cod_padre = null allora � un onere
                    // posticipato
                    boolean alreadyAdded = false;
                    if ((null == rs.getString("imp_acc") || "".equalsIgnoreCase(rs.getString("imp_acc")))
                            && (null == rs.getString("cod_padre") || "".equalsIgnoreCase(rs.getString("cod_padre")))) {
                        bean.setImporto(-1);
                        if (bean.isCumulabile()) {
                            ArrayList buffer = new ArrayList();
                            buffer.addAll(dataForm.getOneriPosticipati());
                            alreadyAdded = buffer.contains(bean);
                            buffer = null;
                        }
                        if (!bean.isCumulabile() || (bean.isCumulabile() && !alreadyAdded)) {
                            dataForm.getOneriPosticipati().add(bean);
                        }
                    } // .. se valorizzato imp_acc allora � un onere anticipato a
                    // calcolo Fisso
                    else if (!(null == rs.getString("imp_acc") || "".equalsIgnoreCase(rs.getString("imp_acc")))) {
                        bean.setImporto(rs.getDouble("imp_acc"));
                        bean.setDescrizioneAntenato(rs.getString("des_oneri"));
                        if (bean.isCumulabile()) {
                            ArrayList buffer = new ArrayList();
                            buffer.addAll(dataForm.getOneriAnticipati());
                            alreadyAdded = buffer.contains(bean);
                            buffer = null;
                        }
                        if (!bean.isCumulabile() || (bean.isCumulabile() && !alreadyAdded)) {
                            dataForm.getOneriAnticipati().add(bean);
                        }
                    } else {
                    // altrimenti il calcolo dell'onere deve essere calcolato da
                        // formule

                    // risalgo la gerarchia degli oneri con una funzione
                        // ricorsiva ed aggiungo al nodo quanto costruito
                        bean.setCodiceAntenato(bean.getCodice());
                        bean.setDescrizioneAntenato(bean.getDescrizione());

                        if (!(rs.getString("cod_padre") == null || rs.getString(
                                "cod_padre").equals(""))) {

                            addAChild(conn, bean, rs.getInt("cod_padre"),
                                    bean.getCodiceAntenato(),
                                    bean.getDescrizioneAntenato(), dataForm);
                        }
                        if (!bean.isCumulabile() || (bean.isCumulabile() && !answer.contains(bean))) {
                            answer.add(bean);
                        }
                        // }
                    }
// PC - Oneri MIP - inizio 
                    bean.setIdentificativoContabile(rs.getString("identificativo_contabile"));
// PC - Oneri MIP - fine 
                    log.debug(answer.toString());
               
            }
// PC - eiterazione domanda inizio 
                    Set nuovo = new TreeSet(new OneriBeanComparator());
                    for (Iterator iter = dataForm.getOneriAnticipati().iterator(); iter.hasNext();) {
                        OneriBean o = (OneriBean) iter.next();
                        boolean trovato = false;
                        for (Iterator $i = onerianticipatiOld.iterator(); $i.hasNext();){
                            OneriBean old = (OneriBean) $i.next();
                            if (old.getCodice().equals(o.getCodice()) && old.getCod_int().equals(o.getCod_int()) && old.getCodiceAntenato().equals(o.getCodiceAntenato())){
                                nuovo.add(old);
                                trovato = true;
                                break;
                            }
                        }
                        if (!trovato) {
                            nuovo.add(o);
                        }
                    }
                    dataForm.setOneriAnticipati(nuovo);
                    
                    ArrayList array = new ArrayList();
                    for (Iterator iter = answer.iterator(); iter.hasNext();) {
                        OneriBean o = (OneriBean) iter.next();
                        boolean trovato = false;
                        for (Iterator $i = oneriAlberoOld.iterator(); $i.hasNext();){
                            OneriBean old = (OneriBean) $i.next();
                            if (old.getCodice().equals(o.getCodice()) && old.getCod_int().equals(o.getCod_int()) && old.getCodiceAntenato().equals(o.getCodiceAntenato())){
                                array.add(old);
                                trovato = true;
                                break;
                            }
                        }
                        if (!trovato) {
                            array.add(o);
                        }
                    }
                    answer = array;                    
                    
// PC - eiterazione domanda fine              
        } catch (SQLException e) {
            log.error(e);
            throw e;
        } catch (Exception ex) {
            log.error(ex);
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                conn.close();
            } catch (Exception e) {
            }
        }

        return answer;
    }

    /**
     * Metodo ricorsivo usato da getOneriAlbero() per ricostruire l'albero degli
     * oneri a tariffario
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    private void addAChild(Connection conn, OneriBean parent, int cod_padre,
            String codiceAntenato, String descrizioneAntenato,
            ProcessData dataForm) throws SQLException {
        log.debug("addAChild()");
        String sql = "";
        PreparedStatement pstmt = null;
        ResultSet rs1 = null;

        try {
            if (null == conn) {
                conn = db.open();
            }
            sql = "";
            sql += "SELECT oneri_gerarchia_testi.* , oneri_gerarchia.cod_padre, oneri_gerarchia.cod_onere_formula ";
            sql += ", oneri_campi_formula.cod_onere_campo ";
            sql += " FROM oneri_gerarchia ";
            sql += "inner join oneri_gerarchia_testi on  ";
            sql += "oneri_gerarchia.cod_figlio = oneri_gerarchia_testi.cod_figlio  ";
            sql += " left outer join oneri_campi_formula on ";
            sql += " oneri_gerarchia.cod_onere_formula=oneri_campi_formula.cod_onere_formula ";
            sql += "Where  ";
            sql += "oneri_gerarchia.cod_padre=? ";
            sql += "and oneri_gerarchia_testi.cod_lang = ? ";

            // log.debug("addAChild: padre=" + cod_padre);
            HashMap hmCampiGiaCensiti = new HashMap();

            pstmt = db.getPreparedStmt(conn, sql);
            pstmt.setInt(1, cod_padre);
            pstmt.setString(2, language);
            log.info("addAChild: " + pstmt);
            rs1 = pstmt.executeQuery();
            // .. inserisco un nodo e risalgo ricorsivamente la gerarchia
            ArrayList child = new ArrayList();
            while (rs1.next()) {
                if (rs1.getInt("cod_padre") != rs1.getInt("cod_figlio")) {
                    if (hmCampiGiaCensiti.get("" + rs1.getInt("cod_figlio")) == null) {
                        hmCampiGiaCensiti.put("" + rs1.getInt("cod_figlio"),
                                "Y");
                        OneriBean bean = new OneriBean();

                        bean.setCodice(rs1.getString("cod_figlio"));

                        bean.setDescrizione(rs1.getString("des_gerarchia"));
                        bean.setCodiceAntenato(codiceAntenato);
                        bean.setDescrizioneAntenato(descrizioneAntenato);

                        bean.setDesDestinatario(parent.getDesDestinatario());
                        bean.setCodDestinatario(parent.getCodDestinatario());
                        bean.setRiversamentoAutomatico(parent.isRiversamentoAutomatico());
                        bean.setAe_codice_utente(parent.getAe_codice_utente());
                        bean.setAe_codice_ente(parent.getAe_codice_ente());
                        bean.setAe_codice_ufficio(parent.getAe_codice_ufficio());
                        bean.setAe_tipo_ufficio(parent.getAe_tipo_ufficio());
                        bean.setOneriFormula(rs1.getString("cod_onere_formula"));
                        bean.setCampoFormula(rs1.getString("cod_onere_campo"));

                        log.debug(bean.toString());
                        addAChild(conn, bean, rs1.getInt("cod_figlio"),
                                codiceAntenato, descrizioneAntenato, dataForm);
                        child.add(bean);
                    } else {
                        // Aggiunta per gestire formule con più campi formula
                        Iterator it = child.iterator();
                        while (it.hasNext()) {
                            OneriBean bean = (OneriBean) it.next();
                            if (bean.getCodice().equalsIgnoreCase(
                                    "" + rs1.getInt("cod_figlio"))) {
                                OneriBean beanConCampoFormulaUguale = new OneriBean();

                                beanConCampoFormulaUguale.setCodice(rs1.getString("cod_figlio"));

                                beanConCampoFormulaUguale.setDescrizione(rs1.getString("des_gerarchia"));
                                beanConCampoFormulaUguale.setCodiceAntenato(codiceAntenato);
                                beanConCampoFormulaUguale.setDescrizioneAntenato(descrizioneAntenato);

                                beanConCampoFormulaUguale.setOneriFormula(rs1.getString("cod_onere_formula"));
                                beanConCampoFormulaUguale.setCampoFormula(rs1.getString("cod_onere_campo"));

                                bean.getAltriOneri().add(
                                        beanConCampoFormulaUguale);
                            }
                        }
                    }
                }
            }
            parent.setFigli(child);
        } catch (SQLException e) {
            log.error(e);
            throw e;
        } finally {
            // try{ rs.close();}catch(Exception e){}try{
            // conn.close();}catch(Exception e){}
        }

    }

    /**
     * Restituisce una lista di oneriBean rappresentativa dell'albero degli
     * oneri a tariffario
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    public List completeOneri(ArrayList list, String[] oneriSelezionati,
            ProcessData dataForm) throws SQLException {
// PC -  Reiterazione domanda inizio    
        ArrayList oneriOld = new ArrayList();
        for (Iterator iter = dataForm.getOneriAnticipati().iterator(); iter.hasNext();){
                OneriBean oo = (OneriBean) iter.next();
                oneriOld.add(oo);
            } 
      
// PC -  Reiterazione domanda fine        
        ArrayList answer = new ArrayList();

        Iterator it = list.iterator();
        String oneriLista = "";
        for (int i = 0; i < oneriSelezionati.length; i++) {

            oneriLista += oneriSelezionati[i] + ",";
        }

        while (it.hasNext()) {
            OneriBean bean = (OneriBean) it.next();
// PC -  Reiterazione domanda inizio 
//            recurseBean(bean, oneriLista, answer, dataForm);
            recurseBean(bean, oneriLista, answer);
// PC -  Reiterazione domanda fine            

            // answer.add(bean);
        }
// PC -  Reiterazione domanda inizio  
        int index = 0;
        for (Iterator iterator = answer.iterator(); iterator.hasNext(); ){
            OneriBean o = (OneriBean) iterator.next();
            for (Iterator iter = oneriOld.iterator(); iter.hasNext();){
                OneriBean oo = (OneriBean) iter.next();
                if (o.getCodice().equals(oo.getCodice())){
                    answer.set(index, oo);
                }
            }
            index++;
        }
        // dataForm.setOneriAnticipati(new TreeSet(new OneriBeanComparator()));         
        for (Iterator iterator = answer.iterator(); iterator.hasNext();) {
            OneriBean o = (OneriBean) iterator.next();
                           boolean trovato = false;
            for (Iterator iter = dataForm.getOneriAnticipati().iterator(); iter.hasNext();) {
                OneriBean old = (OneriBean) iter.next();
                if (old.getCodice().equals(o.getCodice())&& old.getCodiceAntenato().equals(o.getCodiceAntenato())) {
                    trovato = true;
                    break;
                }

            }
            if (!trovato) {
                dataForm.getOneriAnticipati().add(o);
            }
        }       
// PC -  Reiterazione domanda fine                  

        return answer;
    }

    /**
     * Metodo ricorsivo utilizzato da completeOneri
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
// PC -  Reiterazione domanda inizio      
//    private void recurseBean(OneriBean bean, String listaOneriSelezionati,
//            ArrayList result, ProcessData dataForm) throws SQLException {
    private void recurseBean(OneriBean bean, String listaOneriSelezionati,
            ArrayList result) throws SQLException {        
// PC -  Reiterazione domanda fine        
        List list = bean.getFigli();
        // ArrayList toAdd= new ArrayList();

        if (list.size() > 0) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                OneriBean dBean = (OneriBean) it.next();
                String confronto = dBean.getCampoFormula() + ("|")
                        + (dBean.getOneriFormula()) + ("|")
                        + (dBean.getCodice());
                if (listaOneriSelezionati.indexOf(confronto) != -1) {
                    // ho trovato un onere selezionato

                    DefinizioneCampoFormula definizione = trovaDefinizioneCampoFormula(
                            dBean.getCampoFormula(), dBean.getOneriFormula());
                    dBean.setDefinizione(definizione);

                    if (dBean.getAltriOneri().size() > 0) {
                        Iterator itFigli = dBean.getAltriOneri().iterator();
                        while (itFigli.hasNext()) {
                            OneriBean dBeanFiglio = (OneriBean) itFigli.next();
                            definizione = trovaDefinizioneCampoFormula(
                                    dBeanFiglio.getCampoFormula(),
                                    dBeanFiglio.getOneriFormula());
                            dBeanFiglio.setDefinizione(definizione);
                        }
                    }
// PC -  Reiterazione domanda inizio                      
//                    dataForm.getOneriAnticipati().add(dBean);
// PC -  Reiterazione domanda fine                      
                    result.add(dBean);
                } else {
// PC -  Reiterazione domanda inizio                      
//                    recurseBean(dBean, listaOneriSelezionati, result, dataForm);
                    recurseBean(dBean, listaOneriSelezionati, result);                    
// PC -  Reiterazione domanda fine                    
                }
            }
        } else {
            String confronto1 = bean.getCampoFormula() + ("|")
                    + (bean.getOneriFormula()) + ("|") + (bean.getCodice());
            if (listaOneriSelezionati.indexOf(confronto1) != -1) {
                // ho trovato un onere selezionato
                DefinizioneCampoFormula definizione = trovaDefinizioneCampoFormula(
                        bean.getCampoFormula(), bean.getOneriFormula());
                bean.setDefinizione(definizione);

                if (bean.getAltriOneri().size() > 0) {
                    Iterator itFigli = bean.getAltriOneri().iterator();
                    while (itFigli.hasNext()) {
                        OneriBean dBeanFiglio = (OneriBean) itFigli.next();
                        definizione = trovaDefinizioneCampoFormula(
                                dBeanFiglio.getCampoFormula(),
                                dBeanFiglio.getOneriFormula());
                        dBeanFiglio.setDefinizione(definizione);
                    }
                }
// PC -  Reiterazione domanda inizio  
//                dataForm.getOneriAnticipati().add(bean);
// PC -  Reiterazione domanda fine                 
                result.add(bean);
            } else {
                // .. recurseBean(bean,listaOneriSelezionati);
            }

        }

    }

    /**
     * Restituisce un DefinizioneCampoFormula contenente la formula per
     * l'interpretazione degli oneri a tariffario
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    private DefinizioneCampoFormula trovaDefinizioneCampoFormula(
            String cod_onere_campo, String cod_onere_formula)
            throws SQLException {
        DefinizioneCampoFormula answer = new DefinizioneCampoFormula();

        if (null == cod_onere_campo || cod_onere_campo.equals("")) {
            // TODO probabilmente qui bisogna inserire la ricerca delle formule
            // di quei
            // oneri che non hanno campo
            return answer;
        }

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "";
        try {
            conn = db.open();

            sql = "";
            sql += "SELECT oneri_campi.*, oneri_campi_testi.testo_campo,    ";
            sql += "                          oneri_campi_select_testi.val_select  , ";
            sql += "                          oneri_campi_select_testi.des_val_select  , ";
            sql += "                          oneri_formula.formula,oneri_formula.cod_onere_formula ";
            sql += "         FROM oneri_campi left outer join     ";
            sql += "               oneri_campi_testi on     ";
            sql += "               oneri_campi_testi.cod_lang='" + language
                    + "' and    ";
            sql += "               oneri_campi_testi.cod_onere_campo=oneri_campi.cod_onere_campo    ";
            sql += "              left outer join     ";
            sql += "             oneri_campi_select_testi on    ";
            sql += "             oneri_campi_select_testi.cod_lang='"
                    + language + "' and    ";
            sql += "            oneri_campi_select_testi.cod_onere_campo=oneri_campi.cod_onere_campo    ";
            sql += "            inner join oneri_campi_formula on ";
            sql += "            oneri_campi.cod_onere_campo=oneri_campi_formula.cod_onere_campo  ";
            sql += "            inner join oneri_formula on ";
            sql += "            oneri_campi_formula.cod_onere_formula=oneri_formula.cod_onere_formula            ";
            sql += "         where     ";
            sql += "         oneri_campi.cod_onere_campo=? and ";
            sql += "         oneri_campi_formula.cod_onere_formula=? ";

            pstmt = db.getPreparedStmt(conn, sql);
            pstmt.setString(1, cod_onere_campo);
            pstmt.setString(2, cod_onere_formula);
            log.info("trovaDefinizioneCampoFormula: " + pstmt);
            rs = db.select(conn, pstmt, sql);
            String valoreDefault = "";
            while (rs.next()) {
                answer.setLabel(rs.getString("testo_campo"));
                answer.setTipo(rs.getString("tp_campo"));
                answer.setParteIntera(rs.getInt("lng_campo"));
                answer.setParteDecimale(rs.getInt("lng_dec"));
                if (!(null == rs.getString("val_select") || rs.getString(
                        "val_select").equals(""))) {
                    valoreDefault += rs.getString("val_select") + ",";
                    answer.getIDs().add(rs.getString("val_select"));
                    answer.getDescriptions().add(rs.getString("des_val_select"));
                }
                answer.setFormula(rs.getString("formula"));
                answer.setAccettaValoreZero(rs.getString("accetta_valore_zero").equalsIgnoreCase("s"));
            }
            if (!valoreDefault.equals("")) {
                valoreDefault = valoreDefault.substring(0,
                        valoreDefault.lastIndexOf(","));
                answer.setValoreDefault(valoreDefault);
            }

            log.debug(answer.toString());

        } catch (SQLException e) {
            log.error("trovaDefinizioneCampoFormula:" + cod_onere_campo, e);
            throw e;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                pstmt.close();
            } catch (Exception e) {
            }
            try {
                conn.close();
            } catch (Exception e) {
            }
        }
        return answer;
    }

    /**
     * Per ciascun bean del set di input calcola gli oneri dovuti
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    public void calcolaOneri(Set oneri) throws SQLException {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        PythonInterpreter interp = null;
        String sql = "";
        try {
            conn = db.open();
            Iterator it = oneri.iterator();
            while (it.hasNext()) {
                OneriBean bean = (OneriBean) it.next();
                // .. questo Onere non accede ai campi formula ma a
                // oneri_gerarchia.campo_formula
                if (bean.getCampoFormula().equals("")) {
                    sql = "";
                    sql += "select oneri_formula.formula from oneri_formula  ";
                    sql += "inner join oneri_gerarchia on  ";
                    sql += "oneri_gerarchia.cod_onere_formula = oneri_formula.cod_onere_formula ";
                    sql += " where cod_figlio=? ";
                    pstmt = db.getPreparedStmt(conn, sql);
                    pstmt.setString(1, bean.getCodice());
                } else {// .. questo Onere accede ai campi formula ma a
                    // oneri_campi.campo_formula
                    sql = "";
                    sql += "select oneri_formula.formula from oneri_formula  ";
                    sql += "inner join ";
                    sql += "oneri_campi_formula on  ";
                    sql += "oneri_formula.cod_onere_formula=oneri_campi_formula.cod_onere_formula ";
                    sql += " where oneri_campi_formula.cod_onere_campo=? ";
                    sql += " and oneri_campi_formula.cod_onere_formula=? ";
                    pstmt = db.getPreparedStmt(conn, sql);
                    pstmt.setString(1, bean.getCampoFormula());
                    pstmt.setString(2, bean.getOneriFormula());
                }
                log.info("calcolaOneri: " + pstmt);
                rs = db.select(conn, pstmt, sql);
                if (rs.next()) {

                    String formula = rs.getString("formula");
                    // devo trovare il testo da sostituire prima di fare
                    // interpretare la formula
                    if (!(null == bean.getCampoFormula() || bean.getCampoFormula().equals(""))) {
                        String campo = "\\$" + bean.getCampoFormula() + "\\$";
                        Pattern p = Pattern.compile(campo,
                                Pattern.CASE_INSENSITIVE);
                        Matcher m = p.matcher(formula);
                        String valoreFormula = bean.getValoreFormula();
                        if (null == valoreFormula || valoreFormula.equals("")) {
                            log.error("Attenzione non � stato trovato un valore per la formula con campo:"
                                    + campo);
                            valoreFormula = "0";
                        }
                        try {
                            Double.parseDouble(valoreFormula);
                        } catch (Exception e) {
                            // .. e' una stringa e aggiungo le ""
                            valoreFormula = "\"" + valoreFormula + "\"";
                        }
                        formula = m.replaceAll(valoreFormula);
                    }
                    if (bean.getAltriOneri().size() > 0) {
                        // Significa che abbiamo a che fare con oneri formula
                        // con piu' di un campo formula
                        Iterator itFigli = bean.getAltriOneri().iterator();
                        while (itFigli.hasNext()) {
                            OneriBean beanFiglio = (OneriBean) itFigli.next();
                            if (!(null == beanFiglio.getCampoFormula() || beanFiglio.getCampoFormula().equals(""))) {
                                String campo = "\\$"
                                        + beanFiglio.getCampoFormula() + "\\$";
                                Pattern p = Pattern.compile(campo,
                                        Pattern.CASE_INSENSITIVE);
                                Matcher m = p.matcher(formula);
                                String valoreFormula = beanFiglio.getValoreFormula();
                                if (null == valoreFormula
                                        || valoreFormula.equals("")) {
                                    log.error("Attenzione non e' stato trovato un valore per la formula con campo:"
                                            + campo);
                                    valoreFormula = "0";
                                }

                                try {
                                    Double.parseDouble(valoreFormula);
                                } catch (Exception e) {
                                    // .. e' una stringa e aggiungo le ""
                                    valoreFormula = "\"" + valoreFormula + "\"";
                                }

                                formula = m.replaceAll(valoreFormula);
                            }

                        }
                    }

                    formula = formula.replaceAll("#ValOnere#", "x");
                    // interprete del linguaggio Python

                    // interp.set("a", new PyInteger(450));
                    // interp.set("$1900000$", new PyInteger(52));
                    if (interp == null) {
                        interp = new PythonInterpreter();
                    }
                    interp.exec(formula);
                    PyObject x = interp.get("x");
                    String risultato = x.toString();
                    if (!(null == risultato || risultato.equals(""))) {
                        bean.setImporto(Double.parseDouble(risultato));
                    }
                }

            }
        } catch (SQLException e) {
            log.error("calcolaOneri", e);
            throw e;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                pstmt.close();
            } catch (Exception e) {
            }
            try {
                conn.close();
            } catch (Exception e) {
            }
        }
    }

    public ArrayList getAnagraficaDinamica(ProcessData dataForm, boolean isAnonymus) throws Exception {
        ArrayList listaCampiAnagrafica = new ArrayList();
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = db.open();
            // questa quey recupera tutte le operazioni foglia dell'albero delle operazioni in base al settore scelto
            String sql = "SELECT * "
                    + "FROM anagrafica "
                    + "LEFT OUTER JOIN anagrafica_testi ON anagrafica.contatore=anagrafica_testi.contatore AND anagrafica.nome=anagrafica_testi.nome "
                    + "WHERE anagrafica_testi.cod_lang=? and anagrafica.livello>0 "
                    + "ORDER BY riga,posizione";
            PreparedStatement ps = db.getPreparedStmt(conn, sql);
            ps.setString(1, language);
            log.info("getAnagraficaDinamica 1: " + ps);
            rs = ps.executeQuery();
            while (rs.next()) {
                HrefCampiBean hrefAnagrafica = new HrefCampiBean();
                hrefAnagrafica.setContatore(rs.getString("anagrafica.contatore"));
                hrefAnagrafica.setNome(rs.getString("anagrafica.nome"));
                hrefAnagrafica.setRiga(rs.getInt("riga"));
                hrefAnagrafica.setPosizione(rs.getInt("posizione"));
                hrefAnagrafica.setMolteplicita(Integer.parseInt(Utilities.NVL(rs.getString("tp_riga"), "0")));
                hrefAnagrafica.setTipo(rs.getString("tipo"));
                hrefAnagrafica.setValore(rs.getString("valore"));
                hrefAnagrafica.setControllo(rs.getString("controllo"));
                //hrefAnagrafica.setWeb_serv(rs.getString("web_serv"));
                //hrefAnagrafica.setNome_xsd(rs.getString("nome_xsd"));
                hrefAnagrafica.setLivello(rs.getInt("livello"));
                //hrefAnagrafica.setCampo_key(rs.getString("campo_key"));
                //hrefAnagrafica.setCampo_dati(rs.getString("campo_dati"));
                hrefAnagrafica.setCampo_xml_mod(rs.getString("campo_xml_mod"));
                hrefAnagrafica.setTp_controllo(rs.getString("tp_controllo"));
                hrefAnagrafica.setLunghezza(rs.getInt("lunghezza"));
                hrefAnagrafica.setDecimali(rs.getInt("decimali"));
                hrefAnagrafica.setEdit(rs.getString("edit"));
                // PC - nuovo controllo
                hrefAnagrafica.setPattern(rs.getString("pattern"));
                hrefAnagrafica.setErr_msg(rs.getString("err_msg"));
                // PC - nuovo controllo
                if (isAnonymus || (dataForm.getTipoBookmark() != null && dataForm.getTipoBookmark().equalsIgnoreCase(Costant.bookmarkTypeLivello2Label))) {
                    hrefAnagrafica.setNome_xsd(null);
                    hrefAnagrafica.setWeb_serv(null);
                    hrefAnagrafica.setCampo_dati(null);
                    hrefAnagrafica.setCampo_key(null);
                } else {
                    hrefAnagrafica.setWeb_serv(rs.getString("web_serv"));
                    hrefAnagrafica.setNome_xsd(rs.getString("nome_xsd"));
                    hrefAnagrafica.setCampo_key(rs.getString("campo_key"));
                    hrefAnagrafica.setCampo_dati(rs.getString("campo_dati"));
                }

                hrefAnagrafica.setRaggruppamento_check(rs.getString("raggruppamento_check"));
                hrefAnagrafica.setCampo_collegato(rs.getString("campo_collegato"));
                hrefAnagrafica.setVal_campo_collegato(rs.getString("val_campo_collegato"));
                hrefAnagrafica.setDescrizione(rs.getString("des_campo"));
                hrefAnagrafica.setPrecompilazione(rs.getString("precompilazione"));
                if (hrefAnagrafica.getTipo().equalsIgnoreCase("L")) {
                    String sql2 = "SELECT anagrafica_campi_valori.nome,anagrafica_campi_valori.val_select, anagrafica_campi_valori_testi.des_valore"
                            + " FROM anagrafica_campi_valori "
                            + " LEFT OUTER JOIN anagrafica_campi_valori_testi "
                            + " 	ON  anagrafica_campi_valori.nome=anagrafica_campi_valori_testi.nome "
                            + " 	AND anagrafica_campi_valori.val_select=anagrafica_campi_valori_testi.val_select AND anagrafica_campi_valori_testi.cod_lang=? "
                            + " WHERE anagrafica_campi_valori.nome=?";
                    PreparedStatement ps2 = db.getPreparedStmt(conn, sql2);
                    ps2.setString(1, language);
                    ps2.setString(2, hrefAnagrafica.getNome());
                    log.info("getAnagraficaDinamica 2: " + ps);
                    ResultSet rs2 = ps2.executeQuery();
                    while (rs2.next()) {
                        BaseBean bb = new BaseBean();
                        bb.setCodice(rs2.getString("val_select"));
                        bb.setDescrizione(rs2.getString("des_valore"));
                        hrefAnagrafica.addOpzioniCombo(bb);
                    }

                }
                hrefAnagrafica.setAzione(Utilities.NVL(rs.getString("azione"), ""));
                hrefAnagrafica.setFlg_precompilazione(rs.getString("flg_precompilazione"));
                listaCampiAnagrafica.add(hrefAnagrafica);
            }

        } catch (SQLException e) {
            log.error(e);
            throw e;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                conn.close();
            } catch (Exception e) {
            }
        }
        return listaCampiAnagrafica;
    }

    /**
     * Restituisce una lista di DocumentiBean relativi alle eventuali normative
     * per codRif
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    public List getDocumentiNormative(String codRif) throws SQLException {
        ResultSet rs = null;
        Connection conn = null;
        ArrayList documenti = new ArrayList();
        PreparedStatement ps;

        try {
            conn = db.open();

            String sql = "select n.nome_rif, nd.nome_file from normative n "
                    + "join normative_testi nt on n.cod_rif=nt.cod_rif "
                    + "join normative_documenti nd on nd.cod_rif = n.cod_rif and nd.cod_lang=nt.cod_lang "
                    + "where n.cod_rif = ? and nt.cod_lang = ?";

            ps = db.getPreparedStmt(conn, sql);
            ps.setString(1, codRif);
            ps.setString(2, language);

//            log.debug("DocumentiNormative query:" + sql);
            log.info("getDocumentiNormative 1: " + ps);
            rs = ps.executeQuery();

            while (rs.next()) {
                NormativaBean docBean = new NormativaBean();
                docBean.setNomeFile(rs.getString("nome_file"));
                docBean.setNomeRiferimento(rs.getString("nome_rif"));
                docBean.setCodRif(codRif);

                documenti.add(docBean);
            }
            if (documenti.isEmpty()) {
                rs.close();
                ps.close();
                sql = "select n.nome_rif, nd.nome_file from normative n "
                        + "join normative_testi nt on n.cod_rif=nt.cod_rif "
                        + "join normative_documenti nd on nd.cod_rif = n.cod_rif and nd.cod_lang='it' "
                        + "where n.cod_rif = ? and nt.cod_lang = ?";
                ps = db.getPreparedStmt(conn, sql);
                ps.setString(1, codRif);
                ps.setString(2, language);
                log.info("getDocumentiNormative 2: " + ps);
                rs = ps.executeQuery();
                while (rs.next()) {
                    NormativaBean docBean = new NormativaBean();
                    docBean.setNomeFile(rs.getString("nome_file"));
                    docBean.setNomeRiferimento(rs.getString("nome_rif"));
                    docBean.setCodRif(codRif);

                    documenti.add(docBean);
                }
            }
            return documenti;
        } catch (SQLException e) {
            log.error(e);
            throw e;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                conn.close();
            } catch (Exception e) {
            }
        }
    }

    /**
     * Restituisce una lista di DocumentiBean relativi agli eventuali documenti
     * per codRif
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    public List getDocumentiAllegati(String codDoc) throws SQLException {
        ResultSet rs = null;
        Connection conn = null;
        ArrayList documenti = new ArrayList();
        PreparedStatement ps;

        try {
            conn = db.open();

            String sql = "select dd.nome_file, dd.tip_doc, dt.tit_doc from documenti d "
                    + "join documenti_testi dt on d.cod_doc=dt.cod_doc "
                    + "join documenti_documenti dd on d.cod_doc = dd.cod_doc and dd.cod_lang=dt.cod_lang "
                    + "where d.cod_doc = ? and dt.cod_lang = ? ";
            ps = db.getPreparedStmt(conn, sql);
            ps.setString(1, codDoc);
            ps.setString(2, language);

//            log.debug("DocumentiNormative query:" + sql);
            log.info("getDocumentiAllegati 1: " + ps);
            rs = ps.executeQuery();

            while (rs.next()) {
                AllegatoBean docBean = new AllegatoBean();
                docBean.setNomeFile(rs.getString("nome_file"));
                docBean.setDescrizione(rs.getString("tit_doc"));
                docBean.setCodice(codDoc);
                documenti.add(docBean);
            }
            if (documenti.isEmpty()) {
                rs.close();
                ps.close();
                sql = "select dd.nome_file, dd.tip_doc, d.tit_doc from documenti d "
                        + "join documenti_testi dt on d.cod_doc=dt.cod_doc "
                        + "join documenti_documenti dd on d.cod_doc = dd.cod_doc and dd.cod_lang='it' "
                        + "where d.cod_doc = ? and dt.cod_lang = ? ";
                ps = db.getPreparedStmt(conn, sql);
                ps.setString(1, codDoc);
                ps.setString(2, language);

                log.debug("getDocumentiAllegati 2:" + sql);
                rs = ps.executeQuery();

                while (rs.next()) {
                    AllegatoBean docBean = new AllegatoBean();
                    docBean.setNomeFile(rs.getString("nome_file"));
                    docBean.setDescrizione(rs.getString("tit_doc"));
                    docBean.setCodice(codDoc);
                    documenti.add(docBean);
                }
            }
            return documenti;
        } catch (SQLException e) {
            log.error(e);
            throw e;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                conn.close();
            } catch (Exception e) {
            }
        }
    }

    public ArrayList getListaHrefSettore(ArrayList listaCodiciInterventi)
            throws Exception {
        ResultSet rs = null;
        Connection conn = null;
        ArrayList codHref = new ArrayList();

        try {
            String inClause = getInClauseNumber(listaCodiciInterventi);
            conn = db.open();
            String sql = "SELECT DISTINCT documenti.href FROM allegati "
                    + " INNER JOIN documenti ON allegati.cod_doc=documenti.cod_doc "
                    + " WHERE allegati.cod_int IN (" + inClause
                    + ") AND href IS NOT null";
            PreparedStatement ps = db.getPreparedStmt(conn, sql);
            log.info("getListaHrefSettore: " + ps);
            rs = ps.executeQuery();
            while (rs.next()) {
                String cod = rs.getString("href");
                codHref.add(cod);
            }
        } catch (SQLException e) {
            log.error(e);
            throw e;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                conn.close();
            } catch (Exception e) {
            }
        }
        return codHref;
    }

    public void settaOneriPerProcedimento(ProcessData dataForm)
            throws Exception {
        ArrayList listaCodiciProcedimenti = new ArrayList();
        ResultSet rs = null;
        Connection conn = null;
        Set set = dataForm.getListaProcedimenti().keySet();
        for (Iterator iterator = set.iterator(); iterator.hasNext();) {
            String codProc = (String) iterator.next();
            listaCodiciProcedimenti.add(codProc);
            ProcedimentoBean proc = (ProcedimentoBean) dataForm.getListaProcedimenti().get(codProc);
            proc.setListaCodiciOneri(new ArrayList());
        }

        try {

            conn = db.open();
            for (Iterator iterator = listaCodiciProcedimenti.iterator(); iterator.hasNext();) {
                String codProc = (String) iterator.next();
                ProcedimentoBean proc = (ProcedimentoBean) dataForm.getListaProcedimenti().get(codProc);
                String inClause = getInClauseString(proc.getCodInterventi());
                String sql = "SELECT cod_oneri FROM oneri_interventi WHERE cod_int IN ("
                        + inClause + ") ";
                PreparedStatement ps = db.getPreparedStmt(conn, sql);
                log.info("settaOneriPerProcedimento: " + ps);
                rs = ps.executeQuery();
                while (rs.next()) {
                    String cod_onere = rs.getString("cod_oneri");
                    proc.addListaCodiciOneri(cod_onere);
                }
                try {
                    ps.close();
                } catch (Exception e) {
                }
            }

        } catch (Exception e) {
            log.error(e);
            throw e;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                conn.close();
            } catch (Exception e) {
            }
        }

    }

    public String getXMLPermessiBookmark(String idBookmark, String codEnte,
            String codEveVita) throws ProcedimentoUnicoException {
        ResultSet rs = null;
        Connection conn = null;
        String xml = "";
        try {
            conn = db.open();
            String sql = "select configuration from servizi where cod_servizio=? and cod_com=? and cod_eve_vita=?";
            PreparedStatement ps = db.getPreparedStmt(conn, sql);
            ps.setString(1, idBookmark);
            ps.setString(2, codEnte);
            ps.setString(3, codEveVita);
            log.info("getXMLPermessiBookmark: " + ps);
            rs = ps.executeQuery();
            if (rs.next()) {
                xml = rs.getString("configuration");
            }
        } catch (SQLException e) {
            log.error(e);
            throw new ProcedimentoUnicoException("");
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                conn.close();
            } catch (Exception e) {
            }
        }
        return xml;
    }

    public String getXMLPermessiPU(String communeId) throws Exception {
        ResultSet rs = null;
        Connection conn = null;
        String xml = "";
        PreparedStatement ps = null;
        try {
            conn = db.open();
            String sql = "select value from configuration where communeid=? and name=?";
            ps = db.getPreparedStmt(conn, sql);
            ps.setString(1, communeId);
            ps.setString(2, "parametriPU");
            log.info("getXMLPermessiPU 1: " + ps);
            rs = ps.executeQuery();
            if (rs.next()) {
                xml = rs.getString("value");
            } else {
                sql = "select value from configuration where communeid IS NULL and name=?";
                ps = db.getPreparedStmt(conn, sql);
                ps.setString(1, "parametriPU");
                log.info("getXMLPermessiPU 2: " + ps);
                rs = ps.executeQuery();
                if (rs.next()) {
                    xml = rs.getString("value");
                } else {
                    log.error("ATTENZIONE : XML di configurazione dei parametri del PU mancante");
                    xml = "<CONFIGURAZIONE>"
                            + "<TYPE_OPZ>COMPLETO|CORTESIA|LIVELLO2</TYPE_OPZ>"
                            + "<TYPE>COMPLETO</TYPE>"
                            + "<CON_INVIO_OPZ>TRUE|FALSE</CON_INVIO_OPZ>"
                            + "<CON_INVIO>TRUE</CON_INVIO>"
                            + "<FIRMADIGITALE_OPZ>TRUE|FALSE</FIRMADIGITALE_OPZ>"
                            + "<FIRMADIGITALE>TRUE</FIRMADIGITALE>"
                            + "<PAGAMENTO_OPZ>DISABILITA|FORZA_PAGAMENTO|OPZIONALE</PAGAMENTO_OPZ>"
                            + "<PAGAMENTO>OPZIONALE</PAGAMENTO>"
                            + "</CONFIGURAZIONE>";
                }
            }
        } catch (SQLException e) {
            log.error(e);
            throw new ProcedimentoUnicoException(
                    "Errore nel recupero dell'XML di configurazione PU");
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                conn.close();
            } catch (Exception e) {
            }
        }
        return xml;
    }

//    public AllegatoBean getAllegatoRicevutaPagamento() throws Exception {
//        ResultSet rs = null;
//        Connection conn = null;
//        AllegatoBean allegato = new AllegatoBean();
//        try {
//            conn = db.open();
//            String sql = "SELECT allegati.flg_autocert,allegati.copie,allegati.cod_cond,documenti_documenti.nome_file,documenti.flg_dic,documenti.href,documenti_testi.tit_doc, "
//                    + " documenti_testi.des_doc,allegati.flg_obb,allegati.tipologie,allegati.num_max_pag,allegati.dimensione_max "
//                    // PC - ordinamento allegati
//                    + ", allegati.ordinamento "
//                    // PC - ordinamento allegati
//                    + " FROM allegati "
//                    + " INNER JOIN documenti ON documenti.cod_doc=allegati.cod_doc "
//                    + " INNER JOIN documenti_testi ON documenti_testi.cod_doc = documenti.cod_doc AND documenti_testi.cod_lang=? "
//                    + " LEFT OUTER JOIN documenti_documenti ON documenti.cod_doc=documenti_documenti.cod_doc "
//                    + " WHERE allegati.cod_doc = 'ALLPAG'";
//            PreparedStatement ps = db.getPreparedStmt(conn, sql);
//            ps.setString(1, language);
//            rs = ps.executeQuery();
//            if (rs.next()) {
//                allegato.setCodice("ALLPAG");
//                allegato.setFlagAutocertificazione(rs.getString("flg_autocert"));
//                allegato.setCopie(rs.getString("copie"));
//                allegato.setCodiceCondizione(rs.getString("cod_cond"));
//                allegato.setNomeFile(rs.getString("nome_file"));
//                allegato.setFlagDic(rs.getString("flg_dic"));
//                allegato.setHref(rs.getString("href"));
//                allegato.setTitolo(rs.getString("tit_doc"));
//                allegato.setDescrizione(rs.getString("des_doc"));
//                // PC - ordinamento allegati
//                allegato.setOrdinamento(rs.getInt("ordinamento"));
//                // PC - ordinamento allegati
//                String flg_obblString = rs.getString("flg_obb");
//                if (flg_obblString != null
//                        && flg_obblString.equalsIgnoreCase("S")) {
//                    allegato.setFlg_obb(true);
//                } else {
//                    allegato.setFlg_obb(false);
//                }
//                allegato.setTipologieAllegati(rs.getString("tipologie"));
//                allegato.setNum_max_pag(rs.getString("num_max_pag"));
//                allegato.setDimensione_max(rs.getString("dimensione_max"));
//            }
//        } catch (SQLException e) {
//            log.error(e);
//            throw new ProcedimentoUnicoException("");
//        } finally {
//            try {
//                rs.close();
//            } catch (Exception e) {
//            }
//            try {
//                conn.close();
//            } catch (Exception e) {
//            }
//        }
//        return allegato;
//    }
//
//    public DocumentoBean getDocumentoRicevutaPagamento() throws Exception {
//        ResultSet rs = null;
//        Connection conn = null;
//        DocumentoBean documento = new DocumentoBean();
//        try {
//            conn = db.open();
//            String sql = "SELECT documenti_testi.*"
//                    + " FROM documenti "
//                    + " INNER JOIN documenti_testi ON documenti.cod_doc=documenti_testi.cod_doc AND documenti_testi.cod_lang=? "
//                    + " WHERE documenti.cod_doc='ALLPAG'";
//
//            PreparedStatement ps = db.getPreparedStmt(conn, sql);
//            ps.setString(1, language);
//            rs = ps.executeQuery();
//            if (rs.next()) {
//                documento.setCodiceDoc("ALLPAG");
//                documento.setDescrizione(rs.getString("des_doc"));
//                documento.setTitolo(rs.getString("tit_doc"));
//            }
//        } catch (SQLException e) {
//            log.error(e);
//            throw new ProcedimentoUnicoException("");
//        } finally {
//            try {
//                rs.close();
//            } catch (Exception e) {
//            }
//            try {
//                conn.close();
//            } catch (Exception e) {
//            }
//        }
//        return documento;
//    }
    public boolean checkValiditaAllegato(String codiceIntervento,
            String codAllegato, String codEnte) throws Exception {
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = db.open();
            String sql = "select allegati.cod_doc, cod_int, flg_autocert, copie, cod_cond,  documenti_documenti.nome_file, "
                    + " flg_dic, href, tit_doc, des_doc  from allegati inner join documenti on allegati.cod_doc=documenti.cod_doc "
                    + " inner  join documenti_testi on documenti.cod_doc=documenti_testi.cod_doc "
                    + " left join documenti_documenti on documenti_documenti.cod_doc = documenti.cod_doc "
                    + " join "
                    + " ( select a.cod_doc "
                    + " from  allegati a "
                    + " join  allegati_comuni b "
                    + " on  a.cod_doc = b.cod_doc "
                    + " and b.cod_com = ? "
                    + " and a.cod_int = '"
                    + codiceIntervento
                    + "' "
                    + " and b.flg = 'S' "
                    + " union "
                    + " select a.cod_doc "
                    + " from allegati a "
                    + " left join allegati_comuni b "
                    + " on a.cod_doc = b.cod_doc "
                    + " where a.cod_int = '"
                    + codiceIntervento
                    + "' "
                    + " and b.cod_com is null "
                    + " union "
                    + " select a.cod_doc "
                    + " from allegati a "
                    + " left join allegati_comuni b "
                    + " on a.cod_doc = b.cod_doc "
                    + " where b.cod_com != ? "
                    + " and a.cod_int = '"
                    + codiceIntervento
                    + "' "
                    + " and b.flg = 'N' "
                    + " and a.cod_int not in (select cod_int from allegati_comuni where cod_com = ? and flg = 'N') "
                    + " ) alle "
                    + " on alle.cod_doc = allegati.cod_doc "
                    + " where cod_int = '"
                    + codiceIntervento
                    + "' and cod_cond IS NOT NULL and documenti_testi.cod_lang=?";

            PreparedStatement ps = db.getPreparedStmt(conn, sql);
            ps.setString(1, codEnte);
            ps.setString(2, codEnte);
            ps.setString(3, codEnte);
            ps.setString(4, language);
            log.info("checkValiditaAllegato: " + ps);
            rs = ps.executeQuery();
            while (rs.next()) {
                String codAll = rs.getString("cod_doc");
                if (codAll.equalsIgnoreCase(codAllegato)) {
                    return true;
                }
            }

        } catch (SQLException e) {
            log.error(e);
            throw e;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                conn.close();
            } catch (Exception e) {
            }
        }
        return false;
    }

    public void getDescrizioneBookmark(ProcessData dataForm, String idBookmark,
            String codEnte, String codEventoVita) {
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = db.open();
            String sql = "select nome_servizio,des_servizio from servizi_testi where cod_servizio=? and cod_com=? and cod_eve_vita=? and cod_lang=?";
            PreparedStatement ps = db.getPreparedStmt(conn, sql);
            ps.setString(1, idBookmark);
            ps.setString(2, codEnte);
            ps.setString(3, codEventoVita);
            ps.setString(4, language);
            log.info("getDescrizioneBookmark: " + ps);
            rs = ps.executeQuery();
            if (rs.next()) {
                dataForm.setDescBookmark(rs.getString("des_servizio"));
                dataForm.setNomeBookmark(rs.getString("nome_servizio"));
            }
        } catch (Exception e) {
            log.error(e);
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                conn.close();
            } catch (Exception e) {
            }
        }
    }

    public ArrayList getListaBookmark() {
        ArrayList lista = new ArrayList();
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = db.open();
            String sql = "SELECT * FROM servizi_old "
                    + "WHERE cod_eve_vita!=10 "
                    + "and bookmark_pointer LIKE '%<it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">%'";
            PreparedStatement ps = db.getPreparedStmt(conn, sql);
            log.info("getListaBookmark: " + ps);
            rs = ps.executeQuery();
            while (rs.next()) {
                ImportBookmarkBean bean = new ImportBookmarkBean();
                bean.setBookmark(rs.getString("bookmark_pointer"));
                bean.setCodEnte(rs.getString("cod_com"));
                bean.setCodEventoVita(rs.getString("cod_eve_vita"));
                bean.setCodServizio(rs.getString("cod_servizio"));
                bean.setConfiguration("");
                lista.add(bean);
            }
        } catch (Exception e) {
            log.error(e);
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                conn.close();
            } catch (Exception e) {
            }
        }
        return lista;
    }

    public void recoveryCampiCollegatiDichiarazioniDinamiche(
            ProcessData dataForm) throws Exception {
        try {
            Set set = dataForm.getListaHref().keySet();
            for (Iterator iterator = set.iterator(); iterator.hasNext();) {
                String key = (String) iterator.next();
                SezioneCompilabileBean scb = (SezioneCompilabileBean) dataForm.getListaHref().get(key);
                if (scb.getHref() != null
                        && !scb.getHref().equalsIgnoreCase("")) {
                    ArrayList listaCampiHref = scb.getCampi();
                    for (Iterator iterator2 = listaCampiHref.iterator(); iterator2.hasNext();) {
                        HrefCampiBean campo = (HrefCampiBean) iterator2.next();
                        if (campo.getTipo().equalsIgnoreCase("R")
                                || campo.getTipo().equalsIgnoreCase("C")) {
                            campo.setCampiCollegati(getCampiCollegati(
                                    scb.getHref(), campo.getNome()));
                        }
                    }
                }
            }

        } catch (SQLException e) {
            log.error(e);
        } finally {
        }

    }

    public String getParametroConfigurazione(String communeId,
            String nomeParametro) {
        String ret = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = db.open();
            String sql = "select value from configuration where communeid=? and name=?";
            ps = db.getPreparedStmt(conn, sql);
            ps.setString(1, communeId);
            ps.setString(2, nomeParametro);
            log.info("getParametroConfigurazione 1: " + ps);
            rs = ps.executeQuery();
            if (rs.next()) {
                ret = rs.getString("value");
            } else {
                sql = "select value from configuration where communeid is NULL and name=?";
                ps = db.getPreparedStmt(conn, sql);
                ps.setString(1, nomeParametro);
                log.info("getParametroConfigurazione 2: " + ps);
                rs = ps.executeQuery();
                if (rs.next()) {
                    ret = rs.getString("value");
                }
            }
        } catch (Exception e) {
            log.error(e);
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                conn.close();
            } catch (Exception e) {
            }
        }
        return ret;
    }

    public boolean isParametroConfigurazionePresent(String communeId,
            String nomeParametro, String valore) {
        boolean ret = false;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = db.open();
            String sql = "select * from configuration where communeid=? and name=? and value=?";
            ps = db.getPreparedStmt(conn, sql);
            ps.setString(1, communeId);
            ps.setString(2, nomeParametro);
            ps.setString(3, valore);
            log.info("isParametroConfigurazionePresent 1: " + ps);
            rs = ps.executeQuery();
            if (rs.next()) {
                ret = true;
            } else {
                sql = "select * from configuration where communeid is NULL and name=? and value=?";
                ps = db.getPreparedStmt(conn, sql);
                ps.setString(1, nomeParametro);
                ps.setString(2, valore);
                log.info("isParametroConfigurazionePresent 2: " + ps);
                rs = ps.executeQuery();
                if (rs.next()) {
                    ret = true;
                }
            }
        } catch (Exception e) {
            log.error(e);
        } finally {
            try {
                ps.close();
            } catch (Exception e) {
            }
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                conn.close();
            } catch (Exception e) {
            }
        }
        return ret;

    }

    public void setParametriPU(IRequestWrapper request, ProcessData dataForm)
            throws Exception {
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            String tipologiaFirma = "";
            String tipologiaPagamento = "";
            String modalitaPagamento = "";
            String modalitaPagamentoOpzionale = "";
            String conInvioString = "";
            conn = db.open();
            String tipologiaServizio = request.getParameter("TB_pu");
            if (tipologiaServizio != null
                    && tipologiaServizio.equalsIgnoreCase(String.valueOf(Costant.bookmarkTypeCompleteCod))) {
                tipologiaFirma = request.getParameter("FP_pu");
                tipologiaPagamento = request.getParameter("AP_pu");
                modalitaPagamento = request.getParameter("AP_MP_pu");
                modalitaPagamentoOpzionale = request.getParameter("AP_MPO_pu");
                conInvioString = request.getParameter("INVIO_pu");
            } else {
                tipologiaFirma = String.valueOf(Costant.senzaFirmaCod);
                tipologiaPagamento = String.valueOf(Costant.disabilitaPagamentoCod);
                modalitaPagamento = String.valueOf(Costant.modalitPagamentoSoloOnlineCod);
                modalitaPagamentoOpzionale = String.valueOf(Costant.modalitPagamentoSoloOnlineCod);
                conInvioString = String.valueOf(Costant.senzaInvioCod);
            }
            String xmlConfigurazione = BookmarkDAO.buildXMLConfiguration(
                    tipologiaServizio, tipologiaFirma, tipologiaPagamento,
                    modalitaPagamento, modalitaPagamentoOpzionale,
                    conInvioString);

            ParametriPUBean newParametri = new ParametriPUBean(
                    xmlConfigurazione);
            dataForm.getDatiTemporanei().setParametriPU(newParametri);

            String tmp = getParametroConfigurazione(dataForm.getIdentificatoreUnivoco().getCodiceSistema().getCodiceAmministrazione(), "parametriPU");
            if (Utilities.isset(tmp)) {
                String sql = "SELECT id FROM configuration where communeid = ? and name=?";
                ps = db.getPreparedStmt(conn, sql);
                ps.setString(1, dataForm.getIdentificatoreUnivoco().getCodiceSistema().getCodiceAmministrazione());
                ps.setString(2, "parametriPU");
                log.info("setParametriPU 1: " + ps);
                rs = ps.executeQuery();
                if (rs.next()) {
                    String id = rs.getString("id");
                    sql = "update configuration set value=? where id=? and name=? ";
                    ps = db.getPreparedStmt(conn, sql);
                    ps.setString(1, xmlConfigurazione);
                    ps.setString(2, id);
                    ps.setString(3, "parametriPU");
                    log.info("setParametriPU 2: " + ps);
                    ps.executeUpdate();
                } else {
                    sql = "SELECT MAX(id) as idmax FROM configuration";
                    ps = db.getPreparedStmt(conn, sql);
                    log.info("setParametriPU 3: " + ps);
                    rs = ps.executeQuery();
                    int max = 1;
                    if (rs.next()) {
                        max = Integer.parseInt(rs.getString("idmax"));
                        max++;
                    }
                    sql = "insert into configuration values(?,?,?,?,?)";
                    ps = db.getPreparedStmt(conn, sql);
                    ps.setInt(1, max);
                    ps.setString(2, dataForm.getIdentificatoreUnivoco().getCodiceSistema().getCodiceAmministrazione());
                    ps.setString(3, "parametriPU");
                    ps.setString(4, xmlConfigurazione);
                    ps.setString(5,
                            "XML parametri procedimento unico (communeID = "
                            + dataForm.getIdentificatoreUnivoco().getCodiceSistema().getCodiceAmministrazione() + ")");
                    log.info("setParametriPU 3: " + ps);
                    db.insert(conn, ps, sql);
                    // ps.execute();
                }

            } else {
                String sql = "SELECT MAX(id) as idmax  FROM configuration";
                ps = db.getPreparedStmt(conn, sql);
                log.info("setParametriPU 4: " + ps);
                rs = ps.executeQuery();
                int max = 1;
                if (rs.next()) {
                    max = Integer.parseInt(rs.getString("idmax"));
                    max++;
                }
                sql = "insert into configuration values(?,?,?,?,?)";
                ps = db.getPreparedStmt(conn, sql);
                ps.setInt(1, max);
                ps.setString(2, dataForm.getIdentificatoreUnivoco().getCodiceSistema().getCodiceAmministrazione());
                ps.setString(3, "parametriPU");
                ps.setString(4, xmlConfigurazione);
                ps.setString(5,
                        "XML parametri procedimento unico (communeID = "
                        + dataForm.getIdentificatoreUnivoco().getCodiceSistema().getCodiceAmministrazione() + ")");
                log.info("setParametriPU 5: " + ps);
                db.insert(conn, ps, sql);
                // ps.execute();
            }

        } catch (Exception e) {
            log.error(e);
            throw new ProcedimentoUnicoException("");
        } finally {
            try {
                ps.close();
            } catch (Exception e) {
            }
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                conn.close();
            } catch (Exception e) {
            }
        }

    }

    public Date getDataScadenza(String idBookmark) throws Exception {
        ResultSet rs = null;
        Connection conn = null;
        String sql = null;
        PreparedStatement ps = null;
        Date d = null;
        try {
            conn = db.open();
            sql = " SELECT alla_data as dataScadenza FROM servizi_validita WHERE cod_servizio=?";
            ps = db.getPreparedStmt(conn, sql);
            ps.setString(1, idBookmark);
            log.info("getDataScadenza: " + ps);
            rs = ps.executeQuery();
            if (rs.next()) {
                d = rs.getDate("dataScadenza");
            }
        } catch (Exception e) {
            log.error(e);
            throw e;
        } finally {
            try {
                ps.close();
            } catch (Exception e) {
            }
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                conn.close();
            } catch (Exception e) {
            }
        }
        return d;
    }

    public boolean bookmarkIsBando(String idBookmark) throws Exception {
        ResultSet rs = null;
        Connection conn = null;
        String sql = null;
        PreparedStatement ps = null;
        boolean trovato = false;
        try {
            conn = db.open();
            sql = " SELECT *  FROM servizi_validita WHERE cod_servizio=? and flg_bando=?";
            ps = db.getPreparedStmt(conn, sql);
            ps.setString(1, idBookmark);
            ps.setString(2, "1");
            log.info("bookmarkIsBando: " + ps);
            rs = ps.executeQuery();
            if (rs.next()) {
                trovato = true;
            }
        } catch (Exception e) {
            log.error(e);
            throw e;
        } finally {
            try {
                ps.close();
            } catch (Exception e) {
            }
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                conn.close();
            } catch (Exception e) {
            }
        }
        return trovato;
    }

    public boolean checkAccessList(AbstractPplProcess process,
            String idBookmark, String codFisc) {
        ResultSet rs = null;
        Connection conn = null;
        String sql = null;
        PreparedStatement ps = null;
        boolean trovato = false;
        try {
            conn = db.open();
            sql = " SELECT count(*) as num FROM servizi_accesslist WHERE cod_servizio=?";
            ps = db.getPreparedStmt(conn, sql);
            ps.setString(1, idBookmark);
            log.info("checkAccessList 1: " + ps);
            rs = ps.executeQuery();
            if (rs.next()) {
                int num = rs.getInt("num");
                if (num > 0) {
                    sql = "select count(*) as num FROM servizi_accesslist WHERE cod_servizio=? and codfisc=?";
                    ps = db.getPreparedStmt(conn, sql);
                    ps.setString(1, idBookmark);
                    ps.setString(2, codFisc);
                    log.info("checkAccessList 2: " + ps);
                    rs = ps.executeQuery();
                    if (rs.next()) {
                        int num_ = rs.getInt("num");
                        if (num_ > 0) {
                            trovato = true;
                        }
                    }
                } else {
                    trovato = true;
                }
            } else {
                trovato = true;
            }

        } catch (Exception e) {
            log.error(e);
        } finally {
            try {
                ps.close();
            } catch (Exception e) {
            }
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                conn.close();
            } catch (Exception e) {
            }
        }
        return trovato;
    }

    public Date getDataInizioValidita(String idBookmark) throws Exception {
        ResultSet rs = null;
        Connection conn = null;
        String sql = null;
        PreparedStatement ps = null;
        Date d = null;
        try {
            conn = db.open();
            sql = " SELECT dalla_data as dataInizio FROM servizi_validita WHERE cod_servizio=?";
            ps = db.getPreparedStmt(conn, sql);
            ps.setString(1, idBookmark);
            log.info("getDataInizioValidita: " + ps);
            rs = ps.executeQuery();
            if (rs.next()) {
                d = rs.getDate("dataInizio");
            }
        } catch (Exception e) {
            log.error(e);
            throw e;
        } finally {
            try {
                ps.close();
            } catch (Exception e) {
            }
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                conn.close();
            } catch (Exception e) {
            	
            }
        }
        return d;
    }

    public SezioneCompilabileBean getOggettoPratica(ProcessData dataForm)
            throws Exception {
        ResultSet rs = null;
        Connection conn = null;
        String href = null;
        SezioneCompilabileBean scb = null;
        try {
            conn = db.open();

            try {
                SportelloBean sportello = null;
                Set chiaviSettore = dataForm.getListaSportelli().keySet();
                boolean trovatoSportello = false;
                for (Iterator chiaviSettoreIterator = chiaviSettore.iterator(); chiaviSettoreIterator.hasNext() && !trovatoSportello;) {
                    String chiaveSettore = (String) chiaviSettoreIterator.next();
                    if (sportello == null) {
                        sportello = (SportelloBean) dataForm.getListaSportelli().get(chiaveSettore);
                        trovatoSportello = true;
                    }
                }

                if (trovatoSportello) {
                    String sql = "select href_oggetto from sportelli where cod_sport = ?";
                    PreparedStatement ps = db.getPreparedStmt(conn, sql);
                    ps.setString(1, sportello.getCodiceSportello());
                    log.info("getOggettoPratica 1: " + ps);
                    rs = ps.executeQuery();
                    if (rs.next()) {
                        href = rs.getString("href_oggetto");
                    }
                }
                if (!Utilities.isset(href)) {
                    String sql = "SELECT href FROM comuni_aggregazione LEFT OUTER JOIN tipi_aggregazione ON comuni_aggregazione.tip_aggregazione=tipi_aggregazione.tip_aggregazione  WHERE cod_ente=? ";
                    PreparedStatement ps = db.getPreparedStmt(conn, sql);
                    ps.setString(1, dataForm.getComuneSelezionato().getCodEnte());
                    log.info("getOggettoPratica 2: " + ps);
                    rs = ps.executeQuery();
                    if (rs.next()) {
                        href = rs.getString("href");
                    }
                }
            } catch (Exception exception) {
            	log.error("", exception);
            }

            if (Utilities.isset(href)) {
                String sql = " SELECT href_testi.tit_href, href_testi.piede_href,MAX(href_campi.riga) AS numRiga,MAX(href_campi.posizione) AS maxcol,MAX(href_campi.tp_riga) AS campimultipli  FROM href  LEFT OUTER JOIN href_testi ON href.href=href_testi.href AND href_testi.cod_lang=?  LEFT OUTER JOIN href_campi ON href.href=href_campi.href  WHERE  href.href=? GROUP BY href.href ";
                String sql2 = "SELECT MAX(href_campi.riga) AS lastRiga, MIN(href_campi.riga) AS firstRiga FROM href_campi WHERE href_campi.tp_riga='1' AND href_campi.href=? ";
                PreparedStatement ps = db.getPreparedStmt(conn, sql);
                ps.setString(1, language);
                ps.setString(2, href);
                log.info("getOggettoPratica 3: " + ps);
                rs = ps.executeQuery();
                if (rs.next()) {
                    scb = new SezioneCompilabileBean();
                    scb.setHref(href);
                    scb.setTitolo(rs.getString("tit_href"));
                    scb.setDescrizione("Oggetto pratica");
                    scb.setPiedeHref(Utilities.NVL(rs.getString("piede_href"), ""));
                    scb.setRowCount(rs.getInt("numRiga"));
                    scb.setTdCount(rs.getInt("maxcol"));
                    if (rs.getInt("campimultipli") > 0) {
                        scb.setNumSezioniMultiple(1);
                        ps = db.getPreparedStmt(conn, sql2);
                        ps.setString(1, href);
                        log.info("getOggettoPratica 4: " + ps);
                        rs = ps.executeQuery();
                        if (rs.next()) {
                            scb.setFirstRowCampoMultiplo(rs.getInt("firstRiga"));
                            scb.setLastRowCampoMultiplo(rs.getInt("lastRiga"));
                        }
                    }
                    sql = "SELECT href_campi.*, href_campi_testi.des_campo,  href_campi_testi.err_msg,  CONCAT(href_campi.href, href_campi.nome) nomecomposto, CONCAT(href_campi.href, href_campi.campo_collegato) campo_collegato_composto  FROM href_campi LEFT JOIN  href_campi_testi ON href_campi_testi.contatore = href_campi.contatore AND href_campi_testi.href=href_campi.href AND href_campi_testi.nome=href_campi.nome AND href_campi_testi.cod_lang=? INNER JOIN href_testi ON href_testi.href=href_campi.href AND href_testi.cod_lang=?  WHERE  href_campi.href=? ORDER BY riga , posizione";
                    ps = db.getPreparedStmt(conn, sql);
                    ps.setString(1, language);
                    ps.setString(2, language);
                    ps.setString(3, scb.getHref());
                    
                    HrefCampiBean hcb;
                    log.info("getOggettoPratica 5: " + ps);
                    for (rs = ps.executeQuery(); rs.next(); scb.addCampi(hcb)) {
                        hcb = new HrefCampiBean();
                        hcb.setMolteplicita(scb.getNumSezioniMultiple());
                        hcb.setCampo_collegato(rs.getString("campo_collegato"));
                        hcb.setCampo_xml_mod(rs.getString("campo_xml_mod"));
                        hcb.setContatore(rs.getString("contatore"));
                        hcb.setControllo(rs.getString("controllo"));
                        hcb.setDecimali(rs.getInt("decimali"));
                        hcb.setDescrizione(rs.getString("des_campo"));
                        hcb.setEdit(rs.getString("edit"));
                        hcb.setLunghezza(rs.getInt("lunghezza"));
                        hcb.setNome(rs.getString("nome"));
                        hcb.setMarcatore_incrociato(rs.getString("marcatore_incrociato"));
                        hcb.setPrecompilazione(rs.getString("precompilazione"));
                        hcb.setNome_xsd(rs.getString("nome_xsd"));
                        hcb.setWeb_serv(rs.getString("web_serv"));
                        hcb.setCampo_dati(rs.getString("campo_dati"));
                        hcb.setCampo_key(rs.getString("campo_key"));
                        hcb.setNumCampo(rs.getInt("tp_riga"));
                        hcb.setPosizione(rs.getInt("posizione"));
                        hcb.setRaggruppamento_check(rs.getString("raggruppamento_check"));
                        hcb.setRiga(rs.getInt("riga"));
                        hcb.setTipo(rs.getString("tipo"));
                        hcb.setTp_controllo(rs.getString("tp_controllo"));
                        hcb.setVal_campo_collegato(rs.getString("val_campo_collegato"));
                        hcb.setValore(rs.getString("valore"));
                        hcb.setPattern(rs.getString("pattern"));
                        hcb.setErr_msg(rs.getString("err_msg"));                    
                        if (hcb.getTipo().equalsIgnoreCase("R") || hcb.getTipo().equalsIgnoreCase("C")) {
                            hcb.setCampiCollegati(getCampiCollegati(scb.getHref(), rs.getString("nome")));
                        }
                        if (hcb.getTipo().equalsIgnoreCase("L")) {
                            sql2 = "SELECT href_campi_valori.val_select as cod, href_campi_valori_testi.des_valore FROM href_campi_valori  INNER JOIN href_campi_valori_testi ON href_campi_valori.href=href_campi_valori_testi.href  AND href_campi_valori.nome=href_campi_valori_testi.nome  AND href_campi_valori.val_select=href_campi_valori_testi.val_select  AND href_campi_valori_testi.cod_lang=? WHERE href_campi_valori.href=? AND href_campi_valori.nome=?";
                            PreparedStatement ps2 = db.getPreparedStmt(conn, sql2);
                            ps2.setString(1, language);
                            ps2.setString(2, scb.getHref());
                            ps2.setString(3, hcb.getNome());
                            ResultSet rs2;
                            BaseBean bb;
                            log.info("getOggettoPratica 6: " + ps);
                            for (rs2 = ps2.executeQuery(); rs2.next(); hcb.addOpzioniCombo(bb)) {
                                bb = new BaseBean();
                                bb.setCodice(rs2.getString("cod"));
                                bb.setDescrizione(rs2.getString("des_valore"));
                            }

                            rs2.close();
                            ps2.close();
                        }
                    }

                }
            }

        } catch (SQLException e) {
            log.error(e);
            throw e;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            	log.error("", e);
            }
            try {
                conn.close();
            } catch (Exception e) {
            	log.error("", e);
            }
        }
        return scb;
    }

    public void getDestinatari(ProcessData dataForm) throws Exception {
        ResultSet rs = null;
        Connection conn = null;
        String sql = null;
        PreparedStatement ps = null;
        ArrayList listaCod = new ArrayList();
        try {
            HashMap listaDest = new HashMap();
            Set set = dataForm.getListaProcedimenti().keySet();
            if (set != null) {
                Iterator it = set.iterator();
                if (it != null) {
                    while (it.hasNext()) {
                        String codProc = (String) it.next();
                        ProcedimentoBean procedimento = (ProcedimentoBean) dataForm.getListaProcedimenti().get(codProc);
                        if (procedimento != null
                                && procedimento.getCodDest() != null
                                && !listaDest.containsKey(procedimento.getCodDest())) {
                            listaCod.add(procedimento.getCodDest());
                            listaDest.put(procedimento.getCodDest(), null);
                        }
                    }
                    String inClause = getInClauseString(listaCod);
                    sql = "select a.cod_dest,a.tel,a.fax,a.email,a.indirizzo,a.cap,a.citta,a.prov,a.cod_ente,"
                            + "b.nome_dest,b.intestazione from destinatari a "
                            + "join  destinatari_testi b on a.cod_dest=b.cod_dest and b.cod_lang=? where a.cod_dest  IN ("
                            + inClause + ") ";

                    if (log.isDebugEnabled()) {
                        log.debug("--> " + sql);
                    }
                    conn = db.open();
                    ps = db.getPreparedStmt(conn, sql);
                    ps.setString(1, language);
                    log.info("getDestinatari: " + ps);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        DestinatarioBean destinatario = new DestinatarioBean();
                        destinatario.setCod_dest(rs.getString("cod_dest"));
                        destinatario.setIntestazione(rs.getString("intestazione"));
                        destinatario.setNome_dest(rs.getString("nome_dest"));
                        destinatario.setTelefono(rs.getString("tel"));
                        destinatario.setFax(rs.getString("fax"));
                        destinatario.setEmail(rs.getString("email"));
                        destinatario.setVia(rs.getString("indirizzo"));
                        destinatario.setCap(rs.getString("cap"));
                        destinatario.setCitta(rs.getString("citta"));
                        destinatario.setProvincia(rs.getString("prov"));
                        destinatario.setCod_ente(rs.getString("cod_ente"));
                        listaDest.put(destinatario.getCod_dest(), destinatario);
                    }
                    dataForm.setListaDestinatari(listaDest);
                }
            }
        } catch (SQLException e) {
            log.error(e);
            throw e;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            	log.error("", e);
            }
            try {
                conn.close();
            } catch (Exception e) {
            	log.error("", e);
            }
        }

    }

    /**
     * <p>
     * Utilizzata dal <code>ProcessData</code> per aggiornare i dati dello
     * sportello nel caso del procedimento unico.
     *
     * @param sportelloDaXml
     * @return
     */
    public SportelloBean updateDatiSportello(SportelloBean sportelloDaXml,
            ProcessData dataForm) {

        ResultSet resultSet = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String datiSportelloQuery = "select * from sportelli where cod_sport = ?";
        // Caricamento dati per segnatura cittadino
        String datiSegnaturaQuery = "select name, value from sportelli_param_prot_pec where ref_sport = ?";
        // Caricamento dati per segnatura cittadino
        String datiProtocolloQuery = "select name, value from sportelli_param_prot_ws where ref_sport = ?";
        SportelloBean result = sportelloDaXml;

        try {

            connection = db.open();

            preparedStatement = connection.prepareStatement(datiSportelloQuery);
            preparedStatement.setString(1, sportelloDaXml.getCodiceSportello());
            log.info("updateDatiSportello 1: " + preparedStatement);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                ManagerAllegati managerAllegati = new ManagerAllegati();
                result.setRup(resultSet.getString("nome_rup"));
                result.setTelefono(resultSet.getString("tel"));
                result.setFax(resultSet.getString("fax"));
                result.setEmail(resultSet.getString("email"));
                result.setIndirizzo(resultSet.getString("indirizzo"));
                result.setCap(resultSet.getString("cap"));
                result.setCitta(resultSet.getString("citta"));
                result.setProvincia(resultSet.getString("prov"));
                result.setPec(resultSet.getString("email_cert"));
                result.setId_mail_server(resultSet.getString("id_mail_server"));
                result.setId_protocollo(resultSet.getString("id_protocollo"));
                result.setId_backoffice(resultSet.getString("id_backoffice"));
                result.setTemplate_oggetto_ricevuta(resultSet.getString("template_oggetto_ricevuta"));
                result.setTemplate_corpo_ricevuta(resultSet.getString("template_corpo_ricevuta"));
                result.setTemplate_nome_file_zip(resultSet.getString("template_nome_file_zip"));
                result.setSend_zip_file(resultSet.getString("send_zip_file"));
                result.setSend_single_files(resultSet.getString("send_single_files"));
                result.setSend_xml(resultSet.getString("send_xml"));
                result.setSend_signature(resultSet.getString("send_signature"));
                result.setSend_protocollo(resultSet.getString("send_protocollo_param"));
                result.setSend_ricevuta_dopo_protocollazione(resultSet.getString("send_ricevuta_dopo_protocollazione"));
                result.setSend_ricevuta_dopo_invio_bo(resultSet.getString("send_ricevuta_dopo_invio_bo"));
                result.setTemplate_oggetto_mail_suap(resultSet.getString("template_oggetto_mail_suap"));
                result.setAe_codice_utente(resultSet.getString("ae_codice_utente"));
                result.setAe_codice_ente(resultSet.getString("ae_codice_ente"));
                result.setAe_tipo_ufficio(resultSet.getString("ae_tipo_ufficio"));
                result.setAe_codice_ufficio(resultSet.getString("ae_codice_ufficio"));
                result.setAe_tipologia_servizio(resultSet.getString("ae_tipologia_servizio"));
                result.setAttachmentsUploadUM(resultSet.getString("allegati_dimensione_max_um"));
                result.setShowPrintBlankTemplate(resultSet.getString(
                        "visualizza_stampa_modello_in_bianco").equalsIgnoreCase("s"));
                result.setShowPDFVersion(resultSet.getString(
                        "visualizza_versione_pdf").equalsIgnoreCase("s"));
                result.setOnLineSign(resultSet.getString("firma_on_line").equalsIgnoreCase("s"));
                result.setOffLineSign(resultSet.getString("firma_off_line").equalsIgnoreCase("s"));

                managerAllegati.setAttachmentsMaxTotalSize(
                        dataForm,
                        resultSet.getInt("allegati_dimensione_max"),
                        resultSet.getString("allegati_dimensione_max_um"),
                        (resultSet.getString("id_mail_server") != null && !resultSet.getString("id_mail_server").equalsIgnoreCase("")),
                        (resultSet.getString("id_backoffice") != null && !resultSet.getString("id_backoffice").equalsIgnoreCase("")));

                if (dataForm.getAttachmentsMaximunSize() == ProcessData.UNLIMITED_ATTACHMENTS_MAX_TOTAL_SIZE) {
                    result.setAttachmentsUploadMaximumSize("illimitata");
                } else {
                    result.setAttachmentsUploadMaximumSize(managerAllegati.convertAttachmentsMaximumTotalSize(
                            dataForm.getAttachmentsMaximunSize(),
                            result.getAttachmentsUploadUM()) + " " + result.getAttachmentsUploadUM());
                }
            }

            preparedStatement = connection.prepareStatement(datiSegnaturaQuery);
            preparedStatement.setString(1, sportelloDaXml.getCodiceSportello());
            log.info("updateDatiSportello 2: " + preparedStatement);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.addDatiSegnaturaCittadino(resultSet.getString(1), resultSet.getString(2));
            }
            preparedStatement = connection.prepareStatement(datiProtocolloQuery);
            preparedStatement.setString(1, sportelloDaXml.getCodiceSportello());
            log.info("updateDatiSportello 3: " + preparedStatement);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.addDatiProtocollo(resultSet.getString(1), resultSet.getString(2));
            }

        } catch (SQLException e) {
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ignore) {
            	log.error("", ignore);
            }
        }

        return result;

    }

    // Restituisce una lista di array contenenti i campi collegati ed il valore
    // del radio
    public AllegatoBean getAllegatoAttestatoVersamento(String codiceSportello) {
        AllegatoBean result = null;

        String codiceDocumento = Costant.CODICE_ATTESTAZIONE_VERSAMENTO_ONERI;
        if (codiceSportello != null
                && !codiceSportello.trim().equalsIgnoreCase("")) {
            codiceDocumento += codiceSportello;
        }

        String sql = "";
        sql += "select doc.cod_doc, doc.flg_dic, docTxt.tit_doc, docTxt.des_doc from documenti doc, documenti_testi docTxt where doc.cod_doc = docTxt.cod_doc and doc.cod_doc = ?;";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = db.open();
            pstmt = db.getPreparedStmt(conn, sql);
            pstmt.setString(1, codiceDocumento);
            log.info("getAllegatoAttestatoVersamento: " + pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                result = new AllegatoBean();
                result.setFlg_obb(true);
                result.setCodice(rs.getString("cod_doc"));
                result.setFlagDic(rs.getString("flg_dic"));
                result.setTitolo(rs.getString("tit_doc"));
                result.setDescrizione(rs.getString("des_doc"));
                result.setTipologieAllegati(null);
                result.setNum_max_pag(null);
                result.setDimensione_max(null);
                result.setCopie("1");
                result.setHref(null);
                result.setOrdinamento(new Integer(1));
                result.setOrdinamentoIntervento(new Integer(1));
            } else {
                result = new AllegatoBean();
                result.setFlg_obb(true);
                result.setCodice("SYSCPVERS");
                result.setFlagDic("N");
                result.setTitolo(Costant.TITOLO_ATTETAZIONE_VERSAMENTO_ONERI);
                result.setDescrizione(Costant.TITOLO_ATTETAZIONE_VERSAMENTO_ONERI);
                result.setTipologieAllegati(null);
                result.setNum_max_pag(null);
                result.setDimensione_max(null);
                result.setCopie("1");
                result.setHref(null);
                result.setOrdinamento(new Integer(1));
                result.setOrdinamentoIntervento(new Integer(1));
            }
        } catch (SQLException e) {
            log.error(e);
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            	log.error("", e);
            }
            try {
                conn.close();
            } catch (Exception e) {
            	log.error("", e);
            }
        }
        return result;
    }

    public DocumentoFisicoBean getDocumentoFisico(String codDoc, String codRif) throws Exception {
        DocumentoFisicoBean dfb = null;
        ResultSet resultSet = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String sql = "";
        try {
            connection = db.open();
            if (codDoc != null && !codDoc.equalsIgnoreCase("")) {
                sql = "select * from documenti_documenti where cod_doc=? and cod_lang=?";
                preparedStatement = db.getPreparedStmt(connection, sql);
                preparedStatement.setString(1, codDoc);
                preparedStatement.setString(2, language);
                log.info("getDocumentoFisico 1: " + preparedStatement);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    dfb = new DocumentoFisicoBean();
                    dfb.setNomeFile(resultSet.getString("nome_file"));
                    dfb.setContentType(resultSet.getString("tip_doc"));
                    dfb.setDocumentoFisico(resultSet.getBlob("doc_blob"));
                }
                resultSet.close();
                preparedStatement.close();
                if (dfb == null) {
                    preparedStatement = db.getPreparedStmt(connection, sql);
                    preparedStatement.setString(1, codDoc);
                    preparedStatement.setString(2, "it");
                    log.info("getDocumentoFisico 2: " + preparedStatement);
                    resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        dfb = new DocumentoFisicoBean();
                        dfb.setNomeFile(resultSet.getString("nome_file"));
                        dfb.setContentType(resultSet.getString("tip_doc"));
                        dfb.setDocumentoFisico(resultSet.getBlob("doc_blob"));
                    }
                    resultSet.close();
                    preparedStatement.close();
                }

            } else {
                sql = "select * from normative_documenti where cod_rif=? and cod_lang=?";
                preparedStatement = db.getPreparedStmt(connection, sql);
                preparedStatement.setString(1, codRif);
                preparedStatement.setString(2, language);
                log.info("getDocumentoFisico 3: " + preparedStatement);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    dfb = new DocumentoFisicoBean();
                    dfb.setNomeFile(resultSet.getString("nome_file"));
                    dfb.setContentType(resultSet.getString("tip_doc"));
                    dfb.setDocumentoFisico(resultSet.getBlob("doc_blob"));
                }
                resultSet.close();
                preparedStatement.close();
                if (dfb == null) {
                    preparedStatement = db.getPreparedStmt(connection, sql);
                    preparedStatement.setString(1, codRif);
                    preparedStatement.setString(2, "it");
                    log.info("getDocumentoFisico 4: " + preparedStatement);
                    resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        dfb = new DocumentoFisicoBean();
                        dfb.setNomeFile(resultSet.getString("nome_file"));
                        dfb.setContentType(resultSet.getString("tip_doc"));
                        dfb.setDocumentoFisico(resultSet.getBlob("doc_blob"));
                    }
                    resultSet.close();
                    preparedStatement.close();
                }
            }
        } catch (Exception e) {
            log.error(e);
            throw e;
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ignore) {
            	log.error("", ignore);
            }
        }
        return dfb;

    }

    private boolean getSportelloDaIntervento(String codice, String codiceComune, Connection conn) throws Exception {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        boolean ret = false;
        String sql = "select s.flg_option_allegati from relazioni_enti re "
                + "join procedimenti p on re.cod_cud=p.cod_cud "
                + "join interventi i on p.cod_proc = i.cod_proc "
                + "join sportelli s on re.cod_sport=s.cod_sport "
                + "where i.cod_int = ? and re.cod_com = ?";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, Integer.valueOf(codice));
            preparedStatement.setString(2, codiceComune);
            log.info("getSportelloDaIntervento: " + preparedStatement);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                ret = resultSet.getString("flg_option_allegati").equalsIgnoreCase("s");
            }

        } catch (Exception e) {
            log.error(e);
            throw e;
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }

            } catch (SQLException ignore) {
            	log.error("", ignore);
            }
        }
        return ret;
    }
}
