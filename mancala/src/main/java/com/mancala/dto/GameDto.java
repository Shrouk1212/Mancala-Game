package com.mancala.dto;

import java.util.UUID;

import com.mancala.utils.Constants;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameDto {

	private String gameId;
	public String currentPlayer;
	public PlayerDto player1;
	public PlayerDto player2;
	public boolean isWinnerExist;
	public String winner;

	public GameDto(PlayerDto playerOne, PlayerDto playerTwo) {
		super();
		this.gameId = UUID.randomUUID().toString();
		this.currentPlayer = Constants.PLAYER1_KEY;
		this.player1 = playerOne;
		this.player2 = playerTwo;

	}

	public GameDto(String gameId, String currentPlayer, PlayerDto player1, PlayerDto player2) {
		super();
		this.gameId = gameId;
		this.currentPlayer = currentPlayer;
		this.player1 = player1;
		this.player2 = player2;
	}

}
