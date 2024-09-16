package com.kaio.dev.domain.workout.dto;

import lombok.Data;

@Data
public class CreateWorkoutDTO {

    private String nome;
    private Integer repeticoes;
    private Integer series;
    private byte[] foto;
    private Long sheetId;
}
