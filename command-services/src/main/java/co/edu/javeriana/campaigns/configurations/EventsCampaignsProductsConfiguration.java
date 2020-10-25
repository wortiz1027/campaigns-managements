package co.edu.javeriana.campaigns.configurations;

import org.springframework.amqp.core.*;
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
    String campaignExchange;

    @Value("${events.amqp.campaignproduct.queue}")
    String campaignQueue;

    @Value("${events.amqp.campaignproduct.routing-key}")
    String campaignRoutingKey;

    @Bean("cp-deatletter-exchange")
    DirectExchange deadLetterExchange() {
        return new DirectExchange(deadExchange);
    }

    @Bean("cp-dlq")
    Queue dlq() {
        return QueueBuilder.durable(deadQueue).build();
    }

    @Bean("cp-queue")
    Queue queue() {
        return QueueBuilder.durable(campaignQueue)
                .withArgument("x-dead-letter-exchange", deadExchange)
                .withArgument("x-dead-letter-routing-key", deadRoutingKey)
                .build();
    }

    @Bean("cp-exchange")
    DirectExchange exchange() {
        return new DirectExchange(campaignExchange);
    }

    @Bean("cp-dlq-binding")
    Binding DLQbinding(Queue dlq, DirectExchange deadLetterExchange) {
        return BindingBuilder.bind(dlq).to(deadLetterExchange).with(deadRoutingKey);
    }

    @Bean("cp-binding")
    Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(campaignRoutingKey);
    }

}
