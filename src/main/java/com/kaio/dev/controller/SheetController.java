package com.kaio.dev.controller;

import com.kaio.dev.domain.sheet.SheetService;
import com.kaio.dev.domain.sheet.dto.CreateSheetDTO;
import com.kaio.dev.domain.sheet.dto.SheetResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/sheets")
@RequiredArgsConstructor
public class SheetController {

    private final SheetService service;

    @Operation(summary = "Create a sheet")
    @PostMapping
    public ResponseEntity<SheetResponseDTO> createSheet(@RequestBody @Valid CreateSheetDTO sheetDTO) {
        return new ResponseEntity<>(service.createSheet(sheetDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Search a sheet or many sheets")
    @GetMapping()
    public ResponseEntity<Page<SheetResponseDTO>> searchSheet(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String nome,
            @RequestParam(defaultValue = "0") Integer pagina,
            @RequestParam(defaultValue = "10") Integer elementosPorPagina,
            @RequestParam(defaultValue = "nome") String nomeColuna,
            @RequestParam(defaultValue = "asc") String tipoOrdenacao) {

        Page<SheetResponseDTO> sheets = service.searchSheet(id, nome, pagina, elementosPorPagina, nomeColuna, tipoOrdenacao);
        return ResponseEntity.ok(sheets);
    }

}
