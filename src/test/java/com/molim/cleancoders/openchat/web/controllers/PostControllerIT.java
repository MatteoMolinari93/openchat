package com.molim.cleancoders.openchat.web.controllers;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc 
@Transactional
public class PostControllerIT {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	void createPostSuccessful() throws Exception {
		mockMvc.perform(post("/users/{id}/posts", 12L).content("{\r\n"
				+ "	\"text\" : \"Hello everyone. I'm Matteo.\"\r\n"
				+ "}").contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andExpect(content().contentType(MediaTypes.HAL_JSON))
			.andExpect(jsonPath("$.userId", is(12)))
			.andExpect(jsonPath("$.id", notNullValue()))
			.andExpect(jsonPath("$.text", is("Hello everyone. I'm Matteo.")))
			.andExpect(jsonPath("$.dateTime", notNullValue()));
	}
	
	@Test
	void postCreationUnsuccessful() throws Exception {
		mockMvc.perform(post("/users/{id}/posts", 200L).content("{\r\n"
				+ "	\"text\" : \"Hello everyone. I'm Alice.\"\r\n"
				+ "}").contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.message", is("User does not exist.")));
	}
	
	@Test
	void getUserPosts() throws Exception {
		final long userId = 12L;

		mockMvc.perform(get("/users/{id}/posts", userId))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaTypes.HAL_JSON))
			.andExpect(jsonPath("$._embedded.posts").isArray())
			.andExpect(jsonPath("$._embedded").isNotEmpty())
			.andExpect(jsonPath("$._embedded.posts").isArray())
			.andExpect(jsonPath("$._embedded.posts.length()", is(2)));
	}
}
