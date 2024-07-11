package com.project.toy_player_service.controller.v1;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import com.project.toy_player_service.dto.player.request.PlayerUpdateRequestDTO;
import com.project.toy_player_service.dto.player.response.PlayerDTO;
import com.project.toy_player_service.dto.player.response.PlayerDeleteResponseDTO;
import com.project.toy_player_service.dto.player.response.PlayerResponseDTO;
import com.project.toy_player_service.exceptions.GenericException;
import com.project.toy_player_service.service.PlayerService;

import jakarta.validation.Valid;
import reactor.core.publisher.Mono;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

    @GetMapping(path = "/{player_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PlayerDTO>> playerDetails(@RequestHeader("X-Tracker") String uuid,
            @PathVariable("player_id") BigInteger id) {
        return service.getPlayer(uuid, id).map(ResponseEntity::ok);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Page<PlayerDTO>>> playerList(@RequestHeader("X-Tracker") String uuid, Pageable paginate)
            throws GenericException {
        return service.playerList(uuid, paginate).map(ResponseEntity::ok);
    }

    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PlayerDTO>> updatePlayer(@RequestHeader("X-Tracker") String uuid,
            @RequestBody @Valid PlayerUpdateRequestDTO payload) throws GenericException {
        return service.updatePlayer(uuid, payload).map(ResponseEntity::ok);
    }
}
