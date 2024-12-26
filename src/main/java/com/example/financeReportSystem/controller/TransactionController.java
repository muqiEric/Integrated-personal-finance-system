package com.example.financeReportSystem.controller;

import com.example.financeReportSystem.model.BudgetCategory;
import com.example.financeReportSystem.model.Transaction;
import com.example.financeReportSystem.service.ExcelReportService;
import com.example.financeReportSystem.service.StatisticsService;
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
    private StatisticsService statisticsService;

    @Autowired
    private ExcelReportService excelReportService;

    // 获取所有交易记录（支持分页和排序）
    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        try {
            List<Transaction> transactions = transactionService.getAllTransactions();
            return ResponseEntity.ok(transactions);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // 获取交易记录按日期范围筛选（支持分页）
    @GetMapping("/range")
    public ResponseEntity<List<Transaction>> getTransactionsByDateRange(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        try {
            List<Transaction> transactions = transactionService.getTransactionsByDateRange(startDate, endDate);
            return ResponseEntity.ok(transactions);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // 获取交易记录按备注和日期范围筛选（支持分页）
    @GetMapping("/note-range")
    public ResponseEntity<List<Transaction>> getTransactionsByNoteAndDateRange(
            @RequestParam("note") String note,
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        try {
            List<Transaction> transactions = transactionService.getTransactionsByNoteAndDateRange(note, startDate, endDate);
            return ResponseEntity.ok(transactions);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // 按类别分组统计收支总额
    @GetMapping("/statistics/category")
    public ResponseEntity<List<Map<String, Object>>> getTotalAmountByCategory() {
        try {
            List<Map<String, Object>> statistics = transactionService.getTotalAmountByCategory();
            return ResponseEntity.ok(statistics);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // 按月份和类别统计收支总额
    @GetMapping("/statistics/month-category")
    public ResponseEntity<List<Map<String, Object>>> getTotalAmountGroupedByMonthAndCategory() {
        try {
            List<Map<String, Object>> statistics = transactionService.getTotalAmountGroupedByMonthAndCategory();
            return ResponseEntity.ok(statistics);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // 添加单个交易记录
    @PostMapping
    public ResponseEntity<Transaction> addTransaction(@RequestBody Transaction transaction) {
        try {
            Transaction savedTransaction = transactionService.addTransaction(transaction);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedTransaction);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // 更新单个交易记录
    @PutMapping("/{id}")
    public ResponseEntity<Transaction> updateTransaction(
            @PathVariable Long id, @RequestBody Transaction updatedTransaction) {
        try {
            Transaction updated = transactionService.updateTransaction(id, updatedTransaction);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // 批量删除交易记录
    @DeleteMapping
    public ResponseEntity<String> deleteTransactions(@RequestBody List<Long> ids) {
        try {
            transactionService.deleteTransactions(ids);
            return ResponseEntity.ok("交易记录已删除");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("删除失败：" + e.getMessage());
        }
    }

}
