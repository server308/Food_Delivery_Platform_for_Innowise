package com.food_del_pltfrm.order_service.rabbit;

import com.food_del_pltfrm.order_service.dtos.UserDeletedEvent;
import com.food_del_pltfrm.order_service.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventListener {
    private final OrderService orderService;
    @RabbitListener(queues = RabbitConfig.DELETE_USER_QUEUE)
    public void handleUserDeleted(UserDeletedEvent userDeletedEvent) {
        log.info("👤 Order Service received UserDeletedEvent with id: {}", userDeletedEvent.getUserId());
        orderService.deleteOrdersByUserId(userDeletedEvent.getUserId());
        log.info("All orders with id {} will be deleted", userDeletedEvent.getUserId());
    }
}
