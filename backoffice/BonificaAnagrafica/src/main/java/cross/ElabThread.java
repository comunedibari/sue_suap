package cross;

import cross.BonificaAnagrafica.BonificaAnagraficaApplication;
import cross.utilities.LogClass;

public class ElabThread implements Runnable {	
	   	
	private int thread_id;


	public int getThread_id() {
		return thread_id;
	}

	public void setThread_id(int thread_id) {
		this.thread_id = thread_id;
	}

	public ElabThread(int paramElab) {
		this.setThread_id(paramElab);		
	}

	public void run() {
		LogClass.trace("[" + Thread.currentThread().getName() + "] [data=" + this.getThread_id() + "]" );

		LogClass.trace("RUNNING THREAD:" + this.getThread_id());

		try {
			Thread.sleep(200);
			
			BonificaAnagraficaApplication.run(this.getThread_id());			
			
		} catch (Exception e) {
			e.printStackTrace();
			LogClass.trace("EXCEPTION Exception" + e.getMessage());
		}
		LogClass.trace("END THREAD:" + this.getThread_id());
	}

	public static void main(String[] args) {

	}
}

