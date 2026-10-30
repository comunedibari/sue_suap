package cross.cross_scripts.ave_103;

import cross.cross_scripts.BaseHandler;
import cross.cross_scripts.CrossJpa;
import cross.cross_scripts.MySql;

public class AVE103Runner  extends BaseHandler{

	public AVE103Runner(MySql mysql, CrossJpa jpa) {
		super(mysql, jpa);
	}

	@Override
	public void run() throws Exception {
		System.out.println("AVE-103 Runner");
		Thread.sleep(10000);
		(new PECMPTDocumentLoader(mysql, jpa)).run();
	}

}
