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
package it.people.console.java.util;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import it.people.console.persistence.beans.support.ILazyPagedListHolder;
import it.people.console.system.AbstractLogger;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 16/gen/2011 11.48.09
 *
 */
public class PagedListHoldersTreeMap extends AbstractLogger implements Serializable {

	private static final long serialVersionUID = 677854282288450900L;
	private SortedMap<PagedListHolderKey, ILazyPagedListHolder> map;

	public PagedListHoldersTreeMap() {
		this.setMap(Collections.synchronizedSortedMap(new TreeMap<PagedListHolderKey, ILazyPagedListHolder>(new TreeMapPartialKeyComparator())));
	}

	/**
	 * @return the map
	 */
	private SortedMap<PagedListHolderKey, ILazyPagedListHolder> getMap() {
		return map;
	}

	/**
	 * @param map the map to set
	 */
	private void setMap(
			SortedMap<PagedListHolderKey, ILazyPagedListHolder> map) {
		this.map = map;
	}
	
	public synchronized void put(final ILazyPagedListHolder pagedListHolder) {
		
		PagedListHolderKey holderKey = new PagedListHolderKey(new Timestamp(GregorianCalendar.getInstance().getTimeInMillis()), pagedListHolder.getPagedListId());
		holderKey.setOrdinal(this.getMap().size() + 1);

		if (logger.isDebugEnabled()) {
			logger.debug("Putting holder '" + pagedListHolder.getPagedListId() + "'.");
			dumpAllExistingsKeys();
			logger.debug("New key: " + holderKey);
		}

		this.getMap().put(holderKey, pagedListHolder);

	}

	public synchronized void update(final ILazyPagedListHolder pagedListHolder, 
			PagedListHolderKey holderKey) {
		
		if (logger.isDebugEnabled()) {
			logger.debug("Putting holder '" + pagedListHolder.getPagedListId() + "'.");
			dumpAllExistingsKeys();
			logger.debug("New key: " + holderKey);
		}

		this.getMap().put(holderKey, pagedListHolder);

	}
	
	public synchronized PagedListHolderKey remove(final ILazyPagedListHolder pagedListHolder) {
		
		PagedListHolderKey holderKey = this.getKey(pagedListHolder.getPagedListId());
		if (holderKey != null) {
			this.getMap().remove(holderKey);
		}
		return holderKey;
		
	}
	
	private void dumpAllExistingsKeys() {
		
		if (this.getMap().size() > 0) {
			Set<PagedListHolderKey> keys = this.getMap().keySet();
			Iterator<PagedListHolderKey> keysIterator = keys.iterator();
			while(keysIterator.hasNext()) {
				PagedListHolderKey key = keysIterator.next();
				logger.debug("Holder key: " + key);
			}
		} else {
			logger.debug("Map is empty.");
		}
		
	}
	
	public ILazyPagedListHolder get(String key) {
		
		if (logger.isDebugEnabled()) {
			logger.debug("Getting for pagedListHolder with id = " + key);
		}
		
		ILazyPagedListHolder result = null;
		Set<PagedListHolderKey> mapKeys = this.getMap().keySet();
		
		for(PagedListHolderKey mapKey : mapKeys) {
			if (mapKey.getPagedListId().equalsIgnoreCase(key)) {
				if (logger.isDebugEnabled()) {
					logger.debug("Found pagedListHolder with key = " + mapKey);
				}
				result = this.getMap().get(mapKey);
				break;
			}
		}
		if (logger.isDebugEnabled() && result == null) {
			logger.debug("No pagedListHolder found with id = " + key);
		}
		
		return result;
		
	}

	
	public PagedListHolderKey getKey(String key) {
		
		if (logger.isDebugEnabled()) {
			logger.debug("Getting for pagedListHolder with id = " + key);
		}
		
		PagedListHolderKey result = null;
		Set<PagedListHolderKey> mapKeys = this.getMap().keySet();
		
		for(PagedListHolderKey mapKey : mapKeys) {
			if (mapKey.getPagedListId().equalsIgnoreCase(key)) {
				if (logger.isDebugEnabled()) {
					logger.debug("Found pagedListHolder with key = " + mapKey);
				}
				result = mapKey;
				break;
			}
		}
		if (logger.isDebugEnabled() && result == null) {
			logger.debug("No pagedListHolder key found with id = " + key);
		}
		
		return result;
		
	}
	
	public boolean containsKey(String key) {
		
		if (logger.isDebugEnabled()) {
			logger.debug("Searching for pagedListHolder with id = " + key);
		}
		
		boolean result = false;
		Set<PagedListHolderKey> mapKeys = this.getMap().keySet();
		
		for(PagedListHolderKey mapKey : mapKeys) {
			if (mapKey.getPagedListId().equalsIgnoreCase(key)) {
				if (logger.isDebugEnabled()) {
					logger.debug("Found pagedListHolder with key = " + mapKey);
				}
				result = true;
				break;
			}
		}
		if (logger.isDebugEnabled() && !result) {
			logger.debug("No pagedListHolder found with id = " + key);
		}
		
		return result;
	}
	
	public Collection<ILazyPagedListHolder> values() {
		return this.getMap().values();
	}
	
	public void clear() {
		this.getMap().clear();
	}
	
	public Comparator<?> comparator() {
		return this.getMap().comparator();
	}
	
	public int size() {
		return this.getMap().size();
	}
	
}
