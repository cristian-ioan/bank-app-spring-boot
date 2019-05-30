package com.banking.scheduler;

import com.banking.model.Authentication;
import com.banking.repository.AuthenticationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.banking.util.ScheduleUtils.NUMBER_OF_MINUTES_JOB_EXPIRED;

@Component
public class ScheduledTasks {

    private static final Logger LOG = LoggerFactory.getLogger(ScheduledTasks.class);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Autowired
    private AuthenticationRepository authenticationRepository;

    @Scheduled(fixedRate = 90000)
    public void scheduleTaskWithFixedRate() {
        LOG.info("Task (Delete Token) :: Execution Time - {} ", dateTimeFormatter.format(LocalDateTime.now()) );
        tokenVerification();
    }

    @Scheduled(cron = "0 * * * * ?")
    public void scheduleTaskWithCronExpression() {
        LOG.info("Cron Task (Send Email):: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
    }

    @Transactional
    public void tokenVerification() {
        List<Authentication> authenticationList = authenticationRepository.findAll();
        LocalDateTime timeJob = LocalDateTime.now();
        for (Authentication authentication : authenticationList){
            LocalDateTime tokenCreatedTime = authentication.getCreationTime();
            Duration duration = Duration.between(timeJob, tokenCreatedTime);
            long diff = Math.abs(duration.toMinutes());
            if (diff < NUMBER_OF_MINUTES_JOB_EXPIRED){
                authenticationRepository.delete(authentication);
                authenticationRepository.flush();
                LOG.info("Token deleted! ");
            }
        }
    }

}
