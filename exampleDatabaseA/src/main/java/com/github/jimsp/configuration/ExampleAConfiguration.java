package com.github.jimsp.configuration;

import java.sql.SQLException;
import java.util.Properties;

import javax.jms.ConnectionFactory;
import javax.sql.DataSource;

import org.apache.activemq.ActiveMQXAConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.boot.jta.atomikos.AtomikosConnectionFactoryBean;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.transaction.jta.JtaTransactionManager;

import com.atomikos.icatch.config.UserTransactionServiceImp;
import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;

@Configuration
@EnableJms
public class ExampleAConfiguration {

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

	@Value("${spring.datasource.username}")
	private String username;

	@Value("${spring.datasource.password}")
	private String password;

	@Value("${spring.datasource.url}")
	private String url;

	@Value("${spring.datasource.driverClassName}")
	private String driverClassName;

	@Bean(name = "atomikosDataSource", initMethod = "init", destroyMethod = "close")
	@Primary
	public DataSource atomikosDataSource() throws SQLException {
		final MysqlXADataSource mysqlXADataSource = new MysqlXADataSource();
		mysqlXADataSource.setUser(username);
		mysqlXADataSource.setPassword(password);
		mysqlXADataSource.setUrl(url);
		mysqlXADataSource.setPinGlobalTxToPhysicalConnection(Boolean.TRUE);

		final AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
		atomikosDataSourceBean.setXaDataSource(mysqlXADataSource);
		atomikosDataSourceBean.setUniqueResourceName("atomikosDataSource");
		atomikosDataSourceBean.setMinPoolSize(1);
		atomikosDataSourceBean.setMaxPoolSize(5);

		return atomikosDataSourceBean;
	}

	@Bean(name = "userTransactionServiceImp", initMethod = "init", destroyMethod = "shutdownForce")
	public UserTransactionServiceImp userTransactionServiceImp() {
		final Properties properties = new Properties();
		properties.setProperty("com.atomikos.icatch.service",
				"com.atomikos.icatch.standalone.UserTransactionServiceFactory");
		final UserTransactionServiceImp userTransactionServiceImp = new UserTransactionServiceImp(properties);
		return userTransactionServiceImp;
	}

	@Bean(name = "userTransactionManager", initMethod = "init", destroyMethod = "close")
	@DependsOn("userTransactionServiceImp")
	public UserTransactionManager userTransactionManager() {
		final UserTransactionManager userTransactionManager = new UserTransactionManager();
		userTransactionManager.setForceShutdown(Boolean.FALSE);
		userTransactionManager.setStartupTransactionService(Boolean.FALSE);
		return userTransactionManager;
	}

	@Bean
	@DependsOn("userTransactionServiceImp")
	public UserTransactionImp userTransactionImp() {
		return new UserTransactionImp();
	}

	@Bean("transactionManager")
	public JtaTransactionManager jtaTransactionManager() {
		return new JtaTransactionManager(userTransactionImp(), userTransactionManager());
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
