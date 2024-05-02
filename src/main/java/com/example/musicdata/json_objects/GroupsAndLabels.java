package com.example.musicdata.json_objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupsAndLabels {


    private String label;

    private String genre;

    private String group;

    @JsonProperty("founded_year")
    private String foundedYear;

}
