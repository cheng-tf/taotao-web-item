package com.taotao.springboot.web.item.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Title: RabbitMQConfig</p>
 * <p>Description: </p>
 * <p>Company: bupt.edu.cn</p>
 * <p>Created: 2018-06-09 20:42</p>
 * @author ChengTengfei
 * @version 1.0
 */
@Configuration
public class RabbitMQConfig {

    // ---------------------------------Fanout 交换器-------------------------------------
    // 当消息到达时，Fanout交换器会将消息投递给所有附加到本身的队列
    @Bean
    public Queue freemarkerMessage() {
        return new Queue("item-add.freemarker");
    }

    @Bean
    FanoutExchange itemAddExchange() {
        return new FanoutExchange("item-add");
    }

    @Bean
    Binding bindingExchangeFreemarker(Queue freemarkerMessage, FanoutExchange itemAddExchange) {
        return BindingBuilder.bind(freemarkerMessage).to(itemAddExchange);
    }

}