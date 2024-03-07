package com.example.demo;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MessageProcessor {

	@Async("IE")
	@Transactional
	public void receiveMsg(final Message message) {
		TextMessage textMessage = (TextMessage) message;
		String text;
		try {
			text = textMessage.getText();
			log.error("Message aaya: "+text);
			if(text.equals("boom")) {
				throw new RuntimeException("GM");
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
