package com.example.task.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@PropertySource("classpath:application.properties")
@Service
public class emailService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private Environment env;

    public void send(String email, String massage) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        try {
            mimeMessage.setFrom(new InternetAddress(env.getProperty("spring.mail.username")));
            helper.setTo(email);
            helper.setText(massage, true);
            helper.setSubject("Authorization");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        javaMailSender.send(mimeMessage);
    }
}
