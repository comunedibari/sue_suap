package cross.BonificaAnagrafica;



import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

import cross.ElabThread;
import cross.BonificaAnagrafica.sql.Confronta_ruoli_cross;
import cross.BonificaAnagrafica.sql.Dao;
import cross.BonificaAnagrafica.sql.Query;
import cross.BonificaAnagrafica.sql.anagrafica_civilia;
import cross.BonificaAnagrafica.sql.anagrafica_cross;
import cross.utilities.LogClass;
import cross.utilities.myIndexableMap;
import cross.utilities.myUtility;


public class BonificaAnagraficaApplication {

	public static final String URL_DB_CIVILIA_BONIFICA = "jdbc.url.civilia";

	public static final String URL_DB_OPENCROSS_PROD = "jdbc.url.opencross";

	private int num_thread_elab;
	
	public int getNum_thread_elab() {
		return num_thread_elab;
	}

	public void setNum_thread_elab(int num_thread_elab) {
		this.num_thread_elab = num_thread_elab;
	}

	public void elaboraMultithread() {

		 ArrayList<Thread> listTh = new ArrayList<Thread>();

		for (int i=0; i<this.getNum_thread_elab(); i++ ) {
			int threadid = i+1;
			Thread process = new Thread(new ElabThread(threadid));	
			LogClass.trace("BONIFICA ANAGRAFICA CROSS START THREAD " + threadid);
			process.start();
			listTh.add(process);
		}
		
		System.out.println("BONIFICA ANAGRAFICA CROSS: wait for threads to end");

		try {
			
			for (Thread th : listTh) {
				th.join();
			}

			System.out.println("BONIFICA ANAGRAFICA CROSS: End all thread");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public static void run(int thread_id) {
		int LAST_STEP_EXECUTED = 0;
		int pratiche_elaborate = 0;
		myUtility utl = new myUtility();
		LAST_STEP_EXECUTED=1;
		Dao dao_civilia_bonifica = new Dao();dao_civilia_bonifica.openConnection(URL_DB_CIVILIA_BONIFICA); 
		Dao dao_opencross_prod = new Dao();dao_opencross_prod.openConnection(URL_DB_OPENCROSS_PROD); 
		LogClass.trace("BONIFICA ANAGRAFICA ELABORAZIONE THREAD: " + thread_id);
		int index = 1;
		LAST_STEP_EXECUTED=2;
		String query = "", tmp_codice_fiscale = "", tmp_partita_iva = "";
		int flg_anag_cross_corrispondente = 0;
		int flag_agg_ruoli_stessa_anag = 0;
		int recupdate = 0;
		String anagrafica_con_piu_ruoli = "";

		try {
			java.sql.Connection connection = dao_civilia_bonifica.getConnection(thread_id);
			query = Query.select_nr_pratiche_civilia.replace("@", utl.getValueProperties("schema_civilia"));
			query = myUtility.setString(query, "?", String.valueOf(thread_id));
			int nr_pratiche = dao_civilia_bonifica.queryInt(query, "nr_pratiche",thread_id);
			LAST_STEP_EXECUTED=211;
			
			/***/
			query = Query.select_pratiche_civilia.replace("@", utl.getValueProperties("schema_civilia"));
			/**/			
			/*LOR SOLO x TEST **
			query = Query.tmp_select_pratiche_civilia.replace("@", utl.getValueProperties("schema_civilia"));
			**/
			PreparedStatement ps = connection.prepareStatement(query);
			index=1;
			ps.setInt(index++, thread_id);

			LogClass.trace("BONIFICA ANAGRAFICA ELABORAZIONE THREAD( " + thread_id + ") TOTALE PRATICHE DA ELABORARE(" + nr_pratiche + ")  QUERY:" + query);

			ResultSet rs = ps.executeQuery();
			LAST_STEP_EXECUTED=3;

			while (rs.next()) {
				pratiche_elaborate++;
				int pratica_id_civilia = rs.getInt("pratica_id");
				String identificativo_pratica = rs.getString("identificativo_pratica");
				System.out.println(" id_pratica: " + pratica_id_civilia + " identificativo_pratica:" + identificativo_pratica);
				LogClass.trace(" id_pratica: " + pratica_id_civilia + " identificativo_pratica:" + identificativo_pratica);

				query = Query.select_pratica_cross.replace("@", utl.getValueProperties("schema_opencross"));
				query = query.replace("?", identificativo_pratica);
				int id_pratica_cross = dao_opencross_prod.queryInt(query, "id_pratica",thread_id);
				LAST_STEP_EXECUTED=4;
								
				// Estrazione tutte le anagrafiche civilia relative alla pratica estratta 
				ArrayList<anagrafica_civilia> list_AnagCivilia = new ArrayList<anagrafica_civilia>();
				java.sql.Connection connection_civilia = dao_civilia_bonifica.getConnection(thread_id);

				query = Query.select_anagrafiche_civilia.replace("@", utl.getValueProperties("schema_civilia"));
				PreparedStatement ps_connection_civilia = connection_civilia.prepareStatement(query);
				index=1;
				ps_connection_civilia.setInt(index++, pratica_id_civilia);
				ResultSet rs_ps_connection_civilia = ps_connection_civilia.executeQuery();
				LAST_STEP_EXECUTED=5;

				while (rs_ps_connection_civilia.next()) {
					anagrafica_civilia civ = new anagrafica_civilia
							(
									rs_ps_connection_civilia.getInt("id_anagrafica"),
									myUtility.replaceNull(rs_ps_connection_civilia.getString("tipoAnagrafica_civ")),
									myUtility.replaceNull(rs_ps_connection_civilia.getString("cognome_civ")),
									myUtility.replaceNull(rs_ps_connection_civilia.getString("nome_civ")),
									myUtility.replaceNull(rs_ps_connection_civilia.getString("codiceFiscale_civ")),
									myUtility.replaceNull(rs_ps_connection_civilia.getString("partitaIva_civ")),
									myUtility.replaceNull(rs_ps_connection_civilia.getString("ruolo_civ")),
									myUtility.replaceNull(rs_ps_connection_civilia.getString("sesso"))
									);
					if(civ.getcognome_civ().isEmpty() && civ.getnome_civ().isEmpty()) {
						LAST_STEP_EXECUTED=51;
					} else {
						list_AnagCivilia.add(civ);
					}
				}
				rs_ps_connection_civilia.close();
				ps_connection_civilia.close();

				// Estrazione tutte le anagrafiche cross relative alla pratica estratta 

				ArrayList<anagrafica_cross> list_AnagCross = new ArrayList<anagrafica_cross>();
				java.sql.Connection connection_cross = dao_opencross_prod.getConnection(thread_id);

				LAST_STEP_EXECUTED=6;

				query = Query.select_anagrafiche_cross_eventi.replace("@", utl.getValueProperties("schema_opencross"));
				PreparedStatement ps_connection_cross = connection_cross.prepareStatement(query);
				index = 1;
				ps_connection_cross.setString(index++, identificativo_pratica);
				ResultSet rs_ps_connection_cross = ps_connection_cross.executeQuery();
				LAST_STEP_EXECUTED=7;

				while (rs_ps_connection_cross.next()) {
					anagrafica_cross anag_cross = new anagrafica_cross
							(
									rs_ps_connection_cross.getInt("id_pratica"),
									rs_ps_connection_cross.getInt("id_anagrafica"),  
									rs_ps_connection_cross.getInt("id_tipo_ruolo"), 	
									myUtility.replaceNull(rs_ps_connection_cross.getString("tipo_anagrafica_cross")),
									myUtility.replaceNull(rs_ps_connection_cross.getString("denominazione")), 
									rs_ps_connection_cross.getInt("nuova_anagrafica"), 
									myUtility.replaceNull(rs_ps_connection_cross.getString("cognome_cross")), 
									myUtility.replaceNull(rs_ps_connection_cross.getString("nome_cross")), 
									myUtility.replaceNull(rs_ps_connection_cross.getString("codice_fiscale_cross")), 
									myUtility.replaceNull(rs_ps_connection_cross.getString("ruolo_cross")), 
									myUtility.replaceNull(rs_ps_connection_cross.getString("partita_iva_cross")), 
									myUtility.replaceNull(rs_ps_connection_cross.getString("flg_individuale_cross"))														
									);
					list_AnagCross.add(anag_cross);
				}
				rs_ps_connection_cross.close();
				ps_connection_cross.close();

				LAST_STEP_EXECUTED=8;

				LogClass.trace("list_AnagCivilia size():"  + list_AnagCivilia.size());
				LogClass.trace("list_AnagCross size():"  + list_AnagCross.size());

				
				LAST_STEP_EXECUTED=1204;							
				query = Query.delete_cross_pratica_anagrafica.replace("@", utl.getValueProperties("schema_opencross"));
				query = myUtility.setString(query,  "?", String.valueOf(id_pratica_cross));
				/* query = myUtility.setString(query,  "?", String.valueOf(id_anagrafica_cross)); */
				recupdate = dao_opencross_prod.executeUpdate(query,thread_id);
				LogClass.trace("QUERY delete_cross_pratica_anagrafica:" + query);	
				LAST_STEP_EXECUTED=1202;

				//Anagrafiche da inserire
				for(anagrafica_civilia anag_civ_obj : list_AnagCivilia) {
					flg_anag_cross_corrispondente = 0;

					LogClass.trace("anag_civ_obj to_string():"  + anag_civ_obj.to_string());

					flag_agg_ruoli_stessa_anag = 0;
					String anagCivilia_codice_fiscale = anag_civ_obj.getcodiceFiscale_civ().toLowerCase().trim();
					String anagCivilia_piva = anag_civ_obj.getpartitaIva_civ().toLowerCase().trim();
					
					int id_anagrafica_cross = 0;					
					
					for( anagrafica_cross ana_cross_obj : list_AnagCross) {
						LogClass.trace("ana_cross_obj to_string():"  + ana_cross_obj.to_string());

						String anagCross_codice_fiscale = ana_cross_obj.get_codice_fiscale_cross().toLowerCase().trim();
						String anagCross_piva = ana_cross_obj.get_partita_iva_cross().toLowerCase().trim();
						
						if((!anagCivilia_codice_fiscale.isEmpty() && !anagCross_codice_fiscale.isEmpty() &&  
								anagCivilia_codice_fiscale.equals(anagCross_codice_fiscale)) ||
							(!anagCivilia_piva.isEmpty() && !anagCross_piva.isEmpty() &&  
									anagCivilia_piva.equals(anagCross_piva))){
							
							id_anagrafica_cross = ana_cross_obj.getId_anagrafica();
							
							flg_anag_cross_corrispondente = 1;
							
							LAST_STEP_EXECUTED=1201;
							break;
						}
					}

					//Controllo se esiste già esiste un'anagrafica corrispondente in cross associata alla pratica selezionate					
					if(flg_anag_cross_corrispondente == 1) { // 1=true Anagrafica già esistente

						try {
			
							LAST_STEP_EXECUTED=1207;

							//Seleziona ruolo cross civilia corrispondente 
							query = Query.select_ruoli_cross_civilia.replace("@", utl.getValueProperties("schema_opencross"));
							query = myUtility.setString(query, "?", anag_civ_obj.getruolo_civ());
							int ruolo_cross_civilia = dao_opencross_prod.queryInt(query, "id_tipo_ruolo",thread_id);

							LogClass.trace("Seleziona ruolo cross civilia corrispondente :" + query );

							//Recupero id_etichetta da inserire nella tabella pratica_anagrafica
							LAST_STEP_EXECUTED=1208;
							query = Query.select_ruolo_etichetta.replace("@", utl.getValueProperties("schema_opencross"));
							query = myUtility.setString(query, "?", anag_civ_obj.getruolo_civ());
							int id_etichetta = dao_opencross_prod.queryInt(query, "id",thread_id);
							LogClass.trace("QUERY select_ruolo_etichetta:" + query);	

							LAST_STEP_EXECUTED=1209;
							query = "INSERT INTO @.`pratica_anagrafica` "
									+ " (`id_pratica`, `id_anagrafica`, `id_tipo_ruolo`, `data_inizio_validita`, `flg_ditta_individuale`, `id_etichetta`) " 
									+ " VALUES (?,?,?,now(),'?',?)";

							query = query.replace("@", utl.getValueProperties("schema_opencross"));															
							query = myUtility.setString(query,  "?", String.valueOf(id_pratica_cross));
							query = myUtility.setString(query,  "?", String.valueOf(id_anagrafica_cross));
							query = myUtility.setString(query,  "?", String.valueOf(ruolo_cross_civilia));
							query = myUtility.setString(query,  "?", String.valueOf(anag_civ_obj.gettipoAnagrafica_civ()));
							query = myUtility.setString(query,  "?", String.valueOf(id_etichetta));
							try {
								LogClass.trace("QUERY insert_pratica_anagrafica:" + query);	
								recupdate = dao_opencross_prod.executeUpdate(query,thread_id);
							} catch (Exception e) {
								LogClass.trace("ERRORE insert_pratica_anagrafica:" + e.getMessage());	
							}																		
							LAST_STEP_EXECUTED=124;

						} catch (Exception e) {
							e.printStackTrace();
							LogClass.trace("ERRORE IN elimina_anagrafiche_cross msgerrore:" + e.getMessage());
						}		
					}
					if(flg_anag_cross_corrispondente == 0) { //0=false, Anagrafica non esistente

						int id_anagrafica = 0;

						LAST_STEP_EXECUTED=131;
						//Non inserire le anagrafiche che hanno il ruolo_cross non applicabile (Tabella ruolo_etichetta_civilia_cross)
						query = Query.check_ruolociv_non_applicabile.replace("@", utl.getValueProperties("schema_opencross"));
						query = query.replace("?", anag_civ_obj.getruolo_civ());
						int ruolo_non_applicabile = dao_opencross_prod.queryInt(query, "ruolo_na",thread_id);
						LAST_STEP_EXECUTED=13;

						// Verifico se l'anagrafica da inserire ha un ruolo non applicabile
						if(ruolo_non_applicabile == 0) {	//0=false, 1=true
							tmp_partita_iva = anag_civ_obj.getpartitaIva_civ();

							if(anag_civ_obj.getcodiceFiscale_civ().length()>0) {
								tmp_codice_fiscale = anag_civ_obj.getcodiceFiscale_civ();
							}
							else {
								LAST_STEP_EXECUTED=141;
								int id_anagrafica_senza_cf = genera_codice_fiscale_cross(
										connection_cross, utl, 
										anag_civ_obj.getcognome_civ(), 
										anag_civ_obj.getnome_civ(), 
										anag_civ_obj.getcodiceFiscale_civ(),
										anag_civ_obj.getpartitaIva_civ());

								tmp_codice_fiscale = "Non definito " + id_anagrafica_senza_cf;							
							}

							if(anag_civ_obj.gettipoAnagrafica_civ().equals("F")) {
								LAST_STEP_EXECUTED=14;
								query = Query.check_esistenza_anagrafica_cross_F.replace("@", utl.getValueProperties("schema_opencross"));
								query = query.replace("?", tmp_codice_fiscale);
							} else if(anag_civ_obj.gettipoAnagrafica_civ().equals("G")) {
								query = Query.check_esistenza_anagrafica_cross_G.replace("@", utl.getValueProperties("schema_opencross"));
								query = query.replace("?", tmp_partita_iva);
							}

							LogClass.trace("BONIFICA ANAGRAFICA ELABORAZIONE THREAD: " + thread_id + " QUERY check_esistenza_anagrafica_cross:" + query);

							int anag_cross_esistente = dao_opencross_prod.queryInt(query, "anag_cross_esistente",thread_id);

							// Verifica se l'anagrafica civilia da inserire esiste già in cross
							if(anag_cross_esistente == 0) { //0=false, 1=true

								id_anagrafica = inserimento_anag_cross(
										connection_cross, 
										utl, 
										anag_civ_obj, 
										tmp_codice_fiscale, 
										tmp_partita_iva);

								LAST_STEP_EXECUTED=15;

							} 

							LAST_STEP_EXECUTED=16;
							if(anag_civ_obj.gettipoAnagrafica_civ().equals("F")) {
								LAST_STEP_EXECUTED=14;
								
								query = Query.get_id_anagrafica_cross_F.replace("@", utl.getValueProperties("schema_opencross"));
								query = query.replace("?", tmp_codice_fiscale);
								
							} else if(anag_civ_obj.gettipoAnagrafica_civ().equals("G")) {
								
								query = Query.get_id_anagrafica_cross_G.replace("@", utl.getValueProperties("schema_opencross"));
								query = query.replace("?", tmp_partita_iva);
							}
							id_anagrafica = dao_opencross_prod.queryInt(query, "id_anagrafica",thread_id);
							LAST_STEP_EXECUTED=17;
							
							query = Query.select_ruoli_cross_civilia.replace("@", utl.getValueProperties("schema_opencross"));
							query = myUtility.setString(query, "?", anag_civ_obj.getruolo_civ());
							int ruolo_cross_civilia = dao_opencross_prod.queryInt(query, "id_tipo_ruolo",thread_id);

							LogClass.trace("Seleziona ruolo cross civilia corrispondente :" + query );

							//Recupero id_etichetta da inserire nella tabella pratica_anagrafica
							LAST_STEP_EXECUTED=18;
							query = Query.select_ruolo_etichetta.replace("@", utl.getValueProperties("schema_opencross"));
							query = myUtility.setString(query, "?", anag_civ_obj.getruolo_civ());
							int id_etichetta = dao_opencross_prod.queryInt(query, "id",thread_id);
							LogClass.trace("QUERY select_ruolo_etichetta:" + query);	
							
							LAST_STEP_EXECUTED=19;

							try {
								query = "INSERT INTO @.`pratica_anagrafica` "
										+ " (`id_pratica`, `id_anagrafica`, `id_tipo_ruolo`, `data_inizio_validita`, `flg_ditta_individuale`, `id_etichetta`) " 
										+ " VALUES (?,?,?,now(),?,?)";

								query = query.replace("@", utl.getValueProperties("schema_opencross"));

								PreparedStatement ps_ins_pranag_3 = connection_cross.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
								index=1;
								ps_ins_pranag_3.setInt(index++, id_pratica_cross);
								ps_ins_pranag_3.setInt(index++, id_anagrafica);
								ps_ins_pranag_3.setInt(index++, ruolo_cross_civilia);
								ps_ins_pranag_3.setString(index++, anag_civ_obj.gettipoAnagrafica_civ());
								ps_ins_pranag_3.setInt(index++, id_etichetta);
								ps_ins_pranag_3.executeUpdate();
								ps_ins_pranag_3.close();
								LAST_STEP_EXECUTED=19;


							} catch (SQLIntegrityConstraintViolationException e) {
								// Duplicate entry
								String queryTrace = query;
								queryTrace = myUtility.setString(queryTrace, "?", String.valueOf(id_pratica_cross));
								queryTrace = myUtility.setString(queryTrace, "?", String.valueOf(id_anagrafica));		
								queryTrace = myUtility.setString(queryTrace, "?", String.valueOf(ruolo_cross_civilia));		
								queryTrace = myUtility.setString(queryTrace, "?", anag_civ_obj.gettipoAnagrafica_civ());		
								queryTrace = myUtility.setString(queryTrace, "?", String.valueOf(id_etichetta));		
								LogClass.trace(" SQLException Duplicate entry query:"+ queryTrace);

							} catch (SQLException e) {
								String queryTrace = query;
								queryTrace = myUtility.setString(queryTrace, "?", String.valueOf(id_pratica_cross));
								queryTrace = myUtility.setString(queryTrace, "?", String.valueOf(id_anagrafica));		
								queryTrace = myUtility.setString(queryTrace, "?", String.valueOf(ruolo_cross_civilia));		
								queryTrace = myUtility.setString(queryTrace, "?", anag_civ_obj.gettipoAnagrafica_civ());		
								queryTrace = myUtility.setString(queryTrace, "?", String.valueOf(id_etichetta));		
								LogClass.trace(" SQLException Duplicate entry query:"+ queryTrace);
							}
							query = "INSERT INTO @.`anagrafica_cross_insert` (`pratica_id`, `anagrafica_id_civ`, `anagrafica_id_cross`, `data`) VALUES (?,?,?, NOW())";
							query = query.replace("@", utl.getValueProperties("schema_civilia"));

							LogClass.trace("anagrafica_cross_insert query:"+ query + " id_pratica_cross:" + id_pratica_cross + " id_anagrafica:" + 
									id_anagrafica + " id_anagrafica_civ:" + anag_civ_obj.get_id_anagrafica() );

							PreparedStatement ps_upd_cross_3 = connection_cross.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

							index = 1;
							ps_upd_cross_3.setInt(index++, id_pratica_cross);
							ps_upd_cross_3.setInt(index++, anag_civ_obj.get_id_anagrafica());
							ps_upd_cross_3.setInt(index++, id_anagrafica);
							ps_upd_cross_3.executeUpdate();
							ps_upd_cross_3.close();

							LAST_STEP_EXECUTED=20;
						}
					}
				}					

				// Elimina anagrafiche cross
				for(anagrafica_cross ana_cross_obj2 : list_AnagCross) {
					int idx_anag_civilia_corrispondente = 0;
					for(anagrafica_civilia anag_civ_obj2 : list_AnagCivilia) {
						if(ana_cross_obj2.get_nuova_anagrafica() != 0) {
							idx_anag_civilia_corrispondente=1; 
							break;
						}
						else if(ana_cross_obj2.get_codice_fiscale_cross().toUpperCase().equals(anag_civ_obj2.getcodiceFiscale_civ().toUpperCase()) 
								) {
							query = Query.select_ruolo_etichetta_non_applicabile.replace("@", utl.getValueProperties("schema_opencross"));							
							query = myUtility.setString(query, "?", String.valueOf(ana_cross_obj2.getId_pratica()));
							query = myUtility.setString(query, "?", String.valueOf(ana_cross_obj2.getId_anagrafica()));							
							int checkNonApplicabile = dao_opencross_prod.queryInt(query, "ruolo_non_applicabile",thread_id); 		
							if(checkNonApplicabile == 0) {
								idx_anag_civilia_corrispondente=1; 
								break;
							}
						}
					}

					if(idx_anag_civilia_corrispondente==0) {
						LAST_STEP_EXECUTED=21;

						elimina_anagrafiche_cross(
								ana_cross_obj2.getId_pratica(), 
								ana_cross_obj2.getId_anagrafica(), 
								dao_opencross_prod, 
								utl,
								thread_id);
						LAST_STEP_EXECUTED=22;


					}
				}							
			}			
			ps.close();		

			LogClass.trace("BONIFICA ANAGRAFICA ELABORAZIONE END THREAD( " + thread_id + ") TOTALE PRATICHE ELABORATE(" + pratiche_elaborate + ")");
		} catch (Exception e) {
			e.printStackTrace();
			LogClass.trace("ERRORE IN RUN Thread_id:" + thread_id + " LAST_STEP_EXECUTED:" + LAST_STEP_EXECUTED + " msgerrore:" + e.getMessage());
		}
	}
	/*
	 * Inserisce le anagrafiche presenti in Civilia e non presenti in cross con il ruolo + etichetta
	 * 
	 */
	private static int inserimento_anag_cross(
			java.sql.Connection connection_cross, 
			myUtility utl, 
			anagrafica_civilia anag_civ_obj,
			String tmp_codice_fiscale,
			String tmp_partita_iva)
	{
		int id_anagrafica = 0;
		//Procedo con l'inserimento dell'anagrafica Civilia in Cross

		String query = "INSERT INTO @.`anagrafica` " 
				+ "(`tipo_anagrafica`, "
				+ "`cognome`," 
				+ "`nome`," 
				+ "`codice_fiscale`," 
				+ "`denominazione`," 
				+ "`partita_iva`," 
				+ "`flg_individuale`," 
				+ "`sesso`) VALUES (?,?,?,?,?,?,?,?)";

		query = query.replace("@", utl.getValueProperties("schema_opencross"));
		PreparedStatement ps_upd_cross_2;
		try {
			ps_upd_cross_2 = connection_cross.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);


			int index = 1;
			ps_upd_cross_2.setString(index++, anag_civ_obj.gettipoAnagrafica_civ());
			ps_upd_cross_2.setString(index++, anag_civ_obj.getcognome_civ());
			ps_upd_cross_2.setString(index++, anag_civ_obj.getnome_civ());
			ps_upd_cross_2.setString(index++, tmp_codice_fiscale);
			ps_upd_cross_2.setString(index++, anag_civ_obj.getcognome_civ());
			ps_upd_cross_2.setString(index++, tmp_partita_iva);
			ps_upd_cross_2.setString(index++, anag_civ_obj.gettipoAnagrafica_civ());
			//							ps_upd_cross_2.setNull(index++, Types.VARCHAR);
			//							ps_upd_cross_2.setNull(index++, Types.DATE);
			ps_upd_cross_2.setString(index++, anag_civ_obj.get_sesso());
			ps_upd_cross_2.executeUpdate();

			try (ResultSet rs_upd_cross_2 = ps_upd_cross_2.getGeneratedKeys()) {									
				if (rs_upd_cross_2.next()) {
					id_anagrafica = rs_upd_cross_2.getInt(1);
				}
				rs_upd_cross_2.close();								    

			} catch(Exception e) {
				e.printStackTrace();
				LogClass.trace("ERRORE IN RUN  msgerrore:" + e.getMessage());
			}
			ps_upd_cross_2.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
			LogClass.trace("ERRORE IN RUN  msgerrore:" + e1.getMessage());
		}
		return id_anagrafica;
	}
	/*
	 * Inserisce le anagrafiche presenti in Civilia e non presenti in cross con il ruolo + etichetta
	 * 
	 */
	private static int genera_codice_fiscale_cross(java.sql.Connection connection, myUtility utl, String cognome, String nome, String codice_fiscale, String partita_iva)
	{
		int id_anagrafica = 0;

		String query = "INSERT INTO @.`anagrafica_senza_codice_fiscale`" + 
				"(`cognome`,`nome`, `codice_fiscale`,`partita_iva`) VALUE (?,?,?,?)";		
		query = query.replace("@", utl.getValueProperties("schema_civilia"));

		LogClass.trace("genera_codice_fiscale_cross query:"+ query + " cognome:" + cognome + " nome:" + 
				nome + " codice_fiscale:" + codice_fiscale + " partita_iva:" + partita_iva);

		PreparedStatement ps;
		try {
			ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);


			int index = 1;
			ps.setString(index++, cognome);
			ps.setString(index++, nome);
			ps.setString(index++, codice_fiscale);
			ps.setString(index++, partita_iva);

			ps.executeUpdate();

			try (ResultSet rs = ps.getGeneratedKeys()) {
				if (rs.next()) {
					id_anagrafica = rs.getInt(1);
				}
			}

			ps.close();

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return id_anagrafica;
	}
	/*
	 * Elimina le anagrafiche cross che non sono presenti in Civilia e non hanno un evento di "Collegamento nuova anagrafica"
	 * 
	 */
	private static void elimina_anagrafiche_cross(int id_pratica, int id_anagrafica, Dao dao, myUtility utl, int thread_id)
	{
		String queryDel_1 = "DELETE FROM @.`pratica_anagrafica`" 
				+ " WHERE `id_pratica` = " + id_pratica + " AND `id_anagrafica` = " + id_anagrafica;
		queryDel_1 = queryDel_1.replace("@", utl.getValueProperties("schema_opencross"));

		String queryDel_2 = " DELETE FROM @.`anagrafica` WHERE `id_anagrafica` = " + id_anagrafica;
		queryDel_2 = queryDel_2.replace("@", utl.getValueProperties("schema_opencross"));

		String queryInsertDelete = "INSERT INTO @.`anagrafica_cross_delete` (`pratica_id`, `anagrafica_id_cross`, `data`)"
				+ " VALUES (?,?, now())";
		//				+ "VALUES (" +  id_pratica + "," + id_anagrafica + ", now())";
		queryInsertDelete = queryInsertDelete.replace("@", utl.getValueProperties("schema_civilia"));
		queryInsertDelete = myUtility.setString(queryInsertDelete, "?", String.valueOf(id_pratica));
		queryInsertDelete = myUtility.setString(queryInsertDelete, "?", String.valueOf(id_anagrafica));

		LogClass.trace("elimina_anagrafiche_cross queryInsertDelete:" + queryInsertDelete);
		try {
			LogClass.trace("elimina_anagrafiche_cross queryDel_1:" + queryDel_1);
			dao.executeUpdate(queryDel_1,thread_id);

			LogClass.trace("elimina_anagrafiche_cross queryDel_2:" + queryDel_2);
			// dao.executeUpdate(queryDel_2);

			LogClass.trace("elimina_anagrafiche_cross queryInsertDelete:" + queryInsertDelete);
			dao.executeUpdate(queryInsertDelete,thread_id);

		} catch (Exception e) {
			e.printStackTrace();
			LogClass.trace("ERRORE IN elimina_anagrafiche_cross msgerrore:" + e.getMessage());
		}		
	}

	public static void main(String[] args) {
		myUtility utl = new myUtility();

		String query = Query.select_ruolo_etichetta_non_applicabile.replace("@", utl.getValueProperties("schema_opencross"));
		query = myUtility.setString(query, "?", String.valueOf(1));
		query = myUtility.setString(query, "?", String.valueOf(2));
		System.out.println("query:" + query);

		int num_thread_elab = Integer.valueOf(utl.getValueProperties("max.thread.elab")).intValue();
		BonificaAnagraficaApplication elaboraBonifica = new BonificaAnagraficaApplication();

		
		int LAST_STEP_EXECUTED=1;
		Dao dao_civilia_bonifica = new Dao();dao_civilia_bonifica.openConnection(URL_DB_CIVILIA_BONIFICA); 

		try {
				// Estrazione di tutte le pratiche Civilia 
/*
			query = Query.delete_ruoli_cross.replace("@", utl.getValueProperties("schema_civilia"));
			dao_civilia_bonifica.executeQuery(query);
			LAST_STEP_EXECUTED=200;				

			query = Query.delete_anagrafica_cross_insert.replace("@", utl.getValueProperties("schema_civilia"));
			dao_civilia_bonifica.executeQuery(query);
			LAST_STEP_EXECUTED=201;			

			query = Query.delete_anagrafica_cross.replace("@", utl.getValueProperties("schema_civilia"));
			dao_civilia_bonifica.executeQuery(query);
			LAST_STEP_EXECUTED=202;
*/			
			elaboraBonifica.setNum_thread_elab(num_thread_elab);
			
			elaboraBonifica.elaboraMultithread();

		} catch (Exception e) {
			e.printStackTrace();
			LogClass.trace("ERRORE LAST_STEP_EXECUTED:" + LAST_STEP_EXECUTED + " msgerrore:" + e.getMessage());
		}
	}	
}
