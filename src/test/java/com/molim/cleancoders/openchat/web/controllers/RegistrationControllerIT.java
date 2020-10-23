package com.molim.cleancoders.openchat.web.controllers;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
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
public class RegistrationControllerIT {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	void userCanRegister() throws Exception {
		mockMvc.perform(post("/registration").content("{\r\n"
				+ "	\"username\" : \"Alice\",\r\n"
				+ "	\"password\" : \"alki324d\",\r\n"
				+ "	\"about\" : \"I love playing the piano and travelling.\"\r\n"
				+ "}\r\n").contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andExpect(content().contentType(MediaTypes.HAL_JSON))
			.andExpect(jsonPath("$.username", is("Alice")))
			.andExpect(jsonPath("$.id", notNullValue()))
			.andExpect(jsonPath("$.about", is("I love playing the piano and travelling.")));
	}
	
	@Test
	void loginUnsuccessfulReturnsCorrectMessage() throws Exception {		
		mockMvc.perform(post("/registration").content("{\r\n"
				+ "	\"username\" : \"Alice\",\r\n"
				+ "	\"password\" : \"alki324d\",\r\n"
				+ "	\"about\" : \"I love playing the piano and travelling.\"\r\n"
				+ "}\r\n"
				+ "").contentType(MediaType.APPLICATION_JSON));
		
		mockMvc.perform(post("/registration").content("{\r\n"
				+ "	\"username\" : \"Alice\",\r\n"
				+ "	\"password\" : \"alki324d\",\r\n"
				+ "	\"about\" : \"I love playing the piano and travelling.\"\r\n"
				+ "}\r\n"
				+ "").contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.message", is("Username already in use.")));
		
	}
}
