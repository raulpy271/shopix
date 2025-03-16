package com.shopix.api.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
	@GetMapping("/product")
	public List<String> list() 
	{
		ArrayList<String> li = new ArrayList<>();
		li.add("Hello");
		li.add("World");
		return li;
	}

}
