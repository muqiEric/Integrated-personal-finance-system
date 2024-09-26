package com.example.financeReportSystem.service;

import com.example.financeReportSystem.model.Transaction;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelReportService {

    @Autowired
    private TransactionService transactionService;

    // 生成Excel报告（已存在的方法）
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

    // 新增：解析上传的Excel文件
    //从上传的 Excel 文件中读取数据,并将其转换为 Transaction 对象的列表。
    // 然后,通过调用 TransactionService 的 saveAllTransactions 方法将解析后的交易数据保存到数据库中。
    public void parseExcelFile(MultipartFile file) throws IOException {
        List<Transaction> transactions = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream()) {
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            // 从第二行开始读取数据（假设第一行为表头）
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    // 读取每一行数据
                    LocalDate date = LocalDate.parse(row.getCell(0).getStringCellValue()); // 假设第0列是日期
                    String description = row.getCell(1).getStringCellValue(); // 第1列是描述
                    BigDecimal amount = new BigDecimal(row.getCell(2).getNumericCellValue()); // 第2列是金额
                    String category = row.getCell(3).getStringCellValue(); // 第3列是类别

                    // 创建Transaction对象
                    // 传递顺序：description, amount, date, category
                    Transaction transaction = new Transaction(date, description, amount, category);
                    transactions.add(transaction);
                }
            }
        }

        // 保存到数据库
        transactionService.saveAllTransactions(transactions);
    }
}
