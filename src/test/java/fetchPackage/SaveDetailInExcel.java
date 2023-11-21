package fetchPackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SaveDetailInExcel extends ClassToFetchDetails {

	public static void saveDetailsExcel() throws IOException {

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("SubscriptionDetails");
		Set<Integer> keyset = data.keySet();
		int rownum = 0;
		for (Integer key : keyset) {

			Row row = sheet.createRow(rownum++);

			Object[] objdata = data.get(key);

			int colnum = 0;
			for (Object obj : objdata) {

				Cell cell = row.createCell(colnum++);

				if (obj instanceof String) {
					cell.setCellValue((String) obj);
				} else if (obj instanceof Double) {
					cell.setCellValue((Double) obj);
				} else if (obj instanceof Integer) {
					cell.setCellValue((Integer) obj);
				} else if (obj instanceof Long) {
					cell.setCellValue((Long) obj);
				} else {
					cell.setCellValue(obj.toString());
				}

			}

		}

		try {
			FileOutputStream out = new FileOutputStream(new File("SubscripIDDetail_mandardate.xlsx"));
			workbook.write(out);
			out.close();

			System.out.println("Excel has been created and stored at the given path");

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}

	}

}
