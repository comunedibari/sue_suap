package it.asitech.webservice.protocollo;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.xml.rpc.ServiceException;
import javax.xml.soap.SOAPException;

import org.apache.axis.attachments.AttachmentPart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.BasicConfigurator;

public abstract class AbstractClient {
    public static final int NO_RESP = 1;
    public static final int FAIL_RESP = 2;
    public static final int OK_RESP = 0;

    private String sid = null;
    private ResponseMessage lastResponseMessage = null;
    private ProtocolloServiceSoapBindingStub binding = null;

    public abstract String getUser();

    public abstract String getPassword();

    public abstract String getServiceHttpPortWSDDServiceName( );

    protected abstract Logger getLogger();

    protected AbstractClient() {
    }

    protected Logger getLog(String logFileName) {
//        if( logFileName == null )
//            logFileName = "log4j.properties";
//        Logger log = Logger.getLogger(this.getClass());
//        try {
//            File f = getLogFileName(logFileName);
//            PropertyConfigurator.configureAndWatch(f.getAbsolutePath());
//            log.info("logger configured by "+f.getAbsolutePath());
//        } catch( Exception e ) {
//            BasicConfigurator.configure();
//            log.error("File di configurazione "+logFileName+" non trovato");
//        }
        return LoggerFactory.getLogger(getClass().getName());
    }

    public String getSid() throws ServiceException, RemoteException
    {
        return this.sid;
    }

    /**
     * Chiama la funzone PRIGetSid del ws
     *
     * @param user
     * @param password
     * @return
     * @throws ServiceException
     * @throws RemoteException
     */
    public String getSid(String user, String password) throws ServiceException, RemoteException
    {
        if( getLogger().isDebugEnabled() )
            getLogger().debug( "--- begin "+new Date());

      //ottiene il SID dal server per le sucessive chiamate
        this.doBinding();

        PRIGetSidResponse resp = this.binding.PRIGetSid( user, password );

        if( logResponseMessage( resp ) ){
            this.sid = resp.getSid();
            if( getLogger().isDebugEnabled() ) getLogger().debug( "begin session sid="+this.sid );
        }

        if( getLogger().isDebugEnabled() )
            getLogger().info( "--- end "+new Date());

        return this.sid;
    }


    public ProtocolloServiceSoapBindingStub getBinding()
    {
        return this.binding;
    }

    public ResponseMessage getLastResponseMessage()
    {
        return this.lastResponseMessage;
    }

    public String qt(Object vl){
        if( vl==null ) return "null";
        if( vl instanceof String){
            StringBuilder sb = new StringBuilder(((String)vl).length()+2);
            return sb.append( '"' ).append( vl ).append( '"' ).toString();
        }
        return vl.toString();
    }

