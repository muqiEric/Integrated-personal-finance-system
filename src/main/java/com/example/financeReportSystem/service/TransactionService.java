package com.example.financeReportSystem.service;

import com.example.financeReportSystem.model.Bill;
import com.example.financeReportSystem.model.Transaction;
import com.example.financeReportSystem.repository.BillRepository;
import com.example.financeReportSystem.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Autowired
    private BillRepository billRepository;

    // 保存所有交易记录并创建或更新账单记录
    public void saveAllTransactionsWithBill(List<Transaction> transactions, String billSource, LocalDate startDate, LocalDate endDate) {
        // 计算收入和支出总额
        BigDecimal totalIncome = transactions.stream()
                .filter(t -> "收入".equals(t.getDirection())) // 假设交易方向为"收入"表示收入
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalExpense = transactions.stream()
                .filter(t -> "支出".equals(t.getDirection())) // 假设交易方向为"支出"表示支出
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 查找或创建账单记录
        Bill bill = billRepository.findByBillSourceAndStartDateAndEndDate(billSource, startDate, endDate);
        if (bill == null) {
            bill = new Bill();
            bill.setBillSource(billSource);
            bill.setStartDate(startDate);
            bill.setEndDate(endDate);
        }
        bill.setIncomeAmount(totalIncome);
        bill.setExpenseAmount(totalExpense);

        // 保存账单记录
        billRepository.save(bill);

        // 将账单设置到每个交易记录中
        for (Transaction transaction : transactions) {
            transaction.setBill(bill);
        }

        // 保存所有交易记录
        transactionRepository.saveAll(transactions);
    }

    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }
    @GetMapping("/statistics")
    public Map<String, Object> getStatistics() {
        Map<String, Object> stats = new HashMap<>();

        // 查询所有交易记录
        List<Transaction> transactions = transactionRepository.findAll();

        // 计算总收入和支出
        BigDecimal totalIncome = transactions.stream()
                .filter(t -> t.getAmount().compareTo(BigDecimal.ZERO) > 0)
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalExpense = transactions.stream()
                .filter(t -> t.getAmount().compareTo(BigDecimal.ZERO) < 0)
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 计算按类别统计
        Map<String, BigDecimal> trans_typeStats = transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getTrans_type,
                        Collectors.reducing(BigDecimal.ZERO, Transaction::getAmount, BigDecimal::add)));

        stats.put("totalIncome", totalIncome);
        stats.put("totalExpense", totalExpense);
        stats.put("categoryStats", trans_typeStats);

        return stats;
    }
    public List<Transaction> getTransactionsByDateRange(LocalDate startDate, LocalDate endDate) {
        return transactionRepository.findAllByDateBetween(startDate, endDate);
    }

    public void saveAllTransactions(List<Transaction> transactions) {
        transactionRepository.saveAll(transactions);
    }
    // TransactionService.java
    public Map<String, Object> getStatisticsByDateRange(LocalDate startDate, LocalDate endDate) {
        List<Transaction> transactions = transactionRepository.findAllByDateBetween(startDate, endDate);

        // 计算收入和支出
        BigDecimal totalIncome = transactions.stream()
                .filter(t -> "收入".equals(t.getDirection()))
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalExpense = transactions.stream()
                .filter(t -> "支出".equals(t.getDirection()))
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalIncome", totalIncome);
        stats.put("totalExpense", totalExpense);
        stats.put("netIncome", totalIncome.subtract(totalExpense)); // 计算净收入

        return stats;
    }
    // TransactionService.java
    public Map<String, BigDecimal> getStatisticsByCategory() {
        // 获取按类别分组的统计结果
        List<Map<String, Object>> result = transactionRepository.findTotalAmountByCategory();

        // 将结果转化为一个 Map
        Map<String, BigDecimal> categoryStats = new HashMap<>();
        for (Map<String, Object> row : result) {
            String category = (String) row.get("category");
            BigDecimal totalAmount = (BigDecimal) row.get("totalAmount");
            categoryStats.put(category, totalAmount);
        }
        return categoryStats;
    }



}
