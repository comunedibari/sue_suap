package cross.cross_scripts.reports.ave_33;

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

import it.wego.cross.entity.Anagrafica;

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
		setHeaderCell(row, index, "nome", boldStyle);
		index++;

		setHeaderCell(row, index, "cognome", boldStyle);
		index++;

		setHeaderCell(row, index, "codice fiscale", boldStyle);
		index++;

		setHeaderCell(row, index, "è beneficiario", boldStyle);
		index++;

		setHeaderCell(row, index, "è richiedente", boldStyle);
		index++;
		
		setHeaderCell(row, index, "è tecnico procuratore", boldStyle);
		index++;
		
		setHeaderCell(row, index, "è impresa costruttrice", boldStyle);
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

	public void createRow(Anagrafica a, boolean isBeneficiary, boolean isApplicant, boolean isTechAgent, boolean isBuilder) {

		Row row = currentSheet.createRow(currentIndex++);
		int index = 0;
		row.createCell(index++).setCellValue(a.getCognome());
		row.createCell(index++).setCellValue(a.getNome());
		row.createCell(index++).setCellValue(a.getCodiceFiscale());
		row.createCell(index++).setCellValue(isBeneficiary);
		row.createCell(index++).setCellValue(isApplicant);
		row.createCell(index++).setCellValue(isTechAgent);
		row.createCell(index++).setCellValue(isBuilder);
		
	}

	public void close(String filename) throws IOException {
		FileOutputStream fos = new FileOutputStream(filename+".xls");
		workbook.write(fos);
		fos.close();
	}

}
