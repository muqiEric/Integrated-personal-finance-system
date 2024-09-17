package com.example.financeReportSystem.controller;

import com.example.financeReportSystem.model.Transaction;
import com.example.financeReportSystem.service.ExcelReportService;
import com.example.financeReportSystem.service.TransactionService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @PostMapping
    public Transaction addTransaction(@RequestBody Transaction transaction) {
        return transactionService.saveTransaction(transaction);
    }
    @GetMapping("/statistics")
    public Map<String, Object> getStatistics() {
        return transactionService.getStatistics();
    }


    @Autowired
    private ExcelReportService excelReportService;

    @GetMapping("/export")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=financial_report.xlsx");

        List<Transaction> transactions = transactionService.getAllTransactions();
        Workbook workbook = excelReportService.generateExcelReport(transactions);

        // 将Excel文件写入响应输出流
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
    @GetMapping("/range")
    public List<Transaction> getTransactionsByDateRange(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return transactionService.getTransactionsByDateRange(startDate, endDate);
    }

}

