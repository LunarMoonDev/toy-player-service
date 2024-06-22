package com.project.toy_player_service.dto.player.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PlayerResponseDTO {
    @JsonProperty("code")
    private String code;

    @JsonProperty("message")
    private String message;
}
