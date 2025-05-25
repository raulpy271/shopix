package com.shopix.api.storage;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileSystemStorageService implements StorageService {

	private String location = "upload-dir";
	private Path rootLocation = Paths.get(location);

	@Override
	public void store(MultipartFile file, Path filename) {
		try {
			if (file.isEmpty()) {
				throw new StorageException("Failed to store empty file.");
			}
			Path destinationFile = this.rootLocation.resolve(filename)
					.normalize().toAbsolutePath();
			if (
					!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath()) &&
					!destinationFile.getParent().getParent().equals(this.rootLocation.toAbsolutePath()) &&
					!destinationFile.getParent().getParent().getParent().equals(this.rootLocation.toAbsolutePath())
				) {
				// This is a security check
				throw new StorageException(
						"Cannot store file outside current directory.");
			}
			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, destinationFile,
					StandardCopyOption.REPLACE_EXISTING);
			}
		}
		catch (IOException e) {
			throw new StorageException("Failed to store file.", e);
		}
	}
	
	public List<Path> listFilesByResource(String resource, Long resource_id) {
		try {
			Path productPrefix = this.rootLocation.resolve(resource).resolve(resource_id.toString());
			return Files.walk(productPrefix, 1)
				.filter(path -> !path.equals(this.rootLocation) && !Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS))
				.map(this.rootLocation::relativize)
				.toList();
		} catch (IOException e) {
			return List.of();
		}
	}


	@Override
	public Path load(String filename) {
		return rootLocation.resolve(filename);
	}

	@Override
	public Resource loadAsResource(String filename) {
		try {
			Path file = load(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			}
			else {
				throw new StorageException("Could not read file: " + filename);

			}
		}
		catch (MalformedURLException e) {
			throw new StorageException("Could not read file: " + filename, e);
		}
	}

	@Override
	public boolean createDirIfNotExists(String dir, Optional<String> subdir) {
		try {
			Path dirPath = this.rootLocation.resolve(Paths.get(dir));
			if (subdir.isPresent()) {
				dirPath = dirPath.resolve(Paths.get(subdir.get()));
			}
			if (!Files.exists(dirPath, LinkOption.NOFOLLOW_LINKS) && !Files.isDirectory(dirPath, LinkOption.NOFOLLOW_LINKS)) {
				Files.createDirectories(dirPath);
				return true;
			}
			return false;
		} catch (IOException e) {
			throw new StorageException("Could not create directory: " + dir, e);
		}
	}
}
