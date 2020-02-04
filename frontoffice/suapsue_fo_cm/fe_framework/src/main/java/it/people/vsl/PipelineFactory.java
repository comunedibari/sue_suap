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
package it.people.vsl;

import it.people.db.fedb.Service;
import it.people.exceptions.PeopleDBException;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Category;

/**
 * 
 * User: acuffaro Date: 12-set-2003 Time: 12.26.01
 * 
 */
public class PipelineFactory {
    private Category cat = Category
	    .getInstance(PipelineFactory.class.getName());

    private static PipelineFactory ourInstance;
    private HashMap pipelines;

    public/* synchronized */static PipelineFactory getInstance() {
	if (ourInstance == null) {
	    ourInstance = new PipelineFactory();
	}
	return ourInstance;
    }

    /**
     * Costruttore. Effettua la registrazione sulle pipeline di tutti i processi
     * configurati nel db, invocando per ognuno il relativo metodo
     * 'registerToPipeline'.
     */
    private PipelineFactory() {
	// il caricamento delle pipeline � eseguito in modo dinamico
	if (cat.isDebugEnabled())
	    cat.debug(" PipelineFactory(): inizializzazione ");

	this.pipelines = new HashMap();

	/*
	 * //Recupero i processi. Collection processi = null; ResultSet rs =
	 * null; Statement stat = null; Connection conn = null;
	 * 
	 * try { conn = DBConnector.getInstance().connect(DBConnector.FEDB);
	 * 
	 * String query = "SELECT * FROM service";
	 * 
	 * stat = conn.createStatement(); rs = stat.executeQuery(query);
	 * 
	 * while (rs.next()) { String nomeProcesso = rs.getString("package");
	 * Class classe = Class.forName(rs.getString("process"));
	 * 
	 * Method metodo = classe.getMethod("getPipelineImpl", new Class[0]);
	 * //invoco il metodo Class pipelineImpl = (Class)
	 * metodo.invoke(classe,new Object[0]);
	 * 
	 * //debug if (cat.isDebugEnabled()) cat.debug(" invoke: " +
	 * classe.getName() + "." + metodo.getName());
	 * 
	 * metodo = classe.getMethod("getPipelineHandlers", new Class[0]);
	 * //invoco il metodo PipelineHandler[] PipelineHandlers =
	 * (PipelineHandler[]) metodo.invoke(classe,new Object[0]);
	 * 
	 * //debug if (cat.isDebugEnabled()) cat.debug(" invoke: " +
	 * classe.getName() + "." + metodo.getName());
	 * 
	 * if(PipelineHandlers != null && pipelineImpl != null){ //registro
	 * sulla pipeline il processo. registerPipeline(nomeProcesso,
	 * pipelineImpl, PipelineHandlers); }
	 * 
	 * } } catch (Exception ex) { cat.error(ex); } finally { try { if(rs !=
	 * null) rs.close(); } catch(SQLException e) {} try { if(stat != null)
	 * stat.close(); } catch(SQLException e) {} try { if (conn != null)
	 * conn.close(); } catch (SQLException e) {
	 * 
	 * } }
	 */
    }

    /**
     * Restituisce la pipeline dato il 'dataHolderKey'.
     * 
     * @param dataHolderKey
     * @return
     */
    public Pipeline getPipeline(String dataHolderKey) {
	if (dataHolderKey != null && dataHolderKey.length() > 0) {
	    Iterator iter = this.pipelines.values().iterator();
	    while (iter.hasNext()) {
		Pipeline pl = (Pipeline) iter.next();
		ArrayList queue = pl.getQueue();

		for (int i = 0; i < queue.size(); i++) {
		    PipelineDataHolder pdh = (PipelineDataHolder) queue.get(i);
		    if (dataHolderKey.equals(pdh.getKey())) {
			return pl;
		    }
		}
	    }
	}
	return null;
    }

