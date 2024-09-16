package com.kaio.dev.controller;

import com.kaio.dev.config.exception.RegraDeNegocioException;
import com.kaio.dev.domain.workout.WorkoutService;
import com.kaio.dev.domain.workout.dto.WorkoutResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.io.IOException;

@RestController
@Validated
@RequestMapping("/workouts")
@RequiredArgsConstructor
public class WorkoutController {

    private final WorkoutService workoutService;

    @Operation(summary = "Create a workout")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "201", description = "Workout created successfully")
    public ResponseEntity<WorkoutResponseDTO> createWorkout(
            @RequestParam("imagem") MultipartFile file,
            @RequestParam("nome") String nome,
            @RequestParam("repetições") Integer repeticoes,
            @RequestParam("series") Integer series,
            @RequestParam("isCompleted") Boolean isCompleted
            ) throws IOException {
        return new ResponseEntity<>(workoutService.createWorkout(nome, repeticoes,
                series, isCompleted, file), HttpStatus.CREATED);
    }

    @Operation(summary = "Find workouts")
    @GetMapping
    @ApiResponse(responseCode = "200", description = "Workout find successfully")
    public ResponseEntity<Page<WorkoutResponseDTO>> findWorkouts(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String nome,
            @RequestParam(defaultValue = "0") Integer pagina,
            @RequestParam(defaultValue = "10") Integer elementosPorPagina,
            @RequestParam(defaultValue = "nome") String nomeColuna,
            @RequestParam(defaultValue = "asc") String tipoOrdenacao
    ) {
        return new ResponseEntity<>(workoutService.searchWorkout(id, nome, pagina, elementosPorPagina, nomeColuna,
                tipoOrdenacao), HttpStatus.OK);
    }

    @Operation(summary = "Add workout in a sheet")
    @PatchMapping
    @ApiResponse(responseCode = "200", description = "Workout add in sheet sucessfully")
    public ResponseEntity<WorkoutResponseDTO> addWorkoutInSheet(
            @RequestParam Long id,
            @RequestParam Long sheetID
    ) throws RegraDeNegocioException {
        return new ResponseEntity<>(workoutService.addWorkoutInSheet(id, sheetID), HttpStatus.OK);
    }

    @Operation(summary = "Delete a workout")
    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204", description = "Workout deleted sucessfully")
    public ResponseEntity<Void> deleteWorkout(
            @PathParam("id") Long id
    ) throws RegraDeNegocioException {
        workoutService.removeWorkout(id);
        return ResponseEntity.noContent().build();
    }
}
