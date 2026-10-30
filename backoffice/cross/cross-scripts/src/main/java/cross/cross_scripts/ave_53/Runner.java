package cross.cross_scripts.ave_53;

import cross.cross_scripts.BaseHandler;
import cross.cross_scripts.CrossJpa;
import cross.cross_scripts.MySql;
import cross.cross_scripts.ave_53.owner.TechnicianAgentOwnerExtractor;

public class Runner extends BaseHandler{

	public Runner(MySql mysql, CrossJpa jpa) {
		super(mysql, jpa);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() throws Exception {
//		System.out.println("18) Technical agent validation from xml");
//		Thread.sleep(10000);
//		(new TechnicalAgentValidator(mysql)).run();

		System.out.println("19c) Technician agent owner extractor on table");
		Thread.sleep(10000);
		(new TechnicianAgentOwnerExtractor(mysql)).run();

//		System.out.println("19d) Technician agent report");
//		Thread.sleep(10000);
//		(new TechnicianAgentReport(mysql)).run();

		System.out.println("19e) Technical agent populator");
		Thread.sleep(10000);
		(new TechnicianAgentPopulator(mysql)).run();
	}

}
