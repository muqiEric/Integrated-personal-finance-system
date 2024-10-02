package com.example.financeReportSystem.repository;

import com.example.financeReportSystem.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // 自定义查询：按日期范围查找交易记录
    @Query("SELECT t FROM Transaction t WHERE t.trans_time BETWEEN :startDate AND :endDate")
    List<Transaction> findAllByDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);


    // 按类别分组统计收支总额
    @Query("SELECT t.trans_type AS category, SUM(t.amount) AS totalAmount FROM Transaction t GROUP BY t.trans_type")
    List<Map<String, Object>> findTotalAmountByCategory();
}


