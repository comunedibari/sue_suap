package cross.cross_scripts.reports.ave_1335;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
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
		CellStyle boldStyle = null;
		setHeaderCell(row, index, "Id pratica", boldStyle);
		index++;
		
		setHeaderCell(row, index, "Tipo pratica", boldStyle);
		index++;

		setHeaderCell(row, index, "Numero", boldStyle);
		index++;

		setHeaderCell(row, index, "Anno", boldStyle);
		index++;

		setHeaderCell(row, index, "descrizione sintetica intervento", boldStyle);
		index++;

		setHeaderCell(row, index, "destinazione d'uso esistente", boldStyle);
		index++;
		
		setHeaderCell(row, index, "e di progetto", boldStyle);
		index++;

		setHeaderCell(row, index, "indirizzo", boldStyle);
		index++;
		
		setHeaderCell(row, index, "piano", boldStyle);
		index++;
		
		setHeaderCell(row, index, "zona", boldStyle);
		index++;
	}

	private void setHeaderCell(Row row, int index, String cellValue, CellStyle boldStyle) {
		Cell cell;
		cell = row.createCell(index);
		cell.setCellValue(cellValue);
		if(boldStyle != null)
			cell.setCellStyle(boldStyle);
		currentSheet.setColumnWidth(index, 8000);
	}

	public void createRow(
			int idPratica, 
			String tipoPratica, 
			int numero, 
			int anno, 
			String descrizioneInterento, 
			String destinazioneDuso, 
			String eDiProgetto,
			String indirizzo,
			String piano,
			String valueZona) {

		Row row = currentSheet.createRow(currentIndex++);
		int index = 0;
		row.createCell(index++).setCellValue(idPratica);
		row.createCell(index++).setCellValue(tipoPratica);
		row.createCell(index++).setCellValue(numero);
		row.createCell(index++).setCellValue(anno);
		row.createCell(index++).setCellValue(destinazioneDuso);
		row.createCell(index++).setCellValue(descrizioneInterento);
		row.createCell(index++).setCellValue(eDiProgetto);
		row.createCell(index++).setCellValue(indirizzo);
		row.createCell(index++).setCellValue(piano);
		row.createCell(index++).setCellValue(valueZona);
	}
	public void createRow(
			String query) {
		
		Row row = currentSheet.createRow(currentIndex++);
		int index = 0;
		row.createCell(index++).setCellValue(query);
	}

	public void close(String filename) throws IOException {
		FileOutputStream fos = new FileOutputStream(filename+".xls");
		workbook.write(fos);
		fos.close();
	}

}
