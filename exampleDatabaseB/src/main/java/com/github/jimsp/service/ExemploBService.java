package com.github.jimsp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.jimsp.canonical.MessagingExemplo;
import com.github.jimsp.converter.MessagingExemploToExamploBEntity;
import com.github.jimsp.entities.ExemploBEntity;
import com.github.jimsp.repositories.ExemploBEntityJpaRepository;

@Service
public class ExemploBService {

	@Autowired
	private ExemploBEntityJpaRepository exemploBEntityJpaRepository;

	@Autowired
	private MessagingExemploToExamploBEntity messagingExemploToExamploBEntity;

	@Transactional
	public void save(final MessagingExemplo messagingExemplo) {
		System.out.println("saveB");
		final ExemploBEntity exemploAEntity = messagingExemploToExamploBEntity.convert(messagingExemplo);
		exemploBEntityJpaRepository.save(exemploAEntity);
	}
	
	@Transactional
	public void saveWithError(final MessagingExemplo messagingExemplo) {
		System.out.println("saveBWithError");
		final ExemploBEntity exemploAEntity = messagingExemploToExamploBEntity.convert(messagingExemplo);
		exemploBEntityJpaRepository.save(exemploAEntity);
		throw new RuntimeException("[ERROR] save B");
	}

	public Long count() {
		return exemploBEntityJpaRepository.count();
	}
	
	public void delete() {
		exemploBEntityJpaRepository.deleteAll();
	}
}
