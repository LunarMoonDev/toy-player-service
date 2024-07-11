package com.project.toy_player_service.service.impl;

import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.project.toy_player_service.dto.player.request.PlayerDeleteRequestDTO;
import com.project.toy_player_service.dto.player.request.PlayerRequestDTO;
import com.project.toy_player_service.dto.player.request.PlayerUpdateRequestDTO;
import com.project.toy_player_service.dto.player.response.PlayerDTO;
import com.project.toy_player_service.dto.player.response.PlayerDeleteResponseDTO;
import com.project.toy_player_service.dto.player.response.PlayerResponseDTO;
import com.project.toy_player_service.entity.Player;
import com.project.toy_player_service.enums.Errors;
import com.project.toy_player_service.enums.Success;
import com.project.toy_player_service.exceptions.GenericException;
import com.project.toy_player_service.repository.PlayerRepository;

import java.util.Collections;
import java.util.Optional;
import java.math.BigInteger;

public class PlayerServiceImplTest {
    @InjectMocks
    private PlayerServiceImpl service;

    @Mock
    private PlayerRepository repository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // mocks
    private Player createPlayer() {
        return Player.builder()
                .id(BigInteger.ONE)
                .firstName("first")
                .lastName("last")
                .job("job")
                .server("server")
                .build();
    }

    private PlayerRequestDTO createPlayerRequestDTO() {
        return PlayerRequestDTO.builder()
                .firstName("first")
                .lastName("last")
                .job("job")
                .server("server")
                .build();
    }

    private PlayerDeleteRequestDTO createPlayerDeleteRequestDTO() {
        return PlayerDeleteRequestDTO.builder()
                .ids(Collections.singletonList(BigInteger.ONE))
                .build();
    }

    private String createUuid() {
        return "Sample";
    }

    private BigInteger createPlayerId() {
        return BigInteger.ONE;
    }

    private Pageable createPageable() {
        return PageRequest.of(0, 10, Sort.by("sad"));
    }

    private PlayerUpdateRequestDTO createPlayerUpdateRequestDTO() {
        return PlayerUpdateRequestDTO.builder()
                .firstName("first")
                .lastName("last")
                .id(createPlayerId())
                .job("job")
                .server("server")
                .build();
    }

    // tests
    @Test
    public void testCreatePlayer_Success(){
        PlayerRequestDTO requestDTO = createPlayerRequestDTO();
        String uuid = createUuid();
        Player player = createPlayer();

        when(repository.save(any(Player.class))).thenReturn(player);

        PlayerResponseDTO response = service.createPlayer(uuid, requestDTO).block();

        assertNotNull(response);
        assertEquals(Success.PLAYER_CREATE_SUCCESS.getCode(), response.getCode());
        assertEquals(Success.PLAYER_CREATE_SUCCESS.getMessage(), response.getMessage());
    }

    @Test
    public void testCreatePlayer_Fail() {
        PlayerRequestDTO requestDTO = createPlayerRequestDTO();
        String uuid = createUuid();

        when(repository.save(any(Player.class))).thenThrow(new RuntimeException("unique"));

        GenericException exception = assertThrows(GenericException.class,() -> {
            service.createPlayer(uuid, requestDTO).block();
        });

        assertNotNull(exception);
        assertEquals(Errors.PLAYER_UNIQUE.getCode(), exception.getCode());
        assertEquals(Errors.PLAYER_UNIQUE.getMessage(), exception.getMessage());
    }

    @Test
    public void testCreatePlayer_RuntimeFail() {
        PlayerRequestDTO requestDTO = createPlayerRequestDTO();
        String uuid = createUuid();

        when(repository.save(any(Player.class))).thenThrow(new RuntimeException("non-uniq"));

        GenericException exception = assertThrows(GenericException.class,() -> {
            service.createPlayer(uuid, requestDTO).block();
        });

        assertNotNull(exception);
        assertEquals(Errors.SERVICE_ERROR.getCode(), exception.getCode());
        assertEquals(Errors.SERVICE_ERROR.getMessage(), exception.getMessage());
    }

    @Test
    public void testDeletePlayers_Success() {
        PlayerDeleteRequestDTO requestDTO = createPlayerDeleteRequestDTO();
        String uuid = createUuid();

        when(repository.deleteByIdIn(requestDTO.getIds())).thenReturn(2);

        PlayerDeleteResponseDTO response = service.deletePlayers(uuid, requestDTO).block();

        assertNotNull(response);
        assertEquals(Success.PLAYER_DELETE_SUCCESS.getCode(), response.getCode());
        assertEquals(Success.PLAYER_DELETE_SUCCESS.getMessage(), response.getMessage());
        assertEquals(2, response.getTotal());
    }

    @Test
    public void testDeletePlayer_RuntimeFail() {
        PlayerDeleteRequestDTO requestDTO = createPlayerDeleteRequestDTO();
        String uuid = createUuid();

        when(repository.deleteByIdIn(requestDTO.getIds())).thenThrow(new RuntimeException("some-error"));

        GenericException exception = assertThrows(GenericException.class,() -> {
            service.deletePlayers(uuid, requestDTO).block();
        });

        assertNotNull(exception);
        assertEquals(Errors.SERVICE_ERROR.getCode(), exception.getCode());
        assertEquals(Errors.SERVICE_ERROR.getMessage(), exception.getMessage());
    }

