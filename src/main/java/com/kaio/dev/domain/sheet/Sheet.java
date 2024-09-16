package com.kaio.dev.domain.sheet;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kaio.dev.domain.user.User;
import com.kaio.dev.domain.workout.Workout;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sheets")
@Data
public class Sheet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sheet_id")
    private String sheetID;

    @Column(name = "nome")
    private String nome;

    @OneToMany(mappedBy = "sheet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Workout> workouts = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @JsonIgnore
    private User user;
}
