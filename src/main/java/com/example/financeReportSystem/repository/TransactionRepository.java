package com.example.financeReportSystem.repository;

import com.example.financeReportSystem.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // 按日期范围筛选交易记录
    @Query("SELECT t FROM Transaction t WHERE t.trans_time BETWEEN :startDate AND :endDate")
    List<Transaction> findAllByDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    // 按备注和日期范围筛选交易记录
    @Query("SELECT t FROM Transaction t WHERE t.note LIKE %:note% AND t.trans_time BETWEEN :startDate AND :endDate")
    List<Transaction> findAllByNoteAndDateBetween(
            @Param("note") String note,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    // 按类别分组统计收支总额
    @Query("SELECT t.trans_type AS category, SUM(t.amount) AS totalAmount FROM Transaction t GROUP BY t.trans_type")
    List<Map<String, Object>> findTotalAmountByCategory();

    // 按月份和类别统计收支
    @Query("SELECT MONTH(t.trans_time) AS month, t.trans_type AS category, SUM(t.amount) AS totalAmount " +
            "FROM Transaction t " +
            "GROUP BY MONTH(t.trans_time), t.trans_type " +
            "ORDER BY month")
    List<Map<String, Object>> findTotalAmountGroupedByMonthAndCategory();

    // 批量删除交易记录
    @Query("DELETE FROM Transaction t WHERE t.id IN :ids")
    void deleteAllByIds(@Param("ids") List<Long> ids);
}
