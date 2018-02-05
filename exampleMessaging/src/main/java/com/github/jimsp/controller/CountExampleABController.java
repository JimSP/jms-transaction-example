package com.github.jimsp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.jimsp.canonical.MessagingExemplo;
import com.github.jimsp.service.SaveAAndBService;

@RestController
public class CountExampleABController {
	
	@Autowired
	private SaveAAndBService saveAAndBService;

	@GetMapping("count")
	@CrossOrigin
	public Long count() {
		return saveAAndBService.count();
	}
	
	@PostMapping
	public void save() {
		saveAAndBService.saveAB(new MessagingExemplo("exampleA", "testeC"), new MessagingExemplo("exampleB", "testeD"));
	}
}
