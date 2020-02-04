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
 * Created on 27-dic-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package it.people.process.common.entity;

import java.io.File;

/**
 * @author FabMi
 * 
 *         Rappresenta il riepilogo firmato, consente ai servizi di distingure
 *         tra gli allegati firmati e il riepilogo firmato.
 */
public class SignedSummaryAttachment extends SignedAttachment {

    private static final long serialVersionUID = -5162851167974184996L;

    public SignedSummaryAttachment() {
	super();
    }

    public SignedSummaryAttachment(String name, File file, String sign) {
	super(name, file, sign);
    }

    public SignedSummaryAttachment(String name, String path, String sign) {
	super(name, path, sign);
    }
}
