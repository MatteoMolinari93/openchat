package com.molim.cleancoders.openchat.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;

@TestPropertySource("classpath:application.properties")
@WebMvcTest(RegistrationController.class)
public class RegistrationControllerTest {
	
	@Value("${server.servlet.context-path}")
	private String contextPath;
	
	@Autowired
	MockMvc mockMvc;
	
	@Test
	void registerSuccessful() throws Exception {
		System.out.println(contextPath);
		
		mockMvc.perform(post(contextPath + "/registration"))
			.andExpect(status().isCreated())
			.andExpect(content().contentType(MediaTypes.HAL_JSON));
	}
	
	@Test
	void registerUnsuccessful() throws Exception {
		System.out.println(contextPath);
		
		mockMvc.perform(post(contextPath + "/registration"))
			.andExpect(status().isBadRequest())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}
	

}