    @Test
    public void testGetPlayer_Success() {
        Player player = createPlayer();
        String uuid = createUuid();
        BigInteger id = createPlayerId();

        when(repository.findById(id)).thenReturn(Optional.of(player));

        PlayerDTO response = service.getPlayer(uuid, id).block();

        assertNotNull(response);
        assertEquals(player.getFirstName(), response.getFirstName());
        assertEquals(player.getLastName(), response.getLastName());
        assertEquals(player.getId(), response.getId());
        assertEquals(player.getServer(), response.getServer());
        assertEquals(player.getJob(), response.getJob());
    }

    @Test
    public void testGetPlayer_DoesNotExistFail(){
        String uuid = createUuid();
        BigInteger id = createPlayerId();

        when(repository.findById(id)).thenReturn(Optional.empty());

        GenericException exception = assertThrows(GenericException.class, () -> {
            service.getPlayer(uuid, id).block();
        });

        assertNotNull(exception);
        assertEquals(Errors.PLAYER_NOT_EXIST.getCode(), exception.getCode());
        assertEquals(Errors.PLAYER_NOT_EXIST.getMessage(), exception.getMessage());
    }

    @Test
    public void testGetPlayer_RuntimeFail(){
        String uuid = createUuid();
        BigInteger id = createPlayerId();

        when(repository.findById(id)).thenThrow(new RuntimeException());

        GenericException exception = assertThrows(GenericException.class, () -> {
            service.getPlayer(uuid, id).block();
        });

        assertNotNull(exception);
        assertEquals(Errors.SERVICE_ERROR.getCode(), exception.getCode());
        assertEquals(Errors.SERVICE_ERROR.getMessage(), exception.getMessage());
    }

    @Test
    public void testPlayerList_Success(){
        String uuid = createUuid();
        Pageable page = createPageable();
        Player player = createPlayer();
        Page<Player> playerList = new PageImpl<>(Collections.singletonList(player));

        when(repository.findAll(page)).thenReturn(playerList);

        Page<PlayerDTO> response = service.playerList(uuid, page).block();

        assertNotNull(response);
        assertEquals(1, response.getSize());
        assertEquals(1, response.getTotalPages());

        PlayerDTO playerDTO = response.getContent().get(0);
        assertEquals(player.getFirstName(), playerDTO.getFirstName());
        assertEquals(player.getLastName(), playerDTO.getLastName());
        assertEquals(player.getId(), playerDTO.getId());
        assertEquals(player.getServer(), playerDTO.getServer());
        assertEquals(player.getJob(), playerDTO.getJob());
    }

    @Test
    public void testPlayerList_RuntimeFail(){
        String uuid = createUuid();
        Pageable page = createPageable();

        when(repository.findAll(page)).thenThrow(new RuntimeException());

        GenericException exception = assertThrows(GenericException.class, () -> {
            Page<PlayerDTO> response = service.playerList(uuid, page).block();
        });

        assertNotNull(exception);
        assertEquals(Errors.SERVICE_ERROR.getCode(), exception.getCode());
        assertEquals(Errors.SERVICE_ERROR.getMessage(), exception.getMessage());
    }

    @Test
    public void testUpdatePlayer_Success() {
        String uuid = createUuid();
        PlayerUpdateRequestDTO payload = createPlayerUpdateRequestDTO();
        Player player = createPlayer();

        when(repository.getReferenceById(payload.getId())).thenReturn(player);

        PlayerDTO response = service.updatePlayer(uuid, payload).block();

        assertNotNull(response);
        assertEquals(payload.getFirstName(), response.getFirstName());
        assertEquals(payload.getLastName(), response.getLastName());
        assertEquals(payload.getId(), response.getId());
        assertEquals(payload.getServer(), response.getServer());
        assertEquals(payload.getJob(), response.getJob());
    }

    @Test
    public void testUpdatePlayer_RuntimeFail() {
        String uuid = createUuid();
        PlayerUpdateRequestDTO payload = createPlayerUpdateRequestDTO();
        Player player = createPlayer();

        when(repository.getReferenceById(payload.getId())).thenThrow(new RuntimeException());

        GenericException exception = assertThrows(GenericException.class, () -> {
            PlayerDTO response = service.updatePlayer(uuid, payload).block();
        });

        assertNotNull(exception);
        assertEquals(Errors.SERVICE_ERROR.getCode(), exception.getCode());
        assertEquals(Errors.SERVICE_ERROR.getMessage(), exception.getMessage());
    }

    @Test
    public void testUpdatePlayer_DuplicateFail() {
        String uuid = createUuid();
        PlayerUpdateRequestDTO payload = createPlayerUpdateRequestDTO();
        Player player = createPlayer();

        when(repository.getReferenceById(payload.getId())).thenThrow(new RuntimeException("unique"));

        GenericException exception = assertThrows(GenericException.class, () -> {
            PlayerDTO response = service.updatePlayer(uuid, payload).block();
        });

        assertNotNull(exception);
        assertEquals(Errors.PLAYER_DUPLICATE.getCode(), exception.getCode());
        assertEquals(Errors.PLAYER_DUPLICATE.getMessage(), exception.getMessage());
    }
}
