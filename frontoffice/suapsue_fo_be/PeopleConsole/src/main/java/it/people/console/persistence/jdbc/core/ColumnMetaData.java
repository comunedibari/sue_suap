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
package it.people.console.persistence.jdbc.core;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 15/gen/2011 12.06.19
 *
 */
public class ColumnMetaData {

	private String name;
	
	private String label;
	
	private int type;
	
	private String javaClassName;
	
	private int displaySize;
	
	private String typeName;
	
	private int precision;
	
	private int scale;
	
	public ColumnMetaData(final String name, final String label, final int type, 
			final String javaClassName, final int displaySize, final String typeName, 
			final int precision, final int scale) {
		this.setName(name);
		this.setLabel(label);
		this.setType(type);
		this.setJavaClassName(javaClassName);
		this.setDisplaySize(displaySize);
		this.setTypeName(typeName);
		this.setPrecision(precision);
		this.setScale(scale);
	}

	private void setName(String name) {
		this.name = name;
	}

	private void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @param type the type to set
	 */
	private void setType(int type) {
		this.type = type;
	}

	/**
	 * @param javaClassName the javaClassName to set
	 */
	private void setJavaClassName(String javaClassName) {
		this.javaClassName = javaClassName;
	}

	/**
	 * @param displaySize the displaySize to set
	 */
	private void setDisplaySize(int displaySize) {
		this.displaySize = displaySize;
	}

	/**
	 * @param typeName the typeName to set
	 */
	private void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	/**
	 * @param precision the precision to set
	 */
	private void setPrecision(int precision) {
		this.precision = precision;
	}

	/**
	 * @param scale the scale to set
	 */
	private void setScale(int scale) {
		this.scale = scale;
	}

	public final String getName() {
		return name;
	}

	public final String getLabel() {
		return label;
	}

	/**
	 * @return the type
	 */
	public final int getType() {
		return type;
	}

	/**
	 * @return the javaClassName
	 */
	public final String getJavaClassName() {
		return javaClassName;
	}

	/**
	 * @return the displaySize
	 */
	public final int getDisplaySize() {
		return displaySize;
	}

	/**
	 * @return the typeName
	 */
	public final String getTypeName() {
		return typeName;
	}

	/**
	 * @return the precision
	 */
	public final int getPrecision() {
		return precision;
	}

	/**
	 * @return the scale
	 */
	public final int getScale() {
		return scale;
	}
	
}
