package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerEndpointRegistrar;
import org.springframework.jms.config.JmsListenerEndpointRegistry;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.transaction.PlatformTransactionManager;

import jakarta.jms.ConnectionFactory;
import jakarta.jms.Session;

@Configuration
@EnableJms
public class JmsConfig {

	@Autowired
	JmsListenerEndpointRegistry registry;
	
	@Autowired
	ConnectionFactory connfactory;

	@Bean
	public PlatformTransactionManager jmsTransactionManager(ConnectionFactory connectionFactory) {
		return new JmsTransactionManager(connectionFactory);
	}

	/*
	@Bean
	public ConnectionFactory connectionFactory() {
		ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
		activeMQConnectionFactory.setBrokerURL("tcp://localhost:61616");
		activeMQConnectionFactory.setTrustedPackages(Arrays.asList("com.mailshine.springbootstandaloneactivemq"));
		return activeMQConnectionFactory;
	}
	*/

	@Bean
	public JmsListenerContainerFactory<DefaultMessageListenerContainer> jmsListenerContainerFactory(
			ConnectionFactory connectionFactory) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);

		factory.setTransactionManager(jmsTransactionManager(connectionFactory));
		factory.setSessionTransacted(true);
		factory.setSessionAcknowledgeMode(Session.SESSION_TRANSACTED);

		return factory;
	}

	@Bean
	public JmsListenerEndpointRegistrar registrar() {
		JmsListenerEndpointRegistrar registrar = new JmsListenerEndpointRegistrar();
		registrar.setEndpointRegistry(registry);
		registrar.setContainerFactory(jmsListenerContainerFactory(connfactory));
		return registrar;
	}

}
