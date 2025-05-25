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
import com.shopix.api.repository.UserRepository;
import com.shopix.api.storage.StorageService;

@RestController
@RequestMapping("/storage")
public class StorageController {

	@Autowired
	StorageService storageService;
	@Autowired
	ProductRepository productRepository;
	@Autowired
	UserRepository userRepository;

	@GetMapping("/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

		Resource file = storageService.loadAsResource(filename);

		if (file == null)
			return ResponseEntity.notFound().build();

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}

	@GetMapping({"/{resource}/{resource_id}", "/{resource}/{resource_id}/{name}"})
	@ResponseBody
	public ResponseEntity<Resource> getResourceById(@PathVariable String resource, @PathVariable Long resource_id, @PathVariable(required=false) String name) {
		Path path;
		List<Path> paths = storageService.listFilesByResource(resource, resource_id);
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

	@PostMapping("/upload/{resource}")
	public ResponseEntity<String> handleResourceUpload(@RequestParam("file") MultipartFile file,
			@RequestParam("resource_id") Long resource_id,
			@PathVariable String resource,
			RedirectAttributes redirectAttributes) {
		boolean exists = false;
		switch (resource) {
			case "product":
				exists = productRepository.existsById(resource_id);
				break;
			case "user":
				exists = userRepository.existsById(resource_id);
				break;
			default:
				return new ResponseEntity("Recurso " + resource + " não suportado!", HttpStatus.BAD_REQUEST);
		}
		
		if (exists) {
			storageService.createDirIfNotExists(resource, Optional.of(resource_id.toString()));
			Path path = Paths.get(resource).resolve(resource_id.toString()).resolve(file.getOriginalFilename());
			storageService.store(file, path);
			redirectAttributes.addFlashAttribute("message",
					"You successfully uploaded " + file.getOriginalFilename() + "!");
			return new ResponseEntity("redirect:/" + path.toString(), HttpStatus.CREATED);
		} else {
			return new ResponseEntity("Recurso não encontrado!", HttpStatus.NOT_FOUND);
		}
	}

}
