package com.example.musicdata.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Labels {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "founded_year")
    private int foundedYear;

    public Labels(String name, int foundedYear) {
        this.name = name;
        this.foundedYear = foundedYear;
    }
}
