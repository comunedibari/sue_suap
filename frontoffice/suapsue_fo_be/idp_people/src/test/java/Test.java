/**
 * Copyright (c) 2011, Regione Emilia-Romagna, Italy
 *  
 * Licensed under the EUPL, Version 1.1 or - as soon they
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the
 * Licence.
 * 
 * For convenience a plain text copy of the English version
 * of the Licence can be found in the file LICENCE.txt in
 * the top-level directory of this software distribution.
 * 
 * You may obtain a copy of the Licence in any of 22 European
 * Languages at:
 * 
 * http://joinup.ec.europa.eu/software/page/eupl
 * 
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * 
 * This product includes software developed by Yale University
 * 
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
**/
import it.people.sirac.idp.authentication.AuthenticationClientAdapter;
import it.people.sirac.idp.beans.ResAuthBean;

/**
 * 
 */

/**
 * @author Riccardo Forafò - Engineering Ingegneria Informatica - Genova
 * 11/dic/2011 15:26:15
 */
public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		  AuthenticationClientAdapter authWS = null;
		  try {
			authWS = new AuthenticationClientAdapter("http://people.rer.ri:8080/ws_regauth/services/AuthenticationInterface");
			ResAuthBean resAuthBean = authWS.executeBasicAuthentication("frfrcr71p26a326o", "a");
			System.out.println(resAuthBean.getErrorCode());
			System.out.println(resAuthBean.getEsito());
			System.out.println(resAuthBean.getMessaggio());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
