package com.food_del_pltfrm.email_service.rabbit;

import com.food_del_pltfrm.email_service.EmailService;
import com.food_del_pltfrm.email_service.entites.CodeEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventListener {

    private final EmailService emailService;
    @RabbitListener(queues = RabbitConfig.CREATE_USER_QUEUE)
    public void handleUserCreated(CodeEvent codeEvent) {
        log.info("👤 Email Service received UserCreatedEvent: {}", codeEvent.getEmail());
        emailService.sendVerificationEmail(codeEvent);
        log.info("The message has been sent!");
    }

}
