package dms;

import it.wego.cross.cross_dms.App;
import it.wego.cross.cross_dms.utilities.myUtility;

public class AllineaCrossDmsApplication {
	public AllineaCrossDmsApplication() {
	}

	public static void main(String[] args) {
		myUtility utl = new myUtility();
		String tipo_elab = utl.getValueProperties("startcmd");

//LOR ADD PRATICA CMD: java -jar AllineamentoCrossDms-1.0.jar -t "XXXXXXXXXXXX-018647-9883034"
		
		//LOR String[] argsElabs = new String[]{"-t get_list_pratiche_dms"};
		/* SOLO PER TEST */
		String[] argsElabs = args;
		App.main(argsElabs);
	}
}
