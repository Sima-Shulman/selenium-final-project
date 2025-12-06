package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import pages.CartItem;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelUtils {

    public static void writeCartToExcel(List<CartItem> items, String summary) {

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Cart Result");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Product Name");
        header.createCell(1).setCellValue("Quantity");
        header.createCell(2).setCellValue("Status");

        int rowIndex = 1;
        for (CartItem item : items) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(item.getName());
            row.createCell(1).setCellValue(item.getQuantity());

            String status = (item.getQuantity() >= 1) ? "PASS" : "FAIL";
            row.createCell(2).setCellValue(status);
        }

        Row summaryRow = sheet.createRow(rowIndex + 1);
        summaryRow.createCell(0).setCellValue("Summary:");
        summaryRow.createCell(1).setCellValue(summary);

        try (FileOutputStream file = new FileOutputStream("cart_output.xlsx")) {
            workbook.write(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
