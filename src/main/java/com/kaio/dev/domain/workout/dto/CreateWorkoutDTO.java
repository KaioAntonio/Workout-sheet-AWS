package com.kaio.dev.domain.workout.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CreateWorkoutDTO {

    private String nome;
    private Integer repeticoes;
    private Integer series;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long sheetId;
}
