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
package it.people.console.persistence.utils.sequences;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Iterator;
import java.util.Vector;

import org.apache.ojb.broker.PersistenceBroker;
import org.apache.ojb.broker.PersistenceBrokerException;
import org.apache.ojb.broker.accesslayer.JdbcAccess;
import org.apache.ojb.broker.accesslayer.ResultSetAndStatement;
import org.apache.ojb.broker.metadata.ClassDescriptor;
import org.apache.ojb.broker.metadata.FieldDescriptor;
import org.apache.ojb.broker.metadata.ObjectReferenceDescriptor;
import org.apache.ojb.broker.metadata.fieldaccess.PersistentField;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.util.sequence.SequenceManager;
import org.apache.ojb.broker.util.sequence.SequenceManagerException;
import org.apache.ojb.broker.core.proxy.ProxyHelper;

import it.people.console.system.AbstractLogger;

//Referenced classes of package org.apache.ojb.broker.util.sequence:
//         SequenceManagerException, SequenceManager

public class SequenceManagerMySQLImpl extends AbstractLogger 
        implements SequenceManager {
    
    protected PersistenceBroker broker;
    
    public SequenceManagerMySQLImpl(PersistenceBroker persistencebroker) {
        broker = persistencebroker;
    }
    
    private synchronized int getNextId(FieldDescriptor fielddescriptor)
    throws SequenceManagerException {
        return 0;
    }
    
    public int getUniqueId(FieldDescriptor fielddescriptor)
    throws SequenceManagerException {
        return getNextId(fielddescriptor);
    }
    
    public String getUniqueString(FieldDescriptor fielddescriptor)
    throws SequenceManagerException {
        return Integer.toString(getUniqueId(fielddescriptor));
    }
    
    public long getUniqueLong(FieldDescriptor fielddescriptor)
    throws SequenceManagerException {
        return (long)getUniqueId(fielddescriptor);
    }
    
    public Object getUniqueObject(FieldDescriptor fielddescriptor)
    throws SequenceManagerException {
        return null;
    }
    
    public void afterStore(JdbcAccess jdbcaccess, ClassDescriptor classdescriptor, Object obj)
    throws SequenceManagerException {
        FieldDescriptor[] fldArr = classdescriptor.getAutoIncrementFields();
        FieldDescriptor fielddescriptor = fldArr.length > 0 ? fldArr[0] : null;
        if(fielddescriptor != null) {
            ResultSetAndStatement resultsetandstatement = null;
            try {
                resultsetandstatement = jdbcaccess.executeSQL("SELECT LAST_INSERT_ID() as newid FROM " + classdescriptor.getFullTableName(), classdescriptor, Query.NOT_SCROLLABLE);
                int i = 0;
                if(resultsetandstatement.m_rs.next())
                    i = resultsetandstatement.m_rs.getInt("newid");
                resultsetandstatement.m_rs.close();
                if(logger.isDebugEnabled())
                    logger.debug("After store - newid=" + i);
                PersistentField persistentfield = fielddescriptor.getPersistentField();
                if (persistentfield.getType().getName().equalsIgnoreCase("java.lang.Long")){
                    persistentfield.set(obj, new Long(i));
                }else{
                    persistentfield.set(obj, new Integer(i));
                }
                
                
            } catch(PersistenceBrokerException persistencebrokerexception) {
                throw new SequenceManagerException(persistencebrokerexception);
            } catch(SQLException sqlexception) {
                throw new SequenceManagerException(sqlexception);
            } finally {
                resultsetandstatement.close();
            }
        }
    }
    
    public void setReferenceFKs(Object obj, ClassDescriptor classdescriptor)
    throws SequenceManagerException {
        Vector<?> vector = classdescriptor.getObjectReferenceDescriptors();
        for(Iterator<?> iterator = vector.iterator(); iterator.hasNext();) {
            ObjectReferenceDescriptor objectreferencedescriptor = (ObjectReferenceDescriptor)iterator.next();
            Object obj1 = objectreferencedescriptor.getPersistentField().get(obj);
            if(obj1 != null)
                assertFkAssignment(obj, classdescriptor, obj1, objectreferencedescriptor);
        }
        
    }
    
    private void assertFkAssignment(Object obj, ClassDescriptor classdescriptor, Object obj1, ObjectReferenceDescriptor objectreferencedescriptor) {
        Class<?> class1 = ProxyHelper.getRealClass(obj1);
        ClassDescriptor classdescriptor1 = broker.getDescriptorRepository().getDescriptorFor(class1);
        ClassDescriptor classdescriptor2 = classdescriptor;
        FieldDescriptor afielddescriptor[] = objectreferencedescriptor.getForeignKeyFieldDescriptors(classdescriptor2);
        if(afielddescriptor != null) {
            FieldDescriptor fielddescriptor = classdescriptor1.getAutoIncrementFields()[0];
            for(int i = 0; i < afielddescriptor.length; i++) {
                FieldDescriptor fielddescriptor1 = afielddescriptor[i];
                fielddescriptor1.getPersistentField().set(obj, fielddescriptor.getPersistentField().get(obj1));
            }
            
        }
    }
    
    /**
     * Returns a unique object for the given field attribute.
     * The returned value takes in account the jdbc-type
     * and the FieldConversion.sql2java() conversion defined for <code>field</code>.
     * The returned object is unique accross all tables in the extent
     * of class the field belongs to.
     */
    public Object getUniqueValue(FieldDescriptor field) throws SequenceManagerException
    {
    	Object result = null;
    	switch (field.getJdbcType().getType())
    	{
    		case Types.ARRAY:
    		{
    			Object[] arr = {getUniqueString(field)};
    			result = arr;
    			break;
    		}
    		case Types.BIGINT:
    		{
    			result = new Long(getUniqueLong(field));
    			break;
    		}
    		case Types.BINARY:
    		{
    			result = getUniqueString(field).getBytes();
    			break;
    		}
    		case Types.CHAR:
    		{
    			result = getUniqueString(field);
    			break;
    		}
    		case Types.DATE:
    		{
    			result = new Date(getUniqueLong(field));
    			break;
    		}
    		case Types.DECIMAL:
    		{
    			result = new BigDecimal(getUniqueLong(field));
    			break;
    		}
    		case Types.DOUBLE:
    		{
    			result = new Double(getUniqueLong(field));
    			break;
    		}
    		case Types.FLOAT:
    		{
    			result = new Double(getUniqueLong(field));
    			break;
    		}
    		case Types.INTEGER:
    		{
    			result = new Integer(getUniqueId(field));
    			break;
    		}
    		case Types.JAVA_OBJECT:
    		{
    			result = getUniqueObject(field);
    			break;
    		}
    		case Types.LONGVARBINARY:
    		{
    			result = getUniqueString(field).getBytes();
    			break;
    		}
    		case Types.LONGVARCHAR:
    		{
    			result = getUniqueString(field);
    			break;
    		}
    		case Types.NUMERIC:
    		{
    			result = new BigDecimal(getUniqueLong(field));
    			break;
    		}
    		case Types.REAL:
    		{
    			result = new Float(getUniqueLong(field));
    			break;
    		}
    		case Types.SMALLINT:
    		{
    			result = new Short((short)getUniqueId(field));
    			break;
    		}
    		case Types.TIME:
    		{
    			result = new Time(getUniqueLong(field));
    			break;
    		}
    		case Types.TIMESTAMP:
    		{
    			result = new Timestamp(getUniqueLong(field));
    			break;
    		}
    		case Types.TINYINT:
    		{
    			result = new Byte((byte)getUniqueId(field));
    			break;
    		}
    		case Types.VARBINARY:
    		{
    			result = getUniqueString(field).getBytes();
    			break;
    		}
    		case Types.VARCHAR:
    		{
    			result = getUniqueString(field);
    			break;
    		}
    		default:
    		{
    			result = getUniqueString(field);
    			break;
    		}
    	}
    	result = field.getFieldConversion().sqlToJava(result);
        return result;
    }
    
}
