/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.wego.cross.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 *
 * @author giuseppe
 */
public class Log {

    private Log() {};
    public static final Logger ROOT = LoggerFactory.getLogger("it.wego");
    public static final Logger SQL = LoggerFactory.getLogger("sql");
    public static final Logger XML = LoggerFactory.getLogger("xml");
    public static final Logger EMAIL = LoggerFactory.getLogger("mail");
    public static final Logger WS = LoggerFactory.getLogger("ws");
    public static final Logger APP = LoggerFactory.getLogger("app");
    public static final Logger WORKFLOW = LoggerFactory.getLogger("wf");
    public static final Logger SCHEDULER = LoggerFactory.getLogger("scheduler");
    public static final Logger PLUGIN = LoggerFactory.getLogger("plugin");
    public static final Logger PRATICHE_SPURIE = LoggerFactory.getLogger("pratiche_spurie");
}
