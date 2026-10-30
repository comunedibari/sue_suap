package cross.cross_scripts.ave_90;

import cross.cross_scripts.BaseHandler;
import cross.cross_scripts.CrossJpa;
import cross.cross_scripts.MySql;

public class SuapEndoProcedimentiIdRunner  extends BaseHandler{

	public SuapEndoProcedimentiIdRunner(MySql mysql, CrossJpa jpa) {
		super(mysql, jpa);
	}

	@Override
	public void run() throws Exception {
		System.out.println(SuapEndoProcedimentiIdUpdater.class.getName());
		Thread.sleep(10000);
		(new SuapEndoProcedimentiIdUpdater(mysql, jpa)).run();
	}

}
