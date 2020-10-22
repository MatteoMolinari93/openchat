package com.molim.cleancoders.openchat.web.controllers;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import com.molim.cleancoders.openchat.exceptions.UserDoesNotExistException;
import com.molim.cleancoders.openchat.services.PostService;
import com.molim.cleancoders.openchat.web.models.PostDto;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "com.molim", uriPort = 80)
@WebMvcTest(PostController.class)
public class PostControllerTest {

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
						.dateTime(new Date())
						.build());
		
		mockMvc.perform(post("/user/{id}/posts", 1L).content("{\r\n"
				+ "	\"text\" : \"Hello everyone. I'm Alice.\"\r\n"
				+ "}").contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andExpect(content().contentType(MediaTypes.HAL_JSON))
			.andExpect(jsonPath("$.userId", is((int)Math.toIntExact(postCaptor.getValue().getUserId()))))
			.andExpect(jsonPath("$.id", is(1)))
			.andExpect(jsonPath("$.text", is(postCaptor.getValue().getText())))
			.andExpect(jsonPath("$.dateTime", notNullValue()))
			.andDo(document("/post-create",
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
	void postCreationUnsuccessful() throws Exception {
		when(postService.createPost(any())).thenThrow(new UserDoesNotExistException());
		
		mockMvc.perform(post("/user/{id}/posts", 1L).content("{\r\n"
				+ "	\"text\" : \"Hello everyone. I'm Alice.\"\r\n"
				+ "}").contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.message", is("User does not exist.")));
		
		verify(postService).createPost(Mockito.any(PostDto.class));
	}
	
	@Test
	void getUserPosts() throws Exception {
		final long userId = 3L;
		List<PostDto> userPosts = new ArrayList<PostDto>();
		userPosts.add(new PostDto(12L, userId, "Message1", new Date()));
		userPosts.add(new PostDto(17L, userId, "Message2", new Date()));
		
		
		when(postService.getPostsForUser(userId)).thenReturn(userPosts);

		mockMvc.perform(get("/user/{id}/posts", userId).content("{\r\n"
				+ "	\"text\" : \"Hello everyone. I'm Alice.\"\r\n"
				+ "}").contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaTypes.HAL_JSON))
			.andExpect(jsonPath("$._embedded.posts").isArray())
			//.andExpect(jsonPath("$.id", is(1)))
			//.andExpect(jsonPath("$.text", is(postCaptor.getValue().getText())))
			//.andExpect(jsonPath("$.dateTime", notNullValue()))
			//.andExpect(jsonPath("$._links").isNotEmpty())
			.andExpect(jsonPath("$._embedded").isNotEmpty())
			.andDo(document("/posts-get",
					pathParameters(
							parameterWithName("id").description("User id.")
							),
					responseFields(
							fieldWithPath("_embedded").description("Post Id"),
							fieldWithPath("_embedded.posts").description("List of posts"),
							fieldWithPath("_embedded.posts[].id").description("Post id"),
							fieldWithPath("_embedded.posts[].userId").description("User id"),
							fieldWithPath("_embedded.posts[].text").description("Content of the post"),
							fieldWithPath("_embedded.posts[].dateTime").description("Creation date")
					)));
			
			verify(postService).getPostsForUser(userId);
	}

}
