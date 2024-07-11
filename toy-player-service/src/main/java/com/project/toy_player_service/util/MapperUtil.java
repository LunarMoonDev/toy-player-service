package com.project.toy_player_service.util;

import java.util.Objects;

import com.project.toy_player_service.dto.player.request.PlayerRequestDTO;
import com.project.toy_player_service.dto.player.request.PlayerUpdateRequestDTO;
import com.project.toy_player_service.dto.player.response.PlayerDTO;
import com.project.toy_player_service.dto.player.response.PlayerDeleteResponseDTO;
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

    public static PlayerDeleteResponseDTO toPlayerDeleteResponseDTO(Integer total, Success success) {
        return PlayerDeleteResponseDTO.builder()
                .code(success.getCode())
                .message(success.getMessage())
                .total(total)
                .build();
    }

    public static PlayerDTO toPlayerDTO(Player player) {
        return PlayerDTO.builder()
                .id(player.getId())
                .firstName(player.getFirstName())
                .lastName(player.getLastName())
                .server(player.getServer())
                .job(player.getJob())
                .build();
    }

    public static Player toPlayer(PlayerUpdateRequestDTO payload, Player player) {
        
        if(!Objects.isNull(payload.getFirstName())) {
            player.setFirstName(payload.getFirstName());
        }

        if(!Objects.isNull(payload.getLastName())) {
            player.setLastName(payload.getLastName());
        }

        if(!Objects.isNull(payload.getJob())) {
            player.setJob(payload.getJob());
        }

        if(!Objects.isNull(payload.getServer())) {
            player.setServer(payload.getServer());
        }

        return player;
    }
}
