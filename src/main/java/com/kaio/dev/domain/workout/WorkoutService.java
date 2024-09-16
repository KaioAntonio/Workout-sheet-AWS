package com.kaio.dev.domain.workout;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaio.dev.config.exception.RegraDeNegocioException;
import com.kaio.dev.domain.workout.dto.CreateWorkoutDTO;
import com.kaio.dev.domain.workout.dto.WorkoutResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkoutService {

    private final WorkoutRepository repository;
    private final ObjectMapper objectMapper;

    public WorkoutResponseDTO createWorkout(CreateWorkoutDTO workoutDTO) {
        Workout workout = objectMapper.convertValue(workoutDTO, Workout.class);

        return objectMapper.convertValue(repository.save(workout), WorkoutResponseDTO.class);
    }

    public Page<WorkoutResponseDTO> searchWorkout(
            Long id, String nome,
            Integer pagina, Integer elementosPorPagina,
            String nomeColuna, String tipoOrdenacao) {

        Sort.Direction direction = "desc".equalsIgnoreCase(tipoOrdenacao) ? Sort.Direction.DESC : Sort.Direction.ASC;

        PageRequest pageRequest = PageRequest.of(pagina, elementosPorPagina, Sort.by(direction, nomeColuna));

        Page<Workout> workouts = repository.findAll(pageRequest);

        List<Workout> workoutList = workouts
                .stream()
                .filter(workout -> (id == null || workout.getWorkoutID().equals(id)))
                .filter(workout -> (nome == null || workout.getNome().contains(nome)))
                .toList();

        return new PageImpl<>(
                workoutList
                        .stream()
                        .map(lista -> objectMapper.convertValue(lista, WorkoutResponseDTO.class))
                        .toList(),
                pageRequest,
                workouts.getTotalElements()
        );
    }

    public Workout buscarPorId(Long id) throws RegraDeNegocioException {
        return repository.findById(id).orElseThrow(
                () -> new RegraDeNegocioException("Treino não encontrado!")
        );
    }



    public void removeWorkout(Long id) throws RegraDeNegocioException {
        Workout workout = buscarPorId(id);
        repository.delete(workout);
    }
}
