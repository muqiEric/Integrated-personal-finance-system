package com.example.financeReportSystem.controller;

import com.example.financeReportSystem.model.Transaction;
import com.example.financeReportSystem.service.ExcelReportService;
import com.example.financeReportSystem.service.TransactionService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ExcelReportService excelReportService;

    // 获取所有交易记录
    @GetMapping
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    // 添加单个交易记录
    @PostMapping
    public Transaction addTransaction(@RequestBody Transaction transaction) {
        return transactionService.saveTransaction(transaction);
    }

    // 获取统计信息
    @GetMapping("/statistics")
    public Map<String, Object> getStatistics() {
        return transactionService.getStatistics();
    }

    // 导出所有交易记录为Excel
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

    // 按日期范围获取交易记录
    @GetMapping("/range")
    public List<Transaction> getTransactionsByDateRange(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return transactionService.getTransactionsByDateRange(startDate, endDate);
    }
    // TransactionController.java
    @GetMapping("/statistics/category")
    public Map<String, BigDecimal> getStatisticsByCategory() {
        return transactionService.getStatisticsByCategory();
    }


    // 本地文件合并测试接口
    @PostMapping("/merge")
    public ResponseEntity<String> mergeTransactionFiles(
            @RequestParam("wechatFile") MultipartFile wechatFile,
            @RequestParam("alipayFile") MultipartFile alipayFile,
            HttpServletResponse response) {
        try {
            // 解析并合并两个平台的交易数据
            List<Transaction> mergedTransactions = excelReportService.parseAndMergeFiles(wechatFile, alipayFile);

            // 保存到数据库
            transactionService.saveAllTransactions(mergedTransactions);

            // 调用生成Excel报告的方法，使用合并后的列表
            Workbook workbook = excelReportService.generateExcelReport(mergedTransactions);

            // 设置响应头，告诉浏览器下载文件
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=merged_transactions.xlsx");

            // 将合并后的Excel文件写入响应流，供下载
            ServletOutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();

            return ResponseEntity.ok("文件合并并导出成功，且已保存到数据库！");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("文件处理失败：" + e.getMessage());
        }
    }
}

