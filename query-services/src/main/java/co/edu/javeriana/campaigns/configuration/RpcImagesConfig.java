package co.edu.javeriana.campaigns.configuration;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RpcImagesConfig {

    @Value("${events.rpc.images.exchange}")
    private String rpcExchange;

    @Value("${events.rpc.images.queue}")
    private String rpcQueue;

    @Bean
    public DirectExchange directExchange1() {
        return new DirectExchange(rpcExchange);
    }

    @Bean
    public Queue response1(){
        return new Queue(rpcQueue);
    }

}
