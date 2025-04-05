package com.shopix.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopix.api.dtos.ReviewCreateDTO;
import com.shopix.api.dtos.ReviewResponseDTO;
import com.shopix.api.dtos.ReviewUpdateDTO;
import com.shopix.api.services.ReviewService;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
	@Autowired
	ReviewService reviewService;
	
	@PostMapping
	public ResponseEntity<ReviewResponseDTO> store(@RequestBody ReviewCreateDTO dto)
	{
		ReviewResponseDTO created = reviewService.store(dto);
		return new ResponseEntity<>(created, HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<ReviewResponseDTO>> list()
	{
		return new ResponseEntity<>(reviewService.list(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ReviewResponseDTO> show(@PathVariable Long id)
	{
		try {
			return new ResponseEntity<>(reviewService.show(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@PatchMapping
	public ResponseEntity<ReviewResponseDTO> update(@RequestBody ReviewUpdateDTO dto)
	{
		try {
			return new ResponseEntity<>(reviewService.update(dto), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id)
	{
		try {
			reviewService.destroy(id);
			return new ResponseEntity<>("Review deletado!", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
}
