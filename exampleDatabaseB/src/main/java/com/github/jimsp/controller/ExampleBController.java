package com.github.jimsp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.jimsp.service.ExemploBService;

@RestController
public class ExampleBController {

	@Autowired
	private ExemploBService exemploBService;

	@GetMapping("count")
	@CrossOrigin
	public Long count() {
		return exemploBService.count();
	}

	@DeleteMapping("delete")
	@CrossOrigin
	public void delete() {
		exemploBService.delete();
	}
}
