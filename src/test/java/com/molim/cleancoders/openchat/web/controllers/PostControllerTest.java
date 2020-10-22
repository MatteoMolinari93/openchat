package com.molim.cleancoders.openchat.web.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.molim.cleancoders.openchat.exceptions.InvalidCredentialsException;
import com.molim.cleancoders.openchat.exceptions.UserDoesNotExistException;
import com.molim.cleancoders.openchat.services.LoginService;
import com.molim.cleancoders.openchat.services.PostService;
import com.molim.cleancoders.openchat.web.models.PostDto;
import com.molim.cleancoders.openchat.web.models.UserDto;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "com.molim", uriPort = 80)
@TestPropertySource("classpath:application.properties")
@WebMvcTest(PostController.class)
public class PostControllerTest {
	
	@Value("${server.servlet.context-path}")
	private String contextPath;
	
	@MockBean
	PostService postService;
	
	@Autowired
	MockMvc mockMvc;
	
	@Captor
	private ArgumentCaptor<PostDto> postCaptor;
	
	@Test
	void createPostSuccessful() throws Exception {
		when(postService.createPost(postCaptor.capture()))
			.thenReturn(PostDto.builder()
						.id(1L)
						.userId(1L)
						.text("Hello everyone. I'm Alice.")
						.dateTime(LocalDateTime.now())
						.build());
		
		mockMvc.perform(post(contextPath + "/user/{id}/post", 1L).content("{\r\n"
				+ "	\"text\" : \"Hello everyone. I'm Alice.\"\r\n"
				+ "}").contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andExpect(content().contentType(MediaTypes.HAL_JSON))
			.andExpect(jsonPath("$.userId", is((int)Math.toIntExact(postCaptor.getValue().getUserId()))))
			.andExpect(jsonPath("$.id", is(1)))
			.andExpect(jsonPath("$.text", is(postCaptor.getValue().getText())))
			.andExpect(jsonPath("$.dateTime", notNullValue()))
			.andDo(document("/post",
					requestFields(
							fieldWithPath("text").description("text of the post")
							),
					responseFields(
							fieldWithPath("id").description("Post Id"),
							fieldWithPath("userId").description("Id owner"),
							fieldWithPath("text").description("Content of the post"),
							fieldWithPath("dateTime").description("Creation dateTime")
					)));
				
		verify(postService).createPost(Mockito.any(PostDto.class));
	}
	
	@Test
	void loginUnsuccessful() throws Exception {
		when(postService.createPost(any())).thenThrow(new UserDoesNotExistException());
		
		mockMvc.perform(post(contextPath + "/user/{id}/post", 1L).content("{\r\n"
				+ "	\"text\" : \"Hello everyone. I'm Alice.\"\r\n"
				+ "}").contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.message", is("User does not exist.")));
		
		verify(postService).createPost(Mockito.any(PostDto.class));
	}
	

}
