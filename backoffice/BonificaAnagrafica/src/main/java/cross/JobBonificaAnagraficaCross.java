package cross;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import cross.BonificaAnagrafica.BonificaAnagraficaApplication;
import cross.utilities.myUtility;

import java.util.Date;


public class JobBonificaAnagraficaCross implements Job{ 

	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("Job Elabora bonifica anagrafica cross --->>> START! Time is " + new Date());

		String[] args = null;

		myUtility utl = new myUtility();
		BonificaAnagraficaApplication elaboraBonifica = new BonificaAnagraficaApplication();
		elaboraBonifica.elaboraMultithread();

		System.out.println("Elabora bonifica anagrafica cross ---<<< END! Time is " + new Date());		
	} 

}