package com.bsha2nk.slack;

import java.io.IOException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bsha2nk.exception.SlackMessageException;
import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SlackService {

	@Value("${slack-bot-token}")
	private String token;

	@Autowired
	private Slack slack;
	
	public void sendMessage(String channelId, String event, String message) {
		ChatPostMessageResponse response = null;
		try {
			response = slack.methods(token).chatPostMessage(req -> req
					.channel(channelId)
					.text(String.format("<!channel> *%s* \n %s", event, message))
					);
		} catch (IOException | SlackApiException e) {
			throw new SlackMessageException("Notification could not be sent to Slack channel with id " + channelId);
		}
		
		if (Objects.nonNull(response) && !response.isOk()) {
			throw new SlackMessageException("Notification could not be sent to Slack channel with id " + channelId);
		}

	}

}