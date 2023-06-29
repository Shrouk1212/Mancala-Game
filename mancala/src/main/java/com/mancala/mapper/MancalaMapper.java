package com.mancala.mapper;

import org.springframework.stereotype.Component;

import com.mancala.dto.GameDto;
import com.mancala.dto.PlayerDto;
import com.mancala.entity.GameEntity;
import com.mancala.entity.PlayerEntity;
import com.mancala.exceptions.ResourceNotFoundException;
import com.mancala.utils.Constants;
/*
This class responsible of mapping Entity to DTO and DTO to Entity 
*/
@Component
public class MancalaMapper {

	public GameDto convertGameEntityToGameDto(GameEntity gameEntity) throws ResourceNotFoundException {
		if (gameEntity.getPlayer().size() == 2) {
			return new GameDto(gameEntity.getId(), gameEntity.getCurrentPlayer(),
					new PlayerDto(gameEntity.getPlayer().get(0).getPits(), gameEntity.getPlayer().get(0).getTreasury()),
					new PlayerDto(gameEntity.getPlayer().get(1).getPits(), gameEntity.getPlayer().get(1).getTreasury()),
					gameEntity.isWinnerExist, gameEntity.getWinner());

		} else {
			throw new ResourceNotFoundException("No enough Players!");
		}

	}

	private PlayerEntity convertPlayerDtoToPlayerEntity(String type, PlayerDto playerDto) {
		return PlayerEntity.builder().pits(playerDto.getPits()).treasury(playerDto.getTreasury()).playerType(type)
				.build();
	}

	public GameEntity convertGameDtoToGameEntity(GameDto game) {
		GameEntity gameEntity = new GameEntity();
		gameEntity.setId(game.getGameId());
		gameEntity.setCurrentPlayer(game.getCurrentPlayer());
		gameEntity.setWinner(game.getWinner());
		gameEntity.setWinnerExist(game.isWinnerExist);
		gameEntity.setPlayers(convertPlayerDtoToPlayerEntity(Constants.PLAYER1_KEY, game.getPlayer1()));
		gameEntity.setPlayers(convertPlayerDtoToPlayerEntity(Constants.PLAYER2_KEY, game.getPlayer2()));
		return gameEntity;
	}

}
