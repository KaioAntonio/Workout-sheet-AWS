package com.kaio.dev.domain.sheet.dto;

import com.kaio.dev.domain.workout.dto.WorkoutResponseDTO;
import lombok.Data;

import java.util.List;

@Data
public class SheetResponseDTO extends CreateSheetDTO{

    private String sheetID;
    private List<WorkoutResponseDTO> workouts;
}
