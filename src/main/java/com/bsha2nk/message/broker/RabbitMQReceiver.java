package com.bsha2nk.message.broker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bsha2nk.message.NotificationDTO;
import com.bsha2nk.notifications.NotificationService;

@Service
public class RabbitMQReceiver {
	
	@Autowired
	private NotificationService notificationService;

	public boolean receiveMessage(NotificationDTO notificationDTO) {
		return notificationService.sendNotification(notificationDTO);
	}
}
