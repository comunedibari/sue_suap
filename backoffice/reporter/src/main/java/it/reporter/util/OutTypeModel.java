/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.reporter.util;

/**
 *
 * @author Piergiorgio
 */
public class OutTypeModel {
    private String outputType;

    public String getOutputName() {
        return outputName;
    }

    public void setOutputName(String outputName) {
        this.outputName = outputName;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    public String getOutputType() {
        return outputType;
    }

    public void setOutputType(String outputType) {
        this.outputType = outputType;
    }
    private String outputPath;
    private String outputName;
}
