package com.project.toy_player_service.service.impl;

import java.math.BigInteger;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.toy_player_service.dto.player.request.PlayerDeleteRequestDTO;
import com.project.toy_player_service.dto.player.request.PlayerRequestDTO;
import com.project.toy_player_service.dto.player.request.PlayerUpdateRequestDTO;
import com.project.toy_player_service.dto.player.response.PlayerDTO;
import com.project.toy_player_service.dto.player.response.PlayerDeleteResponseDTO;
import com.project.toy_player_service.dto.player.response.PlayerResponseDTO;
import com.project.toy_player_service.enums.Errors;
import com.project.toy_player_service.enums.Success;
import com.project.toy_player_service.exceptions.GenericException;
import com.project.toy_player_service.repository.PlayerRepository;
import com.project.toy_player_service.service.PlayerService;
import com.project.toy_player_service.util.MapperUtil;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service("PlayerService_STABLE")
@Slf4j
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerRepository repository;

    @Override
    public Mono<PlayerResponseDTO> createPlayer(String uuid, PlayerRequestDTO payload) throws GenericException {
        log.info("X-Tracker: {} | Creating player for given request", uuid);
        log.debug("X-Tracker: {} | request payload: {}", payload);

        return Mono.fromCallable(() -> repository.save(MapperUtil.toPlayerEntity(payload)))
                .doOnError(error -> {
                    log.error("X-Tracker: {} | specific exception: {}", uuid, error.getMessage());

                    if (Objects.nonNull(error.getMessage()) && error.getMessage().contains("unique")) {
                        throw new GenericException(Errors.PLAYER_UNIQUE);
                    }

                    throw new GenericException(Errors.SERVICE_ERROR);
                })
                .map(player -> MapperUtil.toPlayerResponseDTO(Success.PLAYER_CREATE_SUCCESS))
                .doOnNext(response -> log.info("X-Tracker: {} | Success in creating player", uuid));
    }

    @Override
    public Mono<PlayerDeleteResponseDTO> deletePlayers(String uuid, PlayerDeleteRequestDTO payload)
            throws GenericException {
        log.info("X-Tracker: {} | Delete players for the given ids", uuid);
        log.debug("X-Tracker: {} | request payload: {}", payload);

        return Mono.fromCallable(() -> repository.deleteByIdIn(payload.getIds()))
                .doOnError(error -> {
                    log.error("X-Tracker: {} | specific exception: {}", uuid, error.getMessage());

                    throw new GenericException(Errors.SERVICE_ERROR);
                })
                .map(total -> MapperUtil.toPlayerDeleteResponseDTO(total, Success.PLAYER_DELETE_SUCCESS))
                .doOnNext(response -> log.info("X-Tracker: {} | Success in deleting players", uuid));
    }

    @Override
    public Mono<PlayerDTO> getPlayer(String uuid, BigInteger id) throws GenericException {
        log.info("X-Tracker: {} | Retrieving a player from database", uuid);
        log.debug("X-Tracker: {} | request id: {}", id);

        return Mono.fromCallable(() -> repository.findById(id))
                .doOnError(error -> {
                    log.error("X-Tracker: {} | specific exception: {}", uuid, error.getMessage());

                    throw new GenericException(Errors.SERVICE_ERROR);
                })
                .map(optionalPlayer -> {
                    if (optionalPlayer.isPresent()) {
                        return MapperUtil.toPlayerDTO(optionalPlayer.get());
                    } else {
                        log.error("X-Tracker: {} |  exception: player does not exist", uuid);
                        throw new GenericException(Errors.PLAYER_NOT_EXIST);
                    }
                })
                .doOnNext(response -> log.info("X-Tracker: {} | Success in retrieving the player", uuid));
    }

    @Override
    public Mono<Page<PlayerDTO>> playerList(String uuid, Pageable paginate) throws GenericException {
        log.info("X-Tracker: {} | Retrieving players from database", uuid);
        log.debug("X-Tracker: {} | request payload: {}", paginate);

        return Mono.fromCallable(() -> repository.findAll(paginate))
            .map(page -> page.map(player -> MapperUtil.toPlayerDTO(player)))
            .doOnError(error -> {
                log.error("X-Tracker: {} | specific exception: {}", uuid, error.getMessage());

                throw new GenericException(Errors.SERVICE_ERROR);
            })
            .doOnNext(response -> log.info("X-Tracker: {} | Success in retrieving players", uuid));
    }

    @Override
    public Mono<PlayerDTO> updatePlayer(String uuid, PlayerUpdateRequestDTO payload) throws GenericException {
        log.info("X-Tracker: {} | Updating a player from database", uuid);
        log.debug("X-Tracker: {} | request payload: {}", payload);

        return Mono.fromCallable(() -> repository.getReferenceById(payload.getId()))
            .map(player -> MapperUtil.toPlayer(payload, player))
            .doOnNext(player -> repository.save(player))
            .map(player -> MapperUtil.toPlayerDTO(player))
            .doOnError(error -> {
                log.error("X-Tracker: {} | specific exception: {}", uuid, error.getMessage());

                if (Objects.nonNull(error.getMessage()) && error.getMessage().contains("unique")) {
                    throw new GenericException(Errors.PLAYER_DUPLICATE);
                }

                throw new GenericException(Errors.SERVICE_ERROR);
            })
            .doOnNext(response -> log.info("X-Tracker: {} | Success in updating the player", uuid));
    }
}
