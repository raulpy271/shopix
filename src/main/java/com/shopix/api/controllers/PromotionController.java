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

import com.shopix.api.dtos.PromotionCreateDTO;
import com.shopix.api.dtos.PromotionResponseDTO;
import com.shopix.api.dtos.PromotionUpdateDTO;
import com.shopix.api.services.PromotionService;

@RestController
@RequestMapping("/api/promotions")
public class PromotionController {
	@Autowired
	PromotionService promotionService;

	@PostMapping
	public ResponseEntity<PromotionResponseDTO> store(@RequestBody PromotionCreateDTO dto)
	{
		PromotionResponseDTO created = promotionService.store(dto);
		return new ResponseEntity<>(created, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<PromotionResponseDTO>> list()
	{
		return new ResponseEntity<>(promotionService.list(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PromotionResponseDTO> show(@PathVariable Long id)
	{
		try {
			return new ResponseEntity<>(promotionService.show(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@PatchMapping
	public ResponseEntity<PromotionResponseDTO> update(@RequestBody PromotionUpdateDTO dto)
	{
		try {
			return new ResponseEntity<>(promotionService.update(dto), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id)
	{
		try {
			promotionService.destroy(id);
			return new ResponseEntity<>("Promoção deletada!", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
}
