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
 * http://www.osor.eu/eupl
 * 
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
**/
package it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti;

import java.util.ArrayList;
import java.util.List;

public class PrecompilazioneBean {
	
	List input;
    List output;
    
    public PrecompilazioneBean()
    {
        input = new ArrayList();
        output = new ArrayList();
    }

    public List getInput() {
        return input;
    }

    public void setInput(List input) {
        this.input = input;
    }
    
    public void addInput(CampoPrecompilazioneBean bean) {
        try {
            input.add(bean);
        } catch (Exception e) {}
    }
    
    public List getOutput() {
        return output;
    }

    public void setOutput(List output) {
        this.output = output;
    }

    public void addOutput(CampoPrecompilazioneBean bean) {
        try {
            output.add(bean);
        } catch (Exception e) {}
    }
}
