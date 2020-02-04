/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.reporter.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.reporter.transformation.ReportGenerator;


/**
 *
 * @author Piergiorgio
 */

public class ReporterImpl{
    private static Logger log = LoggerFactory.getLogger(ReporterImpl.class.getName());
    private ReportGenerator generator = new ReportGenerator();

    public byte[] generateDocument(byte[] templateOdt, byte[] xmlData, byte[] xmlStaticData, byte [] xmlParams, byte [] outputType) {
        byte[] response = null;
        log.debug("-> Received dataXml [" + xmlData.length + "] template ["
                + templateOdt.length + "] type [" + outputType + "]");
        try {

            response = generator.generateReport(templateOdt,xmlData, xmlStaticData, xmlParams, outputType);

            //log.debug("-> Response: "+new String(response));

        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error generating report ", e);
        }
        return response;
    }

}
