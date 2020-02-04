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
package it.wego.dynamicodt.transformation.html;

import freemarker.template.*;
import java.util.*;
import java.io.*;

public class MyFreemarker {

    public static void main(String[] args) throws Exception {

        /* ------------------------------------------------------------------- */
        /* You usually do it only once in the whole application life-cycle:    */

        /* Create and adjust the configuration */
        Configuration cfg = new Configuration();
        cfg.setDirectoryForTemplateLoading(
                new File("./"));
        cfg.setObjectWrapper(new DefaultObjectWrapper());

        /* ------------------------------------------------------------------- */
        /* You usually do these for many times in the application life-cycle:  */

        /* Get or create a template */
        Template temp = cfg.getTemplate("testH.ftl");

        /* Create a data-model */
        Map root = new HashMap();
        root.put("name", "my name");
        root.put("styleName", "my styleName");
        root.put("numberColumnsRepeated", "4");
        Map rows = new HashMap();
        root.put("table", rows);
        SimpleSequence row = new SimpleSequence();
        rows.put("tr", row);

        Map cols = new HashMap();
        SimpleSequence col = new SimpleSequence();
        cols.put("td", col);
        col.add("0.0");
        col.add("0.1");
        col.add("0.2");
        col.add("0.3");
        row.add(cols);

        cols = new HashMap();
        col = new SimpleSequence();
        cols.put("td", col);
        col.add("1.0");
        col.add("1.1");
        col.add("1.2");
        col.add("1.3");
        row.add(cols);

        /* Merge data-model with template */
        Writer out = new OutputStreamWriter(System.out);
        temp.process(root, out);
        out.flush();
    }
}
