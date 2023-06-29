package com.mancala.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.Assert;
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
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

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
	public void testGetGameExistingGame() throws ResourceNotFoundException {

		String gameId = "existingGameId";
		GameDto expectedGameDto = new GameDto(new PlayerDto(), new PlayerDto());
		expectedGameDto.setGameId(gameId);
		GameEntity gameEntity = new GameEntity();
		gameEntity.setId(gameId);
		Optional<GameEntity> gameEntityOptional = Optional.of(gameEntity);

		GameDto resultGameDto = new GameDto(new PlayerDto(), new PlayerDto());
		resultGameDto.setGameId(gameId);
		when(mancalaMapper.convertGameEntityToGameDto(gameEntity)).thenReturn(resultGameDto);
		when(mancalaRepository.findById(gameId)).thenReturn(gameEntityOptional);

		GameDto result = gameService.getGame(gameId);
		Assert.assertEquals(expectedGameDto, result);
	}

	@Test
	public void testGetGameNewGame() {

		String gameId = "nonExistingGameId";
		GameDto expectedGameDto = new GameDto(new PlayerDto(), new PlayerDto());
		expectedGameDto.setGameId(gameId);
		when(mancalaMapper.convertGameDtoToGameEntity(expectedGameDto)).thenReturn(new GameEntity());
		GameDto result = gameService.getGame(gameId);
		result.setGameId(gameId);

		verify(mancalaRepository, times(1)).save(any());
		Assert.assertEquals(expectedGameDto, result);
	}

	@Test
	public void whenMove_IdNotExists() throws Exception {
		Movement movement = new Movement("random", Constants.PLAYER1_KEY, 4);

		ResourceNotFoundException resourceNotFoundException = Assertions.assertThrows(ResourceNotFoundException.class,
				() -> {
					gameService.getMove(movement);
				});

	}

	@Test
	public void whenMove_LastStoneInOwnTreasury() throws Exception {
		Movement movement = new Movement("random", Constants.PLAYER1_KEY, 2);

		// Create a test game with initial state
		GameDto gameDto = new GameDto();
		gameDto.setGameId("random");
		gameDto.setCurrentPlayer(Constants.PLAYER1_KEY);
		gameDto.setPlayer1(new PlayerDto());
		gameDto.setPlayer2(new PlayerDto());

		GameEntity gameEntity = new GameEntity();
		gameEntity.setId("random");
		Optional<GameEntity> gameEntityOptional = Optional.of(gameEntity);

		when(mancalaRepository.findById("random")).thenReturn(gameEntityOptional);
		when(mancalaMapper.convertGameEntityToGameDto(gameEntity)).thenReturn(gameDto);

		GameDto resultGame = gameService.getMove(movement);
		assertEquals(resultGame.getCurrentPlayer(), Constants.PLAYER1_KEY);
		assertFalse(resultGame.isWinnerExist);
		assertEquals(resultGame.getPlayer1().getTreasury(), 1);
		assertEquals(0, resultGame.getPlayer1().getPits()[2]);
		assertEquals(5, resultGame.getPlayer1().getPits()[3]); 
		assertEquals(4, resultGame.getPlayer2().getPits()[0]); 
		assertEquals(4, resultGame.getPlayer2().getPits()[1]); 
		assertEquals(1, resultGame.getPlayer1().getTreasury());
		assertEquals(1, resultGame.getPlayer1().getTreasury());

	}

	@Test
	public void whenMove_LastStoneInInEmptyCounter() throws Exception {
		Movement movement = new Movement("random", Constants.PLAYER1_KEY, 2);

		GameDto gameDto = new GameDto();
		gameDto.setGameId("random");
		gameDto.setCurrentPlayer(Constants.PLAYER1_KEY);
		gameDto.setPlayer1(new PlayerDto());
		gameDto.setPlayer2(new PlayerDto());

		GameEntity gameEntity = new GameEntity();
		gameEntity.setId("random");
		Optional<GameEntity> gameEntityOptional = Optional.of(gameEntity);

		when(mancalaRepository.findById("random")).thenReturn(gameEntityOptional);
		when(mancalaMapper.convertGameEntityToGameDto(gameEntity)).thenReturn(gameDto);

		GameDto resultGame = gameService.getMove(movement);
		assertEquals(resultGame.getCurrentPlayer(), Constants.PLAYER1_KEY);
		assertFalse(resultGame.isWinnerExist);
		assertEquals(resultGame.getPlayer1().getTreasury(), 1);
		assertEquals(0, resultGame.getPlayer1().getPits()[2]); 
		assertEquals(5, resultGame.getPlayer1().getPits()[3]); 
		assertEquals(4, resultGame.getPlayer2().getPits()[0]); 
		assertEquals(4, resultGame.getPlayer2().getPits()[1]);
		assertEquals(1, resultGame.getPlayer1().getTreasury());
		assertEquals(1, resultGame.getPlayer1().getTreasury());

	}

	@Test
	public void whenMove_LastStoneInEmptyounterPit() throws ResourceNotFoundException {
		Movement movement = new Movement("random", Constants.PLAYER1_KEY, 0);

		GameDto gameDto = new GameDto();
		gameDto.setGameId("random");
		gameDto.setCurrentPlayer(Constants.PLAYER1_KEY);
		gameDto.setPlayer1(new PlayerDto());
		gameDto.setPlayer2(new PlayerDto());

		GameEntity gameEntity = new GameEntity();
		gameEntity.setId("random");
		Optional<GameEntity> gameEntityOptional = Optional.of(gameEntity);

		when(mancalaRepository.findById("random")).thenReturn(gameEntityOptional);
		when(mancalaMapper.convertGameEntityToGameDto(gameEntity)).thenReturn(gameDto);

		GameDto resultGame = gameService.getMove(movement);

		Movement movement2 = new Movement("random", Constants.PLAYER2_KEY, 3);

		when(mancalaRepository.findById("random")).thenReturn(gameEntityOptional);
		when(mancalaMapper.convertGameEntityToGameDto(gameEntity)).thenReturn(resultGame);

		resultGame = gameService.getMove(movement2);
		assertEquals(7, resultGame.getPlayer2().getTreasury());

	}

}
