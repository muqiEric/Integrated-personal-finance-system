package com.example.financeReportSystem.service;

import com.example.financeReportSystem.model.Transaction;
import com.example.financeReportSystem.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    // 获取所有交易记录
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    // 根据日期范围筛选交易记录
    public List<Transaction> getTransactionsByDateRange(LocalDate startDate, LocalDate endDate) {
        return transactionRepository.findAllByDateBetween(startDate, endDate);
    }

    // 根据备注和日期范围筛选交易记录
    public List<Transaction> getTransactionsByNoteAndDateRange(String note, LocalDate startDate, LocalDate endDate) {
        return transactionRepository.findAllByNoteAndDateBetween(note, startDate, endDate);
    }

    // 按类别分组统计收支总额
    public List<Map<String, Object>> getTotalAmountByCategory() {
        return transactionRepository.findTotalAmountByCategory();
    }

    // 按月份和类别统计收支总额
    public List<Map<String, Object>> getTotalAmountGroupedByMonthAndCategory() {
        return transactionRepository.findTotalAmountGroupedByMonthAndCategory();
    }

    // 批量删除交易记录
    public void deleteTransactions(List<Long> ids) {
        transactionRepository.deleteAllByIds(ids);
    }

    // 新增交易记录
    public Transaction addTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    // 更新交易记录
    public Transaction updateTransaction(Long id, Transaction updatedTransaction) {
        Transaction existingTransaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found with ID: " + id));
        // 更新字段
        existingTransaction.setTrans_time(updatedTransaction.getTrans_time());
        existingTransaction.setTrans_type(updatedTransaction.getTrans_type());
        existingTransaction.setTrans_party(updatedTransaction.getTrans_party());
        existingTransaction.setGoods_name(updatedTransaction.getGoods_name());
        existingTransaction.setDirection(updatedTransaction.getDirection());
        existingTransaction.setAmount(updatedTransaction.getAmount());
        existingTransaction.setPayment_method(updatedTransaction.getPayment_method());
        existingTransaction.setTrans_status(updatedTransaction.getTrans_status());
        existingTransaction.setOrder_no(updatedTransaction.getOrder_no());
        existingTransaction.setMerchant_order_no(updatedTransaction.getMerchant_order_no());
        existingTransaction.setNote(updatedTransaction.getNote());
        existingTransaction.setBill(updatedTransaction.getBill());
        return transactionRepository.save(existingTransaction);
    }
}