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
package it.people.util.table;

/**
 * @author Andrea Piemontese - Engineering Ingegneria Informatica 06/06/2012
 */
public class TableValueCachedMapKey {

    private String tableId = null;

    private String process = null;

    private String nodeId = null;

    /**
     * @parma tableId
     * @param process
     * @param nodeId
     */
    public TableValueCachedMapKey(String tableId, String process, String nodeId) {
	this.setTableId(tableId);
	this.setProcess(process);
	this.setNodeId(nodeId);
    }

    /**
     * @return the tableId
     */
    public String getTableId() {
	return tableId;
    }

    /**
     * @param tableId
     *            the tableId to set
     */
    public void setTableId(String tableId) {
	this.tableId = tableId;
    }

    /**
     * @return the process
     */
    public String getProcess() {
	return process;
    }

    /**
     * @param process
     *            the process to set
     */
    public void setProcess(String process) {
	this.process = process;
    }

    /**
     * @return the nodeId
     */
    public String getNodeId() {
	return nodeId;
    }

    /**
     * @param nodeId
     *            the nodeId to set
     */
    public void setNodeId(String nodeId) {
	this.nodeId = nodeId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((nodeId == null) ? 0 : nodeId.hashCode());
	result = prime * result + ((process == null) ? 0 : process.hashCode());
	result = prime * result + ((tableId == null) ? 0 : tableId.hashCode());
	return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	TableValueCachedMapKey other = (TableValueCachedMapKey) obj;
	if (nodeId == null) {
	    if (other.nodeId != null)
		return false;
	} else if (!nodeId.equals(other.nodeId))
	    return false;
	if (process == null) {
	    if (other.process != null)
		return false;
	} else if (!process.equals(other.process))
	    return false;
	if (tableId == null) {
	    if (other.tableId != null)
		return false;
	} else if (!tableId.equals(other.tableId))
	    return false;
	return true;
    }

}
