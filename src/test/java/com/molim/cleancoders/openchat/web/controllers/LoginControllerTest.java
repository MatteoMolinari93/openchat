package com.molim.cleancoders.openchat.web.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.molim.cleancoders.openchat.exceptions.InvalidCredentialsException;
import com.molim.cleancoders.openchat.services.LoginService;
import com.molim.cleancoders.openchat.web.models.UserDto;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "com.molim", uriPort = 80)
@WebMvcTest(LoginController.class)
public class LoginControllerTest {
		
	@MockBean
	LoginService loginService;
	
	@Autowired
	MockMvc mockMvc;
	
	@Captor
	ArgumentCaptor<UserDto> userDtoCaptor;
	
	@Test
	void loginSuccessful() throws Exception {
		when(loginService.loginUser(Mockito.any(UserDto.class)))
			.thenReturn(UserDto.builder()
						.id(1L)
						.username("Alice")
						.password("alki324d")
						.about("I love playing the piano and travelling.")
						.build());
		
		mockMvc.perform(post("/login").content("{\r\n"
				+ "	\"username\" : \"Alice\",\r\n"
				+ "	\"password\" : \"alki324d\"\r\n"
				+ "}\r\n"
				+ "").contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaTypes.HAL_JSON))
			.andExpect(jsonPath("$.username", is("Alice")))
			.andExpect(jsonPath("$.id", notNullValue()))
			.andExpect(jsonPath("$.password").doesNotExist())
			.andDo(document("/login",
					requestFields(
							fieldWithPath("username").description("New User Username"),
							fieldWithPath("password").description("New User Password")
							),
					responseFields(
							fieldWithPath("id").description("User Id"),
							fieldWithPath("username").description("Username"),
							fieldWithPath("about").description("General information about the user.")
					)));
				
		verify(loginService).loginUser(Mockito.any(UserDto.class));
	}
	
	@Test
	void loginUnsuccessful() throws Exception {
		when(loginService.loginUser(any())).thenThrow(new InvalidCredentialsException());
		
		mockMvc.perform(post("/login").content("{\r\n"
				+ "	\"username\" : \"Alice\",\r\n"
				+ "	\"password\" : \"alki324d\",\r\n"
				+ "	\"about\" : \"I love playing the piano and travelling.\"\r\n"
				+ "}\r\n"
				+ "").contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.message", is("Invalid credentials.")));
		
		verify(loginService).loginUser(Mockito.any(UserDto.class));
	}
	

}
