package cross.cross_scripts.ave_115;

import cross.cross_scripts.BaseHandler;
import cross.cross_scripts.CrossJpa;
import cross.cross_scripts.MySql;

public class AVE115Runner extends BaseHandler{

	public AVE115Runner(MySql mysql, CrossJpa jpa) {
		super(mysql, jpa);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() throws Exception {
		System.out.println("26) Deleter");
		Thread.sleep(10000);
		(new Deleter(mysql)).run();

		
	}

}
