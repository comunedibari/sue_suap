package it.wego.cross.mailservices.sql;


import java.sql.*;
import org.apache.commons.dbcp2.BasicDataSource;    

public class MyConnectionPool {

	private static BasicDataSource dataSource;

	public static synchronized BasicDataSource getDataSource() {

		if (dataSource == null) {

			BasicDataSource ds = new BasicDataSource();

			ds.setUrl("jdbc:mysql://localhost:3306/opencross");
			ds.setUsername("");
			ds.setPassword("");

			ds.setDriverClassName("com.mysql.jdbc.Driver");

			ds.setInitialSize(3);
			ds.setMaxTotal(25);
			ds.setMinIdle(0);
			ds.setMaxIdle(8);
			ds.setMaxOpenPreparedStatements(100);

			dataSource = ds;
		}
		return dataSource;
	}

	public static void main(String [] args) throws Exception{   
		for(int i=0; i<11; i++){
			//spawn 11 threads & get each thread to process 100 sql rows at the same time
			new Thread("" + (i*100)){

				public void run(){
					System.out.println("Thread: " + getName() + " running");

					Connection con = null;
					PreparedStatement pstmt = null;
					ResultSet rs = null;
					try {           
						con = MyConnectionPool.getDataSource().getConnection();

						pstmt = con.prepareStatement("select oggetto_risposta from email_consegnate limit "+getName()+",100");
						rs=pstmt.executeQuery();
						while(rs.next()){  
							System.out.println("Doing stuff for thread "+getName());//With smaller ResultSet, this works fine for all 11 threads
							//give the other threads a turn...
							try {
								Thread.sleep(10);
							}
							catch(InterruptedException ex) {
							}
						}

					} catch (Exception e) {
						e.printStackTrace();
					}finally{
						try {pstmt.close();} catch (SQLException e) {}
						try { con.close(); } catch(Exception e) {}
						try { rs.close(); } catch(Exception e) {}
					}
				}
			}.start();
		}
	}
}

