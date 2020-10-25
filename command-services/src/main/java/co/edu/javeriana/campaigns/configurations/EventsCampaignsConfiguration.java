package co.edu.javeriana.campaigns.configurations;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
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
    DirectExchange deadLetterExchange() {
        return new DirectExchange(deadExchange);
    }

    @Bean
    Queue dlq() {
        return QueueBuilder.durable(deadQueue).build();
    }

    @Bean
    Queue queue() {
        return QueueBuilder.durable(campaignQueue)
                .withArgument("x-dead-letter-exchange", deadExchange)
                .withArgument("x-dead-letter-routing-key", deadRoutingKey)
                .build();
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(campaignExchange);
    }

    @Bean
    Binding DLQbinding(Queue dlq, DirectExchange deadLetterExchange) {
        return BindingBuilder.bind(dlq).to(deadLetterExchange).with(deadRoutingKey);
    }

    @Bean
    Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(campaignRoutingKey);
    }

}
