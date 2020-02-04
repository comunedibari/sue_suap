/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.utils;

import org.eclipse.persistence.config.SessionCustomizer;
import org.eclipse.persistence.logging.SessionLog;
import org.eclipse.persistence.sessions.Session;

/**
 *
 * @author giuseppe
 */
public class JPAEclipseLinkSessionCustomizer implements SessionCustomizer {

    /*
     *  Logging level :
        8. OFF \u2013 disables logging
        7. SEVERE \u2013 logs exceptions indicating EclipseLink cannot continue, as well as any exceptions generated during login. This includes a stack trace.
        6. WARNING \u2013 logs exceptions that do not force EclipseLink to stop, including all exceptions not logged with severe level. This does not include a stack trace.
        5. INFO \u2013 logs the login/logout per sever session, including the user name. After acquiring the session, detailed information is logged.
        4. CONFIG \u2013 logs only login, JDBC connection, and database information.
        3. FINE \u2013 logs SQL.
        2. FINER \u2013 similar to warning. Includes stack trace.
        1. FINEST \u2013 includes additional low level information.

     */

    @Override
    public void customize(Session aSession) throws Exception {
        // create a custom logger
        SessionLog aCustomLogger = new JPALogger();
        aCustomLogger.setLevel(3); // Logging level finest
        aSession.setSessionLog(aCustomLogger);
    }
}
