package com.shopix.api.storage;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import net.datafaker.Faker;

public class FileSystemStorageServiceTest {

	private Path root;
	private Faker faker;
	private FileSystemStorageService service;
	private String resource;
	private Long resource_id;
	private String filename1;
	private String filename2;
	
	@BeforeEach
	void setUp() {
		service = new FileSystemStorageService();
		faker = new Faker();
		root = Paths.get("upload-dir"); 
		resource = faker.text().text(5);
		resource_id = (long) faker.number().numberBetween(10, 100);
		filename1 = faker.text().text(4) + faker.file().extension();
		filename2 = faker.text().text(4) + faker.file().extension();
	}
	
	@AfterEach
	void tearDown() throws IOException {
		Files.deleteIfExists(root.resolve(resource).resolve(resource_id.toString()).resolve(filename1));
		Files.deleteIfExists(root.resolve(resource).resolve(resource_id.toString()).resolve(filename2));
		Files.delete(root.resolve(resource).resolve(resource_id.toString()));
		Files.delete(root.resolve(resource));
	}

	@Test
	void shouldCreateNewDir() {
		service.createDirIfNotExists(resource, Optional.of(resource_id.toString()));
		assertThat(Files.exists(root.resolve(resource).resolve(resource_id.toString()))).isTrue();
		assertThat(Files.isDirectory(root.resolve(resource).resolve(resource_id.toString()))).isTrue();
	}
	
	@Test
	void shouldListFilesByResource() throws IOException {
		Path dir = root.resolve(resource).resolve(resource_id.toString());
		Files.createDirectories(dir);
		Path p1 = Files.createFile(dir.resolve(filename1));
		Path p2 = Files.createFile(dir.resolve(filename2));
		List<Path> ps = service.listFilesByResource(resource, resource_id);
		assertThat(ps.size()).isEqualTo(2);
		assertThat(root.relativize(p1)).isIn(ps);
		assertThat(root.relativize(p2)).isIn(ps);
	}
	
	@Test
	void shouldStoreResource() throws IOException {
		byte[] content = faker.text().text().getBytes();
		Path dir = root.resolve(resource).resolve(resource_id.toString());
		Files.createDirectories(dir);
		MultipartFile file = new MockMultipartFile(filename1, content);
		service.store(file, Paths.get(resource).resolve(resource_id.toString()).resolve(filename1));
		assertThat(Files.exists(dir.resolve(filename1))).isTrue();
		assertThat(Files.readAllBytes(dir.resolve(filename1))).isEqualTo(content);
	}
	
	@Test
	void shouldLoadResource() throws IOException {
		byte[] content = faker.text().text().getBytes();
		Path dir = root.resolve(resource).resolve(resource_id.toString());
		Files.createDirectories(dir);
		Files.createFile(dir.resolve(filename1));
		Files.write(dir.resolve(filename1), content);
		Resource res = service.loadAsResource(Paths.get(resource).resolve(resource_id.toString()).resolve(filename1).toString());
		assertThat(res.getFilename()).isEqualTo(filename1);
		assertThat(res.getContentAsByteArray()).isEqualTo(content);
	}
}