    /**
     * Creazione della pipeline con sincronizzazione del thread per impedire
     * creazioni multiple della stessa pipeline.
     * 
     * @param communeId
     * @param processName
     * @return
     * @author Michele Fabbri Cedaf s.r.l.
     */
    protected synchronized Pipeline createPipeline(String communeId,
	    String processName) {
	// Verifica che nel frattempo la pipeline non sia gi� stata creata
	Pipeline pipeline = (Pipeline) this.pipelines.get(getPipelineMapKey(
		communeId, processName));
	if (pipeline != null)
	    return pipeline;

	// Carica la pipeline da db
	try {
	    if (cat.isDebugEnabled())
		cat.debug("getPipelineForName() - Istanziazione della pipeline");

	    Service service = Service.get(processName, communeId);

	    // Istanzia il generic process specifico per il servizio
	    Class classe = Class.forName(service.getProcessType());
	    Method metodo = classe.getMethod("getPipelineImpl", new Class[0]);

	    // Istanzia la pipeline specifica per il servizio
	    Class pipelineImpl = (Class) metodo.invoke(classe, new Object[0]);

	    if (pipelineImpl != null && cat.isDebugEnabled())
		cat.debug("getPipelineForName() - Pipeline per il processo "
			+ service.getProcessType() + " creata.");
	    else if (pipelineImpl == null)
		cat.info("getPipelineForName() - Il processo "
			+ service.getProcessType()
			+ " non ha ritornato nessuna pipeline.");

	    // Determina gli handler
	    metodo = classe.getMethod("getPipelineHandlers", new Class[0]);
	    PipelineHandler[] pipelineHandlers = (PipelineHandler[]) metodo
		    .invoke(classe, new Object[0]);

	    if (pipelineHandlers != null && cat.isDebugEnabled())
		cat.debug("getPipelineForName() - Handlers per il processo "
			+ service.getProcessType() + " creati");
	    else if (pipelineHandlers == null)
		cat.info("getPipelineForName() - Il processo "
			+ service.getProcessType()
			+ " non ha ritornato nessun handlers.");

	    if (pipelineHandlers != null && pipelineImpl != null) {
		// registro sulla pipeline il processo.
		pipeline = registerPipeline(communeId, processName,
			pipelineImpl, pipelineHandlers);
	    }

	} catch (PeopleDBException pdex) {
	    cat.error(
		    "getPipelineForName() - Errore nel caricamento della configurazione del servizio",
		    pdex);
	} catch (Exception ex) {
	    cat.error(
		    "getPipelineForName() - Errore nella creazioen della pipeline",
		    ex);
	}

	return pipeline;
    }

    /**
     * Restituisce la pipeline associata al processo.
     * 
     * @param communeId
     *            identificativo del comune es. 010025
     * @param processName
     *            nome del process es.
     *            it.people.fsl.servizi.esempi.tutorial.serviziotutorial1
     * @return
     * @author Michele Fabbri Cedaf s.r.l.
     */
    public Pipeline getPipelineForName(String communeId, String processName) {
	Pipeline pipeline = (Pipeline) this.pipelines.get(getPipelineMapKey(
		communeId, processName));
	if (pipeline == null) {
	    // si sincronizza sulla creazione della pipeline
	    pipeline = createPipeline(communeId, processName);
	}
	return pipeline;
    }

    /**
     * Restituisce tutte le pipeline.
     * 
     * @return
     */
    public Pipeline[] getPipeline() {
	Iterator iter = this.pipelines.keySet().iterator();
	ArrayList pipelinesList = new ArrayList();

	int count = 0;
	while (iter.hasNext()) {
	    pipelinesList.add(pipelines.get(iter.next()));
	    count++;
	}
	return (Pipeline[]) pipelinesList.toArray(new Pipeline[count]);
    }

    /**
     * Registra il processo sulla pipeline.
     * 
     * @param processClazz
     * @param clazz
     * @param handlers
     */
    protected Pipeline registerPipeline(String communeId, String processName,
	    Class clazz, PipelineHandler[] handlers) {
	Pipeline ppl = null;
	try {
	    ppl = (Pipeline) clazz.getConstructor(new Class[] { String.class })
		    .newInstance(new Object[] { processName });

	    for (int i = 0; i < handlers.length; i++)
		ppl.addHandler(handlers[i]);

	    this.pipelines.put(getPipelineMapKey(communeId, processName), ppl);
	} catch (Exception ex) {
	    cat.error(ex);
	}
	return ppl;
    }

    protected String getPipelineMapKey(String communeId, String processName) {
	return communeId + "." + processName;
    }
}
