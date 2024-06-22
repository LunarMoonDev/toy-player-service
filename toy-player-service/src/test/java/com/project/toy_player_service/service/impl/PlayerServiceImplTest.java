package com.project.toy_player_service.service.impl;

import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.project.toy_player_service.dto.player.request.PlayerRequestDTO;
import com.project.toy_player_service.dto.player.response.PlayerResponseDTO;
import com.project.toy_player_service.entity.Player;
import com.project.toy_player_service.enums.Errors;
import com.project.toy_player_service.enums.Success;
import com.project.toy_player_service.exceptions.GenericException;
import com.project.toy_player_service.repository.PlayerRepository;

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

    private String createUuid() {
        return "Sample";
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

        RuntimeException exception = assertThrows(RuntimeException.class,() -> {
            service.createPlayer(uuid, requestDTO).block();
        });

        assertNotNull(exception);
        assertEquals("non-uniq", exception.getMessage());
    }
}
