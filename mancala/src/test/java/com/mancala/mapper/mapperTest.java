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

@SpringBootTest
public class mapperTest {
	
	@Autowired
	MancalaMapper mancalaMapper;
	
	@Test
	public void convertGameEntityToGameDto() throws ResourceNotFoundException {
		PlayerEntity playerEntityOne = new PlayerEntity();
		PlayerEntity playerEntityTwo = new PlayerEntity();
		List<PlayerEntity> playerEntityList = new ArrayList<PlayerEntity>();
		
		playerEntityList.add(playerEntityOne);
		playerEntityList.add(playerEntityTwo);
		GameEntity gameEntity = new GameEntity();
		gameEntity.setId("random");
		gameEntity.setPlayer(playerEntityList);
		GameDto resultGameDto = mancalaMapper.convertGameEntityToGameDto(gameEntity);
		
		
		GameDto  gameDtoExpected = new GameDto();
		gameDtoExpected.setGameId("random");
		gameDtoExpected.setPlayer1(new PlayerDto());
		gameDtoExpected.setPlayer2(new PlayerDto());
		assertEquals(gameDtoExpected.getGameId(), resultGameDto.getGameId());
		assertEquals(gameDtoExpected.getPlayer1().treasury , resultGameDto.getPlayer1().treasury);
		
		
	}
	
	@Test
	public void convertGameDtoToGameEntity() {
	
		GameDto gameDto = new GameDto(new PlayerDto() , new PlayerDto());
		gameDto.setGameId("random");
		GameEntity resultGameEntity = mancalaMapper.convertGameDtoToGameEntity(gameDto);
		
		GameEntity GameEntity = new GameEntity();
		GameEntity.setId("random");
		
		assertEquals(resultGameEntity.getId(), GameEntity.getId());
		assertEquals(resultGameEntity.getPlayer().size() ,2);
	}
	
	
	@Test
	public void convertGameEntityToGameDto_Exception() throws Exception  {
	
		PlayerEntity playerEntityOne = new PlayerEntity();
		List<PlayerEntity> playerEntityList = new ArrayList<PlayerEntity>();
		
		playerEntityList.add(playerEntityOne);
		GameEntity gameEntity = new GameEntity();
		gameEntity.setId("random");
		gameEntity.setPlayer(playerEntityList);
	
		ResourceNotFoundException resourceNotFoundException = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			mancalaMapper.convertGameEntityToGameDto(gameEntity);
			});
		
		assertTrue(resourceNotFoundException.getMessage().contains("No enough Players!"));
	}

	
	

}
