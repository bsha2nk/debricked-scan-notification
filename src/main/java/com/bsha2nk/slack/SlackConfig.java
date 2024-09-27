package com.bsha2nk.slack;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.slack.api.Slack;

@Configuration
public class SlackConfig {

	@Bean
	Slack slackInstance() {
		return Slack.getInstance();
	}

}