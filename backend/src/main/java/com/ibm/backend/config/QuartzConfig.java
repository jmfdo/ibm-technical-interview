package com.ibm.backend.config;

import com.ibm.backend.job.CheckState;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail jobDetail () {
        return JobBuilder.newJob(CheckState.class)
                .withIdentity("checkRentStateJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger trigger(JobDetail jobDetail) {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity("checkRentStateTrigger")
                .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(0,0))
                .build();
    }
}
