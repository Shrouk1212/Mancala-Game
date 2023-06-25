package com.mancala.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movement {

	public String gameId;

	// @Pattern(regexp = "(?:player1|player2)", message = "must be player1 or
	// player2")
	public String player;

	public int pitNumber;

}
