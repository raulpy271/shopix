package com.shopix.api.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public interface StorageService {

	void store(MultipartFile file, Path filename);
	
	boolean createDirIfNotExists(String dir, Optional<String> subdir);

	Path load(String filename);

	Resource loadAsResource(String filename);

	List<Path> listFilesByResource(String resource, Long resource_id);

}
