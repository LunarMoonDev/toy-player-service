package com.project.toy_player_service.service;

import java.math.BigInteger;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.project.toy_player_service.dto.player.request.PlayerDeleteRequestDTO;
import com.project.toy_player_service.dto.player.request.PlayerRequestDTO;
import com.project.toy_player_service.dto.player.response.PlayerDTO;
import com.project.toy_player_service.dto.player.response.PlayerDeleteResponseDTO;
import com.project.toy_player_service.dto.player.response.PlayerResponseDTO;
import com.project.toy_player_service.exceptions.GenericException;

import reactor.core.publisher.Mono;

public interface PlayerService {
    Mono<PlayerResponseDTO> createPlayer(String uuid, PlayerRequestDTO payload) throws GenericException;

    Mono<PlayerDeleteResponseDTO> deletePlayers(String uuid, PlayerDeleteRequestDTO payload) throws GenericException;

    Mono<PlayerDTO> getPlayer(String uuid, BigInteger id) throws GenericException;

    Mono<Page<PlayerDTO>> playerList(String uuid, Pageable paginate) throws GenericException;
}
