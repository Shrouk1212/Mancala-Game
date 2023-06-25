package com.mancala.dto;

import java.util.Arrays;

import com.mancala.utils.Constants;

import lombok.Builder;
import lombok.Data;

@Data
public class PlayerDto {

	public int[] pits;
	 public int treasury;

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
