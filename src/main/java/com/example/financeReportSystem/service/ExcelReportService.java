package com.example.financeReportSystem.service;

import com.example.financeReportSystem.model.Transaction;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelReportService {

    @Autowired
    private TransactionService transactionService;

    // 解析微信CSV文件
    public List<Transaction> parseWechatCsv(MultipartFile file) throws IOException {
        List<Transaction> transactions = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(convertMultipartFileToFile(file)))) {
            String line;

            // 跳过前12行（标题行）
            for (int i = 0; i < 17; i++) {
                br.readLine();
            }

            // 解析交易记录
            while ((line = br.readLine()) != null) {
                String[] values = line.split(","); // 按逗号分隔
                if (values.length >= 11) { // 检查字段数
                    LocalDate trans_time = LocalDate.parse(values[0]); // 交易时间
                    String trans_type = values[1]; // 交易类型
                    String trans_party = values[2]; // 交易对方
                    String goods_name = values[3]; // 商品名称
                    String direction = values[4]; // 收/支
                    BigDecimal amount = new BigDecimal(values[5].replace("¥", "").trim()); // 金额
                    String payment_method = values[6]; // 支付方式
                    String trans_status = values[7]; // 当前状态
                    String order_no = values[8]; // 交易单号
                    String merchant_order_no = values[9]; // 商户单号
                    String note = values[10]; // 备注

                    // 创建Transaction对象
                    Transaction transaction = new Transaction(trans_time, trans_type, trans_party, goods_name, direction, amount, payment_method, trans_status, order_no, merchant_order_no, note);
                    transactions.add(transaction);
                }
            }
        }

        return transactions; // 返回解析后的交易记录
    }

    // 解析支付宝CSV文件
    public List<Transaction> parseAlipayCsv(MultipartFile file) throws IOException {
        List<Transaction> transactions = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(convertMultipartFileToFile(file)))) {
            String line;

            // 跳过前25行（标题行）
            for (int i = 0; i < 25; i++) {
                br.readLine();
            }

            // 解析交易记录
            while ((line = br.readLine()) != null) {
                String[] values = line.split(","); // 按逗号分隔
                if (values.length >= 11) { // 检查字段数
                    LocalDate trans_time = LocalDate.parse(values[0]); // 交易时间
                    String trans_type = values[1]; // 交易类型
                    String trans_party = values[2]; // 交易对方
                    String goods_name = values[4]; // 商品名称
                    String direction = values[5]; // 收/支
                    BigDecimal amount = new BigDecimal(values[6]); // 金额
                    String payment_method = values[7]; // 支付方式
                    String trans_status = values[8]; // 当前状态
                    String order_no = values[9]; // 交易单号
                    String merchant_order_no = values[10]; // 商户单号
                    String note = values[11]; // 备注

                    // 创建Transaction对象
                    Transaction transaction = new Transaction(trans_time, trans_type, trans_party, goods_name, direction, amount, payment_method, trans_status, order_no, merchant_order_no, note);
                    transactions.add(transaction);
                }
            }
        }

        return transactions; // 返回解析后的交易记录
    }

    // 将 MultipartFile 转换为 File
    private File convertMultipartFileToFile(MultipartFile file) throws IOException {
        File tempFile = File.createTempFile("temp", file.getOriginalFilename());
        file.transferTo(tempFile);
        return tempFile;
    }

    // 生成合并后的Excel报告
    public Workbook generateExcelReport(List<Transaction> transactions) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Financial Report");

        // 设置标题行
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Transaction Time");
        headerRow.createCell(1).setCellValue("Transaction Type");
        headerRow.createCell(2).setCellValue("Transaction Party");
        headerRow.createCell(3).setCellValue("Goods Name");
        headerRow.createCell(4).setCellValue("Direction");
        headerRow.createCell(5).setCellValue("Amount");
        headerRow.createCell(6).setCellValue("Payment Method");
        headerRow.createCell(7).setCellValue("Transaction Status");
        headerRow.createCell(8).setCellValue("Order No");
        headerRow.createCell(9).setCellValue("Merchant Order No");
        headerRow.createCell(10).setCellValue("Note");

        // 填充交易数据
        int rowNum = 1;
        for (Transaction transaction : transactions) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(transaction.getTrans_time().toString());
            row.createCell(1).setCellValue(transaction.getTrans_type());
            row.createCell(2).setCellValue(transaction.getTrans_party());
            row.createCell(3).setCellValue(transaction.getGoods_name());
            row.createCell(4).setCellValue(transaction.getDirection());
            row.createCell(5).setCellValue(transaction.getAmount().toString());
            row.createCell(6).setCellValue(transaction.getPayment_method());
            row.createCell(7).setCellValue(transaction.getTrans_status());
            row.createCell(8).setCellValue(transaction.getOrder_no());
            row.createCell(9).setCellValue(transaction.getMerchant_order_no());
            row.createCell(10).setCellValue(transaction.getNote());
        }

        return workbook;
    }

    // 合并解析两个上传的文件（微信和支付宝）
    public List<Transaction> parseAndMergeFiles(MultipartFile wechatFile, MultipartFile alipayFile) throws IOException {
        List<Transaction> mergedTransactions = new ArrayList<>();

        // 解析微信文件
        List<Transaction> wechatTransactions = parseWechatCsv(wechatFile); // 使用新的CSV解析逻辑
        mergedTransactions.addAll(wechatTransactions);

        // 解析支付宝文件
        List<Transaction> alipayTransactions = parseAlipayCsv(alipayFile); // 使用新的CSV解析逻辑
        mergedTransactions.addAll(alipayTransactions);

        // 返回合并后的交易列表
        return mergedTransactions;
    }
}
