package com.project.toy_player_service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Success {
    PLAYER_CREATE_SUCCESS("PLYR-SUCC-01", "Player has been create.");
    
    private final String code;
    private final String message;
}
