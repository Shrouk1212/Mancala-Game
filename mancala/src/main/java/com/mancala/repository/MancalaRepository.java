package com.mancala.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mancala.entity.GameEntity;

public interface MancalaRepository extends JpaRepository<GameEntity, String>{

}
