package com.molim.cleancoders.openchat.web.controllers;

import static org.hamcrest.Matchers.is;
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

@SpringBootTest
@AutoConfigureMockMvc 
public class PostControllerIT {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	void userCanLogin() throws Exception {
	mockMvc.perform(post("/login").content("{\r\n"
			+ "	\"username\" : \"Matteo\",\r\n"
			+ "	\"password\" : \"1234StrongPassword\"\r\n"
			+ "}\r\n"
			+ "").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaTypes.HAL_JSON))
		.andExpect(jsonPath("$.username", is("Matteo")))
		.andExpect(jsonPath("$.id", is(12)))
		.andExpect(jsonPath("$.password").doesNotExist());
	}
	
	@Test
	void loginUnsuccessfulReturnsCorrectMessage() throws Exception {		
		mockMvc.perform(post("/login").content("{\r\n"
				+ "	\"username\" : \"Matteo\",\r\n"
				+ "	\"password\" : \"alki324d\"\r\n"
				+ "}\r\n").contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.message", is("Invalid credentials.")));
	}
}
