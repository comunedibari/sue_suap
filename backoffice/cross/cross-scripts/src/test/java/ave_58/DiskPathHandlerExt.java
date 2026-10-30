package ave_58;

import cross.cross_scripts.MySql;
import cross.cross_scripts.ave_58.DiskPathHandler;

public class DiskPathHandlerExt extends DiskPathHandler{
	public DiskPathHandlerExt(MySql mysql) {
		super(mysql);
		// TODO Auto-generated constructor stub
	}

	public String modifyBase(String base, String newFolder) {
		return super.modifyBase(base, newFolder);
	}
}
