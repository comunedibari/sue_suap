package it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.dao;

import it.gruppoinit.commons.DBCPManager;
import it.people.db.DBConnector;
import it.people.exceptions.PeopleDBException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LocaleDAO {

    private Log log = LogFactory.getLog(this.getClass());
    
    public List getFEDBLocales(String nodeId) throws SQLException, PeopleDBException, Exception {
        List locales = new ArrayList();
        ResultSet rs = null;
        Connection connection = null;
        try {
            connection = DBConnector.getInstance().connect(DBConnector.FEDB);
            String sql = "SELECT _locale "
                    + "FROM bundles "
                    + "WHERE nodeId = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, nodeId);
            rs = ps.executeQuery();
            while (rs.next()){
                locales.add(rs.getString("_locale"));
            }
        } catch (SQLException ex) {
            log.error("Errore ricercando i locale definiti su FEDB", ex);
            throw ex;
        } catch (PeopleDBException ex) {
            log.error("Errore ricercando i locale definiti su FEDB", ex);
            throw ex;
        } catch (Exception ex) {
            log.error("Errore ricercando i locale definiti su FEDB", ex);
            throw ex;
        } finally {
            try {
                rs.close();
            } catch (Exception ex) {}
            try {
                connection.close();
            } catch (Exception ex) {}
        }
        return locales;
    }
    
    public List getPUDSLocales(DBCPManager db) throws SQLException, Exception{
        List locales = new ArrayList();
        ResultSet rs = null;
        Connection connection = null;
        try {
            String sql = "SELECT DISTINCT(cod_lang) "
                    + "FROM lingue_aggregazioni";
            connection = db.open();
            PreparedStatement ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                locales.add(rs.getString("cod_lang"));
            }
        } catch (SQLException ex) {
            log.error("Errore ricercando i locale definiti su PUDS", ex);
            throw ex;
        } catch (Exception ex) {
            log.error("Errore ricercando i locale definiti su PUDS", ex);
            throw ex;
        } finally {
            try {
                rs.close();
            } catch (Exception ex) {}
            try {
                connection.close();
            } catch (Exception ex) {}
        }
        return locales;
    }
}
