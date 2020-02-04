package it.wego.dynamicodt.transformation.util;

import it.wego.dynamicodt.transformation.ReportGenerator;
import it.wego.dynamicodt.transformation.html.OdtTableParameter;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.StringReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class ManageOdt
{
  private static Log log = LogFactory.getLog(ManageOdt.class);
  private String outputType;
  private File odtFile = null;
  private String backgroundImageFileName = "background.png";
  
  public ManageOdt(File odtFile)
  {
    log.debug("Creo ManageOdt su " + odtFile.getAbsolutePath());
    this.odtFile = odtFile;
  }
  
  public void unzipOdt()
    throws Exception
  {
    Zip zu = new Zip();
    
    zu.unzip(new FileInputStream(this.odtFile), getPathUnzip());
  }
  
  public void deleteOdtDir()
    throws Exception
  {
    IOUtil io = new IOUtil();
    io.deleteDirectory(getPathUnzip());
  }
  
  public File getContentXml()
  {
    String contentPath = getPathUnzip().getAbsolutePath() + File.separator + "content.xml";
    File content = new File(contentPath);
    return content;
  }
  
  public File getPathUnzip()
  {
    String tmpOdtName = this.odtFile.getName();
    
    String tmpDirOdtName = "dir" + tmpOdtName.substring(0, tmpOdtName.length() - 4);
    File templateDirZip = new File(ReportGenerator.TMPDIR + File.separator + tmpDirOdtName);
    templateDirZip.mkdir();
    return templateDirZip;
  }
  
  public void replaceTag()
    throws Exception
  {
    File contentXml = getContentXml();
    
    TemplateSyntaxTool tst = new TemplateSyntaxTool();
    
    ConfigUtil cu = new ConfigUtil();
    
    tst.templateSyntaxToJodSyntax(contentXml, cu.getInstance(), contentXml.getAbsolutePath());
  }
  
  public void replaceAmp()
    throws Exception
  {
    File contentXmlFile = getContentXml();
    
    String contentXmlString = convertFileIntoString(contentXmlFile);
    
    contentXmlString = contentXmlString.replaceAll("&amp;euro;", "�");
    

    contentXmlString = contentXmlString.replaceAll("__EMPTY__!!", "<text:span text:style-name=\"TestoInvisibile\">.</text:span>");
    
    OutputStream fout = new FileOutputStream(contentXmlFile.getAbsolutePath());
    OutputStream bout = new BufferedOutputStream(fout);
    OutputStreamWriter out = new OutputStreamWriter(bout, "UTF-8");
    out.write(contentXmlString);
    out.flush();
    out.close();
  }
  
  private String convertFileIntoString(File file)
    throws Exception
  {
    StringBuffer sb = new StringBuffer();
    BufferedReader br = null;
    String result;
    try
    {
      br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
      
      String line = null;
      while ((line = br.readLine()) != null) {
        sb.append(line);
      }
      result = sb.toString();
    }
    finally
    {
      if (br != null) {
        br.close();
      }
    }
    return result;
  }
  
  public void signExtraTag()
  {
    try
    {
      File file = getContentXml();
      if (file.exists())
      {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(file);
        
        NodeList list = doc.getElementsByTagName("*");
        for (int i = 0; i < list.getLength(); i++)
        {
          Element element = (Element)list.item(i);
          if ((element.hasChildNodes()) && (element.getChildNodes().getLength() == 1) && (element.getFirstChild().getNodeType() == 3))
          {
            String nodeValue = element.getFirstChild().getNodeValue();
            
            log.debug("nodeValue" + nodeValue);
            if ((nodeValue.indexOf("[#list") != -1) || (nodeValue.indexOf("[/#list]") != -1))
            {
              element.setAttribute("toRemove", "true");
              log.debug("setto toRemove per " + nodeValue);
            }
            if ((nodeValue.indexOf("[#if") != -1) || (nodeValue.indexOf("[/#if]") != -1))
            {
              element.setAttribute("toRemove", "true");
              log.debug("setto toRemove per " + nodeValue);
            }
            if (nodeValue.indexOf("[#assign") != -1)
            {
              element.setAttribute("toRemove", "true");
              log.debug("setto toRemove per " + nodeValue);
            }
            if (nodeValue.indexOf("[#else") != -1)
            {
              element.setAttribute("toRemove", "true");
              log.debug("setto toRemove per " + nodeValue);
            }
            if (nodeValue.indexOf("[#--image") != -1) {
              element.setAttribute("toImage", "true");
            }
            if (nodeValue.indexOf("[#--html") != -1) {
              element.setAttribute("toHtml", "true");
            }
          }
        }
        Source source = new DOMSource(doc);
        

        Result result = new StreamResult(file);
        

        Transformer xformer = TransformerFactory.newInstance().newTransformer();
        xformer.transform(source, result);
      }
      else
      {
        System.out.print("File not found!");
      }
    }
    catch (Exception e)
    {
      log.error("Errore", e);
    }
  }
  
  public void removeExtraTag()
  {
    try
    {
      File file = getContentXml();
      if (file.exists())
      {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(file);
        
        NodeList list = doc.getElementsByTagName("*");
        
        ConfigUtil cu = new ConfigUtil();
        String fileName = cu.getInstance().getProperty("h2oTemplateTableFile");
        OdtTableParameter htmlParameter = new OdtTableParameter();
        URL fileUrl = getClass().getClassLoader().getResource("/");
        log.debug("fileUrl " + fileUrl.toString());
        URI fileUri = new URI(fileUrl.toString());
        File f = new File(fileUri);
        htmlParameter.setTemplateDir(f);
        htmlParameter.setOutputType(this.outputType);
        for (int i = 0; i < list.getLength(); i++)
        {
          Element element = (Element)list.item(i);
          if (element.hasAttribute("toRemove"))
          {
            element.getParentNode().removeChild(element);
            i--;
          }
          else if (element.hasAttribute("toImage"))
          {
            PreProcessorUtil pp = new PreProcessorUtil();
            NodeList figli = element.getChildNodes();
            
            String base64Img = "";
            Node elementImg = null;
            for (int ii = 0; ii < figli.getLength(); ii++)
            {
              Node myElement = figli.item(ii);
              
              String valueImg = myElement.getNodeValue();
              if (valueImg != null)
              {
                base64Img = valueImg;
                elementImg = myElement;
                break;
              }
            }
            DocumentFragment fragment = pp.buildImage(getPathUnzip(), base64Img, doc);
            if (elementImg != null) {
              elementImg.setNodeValue("");
            }
            if (fragment != null) {
              element.appendChild(fragment);
            }
          }
          else if (element.hasAttribute("toHtml"))
          {
            log.debug("toHtml...");
            
            PreProcessorUtil pp = new PreProcessorUtil();
            NodeList figli = element.getChildNodes();
            
            String htmlContent = "";
            Node elementHtml = null;
            for (int ii = 0; ii < figli.getLength(); ii++)
            {
              Node myElement = figli.item(ii);
              
              String valueHtml = myElement.getNodeValue();
              if (valueHtml != null)
              {
                htmlContent = valueHtml;
                elementHtml = myElement;
                break;
              }
            }
            List result = pp.buildHtmlTable(htmlContent, doc, htmlParameter);
            
            DocumentFragment[] tables = (DocumentFragment[])result.get(0);
            DocumentFragment[] styles = (DocumentFragment[])result.get(1);
            DocumentFragment[] forms = (DocumentFragment[])result.get(2);
            if (elementHtml != null) {
              elementHtml.setNodeValue("");
            }
            for (int ii = 0; ii < tables.length; ii++)
            {
              DocumentFragment table = tables[ii];
              if (table != null) {
                element.getParentNode().insertBefore(table, element);
              }
            }
            Element elementStyle = (Element)doc.getElementsByTagName("office:automatic-styles").item(0);
            log.debug("elementStyle  " + elementStyle);
            for (int ii = 0; ii < styles.length; ii++)
            {
              DocumentFragment style = styles[ii];
              if (style != null) {
                elementStyle.appendChild(style);
              }
            }
            Element elementForms = (Element)doc.getElementsByTagName("office:forms").item(0);
            log.debug("elementForms  " + elementForms);
            for (int ii = 0; ii < forms.length; ii++)
            {
              DocumentFragment form = forms[ii];
              if (form != null) {
                elementForms.appendChild(form);
              }
            }
            element.getParentNode().removeChild(element);
            i--;
          }
        }
        Source source = new DOMSource(doc);
        

        Result result = new StreamResult(file);
        

        Transformer xformer = TransformerFactory.newInstance().newTransformer();
        xformer.transform(source, result);
      }
      else
      {
        System.out.print("File not found!");
      }
    }
    catch (Exception e)
    {
      log.error("Error", e);
    }
  }
  
  public void regenerateOdtFile()
    throws IOException, IllegalArgumentException
  {
    File d = getPathUnzip();
    Zip.zipDir(d, this.odtFile);
  }
  
  public void setOutputType(String outputType)
  {
    this.outputType = outputType;
  }
  
  public String getOutputType()
  {
    return this.outputType;
  }
  
  public File getStylesXml()
  {
    String contentPath = getPathUnzip().getAbsolutePath() + File.separator + "styles.xml";
    File content = new File(contentPath);
    return content;
  }
  
  public File getManifestXml()
  {
    String manifestPath = getPathUnzip().getAbsolutePath() + File.separator + "META-INF" + File.separator + "manifest.xml";
    File manifest = new File(manifestPath);
    return manifest;
  }
  
  public void copyImage(String filePath, File pictureFolder)
    throws IOException, IllegalArgumentException, ParserConfigurationException, SAXException, TransformerConfigurationException, TransformerException
  {
    copyImage(new File(filePath), pictureFolder);
  }
  
  public File getGenericFile(String filePath)
  {
    File file = new File(filePath);
    return file;
  }
  
  public void copyImage(File imagePath, File pictureFolder)
    throws IOException, IllegalArgumentException, ParserConfigurationException, SAXException, TransformerConfigurationException, TransformerException
  {
    File file = getManifestXml();
    if (file.exists())
    {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      
      DocumentBuilder builder = factory.newDocumentBuilder();
      
      builder.setEntityResolver(new EntityResolver()
      {
        public InputSource resolveEntity(String publicId, String systemId)
          throws SAXException, IOException
        {
          if (systemId.contains("Manifest.dtd")) {
            return new InputSource(new StringReader(""));
          }
          return null;
        }
      });
      Document doc = builder.parse(file);
      Node node = doc.getDocumentElement();
      Element link = doc.createElement("manifest:file-entry");
      link.setAttribute("manifest:media-type", "");
      link.setAttribute("manifest:full-path", pictureFolder.getName() + "/");
      node.appendChild(link);
      
      String[] images = imagePath.list();
      String pathFrom = imagePath.getPath();
      String pathTo = pictureFolder.getPath();
      if (images != null) {
        for (int i = 0; i < images.length; i++)
        {
          String filename = images[i];
          File inpFile = new File(pathFrom + File.separator + filename);
          File outFile = new File(pathTo + File.separator + filename);
          copy(inpFile, outFile);
          InputStream is = new BufferedInputStream(new FileInputStream(inpFile));
          String mimeType = URLConnection.guessContentTypeFromStream(is);
          
          link = doc.createElement("manifest:file-entry");
          link.setAttribute("manifest:media-type", mimeType);
          link.setAttribute("manifest:full-path", pictureFolder.getName() + "/" + filename);
          node.appendChild(link);
        }
      }
      Source source = new DOMSource(doc);
      Result result = new StreamResult(file);
      Transformer xformer = TransformerFactory.newInstance().newTransformer();
      log.debug("xformer.transform - source : " + source.toString() + " result : " + result.toString());
      xformer.transform(source, result);
    }
  }
  
  public void insertBackground(File pictureFolder, HashMap xmlParametersHashMap)
    throws IOException, IllegalArgumentException, ParserConfigurationException, SAXException, TransformerConfigurationException, TransformerException
  {
    if ((xmlParametersHashMap.containsKey("bozza")) && ("S".equalsIgnoreCase((String)xmlParametersHashMap.get("bozza"))))
    {
      File tmp = null;
      log.debug("insertBackground");
      
      log.debug("insertBackground filename :" + this.backgroundImageFileName);
      tmp = getGenericFile(pictureFolder + File.separator + this.backgroundImageFileName);
      log.debug("insertBackground - directory :" + pictureFolder);
      if (tmp.exists())
      {
        log.debug("insertBackground - exsist background image :" + tmp.getName());
        File styles = getStylesXml();
        if (styles.exists())
        {
          DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
          
          DocumentBuilder builder = factory.newDocumentBuilder();
          
          builder.setEntityResolver(new EntityResolver()
          {
            public InputSource resolveEntity(String publicId, String systemId)
              throws SAXException, IOException
            {
              if (systemId.contains("Styles.dtd")) {
                return new InputSource(new StringReader(""));
              }
              return null;
            }
          });
          Document doc = builder.parse(styles);
          Element link = doc.createElement("style:background-image");
          link.setAttribute("xlink:href", pictureFolder.getName() + "/" + this.backgroundImageFileName);
          link.setAttribute("xlink:type", "simple");
          link.setAttribute("xlink:actuate", "onLoad");
          link.setAttribute("style:repeat", "stretch");
          
          NodeList nodes = doc.getElementsByTagName("style:page-layout-properties");
          log.debug("insertBackground - find node  style:page-layout-properties");
          if (nodes.getLength() > 0)
          {
            Node node = nodes.item(0);
            
            node.appendChild(link);
            Source source = new DOMSource(doc);
            Result result = new StreamResult(styles);
            Transformer xformer = TransformerFactory.newInstance().newTransformer();
            log.debug("insertBackground : Effetuato ");
            xformer.transform(source, result);
          }
        }
      }
    }
  }
  
  void copy(File src, File dst)
    throws IOException
  {
    InputStream in = new FileInputStream(src);
    OutputStream out = new FileOutputStream(dst);
    
    byte[] buf = new byte[1024];
    int len;
    while ((len = in.read(buf)) > 0) {
      out.write(buf, 0, len);
    }
    in.close();
    out.close();
  }
}
