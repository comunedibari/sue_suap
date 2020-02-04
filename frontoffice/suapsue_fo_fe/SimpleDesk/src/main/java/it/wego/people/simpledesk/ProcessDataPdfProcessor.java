package it.wego.people.simpledesk;

import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.collect.Collections2;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.io.Files;
import com.google.common.primitives.Ints;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.wego.people.simpledesk.processdata.CampiWrapper.Campi;
import it.wego.people.simpledesk.processdata.Href;
import it.wego.people.simpledesk.processdata.HrefList;
import it.wego.people.simpledesk.processdata.HrefRecord;
import it.wego.xml.XmlUtils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathExpressionException;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.SerializationUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 *
 * @author aleph
 */
public class ProcessDataPdfProcessor {

    public static final String HIDDEN_METADATA = "processDataHiddenMetadata",
            HIDDEN_METADATA_MATCH = HIDDEN_METADATA + "Match",
            HIDDEN_METADATA_INFO = HIDDEN_METADATA + "Info",
            INFO_DATE = "date";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final XmlUtils xmlUtils;
    private final Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
    private static final File tempDir = Files.createTempDir(),
            inputXmlDump = new File(tempDir, "processData_in.xml"),
            outputXmlDump = new File(tempDir, "processData_out.xml"),
            inputPdfDump = new File(tempDir, "moduloCompilabile_in.pdf"),
            outputPdfDump = new File(tempDir, "moduloCompilabile_out.pdf");

//    private ProcessDataPdfBuilder(ProcessDataPdfBuilder processDataPdfBuilder) {
//        this.xPathFactory = processDataPdfBuilder.xPathFactory;
//        this.xPath = processDataPdfBuilder.xPath;
//        this.xpathExpressions = processDataPdfBuilder.xpathExpressions;
//        this.jaxbContext = processDataPdfBuilder.jaxbContext;
//        this.jaxbUnmarshaller = processDataPdfBuilder.jaxbUnmarshaller;
//        this.jaxbMarshaller = processDataPdfBuilder.jaxbMarshaller;
//    }
    public ProcessDataPdfProcessor() {
        try {
            xmlUtils = XmlUtils.getInstance();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    private static final Comparator<it.wego.people.simpledesk.processdata.CampiWrapper.Campi> rowColOrderComparator = new Comparator<it.wego.people.simpledesk.processdata.CampiWrapper.Campi>() {
        public int compare(Campi o1, Campi o2) {
            return ComparisonChain.start().compare(o1.getRiga(), o2.getRiga()).compare(o1.getPosizione(), o2.getPosizione()).result();
        }
    }, colOrderComparator = new Comparator<it.wego.people.simpledesk.processdata.CampiWrapper.Campi>() {
        public int compare(Campi o1, Campi o2) {
            return o1.getPosizione().compareTo(o2.getPosizione());
        }
    };

    private static Predicate<it.wego.people.simpledesk.processdata.CampiWrapper.Campi> getRowFilterpredicate(final int row) {
        return new Predicate<it.wego.people.simpledesk.processdata.CampiWrapper.Campi>() {
            public boolean apply(Campi input) {
                return input.getRiga().intValue() == row;
            }
        };
    }

    public byte[] fillXmlFromPdf(byte[] xmlIn, byte[] pdf, boolean forzaCaricamento) throws Exception {
        if (logger.isDebugEnabled()) {
            Files.write(xmlIn, inputXmlDump);
            Files.write(pdf, inputPdfDump);
            logger.debug("dumped input pdf : {}, xml : {}", inputPdfDump, inputXmlDump);
        }
        Validate.isTrue(xmlIn.length > 0, "got empty xml file");
        Validate.isTrue(pdf.length > 0, "got empty pdf file");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        fillXmlFromPdf(new ByteArrayInputStream(xmlIn), new ByteArrayInputStream(pdf), out, forzaCaricamento);
        byte[] res = out.toByteArray();
        if (logger.isDebugEnabled()) {
            Files.write(res, outputXmlDump);
            logger.debug("dumped output xml : {}", outputXmlDump);
        }
        return res;
    }

    public void fillXmlFromPdf(InputStream xmlIn, InputStream pdf, OutputStream xmlOut, boolean forzaCaricamento) throws Exception {
        Document document = xmlUtils.readXml(xmlIn);
        PdfFormReader reader = new PdfFormReader(pdf);
        fillXmlFromPdf(document, reader, forzaCaricamento);
        reader.close();
        xmlUtils.writeXml(document, xmlOut);
    }

    public void fillXmlFromPdf(Document document, PdfFormReader pdfReader, boolean forzaCaricamento) throws Exception {
        if (!forzaCaricamento) {
            Validate.isTrue(getDataMatch(document, pdfReader), "il documento pdf non e' compatibile con i dati di sessione (processData)");
        }
//        if (!getDataMatch(document, pdfReader)) {
//             new Exception("Errore: il documento pdf non e' compatibile con i dati di sessione (processData)");
//        } else {
        Map infoData = getInfoData(pdfReader);
        Map<String, String> pdfFields = pdfReader.readPdfFields();
        { // copying to treemap for ordered reading
            Map map = Maps.newTreeMap();
            map.putAll(infoData);
            infoData = map;
            map = Maps.newTreeMap();
            map.putAll(pdfFields);
            pdfFields = map;
        }
        if (logger.isDebugEnabled()) {
            logger.debug("pdf info data : {}", gson.toJson(infoData));
            logger.debug("pdf field values : {}", gson.toJson(pdfFields));
        }
        HrefList listaHref = extractListaHref(document);
        List<HrefRecord> hrefList = listaHref.getEntry();
        Map<String, Campi> fieldMap = Maps.newHashMap();
        Map<String, HrefRecord> hrefMap = Maps.newHashMap();
        for (HrefRecord hrefRoot : hrefList) {
            String href = hrefRoot.getKey();
            logger.debug("preparing href {}", href);
            List<Campi> campi = hrefRoot.getValue().getCampi().getCampi();
            expandMultipleCampi(campi);
            for (Campi campo : campi) {
                String field = campo.getNome();
                String fieldId = getFieldId(href, field);
                fieldMap.put(fieldId, campo);
                hrefMap.put(fieldId, hrefRoot);
            }
        }
        for (Entry<String, String> field : pdfFields.entrySet()) {
            String key = field.getKey();
            Campi campo = fieldMap.get(key);
//            if (campo == null && key.matches("^.*_[0-9]+$")) {
//                String singleKey = key.replaceFirst("_[0-9]+$", "");
//                Campi singleField = fieldMap.get(singleKey);
//                if (singleField != null) { // campo multiplo
////                    if (field.getValue() != null) {
//                    logger.debug("adding multiple field instance {}", key);
//                    int mulIndex = Integer.parseInt(key.replaceFirst(".*_([0-9])+$", "$1"));
//                    List<Campi> campi = hrefMap.get(singleKey).getValue().getCampi().getCampi();
//                    List<List<Campi>> rows = getRows(campi);
//                    int mulSpan = Iterables.indexOf(Lists.reverse(rows), firstMultipleRowPredicate) - Iterables.indexOf(rows, multipleRowPredicate) + 1; //TODO optimize
//                    campo = cloneCampoMultiplo(singleField, mulIndex, mulSpan);
//                    campi.add(campi.indexOf(Iterables.getLast(Iterables.find(Lists.reverse(rows), multipleRowPredicate, null))) + 1, campo);
////                    } else {
////                        logger.debug("skipping empty multiple field instance {}", key);
////                        continue;
////                    }
//                }
//            }
            if (campo == null) {
                logger.warn("missing xml field for pdf field {}", key);
            } else {
                String value = field.getValue();
//                logger.debug("loaded value {}={}", key, value);
                if (campo.getTipo().equals("L")) {
                    String valueCode = campo.getOpzioniCombo().getComboAsReverseMap().get(value);
                    if (valueCode == null) {
                        logger.warn("unable to reverse combo code for value = '{}' in field = {}", value, field);
                    } else {
                        campo.setValoreUtente(valueCode);
//                        logger.debug("(combo value = {} )", valueCode);
                    }
                } else {
                    campo.setValoreUtente(Strings.nullToEmpty(value));
                }
            }
        }
        for (HrefRecord hrefRoot : hrefList) {
            shrinkMultipleCampi(hrefRoot.getValue().getCampi().getCampi());
            updateHrefMultiMeta(hrefRoot.getValue());
        }
        mergeListaHref(listaHref, document);
//        }
    }

    public void updateHrefMultiMeta(Href href) {
        if (!Iterables.any(href.getCampi().getCampi(), MultipleCampoPredicate.INSTANCE)) {
            return;
        }
        List<Campi> campi = Lists.newArrayList(href.getCampi().getCampi());
        Collections.sort(campi, rowColOrderComparator);
        href.setFirstRowCampoMultiplo(Iterables.find(campi, MultipleCampoPredicate.INSTANCE, null).getRiga());
        Campi lastMul = Iterables.find(Lists.reverse(campi), MultipleCampoPredicate.INSTANCE, null);
        href.setLastRowCampoMultiplo(lastMul.getRiga());
        href.setNumSezioniMultiple(lastMul.getMolteplicita());
        href.setRowCount(Iterables.getLast(campi).getRiga());
    }

    public void expandMultipleCampi(List<Campi> campi) throws Exception {
        List<List<Campi>> rows = getRows(campi);
        expandMultipleRows(rows);
        campi.clear();
        campi.addAll(getCampi(rows));
    }

    public void expandMultipleRows(List<List<Campi>> rows) throws Exception {
////        if (rows == null) {
////            rows = Lists.newLinkedList(getRows(campi));
////        }
//        int fromIndex = Iterables.indexOf(rows, multipleRowPredicate);
//        if (fromIndex >= 0) {
//            final int mulColNum = 5;
//
//            int toIndex = rows.size() - Iterables.indexOf(Lists.reverse(rows), firstMultipleRowPredicate),
//                    appendIndex = rows.size() - Iterables.indexOf(Lists.reverse(rows), multipleRowPredicate),
//                    iterSize = toIndex - fromIndex,
//                    iterCount = ((appendIndex) - fromIndex) / iterSize;
//            logger.debug("got multiple rows, {}-{},[{}x{}]..{} ", new Object[]{fromIndex, toIndex, iterSize, iterCount, appendIndex});
////            List<List<Campi>> multipleRows = rows.subList(fromIndex, toIndex);
////            rows = Lists.newLinkedList(rows);
//            if (logger.isDebugEnabled() && iterCount < mulColNum) {
//                logger.debug("adding {} ({}x{}) more rows", new Object[]{(mulColNum - iterCount) * iterSize, mulColNum - iterCount, iterSize});
//            }
//            for (int i = iterCount; i < mulColNum; i++) {
//                List<List<Campi>> newRows = Lists.newArrayList();
//                for (List<Campi> row : rows.subList(fromIndex, toIndex)) {
//                    List<Campi> newRow = Lists.newArrayList();
//                    for (Campi campo : row) {
//                        newRow.add(cloneCampoMultiplo(campo, i, iterSize));
//                    }
//                    newRows.add(newRow);
//                }
//                rows.addAll(appendIndex, newRows);
//                appendIndex += iterSize;
//            }
//            //  fix next rows height
//            int indexShift = (iterCount - mulColNum) * iterSize;
//            for (List<Campi> row : rows.subList(appendIndex, rows.size())) {
//                for (Campi campo : row) {
//                    campo.setRiga(campo.getRiga() + indexShift);
//                }
//            }
//        }
    }

    public void shrinkMultipleCampi(final List<Campi> campi) throws Exception {
        //TODO remove empty multiple fields . . 
//        final List<List<Campi>> rows = getRows(campi);
//        Iterator<List<List<Campi>>> iterator = new Iterator<List<List<Campi>>>() {
//
//            private int currentIndex = rows.size();
//            private Integer currentBlockMul, currentBlockHead, blockSize;
//            private List<List<Campi>> currentBlock = null;
//
//            private void findNext() {
//                while (currentIndex > 0) {
//                    currentIndex--;
//                    List<Campi> row = rows.get(currentIndex);
//                    if (!Iterables.any(row, MultipleCampoPredicate.INSTANCE)) {
//                        continue;
//                    }
//                    if (Iterables.any(row, FirstMultipleCampoPredicate.INSTANCE)) {
//                        break;
//                    }
//                    int tail = currentIndex;
//                    currentBlockMul = row.get(0).getMolteplicita();
//                    while (currentBlockMul == rows.get(currentIndex - 1).get(0).getMolteplicita()) {
//                        currentIndex--;
//                    }
//                    currentBlockHead = currentIndex;
//                    blockSize = tail - currentBlockHead + 1;
//                    currentBlock = rows.subList(currentBlockHead, currentBlockHead + blockSize);
//                }
//            }
//
//            public boolean hasNext() {
//                currentBlock = null;
//                findNext();
//                return currentBlock != null;
//            }
//
//            public List<List<Campi>> next() {
//                return currentBlock;
//            }
//
//            public void remove() {
//                logger.debug("removing empty multiple block {} [{}]", currentBlockHead, blockSize);
//                campi.removeAll(getCampi(currentBlock));
//            }
//        };
//        while (iterator.hasNext()) {
//            List<List<Campi>> block = iterator.next();
//            if (Iterables.all(getCampi(block), EmptyCampoPredicate.INSTANCE)) {
//                iterator.remove();
//            }else{
//                break;
//            }
//        }
    }
    private static final String XPATH_HREF_LIST = "/ProcessData/listaHref";

//    private static Campi cloneCampoMultiplo(Campi campo, Iterable<Campi> campiMultipli, Iterable<Campi> campiSingoli, int index, int mulSpan) throws Exception {
//        final Campi clone = (Campi) BeanUtils.cloneBean(campo);
//        clone.setMolteplicita(index + 1);
//        clone.setNome(clone.getNome() + "_" + (index + 1));
//        clone.setValoreUtente(null);
//        clone.setRiga(clone.getRiga() + (index * mulSpan));
//
////        if (!Strings.isNullOrEmpty(clone.getCampoCollegato()) && Iterables.any(campiMultipli, new Predicate<Campi>() {
////
////            public boolean apply(Campi input) {
////                return Objects.equal(input.getNome(), clone.getCampoCollegato());
////            }
////        })) {
////            clone.setCampoCollegato(clone.getCampoCollegato() + "_" + (index + 1));
////        }
////        for(Campi campoSingolo:campiSingoli){
////            if()
////        }
//
//        return clone;
//    }
    public HrefList extractListaHref(Document document) throws ExecutionException, XPathExpressionException, JAXBException {
        return xmlUtils.extractJaxbObject(HrefList.class, XPATH_HREF_LIST, document);
    }

    public void mergeListaHref(HrefList listaHref, Document document) throws ExecutionException, XPathExpressionException, JAXBException {
        xmlUtils.mergeJaxbObject(listaHref, XPATH_HREF_LIST, document);
    }

    public static String getFieldId(String href, String field) {
        return href + "-" + field;
    }

    private boolean getDataMatch(Document document, PdfFormReader pdfReader) throws ExecutionException, XPathExpressionException {
        String pdfData = pdfReader.getHiddenData(HIDDEN_METADATA_MATCH), xmlData = getTestData(document);
        if (Objects.equal(pdfData, xmlData)) {
            return true;
        } else {
            logger.warn("xml metadata does not match with pdf metadata : \n\txml : '{}'\n\tpdf : '{}'", xmlData, pdfData);
            return false;
        }
    }

    private String getTestData(Document document) throws ExecutionException, XPathExpressionException {
//        List<String> codes = Lists.newArrayList(xmlUtils.getStrings("/ProcessData/interventi/interventi/codice", document));
//        Collections.sort(codes);
//        String intCodes = StringUtils.join(codes, ",");
//        return intCodes;
        return "identificatoreProcedimento:" + xmlUtils.getString("/ProcessData/identificatorePeople/identificatoreProcedimento", document);

    }

    private void addTestData(Document document, PdfFormBuilder pdfBuilder) throws ExecutionException, XPathExpressionException {
        pdfBuilder.addHiddenData(HIDDEN_METADATA_MATCH, getTestData(document));
    }

    private void addInfoData(Document document, PdfFormBuilder pdfBuilder) throws ExecutionException, XPathExpressionException {
        Map info = Maps.newHashMap();
        info.put(INFO_DATE, new Date());
        pdfBuilder.addHiddenData(HIDDEN_METADATA_INFO, Base64.encodeBase64String(SerializationUtils.serialize((Serializable) info)));
    }

    private Map getInfoData(PdfFormReader pdfReader) {
        return (Map) SerializationUtils.deserialize(Base64.decodeBase64(pdfReader.getHiddenData(HIDDEN_METADATA_INFO)));
    }

    public byte[] xml2pdf(byte[] xml) throws Exception {
        if (logger.isDebugEnabled()) {
            Files.write(xml, inputXmlDump);
            logger.debug("dumped input xml : {}", inputXmlDump);
        }
        Validate.isTrue(xml.length > 0, "got empty xml file");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        xml2pdf(new ByteArrayInputStream(xml), out);
        byte[] res = out.toByteArray();
        if (logger.isDebugEnabled()) {
            Files.write(res, outputPdfDump);
            logger.debug("dumped output pdf : {}", outputPdfDump);
        }
        return res;
    }

    public void xml2pdf(InputStream inputStream, OutputStream outputStream) throws Exception {

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        final Document document = documentBuilder.parse(inputStream);

        PdfFormBuilder pdfBuilder = new PdfFormBuilder(outputStream);
        pdfBuilder.beginTable(1);

        addTestData(document, pdfBuilder);
        addInfoData(document, pdfBuilder);

        {
            pdfBuilder.beginTable(1).appendHtmlCell("<h1>DOMANDA</h1>");
            for (Node node : xmlUtils.getNodes("/ProcessData/interventi/interventi/descrizione", document)) {
                pdfBuilder.appendHtmlCell("<h1><i>" + node.getTextContent() + "</i></h1>");
            }
            pdfBuilder.endTable();
        }

        {
            pdfBuilder.beginTable(1).appendHtmlCell("<h2>dichiara</h2>").appendHtmlCell("<i>(compilare)</i>");
//            pdfBuilder.getStylesHtml().loadStyle("body", HtmlTags.TEXTALIGN, HtmlTags.ALIGN_JUSTIFIED_ALL);
            PdfFormBuilder hrefPdfBuilder = pdfBuilder.fork();
            List<HrefRecord> hrefList = Lists.newArrayList(extractListaHref(document).getEntry());
            Collections.sort(hrefList, new Comparator<it.wego.people.simpledesk.processdata.HrefRecord>() {
                private final Cache<String, Integer> hrefOrd = CacheBuilder.newBuilder().concurrencyLevel(1).build(new CacheLoader<String, Integer>() {
                    private final List<String> listHrefOrd = Lists.newArrayList(xmlUtils.getStrings("/ProcessData/listaHrefOrdered/listaHrefOrdered", document));

                    @Override
                    public Integer load(String key) throws Exception {
                        return listHrefOrd.indexOf(key);
                    }
                });

                public int compare(HrefRecord o1, HrefRecord o2) {
                    int i1 = hrefOrd.getUnchecked(o1.getKey()), i2 = hrefOrd.getUnchecked(o2.getKey());
                    return Ints.compare(i1, i2);
                }
            });

            for (HrefRecord hrefRoot : hrefList) {
                Href href = hrefRoot.getValue();
                List<Campi> campi = Lists.newArrayList(href.getCampi().getCampi());
                hrefPdfBuilder.beginTable(1, !Iterables.any(campi, RadioPredicate.INSTANCE)); //Workaround for radio field limitations (unable to break radio on multiple pages)
                String hrefCode = href.getHref(), hrefDesc = href.getDescrizione();
                logger.debug("rendering href {}", hrefCode);
                hrefPdfBuilder.appendHtmlCell("<i>(" + hrefCode + ")</i> <h3>" + hrefDesc + "</h3>").endTableRow();
                List<List<Campi>> rows = getRows(campi);
                expandMultipleRows(rows);
//                List<Campi> firsMultipleRow = Iterables.find(rows, multipleRowPredicate, null),
//                        lastMultipleRow = firsMultipleRow == null ? null : Iterables.find(Lists.reverse(rows), multipleRowPredicate, null),
//                        lastMultipleRowFirstIter = firsMultipleRow == null ? null : Iterables.find(Lists.reverse(rows), firstMultipleRowPredicate, null);
//                final int mulColNum = 5;
//                if (firsMultipleRow != null) {
//                    int fromIndex = rows.indexOf(firsMultipleRow),
//                            toIndex = rows.indexOf(lastMultipleRowFirstIter) + 1,
//                            appendIndex = rows.indexOf(lastMultipleRow) + 1,
//                            iterSize = toIndex - fromIndex,
//                            iterCount = ((appendIndex + 1) - fromIndex) / iterSize;
//                    logger.debug("got multiple rows, {} --{}-> {} [{}]", new Object[]{fromIndex, toIndex, appendIndex, iterSize});
//                    List<List<Campi>> multipleRows = rows.subList(fromIndex, toIndex);
//                    rows = Lists.newLinkedList(rows);
//                    for (int i = iterCount; i < mulColNum; i++) {
//                        List<List<Campi>> newRows = Lists.newArrayList();
//                        for (List<Campi> row : multipleRows) {
//                            List<Campi> newRow = Lists.newArrayList();
//                            for (Campi campo : row) {
//                                newRow.add(cloneCampoMultiplo(campo, i, iterSize));
//                            }
//                            newRows.add(newRow);
//                        }
//                        rows.addAll(appendIndex, newRows);
//                        appendIndex += iterSize;
//                    }
//                    // TODO fix next rows height
//                }
                for (List<Campi> row : rows) {
                    int minPos = Iterables.getFirst(row, null).getPosizione(), maxPos = Iterables.getLast(row).getPosizione(), rowWidth = maxPos - minPos + 1;
                    hrefPdfBuilder.beginTable(rowWidth);
                    for (int i = 0; i < row.size(); i++) {
                        it.wego.people.simpledesk.processdata.CampiWrapper.Campi campo = row.get(i);
                        char type = campo.getTipo().toUpperCase().charAt(0);
                        String desc = campo.getDescrizione(),
                                id = getFieldId(hrefCode, campo.getNome()),
                                value = Strings.nullToEmpty(campo.getValore()),
                                userValue = Strings.nullToEmpty(campo.getValoreUtente());
                        boolean checked = !userValue.isEmpty() && userValue.equals(value);
                        int cellPos = campo.getPosizione(), cellWidth = cellPos == maxPos ? 1 : (row.get(i + 1).getPosizione() - cellPos);
                        hrefPdfBuilder.setCellSpan(cellWidth, 1);
                        switch (type) {
                            case 'I':
                                hrefPdfBuilder.appendTextField(desc, id, userValue.isEmpty() ? value : userValue);
                                break;
                            case 'T':
                                hrefPdfBuilder.appendHtmlCell(desc);
                                break;
                            case 'C':
                                hrefPdfBuilder.appendCheckboxField(desc, id, value, checked, null);
                                break;
                            case 'R':
                                hrefPdfBuilder.appendRadioField(desc, id, value, checked, null);
                                break;
                            case 'L':
                                hrefPdfBuilder.appendComboField(desc, id, userValue.isEmpty() ? value : userValue, campo.getOpzioniCombo().getComboAsList());
                                break;
                            case 'A':
                                hrefPdfBuilder.appendTextField(desc, id, userValue.isEmpty() ? value : userValue);
                                break;
                            case 'N':
                                break;                                
                            default:
                                hrefPdfBuilder.appendHtmlCell("x:" + type);
                                logger.warn("unhandled href type : {}", type);
                        }
                    }
                    hrefPdfBuilder.endTable();
                }
                hrefPdfBuilder.resetCellSpan();
                if (!Strings.isNullOrEmpty(href.getPiedeHref())) {
                    hrefPdfBuilder.appendHtmlCell("<i>" + href.getPiedeHref() + "</i>");
                }
                hrefPdfBuilder.endTable();
            }
//            for (Node hrefRootNode : getNodes("/ProcessData/listaHref/entry", document)) {
//                Node hrefNode=getNode("./value",hrefRootNode);
//                String hrefCode=getString("./key",hrefRootNode);
//            
//                for (Node fieldNode : getNodes("./campi/campi/", hrefNode)) {
//                    
//                }
//
//                for (Node fieldNode : getNodes("./campi/campi", hrefNode)) {
//                    char type = getString("./tipo",fieldNode).toUpperCase().charAt(0);
//                    String desc=getString("./descrizione",fieldNode),id= hrefCode+"-"+ getString("./nome",fieldNode),value=getString("./valore",fieldNode);
//                    switch (type) {
//                        case 'T':
//                            pdfBuilder.appendTextField(desc,id,value);
//                            break;
//                        default:
//                            logger.warn("unhandled href type : {}",type); 
//                    }
//                }
//            }
            pdfBuilder.endTable();
        }

        {
            pdfBuilder.beginTable(1).appendHtmlCell("<h2>altre dichiarazioni</h2>");
//            pdfBuilder.getStylesHtml().loadStyle("body", HtmlTags.TEXTALIGN, HtmlTags.ALIGN_JUSTIFIED_ALL);
            for (Node node : xmlUtils.getNodes("/ProcessData/listaDichiarazioniStatiche/entry/value", document)) {
                logger.info("processing static dich node {}", node);
                pdfBuilder.appendHtmlCell("<h3>" + xmlUtils.getString("./titolo", node) + "</h3>").appendHtmlCell(xmlUtils.getString("./descrizione", node));
            }
            pdfBuilder.endTable();
        }

        {
            pdfBuilder.beginTable(1).appendHtmlCell("<h2>allegati richiesti</h2>");
            for (Node node : xmlUtils.getNodes("/ProcessData/listaDocRichiesti/entry/value/descrizione", document)) {
                pdfBuilder.appendHtmlCell(node.getTextContent());
            }
            pdfBuilder.endTable();
        }


//        SAXParserFactory factory = SAXParserFactory.newInstance();
//        SAXParser saxParser = factory.newSAXParser();
//        
//        DefaultHandler handler = new DefaultHandler() {
//            @Override
//            public void startElement(String uri, String localName,String qName, Attributes attributes) throws SAXException {
//                this.
//            }
//        };
//        
//        saxParser.parse(inputStream, handler);

        pdfBuilder.close(PdfFormBuilder.CLOSE_OUTPUTSTREAM);
    }

//    public int getRowMin(List<Campi> campi) {
//        return Collections.min(campi, rowColOrderComparator).getPosizione().intValue();
//    }
//
//    public int getRowMax(List<Campi> campi) {
//        return Collections.max(campi, rowColOrderComparator).getPosizione().intValue();
//    }
//
//    public int getRowWidth(List<Campi> campi) {
//        return getRowMax(campi) - getRowMin(campi);
//    }
//    public Iterable<Campi> getRow(List<Campi> campi, int rowIndex) {
//        return (Collections2.filter(campi, getRowFilterpredicate(rowIndex)));
//    }
    public List<List<Campi>> getRows(List<Campi> campiList) {
        final List<Campi> campi = Lists.newArrayList(campiList);
        Collections.sort(campi, rowColOrderComparator);
        return Lists.newArrayList(Iterables.transform(Sets.newTreeSet(Iterables.transform(campi, new Function<Campi, Integer>() {
            public Integer apply(Campi input) {
                return input.getRiga().intValue();
            }
        })), new Function<Integer, List<Campi>>() {
            public List<Campi> apply(Integer rowIndex) {
                return Lists.newArrayList(Collections2.filter(campi, getRowFilterpredicate(rowIndex)));
            }
        }));
    }

    public List<Campi> getCampi(List<List<Campi>> rows) {
        List<Campi> campi = Lists.newArrayList();
        for (List<Campi> row : rows) {
            campi.addAll(row);
        }
        return campi;
    }

//    private static final String DOC_HEADER_KEY = "itWegoPeopleSimpledeskProcessdata";
//    private void insertDocument(Document data, com.itextpdf.text.Document container) throws IOException, TransformerConfigurationException, TransformerException {
//        container.addHeader(DOC_HEADER_KEY, xmlUtils.packXml(data));
//    }
//    private Document extractDocument(PdfReader reader) {
//        return reader.getMetadata()
//    }
    private static enum RadioPredicate implements Predicate<Campi> {

        INSTANCE;

        public boolean apply(Campi input) {
            return Objects.equal(input.getTipo(), "R");
        }
    }
//    private static enum MultipleRowPredicate implements Predicate<List<Campi>> {
//
//        INSTANCE;
//
//        public boolean apply(List<Campi> input) {
//            return Iterables.any(input, MultipleCampoPredicate.INSTANCE);
//        }
//    }
//
//    private static enum FirstMultipleRowPredicate implements Predicate<List<Campi>> {
//
//        INSTANCE;
//
//        public boolean apply(List<Campi> input) {
//            return Iterables.any(input, MultipleCampoPredicate.INSTANCE);
//        }
//    }
    private static final Predicate<List<Campi>> multipleRowPredicate = any(MultipleCampoPredicate.INSTANCE),
            firstMultipleRowPredicate = any(FirstMultipleCampoPredicate.INSTANCE);

    private static Predicate<List<Campi>> any(final Predicate<Campi> predicate) {
        return new Predicate<List<Campi>>() {
            public boolean apply(List<Campi> input) {
                return Iterables.any(input, predicate);
            }
        };
    }

    private static enum MultipleCampoPredicate implements Predicate<Campi> {

        INSTANCE;
        private static final Integer ONE = Integer.valueOf(1);

        public boolean apply(Campi input) {
            return Objects.equal(input.getNumCampo(), ONE);
        }
    }

    private static enum FirstMultipleCampoPredicate implements Predicate<Campi> {

        INSTANCE;
        private static final Integer ONE = Integer.valueOf(1);

        public boolean apply(Campi input) {
            return Objects.equal(input.getNumCampo(), ONE) && Objects.equal(input.getMolteplicita(), ONE);
        }
    }

    private static enum EmptyCampoPredicate implements Predicate<Campi> {

        INSTANCE;

        public boolean apply(Campi input) {
            return Strings.isNullOrEmpty(input.getValoreUtente());
        }
    }
}
