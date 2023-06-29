package com.mancala.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.mancala.dto.GameDto;
import com.mancala.dto.PlayerDto;
import com.mancala.entity.GameEntity;
import com.mancala.entity.PlayerEntity;
import com.mancala.exceptions.ResourceNotFoundException;
import com.mancala.utils.Constants;

@SpringBootTest
public class mapperTest {
	
	@Autowired
	MancalaMapper mancalaMapper;
	
	@Test
	public void testConvertGameEntityThenReturnGameDto() throws ResourceNotFoundException {
		PlayerEntity playerEntityOne = new PlayerEntity();
		int [] pits1 = new int[] {4,4,4,4,4,4};
		playerEntityOne.setPits(pits1);
		playerEntityOne.setTreasury(0);
		playerEntityOne.setPlayerType(Constants.PLAYER1_KEY);
		
		PlayerEntity playerEntityTwo = new PlayerEntity();
		int [] pits2 = new int[] {4,4,4,4,4,4};
		playerEntityTwo.setPits(pits2);
		playerEntityTwo.setTreasury(0);
		playerEntityTwo.setPlayerType(Constants.PLAYER2_KEY);
	
		GameEntity gameEntity = new GameEntity();
		gameEntity.setId("random");
		gameEntity.setPlayers(playerEntityOne);
		gameEntity.setPlayers(playerEntityTwo);
		GameDto resultGameDto = mancalaMapper.convertGameEntityToGameDto(gameEntity);
		
		
		GameDto  gameDtoExpected = new GameDto();
		gameDtoExpected.setGameId("random");
		gameDtoExpected.setPlayer1(new PlayerDto());
		gameDtoExpected.setPlayer2(new PlayerDto());
		
		assertEquals(gameDtoExpected.getGameId(), resultGameDto.getGameId());
		assertEquals(gameDtoExpected.getPlayer1().getTreasury() , resultGameDto.getPlayer1().getTreasury());

		assertEquals( resultGameDto.getCurrentPlayer() , gameDtoExpected.getCurrentPlayer());
		assertEquals(resultGameDto.getWinner(), gameDtoExpected.getWinner());
		assertEquals(resultGameDto.isWinnerExist(), gameDtoExpected.isWinnerExist());
		assertEquals(resultGameDto.getPlayer1().getTreasury() ,0);
		assertEquals(resultGameDto.getPlayer1().getPits().length,6);
		assertEquals(resultGameDto.getPlayer1().getPits()[0] , 4);
		assertEquals(resultGameDto.getPlayer1().getPits()[1] , 4);
		assertEquals(resultGameDto.getPlayer1().getPits()[2] , 4);
		assertEquals(resultGameDto.getPlayer1().getPits()[3] , 4);
		assertEquals(resultGameDto.getPlayer1().getPits()[4] , 4);
		assertEquals(resultGameDto.getPlayer1().getPits()[5] , 4);
		
		assertEquals(resultGameDto.getPlayer2().getTreasury() ,0);
		assertEquals(resultGameDto.getPlayer2().getPits().length,6);
		assertEquals(resultGameDto.getPlayer2().getPits()[0] , 4);
		assertEquals(resultGameDto.getPlayer2().getPits()[1] , 4);
		assertEquals(resultGameDto.getPlayer2().getPits()[2] , 4);
		assertEquals(resultGameDto.getPlayer2().getPits()[3] , 4);
		assertEquals(resultGameDto.getPlayer2().getPits()[4] , 4);
		assertEquals(resultGameDto.getPlayer2().getPits()[5] , 4);
		
	}
	
	@Test
	public void testConvertGameDtoThenReturnGameEntity() {
	
		GameDto gameDto = new GameDto(new PlayerDto() , new PlayerDto());
		gameDto.setGameId("random");
		GameEntity resultGameEntity = mancalaMapper.convertGameDtoToGameEntity(gameDto);
		
		GameEntity expectedGameEntity = new GameEntity();
		expectedGameEntity.setId("random");
		expectedGameEntity.setCurrentPlayer(Constants.PLAYER1_KEY);
		
		assertEquals(resultGameEntity.getId(), expectedGameEntity.getId());
		assertEquals(resultGameEntity.getCurrentPlayer(), expectedGameEntity.getCurrentPlayer());
		assertEquals(resultGameEntity.getWinner(), expectedGameEntity.getWinner());
		assertEquals(resultGameEntity.isWinnerExist, expectedGameEntity.isWinnerExist);
		assertEquals(resultGameEntity.getPlayers().size() ,2);
		assertEquals(resultGameEntity.getPlayers().get(0).getTreasury() ,0);
		assertEquals(resultGameEntity.getPlayers().get(0).getPits().length,6);
		assertEquals(resultGameEntity.getPlayers().get(0).getPits()[0] , 4);
		assertEquals(resultGameEntity.getPlayers().get(0).getPits()[1] , 4);
		assertEquals(resultGameEntity.getPlayers().get(0).getPits()[2] , 4);
		assertEquals(resultGameEntity.getPlayers().get(0).getPits()[3] , 4);
		assertEquals(resultGameEntity.getPlayers().get(0).getPits()[4] , 4);
		assertEquals(resultGameEntity.getPlayers().get(0).getPits()[5] , 4);
		
		assertEquals(resultGameEntity.getPlayers().get(1).getTreasury() ,0);
		assertEquals(resultGameEntity.getPlayers().get(1).getPits().length,6);
		assertEquals(resultGameEntity.getPlayers().get(1).getPits()[0] , 4);
		assertEquals(resultGameEntity.getPlayers().get(1).getPits()[1] , 4);
		assertEquals(resultGameEntity.getPlayers().get(1).getPits()[2] , 4);
		assertEquals(resultGameEntity.getPlayers().get(1).getPits()[3] , 4);
		assertEquals(resultGameEntity.getPlayers().get(1).getPits()[4] , 4);
		assertEquals(resultGameEntity.getPlayers().get(1).getPits()[5] , 4);
		
	}
	
	
	@Test
	public void testConvertGameEntityThenReturnGameDtoException() throws Exception  {
	
		PlayerEntity playerEntityOne = new PlayerEntity();
		
		GameEntity gameEntity = new GameEntity();
		gameEntity.setId("random");
		gameEntity.setPlayers(playerEntityOne);
	
		ResourceNotFoundException resourceNotFoundException = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			mancalaMapper.convertGameEntityToGameDto(gameEntity);
			});
		
		assertTrue(resourceNotFoundException.getMessage().contains("No enough Players!"));
	}

	
	

}
