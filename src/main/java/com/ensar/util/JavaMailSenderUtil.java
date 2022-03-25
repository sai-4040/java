package com.ensar.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class JavaMailSenderUtil {

    @Value("${EMAIL_HOST:}")
    private String emailHost;

    @Value("${EMAIL_PORT:}")
    private String emailPort;

    @Value("${EMAIL_USER_NAME:}")
    private String emailUserName;

    @Value("${EMAIL_USER_PWD:}")
    private String emailUserPwd;

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(emailHost);
        mailSender.setPort(587);

        mailSender.setUsername(emailUserName);
        mailSender.setPassword(emailUserPwd);


        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
}
