package com.example.financeReportSystem.controller;

import com.example.financeReportSystem.model.Transaction;
import com.example.financeReportSystem.service.ExcelReportService;
import com.example.financeReportSystem.service.TransactionService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/excel")
public class ExcelReportController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ExcelReportService excelReportService;

    // 导出交易记录为 Excel                 
    @GetMapping("/export")
    public void exportToExcel(HttpServletResponse response) {
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=financial_report.xlsx");

            List<Transaction> transactions = transactionService.getAllTransactions();
            Workbook workbook = excelReportService.generateExcelReport(transactions);

            try (ServletOutputStream outputStream = response.getOutputStream()) {
                workbook.write(outputStream);
            }
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    // 合并交易文件并生成 Excel
    @PostMapping("/merge")
    public ResponseEntity<String> mergeTransactionFiles(
            @RequestParam("wechatFile") MultipartFile wechatFile,
            @RequestParam("alipayFile") MultipartFile alipayFile,
            HttpServletResponse response) {
        try {
            // 解析并合并文件
            List<Transaction> mergedTransactions = excelReportService.parseAndMergeFiles(wechatFile, alipayFile);

            // 保存合并后的交易记录
            transactionService.saveAllTransactions(mergedTransactions);

            // 生成并导出 Excel 文件
            Workbook workbook = excelReportService.generateExcelReport(mergedTransactions);
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=merged_transactions.xlsx");

            try (ServletOutputStream outputStream = response.getOutputStream()) {
                workbook.write(outputStream);
            }

            return ResponseEntity.ok("文件合并并导出成功，且已保存到数据库！");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("文件处理失败：" + e.getMessage());
        }
    }
}