package com.github.jimsp.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.github.jimsp.canonical.MessagingExemplo;
import com.github.jimsp.service.SaveAAndBService;

@Component
public class TransactionExampleWithErrorRunner implements CommandLineRunner {

	@Autowired
	private SaveAAndBService saveAAndBService;

	@Override
	public void run(final String... args) throws Exception {
		saveAAndBService.saveAB(new MessagingExemplo("exampleA", "testeE"),
				new MessagingExemplo("exampleBWithError", "testeF"));
	}

}
