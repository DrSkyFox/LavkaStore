package org.shop.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

@Slf4j
@Configuration
public class RabbitConfiguration {

    @Value("${queuename:#{null}")
    private String queueName;

    @Value("${exchangename:#{null}")
    private String exchangeName;

    @Value("${routingkey:#{null}}")
    private String routingKey;

    @Bean
    public Queue queue() {
        log.info("Init params: queue name - {}", queueName);
        return new Queue(queueName, false);
    }

    @Bean
    public DirectExchange exchange() {
        log.info("Init params: exchange name - {}", exchangeName);
        return new DirectExchange(exchangeName);
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        log.info("Init params: binding ....");
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }


}
