package com.molim.cleancoders.openchat.web.controllers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.molim.cleancoders.openchat.services.UserService;
import com.molim.cleancoders.openchat.web.models.UserDto;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "com.molim", uriPort = 80)
@WebMvcTest(UserController.class)
public class UserControllerTest {

	@MockBean
	UserService userService;
	
	@Autowired
	MockMvc mockMvc;
	
	@Test
	void getUsers() throws Exception {
		List<UserDto> users = new ArrayList<UserDto>();
		users.add(new UserDto(1L, "Matteo", "psw1", ""));
		users.add(new UserDto(13L, "Marta", "psw2", ""));
		
		when(userService.getUsers()).thenReturn(users);

		mockMvc.perform(get("/users"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaTypes.HAL_JSON))
			.andExpect(jsonPath("$._embedded.users").isArray())
			//.andExpect(jsonPath("$.id", is(1)))
			//.andExpect(jsonPath("$.text", is(postCaptor.getValue().getText())))
			//.andExpect(jsonPath("$.dateTime", notNullValue()))
			//.andExpect(jsonPath("$._links").isNotEmpty())
			.andExpect(jsonPath("$._embedded").isNotEmpty())
			.andExpect(jsonPath("$._embedded.users[*].password").doesNotExist())
			.andDo(document("/users-get",
					responseFields(
							fieldWithPath("_embedded").description(""),
							fieldWithPath("_embedded.users").description("Users list"),
							fieldWithPath("_embedded.users[].id").description("Post id"),
							fieldWithPath("_embedded.users[].username").description("Username"),
							fieldWithPath("_embedded.users[].about").description("Creation date")
					)));
			
			verify(userService).getUsers();
	}

}
