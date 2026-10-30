package cross;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import cross.utilities.LogClass;


class JavaRunnableInParallel {
	private static LogClass logger;
	
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
					" -jar C:/SUESUAP/WS_BO/BonificaAnagrafica/target/BonificaAnagrafica-1.0.jar -t \"bonifica\" -g \"" +  
					threadid 
			};
			ProcessBuilder pb = new ProcessBuilder(command);

			pb.redirectOutput(new File("C:/SUESUAP/CIVILIA/java/log/BonificaAnagrafica.log"));
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
		JavaRunnableInParallel bonifica = new JavaRunnableInParallel(2);
	}

}
