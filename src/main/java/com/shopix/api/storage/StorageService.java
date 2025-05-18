package com.shopix.api.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;

public interface StorageService {

	void store(MultipartFile file, Path filename);
	
	boolean createDirIfNotExists(String dir);

	Path load(String filename);

	Resource loadAsResource(String filename);

	List<Path> listResourceByProduct(Long product_id);

}
