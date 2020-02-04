/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.selenium;

/**
 *
 * @author GabrieleM
 */
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
 
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Set;
 
public class FileDownloader {
 
    private WebDriver driver;
    private String localDownloadPath = "C:\\temp\\tmpCross";
    private boolean followRedirects = true;
    private boolean mimicWebDriverCookieState = true;
    private int httpStatusOfLastDownloadAttempt = 0;
 
    public FileDownloader(WebDriver driverObject) {
        this.driver = driverObject;
    }
 
    /**
     * Specify if the FileDownloader class should follow redirects when trying to download a file
     *
     * @param value
     */
    public void followRedirectsWhenDownloading(boolean value) {
        this.followRedirects = value;
    }
 
    /**
     * Get the current location that files will be downloaded to.
     *
     * @return The filepath that the file will be downloaded to.
     */
    public String localDownloadPath() {
        return this.localDownloadPath;
    }
 
    /**
     * Set the path that files will be downloaded to.
     *
     * @param filePath The filepath that the file will be downloaded to.
     */
    public void localDownloadPath(String filePath) {
        this.localDownloadPath = filePath;
    }
 
    /**
     * Download the file specified in the href attribute of a WebElement
     *
     * @param element
     * @return
     * @throws Exception
     */
    public String downloadFile(WebElement element) throws Exception {
        return downloader(element, "href");
    }
 
    /**
     * Download the image specified in the src attribute of a WebElement
     *
     * @param element
     * @return
     * @throws Exception
     */
    public String downloadImage(WebElement element) throws Exception {
        return downloader(element, "src");
    }
 
    /**
     * Gets the HTTP status code of the last download file attempt
     *
     * @return
     */
    public int getHTTPStatusOfLastDownloadAttempt() {
        return this.httpStatusOfLastDownloadAttempt;
    }
 
    /**
     * Mimic the cookie state of WebDriver (Defaults to true)
     * This will enable you to access files that are only available when logged in.
     * If set to false the connection will be made as an anonymouse user
     *
     * @param value
     */
    public void mimicWebDriverCookieState(boolean value) {
        this.mimicWebDriverCookieState = value;
    }
 
    /**
     * Load in all the cookies WebDriver currently knows about so that we can mimic the browser cookie state
     *
     * @param seleniumCookieSet
     * @return
     */
    private BasicCookieStore mimicCookieState(Set seleniumCookieSet) {
        BasicCookieStore mimicWebDriverCookieStore = new BasicCookieStore();
        for (Object cookie: seleniumCookieSet) {
            Cookie seleniumCookie=(Cookie) cookie;
            BasicClientCookie duplicateCookie = new BasicClientCookie(seleniumCookie.getName(), seleniumCookie.getValue());
            duplicateCookie.setDomain(seleniumCookie.getDomain());
            duplicateCookie.setSecure(seleniumCookie.isSecure());
            duplicateCookie.setExpiryDate(seleniumCookie.getExpiry());
            duplicateCookie.setPath(seleniumCookie.getPath());
            mimicWebDriverCookieStore.addCookie(duplicateCookie);
        }
 
        return mimicWebDriverCookieStore;
    }
 
    /**
     * Perform the file/image download.
     *
     * @param element
     * @param attribute
     * @return
     * @throws IOException
     * @throws NullPointerException
     */
    private String downloader(WebElement element, String attribute) throws IOException, NullPointerException, URISyntaxException {
        String fileToDownloadLocation = element.getAttribute(attribute);
        if (fileToDownloadLocation.trim().equals("")) throw new NullPointerException("The element you have specified does not link to anything!");
 
        URL fileToDownload = new URL(fileToDownloadLocation);
 //       TMess.azione("Cerco di scaricare il file dalla url:" + fileToDownload.toString());
        File downloadedFile = new File(this.localDownloadPath + "//provafile.txt");
        if (downloadedFile.canWrite() == false) downloadedFile.setWritable(true);
 
        HttpClient client = new DefaultHttpClient();
        BasicHttpContext localContext = new BasicHttpContext();
 
       
        if (this.mimicWebDriverCookieState) {
            localContext.setAttribute(ClientContext.COOKIE_STORE, mimicCookieState(this.driver.manage().getCookies()));
        }
 
        HttpGet httpget = new HttpGet(fileToDownload.toURI());
        HttpParams httpRequestParameters = httpget.getParams();
        httpRequestParameters.setParameter(ClientPNames.HANDLE_REDIRECTS, this.followRedirects);
        httpget.setParams(httpRequestParameters);
 
       
        HttpResponse response = client.execute(httpget, localContext);
        this.httpStatusOfLastDownloadAttempt = response.getStatusLine().getStatusCode();

        FileUtils.copyInputStreamToFile(response.getEntity().getContent(), downloadedFile);
        response.getEntity().getContent().close();
 
        String downloadedFileAbsolutePath = downloadedFile.getAbsolutePath();
 
        return downloadedFileAbsolutePath;
    }
 
}
