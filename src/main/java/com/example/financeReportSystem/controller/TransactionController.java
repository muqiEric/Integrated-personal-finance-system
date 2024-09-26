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

    // 导出交易记录到Excel文件
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

    // 上传Excel文件并解析保存
    //用于处理文件上传。该方法接收一个 MultipartFile 类型的参数,并将其传递给 ExcelReportService 的 parseExcelFile 方法进行解析。
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("请上传文件！");
        }

        try {
            excelReportService.parseExcelFile(file);
            return ResponseEntity.ok("文件上传并解析成功！");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("文件处理失败：无法读取文件。");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("文件处理失败：" + e.getMessage());
        }
    }
}
