package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jms.config.JmsListenerEndpointRegistrar;
import org.springframework.jms.config.SimpleJmsListenerEndpoint;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	String queue = "poc";
	
	@Autowired
	JmsListenerEndpointRegistrar registrar;
	
	@Autowired
	MessageProcessor processor;
	
	@EventListener(ApplicationReadyEvent.class)
	public void configureJMSListener() {
		SimpleJmsListenerEndpoint endpoint = new SimpleJmsListenerEndpoint();
		endpoint.setId("Id-01");
		endpoint.setDestination(queue);
		endpoint.setMessageListener(x -> processor.receiveMsg(x));
		registrar.registerEndpoint(endpoint);
	}
}
