package com.mancala.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.mancala.dto.GameDto;
import com.mancala.dto.Movement;
import com.mancala.exceptions.ResourceNotFoundException;
import com.mancala.service.GameService;

@RestController
public class GameController {

	@Autowired
	private GameService gameService;

	/**
	 * Start the Game
	 * 
	 * @param gameId the gameId
	 * 
	 * @return an Instance of the Game
	 */
	@GetMapping("/start")
	@ResponseBody
	public ResponseEntity<GameDto> getStart(@RequestParam String gameId) {
		
			return ResponseEntity.status(HttpStatus.OK).body(gameService.getGame(gameId));
		
	}

	/**
	 *  Make a Move
	 * 
	 * @param Movement contains the movement informations
	 * 
	 * @return the GameDto
	 * 
	*/
	@PutMapping("/move")
	public ResponseEntity<GameDto> getMovement(@RequestBody Movement movement) throws ResourceNotFoundException {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(gameService.getMove(movement));
		} catch (ResourceNotFoundException ex) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} 
	}

}
