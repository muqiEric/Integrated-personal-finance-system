package com.example.personal_financial_manage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.example.financeReportSystem.model")
public class PersonalFinancialManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonalFinancialManageApplication.class, args);
    }
}
