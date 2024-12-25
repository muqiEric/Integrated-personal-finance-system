package com.example.financeReportSystem.service;

import com.example.financeReportSystem.model.Transaction;
import com.example.financeReportSystem.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatisticsService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Map<String, Object> calculateOverallStatistics() {
        List<Transaction> transactions = transactionRepository.findAll();

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
        stats.put("netIncome", totalIncome.subtract(totalExpense));

        return stats;
    }

    public Map<String, BigDecimal> calculateCategoryStatistics() {
        List<Map<String, Object>> results = transactionRepository.findTotalAmountByCategory();

        Map<String, BigDecimal> categoryStats = new HashMap<>();
        for (Map<String, Object> row : results) {
            String category = (String) row.get("category");
            BigDecimal totalAmount = (BigDecimal) row.get("totalAmount");
            categoryStats.put(category, totalAmount);
        }

        return categoryStats;
    }
}
