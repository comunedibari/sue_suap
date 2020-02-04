/**
 * 
 */
package it.people.util.resourcesupdater;

import java.util.Vector;

/**
 * Interface for resource updater.
 * 
 * A resource updater is useful to synchronize DB content with resource files
 * content.
 * 
 * @author Andrea Piemontese
 * 
 */
public interface IResourceUpdater {

    /**
     * Check if update is needed working with resource files.
     * 
     * @param basePath
     *            the base path of the resource files to.
     * @return boolean
     */
    public boolean update(String basePath);

    /**
     * Get the SQL instruction to synchronize a DB
     * 
     * @return
     */
    public Vector getSynchronizationInstructions();

}
