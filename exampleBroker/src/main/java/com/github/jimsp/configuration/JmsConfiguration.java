package com.github.jimsp.configuration;

import java.net.URI;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.TransportConnector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

@Configuration
@EnableJms
public class JmsConfiguration {
	
	@Bean(initMethod = "start", destroyMethod = "stop")
	public BrokerService brokerService() throws Exception {
		final BrokerService brokerService = new BrokerService();

		final TransportConnector connector = new TransportConnector();
		connector.setUri(new URI("tcp://localhost:61616"));
		brokerService.addConnector(connector);
		return brokerService;
	}
}
