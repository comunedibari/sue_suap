package cross.cross_scripts.ave_74;

import cross.cross_scripts.BaseHandler;
import cross.cross_scripts.CrossJpa;
import cross.cross_scripts.MySql;

public class RuvoDiPugliaRunner  extends BaseHandler{

	public RuvoDiPugliaRunner(MySql mysql, CrossJpa jpa) {
		super(mysql, jpa);
	}

	@Override
	public void run() throws Exception {
		System.out.println("Ruvo Di puglia exporter");
		Thread.sleep(10000);
		(new RuvoDiPugliaExporter(mysql, jpa)).run();
		System.out.println("Ruvo Di puglia copier");
		Thread.sleep(10000);
		(new RuvoDiPugliaAttachmentCopierBat(mysql, jpa)).run();
	}

}
