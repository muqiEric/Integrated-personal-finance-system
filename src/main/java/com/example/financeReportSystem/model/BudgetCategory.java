package com.example.financeReportSystem.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class BudgetCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // 预算类别名称
    private BigDecimal budget; // 预算金额
    private BigDecimal spent; // 已花费金额
    private BigDecimal remaining; // 剩余金额

    public BudgetCategory(String name, BigDecimal budget, BigDecimal spent, BigDecimal remaining) {
        this.name = name;
        this.budget = budget;
        this.spent = spent;
        this.remaining = remaining;
    }


    // Getters 和 Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public BigDecimal getSpent() {
        return spent;
    }

    public void setSpent(BigDecimal spent) {
        this.spent = spent;
    }

    public BigDecimal getRemaining() {
        return remaining;
    }

    public void setRemaining(BigDecimal remaining) {
        this.remaining = remaining;
    }
}
