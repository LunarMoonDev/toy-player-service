package com.project.toy_player_service.dto.player.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PlayerDeleteRequestDTO {
    @JsonProperty("ids")
    private List<BigInteger> ids;
}
