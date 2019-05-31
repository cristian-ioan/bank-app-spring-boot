package com.banking.scheduler;

import com.banking.model.*;
import com.banking.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Properties;

import static com.banking.util.ScheduleUtils.*;

@Component
public class ScheduledTasks {

    private static final Logger LOG = LoggerFactory.getLogger(ScheduledTasks.class);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Autowired
    private AuthenticationRepository authenticationRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PersonRepository personRepository;

    @Scheduled(fixedRate = 90000)
    public void scheduleTaskWithFixedRate() {
        LOG.info("Task (Delete Token) :: Execution Time - {} ", dateTimeFormatter.format(LocalDateTime.now()) );
        deleteTokenAfterExpired();
    }

    @Scheduled(cron = "0 * * * * ?")
    public void scheduleTaskWithCronExpression() throws Exception {
        LOG.info("Cron Task (Send Email):: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
        sendEmailNotifications();
    }

    @Transactional
    public void deleteTokenAfterExpired() {
        List<Authentication> authenticationList = authenticationRepository.findAll();
        LocalDateTime timeJob = LocalDateTime.now();
        for (Authentication authentication : authenticationList){
            LocalDateTime tokenCreatedTime = authentication.getCreationTime();
            Duration duration = Duration.between(timeJob, tokenCreatedTime);
            long diff = Math.abs(duration.toMinutes());
            if (diff < NUMBER_OF_MINUTES_JOB_EXPIRED){
                authenticationRepository.delete(authentication);
                authenticationRepository.flush();
                LOG.info("Token deleted!");
            }
        }
    }

    @Transactional
    public void sendEmailNotifications() {
        List<Notification> notificationList = notificationRepository.findAll();
        for (Notification notification : notificationList){
            if (!notification.getSend()){
                User user = notification.getUser();
                String detailsNotification = notification.getDetails();
                Person person = personRepository.findPersonById(user.getId());
                String userEmail = person.getEmail();
                String subjectEmail = "Notification about transaction made on date: " + notification.getCreatedTime();
                notification.setSend(true);
                notificationRepository.save(notification);
                notificationRepository.flush();
                sendEmail(detailsNotification, userEmail, subjectEmail);
                LOG.info("Send email !");
            }
        }
    }

  public void sendEmail(String message, String mailAddress, String subject) {

        //Setting up configurations for the email connection to the Google SMTP server using TLS
        Properties props = new Properties();
        props.put("mail.smtp.host", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(USERNAME, PASSWORD);
                    }
                });

        try {

            Message mes = new MimeMessage(session);
            mes.setFrom(new InternetAddress(USERNAME));
            mes.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailAddress));
            mes.setSubject(subject);
            mes.setText(message);
            Transport.send(mes);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
