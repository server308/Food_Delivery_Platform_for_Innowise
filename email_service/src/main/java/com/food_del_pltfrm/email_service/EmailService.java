package com.food_del_pltfrm.email_service;

import com.food_del_pltfrm.email_service.entites.CodeEvent;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.naming.Context;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender emailSender;


    public void sendVerificationEmail(CodeEvent event) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(event.getEmail());
            message.setSubject("Подтверждение регистрации");
            message.setText("Ваш код подтверждения: " + event.getCode() + "\n\nКод действителен 15 минут.");
            message.setFrom("noreply@yourapp.com");

            emailSender.send(message);
            log.info("Письмо с кодом отправлено на: {}", event.getEmail());

        } catch (Exception e) {
            log.error("Ошибка отправки письма на: {}", event.getEmail(), e);
            throw new RuntimeException("Не удалось отправить письмо", e);
        }
    }


}
