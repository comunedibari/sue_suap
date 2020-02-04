/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author giuseppe
 */
public class ByteArrayDataSource implements javax.activation.DataSource {

    private byte[] bytes;
    private String contentType;
    private String name;

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }

    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(bytes);
    }

    public OutputStream getOutputStream() throws IOException {
        final ByteArrayDataSource bads = this;
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // return an outputstream that sets my byte array
        // when it is closed.
        return new FilterOutputStream(baos) {
            @Override
            public void close() throws IOException {
                baos.close();
                bads.setBytes(baos.toByteArray());
            }
        };
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
