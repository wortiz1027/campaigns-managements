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
public class EventsCampaignsConfiguration {

    @Value("${events.deadletter.campaign.exchange}")
    String deadExchange;

    @Value("${events.deadletter.campaign.queue}")
    String deadQueue;

    @Value("${events.deadletter.campaign.routing-key}")
    String deadRoutingKey;

    @Value("${events.amqp.campaign.exchange}")
    String campaignExchange;

    @Value("${events.amqp.campaign.queue}")
    String campaignQueue;

    @Value("${events.amqp.campaign.routing-key}")
    String campaignRoutingKey;

    @Bean
    DirectExchange deadLetterExchange1() {
        return new DirectExchange(deadExchange);
    }

    @Bean
    Queue dlq1() {
        return QueueBuilder.durable(deadQueue).build();
    }

    @Bean
    Queue queue1() {
        return QueueBuilder.durable(campaignQueue)
                .withArgument("x-dead-letter-exchange", deadExchange)
                .withArgument("x-dead-letter-routing-key", deadRoutingKey)
                .build();
    }

    @Bean
    DirectExchange exchange1() {
        return new DirectExchange(campaignExchange);
    }

    @Bean
    Binding DLQbinding1(Queue dlq1, DirectExchange deadLetterExchange1) {
        return BindingBuilder.bind(dlq1).to(deadLetterExchange1).with(deadRoutingKey);
    }

    @Bean
    Binding binding1(Queue queue1, DirectExchange exchange1) {
        return BindingBuilder.bind(queue1).to(exchange1).with(campaignRoutingKey);
    }

    @Bean
    MessageListenerContainer messageListenerContainer1(ConnectionFactory connectionFactory ) {
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
        simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
        simpleMessageListenerContainer.setQueues(queue1());
        simpleMessageListenerContainer.setMessageListener(new RabbitMQListener());
        return simpleMessageListenerContainer;
    }

}
