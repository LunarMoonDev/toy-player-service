package com.project.toy_player_service.service;

import com.project.toy_player_service.dto.player.request.PlayerDeleteRequestDTO;
import com.project.toy_player_service.dto.player.request.PlayerRequestDTO;
import com.project.toy_player_service.dto.player.response.PlayerDeleteResponseDTO;
import com.project.toy_player_service.dto.player.response.PlayerResponseDTO;
import com.project.toy_player_service.exceptions.GenericException;

import reactor.core.publisher.Mono;

public interface PlayerService {
    Mono<PlayerResponseDTO> createPlayer(String uuid, PlayerRequestDTO payload) throws GenericException;

    Mono<PlayerDeleteResponseDTO> deletePlayers(String uuid, PlayerDeleteRequestDTO payload) throws GenericException;
}
