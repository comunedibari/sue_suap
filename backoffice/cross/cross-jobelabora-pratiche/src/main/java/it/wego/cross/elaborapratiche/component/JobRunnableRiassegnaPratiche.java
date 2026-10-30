package it.wego.cross.elaborapratiche.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


class RiassegnaPratiche {
	private static final Logger logger = LoggerFactory.getLogger(RiassegnaPratiche.class.getName());

	public void run() {
			
			String query = "SELECT "
					+ " p2.`id_pratica`, "
					+ " p2.`identificativo_pratica`, "
					+ " p2.id_utente as id_utente_pratica,"
					+ " pe2.id_utente as id_utente_evento,"
					+ " p2.`oggetto_pratica`, "
					+ " p2.`protocollo`, "
					+ " p2.`anno_riferimento`, "
					+ " p2.`id_stato_pratica`, "
					+ " p2.`data_chiusura`, "
					+ " p2.`id_processo`, "
					+ " p2.`id_utente`, "
					+ " p2.`id_comune`, "
					+ " p2.`data_ricezione`, "
					+ " p2.`data_protocollazione`, "
					+ " p2.`anno`, "
					+ " p2.`data_ultimo_evento_esterno`,"
					+ " pe2.id_pratica_evento AS id_pratica_evento,"
					+ " pe2.data_evento AS data_evento,"
					+ " pe2.protocollo AS protocollo_evento,"
					+ " pe2.data_protocollo AS data_protocollo_evento"
					+ " FROM ((pratica p2"
					+ " JOIN `pratiche_eventi` `pe2`)"
					+ " JOIN processi_eventi ppe2)"
					+ " WHERE p2.id_utente IN (250,306,307,308,309,360,364)"
					+ " AND `p2`.`id_pratica` = `pe2`.`id_pratica`"
					+ " AND `pe2`.`id_evento` = `ppe2`.`id_evento`"
					+ " AND  ppe2.cod_evento = 'PROCINT' "
					+ " AND pe2.id_pratica_evento = ("
					+ " SELECT MAX(pe.id_pratica_evento) "
					+ " FROM (`pratica` `p` "
					+ " JOIN `pratiche_eventi` `pe`) "
					+ " WHERE p.id_pratica = p2.id_pratica"
					+ " AND `p`.`id_pratica` = `pe`.`id_pratica`"
					+ "	)";
			/*
			EventoBean ev = new EventoBean(); 
			MyAbstractEvent absEv = new MyAbstractEvent();
			try {
				
				Connection connection = null;
				PreparedStatement statement = null;

				try {
					dao.getDbpool();
					connection = MySQLDbcp2.getDataSource().getConnection();			
					statement = connection.prepareStatement(query);
					ResultSet resultSet = statement.executeQuery();			
					while (resultSet.next()) {	
						ev.setIdPratica(Integer.valueOf(resultSet.getInt("id_pratica")));
						ev.setIdUtente(Integer.valueOf(resultSet.getInt("id_utente_evento")));
						ev.setIdEventoPratica(Integer.valueOf(resultSet.getInt("id_pratica_evento")));
						absEv.execute(ev);
					}
					resultSet.close();
					statement.close();
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
				finally {
					connection.close();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
		}
	}