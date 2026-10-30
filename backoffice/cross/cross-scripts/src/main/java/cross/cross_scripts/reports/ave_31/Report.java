package cross.cross_scripts.reports.ave_31;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticheEventi;

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
		setHeaderCell(row, index, "Tipo pratica", boldStyle);
		index++;

		setHeaderCell(row, index, "Numero", boldStyle);
		index++;

		setHeaderCell(row, index, "Anno", boldStyle);
		index++;

		setHeaderCell(row, index, "Fascicolo", boldStyle);
		index++;

		setHeaderCell(row, index, "Stato", boldStyle);
		index++;

		setHeaderCell(row, index, "Data Evento Amm invio avvio diniego 10 bis", boldStyle);
	}

	private void setHeaderCell(Row row, int index, String cellValue, CellStyle boldStyle) {
		Cell cell;
		cell = row.createCell(index);
		cell.setCellValue(cellValue);
		if(boldStyle != null)
			cell.setCellStyle(boldStyle);
		currentSheet.setColumnWidth(index, 8000);
	}

	public void createRow(Pratica pratica, PraticheEventi pe) {

		Row row = currentSheet.createRow(currentIndex++);
		int index = 0;
		row.createCell(index++).setCellValue(pratica.getTipoPratica().getDescrizione());
		row.createCell(index++).setCellValue(pratica.getNumeroPratica());
		row.createCell(index++).setCellValue(pratica.getAnno());
		row.createCell(index++).setCellValue(pratica.getProtocollo());
		row.createCell(index++).setCellValue(pratica.getIdStatoPratica().getDescrizione());
		
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		row.createCell(index++).setCellValue(df.format(pe.getDataEvento()));
//		row.createCell(index++).setCellValue(pe.getIdEvento().getDesEvento());

	}

	public void close(String filename) throws IOException {
		FileOutputStream fos = new FileOutputStream(filename+".xls");
		workbook.write(fos);
		fos.close();
	}

}
