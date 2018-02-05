package com.github.jimsp.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.jimsp.canonical.MessagingExemplo;
import com.github.jimsp.service.ExemploAService;

@Component
public class MessagingExemploMessageListener {

	@Autowired
	private ExemploAService exemploAService;

	@JmsListener(destination = "exampleA", containerFactory = "jmsListenerContainerFactory")
	@Transactional(propagation=Propagation.MANDATORY)
	public void onMessage(final MessagingExemplo messagingExemplo) throws UnexpectedRollbackException{
		exemploAService.save(messagingExemplo);
	}

	@JmsListener(destination = "exampleAWithError", containerFactory = "jmsListenerContainerFactory")
	@Transactional(propagation=Propagation.MANDATORY)
	public void onMessageWithError(final MessagingExemplo messagingExemplo) throws UnexpectedRollbackException{
		exemploAService.saveWithError(messagingExemplo);
	}
}
