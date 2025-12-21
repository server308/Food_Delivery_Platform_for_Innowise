package com.food_del_pltfrm.user_service.rabbit;


import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@EnableRabbit
public class RabbitConfig {

    public static final String CREATE_USER_QUEUE = "create.user.queue";
    public static final String EXCHANGE_NAME = "exchange";
    public static final String ROUTING_KEY = "user.created";
    public static final String DELETE_USER_QUEUE = "delete.user.queue";
    public static final String USER_DELETED_ROUTING_KEY = "user.deleted";

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue userCreateQueue(){
        return new Queue(CREATE_USER_QUEUE, true);
    }

    @Bean
    public Queue userDeleteQueue(){
        return new Queue(DELETE_USER_QUEUE, true);
    }

    @Bean
    public Binding createdBinding(Queue userCreateQueue, TopicExchange exchange) {
        return BindingBuilder.bind(userCreateQueue).to(exchange).with(ROUTING_KEY);
    }

    @Bean
    public Binding userDeletedBinding(Queue userDeleteQueue, TopicExchange exchange){
        return BindingBuilder.bind(userDeleteQueue).to(exchange).with(USER_DELETED_ROUTING_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}

