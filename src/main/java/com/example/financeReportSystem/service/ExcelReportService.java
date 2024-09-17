package com.example.financeReportSystem.service;

import com.example.financeReportSystem.model.Transaction;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExcelReportService {

    public Workbook generateExcelReport(List<Transaction> transactions) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Financial Report");

        // 设置标题行
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Date");
        headerRow.createCell(1).setCellValue("Description");
        headerRow.createCell(2).setCellValue("Amount");
        headerRow.createCell(3).setCellValue("Category");

        // 填充交易数据
        int rowNum = 1;
        for (Transaction transaction : transactions) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(transaction.getDate().toString());
            row.createCell(1).setCellValue(transaction.getDescription());
            row.createCell(2).setCellValue(transaction.getAmount().toString());
            row.createCell(3).setCellValue(transaction.getCategory());
        }

        return workbook;
    }
}
