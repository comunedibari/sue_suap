package it.wego.people.simpledesk;

import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.common.base.Supplier;
import com.google.common.collect.Maps;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.html.simpleparser.StyleSheet;
import com.itextpdf.text.pdf.BaseField;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfFormField;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.RadioCheckField;
import com.itextpdf.text.pdf.TextField;
import java.awt.Point;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author aleph
 */
public class PdfFormBuilder {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public static enum CloseOutputStream {

        CLOSE_OUTPUTSTREAM, LEAVE_OUTPUTSTREAM
    }
    public static final CloseOutputStream CLOSE_OUTPUTSTREAM = CloseOutputStream.CLOSE_OUTPUTSTREAM,
            LEAVE_OUTPUTSTREAM = CloseOutputStream.LEAVE_OUTPUTSTREAM;
    private final OutputStream outputStream;
    private final Document document;
    private final PdfWriter writer;
    private final Stack<PdfPTable> tableStack;
    private final CloneableStyleSheet stylesHtml;
//    private BaseColor fieldBackgroundColor = new BaseColor(255, 255, 204), fieldBorderColor = new BaseColor(255, 204, 0);
//    private int fieldFontSize = 12;
    private final Map<String, Object[]> radioGroups;
    private final Map<String, String> hiddenData;
    private Supplier<PdfPCell> cellSupplier = DefaultCellSupplier.INSTANCE;
    private Function<Integer, PdfPTable> tableBuilder = DefaultTableBuilder.INSTANCE;
    private Function<BaseField, BaseField> fieldInitializer = DefaultFieldInitializer.INSTANCE;
    private Function<PdfPCell, PdfPCell> fieldCellInitializer = DefaultFieldCellInitializer.INSTANCE;
    private Function<Rectangle, Rectangle> fieldCellResizer = DefaultFieldCellResizer.INSTANCE;

    public Function<Integer, PdfPTable> getTableBuilder() {
        return tableBuilder;
    }

    public Document getDocument() {
        return document;
    }

    public PdfFormBuilder setTableBuilder(Function<Integer, PdfPTable> tableBuilder) {
        this.tableBuilder = tableBuilder;
        return this;
    }

    public Supplier<PdfPCell> getCellSupplier() {
        return cellSupplier;
    }

    public PdfFormBuilder setCellSupplier(Supplier<PdfPCell> cellSupplier) {
        this.cellSupplier = cellSupplier;
        return this;
    }

    public StyleSheet getStylesHtml() {
        return stylesHtml;
    }

    public Function<BaseField, BaseField> getFieldInitializer() {
        return fieldInitializer;
    }

    public PdfFormBuilder setFieldInitializer(Function<BaseField, BaseField> fieldInitializer) {
        this.fieldInitializer = fieldInitializer;
        return this;
    }

    public Function<PdfPCell, PdfPCell> getFieldCellInitializer() {
        return fieldCellInitializer;
    }

    public PdfFormBuilder setFieldCellInitializer(Function<PdfPCell, PdfPCell> fieldCellInitializer) {
        this.fieldCellInitializer = fieldCellInitializer;
        return this;
    }

    public PdfFormBuilder setFieldCellResizer(Function<Rectangle, Rectangle> fieldCellResizer) {
        this.fieldCellResizer = fieldCellResizer;
        return this;
    }

    private PdfFormBuilder(final PdfFormBuilder root) {
        this.outputStream = root.outputStream;
        this.document = root.document;
        this.writer = root.writer;
        this.tableStack = root.tableStack;
        this.stylesHtml = root.stylesHtml.getClone();
//        this.fieldBackgroundColor = root.fieldBackgroundColor;
//        this.fieldBorderColor = root.fieldBorderColor;
//        this.fieldFontSize = root.fieldFontSize;
        this.cellSupplier = root.cellSupplier;
        this.tableBuilder = root.tableBuilder;
        this.fieldInitializer = root.fieldInitializer;
        this.fieldCellInitializer = root.fieldCellInitializer;
        this.radioGroups = root.radioGroups;
        this.fieldCellResizer = root.fieldCellResizer;
        this.hiddenData = root.hiddenData;
    }

    public PdfFormBuilder fork() {
        return new PdfFormBuilder(this);
    }

    public PdfFormBuilder() throws DocumentException {
        this(new ByteArrayOutputStream());
    }

