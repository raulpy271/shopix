package com.shopix.api.testUtils;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopix.api.dtos.AuthDTO;
import com.shopix.api.dtos.UserCreatedDTO;
import com.shopix.api.entities.User;
import com.shopix.api.repository.UserRepository;
import com.shopix.api.services.UserService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Component
public class IntegrationUtils {
	@Autowired
	private UserRepository repository;
	@Autowired
	private UserService service;
	@Autowired
	private MockMvc mvc;

	private ObjectMapper mapper = new ObjectMapper();
	
	public User storeUser(User user, String password) {
		UserCreatedDTO dto = new UserCreatedDTO(
			user.getUsername(),
			user.getFullname(),
			user.getEmail(),
			user.getRole(),
			password
		);
		return repository.findById(service.store(dto).id()).get();
	}

	public String MvcAuthenticate(User user, String password) throws Exception {
		AuthDTO auth = new AuthDTO(user.getUsername(), password);
		MvcResult result = mvc.perform(
				post("/api/users/auth")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(auth)))
			.andExpect(status().is2xxSuccessful())
			.andReturn();
		String res = result.getResponse().getContentAsString();
		Map<String, String> resMap = mapper.readValue(res, Map.class);
		return resMap.get("token");
	}
}
