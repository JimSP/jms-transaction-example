package com.github.jimsp.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.jimsp.canonical.MessagingExemplo;
import com.github.jimsp.service.ExemploBService;

@Component
public class MessagingExemploMessageListener {
	
	@Autowired
	private ExemploBService exemploBService;

	@JmsListener(destination = "exampleB", containerFactory = "jmsListenerContainerFactory")
	@Transactional(propagation=Propagation.MANDATORY)
	public void onMessage(final MessagingExemplo messagingExemplo){
		exemploBService.save(messagingExemplo);
	}
	
	@JmsListener(destination = "exampleBWithError", containerFactory = "jmsListenerContainerFactory")
	@Transactional(propagation=Propagation.MANDATORY)
	public void onMessageWithError(final MessagingExemplo messagingExemplo) {
		exemploBService.saveWithError(messagingExemplo);
	}
}
