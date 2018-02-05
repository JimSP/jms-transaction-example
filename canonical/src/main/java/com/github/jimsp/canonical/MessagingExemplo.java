package com.github.jimsp.canonical;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("messagingExemplo")
public class MessagingExemplo {

	private final String destination;
	private final String descricao;

	@JsonCreator
	public MessagingExemplo(@JsonProperty("destination") final String destination,
			@JsonProperty("descricao") final String descricao) {
		super();
		this.destination = destination;
		this.descricao = descricao;
	}

	@JsonGetter("destination")
	public String getDestination() {
		return destination;
	}

	@JsonGetter("descricao")
	public String getDescricao() {
		return descricao;
	}
}
