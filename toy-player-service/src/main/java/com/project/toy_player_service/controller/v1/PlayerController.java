package com.project.toy_player_service.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.toy_player_service.dto.player.request.PlayerDeleteRequestDTO;
import com.project.toy_player_service.dto.player.request.PlayerRequestDTO;
import com.project.toy_player_service.dto.player.response.PlayerDeleteResponseDTO;
import com.project.toy_player_service.dto.player.response.PlayerResponseDTO;
import com.project.toy_player_service.exceptions.GenericException;
import com.project.toy_player_service.service.PlayerService;

import jakarta.validation.Valid;
import reactor.core.publisher.Mono;

@RestController("PlayerControllerV1")
@RequestMapping("/v1.0/player")
public class PlayerController {
    @Autowired
    @Qualifier("PlayerService_STABLE")
    private PlayerService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PlayerResponseDTO>> postPlayer(@RequestHeader("X-Tracker") String uuid,
            @RequestBody @Valid PlayerRequestDTO payload) throws GenericException {
        return service.createPlayer(uuid, payload).map(ResponseEntity::ok);
    }

    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PlayerDeleteResponseDTO>> deletePlayers(@RequestHeader("X-Tracker") String uuid,
            @RequestBody @Valid PlayerDeleteRequestDTO payload) throws GenericException {
        return service.deletePlayers(uuid, payload).map(ResponseEntity::ok);
    }
}
