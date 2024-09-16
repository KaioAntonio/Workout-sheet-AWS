package com.kaio.dev.domain.workout;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.kaio.dev.domain.sheet.Sheet;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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

    @Column(name = "isCompleted")
    private Boolean isCompleted;

    @Lob
    @Column(name = "foto")
    @Type(type="org.hibernate.type.BinaryType")
    private byte[] foto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sheet_id")
    @JsonBackReference
    private Sheet sheet;
}
