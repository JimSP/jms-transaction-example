package com.github.jimsp.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.github.jimsp.canonical.MessagingExemplo;

@Service
public class SaveAAndBService {

	private static final String ENDPOINT_COUNT_A = "http://localhost:8090/count";
	private static final String ENDPOINT_COUNT_B = "http://localhost:8091/count";

	@Autowired
	private JmsTemplate jmsTemplate;

	@PostConstruct
	public void init() {
		jmsTemplate.setSessionTransacted(Boolean.TRUE);
	}

	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = UnexpectedRollbackException.class)
	public void saveAB(final MessagingExemplo messagingExemploA, final MessagingExemplo messagingExemploB)
			throws UnexpectedRollbackException {
		System.out.println("saveAB");
		try {
			jmsTemplate.convertAndSend(messagingExemploA.getDestination(), messagingExemploA);
			jmsTemplate.convertAndSend(messagingExemploB.getDestination(), messagingExemploB);
		}catch (Exception e) {
			e.printStackTrace();
			throw new UnexpectedRollbackException("saveAB", e);
		}
	}

	public Long count() {
		final RestTemplate restTemplate = new RestTemplate();
		final Long countA = restTemplate.getForObject(ENDPOINT_COUNT_A, Long.class);
		final Long countB = restTemplate.getForObject(ENDPOINT_COUNT_B, Long.class);

		return countA + countB;
	}
}
