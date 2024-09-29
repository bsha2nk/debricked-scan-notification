package com.bsha2nk.notifications;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.bsha2nk.email.EmailService;
import com.bsha2nk.message.NotificationDTO;
import com.bsha2nk.slack.SlackService;

@ExtendWith(SpringExtension.class)
public class NotificationServiceTest {
	
	@Mock
	private EmailService emailService;
	
	@Mock
	private SlackService slackService;
	
	@InjectMocks
	private NotificationService notificationService;
	
	@Test
	void test_email_slack_invocation() {
		notificationService.sendNotification(mock(NotificationDTO.class));
		
		verify(emailService).sendEmail(any(), any(), any());
		verify(slackService).sendMessage(any(), any(), any());
	}
	
}