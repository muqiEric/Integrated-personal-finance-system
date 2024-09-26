package com.example.financeReportSystem.repository;

import com.example.financeReportSystem.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // 自定义查询：按日期范围查找交易记录
    @Query("SELECT t FROM Transaction t WHERE t.date BETWEEN :startDate AND :endDate")
    List<Transaction> findAllByDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
