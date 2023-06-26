package com.mancala.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movement {

    @NotBlank
    public String gameId;

    @NotBlank
    @Pattern(regexp = "(?:player1|player2)", message = "must be player1 or player2")
    public String player;

    @Min(0)
    @Max(5)
    public int pitNumber;

}
