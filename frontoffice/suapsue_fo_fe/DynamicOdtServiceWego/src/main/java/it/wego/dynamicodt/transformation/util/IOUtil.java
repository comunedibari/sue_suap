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
 * http://www.osor.eu/eupl
 * 
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
**/
package it.wego.dynamicodt.transformation.util;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Manages IO operations
 *
 * @author Pavle
 *
 */
public class IOUtil {

    private static final Log log = LogFactory.getLog(IOUtil.class);
    private static File templateDir = null;

    /**
     * Return byte [] from {@link String} path to file
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    public byte[] getByteArrayFromFileName(String fileName) throws IOException {
        byte[] bytea = null;

        File file = new File(fileName);
        bytea = getByteArrayFromFile(file);
        return bytea;
    }

    /**
     * Return byte [] from {@link File} file
     *
     * @param file
     * @return
     * @throws IOException
     */
    public byte[] getByteArrayFromFile(File file) throws IOException {
        FileChannel channel = new FileInputStream(file).getChannel();
        int size = (int) channel.size();
        ByteBuffer buffer = ByteBuffer.allocate(size);
        channel.read(buffer, 0);
        channel.close();
        return buffer.array();
    }

    /**
     * Return generated {@link File} on path {@link String} path and data
     * {@link byte} []
     *
     * @param fileName
     * @param data
     * @return
     * @throws IOException
     */
    public File generateFileFromByteArray(String fileName, byte[] data)
            throws IOException {
        File file = new File(fileName);
        file.createNewFile();
        generateFileFromByteArray(file, data);
        return file;
    }

    /**
     * Return generated temp {@link File} with data {@link byte} []
     *
     * @param data
     * @return
     * @throws IOException
     */
    public File generateTempFileFromByteArray(byte[] data) throws IOException {
        File file = generateTempFile();
        generateFileFromByteArray(file, data);
        return file;
    }

    /**
     * Return generated empty temp {@link File}
     *
     * @param name
     * @return
     * @throws IOException
     */
    public File generateTempFile() throws IOException {
        File file = File.createTempFile(System.currentTimeMillis() + "", ".inp.xml.dat");
        return file;
    }

    public File generateTempFile(String ext) throws IOException {
        File file = File.createTempFile(System.currentTimeMillis() + "", "." + ext);
        return file;
    }

    /**
     * Writes byte [] data to {@link File} file
     *
     * @param file
     * @param data
     * @throws IOException
     */
    public void generateFileFromByteArray(File file, byte[] data)
            throws IOException {
        FileChannel channel = new FileOutputStream(file).getChannel();
        ByteBuffer buffer = ByteBuffer.wrap(data);
        channel.write(buffer);
        channel.close();
    }

    /**
     * Returns byte [] of content.xml file form OpenOffice ODT byte array
     *
     * @param odtRawSource
     * @return
     * @throws IOException
     */
    public byte[] getDataFromODTTemplateSource(byte[] odtRawSource)
            throws IOException {
        File odtFile = generateTempFileFromByteArray(odtRawSource);
        StringBuffer buffer = new StringBuffer();
        ZipFile zipFile = null;
        DataInputStream stream = null;
        try {
            zipFile = new ZipFile(odtFile);
            if (log.isInfoEnabled()) {
                log.info("Parsing odt file [" + odtFile + "]");
            }
            ZipEntry entry = zipFile.getEntry("content.xml");
            stream = new DataInputStream(zipFile.getInputStream(entry));
            String line = null;
            while ((line = stream.readLine()) != null) {
                buffer.append(line);
            }
            stream.close();
            zipFile.close();
            if (log.isInfoEnabled()) {
                log.info("Extracted content.xml");
            }
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (Exception e) {
            }
            try {
                if (zipFile != null) {
                    zipFile.close();
                }
            } catch (Exception e) {
            }
            if (log.isInfoEnabled()) {
                log.info("Eraseing odt file [" + odtFile + "]");
            }
            odtFile.delete();
        }
        return buffer.toString().getBytes();
    }

    public boolean deleteDirectory(File path) {
        if (path.exists()) {
            //log.debug("cancello "+path.getAbsolutePath());
            File[] files = path.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    deleteDirectory(files[i]);
                } else {
                    //log.debug("cancello "+files[i].getAbsolutePath());
                    files[i].delete();
                }
            }
        }
        return (path.delete());
    }

    /**
     * Return {@link InputStream} from resource in path
     *
     * @param path
     * @return
     * @throws IOException
     */
    public InputStream getResourceFromPath(String path) throws IOException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        if (loader != null) {
            URL resourceUrl = loader.getResource(path);
            if (resourceUrl != null) {
                if (log.isDebugEnabled()) {
                    log.info("Loading resource [" + resourceUrl.getFile() + "]");
                }
                InputStream stream = resourceUrl.openStream();
                if (stream != null) {
                    if (log.isDebugEnabled()) {
                        log.info("Loaded resource [" + stream + "] on path [" + path + "]");
                    }
                    return stream;
                } else {
                    throw new IOException("Cannot load resource [" + path + "], null");
                }
            } else {
                throw new IOException("Resource is null [" + path + "] , [" + resourceUrl + "]");
            }
        } else {
            throw new IOException("Cannot load resource [" + path + "], Classloader is null");
        }
    }

    public InputStream getResourceFromPath(Class clazz, String path)
            throws IOException {
        ClassLoader loader = clazz.getClassLoader();
        if (loader != null) {
            URL resourceUrl = loader.getResource(path);
            if (resourceUrl != null) {
                if (log.isDebugEnabled()) {
                    log.info("Loading resource [" + resourceUrl.getFile() + "]");
                }
                InputStream stream = resourceUrl.openStream();
                if (stream != null) {
                    if (log.isDebugEnabled()) {
                        log.info("Loaded resource [" + stream + "] on path [" + path + "]");
                    }
                    return stream;
                } else {
                    throw new IOException("Cannot load resource [" + path + "], null");
                }
            } else {
                throw new IOException("Resource is null [" + path + "] , [" + resourceUrl + "]");
            }
        } else {
            throw new IOException("Cannot load resource [" + path + "], Classloader is null");
        }
    }
}
