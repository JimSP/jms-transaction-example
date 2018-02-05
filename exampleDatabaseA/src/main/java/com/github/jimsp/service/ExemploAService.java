package com.github.jimsp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.jimsp.canonical.MessagingExemplo;
import com.github.jimsp.converter.MessagingExemploToExamploAEntity;
import com.github.jimsp.entities.ExemploAEntity;
import com.github.jimsp.repositories.ExemploAEntityJpaRepository;

@Service
public class ExemploAService {

	@Autowired
	private ExemploAEntityJpaRepository exemploAEntityJpaRepository;

	@Autowired
	private MessagingExemploToExamploAEntity messagingExemploToExamploAEntity;

	@Transactional
	public void save(final MessagingExemplo messagingExemplo) {
		System.out.println("saveA");
		final ExemploAEntity exemploAEntity = messagingExemploToExamploAEntity.convert(messagingExemplo);
		exemploAEntityJpaRepository.save(exemploAEntity);
	}
	
	@Transactional
	public void saveWithError(final MessagingExemplo messagingExemplo) {
		System.out.println("saveAWithError");
		final ExemploAEntity exemploAEntity = messagingExemploToExamploAEntity.convert(messagingExemplo);
		exemploAEntityJpaRepository.save(exemploAEntity);
		throw new RuntimeException("[ERROR] save A");
	}

	public Long count() {
		return exemploAEntityJpaRepository.count();
	}

	public void delete() {
		exemploAEntityJpaRepository.deleteAll();
	}
}