    public PdfFormBuilder(OutputStream out) throws DocumentException {
        hiddenData = Maps.newLinkedHashMap();
        this.outputStream = out;
        document = new Document();
        writer = PdfWriter.getInstance(document, outputStream);
        document.open();

        tableStack = new Stack<PdfPTable>();
        stylesHtml = new CloneableStyleSheet();
//        stylesHtml.loadStyle("pdfFont", "size", "9px");
//        stylesHtml.loadStyle("pdfFont", "face", "arial");

        this.radioGroups = Maps.newHashMap();

//        radioFieldMap = CacheBuilder.newBuilder().concurrencyLevel(1).build(new CacheLoader<String, RadioCheckField>() {
//
//        @Override
//        public RadioCheckField load(String key) throws Exception {
//            RadioCheckField radioCheckField = new RadioCheckField(PdfFormBuilder.this.writer, null, key, DEFAULT_RADIOCHECK_VALUE);
//            initField(radioCheckField);
//
//            radioCheckField.setCheckType(RadioCheckField.TYPE_CIRCLE);
//            return radioCheckField;
//        }
//    });
//    radioFieldGroupsMap = CacheBuilder.newBuilder().concurrencyLevel(1).build(new CacheLoader<String, PdfFormField>() {
//
//        @Override
//        public PdfFormField load(String key) throws Exception {
//            return radioFieldMap.get(key).getRadioGroup(true, false);
////            PdfFormField radioGroup = radioFieldMap.get(key).getRadioGroup(true, false);
////            formFields.add(radioGroup);
////            return radioGroup;
//        }
//    });
    }

    public void close() throws DocumentException, IOException {
        close(LEAVE_OUTPUTSTREAM);
    }

    public void close(CloseOutputStream closeOutputStream) throws DocumentException, IOException {
        while (!tableStack.isEmpty()) {
            endTable();
        }
        flushHiddenData();
//        fluxRadio();
        document.close();
        writer.close();
        outputStream.flush();
        if (Objects.equal(closeOutputStream, CLOSE_OUTPUTSTREAM)) {
            outputStream.close();
        }
    }

