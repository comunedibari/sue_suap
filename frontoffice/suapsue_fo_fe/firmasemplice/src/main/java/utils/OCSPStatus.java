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
package utils;

public final class OCSPStatus {

    public static final OCSPStatus GOOD = new OCSPStatus(0);
    public static final OCSPStatus UNKNOWN = new OCSPStatus(1);
    public static final OCSPStatus REVOKED = new OCSPStatus(2);
    public static final OCSPStatus EXPIRED = new OCSPStatus(3);
    public static final OCSPStatus NOT_YET_VALID = new OCSPStatus(4);


    private final int status;

    private OCSPStatus(int status) {
        this.status = status;
    }

    public String toString() {
        String returnString = null;
        switch (status) {
            case 0:
                returnString = "GOOD";
                break;
            case 1:
                returnString = "UNKNOWN";
                break;
            case 2:
                returnString = "REVOKED";
                break;
            case 3:
                returnString = "EXPIRED";
                break;
            case 4:
                returnString = "NOT_YET_VALID";
        }
        return returnString;
    }
}
