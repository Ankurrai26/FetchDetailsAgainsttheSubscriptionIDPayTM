package fetchPackage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class FetchDetailsFromExcel {

	public static ArrayList<String> fetchsubscriptionidfromexcel() throws IOException {
		ArrayList<String> subid = new ArrayList<String>();
		File file = new File(Utility.fetchFromPropertiesFile("ExcelPath")); // Enter File Path here having single sheet
																			// with mutiple subscription id row wise

		try {
			FileInputStream file2 = new FileInputStream(file);
			XSSFWorkbook excel = new XSSFWorkbook(file2);
			int num = excel.getNumberOfSheets() - 1;
			Sheet sheet = excel.getSheetAt(num);
			Iterator<Row> row = sheet.rowIterator();
			DataFormatter dataFormatter = new DataFormatter();
			DecimalFormat decimalFormat = new DecimalFormat("#");
			while (row.hasNext()) {

				Row raw = row.next();
				if (isEmptyRow(raw)) {
					continue;
				}

				Iterator<Cell> cell = raw.cellIterator();
				while (cell.hasNext()) {
					Cell call = cell.next();

					String datanumber;
					if (call.getCellType() == CellType.NUMERIC) {
						datanumber = decimalFormat.format(call.getNumericCellValue());
					} else {
						datanumber = dataFormatter.formatCellValue(call);
					}

					subid.add(datanumber);
				}
			}

			excel.close();

		} catch (IOException e) {

			System.out.println(e);
		}
		// System.out.println("arraylist"+ subid);
		return subid;
	}

	private static boolean isEmptyRow(Row row) {
		Iterator<Cell> cellIterator = row.cellIterator();
		while (cellIterator.hasNext()) {
			Cell cell = cellIterator.next();
			if (cell.getCellType() != CellType.BLANK) {
				return false; // The row is not empty
			}
		}
		return true; // All cells in the row are blank
	}

	@DataProvider(name = "fetchSubsctiptionId")
	public Object[] providesubscriptioid() throws IOException {

		ArrayList<String> list = FetchDetailsFromExcel.fetchsubscriptionidfromexcel();

		Object[] subid = list.toArray();

		return subid;

	}

}
