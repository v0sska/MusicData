package com.example.musicdata.parsers;


import com.example.musicdata.entities.Labels;
import com.example.musicdata.json_objects.GroupsAndLabels;
import com.example.musicdata.pojos.LabelsPojo;
import com.example.musicdata.pojos.MusicGroupsPojo;
import com.example.musicdata.repositories.LabelsRepository;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class JsonParser {

    private LabelsRepository labelsRepository;

    private final ObjectMapper mapper = new ObjectMapper();

    public List<MusicGroupsPojo> readJsonFileByField(MultipartFile file) {

        List<MusicGroupsPojo> musicGroupsPojos = new ArrayList<>();


        try (com.fasterxml.jackson.core.JsonParser jsonParser = mapper.getFactory().createParser(file.getInputStream())) {
            // Переміщення до початку масиву
            while (jsonParser.nextToken() != JsonToken.START_ARRAY) {
                // Порожній цикл для переміщення парсера до початку масиву
            }

            // Зчитування об'єктів по одному
            // Зчитування об'єктів по одному
            while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                GroupsAndLabels jsonEntity = jsonParser.readValueAs(GroupsAndLabels.class);

                LabelsPojo labelsPojo = new LabelsPojo();
                MusicGroupsPojo musicGroupsPojo = new MusicGroupsPojo(); // Створення нового екземпляра тут

                labelsPojo.setName(jsonEntity.getLabel());
                labelsPojo.setFoundedYear(jsonEntity.getFoundedYear());
                musicGroupsPojo.setName(jsonEntity.getGroup());
                musicGroupsPojo.setGenre(jsonEntity.getGenre());

                if (isNotEmpty(musicGroupsPojo, labelsPojo)) {
                    List<LabelsPojo> labelsPojos = new ArrayList<>();
                    labelsPojos.add(labelsPojo);

                    System.out.println(labelsPojos);

                    List<Labels> labelsList = saveLabelsToDB(labelsPojos);

                    for (Labels labels : labelsList) {
                        musicGroupsPojo.setLabelId(labels);
                        musicGroupsPojos.add(musicGroupsPojo);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return musicGroupsPojos;

    }


    private boolean isNotEmpty(MusicGroupsPojo groupsPojo, LabelsPojo labelsPojo) {
        return labelsPojo.getName() != null || groupsPojo.getGenre() != null || groupsPojo.getName() != null || labelsPojo.getFoundedYear() != null;
    }

    private List<Labels> saveLabelsToDB(List<LabelsPojo> pojos){
        List<Labels> labels = pojos.stream()
                .map(this::pojoToEntityLabels)
                .collect(Collectors.toList());

        labelsRepository.saveAll(labels);

        return labels;
    }

    private Labels pojoToEntityLabels(LabelsPojo pojo){

        Labels labels = new Labels();

        labels.setName(pojo.getName());
        labels.setFoundedYear(Integer.parseInt(pojo.getFoundedYear()));

        return labels;
    }

}

