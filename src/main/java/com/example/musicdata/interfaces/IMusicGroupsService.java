package com.example.musicdata.interfaces;


import com.example.musicdata.entities.MusicGroups;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IMusicGroupsService {

    void addGroup(MusicGroups groups);

    void deleteGroupById(Long id);

    void updateGroupById(Long id, MusicGroups updatedGroup);

    List<MusicGroups> getGroupById(Long id);


    void uploadGroupsAndLabelsFromFile(MultipartFile fileToUpload);

    List<MusicGroups> listAll();

     void generateReport(HttpServletResponse response, String genre);

    List<MusicGroups> findByGenre(String genre);

    Page<MusicGroups> listAllByPages(String genre, Pageable pageable);

}
