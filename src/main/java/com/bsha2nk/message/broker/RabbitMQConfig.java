package com.bsha2nk.message.broker;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bsha2nk.message.NotificationDTO;

import lombok.Getter;

@Configuration
@Getter
public class RabbitMQConfig {

	@Value("${notification.queue}")
	private String notificationExchange;

	@Value("${notification.exchange}")
	private String notificationQueue;

	@Value("${notification.routing-key}")
	private String notificationRoutingKey;

	@Bean
	TopicExchange notificationExchange() {
		return new TopicExchange(notificationExchange);
	}

	@Bean
	Queue notificationQueue() {
		return new Queue(notificationQueue, true);
	}

	@Bean
	Binding binding(Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(notificationRoutingKey);
	}

	@Bean
	SimpleMessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory, RabbitMQReceiver rabbitMQReceiver) {
		SimpleMessageListenerContainer messageListenerContainer = new SimpleMessageListenerContainer();
		messageListenerContainer.setAcknowledgeMode(AcknowledgeMode.MANUAL);
		messageListenerContainer.setConnectionFactory(connectionFactory);
		messageListenerContainer.setQueueNames(notificationQueue);
		messageListenerContainer.setMessageListener((ChannelAwareMessageListener)(message, channel) -> {
			NotificationDTO notificationDTO = (NotificationDTO) SerializationUtils.deserialize(message.getBody());

			boolean processed = rabbitMQReceiver.receiveMessage(notificationDTO);

			if (processed) {
				channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
			}
		});

		return messageListenerContainer;
	}

}