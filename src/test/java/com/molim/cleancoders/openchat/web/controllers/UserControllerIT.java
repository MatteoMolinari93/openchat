	package com.molim.cleancoders.openchat.web.controllers;

import static org.hamcrest.Matchers.is;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc 
public class UserControllerIT {
	
	@Autowired
	MockMvc mockMvc;
	
	@Test
	void getUsers() throws Exception {
		mockMvc.perform(get("/users"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaTypes.HAL_JSON))
			.andExpect(jsonPath("$._embedded.users").isArray())
			.andExpect(jsonPath("$._embedded.users.length()", is(3)));
	}

}