    public byte[] getRawPdf() throws DocumentException, IOException {
        if (outputStream instanceof ByteArrayOutputStream) {
            close();
            return ((ByteArrayOutputStream) outputStream).toByteArray();
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public PdfPTable getTable() {
        return tableStack.peek();
    }

    public PdfFormBuilder beginTable(PdfPTable table) throws DocumentException {
        if (!tableStack.isEmpty()) {
            tableStack.peek().addCell(table);
        }
        tableStack.push(table);
        return this;
    }

    public PdfPTable getCurrentTable() {
        return tableStack.peek();
    }

    public PdfFormBuilder beginTable(int colNum) throws DocumentException {
        return beginTable(tableBuilder.apply(colNum));
    }

    public PdfFormBuilder beginTable(int colNum, boolean allowPageBreak) throws DocumentException {
        PdfPTable table = tableBuilder.apply(colNum);
        if (!allowPageBreak) {
            table.setKeepTogether(true);
            table.setSplitRows(false);
        }
        return beginTable(table);
    }

    public PdfFormBuilder endTableRow() throws DocumentException {
        tableStack.peek().completeRow();
        return this;
    }

    public PdfFormBuilder fillTable(int cellNum) throws DocumentException {
        PdfPTable table = tableStack.peek();
        for (int i = 0; i < cellNum; i++) {
            table.addCell(table.getDefaultCell());
        }
        return this;
    }

    public PdfFormBuilder endTable() throws DocumentException {
        PdfPTable table = tableStack.pop();
        table.completeRow();
        if (tableStack.isEmpty()) {
            document.add(table);
        }
//        if (tableStack.size() > 1) {
//            tableStack.peek().addCell(table);
//        } else {
//            document.add(table);
//        }
        return this;
    }

//    public PdfFormBuilder fluxRadio() {
//        for (PdfFormField field : radioGroups.values()) {
//            writer.addAnnotation(field);
//        }
//        radioGroups.clear();
//        return this;
//    }
    public PdfFormBuilder appendField(String fieldLabel, PdfPCell field) throws Exception {
//        return beginTable(2).appendHtmlCell(fieldLabel).appendCell(field).endTable();
        return beginTable(new PdfPTable(2)).appendHtmlCell(fieldLabel).appendCell(field).endTable();
//        PdfPTable table = tableStack.peek();
//        table.addCell(fieldLabel);
//        table.addCell(field);        
//        return this.endTable();
    }

    public PdfFormBuilder appendRadioCheckField(String fieldLabel, PdfPCell field, final float fieldSize) throws Exception {
//        return beginTable(2).appendHtmlCell(fieldLabel).appendCell(field).endTable();
//         PdfPTable table = new PdfPTable(2);
        PdfPTable table = new PdfPTable(2) {

            @Override
            protected void calculateWidths() {
                super.calculateWidths();
                if (totalWidth > 0) {
                    absoluteWidths[0] += absoluteWidths[1] - fieldSize;
                    absoluteWidths[1] = fieldSize;
                    calculateHeights();
                }
            }
        };
        fork().beginTable(table).setCellSupplier(new Supplier<PdfPCell>() {

            public PdfPCell get() {
                logger.debug("aligning right radio/check cell label");
                PdfPCell cell = PdfFormBuilder.this.cellSupplier.get();
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                return cell;
            }
        }).appendHtmlCell(fieldLabel).setCellSupplier(PdfFormBuilder.this.cellSupplier).appendCell(field).endTable();
        return this;
//        PdfPTable table = tableStack.peek();
//        table.addCell(fieldLabel);
//        table.addCell(field);        
//        return this.endTable();
    }

    public PdfFormBuilder appendTextField(String fieldLabel, String fieldName, String fieldValue) throws Exception {
        return appendField(fieldLabel, buildFieldCell(buildTextField(fieldName, fieldValue)));
    }

    public PdfFormBuilder appendComboField(String fieldLabel, String fieldName, String fieldValue, List<String> comboFields) throws Exception {
        return appendField(fieldLabel, buildFieldCell(buildComboField(fieldName, fieldValue, comboFields)));
    }

    public PdfFormBuilder appendRadioField(String fieldLabel, String fieldName, String fieldValue, boolean checked, Float fieldSize) throws Exception {
        return appendRadioCheckField(fieldLabel, buildRadioField(fieldName, fieldValue, checked), Objects.firstNonNull(fieldSize, 15f));
    }

    public PdfFormBuilder appendCheckboxField(String fieldLabel, String fieldName, String fieldValue, boolean checked, Float fieldSize) throws Exception {
        return appendRadioCheckField(fieldLabel, buildCheckboxField(fieldName, fieldValue, checked), Objects.firstNonNull(fieldSize, 15f));
    }

    public PdfFormBuilder appendHtmlCell(String html) throws IOException {
        return appendCell(buildHtmlCell(html));
    }

    public PdfFormBuilder appendCell(PdfPCell cell) {
        cell.setColspan(cellSpan.x);
        cell.setRowspan(cellSpan.y);
        tableStack.peek().addCell(cell);
        return this;
    }
    private Point cellSpan = new Point(1, 1);

    public PdfFormBuilder setCellSpan(int colSpan, int rowSpan) {
        cellSpan = new Point(colSpan, rowSpan);
        return this;
    }

    public PdfFormBuilder resetCellSpan() {
        return setCellSpan(1, 1);
    }

    public PdfPCell buildHtmlCell(String html) throws IOException {
        return buildHtmlCell(stylesHtml, html);
    }

    public PdfPCell buildHtmlCell(StyleSheet stylesHtml, String html) throws IOException {
        PdfPCell cell = cellSupplier.get();
        StringReader sr = new StringReader(html);
        List<Element> objects = HTMLWorker.parseToList(sr, stylesHtml);

        for (int k = 0; k < objects.size(); ++k) {
            cell.addElement(objects.get(k));
        }
        return cell;
    }

    public <E extends BaseField> E initField(E field) {
        return (E) fieldInitializer.apply(field);
    }

    public PdfPCellEvent buildTextField(String fieldName, String fieldValue) {
        TextFieldHelper textFieldHelper = new TextFieldHelper(fieldName);
        initField(textFieldHelper);
//        textFieldHelper.setBorderColor(fieldBorderColor);
        textFieldHelper.setOptions(TextField.MULTILINE);
//        textFieldHelper.setBackgroundColor(fieldBackgroundColor);
//        textFieldHelper.setFontSize(fieldFontSize);
        //textFieldHelper.setBorderWidth(1);
//        textFieldHelper.setBorderStyle(PdfBorderDictionary.STYLE_SOLID);
        if (fieldValue != null) {
            textFieldHelper.setText(fieldValue);
        }
        return textFieldHelper;
    }

    public PdfPCellEvent buildComboField(String fieldName, String fieldValue, List<String> comboFields) {
        TextFieldHelper textFieldHelper = new TextFieldHelper(fieldName, true);
        initField(textFieldHelper);
        textFieldHelper.setChoices(comboFields.toArray(new String[comboFields.size()]));
        if (!Strings.isNullOrEmpty(fieldValue)) {
            int index = comboFields.indexOf(fieldValue);
            if (index == -1) {
                logger.warn("faulty combo, given field value {} not within valid values {}", fieldValue, StringUtils.join(comboFields, ","));
            } else {
//            Validate.isTrue(index >= 0, "broken combo, given field value not found");
                textFieldHelper.setChoiceSelection(index);
            }
        }
        return textFieldHelper;
    }

    public PdfPCell buildFieldCell(PdfPCellEvent helper) {
        return fieldCellInitializer.apply(buildCell(helper));
    }

    public PdfPCell buildCell(PdfPCellEvent helper) {
        PdfPCell cell = cellSupplier.get();
        cell.setCellEvent(helper);
        return cell;
    }
//    private final List<PdfFormField> formFields=Lists.newArrayList();
//    private final Cache<String, PdfFormField> radioFieldGroupsMap;
//    private final Cache<String, RadioCheckField> radioFieldMap;
//    private final Cache<String, RadioCheckField> radioFieldMap; = CacheBuilder.newBuilder().concurrencyLevel(1).build(new CacheLoader<String, RadioCheckField>() {
//
//        @Override
//        public RadioCheckField load(String key) throws Exception {
//            RadioCheckField radioCheckField = new RadioCheckField(PdfFormBuilder.this.writer, null, key, DEFAULT_RADIOCHECK_VALUE);
//            initField(radioCheckField);
//
//            radioCheckField.setCheckType(RadioCheckField.TYPE_CIRCLE);
//            return radioCheckField;
//        }
//    });
//    private final Cache<String, PdfFormField> radioFieldGroupsMap = CacheBuilder.newBuilder().concurrencyLevel(1).build(new CacheLoader<String, PdfFormField>() {
//
//        @Override
//        public PdfFormField load(String key) throws Exception {
//            return radioFieldMap.get(key).getRadioGroup(true, false);
////            PdfFormField radioGroup = radioFieldMap.get(key).getRadioGroup(true, false);
////            formFields.add(radioGroup);
////            return radioGroup;
//        }
//    });
    private final static String DEFAULT_RADIOCHECK_VALUE = "DEFAULT_RADIOCHECK_VALUE";

    public PdfPCell buildRadioField(String fieldName, String onValue, boolean checked) {
        return buildRadioCheckField(fieldName, onValue, checked, true);
    }

    public PdfPCell buildCheckboxField(String fieldName, String onValue, boolean checked) {
        return buildRadioCheckField(fieldName, onValue, checked, false);
    }

    public PdfPCell buildRadioCheckField(String fieldName, String onValue, boolean checked, boolean isRadio) {
        RadioCheckField radioCheckField = new RadioCheckField(writer, null, fieldName, Objects.firstNonNull(Strings.emptyToNull(onValue), DEFAULT_RADIOCHECK_VALUE));
        radioCheckField.setCheckType(isRadio ? RadioCheckField.TYPE_CIRCLE : RadioCheckField.TYPE_CROSS);
        radioCheckField.setChecked(checked);
        initField(radioCheckField);
        return buildFieldCell(new RadioCheckFieldHelper(radioCheckField, isRadio));
    }

    private class RadioCheckFieldHelper implements PdfPCellEvent {

//        private Function<Rectangle, Rectangle> resizer = PdfFormBuilder.this.fieldCellResizer;//getFixedSizeRectangleResizer(13);
        private Function<Rectangle, Rectangle> resizer = getFixedSizeRectangleResizer(13);
        private final RadioCheckField radioCheckField;
//        private final String onValue;
        private final Boolean isRadio;

        public RadioCheckFieldHelper(RadioCheckField radioCheckField, boolean isRadio) {
            this.radioCheckField = radioCheckField;
//            this.onValue = onValue;
//            this.checked = checked;
            this.isRadio = isRadio;
            if (isRadio) {
                Object[] radioGroupData = radioGroups.get(radioCheckField.getFieldName());
                if (radioGroupData == null) {
                    radioGroups.put(radioCheckField.getFieldName(), new Object[]{radioCheckField.getRadioGroup(true, false), 1});
                } else {
                    radioGroupData[1] = ((Integer) radioGroupData[1]) + 1;
                }
            }
        }

        public void cellLayout(PdfPCell cell, Rectangle position, PdfContentByte[] canvases) {
            try {
                radioCheckField.setBox(resizer.apply(position));
                if (isRadio) {
                    Object[] radioGroupData = radioGroups.get(radioCheckField.getFieldName());
                    PdfFormField radioGroup = (PdfFormField) radioGroupData[0];
                    radioGroupData[1] = ((Integer) radioGroupData[1]) - 1;
//                    //                    radioCheckField.setOnValue(onValue);
//                    //                    radioCheckField.setChecked(checked);
                    radioGroup.addKid(radioCheckField.getRadioField());
                    if (((Integer) radioGroupData[1]) == 0) {
                        writer.addAnnotation(radioGroup);
//                        radioGroups.remove(radioCheckField.getFieldName());
                    }
////                    radioFieldGroupsMap.getUnchecked(radioCheckField.getFieldName()).addKid(radioCheckField.getRadioField());
//                    PdfFormField radioGroup = radioCheckField.getRadioGroup(true, false);
//                    radioGroup.addKid(radioCheckField.getRadioField());
//                    writer.addAnnotation(radioGroup);
                } else {
                    writer.addAnnotation(radioCheckField.getCheckField());
                }
//                writer.addAnnotation(isRadio?radioCheckField.getRadioField():radioCheckField.getCheckField());
            } catch (Exception ex) {
                throw ex instanceof RuntimeException ? ((RuntimeException) ex) : new RuntimeException(ex);
            }
        }

        public Function<Rectangle, Rectangle> getResizer() {
            return resizer;
        }

        public void setResizer(Function<Rectangle, Rectangle> resizer) {
            this.resizer = resizer;
        }
    }

    private class TextFieldHelper extends TextField implements PdfPCellEvent {

        private final boolean isCombo;
        private Function<Rectangle, Rectangle> resizer = PdfFormBuilder.this.fieldCellResizer;

        public TextFieldHelper(String fieldName, boolean isCombo) {
            super(PdfFormBuilder.this.writer, null, fieldName);
            this.isCombo = isCombo;
        }

        public TextFieldHelper(String fieldName) {
            this(fieldName, false);
        }

        public void cellLayout(PdfPCell cell, Rectangle position, PdfContentByte[] canvases) {
            try {
                setBox(resizer.apply(position));
                getWriter().addAnnotation(isCombo ? this.getComboField() : this.getTextField());
            } catch (Exception ex) {
                throw ex instanceof RuntimeException ? ((RuntimeException) ex) : new RuntimeException(ex);
            }
        }
    }

//    private PdfPCell buildFieldCell(String label, final PdfPCellEvent fieldHelper, float rightPadding) {
//        final Function<Rectangle, Rectangle> 
//                labelPadder = getPaddedSpacedRectangleResizer(0, rightPadding, 0, 0), 
//                fieldPadder = getMarginSpacedRectangleResizer(null,rightPadding , null,null);
//        
//        PdfPCellEvent newPdfPCellEvent = new PdfPCellEvent() {
//
//            public void cellLayout(PdfPCell cell, Rectangle position, PdfContentByte[] canvases) {
//            }
//        };
//
//    }
    public Function<Rectangle, Rectangle> getFixedSizeRectangleResizer(final float size) {
        return new Function<Rectangle, Rectangle>() {

            public Rectangle apply(Rectangle input) {
                float x1 = input.getLeft() + (input.getWidth() - size) / 2,
                        x2 = x1 + size,
                        y1 = input.getBottom() + (input.getHeight() - size) / 2,
                        y2 = y1 + size;
                return new Rectangle(x1, y1, x2, y2);

            }
        };
    }

    public Function<Rectangle, Rectangle> getEqualPaddingRectangleResizer(final float padding) {
        return new Function<Rectangle, Rectangle>() {

            public Rectangle apply(Rectangle input) {
                return new Rectangle(input.getLeft() + padding, input.getBottom() + padding, input.getRight() - padding, input.getTop() - padding);

            }
        };
    }

    public void addHiddenData(String key, String data) {
        logger.debug("adding hidden data {}", key);
        hiddenData.put(key, data);
    }

//    public void addHiddenData(String key,byte[] data){
//        hiddenData.put(key,Base64.encodeBase64String(data));
//    }
    private void flushHiddenData() throws IOException, DocumentException {
        logger.debug("flushing hidden data, {} entries", hiddenData.size());
        for (Entry<String, String> data : hiddenData.entrySet()) {
            logger.debug("flushing hidden data {}", data.getKey());
            TextField field = new TextField(writer, new Rectangle(0, 0, 1, 1), PdfFormConstants.HIDDEN_DATA_PREFIX + data.getKey());
            field.setText(data.getValue());
            field.setVisibility(TextField.HIDDEN);
            writer.addAnnotation(field.getTextField());
        }
    }
//    private static class FixedSizeRectangleResizer implements Function<Rectangle, Rectangle> {
//
//        private final float size;
//
//        public FixedSizeRectangleResizer(float size) {
//            this.size = size;
//        }
//
//        public Rectangle apply(Rectangle input) {
//            float x1 = input.getLeft() + (input.getWidth() - size) / 2,
//                    x2 = x1 + size,
//                    y1 = input.getBottom() + (input.getHeight() - size) / 2,
//                    y2 = y1 + size;
//            return new Rectangle(x1, y1, x2, y2);
//
//        }
//    }
//    public Function<Rectangle, Rectangle> getPaddedSpacedRectangleResizer(final float topMargin, final float rightMargin, final float bottomMargin, final float leftMargin) {
//        return new Function<Rectangle, Rectangle>() {
//
//            public Rectangle apply(Rectangle input) {
//                return new Rectangle(input.getLeft() + leftMargin, input.getBottom() - bottomMargin, input.getRight() - rightMargin, input.getTop() + topMargin);
//            }
//        };
//    }
//    public Function<Rectangle, Rectangle> getMarginSpacedRectangleResizer(final Float topMargin, final Float rightMargin, final Float bottomMargin, final Float leftMargin) {
//        return new Function<Rectangle, Rectangle>() {
//
//            public Rectangle apply(Rectangle input) {
//                float w = input.getWidth(), h = input.getHeight(), x1 = input.getLeft(), x2 = input.getRight(), y1 = input.getBottom(), y2 = input.getTop();
//                if (rightMargin != null) {
//                    x1 += w - rightMargin;
//                }
//                if (leftMargin != null) {
//                    x2 -= w + leftMargin;
//                }
//                if (topMargin != null) {
//                    y2 -= h + topMargin;
//                }
//                if (bottomMargin != null) {
//                    y1 += h - bottomMargin;
//                }
//                return new Rectangle(x1, y1, x2, y2);
//            }
//        };
//    }

    public static enum DefaultCellSupplier implements Supplier<PdfPCell> {

        INSTANCE;

        public PdfPCell get() {
            PdfPCell cell = new PdfPCell();
            cell.setBorder(0);
            cell.setPadding(5f);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
            return cell;
        }
    };

    public static enum DefaultTableBuilder implements Function<Integer, PdfPTable> {

        INSTANCE;

        public PdfPTable apply(Integer colNum) {
            PdfPTable table = new PdfPTable(colNum);
            table.getDefaultCell().setPadding(2f);
            table.getDefaultCell().setBorder(0);
            table.setWidthPercentage(100);
            table.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.setSplitLate(false);
            return table;


        }
    };

    public static enum DefaultFieldInitializer implements Function<BaseField, BaseField> {

        INSTANCE;

        public BaseField apply(BaseField field) {
            field.setBackgroundColor(new BaseColor(255, 255, 204));
            field.setFontSize(12);
            field.setBorderColor(new BaseColor(255, 204, 0));
            field.setBorderWidth(0.5f);
            field.setAlignment(Element.ALIGN_CENTER);
            return field;
        }
    };

    public static enum DefaultFieldCellInitializer implements Function<PdfPCell, PdfPCell> {

        INSTANCE;

        public PdfPCell apply(PdfPCell cell) {
            cell.setPadding(0f);
            return cell;
        }
    };

    public static enum DefaultFieldCellResizer implements Function<Rectangle, Rectangle> {

        INSTANCE;
        private final float height = 24f;

        public Rectangle apply(Rectangle input) {
            if (input.getHeight() < height) {
                return input;
            }
            float hPadding = (input.getHeight() - height) / 2;
            return new Rectangle(input.getLeft(), input.getBottom() + hPadding, input.getRight(), input.getTop() - hPadding);

        }
    };
}
