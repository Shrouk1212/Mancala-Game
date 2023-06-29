package com.mancala.dto;

import java.util.Arrays;

import com.mancala.utils.Constants;
import lombok.Data;

@Data
public class PlayerDto {

	private int[] pits;
	private int treasury;

	public PlayerDto() {
		this.pits = new int[Constants.PIT];
		Arrays.fill(this.pits, Constants.STONE);
		treasury =0;
	}

	public PlayerDto(int[] pits , int treasury) {
		super();
		this.pits = pits;
		this.treasury = treasury;
	}
	

}
