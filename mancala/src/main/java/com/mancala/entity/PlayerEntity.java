package com.mancala.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Data
@Table(name="PLAYER")
public class PlayerEntity {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String playerType;
	@Column(name="pits")
    private int[] pits;
    private int treasury;
    
 

}
