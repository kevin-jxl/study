package com.ztman.ztspark.job;

import com.ztman.ztspark.service.CompanyWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class TriggerClass {

    @Autowired
    private CompanyWorkService companyWorkService;

    @Scheduled(cron = "0 0 0 1 * ? ")
    public void generateDutyInfo() {
        System.out.println("生成考勤*************");
        companyWorkService.generateDutyInfo();
    }
}