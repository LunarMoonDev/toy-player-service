package com.project.toy_player_service.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import com.project.toy_player_service.controller.v1.PlayerController;
import com.project.toy_player_service.dto.player.request.PlayerDeleteRequestDTO;
import com.project.toy_player_service.dto.player.request.PlayerRequestDTO;
import com.project.toy_player_service.dto.player.response.PlayerDeleteResponseDTO;
import com.project.toy_player_service.dto.player.response.PlayerResponseDTO;
import com.project.toy_player_service.service.PlayerService;

import reactor.core.publisher.Mono;

import java.util.Collections;
import java.math.BigInteger;

public class PlayerControllerTest {

    @InjectMocks
    private PlayerController controller;

    @Mock
    private PlayerService service;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // mocks
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

    private PlayerResponseDTO createPlayerResponseDTO() {
        return PlayerResponseDTO.builder()
                .code("code")
                .message("message")
                .build();
    }

    private PlayerDeleteResponseDTO createPlayerDeleteResponseDTO() {
        return PlayerDeleteResponseDTO.builder()
                .code("code")
                .message("message")
                .total(2)
                .build();
    }

    private String createUuid() {
        return "Sample";
    }

    // tests
    @Test
    public void testPostPlayer_Success() {
        PlayerRequestDTO requestDTO = createPlayerRequestDTO();
        String uuid = createUuid();

        when(service.createPlayer(uuid, requestDTO))
                .thenReturn(Mono.just(createPlayerResponseDTO()));

        ResponseEntity<PlayerResponseDTO> response = controller.postPlayer(uuid, requestDTO).block();

        assertNotNull(response);
        assertEquals("code", response.getBody().getCode());
        assertEquals("message", response.getBody().getMessage());
    }

    @Test
    public void testDeletePlayer_Success() {
        PlayerDeleteRequestDTO requestDTO = createPlayerDeleteRequestDTO();
        PlayerDeleteResponseDTO responseDTO = createPlayerDeleteResponseDTO();
        String uuid = createUuid();

        when(service.deletePlayers(uuid, requestDTO)).thenReturn(Mono.just(responseDTO));

        ResponseEntity<PlayerDeleteResponseDTO> response = controller.deletePlayers(uuid, requestDTO).block();

        assertNotNull(response);
        assertEquals("code", response.getBody().getCode());
        assertEquals("message", response.getBody().getMessage());
        assertEquals(2, response.getBody().getTotal());
    }

}
