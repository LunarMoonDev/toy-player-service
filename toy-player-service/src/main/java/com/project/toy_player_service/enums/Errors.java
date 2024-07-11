package com.project.toy_player_service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Errors {
    PLAYER_UNIQUE("PLYR-ERR-01", "Given player payload already exist in the database"),
    SERVICE_ERROR("PLYR-ERR-02", "Error inside server, please check logs"),
    SERVICE_HEADER("PLYR-ERR-03", "Missing required headers"),
    PLAYER_CREATE_PARAM("PLYR-ERR-04", "Required params are missing: "),
    PLAYER_NOT_EXIST("PLYR-ERR-05", "Given player does not exist in the database"),
    PLAYER_DUPLICATE("PLYR-ERR-06", "Given player payload will cause a duplicate entry");

    private final String code;
    private final String message;
}
