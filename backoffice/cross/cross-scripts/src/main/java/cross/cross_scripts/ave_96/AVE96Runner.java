package cross.cross_scripts.ave_96;

import cross.cross_scripts.BaseHandler;
import cross.cross_scripts.CrossJpa;
import cross.cross_scripts.MySql;

public class AVE96Runner  extends BaseHandler{

	public AVE96Runner(MySql mysql, CrossJpa jpa) {
		super(mysql, jpa);
	}

	@Override
	public void run() throws Exception {
		System.out.println("AVE-96 Runner");
		Thread.sleep(10000);
		(new SueDocumentLoader(mysql, jpa)).run();
	}

}
