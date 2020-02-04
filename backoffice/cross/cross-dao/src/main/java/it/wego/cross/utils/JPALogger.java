/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.wego.cross.utils;

import org.eclipse.persistence.logging.AbstractSessionLog;
import org.eclipse.persistence.logging.SessionLog;
import org.eclipse.persistence.logging.SessionLogEntry;

/**
 *
 * @author giuseppe
 */
public class JPALogger extends AbstractSessionLog implements SessionLog {
    /* @see org.eclipse.persistence.logging.AbstractSessionLog#log(org.eclipse.persistence.logging.SessionLogEntry)
     */
    @Override
    public void log(SessionLogEntry sessionLogEntry) {
        Log.SQL.debug(sessionLogEntry.getMessage()); // untranslated/undecoded message_id
    }
}
