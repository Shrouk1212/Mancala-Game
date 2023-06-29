package com.mancala.repository;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mancala.entity.GameEntity;
import com.mancala.entity.PlayerEntity;

@SpringBootTest
public class RepositoryTest {
	
	
    @Autowired
    MancalaRepository gameRepository;

    @Test
    public void testRepositoryThensucceed() throws  Exception {
    	
    	PlayerEntity playerEntityOne = new PlayerEntity();
		PlayerEntity playerEntityTwo = new PlayerEntity();
		List<PlayerEntity> playerEntityList = new ArrayList<PlayerEntity>();
		
		playerEntityList.add(playerEntityOne);
		playerEntityList.add(playerEntityTwo);
		GameEntity gameEntity = new GameEntity();
		gameEntity.setId("random");
		GameEntity saved= this.gameRepository.save(gameEntity);

        Assert.assertNotNull(saved.getId());

        List<GameEntity> list  = this.gameRepository.findAll();

        Assert.assertEquals(1, list.size());
    }

}
