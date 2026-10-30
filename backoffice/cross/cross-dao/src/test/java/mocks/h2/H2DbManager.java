package mocks.h2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.stream.Stream;

import org.h2.jdbc.JdbcStatement;

public class H2DbManager {

	Connection connection;
	
	public H2DbManager(Connection connection) {
		super();
		this.connection = connection;
	}

	public void init() throws SQLException, IOException {

		JdbcStatement stmt = (JdbcStatement) connection.createStatement();
		loadFromDirectoryPath(Paths.get("src","test","resources","database", "tables"),stmt);
		stmt.close();
	}
	
	public void loadTest(String folder, String name) throws SQLException, IOException {
		JdbcStatement stmt = (JdbcStatement) connection.createStatement();
		loadFromPathFile(Paths.get("src","test","resources","database", "tests", folder, name),stmt);
		stmt.close();
	}
	

	private void loadFromDirectoryPath(Path path, JdbcStatement stmt) throws SQLException, IOException {
		try (Stream<Path> paths = Files.walk(path)) {
			paths.filter(Files::isRegularFile).forEach(fileEntry ->{
				StringBuilder sb = new StringBuilder();

				try {
					loadFromPathFile(fileEntry, stmt);
				} catch (SQLException | IOException e) {
					throw new RuntimeException(e);
				}
			});
		} 		
	}
	
	private void loadFromPathFile(Path path, JdbcStatement stmt) throws SQLException, IOException {
		StringBuilder sb = new StringBuilder();
		try (Stream<String> stream = Files.lines(path)) {
					
			stream.forEach(line->{
				sb.append(line).append("\n");
			});
			
		} catch (IOException e) {
			throw new RuntimeException("error during h2 " +path.toString() +"loading",e);
		}
		try {
			stmt.executeUpdate(sb.toString());
		} catch (SQLException e) {
			throw  new RuntimeException("error during h2 " +path.toString()+"loading",e);
		}
	
	}
	
//	public static void main(String[] args) throws SQLException, IOException {
//		JdbcDataSource dataSource = new JdbcDataSource();
//		dataSource.setUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
//
//		Connection connection = dataSource.getConnection();
//		H2DbManager l = new H2DbManager(connection);
//		l.init();
//	}
}
