package com.mancala.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movement {

	public String gameId;

	public String player;

	public int pitNumber;

}
