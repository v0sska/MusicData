package com.example.musicdata.interfaces;


import com.example.musicdata.entities.MusicGroups;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IMusicGroupsService {

    void addGroup(MusicGroups groups);

    void deleteGroupById(Long id);

    void updateGroupById(Long id, MusicGroups updatedGroup);

    List<MusicGroups> getGroupById(Long id);


    void uploadGroupsAndLabelsFromFile(MultipartFile fileToUpload);

}
