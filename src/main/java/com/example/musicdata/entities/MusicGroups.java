package com.example.musicdata.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "music_groups")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class MusicGroups {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String genre;

    @ManyToOne
    @JoinColumn(name = "label_id")
    private Labels labels;

    public MusicGroups(String name, String genre, Labels labels) {
        this.name = name;
        this.genre = genre;
        this.labels = labels;
    }
}
