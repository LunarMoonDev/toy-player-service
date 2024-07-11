package com.project.toy_player_service.dto.player.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PlayerUpdateRequestDTO {
    @JsonProperty(value = "player_id")
    @NotNull
    private BigInteger id;

    @JsonProperty(value = "first_name")
    private String firstName;

    @JsonProperty(value = "last_name")
    private String lastName;

    @JsonProperty(value = "server")
    private String server;

    @JsonProperty(value = "job")
    private String job;
}
