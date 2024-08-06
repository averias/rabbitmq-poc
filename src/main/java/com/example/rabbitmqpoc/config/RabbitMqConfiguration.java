package com.example.rabbitmqpoc.config;

import com.example.rabbitmqpoc.handler.BasicHandler;
import com.example.rabbitmqpoc.model.AlertMatch;
import com.example.rabbitmqpoc.model.EmailVerification;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMqConfiguration {
    public static final String JSON_MAPPED_QUEUE_NAME = "rabbitmq.json-mapped.queue";


    @Bean
    public RabbitTemplate jsonRabbitTemplateWithClassMapping(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonConverterWithClassMapping());
        template.setMandatory(true);
        return template;
    }

    @Bean
    public MessageConverter jsonConverterWithClassMapping() {
        Jackson2JsonMessageConverter jsonConverter = new Jackson2JsonMessageConverter();
        jsonConverter.setClassMapper(classMapper());
        jsonConverter.setTypePrecedence(Jackson2JavaTypeMapper.TypePrecedence.TYPE_ID);
        return jsonConverter;
    }

    @Bean
    public DefaultClassMapper classMapper() {
        DefaultClassMapper classMapper = new DefaultClassMapper();
        Map<String, Class<?>> idClassMapping = new HashMap<>();
        idClassMapping.put("emailVerification", EmailVerification.class);
        idClassMapping.put("alertMatch", AlertMatch.class);
        classMapper.setIdClassMapping(idClassMapping);
        return classMapper;
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
//    connectionFactory.setPublisherReturns(true);
//    connectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
        return connectionFactory;
    }

    @Bean
    public Queue jsonMappedQueue() {
        return new Queue(JSON_MAPPED_QUEUE_NAME);
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitMqListenerContainerFactory(
            SimpleRabbitListenerContainerFactoryConfigurer configurer) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setMessageConverter(jsonConverterWithClassMapping());
        configurer.configure(factory, new CachingConnectionFactory());

        return factory;
    }

//    @Bean
//    public SimpleMessageListenerContainer listenerContainer() {
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory());
//        container.setQueueNames(JSON_MAPPED_QUEUE_NAME);
//        container.setMessageListener(messageListener());
//        return container;
//    }

//    @Bean
//    public MessageListenerAdapter messageListener() {
//        MessageListenerAdapter messageListener = new MessageListenerAdapter(new BasicHandler());
//        messageListener.setMessageConverter(jsonConverterWithClassMapping());
//        return messageListener;
//    }



}
