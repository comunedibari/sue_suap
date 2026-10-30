package cross.cross_scripts.ave_151.check;

import cross.cross_scripts.BaseHandler;
import cross.cross_scripts.CrossJpa;
import cross.cross_scripts.MySql;

public class Ave151CheckerRunner  extends BaseHandler{

	public Ave151CheckerRunner(MySql mysql, CrossJpa jpa) {
		super(mysql, jpa);
	}

	@Override
	public void run() throws Exception {
		System.out.println("Ave151Runner checker");
		Thread.sleep(10000);
		(new Ave151Checker(mysql, jpa, "attachments_in_error")).run();
	}

}
