package cross.cross_scripts.reports.egov_793;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

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
		setHeaderCell(row, index, "Data ricezione pratica", boldStyle);
		index++;

		setHeaderCell(row, index, "Codice Pratica(TIPO-NUM-ANNO)", boldStyle);
		index++;

		setHeaderCell(row, index, "Data prima integrazione", boldStyle);
		index++;

		setHeaderCell(row, index, "Data ultima integrazione", boldStyle);
		index++;

		setHeaderCell(row, index, "Numero totale integrazioni ricevute", boldStyle);
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

	public void createRow(Pratica pratica, PraticheEventi firstEvent, PraticheEventi lastEvent, int counter) {

		Row row = currentSheet.createRow(currentIndex++);
		int index = 0;
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		row.createCell(index++).setCellValue(df.format(pratica.getDataRicezione()));
		String code = "";
		if(pratica.getTipoPratica() != null)
			code+=pratica.getTipoPratica().getTipoPratica();
		code+="-";
		if(pratica.getNumeroPratica() != null)
			code+=pratica.getNumeroPratica();
		code+="-"+pratica.getAnno();
		row.createCell(index++).setCellValue(code);
		Cell cell = row.createCell(index++);
		if(firstEvent!= null)
			cell.setCellValue(df.format(firstEvent.getDataEvento()));
		cell = row.createCell(index++);
		if(lastEvent!= null)
			cell.setCellValue(df.format(lastEvent.getDataEvento()));
		row.createCell(index++).setCellValue(counter);
		
	}

	public void close(String filename) throws IOException {
		FileOutputStream fos = new FileOutputStream(filename+".xls");
		workbook.write(fos);
		fos.close();
	}

}
