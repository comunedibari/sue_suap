package cross.cross_scripts.ave_151.exporter;

import cross.cross_scripts.BaseHandler;
import cross.cross_scripts.CrossJpa;
import cross.cross_scripts.MySql;
import cross.cross_scripts.ave_151.check.Ave151Checker;

public class Ave151ExporterRunner  extends BaseHandler{

	public Ave151ExporterRunner(MySql mysql, CrossJpa jpa) {
		super(mysql, jpa);
	}

	@Override
	public void run() throws Exception {
		System.out.println("Ave151Runner exporter");
		Thread.sleep(10000);
		(new Ave151Exporter(mysql, jpa)).run();
		Thread.sleep(10000);
		(new Ave151Checker(mysql, jpa, Ave151Exporter.FILE_IN_ERROR)).run();
	}

}
