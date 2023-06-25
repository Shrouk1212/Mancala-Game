package com.mancala.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Table(name = "GAME")
@Entity
public class GameEntity {

	@Id
	public String id;
	public String currentPlayer;
	public boolean isWinnerExist;
	public String winner;
	
	 @OneToMany(cascade = CascadeType.ALL,  orphanRemoval = true)
	  @JoinColumn(name = "game_id")
	 private List<PlayerEntity> player = new ArrayList<>();


	public void setPlayers(PlayerEntity player) {
		this.player.add(player);
	}
	

}

