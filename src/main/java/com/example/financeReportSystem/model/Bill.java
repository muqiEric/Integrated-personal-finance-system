package com.example.financeReportSystem.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "bill")
public class Bill {

    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 账单的唯一标识符

    @Column(name = "bill_source", length = 50)
    private String billSource; // 账单来源，如支付宝或微信

    @Column(name = "account_name", length = 100)
    private String accountName; // 账户名称

    @Column(name = "account_id", length = 50)
    private String accountId; // 账号

    @Column(name = "start_date")
    private LocalDate startDate; // 账单的起始日期

    @Column(name = "end_date")
    private LocalDate endDate; // 账单的结束日期

    @Column(name = "income_amount", precision = 10, scale = 2)
    private BigDecimal incomeAmount; // 账单期间的总收入金额

    @Column(name = "expense_amount", precision = 10, scale = 2)
    private BigDecimal expenseAmount; // 账单期间的总支出金额

    @Column(name = "note", length = 200)
    private String note; // 账单的备注信息

    // 如果需要建立账单与交易之间的关系，可以使用@OneToMany关系映射（可选）
    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> transactions;

}
