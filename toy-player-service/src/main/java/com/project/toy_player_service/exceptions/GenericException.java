package com.project.toy_player_service.exceptions;

import com.project.toy_player_service.enums.Errors;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GenericException extends RuntimeException{
    private String code;
    private String message;

    public GenericException(Errors error) {
        this.code = error.getCode();
        this.message = error.getMessage();
    }
}
