/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.reporter.util;

import freemarker.core.Environment;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import java.io.IOException;

/**
 *
 * @author Piergiorgio
 */
public class ReporterTemplateExceptionHandler implements TemplateExceptionHandler {

    public void handleTemplateException(TemplateException te, Environment env, java.io.Writer out)
            throws TemplateException {
        try {
            out.write("[ERRORE: " + te.getFTLInstructionStack() + "]");
        } catch (IOException ex) {
            throw new TemplateException("Failed to print error message. Cause: " + ex, env);
        }
    }
}
