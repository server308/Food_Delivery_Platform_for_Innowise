package com.food_del_pltfrm.user_service.rabbit;

import com.food_del_pltfrm.user_service.dtos.*;
import com.food_del_pltfrm.user_service.entities.User;
import com.food_del_pltfrm.user_service.entities.VerificationCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.apache.bcel.classfile.Code;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class EventPublisher {
    private final RabbitTemplate rabbitTemplate;


    public void publishUserCreated(VerificationCode code, User user) {
        CodeEvent event = CodeEvent.builder()
                .code(code.getCode())
                .email(user.getEmail())
                .user_id(user.getId())
                .build();

        rabbitTemplate.convertAndSend(
                RabbitConfig.EXCHANGE_NAME,
                RabbitConfig.ROUTING_KEY,
                event
        );

    }

    public void publishUserDeleted(UserDeletedEvent userDeletedEvent){
        userDeletedEvent = UserDeletedEvent.builder().userId(userDeletedEvent.getUserId()).build();
        rabbitTemplate.convertAndSend(
                RabbitConfig.EXCHANGE_NAME,
                RabbitConfig.USER_DELETED_ROUTING_KEY,
                userDeletedEvent
        );
    }

}
