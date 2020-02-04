package it.exprivia.pal.avbari.suapsue.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.exprivia.pal.avbari.suapsue.dto.SportelloEnte;
import it.exprivia.pal.avbari.suapsue.dto.TipoPratica;
import it.exprivia.pal.avbari.suapsue.service.SportelloService;
import it.exprivia.pal.avbari.suapsue.service.persistence.DataSourceFactory;

public class SportelloServiceDAO implements SportelloService {
	
	protected DataSource ds;
	
	protected SportelloEnteRowMapper rowMapper;
	
	private static final Logger logger = LoggerFactory.getLogger(SportelloServiceDAO.class);
	
	
	/**
	 * Costruttore
	 * @throws SQLException 
	 */
	public SportelloServiceDAO() throws SQLException {
		ds = DataSourceFactory.getDataSource();
		rowMapper = new SportelloEnteRowMapper();
	}
	
	@Override
	public Collection<SportelloEnte> findAll() throws SQLException {

		if (logger.isDebugEnabled())
			logger.debug("METODO findAll. RIRCERCO TUTTI GLI SPORTELLI");
		
		final String findAllStatement = "SELECT * FROM enti ORDER BY descr";
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = ds.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery(findAllStatement);
			
			Collection<SportelloEnte> elenco = new ArrayList<SportelloEnte>();
			
			while (rs.next())
				elenco.add(rowMapper.mapRow(rs));
			
			if (logger.isInfoEnabled())
				logger.info(String.format("Sportelli ritornati: %d", elenco.size()));
			
			return elenco;
		} finally {
			close(rs, st, conn);
		}
	}
	
	private void close(ResultSet rs, Statement st, Connection conn) {
		if (rs != null)
			try {
				rs.close();
			} catch (SQLException e) {
				logger.warn("ERRORE NELLA CHIUSURA DEL RESULT SET {}", e.getMessage(),e);
			}
		if (st != null)
			try {
				st.close();
			} catch (SQLException e) {
				logger.warn("ERRORE NELLA CHIUSURA DELLO STATEMENT {}", e.getMessage(),e);
			}
		if (conn != null)
			try {
				conn.close();
			} catch (SQLException e) {
				logger.warn("ERRORE NELLA CHIUSURA DELLA CONNECTION {}", e.getMessage(),e);
			}
	}
	
	@Override
	public Collection<SportelloEnte> findByTipo(TipoPratica tipo) throws SQLException {

		final String findByTipoStatement = "SELECT * FROM enti WHERE tipo=? ORDER BY descr";
		
		if (logger.isInfoEnabled())
			logger.info(String.format("tutti gli sportelli %s", tipo.toString()));
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ds.getConnection();
			ps = conn.prepareStatement(findByTipoStatement);
			ps.setString(1, tipo.toString());
			rs = ps.executeQuery();
			
			Collection<SportelloEnte> elenco = new ArrayList<SportelloEnte>();
			
			while (rs.next())
				elenco.add(rowMapper.mapRow(rs));
			
			if (logger.isInfoEnabled())
				logger.info(String.format("Sportelli ritornati: %d", elenco.size()));
			
			return elenco;
		} finally {
			close(rs, ps, conn);
		}
	}
	
	@Override
	public Collection<SportelloEnte> findByTipoAndStato(TipoPratica tipo, boolean flag_attivo) throws SQLException {

		final String findByTipoStatement = "SELECT * FROM enti WHERE tipo=? AND flag_attivo=? ORDER BY descr";
		
		if (logger.isInfoEnabled())
			logger.info(String.format("Sportelli Tipo: %s, Stato: %s", tipo.toString(), flag_attivo));
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ds.getConnection();
			ps = conn.prepareStatement(findByTipoStatement);
			ps.setString(1, tipo.toString());
			ps.setBoolean(2, flag_attivo);
			rs = ps.executeQuery();
			
			Collection<SportelloEnte> elenco = new ArrayList<SportelloEnte>();
			
			while (rs.next())
				elenco.add(rowMapper.mapRow(rs));
			
			if (logger.isInfoEnabled())
				logger.info(String.format("Sportelli ritornati: %d", elenco.size()));
			
			return elenco;
		} finally {
			close(rs, ps, conn);
		}
	}


	@Override
	public SportelloEnte findById(long id) throws SQLException {

		final String findByIdStatement = "SELECT * FROM enti WHERE id=? ORDER BY descr";
		
		if (logger.isInfoEnabled())
			logger.info(String.format("sportello id %d", id));
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ds.getConnection();
			ps = conn.prepareStatement(findByIdStatement);
			ps.setLong(1, id);
			rs = ps.executeQuery();
			
			SportelloEnte sportello = null;
			if (rs.next())
				sportello = rowMapper.mapRow(rs);
			
			if (logger.isDebugEnabled())
				logger.debug("METODO FINDBYID. CERCO SPORTELLO CON ID {}. SPORTELLO TROVATO {}", id, sportello);
			
			return sportello;
		} finally {
			close(rs, ps, conn);
		}
	}
	
	@Override
	public SportelloEnte findByTipoAndComuneEgov(int comuneEgov, TipoPratica tipo) throws SQLException {

		final String findByIdStatement = "SELECT * FROM enti WHERE comuneEgov=? AND tipo=? ";
		
		if (logger.isDebugEnabled())
			logger.debug(String.format("sportello id=%d, tipo=%s", comuneEgov, tipo));
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ds.getConnection();
			ps = conn.prepareStatement(findByIdStatement);
			ps.setInt(1, comuneEgov);
			ps.setString(2, tipo.toString());
			rs = ps.executeQuery();
			
			SportelloEnte sportello = null;
			if (rs.next())
				sportello = rowMapper.mapRow(rs);
			
			if (logger.isDebugEnabled())
				logger.debug("METODO findByTipoAndComuneEgov. RICERCA SPORTELLO PER COMUNE {}, TIPO PRATICA {}. SPORTELLO TROVATO {}",comuneEgov, tipo, sportello);
			
			return sportello;
		} finally {
			close(rs, ps, conn);
		}
	}
	
	@Override
	public  void updateStatoSportello(int en, boolean st) throws SQLException  {

		final String updateStatement = "UPDATE enti SET flag_attivo=?  WHERE idEnte=?";
		
		if (logger.isInfoEnabled())
			logger.info(String.format("sportello idEnte %d, attivazione %s", en, st));
		
		Connection conn = null;
		PreparedStatement ps = null;
		int rows=0;
		
		try {
			conn = ds.getConnection();
		} catch (SQLException connectionExceotion) {
			String err="E1";
			throw new SQLException(err);
		} 
				
		try {
			
			try{
				ps = conn.prepareStatement(updateStatement);
			} catch (SQLException queryException) {	
				String err="E2";
				throw new SQLException(err);
			}			
			try {				
				ps.setBoolean(1, st);
				ps.setLong(2, en);
			} catch (SQLException queryException) {	
				String err="E3";
				throw new SQLException(err);
			}
			try {				
				rows=ps.executeUpdate();
			} catch (SQLException queryException) {	
				
				if (rows>1){
					String err="E4";
					throw new SQLException(err);
				}	else {
					String err="E5";
					throw new SQLException(err);
				}			
			}					
			if (logger.isInfoEnabled())
				logger.info(String.format("Attivazione sportello idEnte %d eseguita", en));
		} finally {
			close(null, ps, conn);
		}
	}
	
	private class SportelloEnteRowMapper {
		
		public SportelloEnte mapRow(ResultSet rs) throws SQLException {
			SportelloEnte se = new SportelloEnte();
			se.setId(new Long(rs.getLong("id")));
			se.setTipo(TipoPratica.decode(rs.getString("tipo")));
			se.setDenominazione(rs.getString("descr"));
			se.setCodSportello(new Integer(rs.getInt("idEnte")));
			se.setCodComune(new Integer(rs.getInt("codComune")));
			se.setUrl(rs.getString("url"));
			se.setFlagAttivo(rs.getBoolean("flag_attivo"));
			se.setComuneEgov(new Integer(rs.getInt("comuneEgov")));
			se.setUrlPagina(rs.getString("urlPagina"));
			return se;
		}
	}
}