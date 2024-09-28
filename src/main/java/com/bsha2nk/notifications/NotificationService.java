package com.bsha2nk.notifications;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bsha2nk.email.EmailService;
import com.bsha2nk.message.NotificationDTO;
import com.bsha2nk.slack.SlackService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NotificationService {

	@Autowired
	private EmailService emailService;
	
	@Autowired
	private SlackService slackService;
	
	@Value("${slack-channel-id}")
	private String slackChannelID;
	
	@Value("${email-recipient}")
	private String emailRecipient;

	public boolean sendNotification(NotificationDTO notification) {
		try {
			emailService.sendEmail(emailRecipient, notification.getEvent(), notification.getMessage());
			slackService.sendMessage(slackChannelID, notification.getEvent(), notification.getMessage());
		} catch (Exception e) {
			log.warn(e.getMessage());
			return false;
		}

		return true;
	}

}