package it.wego.cross.mailservices.scheduler;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


class JavaRunnableInParallel {
	private static final Logger logger = LoggerFactory.getLogger(JavaRunnableInParallel.class.getName());

	
	public JavaRunnableInParallel(int threadid){

		try {
			Thread t = new Thread(new RunJavaRunnableInParallel(threadid));
			
			t.start();
		}
		catch (Exception e) {
			logger.error("GlobusMonitor encountered exception. reason : " + e); 
		}
	}

	private class RunJavaRunnableInParallel implements Runnable{

		public RunJavaRunnableInParallel(int threadid){

			String[] command = {"C:/Program Files (x86)/Java/jre1.8.0_361/bin/java.exe",
					" -jar C:/SUESUAP/WS_BO/job/target/BonificaAnagrafica-1.0.jar -t \"bonifica\" -g \"" +  
					threadid 
			};
			ProcessBuilder pb = new ProcessBuilder(command);

			pb.redirectOutput(new File("C:/tmp/job/job.log"));
			//Here in above file you will get the feed back from C++ application
			String result;
			String overall="";
			try {
				Process p = pb.start();
				p.waitFor();
				BufferedReader br = new BufferedReader( new InputStreamReader(p.getInputStream()));
				while ((result = br.readLine()) != null){
					overall = overall + "\n" + result;
				}
				//p.destroy();
				System.out.println(result);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}
	}


	public static void main(String[] args) {
		JavaRunnableInParallel elabora = new JavaRunnableInParallel(2);
	}

}
