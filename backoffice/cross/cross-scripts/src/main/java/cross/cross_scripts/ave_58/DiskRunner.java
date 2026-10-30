package cross.cross_scripts.ave_58;

import cross.cross_scripts.BaseHandler;
import cross.cross_scripts.CrossJpa;
import cross.cross_scripts.MySql;

public class DiskRunner extends BaseHandler{

	protected DiskRunner(MySql mysql, CrossJpa jpa) {
		super(mysql, jpa);
		// TODO Auto-generated constructor stub
	}

	public void run() throws Exception {
		System.out.println("20B) AttachmentDimensionFixer");
		Thread.sleep(10000);
		(new AttachmentDimensionFixer(jpa)).run();
		
		System.out.println("20C) DiskPathHandler");
		Thread.sleep(10000);
		(new DiskPathHandler(mysql)).run();

		System.out.println("20D) DiskPathFixer");
		Thread.sleep(10000);
		(new DiskPathFixer(mysql)).run();
	}
}
