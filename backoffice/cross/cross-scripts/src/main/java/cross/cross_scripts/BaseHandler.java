package cross.cross_scripts;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class BaseHandler {

	protected MySql mysql;
	protected CrossJpa jpa;

	protected BaseHandler(MySql mysql, CrossJpa jpa){
		this.mysql = mysql;
		this.jpa = jpa;
	}

	public MySql getMysql() {
		return mysql;
	}

	public CrossJpa getJpa() {
		return jpa;
	}

	public abstract void run() throws Exception;

	protected int min(String column, String table) throws ClassNotFoundException, SQLException {
		return min(column, table, null);
	}

	protected int min(String column, String table, String where) throws ClassNotFoundException, SQLException {
		String query = "SELECT min("+column+") from "+table + (where != null ? " WHERE " + where : "");
		List<Integer> rets = executeSelect(query, set ->  {
			int number = -1;
			try{
				number = set.getInt(1);
			} catch (SQLException e) {
				throw new RuntimeException("Error in min");
			}
			return number;
		});

		return rets.get(0);
	}

	protected int max(String column, String table) throws ClassNotFoundException, SQLException {
		return max(column, table, null);
	}

	protected int max(String column, String table, String where) throws ClassNotFoundException, SQLException {
		String query = "SELECT max("+column+") from "+table + (where != null ? " WHERE " + where : "");
		List<Integer> rets = executeSelect(query, set ->  {
			int number = -1;
			try{
				number = set.getInt(1);
			} catch (SQLException e) {
				throw new RuntimeException("Error in min");
			}
			return number;
		});

		return rets.get(0);
	}

	protected String value(String column, String table, String where) throws ClassNotFoundException, SQLException {
		String query = "SELECT "+column+" from "+table + (where != null ? " WHERE " + where : "");
		List<String> rets = executeSelect(query, set ->  {
			String number = null;
			try{
				number = set.getString(1);
			} catch (SQLException e) {
				throw new RuntimeException("Error");
			}
			return number;
		});

		return rets.get(0);
	}

	protected <T> List<T> executeSelectWithError(String query, Function<ResultSet, T> c) {
		List<T> rets = new LinkedList<>();
		Statement stmt = null;
		ResultSet set = null;
		try {
			stmt = mysql.getStatement();
			set = stmt.executeQuery(query);
		} catch (SQLException e) {
			throw new RuntimeException("in error " + query);
		}

		try {
			while(set.next()) {
				T value = c.apply(set);
				rets.add(value);
			}
		} catch (SQLException e) {
			throw new RuntimeException("data not found " + query);
		}

		if(stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return rets;
	}
	protected <T> List<T> executeSelect(String query, Function<ResultSet, T> c) throws SQLException {
		List<T> rets = new LinkedList<>();
		Statement stmt = mysql.getStatement();
		ResultSet set = stmt.executeQuery(query);

		while(set.next()) {
			T value = c.apply(set);
			rets.add(value);
		}
		stmt.close();
		return rets;
	}

	protected void consumeSelect(String query, Consumer<ResultSet> consumer) throws SQLException {
		Statement stmt = mysql.getStatement();
		ResultSet set = stmt.executeQuery(query);

		while(set.next()) {
			consumer.accept(set);
		}
		stmt.close();
	}

	protected void biconsumeSelect(String query, BiConsumer<Statement, ResultSet> consumer) throws SQLException {
		Statement stmt = mysql.getStatement();
		ResultSet set = stmt.executeQuery(query);

		while(set.next()) {
			consumer.accept(stmt, set);
		}
		stmt.close();
	}
}
