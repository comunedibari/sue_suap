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
package it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.dao;

import it.gruppoinit.commons.DBCPManager;
import it.gruppoinit.commons.Utilities;
import it.people.core.ServiceProfileStore;
import it.people.core.ServiceProfileStoreManager;
import it.people.core.persistence.exception.peopleException;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.AllegatiBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.AllegatiBeanComparator;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.AllegatiBeanComparatorCodice;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.BaseBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.BaseBeanComparator;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.ComuneBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.DefinizioneCampoFormula;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.DocumentiBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.EventiVitaBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.HrefCampiBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.Input;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.InterventoBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.ListBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.NormativaBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.OneriBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.OneriBeanComparator;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.OperazioneBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.OperazioneRamoComparator;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.Output;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.Procedimento;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.ProcedimentoBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.ProcedimentoSempliceBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.ServiziBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.SettoriBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.SezioneCompilabileBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.Sportello;
import it.people.process.AbstractPplProcess;
import it.people.sirac.serviceprofile.xml.PeopleServiceProfile;
import it.people.sirac.serviceprofile.xml.PeopleServiceProfileDocument;
import it.people.sirac.serviceprofile.xml.Service;

import java.beans.IntrospectionException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.betwixt.io.BeanReader;
import org.apache.commons.betwixt.io.BeanWriter;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xmlbeans.XmlException;
import org.python.core.PyException;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;
import org.xml.sax.SAXException;


/**
 * Classe che si occupa dell'accesso al db per il recupero dei dati da presentare all'utente
 *
 * @author InIT http://www.gruppoinit.it
 *
 * 16-giu-2006
 */
public class ProcedimentoUnicoDAO {
	
    public ProcedimentoUnicoDAO(DBCPManager db, String language) {
        this.db = db;
        String dbType = this.db.getDATABASE();
        
        //se l'url del data source contiene mysql
        //attiva il flag per questo database
        if(dbType.toLowerCase().indexOf("mysql") != -1){
            isMySqlDb = true;
        //negli altri casi (Oracle al momento)     
        }else {
            isMySqlDb = false;
        }
        this.language = language;
        
    }

    public ProcedimentoUnicoDAO(DBCPManager db, String language,
            boolean initialisePythonInterpreter) {
        this(db, language);
        if (initialisePythonInterpreter) {
            interp = new PythonInterpreter();
        }
    }

    private PythonInterpreter interp = null;

    private Log log = LogFactory.getLog(this.getClass());

    protected DBCPManager db;

    private Set codRamoPrec = new TreeSet();

    private Set padri = new TreeSet(new OperazioneRamoComparator());

    private String language = "";
    
    private boolean isMySqlDb = true;

