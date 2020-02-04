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

import java.util.HashMap;
import java.util.Map;

public class HTag {

    private int startOpenPos;
    private int startClosePos;
    private boolean isEmpty;
    private int endOpenPos;
    private int endClosePos;
    private String name;
    private String content;
    private Map attributes;

    public HTag() {
        attributes = new HashMap();
    }

    public void setStartOpenPos(int startOpenPos) {
        this.startOpenPos = startOpenPos;
    }

    public void setStartClosePos(int startClosePos) {
        this.startClosePos = startClosePos;
    }

    public void setIsEmpty(boolean isEmpty) {
        this.isEmpty = isEmpty;
    }

    public void setEndOpenPos(int endOpenPos) {
        this.endOpenPos = endOpenPos;
    }

    public void setEndClosePos(int endClosePos) {
        this.endClosePos = endClosePos;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setAttributes(Map attributes) {
        this.attributes = attributes;
    }

    public int getStartOpenPos() {
        return (this.startOpenPos);
    }

    public int getStartClosePos() {
        return (this.startClosePos);
    }

    public boolean getIsEmpty() {
        return (this.isEmpty);
    }

    public int getEndOpenPos() {
        return (this.endOpenPos);
    }

    public int getEndClosePos() {
        return (this.endClosePos);
    }

    public String getName() {
        return (this.name);
    }

    public String getContent() {
        return (this.content);
    }

    public Map getAttributes() {
        return (this.attributes);
    }

    public String toString() {

        String sep = System.getProperty(",");

        StringBuffer buffer = new StringBuffer("HTag");
        buffer.append("{");
        buffer.append("startOpenPos = ");
        buffer.append(startOpenPos);
        buffer.append(sep);
        buffer.append("startClosePos = ");
        buffer.append(startClosePos);
        buffer.append(sep);
        buffer.append("isEmpty = ");
        buffer.append(isEmpty);
        buffer.append(sep);
        buffer.append("endOpenPos = ");
        buffer.append(endOpenPos);
        buffer.append(sep);
        buffer.append("endClosePos = ");
        buffer.append(endClosePos);
        buffer.append(sep);
        buffer.append("name = ");
        buffer.append(name);
        buffer.append(sep);
        buffer.append("content = ");
        buffer.append(content);
        buffer.append(sep);
        buffer.append("attributes = ");
        buffer.append(attributes);
        buffer.append("}");

        return buffer.toString();
    }
}
