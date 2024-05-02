package com.example.musicdata.pojos;


import com.example.musicdata.entities.Labels;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class MusicGroupsPojo {

    private String name;

    private String genre;

    private Labels labelId;

}
