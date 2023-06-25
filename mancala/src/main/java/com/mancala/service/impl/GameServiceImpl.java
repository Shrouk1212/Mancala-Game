package com.mancala.service.impl;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mancala.dto.GameDto;
import com.mancala.dto.Movement;
import com.mancala.dto.PlayerDto;
import com.mancala.entity.GameEntity;
import com.mancala.exceptions.ResourceNotFoundException;
import com.mancala.mapper.MancalaMapper;
import com.mancala.repository.MancalaRepository;
import com.mancala.service.GameService;
import com.mancala.utils.Constants;

import lombok.extern.slf4j.Slf4j;

/*
This class implements data persistence and make the moves of the game
*/
@Service
@Slf4j
public class GameServiceImpl implements GameService {

	@Autowired
	MancalaRepository mancalaRepository;
	@Autowired
	MancalaMapper mancalaMapper;

	// loads the game instance from H2 database if game instance was found or create
	// new instance of the game
	@Override
	public GameDto getGame(String gameId) {
		GameDto gameDto = null;
		try {
			return loadGame(gameId);
		} catch (ResourceNotFoundException ex) {
			if(ex.getMessage().contains("Game id")) {
			gameDto = new GameDto(new PlayerDto(), new PlayerDto());
			}
		}
		gameDto = new GameDto(new PlayerDto(), new PlayerDto());
		mancalaRepository.save(mancalaMapper.convertGameDtoToGameEntity(gameDto));
		return gameDto;
	}

	// loads the game instance from H2 database
	private GameDto loadGame(String gameId) throws ResourceNotFoundException {
		Optional<GameEntity> gameEntityOptional = mancalaRepository.findById(gameId);
		if (gameEntityOptional.isPresent()) {
			GameEntity gameEntity = gameEntityOptional.get();
			return mancalaMapper.convertGameEntityToGameDto(gameEntity);
		} else {
			throw new ResourceNotFoundException("Game id " + gameId + " not found!");
		}
	}

	// make the movements of the stones
	@Override
	public GameDto getMove(Movement movement) throws ResourceNotFoundException {
		GameDto gameDto = loadGame(movement.getGameId());
		boolean currentPlayerTurn = false;
		switch (movement.getPlayer()) {
		case Constants.PLAYER1_KEY:

			currentPlayerTurn = calculateMovements(gameDto, movement.getPitNumber());
			if (!currentPlayerTurn) {
				gameDto.setCurrentPlayer(Constants.PLAYER2_KEY);
			}
			break;
		case Constants.PLAYER2_KEY:
			PlayerDto player1 = gameDto.getPlayer1();
			PlayerDto player2 = gameDto.getPlayer2();
			gameDto.setPlayer1(player2);
			gameDto.setPlayer2(player1);
			currentPlayerTurn = calculateMovements(gameDto, movement.pitNumber);
			if (!currentPlayerTurn) {
				gameDto.setCurrentPlayer(Constants.PLAYER1_KEY);
			}
			player1 = gameDto.getPlayer2();
			player2 = gameDto.getPlayer1();
			gameDto.setPlayer1(player1);
			gameDto.setPlayer2(player2);

			break;
		default:
			return gameDto;
		}
		
		boolean isWinnerExist = checkWinner(gameDto);
		gameDto.setWinnerExist(isWinnerExist);
		mancalaRepository.saveAndFlush(mancalaMapper.convertGameDtoToGameEntity(gameDto));
		return gameDto;
	}

	// calculate the moves
	private boolean calculateMovements(GameDto game, int pitNumber) {
		PlayerDto currentPlayer = game.getPlayer1();
		PlayerDto counterPlayer = game.getPlayer2();

		int[] ownPits = currentPlayer.getPits();
		int stones = ownPits[pitNumber];
		boolean isMyTurn = false;

		ownPits[pitNumber] = Constants.BLANK;
		if (stones == Constants.BLANK)
			return true;
		while (stones != Constants.BLANK) {
			for (int i = pitNumber + 1; i < Constants.PIT; i++) {
				if (stones == Constants.BLANK)
					break;
				ownPits[i]++;
				stones--;
			}

			if (stones != Constants.BLANK) {
				isMyTurn = true;
				currentPlayer.treasury++;
				stones--;
			}

			if (stones != Constants.BLANK) {
				isMyTurn = false;
				int[] counterPits = counterPlayer.getPits();
				int i = 0;
				int targetIndex = (stones >= Constants.PIT) ? Constants.PIT : stones;

				for (i = 0; i < targetIndex - 1; i++) {
					if (stones == Constants.BLANK)
						break;
					counterPits[i]++;
					stones--;
				}
				if (stones == 1 && counterPits[i] == 0) {
					currentPlayer.treasury += 1 + ownPits[Constants.PIT - i - 1];
					ownPits[6 - i - 1] = Constants.BLANK;
					stones--;
				} else if (stones == 1) {
					counterPits[i]++;
					stones--;
				}
				pitNumber = -1;
				counterPlayer.setPits(counterPits);
			}
			currentPlayer.setPits(ownPits);
		}

		game.setPlayer1(currentPlayer);
		game.setPlayer2(counterPlayer);
		return isMyTurn;
	}
	
	// check if the game is ended and the winner exists or nor
	private boolean checkWinner(GameDto game) {

		boolean isWinnerExist = isAllStonesSpent(game.getPlayer1().getPits())
				|| isAllStonesSpent(game.getPlayer2().getPits());

		if (isWinnerExist) {
			decideWinner(game);
		}
		return isWinnerExist;
	}
	// decide who is the winner
	private void decideWinner(GameDto game) {
		int player1Treasury = game.getPlayer1().treasury + Arrays.stream(game.getPlayer1().getPits()).sum();
		int player2Treasury = game.getPlayer2().treasury + Arrays.stream(game.getPlayer2().getPits()).sum();
		if (player1Treasury > player2Treasury) {
			log.info("Winner of " + game.getGameId() + " is " + Constants.PLAYER1_KEY);
			game.setWinner(Constants.PLAYER1_KEY);
		} else {
			log.info("Winner of " + game.getGameId() + " is " + Constants.PLAYER2_KEY);
			game.setWinner(Constants.PLAYER2_KEY);
		}
	}

	// check if the game is ended and all the pits are equal to zero or not
	private static boolean isAllStonesSpent(int[] pits) {
		for (int pit : pits)
			if (pit != 0)
				return false;
		return true;
	}
}
