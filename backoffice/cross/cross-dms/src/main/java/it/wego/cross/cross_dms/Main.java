package it.wego.cross.cross_dms;

import java.io.IOException;
import java.net.ServerSocket;

import it.wego.cross.cross_dms.utilities.LogClass;
import it.wego.cross.cross_dms.utilities.myUtility;


public class Main {

	private static ServerSocket SERVER_SOCKET;

	//public static BlockingQueue<EmailDaInviare> queue = new LinkedBlockingQueue<>(10000);

	private static LogClass logger;
	
	public static void main(String[] args) {

		String result = "OK";		    	    	      

		myUtility util = new myUtility();

		logger.trace("ENTRY GETALLPRATICHEDMS IMPORT SCHEDULER::Main");

		logger.trace("PARAMETRI:");
		//Dao dao = null;

		/* Controllo spostato nella shell di start */ 
		int portProcess = Integer.parseInt(util.getValueProperties("SocketPort"));			
		
		try {			

			// SERVER_SOCKET = new ServerSocket(portProcess);

			Thread process1 = new Thread(new ElabThread(1, args[0]));	
			Thread process2 = new Thread(new ElabThread(2, args[0]));	
			Thread process3 = new Thread(new ElabThread(3, args[0]));	
			Thread process4 = new Thread(new ElabThread(4, args[0]));	

			logger.trace("GETALLPRATICHEDMS IMPORT START THREAD 1");
			process1.start();

			logger.trace("GETALLPRATICHEDMS IMPORT START THREAD 2");
			process2.start();

			logger.trace("GETALLPRATICHEDMS IMPORT START THREAD 3");
			process3.start();

			logger.trace("GETALLPRATICHEDMS IMPORT START THREAD 4");
			process4.start();

			
			System.out.println("GETALLPRATICHEDMS IMPORT: wait for threads to end");

			process1.join();
			process2.join();
			process3.join();
			process4.join();

			System.out.println("GETALLPRATICHEDMS IMPORT: End all thread");


		} catch (InterruptedException e) {
			e.printStackTrace();
			System.out.println("GETALLPRATICHEDMS IMPOR: InterruptedException");

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("GETALLPRATICHEDMS IMPORT Thread main finished");
	}

}
