package com.shopix.api.controllers;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopix.api.repository.ProductRepository;
import com.shopix.api.storage.StorageService;

@RestController
@RequestMapping("/storage")
public class StorageController {

	@Autowired
	StorageService storageService;
	@Autowired
	ProductRepository productRepository;

	@GetMapping("/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

		Resource file = storageService.loadAsResource(filename);

		if (file == null)
			return ResponseEntity.notFound().build();

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}

	@GetMapping({"/product/{product_id}", "/product/{product_id}/{name}"})
	@ResponseBody
	public ResponseEntity<Resource> getProductIdResourse(@PathVariable Long product_id, @PathVariable(required=false) String name) {
		Path path;
		List<Path> paths = storageService.listResourceByProduct(product_id);
		if (paths.size() > 0) {
			if (name == null) {
				path = paths.get(0);
			} else {
				List<Path> matched = paths.stream().filter(p -> p.toString().contains(name)).toList();
				if (matched.size() > 0) {
					path = matched.get(0);
				} else {
					return ResponseEntity.notFound().build();
				}
			}
			Resource file = storageService.loadAsResource(path.toString());
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
					"attachment; filename=\"" + file.getFilename() + "\"").body(file);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/upload")
	public String handleFileUpload(@RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes ) {

		storageService.store(file, Paths.get(file.getOriginalFilename()));
		redirectAttributes.addFlashAttribute("message",
				"You successfully uploaded " + file.getOriginalFilename() + "!");

		return "redirect:/";
	}

	@PostMapping("/upload/product")
	public ResponseEntity<String> handleProductUpload(@RequestParam("file") MultipartFile file,
			@RequestParam("product_id") Long product_id,
			RedirectAttributes redirectAttributes) {
		boolean exists = productRepository.existsById(product_id);
		if (exists) {
			storageService.createDirIfNotExists(product_id.toString());
			storageService.store(file, Paths.get(product_id.toString()).resolve(Paths.get(file.getOriginalFilename())));
			redirectAttributes.addFlashAttribute("message",
					"You successfully uploaded " + file.getOriginalFilename() + "!");
			return new ResponseEntity("redirect:/", HttpStatus.CREATED);
		} else {
			return new ResponseEntity("Produto não encontrado!", HttpStatus.NOT_FOUND);
		}
	}

}
