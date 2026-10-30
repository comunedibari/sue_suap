package it.wego.cross.cross_dms;


import it.wego.cross.cross_dms.config.ConfigException;
import it.wego.cross.cross_dms.config.ConfiguratoreUtility;
import it.wego.cross.cross_dms.sql.PoolConnectionDB;
import it.wego.cross.cross_dms.utilities.LogClass;

class ElabThread implements Runnable {	
	private Thread t;
	   
	private String threadName;
	
	private static LogClass logger;

	private int processo;

	private String tipo_elaborazione;

	public ElabThread(int paramElab, String tipo_elaborazione) {
		this.setProcesso(paramElab);		
		this.setTipo_elaborazione(tipo_elaborazione);		
	}

	/**
	 * @return the processo
	 */
	public int getProcesso() {
		return processo;
	}

	/**
	 * @param processo the processo to set
	 */
	public void setProcesso(int processo) {
		this.processo = processo;
	}

	/**
	 * @return the tipo_elaborazione
	 */
	public String getTipo_elaborazione() {
		return tipo_elaborazione;
	}

	/**
	 * @param tipo_elaborazione the tipo_elaborazione to set
	 */
	public void setTipo_elaborazione(String tipo_elaborazione) {
		this.tipo_elaborazione = tipo_elaborazione;
	}
	
	public void run() {
		logger.trace("[" + Thread.currentThread().getName() + "] [data=" + this.processo + "]" );

		logger.trace("RUNNING THREAD:" + this.processo);

		try {
			Thread.sleep(200);

			ConfiguratoreUtility cnf = new ConfiguratoreUtility();

			//JavaRunnableInParallel cp = new JavaRunnableInParallel(this.processo);
			
			App app = new App();
			
			if(this.getTipo_elaborazione().equals(App.GETALLPRATICHEDMS)) {
				app.get_all_pratiche_dms(this.getProcesso());
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
			logger.trace("EXCEPTION InterruptedException" + e.getMessage());
		} catch (ConfigException e) {
			e.printStackTrace();
			logger.trace("EXCEPTION ConfigException" + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.trace("EXCEPTION Exception" + e.getMessage());
		}
		logger.trace("END THREAD:" + this.processo);
	}
	public static void main(String[] args) {

	}
}

