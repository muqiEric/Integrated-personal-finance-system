package com.example.financeReportSystem.service;

import com.example.financeReportSystem.model.BudgetCategory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class BudgetService {

    private List<BudgetCategory> budgetCategories; // 模拟内存中的预算数据

    // 获取所有预算类别
    public List<BudgetCategory> getBudgetCategories() {
        if (budgetCategories == null) {
            // 模拟返回预算数据
            budgetCategories = new ArrayList<>();
            budgetCategories.add(new BudgetCategory("食品", BigDecimal.valueOf(1500), BigDecimal.valueOf(1200), BigDecimal.valueOf(300)));
            budgetCategories.add(new BudgetCategory("交通", BigDecimal.valueOf(800), BigDecimal.valueOf(600), BigDecimal.valueOf(200)));
            budgetCategories.add(new BudgetCategory("娱乐", BigDecimal.valueOf(500), BigDecimal.valueOf(600), BigDecimal.valueOf(-100)));
            budgetCategories.add(new BudgetCategory("医疗", BigDecimal.valueOf(300), BigDecimal.valueOf(100), BigDecimal.valueOf(200)));
        }
        return budgetCategories;
    }

    // 保存预算数据
    public void saveBudgetCategories(List<BudgetCategory> budgetCategories) {
        // 保存预算数据到内存（或数据库）
        this.budgetCategories = budgetCategories;
    }

    // 计算预算进度
    public BigDecimal calculateProgress(BudgetCategory category) {
        if (category.getBudget().compareTo(BigDecimal.ZERO) == 0) return BigDecimal.ZERO;
        return category.getSpent()
                .divide(category.getBudget(), 2, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }

    // 检查预算提醒信息
    public String checkBudgetAlerts(BudgetCategory category) {
        if (category.getSpent().compareTo(category.getBudget()) > 0) {
            return category.getName() + " 超出了预算！";
        } else if (category.getSpent().compareTo(category.getBudget().multiply(BigDecimal.valueOf(0.8))) >= 0) {
            return category.getName() + " 即将超出预算！";
        }
        return "";
    }
}
