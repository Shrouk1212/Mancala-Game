package com.mancala.service;

import com.mancala.dto.GameDto;
import com.mancala.dto.Movement;
import com.mancala.exceptions.ResourceNotFoundException;

/*
Service Interface
*/
public interface GameService {
	
	/**
	 * Start the Game
	 * 
	 * @param gameId the gameId
	 * 
	 * @return an Instance of the Game
	 */
	GameDto getGame(String gameId);

	/**
	 *  Make a Move
	 * 
	 * @param Movement contains the movement informations
	 * 
	 * @return the GameDto
	 * 
	*/
	GameDto getMove(Movement movement) throws ResourceNotFoundException ;
}
