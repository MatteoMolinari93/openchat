package com.molim.cleancoders.openchat.web.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.molim.cleancoders.openchat.services.RegistrationService;
import com.molim.cleancoders.openchat.web.models.UserDto;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.apache.catalina.User;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@TestPropertySource("classpath:application.properties")
@WebMvcTest(RegistrationController.class)
public class RegistrationControllerTest {
	
	@Value("${server.servlet.context-path}")
	private String contextPath;
	
	@MockBean
	RegistrationService registrationService;
	
	@Autowired
	MockMvc mockMvc;
	
	@Test
	void registerSuccessful() throws Exception {
		when(registrationService.registerUser(Mockito.any(UserDto.class)))
			.thenReturn(UserDto.builder()
						.id(1L)
						.username("Alice")
						.about("I love playing the piano and travelling.")
						.build());
		
		mockMvc.perform(post(contextPath + "/registration").content("{\r\n"
				+ "	\"username\" : \"Alice\",\r\n"
				+ "	\"password\" : \"alki324d\",\r\n"
				+ "	\"about\" : \"I love playing the piano and travelling.\"\r\n"
				+ "}\r\n"
				+ "").contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andExpect(content().contentType(MediaTypes.HAL_JSON))
			.andExpect(jsonPath("$.username", is("Alice")))
			.andExpect(jsonPath("$.id", notNullValue()))
			.andExpect(jsonPath("$.about", is("I love playing the piano and travelling.")));
		
		verify(registrationService).registerUser(Mockito.any(UserDto.class));
	}
	
	@Test
	void registerUnsuccessful() throws Exception {
		System.out.println(contextPath);
		
		mockMvc.perform(post(contextPath + "/registration").content("{\r\n"
				+ "	\"username\" : \"Alice\",\r\n"
				+ "	\"password\" : \"alki324d\",\r\n"
				+ "	\"about\" : \"I love playing the piano and travelling.\"\r\n"
				+ "}\r\n"
				+ ""))
			.andExpect(status().isBadRequest())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}
	

}
