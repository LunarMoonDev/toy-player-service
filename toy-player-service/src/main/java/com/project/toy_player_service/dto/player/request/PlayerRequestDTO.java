package com.project.toy_player_service.dto.player.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
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
public class PlayerRequestDTO {
    @JsonProperty(value = "first_name")
    @NotNull
    private String firstName;

    @JsonProperty(value = "last_name")
    @NotNull
    private String lastName;

    @JsonProperty(value = "server")
    @NotNull
    private String server;

    @JsonProperty(value = "job")
    @NotNull
    private String job;
}