    public String qq(String vl){
        if( vl==null )
            return "null";
        return vl;
    }
    public String qq(Integer vl){
        if( vl==null )
            return "null";
        return String.format( "%010d", vl );
    }
    public String qq(Date vl){
        if( vl==null )
            return "null";
        DateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.US);
        formatter.setTimeZone( TimeZone.getTimeZone("GMT") );
        return formatter.format( vl );
    }
    public String qq(Calendar vl){
        if( vl==null )
            return "null";
        return qq( vl.getTime() );
    }

    public final void doBinding( ) throws ServiceException{
        if( this.binding != null ) return; //...gi fatto

        if( getLogger().isDebugEnabled() )
            getLogger().debug("-----> bind to "+getServiceHttpPortWSDDServiceName());
        ProtocolloServicePortTypeServiceLocator psl = new ProtocolloServicePortTypeServiceLocator();
        psl.setProtocolloServiceEndpointAddress( getServiceHttpPortWSDDServiceName() );
        this.binding = (ProtocolloServiceSoapBindingStub) psl.getProtocolloService();
        this.binding.setTimeout(60000);
        this.binding.setDebug( false );
    }

    /**
     * Chiama la funzone PRIEndSession del ws
     *
     * @return
     * @throws ServiceException
     * @throws RemoteException
     */
    public boolean endSession() throws ServiceException, RemoteException
    {
        if( getLogger().isDebugEnabled() ) getLogger().debug( "--- begin "+new Date());
        this.doBinding();

        if( getLogger().isDebugEnabled() ) getLogger().debug( "end session sid="+this.sid );

        ResponseMessage resp = this.binding.PRIEndSession( this.sid );

        getLogger().debug( "--- end "+new Date());

        return logResponseMessage( resp );

    }

    public ClassDef getClassInfo(String className) throws ServiceException, RemoteException
    {
        ClassDef classDef = null;

        this.doBinding();

        PRIGetClassInfoResponse resp = this.binding.PRIGetClassInfo( this.sid, getUser(), className );

        if( logResponseMessage( resp ) )
            classDef = resp.getClassInfo();

        if( getLogger().isDebugEnabled() )
            getLogger().debug( "getClassInfo:"+classDef );
        return classDef;
    }

    public Field getField(String fieldNane, PriObject po){
        Field[] flds = po.getFields();
        for( Field f : flds ) {
            if( fieldNane.equals( f.getName()) ){
                return f;
            }
        }
        return null;
    }
    public Object getFieldValue0(String fieldNane, PriObject po){
        Field f = this.getField( fieldNane, po );
        if( f!=null ) {
            return f.getValue()[0];
        }
        return null;
    }

    /**
     * Chiama la funzone PRIGetObject del ws
     *
     * @param objId
     * @return
     * @throws ServiceException
     * @throws RemoteException
     */
    public PriObject getObject(int objId) throws ServiceException, RemoteException
    {
        if( getLogger().isDebugEnabled() ) getLogger().debug( "--- begin "+new Date());

        PriObject priObject = null;

        this.doBinding();

        PRIObjectOut  resp = this.binding.PRIGetObject( this.sid, getUser(), objId, "" );
        if( logResponseMessage( resp ) )
            priObject = resp.getPriObj();

        if( getLogger().isDebugEnabled() ){
            getLogger().debug( "getObject:"+priObject );
            getLogger().debug( "--- end" );
        }

        return priObject;
    }

    public PriObject createAttach (String className, File file, List<AttachmentPart> attach){
        FileDataSource fds = new FileDataSource(file);

        return this.createAttach( className, fds, attach );
    }

    public PriObject createAttach (String className, DataSource dataSource, List<AttachmentPart> attach){
        DataHandler dh = new DataHandler(dataSource);
        AttachmentPart ap =  new AttachmentPart(dh);
        attach.add( ap );

        Field[] fldr = new Field[]{
                new Field( "FILE_NAME", new Object[]{dataSource.getName()} ),
                new Field( "FILE_SALVATO", new Object[]{ap.getContentId()} ),
                new Field( "DESCRIZIONE", new Object[]{dataSource.getName()} )
        };

        return new PriObject(className, fldr, 0);
    }

    public PriObject[] createAttachs (String className, File[] files, List<AttachmentPart> attach){
        PriObject[] filesObj = new PriObject[files.length];

        int i= 0;
        for( File file : files ) {
            filesObj[i++] = createAttach(className, file, attach);
        }

        return filesObj;
    }

    public PriObject[] createAttachs (String className, File file, List<AttachmentPart> attach){
        return new PriObject[]{createAttach(className, file, attach)};
    }

    private PriObject createSogg(Object sogg, String mezzo, String tipo){
        Field[] flds = new Field[3];

        if( sogg instanceof String)
            flds[0] = new Field( "RAG_SOG", new Object[]{sogg} );
        else
            flds[0] = new Field( "CODE_SOGG", new Object[]{ sogg } );
        flds[1] = new Field( "MEZZO", new String[]{mezzo} );
        flds[2] = new Field( "TIPO", new String[]{tipo} );

        return new PriObject("REGPROT_SOGG", flds, 0);
    }

    public PriObject[] createSoggs(Integer[] codeSogg, String[] mezzi, String[] tipi){
        PriObject[] soggetti = new PriObject[codeSogg.length];

        for( int i = 0; i < soggetti.length; i++ ) {
            soggetti[i] = createSogg(codeSogg[i], mezzi[i], tipi[i]);
        }

        return soggetti;
    }

    public PriObject[] createSoggs(int codeSogg, String mezzo, String tipo){
        return new PriObject[]{ createSogg(new Integer(codeSogg), mezzo, tipo) };
    }

    public PriObject[] createSoggs(String[] ragSog, String[] mezzi, String[] tipi){
        PriObject[] soggetti = new PriObject[ragSog.length];

        for( int i = 0; i < soggetti.length; i++ ) {
            soggetti[i] = createSogg((Object)ragSog[i], mezzi[i], tipi[i]);
        }

        return soggetti;
    }

    public PriObject[] createSogg(String ragSog, String mezzo, String tipo){
        return new PriObject[]{ createSogg((Object)ragSog, mezzo, tipo) };
    }

    public PriObject[] createReg(int anno, int numero, Date data, String codReg){
        Field[] flds = new Field[4];

        flds[0] = new Field( "ANNO_REG", new Integer[]{anno} );
        flds[1] = new Field( "NUMERO_REG", new Integer[]{numero} );
        flds[2] = new Field( "DATA_REG", new Date[]{ data } );
        flds[3] = new Field( "COD_REGISTRO", new String[]{codReg} );

        return new PriObject[]{ new PriObject("REGPROT_REG", flds, 0) };
    }



    public PriObject[] query(String className, String qry) throws ServiceException, RemoteException
    {
        return query(className, qry, null);
    }

    /**
     * Chiama la funzone PRIGetObject del ws
     *
     * @param className
     * @param qry
     * @param fields
     * @return
     * @throws ServiceException
     * @throws RemoteException
     */
    public PriObject[] query(String className, String qry, String[] fields) throws ServiceException, RemoteException
    {
        if( getLogger().isDebugEnabled() ) getLogger().debug( "--- begin "+new Date());

        this.doBinding();

        PRIQueryObjectOut resp = this.binding.PRIQuery( this.sid, getUser(), className, qry, fields,1 );

        PriObject[] ret = null;
        if( logResponseMessage( resp ) )
            ret = resp.getPriObj();

        if( getLogger().isDebugEnabled() ){
            getLogger().debug( "query getRecordCount="+resp.getRecordCount() );
            getLogger().info( "--- end "+new Date());
        }

        return ret!=null ? ret : new PriObject[0];
    }


    public void deattach(PriObject priFile, OutputStream os) throws SOAPException, IOException
    {
        if( getLogger().isDebugEnabled() )
            getLogger().debug( "deattach priObj:"+ priFile );

        Object attach[] = this.binding.getAttachments();

        //se ci sono file...
        if( attach != null ){
            /*
            AttachmentPart ap = (AttachmentPart)attach[0];
            getLogger().info( "attach[0]=> ContentType:"+ ap.getContentType()+" Size:"+ap.getSize()+" AttachmentFile:"+ap.getAttachmentFile());
            getLogger().info("File received on server is: " + ap.getAttachmentFile() );
            File fi = new File( ap.getAttachmentFile() ); //l'ha gi salvato sulla tua temp
            File fo = new File("d:/tmp/", priFile.getValueString( "FILE_NAME" ) );
            fi.renameTo( fo );
            getLogger().info("File written to: " + fo.getAbsolutePath() );
            */
            AttachmentPart ap = (AttachmentPart)attach[0];
            DataHandler dh = ap.getDataHandler();
            try{
                dh.writeTo( os );
                if( getLogger().isDebugEnabled() ){
                    PriObjectExt ex = new PriObjectExt( priFile );
                    getLogger().debug("written file name: " + ex.getValueString( "FILE_NAME" ) );
                }
            }finally{
                os.flush();
                os.close();
            }
        }
    }

    /**
     * Chiama la funzone PRIGetFile del ws
     *
     * @param objId
     * @param os
     * @throws ServiceException
     * @throws SOAPException
     * @throws IOException
     */
    public void getFile(int objId, OutputStream os) throws ServiceException, SOAPException, IOException
    {
        if( getLogger().isDebugEnabled() ) getLogger().debug( "--- begin "+new Date());

        this.doBinding();

        //richiedo al ws il file con id=objId
        PRIObjectOut resp = this.binding.PRIGetFile( this.sid, getUser(), objId );
        ResponseMessage rm = resp.getReturnCode();

        //se non c' nessun errore salvo i file sul mio disco
        if( !rm.isRejected() ){
            this.deattach(  resp.getPriObj(), os );
        }else {
            logResponseMessage( rm );
        }

        if( getLogger().isDebugEnabled() ) getLogger().debug( "--- end "+new Date());
    }

    /**
     *
     * @param objId
     * @param os
     * @throws ServiceException
     * @throws SOAPException
     * @throws IOException
     */
    public void getFileOrig(int objId, OutputStream os) throws ServiceException, SOAPException, IOException
    {
        if( getLogger().isDebugEnabled() ) getLogger().debug( "--- begin "+new Date());

        this.doBinding();

        PRIObjectOut resp = this.binding.PRIGetFileOrig( this.sid, getUser(), objId );
        ResponseMessage rm = resp.getReturnCode();

        //se non c' nessun errore salvo i file sul mio disco
        if( !rm.isRejected() ){
            this.deattach(  resp.getPriObj(), os );
        }else {
            logResponseMessage( rm );
        }

        if( getLogger().isDebugEnabled() ) getLogger().debug( "--- end "+new Date());
    }

    public File getLogFileName(String logFileName) throws URISyntaxException
    {
        File f = new File( logFileName );
        if( f.exists() )
            return f;

        ClassLoader loader = this.getClass().getClassLoader();
        URL confFileUrl = loader.getResource(logFileName);

        return  new File( new URI( confFileUrl.toString() ) );
    }

    public boolean logResponseMessage( ResponseMessage resp){
        this.lastResponseMessage = resp;
        if( resp == null ){
            getLogger().info( "returnCode==null" );
            return false;
        } else if( resp.isRejected() ) {
            getLogger().error( "fail: " + resp );
            return false;
        } else if( resp.getCount() > 0 ) {
            getLogger().info( "returnCode:"+ resp );
        } else if( getLogger().isDebugEnabled() ){
            getLogger().debug( resp.getMessage() );
        }
        return true;
    }

    public boolean logResponseMessage( PRIOut resp){
        if( resp == null ){
            getLogger().info( "resp==null" );
            return false;
        }
        return logResponseMessage( resp.getReturnCode() );
    }

    public int checkResponseMessage( PRIOut resp){
        if( resp == null ){
            getLogger().error( " resp=null" );
            return NO_RESP;
        }
        logResponseMessage( resp.getReturnCode() );
        return ( resp.getReturnCode().isRejected() ) ? FAIL_RESP : OK_RESP;
    }

}