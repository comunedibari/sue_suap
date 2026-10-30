package cross.cross_protocollo_ruvo;
import java.util.*;
import java.util.concurrent.*;

public class AsyncProtocollazione implements Runnable {
 
    private BlockingQueue<PraticheDaProtocollare> queue;
 
    public AsyncProtocollazione() {
    }

    public AsyncProtocollazione(BlockingQueue<PraticheDaProtocollare> queue) {
        this.queue = queue;
    }

    public void run() {
    	 
        try {
     
             while (true) {
     
            	 PraticheDaProtocollare p = queue.poll(5000, TimeUnit.MILLISECONDS);
     
                if (p != null) {
     
                	inviaPratiche(p);
                	
                	Thread.sleep(3000);
                } else {
                	String msgout = "PraticheDaProtocollare: gave up";
                	/*
                	if(Main.getTakeOnThreadPec() == 0) {                		
                		try {
							dao.logInfoDeg("TAKE OFF THREAD INVIO PEC");
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                		break;
                	}
                	*/
                }
             }
     
        } catch (InterruptedException ie) {
             ie.printStackTrace();
        }
    }
    private void inviaPratiche(PraticheDaProtocollare pec) {
        try {
            Thread.sleep(100);            		
    		
            
        } catch (InterruptedException ie) { ie.printStackTrace(); } catch (Exception e) {
			e.printStackTrace();
		}
 
        System.out.println("sendMailPec: processed " + pec);
    }

    public static void main(String[] args) {
		
	}
}