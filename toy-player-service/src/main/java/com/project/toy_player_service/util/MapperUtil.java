package com.project.toy_player_service.util;

import com.project.toy_player_service.dto.player.request.PlayerRequestDTO;
import com.project.toy_player_service.dto.player.response.PlayerResponseDTO;
import com.project.toy_player_service.entity.Player;
import com.project.toy_player_service.enums.Errors;
import com.project.toy_player_service.enums.Success;
import com.project.toy_player_service.exceptions.GenericException;

public class MapperUtil {

    public static Player toPlayerEntity(PlayerRequestDTO payload) {
        return Player.builder()
                .firstName(payload.getFirstName())
                .lastName(payload.getLastName())
                .job(payload.getJob())
                .server(payload.getServer())
                .build();
    }

    public static PlayerResponseDTO toPlayerResponseDTO(Errors errors) {
        return PlayerResponseDTO.builder()
                .code(errors.getCode())
                .message(errors.getMessage())
                .build();
    }

    public static PlayerResponseDTO toPlayerResponseDTO(Success success) {
        return PlayerResponseDTO.builder()
                .code(success.getCode())
                .message(success.getMessage())
                .build();
    }

    public static PlayerResponseDTO toPlayerResponseDTO(GenericException exception) {
        return PlayerResponseDTO.builder()
                .code(exception.getCode())
                .message(exception.getMessage())
                .build();
    }
}
