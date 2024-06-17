package com.project.toy_player_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.toy_player_service.entity.Player;

import java.math.BigInteger;

public interface PlayerRepository extends JpaRepository<Player, BigInteger>{
}
