package io.github.aleknik.scientificcenterservice.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;


    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    public void sendMail(String emailAddress, String title, String body) {

        final SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(title);
        message.setFrom(senderEmail);
        message.setTo(emailAddress);
        message.setText(body);

        try {
            mailSender.send(message);
        } catch (MailException e) {
            System.out.println(e);
        }
    }
}