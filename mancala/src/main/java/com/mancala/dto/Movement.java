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
    private String gameId;

    @NotBlank
    @Pattern(regexp = "(?:player1|player2)", message = "must be player1 or player2")
    private String player;

    @Min(0)
    @Max(5)
    private int pitNumber;

}
