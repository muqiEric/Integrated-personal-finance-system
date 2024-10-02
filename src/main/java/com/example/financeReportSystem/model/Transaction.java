package com.example.financeReportSystem.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 交易的唯一标识符

    @Column(nullable = false)
    private LocalDate trans_time; // 交易时间

    @Column(nullable = false)
    private String trans_type; // 交易类型

    @Column(nullable = false)
    private String trans_party; // 交易对象

    @Column(nullable = false)
    private String goods_name; // 商品名称

    @Column(nullable = false)
    private String direction; // 收入/支出方向

    @Column(nullable = false)
    private BigDecimal amount; // 金额

    @Column(nullable = false)
    private String payment_method; // 支付方式

    @Column(nullable = false)
    private String trans_status; // 交易状态

    @Column(nullable = false)
    private String order_no; // 订单号

    @Column(nullable = false)
    private String merchant_order_no; // 商户订单号

    private String note; // 备注

    // 外键关系：与 Bill 实体建立多对一关系
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bill_id", nullable = false) // 外键列名称为 "bill_id"
    private Bill bill; // 关联的账单实体

    // 默认构造函数（JPA 需要）
    public Transaction() {}

    // 带所有参数的构造函数（用于创建解析后的数据）
    public Transaction(LocalDate trans_time, String trans_type, String trans_party, String goods_name, String direction,
                       BigDecimal amount, String payment_method, String trans_status, String order_no, String merchant_order_no, String note, Bill bill) {
        this.trans_time = trans_time;
        this.trans_type = trans_type;
        this.trans_party = trans_party;
        this.goods_name = goods_name;
        this.direction = direction;
        this.amount = amount;
        this.payment_method = payment_method;
        this.trans_status = trans_status;
        this.order_no = order_no;
        this.merchant_order_no = merchant_order_no;
        this.note = note;
        this.bill = bill; // 关联的 Bill 实体对象
    }

    // 带参数的简化构造函数（用于无 bill 关联的场景）
    public Transaction(LocalDate trans_time, String trans_type, String trans_party, String goods_name, String direction,
                       BigDecimal amount, String payment_method, String trans_status, String order_no, String merchant_order_no, String note) {
        this(trans_time, trans_type, trans_party, goods_name, direction, amount, payment_method, trans_status, order_no, merchant_order_no, note, null);
    }

    // Getter 和 Setter 方法
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getTrans_time() {
        return trans_time;
    }

    public void setTrans_time(LocalDate trans_time) {
        this.trans_time = trans_time;
    }

    public String getTrans_type() {
        return trans_type;
    }

    public void setTrans_type(String trans_type) {
        this.trans_type = trans_type;
    }

    public String getTrans_party() {
        return trans_party;
    }

    public void setTrans_party(String trans_party) {
        this.trans_party = trans_party;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getTrans_status() {
        return trans_status;
    }

    public void setTrans_status(String trans_status) {
        this.trans_status = trans_status;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getMerchant_order_no() {
        return merchant_order_no;
    }

    public void setMerchant_order_no(String merchant_order_no) {
        this.merchant_order_no = merchant_order_no;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }
}
