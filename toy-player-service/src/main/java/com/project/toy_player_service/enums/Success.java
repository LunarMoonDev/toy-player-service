package com.project.toy_player_service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Success {
    PLAYER_CREATE_SUCCESS("PLYR-SUCC-01", "Player has been create."),
    PLAYER_DELETE_SUCCESS("PLYR-SUCC-02", "Given total shows the number of deleted players");
    
    private final String code;
    private final String message;
}
