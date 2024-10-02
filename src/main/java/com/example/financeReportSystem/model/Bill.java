package com.example.financeReportSystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "bills") // 确保数据库表名称与映射相符
public class Bill {

    // Getter 和 Setter 方法
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 主键 ID

    @Column(nullable = false)
    private String bill_source; // 账单来源

    @Column(nullable = false)
    private String account_name; // 账户名称

    @Column(nullable = false)
    private String account_id; // 账号

    @Column(nullable = false)
    private LocalDate start_date; // 起始日期

    @Column(nullable = false)
    private LocalDate end_date; // 结束日期

    @Column(nullable = false)
    private BigDecimal income_amount; // 总收入金额

    @Column(nullable = false)
    private BigDecimal expense_amount; // 总支出金额

    private String note; // 备注

    // 反向映射：一个 Bill 可以对应多个 Transaction（表示一对多关系）
    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> transactions; // 账单对应的交易列表

    // 默认构造函数
    public Bill() {}

    // 全参数构造函数
    public Bill(String bill_source, String account_name, String account_id, LocalDate start_date, LocalDate end_date,
                BigDecimal income_amount, BigDecimal expense_amount, String note) {
        this.bill_source = bill_source;
        this.account_name = account_name;
        this.account_id = account_id;
        this.start_date = start_date;
        this.end_date = end_date;
        this.income_amount = income_amount;
        this.expense_amount = expense_amount;
        this.note = note;
    }

}
