package cross.cross_scripts.reports.personal_data_comparing;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
public class Report {
	
	private static final Logger logger = LogManager.getLogger(Report.class);
	Workbook workbook;
	Sheet currentSheet;
	int currentIndex = 0;
	static CellStyle boldStyleStatic;
	static CellStyle styleStatic ;

	public Report() {
		workbook = new HSSFWorkbook();
	}

	public void createSheet(String name) {
		currentSheet = workbook.createSheet(name);
		currentSheet.autoSizeColumn(0);
		currentIndex = 0;
		Row row = currentSheet.createRow(currentIndex++);
		int index = 0;
		CellStyle boldStyle = boldFontStyle(workbook);
		boldStyle.setWrapText(true);
		setHeaderCell(row, index, "campo", boldStyle);
		index++;

		setHeaderCell(row, index, "sono uguali?", boldStyle);
		index++;

		setHeaderCell(row, index, "Valore da db", boldStyle);
		index++;

		setHeaderCell(row, index, "Valore da xml", boldStyle);
		index++;

		setHeaderCell(row, index, "Extra", boldStyle);
		index++;
	}

	private void setHeaderCell(Row row, int index, String cellValue, CellStyle boldStyle) {
		Cell cell;
		cell = row.createCell(index);
		cell.setCellValue(cellValue);
		cell.setCellStyle(boldStyle);
		currentSheet.setColumnWidth(index, 8000);
	}

	public void createRow(String value, Object dataXml, Object dataDb) {
		this.createRow(value, dataXml, dataDb, null , null);
	}

	public void createRow(String value, Object dataXml, Object dataDb, BiPredicate<Object, Object> biEquals) {
		this.createRow(value, dataXml, dataDb, biEquals, null);
	}
	
	public void createRow(String value, Object dataXml, Object dataDb, BiPredicate<Object, Object> biEquals, BiFunction<Object, Object, String> extraInfo) {
		logger.info("value from Db:" + dataDb);
		logger.info("value from Xml:" + dataXml);
		if(styleStatic == null) {
			styleStatic = workbook.createCellStyle();
			styleStatic.setWrapText(true);
		}
		Row row = currentSheet.createRow(currentIndex++);
		int index = 0;
		row.createCell(index++).setCellValue(value);

		Cell cellAreEqual = row.createCell(index++);
		boolean equals = false;

		if(dataXml == null && dataDb == null) 
			equals= true;	
		else if(dataXml != null && dataDb != null) {
			if(biEquals != null)
				equals = biEquals.test(dataXml, dataDb);
			else if(dataXml instanceof String && dataDb instanceof String)
				equals = ((String)dataXml).equalsIgnoreCase(((String)dataDb));
			else
				equals = dataXml.equals(dataDb);
		}
		else if(dataXml == null && dataDb!= null && (dataDb.toString().isEmpty() || dataDb.toString().equals("{}"))) {
			equals = true;
		}
		else if(dataXml != null && dataDb== null && (dataXml.toString().isEmpty())) {
			equals = true;
		}
		else {
			equals = false;
		}
		cellAreEqual.setCellValue(equals);

		if(!equals) {
			Cell cellWrongDb = row.createCell(index++);
			if(dataDb != null) {
				if(dataDb.toString().length() > 0)
					cellWrongDb.setCellValue(dataDb.toString());
				else
					cellWrongDb.setCellValue("Empty value");
				cellWrongDb.setCellStyle(styleStatic);
			}
			Cell cellWrongXml = row.createCell(index++);
			if(dataXml != null) {
				if(dataXml.toString().length() > 0)
					cellWrongXml.setCellValue(dataXml.toString());
				else
					cellWrongXml.setCellValue("Empty value");
				cellWrongXml.setCellStyle(styleStatic);
			}
			
		}else {
			row.createCell(index++);
			row.createCell(index++);
		}
		if(extraInfo != null)
			row.createCell(index++).setCellValue(extraInfo.apply(dataXml, dataDb));
	}

	public void close(String filename) throws IOException {
		FileOutputStream fos = new FileOutputStream(filename);
		workbook.write(fos);
		fos.close();
	}

	public static CellStyle boldFontStyle(Workbook workbook) {
		Font boldFont = workbook.createFont();
		boldFont.setBold(true);

		if(boldStyleStatic == null) {
			boldStyleStatic = workbook.createCellStyle();
			boldStyleStatic.setFont(boldFont);
		}
		return boldStyleStatic;
	}
}
