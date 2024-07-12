package com.nhnacademy.front.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class RabbitMqConfig {

    @Value("${spring.rabbitmq.host}")
    private String rabbitmqHost;

    @Value("${spring.rabbitmq.port}")
    private int rabbitmqPort;

    @Value("${spring.rabbitmq.username}")
    private String rabbitmqUsername;

    @Value("${spring.rabbitmq.password}")
    private String rabbitmqPassword;

    private static final String queueName1 = "3RUNNER-COUPON-ISSUED";
    private static final String queueName2 = "3RUNNER-COUPON-EXPIRED-IN-THREE-DAY";
    private static final String exchangeName ="3RUNNER-EXCHANGE-NAME";
    private static final String routingKey1="3RUNNER-ROUTING-KEY";
    private static final String routingKey2 = "3RUNNER-ROUTING-KEY2";
    private static final int MAX_ATTEMPTS = 3;
    private static final long INITIAL_INTERVAL = 3L;
    private static final long MULTIPLIER = 2L;
    private static final long MAX_INTERVAL = 10L;

    @Bean
    public TopicExchange couponExchange() {
        return new TopicExchange(exchangeName);
    }

    @Bean
    public Queue expiredCouponQueue() {
        return new Queue(queueName1, false);
    }

    @Bean
    public Queue expiredThreeDayCouponQueue() {
        return new Queue(queueName2, false);
    }

    @Bean
    public Binding bindingExpiredCouponQueue() {
        return BindingBuilder.bind(expiredCouponQueue()).to(couponExchange()).with(routingKey1);
    }

    @Bean
    public Binding bindingExpiredThreeDayCouponQueue() {
        return BindingBuilder.bind(expiredThreeDayCouponQueue()).to(couponExchange()).with(routingKey2);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public MessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(rabbitmqHost);
        connectionFactory.setPort(rabbitmqPort);
        connectionFactory.setUsername(rabbitmqUsername);
        connectionFactory.setPassword(rabbitmqPassword);
        return connectionFactory;
    }


    /**
     * 메시지 수신 시 규약을 담당할 팩토리.
     *
     * @param connFactory the conn factory
     * @return the simple rabbit listener container factory
     */
    @Bean
    public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory(ConnectionFactory connFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connFactory);
        factory.setDefaultRequeueRejected(false);
        factory.setChannelTransacted(true);
        factory.setMessageConverter(jackson2JsonMessageConverter());
        factory.setAdviceChain(
                RetryInterceptorBuilder.stateless()
                        .maxAttempts(MAX_ATTEMPTS)
                        .backOffOptions(
                                Duration.ofSeconds(INITIAL_INTERVAL).toMillis(),
                                MULTIPLIER,
                                Duration.ofSeconds(MAX_INTERVAL).toMillis()
                        )
                        .recoverer(new RejectAndDontRequeueRecoverer())
                        .build());

        return factory;
    }
}