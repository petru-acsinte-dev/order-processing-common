package com.orderprocessing.common.configurations;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

	// Exchange identifier
    public static final String EXCHANGE = "order.events"; //$NON-NLS-1$

    // Routing keys
    public static final String ORDER_CONFIRMED_KEY = "order.confirmed"; //$NON-NLS-1$
    public static final String ORDER_SHIPPED_KEY   = "order.shipped"; //$NON-NLS-1$

    // Event queues
    public static final String SHIPMENT_ORDER_CONFIRMED_QUEUE = "shipment.order.confirmed"; //$NON-NLS-1$
    public static final String ORDERS_ORDER_SHIPPED_QUEUE     = "orders.order.shipped"; //$NON-NLS-1$

    // Dead letter queues
    public static final String SHIPMENT_ORDER_CONFIRMED_DLQ = "shipment.order.confirmed.dlq"; //$NON-NLS-1$
    public static final String ORDERS_ORDER_SHIPPED_DLQ     = "orders.order.shipped.dlq"; //$NON-NLS-1$

	private static final String DLX_KEY = "x-dead-letter-routing-key"; //$NON-NLS-1$
	private static final String DLX_EXCHANGE = "x-dead-letter-exchange"; //$NON-NLS-1$
	private static final String DEFAULT = ""; //$NON-NLS-1$

    // Exchange bean
    @Bean
    TopicExchange orderEventsExchange() {
        return ExchangeBuilder
                .topicExchange(EXCHANGE)
                .durable(true)
                .build();
    }

    // Shipment queue
    @Bean
    Queue shipmentOrderConfirmedQueue() {
        return QueueBuilder.durable(SHIPMENT_ORDER_CONFIRMED_QUEUE)
                .withArgument(DLX_EXCHANGE, DEFAULT)
                .withArgument(DLX_KEY, SHIPMENT_ORDER_CONFIRMED_DLQ)
                .build();
    }

    // Orders queue
    @Bean
    Queue ordersOrderShippedQueue() {
        return QueueBuilder.durable(ORDERS_ORDER_SHIPPED_QUEUE)
                .withArgument(DLX_EXCHANGE, DEFAULT)
                .withArgument(DLX_KEY, ORDERS_ORDER_SHIPPED_DLQ)
                .build();
    }

    // DLQ queues
    @Bean
    Queue shipmentOrderConfirmedDlq() {
        return QueueBuilder.durable(SHIPMENT_ORDER_CONFIRMED_DLQ).build();
    }

    @Bean
    Queue ordersOrderShippedDlq() {
        return QueueBuilder.durable(ORDERS_ORDER_SHIPPED_DLQ).build();
    }

    // binding confirmed orders to shipment queue
    @Bean
    Binding shipmentOrderConfirmedBinding() {
        return BindingBuilder
                .bind(shipmentOrderConfirmedQueue())
                .to(orderEventsExchange())
                .with(ORDER_CONFIRMED_KEY);
    }

    // binding shipped orders to orders queue
    @Bean
    Binding ordersOrderShippedBinding() {
    	return BindingBuilder
    			.bind(ordersOrderShippedQueue())
    			.to(orderEventsExchange())
    			.with(ORDER_SHIPPED_KEY);
    }

    // JSON message converter
    @Bean
    MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // RabbitTemplate with JSON converter
    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }
}
