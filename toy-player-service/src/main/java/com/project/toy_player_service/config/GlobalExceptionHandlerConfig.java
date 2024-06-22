package com.project.toy_player_service.config;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.project.toy_player_service.dto.player.response.PlayerResponseDTO;
import com.project.toy_player_service.enums.Errors;
import com.project.toy_player_service.exceptions.GenericException;
import com.project.toy_player_service.util.MapperUtil;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandlerConfig {

    @ExceptionHandler({ MethodArgumentNotValidException.class })
    public ResponseEntity<PlayerResponseDTO> handleRequestValidations(MethodArgumentNotValidException exception) {
        log.error("Exception: {}", exception.getMessage());

        BindingResult result = exception.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();

        PlayerResponseDTO response = MapperUtil.toPlayerResponseDTO(Errors.PLAYER_CREATE_PARAM);
        String message = response.getMessage();
        message += fieldErrors.stream().map(field -> field.getField() + " " + field.getDefaultMessage())
                .collect(Collectors.joining(", ", "[", "]"));
        response.setMessage(message);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler({ GenericException.class })
    public ResponseEntity<PlayerResponseDTO> handleLocalException(GenericException exception) {
        log.error("Exception: {}", exception.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(MapperUtil.toPlayerResponseDTO(exception));
    }

    @ExceptionHandler({ MissingRequestHeaderException.class })
    public ResponseEntity<PlayerResponseDTO> handleMissingHeaders(
            MissingRequestHeaderException exception) {
        log.error("Exception: {}", exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(MapperUtil.toPlayerResponseDTO(Errors.SERVICE_HEADER));
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<PlayerResponseDTO> handleOtherException(Exception exception) {
        log.error("Exception: {}", exception.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                .body(MapperUtil.toPlayerResponseDTO(Errors.SERVICE_ERROR));
    }
}
