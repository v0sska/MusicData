package com.example.musicdata.controllers;

import com.example.musicdata.entities.MusicGroups;
import com.example.musicdata.services.MusicGroupService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/music_groups")
@AllArgsConstructor
public class MusicGroupController {

    private MusicGroupService groupService;


    @PostMapping
    public ResponseEntity<String> addGroup(@RequestBody MusicGroups musicGroups){
        groupService.addGroup(musicGroups);

        return new ResponseEntity<>("group is added!", HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public List<MusicGroups> getGroupById(@PathVariable Long id){
        return groupService.getGroupById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateGroupById(@PathVariable Long id, @RequestBody MusicGroups updatedGroup){
        groupService.updateGroupById(id, updatedGroup);

        return new ResponseEntity<>("group is updated!", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGroupById(@PathVariable Long id){
        groupService.deleteGroupById(id);

        return new ResponseEntity<>("group is deleted!", HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadGroupsAndLabelsFromFile(@RequestParam("file") MultipartFile fileToUpload){

        groupService.uploadGroupsAndLabelsFromFile(fileToUpload);

        return new ResponseEntity<>("groups and labels loaded from file!", HttpStatus.CREATED);

    }

    @PostMapping("/report")
    public ResponseEntity<Void> downloadReport(HttpServletResponse response , @RequestParam(required = false) String genre) {
        groupService.generateReport(response, genre);

        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    @PostMapping("/list")
    public Page<MusicGroups> getMusicGroups(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String genre) {

        Page<MusicGroups> musicGroupsPage = groupService.listAllByPages(genre, PageRequest.of(page, size));
        return ResponseEntity.ok().body(musicGroupsPage).getBody();
    }
}
