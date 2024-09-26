package com.bsha2nk.notifications;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bsha2nk.email.EmailService;
import com.bsha2nk.message.NotificationDTO;

@Service
public class NotificationService {
	
	@Autowired
	private EmailService emailService;

	public boolean sendNotification(NotificationDTO notification) {
		try {
			emailService.sendEmail("bsha2nk91@gmail.com", notification.getEvent(), notification.getMessage());			
		} catch (Exception e) {
			return false;
		}
		
		return true;
	}
}
