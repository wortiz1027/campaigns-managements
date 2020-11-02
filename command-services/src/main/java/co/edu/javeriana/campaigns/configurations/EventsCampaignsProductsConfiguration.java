package co.edu.javeriana.campaigns.configurations;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventsCampaignsProductsConfiguration {

    @Value("${events.deadletter.campaignproduct.exchange}")
    String deadExchange;

    @Value("${events.deadletter.campaignproduct.queue}")
    String deadQueue;

    @Value("${events.deadletter.campaignproduct.routing-key}")
    String deadRoutingKey;

    @Value("${events.amqp.campaignproduct.exchange}")
    String campaignProductExchange;

    @Value("${events.amqp.campaignproduct.queue}")
    String campaignProductQueue;

    @Value("${events.amqp.campaignproduct.routing-key}")
    String campaignProductRoutingKey;

    @Bean
    DirectExchange deadLetterExchange2() {
        return new DirectExchange(deadExchange);
    }

    @Bean
    Queue dlq2() {
        return QueueBuilder.durable(deadQueue).build();
    }

    @Bean
    Queue queue2() {
        return QueueBuilder.durable(campaignProductQueue)
                .withArgument("x-dead-letter-exchange", deadExchange)
                .withArgument("x-dead-letter-routing-key", deadRoutingKey)
                .build();
    }

    @Bean
    DirectExchange exchange2() {
        return new DirectExchange(campaignProductExchange);
    }

    @Bean
    Binding DLQbinding2(Queue dlq2, DirectExchange deadLetterExchange2) {
        return BindingBuilder.bind(dlq2).to(deadLetterExchange2).with(deadRoutingKey);
    }

    @Bean
    Binding binding2(Queue queue2, DirectExchange exchange2) {
        return BindingBuilder.bind(queue2).to(exchange2).with(campaignProductRoutingKey);
    }

    @Bean
    MessageListenerContainer messageListenerContainer2(ConnectionFactory connectionFactory ) {
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
        simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
        simpleMessageListenerContainer.setQueues(queue2());

        return simpleMessageListenerContainer;
    }

}
