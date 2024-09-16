package com.kaio.dev.domain.workout;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kaio.dev.domain.sheet.Sheet;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "workout")
@Data
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workout_id")
    private Long workoutID;

    @Column(name = "nome")
    private String nome;

    @Column(name = "repeticoes")
    private Integer repeticoes;

    @Column(name = "series")
    private Integer series;

    @Lob
    @Column(name = "foto", columnDefinition = "BLOB")
    private byte[] foto;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sheet_id", referencedColumnName = "sheet_id")
    @JsonIgnore
    private Sheet sheet;
}
