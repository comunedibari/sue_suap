package cross.cross_scripts.ave_61;

import cross.cross_scripts.BaseHandler;
import cross.cross_scripts.CrossJpa;
import cross.cross_scripts.MySql;

public class ModugnoRunner  extends BaseHandler{

	public ModugnoRunner(MySql mysql, CrossJpa jpa) {
		super(mysql, jpa);
	}

	@Override
	public void run() throws Exception {
		System.out.println("22) Modugno exporter");
		Thread.sleep(10000);
		(new ModugnoExporter(mysql, jpa)).run();
		System.out.println("22) Modugno copier");
		Thread.sleep(10000);
		(new ModugnoAttachmentCopier(mysql, jpa)).run();
	}

}
