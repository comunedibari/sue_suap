/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.reporter.util;

import it.reporter.xsd.ootputType.OutputTypeRoot;

/**
 *
 * @author Piergiorgio
 */
public class OutputType {

    public OutTypeModel getTypeOutput(OutputTypeRoot docOutput) throws Exception {
        String outType = "PDF";
        String outPath = null;
        String outName = null;
        OutTypeModel ret = null;


        ret = new OutTypeModel();
        outType = docOutput.getOutputFormat().trim();
        if (docOutput.getOutputPath() != null) {

            outPath = docOutput.getOutputPath().getPath().trim();
            outName = docOutput.getOutputPath().getName().trim();
        }
        ret.setOutputType(outType);
        ret.setOutputPath(outPath);
        ret.setOutputName(outName);

        return ret;
    }
}
