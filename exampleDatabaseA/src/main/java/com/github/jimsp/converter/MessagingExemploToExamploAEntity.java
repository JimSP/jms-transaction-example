package com.github.jimsp.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.github.jimsp.canonical.MessagingExemplo;
import com.github.jimsp.entities.ExemploAEntity;

@Component
public class MessagingExemploToExamploAEntity implements Converter<MessagingExemplo, ExemploAEntity> {

	@Override
	public ExemploAEntity convert(final MessagingExemplo messagingExemplo) {
		return new ExemploAEntity(messagingExemplo.getDescricao());
	}

}
