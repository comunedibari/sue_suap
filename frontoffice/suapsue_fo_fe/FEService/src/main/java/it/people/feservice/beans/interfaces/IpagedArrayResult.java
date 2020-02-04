package it.people.feservice.beans.interfaces;

import java.io.Serializable;


/**
 * 
 * This interface can be implemented to handle 'partial' array results.
 * Useful to return paged results when you need to know also the total result count. 
 * 
 * @author Andrea Piemontese - Engineering Ingegneria Informatica - Bologna
 * 02/ago/2012
 * 
 */
public interface IpagedArrayResult {

	/**
	 * Get the array of 'partial' paged result
	 * @return Array of <T>
	 */
	<T> T[] getPartialResult();
	
	/**
	 * Get the total result number
	 * @return the total number of results
	 */
	int getTotalResultCount();
	
}
