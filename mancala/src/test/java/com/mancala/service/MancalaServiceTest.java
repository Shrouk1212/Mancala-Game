package com.mancala.service;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.mancala.dto.GameDto;
import com.mancala.dto.Movement;
import com.mancala.dto.PlayerDto;
import com.mancala.entity.GameEntity;
import com.mancala.exceptions.ResourceNotFoundException;
import com.mancala.mapper.MancalaMapper;
import com.mancala.repository.MancalaRepository;
import com.mancala.service.impl.GameServiceImpl;
import com.mancala.utils.Constants;

@SpringBootTest
public class MancalaServiceTest {

	@MockBean
	MancalaRepository mancalaRepository;
	@MockBean
	MancalaMapper mancalaMapper;
	@Autowired
	GameServiceImpl gameService;

	@Test
	public void createGameInstance() throws Exception {
		assertNotNull(gameService.getGame("random"));
	}

	@Test
	public void whenMove_IdNotExists() throws Exception {
		Movement movement = new Movement("random", Constants.PLAYER1_KEY, 4);
		
		ResourceNotFoundException resourceNotFoundException = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
		gameService.getMove(movement);
		});

	}
	
	@Test
	public void whenMove_IdExists() throws Exception {
		Movement movement = new Movement("random", Constants.PLAYER1_KEY, 4);
		GameDto gameDto = new GameDto(new PlayerDto() , new PlayerDto());
		gameDto.setGameId("random");
		gameDto.setCurrentPlayer(Constants.PLAYER1_KEY);
		GameEntity gameEntity = new GameEntity();
		gameEntity.setId("random");
		Optional<GameEntity> gameEntityOptional = Optional.of(gameEntity);
		
		when(mancalaRepository.findById("random")).thenReturn(gameEntityOptional);
		when(mancalaMapper.convertGameEntityToGameDto(gameEntity)).thenReturn(gameDto)
		;
		GameDto game = gameService.getMove(movement);
		assertEquals(game.getCurrentPlayer(), Constants.PLAYER2_KEY);
		assertFalse(game.isWinnerExist);
		assertEquals(game.getPlayer1().getTreasury(), 1);

	}

}
