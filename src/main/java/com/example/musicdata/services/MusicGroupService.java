package com.example.musicdata.services;


import com.example.musicdata.entities.MusicGroups;
import com.example.musicdata.interfaces.IMusicGroupsService;
import com.example.musicdata.parsers.JsonParser;
import com.example.musicdata.pojos.MusicGroupsPojo;
import com.example.musicdata.repositories.MusicGroupsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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


    private MusicGroups pojoGroupToEntity(MusicGroupsPojo musicGroupsPojo){

        MusicGroups convertedGroups = new MusicGroups();

        convertedGroups.setName(musicGroupsPojo.getName());
        convertedGroups.setGenre(musicGroupsPojo.getGenre());
        convertedGroups.setLabels(musicGroupsPojo.getLabelId());

        return convertedGroups;
    }

}
