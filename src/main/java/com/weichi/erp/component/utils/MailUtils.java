package com.weichi.erp.component.utils;

import com.weichi.erp.component.myType.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Created by Wewon on 2018/11/12.
 */
@Component
public class MailUtils {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JavaMailSenderImpl mailSender;

    public void sendSimpleEmail(String[] receiver, String[] copyTo, String subject, String content) {

        long startTimestamp = System.currentTimeMillis();
        logger.info("Start send mail ... ");

        try {
            mailSender.setUsername((String) Cache.getSysConfigMap().get("mailUsername"));
            mailSender.setPassword((String) Cache.getSysConfigMap().get("mailPassword"));
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom((String) Cache.getSysConfigMap().get("mailUsername"));
            message.setTo(receiver);
            message.setCc(copyTo);
            message.setSubject(subject);
            message.setText(content);
            mailSender.send(message);
            logger.info("Send mail success, cost {} million seconds", System.currentTimeMillis() - startTimestamp);
        } catch (MailException e) {
            logger.error("Send mail failed, error message is : {} \n", e.getMessage());
            e.printStackTrace();
        }
    }

    @Async
    public void sendSimpleEmailAsync(String[] receiver, String[] copyTo, String subject, String content) {
        this.sendSimpleEmail(receiver, copyTo, subject, content);
    }

}
