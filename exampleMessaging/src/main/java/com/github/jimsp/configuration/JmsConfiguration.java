package com.github.jimsp.configuration;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQXAConnectionFactory;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.boot.jta.atomikos.AtomikosConnectionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
@EnableJms
public class JmsConfiguration {

	@Bean
	public JmsListenerContainerFactory<?> jmsListenerContainerFactory(final ConnectionFactory connectionFactory,
			final DefaultJmsListenerContainerFactoryConfigurer defaultJmsListenerContainerFactoryConfigurer) {
		final DefaultJmsListenerContainerFactory defaultJmsListenerContainerFactory = new DefaultJmsListenerContainerFactory();
		defaultJmsListenerContainerFactoryConfigurer.configure(defaultJmsListenerContainerFactory, connectionFactory);
		return defaultJmsListenerContainerFactory;
	}

	@Bean
	public MessageConverter jacksonJmsMessageConverter() {
		final MappingJackson2MessageConverter mappingJackson2MessageConverter = new MappingJackson2MessageConverter();
		mappingJackson2MessageConverter.setTargetType(MessageType.TEXT);
		mappingJackson2MessageConverter.setTypeIdPropertyName("_type");
		return mappingJackson2MessageConverter;
	}

	
	@Bean(name = "atomikosConnectionFactoryBean", initMethod = "init", destroyMethod = "close")
	public AtomikosConnectionFactoryBean atomikosConnectionFactoryBean() {
		final AtomikosConnectionFactoryBean atomikosConnectionFactoryBean = new AtomikosConnectionFactoryBean();
		atomikosConnectionFactoryBean.setUniqueResourceName("exampleTransaction");
		atomikosConnectionFactoryBean.setXaConnectionFactory(xaConnection());
		return atomikosConnectionFactoryBean;
	}

	private ActiveMQXAConnectionFactory xaConnection() {
		return new ActiveMQXAConnectionFactory("tcp://localhost:61616");
	}
}
