package com.mancala.controller;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mancala.dto.GameDto;
import com.mancala.dto.Movement;
import com.mancala.exceptions.ResourceNotFoundException;
import com.mancala.service.impl.GameServiceImpl;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private GameServiceImpl gameService;
	
	private ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	private GameController gameController;

	
	@Test
	@WithMockUser
	public void startGame_returnOK() throws Exception {

		when(gameService.getGame(ArgumentMatchers.anyString())).thenReturn(new GameDto());

		mockMvc.perform(get("/start").param("gameId", "random")).andDo(result -> {
			assertEquals(result.getResponse().getStatus(), HttpStatus.OK.value());
			
		});

	}
	
	@Test
	@WithMockUser
	public void Move_returnOK() throws Exception {

		when(gameService.getMove(new Movement())).thenReturn(new GameDto());

		mockMvc.perform(put("/move").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(new Movement()))).andDo(result -> {
					assertEquals(result.getResponse().getStatus(), HttpStatus.OK.value());
				});
	}
	
	@Test
	@WithMockUser
	public void Move_returnNotFound() throws Exception {

		when(gameService.getMove(new Movement())).thenThrow(ResourceNotFoundException.class);

		mockMvc.perform(put("/move").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(new Movement()))).andDo(result -> {
					assertEquals(result.getResponse().getStatus(), HttpStatus.NOT_FOUND.value());
				});
	}

}
