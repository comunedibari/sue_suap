package cross.cross_scripts.reports.addresses_comparing;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.text.similarity.LevenshteinDistance;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import utils.AddressesComparator;
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
		setHeaderCell(row, index, "pratica", boldStyle);
		index++;

		setHeaderCell(row, index, "id counter", boldStyle);
		index++;

		setHeaderCell(row, index, "id anagrafica", boldStyle);
		index++;

		setHeaderCell(row, index, "Valore da db", boldStyle);
		index++;

		setHeaderCell(row, index, "Valore da xml", boldStyle);
		index++;
		
		setHeaderCell(row, index, "equivalenti?", boldStyle);
		index++;
		
		setHeaderCell(row, index, "levensthin?", boldStyle);
	}

	private void setHeaderCell(Row row, int index, String cellValue, CellStyle boldStyle) {
		Cell cell;
		cell = row.createCell(index);
		cell.setCellValue(cellValue);
		cell.setCellStyle(boldStyle);
		currentSheet.setColumnWidth(index, 8000);
	}

	public void createRow(int idPratica, Integer idCounter, Integer idAnagrafica, String addressXml, String addressDb) {

		logger.info("value from Db:" + addressDb);
		logger.info("value from Xml:" + addressXml);
		if(styleStatic == null) {
			styleStatic = workbook.createCellStyle();
			styleStatic.setWrapText(true);
		}
		
		boolean equals = false;

		if(addressXml == null && addressDb == null) 
			equals= true;	
		else if(addressXml != null && addressDb != null) {
			equals = addressXml.equalsIgnoreCase(addressDb);

		}
		else if(addressXml == null && addressDb!= null && (addressDb.toString().isEmpty() )) {
			equals = true;
		}
		else {
			equals = false;
		}
		

		if(!equals) {
			Row row = currentSheet.createRow(currentIndex++);
			int index = 0;
			row.createCell(index++).setCellValue(idPratica);
			row.createCell(index++).setCellValue(idCounter == null?-1:idCounter);
			row.createCell(index++).setCellValue(idAnagrafica == null?-1:idAnagrafica);

			Cell cellWrongXml = row.createCell(index++);
			
			Cell cellWrongDb = row.createCell(index++);
			if(addressDb != null) {
				if(addressDb.length() > 0)
					cellWrongDb.setCellValue(addressDb);
				else
					cellWrongDb.setCellValue("Empty value");
				cellWrongDb.setCellStyle(styleStatic);
			}
			if(addressXml != null) {
				if(addressXml.length() > 0)
					cellWrongXml.setCellValue(addressXml);
				else
					cellWrongXml.setCellValue("Empty value");
				cellWrongXml.setCellStyle(styleStatic);
			}
			if(addressDb != null && addressXml != null) {
				row.createCell(index++).setCellValue(evalAddresses(addressDb.toLowerCase(),addressXml.toLowerCase()));
				Integer v = LevenshteinDistance.getDefaultInstance().apply(addressDb.toLowerCase(),addressXml.toLowerCase());
				logger.info("LevenshteinDistance: " + v);
				row.createCell(index++).setCellValue(v);
			}
			else 
				row.createCell(index++).setCellValue(false);
		}
	}

	public boolean evalAddresses(String indirizzoDb, String indirizzoXml) {
		return new AddressesComparator().compare(indirizzoDb, indirizzoXml);
	}
	
	public void close(String filename) throws IOException {
		FileOutputStream fos = new FileOutputStream(filename+".xls");
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
