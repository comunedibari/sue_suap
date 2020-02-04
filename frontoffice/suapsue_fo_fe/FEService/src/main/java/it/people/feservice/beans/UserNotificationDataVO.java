package it.people.feservice.beans;

import it.people.feservice.beans.interfaces.IpagedArrayResult;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author gguidi - Jun 19, 2013
 *
 */
public class UserNotificationDataVO implements Serializable, IpagedArrayResult {

	private static final long serialVersionUID = 6596997652860877857L;
	
	private int totalResultCount = 0;
	
	UserNotificationBean[] userNotifications;
	
	public UserNotificationDataVO() {}

	/**
	 * @param userNotifications
	 */
	public UserNotificationDataVO(UserNotificationBean[] userNotifications, int totalResultCount) {
		super();
		this.userNotifications = userNotifications;
		this.totalResultCount = totalResultCount;
	}

	public UserNotificationBean[] getUserNotifications() {
		return userNotifications;
	}

	public void setUserNotifications(UserNotificationBean[] userNotifications) {
		this.userNotifications = userNotifications;
	}

	public void setTotalResultCount(int totalResultCount) {
		this.totalResultCount = totalResultCount;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + totalResultCount;
		result = prime * result + Arrays.hashCode(userNotifications);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserNotificationDataVO other = (UserNotificationDataVO) obj;
		if (totalResultCount != other.totalResultCount)
			return false;
		if (!Arrays.equals(userNotifications, other.userNotifications))
			return false;
		return true;
	}




	// Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description
    		.TypeDesc(UserNotificationDataVO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "UserNotificationDataVO"));
        
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();

        elemField.setFieldName("userNotifications");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "userNotifications"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "UserNotificationBean"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://feservice.people.it/", "item"));
        typeDesc.addFieldDesc(elemField);

    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

	/* (non-Javadoc)
	 * @see it.people.feservice.beans.interfaces.IpagedArrayResult#getPartialResult()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T[] getPartialResult() {
		return (T[]) getUserNotifications();
	}

	/* (non-Javadoc)
	 * @see it.people.feservice.beans.interfaces.IpagedArrayResult#getTotalResultCount()
	 */
	@Override
	public int getTotalResultCount() {
		return totalResultCount;
	}
	
	

}
