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
package it.people.core;

import it.people.City;
import it.people.core.persistence.PersistenceManager;
import it.people.core.persistence.PersistenceManagerFactory;
import it.people.core.persistence.exception.peopleException;

import java.util.Collection;

import org.apache.log4j.Category;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryFactory;

public class CommuneManager {
    public static final String DEFAULT_COMMUNE_KEY = "000000";
    private Category cat = Category.getInstance(CommuneManager.class.getName());

    private static CommuneManager ourInstance;

    /**
     * 
     * @return Restituisce l'istanza CommuneManager
     */
    public synchronized static CommuneManager getInstance() {
	if (ourInstance == null) {
	    ourInstance = new CommuneManager();
	}
	return ourInstance;
    }

    private CommuneManager() {
    }

    /**
     * 
     * @return Restituisce un oggetto City del comune di default
     * @throws peopleException
     */
    public City get() throws peopleException {
	// TODO: se presente un solo comune ritornare quello
	City city = new City();
	city.setOid(DEFAULT_COMMUNE_KEY);
	city.setLabel("Comune");
	city.setName("Comune");
	return city;
    }

    /**
     * Il metodo cerca prima in cache poi nella base dati.
     * 
     * @param communeKey
     *            Comune cercato
     * @return Restituisce un oggetto City del comune passato in ingresso
     * @throws peopleException
     */
    public City get(String communeKey) throws peopleException {
	if (communeKey == null || communeKey.length() == 0)
	    return get();

	return read(communeKey);
    }

    public void delete(String communeKey) {
	if (communeKey == null || "".equals(communeKey))
	    return;

	// Creazione query di ricerca
	Criteria criteria = new Criteria();

	// filtro sulla chiave
	criteria.addEqualTo("oid", communeKey);

	PersistenceManager persMgr = null;
	try {
	    // Carica il comune
	    persMgr = PersistenceManagerFactory.getInstance().get(City.class,
		    PersistenceManager.Mode.READ);

	    Collection cities = persMgr.get(QueryFactory.newQuery(City.class,
		    criteria, true));

	    // Rimuove il comune
	    PersistenceManager persistenceManagerWriter = PersistenceManagerFactory
		    .getInstance().get(City.class,
			    PersistenceManager.Mode.WRITE);

	    persistenceManagerWriter.delete(cities);

	    // non e' possibile utilizzare direttamente il deleteByQuery()
	    // perche' questo non tiene conto delle dipendenze e non
	    // pulisce la cache.

	} catch (peopleException ex) {
	    // l'interfaccia non consente il ritorno dell'eccezione
	    cat.error(ex);
	} finally {
	    if (persMgr != null) {
		persMgr.close();
	    }
	}
    }

    /**
     * Restituisce tutti i comune della base dati.
     * 
     * @return
     * @throws peopleException
     */
    public Collection getAll() throws peopleException {
	PersistenceManager persMgr = PersistenceManagerFactory.getInstance()
		.get(City.class, PersistenceManager.Mode.READ);
	Collection communes = null;

	try {
	    Criteria criteria = new Criteria();
	    criteria.addNotEqualTo("oid", DEFAULT_COMMUNE_KEY);
	    communes = persMgr.get(QueryFactory.newQuery(City.class, criteria,
		    true));
	} finally {
	    if (persMgr != null) {
		persMgr.close();
	    }
	}
	return communes;
    }

    /**
     * Salva l'oggetto city sulla base dati
     * 
     * @param city
     * @throws peopleException
     */
    public void set(City city) throws peopleException {
	if (city == null)
	    return;

	PersistenceManager persMgr = PersistenceManagerFactory.getInstance()
		.get(City.class, PersistenceManager.Mode.WRITE);

	try {
	    persMgr.definePrimaryKey(city);
	    persMgr.set(city);
	} catch (Exception ex) {
	    cat.error(ex);
	    throw new peopleException(ex.getMessage());
	} finally {
	    if (persMgr != null) {
		persMgr.close();
	    }
	}
    }

    private City read(String comuneKey) throws peopleException {
	PersistenceManager persMgr = PersistenceManagerFactory.getInstance()
		.get(City.class, PersistenceManager.Mode.READ);

	try {
	    Criteria crtr = new Criteria();
	    crtr.addEqualTo("oid", comuneKey);
	    Collection communes = persMgr.get(QueryFactory.newQuery(City.class,
		    crtr));

	    if (!communes.isEmpty())
		return (City) communes.iterator().next();
	} catch (Exception e) {
	    throw new peopleException(e.getMessage());
	} finally {
	    if (persMgr != null) {
		persMgr.close();
	    }
	}

	return null;
    }
}
