package com.example.financeReportSystem.repository;

import com.example.financeReportSystem.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
    // 根据账单来源和起始日期查找账单记录
    Bill findByBillSourceAndStartDateAndEndDate(String billSource, LocalDate startDate, LocalDate endDate);
}