    /**
     * Restituisce una collection di oggetti ComuneBean
     * 
     * @return Collection
     */
    public List getComuni() throws SQLException {
        ArrayList list = new ArrayList();
        ResultSet rs = null;
        Connection conn = null;
        try {
            String sql = "select * from enti_comuni  ";
            sql += "inner join classi_enti on enti_comuni.cod_classe_ente=classi_enti.cod_classe_ente ";
            sql += "inner join comuni_aggregazione  on comuni_aggregazione.cod_ente = enti_comuni.cod_ente ";
            sql += "inner join enti_comuni_testi on enti_comuni.cod_ente = enti_comuni_testi.cod_ente and enti_comuni_testi.cod_lang = ? ";
            sql += "where classi_enti.flg_com='S' and enti_comuni.cod_ente=comuni_aggregazione.cod_ente order by des_ente ";

            log.debug("opening connection ");

            log.debug("db= " + db);

            conn = db.open();

            log.debug("connection opened:" + conn);
            log.debug("connection driver:" + conn.getMetaData().getDriverName());
            log.debug("connection url:" + conn.getMetaData().getURL());
            log.debug("connection user:" + conn.getMetaData().getUserName());

            PreparedStatement ps = db.getPreparedStmt(conn, sql);

            ps.setString(1, language);

            log.info("getComuni: " + ps);
            rs = db.select(conn, ps, sql);
            while (rs.next()) {
                ComuneBean bean = new ComuneBean();
                bean.setCap(rs.getString("cap"));
                bean.setCitta(rs.getString("des_ente"));
                bean.setEmail(rs.getString("email"));
                bean.setFax(Utilities.NVL(rs.getString("fax")));
                bean.setProvincia(rs.getString("prov"));
                bean.setTelefono(rs.getString("tel"));
                bean.setVia(rs.getString("indirizzo"));
                bean.setCodClasseEnte(rs.getString("cod_classe_ente"));
                bean.setCodEnte(rs.getString("cod_ente"));
                bean.setTipAggregazione(rs.getString("tip_aggregazione"));
                if(rs.getString("cod_istat") != null && !rs.getString("cod_istat").equalsIgnoreCase("")){
                    bean.setCodIstat(rs.getString("cod_istat")); 
                }
                if(rs.getString("cod_bf") != null && !rs.getString("cod_bf").equalsIgnoreCase("")){
                    bean.setCodBf(rs.getString("cod_bf"));
                }
                else{
                    // Da togliere: buono solo per FI
                    bean.setCodBf("054024");
                }
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

    /**
     * Recupera il settore di attivit�
     * 
     * @param codiceRamo
     * @param dataForm
     *            TODO
     * 
     * @return
     * @throws java.sql.SQLException
     */
    public List getSettoreAttivita(String codiceRamo, ProcessData dataForm) throws SQLException {
        // this is a branch test
        ArrayList list = new ArrayList();
        ResultSet rs = null;
        Connection conn = null;
        try {
            String sql = "Select a.cod_ramo, b.des_ramo, a.cod_ramo_prec, a.cod_sett, a.cod_rif ";
            sql += "from gerarchia_settori a inner join gerarchia_settori_testi b on a.cod_ramo=b.cod_ramo ";

            // la query viene eseguita per la prima volta
            if (codiceRamo == null || codiceRamo.equals("")) {
                sql += " Where cod_ramo_prec is null";
            } else {
                sql += " Where cod_ramo_prec = ?";
            }
            sql += " and b.cod_lang=? order by a.cod_ramo";

            log.debug("opening connection ");

            log.debug("db= " + db);

            conn = db.open();

            log.debug("connection opened:" + conn);
            log.debug("connection driver:" + conn.getMetaData().getDriverName());
            log.debug("connection url:" + conn.getMetaData().getURL());
            log.debug("connection user:" + conn.getMetaData().getUserName());

            PreparedStatement ps = db.getPreparedStmt(conn, sql);

            log.debug("ps: " + ps);
            int i = 1;
            if (!(codiceRamo == null || codiceRamo.equals(""))) {
                ps.setString(i, codiceRamo);
                i++;
            }
            ps.setString(i, getLanguage());

            log.debug("before executing resultset " + ps);
            rs = db.select(conn, ps, sql);
            log.debug("rs select done");
            while (rs.next()) {
                SettoriBean bean = new SettoriBean();
                bean.setCodice(rs.getString("cod_ramo"));
                bean.setCodiceSettore(rs.getString("cod_sett"));
                bean.setDescrizione(rs.getString("des_ramo"));
                if (dataForm.getDatiTemporanei().getCodiceRamo() != null)
                    bean.setChecked(dataForm.getDatiTemporanei().getCodiceRamo().equalsIgnoreCase(bean.getCodice()));
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

    /**
     * Restituisce la descrizione di un settore di attivit�
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    public String getSettoreAttivitaDescrizione(String codSettore) throws SQLException {
        ResultSet rs = null;
        Connection conn = null;
        try {
            String sql = "SELECT des_sett from settori_attivita where cod_sett=? and cod_lang=?";
            conn = db.open();
            PreparedStatement ps = db.getPreparedStmt(conn, sql);
            ps.setString(1, codSettore);
            ps.setString(2, language);

            rs = db.select(conn, ps, sql);
            if (rs.next()) {

                return (rs.getString("des_sett"));
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
        return null;
    }

    /**
     * Il metodo restituisce una mappa con due oggetti di tipo Set: 1)
     * codRamoPrec = contiene le stringhe con i codici dei rami precedenti 2)
     * padri = un Set contenente dei OperazioneBean con i padri delle operazioni
     * relative al settore di attivit�
     * 
     * @param codSettore
     * @return Map
     * @throws java.sql.SQLException
     */
    public Map getOperazioniIter3(String codSettore, String tipAggregazione, ProcessData dataForm) throws SQLException {
        Map map = new HashMap();

        ResultSet rs = null;
        Connection conn = null;
        String ramoPrec = null;
        try {
            /*String sql = "SELECT cod_ramo, cod_ramo_prec FROM gerarchia_operazioni inner join condizioni_di_attivazione on ";
            sql += "condizioni_di_attivazione.cod_ope=gerarchia_operazioni.cod_ope ";
            sql += "and condizioni_di_attivazione.tip_aggregazione=gerarchia_operazioni.tip_aggregazione ";
            sql += "Where condizioni_di_attivazione.cod_sett = ? and gerarchia_operazioni.tip_aggregazione=?";*/
            
            
            String sql = "select distinct a.cod_ramo, a.cod_ramo_prec from gerarchia_operazioni a, comuni_aggregazione b ";
            sql += "where a.tip_aggregazione = b.tip_aggregazione and b.cod_ente = ? and a.cod_ramo in (select distinct ";
            sql += "d.cod_ramo from condizioni_di_attivazione a ";
            sql += "inner join interventi b on a.cod_int = b.cod_int ";
            sql += "inner join gerarchia_operazioni d on a.cod_ope = d.cod_ope ";
            sql += "inner join comuni_aggregazione e on d.tip_aggregazione = e.tip_aggregazione and e.cod_ente =� ? ";
            sql += "left join interventi_comuni c on a.cod_int = c.cod_int ";
            sql += "where a.tip_aggregazione = e.tip_aggregazione and a.cod_sett = ? and c.cod_int is null ";
            sql += "union select distinct d.cod_ramo ";
            sql += "from condizioni_di_attivazione a, interventi b, interventi_comuni c, gerarchia_operazioni d, comuni_aggregazione e ";
            sql += "where a.tip_aggregazione = e.tip_aggregazione and a.cod_sett = ? and a.cod_int = b.cod_int and a.cod_int = c.cod_int and a.cod_ope = d.cod_ope and ";
            sql += "c.cod_com = ? and c.flg='S' and d.tip_aggregazione = e.tip_aggregazione and e.cod_ente = ? ";
            sql += "union select distinct d.cod_ramo from condizioni_di_attivazione a ";
            sql += "join interventi b on a.cod_int = b.cod_int join gerarchia_operazioni d on a.cod_ope = d.cod_ope ";
            sql += "join comuni_aggregazione e on d.tip_aggregazione = e.tip_aggregazione and e.cod_ente = ? ";
            sql += "left join interventi_comuni c on a.cod_int = c.cod_int ";
            sql += "where a.tip_aggregazione = e.tip_aggregazione and a.cod_sett = ? and c.cod_com != ? and c.flg='N'  ";
            sql += "and a.cod_int not in (select cod_int from interventi_comuni where cod_com = ? and flg = 'N')) ";
            sql += "order by 2 ";            

            conn = db.open();
            PreparedStatement ps = db.getPreparedStmt(conn, sql);
            /*ps.setString(1, codSettore);
            ps.setString(2, tipAggregazione);*/
            
            ps.setString(1, dataForm.getComune().getCodEnte());
            ps.setString(2, dataForm.getComune().getCodEnte());
            ps.setString(3, codSettore);
            ps.setString(4, codSettore);
            ps.setString(5, dataForm.getComune().getCodEnte());
            ps.setString(6, dataForm.getComune().getCodEnte());
            ps.setString(7, dataForm.getComune().getCodEnte());
            ps.setString(8, codSettore);
            ps.setString(9, dataForm.getComune().getCodEnte());
            ps.setString(10, dataForm.getComune().getCodEnte());

            rs = db.select(conn, ps, sql);

            while (rs.next()) {
                // ps = db.getPreparedStmt(conn, sql);
                ramoPrec = rs.getString("cod_ramo");
                getOperazioniEPadri(conn, ramoPrec, tipAggregazione, dataForm);
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
        log.debug(codRamoPrec.toString());
        map.put("padri", padri);
        map.put("codiciRamiPrec", codRamoPrec);
        return map;
    }

    /**
     * Il metodo, ricorsivo, serve a ricostruire l'albero delle operazioni
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    private void getOperazioniEPadri(Connection conn, String codiceRamoPrec, String tipoAggregazione, ProcessData dataForm) throws SQLException {

        codRamoPrec.add(codiceRamoPrec);
        ResultSet rs = null;
        String tmpCodRamoPrec = null;
        String sql = "SELECT gerarchia_operazioni.cod_ramo, cod_ramo_prec, des_ramo, cod_ope, cod_rif FROM gerarchia_operazioni inner join gerarchia_operazioni_testi ";
        sql += "on gerarchia_operazioni.cod_ramo=gerarchia_operazioni_testi.cod_ramo  Where gerarchia_operazioni.cod_ramo = ? and cod_lang=? ";
        sql += "and gerarchia_operazioni.tip_aggregazione= ?";
        PreparedStatement ps = db.getPreparedStmt(conn, sql);
        ps.setString(1, codiceRamoPrec);
        ps.setString(2, language);
        ps.setString(3, tipoAggregazione);
        rs = db.select(conn, ps, sql);

        if (rs.next()) {
            tmpCodRamoPrec = rs.getString("cod_ramo_prec");
            // log.debug("codice ramo precedente = " + tmpCodRamoPrec);
            if (tmpCodRamoPrec == null || tmpCodRamoPrec.equals("")) {
                // log.debug("Inside if(tmpCodRamoPrec==null ||
                // tmpCodRamoPrec.equals()");
                OperazioneBean bean = new OperazioneBean();
                bean.setCodiceRamo(rs.getString("cod_ramo"));
                bean.setCodice(rs.getString("cod_ope"));
                bean.setCodRif(rs.getString("cod_rif"));
                String tmp = StringEscapeUtils.escapeHtml(rs.getString("des_ramo"));
                tmp = tmp.replaceAll("�", "&agrave;");
                bean.setDescrizioneRamo(tmp);
                bean.setChecked(isChecked((bean.getCodiceRamo() == null ? "" : bean.getCodiceRamo())
                                          + "|"
                                          + (bean.getDescrizioneRamo() == null ? "" : bean.getDescrizioneRamo()), dataForm.getDatiTemporanei().getOpsVec(), false));
                padri.add(bean);

                // codRamoPrec.add(rs.getString("cod_ramo"));
                return;
            } else {
                codRamoPrec.add(tmpCodRamoPrec);
                if (!rs.getString("cod_ramo").equalsIgnoreCase(tmpCodRamoPrec)) {
                    getOperazioniEPadri(conn, tmpCodRamoPrec, tipoAggregazione, dataForm);
                } else {
                    log.debug("\n\n\ncodice ramo e precedente sono uguali:"
                              + tmpCodRamoPrec + "\n\n\n");
                    // TODO controllare
                    /*
                     * throw new SQLException("codice ramo e precedente sono
                     * uguali:" + tmpCodRamoPrec);
                     */
                }

            }

        }
//        if (!rs.isLast()) {
//            log.error("Non � un record unico:" + codiceRamoPrec);
//        }

        if (null != rs) {
            rs.close();
        }
        if (null != ps) {
            ps.close();
        }
    }

    /**
     * Restituisce il padre di una operazione
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    public TreeSet getPadriOps(String gerarchiaOperazioni) throws SQLException {
        ResultSet rs = null;
        TreeSet fathers = new TreeSet(new BaseBeanComparator());
        String arrOperazioni = getInClause(fathers, false);
        Connection conn = null;
        try {
            conn = db.open();
            String sql = "SELECT * FROM gerarchia_operazioni inner join gerarchia_operazioni_testi on "
                         + " gerarchia_operazioni.cod_cond=gerarchia_operazioni_testi.cod_cond Where "
                         + " cod_ramo in (" + arrOperazioni + ") and ";
            if (!(null == gerarchiaOperazioni || gerarchiaOperazioni.equals(""))) {
                sql += " cod_ramo_prec = ?";
            } else {
                sql += " cod_ramo_prec is null";
            }

            PreparedStatement ps = db.getPreparedStmt(conn, sql);

            if (!(null == gerarchiaOperazioni || gerarchiaOperazioni.equals(""))) {
                ps.setString(1, gerarchiaOperazioni);
            }

            rs = db.select(conn, ps, sql);
            while (rs.next()) {
                BaseBean bean = new BaseBean();
                bean.setCodice(rs.getString("cod_ramo"));
                bean.setDescrizione(rs.getString("testo_cond"));
                log.debug("father : " + bean.getCodice() + "---"
                          + bean.getDescrizione());
                fathers.add(bean);
            }

            return fathers;
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
     * Restituisce una collection di OperazioniBean
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    public List getOperazioniIter4(String[] checkBox, AbstractPplProcess process) throws SQLException {
        ArrayList list = new ArrayList();
        ResultSet rs = null;
        ResultSet operazioniRs = null;
        Connection conn = null;
        PreparedStatement ps = null;

        String inClause = getInClause((Set) ((ProcessData) process.getData()).getDatiTemporanei().getStackRami(), false);
        int maxDim = 4;
        String temp[] = new String[maxDim];

        String sql = "SELECT * FROM gerarchia_operazioni inner join gerarchia_operazioni_testi on ";
        sql += " gerarchia_operazioni.cod_ramo=gerarchia_operazioni_testi.cod_ramo ";
        sql += " Where gerarchia_operazioni.cod_ramo in (" + inClause + ") ";
        sql += " and cod_ramo_prec = ?  and cod_lang=? and gerarchia_operazioni.tip_aggregazione=?";
        sql += " and gerarchia_operazioni.tip_aggregazione=gerarchia_operazioni_testi.tip_aggregazione ";
        sql += " order by gerarchia_operazioni.cod_ramo";

        String sqlOperazioni = "SELECT des_ope FROM operazioni where cod_ope=? and cod_lang=?";

        try {
            conn = db.open();
            for (int i = 0; i < checkBox.length; i++) {

                temp = checkBox[i].split("\\|");

                OperazioneBean bean = new OperazioneBean();
                // codice ramo
                bean.setCodiceRamo(temp[0]);
                // descrizione ramo
                bean.setDescrizioneRamo(temp[1]);
                // controllo la dimensione dell'array perch� potrebbe
                // essere <=2
                if (temp.length == maxDim) {
                    // codice operazione
                    bean.setCodice(temp[2]);
                    // descrizione operazione
                    bean.setDescrizione(temp[3]);
                    // se cod_ope � valorizzato, il ramo � considerato chiuso
                    // e la scelta viene salvata nelle operazioni individuate
                    if (temp[2] != null && !temp[2].equalsIgnoreCase("")) {
                        ((ProcessData) process.getData()).getOperazioniIndividuate().add(bean);
                    }
                }

                // memorizza l'operazione selezionata
                ((ProcessData) process.getData()).getOperazioniSelezionate().add(bean);

                ListBean lBean = new ListBean();
                // codice ramo
                lBean.setCodice(temp[0]);
                lBean.setDescrizione(StringEscapeUtils.unescapeHtml(temp[1]));
                // descrizione ramo
                ps = db.getPreparedStmt(conn, sql);
                ps.setString(1, temp[0]);
                ps.setString(2, language);
                ps.setString(3, ((ProcessData) process.getData()).getComune().getTipAggregazione());
                rs = db.select(conn, ps, sql);
                ArrayList rows = new ArrayList();
                while (rs.next()) {

                    OperazioneBean dataBean = new OperazioneBean();
                    dataBean.setCodiceRamo(rs.getString("cod_ramo"));
                    dataBean.setCodice(rs.getString("cod_ope"));
                    dataBean.setDescrizioneRamo(rs.getString("des_ramo"));
                    dataBean.setCodRif(rs.getString("cod_rif"));

                    ps = db.getPreparedStmt(conn, sqlOperazioni);
                    ps.setString(1, rs.getString("cod_ope"));
                    ps.setString(2, language);
                    operazioniRs = db.select(conn, ps, sqlOperazioni);
                    // recupera la descrizione dell'operazione
                    if (operazioniRs.next()) {
                        dataBean.setDescrizione(StringEscapeUtils.escapeHtml(operazioniRs.getString("des_ope")));
                    }
                    if (process.getValidationErrors().isEmpty()) {
                        dataBean.setChecked(isChecked((dataBean.getCodiceRamo() == null ? "" : dataBean.getCodiceRamo())
                                                      + "|"
                                                      + (dataBean.getDescrizioneRamo() == null ? "" : dataBean.getDescrizioneRamo())
                                                      + "|"
                                                      + (dataBean.getCodice() == null ? "" : dataBean.getCodice())
                                                      + "|"
                                                      + (dataBean.getDescrizione() == null ? "" : dataBean.getDescrizione()), ((ProcessData) process.getData()).getDatiTemporanei().getOpsVec(), false));
                        // if (dataBean.isChecked()) {
                        // ((ProcessData)
                        // process.getData()).setCheckBoxValueSelected(true);
                        // }
                    }
                    rows.add(dataBean);
                }

                if (rows.size() > 0) {
                    lBean.setChildren(rows);
                    list.add(lBean);
                }
            }
            return list;
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
     * Restituisce una lista di descrizioni operazioni relativamente al set di codici operazione passato come parametro
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    public List getOperazioniSelezionateDescr(TreeSet operazioniSelezionate) throws SQLException {
        ArrayList list = new ArrayList();
        ResultSet rs = null;
        Connection conn = null;

        try {
            conn = db.open();
            Iterator it = operazioniSelezionate.iterator();
            while (it.hasNext()) {

                String operazione = (String) it.next();

                String sql = "SELECT des_ope from operazioni Where ";
                sql += " cod_ope = ? and cod_lang=?";
                sql += " order by des_ope";

                PreparedStatement ps = db.getPreparedStmt(conn, sql);
                ps.setString(1, operazione);
                ps.setString(2, language);
                rs = db.select(conn, ps, sql);
                while (rs.next()) {
                    list.add(rs.getString("des_ope"));
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
        return list;
    }

    /**
     * Setta nel process data gli interventi selezionati in base al settore attivit� ed alle operazioni selezionate dall'utente
     * 
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    public void getInterventi(ProcessData dataForm) throws SQLException, Exception {
        ResultSet rs = null;
        ResultSet rsTestoCondizione = null;
        Connection conn = null;
        Set operazioniSet = new TreeSet();
        Iterator it = dataForm.getOperazioniIndividuate().iterator();
        while (it.hasNext()) {
            OperazioneBean bean = (OperazioneBean) it.next();
            operazioniSet.add(bean.getCodice());
        }
        String inClause = getInClause(operazioniSet, false);
        try {
            conn = db.open();
            String sql = "";
            // Federico - 28/04/2006 corretto bug segnalato dal Comune di
            // Firenze: gli interventi selezionati
            // non recuperano gli interventi collegati:
            // imprecisione nell'analisi di wego "Specifiche tecniche
            // Autorizzazioni e Concessioni.pdf".
            // L'accesso alla tabella condizioni di attivazione deve
            // avvenire con chiave
            // condizioni_di_attivazione.cod_int=interventi_collegati.cod_int_padre
            // la join con interventi_testi deve avvenire con chiave
            // interventi_collegati.cod_int=interventi_testi.cod_int
            // per recuperare correttamente i testi
            /*
             * NICOLA
             * 
             */
            /*sql += " SELECT  condizioni_di_attivazione.cod_int, A.tit_int, interventi_collegati.cod_int cod_int_collegato,";
            sql += " B.tit_int tit_int_collegato, interventi_collegati.cod_cond, condizioni_di_attivazione.cod_ope";
            sql += " from condizioni_di_attivazione join interventi_testi A on A.cod_int=condizioni_di_attivazione.cod_int";
            sql += " left outer join interventi_collegati  on condizioni_di_attivazione.cod_int=interventi_collegati.cod_int_padre";
            sql += " left join interventi_testi B on B.cod_int=interventi_collegati.cod_int  and B.cod_lang = ? ";
            sql += " Where condizioni_di_attivazione.cod_sett=?";
            sql += " and  condizioni_di_attivazione.cod_ope in (" + inClause
                   + ")";
            sql += " and condizioni_di_attivazione.tip_aggregazione=?";
            sql += " and A.cod_lang=?";*/
            
            sql += " select distinct a.cod_int, tit_int, cod_ope";
            sql += " from condizioni_di_attivazione a";
            sql += " join interventi_testi b on a.cod_int = b.cod_int and b.cod_lang = ?";
            sql += " join comuni_aggregazione e on e.cod_ente = ? and a.tip_aggregazione = e.tip_aggregazione";
            sql += " join interventi_comuni c on a.cod_int = c.cod_int and c.flg='S'";
            sql += " where a.cod_sett = ? and a.cod_ope in (" + inClause + ") and c.cod_com = ?";
            sql += " union";
            sql += " select distinct a.cod_int, tit_int, cod_ope";
            sql += " from condizioni_di_attivazione a";
            sql += " join interventi_testi b on a.cod_int = b.cod_int and b.cod_lang = ?";
            sql += " join comuni_aggregazione e on e.cod_ente = ? and a.tip_aggregazione = e.tip_aggregazione";
            sql += " left join interventi_comuni c on a.cod_int = c.cod_int";
            sql += " where a.cod_sett = ? and a.cod_ope in (" + inClause + ") and c.cod_int is null";
            sql += " union";
            sql += " select distinct a.cod_int, tit_int, cod_ope from condizioni_di_attivazione a";
            sql += " join interventi_testi b on a.cod_int = b.cod_int and b.cod_lang = ?";
            sql += " join comuni_aggregazione e on a.tip_aggregazione = e.tip_aggregazione and e.cod_ente = ?";
            sql += " left join interventi_comuni c on a.cod_int = c.cod_int";
            sql += " where a.cod_sett = ? and a.cod_ope in (" + inClause + ") and c.cod_com != ? and c.flg='N' and";
            sql += " a.cod_int not in (select cod_int from interventi_comuni where cod_com = ? and flg = 'N') order by 1";
            
            PreparedStatement ps = db.getPreparedStmt(conn, sql);
            ps.setString(1, language);
            ps.setString(2, dataForm.getComune().getCodEnte());
            ps.setString(3, dataForm.getCodiceSettore());
            ps.setString(4, dataForm.getComune().getCodEnte());
            ps.setString(5, language);
            ps.setString(6, dataForm.getComune().getCodEnte());
            ps.setString(7, dataForm.getCodiceSettore());
            ps.setString(8, language);
            ps.setString(9, dataForm.getComune().getCodEnte());
            ps.setString(10, dataForm.getCodiceSettore());
            ps.setString(11, dataForm.getComune().getCodEnte());
            ps.setString(12, dataForm.getComune().getCodEnte());
            rs = db.select(conn, ps, sql);

            dataForm.setInterventiFacoltativi(new TreeSet(new BaseBeanComparator()));
            while (rs.next()) {
                if (rs.getString("cod_int") != null) {
                    InterventoBean bean = new InterventoBean();
                    bean.setCodice(rs.getString("cod_int"));
                    bean.setDescrizione(rs.getString("tit_int"));
                    bean.setCodiceOperazioneAttivante(rs.getString("cod_ope"));
                    dataForm.getInterventiSelezionati().add(bean);
                }
            }
            //FACCIO L'ALTRA QUERY DOPO AVER SALVATO LA LISTA DEI COD_INT DALLA QUERY PRECEDENTE
            Set interventiSet = new TreeSet();
            it = dataForm.getInterventiSelezionati().iterator();
            while (it.hasNext()) {
                InterventoBean bean = (InterventoBean) it.next();
                interventiSet.add(bean.getCodice());
            }
            
            String lista_cod_int = getInClause(interventiSet, false);
            sql = "";
            sql += " select a.cod_int, a.cod_int_padre, a.cod_cond, tit_int, cod_ope";
            sql += " from interventi_collegati a";
            sql += " join interventi_comuni b on a.cod_int = b.cod_int and b.cod_com = ? and b.flg = 'S'";
            sql += " inner join interventi_testi c on c.cod_int = a.cod_int and cod_lang = ?";
            sql += " left outer join condizioni_di_attivazione d on d.cod_int = a.cod_int and cod_sett = ? and tip_aggregazione = ?";
            sql += " where a.cod_int_padre in ("+ lista_cod_int +")";
            sql += " union ";
            sql += " select a.cod_int, a.cod_int_padre, a.cod_cond, tit_int, cod_ope";
            sql += " from interventi_collegati a";
            sql += " left join interventi_comuni b on a.cod_int = b.cod_int";
            sql += " inner join interventi_testi c on c.cod_int = a.cod_int and cod_lang = ?";
            sql += " left outer join condizioni_di_attivazione d on d.cod_int = a.cod_int and cod_sett = ? and tip_aggregazione = ?";
            sql += " where a.cod_int_padre in ("+ lista_cod_int +") and b.cod_int is null "; 
            sql += " union  ";
            sql += " select a.cod_int, a.cod_int_padre, a.cod_cond, tit_int, cod_ope ";
            sql += " from interventi_collegati a";
            sql += " left join interventi_comuni b";
            sql += " on a.cod_int = b.cod_int";
            sql += " inner join interventi_testi c on c.cod_int = a.cod_int and cod_lang = ?";
            sql += " left outer join condizioni_di_attivazione d on d.cod_int = a.cod_int and cod_sett = ? and tip_aggregazione = ?";
            sql += " where a.cod_int_padre in ("+ lista_cod_int +") and b.cod_com != ? and b.flg = 'N' and";
            sql += " a.cod_int not in (select cod_int from interventi_comuni where cod_com = ? and flg = 'N')";

            PreparedStatement ps2 = db.getPreparedStmt(conn, sql);
            ps2.setString(1, dataForm.getComune().getCodEnte());
            ps2.setString(2, language);
            ps2.setString(3, dataForm.getCodiceSettore());
            ps2.setString(4, dataForm.getComune().getTipAggregazione());
            ps2.setString(5, language);
            ps2.setString(6, dataForm.getCodiceSettore());
            ps2.setString(7, dataForm.getComune().getTipAggregazione());
            ps2.setString(8, language);
            ps2.setString(9, dataForm.getCodiceSettore());
            ps2.setString(10, dataForm.getComune().getTipAggregazione());
            ps2.setString(11, dataForm.getComune().getCodEnte());
            ps2.setString(12, dataForm.getComune().getCodEnte());
            ResultSet rs2 = db.select(conn, ps2, sql);
            
            while (rs2.next()) {
                if (rs2.getString("cod_int") != null) {
                    if(rs2.getString("cod_cond") != null){
                        sql = "";
                        sql += "select testo_cond from testo_condizioni where cod_cond=? and cod_lang = ? ";
                        ps2 = db.getPreparedStmt(conn, sql);
                        ps2.setString(1, rs2.getString("cod_cond"));
                        ps2.setString(2, language);
                        rsTestoCondizione = db.select(conn, ps2, sql);
                        if (rsTestoCondizione.next()) {
                            InterventoBean facoltativo = new InterventoBean();
                            facoltativo.setCodice(rs2.getString("cod_int"));
                            facoltativo.setDescrizione(rs2.getString("tit_int"));
                            facoltativo.setTestoCondizione(rsTestoCondizione.getString("testo_cond"));
                            String st = facoltativo.getCodice() + "|"
                                        + facoltativo.getDescrizione();
                            facoltativo.setChecked(isChecked(st, dataForm.getDatiTemporanei().getOpsVec(), false));
                            
                            boolean trovato = false;
                            Iterator itInt = dataForm.getInterventiFacoltativi().iterator();
                            while(itInt.hasNext()){
                                InterventoBean intBean = (InterventoBean)itInt.next();
                                if(intBean.getTestoCondizione().equalsIgnoreCase(rsTestoCondizione.getString("testo_cond"))){
                                    trovato = true;
                                    intBean.getInterventiConCondizioneUguale().add(facoltativo);
                                    break;
                                }
                            }
                            if(!trovato){
                                dataForm.getInterventiFacoltativi().add(facoltativo);
                            }
                        }else {
                            InterventoBean facoltativo = new InterventoBean();
                            facoltativo.setCodice(rs2.getString("cod_int"));
                            facoltativo.setDescrizione(rs2.getString("tit_int"));
                            //se esiste un intervento facoltativo ma manca il testo condizione lo inserisco
                            //e mostro un messaggio di avvertimento
                            facoltativo.setTestoCondizione("<font color='red'>Testo condizione non trovato nella base dati per l'intervento "+facoltativo.getCodice()+"</font>");
                            String st = facoltativo.getCodice() + "|"
                                        + facoltativo.getDescrizione();
                            facoltativo.setChecked(isChecked(st, dataForm.getDatiTemporanei().getOpsVec(), false));
                            dataForm.getInterventiFacoltativi().add(facoltativo);
                        }
    
                    } else {
                        InterventoBean bean = new InterventoBean();
                        bean.setCodice(rs2.getString("cod_int"));
                        bean.setDescrizione(rs2.getString("tit_int"));
                        bean.setCodiceOperazioneAttivante(rs2.getString("cod_ope"));
                        dataForm.getInterventiSelezionati().add(bean);
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

    /**
     * Restituisce l'oggetto InterventoBean relativo al cod_int_padre passato come parametro
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    public InterventoBean getInterventoPadre(ProcessData dataForm, String cod_int_padre) throws SQLException, Exception {
        ResultSet rs = null;
        Connection conn = null;
        InterventoBean interventoBean = null;
        try {
            conn = db.open();
            String sql = "";
            sql += "select * from interventi_testi  ";
            sql += "where cod_int = ? and cod_lang = ? ";

            PreparedStatement ps = db.getPreparedStmt(conn, sql);
            ps.setString(1, cod_int_padre);
            ps.setString(2, language);
            rs = db.select(conn, ps, sql);

            while (rs.next()) {
                interventoBean = new InterventoBean();
                interventoBean.setCodice(rs.getString("cod_int"));
                interventoBean.setDescrizione(rs.getString("tit_int"));
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
        return interventoBean;
    }
    
    /**
     * Restituisce true se l'intervento passa il test su interventi_comuni.
     * L'intervento � valido se:
     * 1) Non ha una row su interventi_comuni per il comune selezionato
     * 2) Ha una row in interventi_comuni ma il flg='Y'
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    public boolean isInterventoValido(ProcessData dataForm, String cod_int) throws SQLException, Exception {
        ResultSet rs = null;
        Connection conn = null;
        boolean retVal = false;
        try {
            conn = db.open();
            String sql = "";
            sql += "select cod_int, cod_com, flg from interventi_comuni ";
            sql += "where cod_int = ? ";

            PreparedStatement ps = db.getPreparedStmt(conn, sql);
            ps.setString(1, cod_int);
            //ps.setString(2, dataForm.getComune().getCodEnte());
            rs = db.select(conn, ps, sql);
            boolean almenoUno = false;
            while (rs.next()) {
                almenoUno = true;
                String comune = rs.getString("cod_com");
                String flg = rs.getString("flg");
                if(comune.equalsIgnoreCase(dataForm.getComune().getCodEnte()) && (flg.equalsIgnoreCase("Y") || flg.equalsIgnoreCase("S"))){
                    retVal = true;
                }
            }
            if(!almenoUno){
                retVal = true;
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
        return retVal;
    }

    /**
     * Recupera le descrizioni delle opzioni per individuare il settore di
     * attivit�
     * 
     * @param ArrayList
     *            opzioni
     * @return
     * @throws java.sql.SQLException
     */
    public List getOpzioniSettoreAttivitaDescr(List opzioni) throws SQLException {
        ArrayList list = new ArrayList();
        ResultSet rs = null;
        Connection conn = null;
        Iterator it = opzioni.iterator();
        conn = db.open();
        try {
            while (it.hasNext()) {
                String sql = "SELECT des_ramo FROM gerarchia_settori inner join gerarchia_settori_testi ";
                sql += " on gerarchia_settori.cod_ramo=gerarchia_settori_testi.cod_ramo and gerarchia_settori_testi.cod_lang='"
                       + language + "' ";
                sql += " Where gerarchia_settori.cod_ramo = ?";

                PreparedStatement ps = db.getPreparedStmt(conn, sql);
                String cod_ramo = (String) it.next();
                if (cod_ramo != null) {
                    ps.setString(1, cod_ramo);
                }
                rs = db.select(conn, ps, sql);

                if (rs.next()) {
                    list.add(rs.getString("des_ramo"));
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
        return list;
    }

    /**
     * Memorizza nel ProcessData gli interventi facoltativi selezionati dall'utente
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    public void memorizzaInterventiFacoltativi(ProcessData dataForm) {
        //dataForm.getInterventiFacoltativi().clear();
        String[] checkBox = dataForm.getOpsVec();
        // nessun intervento facoltativo � stato selezionato
        if (checkBox == null) {
            return;
        }
        String[] temp = new String[2];
        for (int i = 0; i < checkBox.length; i++) {
            temp = checkBox[i].split("\\|");
            InterventoBean bean = new InterventoBean();
            bean.setCodice(temp[0]);
            bean.setDescrizione(temp[1]);
            dataForm.getInterventiFacoltativi().add(bean);
            dataForm.getInterventiSelezionati().add(bean);
            
            Iterator it = dataForm.getInterventiFacoltativi().iterator();
            while(it.hasNext()){
                InterventoBean intBean = (InterventoBean) it.next();
                if(intBean.getCodice().equalsIgnoreCase(temp[0])){
                    if(!intBean.getInterventiConCondizioneUguale().isEmpty()){
                        Iterator it2 = intBean.getInterventiConCondizioneUguale().iterator();
                        while(it2.hasNext()){
                            InterventoBean itBean = (InterventoBean) it2.next();
                            dataForm.getInterventiSelezionati().add(itBean);
                        }
                    }
                }
            }
        }
    }

    /**
     * Memorizza nel ProcessData gli allegati facoltativi selezionati dall'utente
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    public void memorizzaAllegatiFacoltativi(ProcessData dataForm) {
        String[] checkBox = dataForm.getOpsVec();
        if (checkBox == null) {
            return;
        }
        String[] temp = new String[3];
        Set allegati = dataForm.getAllegatiBuffer();
        for (int i = 0; i < checkBox.length; i++) {
            temp = checkBox[i].split("\\|");
            Iterator iter = allegati.iterator();
            while (iter.hasNext()) {
                AllegatiBean tempBean = (AllegatiBean) iter.next();
                if (tempBean.getCodice().equalsIgnoreCase(temp[0])
                    && tempBean.getCodiceIntervento().equalsIgnoreCase(temp[1])
                    && tempBean.getCodiceCondizione().equalsIgnoreCase(temp[2])) {
                    dataForm.getAllegatiSelezionati().add(tempBean);
                    if (tempBean.getAllegatiConDescUguale() != null) {
                        Iterator it = tempBean.getAllegatiConDescUguale().iterator();
                        while (it.hasNext()) {
                            dataForm.getAllegatiSelezionati().add((AllegatiBean) it.next());
                        }
                    }
                }

            }
        }
        dataForm.getAllegatiBuffer().clear();
    }

    /**
     * Restuituisce una stringa con le stringhe del set separate da vigole
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    public String getInClause(Set set) {
        String str = "";
        if (set == null)
            return str;
        Object[] arOps = set.toArray();
        for (int i = 0; i < arOps.length; i++) {
            str += ((String) arOps[i]) + ",";
        }
        if (str != null && !"".equalsIgnoreCase(str))
            str = str.substring(0, str.lastIndexOf(","));
        return str;
    }

    /**
     * Restuituisce una stringa con le stringhe del set separate da vigole e con le stringhe racchiuse da apici se !isNumber
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    public String getInClause(Set set, boolean isNumber) {
        if ((set == null || set.size() == 0) && !isNumber)
            return "''";
        if (isNumber) {
            return getInClause(set);
        }
        String str = "'";
        Object[] arOps = set.toArray();
        for (int i = 0; i < arOps.length; i++) {
            str += ((String) arOps[i]) + "','";
        }
        str = str.substring(0, str.lastIndexOf(",'"));
        return str;
    }

    public DBCPManager getDb() {
        return db;
    }

    public void setDb(DBCPManager db) {
        this.db = db;
    }

    /**
     * Restituisce in formato html le scelte fatte dall'utente nella definizione del settore di attivit�
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    public String trovaScelte(List opzioniSettoreAttivita) throws SQLException {
        String answer = "";
        ResultSet rs = null;
        Connection conn = null;
        String inClause = "";
        Iterator it = opzioniSettoreAttivita.iterator();

        while (it.hasNext()) {
            inClause += "'"+(String) it.next()+"'";
            inClause += ",";
        }
        if (null == inClause || inClause.equals("")) {
            return "";
        }
        inClause = inClause.substring(0, inClause.lastIndexOf(","));
        try {
            conn = db.open();

            String sql = "SELECT * FROM gerarchia_settori inner join gerarchia_settori_testi";
            sql += " on gerarchia_settori.cod_ramo=gerarchia_settori_testi.cod_ramo ";
            sql += " Where gerarchia_settori.cod_ramo in (" + inClause
                   + ") and cod_lang=? order by cod_ramo_prec asc";

            PreparedStatement ps = db.getPreparedStmt(conn, sql);
            ps.setString(1, language);
            log.debug("trovaScelte query:" + sql);
            rs = db.select(conn, ps, sql);
            answer = "<ul>";
            while (rs.next()) {
                answer += "<li>";
                answer += rs.getString("des_ramo");
                answer += "</li>";

                // list.add(rs.getString("testo_cond"));
            }
            answer += "</ul>";
            return answer;
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

    // /**
    // * Il metodo recupera i procedimenti che l'utente pu� facoltativamente
    // * attivare presso il SUAP o presso l'ente competente
    // *
    // * @param List
    // * procedimenti
    // * @return List di oggetti ProcedimentoSempliceBean
    // */
    // public List getProcedimentiSuapOrEnteCompetente(List procedimenti) {
    // if (procedimenti == null) {
    // return null;
    // }
    // ArrayList list = new ArrayList();
    // Iterator it = procedimenti.iterator();
    // while (it.hasNext()) {
    // ProcedimentoBean procedimento = (ProcedimentoBean) it.next();
    // ProcedimentoSempliceBean bean = procedimento.getProcedimento();
    // if (bean.getTipo() == ProcedimentoSempliceBean.PROCEDIMENTO_ENTRAMBI) {
    // list.add(bean);
    // }
    // }
    // return list;
    // }

    // /**
    // * Il metodo recupera le sezioni compilabili dall'utente per l'inserimento
    // * dati nei forms del Modello Unico relativo alla sottofunzione
    // * ModelloUnico_5
    // *
    // * @param interventiSelezionati
    // * il set degli interventi selezionati
    // * @param allegatiSelezionati
    // * il set degli allegati Selezionati
    // * @return un oggetto List di SezioniBean
    // */
    // public List getSezioniCompilabili(ProcessData dataForm) throws
    // SQLException {
    //
    // ArrayList answer = new ArrayList();
    // if (dataForm.getInterventiSelezionati().size() == 0) {
    // //non ci sono interventi selezionati
    // //lancio un eccezione?
    // return answer;
    // }
    //
    // String inClause = getInClause(dataForm.getInterventiSelezionati(), true);
    // StringBuffer sb = new StringBuffer("");
    // String tieniAllegato = "";
    // ResultSet rs = null;
    // Connection conn = null;
    // boolean trovato = false;
    // Iterator it = null;
    // try {
    // conn = db.open();
    //
    // sb.append("SELECT allegati.flg_autocert,allegati.cod_int,
    // documenti.*,href.tit_ref ");
    // sb.append("FROM documenti inner join allegati on ");
    // sb.append("documenti.cod_doc=allegati.cod_doc ");
    // sb.append(" inner join href on href.href=documenti.href ");
    // sb.append("where allegati.cod_int in (");
    // sb.append(inClause);
    // sb.append(") ");
    //
    // PreparedStatement ps = db.getPreparedStmt(conn, sb.toString());
    //
    // rs = db.select(conn, ps, sb.toString());
    //
    // while (rs.next()) {
    // SezioniBean sBean = new SezioniBean();
    // tieniAllegato = "N";
    // if (rs.getInt("flg_autocert") == 0) {
    // tieniAllegato = "S";
    // } else {
    // // Ilprocedimento � autocertificato?
    // // se Si allora TieniAllegato="S"
    // // altrimenti TieniAllegato ="N"
    // }
    // if (tieniAllegato.equalsIgnoreCase("S")) {
    // trovato = false;
    // it = dataForm.getAllegatiSelezionati().iterator();
    // if (null != it) {
    // while (it.hasNext()) {
    // AllegatiBean aBean = (AllegatiBean) it.next();
    // if (rs.getString("cod_doc").equalsIgnoreCase(aBean.getCodice())
    // && rs.getString("cod_int").equalsIgnoreCase(aBean.getCodiceIntervento()))
    // {
    // trovato = true;
    // if (rs.getInt("flg_dic") == 1) {
    // sBean.setDescrizione(rs.getString("tit_ref"));
    // } else {
    // tieniAllegato = "N";
    // }
    // }
    // }
    // }
    // if (!trovato) {
    // tieniAllegato = "N";
    // }
    // }
    // if (tieniAllegato.equalsIgnoreCase("S")) {
    //
    // // Aggiungi il titolo della sezione linkando il richiamo
    // // alla sottofunzione
    // // se l'identificativo della sezione href.idref � presente
    // // nel campo MUVE
    // // segna il titolo della sezione in colore rosso anteponendo
    // // un asterisco
    // // sBean.setStile(true);
    //
    // //come se fa ???
    // }
    // // list.add(rs.getString("testo_cond"));
    // }
    //
    // return answer;
    // } catch (SQLException e) {
    // log.error(e);
    // throw e;
    // } finally {
    // try {
    // rs.close();
    // } catch (Exception e) {
    // }
    // try {
    // conn.close();
    // } catch (Exception e) {
    // }
    // }
    //
    // }

    /**
     * Restituisce la lingua del servizio
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Setta la lingua del servizio
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * Restituisce se ci sono oneri da pagare
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    public boolean checkOneri(Set interventi, String codComune) throws SQLException {
        ResultSet rs = null;
        Connection conn = null;
        String inClause = "'";
        Iterator it = interventi.iterator();

        while (it.hasNext()) {

            InterventoBean bean = (InterventoBean) it.next();

            inClause += bean.getCodice();
            inClause += "','";
        }
        if (null == inClause || inClause.equals("'")) {
            // return false;
        }

        inClause = inClause.substring(0, inClause.lastIndexOf(",'"));
        try {
            conn = db.open();

            String sql = "";
            sql += "select distinct d.cod_oneri ";
            sql += "from interventi a  ";
            sql += "inner join  procedimenti b on  a.cod_proc=b.cod_proc ";
            sql += "inner join  oneri_procedimenti c on  b.cod_proc=c.cod_proc ";
            sql += "inner join  oneri d on  c.cod_oneri=d.cod_oneri ";
            sql += "inner join  oneri_comuni e on  d.cod_oneri = e.cod_oneri ";
            sql += "inner join  oneri_testi f on d.cod_oneri = f.cod_oneri and f.cod_lang=? ";
            sql += "inner join  relazioni_enti h on h.cod_com = ? and h.cod_cud = d.cod_cud ";
            sql += "inner join  destinatari i on h.cod_dest = i.cod_dest ";
            sql += "left join oneri_documenti g on d.cod_doc_onere = g.cod_doc_onere ";
            sql += "where a.cod_int in ("+inClause+") and  a.flg_obb <> 'N' and  e.cod_com = ? and  e.flg='S' ";
            sql += "union ";
            sql += "select distinct d.cod_oneri ";
            sql += "from interventi a  ";
            sql += "inner join  procedimenti b on  a.cod_proc=b.cod_proc ";
            sql += "inner join  oneri_procedimenti c on  b.cod_proc=c.cod_proc ";
            sql += "inner join  oneri d on  c.cod_oneri=d.cod_oneri ";
            sql += "inner join  oneri_testi f on d.cod_oneri = f.cod_oneri and f.cod_lang=? ";
            sql += "inner join  relazioni_enti h on h.cod_com = ? and h.cod_cud = d.cod_cud ";
            sql += "inner join  destinatari i on h.cod_dest = i.cod_dest ";
            sql += "left join  oneri_comuni e on  d.cod_oneri = e.cod_oneri ";
            sql += "left join oneri_documenti g on d.cod_doc_onere = g.cod_doc_onere ";
            sql += "where a.cod_int in ("+inClause+") and  a.flg_obb <> 'N' and  e.cod_com is null ";
            sql += "union ";
            sql += "select distinct d.cod_oneri ";
            sql += "from interventi a  ";
            sql += "inner join  procedimenti b on  a.cod_proc=b.cod_proc ";
            sql += "inner join oneri_procedimenti c on  b.cod_proc=c.cod_proc ";
            sql += "inner join oneri d on  c.cod_oneri=d.cod_oneri ";
            sql += "inner join  oneri_comuni e on  d.cod_oneri = e.cod_oneri ";
            sql += "inner join  oneri_testi f on d.cod_oneri = f.cod_oneri and f.cod_lang=? ";
            sql += "inner join  relazioni_enti h on h.cod_com = ? and h.cod_cud = d.cod_cud ";
            sql += "inner join  destinatari i on h.cod_dest = i.cod_dest ";
            sql += "left join oneri_documenti g on d.cod_doc_onere = g.cod_doc_onere  ";
            sql += "where a.cod_int in ("+inClause+") and  a.flg_obb <> 'N' and  e.cod_com <> ? and e.flg='N' ";
            sql += "and d.cod_oneri not in ( select distinct d.cod_oneri from ";
            sql += "interventi a inner join procedimenti b on a.cod_proc=b.cod_proc ";
            sql += "inner join oneri_procedimenti c on b.cod_proc=c.cod_proc ";
            sql += "inner join oneri d on c.cod_oneri=d.cod_oneri ";
            sql += "inner join oneri_comuni e on d.cod_oneri = e.cod_oneri ";
            sql += "where ";
            sql += "e.cod_com = ? and a.cod_int in ("+inClause+") and a.flg_obb <> 'N' and e.flg = 'N') ";
            
            PreparedStatement ps = db.getPreparedStmt(conn, sql);
            ps.setString(1, language);
            ps.setString(2, codComune);
            ps.setString(3, codComune);
            ps.setString(4, language);
            ps.setString(5, codComune);
            ps.setString(6, language);
            ps.setString(7, codComune);
            ps.setString(8, codComune);
            ps.setString(9, codComune);

            log.debug("checkOneri query:" + sql);
            rs = db.select(conn, ps, sql);

            if (rs.next()) {
                return true;
            } else {
                return false;
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
     * Restituisce l'albero degli oneri a tariffario
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    /*public List getOneriAlbero(List procedimenti, ProcessData dataForm, String codCom) throws SQLException {

        // DefaultMutableTreeNode answer = new DefaultMutableTreeNode(new
        // OneriBean());
        ArrayList answer = new ArrayList();

        ResultSet rs = null;
        Connection conn = null;
        String inClause = "'";
        Iterator it = procedimenti.iterator();

        while (it.hasNext()) {

            ProcedimentoBean bean = (ProcedimentoBean) it.next();

            ProcedimentoSempliceBean procedimento = bean.getProcedimento();
            inClause += procedimento.getCodice();
            inClause += "','";
        }
        if (null == inClause || inClause.equals("'")) {
            // return false;
        }

        inClause = inClause.substring(0, inClause.lastIndexOf(",'"));
        try {
            conn = db.open();

            String sql = "";
            // test massimo 16/02/2006
            // sql += "SELECT distinct oneri.*, oneri_testi.des_oneri, ";
            sql += "SELECT oneri.*, oneri_testi.des_oneri, oneri_testi.note, ";
            sql += "                 oneri_documenti.nome_file, relazioni_enti.cod_dest, nome_dest ";
            sql += "                 FROM oneri  ";
            sql += "                 inner join oneri_procedimenti on  ";
            sql += "                 oneri.cod_oneri = oneri_procedimenti.cod_oneri ";
            sql += "                left outer join oneri_documenti on   ";
            sql += "                oneri_documenti.cod_doc_onere = oneri.cod_doc_onere ";
            sql += "                 inner join oneri_testi on  ";
            sql += "                oneri.cod_oneri = oneri_testi.cod_oneri and  ";
            sql += "    oneri_testi.cod_lang = ?    ";
            sql += "    left join oneri_comuni on oneri_comuni.cod_oneri = oneri_procedimenti.cod_oneri and oneri_comuni.cod_com = ? ";
            sql += " left join relazioni_enti on relazioni_enti.cod_com = ? and relazioni_enti.cod_cud = oneri.cod_cud";
            sql += " left join destinatari on destinatari.cod_dest = relazioni_enti.cod_dest";
            sql += " Where oneri_procedimenti.cod_proc in (" + inClause
                   + ") and " + (isMySqlDb?"ifnull":"nvl") + "(flg, 'S') != 'N'";

            PreparedStatement ps = db.getPreparedStmt(conn, sql);
            ps.setString(1, language);
            ps.setString(2, codCom);
            ps.setString(3, codCom);
            log.debug("checkOneri query:" + sql);
            rs = db.select(conn, ps, sql);

            dataForm.setOneriAnticipati(new TreeSet(new OneriBeanComparator()));
            dataForm.setOneriPosticipati(new TreeSet(new OneriBeanComparator()));
            // tiro fuori le radici degli oneri
            while (rs.next()) {
                if(isOnereValido(dataForm, rs.getString("cod_oneri"))){
                    OneriBean bean = new OneriBean();
                    bean.setCodice(rs.getString("cod_oneri"));
                    bean.setDescrizione(rs.getString("des_oneri"));
    
                    bean.setCodDestinatario(rs.getString("cod_dest"));
                    bean.setDesDestinatario(rs.getString("nome_dest"));
                    bean.setNota(rs.getString("note"));
                    // Se imp_acc = null e cod_padre = null 
                    // � un onere posticipato
                    if((null == rs.getString("imp_acc") || "".equalsIgnoreCase(rs.getString("imp_acc"))) &&
                            (null == rs.getString("cod_padre") || "".equalsIgnoreCase(rs.getString("cod_padre")))){
                        bean.setImporto(-1);
                        dataForm.getOneriPosticipati().add(bean);
                    }
    //              .. se valorizzato imp_acc allora � un onere anticipato
                    // .. a calcolo Fisso
                    else if (!(null == rs.getString("imp_acc") || "".equalsIgnoreCase(rs.getString("imp_acc")))) {
                        bean.setImporto(rs.getDouble("imp_acc"));
                        bean.setDescrizioneAntenato(rs.getString("des_oneri"));
                        dataForm.getOneriAnticipati().add(bean);
    
                    } else {
    
                        // altrimenti il calcolo dell'onere deve essere calcolato da
                        // formule
    
                        // DefaultMutableTreeNode childNode = new
                        // DefaultMutableTreeNode(bean);
                        // ArrayList childNode = new ArrayList();
                        // risalgo la gerarchia degli oneri con una funzione
                        // ricorsiva
                        // ed aggiungo al nodo quanto costruito
                        bean.setCodiceAntenato(bean.getCodice());
                        bean.setDescrizioneAntenato(bean.getDescrizione());
    
                        if (!(rs.getString("cod_padre") == null || rs.getString("cod_padre").equals(""))) {
    
                            addAChild(conn, bean, rs.getInt("cod_padre"), bean.getCodiceAntenato(), bean.getDescrizioneAntenato(), dataForm);
                        }
                        answer.add(bean);
                    }
                }
                log.debug(answer.toString());
            }
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
    }*/
    
    /*
     * Nuova versione come da query di Wego (10/10/2007)
     */
    public List getOneriAlbero(ProcessData dataForm) throws SQLException {

        // DefaultMutableTreeNode answer = new DefaultMutableTreeNode(new
        // OneriBean());
        ArrayList answer = new ArrayList();

        ResultSet rs = null;
        Connection conn = null;
        String inClause = "'";
        Iterator it = dataForm.getInterventiSelezionati().iterator();

        while (it.hasNext()) {

            InterventoBean bean = (InterventoBean) it.next();

            inClause += bean.getCodice();
            inClause += "','";
        }
        if (null == inClause || inClause.equals("'")) {
            // return false;
        }

        inClause = inClause.substring(0, inClause.lastIndexOf(",'"));
        try {
            conn = db.open();

            String sql = "";
            sql += "select distinct d.*, f.des_oneri, f.note, g.nome_file, h.cod_dest, nome_dest ";
            sql += "from interventi a  ";
            sql += "inner join  procedimenti b on  a.cod_proc=b.cod_proc ";
            sql += "inner join  oneri_procedimenti c on  b.cod_proc=c.cod_proc ";
            sql += "inner join  oneri d on  c.cod_oneri=d.cod_oneri ";
            sql += "inner join  oneri_comuni e on  d.cod_oneri = e.cod_oneri ";
            sql += "inner join  oneri_testi f on d.cod_oneri = f.cod_oneri and f.cod_lang=? ";
            sql += "inner join  relazioni_enti h on h.cod_com = ? and h.cod_cud = d.cod_cud ";
            sql += "inner join  destinatari i on h.cod_dest = i.cod_dest ";
            sql += "left join oneri_documenti g on d.cod_doc_onere = g.cod_doc_onere ";
            sql += "where a.cod_int in ("+inClause+") and  a.flg_obb <> 'N' and  e.cod_com = ? and  e.flg='S' ";
            sql += "union ";
            sql += "select distinct d.*, f.des_oneri, f.note, g.nome_file, h.cod_dest, nome_dest ";
            sql += "from interventi a  ";
            sql += "inner join  procedimenti b on  a.cod_proc=b.cod_proc ";
            sql += "inner join  oneri_procedimenti c on  b.cod_proc=c.cod_proc ";
            sql += "inner join  oneri d on  c.cod_oneri=d.cod_oneri ";
            sql += "inner join  oneri_testi f on d.cod_oneri = f.cod_oneri and f.cod_lang=? ";
            sql += "inner join  relazioni_enti h on h.cod_com = ? and h.cod_cud = d.cod_cud ";
            sql += "inner join  destinatari i on h.cod_dest = i.cod_dest ";
            sql += "left join  oneri_comuni e on  d.cod_oneri = e.cod_oneri ";
            sql += "left join oneri_documenti g on d.cod_doc_onere = g.cod_doc_onere ";
            sql += "where a.cod_int in ("+inClause+") and  a.flg_obb <> 'N' and  e.cod_com is null ";
            sql += "union ";
            sql += "select distinct d.*, f.des_oneri, f.note, g.nome_file, h.cod_dest, nome_dest ";
            sql += "from interventi a  ";
            sql += "inner join  procedimenti b on  a.cod_proc=b.cod_proc ";
            sql += "inner join oneri_procedimenti c on  b.cod_proc=c.cod_proc ";
            sql += "inner join oneri d on  c.cod_oneri=d.cod_oneri ";
            sql += "inner join  oneri_comuni e on  d.cod_oneri = e.cod_oneri ";
            sql += "inner join  oneri_testi f on d.cod_oneri = f.cod_oneri and f.cod_lang=? ";
            sql += "inner join  relazioni_enti h on h.cod_com = ? and h.cod_cud = d.cod_cud ";
            sql += "inner join  destinatari i on h.cod_dest = i.cod_dest ";
            sql += "left join oneri_documenti g on d.cod_doc_onere = g.cod_doc_onere  ";
            sql += "where a.cod_int in ("+inClause+") and  a.flg_obb <> 'N' and  e.cod_com <> ? and e.flg='N' ";
            sql += "and d.cod_oneri not in ( select distinct d.cod_oneri from ";
            sql += "interventi a inner join procedimenti b on a.cod_proc=b.cod_proc ";
            sql += "inner join oneri_procedimenti c on b.cod_proc=c.cod_proc ";
            sql += "inner join oneri d on c.cod_oneri=d.cod_oneri ";
            sql += "inner join oneri_comuni e on d.cod_oneri = e.cod_oneri ";
            sql += "where ";
            sql += "e.cod_com = ? and a.cod_int in ("+inClause+") and a.flg_obb <> 'N' and e.flg = 'N') ";
            
            PreparedStatement ps = db.getPreparedStmt(conn, sql);
            ps.setString(1, language);
            ps.setString(2, dataForm.getComune().getCodEnte());
            ps.setString(3, dataForm.getComune().getCodEnte());
            ps.setString(4, language);
            ps.setString(5, dataForm.getComune().getCodEnte());
            ps.setString(6, language);
            ps.setString(7, dataForm.getComune().getCodEnte());
            ps.setString(8, dataForm.getComune().getCodEnte());
            ps.setString(9, dataForm.getComune().getCodEnte());
            
            log.debug("checkOneri query:" + sql);
            rs = db.select(conn, ps, sql);

            dataForm.setOneriAnticipati(new TreeSet(new OneriBeanComparator()));
            dataForm.setOneriPosticipati(new TreeSet(new OneriBeanComparator()));
            // tiro fuori le radici degli oneri
            while (rs.next()) {
//                if(isOnereValido(dataForm, rs.getString("cod_oneri"))){
                    OneriBean bean = new OneriBean();
                    bean.setCodice(rs.getString("cod_oneri"));
                    bean.setDescrizione(rs.getString("des_oneri"));
    
                    bean.setCodDestinatario(rs.getString("cod_dest"));
                    bean.setDesDestinatario(rs.getString("nome_dest"));
                    bean.setNota(rs.getString("note"));
                    // Se imp_acc = null e cod_padre = null 
                    // � un onere posticipato
                    if((null == rs.getString("imp_acc") || "".equalsIgnoreCase(rs.getString("imp_acc"))) &&
                            (null == rs.getString("cod_padre") || "".equalsIgnoreCase(rs.getString("cod_padre")))){
                        bean.setImporto(-1);
                        dataForm.getOneriPosticipati().add(bean);
                    }
    //              .. se valorizzato imp_acc allora � un onere anticipato
                    // .. a calcolo Fisso
                    else if (!(null == rs.getString("imp_acc") || "".equalsIgnoreCase(rs.getString("imp_acc")))) {
                        bean.setImporto(rs.getDouble("imp_acc"));
                        bean.setDescrizioneAntenato(rs.getString("des_oneri"));
                        dataForm.getOneriAnticipati().add(bean);
    
                    } else {
    
                        // altrimenti il calcolo dell'onere deve essere calcolato da
                        // formule
    
                        // DefaultMutableTreeNode childNode = new
                        // DefaultMutableTreeNode(bean);
                        // ArrayList childNode = new ArrayList();
                        // risalgo la gerarchia degli oneri con una funzione
                        // ricorsiva
                        // ed aggiungo al nodo quanto costruito
                        bean.setCodiceAntenato(bean.getCodice());
                        bean.setDescrizioneAntenato(bean.getDescrizione());
    
                        if (!(rs.getString("cod_padre") == null || rs.getString("cod_padre").equals(""))) {
    
                            addAChild(conn, bean, rs.getInt("cod_padre"), bean.getCodiceAntenato(), bean.getDescrizioneAntenato(), dataForm);
                        }
                        answer.add(bean);
//                    }
                }
                log.debug(answer.toString());
            }
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
    
    public boolean isOnereValido(ProcessData dataForm, String cod_onere) throws SQLException, Exception {
        ResultSet rs = null;
        Connection conn = null;
        boolean retVal = false;
        try {
            conn = db.open();
            String sql = "";
            sql += "select cod_oneri, cod_com, flg from oneri_comuni ";
            sql += "where cod_oneri = ? ";

            PreparedStatement ps = db.getPreparedStmt(conn, sql);
            ps.setString(1, cod_onere);
            rs = db.select(conn, ps, sql);
            boolean almenoUno = false;
            while (rs.next()) {
                almenoUno = true;
                String comune = rs.getString("cod_com");
                String flg = rs.getString("flg");
                if(comune.equalsIgnoreCase(dataForm.getComune().getCodEnte()) && (flg.equalsIgnoreCase("Y") || flg.equalsIgnoreCase("S"))){
                    retVal = true;
                }
            }
            if(!almenoUno){
                retVal = true;
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
        return retVal;
    }

    /**
     * Metodo ricorsivo usato da getOneriAlbero() per ricostruire l'albero degli oneri a tariffario
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    private void addAChild(Connection conn, OneriBean parent, int cod_padre, String codiceAntenato, String descrizioneAntenato, ProcessData dataForm) throws SQLException {
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
            pstmt.setString(2, getLanguage());
            rs1 = db.select(conn, pstmt, sql);
            // .. inserisco un nodo e risalgo ricorsivamente la gerarchia
            ArrayList child = new ArrayList();
            while (rs1.next()) {
                if (rs1.getInt("cod_padre") != rs1.getInt("cod_figlio")) {
                    if(hmCampiGiaCensiti.get(""+rs1.getInt("cod_figlio")) == null){
                        hmCampiGiaCensiti.put(""+rs1.getInt("cod_figlio"), "Y");
                        OneriBean bean = new OneriBean();
    
                        bean.setCodice(rs1.getString("cod_figlio"));
    
                        bean.setDescrizione(rs1.getString("des_gerarchia"));
                        bean.setCodiceAntenato(codiceAntenato);
                        bean.setDescrizioneAntenato(descrizioneAntenato);

                        bean.setOneriFormula(rs1.getString("cod_onere_formula"));
                        bean.setCampoFormula(rs1.getString("cod_onere_campo"));

                        log.debug(bean.toString());
                        addAChild(conn, bean, rs1.getInt("cod_figlio"), codiceAntenato, descrizioneAntenato, dataForm);
                        child.add(bean);
                    }
                    else{
                        // Aggiunta per gestire formule con pi� campi formula
                        Iterator it = child.iterator();
                        while(it.hasNext()){
                            OneriBean bean = (OneriBean)it.next();
                            if(bean.getCodice().equalsIgnoreCase(""+rs1.getInt("cod_figlio"))){
                                OneriBean beanConCampoFormulaUguale = new OneriBean();
                                
                                beanConCampoFormulaUguale.setCodice(rs1.getString("cod_figlio"));
            
                                beanConCampoFormulaUguale.setDescrizione(rs1.getString("des_gerarchia"));
                                beanConCampoFormulaUguale.setCodiceAntenato(codiceAntenato);
                                beanConCampoFormulaUguale.setDescrizioneAntenato(descrizioneAntenato);

                                beanConCampoFormulaUguale.setOneriFormula(rs1.getString("cod_onere_formula"));
                                beanConCampoFormulaUguale.setCampoFormula(rs1.getString("cod_onere_campo"));
                                
                                bean.getAltriOneri().add(beanConCampoFormulaUguale);
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
     * Restituisce una lista di oneriBean rappresentativa dell'albero degli oneri a tariffario
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    public List completeOneri(ArrayList list, String[] oneriSelezionati, ProcessData dataForm) throws SQLException {
        ArrayList answer = new ArrayList();

        Iterator it = list.iterator();
        String oneriLista = "";
        for (int i = 0; i < oneriSelezionati.length; i++) {

            oneriLista += oneriSelezionati[i] + ",";
        }

        while (it.hasNext()) {
            OneriBean bean = (OneriBean) it.next();

            recurseBean(bean, oneriLista, answer, dataForm);

            // answer.add(bean);
        }

        return answer;
    }

    /**
     * Metodo ricorsivo utilizzato da completeOneri 
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    private void recurseBean(OneriBean bean, String listaOneriSelezionati, ArrayList result, ProcessData dataForm) throws SQLException {
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

                    DefinizioneCampoFormula definizione = trovaDefinizioneCampoFormula(dBean.getCampoFormula(), dBean.getOneriFormula());
                    dBean.setDefinizione(definizione);
                    
                    if(dBean.getAltriOneri().size()>0){
                        Iterator itFigli = dBean.getAltriOneri().iterator();
                        while(itFigli.hasNext()){
                            OneriBean dBeanFiglio = (OneriBean)itFigli.next();
                            definizione = trovaDefinizioneCampoFormula(dBeanFiglio.getCampoFormula(), dBeanFiglio.getOneriFormula());
                            dBeanFiglio.setDefinizione(definizione);
                        }
                    }
                    dataForm.getOneriAnticipati().add(dBean);
                    result.add(dBean);
                } else {
                    recurseBean(dBean, listaOneriSelezionati, result, dataForm);
                }
            }
        } else {
            String confronto1 = bean.getCampoFormula() + ("|")
                                + (bean.getOneriFormula()) + ("|")
                                + (bean.getCodice());
            if (listaOneriSelezionati.indexOf(confronto1) != -1) {
                // ho trovato un onere selezionato
                DefinizioneCampoFormula definizione = trovaDefinizioneCampoFormula(bean.getCampoFormula(), bean.getOneriFormula());
                bean.setDefinizione(definizione);

                if(bean.getAltriOneri().size()>0){
                    Iterator itFigli = bean.getAltriOneri().iterator();
                    while(itFigli.hasNext()){
                        OneriBean dBeanFiglio = (OneriBean)itFigli.next();
                        definizione = trovaDefinizioneCampoFormula(dBeanFiglio.getCampoFormula(), dBeanFiglio.getOneriFormula());
                        dBeanFiglio.setDefinizione(definizione);
                    }
                }

                dataForm.getOneriAnticipati().add(bean);
                result.add(bean);
            } else {
                // .. recurseBean(bean,listaOneriSelezionati);
            }

        }

    }

    /**
     * Restituisce un DefinizioneCampoFormula contenente la formula per l'interpretazione degli oneri a tariffario
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    private DefinizioneCampoFormula trovaDefinizioneCampoFormula(String cod_onere_campo, String cod_onere_formula) throws SQLException {
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
            sql += "                          oneri_formula.formula,oneri_formula.cod_onere_formula ";
            sql += "         FROM oneri_campi left outer join     ";
            sql += "               oneri_campi_testi on     ";
            sql += "               oneri_campi_testi.cod_lang='" + language
                   + "' and    ";
            sql += "               oneri_campi_testi.cod_onere_campo=oneri_campi.cod_onere_campo    ";
            sql += "              left outer join     ";
            sql += "             oneri_campi_select_testi on    ";
            sql += "             oneri_campi_select_testi.cod_lang='" + language
                   + "' and    ";
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
            rs = db.select(conn, pstmt, sql);
            String valoreDefault = "";
            while (rs.next()) {
                answer.setLabel(rs.getString("testo_campo"));
                answer.setTipo(rs.getString("tp_campo"));
                answer.setParteIntera(rs.getInt("lng_campo"));
                answer.setParteDecimale(rs.getInt("lng_dec"));
                if (!(null == rs.getString("val_select") || rs.getString("val_select").equals(""))) {
                    valoreDefault += rs.getString("val_select") + ",";
                }
                answer.setFormula(rs.getString("formula"));
            }
            if (!valoreDefault.equals("")) {
                valoreDefault = valoreDefault.substring(0, valoreDefault.lastIndexOf(","));
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

                rs = db.select(conn, pstmt, sql);
                if (rs.next()) {

                    // String formula = "$1900001$ a < 50: \n" +
                    // " x=0 \n" +
                    // "else: \n" +
                    // " x=6*1.5*41 ";
                    String formula = rs.getString("formula");
                    // devo trovare il testo da sostituire prima di fare
                    // interpretare la formula
                    // i.e. $190001$ deve diventare il valore del campo come
                    // riempito dall'utente
                    if (!(null == bean.getCampoFormula() || bean.getCampoFormula().equals(""))) {
                        String campo = "\\$" + bean.getCampoFormula() + "\\$";
                        Pattern p = Pattern.compile(campo, Pattern.CASE_INSENSITIVE);
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
                            // .. � una stringa e aggiungo le ""
                            valoreFormula = "\"" + valoreFormula + "\"";
                        }

                        formula = m.replaceAll(valoreFormula);
                    }
                    if(bean.getAltriOneri().size()>0){
                        // Significa che abbiamo a che fare con oneri formula con pi� di un campo formula
                        Iterator itFigli = bean.getAltriOneri().iterator();
                        while(itFigli.hasNext()){
                            OneriBean beanFiglio = (OneriBean)itFigli.next();
                        
                            if (!(null == beanFiglio.getCampoFormula() || beanFiglio.getCampoFormula().equals(""))) {
                                String campo = "\\$" + beanFiglio.getCampoFormula() + "\\$";
                                Pattern p = Pattern.compile(campo, Pattern.CASE_INSENSITIVE);
                                Matcher m = p.matcher(formula);
                                String valoreFormula = beanFiglio.getValoreFormula();
                                if (null == valoreFormula || valoreFormula.equals("")) {
                                    log.error("Attenzione non � stato trovato un valore per la formula con campo:"
                                              + campo);
                                    valoreFormula = "0";
                                }

                                try {
                                    Double.parseDouble(valoreFormula);
                                } catch (Exception e) {
                                    // .. � una stringa e aggiungo le ""
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

    /**
     * Restituisce il titolo di una dichiarazione dinamica
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    public String getTitoloHref(String href) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;

        ResultSet rs = null;

        try {

            conn = db.open();
            String sql = "select tit_href, piede_href from href inner join href_testi on href.href=href_testi.href ";
            sql += "where href_testi.href= ? and cod_lang=  ? order by tit_href";
            pstmt = db.getPreparedStmt(conn, sql);
            pstmt.setString(1, href);
            pstmt.setString(2, language);
            rs = db.select(conn, pstmt, sql);
            if (rs.next()) {
                return rs.getString("tit_href");
            } else
                return null;

        } catch (SQLException e) {

            log.error("getTitoloHref", e);

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

    /**
     * Restituisce un set di allegati facoltativi e salva nel ProcessData gli allegati obbligatori individuati
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    public Set getAllegati(ProcessData dataForm) throws SQLException {
        Set set = new TreeSet(new AllegatiBeanComparator());
        ResultSet rs = null;
        ResultSet rsTestoCond = null;
        Connection conn = null;
        TreeSet interventiSet = new TreeSet();
        Set interventi = dataForm.getInterventiSelezionati();
        if (interventi != null && interventi.size() == 0) {
            return set;
        }
        Iterator it = interventi.iterator();
        while (it.hasNext()) {
            BaseBean bean = (BaseBean) it.next();
            interventiSet.add(bean.getCodice());
        }

        String inClause = getInClause(interventiSet);

        try {
            conn = db.open();

            StringBuffer sql = new StringBuffer("");
            sql.append("select allegati.cod_doc, cod_int, flg_autocert, copie, cod_cond ");
            sql.append(", documenti_documenti.nome_file, flg_dic, href, tit_doc, des_doc ");
            sql.append(" from allegati inner join documenti on allegati.cod_doc=documenti.cod_doc inner ");
            sql.append(" join documenti_testi on documenti.cod_doc=documenti_testi.cod_doc ");
            sql.append(" left join documenti_documenti on documenti_documenti.cod_doc = documenti.cod_doc ");
            sql.append(" where cod_int in (" + inClause
                       + ") and documenti_testi.cod_lang=? "); 
            // TODO clausola non standard SQL (non portabile su Oracle, p.es)
            if(isMySqlDb){
                sql.append(" group by cod_doc");
            }
            
            dataForm.setAllegatiSelezionati(new TreeSet(new AllegatiBeanComparatorCodice()));
            PreparedStatement ps = db.getPreparedStmt(conn, sql.toString());
            ps.setString(1, language);
            rs = db.select(conn, ps, sql.toString());

            HashMap treeTmp = new HashMap();
            while (rs.next()) {
                if(isMySqlDb || treeTmp.get(rs.getString("cod_doc")) == null){
                    treeTmp.put(rs.getString("cod_doc"), "Y");
                
                    String cond = rs.getString("cod_cond");
                    // l'allegato � facoltativo, recupero il testo condizione
                    if (cond != null) {
                        sql = new StringBuffer();
                        sql.append("select testo_cond from testo_condizioni where cod_cond=? and cod_lang=?");
                        ps = db.getPreparedStmt(conn, sql.toString());
    
                        ps.setString(1, cond);
                        ps.setString(2, language);
                        rsTestoCond = db.select(conn, ps, sql.toString());
                        if (rsTestoCond.next()) {
                            AllegatiBean facoltativo = new AllegatiBean();
                            facoltativo.setFlagDic(rs.getString("flg_dic"));
                            facoltativo.setHref(rs.getString("href"));
                            facoltativo.setTitolo(rs.getString("tit_doc"));
                            facoltativo.setNomeFile(rs.getString("nome_file"));
                            facoltativo.setCodice(rs.getString("cod_doc"));
                            facoltativo.setCodiceIntervento(rs.getString("cod_int"));
                            facoltativo.setCopie(rs.getString("copie"));
                            facoltativo.setCodiceCondizione(cond);
                            facoltativo.setTestoCondizione(rsTestoCond.getString("testo_cond"));
                            facoltativo.setFlagAutocertificazione(rs.getString("flg_autocert"));
                            facoltativo.setDescrizione(rs.getString("des_doc"));
                            String st = facoltativo.getCodice() + "|"
                                        + facoltativo.getCodiceIntervento() + "|"
                                        + facoltativo.getCodiceCondizione();
                            facoltativo.setChecked(isChecked(st, dataForm.getDatiTemporanei().getOpsVec(), true));
                            set.add(facoltativo);
                        }
                    } else // l'allegato � obbligatorio
                    {
                        AllegatiBean obbligatorio = new AllegatiBean();
                        obbligatorio.setFlagDic(rs.getString("flg_dic"));
                        obbligatorio.setHref(rs.getString("href"));
                        obbligatorio.setTitolo(rs.getString("tit_doc"));
                        obbligatorio.setNomeFile(rs.getString("nome_file"));
                        obbligatorio.setCodice(rs.getString("cod_doc"));
                        obbligatorio.setCodiceIntervento(rs.getString("cod_int"));
                        obbligatorio.setCopie(rs.getString("copie"));
                        // obbligatorio.setCodiceCondizione(cond);
                        obbligatorio.setFlagAutocertificazione(rs.getString("flg_autocert"));
                        obbligatorio.setDescrizione(rs.getString("des_doc"));
                        dataForm.getAllegatiSelezionati().add(obbligatorio);    
                    }
                }
            }

            return set;
        } catch (SQLException e) {
            log.error(e);
            e.printStackTrace();
            throw e;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Restituisce, in base agli interventi individuati, una lista di ProcedimentoBean
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    public List getProcedimenti(ProcessData dataForm) throws SQLException {
        Set interventi = dataForm.getInterventiSelezionati();
        log.debug(interventi);
        ArrayList list = new ArrayList();
        // restituisce una lista vuota ma non null
        if (interventi != null && interventi.size() == 0) {
            return list;
        }
        ResultSet rsProcedimenti = null;
        ResultSet rsDocumenti = null;
        ResultSet rsNormative = null;
        Connection conn = null;

        TreeSet interventiSet = new TreeSet();
        Iterator it = interventi.iterator();
        while (it.hasNext()) {
            BaseBean bean = (BaseBean) it.next();
            interventiSet.add(bean.getCodice());
        }

        String inClauseInterventi = getInClause(interventiSet);
        try {
            conn = db.open();

            // Query prima che vvfc modificasse la tabella procedimenti
            // String sql = "";
            // sql += "select procedimenti.cod_proc,
            // procedimenti_testi.tit_proc, ";
            // sql += " procedimenti.ter_eva ";
            // sql += " , classi_enti_testi.des_classe_ente, ";
            // sql += " flg_tipo_proc from procedimenti inner join ";
            // sql += " procedimenti_testi on ";
            // sql += " procedimenti.cod_proc=procedimenti_testi.cod_proc ";
            // sql += " and procedimenti_testi.cod_lang=? ";
            // sql += "right join classi_enti on 
            // procedimenti.cod_classe_ente=classi_enti.cod_classe_ente ";
            // // modificato il 14/02/2006 per modifica wego alla tabella
            // procedimenti
            // //sql += "inner join classi_enti on
            // procedimenti.cod_classe_ente=classi_enti.cod_classe_ente ";
            // sql += "inner join classi_enti_testi on ";
            // sql +=
            // "classi_enti.cod_classe_ente=classi_enti_testi.cod_classe_ente
            // and ";
            // sql += "classi_enti_testi.cod_lang=? ";
            // sql += "inner join interventi on ";
            // sql += "procedimenti.cod_proc=interventi.cod_proc ";
            // sql += "inner join interventi_testi on ";
            // sql += "interventi.cod_int=interventi_testi.cod_int ";
            // sql += "and interventi_testi.cod_lang=? ";
            // sql += "where interventi.cod_int in(" + inClauseInterventi;
            // sql += ") group by procedimenti.cod_proc,
            // procedimenti_testi.tit_proc, procedimenti.ter_eva ";
            // sql += ", classi_enti_testi.des_classe_ente, flg_tipo_proc order
            // by tit_proc ";

            String sql = "";
            sql += "select procedimenti.cod_proc, procedimenti_testi.tit_proc,  ";
            sql += " procedimenti.ter_eva, ";
            sql += " flg_tipo_proc, flg_bollo from procedimenti  inner join  ";
            sql += " procedimenti_testi on ";
            sql += " procedimenti.cod_proc=procedimenti_testi.cod_proc ";
            sql += " and procedimenti_testi.cod_lang=? ";
            sql += "inner join interventi on   ";
            sql += "procedimenti.cod_proc=interventi.cod_proc  ";
            sql += "inner join interventi_testi on ";
            sql += "interventi.cod_int=interventi_testi.cod_int ";
            sql += "and interventi_testi.cod_lang=? ";
            sql += "where interventi.cod_int in(" + inClauseInterventi;
            sql += ") group by procedimenti.cod_proc, procedimenti_testi.tit_proc, procedimenti.ter_eva ";
            sql += ", flg_tipo_proc, flg_bollo order by tit_proc ";

            PreparedStatement ps = db.getPreparedStmt(conn, sql);
            ps.setString(1, language);
            ps.setString(2, language);
            // ps.setString(3, language);
            rsProcedimenti = db.select(conn, ps, sql);

            while (rsProcedimenti.next()) {
                ProcedimentoBean bean = new ProcedimentoBean();
                ProcedimentoSempliceBean procedimento = new ProcedimentoSempliceBean();
                procedimento.setCodice(rsProcedimenti.getString("cod_proc"));
                procedimento.setNome(rsProcedimenti.getString("tit_proc"));
                procedimento.setTerminiEvasione(rsProcedimenti.getString("ter_eva"));
                procedimento.setEnte(""/* rsProcedimenti.getString("des_classe_ente") */);
                procedimento.setTitoloIntervento(""/* rsProcedimenti.getString("tit_int") */);

                // Recupero gli interventi per ciascun procedimento
                List arrInt = getInterventiPerProcedimento(rsProcedimenti.getString("cod_proc"), dataForm, inClauseInterventi);
                procedimento.setInterventi(arrInt);

                procedimento.setTipo(rsProcedimenti.getInt("flg_tipo_proc"));
                procedimento.setFlg_bollo(rsProcedimenti.getString("flg_bollo"));
                bean.setProcedimento(procedimento);

                list.add(bean);
            }

            return list;
        } catch (SQLException e) {
            log.error(e);
            throw e;
        } finally {
            try {
                rsProcedimenti.close();
                rsDocumenti.close();
                rsNormative.close();
            } catch (Exception e) {
            }
            try {
                conn.close();
            } catch (Exception e) {
            }
        }
    }

    /**
     * Restituisce, per procedimento, gli interventi che lo hanno attivato
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    public List getInterventiPerProcedimento(String procedimento, ProcessData process, String interventi) throws SQLException {
        log.debug(procedimento);
        Connection conn = null;
        ResultSet rsInterventi = null;
        ArrayList list = new ArrayList();
        try {
            conn = db.open();

            String sql = "";
            sql += "select interventi_testi.cod_int, interventi_testi.tit_int from interventi ";
            sql += "inner join interventi_testi on interventi.cod_int = interventi_testi.cod_int ";
            sql += "left outer join condizioni_di_attivazione on interventi.cod_int = condizioni_di_attivazione.cod_int ";
            sql += "and tip_aggregazione = ? ";
            sql += "where interventi.cod_proc = ? and cod_lang = ? and interventi.cod_int in ("
                   + interventi + ")";
            sql += "group by interventi_testi.cod_int, interventi_testi.tit_int ";
            sql += "order by  tit_int";

            PreparedStatement ps = db.getPreparedStmt(conn, sql);
            ps.setString(1, process.getComune().getTipAggregazione());
            ps.setString(2, procedimento);
            ps.setString(3, language);
            rsInterventi = db.select(conn, ps, sql);
            
            while (rsInterventi.next()) {
                InterventoBean bean = new InterventoBean();
                bean.setCodice(rsInterventi.getString("cod_int"));
                bean.setDescrizione(rsInterventi.getString("tit_int"));

                list.add(bean);
            }

            return list;
        } catch (SQLException e) {
            log.error(e);
            throw e;
        } finally {
            try {
                rsInterventi.close();
            } catch (Exception e) {
            }
            try {
                conn.close();
            } catch (Exception e) {
            }
        }
    }

    /**
     * Rstituisce il tpo di procedura da attivare in base ai procedimenti individuati
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    public int getTipoProcedura(List procedimenti) {
        Iterator iter = procedimenti.iterator();
        boolean dia = true;
        while (iter.hasNext()) {
            ProcedimentoBean element = (ProcedimentoBean) iter.next();
            ProcedimentoSempliceBean procedimento = element.getProcedimento();
            // il procedimento non � autocertficabile
            if (procedimento.getTipo() == ProcedimentoSempliceBean.PROCEDIMENTO_NONAUTOCERTIFICABILE) {
                return ProcedimentoSempliceBean.PROCEDIMENTO_NONAUTOCERTIFICABILE;
            } 
            else if (!(procedimento.getTipo() == ProcedimentoSempliceBean.PROCEDIMENTO_DIA_COMUNICAZIONE)) {
                dia = false;
            }
        }
        return dia ? ProcedimentoSempliceBean.PROCEDIMENTO_DIA_COMUNICAZIONE : ProcedimentoSempliceBean.PROCEDIMENTO_AUTOCERTIFICABILE;
    }

    /**
     * Restituisce la lista dei procedimenti con gli eventuali allegati
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    public List memorizzaAllegatiProcedimenti(List procedimenti, Set allegati, int tipoProcedura) throws SQLException {
        ArrayList list = new ArrayList();
        ResultSet rsDocumenti = null;
        ResultSet rsNormative = null;
        Connection conn = null;
        PreparedStatement ps = null;
        Iterator it = null;

        try {
            conn = db.open();

            String sqlDocumenti = "";
            sqlDocumenti = "select cod_doc, cod_cond from allegati inner join interventi on ";
            sqlDocumenti += "allegati.cod_int=interventi.cod_int where interventi.cod_proc =? ";
            /*
             * se la procedura � semplice l'elenco degli allegati non includer�
             * quelli il cui flg_autocert != 0 se la procedura �
             * autocertificabile include solo gli allegati il cui flg_autocert ==
             * 0
             */
            // Parlato con Vidmar in data 23/09/2005. Dice che non bisogna tener
            // conto
            // del flg_autocert
            // sqlDocumenti +="and flg_autocert ";
            // sqlDocumenti += (tipoProcedura == ProcessData.SEMPLICE) ? " != 0
            // " : " =0 ";
            if(isMySqlDb){
                sqlDocumenti += " group by cod_doc order by cod_proc, cod_doc";
            }

            Iterator procedimentiIterator = procedimenti.iterator();

            while (procedimentiIterator.hasNext()) {
                ProcedimentoBean bean = new ProcedimentoBean();

                ProcedimentoBean procedimento = (ProcedimentoBean) procedimentiIterator.next();
                ProcedimentoSempliceBean procedimentoSempliceBean = procedimento.getProcedimento();
                ps = db.getPreparedStmt(conn, sqlDocumenti);
                ps.setString(1, procedimentoSempliceBean.getCodice());
                rsDocumenti = db.select(conn, ps, sqlDocumenti);
                ArrayList docs = new ArrayList();
                HashMap treeTmp = new HashMap();
                while (rsDocumenti.next()) {
                    if(isMySqlDb || treeTmp.get(rsDocumenti.getString("cod_doc")) == null){
                        treeTmp.put(rsDocumenti.getString("cod_doc"), "Y");
                        it = allegati.iterator(); /* torno all'inizio della lista */
                        while (it.hasNext()) {
                            AllegatiBean allegato = (AllegatiBean) it.next();
                            if (allegato.getCodice().equalsIgnoreCase(rsDocumenti.getString("cod_doc"))) {
                                docs.add(allegato);
                                // if(allegato.getAllegatiConDescUguale() != null){
                                // Iterator iter =
                                // allegato.getAllegatiConDescUguale().iterator();
                                // while(iter.hasNext()){
                                // docs.add((AllegatiBean)iter.next());
                                // }
                                // }
                                break;
                            }
                        }
                    }
                }
                if (docs.size() > 0) {
                    bean.setAllegati(docs);
                }
                
                String listaInterventi = parseInterventi(procedimentoSempliceBean);
                
                String sqlNormative = "";
                sqlNormative += "SELECT procedimenti.cod_proc, normative.nome_rif, normative_testi.tit_rif, normative_documenti.nome_file, normative_documenti.cod_rif  ";
                sqlNormative += "   FROM  ";
                sqlNormative += "   procedimenti inner join interventi on   ";
                sqlNormative += "   procedimenti.cod_proc = interventi.cod_proc  ";
                sqlNormative += "   inner join norme_interventi on  ";
                sqlNormative += "   norme_interventi.cod_int=interventi.cod_int  ";
                sqlNormative += "   inner join normative on   ";
                sqlNormative += "   normative.cod_rif=norme_interventi.cod_rif  ";
                sqlNormative += "   inner join normative_testi on ";
                sqlNormative += "   normative.cod_rif=normative_testi.cod_rif ";
                sqlNormative += "   and normative_testi.cod_lang=? ";
                sqlNormative += "   left outer join normative_documenti on ";
                sqlNormative += "   normative.cod_rif=normative_documenti.cod_rif and ";
                sqlNormative += "   normative_documenti.cod_lang=? ";
                sqlNormative += "   where interventi.cod_int in ("+ listaInterventi +") ";
                if(isMySqlDb){
                    sqlNormative += "   group by cod_rif";
                }
                
                ps = db.getPreparedStmt(conn, sqlNormative);
                ps.setString(1, language);
                ps.setString(2, language);
                rsNormative = db.select(conn, ps, sqlNormative);

                treeTmp = new HashMap();
                
                ArrayList normative = new ArrayList();
                while (rsNormative.next()) {
                    if(isMySqlDb || treeTmp.get(rsNormative.getString("cod_rif")) == null){
                        treeTmp.put(rsNormative.getString("cod_rif"), "Y");
                        NormativaBean normativa = new NormativaBean();
                        normativa.setNomeFile(rsNormative.getString("nome_file"));
                        normativa.setNomeRiferimento(rsNormative.getString("nome_rif"));
                        normativa.setTitoloRiferimento(rsNormative.getString("tit_rif"));
                        normativa.setCodRif(rsNormative.getString("cod_rif"));
                        normative.add(normativa);
                    }
                }
                if (normative.size() > 0) {
                    bean.setNormative(normative);
                }
                bean.setProcedimento(procedimentoSempliceBean);
                list.add(bean);
            }

            return list;
        } catch (SQLException e) {
            log.error(e);
            throw e;
        } finally {
            try {
                rsDocumenti.close();
                rsNormative.close();
            } catch (Exception e) {
            }
            try {
                conn.close();
            } catch (Exception e) {
            }
        }
    }
    
    private String parseInterventi(ProcedimentoSempliceBean procedimentoSempliceBean){
        List listaInterventi = procedimentoSempliceBean.getInterventi();
        String inClause = "'";
        Iterator it = listaInterventi.iterator();

        while (it.hasNext()) {

            InterventoBean bean = (InterventoBean) it.next();

            inClause += bean.getCodice();
            inClause += "','";
        }
        if(inClause != null && !inClause.equalsIgnoreCase("") && inClause.lastIndexOf(",'") != -1)
            inClause = inClause.substring(0, inClause.lastIndexOf(",'"));
        return inClause;
    }

    /**
     * Restituisce la SezioneCompilabileBean descrittiva dei campi di una dichiarazione dinamica
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    public SezioneCompilabileBean getHrefDefinitions(String href, ProcessData dataForm, String titolo, String desc, String codIntervento) throws SQLException {
        return getHrefDefinitions(href, dataForm, titolo, desc, codIntervento, false);
    }

    public SezioneCompilabileBean getHrefDefinitions(String href, ProcessData dataForm, String titolo, String desc, String codIntervento, boolean isUtenteAnonimo) throws SQLException {

        SezioneCompilabileBean answer = new SezioneCompilabileBean();
        String sql = "";
        sql += "select href_testi.tit_href, href_testi.piede_href,href_campi.*, href_campi_testi.des_campo  ";
        if(isMySqlDb)
            //sql += ", concat(href_campi.href, href_campi.nome) nomecomposto, concat(href_campi.href, href_campi.campo_collegato) campo_collegato_composto ";
            sql += ", concat(href_campi.href, href_campi.nome) nomecomposto ";
        else
            //sql += ", href_campi.href||href_campi.nome nomecomposto, href_campi.href||href_campi.campo_collegato campo_collegato_composto ";
            sql += ", href_campi.href||href_campi.nome nomecomposto ";
        sql += "from href_campi left join  href_campi_testi on ";
        sql += "    href_campi_testi.contatore = href_campi.contatore and ";
        
        sql += "    href_campi_testi.href=href_campi.href   and ";
        sql += "    href_campi_testi.nome=href_campi.nome and ";
        sql += "    href_campi_testi.cod_lang=?  ";
        sql += " inner join href_testi on  ";
        sql += "    href_testi.href=href_campi.href ";
        sql += "    and href_testi.cod_lang=? ";
        sql += "where  ";
        sql += "href_campi.href=? order by riga , posizione ";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // Commentato per test da massimo
            /*
             * List sezioni = dataForm.getSezioniCompilabili(); Iterator it =
             * sezioni.iterator(); while(it.hasNext()){ SezioneCompilabileBean
             * bean = (SezioneCompilabileBean)it.next();
             * if(href.equalsIgnoreCase(bean.getHref())){
             * if(bean.isProcessed()){ //.. gi� fatto in precedenza il lavoro su
             * DB //.. e non lo rifaccio return bean; } answer = bean; break; } }
             */

            conn = db.open();

            pstmt = db.getPreparedStmt(conn, sql);

            pstmt.setString(1, language);
            pstmt.setString(2, language);
            pstmt.setString(3, href);

            rs = db.select(conn, pstmt, sql);
            int numCols = 0;
            int numRows = 0;
            String hrefTitle = "";
            String piedeHref = "";
            ArrayList listHrefCampiBean = new ArrayList();
            boolean complete = true;
            String tit_href = "";
            while (rs.next()) {

                // Modificato da Massimo Ferreti il 21/02/2006 per modificare il
                // titolo degli href
                // hrefTitle = rs.getString("tit_href");
                hrefTitle = titolo;
                numCols = (numCols >= rs.getInt("posizione")) ? numCols : rs.getInt("posizione");
                numRows = (numRows >= rs.getInt("riga")) ? numRows : rs.getInt("riga");
                HrefCampiBean campo = new HrefCampiBean();
                campo.setControllo(rs.getString("controllo"));
                if (rs.getString("controllo") != null)
                    complete = false;
                campo.setDescrizione(rs.getString("des_campo"));
                // Sostituito nome con nomecomposto per risolver bug dovuto a radio con nome uguale in href differenti
                campo.setNome(rs.getString("nomecomposto"));
                campo.setRiga(rs.getInt("riga"));
                campo.setPosizione(rs.getInt("posizione"));
                campo.setTipo(rs.getString("tipo"));
                campo.setContatore(rs.getString("contatore"));
                // Recupero gli eventuali valori della combo
                if (campo.getTipo().equalsIgnoreCase("L")) {
                    campo.setOpzioniCombo(getOpzioniCombo(href, campo.getNome()));
                }
                campo.setNumCampo(rs.getInt("tp_riga"));
                campo.setValore(rs.getString("valore"));
                /*
                 * Modifica init 17/04/2007 (1.3.1)
                 * Nel caso di utente anonimo non recupero i dati relativi alla precompilazione e ne inibisco l'utilizzo
                 */
                if(isUtenteAnonimo){
                    campo.setWeb_serv(null);
                    campo.setNome_xsd(null);
                    campo.setCampo_key(null);
                    campo.setCampo_dati(null);
                }
                else{
                    campo.setWeb_serv(rs.getString("web_serv"));
                    campo.setNome_xsd(rs.getString("nome_xsd"));
                    campo.setCampo_key(rs.getString("campo_key"));
                    campo.setCampo_dati(rs.getString("campo_dati"));    
                }
                campo.setTp_controllo(rs.getString("tp_controllo"));
                campo.setLunghezza(rs.getString("lunghezza")==null?30:Integer.parseInt(rs.getString("lunghezza")));
                campo.setDecimali(rs.getString("decimali")==null?0:Integer.parseInt(rs.getString("decimali")));
                campo.setEdit(rs.getString("edit")==null?"s":rs.getString("edit"));
                campo.setCampo_xml_mod(rs.getString("campo_xml_mod"));
                /*campo.setRaggruppamento_check(rs.getString("raggruppamento_check"));
                campo.setCampo_collegato(rs.getString("campo_collegato_composto"));
                campo.setVal_campo_collegato(rs.getString("val_campo_collegato"));*/
                
                try{
                    campo.setCampo_collegato(rs.getString("href")+rs.getString("campo_collegato"));
                    campo.setRaggruppamento_check(rs.getString("raggruppamento_check"));
                    campo.setVal_campo_collegato(rs.getString("val_campo_collegato"));
                }catch(SQLException ex){
                    campo.setCampo_collegato(null);
                    campo.setRaggruppamento_check(null);
                    campo.setVal_campo_collegato(null);
                }
                listHrefCampiBean.add(campo);
                piedeHref = rs.getString("piede_href");
                tit_href = rs.getString("tit_href");

                // .. devo recuperare i valori
                // Iterator it = dataForm.getSezioniCompilabili().iterator();
                // while(it.hasNext()){
                // SezioneCompilabileBean dataBean =
                // (SezioneCompilabileBean)it.next();
                // if (){
                //                  
                // }
                // }
            }
            answer.setTitolo(hrefTitle == null ? tit_href : hrefTitle);
            answer.setDescrizione(desc == null ? tit_href : desc);
            answer.setPiedeHref(piedeHref == null ? "" : piedeHref);
            answer.setRowCount(numRows);
            answer.setTdCount(numCols);
            answer.setProcessed(true);
            answer.setCampi(listHrefCampiBean);
            answer.setHref(href);
            answer.setComplete(complete);
            answer.setIntervento(codIntervento);

        } catch (SQLException e) {

            log.error("trovaDefinizioneCampoFormula:", e);

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
     * Restituisce gli oneri dovuti per un procedimento separati da |
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    public String oneriPerProcedimento(String procedimento) throws SQLException {
        ResultSet rs = null;
        String retVal = "";
        Connection conn = null;

        try {
            conn = db.open();

            String sql = "SELECT cod_oneri FROM oneri_procedimenti ";
            sql += "Where cod_proc = ?";

            PreparedStatement ps = db.getPreparedStmt(conn, sql);
            ps.setString(1, procedimento);

            log.debug("oneriPerProcedimento query:" + sql);
            rs = db.select(conn, ps, sql);

            while (rs.next()) {
                retVal += rs.getString("cod_oneri") + "|";
            }
            return retVal;
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
     * Metodo che ritorna tutti gli interventi per un procedimento ed il comune per il quale � valido
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    public List interventiPerProcedimento(String procedimento) throws SQLException {
        ResultSet rs = null;
        Connection conn = null;
        ArrayList interventiPerProc = new ArrayList();

        try {
            conn = db.open();

            String sql = "select interventi.cod_int, tit_int, des_ente from procedimenti ";
            sql += "inner join interventi on procedimenti.cod_proc = interventi.cod_proc ";
            sql += "inner join interventi_testi on interventi.cod_int = interventi_testi.cod_int ";
            sql += "inner join condizioni_di_attivazione on interventi.cod_int = condizioni_di_attivazione.cod_int ";
            sql += "inner join comuni_aggregazione on condizioni_di_attivazione.tip_aggregazione = comuni_aggregazione.tip_aggregazione ";
            sql += "inner join enti_comuni on comuni_aggregazione.cod_ente = enti_comuni.cod_ente ";
            sql += "inner join enti_comuni_testi on enti_comuni.cod_ente = enti_comuni_testi.cod_ente ";
            sql += "where procedimenti.cod_proc = ? and interventi_testi.cod_lang = ? ";
//          TODO clausola non standard SQL (non portabile su Oracle, p.es)
            if(isMySqlDb){
                sql += "group by tit_int, des_ente ";
            }
            sql += "order by des_ente ";

            PreparedStatement ps = db.getPreparedStmt(conn, sql);
            ps.setString(1, procedimento);
            ps.setString(2, language);

            log.debug("oneriPerProcedimento query:" + sql);
            rs = db.select(conn, ps, sql);

            HashMap treeTmp = new HashMap();
            while (rs.next()) {
                if(isMySqlDb || treeTmp.get(rs.getString("cod_int")) == null){
                    treeTmp.put(rs.getString("cod_int"), "Y");
                    InterventoBean intBean = new InterventoBean();
                    intBean.setCodice(rs.getString("cod_int"));
                    intBean.setDescrizione(rs.getString("tit_int"));
                    intBean.setComuneValidita(rs.getString("des_ente"));
                    interventiPerProc.add(intBean);
                }
            }
            return interventiPerProc;
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
     *  Restituisce una lista di BaseBean contenenti le descrizioni dei settori di attivit� e delle operazioni che hanno attivato un intervento
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    public List getSettoriOperazioni(String intervento) throws SQLException {
        ResultSet rs = null;
        Connection conn = null;
        ArrayList settoriOperazioni = new ArrayList();

        try {
            conn = db.open();

            String sql = "select des_sett, des_ope from interventi ";
            sql += "inner join interventi_testi on interventi_testi.cod_int = interventi.cod_int ";
            sql += "inner join condizioni_di_attivazione on condizioni_di_attivazione.cod_int = interventi.cod_int ";
            sql += "inner join settori_attivita on settori_attivita.cod_sett = condizioni_di_attivazione.cod_sett  and settori_attivita.cod_lang = ? ";
            sql += "inner join operazioni on operazioni.cod_ope = condizioni_di_attivazione.cod_ope and operazioni.cod_lang = ? ";
            sql += "where interventi.cod_int = ? ";
            sql += "group by des_sett, des_ope order by des_sett ";

            PreparedStatement ps = db.getPreparedStmt(conn, sql);
            ps.setString(1, language);
            ps.setString(2, language);
            ps.setString(3, intervento);

            log.debug("SettoriOperazioni query:" + sql);
            rs = db.select(conn, ps, sql);

            while (rs.next()) {
                BaseBean intBean = new BaseBean();
                intBean.setCodice(rs.getString("des_sett"));
                intBean.setDescrizione(rs.getString("des_ope"));
                settoriOperazioni.add(intBean);
            }
            return settoriOperazioni;
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
     * Restituisce una lista di eventi della vita creati per il servizio
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    public List getEventiVita() throws SQLException {
        ResultSet rs = null;
        Connection conn = null;
        ArrayList eventiVita = new ArrayList();

        try {
            conn = db.open();

            String sql = "select * from eventi_vita where cod_lang = ? order by des_eve_vita";

            PreparedStatement ps = db.getPreparedStmt(conn, sql);
            ps.setString(1, language);

            log.debug("EventiVita query:" + sql);
            rs = db.select(conn, ps, sql);

            while (rs.next()) {
                EventiVitaBean eventiVitaBean = new EventiVitaBean();
                eventiVitaBean.setCodiceEventoVita(rs.getInt("cod_eve_vita"));
                eventiVitaBean.setDescrizione(rs.getString("des_eve_vita"));
                eventiVita.add(eventiVitaBean);
            }
            if (eventiVita.size() == 0) {
                EventiVitaBean eventiVitaBean = new EventiVitaBean();
                eventiVitaBean.setDescrizione("Nessun evento della vita creato");
                eventiVita.add(eventiVitaBean);
            }
            return eventiVita;
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
     * Restituisce una lista di servizi (bookmark) creati per eventoVita
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    public List getServiziPerEventiVita(int eventoVita, String codComune, String serviceName) throws SQLException {
        ResultSet rs = null;
        Connection conn = null;
        ArrayList servizi = new ArrayList();

        try {
            conn = db.open();

            String sql = "select * from servizi inner join servizi_testi on servizi_testi.cod_servizio = servizi.cod_servizio and ";
            sql += "servizi_testi.cod_com = servizi.cod_com and servizi_testi.cod_eve_vita = servizi.cod_eve_vita ";
            sql += "and servizi_testi.cod_lang = ? ";
            sql += "where servizi.cod_eve_vita = ? and servizi.cod_com = ? order by nome_servizio";

            PreparedStatement ps = db.getPreparedStmt(conn, sql);
            ps.setString(1, language);
            ps.setInt(2, eventoVita);
            ps.setString(3, codComune);

            log.debug("Servizi query:" + sql);
            rs = db.select(conn, ps, sql);

            while (rs.next()) {
                ServiziBean serviziBean = new ServiziBean();
                serviziBean.setCodiceServizio(rs.getInt("cod_servizio"));
                serviziBean.setNome(rs.getString("nome_servizio"));
                serviziBean.setDescrizione(rs.getString("des_servizio"));
                serviziBean.setCodiceComune(rs.getString("cod_com"));
                serviziBean.setCodiceEventoVita(rs.getString("cod_eve_vita"));
                serviziBean.setBookmarkPointer(rs.getString("bookmark_pointer"));
                try {
                    PeopleServiceProfile serviceProfile = caricaServiceProfile(serviceName, ""
                                                                                            + serviziBean.getCodiceServizio());
                    if (serviceProfile != null) {
                        Service service = serviceProfile.getService();
                        boolean intermediari = service.getAuthorizationProfile().getAccessoIntermediari().getEnabled();
                        boolean utenteReg = service.getAuthorizationProfile().getAccessoUtentePeopleRegistrato().getEnabled();
                        boolean autenticazioneForte = service.getSecurityProfile().getStrongAuthentication();
                        boolean autenticazioneDebole = service.getSecurityProfile().getWeakAuthentication();
                        serviziBean.setCheckStatus(autenticazioneForte + "|"
                                                   + autenticazioneDebole + "|"
                                                   + intermediari + "|"
                                                   + utenteReg);
                    } else {
                        serviziBean.setCheckStatus("false|true|false|true");
                    }
                } catch (peopleException e) {
                    log.debug("PeopleServiceProfile: errore nel reperimento dello stato dei check");
                } catch (XmlException e) {
                    log.debug("PeopleServiceProfile: errore nel reperimento dello stato dei check");
                }
                // Non implementato
                serviziBean.setMaxCodServizi(0);
                servizi.add(serviziBean);
            }
            if (servizi.size() == 0) {
                ServiziBean servizioBean = new ServiziBean();
                servizioBean.setDescrizione("Nessun servizio associato");
                servizi.add(servizioBean);
            }
            return servizi;
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
     * Effettua l'insert di un nuovo servizio (bookmark)
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    public int insertServizio(String codComune, int codEventoVita, String bookmarkPointer, String nomeServizio, String descrizioneServizio) throws SQLException {
        ResultSet rs = null;
        Connection conn = null;
        if (codEventoVita == 0)
            return -1;
        try {
            conn = db.open();

            // Faccio la insert nella tabella servizi
            String sql = "insert into servizi (cod_servizio, cod_com, cod_eve_vita, bookmark_pointer) ";
            sql += "values (?, ?, ?, ?) ";

            PreparedStatement ps = db.getPreparedStmt(conn, sql);
            int codServizio = getMaxCodServizio() + 1;
            ps.setInt(1, codServizio);
            ps.setString(2, codComune);
            ps.setInt(3, codEventoVita);
            ps.setString(4, bookmarkPointer);

            log.debug("Insert servizi query:" + sql);
            db.insert(conn, ps, sql);

            // Faccio la insert nella tabella servizi_testi
            sql = "insert into servizi_testi (cod_servizio, cod_com, cod_eve_vita, cod_lang, nome_servizio, des_servizio) ";
            sql += "values (?, ?, ?, ?, ?, ?) ";

            ps = db.getPreparedStmt(conn, sql);
            ps.setInt(1, codServizio);
            ps.setString(2, codComune);
            ps.setInt(3, codEventoVita);
            ps.setString(4, language);
            ps.setString(5, nomeServizio);
            ps.setString(6, descrizioneServizio);

            log.debug("Insert servizi_testi query:" + sql);
            db.insert(conn, ps, sql);

            return codServizio;
        } catch (SQLException e) {
            log.error(e);
            return -1;
        } finally {
            try {
                db.close(conn);
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
     * Effettua l'eliminazione di un servizio (bookmark)
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    public boolean deleteServizio(int codServizio, String codComune, int codEventoVita) throws SQLException {
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = db.open();

            // Faccio la delete nella tabella servizi
            String sql = "delete from servizi where cod_servizio = ? and cod_com = ? and cod_eve_vita = ? ";

            PreparedStatement ps = db.getPreparedStmt(conn, sql);
            ps.setInt(1, codServizio);
            ps.setString(2, codComune);
            ps.setInt(3, codEventoVita);

            log.debug("Insert servizi query:" + sql);
            db.delete(conn, ps, sql);

            // Faccio la delete nella tabella servizi_testi
            sql = "delete from servizi_testi where cod_servizio = ? and cod_com = ? and cod_eve_vita = ? ";

            ps = db.getPreparedStmt(conn, sql);
            ps.setInt(1, codServizio);
            ps.setString(2, codComune);
            ps.setInt(3, codEventoVita);

            log.debug("Insert servizi_testi query:" + sql);
            db.delete(conn, ps, sql);
            db.commit(conn);

            return true;
        } catch (SQLException e) {
            log.error(e);
            return false;
        } finally {
            try {
                db.close(conn);
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
     * Effettua la modifica di un servizio (bookmark)
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     * @param updateSoloDescrizione TODO
     */
    public boolean updateServizio(int codServizio, String codComune, int codEventoVita, String bookmarkPointer, String descServizio, String nomeServizio, boolean updateSoloDescrizione) throws SQLException {
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = db.open();
            PreparedStatement ps = null;
            String sql = "";
            // Faccio la insert nella tabella servizi
            if(!updateSoloDescrizione){
                sql = "update servizi set bookmark_pointer = ? ";
                sql += "where cod_servizio = ? and cod_com = ? and cod_eve_vita = ? ";
    
                ps = db.getPreparedStmt(conn, sql);
                ps.setString(1, bookmarkPointer);
                ps.setInt(2, codServizio);
                ps.setString(3, codComune);
                ps.setInt(4, codEventoVita);
    
                log.debug("Update servizi query:" + sql);
                db.update(conn, ps, sql);
            }
                
            sql = "update servizi_testi set des_servizio = ?, nome_servizio = ? ";
            sql += "where cod_servizio = ? and cod_com = ? and cod_eve_vita = ? and cod_lang = ? ";

            ps = db.getPreparedStmt(conn, sql);
            ps.setString(1, descServizio);
            ps.setString(2, nomeServizio);
            ps.setInt(3, codServizio);
            ps.setString(4, codComune);
            ps.setInt(5, codEventoVita);
            ps.setString(6, "it");

            log.debug("Update servizi_testi query:" + sql);
            db.update(conn, ps, sql);

            return true;
        } catch (SQLException e) {
            log.error(e);
            return false;
        } finally {
            try {
                db.close(conn);
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
     * Restituisce l'id max dei servizi (bookmark) creati fino a quel momento
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    public int getMaxCodServizio() throws SQLException {
        ResultSet rs = null;
        Connection conn = null;
        int retVal = 0;

        try {
            conn = db.open();

            String sql = "select max(cod_servizio) as maxCod from servizi ";

            PreparedStatement ps = db.getPreparedStmt(conn, sql);

            log.debug("MaxServizi query:" + sql);
            rs = db.select(conn, ps, sql);

            if (rs.next()) {
                retVal = rs.getInt("maxCod");
            }
            return retVal;
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
     * Restituisce l'id max degli eventi della vita creati fino a quel momento
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    public int getMaxCodEventoVita() throws SQLException {
        ResultSet rs = null;
        Connection conn = null;
        int retVal = 0;

        try {
            conn = db.open();

            String sql = "select max(cod_eve_vita) as maxCod from eventi_vita ";

            PreparedStatement ps = db.getPreparedStmt(conn, sql);

            log.debug("MaxEventoVita query:" + sql);
            rs = db.select(conn, ps, sql);

            if (rs.next()) {
                retVal = rs.getInt("maxCod");
            }
            return retVal;
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
     * Effettua l'inserimento di un nuovo evento della vita
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    public boolean insertEventoVita(String desEventoVita) throws SQLException {
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = db.open();

            // Faccio la insert nella tabella servizi
            String sql = "insert into eventi_vita (cod_eve_vita, cod_lang, des_eve_vita) ";
            sql += "values (?, ?, ?) ";

            PreparedStatement ps = db.getPreparedStmt(conn, sql);
            int codEveVita = getMaxCodEventoVita() + 1;
            ps.setInt(1, codEveVita);
            ps.setString(2, language);
            ps.setString(3, desEventoVita);

            log.debug("Insert eventiVita query:" + sql);
            log.debug("des_evento_vita=" + desEventoVita);
            db.insert(conn, ps, sql);

            return true;
        } catch (SQLException e) {
            log.error(e);
            return false;
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
     * Effettua l'eliminazione di un evento della vita
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    public boolean deleteEventoVita(int codEventoVita) throws SQLException {
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = db.open();

            // Faccio la insert nella tabella servizi
            String sql = "delete from eventi_vita where cod_eve_vita = ? and cod_lang = ? ";

            PreparedStatement ps = db.getPreparedStmt(conn, sql);
            ps.setInt(1, codEventoVita);
            ps.setString(2, language);

            log.debug("deleteEventoVita query:" + sql);
            db.delete(conn, ps, sql);

            sql = "delete from servizi where cod_eve_vita = ? ";

            ps = db.getPreparedStmt(conn, sql);
            ps.setInt(1, codEventoVita);

            log.debug("deleteServizio di eventiVita query:" + sql);
            db.delete(conn, ps, sql);

            sql = "delete from servizi_testi where cod_eve_vita = ? ";

            ps = db.getPreparedStmt(conn, sql);
            ps.setInt(1, codEventoVita);

            log.debug("deleteServizioTesti di eventiVita query:" + sql);
            db.delete(conn, ps, sql);

            return true;
        } catch (SQLException e) {
            log.error(e);
            return false;
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
    public String getBookmarkXML(String id) {
        ResultSet rs = null;
        Connection conn = null;
        String xml = null;

        try {
            conn = db.open();

            String sql = "select bookmark_pointer from servizi where cod_servizio=?";

            PreparedStatement ps = db.getPreparedStmt(conn, sql);
            ps.setInt(1, Integer.parseInt(id));

            log.debug("getBookmarkXML query:" + sql);
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

    /**
     * Restituisce una lista di DocumentiBean relativi alle eventuali normative per codRif
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    public List getDocumentiNormative(String codRif) throws SQLException {
        ResultSet rs = null;
        Connection conn = null;
        ArrayList documenti = new ArrayList();

        try {
            conn = db.open();

            String sql = "select * from normative ";
            sql += "left join normative_documenti on normative.cod_rif = normative_documenti.cod_rif ";
            sql += "left join normative_testi on normative.cod_rif = normative_testi.cod_rif ";
            sql += "where normative_documenti.cod_rif = ? and normative_documenti.cod_lang = ? and normative_testi.cod_lang = ? ";

            PreparedStatement ps = db.getPreparedStmt(conn, sql);
            ps.setString(1, codRif);
            ps.setString(2, language);
            ps.setString(3, language);

            log.debug("DocumentiNormative query:" + sql);
            rs = db.select(conn, ps, sql);

            while (rs.next()) {
                DocumentiBean docBean = new DocumentiBean();
                docBean.setNomeFile(rs.getString("nome_file"));
                docBean.setTipoDocumento(rs.getString("tip_doc"));
                docBean.setDescrizione(rs.getString("tit_rif"));
                docBean.setNomeRiferimento(rs.getString("nome_rif"));
                docBean.setCodiceDoc(null);
                docBean.setCodiceRif(codRif);

                documenti.add(docBean);
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
     * Restituisce una lista di DocumentiBean relativi agli eventuali documenti per codRif
     * 
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    public List getDocumentiAllegati(String codDoc) throws SQLException {
        ResultSet rs = null;
        Connection conn = null;
        ArrayList documenti = new ArrayList();

        try {
            conn = db.open();

            String sql = "select documenti_documenti.nome_file, tip_doc, tit_doc from documenti ";
            sql += "left join documenti_documenti on documenti.cod_doc = documenti_documenti.cod_doc ";
            sql += "left join documenti_testi on documenti.cod_doc = documenti_testi.cod_doc ";
            sql += "where documenti.cod_doc = ? and documenti_testi.cod_lang = ? and documenti_documenti.cod_lang = ? ";

            PreparedStatement ps = db.getPreparedStmt(conn, sql);
            ps.setString(1, codDoc);
            ps.setString(2, language);
            ps.setString(3, language);
            
            log.debug("DocumentiNormative query:" + sql);
            rs = db.select(conn, ps, sql);

            while (rs.next()) {
                DocumentiBean docBean = new DocumentiBean();
                docBean.setNomeFile(rs.getString("nome_file"));
                docBean.setTipoDocumento(rs.getString("tip_doc"));
                docBean.setDescrizione(rs.getString("tit_doc"));
                docBean.setNomeRiferimento(rs.getString("nome_file"));
                docBean.setCodiceDoc(codDoc);
                docBean.setCodiceRif(null);
                documenti.add(docBean);
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
     * Restituisce se, fra le selezioni dell'utente, c'� il valore passato
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    private boolean isChecked(String value, String[] opsVec, boolean confrontaTutto) {
        if (opsVec != null) {
            for (int i = 0; i < opsVec.length; i++) {
                if (!confrontaTutto
                    && value.substring(0, value.indexOf("|") < 0 ? value.length() : value.indexOf("|")).equalsIgnoreCase(opsVec[i].substring(0, opsVec[i].indexOf("|") < 0 ? opsVec[i].length() : opsVec[i].indexOf("|"))))
                    return true;
                if (confrontaTutto && value.equalsIgnoreCase(opsVec[i]))
                    return true;
            }
        }
        return false;
    }

    /**
     * Restituisce i valori di una combo di un campo di una dichiarazione dinamica
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    public List getOpzioniCombo(String href, String nome) throws SQLException {
        ResultSet rs = null;
        Connection conn = null;
        ArrayList opzioniCombo = new ArrayList();

        try {
            conn = db.open();

            String sql = "select A.val_select, des_valore ";
            sql += "from href_campi_valori A ";
            sql += "inner join href_campi_valori_testi B on B.href = A.href and B.nome = A.nome and B.val_select = A.val_select and b.cod_lang = ? ";
            sql += "where A.href = ? and concat(A.href, A.nome) = ? order by A.val_select ";

            PreparedStatement ps = db.getPreparedStmt(conn, sql);
            ps.setString(1, language);
            ps.setString(2, href);
            ps.setString(3, nome);

            log.debug("getOpzioniCombo query:" + sql);
            rs = db.select(conn, ps, sql);

            while (rs.next()) {
                BaseBean bean = new BaseBean();
                bean.setCodice(rs.getString("val_select"));
                bean.setDescrizione(rs.getString("des_valore"));
                opzioniCombo.add(bean);
            }
            return opzioniCombo;
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
     * Effettua il salvataggio di un blob
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    public void saveBlob() {
        Connection conn = null;
        try {
            conn = db.open();

            PreparedStatement pstmt = db.getPreparedStmt(conn, "update normative_documenti set doc_blob = ?, nome_file = 'test.pdf' where cod_rif = '1234' and tip_doc = 'application/pdf' and cod_lang = 'it'");
            File imageFile = new File("c:/test.pdf");
            FileInputStream is = new FileInputStream(imageFile);
            
            long length = imageFile.length();
//          Create the byte array to hold the data
            byte[] bytes = new byte[(int)length];
        
            // Read in the bytes
            int offset = 0;
            int numRead = 0;
            while (offset < bytes.length
                   && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
                offset += numRead;
            }
            is.close();
            
            pstmt.setBinaryStream(1, is, (int) (imageFile.length()));
            pstmt.executeUpdate();

            // PreparedStatement pstmt = conn.prepareStatement("SELECT image
            // FROM testblob WHERE Name = ?");
            // pstmt.setString(1, args[0]);
            // RandomAccessFile raf = new RandomAccessFile(args[0], "rw");
            // ResultSet rs = pstmt.executeQuery();
            // if (rs.next()) {
            // Blob blob = rs.getBlob(1);
            // int length = (int) blob.length();
            // byte[] _blob = blob.getBytes(1, length);
            // raf.write(_blob);
            // }
            System.out.println("Completed...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Effewttua l'apertura di un blob
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    public void openBlob() {
        Connection conn = null;
        try {
            conn = db.open();

            PreparedStatement pstmt = db.getPreparedStmt(conn, "SELECT doc_blob FROM normative_documenti WHERE cod_rif = '1234' and tip_doc = 'application/pdf' and cod_lang = 'it'");
            // pstmt.setString(1, args[0]);
            RandomAccessFile raf = new RandomAccessFile("c:/test2.pdf", "rw");
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Blob blob = rs.getBlob("doc_blob");
                int length = (int) blob.length();
                byte[] _blob = blob.getBytes(1, length);
                raf.write(_blob);
            }
            System.out.println("Completed...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Restituisce il nome di un servizio (bookmark) identificato dall'id
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    public String getNomeServizio(String idBookmark) throws SQLException {
        ResultSet rs = null;
        Connection conn = null;
        String ret = null;

        try {
            conn = db.open();
            String sql = "select nome_servizio from servizi_testi where cod_lang=? and cod_servizio=?";
            PreparedStatement ps = db.getPreparedStmt(conn, sql);
            ps.setString(1, language);
            ps.setString(2, idBookmark);
            rs = db.select(conn, ps, sql);
            if (rs.next()) {
                return rs.getString(1);
            }
            return ret;
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
     * Restituisce la descrizione di un servizio (bookmark) identificato dall'id
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    public String getDescrizioneServizio(String idBookmark) throws SQLException {
        ResultSet rs = null;
        Connection conn = null;
        String ret = null;

        try {
            conn = db.open();
            String sql = "select des_servizio from servizi_testi where cod_lang=? and cod_servizio=?";
            PreparedStatement ps = db.getPreparedStmt(conn, sql);
            ps.setString(1, language);
            ps.setString(2, idBookmark);
            rs = db.select(conn, ps, sql);
            if (rs.next()) {
                return rs.getString(1);
            }
            return ret;
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
     * Restituisce il PeopleServiceProfile di un servizio (bookmark)
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    public PeopleServiceProfile caricaServiceProfile(String processName, String bookmarkId) throws XmlException, peopleException {
        ServiceProfileStore serviceProfileStore = ServiceProfileStoreManager.getInstance().get(processName, bookmarkId);
        if (serviceProfileStore != null) {
            String xmlProfile = serviceProfileStore.getProfile();
            PeopleServiceProfileDocument serviceProfileDocument;
            serviceProfileDocument = PeopleServiceProfileDocument.Factory.parse(xmlProfile);
            return serviceProfileDocument.getPeopleServiceProfile();
        }
        return null;
    }

    /**
     * Lancia l'inerprete python passsandogli come modulo il pythonModule e come parametro del modulo l'xml
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    public String PythonRunner(String pythonModule, String xml) throws PyException {
        // String scriptName = "c:/CalcolaSportello.py";
        String retVal = "";

        try {
            interp = new PythonInterpreter();
            interp.set("par1", new PyString(xml));
            // interp.execfile(scriptName);
            interp.exec(pythonModule);
            PyObject po = interp.get("xmlout");
            retVal = po.toString();
        } catch (PyException pe) {
            log.error(pe);
            throw pe;
        }
        return retVal;
    }

    /**
     * Restituisce l'oggetto output con lo scompattamento dei procedimenti per destinatario
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    public Output getOutputProcedimentiMultipli(List procedimenti, String codCom) throws SQLException, PyException {
        ResultSet rs = null;
        Connection conn = null;

        String inClause = "";
        Iterator it = procedimenti.iterator();
        while (it.hasNext()) {
            ProcedimentoBean bean = (ProcedimentoBean) it.next();

            ProcedimentoSempliceBean procedimento = bean.getProcedimento();
            inClause += "'" + procedimento.getCodice() + "'";
            inClause += ",";
        }

        if (inClause.length() > 0)
            inClause = inClause.substring(0, inClause.length() - 1);

        try {
            conn = db.open();

            String sql = "";
            if(isMySqlDb){
                sql = "select cod_proc, relazioni_enti.cod_dest, destinatari.cod_ente, enti_comuni_testi.des_ente, cud.cod_classe_ente, flg_com, cud.cod_cud, relazioni_enti.cod_sport, flg_pu, procedimenti.ter_eva, procedimenti.flg_tipo_proc, des_classe_ente, src_pyth, CURDATE()+0 dataodierna ";
                sql += "from procedimenti ";
                sql += "left join relazioni_enti on relazioni_enti.cod_com = ? and relazioni_enti.cod_cud = procedimenti.cod_cud ";
                sql += "left join destinatari on destinatari.cod_dest = relazioni_enti.cod_dest ";
                sql += "left join enti_comuni on enti_comuni.cod_ente = destinatari.cod_ente ";
                sql += "left join enti_comuni_testi on enti_comuni_testi.cod_ente = enti_comuni.cod_ente and enti_comuni_testi.cod_lang = ? ";
                sql += "left join cud on cud.cod_cud = relazioni_enti.cod_cud ";
                sql += "left join classi_enti on classi_enti.cod_classe_ente = cud.cod_classe_ente ";
                sql += "left join classi_enti_testi on classi_enti_testi.cod_classe_ente = classi_enti.cod_classe_ente and classi_enti_testi.cod_lang = ? ";
                sql += "left join sportelli on sportelli.cod_sport = relazioni_enti.cod_sport ";
                sql += "where cod_proc in (" + inClause + ")";
            }
            else{
                sql = "select cod_proc, relazioni_enti.cod_dest, destinatari.cod_ente, enti_comuni_testi.des_ente, cud.cod_classe_ente, flg_com, cud.cod_cud, relazioni_enti.cod_sport, flg_pu, procedimenti.ter_eva, procedimenti.flg_tipo_proc, des_classe_ente, src_pyth, to_char(sysdate, 'yyyymmdd') dataodierna ";
                sql += "from procedimenti ";
                sql += "left join relazioni_enti on relazioni_enti.cod_com = ? and relazioni_enti.cod_cud = procedimenti.cod_cud ";
                sql += "left join destinatari on destinatari.cod_dest = relazioni_enti.cod_dest ";
                sql += "left join enti_comuni on enti_comuni.cod_ente = destinatari.cod_ente ";
                sql += "left join enti_comuni_testi on enti_comuni_testi.cod_ente = enti_comuni.cod_ente and enti_comuni_testi.cod_lang = ? ";
                sql += "left join cud on cud.cod_cud = relazioni_enti.cod_cud ";
                sql += "left join classi_enti on classi_enti.cod_classe_ente = cud.cod_classe_ente ";
                sql += "left join classi_enti_testi on classi_enti_testi.cod_classe_ente = classi_enti.cod_classe_ente and classi_enti_testi.cod_lang = ? ";
                sql += "left join sportelli on sportelli.cod_sport = relazioni_enti.cod_sport ";
                sql += "where cod_proc in (" + inClause + ")";
                
            }
            String dataOdierna = "";
            PreparedStatement ps = db.getPreparedStmt(conn, sql);
            ps.setString(1, codCom);
            ps.setString(2, language);
            ps.setString(3, language);
            
            log.debug("getOutputProcedimentiMultipli query:" + sql);
            rs = db.select(conn, ps, sql);

            Input inputBean = new Input();

            inputBean.setComune(codCom);

            String pythonModule = "";

            while (rs.next()) {
                Procedimento procBean = new Procedimento();
                procBean.setCodiceClasseEnte(rs.getString("cod_classe_ente"));
                procBean.setCodiceCud(rs.getString("cod_cud"));
                procBean.setCodiceEnte(rs.getString("cod_ente"));
                procBean.setCodiceProcedimento(rs.getString("cod_proc"));
                procBean.setCodiceSportello(rs.getString("cod_sport"));
                procBean.setFlagComune(rs.getString("flg_com"));
                // Analisi wego non precisa
                procBean.setFlagProcedimento(rs.getString("flg_tipo_proc"));
                procBean.setFlagPu((rs.getString("flg_pu") != null && !rs.getString("flg_pu").equalsIgnoreCase("")) ? rs.getString("flg_pu") : "N");
                procBean.setTerminiEvasione(rs.getString("ter_eva"));
                if (dataOdierna.equalsIgnoreCase("")) {
                    dataOdierna = rs.getString("dataodierna");
                }
                it = procedimenti.iterator();
                while (it.hasNext()) {
                    ProcedimentoBean bean = (ProcedimentoBean) it.next();
                    ProcedimentoSempliceBean procedimento = bean.getProcedimento();
                    if (procedimento.getCodice().equalsIgnoreCase(procBean.getCodiceProcedimento())) {
                        procedimento.setEnte(rs.getString("des_classe_ente"));
                        break;
                    }
                }
                if (rs.getString("src_pyth") != null)
                    pythonModule = rs.getString("src_pyth");

                inputBean.getProcedimenti().add(procBean);
            }
            inputBean.setDataOdierna(dataOdierna);

            Output output = new Output();
            if (pythonModule != null && !pythonModule.equalsIgnoreCase("")) {
                String xmlInput = marshallInput(inputBean);
                String xmlOutput = PythonRunner(pythonModule, xmlInput);

                log.debug("WEGO - Query: " + sql);
                log.debug("WEGO - xmlInput: " + xmlInput);
                log.debug("WEGO - xmlOutput: " + xmlOutput);

                output = unmarshallOutput(xmlOutput);
            }
            return output;
        } catch (SQLException e) {
            log.error(e);
            throw e;
        } catch (PyException pe) {
            throw pe;
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
     * Restituisce l'xml descrittivo dell'oggetto input. Utilizzato da getOutputProcedimentiMultipli
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    public String marshallInput(Input obj) {
        StringWriter sw = new StringWriter();
        BeanWriter bw = new BeanWriter(sw);

        try {
            bw.getBindingConfiguration().setMapIDs(false);
            // bw.setWriteIDs(false);
            bw.getXMLIntrospector().getConfiguration().setAttributesForPrimitives(false);
            bw.setWriteEmptyElements(true);
            bw.enablePrettyPrint();
            bw.writeXmlDeclaration("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            bw.write(obj);
            bw.flush();
            bw.close();
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
        // logger.debug(sw.toString());
        return sw.toString();
    }

    /**
     * Restituisce l-oggetto output relativo all-xml di input
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    public Output unmarshallOutput(String xml) {
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
        } catch (IntrospectionException e) {
            log.error(e);
        } catch (SAXException e) {
            log.error(e);
        }
        return newData;
    }

    /**
     * Restituisce la descrizione di uno sportello
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    public String getDescrizioneSportello(String codSportello) throws SQLException {
        ResultSet rs = null;
        Connection conn = null;
        String ret = null;

        try {
            conn = db.open();
            String sql = "select des_sport from sportelli_testi where cod_sport = ? and cod_lang = ?";
            PreparedStatement ps = db.getPreparedStmt(conn, sql);
            ps.setString(1, codSportello);
            ps.setString(2, language);
            
            rs = db.select(conn, ps, sql);
            if (rs.next()) {
                return rs.getString("des_sport");
            }
            return ret;
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
     * Restituisce un oggetto Sportello relativo a codSport e codEnte passati come parametri
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 23-giu-2006
     */
    public Sportello getSportello(final String codSportello, final String codEnte) throws SQLException {
        ResultSet rs = null;
        Connection conn = null;
        Sportello ret = null;

        try {
            conn = db.open();
            
            // Modifica da controllare: ho messo una left join su sportelli_comuni
            String sql = "select sportelli_testi.des_sport, sportelli.* from sportelli inner join sportelli_testi ";
                         sql+="on sportelli.cod_sport=sportelli_testi.cod_sport left join sportelli_comuni on  ";
                         sql+="sportelli.cod_sport=sportelli_comuni.cod_sport where sportelli.cod_sport=? ";
                         //sql+="and sportelli_comuni.cod_ente=? and cod_lang=?";
                         sql+="and cod_lang=?";
            PreparedStatement ps = db.getPreparedStmt(conn, sql);
            ps.setString(1, codSportello);
            //ps.setString(2, codEnte);
            ps.setString(2, language);
            rs = db.select(conn, ps, sql);
            if (rs.next()) {
                ret = new Sportello();
                ret.setCap(rs.getString("cap"));
                ret.setCitta(rs.getString("citta"));
                ret.setCodiceSportello(rs.getString("cod_sport"));
                ret.setDescrizioneSportello(rs.getString("des_sport"));
                ret.setEmail(rs.getString("email"));
                ret.setFax(rs.getString("fax"));
                ret.setFlgAttivo(rs.getString("flg_attivo"));
                ret.setFlgPu(rs.getString("flg_pu"));
                ret.setFlgSu(rs.getString("flg_su"));
                ret.setIndirizzo(rs.getString("indirizzo"));
                ret.setPec(rs.getString("email_cert"));
                ret.setRup(rs.getString("nome_rup"));
                ret.setProvincia(rs.getString("prov"));
                ret.setTelefono(rs.getString("tel"));
            }
            return ret;
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
     * Restituisce il destinatario di un onere
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    public String[] getDestinatarioOnere(String codCom, String codCud) throws SQLException {
        ResultSet rs = null;
        Connection conn = null;

        try {
            conn = db.open();
            String sql = "select relazioni_enti.cod_dest, nome_dest ";
            sql += "from relazioni_enti ";
            sql += "inner join destinatari on destinatari.cod_dest = relazioni_enti.cod_dest ";
            sql += "where cod_com = ? and cod_cud = ? ";
            PreparedStatement ps = db.getPreparedStmt(conn, sql);
            ps.setString(1, codCom);
            ps.setString(2, codCud);
            rs = db.select(conn, ps, sql);
            String[] retVal = new String[2];
            if (rs.next()) {
                retVal[0] = rs.getString("cod_dest");
                retVal[1] = rs.getString("nome_dest");
            }
            return retVal;
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
     * Restituisce i le interdipendenze degli interventi
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    public Object[] getConsequenzialitaInterventi(Set interventiSet) throws SQLException {
        ResultSet rs = null;
        Connection conn = null;
        List datiTabella = new ArrayList();
        boolean almenoUnConflitto = false;

        String str = "";
        Iterator it = interventiSet.iterator();
        while (it.hasNext()) {
            InterventoBean intBean = (InterventoBean) it.next();
            if (intBean.getCodiceInterventoFiglio() == null) {
                str += intBean.getCodice() + ",";
            }
        }
        if (str != null && !"".equalsIgnoreCase(str))
            str = str.substring(0, str.lastIndexOf(","));

        try {
            conn = db.open();
            // TODO query non standard: da modificare per un eventuale porting
            // su oracle
            String sql = "";
            if(isMySqlDb){
                sql = "select cod_int_sel, tit_int_sel, tit_int_prec, case ifnull(tit_int_prec,'###') when '###' then cod_int_sel else cod_int_prec end cod_int_prec ";
                sql += "from ( ";
                sql += "select interventi_testi.cod_int cod_int_sel, interventi_testi.tit_int tit_int_sel, A.tit_int tit_int_prec, ifnull(cod_int_prec, interventi_testi.cod_int) cod_int_prec  ";
                sql += "from interventi_testi ";
                sql += "left join interventi_seq on interventi_seq.cod_int_sel = interventi_testi.cod_int ";
                sql += "left join interventi_testi A on A.cod_int = interventi_seq.cod_int_prec and A.cod_int in ("
                       + str + ") ";
                sql += "where interventi_testi.cod_int in (" + str + ") ";
                sql += "and ifnull(cod_int_prec, interventi_testi.cod_int) in (" + str + ")) dati ";
                sql += "order by cod_int_prec, tit_int_prec ";
            }
            else{
                sql = "select cod_int_sel, tit_int_sel, tit_int_prec, case nvl(tit_int_prec,'###') when '###' then cod_int_sel else to_number(cod_int_prec) end cod_int_prec ";
                sql += "from ( ";
                sql += "select interventi_testi.cod_int cod_int_sel, interventi_testi.tit_int tit_int_sel, A.tit_int tit_int_prec, nvl(cod_int_prec, interventi_testi.cod_int) cod_int_prec  ";
                sql += "from interventi_testi ";
                sql += "left join interventi_seq on interventi_seq.cod_int_sel = interventi_testi.cod_int ";
                sql += "left join interventi_testi A on A.cod_int = interventi_seq.cod_int_prec and A.cod_int in ("
                       + str + ") ";
                sql += "where interventi_testi.cod_int in (" + str + ") ";
                sql += "and nvl(cod_int_prec, interventi_testi.cod_int) in (" + str + ")) dati ";
                sql += "order by cod_int_prec, tit_int_prec ";
            }
            PreparedStatement ps = db.getPreparedStmt(conn, sql);
            rs = db.select(conn, ps, sql);
            while (rs.next()) {
                InterventoBean riga = new InterventoBean();
                riga.setCodice(rs.getString("cod_int_sel"));
                riga.setDescrizione(rs.getString("tit_int_sel"));
                if (rs.getString("tit_int_prec") != null) {
                    almenoUnConflitto = true;
                    riga.setCodiceInterventoFiglio(rs.getString("cod_int_prec"));
                    riga.setDescInterventoFiglio(rs.getString("tit_int_prec"));
                } else {
                    riga.setCodiceInterventoFiglio("");
                    riga.setDescInterventoFiglio("");
                }
                datiTabella.add(riga);
            }
            Object retVal[] = new Object[2];
            retVal[0] = datiTabella;
            retVal[1] = new Boolean(almenoUnConflitto);
            return retVal;
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
     * Restituisce la condizione di visualizzazione di una dichiarazione dinamica
     *
     * @author InIT http://www.gruppoinit.it
     *
     * 16-giu-2006
     */
    public String getCondizioneDiVisualizzazione(String inClause) throws SQLException {
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = db.open();

            String sql = "select campo_xml_mod from href_campi where campo_xml_mod is not null and href in ("+inClause+")";

            PreparedStatement ps = db.getPreparedStmt(conn, sql);
            log.debug("trovaScelte query:" + sql);
            rs = db.select(conn, ps, sql);
            while(rs.next()) {
                return rs.getString("campo_xml_mod");
            }
            return null;
        } catch (SQLException e) {
            log.error(e);
            throw e;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {}
            try {
                conn.close();
            } catch (Exception e) {}
        }
    }
}
