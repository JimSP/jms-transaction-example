package com.github.jimsp.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.github.jimsp.canonical.MessagingExemplo;
import com.github.jimsp.entities.ExemploBEntity;

@Component
public class MessagingExemploToExamploBEntity implements Converter<MessagingExemplo, ExemploBEntity> {

	@Override
	public ExemploBEntity convert(final MessagingExemplo messagingExemplo) {
		return new ExemploBEntity(messagingExemplo.getDescricao());
	}

}
