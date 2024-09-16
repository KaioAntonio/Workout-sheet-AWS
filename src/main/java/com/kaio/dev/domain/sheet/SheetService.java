package com.kaio.dev.domain.sheet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaio.dev.config.exception.RegraDeNegocioException;
import com.kaio.dev.domain.sheet.dto.CreateSheetDTO;
import com.kaio.dev.domain.sheet.dto.SheetResponseDTO;
import com.kaio.dev.domain.workout.dto.WorkoutResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SheetService {

    private final SheetRepository sheetRepository;
    private final ObjectMapper objectMapper;

    public SheetResponseDTO createSheet(CreateSheetDTO sheetDTO) {
        Sheet sheet = objectMapper.convertValue(sheetDTO, Sheet.class);
        return objectMapper.convertValue(sheetRepository.save(sheet), SheetResponseDTO.class);
    }

    public Sheet buscarPorId(Long id) throws RegraDeNegocioException {
        return sheetRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Ficha não encontrada!"));
    }

    public Page<SheetResponseDTO> searchSheet(
            Long id, String nome,
            Integer pagina, Integer elementosPorPagina,
            String nomeColuna, String tipoOrdenacao) {

        Sort.Direction direction = "desc".equalsIgnoreCase(tipoOrdenacao) ? Sort.Direction.DESC : Sort.Direction.ASC;

        PageRequest pageRequest = PageRequest.of(pagina, elementosPorPagina, Sort.by(direction, nomeColuna));

        Page<Sheet> sheets = sheetRepository.findAll(pageRequest);
        List<Sheet> sheetList = sheets
                .stream()
                .filter(sheet -> (id == null || sheet.getSheetID().equals(id)))
                .filter(sheet -> (nome == null || sheet.getNome().contains(nome)))
                .toList();

        List<SheetResponseDTO> sheetResponseDTOs = sheetList
                .stream()
                .map(sheet -> {
                    SheetResponseDTO dto = objectMapper.convertValue(sheet, SheetResponseDTO.class);
                    dto.setWorkouts(sheet.getWorkouts().stream()
                            .map(workout -> objectMapper.convertValue(workout, WorkoutResponseDTO.class))
                            .collect(Collectors.toList()));
                    return dto;
                })
                .collect(Collectors.toList());

        return new PageImpl<>(sheetResponseDTOs, pageRequest, sheets.getTotalElements());
    }
}
