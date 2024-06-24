package com.project.toy_player_service.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.project.toy_player_service.dto.player.response.PlayerDeleteResponseDTO;
import com.project.toy_player_service.dto.player.response.PlayerResponseDTO;
import com.project.toy_player_service.enums.Errors;
import com.project.toy_player_service.enums.Success;
import com.project.toy_player_service.exceptions.GenericException;

public class MapperUtilTest {

    @InjectMocks
    private MapperUtil mapperUtil;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    //tests
    @Test
    public void testToPlayerResponseDTO_Errors(){
        Errors error = Errors.PLAYER_CREATE_PARAM;
        PlayerResponseDTO responseDTO = MapperUtil.toPlayerResponseDTO(error);

        assertNotNull(responseDTO);
        assertEquals(error.getCode(), responseDTO.getCode());
        assertEquals(error.getMessage(), responseDTO.getMessage());
    }

    @Test
    public void testToPlayerResponseDTO_GenericException(){
        Errors error = Errors.PLAYER_UNIQUE;
        GenericException exception = new GenericException(error);

        PlayerResponseDTO responseDTO = MapperUtil.toPlayerResponseDTO(exception);

        assertNotNull(responseDTO);
        assertEquals(error.getCode(), responseDTO.getCode());
        assertEquals(error.getMessage(), responseDTO.getMessage());
    }

    @Test
    public void testToPlayerDeleteResponseDTO() {
        Success success = Success.PLAYER_DELETE_SUCCESS;
        Integer total = 2;

        PlayerDeleteResponseDTO responseDTO = MapperUtil.toPlayerDeleteResponseDTO(total, success);

        assertNotNull(responseDTO);
        assertEquals(success.getCode(), responseDTO.getCode());
        assertEquals(success.getMessage(), responseDTO.getMessage());
        assertEquals(total, responseDTO.getTotal());
    }
}
