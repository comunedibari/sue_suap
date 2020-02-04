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
/*
 * Created on 12-feb-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package it.people.process;

/**
 * @author fabmi
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class SummaryState implements java.io.Serializable {

    protected int code;
    protected String label;

    public SummaryState() {
    };

    protected SummaryState(int code, String label) {
	this.code = code;
	this.label = label;
    }

    public int getCode() {
	return this.code;
    }

    public void setCode(int code) {
	this.code = code;
	if (this.equals(SummaryState.ALWAYS))
	    this.label = SummaryState.ALWAYS.getLabel();
	else if (this.equals(SummaryState.FINALLY))
	    this.label = SummaryState.FINALLY.getLabel();
	else if (this.equals(SummaryState.NONE))
	    this.label = SummaryState.NONE.getLabel();
    }

    public String getLabel() {
	return this.label;
    }

    public boolean equals(SummaryState other) {
	return this.getCode() == other.getCode();
    }

    public String toString() {
	return this.label;
    }

    public static SummaryState NONE = new SummaryState(0, "none");
    public static SummaryState ALWAYS = new SummaryState(1, "always");
    public static SummaryState FINALLY = new SummaryState(2, "finally");
}
