package com.example.musicdata.services;


import com.example.musicdata.entities.MusicGroups;
import com.example.musicdata.interfaces.IMusicGroupsService;
import com.example.musicdata.parsers.CsvWriter;
import com.example.musicdata.parsers.JsonParser;
import com.example.musicdata.pojos.MusicGroupsPojo;
import com.example.musicdata.repositories.MusicGroupsRepository;
import com.opencsv.CSVWriter;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MusicGroupService implements IMusicGroupsService {

    private MusicGroupsRepository groupsRepository;

    private JsonParser jsonParser;

    @Override
    public void addGroup(MusicGroups groups) {
        groupsRepository.save(groups);
    }

    @Override
    public void deleteGroupById(Long id) {
        groupsRepository.deleteById(id);
    }

    @Override
    public void updateGroupById(Long id, MusicGroups updatedGroup) {
        Optional<MusicGroups> oldGroup = groupsRepository.findById(id);

        if(oldGroup.isPresent()){
            MusicGroups groupToUpdate = oldGroup.get();

            groupToUpdate.setName(updatedGroup.getName());
            groupToUpdate.setGenre(updatedGroup.getGenre());
            groupToUpdate.setLabels(updatedGroup.getLabels());

            groupsRepository.save(groupToUpdate);
        }
        else
            System.out.println("Group is noy found!");
    }

    @Override
    public List<MusicGroups> getGroupById(Long id) {

        return groupsRepository.findByMusicGroupById(id);

    }

    @Override
    public void uploadGroupsAndLabelsFromFile(MultipartFile fileToUpload){

        String fileName = fileToUpload.getOriginalFilename();

        List<MusicGroupsPojo> pojos;

        if(fileName != null && (fileName.endsWith(".json") || fileName.endsWith(".JSON"))) {
            pojos = jsonParser.readJsonFileByField(fileToUpload);
        }
        else
            throw new IllegalArgumentException("Unsupported format!");

        List<MusicGroups> uploadedGroups = pojos.stream()
                .map(this::pojoGroupToEntity)
                .collect(Collectors.toList());

        groupsRepository.saveAll(uploadedGroups);

    }

    @Override
    public List<MusicGroups> listAll() {
        return (List<MusicGroups>) groupsRepository.findAll();
    }

    @Override
    public void generateReport(HttpServletResponse response, String genre) {

        List<MusicGroups> groups;
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=music_groups_report.csv");

        if(genre != null){
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=music_groups_report_by_genre.csv");
            groups = findByGenre(genre);
        }
        else {
             groups = listAll();
        }

        try (ServletOutputStream outputStream = response.getOutputStream();
             OutputStreamWriter writer = new OutputStreamWriter(outputStream)) {

            // Створення CsvWriter
            CsvWriter csvWriter = new CsvWriter();

            // Отримання даних для звіту
            byte[] csvData = csvWriter.generateReport(groups);

            // Запис даних у вихідний потік
            outputStream.write(csvData);
            outputStream.flush();

        } catch (IOException e) {
            throw new RuntimeException("Error generating CSV report", e);
        }
    }

    @Override
    public List<MusicGroups> findByGenre(String genre) {
        return groupsRepository.findByGenre(genre);
    }

    @Override
    public Page<MusicGroups> listAllByPages(String genre, Pageable pageable) {

        if (genre != null) {
            return groupsRepository.findByGenre(genre, pageable);
        } else {
            return groupsRepository.findAll(pageable);
        }
    }


    private MusicGroups pojoGroupToEntity(MusicGroupsPojo musicGroupsPojo){

        MusicGroups convertedGroups = new MusicGroups();

        convertedGroups.setName(musicGroupsPojo.getName());
        convertedGroups.setGenre(musicGroupsPojo.getGenre());
        convertedGroups.setLabels(musicGroupsPojo.getLabelId());

        return convertedGroups;
    }



}
