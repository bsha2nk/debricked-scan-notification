package com.bsha2nk.notifications;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bsha2nk.email.EmailService;
import com.bsha2nk.message.NotificationDTO;
import com.bsha2nk.slack.SlackService;

@Service
public class NotificationService {

	@Autowired
	private EmailService emailService;
	
	@Autowired
	private SlackService slackService;

	public boolean sendNotification(NotificationDTO notification) {
		try {
			emailService.sendEmail("debrickedscan@gmail.com", notification.getEvent(), notification.getMessage());
			slackService.sendMessage("C07PDBNFZGA", notification.getEvent(), notification.getMessage());
		} catch (Exception e) {
			return false;
		}

		return true;
	}

}